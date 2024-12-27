package com.samsung.android.settings.voiceinput.offline;

import android.content.Context;
import android.preference.PreferenceManager;

import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class OfflineLanguageUtil {
    private static final String TAG = "@VoiceIn: OfflineLanguageUtil";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface onLangPackResultListener {
        void onLangPackResultSubscribed();

        void onLangPackResultUpdated();
    }

    public static void setLanguagePackageStatus(Context context, int i, String str) {
        SemLog.i(TAG, "setLanguagePackageStatus locale :" + str + " Status:" + i);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(str, i).apply();
    }

    public static void setLanguagePackageVersion(Context context, String str, String str2) {
        SemLog.i(TAG, "setLanguagePackageVersion locale :" + str + " version:" + str2);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(str + "_version", str2)
                .apply();
    }
}
