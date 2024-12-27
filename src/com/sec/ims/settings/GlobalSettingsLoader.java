package com.sec.ims.settings;

import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class GlobalSettingsLoader {
    private static final String LOG_TAG = "GlobalSettingsLoader";

    public static GlobalSettings loadGlobalSettings(Context context, String str) {
        return GlobalSettings.getInstance(context);
    }

    public static GlobalSettings loadGlobalSettings(Context context, int i) {
        return GlobalSettings.getInstance(context, i);
    }
}
