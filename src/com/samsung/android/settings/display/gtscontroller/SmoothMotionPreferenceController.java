package com.samsung.android.settings.display.gtscontroller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.core.SecSingleChoicePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.DeviceTypeConstant;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SmoothMotionPreferenceController extends SecSingleChoicePreferenceController {
    private static final String KEY_REFRESH_RATE_MODE = "key_refresh_rate_mode";
    private static final String PREFERENCE_KEY = "sec_high_refresh_rate";
    private static final int REFRESH_RATE_MODE_ALWAYS = 2;
    private static final int REFRESH_RATE_MODE_NORMAL = 0;
    private static final int REFRESH_RATE_MODE_SEAMLESS = 1;
    private static final String TAG =
            "com.samsung.android.settings.display.gtscontroller.SmoothMotionPreferenceController";

    public SmoothMotionPreferenceController(Context context) {
        super(context, PREFERENCE_KEY);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Rune.supportHighRefreshRate(this.mContext, 0) ? 0 : 3;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntries() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(this.mContext.getString(R.string.sec_high_refresh_rate_standard_title));
        arrayList.add(this.mContext.getString(R.string.sec_high_refresh_rate_best_display_title));
        arrayList.add(this.mContext.getString(R.string.sec_high_refresh_rate_dynamic_title));
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntryValues() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        arrayList.add("2");
        arrayList.add("1");
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<Integer> getImageEntries() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getMetricsCategory() {
        return 10410;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public String getSelectedValue() {
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        int intRefreshRate = SecDisplayUtils.getIntRefreshRate(context, 999);
        return intRefreshRate != 0
                ? intRefreshRate != 1 ? intRefreshRate != 2 ? ApnSettings.MVNO_NONE : "2" : "1"
                : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getSubEntries() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        int intRefreshRate = SecDisplayUtils.getIntRefreshRate(context, 999);
        return intRefreshRate != 0
                ? intRefreshRate != 1
                        ? intRefreshRate != 2
                                ? ApnSettings.MVNO_NONE
                                : this.mContext.getString(
                                        R.string.sec_high_refresh_rate_best_display_title)
                        : this.mContext.getString(R.string.sec_high_refresh_rate_dynamic_title)
                : this.mContext.getString(R.string.sec_high_refresh_rate_standard_title);
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        ControlValue value = super.getValue();
        ControlValue.Builder builder = new ControlValue.Builder(value.mKey, value.mControlType);
        builder.mAvailabilityStatus = value.mAvailabilityStatus;
        builder.mForceChange = Boolean.TRUE;
        builder.mBundle = value.mBundle;
        builder.setControlId(value.mControlId);
        builder.mIsDefault = Boolean.valueOf(value.mIsDefault);
        builder.mStatusCode = value.mStatusCode;
        builder.addAttributeInt(DeviceTypeConstant.getDeviceType(), "device_type");
        builder.setValue(value.getValue());
        return builder.build();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public boolean setSelectedValue(String str) {
        int parseInt = Integer.parseInt(str);
        if (parseInt == 0) {
            SecDisplayUtils.putIntRefreshRate(this.mContext, 0, 999);
        } else if (parseInt == 1) {
            SecDisplayUtils.putIntRefreshRate(this.mContext, 1, 999);
        } else if (parseInt == 2) {
            SecDisplayUtils.putIntRefreshRate(this.mContext, 2, 999);
        }
        return true;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        if (controlValue.getAttributeInt("device_type") == DeviceTypeConstant.getDeviceType()) {
            return super.setValue(controlValue);
        }
        Log.e(
                TAG,
                "device settings cannot be transferred on this device due to different device"
                    + " type");
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        builder.mResultCode = ControlResult.ResultCode.FAIL;
        builder.mErrorCode = ControlResult.ErrorCode.DEPENDENT_SETTING;
        builder.setErrorMsg(this.mContext.getString(R.string.unsupported_setting_summary));
        return new ControlResult(builder);
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
