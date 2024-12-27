package com.samsung.android.settings.datausage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.provider.SearchIndexableData;
import android.telephony.SubscriptionManager;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.network.MobileDataEnabledListener;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.connection.ConnectionsUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBillingCycleSettings extends DashboardFragment
        implements SecBillingCycleManager.BillingCycleUpdater, MobileDataEnabledListener.Client {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.sec_billing_cycle);
    public MobileDataEnabledListener mMobileDataListener;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.datausage.SecBillingCycleSettings.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("com.samsung.intent.action.SIMHOTSWAP".equals(intent.getAction())) {
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                SecBillingCycleSettings.SEARCH_INDEX_DATA_PROVIDER;
                        Log.d("SecBillingCycleSettings", "FINISH : HOTSWAP");
                        SecBillingCycleSettings.this.finish();
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.datausage.SecBillingCycleSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            BaseSearchIndexProvider baseSearchIndexProvider =
                    SecBillingCycleSettings.SEARCH_INDEX_DATA_PROVIDER;
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            searchIndexableRaw.title = context.getString(R.string.set_data_limit);
            searchIndexableRaw.screenTitle = context.getString(R.string.billing_cycle);
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            String salesCode = Utils.getSalesCode();
            boolean z = "VZW".equals(salesCode) || "VPP".equals(salesCode);
            boolean isSprModel = Rune.isSprModel();
            if (z || isSprModel) {
                searchIndexableRaw.title =
                        context.getString(R.string.data_usage_disable_mobile_limit_vzw);
            }
            ((SearchIndexableData) searchIndexableRaw).key = "set_data_limit";
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                return false;
            }
            return SecBillingCycleManager.getInstance(context).getAvailableBillingCycleSetting();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecBillingCycleSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.PRN;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_billing_cycle;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((SecBillingCycleController) use(SecBillingCycleController.class)).setParentFragment(this);
        ((SecDataWarningController) use(SecDataWarningController.class)).setParentFragment(this);
        ((SecDataLimitController) use(SecDataLimitController.class)).setParentFragment(this);
        ((SecSetDataLimitController) use(SecSetDataLimitController.class)).setParentFragment(this);
        if (Rune.isDomesticModel()) {
            ((SecTetheringDataWarningController) use(SecTetheringDataWarningController.class))
                    .setParentFragment(this);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!DataUsageUtils.hasMobile(getContext())) {
            Log.i("SecBillingCycleSettings", "there isn't mobile so finish activity");
            finish();
        }
        Bundle arguments = getArguments();
        NetworkTemplate parcelable = arguments.getParcelable("network_template");
        int i = arguments.getInt("subscriptionid", -1);
        SecBillingCycleManager secBillingCycleManager =
                SecBillingCycleManager.getInstance(getContext());
        if (parcelable == null) {
            Context context = secBillingCycleManager.mContext;
            secBillingCycleManager.mNetworkTemplate =
                    DataUsageUtils.getDefaultTemplate(
                            context, DataUsageUtils.getDefaultSubscriptionId(context));
        } else {
            secBillingCycleManager.mNetworkTemplate = parcelable;
        }
        if (-1 == i) {
            i = DataUsageUtils.getDefaultSubscriptionId(secBillingCycleManager.mContext);
        }
        secBillingCycleManager.mSlotNumber =
                ((SubscriptionManager)
                                secBillingCycleManager.mContext.getSystemService(
                                        "telephony_subscription_service"))
                        .getActiveSubscriptionInfo(i)
                        .getSimSlotIndex();
        MobileDataEnabledListener mobileDataEnabledListener =
                new MobileDataEnabledListener(getContext(), this);
        this.mMobileDataListener = mobileDataEnabledListener;
        mobileDataEnabledListener.start(SubscriptionManager.getDefaultDataSubscriptionId());
        if (Rune.isDomesticModel()) {
            return;
        }
        removePreference("billing_cycle_tethering_category");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mMobileDataListener.stop();
        super.onDestroy();
    }

    @Override // com.android.settings.network.MobileDataEnabledListener.Client
    public final void onMobileDataEnabledChange() {
        Log.d("SecBillingCycleSettings", "finish when Mobile data off");
        finish();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        SecBillingCycleManager.getInstance(getContext()).mUIUpdater = null;
        getContext().unregisterReceiver(this.mReceiver);
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (!ConnectionsUtils.isMobileNetworkEnabled(getContext())) {
            finish();
        }
        SecBillingCycleManager.getInstance(getContext()).mServices.mPolicyEditor.read();
        getContext()
                .registerReceiver(
                        this.mReceiver,
                        new IntentFilter("com.samsung.intent.action.SIMHOTSWAP"),
                        2);
        SecBillingCycleManager.getInstance(getContext()).mUIUpdater = this;
        updatePreferenceStates();
    }
}
