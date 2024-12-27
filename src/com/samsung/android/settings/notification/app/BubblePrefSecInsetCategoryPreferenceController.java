package com.samsung.android.settings.notification.app;

import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.NotificationPreferenceController;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BubblePrefSecInsetCategoryPreferenceController
        extends NotificationPreferenceController implements PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bubble_pref_inset";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        NotificationBackend.AppRow appRow;
        boolean z;
        if (!super.isAvailable() || (appRow = this.mAppRow) == null) {
            return false;
        }
        if (this.mChannel != null) {
            if (isFloatingIconBubble()) {
                return isDefaultChannel() || this.mAppRow != null;
            }
            return false;
        }
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        List list = NotificationBackend.getConversations(i, str).getList();
        if ((list != null && !list.isEmpty()) || !isFloatingIconBubble()) {
            return false;
        }
        NotificationBackend.AppRow appRow2 = this.mAppRow;
        try {
            z = NotificationBackend.sINM.hasSentValidMsg(appRow2.pkg, appRow2.uid);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            z = false;
        }
        return z;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        preference.setSelectable(false);
    }
}
