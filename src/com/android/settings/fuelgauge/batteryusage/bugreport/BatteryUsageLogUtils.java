package com.android.settings.fuelgauge.batteryusage.bugreport;

import android.content.Context;
import android.content.SharedPreferences;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BatteryUsageLogUtils {
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getApplicationContext()
                .getSharedPreferences("battery_usage_historical_logs", 0);
    }
}
