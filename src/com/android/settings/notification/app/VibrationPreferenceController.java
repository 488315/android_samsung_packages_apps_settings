package com.android.settings.notification.app;

import android.content.Context;
import android.os.Vibrator;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VibrationPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public final Vibrator mVibrator;

    public VibrationPreferenceController(Context context, NotificationBackend notificationBackend) {
        super(context, notificationBackend);
        this.mVibrator = (Vibrator) context.getSystemService(Vibrator.class);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "vibrate";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        Vibrator vibrator;
        return super.isAvailable()
                && this.mChannel != null
                && checkCanBeVisible()
                && !isDefaultChannel()
                && (vibrator = this.mVibrator) != null
                && vibrator.hasVibrator();
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return this.mPreferenceFilter.contains("vibration");
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (this.mChannel == null) {
            return true;
        }
        this.mChannel.enableVibration(((Boolean) obj).booleanValue());
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
            secRestrictedSwitchPreference.setChecked(this.mChannel.shouldVibrate());
        }
    }
}
