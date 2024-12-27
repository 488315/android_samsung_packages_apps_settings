package com.samsung.android.settings.display.controller;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SecCustomPreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.applications.RecentAppOpsAccess;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.CameraCutoutFragmentUserSelect;
import com.samsung.android.settings.display.DeviceTypeConstant;
import com.samsung.android.settings.display.DisplayDisabledAppearancePreference;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.util.SemLog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CameraCutoutPreferenceController extends SecCustomPreferenceController {
    private static final String PACKAGE = "package";
    private static final String PREFERENCE_KEY = "camera_cutout";
    private static final String TAG =
            "com.samsung.android.settings.display.controller.CameraCutoutPreferenceController";
    private static final String VALUE = "value";
    private LauncherApps mLauncherApps;
    private DisplayDisabledAppearancePreference mPreference;
    private String mResult;
    private final ArrayMap<String, AppRow> mRows;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppRow {
        public String mPackage;
        public int mType;
    }

    public CameraCutoutPreferenceController(Context context, String str) {
        super(context, str);
        this.mRows = new ArrayMap<>();
        this.mLauncherApps = (LauncherApps) this.mContext.getSystemService("launcherapps");
    }

    private void collectData() {
        this.mRows.clear();
        List<LauncherActivityInfo> activityList =
                this.mLauncherApps.getActivityList(null, UserHandle.OWNER);
        Log.secD(TAG, " launchable activities:");
        Iterator<LauncherActivityInfo> it = activityList.iterator();
        while (it.hasNext()) {
            ApplicationInfo applicationInfo = it.next().getApplicationInfo();
            String str = applicationInfo.packageName;
            if (!KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(str)
                    && !this.mRows.containsKey(str)) {
                this.mRows.put(str, loadAppRow(applicationInfo));
            }
        }
        Log.secD(TAG, "Finish getting ApplicationInfo");
    }

    private int getCutoutPolicy(String str, int i) {
        int i2 = 0;
        try {
            i2 = ActivityTaskManager.getService().getCutoutPolicy(i, str);
            Log.d(TAG, "getCameraCutoutPackage() : " + str + " / " + i2);
            return i2;
        } catch (RemoteException unused) {
            Log.secD(TAG, "getCameraCutoutPackage() RemoteException");
            return i2;
        }
    }

    private AppRow loadAppRow(ApplicationInfo applicationInfo) {
        AppRow appRow = new AppRow();
        String str = applicationInfo.packageName;
        appRow.mPackage = str;
        appRow.mType = getCutoutPolicy(str, UserHandle.myUserId());
        return appRow;
    }

    private void setCutoutPolicy(String str, int i, int i2) {
        Log.d(TAG, "setCameraCutoutPackage : " + str + " / " + i2);
        try {
            ActivityTaskManager.getService().setCutoutPolicy(i, str, i2);
        } catch (RemoteException unused) {
            Log.secD(TAG, "setCameraCutoutPackage() RemoteException");
        }
    }

    private void updatePreferenceState(boolean z) {
        if (z) {
            this.mPreference.setEnabledAppearance(true);
        } else {
            this.mPreference.setEnabledAppearance(false);
            this.mPreference.mMsg =
                    this.mContext.getString(R.string.camera_cutout_toast_message_for_cover_screen);
        }
        this.mPreference.setVisible(isAvailable());
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (DisplayDisabledAppearancePreference)
                        preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean z;
        Context context = this.mContext;
        if (Rune.mSupportDisplayCut == null) {
            try {
                Resources resources = context.getResources();
                int identifier =
                        resources.getIdentifier(
                                "config_mainBuiltInDisplayCutout",
                                "string",
                                RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME);
                String string = identifier > 0 ? resources.getString(identifier) : null;
                int identifier2 =
                        resources.getIdentifier(
                                "config_subBuiltInDisplayCutout",
                                "string",
                                RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME);
                String string2 = identifier2 > 0 ? resources.getString(identifier2) : null;
                if (TextUtils.isEmpty(string) && TextUtils.isEmpty(string2)) {
                    z = false;
                    Rune.mSupportDisplayCut = Boolean.valueOf(z);
                }
                z = true;
                Rune.mSupportDisplayCut = Boolean.valueOf(z);
            } catch (Exception e) {
                SemLog.w("Rune", "Can not update mSupportDisplayCut. " + e.toString());
                Rune.mSupportDisplayCut = Boolean.FALSE;
            }
        }
        SemLog.d("Rune", "mSupportDisplayCut : " + Rune.mSupportDisplayCut);
        if (Rune.mSupportDisplayCut.booleanValue()) {
            return ((Rune.isSamsungDexMode(this.mContext)
                                    || Utils.isDesktopStandaloneMode(this.mContext))
                            && !Utils.isNewDexMode(this.mContext))
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
        String name = CameraCutoutFragmentUserSelect.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.full_screen_apps_camera_cutout, null);
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
                this.mContext.getString(R.string.full_screen_apps_summary_for_camera_cutout);
        builder.setValue(this.mResult);
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return builder.build();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        DisplayDisabledAppearancePreference displayDisabledAppearancePreference = this.mPreference;
        if (displayDisabledAppearancePreference == null
                || displayDisabledAppearancePreference.mIsEnabled) {
            return super.handlePreferenceTreeClick(preference);
        }
        return true;
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
                        setCutoutPolicy(
                                jSONObject.getString(PACKAGE), 0, jSONObject.getInt("value"));
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public CameraCutoutPreferenceController(Context context) {
        super(context, PREFERENCE_KEY);
        this.mRows = new ArrayMap<>();
        this.mLauncherApps = (LauncherApps) this.mContext.getSystemService("launcherapps");
    }
}
