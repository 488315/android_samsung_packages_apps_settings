package com.android.settings.fuelgauge.batteryusage;

import android.content.ContentValues;
import android.database.Cursor;

import com.android.settings.fuelgauge.BatteryUtils;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;

import java.time.Duration;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryHistEntry {
    public final String mAppLabel;
    public final double mBackgroundUsageConsumePower;
    public final long mBackgroundUsageTimeInMs;
    public final int mBatteryHealth;
    public final int mBatteryLevel;
    public final int mBatteryStatus;
    public final long mBootTimestamp;
    public final double mCachedUsageConsumePower;
    public final double mConsumePower;
    public final int mConsumerType;
    public final int mDrainType;
    public final double mForegroundServiceUsageConsumePower;
    public final long mForegroundServiceUsageTimeInMs;
    public final double mForegroundUsageConsumePower;
    public final long mForegroundUsageTimeInMs;
    public final boolean mIsHidden;
    public String mKey = null;
    public final String mPackageName;
    public final double mPercentOfTotal;
    public final long mTimestamp;
    public final double mTotalPower;
    public final long mUid;
    public final long mUserId;
    public final String mZoneId;

    public BatteryHistEntry(ContentValues contentValues) {
        this.mUid = getLong(contentValues, NetworkAnalyticsConstants.DataPoints.UID);
        this.mUserId = getLong(contentValues, "userId");
        this.mPackageName =
                contentValues.containsKey("packageName")
                        ? contentValues.getAsString("packageName")
                        : null;
        this.mTimestamp = getLong(contentValues, PhoneRestrictionPolicy.TIMESTAMP);
        this.mConsumerType =
                contentValues.containsKey("consumerType")
                        ? contentValues.getAsInteger("consumerType").intValue()
                        : 0;
        BatteryInformation defaultInstance = BatteryInformation.getDefaultInstance();
        defaultInstance =
                contentValues.containsKey("batteryInformation")
                        ? (BatteryInformation)
                                BatteryUtils.parseProtoFromString(
                                        contentValues.getAsString("batteryInformation"),
                                        defaultInstance)
                        : defaultInstance;
        this.mAppLabel = defaultInstance.getAppLabel();
        this.mIsHidden = defaultInstance.getIsHidden();
        this.mBootTimestamp = defaultInstance.getBootTimestamp();
        this.mZoneId = defaultInstance.getZoneId();
        this.mTotalPower = defaultInstance.getTotalPower();
        this.mConsumePower = defaultInstance.getConsumePower();
        this.mForegroundUsageConsumePower = defaultInstance.getForegroundUsageConsumePower();
        this.mForegroundServiceUsageConsumePower =
                defaultInstance.getForegroundServiceUsageConsumePower();
        this.mBackgroundUsageConsumePower = defaultInstance.getBackgroundUsageConsumePower();
        this.mCachedUsageConsumePower = defaultInstance.getCachedUsageConsumePower();
        this.mPercentOfTotal = defaultInstance.getPercentOfTotal();
        this.mForegroundUsageTimeInMs = defaultInstance.getForegroundUsageTimeInMs();
        this.mForegroundServiceUsageTimeInMs = defaultInstance.getForegroundServiceUsageTimeInMs();
        this.mBackgroundUsageTimeInMs = defaultInstance.getBackgroundUsageTimeInMs();
        this.mDrainType = defaultInstance.getDrainType();
        DeviceBatteryState deviceBatteryState = defaultInstance.getDeviceBatteryState();
        this.mBatteryLevel = deviceBatteryState.getBatteryLevel();
        this.mBatteryStatus = deviceBatteryState.getBatteryStatus();
        this.mBatteryHealth = deviceBatteryState.getBatteryHealth();
    }

    public static long getLong(ContentValues contentValues, String str) {
        if (contentValues.containsKey(str)) {
            return contentValues.getAsLong(str).longValue();
        }
        return 0L;
    }

    public static double interpolate(double d, double d2, double d3) {
        return ((d2 - d) * d3) + d;
    }

    public final String getKey() {
        if (this.mKey == null) {
            int i = this.mConsumerType;
            if (i == 1) {
                this.mKey = Long.toString(this.mUid);
            } else if (i == 2) {
                this.mKey = "U|" + this.mUserId;
            } else if (i == 3) {
                this.mKey = "S|" + this.mDrainType;
            }
        }
        return this.mKey;
    }

    public final String toString() {
        return "\nBatteryHistEntry{"
                + String.format(
                        "\n\tpackage=%s|label=%s|uid=%d|userId=%d|isHidden=%b",
                        this.mPackageName,
                        this.mAppLabel,
                        Long.valueOf(this.mUid),
                        Long.valueOf(this.mUserId),
                        Boolean.valueOf(this.mIsHidden))
                + String.format(
                        "\n\ttimestamp=%s|zoneId=%s|bootTimestamp=%d",
                        ConvertUtils.utcToLocalTimeForLogging(this.mTimestamp),
                        this.mZoneId,
                        Long.valueOf(Duration.ofMillis(this.mBootTimestamp).getSeconds()))
                + String.format(
                        "\n\tusage=%f|total=%f|consume=%f",
                        Double.valueOf(this.mPercentOfTotal),
                        Double.valueOf(this.mTotalPower),
                        Double.valueOf(this.mConsumePower))
                + String.format(
                        "\n\tforeground=%f|foregroundService=%f",
                        Double.valueOf(this.mForegroundUsageConsumePower),
                        Double.valueOf(this.mForegroundServiceUsageConsumePower))
                + String.format(
                        "\n\tbackground=%f|cached=%f",
                        Double.valueOf(this.mBackgroundUsageConsumePower),
                        Double.valueOf(this.mCachedUsageConsumePower))
                + String.format(
                        "\n\telapsedTime,fg=%d|fgs=%d|bg=%d",
                        Long.valueOf(Duration.ofMillis(this.mForegroundUsageTimeInMs).getSeconds()),
                        Long.valueOf(
                                Duration.ofMillis(this.mForegroundServiceUsageTimeInMs)
                                        .getSeconds()),
                        Long.valueOf(Duration.ofMillis(this.mBackgroundUsageTimeInMs).getSeconds()))
                + String.format(
                        "\n\tdrainType=%d|consumerType=%d",
                        Integer.valueOf(this.mDrainType), Integer.valueOf(this.mConsumerType))
                + String.format(
                        "\n\tbattery=%d|status=%d|health=%d\n}",
                        Integer.valueOf(this.mBatteryLevel),
                        Integer.valueOf(this.mBatteryStatus),
                        Integer.valueOf(this.mBatteryHealth));
    }

    public BatteryHistEntry(Cursor cursor) {
        int columnIndex = cursor.getColumnIndex(NetworkAnalyticsConstants.DataPoints.UID);
        this.mUid = columnIndex >= 0 ? cursor.getLong(columnIndex) : 0L;
        int columnIndex2 = cursor.getColumnIndex("userId");
        this.mUserId = columnIndex2 >= 0 ? cursor.getLong(columnIndex2) : 0L;
        int columnIndex3 = cursor.getColumnIndex("packageName");
        this.mPackageName = columnIndex3 >= 0 ? cursor.getString(columnIndex3) : null;
        int columnIndex4 = cursor.getColumnIndex(PhoneRestrictionPolicy.TIMESTAMP);
        this.mTimestamp = columnIndex4 >= 0 ? cursor.getLong(columnIndex4) : 0L;
        int columnIndex5 = cursor.getColumnIndex("consumerType");
        this.mConsumerType = columnIndex5 >= 0 ? cursor.getInt(columnIndex5) : 0;
        BatteryInformation defaultInstance = BatteryInformation.getDefaultInstance();
        int columnIndex6 = cursor.getColumnIndex("batteryInformation");
        defaultInstance =
                columnIndex6 >= 0
                        ? (BatteryInformation)
                                BatteryUtils.parseProtoFromString(
                                        cursor.getString(columnIndex6), defaultInstance)
                        : defaultInstance;
        this.mAppLabel = defaultInstance.getAppLabel();
        this.mIsHidden = defaultInstance.getIsHidden();
        this.mBootTimestamp = defaultInstance.getBootTimestamp();
        this.mZoneId = defaultInstance.getZoneId();
        this.mTotalPower = defaultInstance.getTotalPower();
        this.mConsumePower = defaultInstance.getConsumePower();
        this.mForegroundUsageConsumePower = defaultInstance.getForegroundUsageConsumePower();
        this.mForegroundServiceUsageConsumePower =
                defaultInstance.getForegroundServiceUsageConsumePower();
        this.mBackgroundUsageConsumePower = defaultInstance.getBackgroundUsageConsumePower();
        this.mCachedUsageConsumePower = defaultInstance.getCachedUsageConsumePower();
        this.mPercentOfTotal = defaultInstance.getPercentOfTotal();
        this.mForegroundUsageTimeInMs = defaultInstance.getForegroundUsageTimeInMs();
        this.mForegroundServiceUsageTimeInMs = defaultInstance.getForegroundServiceUsageTimeInMs();
        this.mBackgroundUsageTimeInMs = defaultInstance.getBackgroundUsageTimeInMs();
        this.mDrainType = defaultInstance.getDrainType();
        DeviceBatteryState deviceBatteryState = defaultInstance.getDeviceBatteryState();
        this.mBatteryLevel = deviceBatteryState.getBatteryLevel();
        this.mBatteryStatus = deviceBatteryState.getBatteryStatus();
        this.mBatteryHealth = deviceBatteryState.getBatteryHealth();
    }

    public BatteryHistEntry(
            BatteryHistEntry batteryHistEntry,
            long j,
            long j2,
            double d,
            double d2,
            double d3,
            double d4,
            double d5,
            double d6,
            long j3,
            long j4,
            long j5,
            int i) {
        this.mUid = batteryHistEntry.mUid;
        this.mUserId = batteryHistEntry.mUserId;
        this.mAppLabel = batteryHistEntry.mAppLabel;
        this.mPackageName = batteryHistEntry.mPackageName;
        this.mIsHidden = batteryHistEntry.mIsHidden;
        this.mBootTimestamp = j;
        this.mTimestamp = j2;
        this.mZoneId = batteryHistEntry.mZoneId;
        this.mTotalPower = d;
        this.mConsumePower = d2;
        this.mForegroundUsageConsumePower = d3;
        this.mForegroundServiceUsageConsumePower = d4;
        this.mBackgroundUsageConsumePower = d5;
        this.mCachedUsageConsumePower = d6;
        this.mPercentOfTotal = batteryHistEntry.mPercentOfTotal;
        this.mForegroundUsageTimeInMs = j3;
        this.mForegroundServiceUsageTimeInMs = j4;
        this.mBackgroundUsageTimeInMs = j5;
        this.mDrainType = batteryHistEntry.mDrainType;
        this.mConsumerType = batteryHistEntry.mConsumerType;
        this.mBatteryLevel = i;
        this.mBatteryStatus = batteryHistEntry.mBatteryStatus;
        this.mBatteryHealth = batteryHistEntry.mBatteryHealth;
    }
}
