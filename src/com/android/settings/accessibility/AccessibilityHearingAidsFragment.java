package com.android.settings.accessibility;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.PreferenceCategory;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessibilityHearingAidsFragment extends AccessibilityShortcutPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.accessibility_hearing_aids);
    public String mFeatureName;

    public AccessibilityHearingAidsFragment() {
        super("no_config_bluetooth");
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final ComponentName getComponentName() {
        return AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME;
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final CharSequence getLabelName() {
        return this.mFeatureName;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AccessibilityHearingAidsFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2024;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_hearing_aids;
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment
    public final CharSequence getShortcutTitle() {
        return getText(R.string.accessibility_hearing_device_shortcut_title);
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((AvailableHearingDevicePreferenceController)
                        use(AvailableHearingDevicePreferenceController.class))
                .init(this);
        ((SavedHearingDevicePreferenceController) use(SavedHearingDevicePreferenceController.class))
                .init(this);
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        this.mFeatureName = getContext().getString(R.string.accessibility_hearingaid_title);
        super.onCreate(bundle);
    }

    @Override // com.android.settings.accessibility.AccessibilityShortcutPreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) findPreference("hearing_options_category");
        this.mShortcutPreference.setOrder(20);
        getPreferenceScreen().removePreference(this.mShortcutPreference);
        preferenceCategory.addPreference(this.mShortcutPreference);
        return onCreateView;
    }
}
