package com.samsung.android.settings.connection;

import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.settings.connection.ethernet.controller.EthernetSettingsPreferenceController;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.nearbyscan.NearbyScanningEnabler;
import com.samsung.android.settings.nearbyscan.NearbyScanningUtil;
import com.samsung.android.util.SemLog;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class WirelessSettings extends SecDynamicFragment
        implements Preference.OnPreferenceChangeListener {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();
    public Context mContext;
    public NearbyScanningEnabler mNearbyScanningEnabler;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.connection.WirelessSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            boolean z = UserHandle.myUserId() != 0;
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add("toggle_nearby_scanning");
            if (!ConnectionsUtils.isSupportEthernet()) {
                arrayList.add("ethernet_settings");
            }
            if (!NearbyScanningUtil.isBeaconManagerInstall(context) || z) {
                arrayList.add("nearby_scanning_setting_category");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = WirelessSettings.class.getName();
            searchIndexableResource.xmlResId = R.xml.sec_wireless_settings;
            return Arrays.asList(searchIndexableResource);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        EthernetSettingsPreferenceController ethernetSettingsPreferenceController =
                new EthernetSettingsPreferenceController(context, "ethernet_settings", this);
        if (settingsLifecycle != null) {
            settingsLifecycle.addObserver(ethernetSettingsPreferenceController);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(ethernetSettingsPreferenceController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WirelessSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 110;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wireless_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity().getBaseContext();
        if (UserHandle.myUserId() != 0
                || !NearbyScanningUtil.isBeaconManagerInstall(this.mContext)) {
            removePreference("nearby_scanning_setting_category");
            removePreference("toggle_nearby_scanning");
        } else {
            NearbyScanningEnabler nearbyScanningEnabler =
                    new NearbyScanningEnabler(
                            this.mContext,
                            (SecSwitchPreferenceScreen)
                                    findPreference("nearby_scanning_setting_category"));
            this.mNearbyScanningEnabler = nearbyScanningEnabler;
            nearbyScanningEnabler.init();
            removePreference("toggle_nearby_scanning");
        }
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        NearbyScanningEnabler nearbyScanningEnabler = this.mNearbyScanningEnabler;
        if (nearbyScanningEnabler != null) {
            nearbyScanningEnabler.onPause();
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        return true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (isResumed()) {
            return super.onPreferenceTreeClick(preference);
        }
        return true;
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        try {
            int enterprisePolicyEnabled =
                    Utils.getEnterprisePolicyEnabled(
                            getContext(),
                            "content://com.sec.knox.provider/RestrictionPolicy4",
                            "isVpnAllowed");
            boolean z = enterprisePolicyEnabled == 1;
            if (enterprisePolicyEnabled != -1 && findPreference("vpn_settings") != null) {
                findPreference("vpn_settings").setEnabled(z);
            }
        } catch (Exception e) {
            SemLog.e("WirelessSettings", "error : " + e);
        }
        String string =
                Settings.Global.getString(
                        getContext().getContentResolver(), "airplane_mode_toggleable_radios");
        if (string == null || !string.contains(ImsProfile.PDN_WIFI)) {
            findPreference("vpn_settings")
                    .setEnabled(
                            Settings.Global.getInt(
                                            getContext().getContentResolver(),
                                            "airplane_mode_on",
                                            0)
                                    == 0);
        }
        NearbyScanningEnabler nearbyScanningEnabler = this.mNearbyScanningEnabler;
        if (nearbyScanningEnabler != null) {
            nearbyScanningEnabler.onResume();
        }
    }
}
