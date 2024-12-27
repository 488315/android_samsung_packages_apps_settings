package com.android.settings.notification;

import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioSystem;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.DefaultRingtonePreference;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SoundWorkSettingsController extends AbstractPreferenceController
        implements Preference.OnPreferenceChangeListener, LifecycleObserver, OnResume, OnPause {
    public final AudioHelper mHelper;
    public int mManagedProfileId;
    public final AnonymousClass1 mManagedProfileReceiver;
    public final SoundWorkSettings mParent;
    public PreferenceScreen mScreen;
    public final UserManager mUserManager;
    public final boolean mVoiceCapable;
    public DefaultRingtonePreference mWorkAlarmRingtonePreference;
    public DefaultRingtonePreference mWorkNotificationRingtonePreference;
    public DefaultRingtonePreference mWorkPhoneRingtonePreference;
    public TwoStatePreference mWorkUsePersonalSounds;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class UnifyWorkDialogFragment extends InstrumentedDialogFragment
            implements DialogInterface.OnClickListener {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 553;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            SoundWorkSettingsController soundWorkSettingsController;
            SoundWorkSettings soundWorkSettings = (SoundWorkSettings) getTargetFragment();
            if (!soundWorkSettings.isAdded()
                    || (soundWorkSettingsController =
                                    (SoundWorkSettingsController)
                                            soundWorkSettings.use(
                                                    SoundWorkSettingsController.class))
                            == null) {
                return;
            }
            Settings.Secure.putIntForUser(
                    soundWorkSettingsController.getManagedProfileContext().getContentResolver(),
                    "sync_parent_sounds",
                    1,
                    soundWorkSettingsController.mManagedProfileId);
            soundWorkSettingsController.enableWorkSyncSettings();
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            Context applicationContext = getActivity().getApplicationContext();
            DevicePolicyManager devicePolicyManager =
                    (DevicePolicyManager)
                            applicationContext.getSystemService(DevicePolicyManager.class);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String string =
                    devicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.ENABLE_WORK_PROFILE_SYNC_WITH_PERSONAL_SOUNDS_DIALOG_TITLE",
                                    new SoundWorkSettingsController$$ExternalSyntheticLambda0(
                                            1, applicationContext));
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = string;
            alertParams.mMessage =
                    devicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.ENABLE_WORK_PROFILE_SYNC_WITH_PERSONAL_SOUNDS_DIALOG_MESSAGE",
                                    new SoundWorkSettingsController$$ExternalSyntheticLambda0(
                                            2, applicationContext));
            builder.setPositiveButton(R.string.work_sync_dialog_yes, this);
            builder.setNegativeButton(
                    android.R.string.cancel, (DialogInterface.OnClickListener) null);
            return builder.create();
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.notification.SoundWorkSettingsController$1] */
    public SoundWorkSettingsController(
            Context context,
            SoundWorkSettings soundWorkSettings,
            Lifecycle lifecycle,
            AudioHelper audioHelper) {
        super(context);
        this.mManagedProfileReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.notification.SoundWorkSettingsController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int identifier =
                                ((UserHandle) intent.getExtra("android.intent.extra.USER"))
                                        .getIdentifier();
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.intent.action.MANAGED_PROFILE_ADDED")) {
                            SoundWorkSettingsController soundWorkSettingsController =
                                    SoundWorkSettingsController.this;
                            if (soundWorkSettingsController.mManagedProfileId == -10000) {
                                soundWorkSettingsController.mManagedProfileId = identifier;
                                soundWorkSettingsController.updateWorkPreferences();
                                return;
                            }
                            return;
                        }
                        if (action.equals("android.intent.action.MANAGED_PROFILE_REMOVED")) {
                            SoundWorkSettingsController soundWorkSettingsController2 =
                                    SoundWorkSettingsController.this;
                            if (soundWorkSettingsController2.mManagedProfileId == identifier) {
                                UserManager userManager = soundWorkSettingsController2.mUserManager;
                                soundWorkSettingsController2.mHelper.getClass();
                                soundWorkSettingsController2.mManagedProfileId =
                                        AudioHelper.getManagedProfileId(userManager);
                                soundWorkSettingsController2.updateWorkPreferences();
                            }
                        }
                    }
                };
        this.mUserManager = UserManager.get(context);
        this.mVoiceCapable = Utils.isVoiceCapable(this.mContext);
        this.mParent = soundWorkSettings;
        this.mHelper = audioHelper;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    public final void disableWorkSyncSettings() {
        DefaultRingtonePreference defaultRingtonePreference = this.mWorkPhoneRingtonePreference;
        if (defaultRingtonePreference != null) {
            defaultRingtonePreference.setEnabled(true);
        }
        this.mWorkNotificationRingtonePreference.setEnabled(true);
        this.mWorkAlarmRingtonePreference.setEnabled(true);
        Context managedProfileContext = getManagedProfileContext();
        DefaultRingtonePreference defaultRingtonePreference2 = this.mWorkPhoneRingtonePreference;
        if (defaultRingtonePreference2 != null) {
            defaultRingtonePreference2.setSummary(updateRingtoneName$1(managedProfileContext, 1));
        }
        this.mWorkNotificationRingtonePreference.setSummary(
                updateRingtoneName$1(managedProfileContext, 2));
        this.mWorkAlarmRingtonePreference.setSummary(
                updateRingtoneName$1(managedProfileContext, 4));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mScreen = preferenceScreen;
    }

    public final void enableWorkSyncSettings() {
        this.mWorkUsePersonalSounds.setChecked(true);
        String string =
                ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class))
                        .getResources()
                        .getString(
                                "Settings.WORK_PROFILE_SYNC_WITH_PERSONAL_SOUNDS_ACTIVE_SUMMARY",
                                new SoundWorkSettingsController$$ExternalSyntheticLambda0(0, this));
        DefaultRingtonePreference defaultRingtonePreference = this.mWorkPhoneRingtonePreference;
        if (defaultRingtonePreference != null) {
            defaultRingtonePreference.setSummary(string);
        }
        this.mWorkNotificationRingtonePreference.setSummary(string);
        this.mWorkAlarmRingtonePreference.setSummary(string);
    }

    public final Context getManagedProfileContext() {
        int i = this.mManagedProfileId;
        if (i == -10000) {
            return null;
        }
        return Utils.createPackageContextAsUser(this.mHelper.mContext, i);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        UserManager userManager = this.mUserManager;
        AudioHelper audioHelper = this.mHelper;
        audioHelper.getClass();
        return AudioHelper.getManagedProfileId(userManager) != -10000
                && (AudioSystem.isSingleVolume(audioHelper.mContext) ^ true);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        this.mContext.unregisterReceiver(this.mManagedProfileReceiver);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        int i;
        if ("work_ringtone".equals(preference.getKey())) {
            i = 1;
        } else {
            if (!"work_notification_ringtone".equals(preference.getKey())) {
                if ("work_alarm_ringtone".equals(preference.getKey())) {
                    i = 4;
                }
                return true;
            }
            i = 2;
        }
        preference.setSummary(updateRingtoneName$1(getManagedProfileContext(), i));
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        this.mContext.registerReceiver(
                this.mManagedProfileReceiver,
                ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                        "android.intent.action.MANAGED_PROFILE_ADDED",
                        "android.intent.action.MANAGED_PROFILE_REMOVED"));
        UserManager userManager = this.mUserManager;
        this.mHelper.getClass();
        this.mManagedProfileId = AudioHelper.getManagedProfileId(userManager);
        updateWorkPreferences();
    }

    public final CharSequence updateRingtoneName$1(Context context, int i) {
        if (context != null) {
            UserManager userManager = this.mUserManager;
            int userId = context.getUserId();
            this.mHelper.getClass();
            if (userManager.isUserUnlocked(userId)) {
                return Ringtone.getTitle(
                        context,
                        RingtoneManager.getActualDefaultRingtoneUri(context, i),
                        false,
                        true);
            }
        }
        return this.mContext.getString(R.string.managed_profile_not_available_label);
    }

    public final void updateWorkPreferences() {
        if (isAvailable()) {
            if (this.mWorkUsePersonalSounds == null) {
                TwoStatePreference twoStatePreference =
                        (TwoStatePreference)
                                this.mScreen.findPreference("work_use_personal_sounds");
                this.mWorkUsePersonalSounds = twoStatePreference;
                twoStatePreference.setOnPreferenceChangeListener(
                        new Preference
                                .OnPreferenceChangeListener() { // from class:
                                                                // com.android.settings.notification.SoundWorkSettingsController$$ExternalSyntheticLambda1
                            @Override // androidx.preference.Preference.OnPreferenceChangeListener
                            public final boolean onPreferenceChange(
                                    Preference preference, Object obj) {
                                SoundWorkSettingsController soundWorkSettingsController =
                                        SoundWorkSettingsController.this;
                                soundWorkSettingsController.getClass();
                                if (!((Boolean) obj).booleanValue()) {
                                    Settings.Secure.putIntForUser(
                                            soundWorkSettingsController
                                                    .getManagedProfileContext()
                                                    .getContentResolver(),
                                            "sync_parent_sounds",
                                            0,
                                            soundWorkSettingsController.mManagedProfileId);
                                    soundWorkSettingsController.disableWorkSyncSettings();
                                    return true;
                                }
                                SoundWorkSettings soundWorkSettings =
                                        soundWorkSettingsController.mParent;
                                FragmentManager fragmentManager =
                                        soundWorkSettings.getFragmentManager();
                                if (fragmentManager.findFragmentByTag("UnifyWorkDialogFragment")
                                        != null) {
                                    return false;
                                }
                                SoundWorkSettingsController.UnifyWorkDialogFragment
                                        unifyWorkDialogFragment =
                                                new SoundWorkSettingsController
                                                        .UnifyWorkDialogFragment();
                                unifyWorkDialogFragment.setTargetFragment(soundWorkSettings, 200);
                                unifyWorkDialogFragment.show(
                                        fragmentManager, "UnifyWorkDialogFragment");
                                return false;
                            }
                        });
            }
            if (this.mWorkPhoneRingtonePreference == null) {
                DefaultRingtonePreference defaultRingtonePreference =
                        (DefaultRingtonePreference) this.mScreen.findPreference("work_ringtone");
                defaultRingtonePreference.setOnPreferenceChangeListener(this);
                defaultRingtonePreference.mUserId = this.mManagedProfileId;
                defaultRingtonePreference.mUserContext =
                        Utils.createPackageContextAsUser(
                                defaultRingtonePreference.getContext(),
                                defaultRingtonePreference.mUserId);
                this.mWorkPhoneRingtonePreference = defaultRingtonePreference;
            }
            if (this.mWorkNotificationRingtonePreference == null) {
                DefaultRingtonePreference defaultRingtonePreference2 =
                        (DefaultRingtonePreference)
                                this.mScreen.findPreference("work_notification_ringtone");
                defaultRingtonePreference2.setOnPreferenceChangeListener(this);
                defaultRingtonePreference2.mUserId = this.mManagedProfileId;
                defaultRingtonePreference2.mUserContext =
                        Utils.createPackageContextAsUser(
                                defaultRingtonePreference2.getContext(),
                                defaultRingtonePreference2.mUserId);
                this.mWorkNotificationRingtonePreference = defaultRingtonePreference2;
            }
            if (this.mWorkAlarmRingtonePreference == null) {
                DefaultRingtonePreference defaultRingtonePreference3 =
                        (DefaultRingtonePreference)
                                this.mScreen.findPreference("work_alarm_ringtone");
                defaultRingtonePreference3.setOnPreferenceChangeListener(this);
                defaultRingtonePreference3.mUserId = this.mManagedProfileId;
                defaultRingtonePreference3.mUserContext =
                        Utils.createPackageContextAsUser(
                                defaultRingtonePreference3.getContext(),
                                defaultRingtonePreference3.mUserId);
                this.mWorkAlarmRingtonePreference = defaultRingtonePreference3;
            }
            if (!this.mVoiceCapable) {
                this.mWorkPhoneRingtonePreference.setVisible(false);
                this.mWorkPhoneRingtonePreference = null;
            }
            if (Settings.Secure.getIntForUser(
                            getManagedProfileContext().getContentResolver(),
                            "sync_parent_sounds",
                            0,
                            this.mManagedProfileId)
                    == 1) {
                enableWorkSyncSettings();
            } else {
                disableWorkSyncSettings();
            }
        }
    }
}
