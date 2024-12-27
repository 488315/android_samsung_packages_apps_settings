package com.android.settingslib.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkKey;
import android.net.NetworkRequest;
import android.net.NetworkScoreManager;
import android.net.vcn.VcnTransportInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiStatusTracker {
    public static final SimpleDateFormat SSDF = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public boolean connected;
    public boolean enabled;
    public boolean isDefaultNetwork;
    public final AnonymousClass3 mCacheListener;
    public final Runnable mCallback;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final Handler mHandler;
    public int mHistoryIndex;
    public final Handler mMainThreadHandler;
    public final NetworkScoreManager mNetworkScoreManager;
    public int mPrimaryNetworkId;
    public WifiInfo mWifiInfo;
    public final WifiManager mWifiManager;
    public final WifiNetworkScoreCache mWifiNetworkScoreCache;
    public int rssi;
    public String ssid;
    public String statusLabel;
    public final Set mNetworks = new HashSet();
    public final String[] mHistory = new String[32];
    public final NetworkRequest mNetworkRequest =
            new NetworkRequest.Builder()
                    .clearCapabilities()
                    .addCapability(15)
                    .addTransportType(1)
                    .addTransportType(0)
                    .build();
    public final AnonymousClass1 mNetworkCallback = new AnonymousClass1(this, 0);
    public final AnonymousClass1 mDefaultNetworkCallback = new AnonymousClass1(this, 1);
    public NetworkCapabilities mDefaultNetworkCapabilities = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.wifi.WifiStatusTracker$1, reason: invalid class name */
    public final class AnonymousClass1 extends ConnectivityManager.NetworkCallback {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiStatusTracker this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ AnonymousClass1(WifiStatusTracker wifiStatusTracker, int i) {
            super(1);
            this.$r8$classId = i;
            this.this$0 = wifiStatusTracker;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(
                Network network, NetworkCapabilities networkCapabilities) {
            switch (this.$r8$classId) {
                case 0:
                    WifiInfo mainOrUnderlyingWifiInfo =
                            this.this$0.getMainOrUnderlyingWifiInfo(networkCapabilities);
                    this.this$0.getClass();
                    if (networkCapabilities != null
                            && (mainOrUnderlyingWifiInfo != null
                                    || networkCapabilities.hasTransport(1))) {
                        String str =
                                WifiStatusTracker.SSDF.format(
                                                Long.valueOf(System.currentTimeMillis()))
                                        + ",onCapabilitiesChanged: network="
                                        + network
                                        + ",networkCapabilities="
                                        + networkCapabilities;
                        WifiStatusTracker wifiStatusTracker = this.this$0;
                        int i = wifiStatusTracker.mHistoryIndex;
                        wifiStatusTracker.mHistory[i] = str;
                        wifiStatusTracker.mHistoryIndex = (i + 1) % 32;
                    }
                    if (mainOrUnderlyingWifiInfo != null) {
                        if (!mainOrUnderlyingWifiInfo.isPrimary()) {
                            if (((HashSet) this.this$0.mNetworks)
                                    .contains(Integer.valueOf(network.getNetId()))) {
                                ((HashSet) this.this$0.mNetworks)
                                        .remove(Integer.valueOf(network.getNetId()));
                                break;
                            }
                        } else {
                            if (!((HashSet) this.this$0.mNetworks)
                                    .contains(Integer.valueOf(network.getNetId()))) {
                                ((HashSet) this.this$0.mNetworks)
                                        .add(Integer.valueOf(network.getNetId()));
                            }
                            this.this$0.mPrimaryNetworkId = network.getNetId();
                            WifiStatusTracker.m1053$$Nest$mupdateWifiInfo(
                                    this.this$0, mainOrUnderlyingWifiInfo);
                            this.this$0.updateStatusLabel();
                            this.this$0.mMainThreadHandler.post(
                                    new WifiStatusTracker$1$$ExternalSyntheticLambda0(0, this));
                            break;
                        }
                    }
                    break;
                default:
                    this.this$0.getClass();
                    WifiStatusTracker wifiStatusTracker2 = this.this$0;
                    wifiStatusTracker2.mDefaultNetworkCapabilities = networkCapabilities;
                    wifiStatusTracker2.updateStatusLabel();
                    this.this$0.mMainThreadHandler.post(
                            new WifiStatusTracker$2$$ExternalSyntheticLambda0(this, 0));
                    break;
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            switch (this.$r8$classId) {
                case 0:
                    String str =
                            WifiStatusTracker.SSDF.format(Long.valueOf(System.currentTimeMillis()))
                                    + ",onLost: network="
                                    + network;
                    WifiStatusTracker wifiStatusTracker = this.this$0;
                    int i = wifiStatusTracker.mHistoryIndex;
                    wifiStatusTracker.mHistory[i] = str;
                    wifiStatusTracker.mHistoryIndex = (i + 1) % 32;
                    if (((HashSet) wifiStatusTracker.mNetworks)
                            .contains(Integer.valueOf(network.getNetId()))) {
                        ((HashSet) this.this$0.mNetworks)
                                .remove(Integer.valueOf(network.getNetId()));
                    }
                    int netId = network.getNetId();
                    WifiStatusTracker wifiStatusTracker2 = this.this$0;
                    if (netId == wifiStatusTracker2.mPrimaryNetworkId) {
                        WifiStatusTracker.m1053$$Nest$mupdateWifiInfo(wifiStatusTracker2, null);
                        this.this$0.updateStatusLabel();
                        this.this$0.mMainThreadHandler.post(
                                new WifiStatusTracker$1$$ExternalSyntheticLambda0(1, this));
                        break;
                    }
                    break;
                default:
                    this.this$0.getClass();
                    WifiStatusTracker wifiStatusTracker3 = this.this$0;
                    wifiStatusTracker3.mDefaultNetworkCapabilities = null;
                    wifiStatusTracker3.updateStatusLabel();
                    this.this$0.mMainThreadHandler.post(
                            new WifiStatusTracker$2$$ExternalSyntheticLambda0(this, 1));
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.wifi.WifiStatusTracker$3, reason: invalid class name */
    public final class AnonymousClass3 extends WifiNetworkScoreCache.CacheListener {
        public AnonymousClass3(Handler handler) {
            super(handler);
        }

        public final void networkCacheUpdated(List list) {
            WifiStatusTracker.this.updateStatusLabel();
            WifiStatusTracker.this.mMainThreadHandler.post(
                    new WifiStatusTracker$1$$ExternalSyntheticLambda0(2, this));
        }
    }

    /* renamed from: -$$Nest$mupdateWifiInfo, reason: not valid java name */
    public static void m1053$$Nest$mupdateWifiInfo(
            WifiStatusTracker wifiStatusTracker, WifiInfo wifiInfo) {
        wifiStatusTracker.updateWifiState();
        wifiStatusTracker.connected = wifiInfo != null;
        wifiStatusTracker.mWifiInfo = wifiInfo;
        String str = null;
        wifiStatusTracker.ssid = null;
        if (wifiInfo != null) {
            if (wifiInfo.isPasspointAp() || wifiStatusTracker.mWifiInfo.isOsuAp()) {
                wifiStatusTracker.ssid =
                        wifiStatusTracker.mWifiInfo.getPasspointProviderFriendlyName();
            } else {
                String ssid = wifiStatusTracker.mWifiInfo.getSSID();
                if (ssid != null && !"<unknown ssid>".equals(ssid)) {
                    str = ssid;
                }
                wifiStatusTracker.ssid = str;
            }
            wifiStatusTracker.mWifiInfo.isCarrierMerged();
            wifiStatusTracker.mWifiInfo.getSubscriptionId();
            wifiStatusTracker.rssi = wifiStatusTracker.mWifiInfo.getRssi();
            NetworkKey createFromWifiInfo =
                    NetworkKey.createFromWifiInfo(wifiStatusTracker.mWifiInfo);
            if (wifiStatusTracker.mWifiNetworkScoreCache.getScoredNetwork(createFromWifiInfo)
                    == null) {
                wifiStatusTracker.mNetworkScoreManager.requestScores(
                        new NetworkKey[] {createFromWifiInfo});
            }
        }
    }

    public WifiStatusTracker(
            Context context,
            WifiManager wifiManager,
            NetworkScoreManager networkScoreManager,
            ConnectivityManager connectivityManager,
            Runnable runnable) {
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mWifiNetworkScoreCache = new WifiNetworkScoreCache(context);
        this.mNetworkScoreManager = networkScoreManager;
        this.mConnectivityManager = connectivityManager;
        this.mCallback = runnable;
        HandlerThread handlerThread = new HandlerThread("WifiStatusTrackerHandler");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        this.mHandler = handler;
        this.mMainThreadHandler = new Handler(Looper.getMainLooper());
        this.mCacheListener = new AnonymousClass3(handler);
    }

    public static WifiInfo getMainWifiInfo(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities == null) {
            return null;
        }
        if (!networkCapabilities.hasTransport(1) && !networkCapabilities.hasTransport(0)) {
            return null;
        }
        VcnTransportInfo transportInfo = networkCapabilities.getTransportInfo();
        if (transportInfo instanceof VcnTransportInfo) {
            return transportInfo.getWifiInfo();
        }
        if (transportInfo instanceof WifiInfo) {
            return (WifiInfo) transportInfo;
        }
        return null;
    }

    public final void fetchInitialState() {
        if (this.mWifiManager == null) {
            return;
        }
        updateWifiState();
        NetworkInfo networkInfo = this.mConnectivityManager.getNetworkInfo(1);
        boolean z = networkInfo != null && networkInfo.isConnected();
        this.connected = z;
        String str = null;
        this.mWifiInfo = null;
        this.ssid = null;
        if (z) {
            WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
            this.mWifiInfo = connectionInfo;
            if (connectionInfo != null) {
                if (connectionInfo.isPasspointAp() || this.mWifiInfo.isOsuAp()) {
                    this.ssid = this.mWifiInfo.getPasspointProviderFriendlyName();
                } else {
                    String ssid = this.mWifiInfo.getSSID();
                    if (ssid != null && !"<unknown ssid>".equals(ssid)) {
                        str = ssid;
                    }
                    this.ssid = str;
                }
                this.mWifiInfo.isCarrierMerged();
                this.mWifiInfo.getSubscriptionId();
                this.rssi = this.mWifiInfo.getRssi();
                NetworkKey createFromWifiInfo = NetworkKey.createFromWifiInfo(this.mWifiInfo);
                if (this.mWifiNetworkScoreCache.getScoredNetwork(createFromWifiInfo) == null) {
                    this.mNetworkScoreManager.requestScores(new NetworkKey[] {createFromWifiInfo});
                }
            }
        }
        updateStatusLabel();
    }

    public final WifiInfo getMainOrUnderlyingWifiInfo(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities == null) {
            return null;
        }
        WifiInfo mainWifiInfo = getMainWifiInfo(networkCapabilities);
        if (mainWifiInfo != null) {
            return mainWifiInfo;
        }
        if (!networkCapabilities.hasTransport(0)) {
            return mainWifiInfo;
        }
        List underlyingNetworks = networkCapabilities.getUnderlyingNetworks();
        if (underlyingNetworks == null) {
            return null;
        }
        Iterator it = underlyingNetworks.iterator();
        while (it.hasNext()) {
            WifiInfo mainWifiInfo2 =
                    getMainWifiInfo(
                            this.mConnectivityManager.getNetworkCapabilities((Network) it.next()));
            if (mainWifiInfo2 != null) {
                return mainWifiInfo2;
            }
        }
        return null;
    }

    public final void setListening(boolean z) {
        AnonymousClass1 anonymousClass1 = this.mDefaultNetworkCallback;
        AnonymousClass1 anonymousClass12 = this.mNetworkCallback;
        if (!z) {
            this.mNetworkScoreManager.unregisterNetworkScoreCache(1, this.mWifiNetworkScoreCache);
            this.mWifiNetworkScoreCache.unregisterListener();
            this.mConnectivityManager.unregisterNetworkCallback(anonymousClass12);
            this.mConnectivityManager.unregisterNetworkCallback(anonymousClass1);
            return;
        }
        this.mNetworkScoreManager.registerNetworkScoreCache(1, this.mWifiNetworkScoreCache, 1);
        this.mWifiNetworkScoreCache.registerListener(this.mCacheListener);
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        NetworkRequest networkRequest = this.mNetworkRequest;
        Handler handler = this.mHandler;
        connectivityManager.registerNetworkCallback(networkRequest, anonymousClass12, handler);
        this.mConnectivityManager.registerDefaultNetworkCallback(anonymousClass1, handler);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0015, code lost:

       if (r0.hasTransport(1) == false) goto L12;
    */
    /* JADX WARN: Removed duplicated region for block: B:13:0x001d  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateStatusLabel() {
        /*
            r5 = this;
            android.net.wifi.WifiManager r0 = r5.mWifiManager
            if (r0 != 0) goto L5
            return
        L5:
            android.net.NetworkCapabilities r0 = r5.mDefaultNetworkCapabilities
            r1 = 0
            if (r0 == 0) goto L18
            android.net.wifi.WifiInfo r2 = r5.getMainOrUnderlyingWifiInfo(r0)
            r3 = 1
            if (r2 != 0) goto L19
            boolean r0 = r0.hasTransport(r3)
            if (r0 == 0) goto L18
            goto L19
        L18:
            r3 = r1
        L19:
            r5.isDefaultNetwork = r3
            if (r3 == 0) goto L20
            android.net.NetworkCapabilities r0 = r5.mDefaultNetworkCapabilities
            goto L2c
        L20:
            android.net.ConnectivityManager r0 = r5.mConnectivityManager
            android.net.wifi.WifiManager r2 = r5.mWifiManager
            android.net.Network r2 = r2.getCurrentNetwork()
            android.net.NetworkCapabilities r0 = r0.getNetworkCapabilities(r2)
        L2c:
            if (r0 == 0) goto La1
            r2 = 17
            boolean r2 = r0.hasCapability(r2)
            if (r2 == 0) goto L42
            android.content.Context r0 = r5.mContext
            r1 = 2132031107(0x7f143683, float:1.9700878E38)
            java.lang.String r0 = r0.getString(r1)
            r5.statusLabel = r0
            return
        L42:
            r2 = 24
            boolean r2 = r0.hasCapability(r2)
            if (r2 == 0) goto L56
            android.content.Context r0 = r5.mContext
            r1 = 2132030822(0x7f143566, float:1.97003E38)
            java.lang.String r0 = r0.getString(r1)
            r5.statusLabel = r0
            return
        L56:
            r2 = 16
            boolean r2 = r0.hasCapability(r2)
            if (r2 != 0) goto L87
            android.content.Context r1 = r5.mContext
            android.content.ContentResolver r1 = r1.getContentResolver()
            java.lang.String r2 = "private_dns_mode"
            android.provider.Settings.Global.getString(r1, r2)
            boolean r0 = r0.isPrivateDnsBroken()
            if (r0 == 0) goto L7b
            android.content.Context r0 = r5.mContext
            r1 = 2132024561(0x7f141cf1, float:1.9687602E38)
            java.lang.String r0 = r0.getString(r1)
            r5.statusLabel = r0
            goto L86
        L7b:
            android.content.Context r0 = r5.mContext
            r1 = 2132031106(0x7f143682, float:1.9700876E38)
            java.lang.String r0 = r0.getString(r1)
            r5.statusLabel = r0
        L86:
            return
        L87:
            boolean r0 = r5.isDefaultNetwork
            if (r0 != 0) goto La1
            android.net.NetworkCapabilities r0 = r5.mDefaultNetworkCapabilities
            if (r0 == 0) goto La1
            boolean r0 = r0.hasTransport(r1)
            if (r0 == 0) goto La1
            android.content.Context r0 = r5.mContext
            r1 = 2132030573(0x7f14346d, float:1.9699795E38)
            java.lang.String r0 = r0.getString(r1)
            r5.statusLabel = r0
            return
        La1:
            android.net.wifi.WifiNetworkScoreCache r0 = r5.mWifiNetworkScoreCache
            android.net.wifi.WifiInfo r2 = r5.mWifiInfo
            android.net.NetworkKey r2 = android.net.NetworkKey.createFromWifiInfo(r2)
            android.net.ScoredNetwork r0 = r0.getScoredNetwork(r2)
            if (r0 != 0) goto Lb1
            r0 = 0
            goto Ld8
        Lb1:
            android.content.Context r2 = r5.mContext
            int r3 = r5.rssi
            int r4 = com.android.settingslib.wifi.AccessPoint.$r8$clinit
            int r0 = r0.calculateBadge(r3)
            r3 = 5
            if (r0 >= r3) goto Lbf
            goto Ld4
        Lbf:
            r1 = 7
            if (r0 >= r1) goto Lc4
            r1 = r3
            goto Ld4
        Lc4:
            r1 = 15
            if (r0 >= r1) goto Lcb
            r1 = 10
            goto Ld4
        Lcb:
            r1 = 25
            if (r0 >= r1) goto Ld2
            r1 = 20
            goto Ld4
        Ld2:
            r1 = 30
        Ld4:
            java.lang.String r0 = com.android.settingslib.wifi.AccessPoint.getSpeedLabel(r2, r1)
        Ld8:
            r5.statusLabel = r0
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.wifi.WifiStatusTracker.updateStatusLabel():void");
    }

    public final void updateWifiState() {
        this.enabled = this.mWifiManager.getWifiState() == 3;
    }
}
