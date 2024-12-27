package com.samsung.android.settings.notification.app;

import android.provider.Settings;
import android.util.Log;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.NotificationPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PopupTypeTextPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin {
    public NotificationBackend mBackend;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "popup_notification_text";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        boolean z;
        if (!super.isAvailable()) {
            return false;
        }
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        try {
            z = NotificationBackend.sINM.isAllowNotificationPopUpForPackage(str, i);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            z = false;
        }
        if (z) {
            NotificationBackend.AppRow appRow2 = this.mAppRow;
            if (NotificationBackend.getNotificationAlertsEnabledForPackage(appRow2.uid, appRow2.pkg)
                    && Settings.Secure.getIntForUser(
                                    ((NotificationPreferenceController) this)
                                            .mContext.getContentResolver(),
                                    "show_notification_category_setting",
                                    0,
                                    -2)
                            != 0) {
                return true;
            }
        }
        return false;
    }
}
