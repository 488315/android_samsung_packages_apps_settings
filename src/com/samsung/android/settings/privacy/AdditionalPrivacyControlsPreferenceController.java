package com.samsung.android.settings.privacy;

import android.content.Intent;
import android.content.IntentFilter;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AdditionalPrivacyControlsPreferenceController extends BasePreferenceController {
    private static final String CONFIG_KEY = "camera_toggle_enabled";
    private final boolean isSupportCamera;

    /* JADX WARN: Code restructure failed: missing block: B:4:0x001a, code lost:

       if (android.provider.DeviceConfig.getBoolean("privacy", com.samsung.android.settings.privacy.AdditionalPrivacyControlsPreferenceController.CONFIG_KEY, true) != false) goto L8;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AdditionalPrivacyControlsPreferenceController(
            android.content.Context r2, java.lang.String r3) {
        /*
            r1 = this;
            r1.<init>(r2, r3)
            com.android.settings.utils.SensorPrivacyManagerHelper r2 = com.android.settings.utils.SensorPrivacyManagerHelper.getInstance(r2)
            android.hardware.SensorPrivacyManager r2 = r2.sensorPrivacyManager
            r3 = 2
            boolean r2 = r2.supportsSensorToggle(r3)
            if (r2 == 0) goto L1d
            java.lang.String r2 = "privacy"
            java.lang.String r3 = "camera_toggle_enabled"
            r0 = 1
            boolean r2 = android.provider.DeviceConfig.getBoolean(r2, r3, r0)
            if (r2 == 0) goto L1d
            goto L1e
        L1d:
            r0 = 0
        L1e:
            r1.isSupportCamera = r0
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.privacy.AdditionalPrivacyControlsPreferenceController.<init>(android.content.Context,"
                    + " java.lang.String):void");
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mContext.getText(
                this.isSupportCamera
                        ? R.string.privacy_controls_summary
                        : R.string.privacy_controls_summary_no_camera);
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

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
