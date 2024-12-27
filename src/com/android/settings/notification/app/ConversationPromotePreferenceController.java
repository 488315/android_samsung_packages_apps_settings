package com.android.settings.notification.app;

import android.app.NotificationChannel;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.PreferenceControllerMixin;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConversationPromotePreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin {
    public SettingsPreferenceFragment mHostFragment;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "convo_promote";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (this.mChannel == null || !"convo_promote".equals(preference.getKey())) {
            return false;
        }
        this.mChannel.setDemoted(false);
        this.mChannel.setBypassDnd(false);
        saveChannel();
        SettingsPreferenceFragment settingsPreferenceFragment = this.mHostFragment;
        if (settingsPreferenceFragment == null) {
            return true;
        }
        settingsPreferenceFragment.getActivity().finish();
        return true;
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        NotificationChannel notificationChannel;
        return (!super.isAvailable()
                        || this.mAppRow == null
                        || (notificationChannel = this.mChannel) == null
                        || TextUtils.isEmpty(notificationChannel.getConversationId())
                        || !this.mChannel.isDemoted())
                ? false
                : true;
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return this.mPreferenceFilter.contains("conversation");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        preference.setEnabled(this.mAdmin == null);
    }
}
