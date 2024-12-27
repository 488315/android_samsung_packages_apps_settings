package com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.education;

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
import com.android.settings.biometrics.fingerprint.FingerprintFindSensorAnimation;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintEnrollFindSensorViewModel;

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
            "\u0000\u0014\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003B\u0011\b\u0017\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0002\u0010\u0006¨\u0006\u0007"
        },
        d2 = {
            "Lcom/android/settings/biometrics/fingerprint2/ui/enrollment/fragment/education/RfpsEnrollFindSensorFragment;",
            "Landroidx/fragment/app/Fragment;",
            "<init>",
            "()V",
            "Landroidx/lifecycle/ViewModelProvider$Factory;",
            "theFactory",
            "(Landroidx/lifecycle/ViewModelProvider$Factory;)V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class RfpsEnrollFindSensorFragment extends Fragment {
    public FingerprintFindSensorAnimation animation;
    public final ViewModelProvider.Factory factory;
    public final ViewModelLazy viewModel$delegate;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RfpsEnrollFindSensorFragment(ViewModelProvider.Factory theFactory) {
        this();
        Intrinsics.checkNotNullParameter(theFactory, "theFactory");
        this.factory = theFactory;
    }

    public static final FingerprintEnrollFindSensorViewModel access$getViewModel(
            RfpsEnrollFindSensorFragment rfpsEnrollFindSensorFragment) {
        return (FingerprintEnrollFindSensorViewModel)
                rfpsEnrollFindSensorFragment.viewModel$delegate.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View inflate =
                inflater.inflate(R.layout.fingerprint_v2_enroll_find_sensor, viewGroup, false);
        Intrinsics.checkNotNull(inflate);
        GlifLayout glifLayout = (GlifLayout) inflate;
        glifLayout.setHeaderText(R.string.security_settings_fingerprint_enroll_find_sensor_title);
        glifLayout.setDescriptionText(
                R.string.security_settings_fingerprint_enroll_find_sensor_message);
        FooterBarMixin footerBarMixin = (FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class);
        Intrinsics.checkNotNull(footerBarMixin);
        footerBarMixin.setSecondaryButton(
                new FooterButton(
                        requireActivity()
                                .getString(
                                        R.string
                                                .security_settings_fingerprint_enroll_enrolling_skip),
                        new RfpsEnrollFindSensorFragment$setupPrimaryButton$1(this, 1),
                        7,
                        2132083806),
                false);
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "getViewLifecycleOwner(...)");
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner),
                null,
                null,
                new RfpsEnrollFindSensorFragment$onCreateView$1(this, null),
                3);
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(this),
                null,
                null,
                new RfpsEnrollFindSensorFragment$onCreateView$2(this, footerBarMixin, null),
                3);
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(this),
                null,
                null,
                new RfpsEnrollFindSensorFragment$onCreateView$3(this, glifLayout, null),
                3);
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(this),
                null,
                null,
                new RfpsEnrollFindSensorFragment$onCreateView$4(this, null),
                3);
        return glifLayout;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroy() {
        FingerprintFindSensorAnimation fingerprintFindSensorAnimation = this.animation;
        if (fingerprintFindSensorAnimation != null) {
            fingerprintFindSensorAnimation.stopAnimation();
        }
        super.onDestroy();
    }

    public RfpsEnrollFindSensorFragment() {
        this.viewModel$delegate =
                new ViewModelLazy(
                        Reflection.factory.getOrCreateKotlinClass(
                                FingerprintEnrollFindSensorViewModel.class),
                        new Function0() { // from class:
                                          // com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.education.RfpsEnrollFindSensorFragment$special$$inlined$activityViewModels$default$1
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
                                          // com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.education.RfpsEnrollFindSensorFragment$viewModel$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                ViewModelProvider.Factory factory =
                                        RfpsEnrollFindSensorFragment.this.factory;
                                return factory == null
                                        ? FingerprintEnrollFindSensorViewModel.Factory
                                        : factory;
                            }
                        },
                        new Function0() { // from class:
                                          // com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.education.RfpsEnrollFindSensorFragment$special$$inlined$activityViewModels$default$2
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
}
