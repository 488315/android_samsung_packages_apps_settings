package com.android.settings.biometrics.face;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.lifecycle.Lifecycle;
import androidx.preference.Preference;

import com.android.settings.R;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.UCMUtils;

import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceProfileStatusPreferenceController extends FaceStatusPreferenceController {
    private static final String KEY_FACE_SETTINGS = "face_settings_profile";
    private final DevicePolicyManager mDevicePolicyManager;

    public FaceProfileStatusPreferenceController(Context context) {
        super(context, KEY_FACE_SETTINGS);
        this.mDevicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$updateState$0() {
        return this.mContext
                .getResources()
                .getString(R.string.security_settings_face_profile_preference_title);
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int availabilityStatus = super.getAvailabilityStatus();
        if (availabilityStatus != 0) {
            return availabilityStatus;
        }
        return 1;
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

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public int getUserId() {
        return this.mProfileChallengeUserId;
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

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public boolean isUserSupported() {
        int i = this.mProfileChallengeUserId;
        return i != -10000
                && this.mUm.isManagedProfile(i)
                && (!UCMUtils.isUCMKeyguardEnabledAsUser(this.mProfileChallengeUserId)
                        || UCMUtils.isSupportBiometricForUCM(this.mProfileChallengeUserId));
    }

    @Override // com.android.settings.biometrics.BiometricStatusPreferenceController
    public boolean isWorkProfileController() {
        return true;
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
        preference.setTitle(
                this.mDevicePolicyManager
                        .getResources()
                        .getString(
                                "Settings.FACE_SETTINGS_FOR_WORK_TITLE",
                                new Supplier() { // from class:
                                                 // com.android.settings.biometrics.face.FaceProfileStatusPreferenceController$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        String lambda$updateState$0;
                                        lambda$updateState$0 =
                                                FaceProfileStatusPreferenceController.this
                                                        .lambda$updateState$0();
                                        return lambda$updateState$0;
                                    }
                                }));
    }

    @Override // com.android.settings.biometrics.face.FaceStatusPreferenceController,
              // com.android.settings.biometrics.BiometricStatusPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public FaceProfileStatusPreferenceController(Context context, String str) {
        super(context, str);
        this.mDevicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
    }

    public FaceProfileStatusPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, KEY_FACE_SETTINGS, lifecycle);
        this.mDevicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
    }

    public FaceProfileStatusPreferenceController(Context context, String str, Lifecycle lifecycle) {
        super(context, str, lifecycle);
        this.mDevicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
    }
}
