package com.samsung.android.settings.asbase.work;

import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioSystem;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.DefaultRingtonePreference;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.notification.AudioHelper;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.asbase.rune.AudioRune;
import com.samsung.android.settings.asbase.utils.SimUtils;
import com.samsung.android.settings.asbase.utils.SoundUtils;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecSoundWorkSettingsController extends AbstractPreferenceController
        implements Preference.OnPreferenceChangeListener, LifecycleObserver, OnResume, OnPause {
    public final AudioHelper mHelper;
    public int mManagedProfileId;
    public final AnonymousClass1 mManagedProfileReceiver;
    public final SecSoundWorkSettings mParent;
    public PreferenceScreen mScreen;
    public final UserManager mUserManager;
    public final boolean mVoiceCapable;
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
            SecSoundWorkSettingsController secSoundWorkSettingsController;
            SecSoundWorkSettings secSoundWorkSettings = (SecSoundWorkSettings) getTargetFragment();
            if (secSoundWorkSettings == null
                    || !secSoundWorkSettings.isAdded()
                    || (secSoundWorkSettingsController =
                                    (SecSoundWorkSettingsController)
                                            secSoundWorkSettings.use(
                                                    SecSoundWorkSettingsController.class))
                            == null) {
                return;
            }
            Settings.Secure.putIntForUser(
                    secSoundWorkSettingsController
                            .getManagedProfileContext$1()
                            .getContentResolver(),
                    "sync_parent_sounds",
                    1,
                    secSoundWorkSettingsController.mManagedProfileId);
            secSoundWorkSettingsController.enableWorkSyncSettings$1();
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            FragmentActivity activity = getActivity();
            Objects.requireNonNull(activity);
            Context applicationContext = activity.getApplicationContext();
            DevicePolicyManager devicePolicyManager =
                    (DevicePolicyManager)
                            applicationContext.getSystemService(DevicePolicyManager.class);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String string =
                    devicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.ENABLE_WORK_PROFILE_SYNC_WITH_PERSONAL_SOUNDS_DIALOG_TITLE",
                                    new SecSoundWorkSettingsController$$ExternalSyntheticLambda0(
                                            1, applicationContext));
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = string;
            alertParams.mMessage =
                    devicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.ENABLE_WORK_PROFILE_SYNC_WITH_PERSONAL_SOUNDS_DIALOG_MESSAGE",
                                    new SecSoundWorkSettingsController$$ExternalSyntheticLambda0(
                                            2, applicationContext));
            builder.setPositiveButton(R.string.work_sync_dialog_yes, this);
            builder.setNegativeButton(android.R.string.no, (DialogInterface.OnClickListener) null);
            return builder.create();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.settings.asbase.work.SecSoundWorkSettingsController$1] */
    public SecSoundWorkSettingsController(
            Context context, SecSoundWorkSettings secSoundWorkSettings, Lifecycle lifecycle) {
        super(context);
        AudioHelper audioHelper = new AudioHelper(context);
        this.mManagedProfileReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.asbase.work.SecSoundWorkSettingsController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int identifier =
                                ((UserHandle) intent.getExtra("android.intent.extra.USER"))
                                        .getIdentifier();
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.intent.action.MANAGED_PROFILE_ADDED")) {
                            SecSoundWorkSettingsController secSoundWorkSettingsController =
                                    SecSoundWorkSettingsController.this;
                            if (secSoundWorkSettingsController.mManagedProfileId == -10000) {
                                secSoundWorkSettingsController.mManagedProfileId = identifier;
                                secSoundWorkSettingsController.updateWorkPreferences$1();
                                return;
                            }
                            return;
                        }
                        if (action.equals("android.intent.action.MANAGED_PROFILE_REMOVED")) {
                            SecSoundWorkSettingsController secSoundWorkSettingsController2 =
                                    SecSoundWorkSettingsController.this;
                            if (secSoundWorkSettingsController2.mManagedProfileId == identifier) {
                                UserManager userManager =
                                        secSoundWorkSettingsController2.mUserManager;
                                secSoundWorkSettingsController2.mHelper.getClass();
                                secSoundWorkSettingsController2.mManagedProfileId =
                                        AudioHelper.getManagedProfileId(userManager);
                                secSoundWorkSettingsController2.updateWorkPreferences$1();
                            }
                        }
                    }
                };
        this.mUserManager = UserManager.get(context);
        this.mVoiceCapable =
                Utils.isVoiceCapable(this.mContext)
                        || SoundUtils.isRingtoneMenuSupported(this.mContext);
        this.mParent = secSoundWorkSettings;
        this.mHelper = audioHelper;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    public final void disableWorkSyncSettings$1() {
        CharSequence updateRingtoneName$2;
        CharSequence updateRingtoneName$22;
        DefaultRingtonePreference defaultRingtonePreference = this.mWorkPhoneRingtonePreference;
        if (defaultRingtonePreference != null) {
            defaultRingtonePreference.setEnabled(true);
        }
        DefaultRingtonePreference defaultRingtonePreference2 =
                this.mWorkNotificationRingtonePreference;
        if (defaultRingtonePreference2 != null) {
            defaultRingtonePreference2.setEnabled(true);
        }
        Context managedProfileContext$1 = getManagedProfileContext$1();
        if (this.mWorkPhoneRingtonePreference != null) {
            if (SimUtils.isMultiSimEnabled(this.mContext)) {
                updateRingtoneName$22 =
                        SimUtils.getSimName(this.mContext, 0)
                                + " : "
                                + ((Object) updateRingtoneName$2(managedProfileContext$1, 1))
                                + "\n"
                                + SimUtils.getSimName(this.mContext, 1)
                                + " : "
                                + ((Object) updateRingtoneName$2(managedProfileContext$1, 128));
            } else {
                updateRingtoneName$22 =
                        SimUtils.isEnabledSIM2Only()
                                ? updateRingtoneName$2(managedProfileContext$1, 128)
                                : updateRingtoneName$2(managedProfileContext$1, 1);
            }
            this.mWorkPhoneRingtonePreference.setSummary(updateRingtoneName$22);
        }
        if (this.mWorkNotificationRingtonePreference != null) {
            if (SimUtils.isMultiSimEnabled(this.mContext)) {
                updateRingtoneName$2 =
                        SimUtils.getSimName(this.mContext, 0)
                                + " : "
                                + ((Object) updateRingtoneName$2(managedProfileContext$1, 2))
                                + "\n"
                                + SimUtils.getSimName(this.mContext, 1)
                                + " : "
                                + ((Object) updateRingtoneName$2(managedProfileContext$1, 256));
            } else {
                updateRingtoneName$2 =
                        SimUtils.isEnabledSIM2Only()
                                ? updateRingtoneName$2(managedProfileContext$1, 256)
                                : updateRingtoneName$2(managedProfileContext$1, 2);
            }
            this.mWorkNotificationRingtonePreference.setSummary(updateRingtoneName$2);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mScreen = preferenceScreen;
    }

    public final void enableWorkSyncSettings$1() {
        this.mWorkUsePersonalSounds.setChecked(true);
        String string =
                ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class))
                        .getResources()
                        .getString(
                                "Settings.WORK_PROFILE_SYNC_WITH_PERSONAL_SOUNDS_ACTIVE_SUMMARY",
                                new SecSoundWorkSettingsController$$ExternalSyntheticLambda0(
                                        0, this));
        DefaultRingtonePreference defaultRingtonePreference = this.mWorkPhoneRingtonePreference;
        if (defaultRingtonePreference != null) {
            defaultRingtonePreference.setSummary(string);
        }
        DefaultRingtonePreference defaultRingtonePreference2 =
                this.mWorkNotificationRingtonePreference;
        if (defaultRingtonePreference2 != null) {
            defaultRingtonePreference2.setSummary(string);
        }
    }

    public final Context getManagedProfileContext$1() {
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

    public final DefaultRingtonePreference initWorkPreference$1(
            PreferenceGroup preferenceGroup, String str) {
        DefaultRingtonePreference defaultRingtonePreference =
                (DefaultRingtonePreference) preferenceGroup.findPreference(str);
        defaultRingtonePreference.setOnPreferenceChangeListener(this);
        defaultRingtonePreference.mUserId = this.mManagedProfileId;
        defaultRingtonePreference.mUserContext =
                Utils.createPackageContextAsUser(
                        defaultRingtonePreference.getContext(), defaultRingtonePreference.mUserId);
        if (SimUtils.isEnabledSIM2Only()) {
            if ("work_ringtone".equals(str)) {
                defaultRingtonePreference.mRingtoneType = 128;
            } else if ("work_notification_ringtone".equals(str)) {
                defaultRingtonePreference.mRingtoneType = 256;
            }
        }
        return defaultRingtonePreference;
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
        CharSequence updateRingtoneName$2;
        Context managedProfileContext$1 = getManagedProfileContext$1();
        if (!"work_ringtone".equals(preference.getKey())) {
            if ("work_notification_ringtone".equals(preference.getKey())) {
                if (SimUtils.isMultiSimEnabled(this.mContext)) {
                    updateRingtoneName$2 =
                            SimUtils.getSimName(this.mContext, 0)
                                    + " : "
                                    + ((Object) updateRingtoneName$2(managedProfileContext$1, 2))
                                    + "\n"
                                    + SimUtils.getSimName(this.mContext, 1)
                                    + " : "
                                    + ((Object) updateRingtoneName$2(managedProfileContext$1, 256));
                } else {
                    updateRingtoneName$2 =
                            SimUtils.isEnabledSIM2Only()
                                    ? updateRingtoneName$2(managedProfileContext$1, 256)
                                    : updateRingtoneName$2(managedProfileContext$1, 2);
                }
            }
            return true;
        }
        if (SimUtils.isMultiSimEnabled(this.mContext)) {
            updateRingtoneName$2 =
                    SimUtils.getSimName(this.mContext, 0)
                            + " : "
                            + ((Object) updateRingtoneName$2(managedProfileContext$1, 1))
                            + "\n"
                            + SimUtils.getSimName(this.mContext, 1)
                            + " : "
                            + ((Object) updateRingtoneName$2(managedProfileContext$1, 128));
        } else {
            updateRingtoneName$2 =
                    SimUtils.isEnabledSIM2Only()
                            ? updateRingtoneName$2(managedProfileContext$1, 128)
                            : updateRingtoneName$2(managedProfileContext$1, 1);
        }
        preference.setSummary(updateRingtoneName$2);
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        this.mContext.registerReceiver(
                this.mManagedProfileReceiver,
                ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                        "android.intent.action.MANAGED_PROFILE_ADDED",
                        "android.intent.action.MANAGED_PROFILE_REMOVED"),
                2);
        UserManager userManager = this.mUserManager;
        this.mHelper.getClass();
        this.mManagedProfileId = AudioHelper.getManagedProfileId(userManager);
        updateWorkPreferences$1();
    }

    public final CharSequence updateRingtoneName$2(Context context, int i) {
        if (context != null) {
            UserManager userManager = this.mUserManager;
            int userId = context.getUserId();
            this.mHelper.getClass();
            if (userManager.isUserUnlocked(userId)) {
                Uri actualDefaultRingtoneUri =
                        RingtoneManager.getActualDefaultRingtoneUri(context, i);
                return AudioRune.SUPPORT_SOUND_THEME
                        ? Ringtone.getTitleWithSoundTheme(
                                context, actualDefaultRingtoneUri, false, true)
                        : Ringtone.getTitle(context, actualDefaultRingtoneUri, false, true);
            }
        }
        return this.mContext.getString(R.string.managed_profile_not_available_label);
    }

    public final void updateWorkPreferences$1() {
        if (isAvailable()) {
            if (this.mWorkUsePersonalSounds == null) {
                TwoStatePreference twoStatePreference =
                        (TwoStatePreference)
                                this.mScreen.findPreference("work_use_personal_sounds");
                this.mWorkUsePersonalSounds = twoStatePreference;
                twoStatePreference.setOnPreferenceChangeListener(
                        new Preference
                                .OnPreferenceChangeListener() { // from class:
                                                                // com.samsung.android.settings.asbase.work.SecSoundWorkSettingsController$$ExternalSyntheticLambda1
                            @Override // androidx.preference.Preference.OnPreferenceChangeListener
                            public final boolean onPreferenceChange(
                                    Preference preference, Object obj) {
                                SecSoundWorkSettingsController secSoundWorkSettingsController =
                                        SecSoundWorkSettingsController.this;
                                secSoundWorkSettingsController.getClass();
                                if (!((Boolean) obj).booleanValue()) {
                                    Settings.Secure.putIntForUser(
                                            secSoundWorkSettingsController
                                                    .getManagedProfileContext$1()
                                                    .getContentResolver(),
                                            "sync_parent_sounds",
                                            0,
                                            secSoundWorkSettingsController.mManagedProfileId);
                                    secSoundWorkSettingsController.disableWorkSyncSettings$1();
                                    return true;
                                }
                                SecSoundWorkSettings secSoundWorkSettings =
                                        secSoundWorkSettingsController.mParent;
                                FragmentManager fragmentManager =
                                        secSoundWorkSettings.getFragmentManager();
                                if (fragmentManager.findFragmentByTag("UnifyWorkDialogFragment")
                                        != null) {
                                    return false;
                                }
                                SecSoundWorkSettingsController.UnifyWorkDialogFragment
                                        unifyWorkDialogFragment =
                                                new SecSoundWorkSettingsController
                                                        .UnifyWorkDialogFragment();
                                unifyWorkDialogFragment.setTargetFragment(
                                        secSoundWorkSettings, 200);
                                unifyWorkDialogFragment.show(
                                        fragmentManager, "UnifyWorkDialogFragment");
                                return false;
                            }
                        });
            }
            if (this.mWorkPhoneRingtonePreference == null) {
                this.mWorkPhoneRingtonePreference =
                        initWorkPreference$1(this.mScreen, "work_ringtone");
            }
            if (this.mWorkNotificationRingtonePreference == null) {
                this.mWorkNotificationRingtonePreference =
                        initWorkPreference$1(this.mScreen, "work_notification_ringtone");
            }
            if (!this.mVoiceCapable) {
                this.mWorkPhoneRingtonePreference.setVisible(false);
                this.mWorkPhoneRingtonePreference = null;
            }
            if (Settings.Secure.getIntForUser(
                            getManagedProfileContext$1().getContentResolver(),
                            "sync_parent_sounds",
                            0,
                            this.mManagedProfileId)
                    == 1) {
                enableWorkSyncSettings$1();
            } else {
                disableWorkSyncSettings$1();
            }
        }
    }
}
