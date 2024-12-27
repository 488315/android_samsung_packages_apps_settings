package com.android.wifitrackerlib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityDiagnosticsManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkKey;
import android.net.NetworkRequest;
import android.net.wifi.WifiManager;
import android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.os.BuildCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.LogUtils;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.WifiQoSScoredCache;
import com.samsung.android.wifitrackerlib.WifiScoredNetwork;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BaseWifiTracker {
    public static boolean sVerboseLogging;
    public final AnonymousClass5 mConnectivityDiagnosticsExecutor;
    public final ConnectivityDiagnosticsManager mConnectivityDiagnosticsManager;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final AnonymousClass2 mDefaultNetworkCallback;
    public final WifiTrackerInjector mInjector;
    public final BaseWifiTrackerCallback mListener;
    public final Handler mMainHandler;
    public final long mMaxScanAgeMillis;
    public final AnonymousClass2 mNetworkCallback;
    public boolean mNetworkScoringUiEnabled;
    public final WifiQoSScoredCache mQoSScoredCache;
    public final long mScanIntervalMillis;
    public final ScanResultUpdater mScanResultUpdater;
    public final Scanner mScanner;
    public final SemWifiManager mSemWifiManager;
    public final AnonymousClass7 mSharedConnectivityCallback;
    public final AnonymousClass5 mSharedConnectivityExecutor;
    public final SharedConnectivityManager mSharedConnectivityManager;
    public final String mTag;
    public final WifiManager mWifiManager;
    public final Handler mWorkerHandler;
    public int mWifiState = 4;
    public boolean mIsInitialized = false;
    public final AnonymousClass1 mBroadcastReceiver =
            new BroadcastReceiver() { // from class: com.android.wifitrackerlib.BaseWifiTracker.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    Map qoSScores;
                    String action = intent.getAction();
                    if (BaseWifiTracker.sVerboseLogging) {
                        Log.v(BaseWifiTracker.this.mTag, "Received broadcast: " + action);
                    }
                    int i = 0;
                    if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                        BaseWifiTracker.this.mWifiState = intent.getIntExtra("wifi_state", 1);
                        if (BaseWifiTracker.sVerboseLogging) {
                            Log.v(
                                    BaseWifiTracker.this.mTag,
                                    "WIFI_STATE_CHANGED_ACTION : "
                                            + BaseWifiTracker.this.mWifiState);
                        }
                        BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
                        Scanner scanner = baseWifiTracker.mScanner;
                        boolean z = baseWifiTracker.mWifiState == 3;
                        boolean z2 = scanner.mIsWifiEnabled;
                        scanner.mIsWifiEnabled = z;
                        if (z != z2) {
                            if (!z) {
                                scanner.stopScanning();
                            } else if (z && scanner.mIsStartedState) {
                                BaseWifiTracker baseWifiTracker2 = BaseWifiTracker.this;
                                baseWifiTracker2.getClass();
                                Log.i(baseWifiTracker2.mTag, "Scanning started");
                                scanner.scanLoop();
                            }
                        }
                        BaseWifiTracker baseWifiTracker3 = BaseWifiTracker.this;
                        BaseWifiTrackerCallback baseWifiTrackerCallback =
                                baseWifiTracker3.mListener;
                        if (baseWifiTrackerCallback != null) {
                            baseWifiTracker3.mMainHandler.post(
                                    new BaseWifiTracker$$ExternalSyntheticLambda3(
                                            baseWifiTrackerCallback, 0));
                        }
                        BaseWifiTracker.this.handleWifiStateChangedAction();
                        return;
                    }
                    if (!"android.net.wifi.SCAN_RESULTS".equals(action)) {
                        if ("android.net.wifi.CONFIGURED_NETWORKS_CHANGE".equals(action)) {
                            BaseWifiTracker.this.handleConfiguredNetworksChangedAction(intent);
                            return;
                        }
                        if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                            BaseWifiTracker.this.handleNetworkStateChangedAction(intent);
                            return;
                        } else if ("android.net.wifi.RSSI_CHANGED".equals(action)) {
                            BaseWifiTracker.this.handleRssiChangedAction();
                            return;
                        } else {
                            if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED"
                                    .equals(action)) {
                                BaseWifiTracker.this.handleDefaultSubscriptionChanged(
                                        intent.getIntExtra("subscription", -1));
                                return;
                            }
                            return;
                        }
                    }
                    BaseWifiTracker baseWifiTracker4 = BaseWifiTracker.this;
                    if (baseWifiTracker4.mNetworkScoringUiEnabled) {
                        WifiQoSScoredCache wifiQoSScoredCache = baseWifiTracker4.mQoSScoredCache;
                        Collection collection =
                                (Collection)
                                        baseWifiTracker4.mWifiManager.getScanResults().stream()
                                                .map(
                                                        new BaseWifiTracker$1$$ExternalSyntheticLambda0())
                                                .filter(
                                                        new BaseWifiTracker$1$$ExternalSyntheticLambda1())
                                                .collect(Collectors.toList());
                        wifiQoSScoredCache.getClass();
                        HashSet hashSet = new HashSet(collection);
                        if (!hashSet.isEmpty()) {
                            ArrayList arrayList = new ArrayList();
                            HashMap hashMap = new HashMap();
                            Iterator it = hashSet.iterator();
                            while (it.hasNext()) {
                                NetworkKey networkKey = (NetworkKey) it.next();
                                hashMap.put(networkKey.wifiKey.bssid, networkKey);
                                arrayList.add(networkKey.wifiKey.bssid);
                            }
                            if (arrayList.size() != 0
                                    && (qoSScores =
                                                    wifiQoSScoredCache.mSemWifiManager.getQoSScores(
                                                            arrayList))
                                            != null
                                    && !qoSScores.isEmpty()) {
                                synchronized (wifiQoSScoredCache.mLock) {
                                    ((HashMap) wifiQoSScoredCache.mCache).clear();
                                }
                                wifiQoSScoredCache.mUpdated = false;
                                if (Debug.semIsProductDev()
                                        ? true
                                        : Log.isLoggable("WifiTracker.WifiWifiQoSScoreCache", 3)) {
                                    Log.d(
                                            "WifiTracker.WifiWifiQoSScoreCache",
                                            "------ Add scored data start -----");
                                }
                                Iterator it2 = qoSScores.entrySet().iterator();
                                while (it2.hasNext()) {
                                    String str = (String) ((Map.Entry) it2.next()).getKey();
                                    if (hashSet.contains(hashMap.get(str))) {
                                        Map map = (Map) qoSScores.get(str);
                                        int intValue =
                                                ((Integer) map.get("networkType")).intValue();
                                        WifiScoredNetwork wifiScoredNetwork =
                                                new WifiScoredNetwork(
                                                        str,
                                                        intValue,
                                                        new int[] {
                                                            ((Integer) map.get("levelMax-2"))
                                                                    .intValue(),
                                                            ((Integer) map.get("levelMax-1"))
                                                                    .intValue(),
                                                            ((Integer) map.get("levelMax"))
                                                                    .intValue()
                                                        });
                                        if (!TextUtils.isEmpty(str) && intValue != 3) {
                                            synchronized (wifiQoSScoredCache.mLock) {
                                                try {
                                                    ((HashMap) wifiQoSScoredCache.mCache)
                                                            .put(str, wifiScoredNetwork);
                                                    if (Debug.semIsProductDev()
                                                            ? true
                                                            : Log.isLoggable(
                                                                    "WifiTracker.WifiWifiQoSScoreCache",
                                                                    3)) {
                                                        LogUtils logUtils = wifiQoSScoredCache.mLog;
                                                        String wifiScoredNetwork2 =
                                                                wifiScoredNetwork.toString();
                                                        if (logUtils.isProductDev) {
                                                            Log.d(
                                                                    "WifiTracker.WifiWifiQoSScoreCache",
                                                                    logUtils.getPrintableLog(
                                                                            wifiScoredNetwork2));
                                                        }
                                                    }
                                                } catch (Throwable th) {
                                                    throw th;
                                                }
                                            }
                                            wifiQoSScoredCache.mUpdated = true;
                                        }
                                        hashSet.remove(hashMap.get(str));
                                        i++;
                                    }
                                }
                                if (Debug.semIsProductDev()
                                        ? true
                                        : Log.isLoggable("WifiTracker.WifiWifiQoSScoreCache", 3)) {
                                    Log.d(
                                            "WifiTracker.WifiWifiQoSScoreCache",
                                            "------ Add scored data end -----");
                                }
                                Log.d(
                                        "WifiTracker.WifiWifiQoSScoreCache",
                                        i + " key set are removed");
                                final AnonymousClass8 anonymousClass8 =
                                        wifiQoSScoredCache.mListener;
                                if (anonymousClass8 != null && wifiQoSScoredCache.mUpdated) {
                                    anonymousClass8.mHandler.post(
                                            new Runnable() { // from class:
                                                // com.samsung.android.wifitrackerlib.WifiQoSScoredCache$SemCacheListener$1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    BaseWifiTracker.this
                                                            .handleQosScoreCacheUpdated();
                                                }
                                            });
                                }
                            }
                        }
                    }
                    BaseWifiTracker.this.handleScanResultsAvailableAction(intent);
                    BaseWifiTracker.this.semNotifyScanStateChanged(2);
                }
            };
    public final WifiTrackerLifecycleObserver mLifecycleObserver =
            new WifiTrackerLifecycleObserver();
    public final NetworkRequest mNetworkRequest =
            new NetworkRequest.Builder()
                    .clearCapabilities()
                    .addCapability(15)
                    .addTransportType(1)
                    .build();
    public final AnonymousClass4 mConnectivityDiagnosticsCallback =
            new ConnectivityDiagnosticsManager
                    .ConnectivityDiagnosticsCallback() { // from class:
                                                         // com.android.wifitrackerlib.BaseWifiTracker.4
                @Override // android.net.ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback
                public final void onConnectivityReportAvailable(
                        ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {
                    BaseWifiTracker.this.handleConnectivityReportAvailable(connectivityReport);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.wifitrackerlib.BaseWifiTracker$8, reason: invalid class name */
    public final class AnonymousClass8 {
        public final Handler mHandler;

        public AnonymousClass8(Handler handler) {
            Objects.requireNonNull(handler);
            this.mHandler = handler;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Scanner extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;
        public boolean mIsStartedState;
        public boolean mIsWifiEnabled;

        public Scanner(Looper looper) {
            super(looper);
            this.mIsStartedState = false;
            this.mIsWifiEnabled = false;
        }

        public final void scanLoop() {
            boolean z;
            boolean z2 = this.mIsWifiEnabled;
            BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
            if (z2 && this.mIsStartedState) {
                baseWifiTracker.getClass();
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                Log.e(
                        baseWifiTracker.mTag,
                        "Scan loop called even though we shouldn't be scanning! mIsWifiEnabled="
                                + this.mIsWifiEnabled
                                + " mIsStartedState="
                                + this.mIsStartedState);
                return;
            }
            boolean equals = "WifiPickerTracker".equals(baseWifiTracker.mTag);
            String str = baseWifiTracker.mTag;
            if (equals) {
                Log.d(str, "Starting BLE scan for AutoHotspot");
                baseWifiTracker.mSemWifiManager.wifiApBleClientRole(true);
            }
            if (BaseWifiTracker.sVerboseLogging) {
                Log.v(str, "Issuing scan request from WifiManager");
            }
            removeCallbacksAndMessages(null);
            baseWifiTracker.mSemWifiManager.startScan();
            baseWifiTracker.semNotifyScanStateChanged(1);
            BaseWifiTrackerCallback baseWifiTrackerCallback = baseWifiTracker.mListener;
            if (baseWifiTrackerCallback != null) {
                baseWifiTracker.mMainHandler.post(
                        new BaseWifiTracker$$ExternalSyntheticLambda3(baseWifiTrackerCallback, 1));
            }
            postDelayed(
                    new BaseWifiTracker$Scanner$$ExternalSyntheticLambda0(this, 1),
                    baseWifiTracker.mScanIntervalMillis);
        }

        public final void stopScanning() {
            BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
            Log.i(baseWifiTracker.mTag, "Scanning stopped");
            removeCallbacksAndMessages(null);
            if ("WifiPickerTracker".equals(baseWifiTracker.mTag)) {
                Log.d(baseWifiTracker.mTag, "Stopping BLE scan for AutoHotspot");
                baseWifiTracker.mSemWifiManager.wifiApBleClientRole(false);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiTrackerLifecycleObserver implements LifecycleObserver {
        public WifiTrackerLifecycleObserver() {}

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {
            BaseWifiTracker.this.unregisterReceiverAndCallback();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart() {
            BaseWifiTracker.this.onStart();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void onStop() {
            BaseWifiTracker.this.onStop();
        }
    }

    /* JADX WARN: Type inference failed for: r10v2, types: [com.android.wifitrackerlib.BaseWifiTracker$5] */
    /* JADX WARN: Type inference failed for: r10v22, types: [com.android.wifitrackerlib.BaseWifiTracker$7] */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.wifitrackerlib.BaseWifiTracker$1] */
    /* JADX WARN: Type inference failed for: r12v5, types: [com.android.wifitrackerlib.BaseWifiTracker$2] */
    /* JADX WARN: Type inference failed for: r12v6, types: [com.android.wifitrackerlib.BaseWifiTracker$2] */
    /* JADX WARN: Type inference failed for: r12v7, types: [com.android.wifitrackerlib.BaseWifiTracker$4] */
    /* JADX WARN: Type inference failed for: r12v8, types: [com.android.wifitrackerlib.BaseWifiTracker$5] */
    public BaseWifiTracker(
            WifiTrackerInjector wifiTrackerInjector,
            final Lifecycle lifecycle,
            Context context,
            WifiManager wifiManager,
            ConnectivityManager connectivityManager,
            Handler handler,
            Handler handler2,
            Clock clock,
            long j,
            long j2,
            BaseWifiTrackerCallback baseWifiTrackerCallback,
            String str) {
        final int i = 0;
        this.mSharedConnectivityManager = null;
        final int i2 = 1;
        this.mNetworkCallback =
                new ConnectivityManager.NetworkCallback(
                        this) { // from class: com.android.wifitrackerlib.BaseWifiTracker.2
                    public final /* synthetic */ BaseWifiTracker this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                        this.this$0 = this;
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onCapabilitiesChanged(
                            Network network, NetworkCapabilities networkCapabilities) {
                        switch (i) {
                            case 0:
                                this.this$0.handleNetworkCapabilitiesChanged(
                                        network, networkCapabilities);
                                break;
                            default:
                                this.this$0.handleDefaultNetworkCapabilitiesChanged(
                                        network, networkCapabilities);
                                break;
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onLinkPropertiesChanged(
                            Network network, LinkProperties linkProperties) {
                        switch (i) {
                            case 0:
                                this.this$0.handleLinkPropertiesChanged(network, linkProperties);
                                break;
                            default:
                                super.onLinkPropertiesChanged(network, linkProperties);
                                break;
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLost(Network network) {
                        switch (i) {
                            case 0:
                                this.this$0.handleNetworkLost(network);
                                break;
                            default:
                                this.this$0.handleDefaultNetworkLost();
                                break;
                        }
                    }
                };
        this.mDefaultNetworkCallback =
                new ConnectivityManager.NetworkCallback(
                        this) { // from class: com.android.wifitrackerlib.BaseWifiTracker.2
                    public final /* synthetic */ BaseWifiTracker this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                        this.this$0 = this;
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onCapabilitiesChanged(
                            Network network, NetworkCapabilities networkCapabilities) {
                        switch (i2) {
                            case 0:
                                this.this$0.handleNetworkCapabilitiesChanged(
                                        network, networkCapabilities);
                                break;
                            default:
                                this.this$0.handleDefaultNetworkCapabilitiesChanged(
                                        network, networkCapabilities);
                                break;
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onLinkPropertiesChanged(
                            Network network, LinkProperties linkProperties) {
                        switch (i2) {
                            case 0:
                                this.this$0.handleLinkPropertiesChanged(network, linkProperties);
                                break;
                            default:
                                super.onLinkPropertiesChanged(network, linkProperties);
                                break;
                        }
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLost(Network network) {
                        switch (i2) {
                            case 0:
                                this.this$0.handleNetworkLost(network);
                                break;
                            default:
                                this.this$0.handleDefaultNetworkLost();
                                break;
                        }
                    }
                };
        this.mConnectivityDiagnosticsExecutor =
                new Executor(this) { // from class: com.android.wifitrackerlib.BaseWifiTracker.5
                    public final /* synthetic */ BaseWifiTracker this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.util.concurrent.Executor
                    public final void execute(Runnable runnable) {
                        switch (i) {
                            case 0:
                                this.this$0.mWorkerHandler.post(runnable);
                                break;
                            default:
                                this.this$0.mWorkerHandler.post(runnable);
                                break;
                        }
                    }
                };
        this.mSharedConnectivityExecutor =
                new Executor(this) { // from class: com.android.wifitrackerlib.BaseWifiTracker.5
                    public final /* synthetic */ BaseWifiTracker this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.util.concurrent.Executor
                    public final void execute(Runnable runnable) {
                        switch (i2) {
                            case 0:
                                this.this$0.mWorkerHandler.post(runnable);
                                break;
                            default:
                                this.this$0.mWorkerHandler.post(runnable);
                                break;
                        }
                    }
                };
        this.mSharedConnectivityCallback = null;
        this.mInjector = wifiTrackerInjector;
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mSemWifiManager = (SemWifiManager) context.getSystemService(SemWifiManager.class);
        this.mConnectivityManager = connectivityManager;
        this.mConnectivityDiagnosticsManager =
                (ConnectivityDiagnosticsManager)
                        context.getSystemService(ConnectivityDiagnosticsManager.class);
        if (wifiTrackerInjector.isSharedConnectivityFeatureEnabled()) {
            int i3 = BuildCompat.$r8$clinit;
            this.mSharedConnectivityManager =
                    (SharedConnectivityManager)
                            context.getSystemService(SharedConnectivityManager.class);
            this.mSharedConnectivityCallback =
                    new SharedConnectivityClientCallback() { // from class:
                                                             // com.android.wifitrackerlib.BaseWifiTracker.7
                        public final void onHotspotNetworkConnectionStatusChanged(
                                HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) {
                            BaseWifiTracker.this.handleHotspotNetworkConnectionStatusChanged(
                                    hotspotNetworkConnectionStatus);
                        }

                        public final void onHotspotNetworksUpdated(List list) {
                            BaseWifiTracker.this.handleHotspotNetworksUpdated(list);
                        }

                        public final void onKnownNetworkConnectionStatusChanged(
                                KnownNetworkConnectionStatus knownNetworkConnectionStatus) {
                            BaseWifiTracker.this.handleKnownNetworkConnectionStatusChanged(
                                    knownNetworkConnectionStatus);
                        }

                        public final void onKnownNetworksUpdated(List list) {
                            BaseWifiTracker.this.handleKnownNetworksUpdated(list);
                        }

                        public final void onRegisterCallbackFailed(Exception exc) {
                            BaseWifiTracker.this.getClass();
                        }

                        public final void onServiceConnected() {
                            BaseWifiTracker.this.handleServiceConnected();
                        }

                        public final void onServiceDisconnected() {
                            BaseWifiTracker.this.handleServiceDisconnected();
                        }

                        public final void onSharedConnectivitySettingsChanged(
                                SharedConnectivitySettingsState sharedConnectivitySettingsState) {
                            BaseWifiTracker.this.getClass();
                        }
                    };
        }
        this.mMainHandler = handler;
        this.mWorkerHandler = handler2;
        this.mMaxScanAgeMillis = j;
        this.mScanIntervalMillis = j2;
        this.mListener = baseWifiTrackerCallback;
        this.mTag = str;
        this.mScanResultUpdater = new ScanResultUpdater(clock, j + j2, context);
        sVerboseLogging = wifiTrackerInjector.mWifiManager.isVerboseLoggingEnabled();
        this.mQoSScoredCache = new WifiQoSScoredCache(context, new AnonymousClass8(handler2));
        this.mScanner = new Scanner(handler2.getLooper());
        if (lifecycle != null) {
            handler.post(
                    new Runnable() { // from class:
                                     // com.android.wifitrackerlib.BaseWifiTracker$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            lifecycle.addObserver(BaseWifiTracker.this.mLifecycleObserver);
                        }
                    });
        }
    }

    public final int getWifiState() {
        if (this.mWifiState == 4) {
            this.mWifiState = this.mWifiManager.getWifiState();
        }
        return this.mWifiState;
    }

    public abstract void handleLinkPropertiesChanged(
            Network network, LinkProperties linkProperties);

    public abstract void handleNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities);

    public abstract void handleNetworkLost(Network network);

    public abstract void handleNetworkStateChangedAction(Intent intent);

    public abstract void handleOnStart();

    public final void onStart() {
        if (sVerboseLogging) {
            Log.v(this.mTag, "onStart");
        }
        Scanner scanner = this.mScanner;
        scanner.mIsStartedState = true;
        BaseWifiTracker.this.mWorkerHandler.post(
                new BaseWifiTracker$Scanner$$ExternalSyntheticLambda0(scanner, 2));
        this.mWorkerHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda1(this, 1));
        SemWifiEntryFlags.isBlockedUnSecureWifiAutoJoin = -1;
    }

    public final void onStop() {
        if (sVerboseLogging) {
            Log.v(this.mTag, "onStop");
        }
        Scanner scanner = this.mScanner;
        scanner.mIsStartedState = false;
        BaseWifiTracker.this.mWorkerHandler.post(
                new BaseWifiTracker$Scanner$$ExternalSyntheticLambda0(scanner, 0));
        this.mWorkerHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda1(this, 0));
    }

    public final void unregisterReceiverAndCallback() {
        AnonymousClass7 anonymousClass7;
        try {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
            this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
            this.mConnectivityManager.unregisterNetworkCallback(this.mDefaultNetworkCallback);
            this.mConnectivityDiagnosticsManager.unregisterConnectivityDiagnosticsCallback(
                    this.mConnectivityDiagnosticsCallback);
            SharedConnectivityManager sharedConnectivityManager = this.mSharedConnectivityManager;
            if (sharedConnectivityManager == null
                    || (anonymousClass7 = this.mSharedConnectivityCallback) == null) {
                return;
            }
            int i = BuildCompat.$r8$clinit;
            if (sharedConnectivityManager.unregisterCallback(anonymousClass7)) {
                return;
            }
            Log.e(this.mTag, "onDestroyed: unregisterCallback failed");
        } catch (IllegalArgumentException unused) {
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface BaseWifiTrackerCallback {
        void onWifiStateChanged();

        default void onScanRequested() {}
    }

    public void handleDefaultNetworkLost() {}

    public void handleQosScoreCacheUpdated() {}

    public void handleRssiChangedAction() {}

    public void handleServiceConnected() {}

    public void handleServiceDisconnected() {}

    public void handleWifiStateChangedAction() {}

    public void handleConfiguredNetworksChangedAction(Intent intent) {}

    public void handleConnectivityReportAvailable(
            ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {}

    public void handleDefaultSubscriptionChanged(int i) {}

    public void handleHotspotNetworkConnectionStatusChanged(
            HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) {}

    public void handleHotspotNetworksUpdated(List list) {}

    public void handleKnownNetworkConnectionStatusChanged(
            KnownNetworkConnectionStatus knownNetworkConnectionStatus) {}

    public void handleKnownNetworksUpdated(List list) {}

    public void handleScanResultsAvailableAction(Intent intent) {}

    public void semNotifyScanStateChanged(int i) {}

    public void handleDefaultNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {}
}
