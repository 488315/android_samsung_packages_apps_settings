package com.samsung.android.settings.deviceinfo.batteryinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBatteryManufactureDatePreferenceController
        extends BatteryRegulatoryPreferenceController {
    private static final String FILE_2ND_BATTERY_QR_CODE = "/efs/FactoryApp/HwParamBattQR_2nd";
    private static final String FILE_BATTERY_QR_CODE = "/efs/FactoryApp/HwParamBattQR";

    public SecBatteryManufactureDatePreferenceController(Context context, String str) {
        super(context, str);
    }

    private String getBatteryManufactureDateInfo(String str) {
        String batteryInfoFromEFS = getBatteryInfoFromEFS(str);
        if (!TextUtils.isEmpty(batteryInfoFromEFS)) {
            try {
                int batteryManufactureYear =
                        getBatteryManufactureYear(batteryInfoFromEFS.substring(15, 16));
                int parseInt = Integer.parseInt(batteryInfoFromEFS.substring(16, 17), 16) - 1;
                int parseInt2 = Integer.parseInt(batteryInfoFromEFS.substring(17, 19));
                Calendar calendar = Calendar.getInstance();
                calendar.set(batteryManufactureYear, parseInt, parseInt2);
                return getFormattedDate(calendar.getTime());
            } catch (Exception e) {
                CloneBackend$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("ManufactureDate Parse Error : "),
                        BatteryRegulatoryPreferenceController.LOG_TAG);
            }
        }
        return null;
    }

    private int getBatteryManufactureYear(String str) {
        int i;
        HashMap hashMap = new HashMap();
        XmlResourceParser xml =
                this.mContext.getResources().getXml(R.xml.sec_battery_info_year_table_2021);
        while (xml.getEventType() != 1) {
            try {
                if (xml.getEventType() == 2 && xml.getName().equals("entry")) {
                    hashMap.put(
                            xml.getAttributeValue(null, "yearCode"),
                            xml.getAttributeValue(null, "yearValue"));
                }
                xml.next();
            } catch (Exception e) {
                Log.w(
                        BatteryRegulatoryPreferenceController.LOG_TAG,
                        "mBatteryManufactureYearMap parser fail  : " + e.getMessage());
            }
        }
        int i2 = Calendar.getInstance().get(1);
        Log.d(
                BatteryRegulatoryPreferenceController.LOG_TAG,
                "current Year : " + i2 + ", YearCode : " + str);
        try {
            i = Integer.parseInt((String) hashMap.get(str));
        } catch (Exception e2) {
            Log.w(
                    BatteryRegulatoryPreferenceController.LOG_TAG,
                    "year value - format Error : " + e2.getMessage());
            i = 0;
        }
        if (i2 < i) {
            StringBuilder m =
                    ActivityResultRegistry$$ExternalSyntheticOutline0.m("YearCode : ", str, " = ");
            int i3 = i - 20;
            m.append(i3);
            m.append("from year table for 2001 ~ 2020");
            Log.d(BatteryRegulatoryPreferenceController.LOG_TAG, m.toString());
            return i3;
        }
        Log.d(
                BatteryRegulatoryPreferenceController.LOG_TAG,
                "YearCode : " + str + " = " + i + " from year table for 2021 ~ 2040");
        return i;
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
        return getBatteryManufactureDateInfo(FILE_BATTERY_QR_CODE);
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController
    public String getSummaryForBattery_Sub() {
        return getBatteryManufactureDateInfo(FILE_2ND_BATTERY_QR_CODE);
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
        return isDualBattery();
    }

    @Override // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryRegulatoryPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
