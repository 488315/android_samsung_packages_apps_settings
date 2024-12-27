package com.android.settings.development;

import android.os.Build;
import android.os.SystemProperties;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.samsung.android.settings.Rune;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SatelliteCommunicationPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "enable_satellite_communication";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (Rune.isChinaModel()) {
            String str = Build.TYPE;
            if ("eng".equals(str) || "userdebug".equals(str)) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SystemProperties.set(
                "ril.tiantong.equiptest",
                "ON".equals(obj.toString()) ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        updateState(preference);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ListPreference listPreference = (ListPreference) preference;
        boolean equals = "1".equals(SystemProperties.get("ril.tiantong.equiptest"));
        listPreference.setValue(equals ? "ON" : "OFF");
        listPreference.setSummary(equals ? "ON" : "OFF");
    }
}
