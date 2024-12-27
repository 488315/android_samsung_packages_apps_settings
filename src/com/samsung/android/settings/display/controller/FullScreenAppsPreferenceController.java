package com.samsung.android.settings.display.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.IWindowManager;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SecCustomPreferenceController;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.AspectRatioFragmentUserSelect;
import com.samsung.android.settings.display.DeviceTypeConstant;
import com.samsung.android.view.SemWindowManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FullScreenAppsPreferenceController extends SecCustomPreferenceController {
    public static final int ASPECT_RATIO_VALUE_APP_DEFAULT = 0;
    public static final int ASPECT_RATIO_VALUE_CUTOUT_OFF = 4;
    public static final int ASPECT_RATIO_VALUE_FIXED_OFF = 3;
    public static final int ASPECT_RATIO_VALUE_FIXED_ON = 2;
    public static final int ASPECT_RATIO_VALUE_FULL_SCREEN = 1;
    private static final String PACKAGE = "package";
    private static final String PREFERENCE_KEY = "full_screen_apps";
    private static final String TAG =
            "com.samsung.android.settings.display.controller.FullScreenAppsPreferenceController";
    private static final String VALUE = "value";
    private LauncherApps mLauncherApps;
    private String mResult;
    private final ArrayMap<String, AppRow> mRows;
    private IWindowManager mWindowManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppRow {
        public String mPackage;
        public int mType;
    }

    public FullScreenAppsPreferenceController(Context context, String str) {
        super(context, str);
        this.mRows = new ArrayMap<>();
        this.mWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        this.mLauncherApps = (LauncherApps) this.mContext.getSystemService("launcherapps");
    }

    private void collectData() {
        AppRow loadAppRow;
        int i;
        this.mRows.clear();
        List<LauncherActivityInfo> activityList =
                this.mLauncherApps.getActivityList(null, UserHandle.OWNER);
        Log.secD(TAG, " launchable activities:");
        for (LauncherActivityInfo launcherActivityInfo : activityList) {
            Log.d(TAG, "    " + launcherActivityInfo.getComponentName().toString());
            ApplicationInfo applicationInfo = launcherActivityInfo.getApplicationInfo();
            String str = applicationInfo.packageName;
            if (!KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(str)
                    && !this.mRows.containsKey(str)
                    && (i = (loadAppRow = loadAppRow(applicationInfo)).mType) != 2
                    && i != 3
                    && i != 4) {
                this.mRows.put(str, loadAppRow);
            }
        }
        Log.secD(TAG, "Finish getting ApplicationInfo");
    }

    private int getMaxAspectRatioPolicy(String str, int i) {
        int i2 = 0;
        try {
            i2 = this.mWindowManager.getMaxAspectRatioPolicy(str, i);
            Log.d(TAG, "getMaxAspectRatioPolicy() : " + str + " / " + i2);
            return i2;
        } catch (RemoteException unused) {
            Log.d(TAG, "getMaxAspectRatioPolicy() RemoteException");
            return i2;
        }
    }

    private AppRow loadAppRow(ApplicationInfo applicationInfo) {
        AppRow appRow = new AppRow();
        String str = applicationInfo.packageName;
        appRow.mPackage = str;
        appRow.mType = getMaxAspectRatioPolicy(str, 0);
        return appRow;
    }

    private void setMaxAspectRatioPolicy(String str, int i, boolean z) {
        Log.d(TAG, "setMaxAspectPackage : " + str + " / " + z);
        try {
            this.mWindowManager.setMaxAspectRatioPolicy(str, i, z, -1);
        } catch (RemoteException unused) {
            Log.d(TAG, "setMaxAspectPackage() RemoteException");
        }
    }

    private void writeJSONData() {
        JSONArray jSONArray = new JSONArray();
        for (AppRow appRow : this.mRows.values()) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(PACKAGE, appRow.mPackage);
                jSONObject.put("value", appRow.mType);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            jSONArray.put(jSONObject);
        }
        this.mResult = jSONArray.toString();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Utils.isTablet() && SemWindowManager.isSupportAspectRatioMode(this.mContext)) {
            return (Rune.isSamsungDexMode(this.mContext)
                            || Utils.isDesktopStandaloneMode(this.mContext))
                    ? 5
                    : 0;
        }
        return 3;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = AspectRatioFragmentUserSelect.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.full_screen_apps_title, null);
        launchRequest.mSourceMetricsCategory = 100;
        subSettingLauncher.addFlags(268468224);
        return subSettingLauncher.toIntent();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        collectData();
        writeJSONData();
        ControlValue.Builder builder = new ControlValue.Builder(PREFERENCE_KEY, getControlType());
        builder.addAttributeInt(DeviceTypeConstant.getDeviceType(), "device_type");
        builder.mAvailabilityStatus = getAvailabilityStatus();
        Boolean bool = Boolean.TRUE;
        builder.mForceChange = bool;
        builder.setControlId(getControlId());
        builder.mIsDefault = bool;
        builder.mStatusCode = getStatusCode();
        builder.mSummary =
                this.mContext.getString(R.string.full_screen_apps_summary_for_aspect_ratio);
        builder.setValue(this.mResult);
        return builder.build();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        if (controlValue != null && controlValue.mKey.equals(PREFERENCE_KEY)) {
            if (getAvailabilityStatus() != 0
                    || controlValue.getAttributeInt("device_type")
                            != DeviceTypeConstant.getDeviceType()) {
                Log.e(
                        TAG,
                        "device settings cannot be transferred on this device due to different"
                            + " device type");
                ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
                builder.mResultCode = ControlResult.ResultCode.FAIL;
                builder.mErrorCode = ControlResult.ErrorCode.DEPENDENT_SETTING;
                builder.setErrorMsg(this.mContext.getString(R.string.unsupported_setting_summary));
                return new ControlResult(builder);
            }
            this.mResult = controlValue.getValue();
            collectData();
            ArrayList arrayList = new ArrayList();
            Iterator<AppRow> it = this.mRows.values().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().mPackage);
            }
            try {
                JSONArray jSONArray = new JSONArray(this.mResult);
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    if (arrayList.contains(jSONObject.getString(PACKAGE))) {
                        setMaxAspectRatioPolicy(
                                jSONObject.getString(PACKAGE), 0, jSONObject.getInt("value") != 0);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        ControlResult.Builder builder2 = new ControlResult.Builder(getPreferenceKey());
        builder2.mResultCode = ControlResult.ResultCode.SUCCESS;
        return new ControlResult(builder2);
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public FullScreenAppsPreferenceController(Context context) {
        super(context, PREFERENCE_KEY);
        this.mRows = new ArrayMap<>();
        this.mWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        this.mLauncherApps = (LauncherApps) this.mContext.getSystemService("launcherapps");
    }
}
