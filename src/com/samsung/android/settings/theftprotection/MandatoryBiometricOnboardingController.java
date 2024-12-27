package com.samsung.android.settings.theftprotection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MandatoryBiometricOnboardingController extends BasePreferenceController {
    private Context mContext;
    private SecPreferenceScreen mPreference;

    public MandatoryBiometricOnboardingController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    private boolean isPreferenceVisible() {
        int mandatoryBiometricStatus =
                TheftProtectionUtils.getMandatoryBiometricStatus(this.mContext);
        if (mandatoryBiometricStatus == -1) {
            return true;
        }
        return mandatoryBiometricStatus == 1
                && (TheftProtectionUtils.hasBiometricStrong(this.mContext) ^ true);
    }

    private void updateSummary() {
        if (TheftProtectionUtils.getMandatoryBiometricStatus(this.mContext) != 1
                || TheftProtectionUtils.hasBiometricStrong(this.mContext)) {
            this.mPreference.seslSetSummaryColor(
                    this.mContext.getColor(R.color.security_dashboard_menu_subtext_default_color));
            this.mPreference.setSummary(R.string.theft_protection_mandatory_biometric_summary);
        } else {
            this.mPreference.seslSetSummaryColor(
                    this.mContext.getColor(
                            R.color.security_dashboard_menu_subtext_suggestion_color));
            this.mPreference.setSummary(R.string.mandatory_biometric_fingerprint_removed_summary);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Context context = this.mContext;
        return (1 == BiometricsGenericHelper.getSecurityLevel(context, 1)
                        || 1 == BiometricsGenericHelper.getSecurityLevel(context, 256))
                ? 0
                : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (TheftProtectionUtils.getMandatoryBiometricStatus(this.mContext) != 1
                || TheftProtectionUtils.hasBiometricStrong(this.mContext)) {
            return super.handlePreferenceTreeClick(preference);
        }
        TheftProtectionUtils.startFingerprintLockSettings(this.mContext);
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        if (isPreferenceVisible()) {
            return;
        }
        list.add(getPreferenceKey());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mPreference.setVisible(isPreferenceVisible());
        updateSummary();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
