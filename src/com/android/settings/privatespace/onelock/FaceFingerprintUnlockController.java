package com.android.settings.privatespace.onelock;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.UserHandle;
import android.util.Log;

import androidx.preference.Preference;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.Utils;
import com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController;
import com.android.settings.privatespace.PrivateSpaceMaintainer;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceFingerprintUnlockController extends CombinedBiometricStatusPreferenceController {
    private static final String KEY_SET_UNSET_FACE_FINGERPRINT = "private_space_biometrics";
    private static final String TAG = "PSBiometricCtrl";
    private final int mProfileUserId;

    public FaceFingerprintUnlockController(Context context, Lifecycle lifecycle) {
        super(context, KEY_SET_UNSET_FACE_FINGERPRINT, lifecycle);
        this.mProfileUserId = getUserId();
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_SET_UNSET_FACE_FINGERPRINT;
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController
    public String getSettingsClassName() {
        this.mCombinedBiometricStatusUtils.getClass();
        return Settings.PrivateSpaceBiometricSettingsActivity.class.getName();
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
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

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public boolean isUserSupported() {
        return Flags.allowPrivateProfile()
                && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace()
                && android.multiuser.Flags.enablePrivateSpaceFeatures()
                && this.mProfileUserId != -10000;
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(this.mProfileUserId)) {
            super.updateState(preference);
            preference.setEnabled(true);
            return;
        }
        Context context = this.mContext;
        int userId = getUserId();
        FaceManager faceManagerOrNull = Utils.getFaceManagerOrNull(context);
        if (faceManagerOrNull != null && faceManagerOrNull.hasEnrolledTemplates(userId)) {
            faceManagerOrNull.removeAll(userId, new Utils.AnonymousClass1(userId));
        }
        Context context2 = this.mContext;
        int userId2 = getUserId();
        FingerprintManager fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(context2);
        if (fingerprintManagerOrNull != null
                && fingerprintManagerOrNull.hasEnrolledTemplates(userId2)) {
            fingerprintManagerOrNull.removeAll(userId2, new Utils.AnonymousClass2(userId2));
        }
        preference.setSummary(
                this.mContext.getString(R.string.lock_settings_profile_unified_summary));
        preference.setEnabled(false);
    }

    @Override // com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController, com.android.settings.biometrics.BiometricStatusPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
