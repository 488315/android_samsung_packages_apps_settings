package com.samsung.android.settingslib;

import android.os.Build;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SettingsLibRune {
    public static final boolean MENU_SHOW_EXTERNAL_CODE;

    static {
        String str = Build.TYPE;
        MENU_SHOW_EXTERNAL_CODE = "eng".equals(str) || "userdebug".equals(str);
    }
}
