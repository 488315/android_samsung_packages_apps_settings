package com.android.settings.applications.specialaccess;

import android.content.Context;
import android.os.Bundle;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.applications.AppCommonUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SpecialAccessSettings extends DashboardFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.specialaccess.SpecialAccessSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            super.getNonIndexableKeys(context);
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                ((ArrayList) nonIndexableKeys).add("data_saver");
            } else {
                ((ArrayList) nonIndexableKeys).add("data_saver_chn");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            if (Utils.isSystemAlertWindowEnabled(context)) {
                ((SearchIndexableData) searchIndexableRaw).key = "system_alert_window_app_list";
                searchIndexableRaw.title = String.valueOf(AppCommonUtils.getOverlayTitle());
                searchIndexableRaw.keywords =
                        context.getResources().getString(R.string.keywords_draw_overlay);
                searchIndexableRaw.screenTitle =
                        context.getResources().getString(R.string.special_access);
                arrayList.add(searchIndexableRaw);
            }
            SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw2).key = "write_settings_apps";
            searchIndexableRaw2.title = String.valueOf(AppCommonUtils.getWriteSettingsTitle());
            searchIndexableRaw2.screenTitle =
                    context.getResources().getString(R.string.special_access);
            arrayList.add(searchIndexableRaw2);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.special_access;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SpecialAccessSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 351;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.special_access;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        replaceEnterpriseStringTitle(
                "interact_across_profiles",
                "Settings.CONNECTED_WORK_AND_PERSONAL_APPS_TITLE",
                R.string.interact_across_profiles_title);
        replaceEnterpriseStringTitle(
                "device_administrators",
                "Settings.MANAGE_DEVICE_ADMIN_APPS",
                R.string.manage_device_admin);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        super.setPreferenceScreen(preferenceScreen);
        Preference findPreference =
                preferenceScreen.findPreference(SettingsPreferenceFragment.KEY_FOOTER_PREFERENCE);
        if (findPreference != null) {
            findPreference.setOrder(1073741822);
        }
    }
}
