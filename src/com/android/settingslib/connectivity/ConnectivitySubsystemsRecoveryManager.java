package com.android.settingslib.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.settings.network.InternetResetHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ConnectivitySubsystemsRecoveryManager {
    public final Context mContext;
    public InternetResetHelper.RecoveryWorker mCurrentRecoveryCallback;
    public final Handler mHandler;
    public final MobileTelephonyCallback mTelephonyCallback;
    public final TelephonyManager mTelephonyManager;
    public boolean mTelephonyRestartInProgress;
    public final WifiManager mWifiManager;
    public boolean mWifiRestartInProgress;
    public final AnonymousClass2 mWifiSubsystemRestartTrackingCallback;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MobileTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.RadioPowerStateListener {
        public MobileTelephonyCallback() {}

        public final void onRadioPowerStateChanged(int i) {
            ConnectivitySubsystemsRecoveryManager connectivitySubsystemsRecoveryManager =
                    ConnectivitySubsystemsRecoveryManager.this;
            if (!connectivitySubsystemsRecoveryManager.mTelephonyRestartInProgress
                    || connectivitySubsystemsRecoveryManager.mCurrentRecoveryCallback == null) {
                connectivitySubsystemsRecoveryManager.stopTrackingTelephonyRestart();
            }
            if (i == 1) {
                ConnectivitySubsystemsRecoveryManager connectivitySubsystemsRecoveryManager2 =
                        ConnectivitySubsystemsRecoveryManager.this;
                connectivitySubsystemsRecoveryManager2.mTelephonyRestartInProgress = false;
                connectivitySubsystemsRecoveryManager2.stopTrackingTelephonyRestart();
                ConnectivitySubsystemsRecoveryManager connectivitySubsystemsRecoveryManager3 =
                        ConnectivitySubsystemsRecoveryManager.this;
                if (connectivitySubsystemsRecoveryManager3.mWifiRestartInProgress
                        || connectivitySubsystemsRecoveryManager3.mTelephonyRestartInProgress
                        || connectivitySubsystemsRecoveryManager3.mCurrentRecoveryCallback
                                == null) {
                    return;
                }
                Log.d("RecoveryWorker", "The connectivity subsystem is done for recovery.");
                InternetResetHelper.RecoveryWorker.sIsRecovering = false;
                InternetResetHelper internetResetHelper =
                        (InternetResetHelper) InternetResetHelper.RecoveryWorker.sCallback.get();
                if (internetResetHelper != null) {
                    internetResetHelper.resumePreferences();
                }
                connectivitySubsystemsRecoveryManager3.mCurrentRecoveryCallback = null;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settingslib.connectivity.ConnectivitySubsystemsRecoveryManager$2] */
    public ConnectivitySubsystemsRecoveryManager(Context context, Handler handler) {
        this.mWifiManager = null;
        this.mTelephonyManager = null;
        new BroadcastReceiver() { // from class:
                                  // com.android.settingslib.connectivity.ConnectivitySubsystemsRecoveryManager.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                ConnectivitySubsystemsRecoveryManager.this.getClass();
            }
        };
        this.mWifiRestartInProgress = false;
        this.mTelephonyRestartInProgress = false;
        this.mCurrentRecoveryCallback = null;
        this.mWifiSubsystemRestartTrackingCallback =
                new WifiManager
                        .SubsystemRestartTrackingCallback() { // from class:
                                                              // com.android.settingslib.connectivity.ConnectivitySubsystemsRecoveryManager.2
                    @Override // android.net.wifi.WifiManager.SubsystemRestartTrackingCallback
                    public final void onSubsystemRestarted() {
                        ConnectivitySubsystemsRecoveryManager
                                connectivitySubsystemsRecoveryManager =
                                        ConnectivitySubsystemsRecoveryManager.this;
                        connectivitySubsystemsRecoveryManager.mWifiRestartInProgress = false;
                        connectivitySubsystemsRecoveryManager.stopTrackingWifiRestart();
                        ConnectivitySubsystemsRecoveryManager
                                connectivitySubsystemsRecoveryManager2 =
                                        ConnectivitySubsystemsRecoveryManager.this;
                        if (connectivitySubsystemsRecoveryManager2.mWifiRestartInProgress
                                || connectivitySubsystemsRecoveryManager2
                                        .mTelephonyRestartInProgress
                                || connectivitySubsystemsRecoveryManager2.mCurrentRecoveryCallback
                                        == null) {
                            return;
                        }
                        Log.d("RecoveryWorker", "The connectivity subsystem is done for recovery.");
                        InternetResetHelper.RecoveryWorker.sIsRecovering = false;
                        InternetResetHelper internetResetHelper =
                                (InternetResetHelper)
                                        InternetResetHelper.RecoveryWorker.sCallback.get();
                        if (internetResetHelper != null) {
                            internetResetHelper.resumePreferences();
                        }
                        connectivitySubsystemsRecoveryManager2.mCurrentRecoveryCallback = null;
                    }

                    @Override // android.net.wifi.WifiManager.SubsystemRestartTrackingCallback
                    public final void onSubsystemRestarting() {}
                };
        this.mTelephonyCallback = new MobileTelephonyCallback();
        this.mContext = context;
        this.mHandler = new Handler(handler.getLooper());
        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(WifiManager.class);
            this.mWifiManager = wifiManager;
            if (wifiManager == null) {
                Log.e("ConnectivitySubsystemsRecoveryManager", "WifiManager not available!?");
            }
        }
        if (context.getPackageManager().hasSystemFeature("android.hardware.telephony")) {
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getSystemService(TelephonyManager.class);
            this.mTelephonyManager = telephonyManager;
            if (telephonyManager == null) {
                Log.e("ConnectivitySubsystemsRecoveryManager", "TelephonyManager not available!?");
            }
        }
    }

    public void startTrackingTelephonyRestart() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager == null) {
            return;
        }
        telephonyManager.registerTelephonyCallback(
                new HandlerExecutor(this.mHandler), this.mTelephonyCallback);
    }

    public void startTrackingWifiRestart() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager == null) {
            return;
        }
        wifiManager.registerSubsystemRestartTrackingCallback(
                new HandlerExecutor(this.mHandler), this.mWifiSubsystemRestartTrackingCallback);
    }

    public void stopTrackingTelephonyRestart() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager == null) {
            return;
        }
        telephonyManager.unregisterTelephonyCallback(this.mTelephonyCallback);
    }

    public void stopTrackingWifiRestart() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager == null) {
            return;
        }
        wifiManager.unregisterSubsystemRestartTrackingCallback(
                this.mWifiSubsystemRestartTrackingCallback);
    }
}
