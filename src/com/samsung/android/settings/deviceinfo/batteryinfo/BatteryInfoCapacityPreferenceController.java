package com.samsung.android.settings.deviceinfo.batteryinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.os.PowerProfile;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.deviceinfo.SecAboutDeviceItems;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BatteryInfoCapacityPreferenceController extends BasePreferenceController {
    private static final String LOG_TAG = "BatteryInfoCapacityPreferenceController";
    public final int BATTERY_CAPACITY_VALUE_TYPE_RATED;
    public final int BATTERY_CAPACITY_VALUE_TYPE_TYPICAL;
    public final int BATTERY_CAPACITY_VALUE_UNKNOWN;

    public BatteryInfoCapacityPreferenceController(Context context, String str) {
        super(context, str);
        this.BATTERY_CAPACITY_VALUE_UNKNOWN = -1;
        this.BATTERY_CAPACITY_VALUE_TYPE_RATED = 0;
        this.BATTERY_CAPACITY_VALUE_TYPE_TYPICAL = 1;
    }

    private String getBatteryCapacitySummary() {
        return isTypicalType()
                ? this.mContext.getString(
                        R.string.device_info_battery_capacity_typical_summary_1,
                        Integer.valueOf(getBatteryCapacityByType(1)))
                : this.mContext.getString(
                        R.string.device_info_battery_capacity_summary,
                        Integer.valueOf(getBatteryCapacityByType(0)));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return supportBatteryCapacity() ? 0 : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public int getBatteryCapacityByType(int i) {
        double batteryCapacity;
        PowerProfile powerProfile = new PowerProfile(this.mContext);
        if (i == 0) {
            batteryCapacity = powerProfile.getBatteryCapacity();
        } else {
            if (i != 1) {
                return -1;
            }
            batteryCapacity = powerProfile.getBatteryTypicalCapacity();
        }
        return (int) batteryCapacity;
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
        return getBatteryCapacitySummary();
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

    public boolean isTypicalType() {
        if (getBatteryCapacityByType(1) != 0) {
            return true;
        }
        Log.d(LOG_TAG, "powerProfile.getBatteryTypicalCapacity() == 0");
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

    public boolean supportBatteryCapacity() {
        int i = SecAboutDeviceItems.ABOUTITEM_STATUS_BATTERY_CAPACITY;
        return i == -1
                ? !TextUtils.isEmpty(
                        SemFloatingFeature.getInstance()
                                .getString(
                                        "SEC_FLOATING_FEATURE_SETTINGS_CONFIG_ELECTRIC_RATED_VALUE"))
                : i != 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
