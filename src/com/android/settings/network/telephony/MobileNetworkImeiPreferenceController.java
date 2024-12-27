package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settings.R;
import com.android.settings.deviceinfo.imei.ImeiInfoDialogFragment;
import com.android.settings.network.SubscriptionInfoListViewModel;
import com.android.settings.network.SubscriptionUtil;
import com.android.settingslib.Utils;
import com.android.settingslib.spa.framework.util.FlowsKt;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 ,2\u00020\u0001:\u0001,B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u0012H\u0016J\b\u0010\u001a\u001a\u00020\u0005H\u0002J\u0006\u0010\u001b\u001a\u00020\u0012J\b\u0010\u001c\u001a\u00020\u0005H\u0002J\b\u0010\u001d\u001a\u00020\u0005H\u0002J\b\u0010\u001e\u001a\u00020\u0005H\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0016\u0010!\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u0012J\b\u0010\"\u001a\u00020 H\u0002J\b\u0010#\u001a\u00020 H\u0004J\u0010\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020&H\u0016J\u0016\u0010'\u001a\u00020\u00152\u0006\u0010(\u001a\u00020)H\u0087@¢\u0006\u0002\u0010*J\b\u0010+\u001a\u00020\u0015H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006-²\u0006\n\u0010.\u001a\u00020\fX\u008a\u0084\u0002"}, d2 = {"Lcom/android/settings/network/telephony/MobileNetworkImeiPreferenceController;", "Lcom/android/settings/network/telephony/TelephonyBasePreferenceController;", "context", "Landroid/content/Context;", GoodSettingsContract.EXTRA_NAME.POLICY_KEY, ApnSettings.MVNO_NONE, "(Landroid/content/Context;Ljava/lang/String;)V", "fragment", "Landroidx/fragment/app/Fragment;", "imei", "lazyViewModel", "Lkotlin/Lazy;", "Lcom/android/settings/network/SubscriptionInfoListViewModel;", "mTelephonyManager", "Landroid/telephony/TelephonyManager;", "preference", "Landroidx/preference/Preference;", "simSlot", ApnSettings.MVNO_NONE, UniversalCredentialUtil.AGENT_TITLE, "displayPreference", ApnSettings.MVNO_NONE, "screen", "Landroidx/preference/PreferenceScreen;", "getAvailabilityStatus", "subId", "getImei", "getPhoneType", "getTitle", "getTitleForCdmaPhone", "getTitleForGsmPhone", "handlePreferenceTreeClick", ApnSettings.MVNO_NONE, "init", "isMultiSim", "isPrimaryImei", "onViewCreated", "viewLifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "refreshData", "subscription", "Landroid/telephony/SubscriptionInfo;", "(Landroid/telephony/SubscriptionInfo;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshUi", "Companion", "applications__sources__apps__SecSettings__android_common__SecSettings-core", "viewModel"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class MobileNetworkImeiPreferenceController extends TelephonyBasePreferenceController {
    public static final int $stable = 8;
    private static final String TAG = "MobileNetworkImeiPreferenceController";
    private Fragment fragment;
    private String imei;
    private Lazy lazyViewModel;
    private TelephonyManager mTelephonyManager;
    private Preference preference;
    private int simSlot;
    private String title;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileNetworkImeiPreferenceController(Context context, String key) {
        super(context, key);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        this.simSlot = -1;
        this.imei = new String();
        this.title = new String();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getImei() {
        if (getPhoneType() == 2) {
            TelephonyManager telephonyManager = this.mTelephonyManager;
            if (telephonyManager != null) {
                String meid = telephonyManager.getMeid();
                return meid == null ? new String() : meid;
            }
            Intrinsics.throwUninitializedPropertyAccessException("mTelephonyManager");
            throw null;
        }
        TelephonyManager telephonyManager2 = this.mTelephonyManager;
        if (telephonyManager2 != null) {
            String imei = telephonyManager2.getImei();
            return imei == null ? new String() : imei;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mTelephonyManager");
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getTitle() {
        return getPhoneType() == 2 ? getTitleForCdmaPhone() : getTitleForGsmPhone();
    }

    private final String getTitleForCdmaPhone() {
        String string = this.mContext.getString(isPrimaryImei() ? R.string.meid_primary : R.string.status_meid_number);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    private final String getTitleForGsmPhone() {
        String string = this.mContext.getString(isPrimaryImei() ? R.string.imei_primary : R.string.status_imei);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    private final boolean isMultiSim() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager != null) {
            return telephonyManager.getActiveModemCount() > 1;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mTelephonyManager");
        throw null;
    }

    private static final SubscriptionInfoListViewModel onViewCreated$lambda$0(Lazy lazy) {
        return (SubscriptionInfoListViewModel) lazy.getValue();
    }

    private final void refreshUi() {
        Preference preference = this.preference;
        if (preference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
        preference.setTitle(this.title);
        Preference preference2 = this.preference;
        if (preference2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
        preference2.setSummary(this.imei);
        Preference preference3 = this.preference;
        if (preference3 != null) {
            preference3.setVisible(true);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        Preference findPreference = screen.findPreference(getPreferenceKey());
        Intrinsics.checkNotNull(findPreference);
        this.preference = findPreference;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int subId) {
        if (SubscriptionManager.isValidSubscriptionId(subId) && SubscriptionUtil.isSimHardwareVisible(this.mContext)) {
            Context mContext = this.mContext;
            Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
            if (ContextsKt.getUserManager(mContext).isAdminUser() && !Utils.isWifiOnly(this.mContext)) {
                return 0;
            }
        }
        return 2;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    public final int getPhoneType() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager != null) {
            return telephonyManager.getCurrentPhoneType();
        }
        Intrinsics.throwUninitializedPropertyAccessException("mTelephonyManager");
        throw null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        if (!Intrinsics.areEqual(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        Log.d(TAG, "handlePreferenceTreeClick:");
        Fragment fragment = this.fragment;
        if (fragment != null) {
            ImeiInfoDialogFragment.show(this.simSlot, fragment, String.valueOf(preference.getTitle()));
            return true;
        }
        Intrinsics.throwUninitializedPropertyAccessException("fragment");
        throw null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.network.telephony.MobileNetworkImeiPreferenceController$init$$inlined$viewModels$default$1] */
    public final void init(final Fragment fragment, int subId) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        this.fragment = fragment;
        final ?? r0 = new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkImeiPreferenceController$init$$inlined$viewModels$default$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this;
            }
        };
        final Lazy lazy = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkImeiPreferenceController$init$$inlined$viewModels$default$2
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
        this.lazyViewModel = new ViewModelLazy(Reflection.factory.getOrCreateKotlinClass(SubscriptionInfoListViewModel.class), new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkImeiPreferenceController$init$$inlined$viewModels$default$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return ((ViewModelStoreOwner) Lazy.this.getValue()).getViewModelStore();
            }
        }, new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkImeiPreferenceController$init$$inlined$viewModels$default$5
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
        }, new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkImeiPreferenceController$init$$inlined$viewModels$default$4
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
        this.mSubId = subId;
        TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        TelephonyManager createForSubscriptionId = telephonyManager != null ? telephonyManager.createForSubscriptionId(this.mSubId) : null;
        Intrinsics.checkNotNull(createForSubscriptionId);
        this.mTelephonyManager = createForSubscriptionId;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public final boolean isPrimaryImei() {
        TelephonyManager telephonyManager;
        String imei = getImei();
        String str = new String();
        try {
            telephonyManager = this.mTelephonyManager;
        } catch (Exception e) {
            SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m("PrimaryImei not available. ", e, TAG);
        }
        if (telephonyManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTelephonyManager");
            throw null;
        }
        String primaryImei = telephonyManager.getPrimaryImei();
        Intrinsics.checkNotNullExpressionValue(primaryImei, "getPrimaryImei(...)");
        str = primaryImei;
        return str.equals(imei) && isMultiSim();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void onViewCreated(LifecycleOwner viewLifecycleOwner) {
        Intrinsics.checkNotNullParameter(viewLifecycleOwner, "viewLifecycleOwner");
        Lazy lazy = this.lazyViewModel;
        if (lazy == null) {
            Log.e(TAG, "lateinit property lazyViewModel has not been initialized");
        } else {
            FlowsKt.collectLatestWithLifecycle(onViewCreated$lambda$0(lazy).subscriptionInfoListFlow, viewLifecycleOwner, Lifecycle.State.STARTED, new MobileNetworkImeiPreferenceController$onViewCreated$1(this, LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner), null));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object refreshData(android.telephony.SubscriptionInfo r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.settings.network.telephony.MobileNetworkImeiPreferenceController$refreshData$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.settings.network.telephony.MobileNetworkImeiPreferenceController$refreshData$1 r0 = (com.android.settings.network.telephony.MobileNetworkImeiPreferenceController$refreshData$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.network.telephony.MobileNetworkImeiPreferenceController$refreshData$1 r0 = new com.android.settings.network.telephony.MobileNetworkImeiPreferenceController$refreshData$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            com.android.settings.network.telephony.MobileNetworkImeiPreferenceController r5 = (com.android.settings.network.telephony.MobileNetworkImeiPreferenceController) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L49
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.scheduling.DefaultScheduler r7 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.network.telephony.MobileNetworkImeiPreferenceController$refreshData$2 r2 = new com.android.settings.network.telephony.MobileNetworkImeiPreferenceController$refreshData$2
            r4 = 0
            r2.<init>(r5, r6, r4)
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r7, r2, r0)
            if (r6 != r1) goto L49
            return r1
        L49:
            r5.refreshUi()
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.network.telephony.MobileNetworkImeiPreferenceController.refreshData(android.telephony.SubscriptionInfo, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
