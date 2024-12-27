package com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.viewmodel.UdfpsViewModel;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.widget.UdfpsEnrollViewV2;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintAction;

import com.airbnb.lottie.LottieAnimationView;
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
            "Lcom/android/settings/biometrics/fingerprint2/ui/enrollment/modules/enrolling/udfps/ui/fragment/UdfpsEnrollFragment;",
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
public final class UdfpsEnrollFragment extends Fragment {
    public final ViewModelProvider.Factory factory;
    public LottieAnimationView lottie;
    public UdfpsEnrollViewV2 udfpsEnrollView;
    public final ViewModelLazy viewModel$delegate;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public UdfpsEnrollFragment(ViewModelProvider.Factory theFactory) {
        this();
        Intrinsics.checkNotNullParameter(theFactory, "theFactory");
        this.factory = theFactory;
    }

    public final UdfpsViewModel getViewModel() {
        return (UdfpsViewModel) this.viewModel$delegate.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        View findViewById = view.findViewById(R.id.illustration_lottie);
        Intrinsics.checkNotNull(findViewById);
        View findViewById2 = view.findViewById(R.id.udfps_animation_view);
        Intrinsics.checkNotNull(findViewById2);
        this.udfpsEnrollView = (UdfpsEnrollViewV2) findViewById2;
        View findViewById3 = view.findViewById(R.id.glif_layout);
        Intrinsics.checkNotNull(findViewById3);
        GlifLayout glifLayout = (GlifLayout) findViewById3;
        ColorStateList backgroundBaseColor = glifLayout.getBackgroundBaseColor();
        Window window = requireActivity().getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
        if (backgroundBaseColor == null) {
            backgroundBaseColor = glifLayout.getPrimaryColor();
        }
        int defaultColor = backgroundBaseColor.getDefaultColor();
        window.setStatusBarColor(defaultColor);
        view.setBackgroundColor(defaultColor);
        Button button = (Button) view.findViewById(R.id.skip);
        if (button != null) {
            button.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.fragment.UdfpsEnrollFragment$onViewCreated$1$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            UdfpsEnrollFragment.this.getViewModel().getClass();
                            FingerprintAction[] fingerprintActionArr = FingerprintAction.$VALUES;
                            throw null;
                        }
                    });
        }
        UdfpsEnrollViewV2 udfpsEnrollViewV2 = this.udfpsEnrollView;
        if (udfpsEnrollViewV2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("udfpsEnrollView");
            throw null;
        }
        udfpsEnrollViewV2.setFinishAnimationCompleted(
                new Function0() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.fragment.UdfpsEnrollFragment$onViewCreated$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        UdfpsEnrollFragment.this.getViewModel().getClass();
                        throw null;
                    }
                });
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "getViewLifecycleOwner(...)");
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner),
                null,
                null,
                new UdfpsEnrollFragment$onViewCreated$3(this, glifLayout, this, null),
                3);
        LifecycleOwner viewLifecycleOwner2 = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner2, "getViewLifecycleOwner(...)");
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner2),
                null,
                null,
                new UdfpsEnrollFragment$onViewCreated$4(this, null),
                3);
        LifecycleOwner viewLifecycleOwner3 = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner3, "getViewLifecycleOwner(...)");
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner3),
                null,
                null,
                new UdfpsEnrollFragment$onViewCreated$5(this, null),
                3);
        LifecycleOwner viewLifecycleOwner4 = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner4, "getViewLifecycleOwner(...)");
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner4),
                null,
                null,
                new UdfpsEnrollFragment$onViewCreated$6(this, null),
                3);
        LifecycleOwner viewLifecycleOwner5 = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner5, "getViewLifecycleOwner(...)");
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner5),
                null,
                null,
                new UdfpsEnrollFragment$onViewCreated$7(view, this, null),
                3);
        LifecycleOwner viewLifecycleOwner6 = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner6, "getViewLifecycleOwner(...)");
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner6),
                null,
                null,
                new UdfpsEnrollFragment$onViewCreated$8(this, null),
                3);
        getViewModel();
        throw null;
    }

    public UdfpsEnrollFragment() {
        super(R.layout.fingerprint_v2_udfps_enroll_enrolling);
        this.viewModel$delegate =
                new ViewModelLazy(
                        Reflection.factory.getOrCreateKotlinClass(UdfpsViewModel.class),
                        new Function0() { // from class:
                                          // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.fragment.UdfpsEnrollFragment$special$$inlined$activityViewModels$default$1
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
                                          // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.fragment.UdfpsEnrollFragment$viewModel$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                ViewModelProvider.Factory factory =
                                        UdfpsEnrollFragment.this.factory;
                                return factory == null ? UdfpsViewModel.Factory : factory;
                            }
                        },
                        new Function0() { // from class:
                                          // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.fragment.UdfpsEnrollFragment$special$$inlined$activityViewModels$default$2
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
