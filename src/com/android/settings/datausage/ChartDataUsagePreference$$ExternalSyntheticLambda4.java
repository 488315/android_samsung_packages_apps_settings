package com.android.settings.datausage;

import com.android.settings.datausage.lib.NetworkUsageData;

import java.util.function.ToLongFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ChartDataUsagePreference$$ExternalSyntheticLambda4
        implements ToLongFunction {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((ChartDataUsagePreference.DataUsageSummaryNode) obj).mStartTime;
            case 1:
                return ((ChartDataUsagePreference.DataUsageSummaryNode) obj).mEndTime;
            default:
                return ((NetworkUsageData) obj).usage;
        }
    }
}
