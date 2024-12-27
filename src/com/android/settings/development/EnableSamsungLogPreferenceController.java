package com.android.settings.development;

import android.os.SystemProperties;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.sec.ims.configuration.DATA;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnableSamsungLogPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final String SETTING_VALUE_OFF = "0xFFFFFF00";
    static final String SETTING_VALUE_ON = "0xFFFFFFFF";

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "enable_samsung_log";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !DATA.DM_FIELD_INDEX.PCSCF_DOMAIN.equals(
                SystemProperties.get("persist.log.semlevel", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN));
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        try {
            SystemProperties.set("persist.log.semlevel", SETTING_VALUE_OFF);
        } catch (Exception unused) {
            Log.e("EnableSamsungLogPreferenceController", "SystemProperties.set failed");
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        try {
            if (((Boolean) obj).booleanValue()) {
                SystemProperties.set("persist.log.semlevel", SETTING_VALUE_ON);
            } else {
                SystemProperties.set("persist.log.semlevel", SETTING_VALUE_OFF);
            }
            return true;
        } catch (Exception unused) {
            Log.e("EnableSamsungLogPreferenceController", "SystemProperties.set failed");
            return true;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        String upperCase =
                SystemProperties.get("persist.log.semlevel", SETTING_VALUE_OFF)
                        .toUpperCase(Locale.getDefault());
        ((SwitchPreference) this.mPreference)
                .setChecked(SETTING_VALUE_ON.toUpperCase(Locale.getDefault()).equals(upperCase));
    }
}
