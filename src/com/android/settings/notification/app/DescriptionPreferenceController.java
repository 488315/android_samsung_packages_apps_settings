package com.android.settings.notification.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DescriptionPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "desc";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable()) {
            return false;
        }
        NotificationChannel notificationChannel = this.mChannel;
        if (notificationChannel == null && this.mChannelGroup == null) {
            return false;
        }
        if (notificationChannel != null
                && !TextUtils.isEmpty(notificationChannel.getDescription())) {
            return true;
        }
        NotificationChannelGroup notificationChannelGroup = this.mChannelGroup;
        return (notificationChannelGroup == null
                        || TextUtils.isEmpty(notificationChannelGroup.getDescription()))
                ? false
                : true;
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mAppRow != null) {
            NotificationChannel notificationChannel = this.mChannel;
            if (notificationChannel != null) {
                preference.setTitle(notificationChannel.getDescription());
            } else {
                NotificationChannelGroup notificationChannelGroup = this.mChannelGroup;
                if (notificationChannelGroup != null) {
                    preference.setTitle(notificationChannelGroup.getDescription());
                }
            }
        }
        preference.setEnabled(false);
        preference.setSelectable(false);
    }
}
