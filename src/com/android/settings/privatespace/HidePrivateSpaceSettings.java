package com.android.settings.privatespace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.widget.IllustrationPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class HidePrivateSpaceSettings extends DashboardFragment {
    public static int countPreferencesRecursive(PreferenceGroup preferenceGroup) {
        int preferenceCount = preferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferenceGroup.getPreferenceCount(); i++) {
            Preference preference = preferenceGroup.getPreference(i);
            if (preference instanceof PreferenceGroup) {
                preferenceCount =
                        countPreferencesRecursive((PreferenceGroup) preference) + preferenceCount;
            }
        }
        return preferenceCount;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "HidePrivateSpaceSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2040;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.private_space_hide_locked;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            super.onCreate(bundle);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        final int countPreferencesRecursive = countPreferencesRecursive(getPreferenceScreen());
        onCreateView.setAccessibilityDelegate(
                new View
                        .AccessibilityDelegate() { // from class:
                                                   // com.android.settings.privatespace.HidePrivateSpaceSettings.1
                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(
                            View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        accessibilityNodeInfo.setCollectionInfo(
                                new AccessibilityNodeInfo.CollectionInfo.Builder()
                                        .setRowCount(countPreferencesRecursive)
                                        .setColumnCount(1)
                                        .setItemCount(countPreferencesRecursive)
                                        .setImportantForAccessibilityItemCount(5)
                                        .build());
                    }
                });
        return onCreateView;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        IllustrationPreference illustrationPreference =
                (IllustrationPreference)
                        getPreferenceScreen().findPreference("private_space_hide_illustration");
        illustrationPreference.mLottieDynamicColor = true;
        illustrationPreference.notifyChanged();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (PrivateSpaceMaintainer.getInstance(getContext()).isPrivateSpaceLocked()) {
            finish();
        }
    }
}
