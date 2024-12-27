package com.android.settings.system;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SystemDashboardFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.system_dashboard_fragment);

    public static int getVisiblePreferenceCount(PreferenceGroup preferenceGroup) {
        int i = 0;
        for (int i2 = 0; i2 < preferenceGroup.getPreferenceCount(); i2++) {
            Preference preference = preferenceGroup.getPreference(i2);
            if (preference instanceof PreferenceGroup) {
                i = getVisiblePreferenceCount((PreferenceGroup) preference) + i;
            } else if (preference.isVisible()) {
                i++;
            }
        }
        return i;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SystemDashboardFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 744;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.system_dashboard_fragment;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (getVisiblePreferenceCount(preferenceScreen)
                == preferenceScreen.mInitialExpandedChildrenCount + 1) {
            preferenceScreen.mInitialExpandedChildrenCount = Preference.DEFAULT_ORDER;
        }
    }
}
