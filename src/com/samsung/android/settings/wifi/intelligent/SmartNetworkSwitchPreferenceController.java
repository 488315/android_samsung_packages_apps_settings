package com.samsung.android.settings.wifi.intelligent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.wifi.WifiManager;
import android.os.Debug;
import android.os.Handler;
import android.os.SystemProperties;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SmartNetworkSwitchPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final String CscFeature_Wifi_ConfigAutoWifiNaming =
            "CscFeature_Wifi_ConfigAutoWifiNaming";
    private static final String KEY_POOR_NETWORK_DETECTION = "wifi_poor_network_detection";
    private static final String TAG = "ConfigureWifiSettings.SNS";
    private ContentObserver mDataRoamingObserver;
    private IntentFilter mFilter;
    private ContentObserver mMobileDataObserver;
    private SwitchPreferenceCompat mPoorNetworkDetection;
    private final BroadcastReceiver mReceiver;
    private final WifiManager mWifiManager;
    private static final boolean DBG = Debug.semIsProductDev();
    private static final boolean mIsSupportAdaptiveWifi =
            SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_WLAN_SUPPORT_ADAPTIVE_WIFI");

    public SmartNetworkSwitchPreferenceController(Context context, String str) {
        super(context, str);
        final int i = 0;
        this.mMobileDataObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchPreferenceController.1
                    public final /* synthetic */ SmartNetworkSwitchPreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i) {
                            case 0:
                                SmartNetworkSwitchPreferenceController
                                        smartNetworkSwitchPreferenceController = this.this$0;
                                smartNetworkSwitchPreferenceController.updateState(
                                        smartNetworkSwitchPreferenceController
                                                .mPoorNetworkDetection);
                                break;
                            default:
                                SmartNetworkSwitchPreferenceController
                                        smartNetworkSwitchPreferenceController2 = this.this$0;
                                smartNetworkSwitchPreferenceController2.updateState(
                                        smartNetworkSwitchPreferenceController2
                                                .mPoorNetworkDetection);
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mDataRoamingObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchPreferenceController.1
                    public final /* synthetic */ SmartNetworkSwitchPreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i2) {
                            case 0:
                                SmartNetworkSwitchPreferenceController
                                        smartNetworkSwitchPreferenceController = this.this$0;
                                smartNetworkSwitchPreferenceController.updateState(
                                        smartNetworkSwitchPreferenceController
                                                .mPoorNetworkDetection);
                                break;
                            default:
                                SmartNetworkSwitchPreferenceController
                                        smartNetworkSwitchPreferenceController2 = this.this$0;
                                smartNetworkSwitchPreferenceController2.updateState(
                                        smartNetworkSwitchPreferenceController2
                                                .mPoorNetworkDetection);
                                break;
                        }
                    }
                };
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchPreferenceController.3
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        String action = intent.getAction();
                        if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                            int intExtra = intent.getIntExtra("wifi_state", 4);
                            if (intExtra == 3 || intExtra == 1) {
                                SmartNetworkSwitchPreferenceController
                                        smartNetworkSwitchPreferenceController =
                                                SmartNetworkSwitchPreferenceController.this;
                                smartNetworkSwitchPreferenceController.updateState(
                                        smartNetworkSwitchPreferenceController
                                                .mPoorNetworkDetection);
                                return;
                            }
                            return;
                        }
                        if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                            SmartNetworkSwitchPreferenceController
                                    smartNetworkSwitchPreferenceController2 =
                                            SmartNetworkSwitchPreferenceController.this;
                            smartNetworkSwitchPreferenceController2.updateState(
                                    smartNetworkSwitchPreferenceController2.mPoorNetworkDetection);
                        } else if (action.equals("android.intent.action.SIM_STATE_CHANGED")
                                || action.equals("android.intent.action.ANY_DATA_STATE")) {
                            SmartNetworkSwitchPreferenceController
                                    smartNetworkSwitchPreferenceController3 =
                                            SmartNetworkSwitchPreferenceController.this;
                            smartNetworkSwitchPreferenceController3.updateState(
                                    smartNetworkSwitchPreferenceController3.mPoorNetworkDetection);
                        }
                    }
                };
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        SwitchPreferenceCompat switchPreferenceCompat;
        super.displayPreference(preferenceScreen);
        SwitchPreferenceCompat switchPreferenceCompat2 =
                (SwitchPreferenceCompat) preferenceScreen.findPreference(getPreferenceKey());
        this.mPoorNetworkDetection = switchPreferenceCompat2;
        updateState(switchPreferenceCompat2);
        boolean z = SystemProperties.getBoolean("ro.radio.noril", false);
        if ((Utils.locateSmartNetworkSwitch(this.mContext) == 3 || z)
                && (switchPreferenceCompat = this.mPoorNetworkDetection) != null) {
            preferenceScreen.removePreference(switchPreferenceCompat);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean z = SystemProperties.getBoolean("ro.radio.noril", false);
        if (Utils.locateSmartNetworkSwitch(this.mContext) == 3 || z) {
            return 3;
        }
        if (!this.mWifiManager.isWifiEnabled()) {
            return 5;
        }
        int updateSmartNetworkSwitchAvailability =
                Utils.updateSmartNetworkSwitchAvailability(this.mContext);
        return (updateSmartNetworkSwitchAvailability == 2
                        || updateSmartNetworkSwitchAvailability == 3
                        || updateSmartNetworkSwitchAvailability == 4
                        || updateSmartNetworkSwitchAvailability == 5)
                ? 5
                : 0;
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
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        Resources resources = this.mContext.getResources();
        int updateSmartNetworkSwitchAvailability =
                Utils.updateSmartNetworkSwitchAvailability(this.mContext);
        if (updateSmartNetworkSwitchAvailability != 1) {
            if (updateSmartNetworkSwitchAvailability == 2) {
                return resources.getString(
                        R.string.wifi_watchdog_connectivity_check_disabled_no_sim);
            }
            if (updateSmartNetworkSwitchAvailability == 3) {
                return resources.getString(
                        R.string.wifi_watchdog_connectivity_check_disabled_airplane_mode);
            }
            if (updateSmartNetworkSwitchAvailability == 4) {
                return resources.getString(
                        R.string.wifi_watchdog_connectivity_check_disabled_mobile_data_off);
            }
            if (updateSmartNetworkSwitchAvailability == 5) {
                return resources.getString(
                        R.string.wifi_watchdog_connectivity_check_disabled_by_data_roaming);
            }
        } else if (this.mWifiManager.isWifiEnabled()) {
            Settings.Global.getInt(
                    this.mContext.getContentResolver(),
                    "wifi_watchdog_poor_network_test_enabled",
                    0);
            return resources.getString(R.string.wifi_use_mobile_data);
        }
        return super.getSummary();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (this.mPoorNetworkDetection != null
                && KEY_POOR_NETWORK_DETECTION.equals(preference.getKey())) {
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mSourceMetricsCategory = FileType.XLSB;
            launchRequest.mDestinationName = SmartNetworkSwitchSettings.class.getCanonicalName();
            subSettingLauncher.setTitleRes(R.string.wifi_switch_to_mobile_data, null);
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
        return Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "wifi_watchdog_poor_network_test_enabled",
                        0)
                == 1;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mMobileDataObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mDataRoamingObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        this.mFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        this.mFilter.addAction("android.intent.action.ANY_DATA_STATE");
        this.mFilter.addAction("android.intent.action.AIRPLANE_MODE");
        this.mContext.registerReceiver(
                this.mReceiver, this.mFilter, "android.permission.CHANGE_NETWORK_STATE", null);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("mobile_data"), false, this.mMobileDataObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("data_roaming"),
                        false,
                        this.mDataRoamingObserver);
        updateState(this.mPoorNetworkDetection);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        int i =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "wifi_num_of_switch_to_mobile_data_toggle",
                        -1);
        if (i == -1) {
            i = 0;
        }
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "wifi_num_of_switch_to_mobile_data_toggle",
                i + 1);
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                "wifi_watchdog_poor_network_test_enabled",
                z ? 1 : 0);
        SALogging.insertSALog(z ? 1L : 0L, "WIFI_200", "1210");
        refreshSummary(this.mPoorNetworkDetection);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (!this.mWifiManager.isWifiEnabled()) {
            preference.setEnabled(false);
        }
        int updateSmartNetworkSwitchAvailability =
                Utils.updateSmartNetworkSwitchAvailability(this.mContext);
        if (updateSmartNetworkSwitchAvailability != 1) {
            if (updateSmartNetworkSwitchAvailability == 2) {
                preference.setSummary(R.string.wifi_watchdog_connectivity_check_disabled_no_sim);
                preference.setEnabled(false);
            } else if (updateSmartNetworkSwitchAvailability == 3) {
                preference.setSummary(
                        R.string.wifi_watchdog_connectivity_check_disabled_airplane_mode);
                preference.setEnabled(false);
            } else if (updateSmartNetworkSwitchAvailability == 4) {
                preference.setSummary(
                        R.string.wifi_watchdog_connectivity_check_disabled_mobile_data_off);
                preference.setEnabled(false);
            } else if (updateSmartNetworkSwitchAvailability == 5) {
                preference.setSummary(
                        R.string.wifi_watchdog_connectivity_check_disabled_by_data_roaming);
                preference.setEnabled(false);
            }
        } else if (this.mWifiManager.isWifiEnabled()) {
            preference.setEnabled(true);
            Settings.Global.getInt(
                    this.mContext.getContentResolver(),
                    "wifi_watchdog_poor_network_test_enabled",
                    0);
            preference.setSummary(R.string.wifi_use_mobile_data);
        }
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
