package com.regula.sdk.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/***
 * Class, contains fields, describing all possible types of document's graphic fields (portrait, signature etc.)
 */
public class eGraphicFieldType {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            gf_Portrait,
              gf_Fingerpr,
              gf_Eye,
              gf_Signature,
              gt_BarCode,
              gt_Proof_Of_Citizenship,
              gt_Document_Front,
              gt_Document_Rear,
              gf_ColorDynamic,
              gf_GhostPortrait,
              gt_Other,
              gf_Finger_LeftThumb,
              gf_Finger_LeftIndex,
              gf_Finger_LeftMiddle,
              gf_Finger_LeftRing,
              gf_Finger_LeftLittle,
              gf_Finger_RightThumb,
              gf_Finger_RightIndex,
              gf_Finger_RightMiddle,
              gf_Finger_RightRing,
              gf_Finger_RightLittle, 
    })

    public @interface GraphicFields{}
    
public static final int gf_Portrait = 201;
public static final int  gf_Fingerpr = 202;
public static final int  gf_Eye = 203;
public static final int  gf_Signature = 204;
public static final int  gt_BarCode = 205;
public static final int  gt_Proof_Of_Citizenship = 206;
public static final int  gt_Document_Front = 207;
public static final int  gt_Document_Rear = 208;
public static final int  gf_ColorDynamic = 209;
public static final int  gf_GhostPortrait = 210;
public static final int  gt_Other = 250;
public static final int  gf_Finger_LeftThumb = 300;
public static final int  gf_Finger_LeftIndex = 301;
public static final int  gf_Finger_LeftMiddle = 302;
public static final int  gf_Finger_LeftRing = 303;
public static final int  gf_Finger_LeftLittle = 304;
public static final int  gf_Finger_RightThumb = 305;
public static final int  gf_Finger_RightIndex = 306;
public static final int  gf_Finger_RightMiddle = 307;
public static final int  gf_Finger_RightRing = 308;
public static final int  gf_Finger_RightLittle = 309;
};

