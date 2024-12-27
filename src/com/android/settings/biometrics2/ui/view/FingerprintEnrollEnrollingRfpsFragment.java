package com.android.settings.biometrics2.ui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.samsung.android.knox.container.EnterpriseContainerConstants;

import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;

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
            "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollEnrollingRfpsFragment;",
            "Landroidx/fragment/app/Fragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollEnrollingRfpsFragment extends Fragment {
    public FingerprintEnrollEnrollingViewModel _enrollingViewModel;
    public FingerprintEnrollErrorDialogViewModel _errorDialogViewModel;
    public FingerprintEnrollProgressViewModel _progressViewModel;
    public final FingerprintEnrollEnrollingRfpsFragment$progressObserver$1 canceledSignalObserver;
    public Object enrollingCancelSignal;
    public GlifLayout enrollingView;
    public final FingerprintEnrollEnrollingRfpsFragment$progressObserver$1 errorMessageObserver;
    public Interpolator fastOutLinearInInterpolator;
    public Interpolator fastOutSlowInInterpolator;
    public final FingerprintEnrollEnrollingRfpsFragment$progressObserver$1 helpMessageObserver;
    public int iconTouchCount;
    public boolean isAnimationCancelled;
    public Interpolator linearOutSlowInInterpolator;
    public ObjectAnimator progressAnim;
    public final FingerprintEnrollEnrollingRfpsFragment$progressObserver$1 progressObserver;
    public final FingerprintEnrollEnrollingRfpsFragment$clearError$1 touchAgainRunnable =
            new FingerprintEnrollEnrollingRfpsFragment$clearError$1(this, 4);
    public final FingerprintEnrollEnrollingRfpsFragment$onSkipClickListener$1 onSkipClickListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$onSkipClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                            FingerprintEnrollEnrollingRfpsFragment.this._enrollingViewModel;
                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                    fingerprintEnrollEnrollingViewModel.mOnSkipPressed = true;
                    FingerprintEnrollEnrollingRfpsFragment.this.cancelEnrollment(true);
                }
            };
    public final FingerprintEnrollEnrollingRfpsFragment$onBackPressedCallback$1
            onBackPressedCallback =
                    new OnBackPressedCallback() { // from class:
                                                  // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$onBackPressedCallback$1
                        {
                            super(true);
                        }

                        @Override // androidx.activity.OnBackPressedCallback
                        public final void handleOnBackPressed() {
                            setEnabled(false);
                            FingerprintEnrollEnrollingRfpsFragment
                                    fingerprintEnrollEnrollingRfpsFragment =
                                            FingerprintEnrollEnrollingRfpsFragment.this;
                            FingerprintEnrollEnrollingViewModel
                                    fingerprintEnrollEnrollingViewModel =
                                            fingerprintEnrollEnrollingRfpsFragment
                                                    ._enrollingViewModel;
                            Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                            fingerprintEnrollEnrollingViewModel.mOnBackPressed = true;
                            fingerprintEnrollEnrollingRfpsFragment.cancelEnrollment(true);
                        }
                    };
    public final FingerprintEnrollEnrollingRfpsFragment$clearError$1 showDialogRunnable =
            new FingerprintEnrollEnrollingRfpsFragment$clearError$1(this, 3);
    public final FingerprintEnrollEnrollingRfpsFragment$progressAnimationListener$1
            progressAnimationListener =
                    new Animator
                            .AnimatorListener() { // from class:
                                                  // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$progressAnimationListener$1
                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animation) {
                            Intrinsics.checkNotNullParameter(animation, "animation");
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animation) {
                            Intrinsics.checkNotNullParameter(animation, "animation");
                            FingerprintEnrollEnrollingRfpsFragment
                                    fingerprintEnrollEnrollingRfpsFragment =
                                            FingerprintEnrollEnrollingRfpsFragment.this;
                            fingerprintEnrollEnrollingRfpsFragment.isAnimationCancelled = true;
                            AnimatedVectorDrawable iconAnimationDrawable =
                                    fingerprintEnrollEnrollingRfpsFragment
                                            .getIconAnimationDrawable();
                            if (iconAnimationDrawable != null) {
                                iconAnimationDrawable.stop();
                            }
                            if (FingerprintEnrollEnrollingRfpsFragment.this
                                            .getProgressBar$1()
                                            .getProgress()
                                    >= 10000) {
                                FingerprintEnrollEnrollingRfpsFragment.this
                                        .getProgressBar$1()
                                        .postDelayed(
                                                FingerprintEnrollEnrollingRfpsFragment.this
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
                            AnimatedVectorDrawable iconAnimationDrawable =
                                    FingerprintEnrollEnrollingRfpsFragment.this
                                            .getIconAnimationDrawable();
                            if (iconAnimationDrawable != null) {
                                iconAnimationDrawable.start();
                            }
                        }
                    };
    public final FingerprintEnrollEnrollingRfpsFragment$clearError$1 delayedFinishRunnable =
            new FingerprintEnrollEnrollingRfpsFragment$clearError$1(this, 1);
    public final FingerprintEnrollEnrollingRfpsFragment$iconAnimationCallback$1
            iconAnimationCallback =
                    new Animatable2
                            .AnimationCallback() { // from class:
                                                   // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$iconAnimationCallback$1
                        @Override // android.graphics.drawable.Animatable2.AnimationCallback
                        public final void onAnimationEnd(Drawable d) {
                            Intrinsics.checkNotNullParameter(d, "d");
                            FingerprintEnrollEnrollingRfpsFragment
                                    fingerprintEnrollEnrollingRfpsFragment =
                                            FingerprintEnrollEnrollingRfpsFragment.this;
                            if (fingerprintEnrollEnrollingRfpsFragment.isAnimationCancelled) {
                                return;
                            }
                            fingerprintEnrollEnrollingRfpsFragment
                                    .getProgressBar$1()
                                    .post(
                                            new FingerprintEnrollEnrollingRfpsFragment$clearError$1(
                                                    FingerprintEnrollEnrollingRfpsFragment.this,
                                                    2));
                        }
                    };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$onSkipClickListener$1] */
    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$iconAnimationCallback$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$progressObserver$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$progressObserver$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$progressObserver$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$progressObserver$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$onBackPressedCallback$1] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$progressAnimationListener$1] */
    public FingerprintEnrollEnrollingRfpsFragment() {
        final int i = 0;
        this.progressObserver =
                new Observer(this) { // from class:
                    // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingRfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null && enrollmentProgress.steps >= 0) {
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment = this.this$0;
                                    fingerprintEnrollEnrollingRfpsFragment.updateProgress(
                                            true, enrollmentProgress);
                                    fingerprintEnrollEnrollingRfpsFragment
                                            .updateTitleAndDescription();
                                    Drawable background =
                                            fingerprintEnrollEnrollingRfpsFragment
                                                    .getProgressBar$1()
                                                    .getBackground();
                                    Intrinsics.checkNotNull(
                                            background,
                                            "null cannot be cast to non-null type"
                                                + " android.graphics.drawable.LayerDrawable");
                                    AnimatedVectorDrawable animatedVectorDrawable =
                                            (AnimatedVectorDrawable)
                                                    ((LayerDrawable) background)
                                                            .findDrawableByLayerId(
                                                                    R.id.fingerprint_background);
                                    if (animatedVectorDrawable != null) {
                                        animatedVectorDrawable.start();
                                    }
                                    fingerprintEnrollEnrollingRfpsFragment
                                            .getErrorText()
                                            .removeCallbacks(
                                                    fingerprintEnrollEnrollingRfpsFragment
                                                            .touchAgainRunnable);
                                    fingerprintEnrollEnrollingRfpsFragment
                                            .getErrorText()
                                            .postDelayed(
                                                    fingerprintEnrollEnrollingRfpsFragment
                                                            .touchAgainRunnable,
                                                    2500L);
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingRfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment2 = this.this$0;
                                    fingerprintEnrollEnrollingRfpsFragment2.isAnimationCancelled =
                                            true;
                                    AnimatedVectorDrawable iconAnimationDrawable =
                                            fingerprintEnrollEnrollingRfpsFragment2
                                                    .getIconAnimationDrawable();
                                    if (iconAnimationDrawable != null) {
                                        iconAnimationDrawable.stop();
                                    }
                                    fingerprintEnrollEnrollingRfpsFragment2.cancelEnrollment(true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingRfpsFragment2),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingRfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingRfpsFragment2,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                if (obj != null) {
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment3 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollEnrollingRfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingRfpsFragment3
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingRfpsFragment3
                                                    .enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        if (!fingerprintEnrollEnrollingViewModel.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel2 =
                                                            fingerprintEnrollEnrollingRfpsFragment3
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel2);
                                            if (fingerprintEnrollEnrollingViewModel2
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel3 =
                                                                fingerprintEnrollEnrollingRfpsFragment3
                                                                        ._enrollingViewModel;
                                                Intrinsics.checkNotNull(
                                                        fingerprintEnrollEnrollingViewModel3);
                                                fingerprintEnrollEnrollingViewModel3
                                                                .mOnSkipPressed =
                                                        false;
                                                fingerprintEnrollEnrollingViewModel3.mActionLiveData
                                                        .postValue(2);
                                                break;
                                            }
                                        } else {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel4 =
                                                            fingerprintEnrollEnrollingRfpsFragment3
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel4);
                                            fingerprintEnrollEnrollingViewModel4.mOnBackPressed =
                                                    false;
                                            fingerprintEnrollEnrollingViewModel4.mActionLiveData
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
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment4 = this.this$0;
                                    fingerprintEnrollEnrollingRfpsFragment4.getClass();
                                    Log.d(
                                            "FingerprintEnrollEnrollingRfpsFragment",
                                            "onEnrollmentHelp(" + enrollmentStatusMessage2 + ")");
                                    CharSequence charSequence = enrollmentStatusMessage2.str;
                                    if (!TextUtils.isEmpty(charSequence)) {
                                        fingerprintEnrollEnrollingRfpsFragment4
                                                .getErrorText()
                                                .removeCallbacks(
                                                        fingerprintEnrollEnrollingRfpsFragment4
                                                                .touchAgainRunnable);
                                        fingerprintEnrollEnrollingRfpsFragment4.showError$2(
                                                charSequence);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 3;
        this.helpMessageObserver =
                new Observer(this) { // from class:
                    // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingRfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i2) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null && enrollmentProgress.steps >= 0) {
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment = this.this$0;
                                    fingerprintEnrollEnrollingRfpsFragment.updateProgress(
                                            true, enrollmentProgress);
                                    fingerprintEnrollEnrollingRfpsFragment
                                            .updateTitleAndDescription();
                                    Drawable background =
                                            fingerprintEnrollEnrollingRfpsFragment
                                                    .getProgressBar$1()
                                                    .getBackground();
                                    Intrinsics.checkNotNull(
                                            background,
                                            "null cannot be cast to non-null type"
                                                + " android.graphics.drawable.LayerDrawable");
                                    AnimatedVectorDrawable animatedVectorDrawable =
                                            (AnimatedVectorDrawable)
                                                    ((LayerDrawable) background)
                                                            .findDrawableByLayerId(
                                                                    R.id.fingerprint_background);
                                    if (animatedVectorDrawable != null) {
                                        animatedVectorDrawable.start();
                                    }
                                    fingerprintEnrollEnrollingRfpsFragment
                                            .getErrorText()
                                            .removeCallbacks(
                                                    fingerprintEnrollEnrollingRfpsFragment
                                                            .touchAgainRunnable);
                                    fingerprintEnrollEnrollingRfpsFragment
                                            .getErrorText()
                                            .postDelayed(
                                                    fingerprintEnrollEnrollingRfpsFragment
                                                            .touchAgainRunnable,
                                                    2500L);
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingRfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment2 = this.this$0;
                                    fingerprintEnrollEnrollingRfpsFragment2.isAnimationCancelled =
                                            true;
                                    AnimatedVectorDrawable iconAnimationDrawable =
                                            fingerprintEnrollEnrollingRfpsFragment2
                                                    .getIconAnimationDrawable();
                                    if (iconAnimationDrawable != null) {
                                        iconAnimationDrawable.stop();
                                    }
                                    fingerprintEnrollEnrollingRfpsFragment2.cancelEnrollment(true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingRfpsFragment2),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingRfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingRfpsFragment2,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                if (obj != null) {
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment3 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollEnrollingRfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingRfpsFragment3
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingRfpsFragment3
                                                    .enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        if (!fingerprintEnrollEnrollingViewModel.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel2 =
                                                            fingerprintEnrollEnrollingRfpsFragment3
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel2);
                                            if (fingerprintEnrollEnrollingViewModel2
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel3 =
                                                                fingerprintEnrollEnrollingRfpsFragment3
                                                                        ._enrollingViewModel;
                                                Intrinsics.checkNotNull(
                                                        fingerprintEnrollEnrollingViewModel3);
                                                fingerprintEnrollEnrollingViewModel3
                                                                .mOnSkipPressed =
                                                        false;
                                                fingerprintEnrollEnrollingViewModel3.mActionLiveData
                                                        .postValue(2);
                                                break;
                                            }
                                        } else {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel4 =
                                                            fingerprintEnrollEnrollingRfpsFragment3
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel4);
                                            fingerprintEnrollEnrollingViewModel4.mOnBackPressed =
                                                    false;
                                            fingerprintEnrollEnrollingViewModel4.mActionLiveData
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
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment4 = this.this$0;
                                    fingerprintEnrollEnrollingRfpsFragment4.getClass();
                                    Log.d(
                                            "FingerprintEnrollEnrollingRfpsFragment",
                                            "onEnrollmentHelp(" + enrollmentStatusMessage2 + ")");
                                    CharSequence charSequence = enrollmentStatusMessage2.str;
                                    if (!TextUtils.isEmpty(charSequence)) {
                                        fingerprintEnrollEnrollingRfpsFragment4
                                                .getErrorText()
                                                .removeCallbacks(
                                                        fingerprintEnrollEnrollingRfpsFragment4
                                                                .touchAgainRunnable);
                                        fingerprintEnrollEnrollingRfpsFragment4.showError$2(
                                                charSequence);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i3 = 1;
        this.errorMessageObserver =
                new Observer(this) { // from class:
                    // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingRfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i3) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null && enrollmentProgress.steps >= 0) {
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment = this.this$0;
                                    fingerprintEnrollEnrollingRfpsFragment.updateProgress(
                                            true, enrollmentProgress);
                                    fingerprintEnrollEnrollingRfpsFragment
                                            .updateTitleAndDescription();
                                    Drawable background =
                                            fingerprintEnrollEnrollingRfpsFragment
                                                    .getProgressBar$1()
                                                    .getBackground();
                                    Intrinsics.checkNotNull(
                                            background,
                                            "null cannot be cast to non-null type"
                                                + " android.graphics.drawable.LayerDrawable");
                                    AnimatedVectorDrawable animatedVectorDrawable =
                                            (AnimatedVectorDrawable)
                                                    ((LayerDrawable) background)
                                                            .findDrawableByLayerId(
                                                                    R.id.fingerprint_background);
                                    if (animatedVectorDrawable != null) {
                                        animatedVectorDrawable.start();
                                    }
                                    fingerprintEnrollEnrollingRfpsFragment
                                            .getErrorText()
                                            .removeCallbacks(
                                                    fingerprintEnrollEnrollingRfpsFragment
                                                            .touchAgainRunnable);
                                    fingerprintEnrollEnrollingRfpsFragment
                                            .getErrorText()
                                            .postDelayed(
                                                    fingerprintEnrollEnrollingRfpsFragment
                                                            .touchAgainRunnable,
                                                    2500L);
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingRfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment2 = this.this$0;
                                    fingerprintEnrollEnrollingRfpsFragment2.isAnimationCancelled =
                                            true;
                                    AnimatedVectorDrawable iconAnimationDrawable =
                                            fingerprintEnrollEnrollingRfpsFragment2
                                                    .getIconAnimationDrawable();
                                    if (iconAnimationDrawable != null) {
                                        iconAnimationDrawable.stop();
                                    }
                                    fingerprintEnrollEnrollingRfpsFragment2.cancelEnrollment(true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingRfpsFragment2),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingRfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingRfpsFragment2,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                if (obj != null) {
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment3 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollEnrollingRfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingRfpsFragment3
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingRfpsFragment3
                                                    .enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        if (!fingerprintEnrollEnrollingViewModel.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel2 =
                                                            fingerprintEnrollEnrollingRfpsFragment3
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel2);
                                            if (fingerprintEnrollEnrollingViewModel2
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel3 =
                                                                fingerprintEnrollEnrollingRfpsFragment3
                                                                        ._enrollingViewModel;
                                                Intrinsics.checkNotNull(
                                                        fingerprintEnrollEnrollingViewModel3);
                                                fingerprintEnrollEnrollingViewModel3
                                                                .mOnSkipPressed =
                                                        false;
                                                fingerprintEnrollEnrollingViewModel3.mActionLiveData
                                                        .postValue(2);
                                                break;
                                            }
                                        } else {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel4 =
                                                            fingerprintEnrollEnrollingRfpsFragment3
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel4);
                                            fingerprintEnrollEnrollingViewModel4.mOnBackPressed =
                                                    false;
                                            fingerprintEnrollEnrollingViewModel4.mActionLiveData
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
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment4 = this.this$0;
                                    fingerprintEnrollEnrollingRfpsFragment4.getClass();
                                    Log.d(
                                            "FingerprintEnrollEnrollingRfpsFragment",
                                            "onEnrollmentHelp(" + enrollmentStatusMessage2 + ")");
                                    CharSequence charSequence = enrollmentStatusMessage2.str;
                                    if (!TextUtils.isEmpty(charSequence)) {
                                        fingerprintEnrollEnrollingRfpsFragment4
                                                .getErrorText()
                                                .removeCallbacks(
                                                        fingerprintEnrollEnrollingRfpsFragment4
                                                                .touchAgainRunnable);
                                        fingerprintEnrollEnrollingRfpsFragment4.showError$2(
                                                charSequence);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i4 = 2;
        this.canceledSignalObserver =
                new Observer(this) { // from class:
                    // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingRfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i4) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null && enrollmentProgress.steps >= 0) {
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment = this.this$0;
                                    fingerprintEnrollEnrollingRfpsFragment.updateProgress(
                                            true, enrollmentProgress);
                                    fingerprintEnrollEnrollingRfpsFragment
                                            .updateTitleAndDescription();
                                    Drawable background =
                                            fingerprintEnrollEnrollingRfpsFragment
                                                    .getProgressBar$1()
                                                    .getBackground();
                                    Intrinsics.checkNotNull(
                                            background,
                                            "null cannot be cast to non-null type"
                                                + " android.graphics.drawable.LayerDrawable");
                                    AnimatedVectorDrawable animatedVectorDrawable =
                                            (AnimatedVectorDrawable)
                                                    ((LayerDrawable) background)
                                                            .findDrawableByLayerId(
                                                                    R.id.fingerprint_background);
                                    if (animatedVectorDrawable != null) {
                                        animatedVectorDrawable.start();
                                    }
                                    fingerprintEnrollEnrollingRfpsFragment
                                            .getErrorText()
                                            .removeCallbacks(
                                                    fingerprintEnrollEnrollingRfpsFragment
                                                            .touchAgainRunnable);
                                    fingerprintEnrollEnrollingRfpsFragment
                                            .getErrorText()
                                            .postDelayed(
                                                    fingerprintEnrollEnrollingRfpsFragment
                                                            .touchAgainRunnable,
                                                    2500L);
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingRfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment2 = this.this$0;
                                    fingerprintEnrollEnrollingRfpsFragment2.isAnimationCancelled =
                                            true;
                                    AnimatedVectorDrawable iconAnimationDrawable =
                                            fingerprintEnrollEnrollingRfpsFragment2
                                                    .getIconAnimationDrawable();
                                    if (iconAnimationDrawable != null) {
                                        iconAnimationDrawable.stop();
                                    }
                                    fingerprintEnrollEnrollingRfpsFragment2.cancelEnrollment(true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingRfpsFragment2),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingRfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingRfpsFragment2,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                if (obj != null) {
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment3 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollEnrollingRfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingRfpsFragment3
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingRfpsFragment3
                                                    .enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingRfpsFragment3
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        if (!fingerprintEnrollEnrollingViewModel.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel2 =
                                                            fingerprintEnrollEnrollingRfpsFragment3
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel2);
                                            if (fingerprintEnrollEnrollingViewModel2
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel3 =
                                                                fingerprintEnrollEnrollingRfpsFragment3
                                                                        ._enrollingViewModel;
                                                Intrinsics.checkNotNull(
                                                        fingerprintEnrollEnrollingViewModel3);
                                                fingerprintEnrollEnrollingViewModel3
                                                                .mOnSkipPressed =
                                                        false;
                                                fingerprintEnrollEnrollingViewModel3.mActionLiveData
                                                        .postValue(2);
                                                break;
                                            }
                                        } else {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel4 =
                                                            fingerprintEnrollEnrollingRfpsFragment3
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel4);
                                            fingerprintEnrollEnrollingViewModel4.mOnBackPressed =
                                                    false;
                                            fingerprintEnrollEnrollingViewModel4.mActionLiveData
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
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment4 = this.this$0;
                                    fingerprintEnrollEnrollingRfpsFragment4.getClass();
                                    Log.d(
                                            "FingerprintEnrollEnrollingRfpsFragment",
                                            "onEnrollmentHelp(" + enrollmentStatusMessage2 + ")");
                                    CharSequence charSequence = enrollmentStatusMessage2.str;
                                    if (!TextUtils.isEmpty(charSequence)) {
                                        fingerprintEnrollEnrollingRfpsFragment4
                                                .getErrorText()
                                                .removeCallbacks(
                                                        fingerprintEnrollEnrollingRfpsFragment4
                                                                .touchAgainRunnable);
                                        fingerprintEnrollEnrollingRfpsFragment4.showError$2(
                                                charSequence);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
    }

    public final void cancelEnrollment(boolean z) {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        if (!fingerprintEnrollProgressViewModel.isEnrolling()) {
            Log.d(
                    "FingerprintEnrollEnrollingRfpsFragment",
                    "cancelEnrollment(), failed because isEnrolling is false");
            return;
        }
        removeEnrollmentObservers();
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
                "FingerprintEnrollEnrollingRfpsFragment",
                "cancelEnrollment(), failed to cancel enrollment");
    }

    public final void clearError$1() {
        if (getErrorText().getVisibility() == 0) {
            getErrorText()
                    .animate()
                    .alpha(0.0f)
                    .translationY(
                            getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.fingerprint_error_text_disappear_distance))
                    .setDuration(100L)
                    .setInterpolator(this.fastOutLinearInInterpolator)
                    .withEndAction(new FingerprintEnrollEnrollingRfpsFragment$clearError$1(this, 0))
                    .start();
        }
    }

    public final TextView getErrorText() {
        GlifLayout glifLayout = this.enrollingView;
        Intrinsics.checkNotNull(glifLayout);
        View findViewById = glifLayout.findViewById(R.id.error_text);
        Intrinsics.checkNotNull(findViewById);
        return (TextView) findViewById;
    }

    public final AnimatedVectorDrawable getIconAnimationDrawable() {
        Drawable background = getProgressBar$1().getBackground();
        Intrinsics.checkNotNull(
                background,
                "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable");
        return (AnimatedVectorDrawable)
                ((LayerDrawable) background).findDrawableByLayerId(R.id.fingerprint_animation);
    }

    public final ProgressBar getProgressBar$1() {
        GlifLayout glifLayout = this.enrollingView;
        Intrinsics.checkNotNull(glifLayout);
        View findViewById = glifLayout.findViewById(R.id.fingerprint_progress_bar);
        Intrinsics.checkNotNull(findViewById);
        return (ProgressBar) findViewById;
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
        View inflate = inflater.inflate(R.layout.fingerprint_enroll_enrolling, viewGroup, false);
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
                "FingerprintEnrollEnrollingRfpsFragment");
        if (!value) {
            this.isAnimationCancelled = false;
            AnimatedVectorDrawable iconAnimationDrawable = getIconAnimationDrawable();
            if (iconAnimationDrawable != null) {
                iconAnimationDrawable.start();
            }
            startEnrollment$1();
        }
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel2 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel2);
        Object value2 = fingerprintEnrollProgressViewModel2.mProgressLiveData.getValue();
        Intrinsics.checkNotNull(value2);
        updateProgress(false, (EnrollmentProgress) value2);
        updateTitleAndDescription();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStop() {
        this.isAnimationCancelled = true;
        AnimatedVectorDrawable iconAnimationDrawable = getIconAnimationDrawable();
        if (iconAnimationDrawable != null) {
            iconAnimationDrawable.stop();
        }
        removeEnrollmentObservers();
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
                "FingerprintEnrollEnrollingRfpsFragment");
        if (isEnrolling && !isChangingConfigurations) {
            cancelEnrollment(false);
        }
        super.onStop();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        AnimatedVectorDrawable iconAnimationDrawable = getIconAnimationDrawable();
        Intrinsics.checkNotNull(iconAnimationDrawable);
        iconAnimationDrawable.registerAnimationCallback(this.iconAnimationCallback);
        getProgressBar$1()
                .setOnTouchListener(
                        new View
                                .OnTouchListener() { // from class:
                                                     // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingRfpsFragment$onViewCreated$1
                            @Override // android.view.View.OnTouchListener
                            public final boolean onTouch(View view2, MotionEvent event) {
                                Intrinsics.checkNotNullParameter(event, "event");
                                if (event.getActionMasked() == 0) {
                                    FingerprintEnrollEnrollingRfpsFragment
                                            fingerprintEnrollEnrollingRfpsFragment =
                                                    FingerprintEnrollEnrollingRfpsFragment.this;
                                    int i =
                                            fingerprintEnrollEnrollingRfpsFragment.iconTouchCount
                                                    + 1;
                                    fingerprintEnrollEnrollingRfpsFragment.iconTouchCount = i;
                                    if (i == 3) {
                                        fingerprintEnrollEnrollingRfpsFragment.iconTouchCount = 0;
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingRfpsFragment
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        fingerprintEnrollEnrollingViewModel.mActionLiveData
                                                .postValue(1);
                                    } else {
                                        fingerprintEnrollEnrollingRfpsFragment
                                                .getProgressBar$1()
                                                .postDelayed(
                                                        FingerprintEnrollEnrollingRfpsFragment.this
                                                                .showDialogRunnable,
                                                        500L);
                                    }
                                } else if (event.getActionMasked() == 3
                                        || event.getActionMasked() == 1) {
                                    FingerprintEnrollEnrollingRfpsFragment.this
                                            .getProgressBar$1()
                                            .removeCallbacks(
                                                    FingerprintEnrollEnrollingRfpsFragment.this
                                                            .showDialogRunnable);
                                }
                                return true;
                            }
                        });
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        GlifLayout glifLayout = this.enrollingView;
        Intrinsics.checkNotNull(glifLayout);
        FingerprintEnrollEnrollingRfpsFragment$onSkipClickListener$1 onSkipClickListener =
                this.onSkipClickListener;
        Intrinsics.checkNotNullParameter(onSkipClickListener, "onSkipClickListener");
        String string =
                requireActivity.getString(
                        R.string.security_settings_fingerprint_enroll_start_message);
        if (!TextUtils.equals(glifLayout.getDescriptionText(), string)) {
            glifLayout.setDescriptionText(string);
        }
        TextView headerTextView = glifLayout.getHeaderTextView();
        CharSequence text = headerTextView.getText();
        CharSequence text2 =
                requireActivity.getText(R.string.security_settings_fingerprint_enroll_repeat_title);
        Intrinsics.checkNotNullExpressionValue(text2, "getText(...)");
        if (text != text2) {
            if (!TextUtils.isEmpty(text)) {
                headerTextView.setAccessibilityLiveRegion(1);
            }
            glifLayout.setHeaderText(text2);
            glifLayout.getHeaderTextView().setContentDescription(text2);
            requireActivity.setTitle(text2);
        }
        View findViewById = glifLayout.findViewById(R.id.fingerprint_progress_bar);
        Intrinsics.checkNotNull(findViewById);
        ((ProgressBar) findViewById).setProgressBackgroundTintMode(PorterDuff.Mode.SRC);
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
        this.fastOutSlowInInterpolator =
                AnimationUtils.loadInterpolator(
                        requireContext(), android.R.interpolator.fast_out_slow_in);
        this.linearOutSlowInInterpolator =
                AnimationUtils.loadInterpolator(
                        requireContext(), android.R.interpolator.linear_out_slow_in);
        this.fastOutLinearInInterpolator =
                AnimationUtils.loadInterpolator(
                        requireContext(), android.R.interpolator.fast_out_linear_in);
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(this),
                null,
                null,
                new FingerprintEnrollEnrollingRfpsFragment$onViewCreated$2(this, null),
                3);
    }

    public final void removeEnrollmentObservers() {
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

    public final void showError$2(CharSequence charSequence) {
        getErrorText().setText(charSequence);
        if (getErrorText().getVisibility() == 4) {
            getErrorText().setVisibility(0);
            TextView errorText = getErrorText();
            Intrinsics.checkNotNull(this.enrollingView);
            errorText.setTranslationY(
                    r0.getContext()
                            .getResources()
                            .getDimensionPixelSize(R.dimen.fingerprint_error_text_appear_distance));
            getErrorText().setAlpha(0.0f);
            getErrorText()
                    .animate()
                    .alpha(1.0f)
                    .translationY(0.0f)
                    .setDuration(200L)
                    .setInterpolator(this.linearOutSlowInInterpolator)
                    .start();
        } else {
            getErrorText().animate().cancel();
            getErrorText().setAlpha(1.0f);
            getErrorText().setTranslationY(0.0f);
        }
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
                        fingerprintEnrollEnrollingViewModel2.getApplication().getOpPackageName(),
                        FingerprintEnrollEnrollingViewModel.VIBRATE_EFFECT_ERROR,
                        "FingerprintEnrollEnrollingRfpsFragment::showError",
                        FingerprintEnrollEnrollingViewModel
                                .FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES);
            }
        }
    }

    public final void startEnrollment$1() {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        Object startEnrollment = fingerprintEnrollProgressViewModel.startEnrollment(2);
        this.enrollingCancelSignal = startEnrollment;
        if (startEnrollment == null) {
            Log.e("FingerprintEnrollEnrollingRfpsFragment", "startEnrollment(), failed");
        } else {
            Log.d("FingerprintEnrollEnrollingRfpsFragment", "startEnrollment(), success");
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

    public final void updateProgress(boolean z, EnrollmentProgress enrollmentProgress) {
        int i = enrollmentProgress.steps;
        if (i != -1) {
            int i2 = i + 1;
            int i3 = i2 - enrollmentProgress.remaining;
            r3 = ((i3 > 0 ? i3 : 0) * EnterpriseContainerConstants.SYSTEM_SIGNED_APP) / i2;
        }
        Log.d(
                "FingerprintEnrollEnrollingRfpsFragment",
                "updateProgress("
                        + z
                        + ", "
                        + enrollmentProgress
                        + "), old:"
                        + getProgressBar$1().getProgress()
                        + ", new:"
                        + r3);
        if (getProgressBar$1().getProgress() < r3) {
            clearError$1();
        }
        if (!z) {
            getProgressBar$1().setProgress(r3);
            if (r3 >= 10000) {
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
                        getProgressBar$1(), "progress", getProgressBar$1().getProgress(), r3);
        ofInt.addListener(this.progressAnimationListener);
        ofInt.setInterpolator(this.fastOutSlowInInterpolator);
        ofInt.setDuration(250L);
        ofInt.start();
        this.progressAnim = ofInt;
    }

    public final void updateTitleAndDescription() {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        Object value = fingerprintEnrollProgressViewModel.mProgressLiveData.getValue();
        Intrinsics.checkNotNull(value);
        Intrinsics.checkNotNull(getActivity());
        GlifLayout glifLayout = this.enrollingView;
        Intrinsics.checkNotNull(glifLayout);
        GlifLayout glifLayout2 = this.enrollingView;
        Intrinsics.checkNotNull(glifLayout2);
        String string =
                glifLayout2
                        .getContext()
                        .getString(
                                ((EnrollmentProgress) value).steps == -1
                                        ? R.string
                                                .security_settings_fingerprint_enroll_start_message
                                        : R.string
                                                .security_settings_fingerprint_enroll_repeat_message);
        if (TextUtils.equals(glifLayout.getDescriptionText(), string)) {
            return;
        }
        glifLayout.setDescriptionText(string);
    }
}
