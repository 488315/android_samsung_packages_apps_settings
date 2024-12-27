package com.android.settings.datausage.lib;

import android.content.Context;
import android.net.NetworkTemplate;
import android.os.Process;

import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppDataUsageDetailsRepository implements IAppDataUsageDetailsRepository {
    public static final String TAG =
            Reflection.factory
                    .getOrCreateKotlinClass(AppDataUsageDetailsRepository.class)
                    .getSimpleName();
    public final List cycles;
    public final INetworkCycleDataRepository networkCycleDataRepository;
    public final NetworkStatsRepository networkStatsRepository;
    public final List withSdkSandboxUids;

    public AppDataUsageDetailsRepository(
            Context context, NetworkTemplate template, List list, List uids) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(template, "template");
        Intrinsics.checkNotNullParameter(uids, "uids");
        NetworkCycleDataRepository networkCycleDataRepository =
                new NetworkCycleDataRepository(context, template);
        NetworkStatsRepository networkStatsRepository =
                new NetworkStatsRepository(context, template);
        this.cycles = list;
        this.networkCycleDataRepository = networkCycleDataRepository;
        this.networkStatsRepository = networkStatsRepository;
        Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(uids);
        Iterator it = uids.iterator();
        while (it.hasNext()) {
            int intValue = ((Number) it.next()).intValue();
            if (Process.isApplicationUid(intValue)) {
                mutableSet.add(Integer.valueOf(Process.toSdkSandboxUid(intValue)));
            }
        }
        this.withSdkSandboxUids = CollectionsKt___CollectionsKt.toList(mutableSet);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0078, code lost:

       if (r11 == null) goto L25;
    */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object queryDetailsForCycles(kotlin.coroutines.Continuation r11) {
        /*
            r10 = this;
            boolean r0 = r11 instanceof com.android.settings.datausage.lib.AppDataUsageDetailsRepository$queryDetailsForCycles$1
            if (r0 == 0) goto L13
            r0 = r11
            com.android.settings.datausage.lib.AppDataUsageDetailsRepository$queryDetailsForCycles$1 r0 = (com.android.settings.datausage.lib.AppDataUsageDetailsRepository$queryDetailsForCycles$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.datausage.lib.AppDataUsageDetailsRepository$queryDetailsForCycles$1 r0 = new com.android.settings.datausage.lib.AppDataUsageDetailsRepository$queryDetailsForCycles$1
            r0.<init>(r10, r11)
        L18:
            java.lang.Object r11 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r11)
            goto L93
        L27:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L2f:
            kotlin.ResultKt.throwOnFailure(r11)
            java.util.List r11 = r10.cycles
            if (r11 == 0) goto L7a
            java.lang.Iterable r11 = (java.lang.Iterable) r11
            java.util.Iterator r11 = r11.iterator()
            boolean r2 = r11.hasNext()
            if (r2 != 0) goto L45
            kotlin.collections.EmptyList r11 = kotlin.collections.EmptyList.INSTANCE
            goto L78
        L45:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.lang.Object r4 = r11.next()
        L4e:
            boolean r5 = r11.hasNext()
            if (r5 == 0) goto L77
            java.lang.Object r5 = r11.next()
            r6 = r5
            java.lang.Number r6 = (java.lang.Number) r6
            long r6 = r6.longValue()
            java.lang.Number r4 = (java.lang.Number) r4
            long r8 = r4.longValue()
            android.util.Range r4 = new android.util.Range
            java.lang.Long r6 = java.lang.Long.valueOf(r6)
            java.lang.Long r7 = java.lang.Long.valueOf(r8)
            r4.<init>(r6, r7)
            r2.add(r4)
            r4 = r5
            goto L4e
        L77:
            r11 = r2
        L78:
            if (r11 != 0) goto L82
        L7a:
            com.android.settings.datausage.lib.INetworkCycleDataRepository r11 = r10.networkCycleDataRepository
            com.android.settings.datausage.lib.NetworkCycleDataRepository r11 = (com.android.settings.datausage.lib.NetworkCycleDataRepository) r11
            java.util.List r11 = r11.getCycles()
        L82:
            java.lang.Iterable r11 = (java.lang.Iterable) r11
            com.android.settings.datausage.lib.AppDataUsageDetailsRepository$queryDetailsForCycles$$inlined$asyncMap$1 r2 = new com.android.settings.datausage.lib.AppDataUsageDetailsRepository$queryDetailsForCycles$$inlined$asyncMap$1
            r4 = 0
            r2.<init>(r11, r4, r10)
            r0.label = r3
            java.lang.Object r11 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r2, r0)
            if (r11 != r1) goto L93
            return r1
        L93:
            java.lang.Iterable r11 = (java.lang.Iterable) r11
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            java.util.Iterator r11 = r11.iterator()
        L9e:
            boolean r0 = r11.hasNext()
            if (r0 == 0) goto Lb7
            java.lang.Object r0 = r11.next()
            r1 = r0
            com.android.settings.datausage.lib.NetworkUsageDetailsData r1 = (com.android.settings.datausage.lib.NetworkUsageDetailsData) r1
            long r1 = r1.totalUsage
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 <= 0) goto L9e
            r10.add(r0)
            goto L9e
        Lb7:
            return r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.lib.AppDataUsageDetailsRepository.queryDetailsForCycles(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
