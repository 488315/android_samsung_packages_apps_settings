package com.android.settings.network.telephony;

import android.telephony.TelephonyCallback;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0000\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"
                + "¢\u0006\u0002\b\u0004"
        },
        d2 = {
            "<anonymous>",
            "Landroid/telephony/TelephonyCallback;",
            "Lkotlinx/coroutines/channels/ProducerScope;",
            ApnSettings.MVNO_NONE,
            "invoke"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class CallStateRepository$callStateFlow$1 extends Lambda implements Function1 {
    public static final CallStateRepository$callStateFlow$1 INSTANCE =
            new CallStateRepository$callStateFlow$1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.network.telephony.CallStateRepository$callStateFlow$1$1, reason: invalid class name */
    public final class AnonymousClass1 extends TelephonyCallback
            implements TelephonyCallback.CallStateListener {
        public final /* synthetic */ ProducerScope $this_telephonyCallbackFlow;

        public AnonymousClass1(ProducerScope producerScope) {
            this.$this_telephonyCallbackFlow = producerScope;
        }

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public final void onCallStateChanged(int i) {
            ((ProducerCoroutine) this.$this_telephonyCallbackFlow)
                    .mo1469trySendJP2dKIU(Integer.valueOf(i));
        }
    }

    public CallStateRepository$callStateFlow$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ProducerScope telephonyCallbackFlow = (ProducerScope) obj;
        Intrinsics.checkNotNullParameter(telephonyCallbackFlow, "$this$telephonyCallbackFlow");
        return new AnonymousClass1(telephonyCallbackFlow);
    }
}
