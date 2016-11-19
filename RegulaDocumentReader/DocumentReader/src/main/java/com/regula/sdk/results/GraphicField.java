package com.regula.sdk.results;

import android.graphics.Bitmap;

/***
 * Class, contains fields, describing single graphic field of the document
 */
public class GraphicField {
    public int fieldType, fieldRectLeft, fieldRectRight, fieldRectTop, fieldRectBottom, fileImageLength;
    public String fieldName, fileImageFormat;
    public Bitmap fileImage;
}
