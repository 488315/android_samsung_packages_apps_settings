package com.android.settings.fuelgauge.batteryusage;

import android.os.BatteryConsumer;
import android.os.UidBatteryConsumer;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DataProcessor$$ExternalSyntheticLambda3 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        BatteryEntry batteryEntry = (BatteryEntry) obj;
        BatteryConsumer batteryConsumer = batteryEntry.mBatteryConsumer;
        long j =
                batteryConsumer instanceof UidBatteryConsumer
                        ? batteryEntry.mTimeInForegroundMs
                        : batteryEntry.mUsageDurationMs;
        long j2 =
                batteryConsumer instanceof UidBatteryConsumer
                        ? batteryEntry.mTimeInBackgroundMs
                        : 0L;
        double d = batteryEntry.mConsumedPower;
        return d > 0.0d || (d == 0.0d && !(j == 0 && j2 == 0));
    }
}
