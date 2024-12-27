package com.android.settings.development;

import android.content.Context;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.oemlock.OemLockManager;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class Enable16kUtils {
    static final String DEV_OPTION_PROPERTY = "ro.product.build.16k_page.enabled";
    public static final long PAGE_SIZE = Os.sysconf(OsConstants._SC_PAGESIZE);

    public static boolean isDeviceOEMUnlocked(Context context) {
        OemLockManager oemLockManager =
                (OemLockManager) context.getSystemService(OemLockManager.class);
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        if (oemLockManager == null || userManager == null) {
            Log.e(
                    "Enable16kUtils",
                    "Required services not found on device to check for OEM unlock state.");
            return false;
        }
        if (!oemLockManager.isDeviceOemUnlocked()) {
            Log.e("Enable16kUtils", "Device is not OEM unlocked");
            return false;
        }
        if (!userManager.hasBaseUserRestriction(
                "no_factory_reset", UserHandle.of(UserHandle.myUserId()))) {
            return true;
        }
        Log.e("Enable16kUtils", "Factory reset is not allowed for user.");
        return false;
    }

    public static boolean isPageAgnosticModeOn(Context context) {
        if (!SystemProperties.getBoolean(DEV_OPTION_PROPERTY, false)
                || !isDeviceOEMUnlocked(context)) {
            return false;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/mounts"));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        bufferedReader.close();
                        return false;
                    }
                    Log.i("Enable16kUtils", readLine);
                    String[] split = readLine.split(" ");
                    String str = split[1];
                    String str2 = split[2];
                    if (str.equals("/data") && str2.equals("ext4")) {
                        bufferedReader.close();
                        return true;
                    }
                } finally {
                }
            }
        } catch (IOException unused) {
            Log.e("Enable16kUtils", "Failed to read /proc/mounts");
            return false;
        }
    }

    public static boolean isUsing16kbPages() {
        return PAGE_SIZE == 16384;
    }
}
