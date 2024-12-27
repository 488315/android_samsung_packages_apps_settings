package com.android.settings.biometrics.combination;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.lifecycle.Lifecycle;
import androidx.preference.Preference;

import com.android.settings.Utils;
import com.android.settings.biometrics.face.FaceStatusPreferenceController;
import com.android.settings.biometrics.face.FaceStatusUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BiometricFaceStatusPreferenceController extends FaceStatusPreferenceController {
    public BiometricFaceStatusPreferenceController(Context context, String str) {
        super(context, str, null);
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController
    public boolean isDeviceSupported() {
        return Utils.isMultipleBiometricsSupported(this.mContext) && isHardwareSupported();
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController
    public boolean isHardwareSupported() {
        return Utils.hasFaceHardware(this.mContext);
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        FaceStatusUtils faceStatusUtils = this.mFaceStatusUtils;
        boolean hasEnrolledTemplates =
                faceStatusUtils.mFaceManager.hasEnrolledTemplates(faceStatusUtils.mUserId);
        RestrictedLockUtils.EnforcedAdmin checkIfKeyguardFeaturesDisabled =
                RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                        this.mContext, 128, getUserId());
        if (checkIfKeyguardFeaturesDisabled == null || hasEnrolledTemplates) {
            preference.setEnabled(true);
        } else {
            ((RestrictedPreference) preference).setDisabledByAdmin(checkIfKeyguardFeaturesDisabled);
        }
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public BiometricFaceStatusPreferenceController(
            Context context, String str, Lifecycle lifecycle) {
        super(context, str, lifecycle);
    }
}
