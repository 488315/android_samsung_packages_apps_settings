package com.android.settings.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.settings.AirplaneModeEnabler;
import com.android.settingslib.WirelessUtils;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InternetUpdater
        implements AirplaneModeEnabler.OnAirplaneModeChangedListener, LifecycleObserver {
    public static final Map sTransportMap;
    AirplaneModeEnabler mAirplaneModeEnabler;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    boolean mInternetAvailable;
    public int mInternetType;
    public final InternetChangeListener mListener;
    int mTransport;
    public final WifiManager mWifiManager;
    public final AnonymousClass1 mNetworkCallback =
            new ConnectivityManager
                    .NetworkCallback() { // from class:
                                         // com.android.settings.network.InternetUpdater.1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public final void onCapabilitiesChanged(
                        Network network, NetworkCapabilities networkCapabilities) {
                    InternetUpdater.this.updateInternetAvailable(networkCapabilities);
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public final void onLost(Network network) {
                    InternetUpdater internetUpdater = InternetUpdater.this;
                    internetUpdater.mInternetAvailable = false;
                    internetUpdater.updateInternetType();
                }
            };
    public final AnonymousClass2 mWifiStateReceiver =
            new BroadcastReceiver() { // from class: com.android.settings.network.InternetUpdater.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    InternetUpdater.this.fetchActiveNetwork();
                    InternetUpdater internetUpdater = InternetUpdater.this;
                    InternetChangeListener internetChangeListener = internetUpdater.mListener;
                    if (internetChangeListener != null) {
                        internetUpdater.mWifiManager.isWifiEnabled();
                        internetChangeListener.getClass();
                    }
                }
            };
    public final IntentFilter mWifiStateFilter =
            new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");

    static {
        HashMap hashMap = new HashMap();
        sTransportMap = hashMap;
        hashMap.put(1, 2);
        hashMap.put(0, 3);
        hashMap.put(3, 4);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.network.InternetUpdater$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.network.InternetUpdater$2] */
    public InternetUpdater(
            Context context, Lifecycle lifecycle, InternetChangeListener internetChangeListener) {
        this.mContext = context;
        this.mAirplaneModeEnabler = new AirplaneModeEnabler(context, this);
        this.mConnectivityManager =
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mListener = internetChangeListener;
        fetchActiveNetwork();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    public final void fetchActiveNetwork() {
        Network activeNetwork = this.mConnectivityManager.getActiveNetwork();
        if (activeNetwork == null) {
            this.mInternetAvailable = false;
            updateInternetType();
            return;
        }
        NetworkCapabilities networkCapabilities =
                this.mConnectivityManager.getNetworkCapabilities(activeNetwork);
        if (networkCapabilities != null) {
            updateInternetAvailable(networkCapabilities);
        } else {
            this.mInternetAvailable = false;
            updateInternetType();
        }
    }

    @Override // com.android.settings.AirplaneModeEnabler.OnAirplaneModeChangedListener
    public final void onAirplaneModeChanged(boolean z) {
        fetchActiveNetwork();
        InternetChangeListener internetChangeListener = this.mListener;
        if (internetChangeListener != null) {
            internetChangeListener.onAirplaneModeChanged(z);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        this.mAirplaneModeEnabler.close();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        this.mAirplaneModeEnabler.stop();
        this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
        this.mContext.unregisterReceiver(this.mWifiStateReceiver);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        this.mAirplaneModeEnabler.start();
        this.mConnectivityManager.registerDefaultNetworkCallback(this.mNetworkCallback);
        this.mContext.registerReceiver(this.mWifiStateReceiver, this.mWifiStateFilter, 2);
    }

    public void updateInternetAvailable(NetworkCapabilities networkCapabilities) {
        boolean z = false;
        if (networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16)) {
            int[] transportTypes = networkCapabilities.getTransportTypes();
            int length = transportTypes.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                int i2 = transportTypes[i];
                if (((HashMap) sTransportMap).containsKey(Integer.valueOf(i2))) {
                    this.mTransport = i2;
                    TooltipPopup$$ExternalSyntheticOutline0.m(
                            new StringBuilder(
                                    "Detect an internet available network with transport type: "),
                            this.mTransport,
                            "InternetUpdater");
                    z = true;
                    break;
                }
                i++;
            }
        }
        this.mInternetAvailable = z;
        updateInternetType();
    }

    public void updateInternetType() {
        WifiInfo connectionInfo;
        int i = 3;
        if (this.mInternetAvailable) {
            int intValue =
                    ((Integer) ((HashMap) sTransportMap).get(Integer.valueOf(this.mTransport)))
                            .intValue();
            if (intValue == 2
                    && (connectionInfo = this.mWifiManager.getConnectionInfo()) != null
                    && connectionInfo.isCarrierMerged()) {
                Log.i("InternetUpdater", "Detect a merged carrier Wi-Fi connected.");
            } else {
                i = intValue;
            }
        } else {
            i =
                    (!WirelessUtils.isAirplaneModeOn(this.mAirplaneModeEnabler.mContext)
                                    || this.mWifiManager.getWifiState() == 3)
                            ? 1
                            : 0;
        }
        this.mInternetType = i;
        InternetChangeListener internetChangeListener = this.mListener;
        if (internetChangeListener != null) {
            internetChangeListener.onInternetTypeChanged(i);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface InternetChangeListener {
        void onInternetTypeChanged(int i);

        default void onAirplaneModeChanged(boolean z) {}
    }
}
