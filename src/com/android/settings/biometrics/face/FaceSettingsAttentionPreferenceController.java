package com.android.settings.biometrics.face;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.face.FaceManager;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceSettingsAttentionPreferenceController extends FaceSettingsPreferenceController {
    public static final String KEY = "security_settings_face_require_attention";
    private FaceManager mFaceManager;
    private final FaceManager.GetFeatureCallback mGetFeatureCallback;
    private TwoStatePreference mPreference;
    private final FaceManager.SetFeatureCallback mSetFeatureCallback;
    private byte[] mToken;

    public FaceSettingsAttentionPreferenceController(Context context, String str) {
        super(context, str);
        this.mSetFeatureCallback =
                new FaceManager
                        .SetFeatureCallback() { // from class:
                                                // com.android.settings.biometrics.face.FaceSettingsAttentionPreferenceController.1
                    public final void onCompleted(boolean z, int i) {
                        if (i == 1) {
                            FaceSettingsAttentionPreferenceController.this.mPreference.setEnabled(
                                    true);
                            if (!z) {
                                FaceSettingsAttentionPreferenceController.this.mPreference
                                        .setChecked(
                                                !FaceSettingsAttentionPreferenceController.this
                                                        .mPreference.isChecked());
                                return;
                            }
                            Settings.Secure.putIntForUser(
                                    ((AbstractPreferenceController)
                                                    FaceSettingsAttentionPreferenceController.this)
                                            .mContext.getContentResolver(),
                                    "face_unlock_attention_required",
                                    FaceSettingsAttentionPreferenceController.this.mPreference
                                                    .isChecked()
                                            ? 1
                                            : 0,
                                    FaceSettingsAttentionPreferenceController.this.getUserId());
                        }
                    }
                };
        this.mGetFeatureCallback =
                new FaceManager
                        .GetFeatureCallback() { // from class:
                                                // com.android.settings.biometrics.face.FaceSettingsAttentionPreferenceController.2
                    public final void onCompleted(boolean z, int[] iArr, boolean[] zArr) {
                        boolean z2 = false;
                        for (int i = 0; i < iArr.length; i++) {
                            if (iArr[i] == 1) {
                                z2 = zArr[i];
                            }
                        }
                        FaceSettingsAttentionPreferenceController.this.mPreference.setChecked(z2);
                        if (FaceSettingsAttentionPreferenceController.this.getRestrictingAdmin()
                                != null) {
                            FaceSettingsAttentionPreferenceController.this.mPreference.setEnabled(
                                    false);
                        } else {
                            FaceSettingsAttentionPreferenceController.this.mPreference.setEnabled(
                                    z);
                        }
                    }
                };
        this.mFaceManager = Utils.getFaceManagerOrNull(context);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (TwoStatePreference) preferenceScreen.findPreference(KEY);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        if (!FaceSettings.isFaceHardwareDetected(this.mContext)) {
            return true;
        }
        this.mPreference.setEnabled(false);
        this.mFaceManager.getFeature(getUserId(), 1, this.mGetFeatureCallback);
        return true;
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
        this.mPreference.setEnabled(false);
        this.mPreference.setChecked(z);
        this.mFaceManager.setFeature(getUserId(), 1, z, this.mToken, this.mSetFeatureCallback);
        return true;
    }

    public void setToken(byte[] bArr) {
        this.mToken = bArr;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference == null) {
            return;
        }
        super.updateState(preference);
        if (Utils.isPrivateProfile(this.mContext, getUserId())) {
            preference.setSummary(
                    this.mContext.getString(
                            R.string.private_space_face_settings_require_attention_details));
        }
    }

    @Override // com.android.settings.biometrics.face.FaceSettingsPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public FaceSettingsAttentionPreferenceController(Context context) {
        this(context, KEY);
    }
}
