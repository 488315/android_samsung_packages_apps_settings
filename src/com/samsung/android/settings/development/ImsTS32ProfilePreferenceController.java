package com.samsung.android.settings.development;

import android.os.SystemProperties;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ImsTS32ProfilePreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "ims_ts32_profile_enable";
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SystemProperties.set(
                "persist.ims.ts32.profile.enable",
                ((Boolean) obj).booleanValue() ? "true" : "false");
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((SwitchPreference) this.mPreference)
                .setChecked(SystemProperties.getBoolean("persist.ims.ts32.profile.enable", false));
        preference.setSummary(R.string.ims_ts32_profile_enable_summary);
    }
}
