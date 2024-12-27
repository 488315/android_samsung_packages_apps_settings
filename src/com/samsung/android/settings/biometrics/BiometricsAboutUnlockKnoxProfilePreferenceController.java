package com.samsung.android.settings.biometrics;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.biometrics.face.FaceProfileStatusPreferenceController;
import com.android.settings.biometrics.fingerprint.FingerprintProfileStatusPreferenceController;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.security.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricsAboutUnlockKnoxProfilePreferenceController extends BasePreferenceController {
    public static final String KEY_ABOUT_KNOX_BIOMETRICS = "key_about_unlock_knox_biometrics";
    private final FaceProfileStatusPreferenceController mFaceSettingsPreferenceController;
    private final FingerprintProfileStatusPreferenceController
            mFingerprintSettingsPreferenceController;

    public BiometricsAboutUnlockKnoxProfilePreferenceController(Context context) {
        this(context, KEY_ABOUT_KNOX_BIOMETRICS);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        SecurityUtils.hasFingerprintFeature(this.mContext);
        Log.d("BiometricsGenericHelper", "isSupportAboutBiometricsUnlockMenu : true");
        return (this.mFaceSettingsPreferenceController.isAvailable()
                        || this.mFingerprintSettingsPreferenceController.isAvailable())
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
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        LoggingHelper.insertFlowLogging(8521);
        launchBiometricsSecurityNotice();
        return super.handlePreferenceTreeClick(preference);
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

    public void launchBiometricsSecurityNotice() {
        Intent intent = new Intent();
        intent.setClassName(
                this.mContext.getPackageName(),
                "com.samsung.android.settings.biometrics.BiometricsCommonSecurityNoticeActivity");
        this.mContext.startActivity(intent);
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public BiometricsAboutUnlockKnoxProfilePreferenceController(Context context, String str) {
        super(context, str);
        this.mFaceSettingsPreferenceController = new FaceProfileStatusPreferenceController(context);
        this.mFingerprintSettingsPreferenceController =
                new FingerprintProfileStatusPreferenceController(context);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
