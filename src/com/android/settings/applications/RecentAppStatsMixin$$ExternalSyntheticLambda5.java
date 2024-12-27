package com.android.settings.applications;

import android.app.usage.UsageStats;

import java.util.function.ToLongFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RecentAppStatsMixin$$ExternalSyntheticLambda5
        implements ToLongFunction {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((RecentAppStatsMixin.UsageStatsWrapper) obj).mUsageStats.getLastTimeUsed()
                        * (-1);
            default:
                return ((UsageStats) obj).getLastTimeUsed();
        }
    }
}
