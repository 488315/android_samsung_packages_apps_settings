package com.android.settingslib.utils.applications;

import android.content.pm.PackageManager;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppUtils {
    public static CharSequence getApplicationLabel(PackageManager packageManager, String str) {
        try {
            return packageManager.getApplicationInfo(str, 4194816).loadLabel(packageManager);
        } catch (PackageManager.NameNotFoundException unused) {
            MotionLayout$$ExternalSyntheticOutline0.m(
                    "Unable to find info for package: ", str, "AppUtils");
            return null;
        }
    }
}
