package com.android.settingslib.wifi;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiPermissionChecker {
    public final String mLaunchedPackage;
    public final PackageManager mPackageManager;

    public WifiPermissionChecker(Activity activity) {
        String str;
        IActivityManager service = ActivityManager.getService();
        this.mPackageManager = activity.getPackageManager();
        try {
            str = service.getLaunchedFromPackage(activity.getActivityToken());
        } catch (RemoteException unused) {
            Log.e("WifiPermChecker", "Can not get the launched package from activity manager!");
            str = null;
        }
        this.mLaunchedPackage = str;
    }

    public final boolean checkPermission(String str) {
        PackageManager packageManager = this.mPackageManager;
        String str2 = this.mLaunchedPackage;
        if (packageManager != null && !TextUtils.isEmpty(str2)) {
            if (this.mPackageManager.checkPermission(str, str2) == 0) {
                return true;
            }
            Log.w(
                    "WifiPermChecker",
                    MotionLayout$$ExternalSyntheticOutline0.m(
                            "The launched package does not have the required permission!"
                                + " {LaunchedPackage:",
                            str2,
                            ", Permission:",
                            str,
                            "}"));
            return false;
        }
        Log.e(
                "WifiPermChecker",
                "Failed to check package permission! {PackageManager:"
                        + this.mPackageManager
                        + ", LaunchedPackage:"
                        + str2
                        + "}");
        return false;
    }
}
