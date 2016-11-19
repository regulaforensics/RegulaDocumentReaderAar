package com.regula.sdk;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

/** A basic Camera preview class */
@SuppressWarnings("deprecation") //Camera 2 Api not yet used widely
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{
    private static final int MAX_WIDTH = 2560, MAX_HEIGHT = 1440;
    private SurfaceHolder mHolder;
    private PreviewCallback cb;
    private Activity mContext;
    private int mCurrentDisplayRotation, mCameraId;
    private Camera mCamera;
    private Camera.Parameters params;


    public CameraPreview(Activity context, Camera camera, int cameraId,  PreviewCallback cb) {
        super(context);
        this.cb = cb;
        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mCamera = camera;
        mCameraId = cameraId;
        if(mCamera!=null) {
            params = mCamera.getParameters();
            mCamera.setErrorCallback(new Camera.ErrorCallback() {
                @Override
                public void onError(int error, Camera camera) {
                    Log.d(DocumentReader.DEBUG, "Camera error: " + error);
                }
            });
        }
        Log.d(DocumentReader.DEBUG, "CameraPreview created!");
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {}

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (mHolder.getSurface() == null)
            return;

        try {
            mCamera.stopPreview();
        } catch (Exception e) { // ignore: tried to stop a non-existent preview
            e.printStackTrace();
        }

        try {
            holder.setFormat(PixelFormat.TRANSPARENT);
            Camera.Size size = getOptimalPreviewSize(params.getSupportedPreviewSizes(), w, h);
            params.setPreviewSize(size.width, size.height);

            boolean doManualFocus = false;
            if(params.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE))
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            else
                doManualFocus = true;

            Log.d(DocumentReader.DEBUG, "Manual focus: " + doManualFocus);

            if(params.isZoomSupported()) {
                Log.d(DocumentReader.DEBUG, "Current zoom:" + params.getZoom() + " setting zoom to 0");
                params.setZoom(0);
            }

            //hack to make Nexus work )
            Camera.Size picturesize = getPictureSize(params.getSupportedPictureSizes(), size);
            params.setPictureSize(picturesize.width, picturesize.height);

            Log.d(DocumentReader.DEBUG, "Picture size set to " +picturesize.width + "*" + picturesize.height);

            mCamera.setParameters(params);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(cb);
            mCamera.startPreview();

            Log.d(DocumentReader.DEBUG, "Camera preview started");

            setCameraRotation();

            if(doManualFocus)
                mCamera.autoFocus(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setCameraRotation() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraId, info);
        int rotation = mContext.getWindowManager().getDefaultDisplay().getRotation();

        if (mCurrentDisplayRotation != rotation) {
            Log.d(DocumentReader.DEBUG, "Device rotated, rotation: " + rotation);
            mCurrentDisplayRotation = rotation;
            int degrees = 0;
            switch (mCurrentDisplayRotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
            }

            if(mCamera!=null) {
                int result = (info.orientation - degrees + 360) % 360;

                if (Build.VERSION.SDK_INT < 14) {
                    mCamera.stopPreview();
                }

                mCamera.setDisplayOrientation(result);

                if (Build.VERSION.SDK_INT < 14) {
                    mCamera.startPreview();
                }
            }
        }
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio=(double)w / h;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - h) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - h);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - h) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - h);
                }
            }
        }
        return optimalSize;
    }

    @SuppressWarnings("unused") //Might be used
    private Camera.Size getBiggestCameraSize(List<Camera.Size> sizes) {
        Camera.Size biggest = null;
        for (Camera.Size size : sizes) {
            if (size.width <= MAX_WIDTH && size.height <= MAX_HEIGHT) { //Equal or less than 4k resolution
                if (biggest == null)
                    biggest = size;
                else {
                    if (size.width > biggest.width)
                        biggest = size;
                    else if (size.width == biggest.width)
                        if (size.height > biggest.height)
                            biggest = size;
                }
            }
        }
        return biggest;
    }

    private Camera.Size getPictureSize(List<Camera.Size> sizes, Camera.Size camSize) {
        if(sizes.contains(camSize)){
            return camSize;
        } else {
            float ratio = camSize.width / camSize.height;
            for (Camera.Size pictureSize : sizes) {
                if ((float) pictureSize.width / pictureSize.height == ratio)
                    return pictureSize;
            }
            return sizes.get(0);
        }
    }
}
