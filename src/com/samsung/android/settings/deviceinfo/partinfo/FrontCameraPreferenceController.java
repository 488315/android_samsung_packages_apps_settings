package com.samsung.android.settings.deviceinfo.partinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FrontCameraPreferenceController extends PartReplacementPreferenceController {
    private static final String MODULE_NAME_FRONT_CAMERA = "front_wide";

    public FrontCameraPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController
    public String getPartReplacementFileName() {
        return getMapForCameraModuleId().get(MODULE_NAME_FRONT_CAMERA);
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.samsung.android.settings.deviceinfo.partinfo.PartReplacementPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}