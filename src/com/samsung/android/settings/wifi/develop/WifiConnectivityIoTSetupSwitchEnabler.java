package com.samsung.android.settings.wifi.develop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import com.android.settings.SettingsPreferenceFragment;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.util.SemLog;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiConnectivityIoTSetupSwitchEnabler implements LifecycleEventObserver {
    public static final IntentFilter INTENT_FILTER;
    public AnonymousClass2 checkConnectionFailure;
    public Handler connectionStatusHandler;
    public String lastConnectedBSSID = null;
    public final AnonymousClass1 mBroadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.android.settings.wifi.develop.WifiConnectivityIoTSetupSwitchEnabler.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            IntentFilter intentFilter = WifiConnectivityIoTSetupSwitchEnabler.INTENT_FILTER;
            SemLog.d("WifiConnectivityIoTSetupSwitchEnabler", "Received broadcast action " + action);
            if (isInitialStickyBroadcast()) {
                return;
            }
            if (action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                int intExtra = intent.getIntExtra("wifi_state", 4);
                if (intExtra == 1 || intExtra == 3) {
                    WifiConnectivityIoTSetupSwitchEnabler.this.updateSwitchState(false);
                    return;
                }
                return;
            }
            if (action.equals("android.net.wifi.STATE_CHANGE")) {
                WifiConnectivityIoTSetupSwitchEnabler.this.updateSwitchState(false);
            } else if (action.equals("android.net.wifi.SCAN_RESULTS")) {
                if (intent.getBooleanExtra("resultsUpdated", true)) {
                    WifiConnectivityIoTSetupSwitchEnabler wifiConnectivityIoTSetupSwitchEnabler = WifiConnectivityIoTSetupSwitchEnabler.this;
                    List<ScanResult> scanResults = wifiConnectivityIoTSetupSwitchEnabler.mWifiManager.getScanResults();
                    if (scanResults != null) {
                        wifiConnectivityIoTSetupSwitchEnabler.getFilteredScanResult(scanResults);
                    }
                }
                WifiConnectivityIoTSetupSwitchEnabler.this.updateSwitchState(false);
            }
        }
    };
    public final FragmentActivity mContext;
    public OnStateChangeListener mOnStateChangeListener;
    public final SemWifiManager mSemWifiManager;
    public final WifiManager mWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnStateChangeListener {
        void onStateChanged(int i);
    }

    static {
        IntentFilter intentFilter = new IntentFilter();
        INTENT_FILTER = intentFilter;
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.wifi.develop.WifiConnectivityIoTSetupSwitchEnabler$1] */
    public WifiConnectivityIoTSetupSwitchEnabler(SettingsPreferenceFragment settingsPreferenceFragment) {
        FragmentActivity activity = settingsPreferenceFragment.getActivity();
        this.mContext = activity;
        settingsPreferenceFragment.getSettingsLifecycle().addObserver(this);
        this.mSemWifiManager = (SemWifiManager) activity.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mWifiManager = (WifiManager) activity.getSystemService(ImsProfile.PDN_WIFI);
    }

    public static String getFrequencyBand(int i) {
        return (i < 2412 || i > 2472) ? (i < 5180 || i > 5825) ? (i < 5955 || i > 7115) ? "Unknown" : "6 GHz" : "5 GHz" : "2.4 GHz";
    }

    public final ScanResult getFilteredScanResult(List list) {
        String str;
        int i;
        int i2;
        Iterator it = list.iterator();
        int i3 = -200;
        ScanResult scanResult = null;
        while (it.hasNext()) {
            ScanResult scanResult2 = (ScanResult) it.next();
            WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
            String str2 = ApnSettings.MVNO_NONE;
            if (connectionInfo != null) {
                String replaceAll = connectionInfo.getSSID().replaceAll("\"", ApnSettings.MVNO_NONE);
                str2 = connectionInfo.getBSSID();
                str = replaceAll;
            } else {
                str = ApnSettings.MVNO_NONE;
            }
            if (str2 != null && scanResult2.SSID.equals(str) && !scanResult2.BSSID.equals(str2) && (i = scanResult2.frequency) >= 2412 && i <= 2472 && (i2 = scanResult2.level) > i3) {
                scanResult = scanResult2;
                i3 = i2;
            }
        }
        return scanResult;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Lifecycle.Event event2 = Lifecycle.Event.ON_START;
        AnonymousClass1 anonymousClass1 = this.mBroadcastReceiver;
        if (event == event2) {
            this.mContext.registerReceiver(anonymousClass1, INTENT_FILTER, 2);
        } else if (event == Lifecycle.Event.ON_STOP) {
            this.mContext.unregisterReceiver(anonymousClass1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.samsung.android.settings.wifi.develop.WifiConnectivityIoTSetupSwitchEnabler$2, java.lang.Runnable] */
    public final void setChecked(boolean z) {
        boolean z2;
        ScanResult filteredScanResult;
        SemLog.d("WifiConnectivityIoTSetupSwitchEnabler", "setChecked: " + z);
        Settings.Secure.putInt(this.mContext.getContentResolver(), "sec_wifi_iot_setup_enabled", z ? 1 : 0);
        SemLog.d("WifiConnectivityIoTSetupSwitchEnabler", " executeConnectionto24Ghzbssid() ");
        if (this.mSemWifiManager.isNCHOModeEnabled()) {
            z2 = true;
        } else {
            z2 = this.mSemWifiManager.setNCHOModeEnabled(true);
            SemLog.d("WifiConnectivityIoTSetupSwitchEnabler", "nchostatus:" + z2);
        }
        if (z2) {
            if (this.mSemWifiManager.setRoamBand(1)) {
                List<ScanResult> scanResults = this.mWifiManager.getScanResults();
                if (scanResults != null && (filteredScanResult = getFilteredScanResult(scanResults)) != null) {
                    if (!this.mSemWifiManager.sendReassociationFrequencyRequestFrame(filteredScanResult.BSSID, filteredScanResult.frequency)) {
                        SemLog.d("WifiConnectivityIoTSetupSwitchEnabler", " sendReassociationFrequencyRequestFrame failed");
                    }
                }
            } else {
                SemLog.d("WifiConnectivityIoTSetupSwitchEnabler", "setRoamBand failed");
            }
            this.mSemWifiManager.setNCHOModeEnabled(false);
            SemLog.d("WifiConnectivityIoTSetupSwitchEnabler", "Resetting to 0 in setChecked connectionFailed case");
            Settings.Secure.putInt(this.mContext.getContentResolver(), "sec_wifi_iot_setup_enabled", 0);
            this.mOnStateChangeListener.onStateChanged(4);
            return;
        }
        if (z) {
            ?? r0 = new Runnable() { // from class: com.samsung.android.settings.wifi.develop.WifiConnectivityIoTSetupSwitchEnabler.2
                @Override // java.lang.Runnable
                public final void run() {
                    WifiInfo connectionInfo = WifiConnectivityIoTSetupSwitchEnabler.this.mWifiManager.getConnectionInfo();
                    int frequency = connectionInfo != null ? connectionInfo.getFrequency() : 0;
                    WifiConnectivityIoTSetupSwitchEnabler.this.getClass();
                    if (TextUtils.equals(WifiConnectivityIoTSetupSwitchEnabler.getFrequencyBand(frequency), "2.4 GHz")) {
                        if (connectionInfo != null) {
                            WifiConnectivityIoTSetupSwitchEnabler.this.lastConnectedBSSID = connectionInfo.getBSSID();
                        }
                        WifiConnectivityIoTSetupSwitchEnabler.this.mOnStateChangeListener.onStateChanged(7);
                        SemLog.d("WifiConnectivityIoTSetupSwitchEnabler", "Successfully connected to 2.4G");
                        return;
                    }
                    Settings.Secure.putInt(WifiConnectivityIoTSetupSwitchEnabler.this.mContext.getContentResolver(), "sec_wifi_iot_setup_enabled", 0);
                    WifiConnectivityIoTSetupSwitchEnabler.this.mOnStateChangeListener.onStateChanged(4);
                    Toast.makeText(WifiConnectivityIoTSetupSwitchEnabler.this.mContext, "Failed to connect to 2.4 GHz", 0).show();
                    WifiConnectivityIoTSetupSwitchEnabler.this.lastConnectedBSSID = null;
                }
            };
            this.checkConnectionFailure = r0;
            Handler handler = this.connectionStatusHandler;
            if (handler == 0) {
                this.connectionStatusHandler = new Handler();
            } else {
                handler.removeCallbacks(r0);
            }
            this.connectionStatusHandler.postDelayed(this.checkConnectionFailure, 3000L);
        }
        updateSwitchState(z);
    }

    public final void updateSwitchState(boolean z) {
        String str;
        String str2;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        if (connectionInfo != null) {
            z2 = this.mWifiManager.isWifiEnabled();
            str = connectionInfo.getBSSID();
            z3 = str != null;
            z4 = !connectionInfo.getAssociatedMloLinks().isEmpty();
            List<ScanResult> scanResults = this.mWifiManager.getScanResults();
            z5 = (scanResults == null || getFilteredScanResult(scanResults) == null) ? false : true;
            str2 = getFrequencyBand(connectionInfo.getFrequency());
        } else {
            str = null;
            str2 = ApnSettings.MVNO_NONE;
            z2 = false;
            z3 = false;
            z4 = false;
            z5 = false;
        }
        SemLog.i("WifiConnectivityIoTSetupSwitchEnabler", "updateSwitchState() - isWifiEnabled: " + z2 + ",  isConnected: " + z3);
        if (!z2) {
            Settings.Secure.putInt(this.mContext.getContentResolver(), "sec_wifi_iot_setup_enabled", 0);
            this.mOnStateChangeListener.onStateChanged(2);
            return;
        }
        if (!z3) {
            Settings.Secure.putInt(this.mContext.getContentResolver(), "sec_wifi_iot_setup_enabled", 0);
            this.mOnStateChangeListener.onStateChanged(1);
            return;
        }
        if (z4) {
            this.mOnStateChangeListener.onStateChanged(5);
            return;
        }
        if (!z) {
            if (!TextUtils.equals(str2, "2.4 GHz")) {
                Settings.Secure.putInt(this.mContext.getContentResolver(), "sec_wifi_iot_setup_enabled", 0);
                if (z5) {
                    this.mOnStateChangeListener.onStateChanged(6);
                    return;
                } else {
                    this.mOnStateChangeListener.onStateChanged(3);
                    return;
                }
            }
            if (TextUtils.equals(str2, "2.4 GHz")) {
                SemLog.d("WifiConnectivityIoTSetupSwitchEnabler", "Last connected bssid " + this.lastConnectedBSSID + " current connected " + str);
                if (!TextUtils.isEmpty(this.lastConnectedBSSID) && !TextUtils.equals(this.lastConnectedBSSID, str)) {
                    Settings.Secure.putInt(this.mContext.getContentResolver(), "sec_wifi_iot_setup_enabled", 0);
                }
                this.mOnStateChangeListener.onStateChanged(8);
                return;
            }
        }
        if (z5) {
            this.mOnStateChangeListener.onStateChanged(-1);
        } else {
            this.mOnStateChangeListener.onStateChanged(3);
        }
    }
}
