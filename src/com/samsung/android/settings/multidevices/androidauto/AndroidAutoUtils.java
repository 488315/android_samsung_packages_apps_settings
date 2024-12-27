package com.samsung.android.settings.multidevices.androidauto;

import android.content.Context;
import android.content.pm.PackageManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AndroidAutoUtils {
    public static boolean isAndroidAutoInstalledEnabled(Context context) {
        int applicationEnabledSetting;
        PackageManager packageManager = context.getPackageManager();
        try {
            applicationEnabledSetting =
                    packageManager.getApplicationEnabledSetting(
                            "com.google.android.projection.gearhead");
            packageManager.getPackageInfo("com.google.android.projection.gearhead", 0);
        } catch (PackageManager.NameNotFoundException | IllegalArgumentException unused) {
        }
        return (applicationEnabledSetting == 2 || applicationEnabledSetting == 3) ? false : true;
    }
}
