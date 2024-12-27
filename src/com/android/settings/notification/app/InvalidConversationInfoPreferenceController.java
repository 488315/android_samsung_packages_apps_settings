package com.android.settings.notification.app;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InvalidConversationInfoPreferenceController
        extends NotificationPreferenceController {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "invalid_conversation_info";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow == null || appRow.banned) {
            return false;
        }
        if (this.mPreferenceFilter != null && !isIncludedInFilter()) {
            return false;
        }
        NotificationBackend.AppRow appRow2 = this.mAppRow;
        String str = appRow2.pkg;
        int i = appRow2.uid;
        this.mBackend.getClass();
        return NotificationBackend.isInInvalidMsgState(i, str);
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return this.mPreferenceFilter.contains("conversation");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mAppRow == null) {
            return;
        }
        preference.setSummary(
                ((NotificationPreferenceController) this)
                        .mContext.getString(R.string.convo_not_supported_summary));
    }
}
