package com.android.settings.security;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.knox.UCMUtils;
import com.samsung.android.settings.security.BiometricRemoveWarningDialog;
import com.samsung.android.settings.security.WorkProfileSecuritySettings;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LockUnificationPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public static final int MY_USER_ID = UserHandle.myUserId();
    public LockscreenCredential mCurrentDevicePassword;
    public LockscreenCredential mCurrentProfilePassword;
    public final DevicePolicyManager mDpm;
    public final FaceManager mFaceManager;
    public final FingerprintManager mFingerprintManager;
    public final SettingsPreferenceFragment mHost;
    public final LockPatternUtils mLockPatternUtils;
    public final String mPreferenceKey;
    public final int mProfileUserId;
    public boolean mRequireNewDevicePassword;
    public final UserManager mUm;
    public RestrictedSwitchPreference mUnifyProfile;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.security.LockUnificationPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}

        public final void updatePreference() {
            SettingsPreferenceFragment settingsPreferenceFragment =
                    LockUnificationPreferenceController.this.mHost;
            if (settingsPreferenceFragment instanceof WorkProfileSecuritySettings) {
                ((WorkProfileSecuritySettings) settingsPreferenceFragment)
                        .updateUnificationPreference();
            }
        }
    }

    public LockUnificationPreferenceController(
            Context context, SettingsPreferenceFragment settingsPreferenceFragment) {
        super(context);
        this.mHost = settingsPreferenceFragment;
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        this.mUm = userManager;
        this.mDpm = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mLockPatternUtils =
                featureFactoryImpl.getSecurityFeatureProvider().getLockPatternUtils(context);
        this.mProfileUserId = Utils.getManagedProfileId(userManager, MY_USER_ID);
        this.mCurrentDevicePassword = LockscreenCredential.createNone();
        this.mCurrentProfilePassword = LockscreenCredential.createNone();
        this.mPreferenceKey = "unification";
        this.mFingerprintManager = Utils.getFingerprintManagerOrNull(context);
        this.mFaceManager = Utils.getFaceManagerOrNull(context);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mUnifyProfile =
                (RestrictedSwitchPreference) preferenceScreen.findPreference(this.mPreferenceKey);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.mPreferenceKey;
    }

    public final boolean handleActivityResult(int i, int i2, Intent intent) {
        if (i == 130 && i2 == -1) {
            this.mCurrentDevicePassword = intent.getParcelableExtra(HostAuth.PASSWORD);
            ununifyLocks();
            return true;
        }
        if (i != 129 || i2 != -1) {
            return false;
        }
        this.mCurrentProfilePassword = intent.getParcelableExtra(HostAuth.PASSWORD);
        startUnifyLocks();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        int i = this.mProfileUserId;
        return (i == -10000
                        || !this.mUm.isManagedProfile(i)
                        || SemPersonaManager.isDarDualEncryptionEnabled(i))
                ? false
                : true;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Context context = this.mContext;
        UserManager userManager = this.mUm;
        int i = this.mProfileUserId;
        if (Utils.startQuietModeDialogIfNecessary(context, userManager, i)) {
            return false;
        }
        SettingsPreferenceFragment settingsPreferenceFragment = this.mHost;
        if (!KnoxUtils.isAvailableWithMultiWindowForKnox(
                settingsPreferenceFragment.getActivity(), i)) {
            Toast.makeText(
                            settingsPreferenceFragment.getActivity(),
                            this.mContext.getString(
                                    R.string.lock_screen_doesnt_support_multi_window_text),
                            0)
                    .show();
            return false;
        }
        if (((Boolean) obj).booleanValue()) {
            this.mRequireNewDevicePassword =
                    (this.mDpm.isPasswordSufficientAfterProfileUnification(UserHandle.myUserId(), i)
                                    && SemPersonaManager
                                            .isPasswordSufficientAfterKnoxProfileUnification(i))
                            ? false
                            : true;
            startUnification();
        } else {
            String string =
                    this.mContext.getString(R.string.sec_unlock_set_unlock_launch_picker_title);
            ChooseLockSettingsHelper.Builder builder =
                    new ChooseLockSettingsHelper.Builder(
                            settingsPreferenceFragment.getActivity(), settingsPreferenceFragment);
            builder.mRequestCode = 130;
            builder.mTitle = string;
            builder.mReturnCredentials = true;
            builder.mUserId = MY_USER_ID;
            if (!builder.show()) {
                ununifyLocks();
            }
        }
        return true;
    }

    public final void startUnification() {
        String string =
                this.mDpm
                        .getResources()
                        .getString(
                                "Settings.WORK_PROFILE_SET_UNLOCK_LAUNCH_PICKER_TITLE",
                                new Supplier() { // from class:
                                                 // com.android.settings.security.LockUnificationPreferenceController$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        String string2;
                                        string2 =
                                                LockUnificationPreferenceController.this.mContext
                                                        .getString(
                                                                R.string
                                                                        .sec_unlock_set_unlock_launch_picker_title_profile);
                                        return string2;
                                    }
                                });
        SettingsPreferenceFragment settingsPreferenceFragment = this.mHost;
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(
                        settingsPreferenceFragment.getActivity(), settingsPreferenceFragment);
        builder.mRequestCode = 129;
        builder.mTitle = string;
        builder.mReturnCredentials = true;
        builder.mUserId = this.mProfileUserId;
        if (builder.show()) {
            return;
        }
        startUnifyLocks();
    }

    public final void startUnifyLocks() {
        FaceManager faceManager;
        if (!this.mRequireNewDevicePassword && !this.mLockPatternUtils.isSecure(MY_USER_ID)) {
            FingerprintManager fingerprintManager = this.mFingerprintManager;
            int i = this.mProfileUserId;
            if ((fingerprintManager != null && fingerprintManager.hasEnrolledFingerprints(i))
                    || ((faceManager = this.mFaceManager) != null
                            && faceManager.hasEnrolledTemplates(i))) {
                if (BiometricRemoveWarningDialog.mInstance == null) {
                    BiometricRemoveWarningDialog.mInstance = new BiometricRemoveWarningDialog();
                }
                BiometricRemoveWarningDialog biometricRemoveWarningDialog =
                        BiometricRemoveWarningDialog.mInstance;
                biometricRemoveWarningDialog.mUserId = i;
                biometricRemoveWarningDialog.mCallback = new AnonymousClass1();
                biometricRemoveWarningDialog.show(
                        this.mHost.getChildFragmentManager(), "biometric_removal_dialog");
                return;
            }
        }
        unifyLocks();
    }

    public final void unifyLocks() {
        boolean z = this.mRequireNewDevicePassword;
        int i = this.mProfileUserId;
        if (z) {
            Bundle bundle = new Bundle();
            bundle.putInt("unification_profile_id", i);
            bundle.putParcelable("unification_profile_credential", this.mCurrentProfilePassword);
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
            String name = ChooseLockGeneric.ChooseLockGenericFragment.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            subSettingLauncher.setTitleRes(R.string.lock_settings_picker_title, null);
            launchRequest.mSourceMetricsCategory = this.mHost.getMetricsCategory();
            launchRequest.mArguments = bundle;
            launchRequest.mTransitionType = 1;
            subSettingLauncher.launch();
        } else {
            this.mLockPatternUtils.setSeparateProfileChallengeEnabled(
                    i, false, this.mCurrentProfilePassword);
            this.mLockPatternUtils.setBiometricState(1, 0, i);
            this.mLockPatternUtils.setBiometricState(256, 0, i);
        }
        LockscreenCredential lockscreenCredential = this.mCurrentDevicePassword;
        if (lockscreenCredential != null) {
            lockscreenCredential.zeroize();
            this.mCurrentDevicePassword = null;
        }
        LockscreenCredential lockscreenCredential2 = this.mCurrentProfilePassword;
        if (lockscreenCredential2 != null) {
            lockscreenCredential2.zeroize();
            this.mCurrentProfilePassword = null;
        }
    }

    public final void ununifyLocks() {
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.USER_ID", this.mProfileUserId);
        bundle.putParcelable(HostAuth.PASSWORD, this.mCurrentDevicePassword);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ChooseLockGeneric.ChooseLockGenericFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = this.mHost.getMetricsCategory();
        launchRequest.mArguments = bundle;
        launchRequest.mTransitionType = 1;
        subSettingLauncher.launch();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mUnifyProfile != null) {
            LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
            int i = this.mProfileUserId;
            boolean isSeparateProfileChallengeEnabled =
                    lockPatternUtils.isSeparateProfileChallengeEnabled(i);
            this.mUnifyProfile.setChecked(!isSeparateProfileChallengeEnabled);
            if (isSeparateProfileChallengeEnabled) {
                this.mUnifyProfile.setEnabled(true);
                this.mUnifyProfile.setSummary(
                        this.mContext.getString(
                                R.string.lock_settings_profile_unification_summary));
                this.mUnifyProfile.setDisabledByAdmin(
                        RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                this.mContext, i, "no_unified_password"));
                boolean isMultifactorEnabledForWork =
                        KnoxUtils.isMultifactorEnabledForWork(this.mContext, i);
                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                        "Disable one lock : ", "KnoxUtils", isMultifactorEnabledForWork);
                if (isMultifactorEnabledForWork) {
                    this.mUnifyProfile.setEnabled(false);
                    RestrictedSwitchPreference restrictedSwitchPreference = this.mUnifyProfile;
                    Context context = this.mContext;
                    restrictedSwitchPreference.setSummary(
                            context.getString(
                                    R.string.knox_onelock_settings_not_allowed_twostep,
                                    KnoxUtils.getKnoxName(context, i)));
                }
            }
            if (UCMUtils.isUCMKeyguardEnabledAsUser(i)) {
                this.mUnifyProfile.setEnabled(false);
            }
        }
    }
}
