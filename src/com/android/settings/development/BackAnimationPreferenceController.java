package com.android.settings.development;

import android.content.Context;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;
import com.android.window.flags.Flags;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackAnimationPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public final DevelopmentSettingsDashboardFragment mFragment;

    @VisibleForTesting
    public BackAnimationPreferenceController(Context context) {
        super(context);
        this.mFragment = null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "back_navigation_animation";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !Flags.predictiveBackSystemAnims();
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.Global.putInt(this.mContext.getContentResolver(), "enable_back_animation", 0);
        ((TwoStatePreference) this.mPreference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "enable_back_animation", booleanValue ? 1 : 0);
        DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment = this.mFragment;
        if (developmentSettingsDashboardFragment == null || !booleanValue) {
            return true;
        }
        BackAnimationPreferenceDialog.show(developmentSettingsDashboardFragment);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) this.mPreference)
                .setChecked(
                        Settings.Global.getInt(
                                        this.mContext.getContentResolver(),
                                        "enable_back_animation",
                                        0)
                                != 0);
    }

    public BackAnimationPreferenceController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        super(context);
        this.mFragment = developmentSettingsDashboardFragment;
    }
}
