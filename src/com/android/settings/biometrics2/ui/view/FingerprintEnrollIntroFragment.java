package com.android.settings.biometrics2.ui.view;

import android.app.Application;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleCoroutineScopeImpl;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;

import com.android.settings.R;
import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;
import com.android.settings.biometrics.ParentalControlsUtils;
import com.android.settings.biometrics2.ui.model.FingerprintEnrollIntroStatus;
import com.android.settings.biometrics2.ui.model.FingerprintEnrollable;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollIntroViewModel;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.util.DeviceHelper;
import com.google.android.setupdesign.util.DynamicColorPalette;

import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import kotlinx.coroutines.BuildersKt;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/biometrics2/ui/view/FingerprintEnrollIntroFragment;",
            "Landroidx/fragment/app/Fragment;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollIntroFragment extends Fragment {
    public FingerprintEnrollIntroViewModel _viewModel;
    public GlifLayout introView;
    public final FingerprintEnrollIntroFragment$onNextClickListener$1 onNextClickListener;
    public final FingerprintEnrollIntroFragment$onNextClickListener$1 onSkipOrCancelClickListener;
    public FooterButton primaryFooterButton;
    public FooterButton secondaryFooterButton;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollIntroFragment$onNextClickListener$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.biometrics2.ui.view.FingerprintEnrollIntroFragment$onNextClickListener$1] */
    public FingerprintEnrollIntroFragment() {
        final int i = 0;
        this.onNextClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollIntroFragment$onNextClickListener$1
                    public final /* synthetic */ FingerprintEnrollIntroFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                FragmentActivity activity = this.this$0.getActivity();
                                if (activity != null) {
                                    LifecycleCoroutineScopeImpl lifecycleScope =
                                            LifecycleOwnerKt.getLifecycleScope(activity);
                                    FingerprintEnrollIntroViewModel
                                            fingerprintEnrollIntroViewModel =
                                                    this.this$0._viewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollIntroViewModel);
                                    fingerprintEnrollIntroViewModel.onNextButtonClick(
                                            lifecycleScope);
                                    break;
                                }
                                break;
                            default:
                                FragmentActivity activity2 = this.this$0.getActivity();
                                if (activity2 != null) {
                                    LifecycleCoroutineScopeImpl lifecycleScope2 =
                                            LifecycleOwnerKt.getLifecycleScope(activity2);
                                    FingerprintEnrollIntroViewModel
                                            fingerprintEnrollIntroViewModel2 =
                                                    this.this$0._viewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollIntroViewModel2);
                                    fingerprintEnrollIntroViewModel2.onSkipOrCancelButtonClick(
                                            lifecycleScope2);
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.onSkipOrCancelClickListener =
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.biometrics2.ui.view.FingerprintEnrollIntroFragment$onNextClickListener$1
                    public final /* synthetic */ FingerprintEnrollIntroFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                FragmentActivity activity = this.this$0.getActivity();
                                if (activity != null) {
                                    LifecycleCoroutineScopeImpl lifecycleScope =
                                            LifecycleOwnerKt.getLifecycleScope(activity);
                                    FingerprintEnrollIntroViewModel
                                            fingerprintEnrollIntroViewModel =
                                                    this.this$0._viewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollIntroViewModel);
                                    fingerprintEnrollIntroViewModel.onNextButtonClick(
                                            lifecycleScope);
                                    break;
                                }
                                break;
                            default:
                                FragmentActivity activity2 = this.this$0.getActivity();
                                if (activity2 != null) {
                                    LifecycleCoroutineScopeImpl lifecycleScope2 =
                                            LifecycleOwnerKt.getLifecycleScope(activity2);
                                    FingerprintEnrollIntroViewModel
                                            fingerprintEnrollIntroViewModel2 =
                                                    this.this$0._viewModel;
                                    Intrinsics.checkNotNull(fingerprintEnrollIntroViewModel2);
                                    fingerprintEnrollIntroViewModel2.onSkipOrCancelButtonClick(
                                            lifecycleScope2);
                                    break;
                                }
                                break;
                        }
                    }
                };
    }

    public static final void access$updateFooterButtons(
            FingerprintEnrollIntroFragment fingerprintEnrollIntroFragment,
            FingerprintEnrollIntroStatus fingerprintEnrollIntroStatus) {
        FooterButton footerButton = fingerprintEnrollIntroFragment.primaryFooterButton;
        Intrinsics.checkNotNull(footerButton);
        Context context = fingerprintEnrollIntroFragment.getContext();
        FingerprintEnrollable fingerprintEnrollable = fingerprintEnrollIntroStatus.enrollableStatus;
        FingerprintEnrollable fingerprintEnrollable2 =
                FingerprintEnrollable.FINGERPRINT_ENROLLABLE_ERROR_REACH_MAX;
        boolean z = fingerprintEnrollIntroStatus.mHasScrollToBottom;
        footerButton.setText(
                context,
                fingerprintEnrollable == fingerprintEnrollable2
                        ? R.string.done
                        : z
                                ? R.string.security_settings_fingerprint_enroll_introduction_agree
                                : R.string.security_settings_face_enroll_introduction_more);
        FooterButton footerButton2 = fingerprintEnrollIntroFragment.secondaryFooterButton;
        Intrinsics.checkNotNull(footerButton2);
        FingerprintEnrollable fingerprintEnrollable3 =
                fingerprintEnrollIntroStatus.enrollableStatus;
        footerButton2.setVisibility(
                (!z || fingerprintEnrollable3 == fingerprintEnrollable2) ? 4 : 0);
        View view = fingerprintEnrollIntroFragment.getView();
        Intrinsics.checkNotNull(view);
        TextView textView = (TextView) view.requireViewById(R.id.error_text);
        int ordinal = fingerprintEnrollable3.ordinal();
        if (ordinal == 1) {
            textView.setText((CharSequence) null);
            textView.setVisibility(8);
        } else {
            if (ordinal != 2) {
                return;
            }
            textView.setText(R.string.fingerprint_intro_error_max);
            textView.setVisibility(0);
        }
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
        KClass modelClass = JvmClassMappingKt.getKotlinClass(FingerprintEnrollIntroViewModel.class);
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        String qualifiedName = modelClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        this._viewModel =
                (FingerprintEnrollIntroViewModel)
                        m.getViewModel$lifecycle_viewmodel_release(
                                modelClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
        super.onAttach(context);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View inflate = inflater.inflate(R.layout.fingerprint_enroll_introduction, viewGroup, false);
        Intrinsics.checkNotNull(
                inflate,
                "null cannot be cast to non-null type com.google.android.setupdesign.GlifLayout");
        GlifLayout glifLayout = (GlifLayout) inflate;
        this.introView = glifLayout;
        return glifLayout;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStart() {
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        GlifLayout glifLayout = this.introView;
        Intrinsics.checkNotNull(glifLayout);
        Mixin mixin = glifLayout.getMixin(FooterBarMixin.class);
        Intrinsics.checkNotNullExpressionValue(mixin, "getMixin(...)");
        FooterBarMixin footerBarMixin = (FooterBarMixin) mixin;
        FingerprintEnrollIntroViewModel fingerprintEnrollIntroViewModel = this._viewModel;
        Intrinsics.checkNotNull(fingerprintEnrollIntroViewModel);
        fingerprintEnrollIntroViewModel.updateEnrollableStatus(
                LifecycleOwnerKt.getLifecycleScope(this));
        if (footerBarMixin.primaryButton == null) {
            FooterButton footerButton =
                    new FooterButton(
                            requireContext.getString(
                                    R.string
                                            .security_settings_fingerprint_enroll_introduction_agree),
                            null,
                            6,
                            2132083805);
            footerButton.onClickListener = this.onNextClickListener;
            footerBarMixin.setPrimaryButton(footerButton);
            this.primaryFooterButton = footerButton;
        }
        if (footerBarMixin.secondaryButton == null) {
            FingerprintEnrollIntroViewModel fingerprintEnrollIntroViewModel2 = this._viewModel;
            Intrinsics.checkNotNull(fingerprintEnrollIntroViewModel2);
            FooterButton footerButton2 =
                    new FooterButton(
                            requireContext.getString(
                                    fingerprintEnrollIntroViewModel2
                                                    .request
                                                    .isAfterSuwOrSuwSuggestedAction
                                            ? R.string
                                                    .security_settings_fingerprint_enroll_introduction_cancel
                                            : R.string
                                                    .security_settings_fingerprint_enroll_introduction_no_thanks),
                            null,
                            5,
                            2132083805);
            footerButton2.onClickListener = this.onSkipOrCancelClickListener;
            footerBarMixin.setSecondaryButton(footerButton2, true);
            this.secondaryFooterButton = footerButton2;
        }
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(this),
                null,
                null,
                new FingerprintEnrollIntroFragment$collectPageStatusFlowIfNeed$1(this, null),
                3);
        super.onStart();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        GlifLayout glifLayout = this.introView;
        Intrinsics.checkNotNull(glifLayout);
        FingerprintEnrollIntroViewModel fingerprintEnrollIntroViewModel = this._viewModel;
        Intrinsics.checkNotNull(fingerprintEnrollIntroViewModel);
        boolean canAssumeUdfps =
                fingerprintEnrollIntroViewModel.fingerprintRepository.canAssumeUdfps();
        FingerprintEnrollIntroViewModel fingerprintEnrollIntroViewModel2 = this._viewModel;
        Intrinsics.checkNotNull(fingerprintEnrollIntroViewModel2);
        Application application = fingerprintEnrollIntroViewModel2.getApplication();
        fingerprintEnrollIntroViewModel2.fingerprintRepository.getClass();
        boolean z =
                RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                                application, 32, fingerprintEnrollIntroViewModel2.userId)
                        != null;
        FingerprintEnrollIntroViewModel fingerprintEnrollIntroViewModel3 = this._viewModel;
        Intrinsics.checkNotNull(fingerprintEnrollIntroViewModel3);
        Application application2 = fingerprintEnrollIntroViewModel3.getApplication();
        fingerprintEnrollIntroViewModel3.fingerprintRepository.getClass();
        boolean z2 = ParentalControlsUtils.parentConsentRequired(application2, 2) != null;
        Context context = glifLayout.getContext();
        View findViewById = glifLayout.findViewById(R.id.icon_fingerprint);
        Intrinsics.checkNotNull(findViewById);
        View findViewById2 = glifLayout.findViewById(R.id.icon_device_locked);
        Intrinsics.checkNotNull(findViewById2);
        View findViewById3 = glifLayout.findViewById(R.id.icon_trash_can);
        Intrinsics.checkNotNull(findViewById3);
        View findViewById4 = glifLayout.findViewById(R.id.icon_info);
        Intrinsics.checkNotNull(findViewById4);
        View findViewById5 = glifLayout.findViewById(R.id.icon_shield);
        Intrinsics.checkNotNull(findViewById5);
        ImageView imageView = (ImageView) findViewById5;
        View findViewById6 = glifLayout.findViewById(R.id.icon_link);
        Intrinsics.checkNotNull(findViewById6);
        View findViewById7 = glifLayout.findViewById(R.id.footer_message_6);
        Intrinsics.checkNotNull(findViewById7);
        TextView textView = (TextView) findViewById7;
        PorterDuffColorFilter porterDuffColorFilter =
                new PorterDuffColorFilter(
                        DynamicColorPalette.getColor(context), PorterDuff.Mode.SRC_IN);
        ((ImageView) findViewById).getDrawable().setColorFilter(porterDuffColorFilter);
        ((ImageView) findViewById2).getDrawable().setColorFilter(porterDuffColorFilter);
        ((ImageView) findViewById3).getDrawable().setColorFilter(porterDuffColorFilter);
        ((ImageView) findViewById4).getDrawable().setColorFilter(porterDuffColorFilter);
        imageView.getDrawable().setColorFilter(porterDuffColorFilter);
        ((ImageView) findViewById6).getDrawable().setColorFilter(porterDuffColorFilter);
        View findViewById8 = glifLayout.findViewById(R.id.footer_learn_more);
        Intrinsics.checkNotNull(findViewById8);
        TextView textView2 = (TextView) findViewById8;
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
        String string =
                context.getString(
                        R.string
                                .security_settings_fingerprint_v2_enroll_introduction_message_learn_more,
                        0);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        textView2.setText(Html.fromHtml(string));
        if (canAssumeUdfps) {
            textView.setVisibility(0);
            imageView.setVisibility(0);
        } else {
            textView.setVisibility(8);
            imageView.setVisibility(8);
        }
        GlifLayoutHelper glifLayoutHelper = new GlifLayoutHelper(requireActivity, glifLayout);
        if (!z || z2) {
            glifLayoutHelper.setHeaderText(
                    R.string.security_settings_fingerprint_enroll_introduction_title);
            glifLayoutHelper.setDescriptionText(
                    requireActivity.getString(
                            R.string.security_settings_fingerprint_enroll_introduction_v3_message,
                            new Object[] {DeviceHelper.getDeviceName(context)}));
        } else {
            glifLayoutHelper.setHeaderText(
                    R.string
                            .security_settings_fingerprint_enroll_introduction_title_unlock_disabled);
            final Context context2 = view.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            Object systemService = requireActivity().getSystemService(DevicePolicyManager.class);
            if (systemService == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            glifLayoutHelper.setDescriptionText(
                    ((DevicePolicyManager) systemService)
                            .getResources()
                            .getString(
                                    "Settings.FINGERPRINT_UNLOCK_DISABLED",
                                    new Supplier() { // from class:
                                                     // com.android.settings.biometrics2.ui.view.FingerprintEnrollIntroFragment$getDescriptionDisabledByAdmin$1
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            return context2.getString(
                                                    R.string
                                                            .security_settings_fingerprint_enroll_introduction_message_unlock_disabled);
                                        }
                                    }));
        }
        ScrollView scrollView = (ScrollView) glifLayout.findViewById(R.id.sud_scroll_view);
        if (scrollView == null) {
            return;
        }
        scrollView.setImportantForAccessibility(1);
    }
}
