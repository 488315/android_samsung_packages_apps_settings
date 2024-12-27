package com.samsung.android.settings.analyzestorage.presenter.utils.preference;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class PreferenceUtils {
    public static void resetPrevMpGenerationForAnalyzeStorage(Context context, int i) {
        SharedPreferences.Editor edit =
                PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putLong("prev_mp_generation" + i, -1L);
        edit.apply();
    }

    public static void setExtremelyFullCondition(Context context, boolean z) {
        SharedPreferences.Editor edit =
                PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putBoolean("extremely_full_condition", z);
        edit.apply();
    }
}
