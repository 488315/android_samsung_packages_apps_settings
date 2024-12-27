package com.samsung.android.settings.notification.app;

import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.NotificationPreferenceController;

import com.samsung.android.settings.logging.SALogging;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class HideContentLockScreenPreferenceController
        extends NotificationPreferenceController implements Preference.OnPreferenceChangeListener {
    public NotificationBackend mBackend;
    public int mCurrentLockType;
    public SwitchPreference mPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (SwitchPreference) preferenceScreen.findPreference("hide_content");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "hide_content";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable()
                || Settings.Secure.getIntForUser(
                                ((NotificationPreferenceController) this)
                                        .mContext.getContentResolver(),
                                "lock_screen_show_notifications",
                                0,
                                -2)
                        == 0
                || Settings.Secure.getIntForUser(
                                ((NotificationPreferenceController) this)
                                        .mContext.getContentResolver(),
                                "lock_screen_allow_private_notifications",
                                0,
                                -2)
                        == 0) {
            return false;
        }
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        return NotificationBackend.getLockScreenNotificationVisibilityForPackage(i, str) != -1;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        this.mCurrentLockType = booleanValue ? 1 : 0;
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        NotificationBackend.setLockScreenNotificationVisibilityForPackage(
                i, booleanValue ? 1 : 0, str);
        HashMap hashMap = new HashMap();
        if (this.mCurrentLockType == 1) {
            hashMap.put("App_Lock_show content", this.mAppRow.pkg + "_show");
        } else {
            hashMap.put("App_Lock_show content", this.mAppRow.pkg + "_hide");
        }
        SALogging.insertSALog(Integer.toString(36024), "NSTE0036", hashMap, 0);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        int lockScreenNotificationVisibilityForPackage =
                NotificationBackend.getLockScreenNotificationVisibilityForPackage(i, str);
        this.mCurrentLockType = lockScreenNotificationVisibilityForPackage;
        SwitchPreference switchPreference = this.mPreference;
        boolean z = true;
        if (lockScreenNotificationVisibilityForPackage != 1
                && lockScreenNotificationVisibilityForPackage != -1000) {
            z = false;
        }
        switchPreference.setChecked(z);
    }
}
