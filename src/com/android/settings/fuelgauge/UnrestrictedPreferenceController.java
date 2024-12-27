package com.android.settings.fuelgauge;

import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UnrestrictedPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    static final String KEY_UNRESTRICTED_PREF = "unrestricted_preference";
    BatteryOptimizeUtils mBatteryOptimizeUtils;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_UNRESTRICTED_PREF;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        return KEY_UNRESTRICTED_PREF.equals(preference.getKey());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mBatteryOptimizeUtils.getAppOptimizationMode(true) == 2) {
            Log.d("UNRESTRICTED_PREF", "is unrestricted states");
            ((SelectorWithWidgetPreference) preference).setChecked(true);
            return;
        }
        ((SelectorWithWidgetPreference) preference).setChecked(false);
        if (this.mBatteryOptimizeUtils.isSystemOrDefaultApp()) {
            Log.d("UNRESTRICTED_PREF", "is system or default app, disable pref");
            preference.setEnabled(false);
        }
    }
}
