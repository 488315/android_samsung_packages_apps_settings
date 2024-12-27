package com.samsung.android.settings.wifi.mobileap.hotspotlabs;

import android.os.Bundle;

import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.settings.wifi.mobileap.datamodels.hotspotlab.WifiApHotspotLabModel;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApHotspotLabUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApHotspotConfigHistory extends DashboardFragment
        implements OnActivityResultListener {
    public List mWifiApHotspotLabModelList = new ArrayList();

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiApHotspotConfigHistory";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_ap_hotspot_lab_config_history_screen;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Iterator it = ((ArrayList) this.mWifiApHotspotLabModelList).iterator();
        int i = 0;
        while (it.hasNext()) {
            WifiApHotspotLabModel wifiApHotspotLabModel = (WifiApHotspotLabModel) it.next();
            WifiApPreference wifiApPreference = new WifiApPreference(getContext());
            wifiApPreference.mSummaryMaxLines = 50;
            wifiApPreference.setSelectable(false);
            wifiApPreference.setOrder(i);
            wifiApPreference.setSummary(wifiApHotspotLabModel.getSpannableSummary());
            ((PreferenceCategory) findPreference("list_preference_category"))
                    .addPreference(wifiApPreference);
            i++;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getContext();
        getPreferenceScreen();
        this.mWifiApHotspotLabModelList =
                WifiApHotspotLabUtils.getHotspotLabModelList("#tag_wifi_ap_lab_config_event#");
    }
}
