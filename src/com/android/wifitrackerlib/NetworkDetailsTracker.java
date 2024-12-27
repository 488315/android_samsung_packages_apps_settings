package com.android.wifitrackerlib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityDiagnosticsManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;

import androidx.lifecycle.Lifecycle;

import com.sec.ims.IMSParameter;

import java.time.Clock;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class NetworkDetailsTracker extends BaseWifiTracker {
    public static NetworkDetailsTracker createNetworkDetailsTracker(
            WifiTrackerInjector wifiTrackerInjector,
            Lifecycle lifecycle,
            Context context,
            WifiManager wifiManager,
            ConnectivityManager connectivityManager,
            Handler handler,
            Handler handler2,
            Clock clock,
            long j,
            long j2,
            String str) {
        if (str.startsWith("StandardWifiEntry:")) {
            return new StandardNetworkDetailsTracker(
                    wifiTrackerInjector,
                    lifecycle,
                    context,
                    wifiManager,
                    connectivityManager,
                    handler,
                    handler2,
                    clock,
                    j,
                    j2,
                    str);
        }
        if (str.startsWith("PasspointWifiEntry:")) {
            return new PasspointNetworkDetailsTracker(
                    wifiTrackerInjector,
                    lifecycle,
                    context,
                    wifiManager,
                    connectivityManager,
                    handler,
                    handler2,
                    clock,
                    j,
                    j2,
                    str);
        }
        if (str.startsWith("HotspotNetworkEntry:")) {
            return new HotspotNetworkDetailsTracker(
                    wifiTrackerInjector,
                    lifecycle,
                    context,
                    wifiManager,
                    connectivityManager,
                    handler,
                    handler2,
                    clock,
                    j,
                    j2,
                    str);
        }
        throw new IllegalArgumentException("Key does not contain valid key prefix!");
    }

    public abstract WifiEntry getWifiEntry();

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConnectivityReportAvailable(
            ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {
        getWifiEntry().updateConnectivityReport(connectivityReport);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        getWifiEntry().onDefaultNetworkCapabilitiesChanged(network, networkCapabilities);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultNetworkLost() {
        getWifiEntry().onDefaultNetworkLost();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        WifiEntry wifiEntry = getWifiEntry();
        if (wifiEntry.getConnectedState() == 2) {
            wifiEntry.updateLinkProperties(network, linkProperties);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        getWifiEntry().onNetworkCapabilitiesChanged(network, networkCapabilities);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkLost(Network network) {
        getWifiEntry().onNetworkLost(network);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkStateChangedAction(Intent intent) {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        NetworkInfo networkInfo =
                (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
        if (connectionInfo == null || networkInfo == null) {
            return;
        }
        getWifiEntry().onPrimaryWifiInfoChanged(connectionInfo, networkInfo);
    }
}
