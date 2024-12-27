package com.android.settings.fuelgauge.batteryusage.db;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.android.settings.fuelgauge.batteryusage.ConvertUtils;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryEventEntity {
    public final int batteryEventType;
    public final int batteryLevel;
    public long mId;
    public final long timestamp;

    public BatteryEventEntity(int i, int i2, long j) {
        this.timestamp = j;
        this.batteryEventType = i;
        this.batteryLevel = i2;
    }

    public final String toString() {
        String utcToLocalTimeForLogging = ConvertUtils.utcToLocalTimeForLogging(this.timestamp);
        StringBuilder sb = new StringBuilder("\nBatteryEvent{");
        Locale locale = Locale.US;
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "\n\ttimestamp=", utcToLocalTimeForLogging, "|batteryEventType=");
        m.append(this.batteryEventType);
        m.append("|batteryLevel=");
        m.append(this.batteryLevel);
        sb.append(m.toString());
        sb.append("\n}");
        return sb.toString();
    }
}
