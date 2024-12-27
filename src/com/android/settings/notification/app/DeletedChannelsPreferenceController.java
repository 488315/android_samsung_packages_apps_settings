package com.android.settings.notification.app;

import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DeletedChannelsPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "deleted";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        int i;
        if (!super.isAvailable() || this.mChannel != null || this.mChannelGroup != null) {
            return false;
        }
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i2 = appRow.uid;
        this.mBackend.getClass();
        try {
            i = NotificationBackend.sINM.getDeletedChannelCount(str, i2);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            i = 0;
        }
        return i > 0;
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        int i;
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow != null) {
            String str = appRow.pkg;
            int i2 = appRow.uid;
            this.mBackend.getClass();
            try {
                i = NotificationBackend.sINM.getDeletedChannelCount(str, i2);
            } catch (Exception e) {
                Log.w("NotificationBackend", "Error calling NoMan", e);
                i = 0;
            }
            preference.setTitle(
                    i == 1
                            ? ((NotificationPreferenceController) this)
                                    .mContext
                                    .getResources()
                                    .getString(R.string.deleted_channels_one)
                            : ((NotificationPreferenceController) this)
                                    .mContext
                                    .getResources()
                                    .getString(
                                            R.string.deleted_channels_other, Integer.valueOf(i)));
        }
        preference.setSelectable(false);
    }
}
