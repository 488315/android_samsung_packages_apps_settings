package com.samsung.android.settings.voiceinput;

import android.content.Context;
import android.preference.PreferenceManager;

import com.samsung.android.util.SemLog;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class VoiceInputUtils {
    public static final String BIXBY_PACKAGE_NAME = "com.samsung.android.bixby.agent";
    public static String TAG = "@VoiceIn: VoiceInputUtils";

    public static int getVersion(String str) {
        if (str != null
                && !str.isEmpty()
                && str.chars().allMatch(new VoiceInputUtils$$ExternalSyntheticLambda0())) {
            return Integer.parseInt(str);
        }
        SemLog.d(TAG, "Invalid versionCode");
        return 0;
    }

    public static boolean isLangPackRequestNeeded(Context context) {
        ZonedDateTime minusHours = ZonedDateTime.now().minusHours(24L);
        SemLog.i("@VoiceIn: OfflineLanguageUtil", "getLastUpdateTime: ");
        return ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(
                                PreferenceManager.getDefaultSharedPreferences(context)
                                        .getLong(Constants.DICTATION_LANGPACK_REQUEST_TIME, 0L)),
                        ZoneId.systemDefault())
                .isBefore(minusHours);
    }
}
