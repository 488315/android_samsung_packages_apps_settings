package com.android.settings.biometrics2.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;

import com.android.settings.R;
import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollFindSensorViewModel;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.GlifLayout;

import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollFindUdfpsFragment;",
            "Landroidx/fragment/app/Fragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollFindUdfpsFragment extends Fragment {
    public FingerprintEnrollFindSensorViewModel _viewModel;
    public GlifLayout findUdfpsView;
    public final FingerprintEnrollFindUdfpsFragment$mOnSkipClickListener$1 mOnSkipClickListener;
    public final FingerprintEnrollFindUdfpsFragment$mOnSkipClickListener$1 mOnStartClickListener;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollFindUdfpsFragment$mOnSkipClickListener$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollFindUdfpsFragment$mOnSkipClickListener$1] */
    public FingerprintEnrollFindUdfpsFragment() {
        final int i = 0;
        this.mOnSkipClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollFindUdfpsFragment$mOnSkipClickListener$1
                    public final /* synthetic */ FingerprintEnrollFindUdfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                FingerprintEnrollFindSensorViewModel
                                        fingerprintEnrollFindSensorViewModel =
                                                this.this$0._viewModel;
                                Intrinsics.checkNotNull(fingerprintEnrollFindSensorViewModel);
                                fingerprintEnrollFindSensorViewModel.onSkipButtonClick$1();
                                break;
                            default:
                                FingerprintEnrollFindSensorViewModel
                                        fingerprintEnrollFindSensorViewModel2 =
                                                this.this$0._viewModel;
                                Intrinsics.checkNotNull(fingerprintEnrollFindSensorViewModel2);
                                fingerprintEnrollFindSensorViewModel2.onStartButtonClick$1();
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mOnStartClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollFindUdfpsFragment$mOnSkipClickListener$1
                    public final /* synthetic */ FingerprintEnrollFindUdfpsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                FingerprintEnrollFindSensorViewModel
                                        fingerprintEnrollFindSensorViewModel =
                                                this.this$0._viewModel;
                                Intrinsics.checkNotNull(fingerprintEnrollFindSensorViewModel);
                                fingerprintEnrollFindSensorViewModel.onSkipButtonClick$1();
                                break;
                            default:
                                FingerprintEnrollFindSensorViewModel
                                        fingerprintEnrollFindSensorViewModel2 =
                                                this.this$0._viewModel;
                                Intrinsics.checkNotNull(fingerprintEnrollFindSensorViewModel2);
                                fingerprintEnrollFindSensorViewModel2.onStartButtonClick$1();
                                break;
                        }
                    }
                };
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        ViewModelStore store = requireActivity.getViewModelStore();
        ViewModelProvider.Factory factory = requireActivity.getDefaultViewModelProviderFactory();
        CreationExtras defaultViewModelCreationExtras =
                requireActivity.getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        ViewModelProviderImpl m =
                ProgressDialogFragment$$ExternalSyntheticOutline0.m(
                        defaultViewModelCreationExtras,
                        "defaultCreationExtras",
                        store,
                        factory,
                        defaultViewModelCreationExtras);
        KClass modelClass =
                JvmClassMappingKt.getKotlinClass(FingerprintEnrollFindSensorViewModel.class);
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        String qualifiedName = modelClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        this._viewModel =
                (FingerprintEnrollFindSensorViewModel)
                        m.getViewModel$lifecycle_viewmodel_release(
                                modelClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
        super.onAttach(context);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View inflate = inflater.inflate(R.layout.udfps_enroll_find_sensor_layout, viewGroup, false);
        Intrinsics.checkNotNull(
                inflate,
                "null cannot be cast to non-null type com.google.android.setupdesign.GlifLayout");
        GlifLayout glifLayout = (GlifLayout) inflate;
        this.findUdfpsView = glifLayout;
        return glifLayout;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        GlifLayout glifLayout = this.findUdfpsView;
        Intrinsics.checkNotNull(glifLayout);
        FingerprintEnrollFindSensorViewModel fingerprintEnrollFindSensorViewModel = this._viewModel;
        Intrinsics.checkNotNull(fingerprintEnrollFindSensorViewModel);
        boolean isEnabled = fingerprintEnrollFindSensorViewModel.mAccessibilityManager.isEnabled();
        FingerprintEnrollFindUdfpsFragment$mOnSkipClickListener$1 onSkipClickListener =
                this.mOnSkipClickListener;
        FingerprintEnrollFindUdfpsFragment$mOnSkipClickListener$1 onStartClickListener =
                this.mOnStartClickListener;
        Intrinsics.checkNotNullParameter(onSkipClickListener, "onSkipClickListener");
        Intrinsics.checkNotNullParameter(onStartClickListener, "onStartClickListener");
        TextView headerTextView = glifLayout.getHeaderTextView();
        CharSequence text = headerTextView.getText();
        CharSequence text2 =
                requireActivity.getText(R.string.security_settings_udfps_enroll_find_sensor_title);
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
                        R.string.security_settings_udfps_enroll_find_sensor_message);
        if (!TextUtils.equals(glifLayout.getDescriptionText(), text3)) {
            glifLayout.setDescriptionText(text3);
        }
        Mixin mixin = glifLayout.getMixin(FooterBarMixin.class);
        Intrinsics.checkNotNull(mixin);
        FooterBarMixin footerBarMixin = (FooterBarMixin) mixin;
        footerBarMixin.setSecondaryButton(
                new FooterButton(
                        requireActivity.getString(
                                R.string.security_settings_fingerprint_enroll_enrolling_skip),
                        null,
                        7,
                        2132083806),
                false);
        footerBarMixin.secondaryButton.onClickListener = onSkipClickListener;
        footerBarMixin.setPrimaryButton(
                new FooterButton(
                        requireActivity.getString(
                                R.string.security_settings_udfps_enroll_find_sensor_start_button),
                        null,
                        5,
                        2132083805));
        footerBarMixin.primaryButton.onClickListener = onStartClickListener;
        View findViewById = glifLayout.findViewById(R.id.illustration_lottie);
        Intrinsics.checkNotNull(findViewById);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById;
        lottieAnimationView.setOnClickListener(onStartClickListener);
        if (isEnabled) {
            lottieAnimationView.setAnimation(R.raw.udfps_edu_a11y_lottie);
        }
    }
}
