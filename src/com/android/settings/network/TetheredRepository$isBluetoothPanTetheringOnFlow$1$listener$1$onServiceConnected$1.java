package com.android.settings.network;

import android.bluetooth.BluetoothPan;
import android.bluetooth.BluetoothProfile;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class TetheredRepository$isBluetoothPanTetheringOnFlow$1$listener$1$onServiceConnected$1
        extends SuspendLambda implements Function2 {
    final /* synthetic */ ProducerScope $$this$callbackFlow;
    final /* synthetic */ BluetoothProfile $proxy;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TetheredRepository$isBluetoothPanTetheringOnFlow$1$listener$1$onServiceConnected$1(
            ProducerScope producerScope,
            BluetoothProfile bluetoothProfile,
            Continuation continuation) {
        super(2, continuation);
        this.$$this$callbackFlow = producerScope;
        this.$proxy = bluetoothProfile;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TetheredRepository$isBluetoothPanTetheringOnFlow$1$listener$1$onServiceConnected$1(
                this.$$this$callbackFlow, this.$proxy, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        TetheredRepository$isBluetoothPanTetheringOnFlow$1$listener$1$onServiceConnected$1
                tetheredRepository$isBluetoothPanTetheringOnFlow$1$listener$1$onServiceConnected$1 =
                        (TetheredRepository$isBluetoothPanTetheringOnFlow$1$listener$1$onServiceConnected$1)
                                create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        tetheredRepository$isBluetoothPanTetheringOnFlow$1$listener$1$onServiceConnected$1
                .invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ProducerScope producerScope = this.$$this$callbackFlow;
        BluetoothPan bluetoothPan = this.$proxy;
        Intrinsics.checkNotNull(
                bluetoothPan,
                "null cannot be cast to non-null type android.bluetooth.BluetoothPan");
        ((ProducerCoroutine) producerScope)
                .mo1469trySendJP2dKIU(Boolean.valueOf(bluetoothPan.isTetheringOn()));
        return Unit.INSTANCE;
    }
}
