package com.android.settings.biometrics.face;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.face.FaceManager;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.Utils;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceSettingsAppPreferenceController extends FaceSettingsPreferenceController {
    private static final int DEFAULT = 1;
    static final String KEY = "security_settings_face_app";
    private static final int OFF = 0;
    private static final int ON = 1;
    private FaceManager mFaceManager;

    public FaceSettingsAppPreferenceController(Context context, String str) {
        super(context, str);
        this.mFaceManager = Utils.getFaceManagerOrNull(context);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Utils.isMultipleBiometricsSupported(this.mContext)) {
            return 3;
        }
        FaceManager faceManager = this.mFaceManager;
        if (faceManager == null) {
            return 1;
        }
        return (!faceManager.hasEnrolledTemplates(getUserId())
                        || (Settings.Secure.getIntForUser(
                                        this.mContext.getContentResolver(),
                                        "face_unlock_app_enabled",
                                        0,
                                        getUserId())
                                == 1))
                ? 1
                : 0;
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
        return FaceSettings.isFaceHardwareDetected(this.mContext)
                && Settings.Secure.getIntForUser(
                                this.mContext.getContentResolver(),
                                "face_unlock_app_enabled",
                                1,
                                getUserId())
                        == 1;
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
        return Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(),
                "face_unlock_app_enabled",
                z ? 1 : 0,
                getUserId());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (!FaceSettings.isFaceHardwareDetected(this.mContext)) {
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

    public FaceSettingsAppPreferenceController(Context context) {
        this(context, KEY);
    }
}
