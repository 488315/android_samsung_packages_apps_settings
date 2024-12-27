package com.samsung.android.settings.notification;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.NotificationPreferenceController;
import com.android.settings.notification.app.NotificationSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NoneRadioBubblePreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public boolean mIsAppPage;
    public NotificationSettings.DependentFieldListener mListener;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bubble_none";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable()) {
            return false;
        }
        if (this.mIsAppPage || isFloatingIconBubble()) {
            return this.mChannel == null || isDefaultChannel() || this.mAppRow != null;
        }
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (this.mAppRow != null && ((Boolean) obj).booleanValue()) {
            NotificationBackend.AppRow appRow = this.mAppRow;
            appRow.bubblePreference = 0;
            String str = appRow.pkg;
            int i = appRow.uid;
            this.mBackend.getClass();
            NotificationBackend.setAllowBubbles(i, 0, str);
        }
        NotificationSettings.DependentFieldListener dependentFieldListener = this.mListener;
        if (dependentFieldListener == null) {
            return true;
        }
        dependentFieldListener.onFieldValueChanged();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow != null) {
            String str = appRow.pkg;
            int i = appRow.uid;
            this.mBackend.getClass();
            ((RadioPreference) preference)
                    .setChecked(
                            !isFloatingIconBubble()
                                    || NotificationBackend.getBubblePreference(i, str) == 0);
        }
    }
}
