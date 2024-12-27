package com.samsung.android.settings.deviceinfo.statusinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.deviceinfo.SecAboutDeviceItems;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SpenIsedCertificationPreferenceController extends BasePreferenceController {
    private static final String SEPARATOR_FCC_ISED_FOR_SPEN = ";";

    public SpenIsedCertificationPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return ("CA".equals(Utils.readCountryCode()) && supportSpenIsedId()) ? 0 : 3;
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

    public String getSpenIsedId() {
        String itemValue = SecAboutDeviceItems.getItemValue("fccforspen");
        if (TextUtils.isEmpty(itemValue)) {
            itemValue =
                    SemFloatingFeature.getInstance()
                            .getString(
                                    "SEC_FLOATING_FEATURE_SETTINGS_CONFIG_SPEN_FCC_ID",
                                    ApnSettings.MVNO_NONE)
                            .split(SEPARATOR_FCC_ISED_FOR_SPEN)[1];
        }
        return TextUtils.isEmpty(itemValue)
                ? this.mContext.getString(R.string.device_info_not_available)
                : itemValue;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mContext.getString(R.string.device_info_spen_ic_id, getSpenIsedId());
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

    public boolean supportSpenIsedId() {
        if (!SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BLE_SPEN")) {
            return false;
        }
        int i = SecAboutDeviceItems.ABOUTITEM_STATUS_SPEN_ISED_CERTIFICATION;
        if (i != -1) {
            return i == 2;
        }
        String string =
                SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_SETTINGS_CONFIG_SPEN_FCC_ID",
                                ApnSettings.MVNO_NONE);
        return !TextUtils.isEmpty(string) && string.split(SEPARATOR_FCC_ISED_FOR_SPEN).length >= 2;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
