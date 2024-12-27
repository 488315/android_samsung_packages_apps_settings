package com.android.settings.security;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.security.screenlock.ScreenLockSettings;
import com.android.settings.widget.GearPreference;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChangeScreenLockPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, GearPreference.OnGearClickListener {
    public final SettingsPreferenceFragment mHost;
    public final LockPatternUtils mLockPatternUtils;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public RestrictedPreference mPreference;
    public final int mProfileChallengeUserId;
    public final ScreenLockPreferenceDetailsUtils mScreenLockPreferenceDetailUtils;
    public final UserManager mUm;
    public final int mUserId;

    public ChangeScreenLockPreferenceController(
            Context context, SettingsPreferenceFragment settingsPreferenceFragment) {
        super(context);
        int myUserId = UserHandle.myUserId();
        this.mUserId = myUserId;
        UserManager userManager = (UserManager) context.getSystemService("user");
        this.mUm = userManager;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mLockPatternUtils =
                featureFactoryImpl.getSecurityFeatureProvider().getLockPatternUtils(context);
        this.mHost = settingsPreferenceFragment;
        this.mProfileChallengeUserId = Utils.getManagedProfileId(userManager, myUserId);
        FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
        if (featureFactoryImpl2 == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl2.getMetricsFeatureProvider();
        this.mScreenLockPreferenceDetailUtils = new ScreenLockPreferenceDetailsUtils(context);
    }

    public final void disableIfPasswordQualityManaged(int i) {
        RestrictedLockUtils.EnforcedAdmin checkIfPasswordQualityIsSet =
                RestrictedLockUtilsInternal.checkIfPasswordQualityIsSet(this.mContext, i);
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager)
                        this.mScreenLockPreferenceDetailUtils.mContext.getSystemService(
                                "device_policy");
        if (checkIfPasswordQualityIsSet == null
                || devicePolicyManager.getPasswordQuality(checkIfPasswordQualityIsSet.component, i)
                        != 524288) {
            return;
        }
        this.mPreference.setDisabledByAdmin(checkIfPasswordQualityIsSet);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (RestrictedPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return "unlock_set_or_change";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        int metricsCategory = this.mHost.getMetricsCategory();
        ScreenLockPreferenceDetailsUtils screenLockPreferenceDetailsUtils =
                this.mScreenLockPreferenceDetailUtils;
        Intent quietModeDialogIntent = screenLockPreferenceDetailsUtils.getQuietModeDialogIntent();
        if (quietModeDialogIntent != null) {
            screenLockPreferenceDetailsUtils.mContext.startActivity(quietModeDialogIntent);
            return false;
        }
        Context context = screenLockPreferenceDetailsUtils.mContext;
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String name = ChooseLockGeneric.ChooseLockGenericFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = metricsCategory;
        launchRequest.mTransitionType = 1;
        context.startActivity(subSettingLauncher.toIntent());
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean isAvailable() {
        return this.mScreenLockPreferenceDetailUtils
                .mContext
                .getResources()
                .getBoolean(R.bool.config_show_unlock_set_or_change);
    }

    @Override // com.android.settings.widget.GearPreference.OnGearClickListener
    public final void onGearClick(GearPreference gearPreference) {
        if (TextUtils.equals(gearPreference.getKey(), getPreferenceKey())) {
            this.mMetricsFeatureProvider.logClickedPreference(
                    gearPreference, gearPreference.getExtras().getInt("category"));
            int metricsCategory = this.mHost.getMetricsCategory();
            Context context = this.mScreenLockPreferenceDetailUtils.mContext;
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
            String name = ScreenLockSettings.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            launchRequest.mSourceMetricsCategory = metricsCategory;
            context.startActivity(subSettingLauncher.toIntent());
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        RestrictedPreference restrictedPreference = this.mPreference;
        ScreenLockPreferenceDetailsUtils screenLockPreferenceDetailsUtils =
                this.mScreenLockPreferenceDetailUtils;
        if (restrictedPreference != null && (restrictedPreference instanceof GearPreference)) {
            if (screenLockPreferenceDetailsUtils.mLockPatternUtils.isSecure(
                    screenLockPreferenceDetailsUtils.mUserId)) {
                GearPreference gearPreference = (GearPreference) this.mPreference;
                gearPreference.mOnGearClickListener = this;
                gearPreference.notifyChanged();
            } else {
                GearPreference gearPreference2 = (GearPreference) this.mPreference;
                gearPreference2.mOnGearClickListener = null;
                gearPreference2.notifyChanged();
            }
        }
        int i = this.mUserId;
        preference.setSummary(screenLockPreferenceDetailsUtils.getSummary(i));
        this.mPreference.setEnabled(true);
        disableIfPasswordQualityManaged(i);
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        int i2 = this.mProfileChallengeUserId;
        if (lockPatternUtils.isSeparateProfileChallengeEnabled(i2)) {
            return;
        }
        disableIfPasswordQualityManaged(i2);
    }
}
