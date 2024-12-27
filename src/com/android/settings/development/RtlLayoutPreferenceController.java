package com.android.settings.development;

import android.provider.Settings;
import android.sysprop.DisplayProperties;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.internal.app.LocalePicker;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RtlLayoutPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final int SETTING_VALUE_OFF = 0;
    static final int SETTING_VALUE_ON = 1;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "force_rtl_layout_all_locales";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.Global.putInt(this.mContext.getContentResolver(), "debug.force_rtl", 0);
        DisplayProperties.debug_force_rtl(Boolean.FALSE);
        updateLocales();
        ((TwoStatePreference) this.mPreference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Boolean bool = (Boolean) obj;
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "debug.force_rtl", bool.booleanValue() ? 1 : 0);
        DisplayProperties.debug_force_rtl(bool);
        updateLocales();
        return true;
    }

    public void updateLocales() {
        LocalePicker.updateLocales(this.mContext.getResources().getConfiguration().getLocales());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) this.mPreference)
                .setChecked(
                        Settings.Global.getInt(
                                        this.mContext.getContentResolver(), "debug.force_rtl", 0)
                                != 0);
    }
}
