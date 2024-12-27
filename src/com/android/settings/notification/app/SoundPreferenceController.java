package com.android.settings.notification.app;

import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.secutil.Log;

import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.settings.notification.NotiUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SoundPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin,
                Preference.OnPreferenceChangeListener,
                PreferenceManager.OnActivityResultListener {
    public final SettingsPreferenceFragment mFragment;
    public final NotificationSettings.DependentFieldListener mListener;
    public NotificationSoundPreference mPreference;

    public SoundPreferenceController(
            Context context,
            SettingsPreferenceFragment settingsPreferenceFragment,
            NotificationSettings.DependentFieldListener dependentFieldListener,
            NotificationBackend notificationBackend) {
        super(context, notificationBackend);
        this.mFragment = settingsPreferenceFragment;
        this.mListener = dependentFieldListener;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (NotificationSoundPreference) preferenceScreen.findPreference("ringtone");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "ringtone";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!"ringtone".equals(preference.getKey()) || this.mFragment == null) {
            return false;
        }
        NotificationSoundPreference notificationSoundPreference =
                (NotificationSoundPreference) preference;
        notificationSoundPreference.onPrepareRingtonePickerIntent(
                notificationSoundPreference.getIntent());
        this.mFragment.startActivityForResult(preference.getIntent(), 200);
        return true;
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return super.isAvailable()
                && this.mChannel != null
                && checkCanBeVisible()
                && !isDefaultChannel();
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return this.mPreferenceFilter.contains("sound");
    }

    @Override // android.preference.PreferenceManager.OnActivityResultListener
    public final boolean onActivityResult(int i, int i2, Intent intent) {
        if (200 != i) {
            return false;
        }
        NotificationSoundPreference notificationSoundPreference = this.mPreference;
        if (notificationSoundPreference != null) {
            notificationSoundPreference.onActivityResult(i, i2, intent);
        }
        this.mListener.onFieldValueChanged();
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        NotificationChannel notificationChannel = this.mChannel;
        if (notificationChannel == null) {
            return true;
        }
        notificationChannel.setSound((Uri) obj, notificationChannel.getAudioAttributes());
        saveChannel();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        NotificationChannel notificationChannel;
        if (this.mAppRow == null || (notificationChannel = this.mChannel) == null) {
            return;
        }
        NotificationSoundPreference notificationSoundPreference =
                (NotificationSoundPreference) preference;
        notificationSoundPreference.setEnabled(
                this.mAdmin == null && isChannelConfigurable(notificationChannel));
        notificationSoundPreference.setRingtone(this.mChannel.getSound());
        notificationSoundPreference.mRingtoneType = 2;
        String str = this.mAppRow.pkg;
        String id = this.mChannel.getId();
        if (!id.contains("[SEC_SIM2]")) {
            String m = AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(str, ":", id);
            if (m.equals(NotiUtils.mSIM2Channels[0])) {
                Log.secD("NotiUtils", "isSIM2NotiChannel true - ".concat(m));
            }
            SecPreferenceUtils.applySummaryColor(notificationSoundPreference, true);
        }
        Log.secD("NotiUtils", "isSIM2NotiChannel true - ".concat(id));
        notificationSoundPreference.mRingtoneType = 256;
        SecPreferenceUtils.applySummaryColor(notificationSoundPreference, true);
    }
}
