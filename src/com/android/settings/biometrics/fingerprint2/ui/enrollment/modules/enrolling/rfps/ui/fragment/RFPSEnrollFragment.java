package com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.android.settings.R;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.viewmodel.RFPSIconTouchViewModel;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.viewmodel.RFPSViewModel;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.widget.RFPSProgressBar;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.BackgroundViewModel;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintNavigationStep$Enrollment;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003B\u0011\b\u0017\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/android/settings/biometrics/fingerprint2/ui/enrollment/modules/enrolling/rfps/ui/fragment/RFPSEnrollFragment;", "Landroidx/fragment/app/Fragment;", "<init>", "()V", "Landroidx/lifecycle/ViewModelProvider$Factory;", "theFactory", "(Landroidx/lifecycle/ViewModelProvider$Factory;)V", "applications__sources__apps__SecSettings__android_common__SecSettings-core"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class RFPSEnrollFragment extends Fragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ViewModelLazy backgroundViewModel$delegate;
    public final ViewModelProvider.Factory factory;
    public final ViewModelLazy iconTouchViewModel$delegate;
    public RFPSProgressBar progressBar;
    public final ViewModelLazy rfpsViewModel$delegate;
    public TextView textView;

    static {
        Reflection.factory.getOrCreateKotlinClass(FingerprintNavigationStep$Enrollment.class);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RFPSEnrollFragment(ViewModelProvider.Factory theFactory) {
        this();
        Intrinsics.checkNotNullParameter(theFactory, "theFactory");
        this.factory = theFactory;
    }

    public final RFPSViewModel getRfpsViewModel() {
        return (RFPSViewModel) this.rfpsViewModel$delegate.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View onCreateView = super.onCreateView(inflater, viewGroup, bundle);
        Intrinsics.checkNotNull(onCreateView);
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        View requireViewById = onCreateView.requireViewById(R.id.setup_wizard_layout);
        Intrinsics.checkNotNull(requireViewById, "null cannot be cast to non-null type com.google.android.setupdesign.GlifLayout");
        GlifLayout glifLayout = (GlifLayout) requireViewById;
        glifLayout.setDescriptionText(R.string.security_settings_fingerprint_enroll_start_message);
        glifLayout.setHeaderText(R.string.security_settings_fingerprint_enroll_repeat_title);
        Intrinsics.checkNotNullExpressionValue(AnimationUtils.loadInterpolator(requireContext, android.R.interpolator.fast_out_linear_in), "loadInterpolator(...)");
        Intrinsics.checkNotNullExpressionValue(AnimationUtils.loadInterpolator(requireContext, android.R.interpolator.linear_out_slow_in), "loadInterpolator(...)");
        View requireViewById2 = onCreateView.requireViewById(R.id.text);
        Intrinsics.checkNotNull(requireViewById2, "null cannot be cast to non-null type android.widget.TextView");
        View requireViewById3 = onCreateView.requireViewById(R.id.fingerprint_progress_bar);
        Intrinsics.checkNotNull(requireViewById3, "null cannot be cast to non-null type com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.widget.RFPSProgressBar");
        this.progressBar = (RFPSProgressBar) requireViewById3;
        FooterBarMixin footerBarMixin = (FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class);
        footerBarMixin.setSecondaryButton(new FooterButton(requireContext.getString(R.string.security_settings_fingerprint_enroll_enrolling_skip), new View.OnClickListener() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$onCreateView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StateFlowImpl stateFlowImpl;
                Object value;
                RFPSEnrollFragment rFPSEnrollFragment = RFPSEnrollFragment.this;
                int i = RFPSEnrollFragment.$r8$clinit;
                RFPSViewModel rfpsViewModel = rFPSEnrollFragment.getRfpsViewModel();
                do {
                    stateFlowImpl = rfpsViewModel._textViewIsVisible;
                    value = stateFlowImpl.getValue();
                    ((Boolean) value).getClass();
                } while (!stateFlowImpl.compareAndSet(value, Boolean.FALSE));
                throw null;
            }
        }, 7, 2132083806), false);
        footerBarMixin.buttonContainer.setBackgroundColor(0);
        RFPSProgressBar rFPSProgressBar = this.progressBar;
        if (rFPSProgressBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progressBar");
            throw null;
        }
        rFPSProgressBar.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$onCreateView$2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                StateFlowImpl stateFlowImpl;
                Object value;
                if (motionEvent.getActionMasked() == 0) {
                    RFPSIconTouchViewModel rFPSIconTouchViewModel = (RFPSIconTouchViewModel) RFPSEnrollFragment.this.iconTouchViewModel$delegate.getValue();
                    do {
                        stateFlowImpl = rFPSIconTouchViewModel._touches;
                        value = stateFlowImpl.getValue();
                        ((Number) value).intValue();
                    } while (!stateFlowImpl.compareAndSet(value, Integer.valueOf(((Number) stateFlowImpl.getValue()).intValue() + 1)));
                }
                return true;
            }
        });
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "getViewLifecycleOwner(...)");
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner), null, null, new RFPSEnrollFragment$onCreateView$3(this, null), 3);
        getRfpsViewModel();
        throw null;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$special$$inlined$viewModels$default$1] */
    public RFPSEnrollFragment() {
        super(R.layout.fingerprint_v2_rfps_enroll_enrolling);
        Function0 function0 = new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$iconTouchViewModel$2
            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return RFPSIconTouchViewModel.Factory;
            }
        };
        final ?? r1 = new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this;
            }
        };
        final Lazy lazy = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$special$$inlined$viewModels$default$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return (ViewModelStoreOwner) r1.mo1068invoke();
            }
        });
        ReflectionFactory reflectionFactory = Reflection.factory;
        this.iconTouchViewModel$delegate = new ViewModelLazy(reflectionFactory.getOrCreateKotlinClass(RFPSIconTouchViewModel.class), new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$special$$inlined$viewModels$default$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return ((ViewModelStoreOwner) Lazy.this.getValue()).getViewModelStore();
            }
        }, function0 == null ? new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$special$$inlined$viewModels$default$5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory;
                ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) lazy.getValue();
                HasDefaultViewModelProviderFactory hasDefaultViewModelProviderFactory = viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory ? (HasDefaultViewModelProviderFactory) viewModelStoreOwner : null;
                return (hasDefaultViewModelProviderFactory == null || (defaultViewModelProviderFactory = hasDefaultViewModelProviderFactory.getDefaultViewModelProviderFactory()) == null) ? Fragment.this.getDefaultViewModelProviderFactory() : defaultViewModelProviderFactory;
            }
        } : function0, new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$special$$inlined$viewModels$default$4
            final /* synthetic */ Function0 $extrasProducer = null;

            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                CreationExtras creationExtras;
                Function0 function02 = this.$extrasProducer;
                if (function02 != null && (creationExtras = (CreationExtras) function02.mo1068invoke()) != null) {
                    return creationExtras;
                }
                ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) Lazy.this.getValue();
                HasDefaultViewModelProviderFactory hasDefaultViewModelProviderFactory = viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory ? (HasDefaultViewModelProviderFactory) viewModelStoreOwner : null;
                return hasDefaultViewModelProviderFactory != null ? hasDefaultViewModelProviderFactory.getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
            }
        });
        this.rfpsViewModel$delegate = new ViewModelLazy(reflectionFactory.getOrCreateKotlinClass(RFPSViewModel.class), new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$special$$inlined$activityViewModels$default$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$rfpsViewModel$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                ViewModelProvider.Factory factory = RFPSEnrollFragment.this.factory;
                return factory == null ? RFPSViewModel.Factory : factory;
            }
        }, new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$special$$inlined$activityViewModels$default$2
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
        new ViewModelLazy(reflectionFactory.getOrCreateKotlinClass(BackgroundViewModel.class), new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$special$$inlined$activityViewModels$default$4
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this.requireActivity().getViewModelStore();
            }
        }, new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$special$$inlined$activityViewModels$default$6
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this.requireActivity().getDefaultViewModelProviderFactory();
            }
        }, new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.fragment.RFPSEnrollFragment$special$$inlined$activityViewModels$default$5
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
    }
}
