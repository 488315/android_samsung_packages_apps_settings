package com.android.settings.fuelgauge.batteryusage;

import android.database.Cursor;

import com.android.settings.fuelgauge.BatteryUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DatabaseUtils$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DatabaseUtils$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return new BatteryHistEntry((Cursor) obj);
            case 1:
                return String.valueOf(((BatteryEventType) obj).getNumber());
            case 2:
                Cursor cursor = (Cursor) obj;
                BatteryEvent.Builder newBuilder = BatteryEvent.newBuilder();
                int columnIndex = cursor.getColumnIndex(PhoneRestrictionPolicy.TIMESTAMP);
                long j = columnIndex >= 0 ? cursor.getLong(columnIndex) : 0L;
                newBuilder.copyOnWrite();
                BatteryEvent.m890$$Nest$msetTimestamp((BatteryEvent) newBuilder.instance, j);
                int columnIndex2 = cursor.getColumnIndex("batteryEventType");
                BatteryEventType forNumber =
                        BatteryEventType.forNumber(
                                columnIndex2 >= 0 ? cursor.getInt(columnIndex2) : 0);
                newBuilder.copyOnWrite();
                BatteryEvent.m891$$Nest$msetType((BatteryEvent) newBuilder.instance, forNumber);
                int columnIndex3 = cursor.getColumnIndex("batteryLevel");
                int i = columnIndex3 >= 0 ? cursor.getInt(columnIndex3) : 0;
                newBuilder.copyOnWrite();
                BatteryEvent.m889$$Nest$msetBatteryLevel((BatteryEvent) newBuilder.instance, i);
                return (BatteryEvent) newBuilder.build();
            case 3:
                Cursor cursor2 = (Cursor) obj;
                BatteryUsageSlot defaultInstance = BatteryUsageSlot.getDefaultInstance();
                int columnIndex4 = cursor2.getColumnIndex("batteryUsageSlot");
                return columnIndex4 < 0
                        ? defaultInstance
                        : (BatteryUsageSlot)
                                BatteryUtils.parseProtoFromString(
                                        cursor2.getString(columnIndex4), defaultInstance);
            case 4:
                return String.valueOf((Integer) obj);
            default:
                Cursor cursor3 = (Cursor) obj;
                AppUsageEvent.Builder newBuilder2 = AppUsageEvent.newBuilder();
                int columnIndex5 = cursor3.getColumnIndex(PhoneRestrictionPolicy.TIMESTAMP);
                long j2 = columnIndex5 >= 0 ? cursor3.getLong(columnIndex5) : 0L;
                newBuilder2.copyOnWrite();
                AppUsageEvent.m882$$Nest$msetTimestamp((AppUsageEvent) newBuilder2.instance, j2);
                int columnIndex6 = cursor3.getColumnIndex("appUsageEventType");
                AppUsageEventType forNumber2 =
                        AppUsageEventType.forNumber(
                                columnIndex6 >= 0 ? cursor3.getInt(columnIndex6) : 0);
                newBuilder2.copyOnWrite();
                AppUsageEvent.m883$$Nest$msetType((AppUsageEvent) newBuilder2.instance, forNumber2);
                int columnIndex7 = cursor3.getColumnIndex("packageName");
                String str = ApnSettings.MVNO_NONE;
                String string =
                        columnIndex7 >= 0 ? cursor3.getString(columnIndex7) : ApnSettings.MVNO_NONE;
                newBuilder2.copyOnWrite();
                AppUsageEvent.m880$$Nest$msetPackageName(
                        (AppUsageEvent) newBuilder2.instance, string);
                int columnIndex8 = cursor3.getColumnIndex("instanceId");
                int i2 = columnIndex8 >= 0 ? cursor3.getInt(columnIndex8) : 0;
                newBuilder2.copyOnWrite();
                AppUsageEvent.m879$$Nest$msetInstanceId((AppUsageEvent) newBuilder2.instance, i2);
                int columnIndex9 = cursor3.getColumnIndex("taskRootPackageName");
                if (columnIndex9 >= 0) {
                    str = cursor3.getString(columnIndex9);
                }
                newBuilder2.copyOnWrite();
                AppUsageEvent.m881$$Nest$msetTaskRootPackageName(
                        (AppUsageEvent) newBuilder2.instance, str);
                int columnIndex10 = cursor3.getColumnIndex("userId");
                long j3 = columnIndex10 >= 0 ? cursor3.getLong(columnIndex10) : 0L;
                newBuilder2.copyOnWrite();
                AppUsageEvent.m885$$Nest$msetUserId((AppUsageEvent) newBuilder2.instance, j3);
                int columnIndex11 =
                        cursor3.getColumnIndex(NetworkAnalyticsConstants.DataPoints.UID);
                long j4 = columnIndex11 >= 0 ? cursor3.getLong(columnIndex11) : 0L;
                newBuilder2.copyOnWrite();
                AppUsageEvent.m884$$Nest$msetUid((AppUsageEvent) newBuilder2.instance, j4);
                return (AppUsageEvent) newBuilder2.build();
        }
    }
}
