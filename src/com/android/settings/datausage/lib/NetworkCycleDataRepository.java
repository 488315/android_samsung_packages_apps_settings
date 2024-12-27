package com.android.settings.datausage.lib;

import android.app.usage.NetworkStats;
import android.content.Context;
import android.net.NetworkPolicy;
import android.net.NetworkPolicyManager;
import android.net.NetworkTemplate;
import android.util.Log;
import android.util.Range;

import com.android.settingslib.NetworkPolicyEditor;

import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.LongProgression;
import kotlin.ranges.LongProgressionIterator;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt___SequencesKt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkCycleDataRepository implements INetworkCycleDataRepository {
    public final NetworkStatsRepository networkStatsRepository;
    public final NetworkTemplate networkTemplate;
    public final NetworkPolicyManager policyManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Companion {
        public static List asFourWeeks(Range range) {
            if (range == null) {
                return EmptyList.INSTANCE;
            }
            Object lower = range.getLower();
            Intrinsics.checkNotNullExpressionValue(lower, "getLower(...)");
            long longValue = ((Number) lower).longValue();
            Object upper = range.getUpper();
            Intrinsics.checkNotNullExpressionValue(upper, "getUpper(...)");
            Iterator it =
                    RangesKt.step(
                                    new LongProgression(
                                            ((Number) upper).longValue(),
                                            longValue - 2419199999L,
                                            -1L),
                                    2419200000L)
                            .iterator();
            LongProgressionIterator longProgressionIterator = (LongProgressionIterator) it;
            if (!longProgressionIterator.hasNext) {
                return EmptyList.INSTANCE;
            }
            ArrayList arrayList = new ArrayList();
            LongProgressionIterator longProgressionIterator2 = (LongProgressionIterator) it;
            long nextLong = longProgressionIterator2.nextLong();
            while (longProgressionIterator.hasNext) {
                long nextLong2 = longProgressionIterator2.nextLong();
                arrayList.add(new Range(Long.valueOf(nextLong2), Long.valueOf(nextLong)));
                nextLong = nextLong2;
            }
            return arrayList;
        }

        public static List getCycles(NetworkPolicy networkPolicy) {
            Intrinsics.checkNotNullParameter(networkPolicy, "<this>");
            Iterator cycleIterator = networkPolicy.cycleIterator();
            Intrinsics.checkNotNullExpressionValue(cycleIterator, "cycleIterator(...)");
            return SequencesKt___SequencesKt.toList(
                    SequencesKt___SequencesKt.map(
                            SequencesKt___SequencesKt.asSequence(cycleIterator),
                            NetworkCycleDataRepository$Companion$getCycles$1.INSTANCE));
        }
    }

    public NetworkCycleDataRepository(Context context, NetworkTemplate networkTemplate) {
        NetworkStatsRepository networkStatsRepository =
                new NetworkStatsRepository(context, networkTemplate);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(networkTemplate, "networkTemplate");
        this.networkTemplate = networkTemplate;
        this.networkStatsRepository = networkStatsRepository;
        Object systemService = context.getSystemService((Class<Object>) NetworkPolicyManager.class);
        Intrinsics.checkNotNull(systemService);
        this.policyManager = (NetworkPolicyManager) systemService;
    }

    public final List getCycles() {
        NetworkPolicyEditor networkPolicyEditor = new NetworkPolicyEditor(this.policyManager);
        networkPolicyEditor.read();
        NetworkPolicy policy = networkPolicyEditor.getPolicy(this.networkTemplate);
        List cycles = policy != null ? Companion.getCycles(policy) : null;
        if (cycles == null) {
            cycles = EmptyList.INSTANCE;
        }
        List list = cycles;
        if (list.isEmpty()) {
            NetworkStatsRepository.Companion companion = NetworkStatsRepository.Companion;
            NetworkUsageData aggregate =
                    NetworkStatsRepository.Companion.aggregate(
                            this.networkStatsRepository.queryDetailsForDevice());
            list = Companion.asFourWeeks(aggregate != null ? aggregate.timeRange : null);
        }
        return list;
    }

    public final NetworkUsageData queryUsage(Range range) {
        long j;
        Intrinsics.checkNotNullParameter(range, "range");
        Object lower = range.getLower();
        Intrinsics.checkNotNullExpressionValue(lower, "getLower(...)");
        long longValue = ((Number) lower).longValue();
        Object upper = range.getUpper();
        Intrinsics.checkNotNullExpressionValue(upper, "getUpper(...)");
        long longValue2 = ((Number) upper).longValue();
        Object lower2 = range.getLower();
        Intrinsics.checkNotNullExpressionValue(lower2, "getLower(...)");
        long longValue3 = ((Number) lower2).longValue();
        Object upper2 = range.getUpper();
        Intrinsics.checkNotNullExpressionValue(upper2, "getUpper(...)");
        long longValue4 = ((Number) upper2).longValue();
        NetworkStatsRepository networkStatsRepository = this.networkStatsRepository;
        networkStatsRepository.getClass();
        try {
            NetworkStatsRepository.Companion companion = NetworkStatsRepository.Companion;
            NetworkStats.Bucket querySummaryForDevice =
                    networkStatsRepository.networkStatsManager.querySummaryForDevice(
                            networkStatsRepository.template, longValue3, longValue4);
            Intrinsics.checkNotNullExpressionValue(
                    querySummaryForDevice, "querySummaryForDevice(...)");
            j = querySummaryForDevice.getTxBytes() + querySummaryForDevice.getRxBytes();
        } catch (Exception e) {
            Log.e("NetworkStatsRepository", "Exception querySummaryForDevice", e);
            j = 0;
        }
        return new NetworkUsageData(longValue, longValue2, j);
    }
}
