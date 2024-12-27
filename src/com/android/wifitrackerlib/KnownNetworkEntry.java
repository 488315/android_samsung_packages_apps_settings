package com.android.wifitrackerlib;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.sharedconnectivity.app.KnownNetwork;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.os.Handler;
import android.text.BidiFormatter;

import com.android.settings.R;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class KnownNetworkEntry extends StandardWifiEntry {
    public final KnownNetwork mKnownNetworkData;
    public final SharedConnectivityManager mSharedConnectivityManager;

    public KnownNetworkEntry(
            WifiTrackerInjector wifiTrackerInjector,
            Handler handler,
            StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey,
            List list,
            WifiManager wifiManager,
            SharedConnectivityManager sharedConnectivityManager,
            KnownNetwork knownNetwork) {
        super(wifiTrackerInjector, handler, standardWifiEntryKey, null, list, wifiManager, false);
        this.mSharedConnectivityManager = sharedConnectivityManager;
        this.mKnownNetworkData = knownNetwork;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized void connect(WifiEntry.ConnectCallback connectCallback) {
        this.mConnectCallback = connectCallback;
        SharedConnectivityManager sharedConnectivityManager = this.mSharedConnectivityManager;
        if (sharedConnectivityManager != null) {
            sharedConnectivityManager.connectKnownNetwork(this.mKnownNetworkData);
        } else {
            if (connectCallback != null) {
                this.mCallbackHandler.post(
                        new KnownNetworkEntry$$ExternalSyntheticLambda0(1, connectCallback));
            }
        }
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean connectionInfoMatches(WifiInfo wifiInfo) {
        if (!wifiInfo.isPasspointAp() && !wifiInfo.isOsuAp()) {
            return Objects.equals(
                    this.mKey.mScanResultKey,
                    new StandardWifiEntry.ScanResultKey(
                            WifiInfo.sanitizeSsid(wifiInfo.getSSID()),
                            Collections.singletonList(
                                    Integer.valueOf(wifiInfo.getCurrentSecurityType()))));
        }
        return false;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSummary(boolean z) {
        return this.mContext.getString(
                R.string.wifitrackerlib_known_network_summary,
                BidiFormatter.getInstance()
                        .unicodeWrap(
                                this.mKnownNetworkData.getNetworkProviderInfo().getDeviceName()));
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isSaved() {
        return false;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isSuggestion() {
        return false;
    }
}
