package com.android.settings.network;

import android.util.Log;

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
            "\u0000\u0016\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001*\u0010\u0012\f\u0012\n"
                + " \u0004*\u0004\u0018\u00010\u00030\u00030\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            "Lcom/android/settings/SidecarFragment;",
            "kotlin.jvm.PlatformType",
            "e",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class SimOnboardingActivity$sidecarReceiverFlow$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SimOnboardingActivity$sidecarReceiverFlow$2 simOnboardingActivity$sidecarReceiverFlow$2 =
                new SimOnboardingActivity$sidecarReceiverFlow$2(3, (Continuation) obj3);
        simOnboardingActivity$sidecarReceiverFlow$2.L$0 = (Throwable) obj2;
        Unit unit = Unit.INSTANCE;
        simOnboardingActivity$sidecarReceiverFlow$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Log.e("SimOnboardingActivity", "Error while sidecarReceiverFlow", (Throwable) this.L$0);
        return Unit.INSTANCE;
    }
}
