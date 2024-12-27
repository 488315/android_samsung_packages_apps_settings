package com.android.settings.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkScoreManager;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.widget.SummaryUpdater;
import com.android.settingslib.wifi.WifiStatusTracker;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiSummaryUpdater extends SummaryUpdater {
    public static final IntentFilter INTENT_FILTER;
    public final AnonymousClass1 mReceiver;
    public final WifiStatusTracker mWifiTracker;

    static {
        IntentFilter intentFilter = new IntentFilter();
        INTENT_FILTER = intentFilter;
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.RSSI_CHANGED");
    }

    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.settings.wifi.WifiSummaryUpdater$1] */
    public WifiSummaryUpdater(
            Context context,
            SummaryUpdater.OnSummaryChangeListener onSummaryChangeListener,
            WifiStatusTracker wifiStatusTracker) {
        super(context, onSummaryChangeListener);
        this.mWifiTracker =
                wifiStatusTracker == null
                        ? new WifiStatusTracker(
                                context,
                                (WifiManager) context.getSystemService(WifiManager.class),
                                (NetworkScoreManager)
                                        context.getSystemService(NetworkScoreManager.class),
                                (ConnectivityManager)
                                        context.getSystemService(ConnectivityManager.class),
                                new Runnable() { // from class:
                                                 // com.android.settings.wifi.WifiSummaryUpdater$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        WifiSummaryUpdater.this.notifyChangeIfNeeded();
                                    }
                                })
                        : wifiStatusTracker;
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.wifi.WifiSummaryUpdater.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        WifiStatusTracker wifiStatusTracker2 = WifiSummaryUpdater.this.mWifiTracker;
                        if (wifiStatusTracker2.mWifiManager != null
                                && intent.getAction()
                                        .equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                            wifiStatusTracker2.updateWifiState();
                        }
                        WifiSummaryUpdater.this.notifyChangeIfNeeded();
                    }
                };
    }

    @Override // com.android.settings.widget.SummaryUpdater
    public final String getSummary() {
        WifiStatusTracker wifiStatusTracker = this.mWifiTracker;
        if (!wifiStatusTracker.enabled) {
            return this.mContext.getString(R.string.switch_off_text);
        }
        if (!wifiStatusTracker.connected) {
            return this.mContext.getString(R.string.disconnected);
        }
        String sanitizeSsid = android.net.wifi.WifiInfo.sanitizeSsid(wifiStatusTracker.ssid);
        return TextUtils.isEmpty(wifiStatusTracker.statusLabel)
                ? sanitizeSsid
                : this.mContext
                        .getResources()
                        .getString(
                                R.string.preference_summary_default_combination,
                                sanitizeSsid,
                                wifiStatusTracker.statusLabel);
    }

    public final void register(boolean z) {
        AnonymousClass1 anonymousClass1 = this.mReceiver;
        WifiStatusTracker wifiStatusTracker = this.mWifiTracker;
        if (z) {
            wifiStatusTracker.fetchInitialState();
            notifyChangeIfNeeded();
            this.mContext.registerReceiver(anonymousClass1, INTENT_FILTER, 2);
        } else {
            this.mContext.unregisterReceiver(anonymousClass1);
        }
        wifiStatusTracker.setListening(z);
    }
}
