package com.android.settings.biometrics.combination;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.Settings;
import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricStatusPreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CombinedBiometricStatusPreferenceController extends BiometricStatusPreferenceController
        implements LifecycleObserver {
    public static final String KEY_BIOMETRIC_SETTINGS = "biometric_settings";
    protected final CombinedBiometricStatusUtils mCombinedBiometricStatusUtils;

    @VisibleForTesting RestrictedPreference mPreference;
    private PreferenceScreen mPreferenceScreen;

    public CombinedBiometricStatusPreferenceController(Context context) {
        this(context, KEY_BIOMETRIC_SETTINGS, null);
    }

    private void updateStateInternal() {
        updateStateInternal(this.mCombinedBiometricStatusUtils.getDisablingAdmin());
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceScreen = preferenceScreen;
        this.mPreference =
                (RestrictedPreference) preferenceScreen.findPreference(this.mPreferenceKey);
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public String getSettingsClassName() {
        this.mCombinedBiometricStatusUtils.getClass();
        return Settings.CombinedBiometricSettingsActivity.class.getName();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public String getSummaryText() {
        return this.mCombinedBiometricStatusUtils.getSummary();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public boolean isDeviceSupported() {
        CombinedBiometricStatusUtils combinedBiometricStatusUtils =
                this.mCombinedBiometricStatusUtils;
        return Utils.hasFingerprintHardware(combinedBiometricStatusUtils.mContext)
                && Utils.hasFaceHardware(combinedBiometricStatusUtils.mContext);
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public boolean isHardwareSupported() {
        return Utils.hasFaceHardware(this.mContext) || Utils.hasFingerprintHardware(this.mContext);
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        updateStateInternal();
        PreferenceScreen preferenceScreen = this.mPreferenceScreen;
        if (preferenceScreen != null) {
            displayPreference(preferenceScreen);
        }
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        this.mPreferenceScreen = preferenceScreen;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateStateInternal();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public CombinedBiometricStatusPreferenceController(Context context, String str) {
        this(context, str, null);
    }

    public CombinedBiometricStatusPreferenceController(Context context, Lifecycle lifecycle) {
        this(context, KEY_BIOMETRIC_SETTINGS, lifecycle);
    }

    public CombinedBiometricStatusPreferenceController(
            Context context, String str, Lifecycle lifecycle) {
        super(context, str);
        this.mCombinedBiometricStatusUtils = new CombinedBiometricStatusUtils(context, getUserId());
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @VisibleForTesting
    public void updateStateInternal(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        RestrictedPreference restrictedPreference = this.mPreference;
        if (restrictedPreference != null) {
            restrictedPreference.setDisabledByAdmin(enforcedAdmin);
        }
    }
}
