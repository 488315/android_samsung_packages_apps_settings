package com.android.settings.biometrics.fingerprint2.ui.settings.binder;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollIntroductionInternal;
import com.android.settings.biometrics.fingerprint2.ui.settings.fragment.FingerprintSettingsV2Fragment;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.EnrollAdditionalFingerprint;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.EnrollFirstFingerprint;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsNavigationViewModel;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FinishSettings;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FinishSettingsWithResult;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.LaunchConfirmDeviceCredential;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.LaunchedActivity;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.NextStepViewModel;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.ShowSettings;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.Reflection;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

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
final class FingerprintSettingsViewBinder$bind$6 extends SuspendLambda implements Function2 {
    final /* synthetic */ FingerprintSettingsNavigationViewModel $navigationViewModel;
    final /* synthetic */ FingerprintSettingsViewBinder.FingerprintView $view;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSettingsViewBinder$bind$6(
            FingerprintSettingsNavigationViewModel fingerprintSettingsNavigationViewModel,
            FingerprintSettingsViewBinder.FingerprintView fingerprintView,
            Continuation continuation) {
        super(2, continuation);
        this.$navigationViewModel = fingerprintSettingsNavigationViewModel;
        this.$view = fingerprintView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintSettingsViewBinder$bind$6(
                this.$navigationViewModel, this.$view, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintSettingsViewBinder$bind$6)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 filterNotNull =
                    FlowKt.filterNotNull(this.$navigationViewModel.nextStep);
            final FingerprintSettingsViewBinder.FingerprintView fingerprintView = this.$view;
            FlowCollector flowCollector =
                    new FlowCollector() { // from class:
                                          // com.android.settings.biometrics.fingerprint2.ui.settings.binder.FingerprintSettingsViewBinder$bind$6.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            NextStepViewModel nextStepViewModel = (NextStepViewModel) obj2;
                            Ref$ObjectRef ref$ObjectRef2 = Ref$ObjectRef.this;
                            Job job = (Job) ref$ObjectRef2.element;
                            if (job != null) {
                                job.cancel(null);
                            }
                            ref$ObjectRef2.element = null;
                            Log.d(
                                    "FingerprintSettingsViewBinder",
                                    "next step = " + nextStepViewModel);
                            boolean z = nextStepViewModel instanceof EnrollFirstFingerprint;
                            FingerprintSettingsViewBinder.FingerprintView fingerprintView2 =
                                    fingerprintView;
                            if (z) {
                                EnrollFirstFingerprint enrollFirstFingerprint =
                                        (EnrollFirstFingerprint) nextStepViewModel;
                                int i2 = enrollFirstFingerprint.userId;
                                FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment =
                                        (FingerprintSettingsV2Fragment) fingerprintView2;
                                FingerprintSettingsNavigationViewModel
                                        fingerprintSettingsNavigationViewModel =
                                                fingerprintSettingsV2Fragment.navigationViewModel;
                                if (fingerprintSettingsNavigationViewModel == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException(
                                            "navigationViewModel");
                                    throw null;
                                }
                                fingerprintSettingsNavigationViewModel.setStepToLaunched();
                                Log.d(
                                        "FingerprintSettingsV2Fragment",
                                        "launchFullFingerprintEnrollment");
                                Intent intent = new Intent();
                                intent.setClassName(
                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                        FingerprintEnrollIntroductionInternal.class.getName());
                                intent.putExtra("from_settings_summary", true);
                                intent.putExtra("page_transition_type", 1);
                                intent.putExtra("android.intent.extra.USER_ID", i2);
                                Long l = enrollFirstFingerprint.gateKeeperPasswordHandle;
                                if (l != null) {
                                    intent.putExtra("gk_pw_handle", l.longValue());
                                } else {
                                    intent.putExtra(
                                            "hw_auth_token", enrollFirstFingerprint.challengeToken);
                                    intent.putExtra("challenge", enrollFirstFingerprint.challenge);
                                }
                                fingerprintSettingsV2Fragment.launchFirstEnrollmentListener.launch(
                                        intent);
                            } else if (nextStepViewModel instanceof EnrollAdditionalFingerprint) {
                                EnrollAdditionalFingerprint enrollAdditionalFingerprint =
                                        (EnrollAdditionalFingerprint) nextStepViewModel;
                                int i3 = enrollAdditionalFingerprint.userId;
                                FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment2 =
                                        (FingerprintSettingsV2Fragment) fingerprintView2;
                                FingerprintSettingsNavigationViewModel
                                        fingerprintSettingsNavigationViewModel2 =
                                                fingerprintSettingsV2Fragment2.navigationViewModel;
                                if (fingerprintSettingsNavigationViewModel2 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException(
                                            "navigationViewModel");
                                    throw null;
                                }
                                fingerprintSettingsNavigationViewModel2.setStepToLaunched();
                                Intent intent2 = new Intent();
                                intent2.setClassName(
                                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                        String.valueOf(
                                                Reflection.factory
                                                        .getOrCreateKotlinClass(
                                                                FingerprintEnrollEnrolling.class)
                                                        .getQualifiedName()));
                                intent2.putExtra("android.intent.extra.USER_ID", i3);
                                intent2.putExtra(
                                        "hw_auth_token",
                                        enrollAdditionalFingerprint.challengeToken);
                                fingerprintSettingsV2Fragment2.launchAdditionalFingerprintListener
                                        .launch(intent2);
                            } else if (nextStepViewModel instanceof LaunchConfirmDeviceCredential) {
                                ((FingerprintSettingsV2Fragment) fingerprintView2)
                                        .launchConfirmOrChooseLock(
                                                ((LaunchConfirmDeviceCredential) nextStepViewModel)
                                                        .userId);
                            } else if (nextStepViewModel instanceof FinishSettings) {
                                Log.d(
                                        "FingerprintSettingsViewBinder",
                                        "Finishing due to "
                                                + ((FinishSettings) nextStepViewModel).reason);
                                fingerprintView2.finish();
                            } else if (nextStepViewModel instanceof FinishSettingsWithResult) {
                                FinishSettingsWithResult finishSettingsWithResult =
                                        (FinishSettingsWithResult) nextStepViewModel;
                                MainClearConfirm$$ExternalSyntheticOutline0.m(
                                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                finishSettingsWithResult.result,
                                                "Finishing with result ",
                                                " due to "),
                                        finishSettingsWithResult.reason,
                                        "FingerprintSettingsViewBinder");
                                ((FingerprintSettingsV2Fragment) fingerprintView2)
                                        .setResult(finishSettingsWithResult.result);
                                fingerprintView2.finish();
                            } else if (nextStepViewModel instanceof ShowSettings) {
                                Log.d("FingerprintSettingsViewBinder", "Showing settings");
                            } else if (nextStepViewModel instanceof LaunchedActivity) {
                                Log.d(
                                        "FingerprintSettingsViewBinder",
                                        "Launched activity, awaiting result");
                            }
                            return Unit.INSTANCE;
                        }
                    };
            this.label = 1;
            if (filterNotNull.collect(flowCollector, this) == coroutineSingletons) {
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
