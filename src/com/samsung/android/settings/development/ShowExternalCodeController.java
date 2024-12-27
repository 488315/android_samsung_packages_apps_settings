package com.samsung.android.settings.development;

import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.samsung.android.settingslib.SettingsLibRune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ShowExternalCodeController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "key_show_external_code";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return SettingsLibRune.MENU_SHOW_EXTERNAL_CODE;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.System.putInt(this.mContext.getContentResolver(), "show_external_code", 0);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "show_external_code",
                ((Boolean) obj).booleanValue() ? 1 : 0);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((SwitchPreference) preference)
                .setChecked(
                        Settings.System.getInt(
                                        this.mContext.getContentResolver(), "show_external_code", 0)
                                != 0);
    }
}
