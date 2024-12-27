package com.android.settingslib.fuelgauge;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import android.os.UserManager;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BatteryUtils {
    public static Boolean sChargingStringV2Enabled;

    public static Intent getBatteryIntent(Context context) {
        return context.registerReceiver(
                null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    }

    public static boolean isChargingStringV2Enabled() {
        if (sChargingStringV2Enabled == null) {
            sChargingStringV2Enabled =
                    Boolean.valueOf(SystemProperties.getBoolean("charging_string.apply_v2", false));
        }
        return sChargingStringV2Enabled.booleanValue();
    }

    public static boolean isWorkProfile(Context context) {
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        return userManager.isManagedProfile() && !userManager.isSystemUser();
    }

    public static void setChargingStringV2Enabled(Boolean bool) {
        setChargingStringV2Enabled(bool, true);
    }

    public static void setChargingStringV2Enabled(Boolean bool, boolean z) {
        if (z) {
            SystemProperties.set(
                    "charging_string.apply_v2",
                    bool == null ? ApnSettings.MVNO_NONE : String.valueOf(bool));
        }
        sChargingStringV2Enabled = bool;
    }
}
