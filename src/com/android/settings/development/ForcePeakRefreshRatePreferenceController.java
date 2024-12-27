package com.android.settings.development;

import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.server.display.feature.flags.Flags;
import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ForcePeakRefreshRatePreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static float NO_CONFIG;
    float mPeakRefreshRate;

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference("pref_key_peak_refresh_rate");
    }

    public void forcePeakRefreshRate(boolean z) {
        float f =
                Flags.backUpSmoothDisplayAndForcePeakRefreshRate()
                        ? Float.POSITIVE_INFINITY
                        : this.mPeakRefreshRate;
        if (!z) {
            f = NO_CONFIG;
        }
        Settings.System.putFloat(this.mContext.getContentResolver(), "min_refresh_rate", f);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "pref_key_peak_refresh_rate";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_smooth_display)
                && this.mPeakRefreshRate > 60.0f;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.System.putFloat(this.mContext.getContentResolver(), "min_refresh_rate", NO_CONFIG);
        ((TwoStatePreference) this.mPreference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        forcePeakRefreshRate(((Boolean) obj).booleanValue());
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        TwoStatePreference twoStatePreference = (TwoStatePreference) this.mPreference;
        float f =
                Settings.System.getFloat(
                        this.mContext.getContentResolver(), "min_refresh_rate", NO_CONFIG);
        twoStatePreference.setChecked(
                Math.round(f) == Math.round(this.mPeakRefreshRate) || Float.isInfinite(f));
    }
}
