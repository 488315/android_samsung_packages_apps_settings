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
import android.os.Handler;
import android.util.Log;

import androidx.core.util.Preconditions;

import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.android.wifitrackerlib.SavedNetworkTracker$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda4;
import com.android.wifitrackerlib.WifiTrackerInjector;

import com.sec.ims.IMSParameter;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SemSavedNetworkTracker extends BaseWifiTracker {
    public final SavedNetworkFilter.LabsNetworkComparator mFilter;
    public final SavedNetworkTrackerCallback mListener;
    public final Object mLock;
    public final List mSavedWifiEntries;
    public final List mStandardWifiEntryCache;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface SavedNetworkTrackerCallback extends BaseWifiTracker.BaseWifiTrackerCallback {
        void onSavedWifiEntriesChanged();
    }

    public SemSavedNetworkTracker(
            Lifecycle lifecycle,
            Context context,
            WifiManager wifiManager,
            ConnectivityManager connectivityManager,
            Handler handler,
            Handler handler2,
            Clock clock,
            SavedNetworkTrackerCallback savedNetworkTrackerCallback,
            SavedNetworkFilter.LabsNetworkComparator labsNetworkComparator) {
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
                savedNetworkTrackerCallback,
                "SemSavedNetworkTracker");
        this.mLock = new Object();
        this.mSavedWifiEntries = new ArrayList();
        this.mStandardWifiEntryCache = new ArrayList();
        this.mListener = savedNetworkTrackerCallback;
        this.mFilter = labsNetworkComparator;
    }

    public final List getAllWifiEntries$3() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mStandardWifiEntryCache);
        return arrayList;
    }

    public final List getSavedWifiEntries() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mSavedWifiEntries);
        }
        return arrayList;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        Iterator it = ((ArrayList) getAllWifiEntries$3()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next())
                    .onDefaultNetworkCapabilitiesChanged(network, networkCapabilities);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        Iterator it = ((ArrayList) getAllWifiEntries$3()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).updateLinkProperties(network, linkProperties);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        Iterator it = ((ArrayList) getAllWifiEntries$3()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onNetworkCapabilitiesChanged(network, networkCapabilities);
        }
        updateWifiEntries$2();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkLost(Network network) {
        Iterator it = ((ArrayList) getAllWifiEntries$3()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onNetworkLost(network);
        }
        updateWifiEntries$2();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkStateChangedAction(Intent intent) {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        NetworkInfo networkInfo =
                (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
        if (connectionInfo == null || networkInfo == null) {
            return;
        }
        Iterator it = ((ArrayList) getAllWifiEntries$3()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onPrimaryWifiInfoChanged(connectionInfo, networkInfo);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleOnStart() {
        ((ArrayList) this.mStandardWifiEntryCache).clear();
        List<WifiConfiguration> configuredNetworks = this.mWifiManager.getConfiguredNetworks();
        Preconditions.checkNotNull(configuredNetworks, "Config list should not be null!");
        final Map map =
                (Map)
                        configuredNetworks.stream()
                                .filter(new SemSavedNetworkTracker$$ExternalSyntheticLambda1())
                                .collect(
                                        Collectors.groupingBy(
                                                new SavedNetworkTracker$$ExternalSyntheticLambda0(
                                                        1)));
        ((ArrayList) this.mStandardWifiEntryCache)
                .removeIf(
                        new Predicate() { // from class:
                                          // com.samsung.android.wifitrackerlib.SemSavedNetworkTracker$$ExternalSyntheticLambda2
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                                standardWifiEntry.updateConfig(
                                        (List) map.remove(standardWifiEntry.mKey));
                                return !standardWifiEntry.isSaved();
                            }
                        });
        for (Map.Entry entry : map.entrySet()) {
            List list = this.mStandardWifiEntryCache;
            ArrayList arrayList = (ArrayList) list;
            arrayList.add(
                    new StandardWifiEntry(
                            this.mInjector,
                            this.mMainHandler,
                            (StandardWifiEntry.StandardWifiEntryKey) entry.getKey(),
                            (List) map.get(entry.getKey()),
                            null,
                            this.mWifiManager,
                            true));
        }
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
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        NetworkInfo networkInfo = this.mConnectivityManager.getNetworkInfo(currentNetwork);
        Iterator it = ((ArrayList) this.mStandardWifiEntryCache).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        updateWifiEntries$2();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleWifiStateChangedAction() {
        updateWifiEntries$2();
    }

    public final void updateWifiEntries$2() {
        Comparator comparing;
        synchronized (this.mLock) {
            try {
                ((ArrayList) this.mSavedWifiEntries).clear();
                ((ArrayList) this.mSavedWifiEntries).addAll(this.mStandardWifiEntryCache);
                List list = this.mSavedWifiEntries;
                switch (this.mFilter.$r8$classId) {
                    case 0:
                        comparing =
                                Comparator.comparing(
                                        new WifiEntry$$ExternalSyntheticLambda4(2),
                                        String.CASE_INSENSITIVE_ORDER);
                        break;
                    default:
                        comparing =
                                Comparator.comparing(
                                                new SavedNetworkFilter$ManageNetworkComparator$$ExternalSyntheticLambda0())
                                        .thenComparing(
                                                new WifiEntry$$ExternalSyntheticLambda4(2),
                                                String.CASE_INSENSITIVE_ORDER);
                        break;
                }
                Collections.sort(list, comparing);
                if (BaseWifiTracker.sVerboseLogging) {
                    Log.v(
                            "SemSavedNetworkTracker",
                            "Updated SavedWifiEntries: "
                                    + ((ArrayList) this.mSavedWifiEntries).size());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        final SavedNetworkTrackerCallback savedNetworkTrackerCallback = this.mListener;
        if (savedNetworkTrackerCallback != null) {
            this.mMainHandler.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.wifitrackerlib.SemSavedNetworkTracker$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemSavedNetworkTracker.SavedNetworkTrackerCallback.this
                                    .onSavedWifiEntriesChanged();
                        }
                    });
        }
    }
}
