package com.samsung.android.settings.notification;

import android.content.Context;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.NotificationPreferenceController;
import com.android.settings.notification.app.NotificationSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecAlertConversationPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public final NotificationSettings.DependentFieldListener mDependentFieldListener;

    public SecAlertConversationPreferenceController(
            Context context,
            NotificationBackend notificationBackend,
            NotificationSettings.DependentFieldListener dependentFieldListener) {
        super(context, notificationBackend);
        this.mDependentFieldListener = dependentFieldListener;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "alert_importance";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        NotificationBackend.AppRow appRow;
        if (super.isAvailable() && (appRow = this.mAppRow) != null && this.mChannel != null) {
            String str = appRow.pkg;
            int i = appRow.uid;
            this.mBackend.getClass();
            if (NotificationBackend.getNotificationAlertsEnabledForPackage(i, str)) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (this.mChannel == null) {
            return false;
        }
        if (!((Boolean) obj).booleanValue()) {
            return true;
        }
        this.mChannel.setImportance(3);
        this.mChannel.setImportantConversation(false);
        this.mChannel.setAllowBubbles(false);
        this.mDependentFieldListener.onFieldValueChanged();
        saveChannel();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mAppRow == null || this.mChannel == null) {
            return;
        }
        boolean z = false;
        boolean z2 = this.mAdmin == null;
        preference.setEnabled(z2);
        SecRadioPreference secRadioPreference = (SecRadioPreference) preference;
        if (this.mChannel.getImportance() >= 3 && !this.mChannel.isImportantConversation()) {
            z = true;
        }
        secRadioPreference.setChecked(z);
        secRadioPreference.setIconEnabled(z2);
        secRadioPreference.setRadioColor(z);
        secRadioPreference.seslSetDividerStartOffset(
                (int)
                        ((NotificationPreferenceController) this)
                                .mContext
                                .getResources()
                                .getDimension(R.dimen.sec_widget_app_list_divider_margin_start));
    }
}
