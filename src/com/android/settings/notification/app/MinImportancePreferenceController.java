package com.android.settings.notification.app;

import android.app.NotificationChannel;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;

import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MinImportancePreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public NotificationSettings.DependentFieldListener mDependentFieldListener;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "min_importance";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return super.isAvailable()
                && this.mChannel != null
                && !isDefaultChannel()
                && this.mChannel.getImportance() <= 2;
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return this.mPreferenceFilter.contains("importance");
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (this.mChannel != null) {
            this.mChannel.setImportance(((Boolean) obj).booleanValue() ? 1 : 2);
            this.mChannel.lockFields(4);
            saveChannel();
            this.mDependentFieldListener.onFieldValueChanged();
        }
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        NotificationChannel notificationChannel;
        if (this.mAppRow == null || (notificationChannel = this.mChannel) == null) {
            return;
        }
        preference.setEnabled(isChannelPreferenceEnabled(notificationChannel));
        ((SecRestrictedSwitchPreference) preference).setChecked(this.mChannel.getImportance() == 1);
    }
}
