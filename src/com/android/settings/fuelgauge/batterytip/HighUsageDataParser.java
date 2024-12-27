package com.android.settings.fuelgauge.batterytip;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HighUsageDataParser {
    public int mBatteryDrain;
    public byte mEndBatteryLevel;
    public long mEndTimeMs;
    public byte mLastPeriodBatteryLevel;
    public final int mThreshold;
    public final long mTimePeriodMs;

    public HighUsageDataParser(int i, long j) {
        this.mTimePeriodMs = j;
        this.mThreshold = i;
    }
}
