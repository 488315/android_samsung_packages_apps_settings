package com.samsung.android.settings.batterywidget;

import android.util.Log;

import com.samsung.android.os.SemCompanionDeviceBatteryInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BatteryManagerUtil {
    public static SemCompanionDeviceBatteryInfo convertDeviceTypeIfNeeded(
            SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) {
        if (semCompanionDeviceBatteryInfo != null) {
            return (semCompanionDeviceBatteryInfo.getDeviceType() != 3
                            ? new DefaultTypeConverter()
                            : new BudsTypeConverter())
                    .convertDeviceType(semCompanionDeviceBatteryInfo);
        }
        Log.w("BatteryWidget/BatteryManagerUtil", "convertDeviceTypeIfNeeded batteryInfo is null");
        return null;
    }
}
