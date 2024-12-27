package com.samsung.android.settings.usefulfeature.intelligenceservice;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class IntelligenceServiceUtils {
    public static String getAppTitle(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return (String)
                    packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 128));
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getClassName(Context context, String str) {
        for (ResolveInfo resolveInfo :
                context.getPackageManager()
                        .queryIntentActivitiesAsUser(
                                new Intent(
                                        "com.samsung.android.settings.action.INTELLIGENCE_SERVICE_SETTINGS"),
                                128,
                                UserHandle.myUserId())) {
            if (str.equals(resolveInfo.activityInfo.packageName)) {
                return resolveInfo.activityInfo.getComponentName().getClassName();
            }
        }
        return ApnSettings.MVNO_NONE;
    }

    public static boolean isInstalled(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean isSupportedAI(Context context, String str) {
        Iterator it =
                context.getPackageManager()
                        .queryIntentActivitiesAsUser(
                                new Intent(
                                        "com.samsung.android.settings.action.INTELLIGENCE_SERVICE_SETTINGS"),
                                128,
                                UserHandle.myUserId())
                        .iterator();
        while (it.hasNext()) {
            if (str.equals(((ResolveInfo) it.next()).activityInfo.packageName)) {
                return true;
            }
        }
        return false;
    }
}
