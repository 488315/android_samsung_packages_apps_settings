package com.samsung.android.settings.wifi.mobileap.otp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApSettings;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApOtpSwitchController extends TogglePreferenceController
        implements LifecycleEventObserver {
    private static String TAG = "WifiApOtpSwitchController";
    private boolean mSwitchStateChangedAfterApEnabled;
    private SecSwitchPreferenceScreen mThisSwitchPreference;
    private WifiApSettings mWifiApSettings;
    private WifiApStateChangeReceiver mWifiApStateChangeReceiver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiApStateChangeReceiver extends BroadcastReceiver {
        public WifiApStateChangeReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction()
                    .equals(
                            WifiApMobileDataSharedTodayPreferenceController
                                    .ACTION_WIFI_AP_STATE_CHANGED)) {
                int intExtra = intent.getIntExtra("wifi_state", 14);
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        intExtra, "onReceive:  Wi-Fi AP State: ", WifiApOtpSwitchController.TAG);
                if (WifiApOtpSwitchController.this.mSwitchStateChangedAfterApEnabled) {
                    if (intExtra == 13) {
                        WifiApOtpSwitchController.this.mSwitchStateChangedAfterApEnabled = false;
                        WifiApOtpSwitchController.this.mThisSwitchPreference.setEnabled(true);
                        return;
                    }
                    return;
                }
                if (intExtra != 13 && intExtra != 11) {
                    WifiApOtpSwitchController.this.mThisSwitchPreference.setChecked(
                            WifiApFrameworkUtils.isOtpPasswordEnabled(
                                    ((AbstractPreferenceController) WifiApOtpSwitchController.this)
                                            .mContext));
                } else {
                    WifiApOtpSwitchController.this.mThisSwitchPreference.setEnabled(true);
                    WifiApOtpSwitchController.this.mThisSwitchPreference.setChecked(
                            WifiApFrameworkUtils.isOtpPasswordEnabled(
                                    ((AbstractPreferenceController) WifiApOtpSwitchController.this)
                                            .mContext));
                }
            }
        }
    }

    public WifiApOtpSwitchController(Context context, String str) {
        super(context, str);
        this.mSwitchStateChangedAfterApEnabled = false;
        this.mWifiApStateChangeReceiver = new WifiApStateChangeReceiver();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mThisSwitchPreference =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return WifiApFeatureUtils.isOneTimePasswordSupported(this.mContext) ? 0 : 3;
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
        if (TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            Log.i(TAG, "Preference screen clicked");
            SALogging.insertSALog("TETH_010", "8071");
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
            int metricsCategory = getMetricsCategory();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mSourceMetricsCategory = metricsCategory;
            launchRequest.mDestinationName = WifiApOtpSettings.class.getCanonicalName();
            subSettingLauncher.launch();
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
        return this.mThisSwitchPreference.mChecked;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_RESUME) {
            Log.i(TAG, "onStateChanged:Resume ");
            this.mContext.registerReceiver(
                    this.mWifiApStateChangeReceiver,
                    new IntentFilter(
                            WifiApMobileDataSharedTodayPreferenceController
                                    .ACTION_WIFI_AP_STATE_CHANGED),
                    2);
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            Log.i(TAG, "onStateChanged:Pause");
            try {
                this.mContext.unregisterReceiver(this.mWifiApStateChangeReceiver);
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("setChecked: ", TAG, z);
        SALogging.insertSALog(z ? 1L : 0L, "TETH_010", "8072", (String) null);
        WifiApFrameworkUtils.setOtpPasswordEnabled(this.mContext, z);
        if (WifiApFrameworkUtils.isWifiApStateEnabled(this.mContext)) {
            Log.i(TAG, "setChecked, AP enabled");
            Context context = this.mContext;
            Log.i("WifiApFrameworkUtils", "setOtpPassword: ");
            WifiApFrameworkUtils.getSemWifiManager(context).setWifiApGuestPassword("abcdefgh");
            this.mThisSwitchPreference.setEnabled(false);
            this.mSwitchStateChangedAfterApEnabled = true;
        }
        WifiApFrameworkUtils.resetSoftAp(this.mContext);
        return true;
    }

    public void setHost(WifiApSettings wifiApSettings) {
        this.mWifiApSettings = wifiApSettings;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mThisSwitchPreference.setVisible(
                WifiApFeatureUtils.isOneTimePasswordSupported(this.mContext)
                        && WifiApSoftApUtils.getSecurityType(this.mContext) == 1);
        this.mThisSwitchPreference.setChecked(
                WifiApFrameworkUtils.isOtpPasswordEnabled(this.mContext));
        int wifiApState = WifiApFrameworkUtils.getSemWifiManager(this.mContext).getWifiApState();
        MainClearConfirm$$ExternalSyntheticOutline0.m(wifiApState, "state: ", TAG);
        if (wifiApState == 13 || wifiApState == 11) {
            this.mThisSwitchPreference.setEnabled(true);
        } else {
            if (this.mWifiApSettings.mSwitchEnabler.isOperatorRequiresProvisioning()) {
                return;
            }
            this.mThisSwitchPreference.setEnabled(false);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
