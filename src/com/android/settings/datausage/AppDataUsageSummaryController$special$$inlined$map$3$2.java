package com.android.settings.datausage;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppDataUsageSummaryController$special$$inlined$map$3$2 implements FlowCollector {
    public final /* synthetic */ FlowCollector $this_unsafeFlow;
    public final /* synthetic */ AppDataUsageSummaryController this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$3$2$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppDataUsageSummaryController$special$$inlined$map$3$2.this.emit(null, this);
        }
    }

    public AppDataUsageSummaryController$special$$inlined$map$3$2(
            FlowCollector flowCollector,
            AppDataUsageSummaryController appDataUsageSummaryController) {
        this.$this_unsafeFlow = flowCollector;
        this.this$0 = appDataUsageSummaryController;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$3$2.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$3$2$1 r0 = (com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$3$2.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$3$2$1 r0 = new com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$3$2$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r8)
            goto L4b
        L27:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L2f:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.settings.datausage.lib.NetworkUsageDetailsData r7 = (com.android.settings.datausage.lib.NetworkUsageDetailsData) r7
            com.android.settings.datausage.AppDataUsageSummaryController r8 = r6.this$0
            com.android.settings.datausage.lib.DataUsageFormatter r8 = com.android.settings.datausage.AppDataUsageSummaryController.access$getDataUsageFormatter$p(r8)
            long r4 = r7.backgroundUsage
            com.android.settings.datausage.lib.DataUsageFormatter$FormattedDataUsage r7 = r8.formatDataUsage(r4)
            r0.label = r3
            kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
            java.lang.Object r6 = r6.emit(r7, r0)
            if (r6 != r1) goto L4b
            return r1
        L4b:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$3$2.emit(java.lang.Object,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }
}
