package com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.fragment;

import android.view.MotionEvent;
import android.view.View;

import com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.viewmodel.UdfpsViewModel;

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
final class UdfpsEnrollFragment$onViewCreated$7 extends SuspendLambda implements Function2 {
    final /* synthetic */ View $view;
    int label;
    final /* synthetic */ UdfpsEnrollFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UdfpsEnrollFragment$onViewCreated$7(
            View view, UdfpsEnrollFragment udfpsEnrollFragment, Continuation continuation) {
        super(2, continuation);
        this.$view = view;
        this.this$0 = udfpsEnrollFragment;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UdfpsEnrollFragment$onViewCreated$7(this.$view, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        UdfpsEnrollFragment$onViewCreated$7 udfpsEnrollFragment$onViewCreated$7 =
                (UdfpsEnrollFragment$onViewCreated$7)
                        create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        udfpsEnrollFragment$onViewCreated$7.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        View view = this.$view;
        final UdfpsEnrollFragment udfpsEnrollFragment = this.this$0;
        view.setOnTouchListener(
                new View
                        .OnTouchListener() { // from class:
                                             // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.fragment.UdfpsEnrollFragment$onViewCreated$7.1
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view2, MotionEvent motionEvent) {
                        UdfpsViewModel viewModel = UdfpsEnrollFragment.this.getViewModel();
                        Intrinsics.checkNotNull(motionEvent);
                        viewModel.getClass();
                        throw null;
                    }
                });
        return Unit.INSTANCE;
    }
}
