package com.regula.sdk.translation;

import android.content.Context;

/***
 * Class provides Translation functionality
 */
public class TranslationUtil {

    /***
     * Use this method, to get string representation of the specified field type
     * @param context Context object
     * @param textFieldType One of eVisualFieldType member
     * @return String representation of the specified field type
     */
    public static String getTextFieldTranslation(Context context, int textFieldType){
        if(EnumStringMapping.eVisualFieldTypeStrings.containsKey(textFieldType)){
            return Translate(context, EnumStringMapping.eVisualFieldTypeStrings.get(textFieldType));
        }
        return "";
    }

    /***
     * Use this method, to get string representation of the specified field type
     * @param context Context object
     * @param graphicFieldtype One of eGraphicFieldType member
     * @return String representation of the specified field type
     */
    public static String getGraphicFieldTranslation(Context context, int graphicFieldtype){
        if(EnumStringMapping.eGraphicFieldTypeStrings.containsKey(graphicFieldtype)){
            return Translate(context, EnumStringMapping.eGraphicFieldTypeStrings.get(graphicFieldtype));
        }
        return "";
    }

	private static String Translate(Context context, String name) {
		int id = 0;
		try {
			id = context.getResources().getIdentifier(name, "string", context.getPackageName());
		} catch (Exception exception) {}
		if (id != 0)
			return context.getResources().getString(id);

		return name;
	}
}