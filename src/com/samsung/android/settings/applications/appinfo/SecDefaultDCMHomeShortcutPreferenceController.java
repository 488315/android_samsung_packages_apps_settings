package com.samsung.android.settings.applications.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.applications.DefaultAppInfo;

import com.samsung.android.settings.DCMHomeSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDefaultDCMHomeShortcutPreferenceController
        extends DefaultAppShortcutPreferenceControllerBase {
    private static final String KEY = "home_dcm";

    public SecDefaultDCMHomeShortcutPreferenceController(Context context, String str) {
        super(context, KEY, "android.app.role.HOME", str);
    }

    private static String getOnlyAppLabel(Context context, PackageManager packageManager) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        packageManager.getHomeActivities(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = ((ResolveInfo) it.next()).activityInfo;
            if (!activityInfo.packageName.equals(context.getPackageName())) {
                arrayList2.add(activityInfo);
            }
        }
        if (arrayList2.size() == 1) {
            return ((ActivityInfo) arrayList2.get(0))
                    .loadLabel(context.getPackageManager())
                    .toString();
        }
        return null;
    }

    public static boolean hasHomePreference(String str, Context context) {
        ArrayList arrayList = new ArrayList();
        context.getPackageManager().getHomeActivities(arrayList);
        for (int i = 0; i < arrayList.size(); i++) {
            if (((ResolveInfo) arrayList.get(i)).activityInfo.packageName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (Rune.isJapanDCMModel()
                        && !Utils.hasPackage(this.mContext, "com.bmwgroup.touchcommand")
                        && hasHomePreference(this.mPackageName, this.mContext))
                ? 0
                : 3;
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        PackageManager packageManager = this.mContext.getPackageManager();
        CharSequence loadLabel =
                new DefaultAppInfo(
                                this.mContext,
                                packageManager,
                                UserHandle.myUserId(),
                                packageManager.getHomeActivities(new ArrayList()))
                        .loadLabel();
        if (!TextUtils.isEmpty(loadLabel)) {
            return loadLabel;
        }
        String onlyAppLabel = getOnlyAppLabel(this.mContext, packageManager);
        return !TextUtils.isEmpty(onlyAppLabel)
                ? onlyAppLabel
                : this.mContext.getString(R.string.sec_no_default_app);
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(this.mPreferenceKey, preference.getKey())) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", this.mPreferenceKey);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = DCMHomeSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.sec_home_app, null);
        launchRequest.mSourceMetricsCategory = 0;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.applications.appinfo.DefaultAppShortcutPreferenceControllerBase, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
