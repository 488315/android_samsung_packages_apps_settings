package com.android.settingslib.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkKey;
import android.net.NetworkRequest;
import android.net.NetworkScoreManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.net.wifi.hotspot2.OsuProvider;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.sec.ims.IMSParameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiTracker implements LifecycleObserver, OnStart, OnStop, OnDestroy {
    static final long MAX_SCAN_RESULT_AGE_MILLIS = 15000;
    public static boolean sVerboseLogging;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final IntentFilter mFilter;
    public WifiInfo mLastInfo;
    public NetworkInfo mLastNetworkInfo;
    public final WifiListenerExecutor mListener;
    public long mMaxSpeedLabelScoreCacheAge;
    public WifiTrackerNetworkCallback mNetworkCallback;
    public final NetworkRequest mNetworkRequest;
    public final NetworkScoreManager mNetworkScoreManager;
    public boolean mNetworkScoringUiEnabled;
    public boolean mRegistered;
    Scanner mScanner;
    public WifiNetworkScoreCache mScoreCache;
    public final WifiManager mWifiManager;
    Handler mWorkHandler;
    public HandlerThread mWorkThread;
    public final AtomicBoolean mConnected = new AtomicBoolean(false);
    public final Object mLock = new Object();
    public final List mInternalAccessPoints = new ArrayList();
    public final Set mRequestedScores = new ArraySet();
    public boolean mStaleScanResults = true;
    public boolean mLastScanSucceeded = true;
    public final HashMap mScanResultCache = new HashMap();
    final BroadcastReceiver mReceiver =
            new BroadcastReceiver() { // from class: com.android.settingslib.wifi.WifiTracker.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (!"android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                        if ("android.net.wifi.SCAN_RESULTS".equals(action)) {
                            WifiTracker wifiTracker = WifiTracker.this;
                            wifiTracker.mStaleScanResults = false;
                            wifiTracker.mLastScanSucceeded =
                                    intent.getBooleanExtra("resultsUpdated", true);
                            WifiTracker.this.fetchScansAndConfigsAndUpdateAccessPoints();
                            return;
                        }
                        if ("android.net.wifi.CONFIGURED_NETWORKS_CHANGE".equals(action)
                                || "android.net.wifi.LINK_CONFIGURATION_CHANGED".equals(action)) {
                            WifiTracker.this.fetchScansAndConfigsAndUpdateAccessPoints();
                            return;
                        }
                        if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                            WifiTracker.m1054$$Nest$mupdateNetworkInfo(
                                    WifiTracker.this,
                                    (NetworkInfo)
                                            intent.getParcelableExtra(
                                                    IMSParameter.GENERAL.NETWORK_INFO));
                            WifiTracker.this.fetchScansAndConfigsAndUpdateAccessPoints();
                            return;
                        } else {
                            if ("android.net.wifi.RSSI_CHANGED".equals(action)) {
                                WifiTracker.m1054$$Nest$mupdateNetworkInfo(WifiTracker.this, null);
                                return;
                            }
                            return;
                        }
                    }
                    WifiTracker wifiTracker2 = WifiTracker.this;
                    int intExtra = intent.getIntExtra("wifi_state", 4);
                    wifiTracker2.getClass();
                    if (WifiTracker.isVerboseLoggingEnabled()) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                intExtra, "updateWifiState: ", "WifiTracker");
                    }
                    if (intExtra == 3) {
                        synchronized (wifiTracker2.mLock) {
                            Scanner scanner = wifiTracker2.mScanner;
                            if (scanner != null) {
                                if (WifiTracker.isVerboseLoggingEnabled()) {
                                    Log.d("WifiTracker", "Scanner resume");
                                }
                                if (!scanner.hasMessages(0)) {
                                    scanner.sendEmptyMessage(0);
                                }
                            }
                        }
                    } else {
                        wifiTracker2.clearAccessPointsAndConditionallyUpdate();
                        wifiTracker2.mLastInfo = null;
                        wifiTracker2.mLastNetworkInfo = null;
                        synchronized (wifiTracker2.mLock) {
                            Scanner scanner2 = wifiTracker2.mScanner;
                            if (scanner2 != null) {
                                if (WifiTracker.isVerboseLoggingEnabled()) {
                                    Log.d("WifiTracker", "Scanner pause");
                                }
                                scanner2.mRetry = 0;
                                scanner2.removeMessages(0);
                            }
                        }
                        wifiTracker2.mStaleScanResults = true;
                    }
                    WifiListenerExecutor wifiListenerExecutor = wifiTracker2.mListener;
                    wifiListenerExecutor.getClass();
                    ThreadUtils.postOnMainThread(
                            new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda3(
                                    wifiListenerExecutor,
                                    String.format(
                                            "Invoking onWifiStateChanged callback with state %d",
                                            Integer.valueOf(intExtra)),
                                    new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda1(
                                            wifiListenerExecutor, intExtra)));
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class Scanner extends Handler {
        public int mRetry = 0;

        public Scanner() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            WifiTracker wifiTracker = WifiTracker.this;
            if (wifiTracker.mWifiManager.startScan()) {
                this.mRetry = 0;
            } else {
                int i = this.mRetry + 1;
                this.mRetry = i;
                if (i >= 3) {
                    this.mRetry = 0;
                    Context context = wifiTracker.mContext;
                    if (context != null) {
                        Toast.makeText(context, R.string.wifi_fail_to_scan, 1).show();
                        return;
                    }
                    return;
                }
            }
            sendEmptyMessageDelayed(0, 10000L);
        }

        public boolean isScanning() {
            return hasMessages(0);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface WifiListener {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class WifiListenerExecutor implements WifiListener {
        public final WifiListener mDelegatee;

        public WifiListenerExecutor(WifiListener wifiListener) {
            this.mDelegatee = wifiListener;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiTrackerNetworkCallback extends ConnectivityManager.NetworkCallback {
        public WifiTrackerNetworkCallback() {}

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(
                Network network, NetworkCapabilities networkCapabilities) {
            if (network.equals(WifiTracker.this.mWifiManager.getCurrentNetwork())) {
                WifiTracker.m1054$$Nest$mupdateNetworkInfo(WifiTracker.this, null);
            }
        }
    }

    /* renamed from: -$$Nest$mupdateNetworkInfo, reason: not valid java name */
    public static void m1054$$Nest$mupdateNetworkInfo(
            WifiTracker wifiTracker, NetworkInfo networkInfo) {
        WifiManager wifiManager = wifiTracker.mWifiManager;
        if (wifiManager == null || !wifiManager.isWifiEnabled()) {
            wifiTracker.clearAccessPointsAndConditionallyUpdate();
            return;
        }
        if (networkInfo != null) {
            wifiTracker.mLastNetworkInfo = networkInfo;
            if (Log.isLoggable("WifiTracker", 3)) {
                Log.d("WifiTracker", "mLastNetworkInfo set: " + wifiTracker.mLastNetworkInfo);
            }
            if (networkInfo.isConnected()
                    != wifiTracker.mConnected.getAndSet(networkInfo.isConnected())) {
                WifiListenerExecutor wifiListenerExecutor = wifiTracker.mListener;
                WifiListener wifiListener = wifiListenerExecutor.mDelegatee;
                Objects.requireNonNull(wifiListener);
                ThreadUtils.postOnMainThread(
                        new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda3(
                                wifiListenerExecutor,
                                "Invoking onConnectedChanged callback",
                                new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(
                                        wifiListener, 1)));
            }
        }
        wifiTracker.mLastInfo = wifiTracker.mWifiManager.getConnectionInfo();
        if (Log.isLoggable("WifiTracker", 3)) {
            Log.d("WifiTracker", "mLastInfo set as: " + wifiTracker.mLastInfo);
        }
        WifiInfo wifiInfo = wifiTracker.mLastInfo;
        WifiConfiguration wifiConfiguration = null;
        if (wifiInfo != null) {
            int networkId = wifiInfo.getNetworkId();
            List<WifiConfiguration> configuredNetworks =
                    wifiTracker.mWifiManager.getConfiguredNetworks();
            if (configuredNetworks != null) {
                Iterator<WifiConfiguration> it = configuredNetworks.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    WifiConfiguration next = it.next();
                    if (wifiTracker.mLastInfo != null && networkId == next.networkId) {
                        wifiConfiguration = next;
                        break;
                    }
                }
            }
        }
        synchronized (wifiTracker.mLock) {
            try {
                boolean z = false;
                boolean z2 = false;
                for (int size = ((ArrayList) wifiTracker.mInternalAccessPoints).size() - 1;
                        size >= 0;
                        size--) {
                    AccessPoint accessPoint =
                            (AccessPoint) ((ArrayList) wifiTracker.mInternalAccessPoints).get(size);
                    boolean isActive = accessPoint.isActive();
                    if (accessPoint.update(
                            wifiConfiguration,
                            wifiTracker.mLastInfo,
                            wifiTracker.mLastNetworkInfo)) {
                        if (isActive != accessPoint.isActive()) {
                            z = true;
                            z2 = true;
                        } else {
                            z2 = true;
                        }
                    }
                    if (accessPoint.update(
                            wifiTracker.mScoreCache,
                            wifiTracker.mNetworkScoringUiEnabled,
                            wifiTracker.mMaxSpeedLabelScoreCacheAge)) {
                        z = true;
                        z2 = true;
                    }
                }
                if (z) {
                    Collections.sort(wifiTracker.mInternalAccessPoints);
                }
                if (z2 && !wifiTracker.mStaleScanResults) {
                    WifiListenerExecutor wifiListenerExecutor2 = wifiTracker.mListener;
                    WifiListener wifiListener2 = wifiListenerExecutor2.mDelegatee;
                    Objects.requireNonNull(wifiListener2);
                    ThreadUtils.postOnMainThread(
                            new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda3(
                                    wifiListenerExecutor2,
                                    "Invoking onAccessPointsChanged callback",
                                    new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(
                                            wifiListener2, 0)));
                }
            } finally {
            }
        }
    }

    public WifiTracker(
            Context context,
            WifiListener wifiListener,
            WifiManager wifiManager,
            ConnectivityManager connectivityManager,
            NetworkScoreManager networkScoreManager,
            IntentFilter intentFilter) {
        boolean z = false;
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mListener = new WifiListenerExecutor(wifiListener);
        this.mConnectivityManager = connectivityManager;
        if (wifiManager != null && wifiManager.isVerboseLoggingEnabled()) {
            z = true;
        }
        sVerboseLogging = z;
        this.mFilter = intentFilter;
        this.mNetworkRequest =
                new NetworkRequest.Builder()
                        .clearCapabilities()
                        .addCapability(15)
                        .addTransportType(1)
                        .build();
        this.mNetworkScoreManager = networkScoreManager;
        HandlerThread handlerThread =
                new HandlerThread(
                        "WifiTracker{" + Integer.toHexString(System.identityHashCode(this)) + "}",
                        10);
        handlerThread.start();
        setWorkThread(handlerThread);
    }

    public static AccessPoint getCachedByKey(String str, List list) {
        ListIterator listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            AccessPoint accessPoint = (AccessPoint) listIterator.next();
            if (accessPoint.mKey.equals(str)) {
                listIterator.remove();
                return accessPoint;
            }
        }
        return null;
    }

    public static boolean isVerboseLoggingEnabled() {
        return sVerboseLogging || Log.isLoggable("WifiTracker", 2);
    }

    public final void clearAccessPointsAndConditionallyUpdate() {
        synchronized (this.mLock) {
            try {
                if (!((ArrayList) this.mInternalAccessPoints).isEmpty()) {
                    ((ArrayList) this.mInternalAccessPoints).clear();
                    if (!this.mStaleScanResults) {
                        WifiListenerExecutor wifiListenerExecutor = this.mListener;
                        WifiListener wifiListener = wifiListenerExecutor.mDelegatee;
                        Objects.requireNonNull(wifiListener);
                        ThreadUtils.postOnMainThread(
                                new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda3(
                                        wifiListenerExecutor,
                                        "Invoking onAccessPointsChanged callback",
                                        new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(
                                                wifiListener, 0)));
                    }
                }
            } finally {
            }
        }
    }

    public final void fetchScansAndConfigsAndUpdateAccessPoints() {
        ArrayList arrayList;
        WifiConfiguration wifiConfiguration;
        List<ScanResult> scanResults = this.mWifiManager.getScanResults();
        if (scanResults == null) {
            arrayList = null;
        } else {
            boolean isEnhancedOpenSupported = this.mWifiManager.isEnhancedOpenSupported();
            boolean isWpa3SaeSupported = this.mWifiManager.isWpa3SaeSupported();
            boolean isWpa3SuiteBSupported = this.mWifiManager.isWpa3SuiteBSupported();
            arrayList = new ArrayList();
            for (ScanResult scanResult : scanResults) {
                if (scanResult.capabilities.contains("PSK")) {
                    arrayList.add(scanResult);
                } else if ((!scanResult.capabilities.contains("SUITE_B_192")
                                || isWpa3SuiteBSupported)
                        && ((!scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE)
                                        || isWpa3SaeSupported)
                                && (!scanResult.capabilities.contains("OWE")
                                        || isEnhancedOpenSupported))) {
                    arrayList.add(scanResult);
                } else if (isVerboseLoggingEnabled()) {
                    Log.v(
                            "WifiTracker",
                            "filterScanResultsByCapabilities: Filtering SSID "
                                    + scanResult.SSID
                                    + " with capabilities: "
                                    + scanResult.capabilities);
                }
            }
        }
        if (isVerboseLoggingEnabled()) {
            Log.i("WifiTracker", "Fetched scan results: " + arrayList);
        }
        List<WifiConfiguration> configuredNetworks = this.mWifiManager.getConfiguredNetworks();
        WifiInfo wifiInfo = this.mLastInfo;
        if (wifiInfo != null) {
            int networkId = wifiInfo.getNetworkId();
            if (configuredNetworks != null) {
                Iterator<WifiConfiguration> it = configuredNetworks.iterator();
                while (it.hasNext()) {
                    wifiConfiguration = it.next();
                    if (this.mLastInfo != null && networkId == wifiConfiguration.networkId) {
                        break;
                    }
                }
            }
        }
        wifiConfiguration = null;
        synchronized (this.mLock) {
            try {
                ArrayMap updateScanResultCache = updateScanResultCache(arrayList);
                List<AccessPoint> arrayList2 = new ArrayList<>(this.mInternalAccessPoints);
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                for (Map.Entry entry : updateScanResultCache.entrySet()) {
                    Iterator it2 = ((List) entry.getValue()).iterator();
                    while (it2.hasNext()) {
                        NetworkKey createFromScanResult =
                                NetworkKey.createFromScanResult((ScanResult) it2.next());
                        if (createFromScanResult != null
                                && !((ArraySet) this.mRequestedScores)
                                        .contains(createFromScanResult)) {
                            arrayList4.add(createFromScanResult);
                        }
                    }
                    List list = (List) entry.getValue();
                    Context context = this.mContext;
                    ScanResult scanResult2 = (ScanResult) list.get(0);
                    int i = AccessPoint.$r8$clinit;
                    final AccessPoint cachedByKey =
                            getCachedByKey(
                                    AccessPoint.getKey(
                                            AccessPoint.getSecurity(context, scanResult2),
                                            scanResult2.SSID,
                                            scanResult2.BSSID),
                                    arrayList2);
                    if (cachedByKey == null) {
                        cachedByKey = new AccessPoint(this.mContext, list);
                    } else {
                        cachedByKey.setScanResults(list);
                    }
                    List list2 =
                            (List)
                                    configuredNetworks.stream()
                                            .filter(
                                                    new Predicate() { // from class:
                                                                      // com.android.settingslib.wifi.WifiTracker$$ExternalSyntheticLambda0
                                                        @Override // java.util.function.Predicate
                                                        public final boolean test(Object obj) {
                                                            return AccessPoint.this.matches(
                                                                    (WifiConfiguration) obj);
                                                        }
                                                    })
                                            .collect(Collectors.toList());
                    int size = list2.size();
                    if (size == 0) {
                        cachedByKey.update(null);
                    } else if (size == 1) {
                        cachedByKey.update((WifiConfiguration) list2.get(0));
                    } else {
                        Optional findFirst =
                                list2.stream()
                                        .filter(new WifiTracker$$ExternalSyntheticLambda1())
                                        .findFirst();
                        if (findFirst.isPresent()) {
                            cachedByKey.update((WifiConfiguration) findFirst.get());
                        } else {
                            cachedByKey.update((WifiConfiguration) list2.get(0));
                        }
                    }
                    arrayList3.add(cachedByKey);
                }
                ArrayList arrayList5 = new ArrayList(this.mScanResultCache.values());
                arrayList3.addAll(
                        updatePasspointAccessPoints(
                                this.mWifiManager.getAllMatchingWifiConfigs(arrayList5),
                                arrayList2));
                arrayList3.addAll(
                        updateOsuAccessPoints(
                                this.mWifiManager.getMatchingOsuProviders(arrayList5), arrayList2));
                if (this.mLastInfo != null && this.mLastNetworkInfo != null) {
                    Iterator it3 = arrayList3.iterator();
                    while (it3.hasNext()) {
                        ((AccessPoint) it3.next())
                                .update(wifiConfiguration, this.mLastInfo, this.mLastNetworkInfo);
                    }
                }
                if (arrayList3.isEmpty() && wifiConfiguration != null) {
                    AccessPoint accessPoint = new AccessPoint(this.mContext, wifiConfiguration);
                    accessPoint.update(wifiConfiguration, this.mLastInfo, this.mLastNetworkInfo);
                    arrayList3.add(accessPoint);
                    arrayList4.add(NetworkKey.createFromWifiInfo(this.mLastInfo));
                }
                requestScoresForNetworkKeys(arrayList4);
                Iterator it4 = arrayList3.iterator();
                while (it4.hasNext()) {
                    ((AccessPoint) it4.next())
                            .update(
                                    this.mScoreCache,
                                    this.mNetworkScoringUiEnabled,
                                    this.mMaxSpeedLabelScoreCacheAge);
                }
                Collections.sort(arrayList3);
                if (Log.isLoggable("WifiTracker", 3)) {
                    Log.d(
                            "WifiTracker",
                            "------ Dumping AccessPoints that were not seen on this scan ------");
                    Iterator it5 = ((ArrayList) this.mInternalAccessPoints).iterator();
                    while (it5.hasNext()) {
                        String title = ((AccessPoint) it5.next()).getTitle();
                        Iterator it6 = arrayList3.iterator();
                        while (true) {
                            if (!it6.hasNext()) {
                                Log.d("WifiTracker", "Did not find " + title + " in this scan");
                                break;
                            }
                            AccessPoint accessPoint2 = (AccessPoint) it6.next();
                            if (accessPoint2.getTitle() == null
                                    || !accessPoint2.getTitle().equals(title)) {}
                        }
                    }
                    Log.d(
                            "WifiTracker",
                            "---- Done dumping AccessPoints that were not seen on this scan ----");
                }
                ((ArrayList) this.mInternalAccessPoints).clear();
                ((ArrayList) this.mInternalAccessPoints).addAll(arrayList3);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.mStaleScanResults) {
            return;
        }
        WifiListenerExecutor wifiListenerExecutor = this.mListener;
        WifiListener wifiListener = wifiListenerExecutor.mDelegatee;
        Objects.requireNonNull(wifiListener);
        ThreadUtils.postOnMainThread(
                new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda3(
                        wifiListenerExecutor,
                        "Invoking onAccessPointsChanged callback",
                        new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(
                                wifiListener, 0)));
    }

    public void forceUpdate() {
        this.mLastInfo = this.mWifiManager.getConnectionInfo();
        this.mLastNetworkInfo =
                this.mConnectivityManager.getNetworkInfo(this.mWifiManager.getCurrentNetwork());
        fetchScansAndConfigsAndUpdateAccessPoints();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {
        this.mWorkThread.quit();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        forceUpdate();
        this.mNetworkScoreManager.registerNetworkScoreCache(1, this.mScoreCache, 2);
        this.mNetworkScoringUiEnabled =
                Settings.Global.getInt(
                                this.mContext.getContentResolver(), "network_scoring_ui_enabled", 0)
                        == 1;
        this.mMaxSpeedLabelScoreCacheAge =
                Settings.Global.getLong(
                        this.mContext.getContentResolver(),
                        "speed_label_cache_eviction_age_millis",
                        1200000L);
        synchronized (this.mLock) {
            try {
                if (this.mScanner == null) {
                    this.mScanner = new Scanner();
                }
                WifiManager wifiManager = this.mWifiManager;
                if (wifiManager != null && wifiManager.isWifiEnabled()) {
                    Scanner scanner = this.mScanner;
                    scanner.getClass();
                    if (isVerboseLoggingEnabled()) {
                        Log.d("WifiTracker", "Scanner resume");
                    }
                    if (!scanner.hasMessages(0)) {
                        scanner.sendEmptyMessage(0);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.mRegistered) {
            return;
        }
        this.mContext.registerReceiver(this.mReceiver, this.mFilter, null, this.mWorkHandler, 2);
        WifiTrackerNetworkCallback wifiTrackerNetworkCallback = new WifiTrackerNetworkCallback();
        this.mNetworkCallback = wifiTrackerNetworkCallback;
        this.mConnectivityManager.registerNetworkCallback(
                this.mNetworkRequest, wifiTrackerNetworkCallback, this.mWorkHandler);
        this.mRegistered = true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        if (this.mRegistered) {
            this.mContext.unregisterReceiver(this.mReceiver);
            this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
            this.mRegistered = false;
        }
        this.mNetworkScoreManager.unregisterNetworkScoreCache(1, this.mScoreCache);
        synchronized (this.mLock) {
            ((ArraySet) this.mRequestedScores).clear();
        }
        synchronized (this.mLock) {
            try {
                Scanner scanner = this.mScanner;
                if (scanner != null) {
                    if (isVerboseLoggingEnabled()) {
                        Log.d("WifiTracker", "Scanner pause");
                    }
                    scanner.mRetry = 0;
                    scanner.removeMessages(0);
                    this.mScanner = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mStaleScanResults = true;
        this.mWorkHandler.removeCallbacksAndMessages(null);
    }

    public final void requestScoresForNetworkKeys(Collection collection) {
        ArrayList arrayList = (ArrayList) collection;
        if (arrayList.isEmpty()) {
            return;
        }
        if (Log.isLoggable("WifiTracker", 3)) {
            Log.d("WifiTracker", "Requesting scores for Network Keys: " + collection);
        }
        this.mNetworkScoreManager.requestScores(
                (NetworkKey[]) arrayList.toArray(new NetworkKey[arrayList.size()]));
        synchronized (this.mLock) {
            ((ArraySet) this.mRequestedScores).addAll(collection);
        }
    }

    public void setWorkThread(HandlerThread handlerThread) {
        this.mWorkThread = handlerThread;
        this.mWorkHandler = new Handler(handlerThread.getLooper());
        this.mScoreCache =
                new WifiNetworkScoreCache(
                        this.mContext,
                        new WifiNetworkScoreCache.CacheListener(this.mWorkHandler) { // from class:
                            // com.android.settingslib.wifi.WifiTracker.1
                            public final void networkCacheUpdated(List list) {
                                if (WifiTracker.this.mRegistered) {
                                    if (Log.isLoggable("WifiTracker", 2)) {
                                        Log.v(
                                                "WifiTracker",
                                                "Score cache was updated with networks: " + list);
                                    }
                                    WifiTracker wifiTracker = WifiTracker.this;
                                    synchronized (wifiTracker.mLock) {
                                        boolean z = false;
                                        for (int i = 0;
                                                i
                                                        < ((ArrayList)
                                                                        wifiTracker
                                                                                .mInternalAccessPoints)
                                                                .size();
                                                i++) {
                                            try {
                                                if (((AccessPoint)
                                                                ((ArrayList)
                                                                                wifiTracker
                                                                                        .mInternalAccessPoints)
                                                                        .get(i))
                                                        .update(
                                                                wifiTracker.mScoreCache,
                                                                wifiTracker
                                                                        .mNetworkScoringUiEnabled,
                                                                wifiTracker
                                                                        .mMaxSpeedLabelScoreCacheAge)) {
                                                    z = true;
                                                }
                                            } finally {
                                            }
                                        }
                                        if (z) {
                                            Collections.sort(wifiTracker.mInternalAccessPoints);
                                            if (!wifiTracker.mStaleScanResults) {
                                                WifiListenerExecutor wifiListenerExecutor =
                                                        wifiTracker.mListener;
                                                WifiListener wifiListener =
                                                        wifiListenerExecutor.mDelegatee;
                                                Objects.requireNonNull(wifiListener);
                                                ThreadUtils.postOnMainThread(
                                                        new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda3(
                                                                wifiListenerExecutor,
                                                                "Invoking onAccessPointsChanged"
                                                                    + " callback",
                                                                new WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0(
                                                                        wifiListener, 0)));
                                            }
                                        }
                                    }
                                }
                            }
                        });
    }

    public List<AccessPoint> updateOsuAccessPoints(
            Map<OsuProvider, List<ScanResult>> map, List<AccessPoint> list) {
        ArrayList arrayList = new ArrayList();
        Set keySet =
                this.mWifiManager.getMatchingPasspointConfigsForOsuProviders(map.keySet()).keySet();
        for (OsuProvider osuProvider : map.keySet()) {
            if (!keySet.contains(osuProvider)) {
                List<ScanResult> list2 = map.get(osuProvider);
                int i = AccessPoint.$r8$clinit;
                AccessPoint cachedByKey =
                        getCachedByKey(
                                "OSU:"
                                        + osuProvider.getFriendlyName()
                                        + ','
                                        + osuProvider.getServerUri(),
                                list);
                if (cachedByKey == null) {
                    cachedByKey = new AccessPoint(this.mContext, osuProvider, list2);
                } else {
                    cachedByKey.setScanResults(list2);
                }
                arrayList.add(cachedByKey);
            }
        }
        return arrayList;
    }

    public List<AccessPoint> updatePasspointAccessPoints(
            List<Pair<WifiConfiguration, Map<Integer, List<ScanResult>>>> list,
            List<AccessPoint> list2) {
        ArrayList arrayList = new ArrayList();
        ArraySet arraySet = new ArraySet();
        for (Pair<WifiConfiguration, Map<Integer, List<ScanResult>>> pair : list) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            if (arraySet.add(wifiConfiguration.FQDN)) {
                List list3 = (List) ((Map) pair.second).get(0);
                List list4 = (List) ((Map) pair.second).get(1);
                int i = AccessPoint.$r8$clinit;
                AccessPoint cachedByKey =
                        getCachedByKey(
                                wifiConfiguration.isPasspoint()
                                        ? AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0
                                                .m("PASSPOINT:", wifiConfiguration.getKey())
                                        : AccessPoint.getKey(
                                                AccessPoint.getSecurity(wifiConfiguration),
                                                AccessPoint.removeDoubleQuotes(
                                                        wifiConfiguration.SSID),
                                                wifiConfiguration.BSSID),
                                list2);
                if (cachedByKey == null) {
                    cachedByKey = new AccessPoint(this.mContext, wifiConfiguration, list3, list4);
                } else {
                    cachedByKey.update(wifiConfiguration);
                    cachedByKey.setScanResultsPasspoint(list3, list4);
                }
                arrayList.add(cachedByKey);
            }
        }
        return arrayList;
    }

    public final ArrayMap updateScanResultCache(List list) {
        List list2;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ScanResult scanResult = (ScanResult) it.next();
            String str = scanResult.SSID;
            if (str != null && !str.isEmpty()) {
                this.mScanResultCache.put(scanResult.BSSID, scanResult);
            }
        }
        long j = this.mLastScanSucceeded ? MAX_SCAN_RESULT_AGE_MILLIS : 30000L;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Iterator it2 = this.mScanResultCache.values().iterator();
        while (it2.hasNext()) {
            if (elapsedRealtime - (((ScanResult) it2.next()).timestamp / 1000) > j) {
                it2.remove();
            }
        }
        ArrayMap arrayMap = new ArrayMap();
        for (ScanResult scanResult2 : this.mScanResultCache.values()) {
            String str2 = scanResult2.SSID;
            if (str2 != null
                    && str2.length() != 0
                    && !scanResult2.capabilities.contains("[IBSS]")) {
                Context context = this.mContext;
                int i = AccessPoint.$r8$clinit;
                String key =
                        AccessPoint.getKey(
                                AccessPoint.getSecurity(context, scanResult2),
                                scanResult2.SSID,
                                scanResult2.BSSID);
                if (arrayMap.containsKey(key)) {
                    list2 = (List) arrayMap.get(key);
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayMap.put(key, arrayList);
                    list2 = arrayList;
                }
                list2.add(scanResult2);
            }
        }
        return arrayMap;
    }
}
