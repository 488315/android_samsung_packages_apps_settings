package com.android.settings.development;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SimulateColorSpacePreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final int SETTING_VALUE_OFF = 0;
    static final int SETTING_VALUE_ON = 1;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "simulate_color_space";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsDisabled() {
        super.onDeveloperOptionsDisabled();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (Settings.Secure.getInt(contentResolver, "accessibility_display_daltonizer_enabled", 0)
                != 0) {
            if (((ListPreference) this.mPreference)
                            .findIndexOfValue(
                                    Integer.toString(
                                            Settings.Secure.getInt(
                                                    contentResolver,
                                                    "accessibility_display_daltonizer",
                                                    -1)))
                    >= 0) {
                writeSimulateColorSpace(-1);
            }
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        writeSimulateColorSpace(obj);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z =
                Settings.Secure.getInt(
                                contentResolver, "accessibility_display_daltonizer_enabled", 0)
                        != 0;
        ListPreference listPreference = (ListPreference) this.mPreference;
        if (!z) {
            listPreference.setValue(Integer.toString(-1));
            return;
        }
        String num =
                Integer.toString(
                        Settings.Secure.getInt(
                                contentResolver, "accessibility_display_daltonizer", -1));
        listPreference.setValue(num);
        if (listPreference.findIndexOfValue(num) >= 0) {
            listPreference.setSummary("%s");
        } else {
            Resources resources = this.mContext.getResources();
            listPreference.setSummary(
                    resources.getString(
                            R.string.daltonizer_type_overridden,
                            resources.getString(
                                    R.string.accessibility_display_daltonizer_preference_title)));
        }
    }

    public final void writeSimulateColorSpace(Object obj) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int parseInt = Integer.parseInt(obj.toString());
        if (parseInt < 0) {
            Settings.Secure.putInt(contentResolver, "accessibility_display_daltonizer_enabled", 0);
        } else {
            Settings.Secure.putInt(contentResolver, "accessibility_display_daltonizer_enabled", 1);
            Settings.Secure.putInt(contentResolver, "accessibility_display_daltonizer", parseInt);
        }
    }
}
