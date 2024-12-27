package com.android.settings.datausage.lib;

import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkCycleChartData {
    public static final long BUCKET_DURATION;
    public final List dailyUsage;
    public final NetworkUsageData total;

    static {
        int i = Duration.$r8$clinit;
        BUCKET_DURATION = DurationKt.toDuration(1, DurationUnit.DAYS);
    }

    public NetworkCycleChartData(NetworkUsageData total, List list) {
        Intrinsics.checkNotNullParameter(total, "total");
        this.total = total;
        this.dailyUsage = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NetworkCycleChartData)) {
            return false;
        }
        NetworkCycleChartData networkCycleChartData = (NetworkCycleChartData) obj;
        return Intrinsics.areEqual(this.total, networkCycleChartData.total)
                && Intrinsics.areEqual(this.dailyUsage, networkCycleChartData.dailyUsage);
    }

    public final int hashCode() {
        return this.dailyUsage.hashCode() + (this.total.hashCode() * 31);
    }

    public final String toString() {
        return "NetworkCycleChartData(total="
                + this.total
                + ", dailyUsage="
                + this.dailyUsage
                + ")";
    }
}
