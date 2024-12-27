package com.samsung.android.settings.wifi.develop;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.util.SemLog;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiConnectivityIoTSetupController extends TogglePreferenceController {
    private static final String TAG = "WifiConnectivityIoTSetupController";
    private Context mContext;
    private final WifiConnectivityIoTSetupSwitchEnabler.OnStateChangeListener
            mOnWifiConnectivityIoTSetupSwitchChangeListener;
    private SecSwitchPreferenceScreen mSecSwitchPreferenceScreen;
    private SettingsPreferenceFragment mSettingsPreferenceFragment;
    private WifiConnectivityIoTSetupSwitchEnabler mWifiConnectivityIoTSetupSwitchEnabler;

    public WifiConnectivityIoTSetupController(Context context, String str) {
        super(context, str);
        this.mOnWifiConnectivityIoTSetupSwitchChangeListener =
                new WifiConnectivityIoTSetupSwitchEnabler
                        .OnStateChangeListener() { // from class:
                                                   // com.samsung.android.settings.wifi.develop.WifiConnectivityIoTSetupController.1
                    @Override // com.samsung.android.settings.wifi.develop.WifiConnectivityIoTSetupSwitchEnabler.OnStateChangeListener
                    public final void onStateChanged(int i) {
                        WifiConnectivityIoTSetupController wifiConnectivityIoTSetupController =
                                WifiConnectivityIoTSetupController.this;
                        int i2 =
                                Settings.Secure.getInt(
                                        wifiConnectivityIoTSetupController.mContext
                                                .getContentResolver(),
                                        "sec_wifi_iot_setup_enabled",
                                        0);
                        SemLog.i(
                                WifiConnectivityIoTSetupController.TAG,
                                "switch onStateChanged() - resultCode: "
                                        + i
                                        + ", user_enabled_value: "
                                        + i2);
                        if (i2 == 0) {
                            wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                    .setChecked(false);
                        } else {
                            wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                    .setChecked(true);
                        }
                        switch (i) {
                            case 1:
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setSummary("Wi-Fi is Disconnected");
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setEnabled(false);
                                break;
                            case 2:
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setSummary("Wi-Fi is OFF");
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setEnabled(false);
                                break;
                            case 3:
                                if (i2 == 1) {
                                    wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                            .setSummary("Device is connected to 2.4GHz BSSID");
                                } else {
                                    wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                            .setSummary("No Matching AP for IOT Setup");
                                }
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setEnabled(false);
                                break;
                            case 4:
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setSummary("Connection Failed");
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setEnabled(true);
                                break;
                            case 5:
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setSummary("Connected to Wi-FI7 AP");
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setEnabled(false);
                                break;
                            case 6:
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setEnabled(true);
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setSummary(ApnSettings.MVNO_NONE);
                                break;
                            case 7:
                            default:
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setEnabled(false);
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setSummary(ApnSettings.MVNO_NONE);
                                break;
                            case 8:
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setEnabled(false);
                                wifiConnectivityIoTSetupController.mSecSwitchPreferenceScreen
                                        .setSummary("Device is connected to 2.4GHz BSSID");
                                break;
                        }
                    }
                };
        this.mContext = context;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mSecSwitchPreferenceScreen =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        WifiConnectivityIoTSetupSwitchEnabler wifiConnectivityIoTSetupSwitchEnabler =
                new WifiConnectivityIoTSetupSwitchEnabler(this.mSettingsPreferenceFragment);
        this.mWifiConnectivityIoTSetupSwitchEnabler = wifiConnectivityIoTSetupSwitchEnabler;
        wifiConnectivityIoTSetupSwitchEnabler.mOnStateChangeListener =
                this.mOnWifiConnectivityIoTSetupSwitchChangeListener;
        wifiConnectivityIoTSetupSwitchEnabler.updateSwitchState(false);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        return R.string.menu_key_connections;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
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
        return this.mSecSwitchPreferenceScreen.mChecked;
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        this.mSecSwitchPreferenceScreen.setEnabled(false);
        this.mWifiConnectivityIoTSetupSwitchEnabler.setChecked(z);
        return true;
    }

    public void setHost(SettingsPreferenceFragment settingsPreferenceFragment) {
        this.mSettingsPreferenceFragment = settingsPreferenceFragment;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mWifiConnectivityIoTSetupSwitchEnabler.updateSwitchState(false);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
