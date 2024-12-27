package com.samsung.android.settings.usefulfeature.labs.appallowedoncoverscreen;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.utils.applications.AppUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.controller.SecAccountPreferenceController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class AppAllowedOnCoverScreenPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume {
    private static final String APP_ALLOWED_COVER_SCREEN_ENABLED = "large_cover_screen_apps";
    private static final String TAG = "AppAllowedOnCoverScreenPreferenceController";
    private SecSwitchPreferenceScreen mPreference;

    public AppAllowedOnCoverScreenPreferenceController(Context context, String str) {
        super(context, str);
    }

    private int getEnabledCoverScreenAppCount() {
        int i;
        try {
            i =
                    ActivityTaskManager.getService()
                            .getCoverLauncherEnabledAppList(UserHandle.getCallingUserId())
                            .size();
        } catch (RemoteException e) {
            SemLog.e(TAG, "Can not retrive enable app list : " + e.getMessage());
            i = 0;
        }
        SemLog.d(TAG, "Cover Launcher Enabled App Count  = " + i);
        return i;
    }

    private int getSupportedCoverScreenAppCount() {
        int i;
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(
                    ActivityTaskManager.getService()
                            .getCoverLauncherAvailableAppList(UserHandle.getCallingUserId()));
            i = arrayList.size();
        } catch (RemoteException e) {
            SemLog.e(TAG, "Can not retrieve app list : " + e.getMessage());
            i = 0;
        }
        SemLog.d(TAG, "Cover Launcher App Count = " + i);
        return i;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secSwitchPreferenceScreen;
        secSwitchPreferenceScreen.getClass();
        SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen, true);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return 3;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        subSettingLauncher.mLaunchRequest.mDestinationName =
                AppAllowedOnCoverScreenSettings.class.getName();
        return SecAccountPreferenceController$$ExternalSyntheticOutline0.m(
                subSettingLauncher,
                null,
                R.string.sec_labs_cover_screen_apps_allowed_on_cover_screen,
                268468224);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_advanced_features;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        int i = 0;
        int i2 =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), APP_ALLOWED_COVER_SCREEN_ENABLED, 0);
        String str = ApnSettings.MVNO_NONE;
        if (i2 == 0) {
            return ApnSettings.MVNO_NONE;
        }
        HashMap hashMap = new HashMap();
        try {
            hashMap.putAll(
                    ActivityTaskManager.getService()
                            .getCoverLauncherEnabledAppList(UserHandle.getCallingUserId()));
        } catch (RemoteException e) {
            SemLog.e(TAG, "Can not retrieve enable app list : " + e.getMessage());
        }
        int size = hashMap.size();
        SemLog.d(TAG, "CoverLauncherEnabledApp = " + hashMap.size());
        if (size == 0) {
            return ApnSettings.MVNO_NONE;
        }
        if (size < 4) {
            for (Map.Entry entry : hashMap.entrySet()) {
                StringBuilder m =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                str);
                m.append(
                        (Object)
                                AppUtils.getApplicationLabel(
                                        this.mContext.getPackageManager(),
                                        (String) entry.getKey()));
                str = m.toString();
                if (i < size - 1) {
                    str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, ", ");
                }
                i++;
            }
            return str;
        }
        String[] strArr = new String[2];
        Iterator it = hashMap.entrySet().iterator();
        int i3 = 0;
        while (it.hasNext()) {
            strArr[i3] =
                    AppUtils.getApplicationLabel(
                                    this.mContext.getPackageManager(),
                                    (String) ((Map.Entry) it.next()).getKey())
                            .toString();
            if (i3 >= 1) {
                break;
            }
            i3++;
        }
        return this.mContext.getString(
                R.string.sec_labs_cover_screen_apps_allowed_on_cover_screen_summary,
                strArr[0],
                strArr[1],
                Integer.valueOf(size - 2));
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.System.getInt(
                        this.mContext.getContentResolver(), APP_ALLOWED_COVER_SCREEN_ENABLED, 0)
                != 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        refreshSummary(this.mPreference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.System.putInt(
                this.mContext.getContentResolver(), APP_ALLOWED_COVER_SCREEN_ENABLED, z ? 1 : 0);
        refreshSummary(this.mPreference);
        LoggingHelper.insertEventLogging(68700, 68703, z);
        if (!(this.mContext instanceof SettingsActivity)
                || !z
                || getEnabledCoverScreenAppCount() != 0) {
            return true;
        }
        Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("type", "setting");
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = AppAllowedOnCoverScreenSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.sec_labs_cover_screen_title, null);
        launchRequest.mSourceMetricsCategory = getMetricsCategory();
        launchRequest.mArguments = m;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
