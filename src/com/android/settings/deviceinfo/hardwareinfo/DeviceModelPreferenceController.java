package com.android.settings.deviceinfo.hardwareinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.settings.deviceinfo.HardwareInfoPreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DeviceModelPreferenceController extends HardwareInfoPreferenceController {
    public DeviceModelPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int availabilityStatus = super.getAvailabilityStatus();
        if (availabilityStatus == 1) {
            return 0;
        }
        return availabilityStatus;
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return HardwareInfoPreferenceController.getDeviceModel();
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isPublicSlice() {
        return true;
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return true;
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.deviceinfo.HardwareInfoPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean useDynamicSliceSummary() {
        return true;
    }
}
