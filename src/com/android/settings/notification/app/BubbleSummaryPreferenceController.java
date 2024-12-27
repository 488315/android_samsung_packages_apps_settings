package com.android.settings.notification.app;

import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BubbleSummaryPreferenceController extends NotificationPreferenceController {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bubble_pref_link";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final CharSequence getSummary() {
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow == null) {
            return null;
        }
        int i = appRow.bubblePreference;
        Resources resources = ((NotificationPreferenceController) this).mContext.getResources();
        return (i == 0 || !isFloatingIconBubble())
                ? resources.getString(R.string.bubble_app_setting_none)
                : i == 1
                        ? resources.getString(R.string.bubble_app_setting_all)
                        : resources.getString(R.string.bubble_app_setting_selected);
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        boolean z;
        if (!super.isAvailable() || this.mAppRow == null) {
            return false;
        }
        if (this.mChannel != null) {
            if (isFloatingIconBubble()) {
                return isDefaultChannel() || this.mAppRow != null;
            }
            return false;
        }
        if (!isFloatingIconBubble()) {
            return false;
        }
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        try {
            z = NotificationBackend.sINM.hasSentValidBubble(str, i);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            z = false;
        }
        return z;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        if (this.mAppRow != null) {
            Intent intent = new Intent("android.settings.APP_NOTIFICATION_BUBBLE_SETTINGS");
            intent.setPackage(((NotificationPreferenceController) this).mContext.getPackageName());
            intent.putExtra("android.provider.extra.APP_PACKAGE", this.mAppRow.pkg);
            intent.putExtra("app_uid", this.mAppRow.uid);
            preference.setIntent(intent);
        }
    }
}
