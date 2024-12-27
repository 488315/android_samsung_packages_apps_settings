package com.android.settings.fuelgauge;

import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class OptimizedPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    static final String KEY_OPTIMIZED_PREF = "optimized_preference";
    BatteryOptimizeUtils mBatteryOptimizeUtils;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_OPTIMIZED_PREF;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        return KEY_OPTIMIZED_PREF.equals(preference.getKey());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mBatteryOptimizeUtils.isDisabledForOptimizeModeOnly()
                || this.mBatteryOptimizeUtils.getAppOptimizationMode(true) == 3) {
            Log.d("OPTIMIZED_PREF", "is optimized states");
            ((SelectorWithWidgetPreference) preference).setChecked(true);
            return;
        }
        ((SelectorWithWidgetPreference) preference).setChecked(false);
        if (this.mBatteryOptimizeUtils.isSystemOrDefaultApp()) {
            Log.d("OPTIMIZED_PREF", "is system or default app, disable pref");
            preference.setEnabled(false);
        }
    }
}
