package com.android.settings.notification.app;

import android.content.Context;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppLinkPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin {
    public String mAppLabel;

    public AppLinkPreferenceController(Context context) {
        super(context, null);
        this.mAppLabel = ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "app_link";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return super.isAvailable() && this.mAppRow.settingsIntent != null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow != null) {
            this.mAppLabel = (String) appRow.label;
            preference.setIntent(appRow.settingsIntent);
            preference.setTitle(
                    ((NotificationPreferenceController) this)
                            .mContext.getString(R.string.app_settings_link, this.mAppLabel));
        }
    }
}
