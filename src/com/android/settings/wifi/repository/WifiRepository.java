package com.android.settings.wifi.repository;

import android.content.Context;
import android.content.IntentFilter;

import com.android.settingslib.spaprivileged.framework.common.BroadcastReceiverFlowKt;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiRepository {
    public final Flow wifiStateChangedActionFlow;

    public WifiRepository(Context context) {
        Flow wifiStateChangedActionFlow =
                BroadcastReceiverFlowKt.broadcastReceiverFlow(
                        context, new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"));
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(wifiStateChangedActionFlow, "wifiStateChangedActionFlow");
        this.wifiStateChangedActionFlow = wifiStateChangedActionFlow;
    }

    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 wifiStateFlow() {
        final Flow flow = this.wifiStateChangedActionFlow;
        return FlowKt.onEach(
                new Flow() { // from class:
                    // com.android.settings.wifi.repository.WifiRepository$wifiStateFlow$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.wifi.repository.WifiRepository$wifiStateFlow$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.wifi.repository.WifiRepository$wifiStateFlow$$inlined$map$1$2$1, reason: invalid class name */
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
                                java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                            /*
                                r4 = this;
                                boolean r0 = r6 instanceof com.android.settings.wifi.repository.WifiRepository$wifiStateFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.settings.wifi.repository.WifiRepository$wifiStateFlow$$inlined$map$1$2$1 r0 = (com.android.settings.wifi.repository.WifiRepository$wifiStateFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.wifi.repository.WifiRepository$wifiStateFlow$$inlined$map$1$2$1 r0 = new com.android.settings.wifi.repository.WifiRepository$wifiStateFlow$$inlined$map$1$2$1
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
                                java.lang.String r6 = "wifi_state"
                                r2 = 4
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
                                        + " com.android.settings.wifi.repository.WifiRepository$wifiStateFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        Object collect =
                                Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                ? collect
                                : Unit.INSTANCE;
                    }
                },
                new WifiRepository$wifiStateFlow$2(2, null));
    }
}
