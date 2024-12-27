package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleCoroutineScopeImpl;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settings.R;
import com.android.settings.network.SubscriptionInfoListViewModel;
import com.android.settings.network.SubscriptionUtil;
import com.android.settingslib.spa.framework.util.FlowsKt;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u0012\u0010\u0014\u001a\u00020\u00052\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0005H\u0002J\u0016\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u0012J\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0016\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0087@¢\u0006\u0002\u0010\u001fJ\b\u0010 \u001a\u00020\u000eH\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000¨\u0006!²\u0006\n\u0010\"\u001a\u00020\tX\u008a\u0084\u0002"}, d2 = {"Lcom/android/settings/network/telephony/MobileNetworkPhoneNumberPreferenceController;", "Lcom/android/settings/network/telephony/TelephonyBasePreferenceController;", "context", "Landroid/content/Context;", GoodSettingsContract.EXTRA_NAME.POLICY_KEY, ApnSettings.MVNO_NONE, "(Landroid/content/Context;Ljava/lang/String;)V", "lazyViewModel", "Lkotlin/Lazy;", "Lcom/android/settings/network/SubscriptionInfoListViewModel;", "phoneNumber", "preference", "Landroidx/preference/Preference;", "displayPreference", ApnSettings.MVNO_NONE, "screen", "Landroidx/preference/PreferenceScreen;", "getAvailabilityStatus", ApnSettings.MVNO_NONE, "subId", "getFormattedPhoneNumber", "subscriptionInfo", "Landroid/telephony/SubscriptionInfo;", "getStringUnknown", "init", "fragment", "Landroidx/fragment/app/Fragment;", "onViewCreated", "viewLifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "refreshData", "(Landroid/telephony/SubscriptionInfo;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshUi", "applications__sources__apps__SecSettings__android_common__SecSettings-core", "viewModel"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class MobileNetworkPhoneNumberPreferenceController extends TelephonyBasePreferenceController {
    public static final int $stable = 8;
    private Lazy lazyViewModel;
    private String phoneNumber;
    private Preference preference;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileNetworkPhoneNumberPreferenceController(Context context, String key) {
        super(context, key);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        this.phoneNumber = new String();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getFormattedPhoneNumber(SubscriptionInfo subscriptionInfo) {
        String bidiFormattedPhoneNumber = SubscriptionUtil.getBidiFormattedPhoneNumber(this.mContext, subscriptionInfo);
        return bidiFormattedPhoneNumber != null ? bidiFormattedPhoneNumber.length() == 0 ? getStringUnknown() : bidiFormattedPhoneNumber : getStringUnknown();
    }

    private final String getStringUnknown() {
        String string = this.mContext.getString(R.string.device_info_default);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    private static final SubscriptionInfoListViewModel onViewCreated$lambda$0(Lazy lazy) {
        return (SubscriptionInfoListViewModel) lazy.getValue();
    }

    private final void refreshUi() {
        Preference preference = this.preference;
        if (preference != null) {
            preference.setSummary(this.phoneNumber);
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
        return (SubscriptionManager.isValidSubscriptionId(subId) && SubscriptionUtil.isSimHardwareVisible(this.mContext)) ? 0 : 2;
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

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$init$$inlined$viewModels$default$1] */
    public final void init(final Fragment fragment, int subId) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        final ?? r0 = new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$init$$inlined$viewModels$default$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this;
            }
        };
        final Lazy lazy = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$init$$inlined$viewModels$default$2
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
        this.lazyViewModel = new ViewModelLazy(Reflection.factory.getOrCreateKotlinClass(SubscriptionInfoListViewModel.class), new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$init$$inlined$viewModels$default$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return ((ViewModelStoreOwner) Lazy.this.getValue()).getViewModelStore();
            }
        }, new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$init$$inlined$viewModels$default$5
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
        }, new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$init$$inlined$viewModels$default$4
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
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
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
            Log.e("MobileNetworkPhoneNumberPreferenceController", "lateinit property lazyViewModel has not been initialized");
            return;
        }
        LifecycleCoroutineScopeImpl lifecycleScope = LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner);
        final ReadonlyStateFlow readonlyStateFlow = onViewCreated$lambda$0(lazy).subscriptionInfoListFlow;
        FlowsKt.collectLatestWithLifecycle(FlowKt.flowOn(new Flow() { // from class: com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$onViewCreated$$inlined$map$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$onViewCreated$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileNetworkPhoneNumberPreferenceController this$0;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                /* renamed from: com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$onViewCreated$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, MobileNetworkPhoneNumberPreferenceController mobileNetworkPhoneNumberPreferenceController) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileNetworkPhoneNumberPreferenceController;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$onViewCreated$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$onViewCreated$$inlined$map$1$2$1 r0 = (com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$onViewCreated$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$onViewCreated$$inlined$map$1$2$1 r0 = new com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$onViewCreated$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L61
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        java.util.List r6 = (java.util.List) r6
                        kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
                        java.lang.Iterable r6 = (java.lang.Iterable) r6
                        java.util.Iterator r6 = r6.iterator()
                    L3d:
                        boolean r7 = r6.hasNext()
                        if (r7 == 0) goto L55
                        java.lang.Object r7 = r6.next()
                        r2 = r7
                        android.telephony.SubscriptionInfo r2 = (android.telephony.SubscriptionInfo) r2
                        int r2 = r2.getSubscriptionId()
                        com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController r4 = r5.this$0
                        int r4 = r4.mSubId
                        if (r2 != r4) goto L3d
                        goto L56
                    L55:
                        r7 = 0
                    L56:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L61
                        return r1
                    L61:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$onViewCreated$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, Dispatchers.Default), viewLifecycleOwner, Lifecycle.State.STARTED, new MobileNetworkPhoneNumberPreferenceController$onViewCreated$2(lifecycleScope, this, null));
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
            boolean r0 = r7 instanceof com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$refreshData$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$refreshData$1 r0 = (com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$refreshData$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$refreshData$1 r0 = new com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$refreshData$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController r5 = (com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController) r5
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
            com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$refreshData$2 r2 = new com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController$refreshData$2
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
        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.network.telephony.MobileNetworkPhoneNumberPreferenceController.refreshData(android.telephony.SubscriptionInfo, kotlin.coroutines.Continuation):java.lang.Object");
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
