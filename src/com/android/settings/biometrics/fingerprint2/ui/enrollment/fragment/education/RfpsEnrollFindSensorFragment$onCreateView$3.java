package com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.education;

import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintEnrollFindSensorViewModel;

import com.google.android.setupdesign.GlifLayout;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

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
final class RfpsEnrollFindSensorFragment$onCreateView$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ GlifLayout $view;
    int label;
    final /* synthetic */ RfpsEnrollFindSensorFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RfpsEnrollFindSensorFragment$onCreateView$3(
            RfpsEnrollFindSensorFragment rfpsEnrollFindSensorFragment,
            GlifLayout glifLayout,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = rfpsEnrollFindSensorFragment;
        this.$view = glifLayout;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RfpsEnrollFindSensorFragment$onCreateView$3(
                this.this$0, this.$view, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RfpsEnrollFindSensorFragment$onCreateView$3)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FingerprintEnrollFindSensorViewModel access$getViewModel =
                    RfpsEnrollFindSensorFragment.access$getViewModel(this.this$0);
            RfpsEnrollFindSensorFragment$onCreateView$2.AnonymousClass1 anonymousClass1 =
                    new RfpsEnrollFindSensorFragment$onCreateView$2.AnonymousClass1(
                            this.this$0, this.$view, 1);
            this.label = 1;
            if (access$getViewModel.showRfpsAnimation.collect(anonymousClass1, this)
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
