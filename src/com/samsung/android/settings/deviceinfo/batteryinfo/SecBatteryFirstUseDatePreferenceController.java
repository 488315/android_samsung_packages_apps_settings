package com.samsung.android.settings.deviceinfo.batteryinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import android.text.TextUtils;

import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.text.SimpleDateFormat;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBatteryFirstUseDatePreferenceController
        extends BatteryRegulatoryPreferenceController {
    private static final String FILE_2ND_BATTERY_FIRST_DATE_INFO =
            "/efs/FactoryApp/batt_beginning_date_2nd";
    private static final String FILE_BATTERY_FIRST_DATE_INFO =
            "/efs/FactoryApp/batt_beginning_date";

    public SecBatteryFirstUseDatePreferenceController(Context context, String str) {
        super(context, str);
    }

    private String getFirstUseDateInfo(String str) {
        String batteryInfoFromEFS = getBatteryInfoFromEFS(str);
        if (!TextUtils.isEmpty(batteryInfoFromEFS)) {
            try {
                return getFormattedDate(new SimpleDateFormat("yyyyMMdd").parse(batteryInfoFromEFS));
            } catch (Exception e) {
                CloneBackend$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("First Used Date Parse Error : "),
                        BatteryRegulatoryPreferenceController.LOG_TAG);
            }
        }
        return null;
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if ("SM-A236B".equalsIgnoreCase(SystemProperties.get("ro.product.model"))) {
            return 3;
        }
        return super.getAvailabilityStatus();
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
        return getFirstUseDateInfo(FILE_BATTERY_FIRST_DATE_INFO);
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController
    public String getSummaryForBattery_Sub() {
        return getFirstUseDateInfo(FILE_2ND_BATTERY_FIRST_DATE_INFO);
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
