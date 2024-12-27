package com.android.settings.emergency;

import android.R;
import android.content.Context;
import android.os.Bundle;
import android.provider.SearchIndexableResource;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.notification.EmergencyBroadcastPreferenceController;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.drawer.Tile;

import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EmergencyDashboardFragment extends SecDynamicFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass2();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.emergency.EmergencyDashboardFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    EmergencyDashboardFragment.SEARCH_INDEX_DATA_PROVIDER;
            ArrayList arrayList = new ArrayList();
            arrayList.add(new EmergencyBroadcastPreferenceController(context));
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add("dashboard_tile_pref_com.android.emergency.EmergencyInfoActivity");
            if (context.getResources().getBoolean(R.bool.config_displayWhiteBalanceAvailable)) {
                arrayList.add("app_and_notif_cell_broadcast_settings");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = EmergencyDashboardFragment.class.getName();
            searchIndexableResource.xmlResId = com.android.settings.R.xml.sec_emergency_settings;
            return Arrays.asList(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return context.getResources()
                    .getBoolean(com.android.settings.R.bool.config_show_emergency_settings);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new EmergencyBroadcastPreferenceController(context));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final boolean displayTile(Tile tile) {
        if ("com.android.emergency.EmergencyInfoActivity".equals(tile.mComponentName)) {
            return false;
        }
        return super.displayTile(tile);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "EmergencyDashboard";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1859;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return com.android.settings.R.xml.sec_emergency_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        RestrictedPreference restrictedPreference =
                (RestrictedPreference)
                        preferenceScreen.findPreference("app_and_notif_cell_broadcast_settings");
        if (getResources().getBoolean(R.bool.config_displayWhiteBalanceAvailable)
                && restrictedPreference != null) {
            preferenceScreen.removePreference(restrictedPreference);
        }
        try {
            SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                    (SecSwitchPreferenceScreen)
                            preferenceScreen.findPreference("master_switch_preference_key");
            final Preference.OnPreferenceClickListener onPreferenceClickListener =
                    secSwitchPreferenceScreen.getOnPreferenceClickListener();
            secSwitchPreferenceScreen.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.emergency.EmergencyDashboardFragment.1
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            LoggingHelper.insertEventLogging(1859, 59502);
                            Preference.OnPreferenceClickListener.this.onPreferenceClick(preference);
                            return true;
                        }
                    });
        } catch (Exception e) {
            Log.d(
                    "EmergencyDashboard",
                    "SA Logging for Send SOS Message settings : " + e.toString());
        }
    }
}
