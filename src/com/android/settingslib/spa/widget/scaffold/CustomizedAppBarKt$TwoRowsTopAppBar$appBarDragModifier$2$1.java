package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.DecayAnimationSpecImpl;
import androidx.compose.material3.ExitUntilCollapsedScrollBehavior;
import androidx.compose.material3.TopAppBarScrollBehavior;
import androidx.compose.material3.TopAppBarState;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0007\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/CoroutineScope;",
            "velocity",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class CustomizedAppBarKt$TwoRowsTopAppBar$appBarDragModifier$2$1 extends SuspendLambda
        implements Function3 {
    final /* synthetic */ TopAppBarScrollBehavior $scrollBehavior;
    /* synthetic */ float F$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizedAppBarKt$TwoRowsTopAppBar$appBarDragModifier$2$1(
            TopAppBarScrollBehavior topAppBarScrollBehavior, Continuation continuation) {
        super(3, continuation);
        this.$scrollBehavior = topAppBarScrollBehavior;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float floatValue = ((Number) obj2).floatValue();
        CustomizedAppBarKt$TwoRowsTopAppBar$appBarDragModifier$2$1
                customizedAppBarKt$TwoRowsTopAppBar$appBarDragModifier$2$1 =
                        new CustomizedAppBarKt$TwoRowsTopAppBar$appBarDragModifier$2$1(
                                this.$scrollBehavior, (Continuation) obj3);
        customizedAppBarKt$TwoRowsTopAppBar$appBarDragModifier$2$1.F$0 = floatValue;
        return customizedAppBarKt$TwoRowsTopAppBar$appBarDragModifier$2$1.invokeSuspend(
                Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            float f = this.F$0;
            TopAppBarScrollBehavior topAppBarScrollBehavior = this.$scrollBehavior;
            TopAppBarState topAppBarState =
                    ((ExitUntilCollapsedScrollBehavior) topAppBarScrollBehavior).state;
            DecayAnimationSpecImpl decayAnimationSpecImpl =
                    ((ExitUntilCollapsedScrollBehavior) topAppBarScrollBehavior).flingAnimationSpec;
            AnimationSpec animationSpec =
                    ((ExitUntilCollapsedScrollBehavior) topAppBarScrollBehavior).snapAnimationSpec;
            this.label = 1;
            if (CustomizedAppBarKt.access$settleAppBar(
                            topAppBarState, f, decayAnimationSpecImpl, animationSpec, this)
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
