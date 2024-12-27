package com.android.settings.biometrics2.ui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.biometrics2.ui.model.EnrollmentProgress;
import com.android.settings.biometrics2.ui.model.EnrollmentStatusMessage;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollEnrollingViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollErrorDialogViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollProgressViewModel;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.template.DescriptionMixin;
import com.google.android.setupdesign.template.HeaderMixin;
import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;

import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollEnrollingSfpsFragment;",
            "Landroidx/fragment/app/Fragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollEnrollingSfpsFragment extends Fragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public FingerprintEnrollEnrollingViewModel _enrollingViewModel;
    public FingerprintEnrollErrorDialogViewModel _errorDialogViewModel;
    public FingerprintEnrollProgressViewModel _progressViewModel;
    public final FingerprintEnrollEnrollingSfpsFragment$progressObserver$1 canceledSignalObserver;
    public Object enrollingCancelSignal;
    public GlifLayout enrollingView;
    public final FingerprintEnrollEnrollingSfpsFragment$progressObserver$1 errorMessageObserver;
    public boolean haveShownSfpsCenterLottie;
    public boolean haveShownSfpsLeftEdgeLottie;
    public boolean haveShownSfpsNoAnimationLottie;
    public boolean haveShownSfpsRightEdgeLottie;
    public boolean haveShownSfpsTipLottie;
    public ObjectAnimator helpAnimation;
    public final FingerprintEnrollEnrollingSfpsFragment$progressObserver$1 helpMessageObserver;
    public int iconTouchCount;
    public ObjectAnimator progressAnim;
    public final FingerprintEnrollEnrollingSfpsFragment$progressObserver$1 progressObserver;
    public final FingerprintEnrollEnrollingSfpsFragment$progressAnimationListener$1
            progressAnimationListener =
                    new Animator
                            .AnimatorListener() { // from class:
                                                  // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$progressAnimationListener$1
                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animation) {
                            Intrinsics.checkNotNullParameter(animation, "animation");
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animation) {
                            Intrinsics.checkNotNullParameter(animation, "animation");
                            FingerprintEnrollEnrollingSfpsFragment
                                    fingerprintEnrollEnrollingSfpsFragment =
                                            FingerprintEnrollEnrollingSfpsFragment.this;
                            int i = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                            if (fingerprintEnrollEnrollingSfpsFragment
                                            .getProgressBar$2()
                                            .getProgress()
                                    >= 10000) {
                                FingerprintEnrollEnrollingSfpsFragment.this
                                        .getProgressBar$2()
                                        .postDelayed(
                                                FingerprintEnrollEnrollingSfpsFragment.this
                                                        .delayedFinishRunnable,
                                                250L);
                            }
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationRepeat(Animator animation) {
                            Intrinsics.checkNotNullParameter(animation, "animation");
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animation) {
                            Intrinsics.checkNotNullParameter(animation, "animation");
                        }
                    };
    public final FingerprintEnrollEnrollingSfpsFragment$delayedFinishRunnable$1
            showIconTouchDialogRunnable =
                    new FingerprintEnrollEnrollingSfpsFragment$delayedFinishRunnable$1(this, 1);
    public final FingerprintEnrollEnrollingSfpsFragment$delayedFinishRunnable$1
            delayedFinishRunnable =
                    new FingerprintEnrollEnrollingSfpsFragment$delayedFinishRunnable$1(this, 0);
    public final FingerprintEnrollEnrollingSfpsFragment$onSkipClickListener$1 onSkipClickListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$onSkipClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                            FingerprintEnrollEnrollingSfpsFragment.this._enrollingViewModel;
                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                    fingerprintEnrollEnrollingViewModel.mOnSkipPressed = true;
                    FingerprintEnrollEnrollingSfpsFragment.this.cancelEnrollment$1(true);
                }
            };
    public final FingerprintEnrollEnrollingSfpsFragment$onBackPressedCallback$1
            onBackPressedCallback =
                    new OnBackPressedCallback() { // from class:
                                                  // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$onBackPressedCallback$1
                        {
                            super(true);
                        }

                        @Override // androidx.activity.OnBackPressedCallback
                        public final void handleOnBackPressed() {
                            setEnabled(false);
                            FingerprintEnrollEnrollingSfpsFragment
                                    fingerprintEnrollEnrollingSfpsFragment =
                                            FingerprintEnrollEnrollingSfpsFragment.this;
                            FingerprintEnrollEnrollingViewModel
                                    fingerprintEnrollEnrollingViewModel =
                                            fingerprintEnrollEnrollingSfpsFragment
                                                    ._enrollingViewModel;
                            Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                            fingerprintEnrollEnrollingViewModel.mOnBackPressed = true;
                            fingerprintEnrollEnrollingSfpsFragment.cancelEnrollment$1(true);
                        }
                    };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$progressAnimationListener$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$onSkipClickListener$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$progressObserver$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$progressObserver$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$progressObserver$1] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$progressObserver$1] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$onBackPressedCallback$1] */
    public FingerprintEnrollEnrollingSfpsFragment() {
        final int i = 0;
        this.progressObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingSfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        int i2;
                        FingerprintEnrollEnrollingSfpsFragment
                                fingerprintEnrollEnrollingSfpsFragment = this.this$0;
                        switch (i) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null
                                        && (i2 = enrollmentProgress.steps) >= 0) {
                                    int i3 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                    fingerprintEnrollEnrollingSfpsFragment.updateProgress$1(
                                            true, enrollmentProgress);
                                    FingerprintEnrollEnrollingViewModel
                                            fingerprintEnrollEnrollingViewModel =
                                                    fingerprintEnrollEnrollingSfpsFragment
                                                            ._enrollingViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                                    if (fingerprintEnrollEnrollingViewModel.mAccessibilityManager
                                            .isEnabled()) {
                                        int i4 =
                                                (int)
                                                        (((i2 - enrollmentProgress.remaining) / i2)
                                                                * 100);
                                        String string =
                                                fingerprintEnrollEnrollingSfpsFragment.getString(
                                                        R.string
                                                                .security_settings_sfps_enroll_progress_a11y_message,
                                                        Integer.valueOf(i4));
                                        Intrinsics.checkNotNullExpressionValue(
                                                string, "getString(...)");
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel2 =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel2);
                                        fingerprintEnrollEnrollingViewModel2.sendAccessibilityEvent(
                                                string);
                                        fingerprintEnrollEnrollingSfpsFragment
                                                .getIllustrationLottie()
                                                .setContentDescription(
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                .getString(
                                                                        R.string
                                                                                .security_settings_sfps_animation_a11y_label,
                                                                        Integer.valueOf(i4)));
                                    }
                                    fingerprintEnrollEnrollingSfpsFragment
                                            .updateTitleAndDescription$1();
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                int i5 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                Log.d(
                                        "FingerprintEnrollEnrollingSfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    fingerprintEnrollEnrollingSfpsFragment.cancelEnrollment$1(true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingSfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingSfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingSfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                int i6 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                Log.d(
                                        "FingerprintEnrollEnrollingSfpsFragment",
                                        "canceledSignalObserver(" + obj + ")");
                                if (obj != null) {
                                    Log.d(
                                            "FingerprintEnrollEnrollingSfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingSfpsFragment
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingSfpsFragment.enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel3 =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel3);
                                        if (!fingerprintEnrollEnrollingViewModel3.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel4 =
                                                            fingerprintEnrollEnrollingSfpsFragment
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel4);
                                            if (fingerprintEnrollEnrollingViewModel4
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel5 =
                                                                fingerprintEnrollEnrollingSfpsFragment
                                                                        ._enrollingViewModel;
                                                Intrinsics.checkNotNull(
                                                        fingerprintEnrollEnrollingViewModel5);
                                                fingerprintEnrollEnrollingViewModel5
                                                                .mOnSkipPressed =
                                                        false;
                                                fingerprintEnrollEnrollingViewModel5.mActionLiveData
                                                        .postValue(2);
                                                break;
                                            }
                                        } else {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel6 =
                                                            fingerprintEnrollEnrollingSfpsFragment
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel6);
                                            fingerprintEnrollEnrollingViewModel6.mOnBackPressed =
                                                    false;
                                            fingerprintEnrollEnrollingViewModel6.mActionLiveData
                                                    .postValue(3);
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                EnrollmentStatusMessage enrollmentStatusMessage2 =
                                        (EnrollmentStatusMessage) obj;
                                if (enrollmentStatusMessage2 != null) {
                                    int i7 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                    fingerprintEnrollEnrollingSfpsFragment.onEnrollmentHelp$1(
                                            enrollmentStatusMessage2);
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 3;
        this.helpMessageObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingSfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        int i22;
                        FingerprintEnrollEnrollingSfpsFragment
                                fingerprintEnrollEnrollingSfpsFragment = this.this$0;
                        switch (i2) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null
                                        && (i22 = enrollmentProgress.steps) >= 0) {
                                    int i3 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                    fingerprintEnrollEnrollingSfpsFragment.updateProgress$1(
                                            true, enrollmentProgress);
                                    FingerprintEnrollEnrollingViewModel
                                            fingerprintEnrollEnrollingViewModel =
                                                    fingerprintEnrollEnrollingSfpsFragment
                                                            ._enrollingViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                                    if (fingerprintEnrollEnrollingViewModel.mAccessibilityManager
                                            .isEnabled()) {
                                        int i4 =
                                                (int)
                                                        (((i22 - enrollmentProgress.remaining)
                                                                        / i22)
                                                                * 100);
                                        String string =
                                                fingerprintEnrollEnrollingSfpsFragment.getString(
                                                        R.string
                                                                .security_settings_sfps_enroll_progress_a11y_message,
                                                        Integer.valueOf(i4));
                                        Intrinsics.checkNotNullExpressionValue(
                                                string, "getString(...)");
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel2 =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel2);
                                        fingerprintEnrollEnrollingViewModel2.sendAccessibilityEvent(
                                                string);
                                        fingerprintEnrollEnrollingSfpsFragment
                                                .getIllustrationLottie()
                                                .setContentDescription(
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                .getString(
                                                                        R.string
                                                                                .security_settings_sfps_animation_a11y_label,
                                                                        Integer.valueOf(i4)));
                                    }
                                    fingerprintEnrollEnrollingSfpsFragment
                                            .updateTitleAndDescription$1();
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                int i5 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                Log.d(
                                        "FingerprintEnrollEnrollingSfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    fingerprintEnrollEnrollingSfpsFragment.cancelEnrollment$1(true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingSfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingSfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingSfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                int i6 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                Log.d(
                                        "FingerprintEnrollEnrollingSfpsFragment",
                                        "canceledSignalObserver(" + obj + ")");
                                if (obj != null) {
                                    Log.d(
                                            "FingerprintEnrollEnrollingSfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingSfpsFragment
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingSfpsFragment.enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel3 =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel3);
                                        if (!fingerprintEnrollEnrollingViewModel3.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel4 =
                                                            fingerprintEnrollEnrollingSfpsFragment
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel4);
                                            if (fingerprintEnrollEnrollingViewModel4
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel5 =
                                                                fingerprintEnrollEnrollingSfpsFragment
                                                                        ._enrollingViewModel;
                                                Intrinsics.checkNotNull(
                                                        fingerprintEnrollEnrollingViewModel5);
                                                fingerprintEnrollEnrollingViewModel5
                                                                .mOnSkipPressed =
                                                        false;
                                                fingerprintEnrollEnrollingViewModel5.mActionLiveData
                                                        .postValue(2);
                                                break;
                                            }
                                        } else {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel6 =
                                                            fingerprintEnrollEnrollingSfpsFragment
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel6);
                                            fingerprintEnrollEnrollingViewModel6.mOnBackPressed =
                                                    false;
                                            fingerprintEnrollEnrollingViewModel6.mActionLiveData
                                                    .postValue(3);
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                EnrollmentStatusMessage enrollmentStatusMessage2 =
                                        (EnrollmentStatusMessage) obj;
                                if (enrollmentStatusMessage2 != null) {
                                    int i7 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                    fingerprintEnrollEnrollingSfpsFragment.onEnrollmentHelp$1(
                                            enrollmentStatusMessage2);
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i3 = 1;
        this.errorMessageObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingSfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        int i22;
                        FingerprintEnrollEnrollingSfpsFragment
                                fingerprintEnrollEnrollingSfpsFragment = this.this$0;
                        switch (i3) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null
                                        && (i22 = enrollmentProgress.steps) >= 0) {
                                    int i32 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                    fingerprintEnrollEnrollingSfpsFragment.updateProgress$1(
                                            true, enrollmentProgress);
                                    FingerprintEnrollEnrollingViewModel
                                            fingerprintEnrollEnrollingViewModel =
                                                    fingerprintEnrollEnrollingSfpsFragment
                                                            ._enrollingViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                                    if (fingerprintEnrollEnrollingViewModel.mAccessibilityManager
                                            .isEnabled()) {
                                        int i4 =
                                                (int)
                                                        (((i22 - enrollmentProgress.remaining)
                                                                        / i22)
                                                                * 100);
                                        String string =
                                                fingerprintEnrollEnrollingSfpsFragment.getString(
                                                        R.string
                                                                .security_settings_sfps_enroll_progress_a11y_message,
                                                        Integer.valueOf(i4));
                                        Intrinsics.checkNotNullExpressionValue(
                                                string, "getString(...)");
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel2 =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel2);
                                        fingerprintEnrollEnrollingViewModel2.sendAccessibilityEvent(
                                                string);
                                        fingerprintEnrollEnrollingSfpsFragment
                                                .getIllustrationLottie()
                                                .setContentDescription(
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                .getString(
                                                                        R.string
                                                                                .security_settings_sfps_animation_a11y_label,
                                                                        Integer.valueOf(i4)));
                                    }
                                    fingerprintEnrollEnrollingSfpsFragment
                                            .updateTitleAndDescription$1();
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                int i5 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                Log.d(
                                        "FingerprintEnrollEnrollingSfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    fingerprintEnrollEnrollingSfpsFragment.cancelEnrollment$1(true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingSfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingSfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingSfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                int i6 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                Log.d(
                                        "FingerprintEnrollEnrollingSfpsFragment",
                                        "canceledSignalObserver(" + obj + ")");
                                if (obj != null) {
                                    Log.d(
                                            "FingerprintEnrollEnrollingSfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingSfpsFragment
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingSfpsFragment.enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel3 =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel3);
                                        if (!fingerprintEnrollEnrollingViewModel3.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel4 =
                                                            fingerprintEnrollEnrollingSfpsFragment
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel4);
                                            if (fingerprintEnrollEnrollingViewModel4
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel5 =
                                                                fingerprintEnrollEnrollingSfpsFragment
                                                                        ._enrollingViewModel;
                                                Intrinsics.checkNotNull(
                                                        fingerprintEnrollEnrollingViewModel5);
                                                fingerprintEnrollEnrollingViewModel5
                                                                .mOnSkipPressed =
                                                        false;
                                                fingerprintEnrollEnrollingViewModel5.mActionLiveData
                                                        .postValue(2);
                                                break;
                                            }
                                        } else {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel6 =
                                                            fingerprintEnrollEnrollingSfpsFragment
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel6);
                                            fingerprintEnrollEnrollingViewModel6.mOnBackPressed =
                                                    false;
                                            fingerprintEnrollEnrollingViewModel6.mActionLiveData
                                                    .postValue(3);
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                EnrollmentStatusMessage enrollmentStatusMessage2 =
                                        (EnrollmentStatusMessage) obj;
                                if (enrollmentStatusMessage2 != null) {
                                    int i7 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                    fingerprintEnrollEnrollingSfpsFragment.onEnrollmentHelp$1(
                                            enrollmentStatusMessage2);
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i4 = 2;
        this.canceledSignalObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingSfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        int i22;
                        FingerprintEnrollEnrollingSfpsFragment
                                fingerprintEnrollEnrollingSfpsFragment = this.this$0;
                        switch (i4) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null
                                        && (i22 = enrollmentProgress.steps) >= 0) {
                                    int i32 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                    fingerprintEnrollEnrollingSfpsFragment.updateProgress$1(
                                            true, enrollmentProgress);
                                    FingerprintEnrollEnrollingViewModel
                                            fingerprintEnrollEnrollingViewModel =
                                                    fingerprintEnrollEnrollingSfpsFragment
                                                            ._enrollingViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                                    if (fingerprintEnrollEnrollingViewModel.mAccessibilityManager
                                            .isEnabled()) {
                                        int i42 =
                                                (int)
                                                        (((i22 - enrollmentProgress.remaining)
                                                                        / i22)
                                                                * 100);
                                        String string =
                                                fingerprintEnrollEnrollingSfpsFragment.getString(
                                                        R.string
                                                                .security_settings_sfps_enroll_progress_a11y_message,
                                                        Integer.valueOf(i42));
                                        Intrinsics.checkNotNullExpressionValue(
                                                string, "getString(...)");
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel2 =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel2);
                                        fingerprintEnrollEnrollingViewModel2.sendAccessibilityEvent(
                                                string);
                                        fingerprintEnrollEnrollingSfpsFragment
                                                .getIllustrationLottie()
                                                .setContentDescription(
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                .getString(
                                                                        R.string
                                                                                .security_settings_sfps_animation_a11y_label,
                                                                        Integer.valueOf(i42)));
                                    }
                                    fingerprintEnrollEnrollingSfpsFragment
                                            .updateTitleAndDescription$1();
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                int i5 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                Log.d(
                                        "FingerprintEnrollEnrollingSfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    fingerprintEnrollEnrollingSfpsFragment.cancelEnrollment$1(true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingSfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingSfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingSfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                int i6 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                Log.d(
                                        "FingerprintEnrollEnrollingSfpsFragment",
                                        "canceledSignalObserver(" + obj + ")");
                                if (obj != null) {
                                    Log.d(
                                            "FingerprintEnrollEnrollingSfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingSfpsFragment
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingSfpsFragment.enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel3 =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel3);
                                        if (!fingerprintEnrollEnrollingViewModel3.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel4 =
                                                            fingerprintEnrollEnrollingSfpsFragment
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel4);
                                            if (fingerprintEnrollEnrollingViewModel4
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel5 =
                                                                fingerprintEnrollEnrollingSfpsFragment
                                                                        ._enrollingViewModel;
                                                Intrinsics.checkNotNull(
                                                        fingerprintEnrollEnrollingViewModel5);
                                                fingerprintEnrollEnrollingViewModel5
                                                                .mOnSkipPressed =
                                                        false;
                                                fingerprintEnrollEnrollingViewModel5.mActionLiveData
                                                        .postValue(2);
                                                break;
                                            }
                                        } else {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel6 =
                                                            fingerprintEnrollEnrollingSfpsFragment
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel6);
                                            fingerprintEnrollEnrollingViewModel6.mOnBackPressed =
                                                    false;
                                            fingerprintEnrollEnrollingViewModel6.mActionLiveData
                                                    .postValue(3);
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                EnrollmentStatusMessage enrollmentStatusMessage2 =
                                        (EnrollmentStatusMessage) obj;
                                if (enrollmentStatusMessage2 != null) {
                                    int i7 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                    fingerprintEnrollEnrollingSfpsFragment.onEnrollmentHelp$1(
                                            enrollmentStatusMessage2);
                                    break;
                                }
                                break;
                        }
                    }
                };
    }

    public final void applySfpsErrorDynamicColors(boolean z) {
        ProgressBar progressBar$2 = getProgressBar$2();
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        progressBar$2.setProgressTintList(
                ColorStateList.valueOf(
                        requireContext.getColor(
                                z
                                        ? R.color.sfps_enrollment_progress_bar_error_color
                                        : R.color.sfps_enrollment_progress_bar_fill_color)));
        progressBar$2.setProgressTintMode(PorterDuff.Mode.SRC);
        progressBar$2.invalidate();
        LottieAnimationView illustrationLottie = getIllustrationLottie();
        Context requireContext2 = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext2, "requireContext(...)");
        FingerprintEnrollEnrollingSfpsFragmentKt.applyLottieDynamicColor(
                illustrationLottie, requireContext2, z);
    }

    public final void cancelEnrollment$1(boolean z) {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        if (!fingerprintEnrollProgressViewModel.isEnrolling()) {
            Log.d(
                    "FingerprintEnrollEnrollingSfpsFragment",
                    "cancelEnrollment(), failed because isEnrolling is false");
            return;
        }
        removeEnrollmentObservers$1();
        if (z) {
            FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel2 =
                    this._progressViewModel;
            Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel2);
            fingerprintEnrollProgressViewModel2.mCanceledSignalLiveData.observe(
                    this, this.canceledSignalObserver);
        } else {
            this.enrollingCancelSignal = null;
        }
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel3 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel3);
        if (fingerprintEnrollProgressViewModel3.cancelEnrollment()) {
            return;
        }
        Log.e(
                "FingerprintEnrollEnrollingSfpsFragment",
                "cancelEnrollment(), failed to cancel enrollment");
    }

    public final void configureEnrollmentStage(int i, CharSequence charSequence) {
        Intrinsics.checkNotNullExpressionValue(requireActivity(), "requireActivity(...)");
        GlifLayout glifLayout = this.enrollingView;
        Intrinsics.checkNotNull(glifLayout);
        if (!TextUtils.equals(glifLayout.getDescriptionText(), charSequence)) {
            glifLayout.setDescriptionText(charSequence);
        }
        FragmentActivity activity = getActivity();
        LottieCompositionFactory.fromRawRes(
                        activity, i, LottieCompositionFactory.rawResCacheKey(activity, i))
                .addListener(
                        new LottieListener() { // from class:
                                               // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$configureEnrollmentStage$1
                            @Override // com.airbnb.lottie.LottieListener
                            public final void onResult(Object obj) {
                                LottieComposition c = (LottieComposition) obj;
                                Intrinsics.checkNotNullParameter(c, "c");
                                int i2 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                FingerprintEnrollEnrollingSfpsFragment
                                        fingerprintEnrollEnrollingSfpsFragment =
                                                FingerprintEnrollEnrollingSfpsFragment.this;
                                fingerprintEnrollEnrollingSfpsFragment
                                        .getIllustrationLottie()
                                        .setComposition(c);
                                fingerprintEnrollEnrollingSfpsFragment
                                        .getIllustrationLottie()
                                        .setVisibility(0);
                                fingerprintEnrollEnrollingSfpsFragment
                                        .getIllustrationLottie()
                                        .playAnimation$1();
                            }
                        });
    }

    public final LottieAnimationView getIllustrationLottie() {
        GlifLayout glifLayout = this.enrollingView;
        Intrinsics.checkNotNull(glifLayout);
        View findViewById = glifLayout.findViewById(R.id.illustration_lottie);
        Intrinsics.checkNotNull(findViewById);
        return (LottieAnimationView) findViewById;
    }

    public final ProgressBar getProgressBar$2() {
        GlifLayout glifLayout = this.enrollingView;
        Intrinsics.checkNotNull(glifLayout);
        View findViewById = glifLayout.findViewById(R.id.fingerprint_progress_bar);
        Intrinsics.checkNotNull(findViewById);
        return (ProgressBar) findViewById;
    }

    public final int getStageThresholdSteps$1(int i) {
        int i2;
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        EnrollmentProgress enrollmentProgress =
                (EnrollmentProgress)
                        fingerprintEnrollProgressViewModel.mProgressLiveData.getValue();
        if (enrollmentProgress == null || (i2 = enrollmentProgress.steps) == -1) {
            Log.w(
                    "FingerprintEnrollEnrollingSfpsFragment",
                    "getStageThresholdSteps: Enrollment not started yet");
            return 1;
        }
        FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                this._enrollingViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
        return MathKt.roundToInt(
                fingerprintEnrollEnrollingViewModel.mFingerprintRepository.mFingerprintManager
                                .getEnrollStageThreshold(i)
                        * i2);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity);
        this._enrollingViewModel =
                (FingerprintEnrollEnrollingViewModel)
                        viewModelProvider.get(
                                JvmClassMappingKt.getKotlinClass(
                                        FingerprintEnrollEnrollingViewModel.class));
        this._progressViewModel =
                (FingerprintEnrollProgressViewModel)
                        viewModelProvider.get(
                                JvmClassMappingKt.getKotlinClass(
                                        FingerprintEnrollProgressViewModel.class));
        this._errorDialogViewModel =
                (FingerprintEnrollErrorDialogViewModel)
                        viewModelProvider.get(
                                JvmClassMappingKt.getKotlinClass(
                                        FingerprintEnrollErrorDialogViewModel.class));
        super.onAttach(context);
        requireActivity().getOnBackPressedDispatcher().addCallback(this.onBackPressedCallback);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View inflate = inflater.inflate(R.layout.sfps_enroll_enrolling, viewGroup, false);
        Intrinsics.checkNotNull(
                inflate,
                "null cannot be cast to non-null type com.google.android.setupdesign.GlifLayout");
        GlifLayout glifLayout = (GlifLayout) inflate;
        this.enrollingView = glifLayout;
        return glifLayout;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDetach() {
        setEnabled(false);
        super.onDetach();
    }

    public final void onEnrollmentHelp$1(EnrollmentStatusMessage enrollmentStatusMessage) {
        Log.d(
                "FingerprintEnrollEnrollingSfpsFragment",
                "onEnrollmentHelp(" + enrollmentStatusMessage + ")");
        CharSequence charSequence = enrollmentStatusMessage.str;
        if (charSequence.length() > 0) {
            GlifLayout glifLayout = this.enrollingView;
            Intrinsics.checkNotNull(glifLayout);
            glifLayout.setHeaderText(charSequence);
            glifLayout.getHeaderTextView().setContentDescription(charSequence);
            Intrinsics.checkNotNullExpressionValue(requireActivity(), "requireActivity(...)");
            if (!TextUtils.equals(glifLayout.getDescriptionText(), ApnSettings.MVNO_NONE)) {
                glifLayout.setDescriptionText(ApnSettings.MVNO_NONE);
            }
            if (isResumed()) {
                ObjectAnimator objectAnimator = this.helpAnimation;
                Intrinsics.checkNotNull(objectAnimator);
                if (!objectAnimator.isRunning()) {
                    ObjectAnimator objectAnimator2 = this.helpAnimation;
                    Intrinsics.checkNotNull(objectAnimator2);
                    objectAnimator2.start();
                }
            }
            applySfpsErrorDynamicColors(true);
            if (isResumed()) {
                FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                        this._enrollingViewModel;
                Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                if (fingerprintEnrollEnrollingViewModel.mAccessibilityManager.isEnabled()) {
                    FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel2 =
                            this._enrollingViewModel;
                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel2);
                    fingerprintEnrollEnrollingViewModel2.mVibrator.vibrate(
                            fingerprintEnrollEnrollingViewModel2.mUserId,
                            fingerprintEnrollEnrollingViewModel2
                                    .getApplication()
                                    .getOpPackageName(),
                            FingerprintEnrollEnrollingViewModel.VIBRATE_EFFECT_ERROR,
                            "FingerprintEnrollEnrollingSfpsFragment::showError",
                            FingerprintEnrollEnrollingViewModel
                                    .FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES);
                }
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        boolean isEnrolling = fingerprintEnrollProgressViewModel.isEnrolling();
        FingerprintEnrollErrorDialogViewModel fingerprintEnrollErrorDialogViewModel =
                this._errorDialogViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollErrorDialogViewModel);
        boolean value = fingerprintEnrollErrorDialogViewModel._isDialogShown.getValue();
        Utils$$ExternalSyntheticOutline0.m653m(
                "onStart(), isEnrolling:",
                isEnrolling,
                ", isErrorDialog:",
                value,
                "FingerprintEnrollEnrollingSfpsFragment");
        if (!value) {
            startEnrollment$2();
        }
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel2 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel2);
        Object value2 = fingerprintEnrollProgressViewModel2.mProgressLiveData.getValue();
        Intrinsics.checkNotNull(value2);
        updateProgress$1(false, (EnrollmentProgress) value2);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel3 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel3);
        EnrollmentStatusMessage enrollmentStatusMessage =
                (EnrollmentStatusMessage)
                        fingerprintEnrollProgressViewModel3.mHelpMessageLiveData.getValue();
        if (enrollmentStatusMessage != null) {
            onEnrollmentHelp$1(enrollmentStatusMessage);
        } else {
            applySfpsErrorDynamicColors(false);
            updateTitleAndDescription$1();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStop() {
        removeEnrollmentObservers$1();
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        boolean isEnrolling = fingerprintEnrollProgressViewModel.isEnrolling();
        boolean isChangingConfigurations = requireActivity().isChangingConfigurations();
        Utils$$ExternalSyntheticOutline0.m653m(
                "onStop(), enrolling:",
                isEnrolling,
                " isConfigChange:",
                isChangingConfigurations,
                "FingerprintEnrollEnrollingSfpsFragment");
        if (isEnrolling && !isChangingConfigurations) {
            cancelEnrollment$1(false);
        }
        super.onStop();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        GlifLayout glifLayout = this.enrollingView;
        Intrinsics.checkNotNull(glifLayout);
        FingerprintEnrollEnrollingSfpsFragment$onSkipClickListener$1 onSkipClickListener =
                this.onSkipClickListener;
        Intrinsics.checkNotNullParameter(onSkipClickListener, "onSkipClickListener");
        String string =
                requireActivity.getString(
                        R.string.security_settings_fingerprint_enroll_start_message);
        if (!TextUtils.equals(glifLayout.getDescriptionText(), string)) {
            glifLayout.setDescriptionText(string);
        }
        ((FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class))
                .setSecondaryButton(
                        new FooterButton(
                                requireActivity.getString(
                                        R.string
                                                .security_settings_fingerprint_enroll_enrolling_skip),
                                onSkipClickListener,
                                7,
                                2132083806),
                        false);
        View findViewById = glifLayout.findViewById(R.id.fingerprint_progress_bar);
        Intrinsics.checkNotNull(findViewById);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC;
        ((ProgressBar) findViewById).setProgressBackgroundTintMode(mode);
        View findViewById2 = glifLayout.findViewById(R.id.fingerprint_progress_bar);
        Intrinsics.checkNotNull(findViewById2);
        ProgressBar progressBar = (ProgressBar) findViewById2;
        progressBar.setProgressTintList(
                ColorStateList.valueOf(
                        requireActivity.getColor(R.color.sfps_enrollment_progress_bar_fill_color)));
        progressBar.setProgressTintMode(mode);
        progressBar.invalidate();
        View findViewById3 = glifLayout.findViewById(R.id.illustration_lottie);
        Intrinsics.checkNotNull(findViewById3);
        FingerprintEnrollEnrollingSfpsFragmentKt.applyLottieDynamicColor(
                (LottieAnimationView) findViewById3, requireActivity, false);
        int i = requireActivity.getResources().getConfiguration().orientation;
        Mixin mixin = glifLayout.getMixin(HeaderMixin.class);
        Intrinsics.checkNotNullExpressionValue(mixin, "getMixin(...)");
        HeaderMixin headerMixin = (HeaderMixin) mixin;
        Mixin mixin2 = glifLayout.getMixin(DescriptionMixin.class);
        Intrinsics.checkNotNullExpressionValue(mixin2, "getMixin(...)");
        DescriptionMixin descriptionMixin = (DescriptionMixin) mixin2;
        boolean z = i == 2;
        headerMixin.setAutoTextSizeEnabled(z);
        if (z) {
            headerMixin.getTextView().setMinLines(0);
            headerMixin.getTextView().setMaxLines(10);
            descriptionMixin.getTextView().setMinLines(0);
            descriptionMixin.getTextView().setMaxLines(10);
        } else {
            headerMixin.getTextView().setLines(4);
            descriptionMixin.getTextView().setLines(0);
        }
        GlifLayout glifLayout2 = this.enrollingView;
        Intrinsics.checkNotNull(glifLayout2);
        View findViewById4 = glifLayout2.findViewById(R.id.progress_lottie);
        Intrinsics.checkNotNull(findViewById4);
        ObjectAnimator ofFloat =
                ObjectAnimator.ofFloat(
                        findViewById4, "translationX", 0.0f, 40.0f, -40.0f, 40.0f, 0.0f);
        ofFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        ofFloat.setDuration(550L);
        ofFloat.setAutoCancel(false);
        this.helpAnimation = ofFloat;
        getProgressBar$2()
                .setOnTouchListener(
                        new View
                                .OnTouchListener() { // from class:
                                                     // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingSfpsFragment$onViewCreated$2
                            @Override // android.view.View.OnTouchListener
                            public final boolean onTouch(View view2, MotionEvent event) {
                                Intrinsics.checkNotNullParameter(event, "event");
                                if (event.getActionMasked() == 0) {
                                    FingerprintEnrollEnrollingSfpsFragment
                                            fingerprintEnrollEnrollingSfpsFragment =
                                                    FingerprintEnrollEnrollingSfpsFragment.this;
                                    int i2 =
                                            fingerprintEnrollEnrollingSfpsFragment.iconTouchCount
                                                    + 1;
                                    fingerprintEnrollEnrollingSfpsFragment.iconTouchCount = i2;
                                    if (i2 == 3) {
                                        fingerprintEnrollEnrollingSfpsFragment.iconTouchCount = 0;
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingSfpsFragment
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        fingerprintEnrollEnrollingViewModel.mActionLiveData
                                                .postValue(1);
                                    } else {
                                        fingerprintEnrollEnrollingSfpsFragment
                                                .getProgressBar$2()
                                                .postDelayed(
                                                        FingerprintEnrollEnrollingSfpsFragment.this
                                                                .showIconTouchDialogRunnable,
                                                        500L);
                                    }
                                } else if (event.getActionMasked() == 3
                                        || event.getActionMasked() == 1) {
                                    FingerprintEnrollEnrollingSfpsFragment
                                            fingerprintEnrollEnrollingSfpsFragment2 =
                                                    FingerprintEnrollEnrollingSfpsFragment.this;
                                    int i3 = FingerprintEnrollEnrollingSfpsFragment.$r8$clinit;
                                    fingerprintEnrollEnrollingSfpsFragment2
                                            .getProgressBar$2()
                                            .removeCallbacks(
                                                    FingerprintEnrollEnrollingSfpsFragment.this
                                                            .showIconTouchDialogRunnable);
                                }
                                return true;
                            }
                        });
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(this),
                null,
                null,
                new FingerprintEnrollEnrollingSfpsFragment$onViewCreated$3(this, null),
                3);
    }

    public final void removeEnrollmentObservers$1() {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        fingerprintEnrollProgressViewModel.mErrorMessageLiveData.removeObserver(
                this.errorMessageObserver);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel2 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel2);
        fingerprintEnrollProgressViewModel2.mProgressLiveData.removeObserver(this.progressObserver);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel3 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel3);
        fingerprintEnrollProgressViewModel3.mHelpMessageLiveData.removeObserver(
                this.helpMessageObserver);
    }

    public final void startEnrollment$2() {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        Object startEnrollment = fingerprintEnrollProgressViewModel.startEnrollment(2);
        this.enrollingCancelSignal = startEnrollment;
        if (startEnrollment == null) {
            Log.e("FingerprintEnrollEnrollingSfpsFragment", "startEnrollment(), failed");
        } else {
            Log.d("FingerprintEnrollEnrollingSfpsFragment", "startEnrollment(), success");
        }
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel2 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel2);
        fingerprintEnrollProgressViewModel2.mProgressLiveData.observe(this, this.progressObserver);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel3 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel3);
        fingerprintEnrollProgressViewModel3.mHelpMessageLiveData.observe(
                this, this.helpMessageObserver);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel4 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel4);
        fingerprintEnrollProgressViewModel4.mErrorMessageLiveData.observe(
                this, this.errorMessageObserver);
    }

    public final void updateProgress$1(boolean z, EnrollmentProgress enrollmentProgress) {
        int i;
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        if (!fingerprintEnrollProgressViewModel.isEnrolling()) {
            Log.d("FingerprintEnrollEnrollingSfpsFragment", "Enrollment not started yet");
            return;
        }
        int i2 = enrollmentProgress.steps;
        if (i2 == -1) {
            i = 0;
        } else {
            int i3 = i2 + 1;
            int i4 = i3 - enrollmentProgress.remaining;
            if (i4 <= 0) {
                i4 = 0;
            }
            i = (i4 * EnterpriseContainerConstants.SYSTEM_SIGNED_APP) / i3;
        }
        Log.d(
                "FingerprintEnrollEnrollingSfpsFragment",
                "updateProgress("
                        + z
                        + ", "
                        + enrollmentProgress
                        + "), old:"
                        + getProgressBar$2().getProgress()
                        + ", new:"
                        + i);
        if (getProgressBar$2().getProgress() < i) {
            applySfpsErrorDynamicColors(false);
        }
        if (!z) {
            getProgressBar$2().setProgress(i);
            if (i >= 10000) {
                this.delayedFinishRunnable.run();
                return;
            }
            return;
        }
        ObjectAnimator objectAnimator = this.progressAnim;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator ofInt =
                ObjectAnimator.ofInt(
                        getProgressBar$2(), "progress", getProgressBar$2().getProgress(), i);
        ofInt.addListener(this.progressAnimationListener);
        Interpolator loadInterpolator =
                AnimationUtils.loadInterpolator(getActivity(), R.interpolator.fast_out_slow_in);
        Intrinsics.checkNotNullExpressionValue(loadInterpolator, "loadInterpolator(...)");
        ofInt.setInterpolator(loadInterpolator);
        ofInt.setDuration(250L);
        ofInt.start();
        this.progressAnim = ofInt;
    }

    public final void updateTitleAndDescription$1() {
        char c;
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        GlifLayout glifLayout = this.enrollingView;
        Intrinsics.checkNotNull(glifLayout);
        GlifLayoutHelper glifLayoutHelper = new GlifLayoutHelper(requireActivity, glifLayout);
        FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                this._enrollingViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
        if (fingerprintEnrollEnrollingViewModel.mAccessibilityManager.isEnabled()) {
            FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel2 =
                    this._enrollingViewModel;
            Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel2);
            fingerprintEnrollEnrollingViewModel2.mAccessibilityManager.interrupt();
            glifLayout.getDescriptionTextView().setAccessibilityLiveRegion(1);
        }
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        EnrollmentProgress enrollmentProgress =
                (EnrollmentProgress)
                        fingerprintEnrollProgressViewModel.mProgressLiveData.getValue();
        if (enrollmentProgress == null) {
            c = 65535;
        } else {
            int i = enrollmentProgress.steps - enrollmentProgress.remaining;
            c =
                    i < getStageThresholdSteps$1(0)
                            ? (char) 0
                            : i < getStageThresholdSteps$1(1)
                                    ? (char) 1
                                    : i < getStageThresholdSteps$1(2)
                                            ? (char) 2
                                            : i < getStageThresholdSteps$1(3) ? (char) 3 : (char) 4;
        }
        if (c == 65535) {
            glifLayoutHelper.setHeaderText(
                    R.string.security_settings_sfps_enroll_find_sensor_title);
            glifLayoutHelper.setDescriptionText(
                    getString(R.string.security_settings_sfps_enroll_start_message));
            String string = getString(R.string.security_settings_sfps_enroll_find_sensor_message);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            glifLayout.getHeaderTextView().setContentDescription(string);
            requireActivity.setTitle(string);
            return;
        }
        if (c == 0) {
            glifLayoutHelper.setHeaderText(
                    R.string.security_settings_fingerprint_enroll_repeat_title);
            if (this.haveShownSfpsNoAnimationLottie) {
                return;
            }
            this.haveShownSfpsNoAnimationLottie = true;
            getIllustrationLottie()
                    .setContentDescription(
                            getString(R.string.security_settings_sfps_animation_a11y_label, 0));
            String string2 = getString(R.string.security_settings_sfps_enroll_start_message);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            configureEnrollmentStage(R.raw.sfps_lottie_no_animation, string2);
            return;
        }
        if (c == 1) {
            glifLayoutHelper.setHeaderText(
                    R.string.security_settings_sfps_enroll_finger_center_title);
            if (this.haveShownSfpsCenterLottie) {
                return;
            }
            this.haveShownSfpsCenterLottie = true;
            String string3 = getString(R.string.security_settings_sfps_enroll_start_message);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            configureEnrollmentStage(R.raw.sfps_lottie_pad_center, string3);
            return;
        }
        if (c == 2) {
            glifLayoutHelper.setHeaderText(R.string.security_settings_sfps_enroll_fingertip_title);
            if (this.haveShownSfpsTipLottie) {
                return;
            }
            this.haveShownSfpsTipLottie = true;
            configureEnrollmentStage(R.raw.sfps_lottie_tip, ApnSettings.MVNO_NONE);
            return;
        }
        if (c == 3) {
            glifLayoutHelper.setHeaderText(R.string.security_settings_sfps_enroll_left_edge_title);
            if (this.haveShownSfpsLeftEdgeLottie) {
                return;
            }
            this.haveShownSfpsLeftEdgeLottie = true;
            configureEnrollmentStage(R.raw.sfps_lottie_left_edge, ApnSettings.MVNO_NONE);
            return;
        }
        if (c == 4) {
            glifLayoutHelper.setHeaderText(R.string.security_settings_sfps_enroll_right_edge_title);
            if (this.haveShownSfpsRightEdgeLottie) {
                return;
            }
            this.haveShownSfpsRightEdgeLottie = true;
            configureEnrollmentStage(R.raw.sfps_lottie_right_edge, ApnSettings.MVNO_NONE);
            return;
        }
        glifLayoutHelper.setHeaderText(R.string.security_settings_sfps_enroll_find_sensor_title);
        glifLayoutHelper.setDescriptionText(
                getString(R.string.security_settings_sfps_enroll_start_message));
        String string4 = getString(R.string.security_settings_sfps_enroll_find_sensor_message);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        glifLayout.getHeaderTextView().setContentDescription(string4);
        requireActivity.setTitle(string4);
    }
}
