package com.android.settings.fuelgauge;

import android.content.Context;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.MainSwitchPreference;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AllowBackgroundPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    static final String KEY_ALLOW_BACKGROUND_USAGE = "allow_background_usage";
    BatteryOptimizeUtils mBatteryOptimizeUtils;

    public AllowBackgroundPreferenceController(Context context, int i, String str) {
        super(context);
        this.mBatteryOptimizeUtils = new BatteryOptimizeUtils(context, i, str);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_ALLOW_BACKGROUND_USAGE;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        return KEY_ALLOW_BACKGROUND_USAGE.equals(preference.getKey());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        BatteryOptimizeUtils batteryOptimizeUtils = this.mBatteryOptimizeUtils;
        boolean z =
                (batteryOptimizeUtils.isDisabledForOptimizeModeOnly()
                                || batteryOptimizeUtils.isSystemOrDefaultApp())
                        ? false
                        : true;
        boolean z2 = preference instanceof PrimarySwitchPreference;
        if (z2) {
            PrimarySwitchPreference primarySwitchPreference = (PrimarySwitchPreference) preference;
            primarySwitchPreference.setEnabled(z);
            primarySwitchPreference.setSwitchEnabled(z);
        } else if (preference instanceof MainSwitchPreference) {
            ((MainSwitchPreference) preference).setEnabled(z);
        }
        boolean z3 = this.mBatteryOptimizeUtils.getAppOptimizationMode(true) == 1;
        ((SelectorWithWidgetPreference) preference).setChecked(z3);
        if (z2) {
            ((PrimarySwitchPreference) preference).setChecked(z3);
        } else if (preference instanceof MainSwitchPreference) {
            ((MainSwitchPreference) preference).setChecked(z3);
        }
    }
}
