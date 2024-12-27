package com.samsung.android.settings.notification;

import android.app.NotificationChannel;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.NotificationPreferenceController;
import com.android.settings.notification.app.NotificationSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecSilentRadioPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public NotificationSettings.DependentFieldListener mDependentFieldListener;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "silent_importance";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable() || this.mChannel == null) {
            return false;
        }
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        if (NotificationBackend.getNotificationAlertsEnabledForPackage(i, str)) {
            return !isDefaultChannel();
        }
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (this.mChannel == null || !((Boolean) obj).booleanValue()) {
            return true;
        }
        this.mChannel.setImportance(2);
        this.mChannel.lockFields(4);
        saveChannel();
        this.mDependentFieldListener.onFieldValueChanged();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        NotificationChannel notificationChannel;
        if (this.mAppRow == null || (notificationChannel = this.mChannel) == null) {
            return;
        }
        boolean isChannelPreferenceEnabled = isChannelPreferenceEnabled(notificationChannel);
        preference.setEnabled(isChannelPreferenceEnabled);
        SecRadioPreference secRadioPreference = (SecRadioPreference) preference;
        secRadioPreference.setChecked(this.mChannel.getImportance() <= 2);
        secRadioPreference.setIconEnabled(isChannelPreferenceEnabled);
        secRadioPreference.setRadioColor(false);
    }
}
