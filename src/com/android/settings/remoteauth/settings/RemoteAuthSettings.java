package com.android.settings.remoteauth.settings;

import android.os.Bundle;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
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
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lcom/android/settings/remoteauth/settings/RemoteAuthSettings;", "Landroidx/fragment/app/Fragment;", "<init>", "()V", "applications__sources__apps__SecSettings__android_common__SecSettings-core"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class RemoteAuthSettings extends Fragment {
    public final RemoteAuthSettingsRecyclerViewAdapter adapter;
    public final Lazy addAuthenticatorLayout$delegate;
    public final Lazy recyclerView$delegate;
    public final ViewModelLazy viewModel$delegate;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.remoteauth.settings.RemoteAuthSettings$special$$inlined$viewModels$default$1] */
    public RemoteAuthSettings() {
        super(R.layout.remote_auth_settings);
        final ?? r0 = new Function0() { // from class: com.android.settings.remoteauth.settings.RemoteAuthSettings$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this;
            }
        };
        final Lazy lazy = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.android.settings.remoteauth.settings.RemoteAuthSettings$special$$inlined$viewModels$default$2
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
        this.viewModel$delegate = new ViewModelLazy(Reflection.factory.getOrCreateKotlinClass(RemoteAuthSettingsViewModel.class), new Function0() { // from class: com.android.settings.remoteauth.settings.RemoteAuthSettings$special$$inlined$viewModels$default$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return ((ViewModelStoreOwner) Lazy.this.getValue()).getViewModelStore();
            }
        }, new Function0() { // from class: com.android.settings.remoteauth.settings.RemoteAuthSettings$special$$inlined$viewModels$default$5
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
        }, new Function0() { // from class: com.android.settings.remoteauth.settings.RemoteAuthSettings$special$$inlined$viewModels$default$4
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
        RemoteAuthSettingsRecyclerViewAdapter remoteAuthSettingsRecyclerViewAdapter = new RemoteAuthSettingsRecyclerViewAdapter();
        remoteAuthSettingsRecyclerViewAdapter.uiStates = EmptyList.INSTANCE;
        this.adapter = remoteAuthSettingsRecyclerViewAdapter;
        this.recyclerView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.settings.remoteauth.settings.RemoteAuthSettings$recyclerView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                View view = RemoteAuthSettings.this.getView();
                Intrinsics.checkNotNull(view);
                return (RecyclerView) view.requireViewById(R.id.registered_authenticator_list);
            }
        });
        this.addAuthenticatorLayout$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.settings.remoteauth.settings.RemoteAuthSettings$addAuthenticatorLayout$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                View view = RemoteAuthSettings.this.getView();
                Intrinsics.checkNotNull(view);
                return (ConstraintLayout) view.requireViewById(R.id.add_authenticator_layout);
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        RecyclerView recyclerView = (RecyclerView) this.recyclerView$delegate.getValue();
        getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        ((RecyclerView) this.recyclerView$delegate.getValue()).setAdapter(this.adapter);
        ((ConstraintLayout) this.addAuthenticatorLayout$delegate.getValue()).setOnClickListener(new View.OnClickListener() { // from class: com.android.settings.remoteauth.settings.RemoteAuthSettings$onViewCreated$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                NavHostFragment.Companion.findNavController(RemoteAuthSettings.this).navigate(R.id.action_settings_to_introduction);
            }
        });
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new RemoteAuthSettings$onViewCreated$2(this, null), 3);
    }
}
