package com.android.settings.notification.zen;

import android.service.notification.ZenModeConfig;

import androidx.preference.Preference;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LifeStyleZenModeSettingsHeaderPreferenceController
        extends AbstractZenModePreferenceController {
    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "sec_lifestyle_zen_header_preference";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        ZenModeConfig zenModeConfig = this.mNotificationManager.getZenModeConfig();
        if (zenModeConfig != null) {
            ZenModeConfig.ZenRule zenRule = zenModeConfig.manualRule;
        }
        preference.setTitle(this.mContext.getString(R.string.dnd_off_no_schedule_intial_header));
    }
}
