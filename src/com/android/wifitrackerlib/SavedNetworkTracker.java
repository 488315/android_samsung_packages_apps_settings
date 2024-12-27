package com.android.wifitrackerlib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityDiagnosticsManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Handler;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;

import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;

import com.sec.ims.IMSParameter;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SavedNetworkTracker extends BaseWifiTracker {
    public final SavedNetworkTrackerCallback mListener;
    public final Object mLock;
    public final Map mPasspointWifiEntryCache;
    public final List mSavedWifiEntries;
    public final List mStandardWifiEntryCache;
    public final List mSubscriptionWifiEntries;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface SavedNetworkTrackerCallback extends BaseWifiTracker.BaseWifiTrackerCallback {
        void onSavedWifiEntriesChanged();

        void onSubscriptionWifiEntriesChanged();
    }

    public SavedNetworkTracker(
            Lifecycle lifecycle,
            Context context,
            WifiManager wifiManager,
            ConnectivityManager connectivityManager,
            Handler handler,
            Handler handler2,
            Clock clock,
            SavedNetworkTrackerCallback savedNetworkTrackerCallback) {
        this(
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
                savedNetworkTrackerCallback);
    }

    public static boolean isCertificateUsedByConfiguration(
            WifiConfiguration wifiConfiguration, String str) {
        WifiEnterpriseConfig wifiEnterpriseConfig;
        if (TextUtils.isEmpty(str)
                || wifiConfiguration == null
                || (wifiEnterpriseConfig = wifiConfiguration.enterpriseConfig) == null
                || !wifiEnterpriseConfig.isEapMethodServerCertUsed()) {
            return false;
        }
        if (wifiEnterpriseConfig.getCaCertificateAliases() == null
                && wifiEnterpriseConfig.getCaCertificates() == null
                && TextUtils.isEmpty(wifiEnterpriseConfig.getCaPath())
                && TextUtils.isEmpty(wifiEnterpriseConfig.getClientCertificateAlias())) {
            return false;
        }
        String[] caCertificateAliases = wifiEnterpriseConfig.getCaCertificateAliases();
        if (caCertificateAliases != null) {
            for (String str2 : caCertificateAliases) {
                if (!TextUtils.isEmpty(str2) && str.equals(str2)) {
                    return true;
                }
            }
        }
        String clientCertificateAlias = wifiEnterpriseConfig.getClientCertificateAlias();
        return !TextUtils.isEmpty(clientCertificateAlias) && str.equals(clientCertificateAlias);
    }

    public final void conditionallyUpdateScanResults$2(boolean z) {
        if (this.mWifiManager.getWifiState() == 1) {
            updateStandardWifiEntryScans(Collections.emptyList());
            updatePasspointWifiEntryScans$1(Collections.emptyList());
            return;
        }
        ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
        long j = this.mMaxScanAgeMillis;
        if (z) {
            scanResultUpdater.update(this.mWifiManager.getScanResults());
        } else {
            j += this.mScanIntervalMillis;
        }
        updateStandardWifiEntryScans(scanResultUpdater.getScanResults(j));
        updatePasspointWifiEntryScans$1(scanResultUpdater.getScanResults(j));
    }

    public final List getAllWifiEntries() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mStandardWifiEntryCache);
        arrayList.addAll(((ArrayMap) this.mPasspointWifiEntryCache).values());
        return arrayList;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConfiguredNetworksChangedAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        updateStandardWifiEntryConfigs(this.mWifiManager.getConfiguredNetworks());
        updatePasspointWifiEntryConfigs(this.mWifiManager.getPasspointConfigurations());
        updateWifiEntries();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConnectivityReportAvailable(
            ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).updateConnectivityReport(connectivityReport);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next())
                    .onDefaultNetworkCapabilitiesChanged(network, networkCapabilities);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultNetworkLost() {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onDefaultNetworkLost();
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).updateLinkProperties(network, linkProperties);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onNetworkCapabilitiesChanged(network, networkCapabilities);
        }
        updateWifiEntries();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkLost(Network network) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onNetworkLost(network);
        }
        updateWifiEntries();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkStateChangedAction(Intent intent) {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        NetworkInfo networkInfo =
                (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
        if (connectionInfo == null || networkInfo == null) {
            return;
        }
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onPrimaryWifiInfoChanged(connectionInfo, networkInfo);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleOnStart() {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).clearConnectionInfo();
        }
        updateStandardWifiEntryConfigs(this.mWifiManager.getConfiguredNetworks());
        updatePasspointWifiEntryConfigs(this.mWifiManager.getPasspointConfigurations());
        this.mScanResultUpdater.update(this.mWifiManager.getScanResults());
        conditionallyUpdateScanResults$2(true);
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
        updateWifiEntries();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleScanResultsAvailableAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        conditionallyUpdateScanResults$2(intent.getBooleanExtra("resultsUpdated", true));
        updateWifiEntries();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleWifiStateChangedAction() {
        conditionallyUpdateScanResults$2(true);
        updateWifiEntries();
    }

    public final void updatePasspointWifiEntryConfigs(List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        Map map =
                (Map)
                        list.stream()
                                .collect(
                                        Collectors.toMap(
                                                new SavedNetworkTracker$$ExternalSyntheticLambda0(
                                                        4),
                                                Function.identity()));
        ((ArrayMap) this.mPasspointWifiEntryCache)
                .entrySet()
                .removeIf(new SavedNetworkTracker$$ExternalSyntheticLambda8(0, map));
        for (String str : map.keySet()) {
            Map map2 = this.mPasspointWifiEntryCache;
            ArrayMap arrayMap = (ArrayMap) map2;
            arrayMap.put(
                    str,
                    new PasspointWifiEntry(
                            this.mInjector,
                            this.mMainHandler,
                            (PasspointConfiguration) map.get(str),
                            this.mWifiManager,
                            true));
        }
    }

    public final void updatePasspointWifiEntryScans$1(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        TreeSet treeSet = new TreeSet();
        for (Pair pair : this.mWifiManager.getAllMatchingWifiConfigs(list)) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            String uniqueIdToPasspointWifiEntryKey =
                    PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey());
            treeSet.add(uniqueIdToPasspointWifiEntryKey);
            if (((ArrayMap) this.mPasspointWifiEntryCache)
                    .containsKey(uniqueIdToPasspointWifiEntryKey)) {
                ((PasspointWifiEntry)
                                ((ArrayMap) this.mPasspointWifiEntryCache)
                                        .get(uniqueIdToPasspointWifiEntryKey))
                        .updateScanResultInfo(
                                wifiConfiguration,
                                (List) ((Map) pair.second).get(0),
                                (List) ((Map) pair.second).get(1));
            }
        }
        for (PasspointWifiEntry passpointWifiEntry :
                ((ArrayMap) this.mPasspointWifiEntryCache).values()) {
            if (!treeSet.contains(passpointWifiEntry.mKey)) {
                passpointWifiEntry.updateScanResultInfo(null, null, null);
            }
        }
    }

    public final void updateStandardWifiEntryConfigs(List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        Map map =
                (Map)
                        list.stream()
                                .filter(new SavedNetworkTracker$$ExternalSyntheticLambda11())
                                .collect(
                                        Collectors.groupingBy(
                                                new SavedNetworkTracker$$ExternalSyntheticLambda0(
                                                        1)));
        ((ArrayList) this.mStandardWifiEntryCache)
                .removeIf(new SavedNetworkTracker$$ExternalSyntheticLambda8(1, map));
        for (StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey : map.keySet()) {
            List list2 = this.mStandardWifiEntryCache;
            List list3 = (List) map.get(standardWifiEntryKey);
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

    public final void updateStandardWifiEntryScans(List list) {
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
                                         // com.android.wifitrackerlib.SavedNetworkTracker$$ExternalSyntheticLambda10
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                                standardWifiEntry.updateScanResultInfo(
                                        (List) map.get(standardWifiEntry.mKey.mScanResultKey));
                            }
                        });
    }

    public final void updateWifiEntries() {
        synchronized (this.mLock) {
            try {
                ((ArrayList) this.mSavedWifiEntries).clear();
                ((ArrayList) this.mSavedWifiEntries).addAll(this.mStandardWifiEntryCache);
                List list = this.mSavedWifiEntries;
                Comparator comparator = WifiEntry.TITLE_COMPARATOR;
                Collections.sort(list, comparator);
                ((ArrayList) this.mSubscriptionWifiEntries).clear();
                ((ArrayList) this.mSubscriptionWifiEntries)
                        .addAll(((ArrayMap) this.mPasspointWifiEntryCache).values());
                Collections.sort(this.mSubscriptionWifiEntries, comparator);
                if (BaseWifiTracker.sVerboseLogging) {
                    Log.v(
                            "SavedNetworkTracker",
                            "Updated SavedWifiEntries: "
                                    + Arrays.toString(
                                            ((ArrayList) this.mSavedWifiEntries).toArray()));
                    Log.v(
                            "SavedNetworkTracker",
                            "Updated SubscriptionWifiEntries: "
                                    + Arrays.toString(
                                            ((ArrayList) this.mSubscriptionWifiEntries).toArray()));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        final SavedNetworkTrackerCallback savedNetworkTrackerCallback = this.mListener;
        if (savedNetworkTrackerCallback != null) {
            final int i = 0;
            this.mMainHandler.post(
                    new Runnable() { // from class:
                                     // com.android.wifitrackerlib.SavedNetworkTracker$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i2 = i;
                            SavedNetworkTracker.SavedNetworkTrackerCallback
                                    savedNetworkTrackerCallback2 = savedNetworkTrackerCallback;
                            switch (i2) {
                                case 0:
                                    savedNetworkTrackerCallback2.onSavedWifiEntriesChanged();
                                    break;
                                default:
                                    savedNetworkTrackerCallback2.onSubscriptionWifiEntriesChanged();
                                    break;
                            }
                        }
                    });
        }
        final SavedNetworkTrackerCallback savedNetworkTrackerCallback2 = this.mListener;
        if (savedNetworkTrackerCallback2 != null) {
            final int i2 = 1;
            this.mMainHandler.post(
                    new Runnable() { // from class:
                                     // com.android.wifitrackerlib.SavedNetworkTracker$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i22 = i2;
                            SavedNetworkTracker.SavedNetworkTrackerCallback
                                    savedNetworkTrackerCallback22 = savedNetworkTrackerCallback2;
                            switch (i22) {
                                case 0:
                                    savedNetworkTrackerCallback22.onSavedWifiEntriesChanged();
                                    break;
                                default:
                                    savedNetworkTrackerCallback22
                                            .onSubscriptionWifiEntriesChanged();
                                    break;
                            }
                        }
                    });
        }
    }

    public SavedNetworkTracker(
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
            SavedNetworkTrackerCallback savedNetworkTrackerCallback) {
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
                savedNetworkTrackerCallback,
                "SavedNetworkTracker");
        this.mLock = new Object();
        this.mSavedWifiEntries = new ArrayList();
        this.mSubscriptionWifiEntries = new ArrayList();
        this.mStandardWifiEntryCache = new ArrayList();
        this.mPasspointWifiEntryCache = new ArrayMap();
        this.mListener = savedNetworkTrackerCallback;
    }
}
