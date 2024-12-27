package com.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkPolicy;
import android.net.NetworkTemplate;
import android.telephony.SubscriptionInfo;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settings.R;
import com.android.settings.datausage.lib.DataUsageLib;
import com.android.settings.datausage.lib.INetworkCycleDataRepository;
import com.android.settings.network.ProxySubscriptionManager;
import com.android.settings.network.policy.NetworkPolicyRepository;
import com.android.settings.network.telephony.TelephonyBasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.spa.framework.util.FlowsKt;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\r\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0017\u0018\u0000 ?2\u00020\u0001:\u0001?BY\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n\u0012\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u000e0\n¢\u0006\u0004\b\u0010\u0010\u0011J\u0018\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0012H\u0082@¢\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0017H\u0002¢\u0006\u0004\b\u0019\u0010\u001aJ\u0015\u0010\u001c\u001a\u0004\u0018\u00010\u001b*\u00020\u0012H\u0002¢\u0006\u0004\b\u001c\u0010\u001dJ\u0015\u0010\u001e\u001a\u0004\u0018\u00010\u001b*\u00020\u0012H\u0002¢\u0006\u0004\b\u001e\u0010\u001dJ\u0017\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016¢\u0006\u0004\b\u001f\u0010 J\u0017\u0010#\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020!H\u0016¢\u0006\u0004\b#\u0010$J\u0017\u0010'\u001a\u00020\u00142\u0006\u0010&\u001a\u00020%H\u0016¢\u0006\u0004\b'\u0010(R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0007\u0010)R\u0014\u0010\t\u001a\u00020\b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\t\u0010*R \u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\r\u0010+R \u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u000e0\n8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000f\u0010+R\u0016\u0010,\u001a\u00020\u000b8\u0002@\u0002X\u0082.¢\u0006\u0006\n\u0004\b,\u0010-R\u001d\u00103\u001a\u0004\u0018\u00010.8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102R\u001b\u00107\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b4\u00100\u001a\u0004\b5\u00106R\u001b\u0010;\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b8\u00100\u001a\u0004\b9\u0010:R\u0016\u0010=\u001a\u00020<8\u0002@\u0002X\u0082.¢\u0006\u0006\n\u0004\b=\u0010>¨\u0006@"}, d2 = {"Lcom/android/settings/datausage/DataUsageSummaryPreferenceController;", "Lcom/android/settings/network/telephony/TelephonyBasePreferenceController;", "Landroid/content/Context;", "context", ApnSettings.MVNO_NONE, "subId", "Lcom/android/settings/network/ProxySubscriptionManager;", "proxySubscriptionManager", "Lcom/android/settings/network/policy/NetworkPolicyRepository;", "networkPolicyRepository", "Lkotlin/Function1;", "Landroid/net/NetworkTemplate;", "Lcom/android/settings/datausage/lib/INetworkCycleDataRepository;", "networkCycleDataRepositoryFactory", "Lcom/android/settings/datausage/DataPlanRepositoryImpl;", "dataPlanRepositoryFactory", "<init>", "(Landroid/content/Context;ILcom/android/settings/network/ProxySubscriptionManager;Lcom/android/settings/network/policy/NetworkPolicyRepository;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "Landroid/net/NetworkPolicy;", "policy", ApnSettings.MVNO_NONE, "update", "(Landroid/net/NetworkPolicy;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", ApnSettings.MVNO_NONE, "dataBarSize", "setDataBarSize", "(J)V", ApnSettings.MVNO_NONE, "getLimitInfo", "(Landroid/net/NetworkPolicy;)Ljava/lang/CharSequence;", "getWarningInfo", "getAvailabilityStatus", "(I)I", "Landroidx/preference/PreferenceScreen;", "screen", "displayPreference", "(Landroidx/preference/PreferenceScreen;)V", "Landroidx/lifecycle/LifecycleOwner;", "viewLifecycleOwner", "onViewCreated", "(Landroidx/lifecycle/LifecycleOwner;)V", "Lcom/android/settings/network/ProxySubscriptionManager;", "Lcom/android/settings/network/policy/NetworkPolicyRepository;", "Lkotlin/jvm/functions/Function1;", "defaultNetworkTemplate", "Landroid/net/NetworkTemplate;", "Landroid/telephony/SubscriptionInfo;", "subInfo$delegate", "Lkotlin/Lazy;", "getSubInfo", "()Landroid/telephony/SubscriptionInfo;", "subInfo", "networkTemplate$delegate", "getNetworkTemplate", "()Landroid/net/NetworkTemplate;", "networkTemplate", "networkCycleDataRepository$delegate", "getNetworkCycleDataRepository", "()Lcom/android/settings/datausage/lib/INetworkCycleDataRepository;", "networkCycleDataRepository", "Lcom/android/settings/datausage/DataUsageSummaryPreference;", "preference", "Lcom/android/settings/datausage/DataUsageSummaryPreference;", "Companion", "applications__sources__apps__SecSettings__android_common__SecSettings-core"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes2.dex */
public class DataUsageSummaryPreferenceController extends TelephonyBasePreferenceController {
    public static final int $stable = 8;
    private static final String KEY = "status_header";
    private static final String TAG = "DataUsageSummaryPC";
    private final Function1 dataPlanRepositoryFactory;
    private NetworkTemplate defaultNetworkTemplate;

    /* renamed from: networkCycleDataRepository$delegate, reason: from kotlin metadata */
    private final Lazy networkCycleDataRepository;
    private final Function1 networkCycleDataRepositoryFactory;
    private final NetworkPolicyRepository networkPolicyRepository;

    /* renamed from: networkTemplate$delegate, reason: from kotlin metadata */
    private final Lazy networkTemplate;
    private DataUsageSummaryPreference preference;
    private final ProxySubscriptionManager proxySubscriptionManager;

    /* renamed from: subInfo$delegate, reason: from kotlin metadata */
    private final Lazy subInfo;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DataUsageSummaryPreferenceController(Context context, int i) {
        this(context, i, null, null, null, null, 60, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final CharSequence getLimitInfo(NetworkPolicy networkPolicy) {
        if (networkPolicy.limitBytes > 0) {
            return TextUtils.expandTemplate(this.mContext.getText(R.string.cell_data_limit), DataUsageUtils.formatDataUsage(this.mContext, networkPolicy.limitBytes));
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final INetworkCycleDataRepository getNetworkCycleDataRepository() {
        return (INetworkCycleDataRepository) this.networkCycleDataRepository.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NetworkTemplate getNetworkTemplate() {
        return (NetworkTemplate) this.networkTemplate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SubscriptionInfo getSubInfo() {
        return (SubscriptionInfo) this.subInfo.getValue();
    }

    private final CharSequence getWarningInfo(NetworkPolicy networkPolicy) {
        if (networkPolicy.warningBytes > 0) {
            return TextUtils.expandTemplate(this.mContext.getText(R.string.cell_data_warning), DataUsageUtils.formatDataUsage(this.mContext, networkPolicy.warningBytes));
        }
        return null;
    }

    private final void setDataBarSize(long dataBarSize) {
        DataUsageSummaryPreference dataUsageSummaryPreference = this.preference;
        if (dataUsageSummaryPreference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
        CharSequence formatDataUsage = DataUsageUtils.formatDataUsage(this.mContext, 0L);
        CharSequence formatDataUsage2 = DataUsageUtils.formatDataUsage(this.mContext, dataBarSize);
        dataUsageSummaryPreference.mStartLabel = formatDataUsage;
        dataUsageSummaryPreference.mEndLabel = formatDataUsage2;
        dataUsageSummaryPreference.notifyChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object update(android.net.NetworkPolicy r18, kotlin.coroutines.Continuation r19) {
        /*
            Method dump skipped, instructions count: 639
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.datausage.DataUsageSummaryPreferenceController.update(android.net.NetworkPolicy, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        Preference findPreference = screen.findPreference(getPreferenceKey());
        Intrinsics.checkNotNull(findPreference);
        this.preference = (DataUsageSummaryPreference) findPreference;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int subId) {
        return getSubInfo() != null ? 0 : 2;
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
        FlowsKt.collectLatestWithLifecycle(this.networkPolicyRepository.networkPolicyFlow(getNetworkTemplate()), viewLifecycleOwner, Lifecycle.State.STARTED, new DataUsageSummaryPreferenceController$onViewCreated$1(this, null));
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

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DataUsageSummaryPreferenceController(Context context, int i, ProxySubscriptionManager proxySubscriptionManager) {
        this(context, i, proxySubscriptionManager, null, null, null, 56, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(proxySubscriptionManager, "proxySubscriptionManager");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DataUsageSummaryPreferenceController(Context context, int i, ProxySubscriptionManager proxySubscriptionManager, NetworkPolicyRepository networkPolicyRepository) {
        this(context, i, proxySubscriptionManager, networkPolicyRepository, null, null, 48, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(proxySubscriptionManager, "proxySubscriptionManager");
        Intrinsics.checkNotNullParameter(networkPolicyRepository, "networkPolicyRepository");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DataUsageSummaryPreferenceController(Context context, int i, ProxySubscriptionManager proxySubscriptionManager, NetworkPolicyRepository networkPolicyRepository, Function1 networkCycleDataRepositoryFactory) {
        this(context, i, proxySubscriptionManager, networkPolicyRepository, networkCycleDataRepositoryFactory, null, 32, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(proxySubscriptionManager, "proxySubscriptionManager");
        Intrinsics.checkNotNullParameter(networkPolicyRepository, "networkPolicyRepository");
        Intrinsics.checkNotNullParameter(networkCycleDataRepositoryFactory, "networkCycleDataRepositoryFactory");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ DataUsageSummaryPreferenceController(final android.content.Context r8, int r9, com.android.settings.network.ProxySubscriptionManager r10, com.android.settings.network.policy.NetworkPolicyRepository r11, kotlin.jvm.functions.Function1 r12, kotlin.jvm.functions.Function1 r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r7 = this;
            r15 = r14 & 4
            if (r15 == 0) goto Ld
            com.android.settings.network.ProxySubscriptionManager r10 = com.android.settings.network.ProxySubscriptionManager.getInstance(r8)
            java.lang.String r15 = "getInstance(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r15)
        Ld:
            r3 = r10
            r10 = r14 & 8
            if (r10 == 0) goto L17
            com.android.settings.network.policy.NetworkPolicyRepository r11 = new com.android.settings.network.policy.NetworkPolicyRepository
            r11.<init>(r8)
        L17:
            r4 = r11
            r10 = r14 & 16
            if (r10 == 0) goto L21
            com.android.settings.datausage.DataUsageSummaryPreferenceController$1 r12 = new com.android.settings.datausage.DataUsageSummaryPreferenceController$1
            r12.<init>()
        L21:
            r5 = r12
            r10 = r14 & 32
            if (r10 == 0) goto L28
            com.android.settings.datausage.DataUsageSummaryPreferenceController$2 r13 = new kotlin.jvm.functions.Function1() { // from class: com.android.settings.datausage.DataUsageSummaryPreferenceController.2
                static {
                    /*
                        com.android.settings.datausage.DataUsageSummaryPreferenceController$2 r0 = new com.android.settings.datausage.DataUsageSummaryPreferenceController$2
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.android.settings.datausage.DataUsageSummaryPreferenceController$2) com.android.settings.datausage.DataUsageSummaryPreferenceController.2.INSTANCE com.android.settings.datausage.DataUsageSummaryPreferenceController$2
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settings.datausage.DataUsageSummaryPreferenceController.AnonymousClass2.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settings.datausage.DataUsageSummaryPreferenceController.AnonymousClass2.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final java.lang.Object invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        com.android.settings.datausage.lib.INetworkCycleDataRepository r1 = (com.android.settings.datausage.lib.INetworkCycleDataRepository) r1
                        java.lang.String r0 = "it"
                        kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r1, r0)
                        com.android.settings.datausage.DataPlanRepositoryImpl r0 = new com.android.settings.datausage.DataPlanRepositoryImpl
                        r0.<init>(r1)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settings.datausage.DataUsageSummaryPreferenceController.AnonymousClass2.invoke(java.lang.Object):java.lang.Object");
                }
            }
        L28:
            r6 = r13
            r0 = r7
            r1 = r8
            r2 = r9
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.datausage.DataUsageSummaryPreferenceController.<init>(android.content.Context, int, com.android.settings.network.ProxySubscriptionManager, com.android.settings.network.policy.NetworkPolicyRepository, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataUsageSummaryPreferenceController(Context context, int i, ProxySubscriptionManager proxySubscriptionManager, NetworkPolicyRepository networkPolicyRepository, Function1 networkCycleDataRepositoryFactory, Function1 dataPlanRepositoryFactory) {
        super(context, KEY);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(proxySubscriptionManager, "proxySubscriptionManager");
        Intrinsics.checkNotNullParameter(networkPolicyRepository, "networkPolicyRepository");
        Intrinsics.checkNotNullParameter(networkCycleDataRepositoryFactory, "networkCycleDataRepositoryFactory");
        Intrinsics.checkNotNullParameter(dataPlanRepositoryFactory, "dataPlanRepositoryFactory");
        this.proxySubscriptionManager = proxySubscriptionManager;
        this.networkPolicyRepository = networkPolicyRepository;
        this.networkCycleDataRepositoryFactory = networkCycleDataRepositoryFactory;
        this.dataPlanRepositoryFactory = dataPlanRepositoryFactory;
        this.mSubId = i;
        Log.i(TAG, "subid : " + i);
        this.subInfo = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.settings.datausage.DataUsageSummaryPreferenceController$subInfo$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                Context context2;
                ProxySubscriptionManager proxySubscriptionManager2;
                int i2;
                context2 = ((AbstractPreferenceController) DataUsageSummaryPreferenceController.this).mContext;
                if (!DataUsageUtils.hasMobileData(context2)) {
                    return null;
                }
                proxySubscriptionManager2 = DataUsageSummaryPreferenceController.this.proxySubscriptionManager;
                i2 = ((TelephonyBasePreferenceController) DataUsageSummaryPreferenceController.this).mSubId;
                return proxySubscriptionManager2.getAccessibleSubscriptionInfo(i2);
            }
        });
        this.networkTemplate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.settings.datausage.DataUsageSummaryPreferenceController$networkTemplate$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                Context context2;
                int i2;
                context2 = ((AbstractPreferenceController) DataUsageSummaryPreferenceController.this).mContext;
                Intrinsics.checkNotNullExpressionValue(context2, "access$getMContext$p$s1319510310(...)");
                i2 = ((TelephonyBasePreferenceController) DataUsageSummaryPreferenceController.this).mSubId;
                return DataUsageLib.getMobileTemplate(context2, i2);
            }
        });
        this.networkCycleDataRepository = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.settings.datausage.DataUsageSummaryPreferenceController$networkCycleDataRepository$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                Function1 function1;
                NetworkTemplate networkTemplate;
                function1 = DataUsageSummaryPreferenceController.this.networkCycleDataRepositoryFactory;
                networkTemplate = DataUsageSummaryPreferenceController.this.getNetworkTemplate();
                return (INetworkCycleDataRepository) function1.invoke(networkTemplate);
            }
        });
    }
}
