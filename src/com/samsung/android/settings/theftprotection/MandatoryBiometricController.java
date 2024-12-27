package com.samsung.android.settings.theftprotection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.PreferenceManager;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.theftprotection.logging.Log;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MandatoryBiometricController extends TogglePreferenceController
        implements PreferenceManager.OnActivityResultListener {
    private static final int REQUEST_PROTECTION_PROMPT = 1001;
    private static final String TAG = "MandatoryBiometricController";
    private Context mContext;
    private Fragment mParentFragment;
    private SecSwitchPreferenceScreen mPreference;

    public MandatoryBiometricController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    private boolean isPreferenceVisible() {
        int mandatoryBiometricStatus =
                TheftProtectionUtils.getMandatoryBiometricStatus(this.mContext);
        if (mandatoryBiometricStatus == -1) {
            return false;
        }
        return (mandatoryBiometricStatus == 1
                        && (TheftProtectionUtils.hasBiometricStrong(this.mContext) ^ true))
                ? false
                : true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Context context = this.mContext;
        return (1 == BiometricsGenericHelper.getSecurityLevel(context, 1)
                        || 1 == BiometricsGenericHelper.getSecurityLevel(context, 256))
                ? 0
                : 3;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public void init(Fragment fragment) {
        this.mParentFragment = fragment;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return TheftProtectionUtils.getMandatoryBiometricStatus(this.mContext) == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // android.preference.PreferenceManager.OnActivityResultListener
    public boolean onActivityResult(int i, int i2, Intent intent) {
        Log.d(TAG, "onActivityResult requestCode : " + i + " resultCode : " + i2);
        if (i == 1001 && i2 == -1) {
            TheftProtectionUtils.setMandatoryBiometricStatus(this.mContext, 0);
            this.mPreference.setChecked(false);
        }
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Log.d(TAG, "setChecked = " + z);
        if (!z) {
            TheftProtectionUtils.showProtectionPromptWithAccount(this.mParentFragment);
            return false;
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = MandatoryBiometricOnboardingFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = getMetricsCategory();
        subSettingLauncher.setTitleRes(R.string.theft_protection_mandatory_biometric_title, null);
        subSettingLauncher.launch();
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (isPreferenceVisible()) {
            return;
        }
        list.add(getPreferenceKey());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mPreference.setVisible(isPreferenceVisible());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
