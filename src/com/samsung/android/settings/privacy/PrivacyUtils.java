package com.samsung.android.settings.privacy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class PrivacyUtils {
    public static boolean isConsentTncAgree(Context context) {
        return "true"
                .equalsIgnoreCase(
                        Settings.Global.getString(
                                context.getContentResolver(), "dbsc_consent_tnc_agree_value"));
    }

    public static boolean isSupportRuneStonePackage(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo("com.samsung.android.rubin.app", 0);
            int applicationEnabledSetting =
                    packageManager.getApplicationEnabledSetting("com.samsung.android.rubin.app");
            return applicationEnabledSetting == 1 || applicationEnabledSetting == 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
