package com.samsung.android.wifitrackerlib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import androidx.core.util.Preconditions;

import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.android.wifitrackerlib.PasspointWifiEntry;
import com.android.wifitrackerlib.SavedNetworkTracker$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.ScanResultUpdater;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiTrackerInjector;

import com.sec.ims.IMSParameter;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SavedScannedTracker extends BaseWifiTracker {
    public WifiEntry mConnectedWifiEntry;
    public final SavedScannedTrackerCallback mListener;
    public final Object mLock;
    public final Map mPasspointWifiEntryCache;
    public final List mSavedWifiEntries;
    public final List mStandardWifiEntryCache;
    public final List mSubscriptionWifiEntries;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface SavedScannedTrackerCallback extends BaseWifiTracker.BaseWifiTrackerCallback {
        void onSavedWifiEntriesChanged();

        void onSubscriptionWifiEntriesChanged();
    }

    public SavedScannedTracker(
            Lifecycle lifecycle,
            Context context,
            WifiManager wifiManager,
            ConnectivityManager connectivityManager,
            Handler handler,
            Handler handler2,
            Clock clock,
            SavedScannedTrackerCallback savedScannedTrackerCallback) {
        super(
                new WifiTrackerInjector(context),
                lifecycle,
                context,
                wifiManager,
                connectivityManager,
                handler,
                handler2,
                clock,
                15000L,
                10000L,
                savedScannedTrackerCallback,
                "SavedScannedTracker");
        this.mLock = new Object();
        this.mSavedWifiEntries = new ArrayList();
        this.mSubscriptionWifiEntries = new ArrayList();
        this.mStandardWifiEntryCache = new ArrayList();
        this.mPasspointWifiEntryCache = new HashMap();
        this.mListener = savedScannedTrackerCallback;
        handleOnStart();
    }

    public final void conditionallyUpdateScanResults$4(boolean z) {
        if (this.mWifiManager.getWifiState() == 1) {
            updateStandardWifiEntryScans$2(Collections.emptyList());
            updatePasspointWifiEntryScans$3(Collections.emptyList());
            return;
        }
        ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
        long j = this.mMaxScanAgeMillis;
        if (z) {
            scanResultUpdater.update(this.mWifiManager.getScanResults());
        } else {
            j += this.mScanIntervalMillis;
        }
        updateStandardWifiEntryScans$2(scanResultUpdater.getScanResults(j));
        updatePasspointWifiEntryScans$3(scanResultUpdater.getScanResults(j));
    }

    public final List getAllWifiEntries$2() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mStandardWifiEntryCache);
        arrayList.addAll(this.mPasspointWifiEntryCache.values());
        return arrayList;
    }

    public final WifiEntry getConnectedWifiEntry() {
        WifiEntry wifiEntry;
        synchronized (this.mLock) {
            wifiEntry = this.mConnectedWifiEntry;
        }
        return wifiEntry;
    }

    public final List getSavedWifiEntries() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mSavedWifiEntries);
        }
        return arrayList;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConfiguredNetworksChangedAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        updateStandardWifiEntryConfigs$1(this.mWifiManager.getConfiguredNetworks());
        updatePasspointWifiEntryConfigs$1(this.mWifiManager.getPasspointConfigurations());
        updateWifiEntries$1$1();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        WifiEntry wifiEntry = this.mConnectedWifiEntry;
        if (wifiEntry == null || wifiEntry.getConnectedState() != 2) {
            return;
        }
        this.mConnectedWifiEntry.updateLinkProperties(network, linkProperties);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        Iterator it = ((ArrayList) getAllWifiEntries$2()).iterator();
        while (it.hasNext()) {
            WifiEntry wifiEntry = (WifiEntry) it.next();
            wifiEntry.onNetworkCapabilitiesChanged(network, networkCapabilities);
            if (wifiEntry.getConnectedState() != 0) {
                this.mConnectedWifiEntry = wifiEntry;
            }
        }
        updateWifiEntries$1$1();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkLost(Network network) {
        Iterator it = ((ArrayList) getAllWifiEntries$2()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onNetworkLost(network);
        }
        updateWifiEntries$1$1();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkStateChangedAction(Intent intent) {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        NetworkInfo networkInfo =
                (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
        if (connectionInfo == null || networkInfo == null) {
            return;
        }
        Iterator it = ((ArrayList) getAllWifiEntries$2()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onPrimaryWifiInfoChanged(connectionInfo, networkInfo);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleOnStart() {
        ((ArrayList) this.mStandardWifiEntryCache).clear();
        ((HashMap) this.mPasspointWifiEntryCache).clear();
        updateStandardWifiEntryConfigs$1(this.mWifiManager.getConfiguredNetworks());
        updatePasspointWifiEntryConfigs$1(this.mWifiManager.getPasspointConfigurations());
        this.mScanResultUpdater.update(this.mWifiManager.getScanResults());
        conditionallyUpdateScanResults$4(true);
        Network currentNetwork = this.mWifiManager.getCurrentNetwork();
        if (currentNetwork != null) {
            NetworkCapabilities networkCapabilities =
                    this.mConnectivityManager.getNetworkCapabilities(currentNetwork);
            if (networkCapabilities != null) {
                handleNetworkCapabilitiesChanged(
                        currentNetwork,
                        new NetworkCapabilities.Builder(networkCapabilities)
                                .setTransportInfo(this.mWifiManager.getConnectionInfo())
                                .build());
            }
            LinkProperties linkProperties =
                    this.mConnectivityManager.getLinkProperties(currentNetwork);
            if (linkProperties != null) {
                handleLinkPropertiesChanged(currentNetwork, linkProperties);
            }
        }
        updateWifiEntries$1$1();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleScanResultsAvailableAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        conditionallyUpdateScanResults$4(intent.getBooleanExtra("resultsUpdated", true));
        updateWifiEntries$1$1();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleWifiStateChangedAction() {
        conditionallyUpdateScanResults$4(true);
        updateWifiEntries$1$1();
    }

    public final void updatePasspointWifiEntryConfigs$1(List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        Map map =
                (Map)
                        list.stream()
                                .collect(
                                        Collectors.toMap(
                                                new SavedScannedTracker$$ExternalSyntheticLambda0(),
                                                Function.identity()));
        ((HashMap) this.mPasspointWifiEntryCache)
                .entrySet()
                .removeIf(new SavedScannedTracker$$ExternalSyntheticLambda1(0, map));
        for (Map.Entry entry : map.entrySet()) {
            Map map2 = this.mPasspointWifiEntryCache;
            HashMap hashMap = (HashMap) map2;
            hashMap.put(
                    (String) entry.getKey(),
                    new PasspointWifiEntry(
                            this.mInjector,
                            this.mMainHandler,
                            (PasspointConfiguration) map.get(entry.getKey()),
                            this.mWifiManager,
                            true));
        }
    }

    public final void updatePasspointWifiEntryScans$3(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        TreeSet treeSet = new TreeSet();
        for (Pair pair : this.mWifiManager.getAllMatchingWifiConfigs(list)) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            String uniqueIdToPasspointWifiEntryKey =
                    PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey());
            treeSet.add(uniqueIdToPasspointWifiEntryKey);
            if (((HashMap) this.mPasspointWifiEntryCache)
                    .containsKey(uniqueIdToPasspointWifiEntryKey)) {
                ((PasspointWifiEntry)
                                ((HashMap) this.mPasspointWifiEntryCache)
                                        .get(uniqueIdToPasspointWifiEntryKey))
                        .updateScanResultInfo(
                                wifiConfiguration,
                                (List) ((Map) pair.second).get(0),
                                (List) ((Map) pair.second).get(1));
            }
        }
        for (PasspointWifiEntry passpointWifiEntry :
                ((HashMap) this.mPasspointWifiEntryCache).values()) {
            if (!treeSet.contains(passpointWifiEntry.mKey)) {
                passpointWifiEntry.updateScanResultInfo(null, null, null);
            }
        }
    }

    public final void updateStandardWifiEntryConfigs$1(List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        Map map =
                (Map)
                        list.stream()
                                .filter(new SavedScannedTracker$$ExternalSyntheticLambda3())
                                .collect(
                                        Collectors.groupingBy(
                                                new SavedNetworkTracker$$ExternalSyntheticLambda0(
                                                        1)));
        ((ArrayList) this.mStandardWifiEntryCache)
                .removeIf(new SavedScannedTracker$$ExternalSyntheticLambda1(1, map));
        for (Map.Entry entry : map.entrySet()) {
            List list2 = this.mStandardWifiEntryCache;
            StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey =
                    (StandardWifiEntry.StandardWifiEntryKey) entry.getKey();
            List list3 = (List) map.get(entry.getKey());
            WifiManager wifiManager = this.mWifiManager;
            ArrayList arrayList = (ArrayList) list2;
            arrayList.add(
                    new StandardWifiEntry(
                            this.mInjector,
                            this.mMainHandler,
                            standardWifiEntryKey,
                            list3,
                            null,
                            wifiManager,
                            true));
        }
    }

    public final void updateStandardWifiEntryScans$2(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        final Map map =
                (Map)
                        list.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                new SavedNetworkTracker$$ExternalSyntheticLambda0(
                                                        5)));
        ((ArrayList) this.mStandardWifiEntryCache)
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.wifitrackerlib.SavedScannedTracker$$ExternalSyntheticLambda6
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                                standardWifiEntry.updateScanResultInfo(
                                        (List) map.get(standardWifiEntry.mKey.mScanResultKey));
                            }
                        });
    }

    public final void updateWifiEntries$1$1() {
        synchronized (this.mLock) {
            try {
                this.mConnectedWifiEntry = null;
                for (WifiEntry wifiEntry : this.mStandardWifiEntryCache) {
                    if (wifiEntry.getConnectedState() != 0) {
                        this.mConnectedWifiEntry = wifiEntry;
                    }
                }
                for (WifiEntry wifiEntry2 : this.mSubscriptionWifiEntries) {
                    if (wifiEntry2.getConnectedState() != 0) {
                        this.mConnectedWifiEntry = wifiEntry2;
                    }
                }
                this.mSavedWifiEntries.clear();
                this.mSavedWifiEntries.addAll(this.mStandardWifiEntryCache);
                List list = this.mSavedWifiEntries;
                Comparator comparator = WifiEntry.TITLE_COMPARATOR;
                Collections.sort(list, comparator);
                this.mSubscriptionWifiEntries.clear();
                this.mSubscriptionWifiEntries.addAll(this.mPasspointWifiEntryCache.values());
                Collections.sort(this.mSubscriptionWifiEntries, comparator);
                if (BaseWifiTracker.sVerboseLogging) {
                    Log.v(
                            "SavedScannedTracker",
                            "Updated SavedWifiEntries: "
                                    + Arrays.toString(this.mSavedWifiEntries.toArray()));
                    Log.v(
                            "SavedScannedTracker",
                            "Updated SubscriptionWifiEntries: "
                                    + Arrays.toString(this.mSubscriptionWifiEntries.toArray()));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        final SavedScannedTrackerCallback savedScannedTrackerCallback = this.mListener;
        if (savedScannedTrackerCallback != null) {
            final int i = 1;
            this.mMainHandler.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.wifitrackerlib.SavedScannedTracker$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i2 = i;
                            SavedScannedTracker.SavedScannedTrackerCallback
                                    savedScannedTrackerCallback2 = savedScannedTrackerCallback;
                            switch (i2) {
                                case 0:
                                    savedScannedTrackerCallback2.onSubscriptionWifiEntriesChanged();
                                    break;
                                default:
                                    savedScannedTrackerCallback2.onSavedWifiEntriesChanged();
                                    break;
                            }
                        }
                    });
        }
        final SavedScannedTrackerCallback savedScannedTrackerCallback2 = this.mListener;
        if (savedScannedTrackerCallback2 != null) {
            final int i2 = 0;
            this.mMainHandler.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.wifitrackerlib.SavedScannedTracker$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i22 = i2;
                            SavedScannedTracker.SavedScannedTrackerCallback
                                    savedScannedTrackerCallback22 = savedScannedTrackerCallback2;
                            switch (i22) {
                                case 0:
                                    savedScannedTrackerCallback22
                                            .onSubscriptionWifiEntriesChanged();
                                    break;
                                default:
                                    savedScannedTrackerCallback22.onSavedWifiEntriesChanged();
                                    break;
                            }
                        }
                    });
        }
    }
}
