package com.samsung.android.settings.nearbyscan;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.secutil.Log;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class NearbyScanningUtil {
    public static int misAppInstalled = -1;

    public static int getDBInt(ContentResolver contentResolver) {
        if (contentResolver == null) {
            Log.secE("NearbyDeviceScanningUtil", "Context is null");
            return -1;
        }
        try {
            return Settings.System.getInt(contentResolver, "nearby_scanning_enabled");
        } catch (Settings.SettingNotFoundException e) {
            Log.secE("NearbyDeviceScanningUtil", "SettingNotFoundException : " + e);
            Settings.System.putInt(contentResolver, "nearby_scanning_enabled", 1);
            return -1;
        }
    }

    public static boolean isBeaconManagerInstall(Context context) {
        int i;
        if (misAppInstalled == -1) {
            try {
                int i2 =
                        context.getPackageManager()
                                .getPackageInfo("com.samsung.android.beaconmanager", 1)
                                .versionCode;
                if (i2 >= 40) {
                    i = 1;
                } else {
                    Log.secW(
                            "NearbyDeviceScanningUtil",
                            " installed -  But version  " + i2 + ">=40");
                    i = 0;
                }
                Log.secI(
                        "NearbyDeviceScanningUtil",
                        "Installed - com.samsung.android.beaconmanager");
            } catch (PackageManager.NameNotFoundException unused) {
                Log.secW(
                        "NearbyDeviceScanningUtil",
                        "Not installed - com.samsung.android.beaconmanager");
                i = 0;
            }
            misAppInstalled = i;
        }
        Log.i("NearbyDeviceScanningUtil", "isBeaconManagerInstall - " + misAppInstalled);
        return misAppInstalled == 1;
    }

    public static void setDBInt(Context context, int i) {
        if (context == null) {
            Log.secE("NearbyDeviceScanningUtil", "Context is null");
            return;
        }
        ContentResolver contentResolver = context.getContentResolver();
        if (getDBInt(contentResolver) != i) {
            Log.secD("NearbyDeviceScanningUtil", "setDBInt : " + i);
            Settings.System.putInt(contentResolver, "nearby_scanning_enabled", i);
            Intent intent = new Intent("com.samsung.android.nearbyscanning");
            intent.addFlags(16777216);
            intent.addFlags(32);
            context.sendBroadcast(intent);
            Intent intent2 = new Intent("com.samsung.android.nearbyscanning_with_permission");
            intent2.addFlags(16777216);
            intent2.addFlags(32);
            context.sendBroadcast(
                    intent2, "com.samsung.android.beaconmanager.permission.START_BLE_PROXY");
            Log.secD("NearbyDeviceScanningUtil", "sendBroadcast");
        }
    }

    public static void settingslogging(Context context) {
        int dBInt = getDBInt(context.getContentResolver());
        Log.secD(
                "NearbyDeviceScanningUtil",
                "SwitchBar, send Event Log : 3701, on/off ("
                        .concat(dBInt == 1 ? "on 1000)" : "off 0)"));
        LoggingHelper.insertEventLogging(3700, 3701, dBInt == 1 ? 1000L : 0L);
    }
}
