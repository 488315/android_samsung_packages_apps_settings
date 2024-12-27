package com.samsung.android.settings.logging.status;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SharedPreferencesHelper {
    public static void savePreferences(Context context, String str, String str2) {
        SharedPreferences.Editor edit =
                context.getSharedPreferences("settings_sa_status_shared_prefs", 0).edit();
        edit.putString(str, str2);
        Log.i("Settings_SA_SharedPref", "key : " + str + " value : " + str2);
        edit.apply();
    }
}
