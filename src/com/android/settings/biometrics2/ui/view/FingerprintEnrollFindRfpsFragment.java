package com.android.settings.biometrics2.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.biometrics.fingerprint.FingerprintFindSensorAnimation;
import com.android.settings.biometrics2.ui.model.EnrollmentProgress;
import com.android.settings.biometrics2.ui.model.EnrollmentStatusMessage;
import com.android.settings.biometrics2.ui.viewmodel.DeviceRotationViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollErrorDialogViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollFindSensorViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollProgressViewModel;

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
            "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollFindRfpsFragment;",
            "Landroidx/fragment/app/Fragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollFindRfpsFragment extends Fragment {
    public FingerprintEnrollErrorDialogViewModel _errorDialogViewModel;
    public FingerprintEnrollProgressViewModel _progressViewModel;
    public DeviceRotationViewModel _rotationViewModel;
    public FingerprintEnrollFindSensorViewModel _viewModel;
    public FingerprintFindSensorAnimation animation;
    public final FingerprintEnrollFindRfpsFragment$progressObserver$1 canceledSignalObserver;
    public Object enrollingCancelSignal;
    public final FingerprintEnrollFindRfpsFragment$progressObserver$1 errorMessageObserver;
    public GlifLayout findRfpsView;
    public final FingerprintEnrollFindRfpsFragment$onSkipClickListener$1 onSkipClickListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.android.settings.biometrics2.ui.view.FingerprintEnrollFindRfpsFragment$onSkipClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FingerprintEnrollFindSensorViewModel fingerprintEnrollFindSensorViewModel =
                            FingerprintEnrollFindRfpsFragment.this._viewModel;
                    Intrinsics.checkNotNull(fingerprintEnrollFindSensorViewModel);
                    fingerprintEnrollFindSensorViewModel.onSkipButtonClick$1();
                }
            };
    public final FingerprintEnrollFindRfpsFragment$progressObserver$1 progressObserver;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollFindRfpsFragment$onSkipClickListener$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollFindRfpsFragment$progressObserver$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollFindRfpsFragment$progressObserver$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollFindRfpsFragment$progressObserver$1] */
    public FingerprintEnrollFindRfpsFragment() {
        final int i = 0;
        this.progressObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollFindRfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollFindRfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null && enrollmentProgress.steps != -1) {
                                    this.this$0.cancelEnrollment$3(true);
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollFindRfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollFindRfpsFragment
                                            fingerprintEnrollFindRfpsFragment = this.this$0;
                                    fingerprintEnrollFindRfpsFragment.cancelEnrollment$3(false);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollFindRfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollFindRfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollFindRfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            default:
                                if (obj != null) {
                                    FingerprintEnrollFindRfpsFragment
                                            fingerprintEnrollFindRfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollFindRfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollFindRfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollFindRfpsFragment2.enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollFindRfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        EnrollmentProgress enrollmentProgress2 =
                                                (EnrollmentProgress)
                                                        fingerprintEnrollProgressViewModel
                                                                .mProgressLiveData.getValue();
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollFindRfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollFindRfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel3 =
                                                        fingerprintEnrollFindRfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel3);
                                        fingerprintEnrollProgressViewModel3.clearProgressLiveData();
                                        if (enrollmentProgress2 != null
                                                && enrollmentProgress2.steps != -1) {
                                            FingerprintEnrollFindSensorViewModel
                                                    fingerprintEnrollFindSensorViewModel =
                                                            fingerprintEnrollFindRfpsFragment2
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
                        }
                    }
                };
        final int i2 = 1;
        this.errorMessageObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollFindRfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollFindRfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i2) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null && enrollmentProgress.steps != -1) {
                                    this.this$0.cancelEnrollment$3(true);
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollFindRfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollFindRfpsFragment
                                            fingerprintEnrollFindRfpsFragment = this.this$0;
                                    fingerprintEnrollFindRfpsFragment.cancelEnrollment$3(false);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollFindRfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollFindRfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollFindRfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            default:
                                if (obj != null) {
                                    FingerprintEnrollFindRfpsFragment
                                            fingerprintEnrollFindRfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollFindRfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollFindRfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollFindRfpsFragment2.enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollFindRfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        EnrollmentProgress enrollmentProgress2 =
                                                (EnrollmentProgress)
                                                        fingerprintEnrollProgressViewModel
                                                                .mProgressLiveData.getValue();
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollFindRfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollFindRfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel3 =
                                                        fingerprintEnrollFindRfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel3);
                                        fingerprintEnrollProgressViewModel3.clearProgressLiveData();
                                        if (enrollmentProgress2 != null
                                                && enrollmentProgress2.steps != -1) {
                                            FingerprintEnrollFindSensorViewModel
                                                    fingerprintEnrollFindSensorViewModel =
                                                            fingerprintEnrollFindRfpsFragment2
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
                        }
                    }
                };
        final int i3 = 2;
        this.canceledSignalObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollFindRfpsFragment$progressObserver$1
                    public final /* synthetic */ FingerprintEnrollFindRfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i3) {
                            case 0:
                                EnrollmentProgress enrollmentProgress = (EnrollmentProgress) obj;
                                if (enrollmentProgress != null && enrollmentProgress.steps != -1) {
                                    this.this$0.cancelEnrollment$3(true);
                                    break;
                                }
                                break;
                            case 1:
                                EnrollmentStatusMessage enrollmentStatusMessage =
                                        (EnrollmentStatusMessage) obj;
                                Log.d(
                                        "FingerprintEnrollFindRfpsFragment",
                                        "errorMessageObserver(" + enrollmentStatusMessage + ")");
                                if (enrollmentStatusMessage != null) {
                                    FingerprintEnrollFindRfpsFragment
                                            fingerprintEnrollFindRfpsFragment = this.this$0;
                                    fingerprintEnrollFindRfpsFragment.cancelEnrollment$3(false);
                                    BuildersKt.launch$default(
                                            LifecycleOwnerKt.getLifecycleScope(
                                                    fingerprintEnrollFindRfpsFragment),
                                            null,
                                            null,
                                            new FingerprintEnrollFindRfpsFragment$onEnrollmentError$1(
                                                    enrollmentStatusMessage,
                                                    fingerprintEnrollFindRfpsFragment,
                                                    null),
                                            3);
                                    break;
                                }
                                break;
                            default:
                                if (obj != null) {
                                    FingerprintEnrollFindRfpsFragment
                                            fingerprintEnrollFindRfpsFragment2 = this.this$0;
                                    Log.d(
                                            "FingerprintEnrollFindRfpsFragment",
                                            "onEnrollmentCanceled enrolling:"
                                                    + fingerprintEnrollFindRfpsFragment2
                                                            .enrollingCancelSignal
                                                    + ", canceled:"
                                                    + obj);
                                    if (fingerprintEnrollFindRfpsFragment2.enrollingCancelSignal
                                            == obj) {
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel =
                                                        fingerprintEnrollFindRfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
                                        EnrollmentProgress enrollmentProgress2 =
                                                (EnrollmentProgress)
                                                        fingerprintEnrollProgressViewModel
                                                                .mProgressLiveData.getValue();
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel2 =
                                                        fingerprintEnrollFindRfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel2);
                                        fingerprintEnrollProgressViewModel2.mCanceledSignalLiveData
                                                .removeObserver(
                                                        fingerprintEnrollFindRfpsFragment2
                                                                .canceledSignalObserver);
                                        FingerprintEnrollProgressViewModel
                                                fingerprintEnrollProgressViewModel3 =
                                                        fingerprintEnrollFindRfpsFragment2
                                                                ._progressViewModel;
                                        Intrinsics.checkNotNull(
                                                fingerprintEnrollProgressViewModel3);
                                        fingerprintEnrollProgressViewModel3.clearProgressLiveData();
                                        if (enrollmentProgress2 != null
                                                && enrollmentProgress2.steps != -1) {
                                            FingerprintEnrollFindSensorViewModel
                                                    fingerprintEnrollFindSensorViewModel =
                                                            fingerprintEnrollFindRfpsFragment2
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
                        }
                    }
                };
    }

    public final void cancelEnrollment$3(boolean z) {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        if (!fingerprintEnrollProgressViewModel.isEnrolling()) {
            Log.d(
                    "FingerprintEnrollFindRfpsFragment",
                    "cancelEnrollment(), failed because isEnrolling is false");
            return;
        }
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel2 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel2);
        fingerprintEnrollProgressViewModel2.mProgressLiveData.removeObserver(this.progressObserver);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel3 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel3);
        fingerprintEnrollProgressViewModel3.mHelpMessageLiveData.removeObserver(
                this.errorMessageObserver);
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
                "FingerprintEnrollFindRfpsFragment",
                "cancelEnrollment(), failed to cancel enrollment");
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
        this._errorDialogViewModel =
                (FingerprintEnrollErrorDialogViewModel)
                        viewModelProvider.get(FingerprintEnrollErrorDialogViewModel.class);
        super.onAttach(context);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View inflate = inflater.inflate(R.layout.fingerprint_enroll_find_sensor, viewGroup, false);
        Intrinsics.checkNotNull(
                inflate,
                "null cannot be cast to non-null type com.google.android.setupdesign.GlifLayout");
        GlifLayout glifLayout = (GlifLayout) inflate;
        this.findRfpsView = glifLayout;
        KeyEvent.Callback findViewById =
                glifLayout.findViewById(R.id.fingerprint_sensor_location_animation);
        if (findViewById instanceof FingerprintFindSensorAnimation) {
            this.animation = (FingerprintFindSensorAnimation) findViewById;
        }
        GlifLayout glifLayout2 = this.findRfpsView;
        Intrinsics.checkNotNull(glifLayout2);
        return glifLayout2;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroy() {
        FingerprintFindSensorAnimation fingerprintFindSensorAnimation = this.animation;
        if (fingerprintFindSensorAnimation != null) {
            fingerprintFindSensorAnimation.stopAnimation();
        }
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        FingerprintFindSensorAnimation fingerprintFindSensorAnimation = this.animation;
        if (fingerprintFindSensorAnimation != null) {
            fingerprintFindSensorAnimation.pauseAnimation();
        }
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        FingerprintFindSensorAnimation fingerprintFindSensorAnimation;
        DeviceRotationViewModel deviceRotationViewModel = this._rotationViewModel;
        Intrinsics.checkNotNull(deviceRotationViewModel);
        LiveData liveData = deviceRotationViewModel.getLiveData();
        Intrinsics.checkNotNullExpressionValue(liveData, "getLiveData(...)");
        Object value = liveData.getValue();
        Intrinsics.checkNotNull(value);
        ((Number) value).intValue();
        FingerprintEnrollErrorDialogViewModel fingerprintEnrollErrorDialogViewModel =
                this._errorDialogViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollErrorDialogViewModel);
        if (!fingerprintEnrollErrorDialogViewModel._isDialogShown.getValue()
                && (fingerprintFindSensorAnimation = this.animation) != null) {
            Log.d("FingerprintEnrollFindRfpsFragment", "onResume(), start animation");
            fingerprintFindSensorAnimation.startAnimation();
        }
        super.onResume();
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
                "FingerprintEnrollFindRfpsFragment");
        if (value) {
            return;
        }
        startEnrollment$4();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        fingerprintEnrollProgressViewModel.mProgressLiveData.removeObserver(this.progressObserver);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel2 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel2);
        fingerprintEnrollProgressViewModel2.mHelpMessageLiveData.removeObserver(
                this.errorMessageObserver);
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel3 =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel3);
        boolean isEnrolling = fingerprintEnrollProgressViewModel3.isEnrolling();
        boolean isChangingConfigurations = requireActivity().isChangingConfigurations();
        Utils$$ExternalSyntheticOutline0.m653m(
                "onStop(), enrolling:",
                isEnrolling,
                " isConfigChange:",
                isChangingConfigurations,
                "FingerprintEnrollFindRfpsFragment");
        if (!isEnrolling || isChangingConfigurations) {
            return;
        }
        cancelEnrollment$3(false);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        GlifLayout glifLayout = this.findRfpsView;
        Intrinsics.checkNotNull(glifLayout);
        FingerprintEnrollFindRfpsFragment$onSkipClickListener$1 onSkipClickListener =
                this.onSkipClickListener;
        Intrinsics.checkNotNullParameter(onSkipClickListener, "onSkipClickListener");
        TextView headerTextView = glifLayout.getHeaderTextView();
        CharSequence text = headerTextView.getText();
        CharSequence text2 =
                requireActivity.getText(
                        R.string.security_settings_fingerprint_enroll_find_sensor_title);
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
                requireActivity.getText(
                        R.string.security_settings_fingerprint_enroll_find_sensor_message);
        if (!TextUtils.equals(glifLayout.getDescriptionText(), text3)) {
            glifLayout.setDescriptionText(text3);
        }
        FooterBarMixin footerBarMixin = (FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class);
        FooterButton footerButton =
                new FooterButton(
                        requireActivity.getString(
                                R.string.security_settings_fingerprint_enroll_enrolling_skip),
                        null,
                        7,
                        2132083806);
        footerButton.onClickListener = onSkipClickListener;
        footerBarMixin.setSecondaryButton(footerButton, false);
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(this),
                null,
                null,
                new FingerprintEnrollFindRfpsFragment$onViewCreated$1(this, null),
                3);
    }

    public final void startEnrollment$4() {
        FingerprintEnrollProgressViewModel fingerprintEnrollProgressViewModel =
                this._progressViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollProgressViewModel);
        Object startEnrollment = fingerprintEnrollProgressViewModel.startEnrollment(1);
        this.enrollingCancelSignal = startEnrollment;
        if (startEnrollment == null) {
            Log.e(
                    "FingerprintEnrollFindRfpsFragment",
                    "startEnrollment(), failed to start enrollment");
        } else {
            Log.d("FingerprintEnrollFindRfpsFragment", "startEnrollment(), success");
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
