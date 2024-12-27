package com.android.settings.privatespace.onelock;

import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.privatespace.PrivateSpaceMaintainer;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PrivateSpaceLockController extends AbstractPreferenceController {
    public final SettingsPreferenceFragment mHost;
    public final LockPatternUtils mLockPatternUtils;
    public final int mProfileUserId;
    public final UserManager mUserManager;

    public PrivateSpaceLockController(
            Context context, SettingsPreferenceFragment settingsPreferenceFragment) {
        super(context);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mLockPatternUtils =
                featureFactoryImpl.getSecurityFeatureProvider().getLockPatternUtils(context);
        this.mHost = settingsPreferenceFragment;
        UserHandle privateProfileHandle =
                PrivateSpaceMaintainer.getInstance(context).getPrivateProfileHandle();
        if (privateProfileHandle != null) {
            this.mProfileUserId = privateProfileHandle.getIdentifier();
        } else {
            this.mProfileUserId = -1;
            Log.e(
                    "PrivateSpaceLockContr",
                    "Private profile user handle is not expected to be null.");
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "change_private_space_lock";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), "change_private_space_lock")) {
            return false;
        }
        Context context = this.mContext;
        UserManager userManager = this.mUserManager;
        int i = this.mProfileUserId;
        if (Utils.startQuietModeDialogIfNecessary(context, userManager, i)) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.USER_ID", i);
        bundle.putBoolean("hide_insecure_options", true);
        bundle.putInt("choose_lock_setup_screen_title", R.string.private_space_lock_setup_title);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ChooseLockGeneric.ChooseLockGenericFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = this.mHost.getMetricsCategory();
        launchRequest.mArguments = bundle;
        launchRequest.mExtras = bundle;
        launchRequest.mTransitionType = 1;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        int i = this.mProfileUserId;
        if (!lockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
            preference.setSummary(
                    this.mContext.getString(R.string.lock_settings_profile_unified_summary));
            preference.setEnabled(false);
        } else {
            Context context = this.mContext;
            int credentialTypeForUser = this.mLockPatternUtils.getCredentialTypeForUser(i);
            preference.setSummary(
                    context.getString(
                            credentialTypeForUser != 1
                                    ? credentialTypeForUser != 3
                                            ? credentialTypeForUser != 4
                                                    ? R.string.unlock_set_unlock_mode_off
                                                    : R.string.unlock_set_unlock_mode_password
                                            : R.string.unlock_set_unlock_mode_pin
                                    : R.string.unlock_set_unlock_mode_pattern));
            preference.setEnabled(true);
        }
    }
}
