package com.android.settings.development;

import android.os.SystemProperties;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MockModemPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final String ALLOW_MOCK_MODEM_PROPERTY = "persist.radio.allow_mock_modem";

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "allow_mock_modem";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        try {
            SystemProperties.set(ALLOW_MOCK_MODEM_PROPERTY, "false");
            ((TwoStatePreference) this.mPreference).setChecked(false);
        } catch (RuntimeException e) {
            DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Fail to set radio system property: "),
                    "MockModemPreferenceController");
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        try {
            SystemProperties.set(
                    ALLOW_MOCK_MODEM_PROPERTY, ((Boolean) obj).booleanValue() ? "true" : "false");
            return true;
        } catch (RuntimeException e) {
            DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Fail to set radio system property: "),
                    "MockModemPreferenceController");
            return true;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        try {
            ((TwoStatePreference) this.mPreference)
                    .setChecked(SystemProperties.getBoolean(ALLOW_MOCK_MODEM_PROPERTY, false));
        } catch (RuntimeException e) {
            DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Fail to get radio system property: "),
                    "MockModemPreferenceController");
        }
    }
}
