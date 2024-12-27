package com.android.settings.network.telephony.wificalling;

import android.telephony.ims.ProvisioningManager;

import com.android.settings.network.telephony.ims.ImsFeatureProvisionedFlowKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00010\u00022\u0006\u0010\u0003\u001a\u00028\u0000H\u008a@"
        },
        d2 = {
            "T",
            "R",
            "Lkotlinx/coroutines/flow/FlowCollector;",
            "it",
            ApnSettings.MVNO_NONE,
            "<anonymous>"
        },
        k = 3,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class WifiCallingRepository$wifiCallingReadyFlow$$inlined$flatMapLatest$1
        extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ WifiCallingRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiCallingRepository$wifiCallingReadyFlow$$inlined$flatMapLatest$1(
            WifiCallingRepository wifiCallingRepository, Continuation continuation) {
        super(3, continuation);
        this.this$0 = wifiCallingRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        WifiCallingRepository$wifiCallingReadyFlow$$inlined$flatMapLatest$1
                wifiCallingRepository$wifiCallingReadyFlow$$inlined$flatMapLatest$1 =
                        new WifiCallingRepository$wifiCallingReadyFlow$$inlined$flatMapLatest$1(
                                this.this$0, (Continuation) obj3);
        wifiCallingRepository$wifiCallingReadyFlow$$inlined$flatMapLatest$1.L$0 =
                (FlowCollector) obj;
        wifiCallingRepository$wifiCallingReadyFlow$$inlined$flatMapLatest$1.L$1 = obj2;
        return wifiCallingRepository$wifiCallingReadyFlow$$inlined$flatMapLatest$1.invokeSuspend(
                Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int i2 = this.this$0.subId;
            ProvisioningManager createForSubscriptionId =
                    ProvisioningManager.createForSubscriptionId(i2);
            Intrinsics.checkNotNullExpressionValue(
                    createForSubscriptionId, "createForSubscriptionId(...)");
            Flow imsFeatureProvisionedFlow =
                    ImsFeatureProvisionedFlowKt.imsFeatureProvisionedFlow(
                            i2, 1, 1, createForSubscriptionId);
            final WifiCallingRepository wifiCallingRepository = this.this$0;
            final Flow imsReadyFlow = wifiCallingRepository.imsMmTelRepository.imsReadyFlow();
            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 combine =
                    FlowKt.combine(
                            imsFeatureProvisionedFlow,
                            new Flow() { // from class:
                                // com.android.settings.network.telephony.wificalling.WifiCallingRepository$isWifiCallingSupportedFlow$$inlined$map$1

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                /* renamed from: com.android.settings.network.telephony.wificalling.WifiCallingRepository$isWifiCallingSupportedFlow$$inlined$map$1$2, reason: invalid class name */
                                public final class AnonymousClass2 implements FlowCollector {
                                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                    public final /* synthetic */ WifiCallingRepository this$0;

                                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                    @Metadata(
                                            k = 3,
                                            mv = {1, 9, 0},
                                            xi = 48)
                                    /* renamed from: com.android.settings.network.telephony.wificalling.WifiCallingRepository$isWifiCallingSupportedFlow$$inlined$map$1$2$1, reason: invalid class name */
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
                                            WifiCallingRepository wifiCallingRepository) {
                                        this.$this_unsafeFlow = flowCollector;
                                        this.this$0 = wifiCallingRepository;
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:20:0x0072 A[RETURN] */
                                    /* JADX WARN: Removed duplicated region for block: B:21:0x003b  */
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                                    @Override // kotlinx.coroutines.flow.FlowCollector
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                    */
                                    public final java.lang.Object emit(
                                            java.lang.Object r8,
                                            kotlin.coroutines.Continuation r9) {
                                        /*
                                            r7 = this;
                                            boolean r0 = r9 instanceof com.android.settings.network.telephony.wificalling.WifiCallingRepository$isWifiCallingSupportedFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                            if (r0 == 0) goto L13
                                            r0 = r9
                                            com.android.settings.network.telephony.wificalling.WifiCallingRepository$isWifiCallingSupportedFlow$$inlined$map$1$2$1 r0 = (com.android.settings.network.telephony.wificalling.WifiCallingRepository$isWifiCallingSupportedFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                            int r1 = r0.label
                                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                            r3 = r1 & r2
                                            if (r3 == 0) goto L13
                                            int r1 = r1 - r2
                                            r0.label = r1
                                            goto L18
                                        L13:
                                            com.android.settings.network.telephony.wificalling.WifiCallingRepository$isWifiCallingSupportedFlow$$inlined$map$1$2$1 r0 = new com.android.settings.network.telephony.wificalling.WifiCallingRepository$isWifiCallingSupportedFlow$$inlined$map$1$2$1
                                            r0.<init>(r9)
                                        L18:
                                            java.lang.Object r9 = r0.result
                                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                            int r2 = r0.label
                                            r3 = 0
                                            r4 = 2
                                            r5 = 1
                                            if (r2 == 0) goto L3b
                                            if (r2 == r5) goto L33
                                            if (r2 != r4) goto L2b
                                            kotlin.ResultKt.throwOnFailure(r9)
                                            goto L73
                                        L2b:
                                            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                                            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                                            r7.<init>(r8)
                                            throw r7
                                        L33:
                                            java.lang.Object r7 = r0.L$0
                                            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                                            kotlin.ResultKt.throwOnFailure(r9)
                                            goto L62
                                        L3b:
                                            kotlin.ResultKt.throwOnFailure(r9)
                                            java.lang.Boolean r8 = (java.lang.Boolean) r8
                                            boolean r8 = r8.booleanValue()
                                            kotlinx.coroutines.flow.FlowCollector r9 = r7.$this_unsafeFlow
                                            if (r8 == 0) goto L66
                                            r0.L$0 = r9
                                            r0.label = r5
                                            com.android.settings.network.telephony.wificalling.WifiCallingRepository r7 = r7.this$0
                                            r7.getClass()
                                            kotlinx.coroutines.scheduling.DefaultScheduler r8 = kotlinx.coroutines.Dispatchers.Default
                                            com.android.settings.network.telephony.wificalling.WifiCallingRepository$isWifiCallingSupported$2 r2 = new com.android.settings.network.telephony.wificalling.WifiCallingRepository$isWifiCallingSupported$2
                                            r2.<init>(r7, r3)
                                            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r8, r2, r0)
                                            if (r7 != r1) goto L5f
                                            return r1
                                        L5f:
                                            r6 = r9
                                            r9 = r7
                                            r7 = r6
                                        L62:
                                            r6 = r9
                                            r9 = r7
                                            r7 = r6
                                            goto L68
                                        L66:
                                            java.lang.Boolean r7 = java.lang.Boolean.FALSE
                                        L68:
                                            r0.L$0 = r3
                                            r0.label = r4
                                            java.lang.Object r7 = r9.emit(r7, r0)
                                            if (r7 != r1) goto L73
                                            return r1
                                        L73:
                                            kotlin.Unit r7 = kotlin.Unit.INSTANCE
                                            return r7
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.settings.network.telephony.wificalling.WifiCallingRepository$isWifiCallingSupportedFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                    + " kotlin.coroutines.Continuation):java.lang.Object");
                                    }
                                }

                                @Override // kotlinx.coroutines.flow.Flow
                                public final Object collect(
                                        FlowCollector flowCollector2, Continuation continuation) {
                                    Object collect =
                                            Flow.this.collect(
                                                    new AnonymousClass2(
                                                            flowCollector2, wifiCallingRepository),
                                                    continuation);
                                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                            ? collect
                                            : Unit.INSTANCE;
                                }
                            },
                            new WifiCallingRepository$wifiCallingReadyFlow$1$1(3, null));
            this.label = 1;
            if (FlowKt.emitAll(this, combine, flowCollector) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
