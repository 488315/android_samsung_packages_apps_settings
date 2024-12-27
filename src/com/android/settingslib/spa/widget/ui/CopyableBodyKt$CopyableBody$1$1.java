package com.android.settingslib.spa.widget.ui;

import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.unit.DpOffset;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Landroidx/compose/ui/input/pointer/PointerInputScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class CopyableBodyKt$CopyableBody$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState $dpOffset$delegate;
    final /* synthetic */ MutableState $expanded$delegate;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CopyableBodyKt$CopyableBody$1$1(
            MutableState mutableState, MutableState mutableState2, Continuation continuation) {
        super(2, continuation);
        this.$dpOffset$delegate = mutableState;
        this.$expanded$delegate = mutableState2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CopyableBodyKt$CopyableBody$1$1 copyableBodyKt$CopyableBody$1$1 =
                new CopyableBodyKt$CopyableBody$1$1(
                        this.$dpOffset$delegate, this.$expanded$delegate, continuation);
        copyableBodyKt$CopyableBody$1$1.L$0 = obj;
        return copyableBodyKt$CopyableBody$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CopyableBodyKt$CopyableBody$1$1)
                        create((PointerInputScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object detectTapGestures;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final PointerInputScope pointerInputScope = (PointerInputScope) this.L$0;
            final MutableState mutableState = this.$dpOffset$delegate;
            final MutableState mutableState2 = this.$expanded$delegate;
            Function1 function1 =
                    new Function1() { // from class:
                                      // com.android.settingslib.spa.widget.ui.CopyableBodyKt$CopyableBody$1$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            long j = ((Offset) obj2).packedValue;
                            mutableState.setValue(
                                    new DpOffset(
                                            DpKt.m587DpOffsetYgX7TsA(
                                                    Offset.m267getXimpl(j)
                                                            / ((SuspendingPointerInputModifierNodeImpl)
                                                                            PointerInputScope.this)
                                                                    .getDensity(),
                                                    Offset.m268getYimpl(j)
                                                            / ((SuspendingPointerInputModifierNodeImpl)
                                                                            PointerInputScope.this)
                                                                    .getDensity())));
                            mutableState2.setValue(Boolean.TRUE);
                            return Unit.INSTANCE;
                        }
                    };
            this.label = 1;
            detectTapGestures =
                    TapGestureDetectorKt.detectTapGestures(
                            pointerInputScope,
                            this,
                            null,
                            (r12 & 2) != 0 ? null : function1,
                            (r12 & 8) != 0 ? null : null,
                            TapGestureDetectorKt.NoPressGesture);
            if (detectTapGestures == coroutineSingletons) {
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
