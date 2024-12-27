package com.samsung.android.settings.notification.app;

import android.content.Context;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.NotificationPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DividerPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin {
    public final boolean mFromChannel;

    public DividerPreferenceController(
            Context context, NotificationBackend notificationBackend, boolean z) {
        super(context, notificationBackend);
        this.mFromChannel = z;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "divider";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable()) {
            return false;
        }
        if (!this.mFromChannel) {
            return true;
        }
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        return NotificationBackend.getNotificationAlertsEnabledForPackage(i, str);
    }
}
