package com.samsung.android.settings.wifi;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiTipsUtils {
    public static boolean isSupportWifiTips(Context context) {
        if (context != null) {
            try {
                context.getPackageManager()
                        .getApplicationInfo("com.samsung.android.net.wifi.wifiguider", 128);
                try {
                    return ((long)
                                    context.getApplicationContext()
                                            .getPackageManager()
                                            .getPackageInfo(
                                                    "com.samsung.android.net.wifi.wifiguider", 0)
                                            .versionCode)
                            >= 130000000;
                } catch (PackageManager.NameNotFoundException unused) {
                    return false;
                }
            } catch (PackageManager.NameNotFoundException unused2) {
                Log.d(
                        "WifiTips_Utils",
                        "Package not found : com.samsung.android.net.wifi.wifiguider");
            }
        }
        return false;
    }
}
