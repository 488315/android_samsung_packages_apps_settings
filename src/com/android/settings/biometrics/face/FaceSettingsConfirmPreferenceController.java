package com.android.settings.biometrics.face;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorProperties;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceSettingsConfirmPreferenceController extends FaceSettingsPreferenceController {
    private static final int DEFAULT = 0;
    static final String KEY = "security_settings_face_require_confirmation";
    private static final int OFF = 0;
    private static final int ON = 1;
    private FaceManager mFaceManager;

    public FaceSettingsConfirmPreferenceController(Context context) {
        this(context, KEY);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        List sensorProperties = this.mFaceManager.getSensorProperties();
        return (sensorProperties.isEmpty()
                        || ((FaceSensorProperties) sensorProperties.get(0)).getSensorStrength()
                                != 0)
                ? 0
                : 2;
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
        return Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        "face_unlock_always_require_confirmation",
                        0,
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
                "face_unlock_always_require_confirmation",
                z ? 1 : 0,
                getUserId());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (!FaceSettings.isFaceHardwareDetected(this.mContext)) {
            preference.setEnabled(false);
            return;
        }
        if (!this.mFaceManager.hasEnrolledTemplates(getUserId())) {
            preference.setEnabled(false);
            return;
        }
        if (getRestrictingAdmin() != null) {
            preference.setEnabled(false);
            return;
        }
        preference.setEnabled(true);
        if (Utils.isPrivateProfile(this.mContext, getUserId())) {
            preference.setSummary(
                    this.mContext.getString(
                            R.string.private_space_face_settings_require_confirmation_details));
        }
    }

    @Override // com.android.settings.biometrics.face.FaceSettingsPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public FaceSettingsConfirmPreferenceController(Context context, String str) {
        super(context, str);
        this.mFaceManager = Utils.getFaceManagerOrNull(context);
    }
}
