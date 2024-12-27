package com.android.settings.fuelgauge.batteryusage.db;

import android.content.ContentValues;

import androidx.core.text.PrecomputedTextCompat$Params$$ExternalSyntheticOutline0;

import com.android.settings.fuelgauge.batteryusage.ConvertUtils;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppUsageEventEntity {
    public final int appUsageEventType;
    public final int instanceId;
    public long mId;
    public final String packageName;
    public final String taskRootPackageName;
    public final long timestamp;
    public final long uid;
    public final long userId;

    public AppUsageEventEntity(long j, long j2, long j3, int i, String str, int i2, String str2) {
        this.uid = j;
        this.userId = j2;
        this.timestamp = j3;
        this.appUsageEventType = i;
        this.packageName = str;
        this.instanceId = i2;
        this.taskRootPackageName = str2;
    }

    public static AppUsageEventEntity create(ContentValues contentValues) {
        return new AppUsageEventEntity(
                contentValues.containsKey(NetworkAnalyticsConstants.DataPoints.UID)
                        ? contentValues
                                .getAsLong(NetworkAnalyticsConstants.DataPoints.UID)
                                .longValue()
                        : 0L,
                contentValues.containsKey("userId")
                        ? contentValues.getAsLong("userId").longValue()
                        : 0L,
                contentValues.containsKey(PhoneRestrictionPolicy.TIMESTAMP)
                        ? contentValues.getAsLong(PhoneRestrictionPolicy.TIMESTAMP).longValue()
                        : 0L,
                contentValues.containsKey("appUsageEventType")
                        ? contentValues.getAsInteger("appUsageEventType").intValue()
                        : 0,
                contentValues.containsKey("packageName")
                        ? contentValues.getAsString("packageName")
                        : null,
                contentValues.containsKey("instanceId")
                        ? contentValues.getAsInteger("instanceId").intValue()
                        : 0,
                contentValues.containsKey("taskRootPackageName")
                        ? contentValues.getAsString("taskRootPackageName")
                        : null);
    }

    public final String toString() {
        String utcToLocalTimeForLogging = ConvertUtils.utcToLocalTimeForLogging(this.timestamp);
        StringBuilder sb = new StringBuilder("\nAppUsageEvent{");
        Locale locale = Locale.US;
        sb.append(
                "\n\tpackage=" + this.packageName + "|uid=" + this.uid + "|userId=" + this.userId);
        StringBuilder sb2 = new StringBuilder("\n\ttimestamp=");
        sb2.append(utcToLocalTimeForLogging);
        sb2.append("|eventType=");
        sb2.append(this.appUsageEventType);
        sb2.append("|instanceId=");
        StringBuilder m =
                PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                        sb2, this.instanceId, sb, "\n\ttaskRootPackageName=");
        m.append(this.taskRootPackageName);
        sb.append(m.toString());
        return sb.toString();
    }
}
