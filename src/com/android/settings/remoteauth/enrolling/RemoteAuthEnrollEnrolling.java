package com.android.settings.remoteauth.enrolling;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settings.R;
import com.android.settings.remoteauth.RemoteAuthEnrollBase;
import com.google.android.setupcompat.template.FooterButton;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lcom/android/settings/remoteauth/enrolling/RemoteAuthEnrollEnrolling;", "Lcom/android/settings/remoteauth/RemoteAuthEnrollBase;", "<init>", "()V", "applications__sources__apps__SecSettings__android_common__SecSettings-core"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class RemoteAuthEnrollEnrolling extends RemoteAuthEnrollBase {
    public final RemoteAuthEnrollEnrollingRecyclerViewAdapter adapter;
    public final Lazy errorText$delegate;
    public final Lazy navController$delegate;
    public final Lazy progressBar$delegate;
    public final Lazy recyclerView$delegate;
    public final ViewModelLazy viewModel$delegate;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrolling$special$$inlined$viewModels$default$1] */
    public RemoteAuthEnrollEnrolling() {
        super(R.layout.remote_auth_enroll_enrolling);
        final ?? r0 = new Function0() { // from class: com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrolling$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this;
            }
        };
        final Lazy lazy = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrolling$special$$inlined$viewModels$default$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return (ViewModelStoreOwner) r0.mo1068invoke();
            }
        });
        this.viewModel$delegate = new ViewModelLazy(Reflection.factory.getOrCreateKotlinClass(RemoteAuthEnrollEnrollingViewModel.class), new Function0() { // from class: com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrolling$special$$inlined$viewModels$default$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return ((ViewModelStoreOwner) Lazy.this.getValue()).getViewModelStore();
            }
        }, new Function0() { // from class: com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrolling$special$$inlined$viewModels$default$5
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
        }, new Function0() { // from class: com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrolling$special$$inlined$viewModels$default$4
            final /* synthetic */ Function0 $extrasProducer = null;

            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                CreationExtras creationExtras;
                Function0 function0 = this.$extrasProducer;
                if (function0 != null && (creationExtras = (CreationExtras) function0.mo1068invoke()) != null) {
                    return creationExtras;
                }
                ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) Lazy.this.getValue();
                HasDefaultViewModelProviderFactory hasDefaultViewModelProviderFactory = viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory ? (HasDefaultViewModelProviderFactory) viewModelStoreOwner : null;
                return hasDefaultViewModelProviderFactory != null ? hasDefaultViewModelProviderFactory.getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
            }
        });
        this.navController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrolling$navController$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return NavHostFragment.Companion.findNavController(RemoteAuthEnrollEnrolling.this);
            }
        });
        RemoteAuthEnrollEnrollingRecyclerViewAdapter remoteAuthEnrollEnrollingRecyclerViewAdapter = new RemoteAuthEnrollEnrollingRecyclerViewAdapter();
        remoteAuthEnrollEnrollingRecyclerViewAdapter.uiStates = EmptyList.INSTANCE;
        this.adapter = remoteAuthEnrollEnrollingRecyclerViewAdapter;
        this.progressBar$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrolling$progressBar$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                View view = RemoteAuthEnrollEnrolling.this.getView();
                Intrinsics.checkNotNull(view);
                return (ProgressBar) view.requireViewById(R.id.enrolling_list_progress_bar);
            }
        });
        this.errorText$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrolling$errorText$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                View view = RemoteAuthEnrollEnrolling.this.getView();
                Intrinsics.checkNotNull(view);
                return (TextView) view.requireViewById(R.id.error_text);
            }
        });
        this.recyclerView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrolling$recyclerView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                View view = RemoteAuthEnrollEnrolling.this.getView();
                Intrinsics.checkNotNull(view);
                return (RecyclerView) view.requireViewById(R.id.discovered_authenticator_list);
            }
        });
    }

    @Override // com.android.settings.remoteauth.RemoteAuthEnrollBase
    public final FooterButton initializePrimaryFooterButton() {
        return new FooterButton(requireContext().getString(R.string.security_settings_remoteauth_enroll_enrolling_agree), new RemoteAuthEnrollEnrolling$initializePrimaryFooterButton$1(this, 0), 5, 2132083805);
    }

    @Override // com.android.settings.remoteauth.RemoteAuthEnrollBase
    public final FooterButton initializeSecondaryFooterButton() {
        return new FooterButton(requireContext().getString(R.string.security_settings_remoteauth_enroll_enrolling_disagree), new RemoteAuthEnrollEnrolling$initializePrimaryFooterButton$1(this, 1), 7, 2132083806);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        ((RemoteAuthEnrollEnrollingViewModel) this.viewModel$delegate.getValue()).discoverDevices();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        RecyclerView recyclerView = (RecyclerView) this.recyclerView$delegate.getValue();
        getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        ((RecyclerView) this.recyclerView$delegate.getValue()).setAdapter(this.adapter);
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new RemoteAuthEnrollEnrolling$onViewCreated$1(this, null), 3);
    }
}
