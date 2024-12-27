package com.samsung.android.settings.privacy;

import android.content.Context;
import android.provider.SearchIndexableResource;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.drawer.Tile;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class OtherPrivacySettingsFragment extends SecDynamicFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_other_privacy_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.privacy.OtherPrivacySettingsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (SecurityDashboardUtils.isChinaModel()) {
                ((ArrayList) nonIndexableKeys).add("privacy_google_location_history");
            }
            ((ArrayList) nonIndexableKeys).add("other_privacy_settings");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = OtherPrivacySettingsFragment.class.getName();
            searchIndexableResource.xmlResId = R.xml.sec_other_privacy_settings;
            return Arrays.asList(searchIndexableResource);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final boolean displayTile(Tile tile) {
        if ((Rune.isChinaModel()
                        && "com.android.healthconnect.controller.LegacySettingsEntryPoint"
                                .equals(tile.mComponentName))
                || "privacy_google_autofill".equals(tile.getKey(getActivity()))) {
            return false;
        }
        return super.displayTile(tile);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "OtherPrivacySettingsFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 56041;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_other_privacy_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        key.getClass();
        if (key.equals("privacy_manage_perms")) {
            LoggingHelper.insertEventLogging(56041, 56001);
        }
        return super.onPreferenceTreeClick(preference);
    }
}
