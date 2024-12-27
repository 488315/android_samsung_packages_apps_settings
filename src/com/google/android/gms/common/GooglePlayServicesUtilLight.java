package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;

import com.google.android.gms.common.util.DeviceProperties;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class GooglePlayServicesUtilLight {
    static boolean zza = false;
    static final AtomicBoolean sCanceledAvailabilityNotification = new AtomicBoolean();
    public static final AtomicBoolean zzc = new AtomicBoolean();

    @Deprecated
    public static String getErrorString(int i) {
        return ConnectionResult.zza(i);
    }

    @Deprecated
    public static boolean isSidewinderDevice(Context context) {
        if (DeviceProperties.zze == null) {
            DeviceProperties.zze =
                    Boolean.valueOf(context.getPackageManager().hasSystemFeature("cn.google"));
        }
        return DeviceProperties.zze.booleanValue();
    }

    public static boolean zza(Context context) {
        try {
            Iterator<PackageInstaller.SessionInfo> it =
                    context.getPackageManager().getPackageInstaller().getAllSessions().iterator();
            while (it.hasNext()) {
                if ("com.google.android.gms".equals(it.next().getAppPackageName())) {
                    return true;
                }
            }
            return context.getPackageManager()
                    .getApplicationInfo("com.google.android.gms", 8192)
                    .enabled;
        } catch (PackageManager.NameNotFoundException | Exception unused) {
            return false;
        }
    }
}
