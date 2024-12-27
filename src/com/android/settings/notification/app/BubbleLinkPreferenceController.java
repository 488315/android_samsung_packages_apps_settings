package com.android.settings.notification.app;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.notification.BubbleHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BubbleLinkPreferenceController extends NotificationPreferenceController {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "notification_bubbles";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable()) {
            return false;
        }
        Context context = ((NotificationPreferenceController) this).mContext;
        return BubbleHelper.isSupportedByDevice(context)
                && Settings.Secure.getInt(context.getContentResolver(), "notification_bubbles", 1)
                        == 1;
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
