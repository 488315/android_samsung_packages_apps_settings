package com.android.settings.network;

import android.bluetooth.BluetoothAdapter;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

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
public final class TetheredRepository$isBluetoothTetheringOnFlow$$inlined$flatMapLatest$1
        extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ TetheredRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TetheredRepository$isBluetoothTetheringOnFlow$$inlined$flatMapLatest$1(
            TetheredRepository tetheredRepository, Continuation continuation) {
        super(3, continuation);
        this.this$0 = tetheredRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        TetheredRepository$isBluetoothTetheringOnFlow$$inlined$flatMapLatest$1
                tetheredRepository$isBluetoothTetheringOnFlow$$inlined$flatMapLatest$1 =
                        new TetheredRepository$isBluetoothTetheringOnFlow$$inlined$flatMapLatest$1(
                                this.this$0, (Continuation) obj3);
        tetheredRepository$isBluetoothTetheringOnFlow$$inlined$flatMapLatest$1.L$0 =
                (FlowCollector) obj;
        tetheredRepository$isBluetoothTetheringOnFlow$$inlined$flatMapLatest$1.L$1 = obj2;
        return tetheredRepository$isBluetoothTetheringOnFlow$$inlined$flatMapLatest$1.invokeSuspend(
                Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            BluetoothAdapter bluetoothAdapter = this.this$0.adapter;
            if (bluetoothAdapter == null || bluetoothAdapter.getState() != 12) {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 =
                        new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            } else {
                TetheredRepository tetheredRepository = this.this$0;
                tetheredRepository.getClass();
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 =
                        FlowKt.flowOn(
                                FlowKt.buffer$default(
                                        FlowKt.callbackFlow(
                                                new TetheredRepository$isBluetoothPanTetheringOnFlow$1(
                                                        tetheredRepository, null)),
                                        -1),
                                Dispatchers.Default);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector)
                    == coroutineSingletons) {
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
