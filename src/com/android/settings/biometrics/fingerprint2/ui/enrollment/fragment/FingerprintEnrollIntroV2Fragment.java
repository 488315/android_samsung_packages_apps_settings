package com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintEnrollIntroViewModel;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintGatekeeperViewModel;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintScrollViewModel;
import com.google.android.setupcompat.template.FooterBarMixin;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lcom/android/settings/biometrics/fingerprint2/ui/enrollment/fragment/FingerprintEnrollIntroV2Fragment;", "Landroidx/fragment/app/Fragment;", "applications__sources__apps__SecSettings__android_common__SecSettings-core"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintEnrollIntroV2Fragment extends Fragment {
    public final ViewModelLazy fingerprintScrollViewModel$delegate;
    public FooterBarMixin footerBarMixin;
    public final ViewModelLazy gateKeeperViewModel$delegate;
    public TextModel textModel;
    public final ViewModelLazy viewModel$delegate;

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$special$$inlined$viewModels$default$1] */
    public FingerprintEnrollIntroV2Fragment() {
        super(R.layout.fingerprint_v2_enroll_introduction);
        final ViewModelProvider.Factory factory = null;
        Function0 function0 = new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$viewModel$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                ViewModelProvider.Factory factory2 = ViewModelProvider.Factory.this;
                return factory2 == null ? FingerprintEnrollIntroViewModel.Factory : factory2;
            }
        };
        ReflectionFactory reflectionFactory = Reflection.factory;
        this.viewModel$delegate = new ViewModelLazy(reflectionFactory.getOrCreateKotlinClass(FingerprintEnrollIntroViewModel.class), new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$special$$inlined$activityViewModels$default$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this.requireActivity().getViewModelStore();
            }
        }, function0, new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$special$$inlined$activityViewModels$default$2
            final /* synthetic */ Function0 $extrasProducer = null;

            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                CreationExtras creationExtras;
                Function0 function02 = this.$extrasProducer;
                return (function02 == null || (creationExtras = (CreationExtras) function02.mo1068invoke()) == null) ? Fragment.this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        });
        Function0 function02 = new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$fingerprintScrollViewModel$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                ViewModelProvider.Factory factory2 = ViewModelProvider.Factory.this;
                return factory2 == null ? FingerprintScrollViewModel.Factory : factory2;
            }
        };
        final ?? r3 = new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this;
            }
        };
        final Lazy lazy = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$special$$inlined$viewModels$default$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return (ViewModelStoreOwner) r3.mo1068invoke();
            }
        });
        this.fingerprintScrollViewModel$delegate = new ViewModelLazy(reflectionFactory.getOrCreateKotlinClass(FingerprintScrollViewModel.class), new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$special$$inlined$viewModels$default$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return ((ViewModelStoreOwner) Lazy.this.getValue()).getViewModelStore();
            }
        }, function02, new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$special$$inlined$viewModels$default$4
            final /* synthetic */ Function0 $extrasProducer = null;

            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                CreationExtras creationExtras;
                Function0 function03 = this.$extrasProducer;
                if (function03 != null && (creationExtras = (CreationExtras) function03.mo1068invoke()) != null) {
                    return creationExtras;
                }
                ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) Lazy.this.getValue();
                HasDefaultViewModelProviderFactory hasDefaultViewModelProviderFactory = viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory ? (HasDefaultViewModelProviderFactory) viewModelStoreOwner : null;
                return hasDefaultViewModelProviderFactory != null ? hasDefaultViewModelProviderFactory.getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
            }
        });
        this.gateKeeperViewModel$delegate = new ViewModelLazy(reflectionFactory.getOrCreateKotlinClass(FingerprintGatekeeperViewModel.class), new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$special$$inlined$activityViewModels$default$4
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$gateKeeperViewModel$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                ViewModelProvider.Factory factory2 = ViewModelProvider.Factory.this;
                return factory2 == null ? FingerprintGatekeeperViewModel.Factory : factory2;
            }
        }, new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.FingerprintEnrollIntroV2Fragment$special$$inlined$activityViewModels$default$5
            final /* synthetic */ Function0 $extrasProducer = null;

            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                CreationExtras creationExtras;
                Function0 function03 = this.$extrasProducer;
                return (function03 == null || (creationExtras = (CreationExtras) function03.mo1068invoke()) == null) ? Fragment.this.requireActivity().getDefaultViewModelCreationExtras() : creationExtras;
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View onCreateView = super.onCreateView(inflater, viewGroup, bundle);
        Intrinsics.checkNotNull(onCreateView);
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "getViewLifecycleOwner(...)");
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner), null, null, new FingerprintEnrollIntroV2Fragment$onCreateView$1$1(this, onCreateView, null), 3);
        return onCreateView;
    }
}
