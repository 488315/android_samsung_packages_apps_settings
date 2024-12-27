package com.android.settings.privatespace.onelock;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.fingerprint.FingerprintManager;
import android.os.UserHandle;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController;
import com.android.settings.privatespace.PrivateSpaceMaintainer;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateSpaceFingerprintPreferenceController
        extends BiometricFingerprintStatusPreferenceController {
    private static final String TAG = "PrivateSpaceFingerCtrl";

    public PrivateSpaceFingerprintPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (Utils.isMultipleBiometricsSupported(this.mContext)
                && Utils.isFaceNotConvenienceBiometric(this.mContext)) {
            return;
        }
        findPreference.setTitle(R.string.private_space_fingerprint_title);
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (Flags.allowPrivateProfile()
                        && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace()
                        && android.multiuser.Flags.enablePrivateSpaceFeatures())
                ? 0
                : 3;
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public int getUserId() {
        UserHandle privateProfileHandle =
                PrivateSpaceMaintainer.getInstance(this.mContext).getPrivateProfileHandle();
        if (privateProfileHandle != null) {
            return privateProfileHandle.getIdentifier();
        }
        Log.e(TAG, "Private profile user handle is not expected to be null.");
        return -10000;
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public boolean isUserSupported() {
        return Flags.allowPrivateProfile()
                && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace()
                && android.multiuser.Flags.enablePrivateSpaceFeatures()
                && getUserId() != -10000;
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(getUserId())) {
            super.updateState(preference);
            preference.setEnabled(true);
            return;
        }
        Context context = this.mContext;
        int userId = getUserId();
        FingerprintManager fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(context);
        if (fingerprintManagerOrNull != null
                && fingerprintManagerOrNull.hasEnrolledTemplates(userId)) {
            fingerprintManagerOrNull.removeAll(userId, new Utils.AnonymousClass2(userId));
        }
        preference.setSummary(
                this.mContext.getString(R.string.lock_settings_profile_unified_summary));
        preference.setEnabled(false);
    }

    @Override // com.android.settings.biometrics.combination.BiometricFingerprintStatusPreferenceController, com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public PrivateSpaceFingerprintPreferenceController(
            Context context, String str, Lifecycle lifecycle) {
        super(context, str, lifecycle);
    }
}
