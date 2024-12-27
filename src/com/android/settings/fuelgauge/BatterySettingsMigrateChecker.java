package com.android.settings.fuelgauge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BatterySettingsMigrateChecker extends BroadcastReceiver {
    static BatteryOptimizeUtils sBatteryOptimizeUtils;

    public static void verifyBatteryOptimizeModeApps(
            final Context context, final int i, List<String> list) {
        list.forEach(
                new Consumer() { // from class:
                                 // com.android.settings.fuelgauge.BatterySettingsMigrateChecker$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        BatteryOptimizeUtils batteryOptimizeUtils;
                        Context context2 = context;
                        int i2 = i;
                        String str = (String) obj;
                        BatteryOptimizeUtils batteryOptimizeUtils2 =
                                BatterySettingsMigrateChecker.sBatteryOptimizeUtils;
                        int packageUid = BatteryUtils.getInstance(context2).getPackageUid(str);
                        if (packageUid == -1) {
                            batteryOptimizeUtils = null;
                        } else {
                            if (batteryOptimizeUtils2 == null) {
                                batteryOptimizeUtils2 =
                                        new BatteryOptimizeUtils(context2, packageUid, str);
                            }
                            batteryOptimizeUtils = batteryOptimizeUtils2;
                        }
                        if (batteryOptimizeUtils == null
                                || batteryOptimizeUtils.getAppOptimizationMode(true) == i2) {
                            return;
                        }
                        Log.w(
                                "BatterySettingsMigrateChecker",
                                "Reset " + str + " battery mode into " + i2);
                        batteryOptimizeUtils.setAppUsageState(
                                i2, BatteryOptimizeHistoricalLogEntry.Action.FORCE_RESET);
                    }
                });
    }
}
