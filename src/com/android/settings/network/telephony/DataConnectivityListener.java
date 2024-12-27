package com.android.settings.network.telephony;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DataConnectivityListener extends ConnectivityManager.NetworkCallback {
    public final Client mClient;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final NetworkRequest mNetworkRequest =
            new NetworkRequest.Builder().addCapability(12).build();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Client {
        void onDataConnectivityChange();
    }

    public DataConnectivityListener(Context context, Client client) {
        this.mContext = context;
        this.mConnectivityManager =
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        this.mClient = client;
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public final void onCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        Network activeNetwork = this.mConnectivityManager.getActiveNetwork();
        if (activeNetwork == null || !activeNetwork.equals(network)) {
            return;
        }
        this.mClient.onDataConnectivityChange();
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public final void onLosing(Network network, int i) {
        this.mClient.onDataConnectivityChange();
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public final void onLost(Network network) {
        this.mClient.onDataConnectivityChange();
    }
}
