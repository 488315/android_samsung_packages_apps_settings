package com.android.settings.fuelgauge.batteryusage.db;

import android.content.ContentValues;

import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.fuelgauge.batteryusage.BatteryInformation;
import com.android.settings.fuelgauge.batteryusage.ConvertUtils;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryState {
    public final String batteryInformation;
    public final String batteryInformationDebug;
    public final int consumerType;
    public final boolean isFullChargeCycleStart;
    public long mId;
    public final String packageName;
    public final long timestamp;
    public final long uid;
    public final long userId;

    public BatteryState(
            long j, long j2, String str, long j3, int i, boolean z, String str2, String str3) {
        this.uid = j;
        this.userId = j2;
        this.packageName = str;
        this.timestamp = j3;
        this.consumerType = i;
        this.isFullChargeCycleStart = z;
        this.batteryInformation = str2;
        this.batteryInformationDebug = str3;
    }

    public static BatteryState create(ContentValues contentValues) {
        return new BatteryState(
                contentValues.containsKey(NetworkAnalyticsConstants.DataPoints.UID)
                        ? contentValues
                                .getAsLong(NetworkAnalyticsConstants.DataPoints.UID)
                                .longValue()
                        : 0L,
                contentValues.containsKey("userId")
                        ? contentValues.getAsLong("userId").longValue()
                        : 0L,
                contentValues.containsKey("packageName")
                        ? contentValues.getAsString("packageName")
                        : null,
                contentValues.containsKey(PhoneRestrictionPolicy.TIMESTAMP)
                        ? contentValues.getAsLong(PhoneRestrictionPolicy.TIMESTAMP).longValue()
                        : 0L,
                contentValues.containsKey("consumerType")
                        ? contentValues.getAsInteger("consumerType").intValue()
                        : 0,
                contentValues.containsKey("isFullChargeCycleStart")
                        ? contentValues.getAsBoolean("isFullChargeCycleStart").booleanValue()
                        : false,
                contentValues.containsKey("batteryInformation")
                        ? contentValues.getAsString("batteryInformation")
                        : null,
                contentValues.containsKey("batteryInformationDebug")
                        ? contentValues.getAsString("batteryInformationDebug")
                        : null);
    }

    public final String toString() {
        String utcToLocalTimeForLogging = ConvertUtils.utcToLocalTimeForLogging(this.timestamp);
        BatteryInformation batteryInformation =
                (BatteryInformation)
                        BatteryUtils.parseProtoFromString(
                                this.batteryInformation, BatteryInformation.getDefaultInstance());
        StringBuilder sb = new StringBuilder("\nBatteryState{");
        Locale locale = Locale.US;
        sb.append(
                "\n\tpackage=" + this.packageName + "|uid=" + this.uid + "|userId=" + this.userId);
        sb.append(
                "\n\ttimestamp="
                        + utcToLocalTimeForLogging
                        + "|consumer="
                        + this.consumerType
                        + "|isStart="
                        + this.isFullChargeCycleStart);
        sb.append("\n\tbatteryInfo=");
        sb.append(batteryInformation.toString());
        return sb.toString();
    }
}
