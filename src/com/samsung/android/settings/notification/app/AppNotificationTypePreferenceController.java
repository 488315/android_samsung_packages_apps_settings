package com.samsung.android.settings.notification.app;

import android.provider.Settings;

import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.NotificationPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppNotificationTypePreferenceController
        extends NotificationPreferenceController {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "noti";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable()) {
            return false;
        }
        if (Settings.Secure.getIntForUser(
                                ((NotificationPreferenceController) this)
                                        .mContext.getContentResolver(),
                                "lock_screen_show_notifications",
                                0,
                                -2)
                        == 0
                && Settings.Secure.getInt(
                                ((NotificationPreferenceController) this)
                                        .mContext.getContentResolver(),
                                "notification_badging",
                                1)
                        == 0) {
            NotificationBackend.AppRow appRow = this.mAppRow;
            String str = appRow.pkg;
            int i = appRow.uid;
            this.mBackend.getClass();
            if (!NotificationBackend.getNotificationAlertsEnabledForPackage(i, str)) {
                return false;
            }
        }
        return true;
    }
}
