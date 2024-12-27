package com.android.settings.network;

import android.util.Log;

import androidx.compose.runtime.MutableState;

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
final class SimOnboardingActivity$Content$6 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SimOnboardingActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimOnboardingActivity$Content$6(
            SimOnboardingActivity simOnboardingActivity, Continuation continuation) {
        super(2, continuation);
        this.this$0 = simOnboardingActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SimOnboardingActivity$Content$6(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        SimOnboardingActivity$Content$6 simOnboardingActivity$Content$6 =
                (SimOnboardingActivity$Content$6) create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        simOnboardingActivity$Content$6.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.this$0.getShowError().getValue()
                == SimOnboardingActivity$Companion$ErrorType.ERROR_NONE) {
            MutableState mutableState = this.this$0.showProgressDialog;
            if (mutableState == null) {
                Intrinsics.throwUninitializedPropertyAccessException("showProgressDialog");
                throw null;
            }
            if (!((Boolean) mutableState.getValue()).booleanValue()
                    && !((Boolean) this.this$0.getShowDsdsProgressDialog().getValue())
                            .booleanValue()) {
                MutableState mutableState2 = this.this$0.showRestartDialog;
                if (mutableState2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("showRestartDialog");
                    throw null;
                }
                if (!((Boolean) mutableState2.getValue()).booleanValue()) {
                    if (!SimOnboardingActivity.onboardingService.activeSubInfoList.isEmpty()) {
                        MutableState mutableState3 = this.this$0.showStartingDialog;
                        if (mutableState3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException(
                                    "showStartingDialog");
                            throw null;
                        }
                        mutableState3.setValue(Boolean.TRUE);
                    }
                    return Unit.INSTANCE;
                }
            }
        }
        Object value = this.this$0.getShowError().getValue();
        MutableState mutableState4 = this.this$0.showProgressDialog;
        if (mutableState4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("showProgressDialog");
            throw null;
        }
        Object value2 = mutableState4.getValue();
        Object value3 = this.this$0.getShowDsdsProgressDialog().getValue();
        MutableState mutableState5 = this.this$0.showRestartDialog;
        if (mutableState5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("showRestartDialog");
            throw null;
        }
        Log.d(
                "SimOnboardingActivity",
                "status: showError:"
                        + value
                        + ", showProgressDialog:"
                        + value2
                        + ", showDsdsProgressDialog:"
                        + value3
                        + ", showRestartDialog:"
                        + mutableState5.getValue());
        MutableState mutableState6 = this.this$0.showStartingDialog;
        if (mutableState6 != null) {
            mutableState6.setValue(Boolean.FALSE);
            return Unit.INSTANCE;
        }
        Intrinsics.throwUninitializedPropertyAccessException("showStartingDialog");
        throw null;
    }
}
