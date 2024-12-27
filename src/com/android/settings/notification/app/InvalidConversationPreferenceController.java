package com.android.settings.notification.app;

import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.RestrictedSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InvalidConversationPreferenceController extends NotificationPreferenceController
        implements Preference.OnPreferenceChangeListener {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "invalid_conversation_switch";
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

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow == null) {
            return false;
        }
        String str = appRow.pkg;
        int i = appRow.uid;
        boolean z = !((Boolean) obj).booleanValue();
        this.mBackend.getClass();
        try {
            NotificationBackend.sINM.setInvalidMsgAppDemoted(str, i, z);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
        }
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        boolean z;
        if (this.mAppRow == null) {
            return;
        }
        RestrictedSwitchPreference restrictedSwitchPreference =
                (RestrictedSwitchPreference) preference;
        restrictedSwitchPreference.setDisabledByAdmin(this.mAdmin);
        restrictedSwitchPreference.setEnabled(!restrictedSwitchPreference.mHelper.mDisabledByAdmin);
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        try {
            z = NotificationBackend.sINM.hasUserDemotedInvalidMsgApp(str, i);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            z = false;
        }
        restrictedSwitchPreference.setChecked(!z);
        preference.setSummary(
                ((NotificationPreferenceController) this)
                        .mContext.getString(R.string.conversation_section_switch_summary));
    }
}
