package com.samsung.android.sdk.scs.base.feature;

import com.android.settingslib.widget.LottieColorUtils$$ExternalSyntheticOutline0;

import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class Feature {
    public static final Map sinceVersionMap;
    public static final List SUPPORTED_SIVS_FEATURES =
            Arrays.asList(
                    "FEATURE_SPEECH_RECOGNITION",
                    "FEATURE_SPEAKER_DIARISATION",
                    "FEATURE_AI_GEN_SUMMARY",
                    "FEATURE_AI_GEN_TRANSLATION",
                    "FEATURE_AI_GEN_TONE",
                    "FEATURE_AI_GEN_CORRECTION",
                    "FEATURE_AI_GEN_SMART_COVER",
                    "FEATURE_AI_GEN_SMART_REPLY",
                    "FEATURE_AI_GEN_EMOJI_AUGMENTATION",
                    "FEATURE_AI_GEN_NOTES_ORGANIZATION",
                    "FEATURE_AI_GEN_SMART_CAPTURE",
                    "FEATURE_AI_GEN_GENERIC",
                    "FEATURE_AI_GEN_USAGE",
                    "FEATURE_NEURAL_TRANSLATION",
                    "FEATURE_LANGUAGE_LIST_IDENTIFICATION",
                    "FEATURE_LANGUAGE_IDENTIFICATION_AND_GET_CANDIDATE",
                    "FEATURE_SIVS_CLASSIFICATION",
                    "FEATURE_SIVS_CONFIGURATION",
                    "FEATURE_SIVS_EXTRACTION");
    public static final List SUPPORTED_SBIS_FEATURES = Arrays.asList("FEATURE_AI_LEX_RANK");

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("FEATURE_IMAGE_GET_BOUNDARIES", 1);
        hashMap.put("FEATURE_IMAGE_GET_LARGEST_BOUNDARY", 1);
        hashMap.put("FEATURE_IMAGE_UPSCALE", 2);
        hashMap.put("FEATURE_SUGGESTION_SUGGEST_KEYWORD", 1);
        hashMap.put("FEATURE_SUGGESTION_SUGGEST_APP_CATEGORY", 2);
        hashMap.put("FEATURE_SUGGESTION_SUGGEST_APP_CATEGORY_DETAILS", 3);
        hashMap.put("FEATURE_SUGGESTION_SUGGEST_FOLDER_NAME", 2);
        hashMap.put("FEATURE_TEXT_GET_ENTITY", 2);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_DATETIME_NUMERAL", 9);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_PHONE_NUMBER", 9);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_POI", 10);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_BANK", 9);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_IS_MAPPABLE", 15);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_IS_RELATIVE", 15);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_IS_SPECIAL_DAY", 17);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_HAS_YEAR_MONTH_DAY", 18);
        hashMap.put("FEATURE_TEXT_GET_ENTITY_UPI_ID", 16);
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                20, hashMap, "FEATURE_TEXT_GET_ENTITY_UNIT", 13, "FEATURE_TEXT_GET_EVENT");
        hashMap.put("FEATURE_TEXT_GET_EVENT_HAS_YEAR_MONTH_DAY", 18);
        hashMap.put("FEATURE_TEXT_GET_EVENT_INDEX", 21);
        hashMap.put("FEATURE_TEXT_GET_KEY_PHRASE", 3);
        hashMap.put("FEATURE_TEXT_GET_KEY_PHRASE_EVENT_TITLE", 11);
        hashMap.put("FEATURE_TEXT_GET_DOCUMENT_CATEGORY", 5);
        hashMap.put("FEATURE_TEXT_GET_BNLP", 2);
        hashMap.put("FEATURE_TEXT_GET_BNLP_TOKEN", 12);
        hashMap.put("FEATURE_TEXT_DETECT_LANGUAGE", 9);
        hashMap.put("FEATURE_TEXT_CONVERT_UNIT", 19);
        hashMap.put("FEATURE_NATURAL_LANGUAGE_QUERY", 1);
        hashMap.put("FEATURE_SPEECH_RECOGNITION", SpeechRecognitionConst.SINCE_SPEECH_RECOGNITION);
        hashMap.put(
                "FEATURE_SPEAKER_DIARISATION", SpeechRecognitionConst.SINCE_SPEAKER_DIARISATION);
        hashMap.put("FEATURE_AI_GEN_SUMMARY", 6);
        hashMap.put("FEATURE_AI_GEN_TRANSLATION", 6);
        hashMap.put("FEATURE_AI_GEN_TONE", 6);
        hashMap.put("FEATURE_AI_GEN_CORRECTION", 6);
        hashMap.put("FEATURE_AI_GEN_SMART_COVER", 6);
        hashMap.put("FEATURE_AI_GEN_SMART_REPLY", 6);
        hashMap.put("FEATURE_AI_GEN_EMOJI_AUGMENTATION", 6);
        hashMap.put("FEATURE_AI_GEN_NOTES_ORGANIZATION", 6);
        hashMap.put("FEATURE_AI_GEN_SMART_CAPTURE", 6);
        hashMap.put("FEATURE_AI_GEN_GENERIC", 6);
        hashMap.put("FEATURE_NEURAL_TRANSLATION", 1);
        hashMap.put("FEATURE_LANGUAGE_LIST_IDENTIFICATION", 7);
        hashMap.put("FEATURE_LANGUAGE_IDENTIFICATION_AND_GET_CANDIDATE", 8);
        hashMap.put("FEATURE_SIVS_CLASSIFICATION", 6);
        hashMap.put("FEATURE_SIVS_EXTRACTION", 6);
        hashMap.put("FEATURE_SIVS_CONFIGURATION", 6);
        hashMap.put("FEATURE_AI_LEX_RANK", 1);
        hashMap.put("FEATURE_AI_GEN_USAGE", 6);
        sinceVersionMap = Collections.unmodifiableMap(hashMap);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x017f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int checkFeature(android.content.Context r10) {
        /*
            Method dump skipped, instructions count: 509
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.sdk.scs.base.feature.Feature.checkFeature(android.content.Context):int");
    }
}
