package com.android.settings.fuelgauge.batteryusage.db;

import com.android.settings.fuelgauge.batteryusage.ConvertUtils;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryUsageSlotEntity {
    public final String batteryUsageSlot;
    public long mId;
    public final long timestamp;

    public BatteryUsageSlotEntity(long j, String str) {
        this.timestamp = j;
        this.batteryUsageSlot = str;
    }

    public final String toString() {
        String utcToLocalTimeForLogging = ConvertUtils.utcToLocalTimeForLogging(this.timestamp);
        StringBuilder sb = new StringBuilder("\nBatteryUsageSlot{");
        Locale locale = Locale.US;
        sb.append(
                "\n\ttimestamp="
                        + utcToLocalTimeForLogging
                        + "|batteryUsageSlot="
                        + this.batteryUsageSlot);
        sb.append("\n}");
        return sb.toString();
    }
}
