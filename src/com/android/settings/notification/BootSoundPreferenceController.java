package com.android.settings.notification;

import android.os.SystemProperties;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BootSoundPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    static final String PROPERTY_BOOT_SOUNDS = "persist.sys.bootanim.play_sound";

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (isAvailable()) {
            ((TwoStatePreference) preferenceScreen.findPreference("boot_sounds"))
                    .setChecked(SystemProperties.getBoolean(PROPERTY_BOOT_SOUNDS, true));
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "boot_sounds";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!"boot_sounds".equals(preference.getKey())) {
            return false;
        }
        SystemProperties.set(
                PROPERTY_BOOT_SOUNDS,
                ((TwoStatePreference) preference).isChecked()
                        ? "1"
                        : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.has_boot_sounds);
    }
}
