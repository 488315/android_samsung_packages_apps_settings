package com.samsung.android.settings.display.controller;

import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;

import com.samsung.android.settings.Rune;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NightTimeKeyboardModePreferenceController
        extends SecDisplayPreferenceBaseController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public SecSwitchPreference mPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference = (SecSwitchPreference) preferenceScreen.findPreference("key_night_mode");
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "key_night_mode";
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final ArrayList getUriListRequiringObservation() {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(Settings.System.getUriFor("key_night_mode"));
        return arrayList;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return Rune.isDualFolderType(this.mContext) && Rune.isChinaModel();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.System.putInt(
                this.mContentResolver, "key_night_mode", ((Boolean) obj).booleanValue() ? 1 : 0);
        return true;
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final void updatePreference$11() {
        if (this.mPreference == null) {
            return;
        }
        this.mPreference.setChecked(
                Settings.System.getInt(this.mContentResolver, "key_night_mode", 1) != 0);
        this.mPreference.setSummary(this.mContext.getString(R.string.key_night_mode_summary, 5, 7));
    }
}
