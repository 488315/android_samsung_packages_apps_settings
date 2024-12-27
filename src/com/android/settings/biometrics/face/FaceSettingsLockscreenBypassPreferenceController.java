package com.android.settings.biometrics.face;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.face.FaceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.Utils;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceSettingsLockscreenBypassPreferenceController
        extends FaceSettingsPreferenceController {

    @VisibleForTesting protected FaceManager mFaceManager;
    private UserManager mUserManager;

    public FaceSettingsLockscreenBypassPreferenceController(Context context, String str) {
        super(context, str);
        if (context.getPackageManager().hasSystemFeature("android.hardware.biometrics.face")) {
            this.mFaceManager = (FaceManager) context.getSystemService(FaceManager.class);
        }
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    private int getUserHandle() {
        return UserHandle.of(getUserId()).getIdentifier();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        FaceManager faceManager;
        if (Utils.isMultipleBiometricsSupported(this.mContext)
                || this.mUserManager.isManagedProfile(getUserId())
                || (faceManager = this.mFaceManager) == null
                || !faceManager.isHardwareDetected()) {
            return 3;
        }
        return this.mFaceManager.hasEnrolledTemplates(getUserId()) ? 0 : 5;
    }

    @Override // com.android.settings.biometrics.face.FaceSettingsPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.biometrics.face.FaceSettingsPreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.biometrics.face.FaceSettingsPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.biometrics.face.FaceSettingsPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.biometrics.face.FaceSettingsPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.biometrics.face.FaceSettingsPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.biometrics.face.FaceSettingsPreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        if (!FaceSettings.isFaceHardwareDetected(this.mContext) || getRestrictingAdmin() != null) {
            return false;
        }
        return Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        "face_unlock_dismisses_keyguard",
                        this.mContext
                                        .getResources()
                                        .getBoolean(
                                                R.bool.config_guestUserAllowEphemeralStateChange)
                                ? 1
                                : 0,
                        getUserHandle())
                != 0;
    }

    @Override // com.android.settings.biometrics.face.FaceSettingsPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.biometrics.face.FaceSettingsPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.biometrics.face.FaceSettingsPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(),
                "face_unlock_dismisses_keyguard",
                z ? 1 : 0,
                getUserHandle());
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (!FaceSettings.isFaceHardwareDetected(this.mContext)) {
            preference.setEnabled(false);
            return;
        }
        if (getRestrictingAdmin() != null) {
            preference.setEnabled(false);
        } else if (this.mFaceManager.hasEnrolledTemplates(getUserId())) {
            preference.setEnabled(true);
        } else {
            preference.setEnabled(false);
        }
    }

    @Override // com.android.settings.biometrics.face.FaceSettingsPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
