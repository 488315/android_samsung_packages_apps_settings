package com.android.settings.datausage.lib;

import android.content.Context;
import android.net.NetworkPolicy;
import android.net.NetworkTemplate;
import android.util.Range;

import com.android.settingslib.NetworkPolicyEditor;

import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkCycleBucketRepository {
    public final List buckets;
    public final NetworkCycleDataRepository networkCycleDataRepository;

    public NetworkCycleBucketRepository(
            Context context, NetworkTemplate networkTemplate, List buckets) {
        NetworkCycleDataRepository networkCycleDataRepository =
                new NetworkCycleDataRepository(context, networkTemplate);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(networkTemplate, "networkTemplate");
        Intrinsics.checkNotNullParameter(buckets, "buckets");
        this.buckets = buckets;
        this.networkCycleDataRepository = networkCycleDataRepository;
    }

    public final NetworkUsageData aggregateUsage(Range range) {
        Object lower = range.getLower();
        Intrinsics.checkNotNullExpressionValue(lower, "getLower(...)");
        long longValue = ((Number) lower).longValue();
        Object upper = range.getUpper();
        Intrinsics.checkNotNullExpressionValue(upper, "getUpper(...)");
        long longValue2 = ((Number) upper).longValue();
        NetworkStatsRepository.Companion companion = NetworkStatsRepository.Companion;
        List list = this.buckets;
        Object lower2 = range.getLower();
        Intrinsics.checkNotNullExpressionValue(lower2, "getLower(...)");
        long longValue3 = ((Number) lower2).longValue();
        Object upper2 = range.getUpper();
        Intrinsics.checkNotNullExpressionValue(upper2, "getUpper(...)");
        NetworkUsageData aggregate =
                NetworkStatsRepository.Companion.aggregate(
                        NetworkStatsRepository.Companion.filterTime(
                                list, longValue3, ((Number) upper2).longValue()));
        return new NetworkUsageData(
                longValue, longValue2, aggregate != null ? aggregate.usage : 0L);
    }

    public final List loadCycles() {
        List list;
        NetworkCycleDataRepository networkCycleDataRepository = this.networkCycleDataRepository;
        NetworkPolicyEditor networkPolicyEditor =
                new NetworkPolicyEditor(networkCycleDataRepository.policyManager);
        networkPolicyEditor.read();
        NetworkPolicy policy =
                networkPolicyEditor.getPolicy(networkCycleDataRepository.networkTemplate);
        if (policy != null) {
            Iterator cycleIterator = policy.cycleIterator();
            Intrinsics.checkNotNullExpressionValue(cycleIterator, "cycleIterator(...)");
            list =
                    SequencesKt___SequencesKt.toList(
                            SequencesKt___SequencesKt.map(
                                    SequencesKt___SequencesKt.asSequence(cycleIterator),
                                    NetworkCycleDataRepository$Companion$getCycles$1.INSTANCE));
        } else {
            list = null;
        }
        if (list == null) {
            list = EmptyList.INSTANCE;
        }
        List list2 = list;
        if (list2.isEmpty()) {
            NetworkStatsRepository.Companion companion = NetworkStatsRepository.Companion;
            NetworkUsageData aggregate = NetworkStatsRepository.Companion.aggregate(this.buckets);
            list2 =
                    NetworkCycleDataRepository.Companion.asFourWeeks(
                            aggregate != null ? aggregate.timeRange : null);
        }
        List list3 = list2;
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
        Iterator it = list3.iterator();
        while (it.hasNext()) {
            arrayList.add(aggregateUsage((Range) it.next()));
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Object next = it2.next();
            if (((NetworkUsageData) next).usage > 0) {
                arrayList2.add(next);
            }
        }
        return arrayList2;
    }
}
