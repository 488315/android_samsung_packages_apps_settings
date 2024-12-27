package com.android.wifitrackerlib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.os.Handler;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.lifecycle.Lifecycle;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Clock;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HotspotNetworkDetailsTracker extends NetworkDetailsTracker {
    public final HotspotNetworkEntry mChosenEntry;
    public HotspotNetwork mHotspotNetworkData;

    public HotspotNetworkDetailsTracker(
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
        super(
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
                null,
                "HotspotNetworkDetailsTracker");
        Log.v("HotspotNetworkDetailsTracker", "key: " + str);
        HotspotNetworkEntry.HotspotNetworkEntryKey hotspotNetworkEntryKey =
                new HotspotNetworkEntry.HotspotNetworkEntryKey();
        hotspotNetworkEntryKey.mScanResultKey = null;
        if (str.startsWith("HotspotNetworkEntry:")) {
            try {
                JSONObject jSONObject = new JSONObject(str.substring(20));
                if (jSONObject.has("IS_VIRTUAL_ENTRY_KEY")) {
                    hotspotNetworkEntryKey.mIsVirtualEntry =
                            jSONObject.getBoolean("IS_VIRTUAL_ENTRY_KEY");
                }
                if (jSONObject.has("DEVICE_ID_KEY")) {
                    hotspotNetworkEntryKey.mDeviceId = jSONObject.getLong("DEVICE_ID_KEY");
                }
                if (jSONObject.has("SCAN_RESULT_KEY")) {
                    hotspotNetworkEntryKey.mScanResultKey =
                            new StandardWifiEntry.ScanResultKey(
                                    jSONObject.getString("SCAN_RESULT_KEY"));
                }
            } catch (JSONException e) {
                Log.e(
                        "HotspotNetworkEntry",
                        "JSONException while converting HotspotNetworkEntryKey to string: " + e);
            }
        } else {
            Log.e("HotspotNetworkEntry", "String key does not start with key prefix!");
        }
        if (hotspotNetworkEntryKey.mIsVirtualEntry) {
            Log.e("HotspotNetworkDetailsTracker", "Network details not relevant for virtual entry");
        }
        HotspotNetworkEntry hotspotNetworkEntry =
                new HotspotNetworkEntry(
                        this.mInjector,
                        this.mContext,
                        this.mMainHandler,
                        this.mWifiManager,
                        this.mSharedConnectivityManager,
                        hotspotNetworkEntryKey);
        this.mChosenEntry = hotspotNetworkEntry;
        updateStartInfo();
        Log.v("HotspotNetworkDetailsTracker", "mChosenEntry: " + hotspotNetworkEntry);
    }

    @Override // com.android.wifitrackerlib.NetworkDetailsTracker
    public final WifiEntry getWifiEntry() {
        return this.mChosenEntry;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleHotspotNetworksUpdated(List list) {
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            this.mHotspotNetworkData =
                    (HotspotNetwork)
                            list.stream()
                                    .filter(
                                            new HotspotNetworkDetailsTracker$$ExternalSyntheticLambda0(
                                                    this, 1))
                                    .findFirst()
                                    .orElse(null);
        }
        HotspotNetwork hotspotNetwork = this.mHotspotNetworkData;
        if (hotspotNetwork == null) {
            throw new IllegalArgumentException(
                    "Cannot find data for given HotspotNetworkEntry key!");
        }
        this.mChosenEntry.updateHotspotNetworkData(hotspotNetwork);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleOnStart() {
        updateStartInfo();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleServiceConnected() {
        SharedConnectivityManager sharedConnectivityManager;
        List hotspotNetworks;
        if (this.mInjector.isSharedConnectivityFeatureEnabled()
                && (sharedConnectivityManager = this.mSharedConnectivityManager) != null
                && (hotspotNetworks = sharedConnectivityManager.getHotspotNetworks()) != null) {
            this.mHotspotNetworkData =
                    (HotspotNetwork)
                            hotspotNetworks.stream()
                                    .filter(
                                            new HotspotNetworkDetailsTracker$$ExternalSyntheticLambda0(
                                                    this, 0))
                                    .findFirst()
                                    .orElse(null);
        }
        HotspotNetwork hotspotNetwork = this.mHotspotNetworkData;
        if (hotspotNetwork == null) {
            throw new IllegalArgumentException(
                    "Cannot find data for given HotspotNetworkEntry key!");
        }
        this.mChosenEntry.updateHotspotNetworkData(hotspotNetwork);
    }

    public final void updateStartInfo() {
        SubscriptionManager.getDefaultDataSubscriptionId();
        Network currentNetwork = this.mWifiManager.getCurrentNetwork();
        if (currentNetwork == null) {
            return;
        }
        NetworkCapabilities networkCapabilities =
                this.mConnectivityManager.getNetworkCapabilities(currentNetwork);
        if (networkCapabilities != null) {
            handleNetworkCapabilitiesChanged(
                    currentNetwork,
                    new NetworkCapabilities.Builder(networkCapabilities)
                            .setTransportInfo(this.mWifiManager.getConnectionInfo())
                            .build());
        }
        LinkProperties linkProperties = this.mConnectivityManager.getLinkProperties(currentNetwork);
        if (linkProperties != null) {
            handleLinkPropertiesChanged(currentNetwork, linkProperties);
        }
    }
}
