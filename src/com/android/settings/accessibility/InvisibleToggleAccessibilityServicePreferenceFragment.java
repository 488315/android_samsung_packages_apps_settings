package com.android.settings.accessibility;

import android.content.DialogInterface;

import com.android.settingslib.accessibility.AccessibilityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InvisibleToggleAccessibilityServicePreferenceFragment
        extends ToggleAccessibilityServicePreferenceFragment {
    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void callOnAlertDialogCheckboxClicked(DialogInterface dialogInterface, int i) {
        super.callOnAlertDialogCheckboxClicked(dialogInterface, i);
        AccessibilityUtils.setAccessibilityServiceState(
                getContext(), this.mComponentName, this.mShortcutPreference.mChecked);
    }

    @Override // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment
    public final void onAllowButtonFromShortcutToggleClicked() {
        super.onAllowButtonFromShortcutToggleClicked();
        AccessibilityUtils.setAccessibilityServiceState(getContext(), this.mComponentName, true);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onInstallSwitchPreferenceToggleSwitch() {
        super.onInstallSwitchPreferenceToggleSwitch();
        this.mToggleServiceSwitchBar.hide();
    }

    @Override // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment,
              // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.accessibility.ShortcutPreference.OnClickCallback
    public final void onToggleClicked(ShortcutPreference shortcutPreference) {
        super.onToggleClicked(shortcutPreference);
        AccessibilityUtils.setAccessibilityServiceState(
                getContext(),
                this.mComponentName,
                getArguments().getBoolean("checked") && shortcutPreference.mChecked);
    }
}
