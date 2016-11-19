package com.regula.sdk;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.regula.sdk.enums.MRZDetectorErrorCode;

import java.util.Calendar;

import static android.widget.Toast.LENGTH_SHORT;

@SuppressWarnings("deprecation") //Camera2 api not yet wide used
public class CaptureActivity extends Activity {
    private static final Handler HANDLER = new Handler();

    private static final int DELAY_BETWEEN_FRAMES = 750;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    private SharedPreferences mPreferences;
    private SurfaceView mOverlayDrawView;
    private View mOverlayControlView;
    private CameraPreview mCameraPreview;
    private FrameLayout mPreviewHolder;
    private MediaPlayer mAutoFocusSound;
    private ImageButton mLightBtn;
    private Toast mTimeToast;
    private SensorManager mSensorManager;
    private boolean mIsReturning, mSensorWorked = false, mIsLightOn = false, mIsAutoFocusing=false, mIsFlashAvailable;
    private int mAutoFocusX, mAutoFocusY, mDisplayWidth, mDisplayHeight, mCurrentStatus, mPrevStatus;
    @SuppressWarnings("FieldCanBeLocal") // for debugging use
    private boolean mDoShowWorkTime = false;
    private Camera mCamera;
    private int mCameraId;

    private final Runnable clearAutoFocusSquareRunnable = new Runnable() {
        @Override
        public void run() {
            clearCanvas();
        }
    };

    private final Runnable feedBackRunnable = new Runnable() {
        @Override
        public void run() {
            feedBack(mCurrentStatus);

            HANDLER.postDelayed(this,DELAY_BETWEEN_FRAMES);
        }
    };

    private PreviewCallback previewCallback = new PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, final Camera camera) {
            long start = Calendar.getInstance().getTimeInMillis();
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();
            mCurrentStatus = DocumentReader.Instance().processVideoFrame(data, size.width, size.height, parameters.getPreviewFormat());
            long finish = Calendar.getInstance().getTimeInMillis();

            if(mDoShowWorkTime) {
                if(mTimeToast==null){
                    mTimeToast = Toast.makeText(getApplicationContext(),"Lib work time: " + (finish - start) + "ms", Toast.LENGTH_LONG);
                } else {
                    mTimeToast.setText("Lib work time: " + (finish - start) + "ms");
                }
                mTimeToast.show();
            }

            if(mCurrentStatus!=mPrevStatus){
                Log.d(DocumentReader.DEBUG, "Detection status: " + mCurrentStatus);
                mPrevStatus = mCurrentStatus;
            }

            if (mCurrentStatus== MRZDetectorErrorCode.MRZ_RECOGNIZED_CONFIDENTLY) {
                Log.d(DocumentReader.DEBUG, "MRZ detector found an mrz, switching to ResultsActivity");
                mOverlayDrawView.setOnTouchListener(null);
                mCamera.cancelAutoFocus();
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                if(mTimeToast !=null)
                    mTimeToast.cancel();

                CaptureActivity.this.setResult(RESULT_OK);
                finish();
            }
        }
    };

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                if (mIsFlashAvailable && !mSensorWorked) {
                    float lightAmount = event.values[0];
                    Log.d(DocumentReader.DEBUG, "Current light amount:" + lightAmount);
                    if (lightAmount < 30)
                        switchLight(true);
                }
                mSensorWorked = true;
            } else if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
                if(mCamera!=null) {
                    mCameraPreview.setCameraRotation();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {  }
    };

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @SuppressLint("InflateParams") // overlay control view does not have a parent
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        mPreviewHolder = (FrameLayout) findViewById(R.id.cameraPreviewHolder);
        mOverlayDrawView = new SurfaceView(getApplicationContext());
        mOverlayDrawView.setBackgroundColor(Color.TRANSPARENT);

        mOverlayControlView = getLayoutInflater().inflate(R.layout.overlay_controls, null);
        mOverlayControlView.setBackgroundColor(Color.TRANSPARENT);
        mLightBtn = (ImageButton) mOverlayControlView.findViewById(R.id.lightBtn);
        mPreferences = getSharedPreferences(DocumentReader.DOCUMENT_READER_PREFERENCES, MODE_PRIVATE);

        mAutoFocusSound = MediaPlayer.create(CaptureActivity.this, R.raw.autofocus);

        mIsReturning = savedInstanceState!=null;

        mCameraId = getIntent().getIntExtra(DocumentReader.CAMERA_ID,-1);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!mIsReturning) {
            if (ContextCompat.checkSelfPermission(CaptureActivity.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(CaptureActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

            } else { //Permission is granted
                permissionIsGranted();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        ViewGroup viewGroup = (ViewGroup) mOverlayControlView.getParent();
        if(viewGroup!=null)
            viewGroup.removeView(mOverlayControlView);

        if (mSensorManager != null && sensorEventListener != null) {
            mSensorManager.unregisterListener(sensorEventListener);
            Log.d(DocumentReader.DEBUG, "OnPause: sensor released");
        }

        mPreviewHolder.removeAllViews();

        if(mCamera!=null) {
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
            Log.d(DocumentReader.DEBUG, "OnPause: camera released");
        }

        if(mAutoFocusSound!=null)
            mAutoFocusSound.release();

        HANDLER.removeCallbacks(clearAutoFocusSquareRunnable);
        HANDLER.removeCallbacks(feedBackRunnable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionIsGranted();
                } else {// permission denied, boo!
                    CaptureActivity.this.setResult(RESULT_CANCELED);
                    finish();
                }
            }
        }
    }

    private void permissionIsGranted() {
        mCamera = getCameraInstance();

        if (mCamera == null) {
            Toast.makeText(getApplicationContext(), R.string.no_camera_access, LENGTH_SHORT).show();
            this.finish();
        } else {
            mCameraPreview = new CameraPreview(CaptureActivity.this, mCamera, mCameraId, previewCallback);
            Log.d(DocumentReader.DEBUG, "OnResume: Camera preview created");

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            mDisplayWidth = metrics.widthPixels;
            mDisplayHeight = metrics.heightPixels;

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if(mCameraPreview.getParent()==null) {
                mPreviewHolder.addView(mCameraPreview);
            }
            mOverlayDrawView.setZOrderMediaOverlay(true);
            mOverlayDrawView.getHolder().setFormat(PixelFormat.TRANSPARENT);
            mOverlayDrawView.setLayoutParams(lp);
            if(mOverlayDrawView.getParent()==null) {
                mPreviewHolder.addView(mOverlayDrawView);
            }
            if(mOverlayControlView.getParent()==null) {
                CaptureActivity.this.addContentView(mOverlayControlView, lp);
            }

            Log.d(DocumentReader.DEBUG, "OnResume: All controls added to screen");

            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            if (mSensorManager != null) {
                Log.d(DocumentReader.DEBUG, "Sensor manager created");
                Sensor rotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
                if (rotationSensor != null) {
                    boolean isRotationSensorAdded = mSensorManager.registerListener(sensorEventListener, rotationSensor, SensorManager.SENSOR_DELAY_UI);
                    Log.d(DocumentReader.DEBUG, "OnResume: Rotation sensor listener added: " + isRotationSensorAdded);
                }

                if (mIsFlashAvailable) {
                    mLightBtn.setVisibility(View.VISIBLE);

                    mLightBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mIsLightOn = mPreferences.getBoolean(DocumentReader.IS_LIGHT_ON, false);
                            switchLight(!mIsLightOn);
                        }
                    });
                    if (!mIsReturning) { // app first started
                        mIsReturning = true;
                        Sensor lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

                        if (lightSensor != null) {
                            boolean isLightSensorAdded = mSensorManager.registerListener(sensorEventListener, lightSensor, 1);
                            Log.d(DocumentReader.DEBUG, "OnResume: Light sensor listener added: " + isLightSensorAdded);
                        }
                    } else {  // we are returning to the activity or unable to add light sensor
                        mIsLightOn = mPreferences.getBoolean(DocumentReader.IS_LIGHT_ON, false);
                        switchLight(mIsLightOn);
                    }
                } else {
                    mLightBtn.setVisibility(View.GONE);
                }
            }

            if (mCamera.getParameters().getSupportedFocusModes() != null
                    && mCamera.getParameters().getSupportedFocusModes().contains(Parameters.FOCUS_MODE_AUTO)) {

                mOverlayDrawView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View arg0, final MotionEvent arg1) {

                        if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                            mIsAutoFocusing = true;
                            HANDLER.removeCallbacks(clearAutoFocusSquareRunnable);
                            mAutoFocusX = (int) arg1.getX();
                            mAutoFocusY = (int) arg1.getY();

                            final Camera.Parameters parameters = mCamera.getParameters();
                            final Camera.Size size = parameters.getPreviewSize();

                            drawRect(mOverlayDrawView, mAutoFocusX, mAutoFocusY, size.width / 10,
                                    size.height / 10, Color.YELLOW);

                            parameters.setFocusMode(Parameters.FOCUS_MODE_AUTO);
                            mCamera.setParameters(parameters);
                            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                                @Override
                                public void onAutoFocus(boolean success, Camera camera) {
                                    Log.d(DocumentReader.DEBUG, "AutoFocus started");
                                    if (success) {
                                        Log.d(DocumentReader.DEBUG, "AutoFocus success");
                                        drawRect(mOverlayDrawView, mAutoFocusX, mAutoFocusY, size.width / 10,
                                                size.height / 10, Color.GREEN);
                                        if (mAutoFocusSound != null)
                                            mAutoFocusSound.start();
                                    } else {
                                        Log.d(DocumentReader.DEBUG, "AutoFocus failed");
                                        drawRect(mOverlayDrawView, mAutoFocusX, mAutoFocusY, size.width / 10,
                                                size.height / 10, Color.RED);
                                    }

                                    if (parameters.getSupportedFocusModes().contains(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                                        parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                                        mCamera.setParameters(parameters);
                                    }

                                    HANDLER.postDelayed(clearAutoFocusSquareRunnable, 1000);
                                    mIsAutoFocusing = false;
                                }
                            });
                        }
                        return false;
                    }

                });
            }

            HANDLER.post(feedBackRunnable);
        }
    }

    private void switchLight(boolean light){
        Camera.Parameters parameters = mCamera.getParameters();
        if (light) {
            parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);
            mLightBtn.setImageResource(R.drawable.lightbulb_on);
        } else {
            parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            mLightBtn.setImageResource(R.drawable.lightbulb_off);
        }

        mIsLightOn = light;
        mPreferences.edit().putBoolean("mIsLightOn", mIsLightOn).apply();
        Log.d(DocumentReader.DEBUG, "Switch light: " + mIsLightOn);
    }

    private void drawRect(SurfaceView view, int x, int y, int width, int height, int color) {
        SurfaceHolder holder = view.getHolder();
        Paint p = new Paint();
        p.setStyle(Style.STROKE);
        p.setStrokeWidth(2);

        Canvas c = holder.lockCanvas();
        if (c != null) {
            c.drawColor(Color.TRANSPARENT, Mode.CLEAR);
            p.setColor(color);
            c.drawRoundRect(new RectF(x - width / 2, y - height / 2, x + width / 2, y + height / 2), 10, 10, p);
            holder.unlockCanvasAndPost(c);
        }
    }

    private void feedBack(int code) {
        if (!mIsAutoFocusing) {
            SurfaceHolder holder = mOverlayDrawView.getHolder();
            Canvas canvas = holder.lockCanvas();

            if (canvas != null) {
                try {
                    Camera.Size size = mCamera.getParameters().getPreviewSize();
                    double scaleH = (double) mDisplayHeight / (double) size.height;

                    float y = mDisplayHeight / 2;
                    float x = mDisplayWidth / 2;

                    canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);

                    canvas.save();

                    TextPaint paint=new TextPaint();
                    paint.setStrokeWidth(0);
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setColor(Color.CYAN);
                    paint.setTextSize((float) (80 * scaleH));

                    String mText="";
                    switch (code) {
                        case MRZDetectorErrorCode.NO_DETECTION_MRZ_IS_VERY_CLOSE_TO_LEFT:
                        case MRZDetectorErrorCode.NO_DETECTION_MRZ_IS_VERY_CLOSE_TO_RIGHT:
                        case MRZDetectorErrorCode.NO_DETECTION_MRZ_IS_VERY_CLOSE_TO_TOP:
                        case MRZDetectorErrorCode.NO_DETECTION_MRZ_IS_VERY_CLOSE_TO_BOTTOM:
                            mText = getString(R.string.strMoveToCenter);
                            break;
                        case MRZDetectorErrorCode.NO_DETECTION_MRZ_IS_OUT_OF_FOCUS:
                        case MRZDetectorErrorCode.NO_RECOGNITION_MRZ_IS_OUT_OF_FOCUS:
                            mText = getString(R.string.strOutOfFocus);
                            break;
                        case MRZDetectorErrorCode.NO_RECOGNITION_STRONG_PERSPECTIVE:
                            mText = getString(R.string.strHoldStraight);
                            break;
                        case MRZDetectorErrorCode.NO_DETECTION_MRZ_VERY_SMALL:
                        case MRZDetectorErrorCode.NO_RECOGNITION_MRZ_IS_VERY_SMALL:
                            mText = getString(R.string.strMoveCloser);
                            break;
                    }

                    StaticLayout mTextLayout = new StaticLayout(mText, paint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                    canvas.translate(x, y);
                    mTextLayout.draw(canvas);
                    canvas.restore();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void clearCanvas() {
        SurfaceHolder holder = mOverlayDrawView.getHolder();
        Canvas c = holder.lockCanvas();
        if(c!=null) {
            c.drawColor(Color.TRANSPARENT, Mode.CLEAR);
            holder.unlockCanvasAndPost(c);
        }
    }

    private Camera getCameraInstance() {
        if (CaptureActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            if(mCameraId==-1) {
                int numberOfCameras = Camera.getNumberOfCameras();
                Log.d(DocumentReader.DEBUG, "Number of cameras found:" + numberOfCameras);
                for (int i = 0; i < numberOfCameras; i++) {
                    Camera.CameraInfo info = new Camera.CameraInfo();
                    Camera.getCameraInfo(i, info);

                    if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        mCameraId = i;
                    }
                }
            }

            Camera camera = Camera.open(mCameraId);

            mIsFlashAvailable = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
                    && camera.getParameters().getSupportedFlashModes() != null
                    && camera.getParameters().getSupportedFlashModes().contains(Parameters.FLASH_MODE_TORCH);

            return camera;
        }
        return null;
    }
}
