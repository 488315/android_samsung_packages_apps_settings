package com.android.settings.biometrics2.ui.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.biometrics2.ui.model.EnrollmentProgress;
import com.android.settings.biometrics2.ui.model.EnrollmentStatusMessage;
import com.android.settings.biometrics2.ui.viewmodel.DeviceRotationViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollEnrollingViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollErrorDialogViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollProgressViewModel;
import com.android.settings.biometrics2.ui.widget.UdfpsEnrollDrawable;
import com.android.settings.biometrics2.ui.widget.UdfpsEnrollView;
import com.android.settings.biometrics2.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda2;
import com.android.settingslib.display.DisplayDensityUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;

import kotlinx.coroutines.BuildersKt;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollEnrollingUdfpsFragment;",
            "Landroidx/fragment/app/Fragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollEnrollingUdfpsFragment extends Fragment {
    public FingerprintEnrollEnrollingViewModel _enrollingViewModel;
    public FingerprintEnrollErrorDialogViewModel _errorDialogViewModel;
    public FingerprintEnrollProgressViewModel _progressViewModel;
    public DeviceRotationViewModel _rotationViewModel;
    public final FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1 acquireObserver;
    public final FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1 canceledSignalObserver;
    public Object enrollingCancelSignal;
    public RelativeLayout enrollingView;
    public final FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1 errorMessageObserver;
    public boolean haveShownCenterLottie;
    public boolean haveShownGuideLottie;
    public boolean haveShownLeftEdgeLottie;
    public boolean haveShownRightEdgeLottie;
    public boolean haveShownTipLottie;
    public final FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1 helpMessageObserver;
    public LottieAnimationView illustrationLottie;
    public final FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1 pointerDownObserver;
    public final FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1 pointerUpObserver;
    public final FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1 progressObserver;
    public final FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1 rotationObserver;
    public int rotation = -1;
    public final FingerprintEnrollEnrollingUdfpsFragment$onSkipClickListener$1 onSkipClickListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$onSkipClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                            FingerprintEnrollEnrollingUdfpsFragment.this._enrollingViewModel;
                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                    fingerprintEnrollEnrollingViewModel.mOnSkipPressed = true;
                    FingerprintEnrollEnrollingUdfpsFragment.this.cancelEnrollment$2(true);
                }
            };
    public final FingerprintEnrollEnrollingUdfpsFragment$onBackPressedCallback$1
            onBackPressedCallback =
                    new OnBackPressedCallback() { // from class:
                                                  // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$onBackPressedCallback$1
                        {
                            super(true);
                        }

                        @Override // androidx.activity.OnBackPressedCallback
                        public final void handleOnBackPressed() {
                            setEnabled(false);
                            FingerprintEnrollEnrollingUdfpsFragment
                                    fingerprintEnrollEnrollingUdfpsFragment =
                                            FingerprintEnrollEnrollingUdfpsFragment.this;
                            FingerprintEnrollEnrollingViewModel
                                    fingerprintEnrollEnrollingViewModel =
                                            fingerprintEnrollEnrollingUdfpsFragment
                                                    ._enrollingViewModel;
                            Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                            fingerprintEnrollEnrollingViewModel.mOnBackPressed = true;
                            fingerprintEnrollEnrollingUdfpsFragment.cancelEnrollment$2(true);
                        }
                    };
    public final FingerprintEnrollEnrollingUdfpsFragment$delayedFinishRunnable$1
            delayedFinishRunnable =
                    new FingerprintEnrollEnrollingUdfpsFragment$delayedFinishRunnable$1(this);

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$onSkipClickListener$1] */
    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$onBackPressedCallback$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1] */
    public FingerprintEnrollEnrollingUdfpsFragment() {
        final int i = 6;
        this.progressObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingUdfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        int i2;
                        int i3;
                        switch (i) {
                            case 0:
                                Boolean bool = (Boolean) obj;
                                if (bool != null) {
                                    boolean booleanValue = bool.booleanValue();
                                    final UdfpsEnrollView udfpsEnrollView =
                                            this.this$0.getUdfpsEnrollView();
                                    final boolean z =
                                            booleanValue
                                                    && (i2 = udfpsEnrollView.mRemainingSteps) <= 2
                                                    && i2 >= 0;
                                    udfpsEnrollView.mHandler.post(
                                            new Runnable() { // from class:
                                                             // com.android.settings.biometrics2.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    UdfpsEnrollView udfpsEnrollView2 =
                                                            UdfpsEnrollView.this;
                                                    boolean z2 = z;
                                                    int i4 = UdfpsEnrollView.$r8$clinit;
                                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                                            udfpsEnrollView2.mFingerprintDrawable;
                                                    if (udfpsEnrollDrawable.mSkipDraw) {
                                                        udfpsEnrollDrawable.mSkipDraw = false;
                                                        udfpsEnrollDrawable.invalidateSelf();
                                                    }
                                                    udfpsEnrollView2.mFingerprintDrawable
                                                            .invalidateSelf();
                                                    if (z2) {
                                                        UdfpsEnrollProgressBarDrawable
                                                                udfpsEnrollProgressBarDrawable =
                                                                        udfpsEnrollView2
                                                                                .mFingerprintProgressDrawable;
                                                        udfpsEnrollProgressBarDrawable.updateState(
                                                                0,
                                                                udfpsEnrollProgressBarDrawable
                                                                        .mTotalSteps,
                                                                false);
                                                    }
                                                }
                                            });
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment.cancelEnrollment$2(
                                            true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingUdfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingUdfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingUdfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "canceledSignalObserver(" + obj + ")");
                                if (obj != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollEnrollingUdfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingUdfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingUdfpsFragment2
                                                    .enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        if (!fingerprintEnrollEnrollingViewModel.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel2 =
                                                            fingerprintEnrollEnrollingUdfpsFragment2
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel2);
                                            if (fingerprintEnrollEnrollingViewModel2
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel3 =
                                                                fingerprintEnrollEnrollingUdfpsFragment2
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
                                                            fingerprintEnrollEnrollingUdfpsFragment2
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
                            case 3:
                                EnrollmentStatusMessage enrollmentStatusMessage2 =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "helpMessageObserver(" + enrollmentStatusMessage2 + ")");
                                if (enrollmentStatusMessage2 != null) {
                                    this.this$0.onEnrollmentHelp$2(enrollmentStatusMessage2);
                                    break;
                                }
                                break;
                            case 4:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView2 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                            udfpsEnrollView2.mFingerprintDrawable;
                                    if (!udfpsEnrollDrawable.mSkipDraw) {
                                        udfpsEnrollDrawable.mSkipDraw = true;
                                        udfpsEnrollDrawable.invalidateSelf();
                                    }
                                    udfpsEnrollView2.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 5:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView3 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                            udfpsEnrollView3.mFingerprintDrawable;
                                    if (udfpsEnrollDrawable2.mSkipDraw) {
                                        udfpsEnrollDrawable2.mSkipDraw = false;
                                        udfpsEnrollDrawable2.invalidateSelf();
                                    }
                                    udfpsEnrollView3.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 6:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null
                                        && (i3 = enrollmentProgress.steps) >= 0) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment3 = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment3.updateProgress$2(
                                            true, enrollmentProgress);
                                    fingerprintEnrollEnrollingUdfpsFragment3
                                            .updateTitleAndDescription$2();
                                    FingerprintEnrollEnrollingViewModel
                                            fingerprintEnrollEnrollingViewModel5 =
                                                    fingerprintEnrollEnrollingUdfpsFragment3
                                                            ._enrollingViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel5);
                                    if (fingerprintEnrollEnrollingViewModel5.mAccessibilityManager
                                            .isEnabled()) {
                                        int i4 =
                                                (int)
                                                        (((i3 - enrollmentProgress.remaining) / i3)
                                                                * 100);
                                        FragmentActivity activity =
                                                fingerprintEnrollEnrollingUdfpsFragment3
                                                        .getActivity();
                                        Intrinsics.checkNotNull(activity);
                                        String string =
                                                activity.getString(
                                                        R.string
                                                                .security_settings_udfps_enroll_progress_a11y_message,
                                                        new Object[] {Integer.valueOf(i4)});
                                        Intrinsics.checkNotNullExpressionValue(
                                                string, "getString(...)");
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel6 =
                                                        fingerprintEnrollEnrollingUdfpsFragment3
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel6);
                                        fingerprintEnrollEnrollingViewModel6.sendAccessibilityEvent(
                                                string);
                                        break;
                                    }
                                }
                                break;
                            default:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i5 = (intValue + 2) % 4;
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment4 = this.this$0;
                                    if (i5 == fingerprintEnrollEnrollingUdfpsFragment4.rotation) {
                                        fingerprintEnrollEnrollingUdfpsFragment4.rotation =
                                                intValue;
                                        Context requireContext =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .requireContext();
                                        Intrinsics.checkNotNullExpressionValue(
                                                requireContext, "requireContext(...)");
                                        TextView titleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getTitleText();
                                        TextView subTitleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getSubTitleText();
                                        RelativeLayout relativeLayout =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout);
                                        View findViewById =
                                                relativeLayout.findViewById(R.id.sud_layout_icon);
                                        Intrinsics.checkNotNull(findViewById);
                                        RelativeLayout relativeLayout2 =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout2);
                                        View findViewById2 =
                                                relativeLayout2.findViewById(R.id.skip_btn);
                                        Intrinsics.checkNotNull(findViewById2);
                                        FingerprintEnrollEnrollingUdfpsFragmentKt.configLayout(
                                                requireContext,
                                                intValue,
                                                titleText,
                                                subTitleText,
                                                (ImageView) findViewById,
                                                (Button) findViewById2);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 3;
        this.helpMessageObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingUdfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        int i22;
                        int i3;
                        switch (i2) {
                            case 0:
                                Boolean bool = (Boolean) obj;
                                if (bool != null) {
                                    boolean booleanValue = bool.booleanValue();
                                    final UdfpsEnrollView udfpsEnrollView =
                                            this.this$0.getUdfpsEnrollView();
                                    final boolean z =
                                            booleanValue
                                                    && (i22 = udfpsEnrollView.mRemainingSteps) <= 2
                                                    && i22 >= 0;
                                    udfpsEnrollView.mHandler.post(
                                            new Runnable() { // from class:
                                                             // com.android.settings.biometrics2.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    UdfpsEnrollView udfpsEnrollView2 =
                                                            UdfpsEnrollView.this;
                                                    boolean z2 = z;
                                                    int i4 = UdfpsEnrollView.$r8$clinit;
                                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                                            udfpsEnrollView2.mFingerprintDrawable;
                                                    if (udfpsEnrollDrawable.mSkipDraw) {
                                                        udfpsEnrollDrawable.mSkipDraw = false;
                                                        udfpsEnrollDrawable.invalidateSelf();
                                                    }
                                                    udfpsEnrollView2.mFingerprintDrawable
                                                            .invalidateSelf();
                                                    if (z2) {
                                                        UdfpsEnrollProgressBarDrawable
                                                                udfpsEnrollProgressBarDrawable =
                                                                        udfpsEnrollView2
                                                                                .mFingerprintProgressDrawable;
                                                        udfpsEnrollProgressBarDrawable.updateState(
                                                                0,
                                                                udfpsEnrollProgressBarDrawable
                                                                        .mTotalSteps,
                                                                false);
                                                    }
                                                }
                                            });
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment.cancelEnrollment$2(
                                            true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingUdfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingUdfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingUdfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "canceledSignalObserver(" + obj + ")");
                                if (obj != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollEnrollingUdfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingUdfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingUdfpsFragment2
                                                    .enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        if (!fingerprintEnrollEnrollingViewModel.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel2 =
                                                            fingerprintEnrollEnrollingUdfpsFragment2
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel2);
                                            if (fingerprintEnrollEnrollingViewModel2
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel3 =
                                                                fingerprintEnrollEnrollingUdfpsFragment2
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
                                                            fingerprintEnrollEnrollingUdfpsFragment2
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
                            case 3:
                                EnrollmentStatusMessage enrollmentStatusMessage2 =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "helpMessageObserver(" + enrollmentStatusMessage2 + ")");
                                if (enrollmentStatusMessage2 != null) {
                                    this.this$0.onEnrollmentHelp$2(enrollmentStatusMessage2);
                                    break;
                                }
                                break;
                            case 4:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView2 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                            udfpsEnrollView2.mFingerprintDrawable;
                                    if (!udfpsEnrollDrawable.mSkipDraw) {
                                        udfpsEnrollDrawable.mSkipDraw = true;
                                        udfpsEnrollDrawable.invalidateSelf();
                                    }
                                    udfpsEnrollView2.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 5:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView3 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                            udfpsEnrollView3.mFingerprintDrawable;
                                    if (udfpsEnrollDrawable2.mSkipDraw) {
                                        udfpsEnrollDrawable2.mSkipDraw = false;
                                        udfpsEnrollDrawable2.invalidateSelf();
                                    }
                                    udfpsEnrollView3.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 6:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null
                                        && (i3 = enrollmentProgress.steps) >= 0) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment3 = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment3.updateProgress$2(
                                            true, enrollmentProgress);
                                    fingerprintEnrollEnrollingUdfpsFragment3
                                            .updateTitleAndDescription$2();
                                    FingerprintEnrollEnrollingViewModel
                                            fingerprintEnrollEnrollingViewModel5 =
                                                    fingerprintEnrollEnrollingUdfpsFragment3
                                                            ._enrollingViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel5);
                                    if (fingerprintEnrollEnrollingViewModel5.mAccessibilityManager
                                            .isEnabled()) {
                                        int i4 =
                                                (int)
                                                        (((i3 - enrollmentProgress.remaining) / i3)
                                                                * 100);
                                        FragmentActivity activity =
                                                fingerprintEnrollEnrollingUdfpsFragment3
                                                        .getActivity();
                                        Intrinsics.checkNotNull(activity);
                                        String string =
                                                activity.getString(
                                                        R.string
                                                                .security_settings_udfps_enroll_progress_a11y_message,
                                                        new Object[] {Integer.valueOf(i4)});
                                        Intrinsics.checkNotNullExpressionValue(
                                                string, "getString(...)");
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel6 =
                                                        fingerprintEnrollEnrollingUdfpsFragment3
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel6);
                                        fingerprintEnrollEnrollingViewModel6.sendAccessibilityEvent(
                                                string);
                                        break;
                                    }
                                }
                                break;
                            default:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i5 = (intValue + 2) % 4;
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment4 = this.this$0;
                                    if (i5 == fingerprintEnrollEnrollingUdfpsFragment4.rotation) {
                                        fingerprintEnrollEnrollingUdfpsFragment4.rotation =
                                                intValue;
                                        Context requireContext =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .requireContext();
                                        Intrinsics.checkNotNullExpressionValue(
                                                requireContext, "requireContext(...)");
                                        TextView titleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getTitleText();
                                        TextView subTitleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getSubTitleText();
                                        RelativeLayout relativeLayout =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout);
                                        View findViewById =
                                                relativeLayout.findViewById(R.id.sud_layout_icon);
                                        Intrinsics.checkNotNull(findViewById);
                                        RelativeLayout relativeLayout2 =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout2);
                                        View findViewById2 =
                                                relativeLayout2.findViewById(R.id.skip_btn);
                                        Intrinsics.checkNotNull(findViewById2);
                                        FingerprintEnrollEnrollingUdfpsFragmentKt.configLayout(
                                                requireContext,
                                                intValue,
                                                titleText,
                                                subTitleText,
                                                (ImageView) findViewById,
                                                (Button) findViewById2);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i3 = 1;
        this.errorMessageObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingUdfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        int i22;
                        int i32;
                        switch (i3) {
                            case 0:
                                Boolean bool = (Boolean) obj;
                                if (bool != null) {
                                    boolean booleanValue = bool.booleanValue();
                                    final UdfpsEnrollView udfpsEnrollView =
                                            this.this$0.getUdfpsEnrollView();
                                    final boolean z =
                                            booleanValue
                                                    && (i22 = udfpsEnrollView.mRemainingSteps) <= 2
                                                    && i22 >= 0;
                                    udfpsEnrollView.mHandler.post(
                                            new Runnable() { // from class:
                                                             // com.android.settings.biometrics2.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    UdfpsEnrollView udfpsEnrollView2 =
                                                            UdfpsEnrollView.this;
                                                    boolean z2 = z;
                                                    int i4 = UdfpsEnrollView.$r8$clinit;
                                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                                            udfpsEnrollView2.mFingerprintDrawable;
                                                    if (udfpsEnrollDrawable.mSkipDraw) {
                                                        udfpsEnrollDrawable.mSkipDraw = false;
                                                        udfpsEnrollDrawable.invalidateSelf();
                                                    }
                                                    udfpsEnrollView2.mFingerprintDrawable
                                                            .invalidateSelf();
                                                    if (z2) {
                                                        UdfpsEnrollProgressBarDrawable
                                                                udfpsEnrollProgressBarDrawable =
                                                                        udfpsEnrollView2
                                                                                .mFingerprintProgressDrawable;
                                                        udfpsEnrollProgressBarDrawable.updateState(
                                                                0,
                                                                udfpsEnrollProgressBarDrawable
                                                                        .mTotalSteps,
                                                                false);
                                                    }
                                                }
                                            });
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment.cancelEnrollment$2(
                                            true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingUdfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingUdfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingUdfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "canceledSignalObserver(" + obj + ")");
                                if (obj != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollEnrollingUdfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingUdfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingUdfpsFragment2
                                                    .enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        if (!fingerprintEnrollEnrollingViewModel.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel2 =
                                                            fingerprintEnrollEnrollingUdfpsFragment2
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel2);
                                            if (fingerprintEnrollEnrollingViewModel2
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel3 =
                                                                fingerprintEnrollEnrollingUdfpsFragment2
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
                                                            fingerprintEnrollEnrollingUdfpsFragment2
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
                            case 3:
                                EnrollmentStatusMessage enrollmentStatusMessage2 =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "helpMessageObserver(" + enrollmentStatusMessage2 + ")");
                                if (enrollmentStatusMessage2 != null) {
                                    this.this$0.onEnrollmentHelp$2(enrollmentStatusMessage2);
                                    break;
                                }
                                break;
                            case 4:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView2 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                            udfpsEnrollView2.mFingerprintDrawable;
                                    if (!udfpsEnrollDrawable.mSkipDraw) {
                                        udfpsEnrollDrawable.mSkipDraw = true;
                                        udfpsEnrollDrawable.invalidateSelf();
                                    }
                                    udfpsEnrollView2.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 5:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView3 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                            udfpsEnrollView3.mFingerprintDrawable;
                                    if (udfpsEnrollDrawable2.mSkipDraw) {
                                        udfpsEnrollDrawable2.mSkipDraw = false;
                                        udfpsEnrollDrawable2.invalidateSelf();
                                    }
                                    udfpsEnrollView3.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 6:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null
                                        && (i32 = enrollmentProgress.steps) >= 0) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment3 = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment3.updateProgress$2(
                                            true, enrollmentProgress);
                                    fingerprintEnrollEnrollingUdfpsFragment3
                                            .updateTitleAndDescription$2();
                                    FingerprintEnrollEnrollingViewModel
                                            fingerprintEnrollEnrollingViewModel5 =
                                                    fingerprintEnrollEnrollingUdfpsFragment3
                                                            ._enrollingViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel5);
                                    if (fingerprintEnrollEnrollingViewModel5.mAccessibilityManager
                                            .isEnabled()) {
                                        int i4 =
                                                (int)
                                                        (((i32 - enrollmentProgress.remaining)
                                                                        / i32)
                                                                * 100);
                                        FragmentActivity activity =
                                                fingerprintEnrollEnrollingUdfpsFragment3
                                                        .getActivity();
                                        Intrinsics.checkNotNull(activity);
                                        String string =
                                                activity.getString(
                                                        R.string
                                                                .security_settings_udfps_enroll_progress_a11y_message,
                                                        new Object[] {Integer.valueOf(i4)});
                                        Intrinsics.checkNotNullExpressionValue(
                                                string, "getString(...)");
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel6 =
                                                        fingerprintEnrollEnrollingUdfpsFragment3
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel6);
                                        fingerprintEnrollEnrollingViewModel6.sendAccessibilityEvent(
                                                string);
                                        break;
                                    }
                                }
                                break;
                            default:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i5 = (intValue + 2) % 4;
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment4 = this.this$0;
                                    if (i5 == fingerprintEnrollEnrollingUdfpsFragment4.rotation) {
                                        fingerprintEnrollEnrollingUdfpsFragment4.rotation =
                                                intValue;
                                        Context requireContext =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .requireContext();
                                        Intrinsics.checkNotNullExpressionValue(
                                                requireContext, "requireContext(...)");
                                        TextView titleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getTitleText();
                                        TextView subTitleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getSubTitleText();
                                        RelativeLayout relativeLayout =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout);
                                        View findViewById =
                                                relativeLayout.findViewById(R.id.sud_layout_icon);
                                        Intrinsics.checkNotNull(findViewById);
                                        RelativeLayout relativeLayout2 =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout2);
                                        View findViewById2 =
                                                relativeLayout2.findViewById(R.id.skip_btn);
                                        Intrinsics.checkNotNull(findViewById2);
                                        FingerprintEnrollEnrollingUdfpsFragmentKt.configLayout(
                                                requireContext,
                                                intValue,
                                                titleText,
                                                subTitleText,
                                                (ImageView) findViewById,
                                                (Button) findViewById2);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i4 = 2;
        this.canceledSignalObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingUdfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        int i22;
                        int i32;
                        switch (i4) {
                            case 0:
                                Boolean bool = (Boolean) obj;
                                if (bool != null) {
                                    boolean booleanValue = bool.booleanValue();
                                    final UdfpsEnrollView udfpsEnrollView =
                                            this.this$0.getUdfpsEnrollView();
                                    final boolean z =
                                            booleanValue
                                                    && (i22 = udfpsEnrollView.mRemainingSteps) <= 2
                                                    && i22 >= 0;
                                    udfpsEnrollView.mHandler.post(
                                            new Runnable() { // from class:
                                                             // com.android.settings.biometrics2.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    UdfpsEnrollView udfpsEnrollView2 =
                                                            UdfpsEnrollView.this;
                                                    boolean z2 = z;
                                                    int i42 = UdfpsEnrollView.$r8$clinit;
                                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                                            udfpsEnrollView2.mFingerprintDrawable;
                                                    if (udfpsEnrollDrawable.mSkipDraw) {
                                                        udfpsEnrollDrawable.mSkipDraw = false;
                                                        udfpsEnrollDrawable.invalidateSelf();
                                                    }
                                                    udfpsEnrollView2.mFingerprintDrawable
                                                            .invalidateSelf();
                                                    if (z2) {
                                                        UdfpsEnrollProgressBarDrawable
                                                                udfpsEnrollProgressBarDrawable =
                                                                        udfpsEnrollView2
                                                                                .mFingerprintProgressDrawable;
                                                        udfpsEnrollProgressBarDrawable.updateState(
                                                                0,
                                                                udfpsEnrollProgressBarDrawable
                                                                        .mTotalSteps,
                                                                false);
                                                    }
                                                }
                                            });
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment.cancelEnrollment$2(
                                            true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingUdfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingUdfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingUdfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "canceledSignalObserver(" + obj + ")");
                                if (obj != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollEnrollingUdfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingUdfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingUdfpsFragment2
                                                    .enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        if (!fingerprintEnrollEnrollingViewModel.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel2 =
                                                            fingerprintEnrollEnrollingUdfpsFragment2
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel2);
                                            if (fingerprintEnrollEnrollingViewModel2
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel3 =
                                                                fingerprintEnrollEnrollingUdfpsFragment2
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
                                                            fingerprintEnrollEnrollingUdfpsFragment2
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
                            case 3:
                                EnrollmentStatusMessage enrollmentStatusMessage2 =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "helpMessageObserver(" + enrollmentStatusMessage2 + ")");
                                if (enrollmentStatusMessage2 != null) {
                                    this.this$0.onEnrollmentHelp$2(enrollmentStatusMessage2);
                                    break;
                                }
                                break;
                            case 4:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView2 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                            udfpsEnrollView2.mFingerprintDrawable;
                                    if (!udfpsEnrollDrawable.mSkipDraw) {
                                        udfpsEnrollDrawable.mSkipDraw = true;
                                        udfpsEnrollDrawable.invalidateSelf();
                                    }
                                    udfpsEnrollView2.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 5:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView3 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                            udfpsEnrollView3.mFingerprintDrawable;
                                    if (udfpsEnrollDrawable2.mSkipDraw) {
                                        udfpsEnrollDrawable2.mSkipDraw = false;
                                        udfpsEnrollDrawable2.invalidateSelf();
                                    }
                                    udfpsEnrollView3.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 6:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null
                                        && (i32 = enrollmentProgress.steps) >= 0) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment3 = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment3.updateProgress$2(
                                            true, enrollmentProgress);
                                    fingerprintEnrollEnrollingUdfpsFragment3
                                            .updateTitleAndDescription$2();
                                    FingerprintEnrollEnrollingViewModel
                                            fingerprintEnrollEnrollingViewModel5 =
                                                    fingerprintEnrollEnrollingUdfpsFragment3
                                                            ._enrollingViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel5);
                                    if (fingerprintEnrollEnrollingViewModel5.mAccessibilityManager
                                            .isEnabled()) {
                                        int i42 =
                                                (int)
                                                        (((i32 - enrollmentProgress.remaining)
                                                                        / i32)
                                                                * 100);
                                        FragmentActivity activity =
                                                fingerprintEnrollEnrollingUdfpsFragment3
                                                        .getActivity();
                                        Intrinsics.checkNotNull(activity);
                                        String string =
                                                activity.getString(
                                                        R.string
                                                                .security_settings_udfps_enroll_progress_a11y_message,
                                                        new Object[] {Integer.valueOf(i42)});
                                        Intrinsics.checkNotNullExpressionValue(
                                                string, "getString(...)");
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel6 =
                                                        fingerprintEnrollEnrollingUdfpsFragment3
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel6);
                                        fingerprintEnrollEnrollingViewModel6.sendAccessibilityEvent(
                                                string);
                                        break;
                                    }
                                }
                                break;
                            default:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i5 = (intValue + 2) % 4;
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment4 = this.this$0;
                                    if (i5 == fingerprintEnrollEnrollingUdfpsFragment4.rotation) {
                                        fingerprintEnrollEnrollingUdfpsFragment4.rotation =
                                                intValue;
                                        Context requireContext =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .requireContext();
                                        Intrinsics.checkNotNullExpressionValue(
                                                requireContext, "requireContext(...)");
                                        TextView titleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getTitleText();
                                        TextView subTitleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getSubTitleText();
                                        RelativeLayout relativeLayout =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout);
                                        View findViewById =
                                                relativeLayout.findViewById(R.id.sud_layout_icon);
                                        Intrinsics.checkNotNull(findViewById);
                                        RelativeLayout relativeLayout2 =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout2);
                                        View findViewById2 =
                                                relativeLayout2.findViewById(R.id.skip_btn);
                                        Intrinsics.checkNotNull(findViewById2);
                                        FingerprintEnrollEnrollingUdfpsFragmentKt.configLayout(
                                                requireContext,
                                                intValue,
                                                titleText,
                                                subTitleText,
                                                (ImageView) findViewById,
                                                (Button) findViewById2);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i5 = 0;
        this.acquireObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingUdfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        int i22;
                        int i32;
                        switch (i5) {
                            case 0:
                                Boolean bool = (Boolean) obj;
                                if (bool != null) {
                                    boolean booleanValue = bool.booleanValue();
                                    final UdfpsEnrollView udfpsEnrollView =
                                            this.this$0.getUdfpsEnrollView();
                                    final boolean z =
                                            booleanValue
                                                    && (i22 = udfpsEnrollView.mRemainingSteps) <= 2
                                                    && i22 >= 0;
                                    udfpsEnrollView.mHandler.post(
                                            new Runnable() { // from class:
                                                             // com.android.settings.biometrics2.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    UdfpsEnrollView udfpsEnrollView2 =
                                                            UdfpsEnrollView.this;
                                                    boolean z2 = z;
                                                    int i42 = UdfpsEnrollView.$r8$clinit;
                                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                                            udfpsEnrollView2.mFingerprintDrawable;
                                                    if (udfpsEnrollDrawable.mSkipDraw) {
                                                        udfpsEnrollDrawable.mSkipDraw = false;
                                                        udfpsEnrollDrawable.invalidateSelf();
                                                    }
                                                    udfpsEnrollView2.mFingerprintDrawable
                                                            .invalidateSelf();
                                                    if (z2) {
                                                        UdfpsEnrollProgressBarDrawable
                                                                udfpsEnrollProgressBarDrawable =
                                                                        udfpsEnrollView2
                                                                                .mFingerprintProgressDrawable;
                                                        udfpsEnrollProgressBarDrawable.updateState(
                                                                0,
                                                                udfpsEnrollProgressBarDrawable
                                                                        .mTotalSteps,
                                                                false);
                                                    }
                                                }
                                            });
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment.cancelEnrollment$2(
                                            true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingUdfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingUdfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingUdfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "canceledSignalObserver(" + obj + ")");
                                if (obj != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollEnrollingUdfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingUdfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingUdfpsFragment2
                                                    .enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        if (!fingerprintEnrollEnrollingViewModel.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel2 =
                                                            fingerprintEnrollEnrollingUdfpsFragment2
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel2);
                                            if (fingerprintEnrollEnrollingViewModel2
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel3 =
                                                                fingerprintEnrollEnrollingUdfpsFragment2
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
                                                            fingerprintEnrollEnrollingUdfpsFragment2
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
                            case 3:
                                EnrollmentStatusMessage enrollmentStatusMessage2 =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "helpMessageObserver(" + enrollmentStatusMessage2 + ")");
                                if (enrollmentStatusMessage2 != null) {
                                    this.this$0.onEnrollmentHelp$2(enrollmentStatusMessage2);
                                    break;
                                }
                                break;
                            case 4:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView2 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                            udfpsEnrollView2.mFingerprintDrawable;
                                    if (!udfpsEnrollDrawable.mSkipDraw) {
                                        udfpsEnrollDrawable.mSkipDraw = true;
                                        udfpsEnrollDrawable.invalidateSelf();
                                    }
                                    udfpsEnrollView2.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 5:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView3 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                            udfpsEnrollView3.mFingerprintDrawable;
                                    if (udfpsEnrollDrawable2.mSkipDraw) {
                                        udfpsEnrollDrawable2.mSkipDraw = false;
                                        udfpsEnrollDrawable2.invalidateSelf();
                                    }
                                    udfpsEnrollView3.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 6:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null
                                        && (i32 = enrollmentProgress.steps) >= 0) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment3 = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment3.updateProgress$2(
                                            true, enrollmentProgress);
                                    fingerprintEnrollEnrollingUdfpsFragment3
                                            .updateTitleAndDescription$2();
                                    FingerprintEnrollEnrollingViewModel
                                            fingerprintEnrollEnrollingViewModel5 =
                                                    fingerprintEnrollEnrollingUdfpsFragment3
                                                            ._enrollingViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel5);
                                    if (fingerprintEnrollEnrollingViewModel5.mAccessibilityManager
                                            .isEnabled()) {
                                        int i42 =
                                                (int)
                                                        (((i32 - enrollmentProgress.remaining)
                                                                        / i32)
                                                                * 100);
                                        FragmentActivity activity =
                                                fingerprintEnrollEnrollingUdfpsFragment3
                                                        .getActivity();
                                        Intrinsics.checkNotNull(activity);
                                        String string =
                                                activity.getString(
                                                        R.string
                                                                .security_settings_udfps_enroll_progress_a11y_message,
                                                        new Object[] {Integer.valueOf(i42)});
                                        Intrinsics.checkNotNullExpressionValue(
                                                string, "getString(...)");
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel6 =
                                                        fingerprintEnrollEnrollingUdfpsFragment3
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel6);
                                        fingerprintEnrollEnrollingViewModel6.sendAccessibilityEvent(
                                                string);
                                        break;
                                    }
                                }
                                break;
                            default:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i52 = (intValue + 2) % 4;
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment4 = this.this$0;
                                    if (i52 == fingerprintEnrollEnrollingUdfpsFragment4.rotation) {
                                        fingerprintEnrollEnrollingUdfpsFragment4.rotation =
                                                intValue;
                                        Context requireContext =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .requireContext();
                                        Intrinsics.checkNotNullExpressionValue(
                                                requireContext, "requireContext(...)");
                                        TextView titleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getTitleText();
                                        TextView subTitleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getSubTitleText();
                                        RelativeLayout relativeLayout =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout);
                                        View findViewById =
                                                relativeLayout.findViewById(R.id.sud_layout_icon);
                                        Intrinsics.checkNotNull(findViewById);
                                        RelativeLayout relativeLayout2 =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout2);
                                        View findViewById2 =
                                                relativeLayout2.findViewById(R.id.skip_btn);
                                        Intrinsics.checkNotNull(findViewById2);
                                        FingerprintEnrollEnrollingUdfpsFragmentKt.configLayout(
                                                requireContext,
                                                intValue,
                                                titleText,
                                                subTitleText,
                                                (ImageView) findViewById,
                                                (Button) findViewById2);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i6 = 4;
        this.pointerDownObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingUdfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        int i22;
                        int i32;
                        switch (i6) {
                            case 0:
                                Boolean bool = (Boolean) obj;
                                if (bool != null) {
                                    boolean booleanValue = bool.booleanValue();
                                    final UdfpsEnrollView udfpsEnrollView =
                                            this.this$0.getUdfpsEnrollView();
                                    final boolean z =
                                            booleanValue
                                                    && (i22 = udfpsEnrollView.mRemainingSteps) <= 2
                                                    && i22 >= 0;
                                    udfpsEnrollView.mHandler.post(
                                            new Runnable() { // from class:
                                                             // com.android.settings.biometrics2.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    UdfpsEnrollView udfpsEnrollView2 =
                                                            UdfpsEnrollView.this;
                                                    boolean z2 = z;
                                                    int i42 = UdfpsEnrollView.$r8$clinit;
                                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                                            udfpsEnrollView2.mFingerprintDrawable;
                                                    if (udfpsEnrollDrawable.mSkipDraw) {
                                                        udfpsEnrollDrawable.mSkipDraw = false;
                                                        udfpsEnrollDrawable.invalidateSelf();
                                                    }
                                                    udfpsEnrollView2.mFingerprintDrawable
                                                            .invalidateSelf();
                                                    if (z2) {
                                                        UdfpsEnrollProgressBarDrawable
                                                                udfpsEnrollProgressBarDrawable =
                                                                        udfpsEnrollView2
                                                                                .mFingerprintProgressDrawable;
                                                        udfpsEnrollProgressBarDrawable.updateState(
                                                                0,
                                                                udfpsEnrollProgressBarDrawable
                                                                        .mTotalSteps,
                                                                false);
                                                    }
                                                }
                                            });
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment.cancelEnrollment$2(
                                            true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingUdfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingUdfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingUdfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "canceledSignalObserver(" + obj + ")");
                                if (obj != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollEnrollingUdfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingUdfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingUdfpsFragment2
                                                    .enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        if (!fingerprintEnrollEnrollingViewModel.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel2 =
                                                            fingerprintEnrollEnrollingUdfpsFragment2
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel2);
                                            if (fingerprintEnrollEnrollingViewModel2
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel3 =
                                                                fingerprintEnrollEnrollingUdfpsFragment2
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
                                                            fingerprintEnrollEnrollingUdfpsFragment2
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
                            case 3:
                                EnrollmentStatusMessage enrollmentStatusMessage2 =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "helpMessageObserver(" + enrollmentStatusMessage2 + ")");
                                if (enrollmentStatusMessage2 != null) {
                                    this.this$0.onEnrollmentHelp$2(enrollmentStatusMessage2);
                                    break;
                                }
                                break;
                            case 4:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView2 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                            udfpsEnrollView2.mFingerprintDrawable;
                                    if (!udfpsEnrollDrawable.mSkipDraw) {
                                        udfpsEnrollDrawable.mSkipDraw = true;
                                        udfpsEnrollDrawable.invalidateSelf();
                                    }
                                    udfpsEnrollView2.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 5:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView3 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                            udfpsEnrollView3.mFingerprintDrawable;
                                    if (udfpsEnrollDrawable2.mSkipDraw) {
                                        udfpsEnrollDrawable2.mSkipDraw = false;
                                        udfpsEnrollDrawable2.invalidateSelf();
                                    }
                                    udfpsEnrollView3.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 6:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null
                                        && (i32 = enrollmentProgress.steps) >= 0) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment3 = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment3.updateProgress$2(
                                            true, enrollmentProgress);
                                    fingerprintEnrollEnrollingUdfpsFragment3
                                            .updateTitleAndDescription$2();
                                    FingerprintEnrollEnrollingViewModel
                                            fingerprintEnrollEnrollingViewModel5 =
                                                    fingerprintEnrollEnrollingUdfpsFragment3
                                                            ._enrollingViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel5);
                                    if (fingerprintEnrollEnrollingViewModel5.mAccessibilityManager
                                            .isEnabled()) {
                                        int i42 =
                                                (int)
                                                        (((i32 - enrollmentProgress.remaining)
                                                                        / i32)
                                                                * 100);
                                        FragmentActivity activity =
                                                fingerprintEnrollEnrollingUdfpsFragment3
                                                        .getActivity();
                                        Intrinsics.checkNotNull(activity);
                                        String string =
                                                activity.getString(
                                                        R.string
                                                                .security_settings_udfps_enroll_progress_a11y_message,
                                                        new Object[] {Integer.valueOf(i42)});
                                        Intrinsics.checkNotNullExpressionValue(
                                                string, "getString(...)");
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel6 =
                                                        fingerprintEnrollEnrollingUdfpsFragment3
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel6);
                                        fingerprintEnrollEnrollingViewModel6.sendAccessibilityEvent(
                                                string);
                                        break;
                                    }
                                }
                                break;
                            default:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i52 = (intValue + 2) % 4;
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment4 = this.this$0;
                                    if (i52 == fingerprintEnrollEnrollingUdfpsFragment4.rotation) {
                                        fingerprintEnrollEnrollingUdfpsFragment4.rotation =
                                                intValue;
                                        Context requireContext =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .requireContext();
                                        Intrinsics.checkNotNullExpressionValue(
                                                requireContext, "requireContext(...)");
                                        TextView titleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getTitleText();
                                        TextView subTitleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getSubTitleText();
                                        RelativeLayout relativeLayout =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout);
                                        View findViewById =
                                                relativeLayout.findViewById(R.id.sud_layout_icon);
                                        Intrinsics.checkNotNull(findViewById);
                                        RelativeLayout relativeLayout2 =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout2);
                                        View findViewById2 =
                                                relativeLayout2.findViewById(R.id.skip_btn);
                                        Intrinsics.checkNotNull(findViewById2);
                                        FingerprintEnrollEnrollingUdfpsFragmentKt.configLayout(
                                                requireContext,
                                                intValue,
                                                titleText,
                                                subTitleText,
                                                (ImageView) findViewById,
                                                (Button) findViewById2);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i7 = 5;
        this.pointerUpObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingUdfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        int i22;
                        int i32;
                        switch (i7) {
                            case 0:
                                Boolean bool = (Boolean) obj;
                                if (bool != null) {
                                    boolean booleanValue = bool.booleanValue();
                                    final UdfpsEnrollView udfpsEnrollView =
                                            this.this$0.getUdfpsEnrollView();
                                    final boolean z =
                                            booleanValue
                                                    && (i22 = udfpsEnrollView.mRemainingSteps) <= 2
                                                    && i22 >= 0;
                                    udfpsEnrollView.mHandler.post(
                                            new Runnable() { // from class:
                                                             // com.android.settings.biometrics2.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    UdfpsEnrollView udfpsEnrollView2 =
                                                            UdfpsEnrollView.this;
                                                    boolean z2 = z;
                                                    int i42 = UdfpsEnrollView.$r8$clinit;
                                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                                            udfpsEnrollView2.mFingerprintDrawable;
                                                    if (udfpsEnrollDrawable.mSkipDraw) {
                                                        udfpsEnrollDrawable.mSkipDraw = false;
                                                        udfpsEnrollDrawable.invalidateSelf();
                                                    }
                                                    udfpsEnrollView2.mFingerprintDrawable
                                                            .invalidateSelf();
                                                    if (z2) {
                                                        UdfpsEnrollProgressBarDrawable
                                                                udfpsEnrollProgressBarDrawable =
                                                                        udfpsEnrollView2
                                                                                .mFingerprintProgressDrawable;
                                                        udfpsEnrollProgressBarDrawable.updateState(
                                                                0,
                                                                udfpsEnrollProgressBarDrawable
                                                                        .mTotalSteps,
                                                                false);
                                                    }
                                                }
                                            });
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment.cancelEnrollment$2(
                                            true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingUdfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingUdfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingUdfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "canceledSignalObserver(" + obj + ")");
                                if (obj != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollEnrollingUdfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingUdfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingUdfpsFragment2
                                                    .enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        if (!fingerprintEnrollEnrollingViewModel.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel2 =
                                                            fingerprintEnrollEnrollingUdfpsFragment2
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel2);
                                            if (fingerprintEnrollEnrollingViewModel2
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel3 =
                                                                fingerprintEnrollEnrollingUdfpsFragment2
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
                                                            fingerprintEnrollEnrollingUdfpsFragment2
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
                            case 3:
                                EnrollmentStatusMessage enrollmentStatusMessage2 =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "helpMessageObserver(" + enrollmentStatusMessage2 + ")");
                                if (enrollmentStatusMessage2 != null) {
                                    this.this$0.onEnrollmentHelp$2(enrollmentStatusMessage2);
                                    break;
                                }
                                break;
                            case 4:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView2 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                            udfpsEnrollView2.mFingerprintDrawable;
                                    if (!udfpsEnrollDrawable.mSkipDraw) {
                                        udfpsEnrollDrawable.mSkipDraw = true;
                                        udfpsEnrollDrawable.invalidateSelf();
                                    }
                                    udfpsEnrollView2.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 5:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView3 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                            udfpsEnrollView3.mFingerprintDrawable;
                                    if (udfpsEnrollDrawable2.mSkipDraw) {
                                        udfpsEnrollDrawable2.mSkipDraw = false;
                                        udfpsEnrollDrawable2.invalidateSelf();
                                    }
                                    udfpsEnrollView3.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 6:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null
                                        && (i32 = enrollmentProgress.steps) >= 0) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment3 = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment3.updateProgress$2(
                                            true, enrollmentProgress);
                                    fingerprintEnrollEnrollingUdfpsFragment3
                                            .updateTitleAndDescription$2();
                                    FingerprintEnrollEnrollingViewModel
                                            fingerprintEnrollEnrollingViewModel5 =
                                                    fingerprintEnrollEnrollingUdfpsFragment3
                                                            ._enrollingViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel5);
                                    if (fingerprintEnrollEnrollingViewModel5.mAccessibilityManager
                                            .isEnabled()) {
                                        int i42 =
                                                (int)
                                                        (((i32 - enrollmentProgress.remaining)
                                                                        / i32)
                                                                * 100);
                                        FragmentActivity activity =
                                                fingerprintEnrollEnrollingUdfpsFragment3
                                                        .getActivity();
                                        Intrinsics.checkNotNull(activity);
                                        String string =
                                                activity.getString(
                                                        R.string
                                                                .security_settings_udfps_enroll_progress_a11y_message,
                                                        new Object[] {Integer.valueOf(i42)});
                                        Intrinsics.checkNotNullExpressionValue(
                                                string, "getString(...)");
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel6 =
                                                        fingerprintEnrollEnrollingUdfpsFragment3
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel6);
                                        fingerprintEnrollEnrollingViewModel6.sendAccessibilityEvent(
                                                string);
                                        break;
                                    }
                                }
                                break;
                            default:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i52 = (intValue + 2) % 4;
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment4 = this.this$0;
                                    if (i52 == fingerprintEnrollEnrollingUdfpsFragment4.rotation) {
                                        fingerprintEnrollEnrollingUdfpsFragment4.rotation =
                                                intValue;
                                        Context requireContext =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .requireContext();
                                        Intrinsics.checkNotNullExpressionValue(
                                                requireContext, "requireContext(...)");
                                        TextView titleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getTitleText();
                                        TextView subTitleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getSubTitleText();
                                        RelativeLayout relativeLayout =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout);
                                        View findViewById =
                                                relativeLayout.findViewById(R.id.sud_layout_icon);
                                        Intrinsics.checkNotNull(findViewById);
                                        RelativeLayout relativeLayout2 =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout2);
                                        View findViewById2 =
                                                relativeLayout2.findViewById(R.id.skip_btn);
                                        Intrinsics.checkNotNull(findViewById2);
                                        FingerprintEnrollEnrollingUdfpsFragmentKt.configLayout(
                                                requireContext,
                                                intValue,
                                                titleText,
                                                subTitleText,
                                                (ImageView) findViewById,
                                                (Button) findViewById2);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i8 = 7;
        this.rotationObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$acquireObserver$1
                    public final /* synthetic */ FingerprintEnrollEnrollingUdfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        int i22;
                        int i32;
                        switch (i8) {
                            case 0:
                                Boolean bool = (Boolean) obj;
                                if (bool != null) {
                                    boolean booleanValue = bool.booleanValue();
                                    final UdfpsEnrollView udfpsEnrollView =
                                            this.this$0.getUdfpsEnrollView();
                                    final boolean z =
                                            booleanValue
                                                    && (i22 = udfpsEnrollView.mRemainingSteps) <= 2
                                                    && i22 >= 0;
                                    udfpsEnrollView.mHandler.post(
                                            new Runnable() { // from class:
                                                             // com.android.settings.biometrics2.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    UdfpsEnrollView udfpsEnrollView2 =
                                                            UdfpsEnrollView.this;
                                                    boolean z2 = z;
                                                    int i42 = UdfpsEnrollView.$r8$clinit;
                                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                                            udfpsEnrollView2.mFingerprintDrawable;
                                                    if (udfpsEnrollDrawable.mSkipDraw) {
                                                        udfpsEnrollDrawable.mSkipDraw = false;
                                                        udfpsEnrollDrawable.invalidateSelf();
                                                    }
                                                    udfpsEnrollView2.mFingerprintDrawable
                                                            .invalidateSelf();
                                                    if (z2) {
                                                        UdfpsEnrollProgressBarDrawable
                                                                udfpsEnrollProgressBarDrawable =
                                                                        udfpsEnrollView2
                                                                                .mFingerprintProgressDrawable;
                                                        udfpsEnrollProgressBarDrawable.updateState(
                                                                0,
                                                                udfpsEnrollProgressBarDrawable
                                                                        .mTotalSteps,
                                                                false);
                                                    }
                                                }
                                            });
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment.cancelEnrollment$2(
                                            true);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollEnrollingUdfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollEnrollingUdfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollEnrollingUdfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "canceledSignalObserver(" + obj + ")");
                                if (obj != null) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollEnrollingUdfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollEnrollingUdfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollEnrollingUdfpsFragment2
                                                    .enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        fingerprintEnrollProgressViewModel.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.clearProgressLiveData();
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel =
                                                        fingerprintEnrollEnrollingUdfpsFragment2
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel);
                                        if (!fingerprintEnrollEnrollingViewModel.mOnBackPressed) {
                                            FingerprintEnrollEnrollingViewModel
                                                    fingerprintEnrollEnrollingViewModel2 =
                                                            fingerprintEnrollEnrollingUdfpsFragment2
                                                                    ._enrollingViewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollEnrollingViewModel2);
                                            if (fingerprintEnrollEnrollingViewModel2
                                                    .mOnSkipPressed) {
                                                FingerprintEnrollEnrollingViewModel
                                                        fingerprintEnrollEnrollingViewModel3 =
                                                                fingerprintEnrollEnrollingUdfpsFragment2
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
                                                            fingerprintEnrollEnrollingUdfpsFragment2
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
                            case 3:
                                EnrollmentStatusMessage enrollmentStatusMessage2 =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollEnrollingUdfpsFragment",
                                        "helpMessageObserver(" + enrollmentStatusMessage2 + ")");
                                if (enrollmentStatusMessage2 != null) {
                                    this.this$0.onEnrollmentHelp$2(enrollmentStatusMessage2);
                                    break;
                                }
                                break;
                            case 4:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView2 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable =
                                            udfpsEnrollView2.mFingerprintDrawable;
                                    if (!udfpsEnrollDrawable.mSkipDraw) {
                                        udfpsEnrollDrawable.mSkipDraw = true;
                                        udfpsEnrollDrawable.invalidateSelf();
                                    }
                                    udfpsEnrollView2.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 5:
                                if (((Integer) obj) != null) {
                                    UdfpsEnrollView udfpsEnrollView3 =
                                            this.this$0.getUdfpsEnrollView();
                                    UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                            udfpsEnrollView3.mFingerprintDrawable;
                                    if (udfpsEnrollDrawable2.mSkipDraw) {
                                        udfpsEnrollDrawable2.mSkipDraw = false;
                                        udfpsEnrollDrawable2.invalidateSelf();
                                    }
                                    udfpsEnrollView3.mFingerprintDrawable.invalidateSelf();
                                    break;
                                }
                                break;
                            case 6:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null
                                        && (i32 = enrollmentProgress.steps) >= 0) {
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment3 = this.this$0;
                                    fingerprintEnrollEnrollingUdfpsFragment3.updateProgress$2(
                                            true, enrollmentProgress);
                                    fingerprintEnrollEnrollingUdfpsFragment3
                                            .updateTitleAndDescription$2();
                                    FingerprintEnrollEnrollingViewModel
                                            fingerprintEnrollEnrollingViewModel5 =
                                                    fingerprintEnrollEnrollingUdfpsFragment3
                                                            ._enrollingViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel5);
                                    if (fingerprintEnrollEnrollingViewModel5.mAccessibilityManager
                                            .isEnabled()) {
                                        int i42 =
                                                (int)
                                                        (((i32 - enrollmentProgress.remaining)
                                                                        / i32)
                                                                * 100);
                                        FragmentActivity activity =
                                                fingerprintEnrollEnrollingUdfpsFragment3
                                                        .getActivity();
                                        Intrinsics.checkNotNull(activity);
                                        String string =
                                                activity.getString(
                                                        R.string
                                                                .security_settings_udfps_enroll_progress_a11y_message,
                                                        new Object[] {Integer.valueOf(i42)});
                                        Intrinsics.checkNotNullExpressionValue(
                                                string, "getString(...)");
                                        FingerprintEnrollEnrollingViewModel
                                                fingerprintEnrollEnrollingViewModel6 =
                                                        fingerprintEnrollEnrollingUdfpsFragment3
                                                                ._enrollingViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollEnrollingViewModel6);
                                        fingerprintEnrollEnrollingViewModel6.sendAccessibilityEvent(
                                                string);
                                        break;
                                    }
                                }
                                break;
                            default:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i52 = (intValue + 2) % 4;
                                    FingerprintEnrollEnrollingUdfpsFragment
                                            fingerprintEnrollEnrollingUdfpsFragment4 = this.this$0;
                                    if (i52 == fingerprintEnrollEnrollingUdfpsFragment4.rotation) {
                                        fingerprintEnrollEnrollingUdfpsFragment4.rotation =
                                                intValue;
                                        Context requireContext =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .requireContext();
                                        Intrinsics.checkNotNullExpressionValue(
                                                requireContext, "requireContext(...)");
                                        TextView titleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getTitleText();
                                        TextView subTitleText =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .getSubTitleText();
                                        RelativeLayout relativeLayout =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout);
                                        View findViewById =
                                                relativeLayout.findViewById(R.id.sud_layout_icon);
                                        Intrinsics.checkNotNull(findViewById);
                                        RelativeLayout relativeLayout2 =
                                                fingerprintEnrollEnrollingUdfpsFragment4
                                                        .enrollingView;
                                        Intrinsics.checkNotNull(relativeLayout2);
                                        View findViewById2 =
                                                relativeLayout2.findViewById(R.id.skip_btn);
                                        Intrinsics.checkNotNull(findViewById2);
                                        FingerprintEnrollEnrollingUdfpsFragmentKt.configLayout(
                                                requireContext,
                                                intValue,
                                                titleText,
                                                subTitleText,
                                                (ImageView) findViewById,
                                                (Button) findViewById2);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
    }

    public final void cancelEnrollment$2(boolean z) {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        if (!fingerprintEnrollProgressViewModel.isEnrolling()) {
            Log.d(
                    "FingerprintEnrollEnrollingUdfpsFragment",
                    "cancelEnrollment(), failed because isEnrolling is false");
            return;
        }
        removeEnrollmentObservers$2();
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
                "FingerprintEnrollEnrollingUdfpsFragment",
                "cancelEnrollment(), failed to cancel enrollment");
    }

    public final void configureEnrollmentStage$1(int i) {
        getSubTitleText().setText(ApnSettings.MVNO_NONE);
        FragmentActivity activity = getActivity();
        LottieCompositionFactory.fromRawRes(
                        activity, i, LottieCompositionFactory.rawResCacheKey(activity, i))
                .addListener(
                        new LottieListener() { // from class:
                                               // com.android.settings.biometrics2.ui.view.FingerprintEnrollEnrollingUdfpsFragment$configureEnrollmentStage$1
                            @Override // com.airbnb.lottie.LottieListener
                            public final void onResult(Object obj) {
                                LottieComposition c = (LottieComposition) obj;
                                Intrinsics.checkNotNullParameter(c, "c");
                                LottieAnimationView lottieAnimationView =
                                        FingerprintEnrollEnrollingUdfpsFragment.this
                                                .illustrationLottie;
                                if (lottieAnimationView != null) {
                                    lottieAnimationView.setComposition(c);
                                    lottieAnimationView.setVisibility(0);
                                    lottieAnimationView.playAnimation$1();
                                }
                            }
                        });
    }

    public final int getCurrentStage$1() {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        Object value = fingerprintEnrollProgressViewModel.mProgressLiveData.getValue();
        Intrinsics.checkNotNull(value);
        EnrollmentProgress enrollmentProgress = (EnrollmentProgress) value;
        int i = enrollmentProgress.steps;
        if (i == -1) {
            return -1;
        }
        int i2 = i - enrollmentProgress.remaining;
        if (i2 < getStageThresholdSteps$2(0)) {
            return 0;
        }
        if (i2 < getStageThresholdSteps$2(1)) {
            return 1;
        }
        if (i2 < getStageThresholdSteps$2(2)) {
            return 2;
        }
        return i2 < getStageThresholdSteps$2(3) ? 3 : 4;
    }

    public final int getStageThresholdSteps$2(int i) {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        Object value = fingerprintEnrollProgressViewModel.mProgressLiveData.getValue();
        Intrinsics.checkNotNull(value);
        int i2 = ((EnrollmentProgress) value).steps;
        if (i2 == -1) {
            Log.w(
                    "FingerprintEnrollEnrollingUdfpsFragment",
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

    public final TextView getSubTitleText() {
        RelativeLayout relativeLayout = this.enrollingView;
        Intrinsics.checkNotNull(relativeLayout);
        View findViewById = relativeLayout.findViewById(R.id.sud_layout_subtitle);
        Intrinsics.checkNotNull(findViewById);
        return (TextView) findViewById;
    }

    public final TextView getTitleText() {
        RelativeLayout relativeLayout = this.enrollingView;
        Intrinsics.checkNotNull(relativeLayout);
        View findViewById = relativeLayout.findViewById(R.id.suc_layout_title);
        Intrinsics.checkNotNull(findViewById);
        return (TextView) findViewById;
    }

    public final UdfpsEnrollView getUdfpsEnrollView() {
        RelativeLayout relativeLayout = this.enrollingView;
        Intrinsics.checkNotNull(relativeLayout);
        View findViewById = relativeLayout.findViewById(R.id.udfps_animation_view);
        Intrinsics.checkNotNull(findViewById);
        return (UdfpsEnrollView) findViewById;
    }

    public final boolean isStageHalfCompleted$1() {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        Object value = fingerprintEnrollProgressViewModel.mProgressLiveData.getValue();
        Intrinsics.checkNotNull(value);
        EnrollmentProgress enrollmentProgress = (EnrollmentProgress) value;
        int i = enrollmentProgress.steps;
        if (i == -1) {
            return false;
        }
        int i2 = i - enrollmentProgress.remaining;
        FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                this._enrollingViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
        int enrollStageCount =
                fingerprintEnrollEnrollingViewModel.mFingerprintRepository.mFingerprintManager
                        .getEnrollStageCount();
        int i3 = 0;
        int i4 = 0;
        while (i3 < enrollStageCount) {
            int stageThresholdSteps$2 = getStageThresholdSteps$2(i3);
            if (i4 <= i2 && i2 < stageThresholdSteps$2) {
                return i2 - i4 >= (stageThresholdSteps$2 - i4) / 2;
            }
            i3++;
            i4 = stageThresholdSteps$2;
        }
        return true;
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
        this._rotationViewModel =
                (DeviceRotationViewModel)
                        viewModelProvider.get(
                                JvmClassMappingKt.getKotlinClass(DeviceRotationViewModel.class));
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
        View inflate = inflater.inflate(R.layout.udfps_enroll_enrolling_v2, viewGroup, false);
        Intrinsics.checkNotNull(
                inflate, "null cannot be cast to non-null type android.widget.RelativeLayout");
        RelativeLayout relativeLayout = (RelativeLayout) inflate;
        this.enrollingView = relativeLayout;
        return relativeLayout;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDetach() {
        setEnabled(false);
        super.onDetach();
    }

    public final void onEnrollmentHelp$2(EnrollmentStatusMessage enrollmentStatusMessage) {
        Log.d(
                "FingerprintEnrollEnrollingUdfpsFragment",
                "onEnrollmentHelp(" + enrollmentStatusMessage + ")");
        CharSequence charSequence = enrollmentStatusMessage.str;
        if (charSequence.length() > 0) {
            getTitleText().setText(charSequence);
            getTitleText().setContentDescription(charSequence);
            getSubTitleText().setContentDescription(ApnSettings.MVNO_NONE);
            UdfpsEnrollView udfpsEnrollView = getUdfpsEnrollView();
            udfpsEnrollView.mHandler.post(
                    new UdfpsEnrollView$$ExternalSyntheticLambda2(udfpsEnrollView, 1));
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        DeviceRotationViewModel deviceRotationViewModel = this._rotationViewModel;
        Intrinsics.checkNotNull(deviceRotationViewModel);
        deviceRotationViewModel.getLiveData().removeObserver(this.rotationObserver);
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        DeviceRotationViewModel deviceRotationViewModel = this._rotationViewModel;
        Intrinsics.checkNotNull(deviceRotationViewModel);
        deviceRotationViewModel.getLiveData().observe(this, this.rotationObserver);
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
                "FingerprintEnrollEnrollingUdfpsFragment");
        if (!value) {
            startEnrollment$3();
        }
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel2 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel2);
        Object value2 = fingerprintEnrollProgressViewModel2.mProgressLiveData.getValue();
        Intrinsics.checkNotNull(value2);
        updateProgress$2(false, (EnrollmentProgress) value2);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel3 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel3);
        EnrollmentStatusMessage enrollmentStatusMessage =
                (EnrollmentStatusMessage)
                        fingerprintEnrollProgressViewModel3.mHelpMessageLiveData.getValue();
        if (enrollmentStatusMessage != null) {
            onEnrollmentHelp$2(enrollmentStatusMessage);
        } else {
            updateTitleAndDescription$2();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStop() {
        removeEnrollmentObservers$2();
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
                "FingerprintEnrollEnrollingUdfpsFragment");
        if (isEnrolling && !isChangingConfigurations) {
            cancelEnrollment$2(false);
        }
        super.onStop();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        DeviceRotationViewModel deviceRotationViewModel = this._rotationViewModel;
        Intrinsics.checkNotNull(deviceRotationViewModel);
        Object value = deviceRotationViewModel.getLiveData().getValue();
        Intrinsics.checkNotNull(value);
        int intValue = ((Number) value).intValue();
        this.rotation = intValue;
        if (intValue == 1 || intValue == 3) {
            this.illustrationLottie = null;
        } else {
            DisplayDensityUtils displayDensityUtils = new DisplayDensityUtils(requireContext());
            if (displayDensityUtils.mDefaultDensityForDefaultDisplay
                    == displayDensityUtils
                            .mDefaultDisplayDensityValues[displayDensityUtils.mCurrentIndex]) {
                RelativeLayout relativeLayout = this.enrollingView;
                Intrinsics.checkNotNull(relativeLayout);
                this.illustrationLottie =
                        (LottieAnimationView) relativeLayout.findViewById(R.id.illustration_lottie);
            }
        }
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        RelativeLayout relativeLayout2 = this.enrollingView;
        Intrinsics.checkNotNull(relativeLayout2);
        FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                this._enrollingViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
        FingerprintSensorPropertiesInternal firstFingerprintSensorPropertiesInternal =
                fingerprintEnrollEnrollingViewModel.mFingerprintRepository
                        .getFirstFingerprintSensorPropertiesInternal();
        Intrinsics.checkNotNull(firstFingerprintSensorPropertiesInternal);
        int i = this.rotation;
        FingerprintEnrollEnrollingUdfpsFragment$onSkipClickListener$1 onSkipClickListener =
                this.onSkipClickListener;
        Intrinsics.checkNotNullParameter(onSkipClickListener, "onSkipClickListener");
        View findViewById = relativeLayout2.findViewById(R.id.udfps_animation_view);
        Intrinsics.checkNotNull(findViewById);
        ((UdfpsEnrollView) findViewById)
                .setSensorProperties(firstFingerprintSensorPropertiesInternal);
        View findViewById2 = relativeLayout2.findViewById(R.id.suc_layout_title);
        Intrinsics.checkNotNull(findViewById2);
        View findViewById3 = relativeLayout2.findViewById(R.id.sud_layout_subtitle);
        Intrinsics.checkNotNull(findViewById3);
        View findViewById4 = relativeLayout2.findViewById(R.id.sud_layout_icon);
        Intrinsics.checkNotNull(findViewById4);
        View findViewById5 = relativeLayout2.findViewById(R.id.skip_btn);
        Intrinsics.checkNotNull(findViewById5);
        Button button = (Button) findViewById5;
        button.setOnClickListener(onSkipClickListener);
        FingerprintEnrollEnrollingUdfpsFragmentKt.configLayout(
                requireActivity,
                i,
                (TextView) findViewById2,
                (TextView) findViewById3,
                (ImageView) findViewById4,
                button);
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(this),
                null,
                null,
                new FingerprintEnrollEnrollingUdfpsFragment$onViewCreated$1(this, null),
                3);
    }

    public final void removeEnrollmentObservers$2() {
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
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel4 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel4);
        fingerprintEnrollProgressViewModel4.mAcquireLiveData.removeObserver(this.acquireObserver);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel5 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel5);
        fingerprintEnrollProgressViewModel5.mPointerDownLiveData.removeObserver(
                this.pointerDownObserver);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel6 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel6);
        fingerprintEnrollProgressViewModel6.mPointerUpLiveData.removeObserver(
                this.pointerUpObserver);
    }

    public final void startEnrollment$3() {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        Object startEnrollment = fingerprintEnrollProgressViewModel.startEnrollment(2);
        this.enrollingCancelSignal = startEnrollment;
        if (startEnrollment == null) {
            Log.e("FingerprintEnrollEnrollingUdfpsFragment", "startEnrollment(), failed");
        } else {
            Log.d("FingerprintEnrollEnrollingUdfpsFragment", "startEnrollment(), success");
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
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel5 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel5);
        fingerprintEnrollProgressViewModel5.mAcquireLiveData.observe(this, this.acquireObserver);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel6 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel6);
        fingerprintEnrollProgressViewModel6.mPointerDownLiveData.observe(
                this, this.pointerDownObserver);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel7 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel7);
        fingerprintEnrollProgressViewModel7.mPointerUpLiveData.observe(
                this, this.pointerUpObserver);
    }

    public final void updateProgress$2(boolean z, EnrollmentProgress enrollmentProgress) {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        if (!fingerprintEnrollProgressViewModel.isEnrolling()) {
            Log.d("FingerprintEnrollEnrollingUdfpsFragment", "Enrollment not started yet");
            return;
        }
        int i = enrollmentProgress.steps;
        final int i2 = enrollmentProgress.remaining;
        if (i != -1) {
            int i3 = i + 1;
            int i4 = i3 - i2;
            r5 = ((i4 > 0 ? i4 : 0) * EnterpriseContainerConstants.SYSTEM_SIGNED_APP) / i3;
        }
        StringBuilder sb = new StringBuilder("updateProgress(");
        sb.append(z);
        sb.append(", ");
        sb.append(enrollmentProgress);
        sb.append("), progress:");
        Preference$$ExternalSyntheticOutline0.m(sb, r5, "FingerprintEnrollEnrollingUdfpsFragment");
        final int i5 = enrollmentProgress.steps;
        if (i5 != -1) {
            final UdfpsEnrollView udfpsEnrollView = getUdfpsEnrollView();
            if (udfpsEnrollView.mTotalSteps == -1) {
                udfpsEnrollView.mTotalSteps = i5;
            }
            udfpsEnrollView.mRemainingSteps = i2;
            udfpsEnrollView.mHandler.post(
                    new Runnable() { // from class:
                        // com.android.settings.biometrics2.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i6;
                            PointF pointF;
                            int i7;
                            int i8;
                            int i9;
                            boolean z2 = false;
                            z2 = false;
                            z2 = false;
                            final int i10 = 1;
                            final int i11 = 2;
                            UdfpsEnrollView udfpsEnrollView2 = UdfpsEnrollView.this;
                            int i12 = i2;
                            int i13 = i5;
                            UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable =
                                    udfpsEnrollView2.mFingerprintProgressDrawable;
                            udfpsEnrollProgressBarDrawable.mAfterFirstTouch = true;
                            udfpsEnrollProgressBarDrawable.updateState(i12, i13, false);
                            final UdfpsEnrollDrawable udfpsEnrollDrawable =
                                    udfpsEnrollView2.mFingerprintDrawable;
                            if (udfpsEnrollDrawable.mTotalSteps == -1) {
                                udfpsEnrollDrawable.mTotalSteps = i13;
                            }
                            if (i12 != udfpsEnrollDrawable.mRemainingSteps) {
                                udfpsEnrollDrawable.mLocationsEnrolled++;
                                if (udfpsEnrollDrawable.isCenterEnrollmentStage()) {
                                    udfpsEnrollDrawable.mCenterTouchCount++;
                                }
                            }
                            udfpsEnrollDrawable.mRemainingSteps = i12;
                            if (udfpsEnrollDrawable.isCenterEnrollmentStage()) {
                                udfpsEnrollDrawable.updateTipHintVisibility();
                            } else {
                                AnimatorSet animatorSet = udfpsEnrollDrawable.mTargetAnimatorSet;
                                if (animatorSet != null && animatorSet.isRunning()) {
                                    udfpsEnrollDrawable.mTargetAnimatorSet.end();
                                }
                                boolean z3 = udfpsEnrollDrawable.mAccessibilityEnabled;
                                if (z3
                                        || z3
                                        || (i7 = udfpsEnrollDrawable.mTotalSteps) == -1
                                        || (i8 = udfpsEnrollDrawable.mRemainingSteps) == -1
                                        || (i9 = i7 - i8)
                                                < udfpsEnrollDrawable.getStageThresholdSteps(i7, 0)
                                        || i9
                                                >= udfpsEnrollDrawable.getStageThresholdSteps(
                                                        udfpsEnrollDrawable.mTotalSteps, 1)) {
                                    pointF = new PointF(0.0f, 0.0f);
                                } else {
                                    float floatForUser =
                                            (Build.IS_ENG || Build.IS_USERDEBUG)
                                                    ? Settings.Secure.getFloatForUser(
                                                            udfpsEnrollDrawable.mContext
                                                                    .getContentResolver(),
                                                            "com.android.systemui.biometrics.UdfpsEnrollHelper.scale",
                                                            0.5f,
                                                            -2)
                                                    : 0.5f;
                                    int i14 =
                                            udfpsEnrollDrawable.mLocationsEnrolled
                                                    - udfpsEnrollDrawable.mCenterTouchCount;
                                    ArrayList arrayList =
                                            (ArrayList) udfpsEnrollDrawable.mGuidedEnrollmentPoints;
                                    PointF pointF2 = (PointF) arrayList.get(i14 % arrayList.size());
                                    pointF =
                                            new PointF(
                                                    pointF2.x * floatForUser,
                                                    pointF2.y * floatForUser);
                                }
                                float f = udfpsEnrollDrawable.mCurrentX;
                                float f2 = pointF.x;
                                if (f == f2 && udfpsEnrollDrawable.mCurrentY == pointF.y) {
                                    udfpsEnrollDrawable.updateTipHintVisibility();
                                } else {
                                    ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
                                    final int i15 = z2 ? 1 : 0;
                                    ofFloat.addUpdateListener(
                                            new ValueAnimator
                                                    .AnimatorUpdateListener() { // from class:
                                                // com.android.settings.biometrics2.ui.widget.UdfpsEnrollDrawable$$ExternalSyntheticLambda0
                                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                                public final void onAnimationUpdate(
                                                        ValueAnimator valueAnimator) {
                                                    int i16 = i15;
                                                    UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                                            udfpsEnrollDrawable;
                                                    udfpsEnrollDrawable2.getClass();
                                                    switch (i16) {
                                                        case 0:
                                                            udfpsEnrollDrawable2.mCurrentX =
                                                                    ((Float)
                                                                                    valueAnimator
                                                                                            .getAnimatedValue())
                                                                            .floatValue();
                                                            udfpsEnrollDrawable2.invalidateSelf();
                                                            break;
                                                        case 1:
                                                            udfpsEnrollDrawable2.mCurrentY =
                                                                    ((Float)
                                                                                    valueAnimator
                                                                                            .getAnimatedValue())
                                                                            .floatValue();
                                                            udfpsEnrollDrawable2.invalidateSelf();
                                                            break;
                                                        default:
                                                            udfpsEnrollDrawable2.mCurrentScale =
                                                                    (((float)
                                                                                            Math
                                                                                                    .sin(
                                                                                                            ((Float)
                                                                                                                            valueAnimator
                                                                                                                                    .getAnimatedValue())
                                                                                                                    .floatValue()))
                                                                                    * 0.25f)
                                                                            + 1.0f;
                                                            udfpsEnrollDrawable2.invalidateSelf();
                                                            break;
                                                    }
                                                }
                                            });
                                    ValueAnimator ofFloat2 =
                                            ValueAnimator.ofFloat(
                                                    udfpsEnrollDrawable.mCurrentY, pointF.y);
                                    ofFloat2.addUpdateListener(
                                            new ValueAnimator
                                                    .AnimatorUpdateListener() { // from class:
                                                // com.android.settings.biometrics2.ui.widget.UdfpsEnrollDrawable$$ExternalSyntheticLambda0
                                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                                public final void onAnimationUpdate(
                                                        ValueAnimator valueAnimator) {
                                                    int i16 = i10;
                                                    UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                                            udfpsEnrollDrawable;
                                                    udfpsEnrollDrawable2.getClass();
                                                    switch (i16) {
                                                        case 0:
                                                            udfpsEnrollDrawable2.mCurrentX =
                                                                    ((Float)
                                                                                    valueAnimator
                                                                                            .getAnimatedValue())
                                                                            .floatValue();
                                                            udfpsEnrollDrawable2.invalidateSelf();
                                                            break;
                                                        case 1:
                                                            udfpsEnrollDrawable2.mCurrentY =
                                                                    ((Float)
                                                                                    valueAnimator
                                                                                            .getAnimatedValue())
                                                                            .floatValue();
                                                            udfpsEnrollDrawable2.invalidateSelf();
                                                            break;
                                                        default:
                                                            udfpsEnrollDrawable2.mCurrentScale =
                                                                    (((float)
                                                                                            Math
                                                                                                    .sin(
                                                                                                            ((Float)
                                                                                                                            valueAnimator
                                                                                                                                    .getAnimatedValue())
                                                                                                                    .floatValue()))
                                                                                    * 0.25f)
                                                                            + 1.0f;
                                                            udfpsEnrollDrawable2.invalidateSelf();
                                                            break;
                                                    }
                                                }
                                            });
                                    long j = (pointF.x == 0.0f && pointF.y == 0.0f) ? 600L : 800L;
                                    ValueAnimator ofFloat3 =
                                            ValueAnimator.ofFloat(0.0f, 3.1415927f);
                                    ofFloat3.setDuration(j);
                                    ofFloat3.addUpdateListener(
                                            new ValueAnimator
                                                    .AnimatorUpdateListener() { // from class:
                                                // com.android.settings.biometrics2.ui.widget.UdfpsEnrollDrawable$$ExternalSyntheticLambda0
                                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                                public final void onAnimationUpdate(
                                                        ValueAnimator valueAnimator) {
                                                    int i16 = i11;
                                                    UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                                            udfpsEnrollDrawable;
                                                    udfpsEnrollDrawable2.getClass();
                                                    switch (i16) {
                                                        case 0:
                                                            udfpsEnrollDrawable2.mCurrentX =
                                                                    ((Float)
                                                                                    valueAnimator
                                                                                            .getAnimatedValue())
                                                                            .floatValue();
                                                            udfpsEnrollDrawable2.invalidateSelf();
                                                            break;
                                                        case 1:
                                                            udfpsEnrollDrawable2.mCurrentY =
                                                                    ((Float)
                                                                                    valueAnimator
                                                                                            .getAnimatedValue())
                                                                            .floatValue();
                                                            udfpsEnrollDrawable2.invalidateSelf();
                                                            break;
                                                        default:
                                                            udfpsEnrollDrawable2.mCurrentScale =
                                                                    (((float)
                                                                                            Math
                                                                                                    .sin(
                                                                                                            ((Float)
                                                                                                                            valueAnimator
                                                                                                                                    .getAnimatedValue())
                                                                                                                    .floatValue()))
                                                                                    * 0.25f)
                                                                            + 1.0f;
                                                            udfpsEnrollDrawable2.invalidateSelf();
                                                            break;
                                                    }
                                                }
                                            });
                                    AnimatorSet animatorSet2 = new AnimatorSet();
                                    udfpsEnrollDrawable.mTargetAnimatorSet = animatorSet2;
                                    animatorSet2.setInterpolator(
                                            new AccelerateDecelerateInterpolator());
                                    udfpsEnrollDrawable.mTargetAnimatorSet.setDuration(j);
                                    udfpsEnrollDrawable.mTargetAnimatorSet.addListener(
                                            udfpsEnrollDrawable.mTargetAnimListener);
                                    udfpsEnrollDrawable.mTargetAnimatorSet.playTogether(
                                            ofFloat, ofFloat2, ofFloat3);
                                    udfpsEnrollDrawable.mTargetAnimatorSet.start();
                                }
                            }
                            int i16 = udfpsEnrollDrawable.mTotalSteps;
                            if (i16 != -1
                                    && (i6 = udfpsEnrollDrawable.mRemainingSteps) != -1
                                    && i16 - i6
                                            >= udfpsEnrollDrawable.getStageThresholdSteps(i16, 2)) {
                                z2 = true;
                            }
                            if (udfpsEnrollDrawable.mShouldShowEdgeHint == z2) {
                                return;
                            }
                            udfpsEnrollDrawable.mShouldShowEdgeHint = z2;
                        }
                    });
        }
        if (r5 >= 10000) {
            if (!z) {
                this.delayedFinishRunnable.run();
                return;
            }
            FragmentActivity activity = getActivity();
            Intrinsics.checkNotNull(activity);
            activity.getMainThreadHandler().postDelayed(this.delayedFinishRunnable, 400L);
        }
    }

    public final void updateTitleAndDescription$2() {
        LottieAnimationView lottieAnimationView;
        LottieAnimationView lottieAnimationView2;
        LottieAnimationView lottieAnimationView3;
        LottieAnimationView lottieAnimationView4;
        LottieAnimationView lottieAnimationView5;
        Log.d(
                "FingerprintEnrollEnrollingUdfpsFragment",
                "updateTitleAndDescription(" + getCurrentStage$1() + ")");
        int currentStage$1 = getCurrentStage$1();
        if (currentStage$1 == -1) {
            getTitleText().setText(R.string.security_settings_fingerprint_enroll_udfps_title);
            getSubTitleText().setText(R.string.security_settings_udfps_enroll_start_message);
            String string = getString(R.string.security_settings_udfps_enroll_a11y);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            requireActivity().setTitle(string);
            return;
        }
        if (currentStage$1 == 0) {
            getTitleText().setText(R.string.security_settings_fingerprint_enroll_repeat_title);
            FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                    this._enrollingViewModel;
            Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
            if (fingerprintEnrollEnrollingViewModel.mAccessibilityManager.isEnabled()
                    || (lottieAnimationView = this.illustrationLottie) == null) {
                getSubTitleText().setText(R.string.security_settings_udfps_enroll_start_message);
                return;
            } else {
                if (this.haveShownCenterLottie) {
                    return;
                }
                this.haveShownCenterLottie = true;
                lottieAnimationView.setContentDescription(
                        getString(R.string.security_settings_sfps_enroll_finger_center_title));
                configureEnrollmentStage$1(R.raw.udfps_center_hint_lottie);
                return;
            }
        }
        if (currentStage$1 == 1) {
            getTitleText().setText(R.string.security_settings_fingerprint_enroll_repeat_title);
            FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel2 =
                    this._enrollingViewModel;
            Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel2);
            if (fingerprintEnrollEnrollingViewModel2.mAccessibilityManager.isEnabled()
                    || (lottieAnimationView2 = this.illustrationLottie) == null) {
                getSubTitleText()
                        .setText(R.string.security_settings_udfps_enroll_repeat_a11y_message);
                return;
            } else {
                if (this.haveShownGuideLottie) {
                    return;
                }
                this.haveShownGuideLottie = true;
                lottieAnimationView2.setContentDescription(
                        getString(R.string.security_settings_fingerprint_enroll_repeat_message));
                configureEnrollmentStage$1(R.raw.udfps_center_hint_lottie);
                return;
            }
        }
        if (currentStage$1 == 2) {
            getTitleText().setText(R.string.security_settings_udfps_enroll_fingertip_title);
            if (this.haveShownTipLottie
                    || (lottieAnimationView3 = this.illustrationLottie) == null) {
                return;
            }
            this.haveShownTipLottie = true;
            lottieAnimationView3.setContentDescription(
                    getString(R.string.security_settings_udfps_tip_fingerprint_help));
            configureEnrollmentStage$1(R.raw.udfps_tip_hint_lottie);
            return;
        }
        if (currentStage$1 == 3) {
            getTitleText().setText(R.string.security_settings_udfps_enroll_left_edge_title);
            if (!this.haveShownLeftEdgeLottie
                    && (lottieAnimationView4 = this.illustrationLottie) != null) {
                this.haveShownLeftEdgeLottie = true;
                lottieAnimationView4.setContentDescription(
                        getString(R.string.security_settings_udfps_side_fingerprint_help));
                configureEnrollmentStage$1(R.raw.udfps_left_edge_hint_lottie);
                return;
            } else {
                if (this.illustrationLottie == null) {
                    if (isStageHalfCompleted$1()) {
                        getSubTitleText()
                                .setText(
                                        R.string
                                                .security_settings_fingerprint_enroll_repeat_message);
                        return;
                    } else {
                        getSubTitleText()
                                .setText(R.string.security_settings_udfps_enroll_edge_message);
                        return;
                    }
                }
                return;
            }
        }
        if (currentStage$1 != 4) {
            getTitleText().setText(R.string.security_settings_fingerprint_enroll_udfps_title);
            getSubTitleText().setText(R.string.security_settings_udfps_enroll_start_message);
            String string2 = getString(R.string.security_settings_udfps_enroll_a11y);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            requireActivity().setTitle(string2);
            return;
        }
        getTitleText().setText(R.string.security_settings_udfps_enroll_right_edge_title);
        if (!this.haveShownRightEdgeLottie
                && (lottieAnimationView5 = this.illustrationLottie) != null) {
            this.haveShownRightEdgeLottie = true;
            lottieAnimationView5.setContentDescription(
                    getString(R.string.security_settings_udfps_side_fingerprint_help));
            configureEnrollmentStage$1(R.raw.udfps_right_edge_hint_lottie);
        } else if (this.illustrationLottie == null) {
            if (isStageHalfCompleted$1()) {
                getSubTitleText()
                        .setText(R.string.security_settings_fingerprint_enroll_repeat_message);
            } else {
                getSubTitleText().setText(R.string.security_settings_udfps_enroll_edge_message);
            }
        }
    }
}
