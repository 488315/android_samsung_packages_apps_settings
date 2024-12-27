package com.android.settings.accessibility;

import android.os.Bundle;
import android.view.View;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VolumeShortcutToggleAccessibilityServicePreferenceFragment
        extends ToggleAccessibilityServicePreferenceFragment {
    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mShortcutPreference.setSummary(
                getPrefContext()
                        .getText(R.string.accessibility_shortcut_edit_dialog_title_hardware));
        ShortcutPreference shortcutPreference = this.mShortcutPreference;
        if (shortcutPreference.mSettingsEditable) {
            shortcutPreference.mSettingsEditable = false;
            shortcutPreference.notifyChanged();
        }
        PreferredShortcuts.saveUserShortcutType(
                getPrefContext(), new PreferredShortcut(this.mComponentName.flattenToString(), 2));
    }
}
