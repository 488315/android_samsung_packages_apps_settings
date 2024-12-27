package com.android.settings.network;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/channels/ProducerScope;",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class TetheredRepository$isBluetoothPanTetheringOnFlow$1 extends SuspendLambda
        implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TetheredRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TetheredRepository$isBluetoothPanTetheringOnFlow$1(
            TetheredRepository tetheredRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = tetheredRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TetheredRepository$isBluetoothPanTetheringOnFlow$1
                tetheredRepository$isBluetoothPanTetheringOnFlow$1 =
                        new TetheredRepository$isBluetoothPanTetheringOnFlow$1(
                                this.this$0, continuation);
        tetheredRepository$isBluetoothPanTetheringOnFlow$1.L$0 = obj;
        return tetheredRepository$isBluetoothPanTetheringOnFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TetheredRepository$isBluetoothPanTetheringOnFlow$1)
                        create((ProducerScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            BluetoothProfile.ServiceListener serviceListener =
                    new BluetoothProfile
                            .ServiceListener() { // from class:
                                                 // com.android.settings.network.TetheredRepository$isBluetoothPanTetheringOnFlow$1$listener$1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // android.bluetooth.BluetoothProfile.ServiceListener
                        public final void onServiceConnected(int i2, BluetoothProfile proxy) {
                            Intrinsics.checkNotNullParameter(proxy, "proxy");
                            Ref$ObjectRef.this.element = proxy;
                            ProducerScope producerScope2 = producerScope;
                            BuildersKt.launch$default(
                                    producerScope2,
                                    Dispatchers.Default,
                                    null,
                                    new TetheredRepository$isBluetoothPanTetheringOnFlow$1$listener$1$onServiceConnected$1(
                                            producerScope2, proxy, null),
                                    2);
                        }

                        @Override // android.bluetooth.BluetoothProfile.ServiceListener
                        public final void onServiceDisconnected(int i2) {}
                    };
            TetheredRepository tetheredRepository = this.this$0;
            BluetoothAdapter bluetoothAdapter = tetheredRepository.adapter;
            if (bluetoothAdapter != null) {
                bluetoothAdapter.getProfileProxy(tetheredRepository.context, serviceListener, 5);
            }
            final TetheredRepository tetheredRepository2 = this.this$0;
            Function0 function0 =
                    new Function0() { // from class:
                                      // com.android.settings.network.TetheredRepository$isBluetoothPanTetheringOnFlow$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            BluetoothAdapter bluetoothAdapter2;
                            BluetoothProfile bluetoothProfile = Ref$ObjectRef.this.element;
                            if (bluetoothProfile != null
                                    && (bluetoothAdapter2 = tetheredRepository2.adapter) != null) {
                                bluetoothAdapter2.closeProfileProxy(5, bluetoothProfile);
                            }
                            return Unit.INSTANCE;
                        }
                    };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
