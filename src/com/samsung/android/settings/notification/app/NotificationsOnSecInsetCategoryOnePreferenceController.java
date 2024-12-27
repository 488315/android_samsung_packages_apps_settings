package com.samsung.android.settings.notification.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.app.NotificationPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NotificationsOnSecInsetCategoryOnePreferenceController
        extends NotificationPreferenceController implements PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "SecInsetCategory_for_desc";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (this.mAppRow == null) {
            return false;
        }
        NotificationChannel notificationChannel = this.mChannel;
        if (notificationChannel != null
                && !TextUtils.isEmpty(notificationChannel.getDescription())) {
            return false;
        }
        NotificationChannelGroup notificationChannelGroup = this.mChannelGroup;
        if (notificationChannelGroup == null
                || TextUtils.isEmpty(notificationChannelGroup.getDescription())) {
            return super.isAvailable();
        }
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        preference.setSelectable(false);
    }
}
