package com.samsung.android.settings.development;

import android.provider.Settings;
import android.util.Slog;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.samsung.android.settings.goodsettings.GoodSettingsTopLevelPreferenceParser;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CloPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        Slog.d("CloPreferenceController", "getPreferenceKey()");
        return "cross_layer_optimizer_for_mobile_network";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        Slog.d(
                "CloPreferenceController",
                GoodSettingsTopLevelPreferenceParser.PREFERENCE_INFO_IS_AVAILABLE);
        return false;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Slog.d("CloPreferenceController", "onDeveloperOptionsSwitchDisabled");
        Settings.System.putInt(
                this.mContext.getContentResolver(), "cross_layer_optimizer_for_mobile_network", 0);
        ((SwitchPreference) this.mPreference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Slog.d("CloPreferenceController", "onPreferenceChange");
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "cross_layer_optimizer_for_mobile_network",
                ((Boolean) obj).booleanValue() ? 1 : 0);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        Slog.d("CloPreferenceController", "updateState");
        ((SwitchPreference) this.mPreference)
                .setChecked(
                        Settings.System.getInt(
                                        this.mContext.getContentResolver(),
                                        "cross_layer_optimizer_for_mobile_network",
                                        0)
                                != 0);
    }
}
