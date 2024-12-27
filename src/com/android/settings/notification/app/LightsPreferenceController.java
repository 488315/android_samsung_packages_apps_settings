package com.android.settings.notification.app;

import android.R;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;

import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LightsPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "lights";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return super.isAvailable()
                && this.mChannel != null
                && checkCanBeVisible()
                && ((NotificationPreferenceController) this)
                        .mContext
                        .getResources()
                        .getBoolean(R.bool.config_knownNetworksEnabledForService)
                && Settings.System.getInt(
                                ((NotificationPreferenceController) this)
                                        .mContext.getContentResolver(),
                                "notification_light_pulse",
                                0)
                        == 1
                && !isDefaultChannel();
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return this.mPreferenceFilter.contains("locked");
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (this.mChannel == null) {
            return true;
        }
        this.mChannel.enableLights(((Boolean) obj).booleanValue());
        saveChannel();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mChannel != null) {
            SecRestrictedSwitchPreference secRestrictedSwitchPreference =
                    (SecRestrictedSwitchPreference) preference;
            secRestrictedSwitchPreference.setDisabledByAdmin(this.mAdmin);
            secRestrictedSwitchPreference.setEnabled(
                    !secRestrictedSwitchPreference.mHelper.mDisabledByAdmin
                            && isChannelConfigurable(this.mChannel));
            secRestrictedSwitchPreference.setChecked(this.mChannel.shouldShowLights());
        }
    }
}
