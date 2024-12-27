package com.android.settings.development;

import android.util.FeatureFlagUtils;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PhantomProcessPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "disable_phantom_process_monitor";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        try {
            FeatureFlagUtils.setEnabled(
                    this.mContext, "settings_enable_monitor_phantom_procs", true);
            ((TwoStatePreference) this.mPreference).setChecked(false);
        } catch (RuntimeException e) {
            DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Fail to set feature flag: "),
                    "PhantomProcessPreferenceController");
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        try {
            FeatureFlagUtils.setEnabled(
                    this.mContext,
                    "settings_enable_monitor_phantom_procs",
                    !((Boolean) obj).booleanValue());
        } catch (RuntimeException e) {
            DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Fail to set feature flag: "),
                    "PhantomProcessPreferenceController");
        }
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        try {
            ((TwoStatePreference) this.mPreference)
                    .setChecked(
                            !FeatureFlagUtils.isEnabled(
                                    this.mContext, "settings_enable_monitor_phantom_procs"));
        } catch (RuntimeException e) {
            DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Fail to get feature flag: "),
                    "PhantomProcessPreferenceController");
        }
    }
}
