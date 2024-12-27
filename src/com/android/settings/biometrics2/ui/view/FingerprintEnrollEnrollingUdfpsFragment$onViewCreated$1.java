package com.android.settings.biometrics2.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.RepeatOnLifecycleKt;

import com.android.settings.R;
import com.android.settings.biometrics2.ui.model.EnrollmentProgress;
import com.android.settings.biometrics2.ui.model.EnrollmentStatusMessage;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollEnrollingViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollErrorDialogViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollProgressViewModel;
import com.android.settings.biometrics2.ui.widget.UdfpsEnrollView;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

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
final class FingerprintEnrollEnrollingUdfpsFragment$onViewCreated$1 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ FingerprintEnrollEnrollingUdfpsFragment this$0;

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
    /* renamed from: com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$onViewCreated$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ FingerprintEnrollEnrollingUdfpsFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                FingerprintEnrollEnrollingUdfpsFragment fingerprintEnrollEnrollingUdfpsFragment,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = fingerprintEnrollEnrollingUdfpsFragment;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
            return CoroutineSingletons.COROUTINE_SUSPENDED;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FingerprintEnrollErrorDialogViewModel fingerprintEnrollErrorDialogViewModel =
                        this.this$0._errorDialogViewModel;
                Intrinsics.checkNotNull(fingerprintEnrollErrorDialogViewModel);
                ReadonlySharedFlow asSharedFlow =
                        FlowKt.asSharedFlow(
                                fingerprintEnrollErrorDialogViewModel._triggerRetryFlow);
                final FingerprintEnrollEnrollingUdfpsFragment
                        fingerprintEnrollEnrollingUdfpsFragment = this.this$0;
                FlowCollector flowCollector =
                        new FlowCollector() { // from class:
                                              // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment.onViewCreated.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                FingerprintEnrollEnrollingUdfpsFragment
                                        fingerprintEnrollEnrollingUdfpsFragment2 =
                                                FingerprintEnrollEnrollingUdfpsFragment.this;
                                RelativeLayout relativeLayout =
                                        fingerprintEnrollEnrollingUdfpsFragment2.enrollingView;
                                Intrinsics.checkNotNull(relativeLayout);
                                View inflate =
                                        LayoutInflater.from(
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                .requireActivity())
                                                .inflate(
                                                        R.layout
                                                                .udfps_enroll_enrolling_v2_udfps_view,
                                                        (ViewGroup) null);
                                int indexOfChild =
                                        relativeLayout.indexOfChild(
                                                fingerprintEnrollEnrollingUdfpsFragment2
                                                        .getUdfpsEnrollView());
                                ViewGroup.LayoutParams layoutParams =
                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                .getUdfpsEnrollView()
                                                .getLayoutParams();
                                relativeLayout.removeView(
                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                .getUdfpsEnrollView());
                                relativeLayout.addView(inflate, indexOfChild, layoutParams);
                                UdfpsEnrollView udfpsEnrollView =
                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                .getUdfpsEnrollView();
                                FingerprintEnrollEnrollingViewModel
                                        fingerprintEnrollEnrollingViewModel =
                                                fingerprintEnrollEnrollingUdfpsFragment2
                                                        ._enrollingViewModel;
                                Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                                udfpsEnrollView.setSensorProperties(
                                        fingerprintEnrollEnrollingViewModel.mFingerprintRepository
                                                .getFirstFingerprintSensorPropertiesInternal());
                                fingerprintEnrollEnrollingUdfpsFragment2.haveShownTipLottie = false;
                                fingerprintEnrollEnrollingUdfpsFragment2.haveShownLeftEdgeLottie =
                                        false;
                                fingerprintEnrollEnrollingUdfpsFragment2.haveShownRightEdgeLottie =
                                        false;
                                fingerprintEnrollEnrollingUdfpsFragment2.haveShownCenterLottie =
                                        false;
                                fingerprintEnrollEnrollingUdfpsFragment2.haveShownGuideLottie =
                                        false;
                                LottieAnimationView lottieAnimationView =
                                        fingerprintEnrollEnrollingUdfpsFragment2.illustrationLottie;
                                if (lottieAnimationView != null) {
                                    lottieAnimationView.setContentDescription(
                                            ApnSettings.MVNO_NONE);
                                    lottieAnimationView.setVisibility(8);
                                }
                                fingerprintEnrollEnrollingUdfpsFragment2.startEnrollment$3();
                                FingerprintEnrollProgressViewModel
                                        fingerprintEnrollProgressViewModel =
                                                fingerprintEnrollEnrollingUdfpsFragment2
                                                        ._progressViewModel;
                                Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                Object value =
                                        fingerprintEnrollProgressViewModel.mProgressLiveData
                                                .getValue();
                                Intrinsics.checkNotNull(value);
                                fingerprintEnrollEnrollingUdfpsFragment2.updateProgress$2(
                                        false, (EnrollmentProgress) value);
                                FingerprintEnrollProgressViewModel
                                        fingerprintEnrollProgressViewModel2 =
                                                fingerprintEnrollEnrollingUdfpsFragment2
                                                        ._progressViewModel;
                                Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel2);
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage)
                                                fingerprintEnrollProgressViewModel2
                                                        .mHelpMessageLiveData.getValue();
                                if (enrollmentStatusMessage != null) {
                                    fingerprintEnrollEnrollingUdfpsFragment2.onEnrollmentHelp$2(
                                            enrollmentStatusMessage);
                                } else {
                                    fingerprintEnrollEnrollingUdfpsFragment2
                                            .updateTitleAndDescription$2();
                                }
                                return Unit.INSTANCE;
                            }
                        };
                this.label = 1;
                if (asSharedFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintEnrollEnrollingUdfpsFragment$onViewCreated$1(
            FingerprintEnrollEnrollingUdfpsFragment fingerprintEnrollEnrollingUdfpsFragment,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintEnrollEnrollingUdfpsFragment;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintEnrollEnrollingUdfpsFragment$onViewCreated$1(
                this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintEnrollEnrollingUdfpsFragment$onViewCreated$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FingerprintEnrollEnrollingUdfpsFragment fingerprintEnrollEnrollingUdfpsFragment =
                    this.this$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(fingerprintEnrollEnrollingUdfpsFragment, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(
                            fingerprintEnrollEnrollingUdfpsFragment, state, anonymousClass1, this)
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
