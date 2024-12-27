package com.android.settings.fuelgauge.batteryusage;

import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DataProcessor$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DataProcessor$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Long.valueOf(((AppUsageEvent) obj).getTimestamp());
            case 1:
                return Long.valueOf(((AppUsagePeriod) obj).getStartTime());
            case 2:
                return new ArrayMap();
            case 3:
                return new ArrayList();
            case 4:
                return Long.valueOf(((BatteryEvent) obj).getTimestamp());
            case 5:
                return ((BatteryHistEntry) obj).getKey();
            case 6:
                return (BatteryHistEntry) obj;
            default:
                AppUsagePeriod appUsagePeriod = (AppUsagePeriod) obj;
                AppUsageEndPoint.Builder newBuilder = AppUsageEndPoint.newBuilder();
                long startTime = appUsagePeriod.getStartTime();
                newBuilder.copyOnWrite();
                AppUsageEndPoint.m877$$Nest$msetTimestamp(
                        (AppUsageEndPoint) newBuilder.instance, startTime);
                AppUsageEndPointType appUsageEndPointType = AppUsageEndPointType.START;
                newBuilder.copyOnWrite();
                AppUsageEndPoint.m878$$Nest$msetType(
                        (AppUsageEndPoint) newBuilder.instance, appUsageEndPointType);
                AppUsageEndPoint appUsageEndPoint = (AppUsageEndPoint) newBuilder.build();
                AppUsageEndPoint.Builder newBuilder2 = AppUsageEndPoint.newBuilder();
                long endTime = appUsagePeriod.getEndTime();
                newBuilder2.copyOnWrite();
                AppUsageEndPoint.m877$$Nest$msetTimestamp(
                        (AppUsageEndPoint) newBuilder2.instance, endTime);
                AppUsageEndPointType appUsageEndPointType2 = AppUsageEndPointType.END;
                newBuilder2.copyOnWrite();
                AppUsageEndPoint.m878$$Nest$msetType(
                        (AppUsageEndPoint) newBuilder2.instance, appUsageEndPointType2);
                return Stream.of(
                        (Object[])
                                new AppUsageEndPoint[] {
                                    appUsageEndPoint, (AppUsageEndPoint) newBuilder2.build()
                                });
        }
    }
}
