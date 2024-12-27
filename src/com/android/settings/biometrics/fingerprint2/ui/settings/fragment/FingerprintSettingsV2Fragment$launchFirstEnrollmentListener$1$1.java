package com.android.settings.biometrics.fingerprint2.ui.settings.fragment;

import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsNavigationViewModel;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FinishSettingsWithResult;

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
import kotlinx.coroutines.flow.StateFlowImpl;

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
final class FingerprintSettingsV2Fragment$launchFirstEnrollmentListener$1$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ ActivityResult $result;
    int label;
    final /* synthetic */ FingerprintSettingsV2Fragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSettingsV2Fragment$launchFirstEnrollmentListener$1$1(
            ActivityResult activityResult,
            FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment,
            Continuation continuation) {
        super(2, continuation);
        this.$result = activityResult;
        this.this$0 = fingerprintSettingsV2Fragment;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintSettingsV2Fragment$launchFirstEnrollmentListener$1$1(
                this.$result, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        FingerprintSettingsV2Fragment$launchFirstEnrollmentListener$1$1
                fingerprintSettingsV2Fragment$launchFirstEnrollmentListener$1$1 =
                        (FingerprintSettingsV2Fragment$launchFirstEnrollmentListener$1$1)
                                create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        fingerprintSettingsV2Fragment$launchFirstEnrollmentListener$1$1.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        StateFlowImpl stateFlowImpl;
        Object value;
        StateFlowImpl stateFlowImpl2;
        Object value2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ActivityResult activityResult = this.$result;
        int i = activityResult.mResultCode;
        Intent intent = activityResult.mData;
        Log.d(
                "FingerprintSettingsV2Fragment",
                "onEnrollFirstFingerprint(" + i + ", " + intent + ")");
        if (i == 1 && intent != null) {
            byte[] byteArrayExtra = intent.getByteArrayExtra("hw_auth_token");
            Long l = (Long) intent.getExtra("challenge");
            FingerprintSettingsNavigationViewModel fingerprintSettingsNavigationViewModel =
                    this.this$0.navigationViewModel;
            if (fingerprintSettingsNavigationViewModel == null) {
                Intrinsics.throwUninitializedPropertyAccessException("navigationViewModel");
                throw null;
            }
            if (byteArrayExtra == null) {
                fingerprintSettingsNavigationViewModel.launchFinishSettings("Error, empty token");
            } else if (l == null) {
                fingerprintSettingsNavigationViewModel.launchFinishSettings(
                        "Error, empty keyChallenge");
            } else {
                fingerprintSettingsNavigationViewModel.token = byteArrayExtra;
                fingerprintSettingsNavigationViewModel.challenge = l;
                fingerprintSettingsNavigationViewModel.showSettingsHelper();
            }
        } else if (i == 3) {
            FingerprintSettingsNavigationViewModel fingerprintSettingsNavigationViewModel2 =
                    this.this$0.navigationViewModel;
            if (fingerprintSettingsNavigationViewModel2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("navigationViewModel");
                throw null;
            }
            do {
                stateFlowImpl2 = fingerprintSettingsNavigationViewModel2._nextStep;
                value2 = stateFlowImpl2.getValue();
            } while (!stateFlowImpl2.compareAndSet(
                    value2,
                    new FinishSettingsWithResult(i, "Received RESULT_TIMEOUT when enrolling")));
        } else {
            FingerprintSettingsNavigationViewModel fingerprintSettingsNavigationViewModel3 =
                    this.this$0.navigationViewModel;
            if (fingerprintSettingsNavigationViewModel3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("navigationViewModel");
                throw null;
            }
            do {
                stateFlowImpl = fingerprintSettingsNavigationViewModel3._nextStep;
                value = stateFlowImpl.getValue();
            } while (!stateFlowImpl.compareAndSet(
                    value,
                    new FinishSettingsWithResult(i, "Incorrect resultCode or data was null")));
        }
        return Unit.INSTANCE;
    }
}
