package com.samsung.android.settings.multidevices.quickshare;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class QuickSharePreferenceController extends TogglePreferenceController {
    private static final String INTENT_ACTION_ACTIVATION_DIALOG =
            "com.samsung.android.app.sharelive.ACTIVATION_DIALOG";
    private static final String INTENT_ACTION_CONNECTED_DEVICES_STATE_CHANGED =
            "com.samsung.android.app.sharelive.CONNECTED_DEVICES_STATE_CHANGED";
    private static final String INTENT_EXTRA_CONNECTED_DEVICES_SETTING_VALUE =
            "quick_share_setting_value";
    private static final String INTENT_EXTRA_LAUNCH_FROM = "launch_from";
    private static final String KEY_QUICK_SHARE_CHINA = "quickshare_settings_china";
    private static final String KEY_QUICK_SHARE_SETTING_VALUE = "quickshare_activated";
    private static final String LAUNCH_ACTIVATION_DIALOG =
            "com.samsung.android.app.sharelive.presentation.permission.ActivationDialogActivity";
    private static final String LAUNCH_ACTIVITY =
            "com.samsung.android.app.sharelive.presentation.settings.DeviceVisibilitySettingsActivity";
    public static final String PACKAGE_NAME = "com.samsung.android.app.sharelive";
    public static final String QUICKSHARE_CHINA_P2P_SUPPORTED =
            "com.samsung.android.app.sharelive.supportChinaP2p";
    private static final String TAG = "ShareLive: QuickSharePreferenceController";

    public QuickSharePreferenceController(Context context, String str) {
        super(context, str);
    }

    private void sendBroadcastChangedValue(int i) {
        Intent intent = new Intent();
        intent.setAction(INTENT_ACTION_CONNECTED_DEVICES_STATE_CHANGED);
        intent.putExtra(INTENT_EXTRA_CONNECTED_DEVICES_SETTING_VALUE, i);
        intent.setPackage("com.samsung.android.app.sharelive");
        this.mContext.sendBroadcast(
                intent, "com.samsung.android.sharelive.permission.SHARE_LIVE_UPDATE");
    }

    private boolean setQuickShareSettingValue(int i) {
        if (i != 0 && i != 1) {
            return false;
        }
        int i2 =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), KEY_QUICK_SHARE_SETTING_VALUE, -1);
        if (i2 == -1 || i2 != i) {
            return Settings.System.putInt(
                    this.mContext.getContentResolver(), KEY_QUICK_SHARE_SETTING_VALUE, i);
        }
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Utils.hasPackage(this.mContext, "com.samsung.android.app.sharelive")) {
            return 3;
        }
        try {
            Bundle bundle =
                    this.mContext
                            .getPackageManager()
                            .getApplicationInfo("com.samsung.android.app.sharelive", 128)
                            .metaData;
            if (bundle != null) {
                return bundle.getBoolean("com.samsung.android.app.sharelive.supportChinaP2p", false)
                        ? 0
                        : 3;
            }
            return 3;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d(TAG, "PackageManager could not find QuickShare");
            return 3;
        }
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
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!Objects.equals(preference.getKey(), KEY_QUICK_SHARE_CHINA)) {
            return false;
        }
        this.mContext.startActivity(
                Settings.System.getInt(
                                        this.mContext.getContentResolver(),
                                        KEY_QUICK_SHARE_SETTING_VALUE,
                                        1)
                                == 1
                        ? new Intent("android.intent.action.MAIN")
                                .setClassName("com.samsung.android.app.sharelive", LAUNCH_ACTIVITY)
                        : new Intent(INTENT_ACTION_ACTIVATION_DIALOG)
                                .setClassName(
                                        "com.samsung.android.app.sharelive",
                                        LAUNCH_ACTIVATION_DIALOG)
                                .putExtra(INTENT_EXTRA_LAUNCH_FROM, 1));
        return false;
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
                        this.mContext.getContentResolver(), KEY_QUICK_SHARE_SETTING_VALUE, 1)
                == 1;
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (!setQuickShareSettingValue(z ? 1 : 0)) {
            return true;
        }
        sendBroadcastChangedValue(z ? 1 : 0);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
