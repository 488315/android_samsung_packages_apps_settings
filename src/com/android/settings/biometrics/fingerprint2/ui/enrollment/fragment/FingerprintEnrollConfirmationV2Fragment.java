package com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintEnrollConfirmationViewModel;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"
        },
        d2 = {
            "Lcom/android/settings/biometrics/fingerprint2/ui/enrollment/fragment/FingerprintEnrollConfirmationV2Fragment;",
            "Landroidx/fragment/app/Fragment;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollConfirmationV2Fragment extends Fragment {
    public final ViewModelLazy viewModel$delegate;

    public FingerprintEnrollConfirmationV2Fragment() {
        super(R.layout.fingerprint_enroll_finish_base);
        final ViewModelProvider.Factory factory = null;
        this.viewModel$delegate =
                new ViewModelLazy(
                        Reflection.factory.getOrCreateKotlinClass(
                                FingerprintEnrollConfirmationViewModel.class),
                        new Function0() { // from class:
                                          // com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollConfirmationV2Fragment$special$$inlined$activityViewModels$default$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return Fragment.this.requireActivity().getViewModelStore();
                            }
                        },
                        new Function0() { // from class:
                                          // com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollConfirmationV2Fragment$viewModel$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                ViewModelProvider.Factory factory2 = ViewModelProvider.Factory.this;
                                return factory2 == null
                                        ? FingerprintEnrollConfirmationViewModel.Factory
                                        : factory2;
                            }
                        },
                        new Function0() { // from class:
                                          // com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollConfirmationV2Fragment$special$$inlined$activityViewModels$default$2
                            final /* synthetic */ Function0 $extrasProducer = null;

                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                CreationExtras creationExtras;
                                Function0 function0 = this.$extrasProducer;
                                return (function0 == null
                                                || (creationExtras =
                                                                (CreationExtras)
                                                                        function0.mo1068invoke())
                                                        == null)
                                        ? Fragment.this
                                                .requireActivity()
                                                .getDefaultViewModelCreationExtras()
                                        : creationExtras;
                            }
                        });
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View onCreateView = super.onCreateView(inflater, viewGroup, bundle);
        Intrinsics.checkNotNull(onCreateView);
        GlifLayout glifLayout = (GlifLayout) onCreateView;
        glifLayout.setHeaderText(R.string.security_settings_fingerprint_enroll_finish_title);
        glifLayout.setDescriptionText(
                R.string.security_settings_fingerprint_enroll_finish_v2_message);
        FooterBarMixin footerBarMixin = (FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class);
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "getViewLifecycleOwner(...)");
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner),
                null,
                null,
                new FingerprintEnrollConfirmationV2Fragment$onCreateView$1$1(
                        this, footerBarMixin, null),
                3);
        footerBarMixin.setPrimaryButton(
                new FooterButton(
                        requireContext()
                                .getString(R.string.security_settings_fingerprint_enroll_done),
                        new FingerprintEnrollConfirmationV2Fragment$onCreateView$1$2(this, 0),
                        5,
                        2132083805));
        return onCreateView;
    }
}
