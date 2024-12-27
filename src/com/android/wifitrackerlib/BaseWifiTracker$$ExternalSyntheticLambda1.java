package com.android.wifitrackerlib;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.core.os.BuildCompat;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;

import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.WifiQoSScoredCache;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BaseWifiTracker$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BaseWifiTracker f$0;

    public /* synthetic */ BaseWifiTracker$$ExternalSyntheticLambda1(
            BaseWifiTracker baseWifiTracker, int i) {
        this.$r8$classId = i;
        this.f$0 = baseWifiTracker;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BaseWifiTracker.AnonymousClass7 anonymousClass7;
        boolean isSupportedQoSProvider;
        boolean z = false;
        int i = this.$r8$classId;
        BaseWifiTracker baseWifiTracker = this.f$0;
        switch (i) {
            case 0:
                baseWifiTracker.unregisterReceiverAndCallback();
                baseWifiTracker.mIsInitialized = false;
                SemWifiEntryFlags.isWifiDeveloperOptionOn = -1;
                SemWifiEntryFlags.isShowBandSummaryOn = -1;
                break;
            default:
                if (baseWifiTracker.mIsInitialized) {
                    Log.v(baseWifiTracker.mTag, "already been initialized");
                } else {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
                    intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
                    intentFilter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
                    intentFilter.addAction("android.net.wifi.STATE_CHANGE");
                    if (BaseWifiTracker.sVerboseLogging) {
                        intentFilter.addAction("android.net.wifi.RSSI_CHANGED");
                    }
                    intentFilter.addAction(
                            "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
                    intentFilter.setPriority(1000);
                    Context context = baseWifiTracker.mContext;
                    BaseWifiTracker.AnonymousClass1 anonymousClass1 =
                            baseWifiTracker.mBroadcastReceiver;
                    Handler handler = baseWifiTracker.mWorkerHandler;
                    context.registerReceiver(anonymousClass1, intentFilter, null, handler);
                    baseWifiTracker.mConnectivityManager.registerNetworkCallback(
                            baseWifiTracker.mNetworkRequest,
                            baseWifiTracker.mNetworkCallback,
                            handler);
                    baseWifiTracker.mConnectivityManager.registerDefaultNetworkCallback(
                            baseWifiTracker.mDefaultNetworkCallback, handler);
                    baseWifiTracker.mConnectivityDiagnosticsManager
                            .registerConnectivityDiagnosticsCallback(
                                    baseWifiTracker.mNetworkRequest,
                                    baseWifiTracker.mConnectivityDiagnosticsExecutor,
                                    baseWifiTracker.mConnectivityDiagnosticsCallback);
                    SharedConnectivityManager sharedConnectivityManager =
                            baseWifiTracker.mSharedConnectivityManager;
                    if (sharedConnectivityManager != null
                            && (anonymousClass7 = baseWifiTracker.mSharedConnectivityCallback)
                                    != null) {
                        int i2 = BuildCompat.$r8$clinit;
                        sharedConnectivityManager.registerCallback(
                                baseWifiTracker.mSharedConnectivityExecutor, anonymousClass7);
                    }
                }
                WifiQoSScoredCache wifiQoSScoredCache = baseWifiTracker.mQoSScoredCache;
                boolean z2 =
                        Settings.Global.getInt(
                                        wifiQoSScoredCache.mContext.getContentResolver(),
                                        "sem_wifi_network_rating_scorer_enabled_labs",
                                        0)
                                == 1;
                SemWifiManager semWifiManager = wifiQoSScoredCache.mSemWifiManager;
                if (semWifiManager == null) {
                    Log.i("WifiTracker.WifiWifiQoSScoreCache", "SemWifiManager: null");
                    isSupportedQoSProvider = false;
                } else {
                    isSupportedQoSProvider = semWifiManager.isSupportedQoSProvider();
                }
                if (isSupportedQoSProvider
                        && !SemWifiUtils.isEnabledUltraPowerSaving(wifiQoSScoredCache.mContext)
                        && z2) {
                    z = true;
                }
                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                        "Network Score Enabling Check ", "WifiTracker.WifiWifiQoSScoreCache", z);
                baseWifiTracker.mNetworkScoringUiEnabled = z;
                baseWifiTracker.handleOnStart();
                baseWifiTracker.mIsInitialized = true;
                break;
        }
    }
}
