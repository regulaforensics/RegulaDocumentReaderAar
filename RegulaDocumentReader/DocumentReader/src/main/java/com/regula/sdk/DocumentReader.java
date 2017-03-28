package com.regula.sdk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.AsyncTask;
import android.util.Log;

import com.regula.sdk.enums.MRZDetectorErrorCode;
import com.regula.sdk.enums.eGraphicFieldType;
import com.regula.sdk.enums.eVisualFieldType;
import com.regula.sdk.results.GraphicField;
import com.regula.sdk.results.TextField;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/***
 * Class, which provides API for easy MRZ recognizing and results receiving.
 * Takes care of initializing and configuring all necessary resources.
 */
public final class DocumentReader {
    @SuppressWarnings("unused")
    public static final int FORMAT_JSON = 0, FORMAT_XML = 1, READER_REQUEST_CODE = 1;
    public static final String CAMERA_ID = "CameraId";

    static final String DEBUG = "MRZ_DETECTOR";
    static final String IS_LIGHT_ON = "mIsLightOn";
    static final String DOCUMENT_READER_PREFERENCES = "DocReaderPrefs";

    private final boolean DO_DEBUG = false;
    private final ArrayList<TextField> mParsedResultItems = new ArrayList<>();

    private Bitmap mSourceImage;
    private ArrayList<GraphicField> mGraphicFields;
    private String mRawResult;
    private AsyncTask<Void,Void,Void> mResultsParsingTask;
    private boolean isInitialized;

    private static DocumentReader mInstance;

    /**
     * This method returns a Singleton instance of Documen Reader class
     */
    public static DocumentReader Instance(){
        if(mInstance==null){
            mInstance = new DocumentReader();
        }

        return mInstance;
    }

    private DocumentReader(){
        isInitialized = false;
        mGraphicFields = new ArrayList<>();
    }

    //region API

    /***
     * Performs the resource loading and overall initialization of Document Reader instance
     * @param context Current context
     * @param license Library's license
     * @return Success flag of initialization process
     */
    public boolean Init(Context context, byte[] license){
        Log.d(DEBUG, "===Document Reader init started===");

        InputStream datInput = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            System.loadLibrary("MrzDetector");

            Log.d(DEBUG, "MRZ detector loaded");
            datInput = context.getResources().openRawResource(R.raw.mrzdetector);
            int i;
            try {
                i = datInput.read();
                while (i != -1)
                {
                    byteArrayOutputStream.write(i);
                    i = datInput.read();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] dat = byteArrayOutputStream.toByteArray();
            setMrzDetectorDat(dat);
            Log.d(DEBUG, "Reading resources finished");

            Log.d(DEBUG,"Setting license");
            boolean licSuccess = setLicense(context, license);
            Log.d(DEBUG, "License loaded:" + licSuccess);

            isInitialized = licSuccess;
        }catch (Exception | UnsatisfiedLinkError ex){
            ex.printStackTrace();
        } finally
         {
            try{
                if(datInput!=null){
                    datInput.close();
                }
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d(DEBUG, "===Document Reader init finished===");

        return isInitialized;
    }

    /***
     * Use this method to find and recognize MRZ on single images, like ones taken earlier and stored in Gallery
     * @param bitmap Bitmap to find and recognize MRZ on
     * @return One of MRZDetectorErrorCode values
     */
    @MRZDetectorErrorCode.MrzDetectorCodes
    public int processBitmap(Bitmap bitmap) throws IllegalStateException {
        if(isInitialized) {
            clearResults();

            int mFrameSize = bitmap.getWidth() * bitmap.getHeight();
            int pixels[] = new int[mFrameSize];
            bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

            ByteBuffer byteBuffer = ByteBuffer.allocate(pixels.length * 4);
            IntBuffer intBuffer = byteBuffer.asIntBuffer();
            intBuffer.put(pixels);

            byte[] array = byteBuffer.array();

            int[] mrzWidth = new int[1], mrzHeight = new int[1], MRZLines = new int[1], MRZSymbolsPerLine = new int[1], MRZCoords = new int[8], errorCode = new int[1];
            byte[] mrzPixels = new byte[4 * bitmap.getWidth() * bitmap.getHeight()], recognizedMRZ = new byte[200];
            String result = detectMRZ(bitmap.getWidth(), bitmap.getHeight(), array, errorCode, mrzWidth, mrzHeight, mrzPixels, recognizedMRZ,
                    MRZLines, MRZSymbolsPerLine, MRZCoords, true, DO_DEBUG);

            int errorcode = errorCode[0];
            if (errorcode == MRZDetectorErrorCode.MRZ_RECOGNIZED_CONFIDENTLY) {
                writeResults(mrzWidth, mrzHeight, MRZCoords, mrzPixels, result);
                mSourceImage = bitmap;
            }
            return MRZDetectorErrorCode.getCode(errorcode);
        }

        throw new IllegalStateException("Document Reader is not initialized. Please, call Init() before use!");
    }

    /***
     * Use this method to find and recognize MRZ on sequential images, like video stream frames
     * @param frame Video frame as byte array
     * @param width Video frame width (Camera.getParameters().getPreviewSize().width)
     * @param height Video frame width (Camera.getParameters().getPreviewSize().height)
     * @param previewFormat Video frame format (Camera.getParameters().getPreviewFormat())
     * @return One of MRZDetectorErrorCode values
     */
    @SuppressWarnings("WeakerAccess") //this method could potentially be used outside the package
    public int processVideoFrame(byte[] frame, int width, int height, int previewFormat) throws IllegalStateException {
        if(isInitialized) {
            if (frame != null) {
                clearResults();

                int[] mrzWidth = new int[1], mrzHeight = new int[1], MRZLines = new int[1], MRZSymbolsPerLine = new int[1], MRZCoords = new int[8], errorCode = new int[1];
                byte[] mrzPixels = new byte[4 * width * height], recognizedMRZ = new byte[200];

                String result = detectMRZ(width, height, frame, errorCode, mrzWidth, mrzHeight, mrzPixels, recognizedMRZ, MRZLines, MRZSymbolsPerLine, MRZCoords, false, DO_DEBUG);

                int errorcode = errorCode[0];
                if (errorcode == MRZDetectorErrorCode.MRZ_RECOGNIZED_CONFIDENTLY) {
                    writeResults(mrzWidth, mrzHeight, MRZCoords, mrzPixels, result);

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    YuvImage yuvImage = new YuvImage(frame, previewFormat, width, height, null);
                    yuvImage.compressToJpeg(new Rect(0, 0, width, height), 50, out);
                    byte[] imageBytes = out.toByteArray();
                    mSourceImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    try {
                       out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return errorcode;
                }
            }
            return MRZDetectorErrorCode.INPUT_CONTAINER_NULL_POINTER;
        }

        throw new IllegalStateException("Document Reader is not initialized. Please, call Init() before use!");
    }

    /***
     * Use this method to get "raw" MRZ detection result in XML format
     * @return MRZ detection result as XML string
     */
    @SuppressWarnings("unused") //This method could potentially be used
    public String getRawResult(){
        //int resultFormat
        //TODO return result depending on requested format
        waitForResult();
        return mRawResult;
    }

    /***
     * Use this method to get selected graphic field from MRZ detection results (currently, only cropped MRZ area is available)
     * @param fieldType One of eGraphicFieldType members, representing the selected field (currently, only gt_Other is used)
     * @return Selected graphic field or null, if there is no selected field in results
     */
    public GraphicField getGraphicFieldByType(@eGraphicFieldType.GraphicFields int fieldType) {
        if (mGraphicFields != null) {
            for (GraphicField item : mGraphicFields) {
                if (item.fieldType == fieldType) {
                    return item;
                }
            }
        }
        return null;
    }

    /***
     * Use this method, to get video stream frame MRZ was detected and recognized on
     * @return Bitmap of source video frame
     */
    public Bitmap getSourceImage(){
        return mSourceImage;
    }

    /***
     * Use this method to receive all the graphic fields from MRZ detection results
     * @return List of gGraphicField objects
     */
    @SuppressWarnings("unused") //This method could potentially be used
    public List<GraphicField> getAllGraphicFields() {
        return mGraphicFields;
    }

    /***
     * Use this method to get selected text field from MRZ detection parsed results.
     * @param fieldType one of eVisualFieldType members, representing the selected field
     * @return Selected text field or null, if there is no selected field in results
     */
    @SuppressWarnings("unused") //This method could potentially be used
    public TextField getTextFieldByType(@eVisualFieldType.VisualFieldTypes int fieldType) {
        waitForResult();
        if (mParsedResultItems.size()>0) {
            for (TextField item : mParsedResultItems) {
                if (item.fieldType == fieldType) {
                    return item;
                }
            }
        }
        return null;
    }

    /***
     * Use this method to get all the text fields from parsed MRZ results
     * @return List of TextField objects
     */
    public List<TextField> getAllTextFields() {
        waitForResult();
        return mParsedResultItems;
    }

    /***
     * Use this method to show detailed information screen about the MRZ detector
     */
    public void about(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /***
     * Use this method to get Document reader's recognition engine version
     */
    @SuppressWarnings("WeakerAccess") //This method could potentially be used outside the package
    public String getLibVersion(){
        return getVersion();
    }

    //endregion

    // region Private methods
    private void writeResults(int[] mrzWidth, int[] mrzHeight, int[] MRZCoords, byte[] mrzPixels, String result) {
        if(result!=null && !result.isEmpty()) {
            mRawResult = result;
            if (mrzPixels != null){
                GraphicField mrz = new GraphicField();
                mrz.fieldType = eGraphicFieldType.gt_Other;
                mrz.fieldRectBottom = Math.max(MRZCoords[5], MRZCoords[7]);
                mrz.fieldRectLeft = Math.min(MRZCoords[0], MRZCoords[6]);
                mrz.fieldRectRight = Math.max(MRZCoords[2], MRZCoords[4]);
                mrz.fieldRectTop = Math.min(MRZCoords[1], MRZCoords[3]);

                byte[] croppedMrz = Arrays.copyOfRange(mrzPixels, 0, (4 * mrzWidth[0] * mrzHeight[0]));
                mGraphicFields.add(mrz);
                Bitmap bm = Bitmap.createBitmap(mrzWidth[0], mrzHeight[0], Bitmap.Config.ARGB_8888);
                ByteBuffer b = ByteBuffer.allocate(4 * croppedMrz.length);
                b.put(croppedMrz, 0, croppedMrz.length);
                b.rewind();
                bm.copyPixelsFromBuffer(b);

                mrz.fileImage =bm;
                mGraphicFields.add(mrz);
            }

            mResultsParsingTask = createParsingTask(mRawResult).execute();
        }
    }

    private void clearResults(){
        if(mSourceImage!=null) {
            mSourceImage.recycle();
        }
        mGraphicFields.clear();
        mParsedResultItems.clear();
        mRawResult = null;
    }

    private AsyncTask<Void,Void,Void> createParsingTask(final String result){
        return new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (result != null && !"".equals(result)) {
                    synchronized (mParsedResultItems) {
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

                        DocumentBuilder db = null;
                        try {
                            db = dbf.newDocumentBuilder();
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        }
                        InputSource is = new InputSource();
                        is.setCharacterStream(new StringReader(result));
                        Document doc = null;
                        if (db != null) {
                            try {
                                doc = db.parse(is);
                            } catch (SAXException | IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (doc != null) {
                            NodeList nodes = doc.getElementsByTagName("Document_Text_Data_Field");
                            for (int k = 0; k < nodes.getLength(); k++) {
                                Element el = (Element) nodes.item(k);
                                TextField item = new TextField();

                                item.fieldType = Integer.parseInt(el.getElementsByTagName("FieldType").item(0).getFirstChild().getNodeValue());
                                item.bufText = el.getElementsByTagName("Buf_Text").item(0).getFirstChild().getNodeValue();
                                item.validity = Integer.parseInt(el.getElementsByTagName("Validity").item(0).getFirstChild().getNodeValue());
                                item.reserved2 = Integer.parseInt(el.getElementsByTagName("Reserved2").item(0).getFirstChild().getNodeValue());

                                mParsedResultItems.add(item);
                            }
                        }
                    }
                }
                return null;
            }
        };
    }

    private void waitForResult() {
        if(mResultsParsingTask!=null){
            try {
                mResultsParsingTask.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //endregion

    //region Native stuff
    @SuppressWarnings("JniMissingFunction") //this function is contained inside mrzdetectorpro.so lib
    private static native String detectMRZ(int width, int height, byte[] pixelsIN, int[] errorCode, int[]/* 1 */mrzWidth,
                                           int[]/* 1 */mrzHeight, byte[] mrzPixels, byte[]/* 200 */recognizedMRZ, int[]/* 1 */MRZLines,
                                           int[]/* 1 */MRZSymbolsPerLine, int[]/* 8 */MRZCoords, boolean inputIsSingleImage, boolean writeDebugInfo);

    @SuppressWarnings("JniMissingFunction") //this function is contained inside mrzdetectorpro.so lib
    private static native String getVersion();

    @SuppressWarnings("JniMissingFunction") //this function is contained inside mrzdetectorpro.so lib
    private static native boolean setLicense(Object context, byte[] dataSize);

    @SuppressWarnings("JniMissingFunction") //this function is contained inside mrzdetectorpro.so lib
    private static native void setMrzDetectorDat(byte[] dataSize);
    //endregion

    private static native String stringFromJNI();
}
