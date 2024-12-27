package com.android.settings.biometrics2.ui.viewmodel;

import android.util.Log;

import com.android.settings.biometrics2.ui.model.FingerprintEnrollable;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SharedFlowImpl;

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
final class FingerprintEnrollIntroViewModel$onNextButtonClick$1 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ FingerprintEnrollIntroViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintEnrollIntroViewModel$onNextButtonClick$1(
            FingerprintEnrollIntroViewModel fingerprintEnrollIntroViewModel,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintEnrollIntroViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintEnrollIntroViewModel$onNextButtonClick$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintEnrollIntroViewModel$onNextButtonClick$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FingerprintEnrollable fingerprintEnrollable =
                    (FingerprintEnrollable) this.this$0.enrollableStatusFlow.getValue();
            int ordinal = fingerprintEnrollable.ordinal();
            if (ordinal == 1) {
                SharedFlowImpl sharedFlowImpl = this.this$0._actionFlow;
                FingerprintEnrollIntroAction fingerprintEnrollIntroAction =
                        FingerprintEnrollIntroAction.CONTINUE_ENROLL;
                this.label = 2;
                if (sharedFlowImpl.emit(fingerprintEnrollIntroAction, this)
                        == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else if (ordinal != 2) {
                Log.w(
                        "FingerprintEnrollIntroViewModel",
                        "fail to click next, enrolled:" + fingerprintEnrollable);
            } else {
                SharedFlowImpl sharedFlowImpl2 = this.this$0._actionFlow;
                FingerprintEnrollIntroAction fingerprintEnrollIntroAction2 =
                        FingerprintEnrollIntroAction.DONE_AND_FINISH;
                this.label = 1;
                if (sharedFlowImpl2.emit(fingerprintEnrollIntroAction2, this)
                        == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1 && i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}