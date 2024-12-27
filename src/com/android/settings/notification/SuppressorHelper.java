package com.android.settings.notification;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SuppressorHelper {
    public static String getSuppressorCaption(Context context, ComponentName componentName) {
        CharSequence loadLabel;
        PackageManager packageManager = context.getPackageManager();
        try {
            ServiceInfo serviceInfo = packageManager.getServiceInfo(componentName, 0);
            if (serviceInfo != null
                    && (loadLabel = serviceInfo.loadLabel(packageManager)) != null) {
                String trim = loadLabel.toString().trim();
                if (trim.length() > 0) {
                    return trim;
                }
            }
        } catch (Throwable th) {
            Log.w("SuppressorHelper", "Error loading suppressor caption", th);
        }
        return componentName.getPackageName();
    }
}
