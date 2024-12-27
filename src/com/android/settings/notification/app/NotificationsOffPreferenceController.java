package com.android.settings.notification.app;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.applications.AppUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NotificationsOffPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "block_desc";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (this.mAppRow == null) {
            return false;
        }
        if (this.mPreferenceFilter == null || isIncludedInFilter()) {
            return !super.isAvailable();
        }
        return false;
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return this.mPreferenceFilter.contains("importance");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow != null) {
            if (AppUtils.isDeepSleepingEnable(appRow.uid, appRow.pkg)) {
                preference.setTitle(
                        ((NotificationPreferenceController) this)
                                        .mContext
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .sec_app_notification_manage_deep_sleeping_apps_summary_title)
                                + "\n\n"
                                + ((NotificationPreferenceController) this)
                                        .mContext
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .sec_app_notification_manage_deep_sleeping_apps_summary_subtitle));
            } else if (this.mChannel != null) {
                preference.setTitle(R.string.channel_notifications_off_desc);
            } else if (this.mChannelGroup != null) {
                preference.setTitle(R.string.channel_group_notifications_off_desc);
            } else {
                this.mAppRow.getClass();
                preference.setTitle(R.string.app_notifications_off_desc);
            }
        }
        preference.setSelectable(false);
    }
}
