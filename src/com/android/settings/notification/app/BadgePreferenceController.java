package com.android.settings.notification.app;

import android.app.NotificationChannel;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BadgePreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "badge";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable()) {
            return false;
        }
        if ((this.mAppRow == null && this.mChannel == null)
                || Settings.Secure.getInt(
                                ((NotificationPreferenceController) this)
                                        .mContext.getContentResolver(),
                                "notification_badging",
                                1)
                        == 0) {
            return false;
        }
        if (this.mChannel == null) {
            return this.mAppRow.channelCount != 0;
        }
        if (isDefaultChannel()) {
            return true;
        }
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow == null || appRow.channelCount == 0) {
            return false;
        }
        return appRow.showBadge;
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return this.mPreferenceFilter.contains("launcher");
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        NotificationChannel notificationChannel = this.mChannel;
        if (notificationChannel != null) {
            notificationChannel.setShowBadge(booleanValue);
            saveChannel();
            return true;
        }
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow == null) {
            return true;
        }
        appRow.showBadge = booleanValue;
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        try {
            NotificationBackend.sINM.setShowBadge(str, i, booleanValue);
            return true;
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return true;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mAppRow != null) {
            SecRestrictedSwitchPreference secRestrictedSwitchPreference =
                    (SecRestrictedSwitchPreference) preference;
            secRestrictedSwitchPreference.setDisabledByAdmin(this.mAdmin);
            NotificationBackend.AppRow appRow = this.mAppRow;
            String str = appRow.pkg;
            int i = appRow.uid;
            this.mBackend.getClass();
            if (NotificationBackend.canShowBadge(i, str)) {
                secRestrictedSwitchPreference.setEnabled(true);
            }
            NotificationChannel notificationChannel = this.mChannel;
            if (notificationChannel == null) {
                secRestrictedSwitchPreference.setChecked(this.mAppRow.showBadge);
            } else {
                secRestrictedSwitchPreference.setChecked(notificationChannel.canShowBadge());
                secRestrictedSwitchPreference.setEnabled(
                        !secRestrictedSwitchPreference.mHelper.mDisabledByAdmin);
            }
        }
    }
}
