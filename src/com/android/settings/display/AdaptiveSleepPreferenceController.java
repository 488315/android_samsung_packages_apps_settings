package com.android.settings.display;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.SensorPrivacyManager;
import android.os.PowerManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.bluetooth.RestrictionUtils;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdaptiveSleepPreferenceController {
    public final Context mContext;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public final PackageManager mPackageManager;
    public final PowerManager mPowerManager;
    RestrictedSwitchPreference mPreference;
    public final SensorPrivacyManager mPrivacyManager;
    public final RestrictionUtils mRestrictionUtils;

    public AdaptiveSleepPreferenceController(Context context) {
        RestrictionUtils restrictionUtils = new RestrictionUtils();
        this.mContext = context;
        this.mRestrictionUtils = restrictionUtils;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mPrivacyManager = SensorPrivacyManager.getInstance(context);
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mPackageManager = context.getPackageManager();
    }

    public static boolean hasSufficientPermission(PackageManager packageManager) {
        String attentionServicePackageName = packageManager.getAttentionServicePackageName();
        return attentionServicePackageName != null
                && packageManager.checkPermission(
                                "android.permission.CAMERA", attentionServicePackageName)
                        == 0;
    }

    public static boolean isAdaptiveSleepSupported(Context context) {
        ResolveInfo resolveService;
        if (context.getResources().getBoolean(R.bool.config_allowAllRotations)) {
            PackageManager packageManager = context.getPackageManager();
            String attentionServicePackageName = packageManager.getAttentionServicePackageName();
            if (!TextUtils.isEmpty(attentionServicePackageName)
                    && (resolveService =
                                    packageManager.resolveService(
                                            new Intent("android.service.attention.AttentionService")
                                                    .setPackage(attentionServicePackageName),
                                            1048576))
                            != null
                    && resolveService.serviceInfo != null) {
                return true;
            }
        }
        return false;
    }

    public void initializePreference() {
        if (this.mPreference == null) {
            RestrictedSwitchPreference restrictedSwitchPreference =
                    new RestrictedSwitchPreference(this.mContext);
            this.mPreference = restrictedSwitchPreference;
            restrictedSwitchPreference.setTitle(com.android.settings.R.string.adaptive_sleep_title);
            this.mPreference.setSummary(com.android.settings.R.string.adaptive_sleep_description);
            this.mPreference.setChecked(isChecked());
            this.mPreference.setKey("adaptive_sleep");
            this.mPreference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.display.AdaptiveSleepPreferenceController$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            AdaptiveSleepPreferenceController adaptiveSleepPreferenceController =
                                    AdaptiveSleepPreferenceController.this;
                            adaptiveSleepPreferenceController.getClass();
                            boolean z = ((RestrictedSwitchPreference) preference).mChecked;
                            adaptiveSleepPreferenceController.mMetricsFeatureProvider.action(
                                    adaptiveSleepPreferenceController.mContext, 1755, z);
                            Settings.Secure.putInt(
                                    adaptiveSleepPreferenceController.mContext.getContentResolver(),
                                    "adaptive_sleep",
                                    z ? 1 : 0);
                            return true;
                        }
                    });
        }
    }

    public boolean isCameraLocked() {
        return this.mPrivacyManager.isSensorPrivacyEnabled(2);
    }

    public boolean isChecked() {
        return (!hasSufficientPermission(this.mContext.getPackageManager())
                        || isCameraLocked()
                        || isPowerSaveMode()
                        || Settings.Secure.getInt(
                                        this.mContext.getContentResolver(), "adaptive_sleep", 0)
                                == 0)
                ? false
                : true;
    }

    public boolean isPowerSaveMode() {
        return this.mPowerManager.isPowerSaveMode();
    }

    public final void updatePreference() {
        initializePreference();
        Context context = this.mContext;
        this.mRestrictionUtils.getClass();
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        context, UserHandle.myUserId(), "no_config_screen_timeout");
        if (checkIfRestrictionEnforced != null) {
            this.mPreference.setDisabledByAdmin(checkIfRestrictionEnforced);
        } else {
            this.mPreference.setChecked(isChecked());
            this.mPreference.setEnabled(
                    (!hasSufficientPermission(this.mPackageManager)
                                    || isCameraLocked()
                                    || isPowerSaveMode())
                            ? false
                            : true);
        }
    }
}
