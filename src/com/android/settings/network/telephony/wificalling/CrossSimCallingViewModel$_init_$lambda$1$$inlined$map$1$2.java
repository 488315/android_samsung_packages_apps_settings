package com.android.settings.network.telephony.wificalling;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

import kotlinx.coroutines.flow.FlowCollector;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CrossSimCallingViewModel$_init_$lambda$1$$inlined$map$1$2
        implements FlowCollector {
    public final /* synthetic */ List $activeSubIds$inlined;
    public final /* synthetic */ FlowCollector $this_unsafeFlow;
    public final /* synthetic */ CrossSimCallingViewModel this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel$_init_$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
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
            return CrossSimCallingViewModel$_init_$lambda$1$$inlined$map$1$2.this.emit(null, this);
        }
    }

    public CrossSimCallingViewModel$_init_$lambda$1$$inlined$map$1$2(
            FlowCollector flowCollector,
            List list,
            CrossSimCallingViewModel crossSimCallingViewModel) {
        this.$this_unsafeFlow = flowCollector;
        this.$activeSubIds$inlined = list;
        this.this$0 = crossSimCallingViewModel;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel$_init_$lambda$1$$inlined$map$1$2.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel$_init_$lambda$1$$inlined$map$1$2$1 r0 = (com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel$_init_$lambda$1$$inlined$map$1$2.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel$_init_$lambda$1$$inlined$map$1$2$1 r0 = new com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel$_init_$lambda$1$$inlined$map$1$2$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r9)
            goto L90
        L27:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L2f:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlin.Unit r8 = (kotlin.Unit) r8
            java.util.List r8 = r7.$activeSubIds$inlined
            com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel r9 = r7.this$0
            r9.getClass()
            int r2 = android.telephony.SubscriptionManager.getDefaultDataSubscriptionId()
            boolean r4 = android.telephony.SubscriptionManager.isValidSubscriptionId(r2)
            if (r4 == 0) goto L7b
            r4 = r8
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            boolean r5 = r4 instanceof java.util.Collection
            if (r5 == 0) goto L56
            r5 = r4
            java.util.Collection r5 = (java.util.Collection) r5
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L56
            goto L7b
        L56:
            java.util.Iterator r4 = r4.iterator()
        L5a:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L7b
            java.lang.Object r5 = r4.next()
            java.lang.Number r5 = (java.lang.Number) r5
            int r5 = r5.intValue()
            if (r5 == r2) goto L5a
            android.app.Application r6 = r9.application
            android.telephony.TelephonyManager r5 = com.android.settings.network.telephony.TelephonyRepositoryKt.telephonyManager(r6, r5)
            r6 = 3
            boolean r5 = r5.isMobileDataPolicyEnabled(r6)
            if (r5 == 0) goto L5a
            r9 = r3
            goto L7c
        L7b:
            r9 = 0
        L7c:
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)
            kotlin.Pair r2 = new kotlin.Pair
            r2.<init>(r8, r9)
            r0.label = r3
            kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
            java.lang.Object r7 = r7.emit(r2, r0)
            if (r7 != r1) goto L90
            return r1
        L90:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel$_init_$lambda$1$$inlined$map$1$2.emit(java.lang.Object,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }
}
