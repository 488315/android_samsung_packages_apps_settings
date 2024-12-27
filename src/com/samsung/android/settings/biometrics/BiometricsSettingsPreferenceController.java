package com.samsung.android.settings.biometrics;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.biometrics.face.FaceSettingsPreferenceController;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricsSettingsPreferenceController extends BasePreferenceController {
    private static final String KEY_BIOMETRICS = "sec_biometrics_preference";
    private static final String LOG_TAG = "BiometricsSettingsPreferenceController";
    private final FaceSettingsPreferenceController mFaceSettingsPreferenceController;
    private final FingerprintSettingsPreferenceController mFingerprintSettingsPreferenceController;

    public BiometricsSettingsPreferenceController(Context context) {
        this(context, KEY_BIOMETRICS);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!KnoxUtils.checkKnoxCustomSettingsHiddenItem(16)) {
            return (this.mFaceSettingsPreferenceController.isAvailable()
                            || this.mFingerprintSettingsPreferenceController.isAvailable())
                    ? 0
                    : 3;
        }
        Log.i(LOG_TAG, "CONDITIONALLY_UNAVAILABLE 2");
        return 2;
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

    public BiometricsSettingsPreferenceController(Context context, String str) {
        super(context, str);
        this.mFaceSettingsPreferenceController = new FaceSettingsPreferenceController(context);
        this.mFingerprintSettingsPreferenceController =
                new FingerprintSettingsPreferenceController(context);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
