package com.samsung.android.settings.deviceinfo.statusinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.deviceinfo.SecAboutDeviceItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FccCertificationPreferenceController extends BasePreferenceController {
    private static final String LOG_TAG = "FccCertificationPreCtr";
    private final String SEPARATOR_FCC_ISED;
    private final String SEPARATOR_FOR_MULTI_FCC;
    private final String SEPARATOR_HW_REVISION_FCC;

    public FccCertificationPreferenceController(Context context, String str) {
        super(context, str);
        this.SEPARATOR_FCC_ISED = ";";
        this.SEPARATOR_FOR_MULTI_FCC = ",";
        this.SEPARATOR_HW_REVISION_FCC = "_";
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (Rune.isLDUModel() || Rune.isChinaModel() || !supportFccId()) ? 3 : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public String getFccId() {
        String itemValue = SecAboutDeviceItems.getItemValue("fcc");
        if (TextUtils.isEmpty(itemValue)) {
            itemValue =
                    SemFloatingFeature.getInstance()
                            .getString(
                                    "SEC_FLOATING_FEATURE_SETTINGS_CONFIG_FCC_ID",
                                    ApnSettings.MVNO_NONE);
            if (itemValue.contains(";")) {
                itemValue = itemValue.split(";")[0];
            }
        }
        return isMultiFccId(itemValue)
                ? getFccIdBasedOnHwRev(itemValue)
                : TextUtils.isEmpty(itemValue)
                        ? this.mContext.getString(R.string.device_info_not_available)
                        : itemValue;
    }

    public String getFccIdBasedOnHwRev(String str) {
        String str2 = SystemProperties.get("ro.revision", ApnSettings.MVNO_NONE);
        if (TextUtils.isEmpty(str2)) {
            Log.i(LOG_TAG, "return default id because HW revision of Device is empty");
            return str.split(",")[0];
        }
        Log.i(LOG_TAG, "Get id based on HwRev");
        float parseFloat = Float.parseFloat(str2);
        String[] split = str.split(",");
        HashMap hashMap = new HashMap();
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                hashMap.put(Float.valueOf(0.0f), split[0]);
            } else {
                String str3 = split[i];
                if (TextUtils.isEmpty(str3)) {
                    Log.i(LOG_TAG, "Format error case: ,,");
                } else if (str3.contains("_")) {
                    String[] split2 = str3.split("_");
                    if (split2.length != 2) {
                        Log.i(LOG_TAG, "Format error case: There must be only one {underBar}.");
                    } else {
                        try {
                            if (Float.parseFloat(split2[0]) < 0.0f) {
                                Log.i(
                                        LOG_TAG,
                                        "Format error case: Revision must be greater than zero");
                            } else {
                                String[] split3 = split[i].split("_");
                                hashMap.put(Float.valueOf(Float.parseFloat(split3[0])), split3[1]);
                            }
                        } catch (NullPointerException | NumberFormatException e) {
                            Log.i(LOG_TAG, "parse fail for revision " + e.getMessage());
                        }
                    }
                } else {
                    Log.i(LOG_TAG, "Format error case: {HwRev} only");
                }
            }
        }
        String str4 = (String) hashMap.get(Float.valueOf(0.0f));
        for (Map.Entry entry : hashMap.entrySet()) {
            if (parseFloat >= ((Float) entry.getKey()).floatValue()) {
                str4 = (String) entry.getValue();
            }
        }
        Log.i(LOG_TAG, "id based on HwRev" + str4);
        return TextUtils.isEmpty(str4)
                ? this.mContext.getString(R.string.device_info_not_available)
                : str4;
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
        return this.mContext.getString(R.string.device_info_fcc_id, getFccId());
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

    public boolean isMultiFccId(String str) {
        return str != null && str.contains(",");
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

    public boolean supportFccId() {
        int i = SecAboutDeviceItems.ABOUTITEM_STATUS_FCC_CERTIFICATION;
        if (i != -1) {
            return i == 2;
        }
        if (!SemCscFeature.getInstance()
                .getBoolean("CscFeature_Setting_SupportELabelFccId", true)) {
            return false;
        }
        String string =
                SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_SETTINGS_CONFIG_FCC_ID",
                                ApnSettings.MVNO_NONE);
        return (TextUtils.isEmpty(string) || string.startsWith(";")) ? false : true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
