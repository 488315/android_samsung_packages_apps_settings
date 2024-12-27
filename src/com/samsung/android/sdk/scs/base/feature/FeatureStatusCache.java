package com.samsung.android.sdk.scs.base.feature;

import android.util.Log;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class FeatureStatusCache {
    public static final HashMap statusMap;

    static {
        HashMap hashMap = new HashMap();
        statusMap = hashMap;
        hashMap.put("FEATURE_IMAGE_GET_BOUNDARIES", -1000);
        hashMap.put("FEATURE_IMAGE_GET_LARGEST_BOUNDARY", -1000);
        hashMap.put("FEATURE_SUGGESTION_SUGGEST_KEYWORD", -1000);
        hashMap.put("FEATURE_SUGGESTION_SUGGEST_APP_CATEGORY", -1000);
        hashMap.put("FEATURE_SUGGESTION_SUGGEST_APP_CATEGORY_DETAILS", -1000);
        hashMap.put("FEATURE_SUGGESTION_SUGGEST_FOLDER_NAME", -1000);
        hashMap.put("FEATURE_TEXT_GET_ENTITY", -1000);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_DATETIME_NUMERAL", -1000);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_PHONE_NUMBER", -1000);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_POI", -1000);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_BANK", -1000);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_IS_MAPPABLE", -1000);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_IS_RELATIVE", -1000);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_IS_SPECIAL_DAY", -1000);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_HAS_YEAR_MONTH_DAY", -1000);
        hashMap.put("FEATURE_TEXT_GET_EVENT", -1000);
        hashMap.put("FEATURE_TEXT_GET_EVENT_HAS_YEAR_MONTH_DAY", -1000);
        hashMap.put("FEATURE_TEXT_GET_EVENT_INDEX", -1000);
        hashMap.put("FEATURE_TEXT_GET_KEY_PHRASE", -1000);
        hashMap.put("FEATURE_TEXT_GET_KEY_PHRASE_EVENT_TITLE", -1000);
        hashMap.put("FEATURE_TEXT_GET_DOCUMENT_CATEGORY", -1000);
        hashMap.put("FEATURE_TEXT_GET_BNLP", -1000);
        hashMap.put("FEATURE_TEXT_DETECT_LANGUAGE", -1000);
        hashMap.put("FEATURE_TEXT_CONVERT_UNIT", -1000);
        hashMap.put("FEATURE_NATURAL_LANGUAGE_QUERY", -1000);
    }

    public static void setStatus(int i) {
        statusMap.put("FEATURE_SPEECH_RECOGNITION", Integer.valueOf(i));
        Log.i(
                com.samsung.android.sdk.scs.base.utils.Log.concatPrefixTag("ScsApi@FeatureHolder"),
                "setStatus() : FEATURE_SPEECH_RECOGNITION : " + i);
    }
}
