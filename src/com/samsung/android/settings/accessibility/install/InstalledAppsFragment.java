package com.samsung.android.settings.accessibility.install;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityActivityPreference;
import com.android.settings.accessibility.AccessibilityServicePreference;
import com.android.settingslib.RestrictedPreference;

import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class InstalledAppsFragment extends AccessibilityDashboardFragment {
    public InstalledAppsManager installedAppsManager;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return getClass().getSimpleName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 6100;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accessibility_installed_apps;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        InstalledAppsManager installedAppsManager =
                new InstalledAppsManager(context, getLifecycle());
        this.installedAppsManager = installedAppsManager;
        installedAppsManager.listener =
                new InstalledAppsManager
                        .OnInstalledAppsChangedListener() { // from class:
                                                            // com.samsung.android.settings.accessibility.install.InstalledAppsFragment$$ExternalSyntheticLambda0
                    @Override // com.samsung.android.settings.accessibility.install.InstalledAppsManager.OnInstalledAppsChangedListener
                    public final void onAppsChanged(List list) {
                        InstalledAppsFragment.this.updatePreferences(list);
                    }
                };
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        InstalledAppsManager installedAppsManager = this.installedAppsManager;
        SecAccessibilityUtils.getAccessibilitySharedPreferences(installedAppsManager.context)
                .edit()
                .putStringSet(
                        "alreadyInstalledApps",
                        (Set)
                                installedAppsManager.preferences.stream()
                                        .map(new InstalledAppsFragment$$ExternalSyntheticLambda1())
                                        .collect(Collectors.toSet()))
                .apply();
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        InstalledAppsManager installedAppsManager = this.installedAppsManager;
        if (((ArrayList) installedAppsManager.preferences).isEmpty()) {
            ((ArrayList) installedAppsManager.preferences)
                    .addAll(
                            InstalledAppsManager.getInstalledAccessibilityList(
                                    installedAppsManager.context));
        }
        updatePreferences(installedAppsManager.preferences);
    }

    public final void updatePreferences(List list) {
        RestrictedPreference restrictedPreference;
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        getPreferenceScreen().removeAll();
        if (list.size() == 0) {
            View findViewById = activity.findViewById(R.id.no_item_layout);
            if (findViewById != null) {
                findViewById.setVisibility(0);
                return;
            }
            View inflate =
                    ((LayoutInflater) activity.getSystemService("layout_inflater"))
                            .inflate(
                                    R.layout.no_item_layout,
                                    (LinearLayout) activity.findViewById(R.id.content_layout));
            TextView textView = (TextView) inflate.findViewById(R.id.no_item_sub_text);
            if (textView != null
                    && !AccessibilityRune.getFloatingFeatureBooleanValue(
                            "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05")) {
                textView.setText(R.string.installed_apps_no_apps_description);
            }
            TextView textView2 = (TextView) inflate.findViewById(R.id.no_item_title_text);
            if (textView2 != null) {
                textView2.setText(R.string.installed_apps_no_apps);
                return;
            }
            return;
        }
        View findViewById2 = activity.findViewById(R.id.no_item_layout);
        if (findViewById2 != null) {
            findViewById2.setVisibility(8);
        }
        List list2 =
                this.installedAppsManager.getNewAppList().stream()
                        .map(new InstalledAppsFragment$$ExternalSyntheticLambda1())
                        .toList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RestrictedPreference restrictedPreference2 = (RestrictedPreference) it.next();
            if (list2.contains(restrictedPreference2.getKey())) {
                restrictedPreference2.setDotVisibility(true);
                restrictedPreference2.setDotContentDescription(
                        activity.getString(
                                R.string.accessibility_new_item_dot_badge_content_description));
            }
            getPreferenceScreen().addPreference(restrictedPreference2);
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int preferenceCount = preferenceScreen.getPreferenceCount();
        if (preferenceCount <= 0
                || (restrictedPreference =
                                (RestrictedPreference)
                                        preferenceScreen.getPreference(preferenceCount - 1))
                        == null) {
            return;
        }
        if (restrictedPreference instanceof AccessibilityServicePreference) {
            AccessibilityServicePreference accessibilityServicePreference =
                    (AccessibilityServicePreference) restrictedPreference;
            Log.d(
                    "AccessibilityServicePreference",
                    "LastInList : " + accessibilityServicePreference.getKey());
            accessibilityServicePreference.isLastInList = true;
            return;
        }
        if (restrictedPreference instanceof AccessibilityActivityPreference) {
            AccessibilityActivityPreference accessibilityActivityPreference =
                    (AccessibilityActivityPreference) restrictedPreference;
            Log.d(
                    "AccessibilityActivityPreference",
                    "LastInList : " + accessibilityActivityPreference.getKey());
            accessibilityActivityPreference.isLastInList = true;
        }
    }
}
