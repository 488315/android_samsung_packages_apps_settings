package com.samsung.android.settings.taskbar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.display.DisplayManager;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class TaskBarUtils {
    public static boolean isFrontDisplay(Context context) {
        return context.getResources().getConfiguration().semDisplayDeviceType == 5;
    }

    public static boolean isSamsungLauncher(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 65536);
        if (resolveActivity == null) {
            return false;
        }
        String str = resolveActivity.activityInfo.packageName;
        Log.i("TaskBarUtils", "Home app package: " + str);
        return "com.sec.android.app.launcher".equals(str);
    }

    public static boolean isSmartViewEnabled(Context context) {
        DisplayManager displayManager =
                (DisplayManager) context.getSystemService(DisplayManager.class);
        return displayManager.getWifiDisplayStatus().getActiveDisplayState() == 2
                && displayManager.semIsFitToActiveDisplay();
    }
}
