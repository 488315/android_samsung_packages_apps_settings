package com.android.settings.network;

import com.android.settings.SidecarFragment;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "it", "Lcom/android/settings/SidecarFragment;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class SimOnboardingActivity$registerSidecarReceiverFlow$3 extends SuspendLambda
        implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SimOnboardingActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimOnboardingActivity$registerSidecarReceiverFlow$3(
            SimOnboardingActivity simOnboardingActivity, Continuation continuation) {
        super(2, continuation);
        this.this$0 = simOnboardingActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SimOnboardingActivity$registerSidecarReceiverFlow$3
                simOnboardingActivity$registerSidecarReceiverFlow$3 =
                        new SimOnboardingActivity$registerSidecarReceiverFlow$3(
                                this.this$0, continuation);
        simOnboardingActivity$registerSidecarReceiverFlow$3.L$0 = obj;
        return simOnboardingActivity$registerSidecarReceiverFlow$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        SimOnboardingActivity$registerSidecarReceiverFlow$3
                simOnboardingActivity$registerSidecarReceiverFlow$3 =
                        (SimOnboardingActivity$registerSidecarReceiverFlow$3)
                                create((SidecarFragment) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        simOnboardingActivity$registerSidecarReceiverFlow$3.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.onStateChange((SidecarFragment) this.L$0);
        return Unit.INSTANCE;
    }
}
