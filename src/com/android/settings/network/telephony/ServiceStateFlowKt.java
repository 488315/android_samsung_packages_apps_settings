package com.android.settings.network.telephony;

import android.content.Context;
import android.telephony.ServiceState;
import android.telephony.TelephonyCallback;
import android.util.Log;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ServiceStateFlowKt {
    public static final Flow serviceStateFlow(Context context, final int i) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return TelephonyRepositoryKt.telephonyCallbackFlow(
                context,
                i,
                new Function1() { // from class:
                                  // com.android.settings.network.telephony.ServiceStateFlowKt$serviceStateFlow$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.network.telephony.ServiceStateFlowKt$serviceStateFlow$1$1, reason: invalid class name */
                    public final class AnonymousClass1 extends TelephonyCallback
                            implements TelephonyCallback.ServiceStateListener {
                        public final /* synthetic */ int $subId;
                        public final /* synthetic */ ProducerScope $this_telephonyCallbackFlow;

                        public AnonymousClass1(ProducerScope producerScope, int i) {
                            this.$this_telephonyCallbackFlow = producerScope;
                            this.$subId = i;
                        }

                        @Override // android.telephony.TelephonyCallback.ServiceStateListener
                        public final void onServiceStateChanged(ServiceState serviceState) {
                            Intrinsics.checkNotNullParameter(serviceState, "serviceState");
                            ((ProducerCoroutine) this.$this_telephonyCallbackFlow)
                                    .mo1469trySendJP2dKIU(serviceState);
                            Log.d(
                                    "ServiceStateFlow",
                                    "[" + this.$subId + "] serviceState: " + serviceState);
                        }
                    }

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ProducerScope telephonyCallbackFlow = (ProducerScope) obj;
                        Intrinsics.checkNotNullParameter(
                                telephonyCallbackFlow, "$this$telephonyCallbackFlow");
                        return new AnonymousClass1(telephonyCallbackFlow, i);
                    }
                });
    }
}
