package com.samsung.android.settings.deviceinfo.batteryinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BatteryHealthPreferenceController extends BatteryRegulatoryPreferenceController {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int BATTERY_HEALTH_VALUE_INVALID = -1;
    private static final int HEALTH_INFO_FIRST = 1;
    private static final int HEALTH_INFO_REPRESENTATIVE = 0;
    private static final int HEALTH_INFO_SECOND = 2;

    public BatteryHealthPreferenceController(Context context, String str) {
        super(context, str);
    }

    private String getBatteryHealthInfo(int i) {
        int i2;
        try {
            long[] semGetValuesAsLong =
                    ((BatteryManager) this.mContext.getSystemService(BatteryManager.class))
                            .semGetValuesAsLong(107);
            Log.i(
                    BatteryRegulatoryPreferenceController.LOG_TAG,
                    "BatteryManager.SEM_BATTERY_PROPERTY_BSOH : "
                            + Arrays.toString(semGetValuesAsLong));
            if (semGetValuesAsLong == null
                    || semGetValuesAsLong.length <= i
                    || (i2 = (int) semGetValuesAsLong[i]) == -1) {
                return null;
            }
            return Utils.formatPercentage(i2);
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("format error of Battery Health : "),
                    BatteryRegulatoryPreferenceController.LOG_TAG);
            return null;
        }
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController
    public String getSummaryForBattery_Main() {
        return shouldDisplayAsDualBattery() ? getBatteryHealthInfo(1) : getBatteryHealthInfo(0);
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController
    public String getSummaryForBattery_Sub() {
        return getBatteryHealthInfo(2);
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController
    public boolean shouldDisplayAsDualBattery() {
        return isDetachableDualBattery();
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
