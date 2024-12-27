package com.android.settings.development;

import android.os.SystemProperties;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;
import com.android.settingslib.development.SystemPropPoker;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CoolColorTemperaturePreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final String COLOR_TEMPERATURE_PROPERTY = "persist.sys.debug.color_temp";

    public void displayColorTemperatureToast() {
        Toast.makeText(this.mContext, R.string.color_temperature_toast, 1).show();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "color_temperature";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.config_enableColorTemperature);
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        SystemProperties.set(COLOR_TEMPERATURE_PROPERTY, Boolean.toString(false));
        ((TwoStatePreference) this.mPreference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SystemProperties.set(
                COLOR_TEMPERATURE_PROPERTY, Boolean.toString(((Boolean) obj).booleanValue()));
        SystemPropPoker.sInstance.poke();
        displayColorTemperatureToast();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) this.mPreference)
                .setChecked(SystemProperties.getBoolean(COLOR_TEMPERATURE_PROPERTY, false));
    }
}
