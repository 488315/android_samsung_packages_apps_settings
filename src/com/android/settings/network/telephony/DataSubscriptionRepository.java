package com.android.settings.network.telephony;

import android.content.Context;
import android.content.IntentFilter;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import com.android.settings.network.SubscriptionUtil;
import com.android.settingslib.spaprivileged.framework.common.BroadcastReceiverFlowKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DataSubscriptionRepository {
    public final Context context;
    public final Function1 getDisplayName;
    public final SubscriptionManager subscriptionManager;
    public final TelephonyManager telephonyManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DataSubscriptionIds {
        public final int activeSubId;
        public final int defaultSubId;

        public DataSubscriptionIds(int i, int i2) {
            this.defaultSubId = i;
            this.activeSubId = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DataSubscriptionIds)) {
                return false;
            }
            DataSubscriptionIds dataSubscriptionIds = (DataSubscriptionIds) obj;
            return this.defaultSubId == dataSubscriptionIds.defaultSubId
                    && this.activeSubId == dataSubscriptionIds.activeSubId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.activeSubId) + (Integer.hashCode(this.defaultSubId) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DataSubscriptionIds(defaultSubId=");
            sb.append(this.defaultSubId);
            sb.append(", activeSubId=");
            return Anchor$$ExternalSyntheticOutline0.m(sb, this.activeSubId, ")");
        }
    }

    public DataSubscriptionRepository(final Context context) {
        Function1 function1 =
                new Function1() { // from class:
                                  // com.android.settings.network.telephony.DataSubscriptionRepository.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return SubscriptionUtil.getUniqueSubscriptionDisplayNames(context)
                                .getOrDefault(
                                        Integer.valueOf(((Number) obj).intValue()),
                                        ApnSettings.MVNO_NONE)
                                .toString();
                    }
                };
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.getDisplayName = function1;
        Object systemService = context.getSystemService((Class<Object>) TelephonyManager.class);
        Intrinsics.checkNotNull(systemService);
        this.telephonyManager = (TelephonyManager) systemService;
        this.subscriptionManager = SubscriptionRepositoryKt.requireSubscriptionManager(context);
    }

    public final Flow dataSummaryFlow() {
        Flow defaultDataSubscriptionIdFlow = defaultDataSubscriptionIdFlow();
        TelephonyManager telephonyManager = this.telephonyManager;
        DataSubscriptionRepository$activeDataSubscriptionIdFlow$1 block =
                new Function1() { // from class:
                                  // com.android.settings.network.telephony.DataSubscriptionRepository$activeDataSubscriptionIdFlow$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.network.telephony.DataSubscriptionRepository$activeDataSubscriptionIdFlow$1$1, reason: invalid class name */
                    public final class AnonymousClass1 extends TelephonyCallback
                            implements TelephonyCallback.ActiveDataSubscriptionIdListener {
                        public final /* synthetic */ ProducerScope $this_telephonyCallbackFlow;

                        public AnonymousClass1(ProducerScope producerScope) {
                            this.$this_telephonyCallbackFlow = producerScope;
                        }

                        @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
                        public final void onActiveDataSubscriptionIdChanged(int i) {
                            ((ProducerCoroutine) this.$this_telephonyCallbackFlow)
                                    .mo1469trySendJP2dKIU(Integer.valueOf(i));
                            Log.d("DataSubscriptionRepo", "activeDataSubscriptionIdFlow: " + i);
                        }
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ProducerScope telephonyCallbackFlow = (ProducerScope) obj;
                        Intrinsics.checkNotNullParameter(
                                telephonyCallbackFlow, "$this$telephonyCallbackFlow");
                        return new AnonymousClass1(telephonyCallbackFlow);
                    }
                };
        Intrinsics.checkNotNullParameter(telephonyManager, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        Flow buffer$default =
                FlowKt.buffer$default(
                        FlowKt.callbackFlow(
                                new TelephonyRepositoryKt$telephonyCallbackFlow$1(
                                        block, telephonyManager, null)),
                        -1);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        final Flow distinctUntilChanged =
                FlowKt.distinctUntilChanged(
                        FlowKt.combine(
                                defaultDataSubscriptionIdFlow,
                                FlowKt.flowOn(buffer$default, defaultScheduler),
                                new DataSubscriptionRepository$dataSummaryFlow$1(3, null)));
        return FlowKt.flowOn(
                FlowKt.buffer$default(
                        new Flow() { // from class:
                            // com.android.settings.network.telephony.DataSubscriptionRepository$dataSummaryFlow$$inlined$map$1

                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                            /* renamed from: com.android.settings.network.telephony.DataSubscriptionRepository$dataSummaryFlow$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ DataSubscriptionRepository this$0;

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settings.network.telephony.DataSubscriptionRepository$dataSummaryFlow$$inlined$map$1$2$1, reason: invalid class name */
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
                                        return AnonymousClass2.this.emit(null, this);
                                    }
                                }

                                public AnonymousClass2(
                                        FlowCollector flowCollector,
                                        DataSubscriptionRepository dataSubscriptionRepository) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.this$0 = dataSubscriptionRepository;
                                }

                                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(
                                        java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                                    /*
                                        r6 = this;
                                        boolean r0 = r8 instanceof com.android.settings.network.telephony.DataSubscriptionRepository$dataSummaryFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r8
                                        com.android.settings.network.telephony.DataSubscriptionRepository$dataSummaryFlow$$inlined$map$1$2$1 r0 = (com.android.settings.network.telephony.DataSubscriptionRepository$dataSummaryFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.settings.network.telephony.DataSubscriptionRepository$dataSummaryFlow$$inlined$map$1$2$1 r0 = new com.android.settings.network.telephony.DataSubscriptionRepository$dataSummaryFlow$$inlined$map$1$2$1
                                        r0.<init>(r8)
                                    L18:
                                        java.lang.Object r8 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r8)
                                        goto L87
                                    L27:
                                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                        r6.<init>(r7)
                                        throw r6
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r8)
                                        com.android.settings.network.telephony.DataSubscriptionRepository$DataSubscriptionIds r7 = (com.android.settings.network.telephony.DataSubscriptionRepository.DataSubscriptionIds) r7
                                        com.android.settings.network.telephony.DataSubscriptionRepository r8 = r6.this$0
                                        android.telephony.SubscriptionManager r2 = r8.subscriptionManager
                                        int r4 = r7.activeSubId
                                        android.telephony.SubscriptionInfo r2 = r2.getActiveSubscriptionInfo(r4)
                                        if (r2 != 0) goto L43
                                        java.lang.String r7 = ""
                                        goto L7c
                                    L43:
                                        android.telephony.SubscriptionManager r4 = r8.subscriptionManager
                                        android.content.Context r5 = r8.context
                                        boolean r2 = com.android.settings.network.SubscriptionUtil.isSubscriptionVisible(r4, r5, r2)
                                        kotlin.jvm.functions.Function1 r4 = r8.getDisplayName
                                        int r5 = r7.defaultSubId
                                        if (r2 != 0) goto L5c
                                        java.lang.Integer r7 = java.lang.Integer.valueOf(r5)
                                        java.lang.Object r7 = r4.invoke(r7)
                                        java.lang.String r7 = (java.lang.String) r7
                                        goto L7c
                                    L5c:
                                        int r7 = r7.activeSubId
                                        java.lang.Integer r2 = java.lang.Integer.valueOf(r7)
                                        java.lang.Object r2 = r4.invoke(r2)
                                        java.lang.String r2 = (java.lang.String) r2
                                        if (r7 != r5) goto L6c
                                        r7 = r2
                                        goto L7c
                                    L6c:
                                        android.content.Context r7 = r8.context
                                        r8 = 2132023275(0x7f1417eb, float:1.9684993E38)
                                        java.lang.Object[] r2 = new java.lang.Object[]{r2}
                                        java.lang.String r7 = r7.getString(r8, r2)
                                        kotlin.jvm.internal.Intrinsics.checkNotNull(r7)
                                    L7c:
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                                        java.lang.Object r6 = r6.emit(r7, r0)
                                        if (r6 != r1) goto L87
                                        return r1
                                    L87:
                                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                        return r6
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.settings.network.telephony.DataSubscriptionRepository$dataSummaryFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(
                                    FlowCollector flowCollector, Continuation continuation) {
                                Object collect =
                                        Flow.this.collect(
                                                new AnonymousClass2(flowCollector, this),
                                                continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                        ? collect
                                        : Unit.INSTANCE;
                            }
                        },
                        -1),
                defaultScheduler);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.network.telephony.DataSubscriptionRepository$defaultDataSubscriptionIdFlow$$inlined$map$1] */
    public final Flow defaultDataSubscriptionIdFlow() {
        final Flow broadcastReceiverFlow =
                BroadcastReceiverFlowKt.broadcastReceiverFlow(
                        this.context,
                        new IntentFilter(
                                "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED"));
        return FlowKt.flowOn(
                FlowKt.buffer$default(
                        new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(
                                new DataSubscriptionRepository$defaultDataSubscriptionIdFlow$2(
                                        2, null),
                                new Flow() { // from class:
                                    // com.android.settings.network.telephony.DataSubscriptionRepository$defaultDataSubscriptionIdFlow$$inlined$map$1

                                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                    /* renamed from: com.android.settings.network.telephony.DataSubscriptionRepository$defaultDataSubscriptionIdFlow$$inlined$map$1$2, reason: invalid class name */
                                    public final class AnonymousClass2 implements FlowCollector {
                                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                        @Metadata(
                                                k = 3,
                                                mv = {1, 9, 0},
                                                xi = 48)
                                        /* renamed from: com.android.settings.network.telephony.DataSubscriptionRepository$defaultDataSubscriptionIdFlow$$inlined$map$1$2$1, reason: invalid class name */
                                        public final class AnonymousClass1
                                                extends ContinuationImpl {
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
                                                return AnonymousClass2.this.emit(null, this);
                                            }
                                        }

                                        public AnonymousClass2(FlowCollector flowCollector) {
                                            this.$this_unsafeFlow = flowCollector;
                                        }

                                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                        @Override // kotlinx.coroutines.flow.FlowCollector
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                                        */
                                        public final java.lang.Object emit(
                                                java.lang.Object r5,
                                                kotlin.coroutines.Continuation r6) {
                                            /*
                                                r4 = this;
                                                boolean r0 = r6 instanceof com.android.settings.network.telephony.DataSubscriptionRepository$defaultDataSubscriptionIdFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                                if (r0 == 0) goto L13
                                                r0 = r6
                                                com.android.settings.network.telephony.DataSubscriptionRepository$defaultDataSubscriptionIdFlow$$inlined$map$1$2$1 r0 = (com.android.settings.network.telephony.DataSubscriptionRepository$defaultDataSubscriptionIdFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                                int r1 = r0.label
                                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                                r3 = r1 & r2
                                                if (r3 == 0) goto L13
                                                int r1 = r1 - r2
                                                r0.label = r1
                                                goto L18
                                            L13:
                                                com.android.settings.network.telephony.DataSubscriptionRepository$defaultDataSubscriptionIdFlow$$inlined$map$1$2$1 r0 = new com.android.settings.network.telephony.DataSubscriptionRepository$defaultDataSubscriptionIdFlow$$inlined$map$1$2$1
                                                r0.<init>(r6)
                                            L18:
                                                java.lang.Object r6 = r0.result
                                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                                int r2 = r0.label
                                                r3 = 1
                                                if (r2 == 0) goto L2f
                                                if (r2 != r3) goto L27
                                                kotlin.ResultKt.throwOnFailure(r6)
                                                goto L4b
                                            L27:
                                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                                r4.<init>(r5)
                                                throw r4
                                            L2f:
                                                kotlin.ResultKt.throwOnFailure(r6)
                                                android.content.Intent r5 = (android.content.Intent) r5
                                                java.lang.String r6 = "subscription"
                                                r2 = -1
                                                int r5 = r5.getIntExtra(r6, r2)
                                                java.lang.Integer r6 = new java.lang.Integer
                                                r6.<init>(r5)
                                                r0.label = r3
                                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                                java.lang.Object r4 = r4.emit(r6, r0)
                                                if (r4 != r1) goto L4b
                                                return r1
                                            L4b:
                                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                                return r4
                                            */
                                            throw new UnsupportedOperationException(
                                                    "Method not decompiled:"
                                                        + " com.android.settings.network.telephony.DataSubscriptionRepository$defaultDataSubscriptionIdFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                                        }
                                    }

                                    @Override // kotlinx.coroutines.flow.Flow
                                    public final Object collect(
                                            FlowCollector flowCollector,
                                            Continuation continuation) {
                                        Object collect =
                                                Flow.this.collect(
                                                        new AnonymousClass2(flowCollector),
                                                        continuation);
                                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                                ? collect
                                                : Unit.INSTANCE;
                                    }
                                }),
                        -1),
                Dispatchers.Default);
    }
}
