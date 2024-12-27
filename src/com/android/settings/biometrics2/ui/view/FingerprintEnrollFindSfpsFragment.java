package com.android.settings.biometrics2.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.biometrics2.ui.model.EnrollmentProgress;
import com.android.settings.biometrics2.ui.model.EnrollmentStatusMessage;
import com.android.settings.biometrics2.ui.viewmodel.DeviceFoldedViewModel;
import com.android.settings.biometrics2.ui.viewmodel.DeviceRotationViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollErrorDialogViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollFindSensorViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollProgressViewModel;
import com.android.settingslib.widget.LottieColorUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;

import kotlin.Metadata;
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
            "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollFindSfpsFragment;",
            "Landroidx/fragment/app/Fragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollFindSfpsFragment extends Fragment {
    public FingerprintEnrollErrorDialogViewModel _errorDialogViewModel;
    public DeviceFoldedViewModel _foldedViewModel;
    public FingerprintEnrollProgressViewModel _progressViewModel;
    public DeviceRotationViewModel _rotationViewModel;
    public FingerprintEnrollFindSensorViewModel _viewModel;
    public final FingerprintEnrollFindSfpsFragment$progressObserver$1 canceledSignalObserver;
    public Object enrollingCancelSignal;
    public final FingerprintEnrollFindSfpsFragment$progressObserver$1 errorMessageObserver;
    public GlifLayout findSfpsView;
    public final FingerprintEnrollFindSfpsFragment$progressObserver$1 progressObserver;
    public final FingerprintEnrollFindSfpsFragment$progressObserver$1 rotationObserver;
    public final FingerprintEnrollFindSfpsFragment$onSkipClickListener$1 onSkipClickListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.android.settings.biometrics2.ui.view.FingerprintEnrollFindSfpsFragment$onSkipClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FingerprintEnrollFindSensorViewModel fingerprintEnrollFindSensorViewModel =
                            FingerprintEnrollFindSfpsFragment.this._viewModel;
                    Intrinsics.checkNotNull(fingerprintEnrollFindSensorViewModel);
                    fingerprintEnrollFindSensorViewModel.onSkipButtonClick$1();
                }
            };
    public int animationRotation = -1;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollFindSfpsFragment$onSkipClickListener$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollFindSfpsFragment$progressObserver$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollFindSfpsFragment$progressObserver$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollFindSfpsFragment$progressObserver$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollFindSfpsFragment$progressObserver$1] */
    public FingerprintEnrollFindSfpsFragment() {
        final int i = 3;
        this.rotationObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollFindSfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollFindSfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null && enrollmentProgress.steps != -1) {
                                    this.this$0.cancelEnrollment$4(true);
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollFindSfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollFindSfpsFragment
                                            fingerprintEnrollFindSfpsFragment = this.this$0;
                                    FingerprintEnrollProgressViewModel
                                            fingerprintEnrollProgressViewModel =
                                                    fingerprintEnrollFindSfpsFragment
                                                            ._progressViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                    fingerprintEnrollProgressViewModel.cancelEnrollment();
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollFindSfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollFindSfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollFindSfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                if (obj != null) {
                                    FingerprintEnrollFindSfpsFragment
                                            fingerprintEnrollFindSfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollFindSfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollFindSfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollFindSfpsFragment2.enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollFindSfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        EnrollmentProgress enrollmentProgress2 =
                                                (EnrollmentProgress)
                                                        fingerprintEnrollProgressViewModel2
                                                                .mProgressLiveData.getValue();
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel3 =
                                                        fingerprintEnrollFindSfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel3);
                                        fingerprintEnrollProgressViewModel3.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollFindSfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel4 =
                                                        fingerprintEnrollFindSfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel4);
                                        fingerprintEnrollProgressViewModel4.clearProgressLiveData();
                                        if (enrollmentProgress2 != null
                                                && enrollmentProgress2.steps != -1) {
                                            FingerprintEnrollFindSensorViewModel
                                                    fingerprintEnrollFindSensorViewModel =
                                                            fingerprintEnrollFindSfpsFragment2
                                                                    ._viewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollFindSensorViewModel);
                                            fingerprintEnrollFindSensorViewModel
                                                    .onStartButtonClick$1();
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i2 = (intValue + 2) % 4;
                                    FingerprintEnrollFindSfpsFragment
                                            fingerprintEnrollFindSfpsFragment3 = this.this$0;
                                    if (i2
                                            == fingerprintEnrollFindSfpsFragment3
                                                    .animationRotation) {
                                        fingerprintEnrollFindSfpsFragment3.playLottieAnimation(
                                                intValue);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 0;
        this.progressObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollFindSfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollFindSfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i2) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null && enrollmentProgress.steps != -1) {
                                    this.this$0.cancelEnrollment$4(true);
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollFindSfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollFindSfpsFragment
                                            fingerprintEnrollFindSfpsFragment = this.this$0;
                                    FingerprintEnrollProgressViewModel
                                            fingerprintEnrollProgressViewModel =
                                                    fingerprintEnrollFindSfpsFragment
                                                            ._progressViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                    fingerprintEnrollProgressViewModel.cancelEnrollment();
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollFindSfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollFindSfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollFindSfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                if (obj != null) {
                                    FingerprintEnrollFindSfpsFragment
                                            fingerprintEnrollFindSfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollFindSfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollFindSfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollFindSfpsFragment2.enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollFindSfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        EnrollmentProgress enrollmentProgress2 =
                                                (EnrollmentProgress)
                                                        fingerprintEnrollProgressViewModel2
                                                                .mProgressLiveData.getValue();
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel3 =
                                                        fingerprintEnrollFindSfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel3);
                                        fingerprintEnrollProgressViewModel3.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollFindSfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel4 =
                                                        fingerprintEnrollFindSfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel4);
                                        fingerprintEnrollProgressViewModel4.clearProgressLiveData();
                                        if (enrollmentProgress2 != null
                                                && enrollmentProgress2.steps != -1) {
                                            FingerprintEnrollFindSensorViewModel
                                                    fingerprintEnrollFindSensorViewModel =
                                                            fingerprintEnrollFindSfpsFragment2
                                                                    ._viewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollFindSensorViewModel);
                                            fingerprintEnrollFindSensorViewModel
                                                    .onStartButtonClick$1();
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i22 = (intValue + 2) % 4;
                                    FingerprintEnrollFindSfpsFragment
                                            fingerprintEnrollFindSfpsFragment3 = this.this$0;
                                    if (i22
                                            == fingerprintEnrollFindSfpsFragment3
                                                    .animationRotation) {
                                        fingerprintEnrollFindSfpsFragment3.playLottieAnimation(
                                                intValue);
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
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollFindSfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollFindSfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i3) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null && enrollmentProgress.steps != -1) {
                                    this.this$0.cancelEnrollment$4(true);
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollFindSfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollFindSfpsFragment
                                            fingerprintEnrollFindSfpsFragment = this.this$0;
                                    FingerprintEnrollProgressViewModel
                                            fingerprintEnrollProgressViewModel =
                                                    fingerprintEnrollFindSfpsFragment
                                                            ._progressViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                    fingerprintEnrollProgressViewModel.cancelEnrollment();
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollFindSfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollFindSfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollFindSfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                if (obj != null) {
                                    FingerprintEnrollFindSfpsFragment
                                            fingerprintEnrollFindSfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollFindSfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollFindSfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollFindSfpsFragment2.enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollFindSfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        EnrollmentProgress enrollmentProgress2 =
                                                (EnrollmentProgress)
                                                        fingerprintEnrollProgressViewModel2
                                                                .mProgressLiveData.getValue();
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel3 =
                                                        fingerprintEnrollFindSfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel3);
                                        fingerprintEnrollProgressViewModel3.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollFindSfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel4 =
                                                        fingerprintEnrollFindSfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel4);
                                        fingerprintEnrollProgressViewModel4.clearProgressLiveData();
                                        if (enrollmentProgress2 != null
                                                && enrollmentProgress2.steps != -1) {
                                            FingerprintEnrollFindSensorViewModel
                                                    fingerprintEnrollFindSensorViewModel =
                                                            fingerprintEnrollFindSfpsFragment2
                                                                    ._viewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollFindSensorViewModel);
                                            fingerprintEnrollFindSensorViewModel
                                                    .onStartButtonClick$1();
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i22 = (intValue + 2) % 4;
                                    FingerprintEnrollFindSfpsFragment
                                            fingerprintEnrollFindSfpsFragment3 = this.this$0;
                                    if (i22
                                            == fingerprintEnrollFindSfpsFragment3
                                                    .animationRotation) {
                                        fingerprintEnrollFindSfpsFragment3.playLottieAnimation(
                                                intValue);
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
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollFindSfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollFindSfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i4) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null && enrollmentProgress.steps != -1) {
                                    this.this$0.cancelEnrollment$4(true);
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollFindSfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollFindSfpsFragment
                                            fingerprintEnrollFindSfpsFragment = this.this$0;
                                    FingerprintEnrollProgressViewModel
                                            fingerprintEnrollProgressViewModel =
                                                    fingerprintEnrollFindSfpsFragment
                                                            ._progressViewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                    fingerprintEnrollProgressViewModel.cancelEnrollment();
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollFindSfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollFindSfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollFindSfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            case 2:
                                if (obj != null) {
                                    FingerprintEnrollFindSfpsFragment
                                            fingerprintEnrollFindSfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollFindSfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollFindSfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollFindSfpsFragment2.enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollFindSfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        EnrollmentProgress enrollmentProgress2 =
                                                (EnrollmentProgress)
                                                        fingerprintEnrollProgressViewModel2
                                                                .mProgressLiveData.getValue();
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel3 =
                                                        fingerprintEnrollFindSfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel3);
                                        fingerprintEnrollProgressViewModel3.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollFindSfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel4 =
                                                        fingerprintEnrollFindSfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel4);
                                        fingerprintEnrollProgressViewModel4.clearProgressLiveData();
                                        if (enrollmentProgress2 != null
                                                && enrollmentProgress2.steps != -1) {
                                            FingerprintEnrollFindSensorViewModel
                                                    fingerprintEnrollFindSensorViewModel =
                                                            fingerprintEnrollFindSfpsFragment2
                                                                    ._viewModel;
                                            Intrinsics.checkNotNull(
                                                    fingerprintEnrollFindSensorViewModel);
                                            fingerprintEnrollFindSensorViewModel
                                                    .onStartButtonClick$1();
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                Integer num = (Integer) obj;
                                if (num != null) {
                                    int intValue = num.intValue();
                                    int i22 = (intValue + 2) % 4;
                                    FingerprintEnrollFindSfpsFragment
                                            fingerprintEnrollFindSfpsFragment3 = this.this$0;
                                    if (i22
                                            == fingerprintEnrollFindSfpsFragment3
                                                    .animationRotation) {
                                        fingerprintEnrollFindSfpsFragment3.playLottieAnimation(
                                                intValue);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
    }

    public final void cancelEnrollment$4(boolean z) {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        if (!fingerprintEnrollProgressViewModel.isEnrolling()) {
            Log.d(
                    "FingerprintEnrollFindSfpsFragment",
                    "cancelEnrollment(), failed because isEnrolling is false");
            return;
        }
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel2 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel2);
        fingerprintEnrollProgressViewModel2.mErrorMessageLiveData.removeObserver(
                this.errorMessageObserver);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel3 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel3);
        fingerprintEnrollProgressViewModel3.mProgressLiveData.removeObserver(this.progressObserver);
        if (z) {
            FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel4 =
                    this._progressViewModel;
            Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel4);
            fingerprintEnrollProgressViewModel4.mCanceledSignalLiveData.observe(
                    this, this.canceledSignalObserver);
        } else {
            this.enrollingCancelSignal = null;
        }
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel5 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel5);
        if (fingerprintEnrollProgressViewModel5.cancelEnrollment()) {
            return;
        }
        Log.e(
                "FingerprintEnrollFindSfpsFragment",
                "cancelEnrollment(), failed to cancel enrollment");
    }

    public final LottieAnimationView getIllustrationLottie$1() {
        GlifLayout glifLayout = this.findSfpsView;
        Intrinsics.checkNotNull(glifLayout);
        View findViewById = glifLayout.findViewById(R.id.illustration_lottie);
        Intrinsics.checkNotNull(findViewById);
        return (LottieAnimationView) findViewById;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity);
        this._viewModel =
                (FingerprintEnrollFindSensorViewModel)
                        viewModelProvider.get(FingerprintEnrollFindSensorViewModel.class);
        this._progressViewModel =
                (FingerprintEnrollProgressViewModel)
                        viewModelProvider.get(FingerprintEnrollProgressViewModel.class);
        this._rotationViewModel =
                (DeviceRotationViewModel) viewModelProvider.get(DeviceRotationViewModel.class);
        this._foldedViewModel =
                (DeviceFoldedViewModel) viewModelProvider.get(DeviceFoldedViewModel.class);
        this._errorDialogViewModel =
                (FingerprintEnrollErrorDialogViewModel)
                        viewModelProvider.get(FingerprintEnrollErrorDialogViewModel.class);
        super.onAttach(context);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View inflate = inflater.inflate(R.layout.sfps_enroll_find_sensor_layout, viewGroup, false);
        Intrinsics.checkNotNull(
                inflate,
                "null cannot be cast to non-null type com.google.android.setupdesign.GlifLayout");
        GlifLayout glifLayout = (GlifLayout) inflate;
        this.findSfpsView = glifLayout;
        return glifLayout;
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
        LiveData liveData = deviceRotationViewModel.getLiveData();
        Intrinsics.checkNotNullExpressionValue(liveData, "getLiveData(...)");
        Object value = liveData.getValue();
        Intrinsics.checkNotNull(value);
        playLottieAnimation(((Number) value).intValue());
        liveData.observe(this, this.rotationObserver);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        FingerprintEnrollErrorDialogViewModel fingerprintEnrollErrorDialogViewModel =
                this._errorDialogViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollErrorDialogViewModel);
        boolean value = fingerprintEnrollErrorDialogViewModel._isDialogShown.getValue();
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        Utils$$ExternalSyntheticOutline0.m653m(
                "onStart(), isEnrolling:",
                fingerprintEnrollProgressViewModel.isEnrolling(),
                ", isErrorDialog:",
                value,
                "FingerprintEnrollFindSfpsFragment");
        if (value) {
            return;
        }
        startEnrollment$5();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
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
                "FingerprintEnrollFindSfpsFragment");
        if (!isEnrolling || isChangingConfigurations) {
            return;
        }
        cancelEnrollment$4(false);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        GlifLayout glifLayout = this.findSfpsView;
        Intrinsics.checkNotNull(glifLayout);
        FingerprintEnrollFindSfpsFragment$onSkipClickListener$1 onSkipClickListener =
                this.onSkipClickListener;
        Intrinsics.checkNotNullParameter(onSkipClickListener, "onSkipClickListener");
        FooterBarMixin footerBarMixin = (FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class);
        footerBarMixin.setSecondaryButton(
                new FooterButton(
                        requireActivity.getString(
                                R.string.security_settings_fingerprint_enroll_enrolling_skip),
                        null,
                        7,
                        2132083806),
                false);
        footerBarMixin.secondaryButton.onClickListener = onSkipClickListener;
        TextView headerTextView = glifLayout.getHeaderTextView();
        CharSequence text = headerTextView.getText();
        CharSequence text2 =
                requireActivity.getText(R.string.security_settings_sfps_enroll_find_sensor_title);
        Intrinsics.checkNotNullExpressionValue(text2, "getText(...)");
        if (text != text2) {
            if (!TextUtils.isEmpty(text)) {
                headerTextView.setAccessibilityLiveRegion(1);
            }
            glifLayout.setHeaderText(text2);
            glifLayout.getHeaderTextView().setContentDescription(text2);
            requireActivity.setTitle(text2);
        }
        CharSequence text3 =
                requireActivity.getText(R.string.security_settings_sfps_enroll_find_sensor_message);
        if (!TextUtils.equals(glifLayout.getDescriptionText(), text3)) {
            glifLayout.setDescriptionText(text3);
        }
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(this),
                null,
                null,
                new FingerprintEnrollFindSfpsFragment$onViewCreated$1(this, null),
                3);
    }

    public final void playLottieAnimation(int i) {
        Boolean bool = Boolean.FALSE;
        DeviceFoldedViewModel deviceFoldedViewModel = this._foldedViewModel;
        Intrinsics.checkNotNull(deviceFoldedViewModel);
        boolean z = !bool.equals(deviceFoldedViewModel.mLiveData.getValue());
        int i2 =
                i != 1
                        ? i != 2
                                ? i != 3
                                        ? z
                                                ? R.raw.fingerprint_edu_lottie_folded_top_right
                                                : R.raw.fingerprint_edu_lottie_landscape_top_right
                                        : z
                                                ? R.raw.fingerprint_edu_lottie_folded_bottom_right
                                                : R.raw.fingerprint_edu_lottie_portrait_bottom_right
                                : z
                                        ? R.raw.fingerprint_edu_lottie_folded_bottom_left
                                        : R.raw.fingerprint_edu_lottie_landscape_bottom_left
                        : z
                                ? R.raw.fingerprint_edu_lottie_folded_top_left
                                : R.raw.fingerprint_edu_lottie_portrait_top_left;
        Preference$$ExternalSyntheticOutline0.m(
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "play lottie animation ",
                        ", previous rotation:",
                        i2,
                        this.animationRotation,
                        ", new rotation:"),
                i,
                "FingerprintEnrollFindSfpsFragment");
        this.animationRotation = i;
        getIllustrationLottie$1().setAnimation(i2);
        LottieColorUtils.applyDynamicColors(getActivity(), getIllustrationLottie$1());
        getIllustrationLottie$1().setVisibility(0);
        getIllustrationLottie$1().playAnimation$1();
    }

    public final void startEnrollment$5() {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        Object startEnrollment = fingerprintEnrollProgressViewModel.startEnrollment(1);
        this.enrollingCancelSignal = startEnrollment;
        if (startEnrollment == null) {
            Log.e(
                    "FingerprintEnrollFindSfpsFragment",
                    "startEnrollment(), failed to start enrollment");
        } else {
            Log.d("FingerprintEnrollFindSfpsFragment", "startEnrollment(), success");
        }
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel2 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel2);
        fingerprintEnrollProgressViewModel2.mProgressLiveData.observe(this, this.progressObserver);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel3 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel3);
        fingerprintEnrollProgressViewModel3.mErrorMessageLiveData.observe(
                this, this.errorMessageObserver);
    }
}
