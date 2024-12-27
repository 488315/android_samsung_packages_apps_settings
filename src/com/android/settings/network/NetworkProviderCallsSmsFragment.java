package com.android.settings.network;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.network.telephony.CallsDefaultSubscriptionController;
import com.android.settings.network.telephony.NetworkProviderWifiCallingPreferenceController;
import com.android.settings.network.telephony.SmsDefaultSubscriptionController;
import com.android.settings.search.BaseSearchIndexProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NetworkProviderCallsSmsFragment extends DashboardFragment {
    static final String KEY_PREFERENCE_CALLS = "provider_model_calls_preference";
    static final String KEY_PREFERENCE_CATEGORY_CALLING = "provider_model_calling_category";
    static final String KEY_PREFERENCE_SMS = "provider_model_sms_preference";
    static final String LOG_TAG = "NetworkProviderCallsSmsFragment";
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.network_provider_calls_sms);
    public NetworkProviderWifiCallingPreferenceController
            mNetworkProviderWifiCallingPreferenceController;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.network.NetworkProviderCallsSmsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return false;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new CallsDefaultSubscriptionController(
                        context, KEY_PREFERENCE_CALLS, getSettingsLifecycle(), this));
        arrayList.add(
                new SmsDefaultSubscriptionController(
                        context, KEY_PREFERENCE_SMS, getSettingsLifecycle(), this));
        NetworkProviderWifiCallingPreferenceController
                networkProviderWifiCallingPreferenceController =
                        new NetworkProviderWifiCallingPreferenceController(
                                context, KEY_PREFERENCE_CATEGORY_CALLING);
        this.mNetworkProviderWifiCallingPreferenceController =
                networkProviderWifiCallingPreferenceController;
        networkProviderWifiCallingPreferenceController.init(getSettingsLifecycle());
        arrayList.add(this.mNetworkProviderWifiCallingPreferenceController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return LOG_TAG;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1997;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.network_provider_calls_sms;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updatePreferenceStates();
    }
}
