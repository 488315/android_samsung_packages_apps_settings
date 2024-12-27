package com.android.settings.security;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.password.ChooseLockGeneric;

import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.knox.UCMUtils;
import com.samsung.android.settings.security.SecurityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ChangeProfileScreenLockPreferenceController
        extends ChangeScreenLockPreferenceController {
    public final String mPreferenceKey;

    public ChangeProfileScreenLockPreferenceController(
            Context context, SettingsPreferenceFragment settingsPreferenceFragment) {
        super(context, settingsPreferenceFragment);
        this.mPreferenceKey = "unlock_set_or_change_profile";
    }

    public final String getBiometricsSummary(boolean z, boolean z2) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = this.mProfileChallengeUserId;
        if (Settings.Secure.getIntForUser(contentResolver, "knox_finger_print_plus", 0, i) == 1) {
            StringBuilder sb = new StringBuilder();
            int keyguardStoredPasswordQuality =
                    this.mLockPatternUtils.getKeyguardStoredPasswordQuality(i);
            if (keyguardStoredPasswordQuality == 65536) {
                Context context = this.mContext;
                sb.append(
                        context.getString(
                                R.string.text_two_locktype_multifactor,
                                context.getString(R.string.sec_unlock_set_unlock_pattern_title)));
            } else if (keyguardStoredPasswordQuality == 131072
                    || keyguardStoredPasswordQuality == 196608) {
                Context context2 = this.mContext;
                sb.append(
                        context2.getString(
                                R.string.text_two_locktype_multifactor,
                                context2.getString(R.string.sec_unlock_set_unlock_pin_title)));
            } else if (keyguardStoredPasswordQuality == 262144
                    || keyguardStoredPasswordQuality == 327680
                    || keyguardStoredPasswordQuality == 393216) {
                Context context3 = this.mContext;
                sb.append(
                        context3.getString(
                                R.string.text_two_locktype_multifactor,
                                context3.getString(R.string.sec_unlock_set_unlock_password_title)));
            }
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        String language = this.mContext.getResources().getConfiguration().locale.getLanguage();
        String str =
                "ar".equals(language)
                        ? "، "
                        : "ja".equals(language) ? "、" : "zh".equals(language) ? "，" : ", ";
        int keyguardStoredPasswordQuality2 =
                this.mLockPatternUtils.getKeyguardStoredPasswordQuality(i);
        if (keyguardStoredPasswordQuality2 == 65536) {
            sb2.append(this.mContext.getString(R.string.sec_unlock_set_unlock_pattern_title));
        } else if (keyguardStoredPasswordQuality2 == 131072
                || keyguardStoredPasswordQuality2 == 196608) {
            sb2.append(this.mContext.getString(R.string.sec_unlock_set_unlock_pin_title));
        } else if (keyguardStoredPasswordQuality2 == 262144
                || keyguardStoredPasswordQuality2 == 327680
                || keyguardStoredPasswordQuality2 == 393216) {
            sb2.append(this.mContext.getString(R.string.sec_unlock_set_unlock_password_title));
        } else if (keyguardStoredPasswordQuality2 == 458752) {
            return SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                    .m(this.mContext, R.string.unlock_set_unlock_cac_pin_title, sb2);
        }
        if (z2) {
            sb2.append(str);
            sb2.append(this.mContext.getString(R.string.sec_face_title));
        }
        if (z) {
            sb2.append(str);
            sb2.append(this.mContext.getString(R.string.sec_fingerprint));
        }
        return sb2.toString();
    }

    @Override // com.android.settings.security.ChangeScreenLockPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.mPreferenceKey;
    }

    @Override // com.android.settings.security.ChangeScreenLockPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), this.mPreferenceKey)) {
            return false;
        }
        Context context = this.mContext;
        UserManager userManager = this.mUm;
        int i = this.mProfileChallengeUserId;
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
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.USER_ID", i);
        bundle.putBoolean("fromScreenLock", true);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ChooseLockGeneric.ChooseLockGenericFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = settingsPreferenceFragment.getMetricsCategory();
        launchRequest.mArguments = bundle;
        launchRequest.mTransitionType = 1;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.security.ChangeScreenLockPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        int keyguardStoredPasswordQuality;
        int i = this.mProfileChallengeUserId;
        if (i == -10000 || !this.mUm.isManagedProfile(i)) {
            return false;
        }
        return !this.mLockPatternUtils.isSecure(i)
                || (keyguardStoredPasswordQuality =
                                this.mLockPatternUtils.getKeyguardStoredPasswordQuality(i))
                        == 65536
                || keyguardStoredPasswordQuality == 131072
                || keyguardStoredPasswordQuality == 196608
                || keyguardStoredPasswordQuality == 262144
                || keyguardStoredPasswordQuality == 327680
                || keyguardStoredPasswordQuality == 393216
                || keyguardStoredPasswordQuality == 458752
                || keyguardStoredPasswordQuality == 524288;
    }

    @Override // com.android.settings.security.ChangeScreenLockPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        String biometricsSummary;
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        int i = this.mProfileChallengeUserId;
        if (lockPatternUtils.isSecure(i)) {
            boolean z = this.mLockPatternUtils.getBiometricState(1, i) == 1;
            boolean z2 = this.mLockPatternUtils.getBiometricState(256, i) == 1;
            if (SecurityUtils.isFingerprintDisabledByDPM(this.mContext, i)) {
                z = false;
            }
            if (SecurityUtils.isFaceDisabledByDPM(this.mContext, i)) {
                z2 = false;
            }
            biometricsSummary = getBiometricsSummary(z, z2);
        } else {
            biometricsSummary = this.mContext.getString(R.string.sec_unlock_set_unlock_off_title);
        }
        preference.setSummary(biometricsSummary);
        this.mPreference.setEnabled(true);
        preference.seslSetSummaryColor(
                this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)
                        ? this.mContext.getResources().getColor(R.color.basic_primary_color)
                        : this.mContext
                                .getResources()
                                .getColor(R.color.basic_secondary_text_color_dimmed));
        if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
            disableIfPasswordQualityManaged(i);
        } else {
            this.mPreference.setSummary(
                    this.mContext.getString(R.string.knox_onelock_settings_lock_summary));
            this.mPreference.setEnabled(false);
        }
        if (UCMUtils.isUCMKeyguardEnabledAsUser(i)) {
            preference.setSummary(getBiometricsSummary(false, false));
            if (!UCMUtils.isEnforcedCredentialStorageExistAsUser(i)
                    || UCMUtils.isSupportChangePin(i)) {
                return;
            }
            preference.seslSetSummaryColor(
                    this.mContext
                            .getResources()
                            .getColor(R.color.basic_secondary_text_color_dimmed));
            this.mPreference.setEnabled(false);
        }
    }
}
