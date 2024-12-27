package com.samsung.android.settings.usefulfeature.hongbao;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.secutil.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class HongbaoAssistantSwitchPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    public static final String HONGBAO_ACCELERATION = "hongbao_acceleration";
    public static final String HONGBAO_ALERTS_ON = "hongbao_alert_on";
    public static final String KEY_EXTERNAL_MASTER_SWITCH_ONOFF = "hongbao_master_switch_onoff";
    private static final String KEY_HONGBAO_ASSISTANT_SWITCH = "hongbao_assistant_switch";
    private static final String TAG = "HongbaoAssistantSwitchPreferenceController";
    private ContentObserver mHongbaoAssistantObserver;
    private SecSwitchPreferenceScreen mPreference;

    public HongbaoAssistantSwitchPreferenceController(Context context) {
        super(context, KEY_HONGBAO_ASSISTANT_SWITCH);
    }

    private boolean isExternalServiceExist() {
        ResolveInfo resolveService =
                this.mContext
                        .getPackageManager()
                        .resolveService(
                                DisplaySettings$$ExternalSyntheticOutline0.m(
                                        "com.samsung.hongbaoassistant",
                                        "com.samsung.hongbaoassistant.ExternalService"),
                                0);
        return (resolveService == null || resolveService.serviceInfo == null) ? false : true;
    }

    private static boolean isHongbaoAssistantEnabled(Context context) {
        String str = "com.tencent.mm";
        if (UserHandle.myUserId() != 0
                || context.getPackageManager().isSafeMode()
                || !Utils.hasPackage(context, "com.samsung.hongbaoassistant")) {
            return false;
        }
        try {
            Bundle bundle =
                    context.getPackageManager()
                            .getApplicationInfo("com.samsung.hongbaoassistant", 128)
                            .metaData;
            if (bundle != null) {
                str = bundle.getString("HongbaoSupportApps", "com.tencent.mm");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (str == null || str.isEmpty()) {
            return true;
        }
        for (String str2 : str.split(",")) {
            if (Utils.hasPackage(context, str2)) {
                return true;
            }
        }
        return false;
    }

    private int settingOf(String str, int i) {
        return Settings.System.getInt(this.mContext.getContentResolver(), str, i);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecSwitchPreferenceScreen)
                        preferenceScreen.findPreference(KEY_HONGBAO_ASSISTANT_SWITCH);
        this.mHongbaoAssistantObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.usefulfeature.hongbao.HongbaoAssistantSwitchPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        if (HongbaoAssistantSwitchPreferenceController.this.mPreference != null) {
                            HongbaoAssistantSwitchPreferenceController
                                    hongbaoAssistantSwitchPreferenceController =
                                            HongbaoAssistantSwitchPreferenceController.this;
                            hongbaoAssistantSwitchPreferenceController.updateState(
                                    hongbaoAssistantSwitchPreferenceController.mPreference);
                        }
                    }
                };
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (isHongbaoAssistantEnabled(this.mContext) && isExternalServiceExist()) ? 0 : 3;
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
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
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
        ArrayList arrayList = new ArrayList();
        if (settingOf(HONGBAO_ALERTS_ON, 0) != 0) {
            arrayList.add(this.mContext.getString(R.string.hongbao_assistant_summary_of_alerts));
        }
        if (settingOf(HONGBAO_ACCELERATION, 0) != 0) {
            arrayList.add(
                    this.mContext.getString(R.string.hongbao_assistant_summary_of_acceleration));
        }
        if (arrayList.isEmpty()) {
            arrayList.add(this.mContext.getString(R.string.hongbao_assistant_summary));
        }
        return Utils.buildSummaryString(
                Utils.getTopLevelSummarySeparator(this.mContext), arrayList, arrayList.size());
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
        if (secSwitchPreferenceScreen != null && preference.equals(secSwitchPreferenceScreen)) {
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.setClassName(
                        "com.samsung.hongbaoassistant",
                        "com.samsung.hongbaoassistant.settings.HongbaoAssistantActivity");
                this.mContext.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e) {
                Log.secD(TAG, "ActivityNotFoundException in HongbaoAssistant");
                e.printStackTrace();
            }
        }
        return super.handlePreferenceTreeClick(preference);
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
        return (settingOf(HONGBAO_ALERTS_ON, 0) == 0 && settingOf(HONGBAO_ACCELERATION, 0) == 0)
                ? false
                : true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return isAvailable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        if (this.mPreference == null || this.mHongbaoAssistantObserver == null) {
            return;
        }
        this.mContext
                .getContentResolver()
                .unregisterContentObserver(this.mHongbaoAssistantObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (this.mPreference != null) {
            boolean threadEnabled = getThreadEnabled();
            SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
            if (threadEnabled != secSwitchPreferenceScreen.mChecked) {
                secSwitchPreferenceScreen.setChecked(getThreadEnabled());
            }
            if (this.mHongbaoAssistantObserver != null) {
                this.mContext
                        .getContentResolver()
                        .registerContentObserver(
                                Settings.System.getUriFor(HONGBAO_ALERTS_ON),
                                true,
                                this.mHongbaoAssistantObserver);
                this.mContext
                        .getContentResolver()
                        .registerContentObserver(
                                Settings.System.getUriFor(HONGBAO_ACCELERATION),
                                true,
                                this.mHongbaoAssistantObserver);
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void refreshSummary(Preference preference) {
        super.refreshSummary(preference);
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
        boolean threadEnabled = getThreadEnabled();
        secSwitchPreferenceScreen.getClass();
        SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen, threadEnabled);
        this.mPreference.setSummary(getSummary());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Intent intent = new Intent();
        intent.putExtra(KEY_EXTERNAL_MASTER_SWITCH_ONOFF, z ? 1 : 0);
        intent.setClassName(
                "com.samsung.hongbaoassistant", "com.samsung.hongbaoassistant.ExternalService");
        ResolveInfo resolveService = this.mContext.getPackageManager().resolveService(intent, 0);
        if (resolveService != null && resolveService.serviceInfo != null) {
            this.mContext.startService(intent);
        }
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference == null) {
            return;
        }
        refreshSummary(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public HongbaoAssistantSwitchPreferenceController(Context context, String str) {
        super(context, str);
    }
}
