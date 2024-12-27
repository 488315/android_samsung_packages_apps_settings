package com.android.settings.notification.app;

import android.app.NotificationChannel;
import android.content.Context;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.RestrictedSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AllowSoundPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public final NotificationSettings.DependentFieldListener mDependentFieldListener;

    public AllowSoundPreferenceController(
            Context context,
            NotificationBackend notificationBackend,
            NotificationSettings.DependentFieldListener dependentFieldListener) {
        super(context, notificationBackend);
        this.mDependentFieldListener = dependentFieldListener;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "allow_sound";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        NotificationChannel notificationChannel;
        return super.isAvailable()
                && (notificationChannel = this.mChannel) != null
                && "miscellaneous".equals(notificationChannel.getId());
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return this.mPreferenceFilter.contains("sound");
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (this.mChannel == null) {
            return true;
        }
        this.mChannel.setImportance(((Boolean) obj).booleanValue() ? -1000 : 2);
        this.mChannel.lockFields(4);
        saveChannel();
        this.mDependentFieldListener.onFieldValueChanged();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mChannel == null) {
            Log.i("AllowSoundPrefContr", "tried to updatestate on a null channel?!");
            return;
        }
        RestrictedSwitchPreference restrictedSwitchPreference =
                (RestrictedSwitchPreference) preference;
        restrictedSwitchPreference.setDisabledByAdmin(this.mAdmin);
        restrictedSwitchPreference.setEnabled(
                !restrictedSwitchPreference.mHelper.mDisabledByAdmin
                        && isChannelConfigurable(this.mChannel));
        restrictedSwitchPreference.setChecked(
                this.mChannel.getImportance() >= 3 || this.mChannel.getImportance() == -1000);
    }
}
