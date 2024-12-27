package com.android.wifitrackerlib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityDiagnosticsManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.KnownNetwork;
import android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.os.BuildCompat;
import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3;
import com.android.settings.wifi.WifiSettings;

import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.settings.wifi.WifiPickerHelper;
import com.samsung.android.wifi.SemEasySetupWifiScanSettings;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifitrackerlib.EasySetupUtils;
import com.samsung.android.wifitrackerlib.SemWifiEntryFilter;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.WifiQoSScoredCache;
import com.sec.ims.IMSParameter;
import com.sec.ims.settings.ImsProfile;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiPickerTracker extends BaseWifiTracker {
    public List mActiveWifiEntries;
    public final List mAutoHotspotEntries;
    public final AtomicBoolean mConnected;
    public WifiEntry mConnectedWifiEntry;
    public final List mEasySetupCandidateEntries;
    public final List mEasySetupEntries;
    public final EasySetupUtils mEasySetupUtils;
    public final List mHotspotNetworkDataCache;
    public final List mHotspotNetworkEntryCache;
    public boolean mIsSettingSupportEasySetup;
    public final boolean mIsSettingsTracker;
    public boolean mIsSupportEasySetup;
    public final List mKnownNetworkDataCache;
    public final List mKnownNetworkEntryCache;
    public WifiInfo mLastWifiInfo;
    public final WifiPickerTrackerCallback mListener;
    public final Object mLockAutoHotspot;
    public final Object mLockEasySetup;
    public MergedCarrierEntry mMergedCarrierEntry;
    public final ArrayMap mNetworkRequestConfigCache;
    public NetworkRequestEntry mNetworkRequestEntry;
    public int mNumSavedNetworks;
    public final Map mOsuWifiEntryCache;
    public final Map mPasspointConfigCache;
    public final SparseArray mPasspointWifiConfigCache;
    public final Map mPasspointWifiEntryCache;
    public final List mSemEasySetupScanSettings;
    public final SemWifiEntryFilter mSemFilter;
    public final SemWifiPickerTrackerCallback mSemListener;
    public final Map mStandardWifiConfigCache;
    public final List mStandardWifiEntryCache;
    public final Map mSuggestedConfigCache;
    public final List mSuggestedWifiEntryCache;
    public List mWifiEntries;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.wifitrackerlib.WifiPickerTracker$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiPickerTracker this$0;
        public final /* synthetic */ boolean val$updatedScanResult;

        public /* synthetic */ AnonymousClass1(
                WifiPickerTracker wifiPickerTracker, boolean z, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiPickerTracker;
            this.val$updatedScanResult = z;
        }

        @Override // java.lang.Runnable
        public final void run() {
            WifiConfiguration orElse;
            switch (this.$r8$classId) {
                case 0:
                    ((WifiSettings) this.this$0.mSemListener)
                            .onWifiEntriesChanged(this.val$updatedScanResult);
                    break;
                default:
                    SemWifiPickerTrackerCallback semWifiPickerTrackerCallback =
                            this.this$0.mSemListener;
                    boolean z = this.val$updatedScanResult;
                    WifiSettings wifiSettings = (WifiSettings) semWifiPickerTrackerCallback;
                    wifiSettings.getClass();
                    Log.d("WifiSettings", "onConnectedChanged " + z);
                    if (!z) {
                        wifiSettings.mAutoHotspotWifiInfo = null;
                        break;
                    } else {
                        wifiSettings.mAutoHotspotWifiInfo =
                                wifiSettings.mWifiManager.getConnectionInfo();
                        wifiSettings.mManualConnectingNetwork = null;
                        WifiPickerHelper wifiPickerHelper = wifiSettings.mWifiPickerHelper;
                        if (wifiPickerHelper != null && wifiPickerHelper.mIsFromLocation) {
                            if (wifiPickerHelper.mLastUserPickedNetwork != null) {
                                WifiInfo connectionInfo =
                                        wifiPickerHelper.mWifiManager.getConnectionInfo();
                                if (connectionInfo != null) {
                                    final int networkId = connectionInfo.getNetworkId();
                                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                            networkId,
                                            "SEC PICKER from location, networkId:",
                                            "WifiSettings.Picker");
                                    if (networkId != -1
                                            && (orElse =
                                                            wifiPickerHelper
                                                                    .mWifiManager
                                                                    .getConfiguredNetworks()
                                                                    .stream()
                                                                    .filter(
                                                                            new Predicate() { // from class: com.samsung.android.settings.wifi.WifiPickerHelper$$ExternalSyntheticLambda0
                                                                                @Override // java.util.function.Predicate
                                                                                public final boolean
                                                                                        test(
                                                                                                Object
                                                                                                        obj) {
                                                                                    return ((WifiConfiguration)
                                                                                                            obj)
                                                                                                    .networkId
                                                                                            == networkId;
                                                                                }
                                                                            })
                                                                    .findFirst()
                                                                    .orElse(null))
                                                    != null
                                            && wifiPickerHelper.mLastUserPickedNetwork.semMatches(
                                                    orElse)) {
                                        Log.d("WifiSettings.Picker", "matched");
                                        wifiSettings
                                                .getActivity()
                                                .setResult(
                                                        -1,
                                                        wifiSettings.mWifiPickerHelper.getApIntent(
                                                                wifiSettings.mSelectedWifiEntry));
                                        wifiSettings.popOrFinishThisActivity();
                                        break;
                                    }
                                }
                            } else {
                                Log.d("WifiSettings.Picker", "user not picked any AP");
                            }
                        }
                        if (!wifiSettings.mFinishIfConnected) {
                            if (wifiSettings.mScrollTimer == 0) {
                                wifiSettings.mScrollTimer = SystemClock.currentThreadTimeMillis();
                            }
                            wifiSettings.forceScrollToTopOfList();
                            break;
                        } else {
                            wifiSettings.popOrFinishThisActivity();
                            break;
                        }
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.wifitrackerlib.WifiPickerTracker$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ int $r8$classId = 1;
        public final /* synthetic */ int val$reason;

        public AnonymousClass2(boolean z, int i) {
            this.val$reason = i;
        }

        /* JADX WARN: Removed duplicated region for block: B:49:0x00fc  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 274
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.wifitrackerlib.WifiPickerTracker.AnonymousClass2.run():void");
        }

        public AnonymousClass2(int i) {
            this.val$reason = i;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface SemWifiPickerTrackerCallback {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface WifiPickerTrackerCallback extends BaseWifiTracker.BaseWifiTrackerCallback {
        void onNumSavedNetworksChanged();

        void onNumSavedSubscriptionsChanged();

        default void onWifiEntriesChanged() {}

        default void onWifiEntriesChanged(int i) {
            onWifiEntriesChanged();
        }
    }

    public WifiPickerTracker(
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
            WifiPickerTrackerCallback wifiPickerTrackerCallback) {
        this(
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
                wifiPickerTrackerCallback,
                null,
                false);
    }

    public final void conditionallyCreateConnectedWifiEntry(WifiInfo wifiInfo) {
        WifiConfiguration wifiConfiguration;
        PasspointWifiEntry passpointWifiEntry;
        if (wifiInfo != null && !wifiInfo.isPasspointAp() && !wifiInfo.isOsuAp()) {
            final int networkId = wifiInfo.getNetworkId();
            Iterator it = ((ArrayMap) this.mStandardWifiConfigCache).values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                List list = (List) it.next();
                if (list.stream()
                                .map(new WifiPickerTracker$$ExternalSyntheticLambda2(7))
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda10
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return ((Integer) obj).intValue() == networkId;
                                            }
                                        })
                                .count()
                        != 0) {
                    StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey =
                            new StandardWifiEntry.StandardWifiEntryKey(
                                    (WifiConfiguration) list.get(0), true);
                    Iterator it2 = ((ArrayList) this.mStandardWifiEntryCache).iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            ((ArrayList) this.mStandardWifiEntryCache)
                                    .add(
                                            new StandardWifiEntry(
                                                    this.mInjector,
                                                    this.mMainHandler,
                                                    standardWifiEntryKey,
                                                    list,
                                                    null,
                                                    this.mWifiManager,
                                                    false));
                            break;
                        } else if (standardWifiEntryKey.equals(
                                ((StandardWifiEntry) it2.next()).mKey)) {
                            break;
                        }
                    }
                }
            }
        }
        if (wifiInfo != null && !wifiInfo.isPasspointAp() && !wifiInfo.isOsuAp()) {
            int networkId2 = wifiInfo.getNetworkId();
            Iterator it3 = ((ArrayMap) this.mSuggestedConfigCache).values().iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                List list2 = (List) it3.next();
                if (!list2.isEmpty()
                        && ((WifiConfiguration) list2.get(0)).networkId == networkId2) {
                    StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey2 =
                            new StandardWifiEntry.StandardWifiEntryKey(
                                    (WifiConfiguration) list2.get(0), true);
                    Iterator it4 = ((ArrayList) this.mSuggestedWifiEntryCache).iterator();
                    while (true) {
                        if (!it4.hasNext()) {
                            ((ArrayList) this.mSuggestedWifiEntryCache)
                                    .add(
                                            new StandardWifiEntry(
                                                    this.mInjector,
                                                    this.mMainHandler,
                                                    standardWifiEntryKey2,
                                                    list2,
                                                    null,
                                                    this.mWifiManager,
                                                    false));
                            break;
                        } else if (standardWifiEntryKey2.equals(
                                ((StandardWifiEntry) it4.next()).mKey)) {
                            break;
                        }
                    }
                }
            }
        }
        if (wifiInfo != null
                && wifiInfo.isPasspointAp()
                && (wifiConfiguration =
                                (WifiConfiguration)
                                        this.mPasspointWifiConfigCache.get(wifiInfo.getNetworkId()))
                        != null) {
            if (!((ArrayMap) this.mPasspointWifiEntryCache)
                    .containsKey(
                            PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(
                                    wifiConfiguration.getKey()))) {
                PasspointConfiguration passpointConfiguration =
                        (PasspointConfiguration)
                                ((ArrayMap) this.mPasspointConfigCache)
                                        .get(
                                                PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(
                                                        wifiConfiguration.getKey()));
                if (passpointConfiguration != null) {
                    passpointWifiEntry =
                            new PasspointWifiEntry(
                                    this.mInjector,
                                    this.mMainHandler,
                                    passpointConfiguration,
                                    this.mWifiManager,
                                    false);
                } else {
                    passpointWifiEntry =
                            new PasspointWifiEntry(
                                    this.mInjector,
                                    this.mMainHandler,
                                    wifiConfiguration,
                                    this.mWifiManager);
                }
                ((ArrayMap) this.mPasspointWifiEntryCache)
                        .put(passpointWifiEntry.mKey, passpointWifiEntry);
            }
        }
        ArrayList arrayList = new ArrayList();
        if (wifiInfo != null) {
            int i = 0;
            while (true) {
                if (i >= this.mNetworkRequestConfigCache.size()) {
                    break;
                }
                List list3 = (List) this.mNetworkRequestConfigCache.valueAt(i);
                if (!list3.isEmpty()
                        && ((WifiConfiguration) list3.get(0)).networkId
                                == wifiInfo.getNetworkId()) {
                    arrayList.addAll(list3);
                    break;
                }
                i++;
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey3 =
                new StandardWifiEntry.StandardWifiEntryKey(
                        (WifiConfiguration) arrayList.get(0), false);
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry == null
                || !networkRequestEntry.mKey.equals(standardWifiEntryKey3)) {
            NetworkRequestEntry networkRequestEntry2 =
                    new NetworkRequestEntry(
                            this.mInjector,
                            this.mMainHandler,
                            standardWifiEntryKey3,
                            this.mWifiManager,
                            false);
            this.mNetworkRequestEntry = networkRequestEntry2;
            networkRequestEntry2.updateConfig(arrayList);
            ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
            updateNetworkRequestEntryScans(
                    scanResultUpdater.getScanResults(scanResultUpdater.mMaxScanAgeMillis));
        }
    }

    public final void conditionallyUpdateScanResults$3(boolean z) {
        if (this.mWifiManager.getWifiState() == 1) {
            updateStandardWifiEntryScans$1(Collections.emptyList());
            updateSuggestedWifiEntryScans(Collections.emptyList());
            updatePasspointWifiEntryScans$2(Collections.emptyList());
            updateOsuWifiEntryScans$1(Collections.emptyList());
            if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
                int i = BuildCompat.$r8$clinit;
                ((ArrayList) this.mKnownNetworkEntryCache).clear();
                ((ArrayList) this.mHotspotNetworkEntryCache).clear();
            }
            updateNetworkRequestEntryScans(Collections.emptyList());
            Collections.emptyList();
            ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
            synchronized (scanResultUpdater.mLock) {
                ((ArrayMap) scanResultUpdater.mScanResultsBySsidAndBssid).clear();
            }
            return;
        }
        long j = this.mMaxScanAgeMillis;
        if (z) {
            this.mScanResultUpdater.update(this.mWifiManager.getScanResults(), this.mLastWifiInfo);
        } else {
            j += this.mScanIntervalMillis;
        }
        List scanResults = this.mScanResultUpdater.getScanResults(j);
        updateStandardWifiEntryScans$1(scanResults);
        updateSuggestedWifiEntryScans(scanResults);
        updatePasspointWifiEntryScans$2(scanResults);
        updateOsuWifiEntryScans$1(scanResults);
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            int i2 = BuildCompat.$r8$clinit;
            updateKnownNetworkEntryScans(scanResults);
            updateHotspotNetworkEntries();
        }
        updateNetworkRequestEntryScans(scanResults);
    }

    public final List getAllWifiEntries$1() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mStandardWifiEntryCache);
        arrayList.addAll(this.mSuggestedWifiEntryCache);
        arrayList.addAll(((ArrayMap) this.mPasspointWifiEntryCache).values());
        arrayList.addAll(((ArrayMap) this.mOsuWifiEntryCache).values());
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            arrayList.addAll(this.mKnownNetworkEntryCache);
            arrayList.addAll(this.mHotspotNetworkEntryCache);
        }
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null) {
            arrayList.add(networkRequestEntry);
        }
        MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
        if (mergedCarrierEntry != null) {
            arrayList.add(mergedCarrierEntry);
        }
        return arrayList;
    }

    public final MergedCarrierEntry getMergedCarrierEntry() {
        int defaultDataSubscriptionId;
        if (!this.mIsInitialized
                && this.mMergedCarrierEntry == null
                && (defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId())
                        != -1) {
            this.mMergedCarrierEntry =
                    new MergedCarrierEntry(
                            this.mInjector,
                            this.mWorkerHandler,
                            this.mWifiManager,
                            defaultDataSubscriptionId);
        }
        return this.mMergedCarrierEntry;
    }

    public final List getWifiEntries() {
        return (List)
                new ArrayList(this.mWifiEntries)
                        .stream()
                                .filter(new WifiPickerTracker$$ExternalSyntheticLambda0(0, this))
                                .collect(Collectors.toList());
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConfiguredNetworksChangedAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        HashMap hashMap = new HashMap();
        for (SemWifiConfiguration semWifiConfiguration :
                this.mSemWifiManager.getConfiguredNetworks()) {
            hashMap.put(semWifiConfiguration.configKey, semWifiConfiguration);
        }
        updateWifiConfigurations(hashMap, this.mWifiManager.getPrivilegedConfiguredNetworks());
        updatePasspointConfigurations(hashMap, this.mWifiManager.getPasspointConfigurations());
        conditionallyUpdateScanResults$3(false);
        WifiPickerTrackerCallback wifiPickerTrackerCallback = this.mListener;
        Handler handler = this.mMainHandler;
        if (wifiPickerTrackerCallback != null) {
            handler.post(
                    new WifiPickerTracker$$ExternalSyntheticLambda36(wifiPickerTrackerCallback, 0));
        }
        if (wifiPickerTrackerCallback != null) {
            handler.post(
                    new WifiPickerTracker$$ExternalSyntheticLambda36(wifiPickerTrackerCallback, 1));
        }
        updateWifiEntries(0, FieldName.CONFIG, false);
        boolean booleanExtra = intent.getBooleanExtra("multipleChanges", false);
        int intExtra = intent.getIntExtra("changeReason", 2);
        if (this.mSemListener != null) {
            handler.post(new AnonymousClass2(booleanExtra, intExtra));
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConnectivityReportAvailable(
            ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {
        Iterator it = ((ArrayList) getAllWifiEntries$1()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).updateConnectivityReport(connectivityReport);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        Iterator it = ((ArrayList) getAllWifiEntries$1()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next())
                    .onDefaultNetworkCapabilitiesChanged(network, networkCapabilities);
        }
        notifyOnWifiEntriesChanged(0, false);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultNetworkLost() {
        Iterator it = ((ArrayList) getAllWifiEntries$1()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onDefaultNetworkLost();
        }
        notifyOnWifiEntriesChanged(0, false);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleDefaultSubscriptionChanged(int i) {
        if (i != -1) {
            MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
            if (mergedCarrierEntry != null && i == mergedCarrierEntry.mSubscriptionId) {
                return;
            }
            this.mMergedCarrierEntry =
                    new MergedCarrierEntry(
                            this.mInjector, this.mWorkerHandler, this.mWifiManager, i);
            Network currentNetwork = this.mWifiManager.getCurrentNetwork();
            if (currentNetwork != null) {
                NetworkCapabilities networkCapabilities =
                        this.mConnectivityManager.getNetworkCapabilities(currentNetwork);
                if (networkCapabilities != null) {
                    this.mMergedCarrierEntry.onNetworkCapabilitiesChanged(
                            currentNetwork,
                            new NetworkCapabilities.Builder(networkCapabilities)
                                    .setTransportInfo(this.mWifiManager.getConnectionInfo())
                                    .build());
                }
                LinkProperties linkProperties =
                        this.mConnectivityManager.getLinkProperties(currentNetwork);
                if (linkProperties != null) {
                    this.mMergedCarrierEntry.updateLinkProperties(currentNetwork, linkProperties);
                }
            }
        } else if (this.mMergedCarrierEntry == null) {
            return;
        } else {
            this.mMergedCarrierEntry = null;
        }
        notifyOnWifiEntriesChanged(0, false);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleHotspotNetworkConnectionStatusChanged(
            HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) {
        this.mHotspotNetworkEntryCache.stream()
                .filter(
                        new WifiPickerTracker$$ExternalSyntheticLambda0(
                                1, hotspotNetworkConnectionStatus))
                .forEach(
                        new WifiPickerTracker$$ExternalSyntheticLambda12(
                                hotspotNetworkConnectionStatus));
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleHotspotNetworksUpdated(List list) {
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            ((ArrayList) this.mHotspotNetworkDataCache).clear();
            Log.i("WifiPickerTracker", "onHotspotNetworksUpdated(), networks:" + list);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Log.i("WifiPickerTracker", "network:" + ((HotspotNetwork) it.next()));
            }
            ((ArrayList) this.mHotspotNetworkDataCache).addAll(list);
            updateHotspotNetworkEntries();
            updateWifiEntries(0, "hotspot", false);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleKnownNetworkConnectionStatusChanged(
            KnownNetworkConnectionStatus knownNetworkConnectionStatus) {
        this.mKnownNetworkEntryCache.stream()
                .filter(
                        new WifiPickerTracker$$ExternalSyntheticLambda11(
                                new StandardWifiEntry.ScanResultKey(
                                        knownNetworkConnectionStatus.getKnownNetwork().getSsid(),
                                        new ArrayList(
                                                knownNetworkConnectionStatus
                                                        .getKnownNetwork()
                                                        .getSecurityTypes())),
                                0))
                .forEach(
                        new WifiPickerTracker$$ExternalSyntheticLambda12(
                                knownNetworkConnectionStatus));
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleKnownNetworksUpdated(List list) {
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            ((ArrayList) this.mKnownNetworkDataCache).clear();
            ((ArrayList) this.mKnownNetworkDataCache).addAll(list);
            ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
            updateKnownNetworkEntryScans(
                    scanResultUpdater.getScanResults(scanResultUpdater.mMaxScanAgeMillis));
            updateWifiEntries(0, "known", false);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        Iterator it = ((ArrayList) getAllWifiEntries$1()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).updateLinkProperties(network, linkProperties);
        }
        Log.d("WifiPickerTracker", "handleLinkPropertiesChanged");
        notifyOnWifiEntriesChanged(0, false);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
        if (this.mNetworkRequestConfigCache.size()
                        + this.mPasspointWifiConfigCache.size()
                        + ((ArrayMap) this.mSuggestedConfigCache).size()
                        + ((ArrayMap) this.mStandardWifiConfigCache).size()
                == 0) {
            HashMap hashMap = new HashMap();
            for (SemWifiConfiguration semWifiConfiguration :
                    this.mSemWifiManager.getConfiguredNetworks()) {
                hashMap.put(semWifiConfiguration.configKey, semWifiConfiguration);
            }
            updateWifiConfigurations(hashMap, this.mWifiManager.getPrivilegedConfiguredNetworks());
        }
        conditionallyCreateConnectedWifiEntry(Utils.getWifiInfo(networkCapabilities));
        Iterator it = ((ArrayList) getAllWifiEntries$1()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onNetworkCapabilitiesChanged(network, networkCapabilities);
        }
        updateWifiEntries(0, "capabilities", false);
        Log.d("WifiPickerTracker", "handleNetworkCapabilitiesChanged");
        notifyOnWifiEntriesChanged(0, false);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkLost(Network network) {
        Iterator it = ((ArrayList) getAllWifiEntries$1()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onNetworkLost(network);
        }
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null && networkRequestEntry.getConnectedState() == 0) {
            this.mNetworkRequestEntry = null;
        }
        updateWifiEntries(0, "lost", false);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkStateChangedAction(Intent intent) {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        NetworkInfo networkInfo =
                (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
        if (connectionInfo != null) {
            conditionallyCreateConnectedWifiEntry(connectionInfo);
        }
        Iterator it = ((ArrayList) getAllWifiEntries$1()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onPrimaryWifiInfoChanged(connectionInfo, networkInfo);
        }
        updateWifiEntries(0, "networkstate", false);
        boolean isConnected = networkInfo.isConnected();
        if (this.mSemListener != null) {
            this.mMainHandler.post(new AnonymousClass1(this, isConnected, 1));
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleOnStart() {
        List<ScanResult> list;
        List list2;
        Iterator it = ((ArrayList) getAllWifiEntries$1()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).clearConnectionInfo();
        }
        HashMap hashMap = new HashMap();
        for (SemWifiConfiguration semWifiConfiguration :
                this.mSemWifiManager.getConfiguredNetworks()) {
            hashMap.put(semWifiConfiguration.configKey, semWifiConfiguration);
        }
        boolean z = this.mIsSettingsTracker;
        if (z) {
            updateWifiConfigurations(hashMap, this.mWifiManager.getPrivilegedConfiguredNetworks());
        } else {
            updateWifiConfigurations(hashMap, this.mWifiManager.getConfiguredNetworks());
        }
        boolean z2 =
                z
                        && this.mIsSettingSupportEasySetup
                        && Settings.Global.getInt(
                                        this.mContext.getContentResolver(), "safe_wifi", 0)
                                == 0;
        this.mIsSupportEasySetup = z2;
        if (z2) {
            Map easySetupScanSettings =
                    this.mEasySetupUtils.mSemWifiManager.getEasySetupScanSettings();
            if (easySetupScanSettings.size() != 0) {
                ((ArrayList) this.mSemEasySetupScanSettings).clear();
                for (SemEasySetupWifiScanSettings semEasySetupWifiScanSettings :
                        easySetupScanSettings.values()) {
                    if (semEasySetupWifiScanSettings != null
                            && semEasySetupWifiScanSettings.pendingIntentForSettings != null
                            && (list2 = semEasySetupWifiScanSettings.ssidPatterns) != null
                            && !list2.isEmpty()) {
                        ((ArrayList) this.mSemEasySetupScanSettings)
                                .add(semEasySetupWifiScanSettings);
                        Preference$$ExternalSyntheticOutline0.m(
                                new StringBuilder("set EasySetup filter - minRssi : "),
                                semEasySetupWifiScanSettings.minRssi,
                                "WifiPickerTracker");
                    }
                }
            }
        }
        updatePasspointConfigurations(hashMap, this.mWifiManager.getPasspointConfigurations());
        this.mLastWifiInfo = this.mWifiManager.getConnectionInfo();
        try {
            list = this.mWifiManager.getScanResults();
        } catch (Exception e) {
            Log.e("WifiPickerTracker", "failed to getScanResults", e);
            list = null;
        }
        if (list != null) {
            this.mScanResultUpdater.update(list, this.mLastWifiInfo);
        }
        conditionallyUpdateScanResults$3(true);
        handleDefaultSubscriptionChanged(SubscriptionManager.getDefaultDataSubscriptionId());
        Network currentNetwork = this.mWifiManager.getCurrentNetwork();
        updateForceNetworkInfo(currentNetwork);
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
        Handler handler = this.mMainHandler;
        WifiPickerTrackerCallback wifiPickerTrackerCallback = this.mListener;
        if (wifiPickerTrackerCallback != null) {
            handler.post(
                    new WifiPickerTracker$$ExternalSyntheticLambda36(wifiPickerTrackerCallback, 0));
        }
        if (wifiPickerTrackerCallback != null) {
            handler.post(
                    new WifiPickerTracker$$ExternalSyntheticLambda36(wifiPickerTrackerCallback, 1));
        }
        updateWifiEntries(0, "force", false);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleQosScoreCacheUpdated() {
        WifiQoSScoredCache wifiQoSScoredCache;
        Iterator it = ((ArrayList) this.mStandardWifiEntryCache).iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            wifiQoSScoredCache = this.mQoSScoredCache;
            if (!hasNext) {
                break;
            } else {
                ((StandardWifiEntry) it.next()).semUpdateScores(wifiQoSScoredCache);
            }
        }
        Iterator it2 = ((ArrayList) this.mSuggestedWifiEntryCache).iterator();
        while (it2.hasNext()) {
            ((StandardWifiEntry) it2.next()).semUpdateScores(wifiQoSScoredCache);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleRssiChangedAction() {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        Iterator it = ((ArrayList) getAllWifiEntries$1()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onPrimaryWifiInfoChanged(connectionInfo, null);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleScanResultsAvailableAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        boolean booleanExtra = intent.getBooleanExtra("resultsUpdated", true);
        handleQosScoreCacheUpdated();
        updateForceNetworkInfo(this.mWifiManager.getCurrentNetwork());
        conditionallyUpdateScanResults$3(intent.getBooleanExtra("resultsUpdated", true));
        updateWifiEntries(1, "scan", booleanExtra);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleServiceConnected() {
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            ((ArrayList) this.mKnownNetworkDataCache).clear();
            List knownNetworks = this.mSharedConnectivityManager.getKnownNetworks();
            if (knownNetworks != null) {
                ((ArrayList) this.mKnownNetworkDataCache).addAll(knownNetworks);
            }
            ((ArrayList) this.mHotspotNetworkDataCache).clear();
            List hotspotNetworks = this.mSharedConnectivityManager.getHotspotNetworks();
            if (hotspotNetworks != null) {
                ((ArrayList) this.mHotspotNetworkDataCache).addAll(hotspotNetworks);
            }
            ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
            updateKnownNetworkEntryScans(
                    scanResultUpdater.getScanResults(scanResultUpdater.mMaxScanAgeMillis));
            updateHotspotNetworkEntries();
            HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus =
                    this.mSharedConnectivityManager.getHotspotNetworkConnectionStatus();
            if (hotspotNetworkConnectionStatus != null) {
                handleHotspotNetworkConnectionStatusChanged(hotspotNetworkConnectionStatus);
            }
            updateWifiEntries(0, "service_connected", false);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleServiceDisconnected() {
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            ((ArrayList) this.mKnownNetworkDataCache).clear();
            ((ArrayList) this.mHotspotNetworkDataCache).clear();
            ((ArrayList) this.mKnownNetworkEntryCache).clear();
            ((ArrayList) this.mHotspotNetworkEntryCache).clear();
            updateWifiEntries(0, "service_disconnect", false);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleWifiStateChangedAction() {
        if (getWifiState() == 1) {
            ((ArrayList) this.mStandardWifiEntryCache).clear();
            ((ArrayList) this.mSuggestedWifiEntryCache).clear();
            ((ArrayMap) this.mPasspointWifiEntryCache).clear();
            ((ArrayMap) this.mOsuWifiEntryCache).clear();
            if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
                ((ArrayList) this.mKnownNetworkEntryCache).clear();
                ((ArrayList) this.mHotspotNetworkEntryCache).clear();
            }
            this.mNetworkRequestEntry = null;
        }
        updateWifiEntries(0, ImsProfile.PDN_WIFI, false);
    }

    public final void notifyOnWifiEntriesChanged(final int i, boolean z) {
        WifiPickerTrackerCallback wifiPickerTrackerCallback = this.mListener;
        Handler handler = this.mMainHandler;
        if (wifiPickerTrackerCallback != null) {
            handler.post(
                    new Runnable() { // from class:
                                     // com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda15
                        @Override // java.lang.Runnable
                        public final void run() {
                            WifiPickerTracker wifiPickerTracker = WifiPickerTracker.this;
                            wifiPickerTracker.mListener.onWifiEntriesChanged(i);
                        }
                    });
        }
        if (this.mSemListener != null) {
            handler.post(new AnonymousClass1(this, z, 0));
        }
    }

    public final void semForceScan() {
        if (getWifiEntries().size() <= 7) {
            SemWifiEntryFilter semWifiEntryFilter = this.mSemFilter;
            if (!semWifiEntryFilter.mIsDeveloperOptionOn) {
                semWifiEntryFilter.mWeakSignalRssi =
                        Math.min(semWifiEntryFilter.mWeakSignalRssi, -80);
                semWifiEntryFilter.mWeakSignalRssi5Ghz =
                        Math.min(semWifiEntryFilter.mWeakSignalRssi5Ghz, -77);
                StringBuilder sb = new StringBuilder("mWeakSignalRssi: ");
                sb.append(semWifiEntryFilter.mWeakSignalRssi);
                sb.append(", mWeakSignalRssi5Ghz: ");
                Preference$$ExternalSyntheticOutline0.m(
                        sb, semWifiEntryFilter.mWeakSignalRssi5Ghz, "SemWifiEntryFilter");
            }
        }
        BaseWifiTracker.Scanner scanner = this.mScanner;
        if (scanner != null) {
            int i = BaseWifiTracker.Scanner.$r8$clinit;
            scanner.scanLoop();
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void semNotifyScanStateChanged(int i) {
        if (this.mSemListener != null) {
            this.mMainHandler.post(new AnonymousClass2(i));
        }
    }

    public final void updateForceNetworkInfo(Network network) {
        NetworkInfo networkInfo = this.mConnectivityManager.getNetworkInfo(network);
        if (networkInfo == null) {
            return;
        }
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        Iterator it = ((ArrayList) this.mStandardWifiEntryCache).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        Iterator it2 = ((ArrayList) this.mSuggestedWifiEntryCache).iterator();
        while (it2.hasNext()) {
            ((WifiEntry) it2.next()).forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        Iterator it3 = ((ArrayMap) this.mPasspointWifiEntryCache).values().iterator();
        while (it3.hasNext()) {
            ((WifiEntry) it3.next()).forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        Iterator it4 = ((ArrayMap) this.mOsuWifiEntryCache).values().iterator();
        while (it4.hasNext()) {
            ((WifiEntry) it4.next()).forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null) {
            networkRequestEntry.forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
        if (mergedCarrierEntry != null) {
            mergedCarrierEntry.forceUpdateNetworkInfo(connectionInfo, networkInfo);
        }
        if (this.mInjector.isSharedConnectivityFeatureEnabled()) {
            Iterator it5 = ((ArrayList) this.mKnownNetworkEntryCache).iterator();
            while (it5.hasNext()) {
                ((WifiEntry) it5.next()).forceUpdateNetworkInfo(connectionInfo, networkInfo);
            }
            Iterator it6 = ((ArrayList) this.mHotspotNetworkEntryCache).iterator();
            while (it6.hasNext()) {
                ((WifiEntry) it6.next()).forceUpdateNetworkInfo(connectionInfo, networkInfo);
            }
        }
        if (networkInfo.isConnected() != this.mConnected.getAndSet(networkInfo.isConnected())) {
            boolean isConnected = networkInfo.isConnected();
            if (this.mSemListener != null) {
                this.mMainHandler.post(new AnonymousClass1(this, isConnected, 1));
            }
        }
    }

    public final void updateHotspotNetworkEntries() {
        NetworkCapabilities networkCapabilities;
        Map map =
                (Map)
                        this.mHotspotNetworkDataCache.stream()
                                .collect(
                                        Collectors.toMap(
                                                new WifiPickerTracker$$ExternalSyntheticLambda2(3),
                                                new WifiPickerTracker$$ExternalSyntheticLambda2(4),
                                                new WifiPickerTracker$$ExternalSyntheticLambda4(
                                                        1)));
        ArraySet arraySet = new ArraySet(map.keySet());
        ((ArrayList) this.mHotspotNetworkEntryCache)
                .removeIf(new WifiPickerTracker$$ExternalSyntheticLambda22(2, arraySet));
        ((ArrayList) this.mHotspotNetworkEntryCache)
                .forEach(new WifiPickerTracker$$ExternalSyntheticLambda7(arraySet, map, 1));
        Network network = null;
        NetworkCapabilities networkCapabilities2 = null;
        if (arraySet.isEmpty()) {
            networkCapabilities = null;
        } else {
            Network currentNetwork = this.mWifiManager.getCurrentNetwork();
            if (currentNetwork != null
                    && (networkCapabilities2 =
                                    this.mConnectivityManager.getNetworkCapabilities(
                                            currentNetwork))
                            != null) {
                networkCapabilities2 =
                        new NetworkCapabilities.Builder(networkCapabilities2)
                                .setTransportInfo(this.mWifiManager.getConnectionInfo())
                                .build();
            }
            NetworkCapabilities networkCapabilities3 = networkCapabilities2;
            network = currentNetwork;
            networkCapabilities = networkCapabilities3;
        }
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            HotspotNetworkEntry hotspotNetworkEntry =
                    new HotspotNetworkEntry(
                            this.mInjector,
                            this.mContext,
                            this.mMainHandler,
                            this.mWifiManager,
                            this.mSharedConnectivityManager,
                            (HotspotNetwork) map.get((Long) it.next()));
            if (network != null && networkCapabilities != null) {
                hotspotNetworkEntry.onNetworkCapabilitiesChanged(network, networkCapabilities);
            }
            ((ArrayList) this.mHotspotNetworkEntryCache).add(hotspotNetworkEntry);
        }
    }

    public final void updateKnownNetworkEntryScans(List list) {
        NetworkCapabilities networkCapabilities;
        final Map map =
                (Map)
                        list.stream()
                                .filter(new WifiPickerTracker$$ExternalSyntheticLambda1(0))
                                .collect(
                                        Collectors.groupingBy(
                                                new SavedNetworkTracker$$ExternalSyntheticLambda0(
                                                        5)));
        final Map map2 =
                (Map)
                        this.mKnownNetworkDataCache.stream()
                                .collect(
                                        Collectors.toMap(
                                                new WifiPickerTracker$$ExternalSyntheticLambda2(0),
                                                new WifiPickerTracker$$ExternalSyntheticLambda2(6),
                                                new WifiPickerTracker$$ExternalSyntheticLambda4(
                                                        0)));
        final int i = 0;
        ((ArrayList) this.mKnownNetworkEntryCache)
                .removeIf(
                        new Predicate() { // from class:
                                          // com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda5
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                int i2 = i;
                                Map map3 = map2;
                                switch (i2) {
                                    case 0:
                                        return !map3.keySet()
                                                .contains(
                                                        ((KnownNetworkEntry) obj)
                                                                .mKey
                                                                .mScanResultKey);
                                    default:
                                        return map3.containsKey(
                                                (StandardWifiEntry.ScanResultKey) obj);
                                }
                            }
                        });
        Stream stream = map2.keySet().stream();
        Objects.requireNonNull(map);
        final int i2 = 1;
        Set<StandardWifiEntry.ScanResultKey> set =
                (Set)
                        stream.filter(
                                        new Predicate() { // from class:
                                                          // com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda5
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                int i22 = i2;
                                                Map map3 = map;
                                                switch (i22) {
                                                    case 0:
                                                        return !map3.keySet()
                                                                .contains(
                                                                        ((KnownNetworkEntry) obj)
                                                                                .mKey
                                                                                .mScanResultKey);
                                                    default:
                                                        return map3.containsKey(
                                                                (StandardWifiEntry.ScanResultKey)
                                                                        obj);
                                                }
                                            }
                                        })
                                .collect(Collectors.toSet());
        ((ArrayList) this.mKnownNetworkEntryCache)
                .forEach(new WifiPickerTracker$$ExternalSyntheticLambda7(set, map, 0));
        Network network = null;
        NetworkCapabilities networkCapabilities2 = null;
        if (set.isEmpty()) {
            networkCapabilities = null;
        } else {
            Network currentNetwork = this.mWifiManager.getCurrentNetwork();
            if (currentNetwork != null
                    && (networkCapabilities2 =
                                    this.mConnectivityManager.getNetworkCapabilities(
                                            currentNetwork))
                            != null) {
                networkCapabilities2 =
                        new NetworkCapabilities.Builder(networkCapabilities2)
                                .setTransportInfo(this.mWifiManager.getConnectionInfo())
                                .build();
            }
            NetworkCapabilities networkCapabilities3 = networkCapabilities2;
            network = currentNetwork;
            networkCapabilities = networkCapabilities3;
        }
        for (StandardWifiEntry.ScanResultKey scanResultKey : set) {
            KnownNetworkEntry knownNetworkEntry =
                    new KnownNetworkEntry(
                            this.mInjector,
                            this.mMainHandler,
                            new StandardWifiEntry.StandardWifiEntryKey(scanResultKey, true),
                            (List) map.get(scanResultKey),
                            this.mWifiManager,
                            this.mSharedConnectivityManager,
                            (KnownNetwork) map2.get(scanResultKey));
            if (network != null && networkCapabilities != null) {
                knownNetworkEntry.onNetworkCapabilitiesChanged(network, networkCapabilities);
            }
            ((ArrayList) this.mKnownNetworkEntryCache).add(knownNetworkEntry);
        }
        ((ArrayList) this.mKnownNetworkEntryCache)
                .removeIf(new WifiPickerTracker$$ExternalSyntheticLambda1(13));
    }

    public final void updateNetworkRequestEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry == null) {
            return;
        }
        this.mNetworkRequestEntry.updateScanResultInfo(
                (List)
                        list.stream()
                                .filter(
                                        new WifiPickerTracker$$ExternalSyntheticLambda11(
                                                networkRequestEntry.mKey.mScanResultKey, 1))
                                .collect(Collectors.toList()));
    }

    public final void updateOsuWifiEntryScans$1(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        Map matchingOsuProviders = this.mWifiManager.getMatchingOsuProviders(list);
        Map matchingPasspointConfigsForOsuProviders =
                this.mWifiManager.getMatchingPasspointConfigsForOsuProviders(
                        matchingOsuProviders.keySet());
        for (OsuWifiEntry osuWifiEntry : ((ArrayMap) this.mOsuWifiEntryCache).values()) {
            osuWifiEntry.updateScanResultInfo(
                    (List) matchingOsuProviders.remove(osuWifiEntry.mOsuProvider));
        }
        for (OsuProvider osuProvider : matchingOsuProviders.keySet()) {
            OsuWifiEntry osuWifiEntry2 =
                    new OsuWifiEntry(
                            this.mInjector, this.mMainHandler, osuProvider, this.mWifiManager);
            osuWifiEntry2.updateScanResultInfo((List) matchingOsuProviders.get(osuProvider));
            ((ArrayMap) this.mOsuWifiEntryCache)
                    .put(OsuWifiEntry.osuProviderToOsuWifiEntryKey(osuProvider), osuWifiEntry2);
        }
        ((ArrayMap) this.mOsuWifiEntryCache)
                .values()
                .forEach(
                        new WifiPickerTracker$$ExternalSyntheticLambda18(
                                this, matchingPasspointConfigsForOsuProviders, 1));
        ((ArrayMap) this.mOsuWifiEntryCache)
                .entrySet()
                .removeIf(new WifiPickerTracker$$ExternalSyntheticLambda1(10));
        ((ArrayMap) this.mOsuWifiEntryCache)
                .entrySet()
                .forEach(new WifiPickerTracker$$ExternalSyntheticLambda42(this, 1));
    }

    public final void updatePasspointConfigurations(Map map, List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        ((ArrayMap) this.mPasspointConfigCache).clear();
        ((ArrayMap) this.mPasspointConfigCache)
                .putAll(
                        (Map)
                                list.stream()
                                        .collect(
                                                Collectors.toMap(
                                                        new WifiPickerTracker$$ExternalSyntheticLambda2(
                                                                5),
                                                        Function.identity())));
        ((ArrayMap) this.mPasspointWifiEntryCache)
                .entrySet()
                .removeIf(new WifiPickerTracker$$ExternalSyntheticLambda19(this, map, 1));
    }

    public final void updatePasspointWifiEntryScans$2(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        TreeSet treeSet = new TreeSet();
        for (Pair pair : this.mWifiManager.getAllMatchingWifiConfigs(list)) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            List list2 = (List) ((Map) pair.second).get(0);
            List list3 = (List) ((Map) pair.second).get(1);
            if ("Vendor Hotspot2.0 Profile".equals(wifiConfiguration.providerFriendlyName)) {
                Log.d(
                        "WifiPickerTracker",
                        "updatePasspointAccessPoints, Do not add if it is not matched with ANQP");
            } else {
                String uniqueIdToPasspointWifiEntryKey =
                        PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(
                                wifiConfiguration.getKey());
                treeSet.add(uniqueIdToPasspointWifiEntryKey);
                if (!((ArrayMap) this.mPasspointWifiEntryCache)
                        .containsKey(uniqueIdToPasspointWifiEntryKey)) {
                    if (wifiConfiguration.fromWifiNetworkSuggestion) {
                        Map map = this.mPasspointWifiEntryCache;
                        ArrayMap arrayMap = (ArrayMap) map;
                        arrayMap.put(
                                uniqueIdToPasspointWifiEntryKey,
                                new PasspointWifiEntry(
                                        this.mInjector,
                                        this.mMainHandler,
                                        wifiConfiguration,
                                        this.mWifiManager));
                    } else if (((ArrayMap) this.mPasspointConfigCache)
                            .containsKey(uniqueIdToPasspointWifiEntryKey)) {
                        Map map2 = this.mPasspointWifiEntryCache;
                        ArrayMap arrayMap2 = (ArrayMap) map2;
                        arrayMap2.put(
                                uniqueIdToPasspointWifiEntryKey,
                                new PasspointWifiEntry(
                                        this.mInjector,
                                        this.mMainHandler,
                                        (PasspointConfiguration)
                                                ((ArrayMap) this.mPasspointConfigCache)
                                                        .get(uniqueIdToPasspointWifiEntryKey),
                                        this.mWifiManager,
                                        false));
                    }
                }
                ((PasspointWifiEntry)
                                ((ArrayMap) this.mPasspointWifiEntryCache)
                                        .get(uniqueIdToPasspointWifiEntryKey))
                        .updateScanResultInfo(wifiConfiguration, list2, list3);
            }
        }
        ((ArrayMap) this.mPasspointWifiEntryCache)
                .entrySet()
                .removeIf(new WifiPickerTracker$$ExternalSyntheticLambda22(3, treeSet));
        ((ArrayMap) this.mPasspointWifiEntryCache)
                .entrySet()
                .forEach(new WifiPickerTracker$$ExternalSyntheticLambda42(this, 2));
    }

    public final void updateStandardWifiEntryScans$1(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        Map map =
                (Map)
                        list.stream()
                                .filter(new WifiPickerTracker$$ExternalSyntheticLambda1(11))
                                .collect(
                                        Collectors.groupingBy(
                                                new SavedNetworkTracker$$ExternalSyntheticLambda0(
                                                        5)));
        ArraySet arraySet = new ArraySet(map.keySet());
        ((ArrayList) this.mStandardWifiEntryCache)
                .forEach(new WifiPickerTracker$$ExternalSyntheticLambda7(arraySet, map, 2));
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            StandardWifiEntry.ScanResultKey scanResultKey =
                    (StandardWifiEntry.ScanResultKey) it.next();
            StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey =
                    new StandardWifiEntry.StandardWifiEntryKey(scanResultKey, true);
            ((ArrayList) this.mStandardWifiEntryCache)
                    .add(
                            new StandardWifiEntry(
                                    this.mInjector,
                                    this.mMainHandler,
                                    standardWifiEntryKey,
                                    (List)
                                            ((ArrayMap) this.mStandardWifiConfigCache)
                                                    .get(standardWifiEntryKey),
                                    (List) map.get(scanResultKey),
                                    this.mWifiManager,
                                    false));
        }
        ((ArrayList) this.mStandardWifiEntryCache)
                .removeIf(new WifiPickerTracker$$ExternalSyntheticLambda1(12));
        ((ArrayList) this.mStandardWifiEntryCache)
                .forEach(new WifiPickerTracker$$ExternalSyntheticLambda42(this, 3));
    }

    public final void updateSuggestedWifiEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        final Set set =
                (Set)
                        this.mWifiManager
                                .getWifiConfigForMatchedNetworkSuggestionsSharedWithUser(list)
                                .stream()
                                .map(new SavedNetworkTracker$$ExternalSyntheticLambda0(1))
                                .collect(Collectors.toSet());
        final Map map =
                (Map)
                        list.stream()
                                .filter(new WifiPickerTracker$$ExternalSyntheticLambda1(8))
                                .collect(
                                        Collectors.groupingBy(
                                                new SavedNetworkTracker$$ExternalSyntheticLambda0(
                                                        5)));
        final ArraySet arraySet = new ArraySet();
        ((ArrayList) this.mSuggestedWifiEntryCache)
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda40
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                Set set2 = arraySet;
                                Map map2 = map;
                                Set set3 = set;
                                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                                StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey =
                                        standardWifiEntry.mKey;
                                set2.add(standardWifiEntryKey);
                                standardWifiEntry.updateScanResultInfo(
                                        (List) map2.get(standardWifiEntryKey.mScanResultKey));
                                boolean contains = set3.contains(standardWifiEntryKey);
                                synchronized (standardWifiEntry) {
                                    standardWifiEntry.mIsUserShareable = contains;
                                }
                            }
                        });
        for (StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey :
                ((ArrayMap) this.mSuggestedConfigCache).keySet()) {
            StandardWifiEntry.ScanResultKey scanResultKey = standardWifiEntryKey.mScanResultKey;
            if (!arraySet.contains(standardWifiEntryKey) && map.containsKey(scanResultKey)) {
                StandardWifiEntry standardWifiEntry =
                        new StandardWifiEntry(
                                this.mInjector,
                                this.mMainHandler,
                                standardWifiEntryKey,
                                (List)
                                        ((ArrayMap) this.mSuggestedConfigCache)
                                                .get(standardWifiEntryKey),
                                (List) map.get(scanResultKey),
                                this.mWifiManager,
                                false);
                boolean contains = set.contains(standardWifiEntryKey);
                synchronized (standardWifiEntry) {
                    standardWifiEntry.mIsUserShareable = contains;
                }
                ((ArrayList) this.mSuggestedWifiEntryCache).add(standardWifiEntry);
            }
        }
        ((ArrayList) this.mSuggestedWifiEntryCache)
                .removeIf(new WifiPickerTracker$$ExternalSyntheticLambda1(9));
        ((ArrayList) this.mSuggestedWifiEntryCache)
                .forEach(new WifiPickerTracker$$ExternalSyntheticLambda42(this, 0));
    }

    public final void updateWifiConfigurations(Map map, List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        ((ArrayMap) this.mStandardWifiConfigCache).clear();
        ((ArrayMap) this.mSuggestedConfigCache).clear();
        this.mNetworkRequestConfigCache.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) it.next();
            if (!wifiConfiguration.carrierMerged) {
                StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey =
                        new StandardWifiEntry.StandardWifiEntryKey(wifiConfiguration, true);
                if (wifiConfiguration.isPasspoint()) {
                    this.mPasspointWifiConfigCache.put(
                            wifiConfiguration.networkId, wifiConfiguration);
                } else if (wifiConfiguration.fromWifiNetworkSuggestion) {
                    if (!((ArrayMap) this.mSuggestedConfigCache)
                            .containsKey(standardWifiEntryKey)) {
                        ((ArrayMap) this.mSuggestedConfigCache)
                                .put(standardWifiEntryKey, new ArrayList());
                    }
                    ((List) ((ArrayMap) this.mSuggestedConfigCache).get(standardWifiEntryKey))
                            .add(wifiConfiguration);
                } else if (wifiConfiguration.fromWifiNetworkSpecifier) {
                    if (!this.mNetworkRequestConfigCache.containsKey(standardWifiEntryKey)) {
                        this.mNetworkRequestConfigCache.put(standardWifiEntryKey, new ArrayList());
                    }
                    ((List) this.mNetworkRequestConfigCache.get(standardWifiEntryKey))
                            .add(wifiConfiguration);
                } else {
                    if (!((ArrayMap) this.mStandardWifiConfigCache)
                            .containsKey(standardWifiEntryKey)) {
                        ((ArrayMap) this.mStandardWifiConfigCache)
                                .put(standardWifiEntryKey, new ArrayList());
                    }
                    ((List) ((ArrayMap) this.mStandardWifiConfigCache).get(standardWifiEntryKey))
                            .add(wifiConfiguration);
                }
            }
        }
        this.mNumSavedNetworks =
                (int)
                        ((ArrayMap) this.mStandardWifiConfigCache)
                                .values().stream()
                                        .flatMap(
                                                new EditShortcutsPreferenceFragment$$ExternalSyntheticLambda3())
                                        .filter(new WifiPickerTracker$$ExternalSyntheticLambda1(1))
                                        .map(new WifiPickerTracker$$ExternalSyntheticLambda2(1))
                                        .distinct()
                                        .count();
        int i = 0;
        ((ArrayList) this.mStandardWifiEntryCache)
                .forEach(new WifiPickerTracker$$ExternalSyntheticLambda18(this, map, i));
        ((ArrayList) this.mSuggestedWifiEntryCache)
                .removeIf(new WifiPickerTracker$$ExternalSyntheticLambda19(this, map, i));
        ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
        updateSuggestedWifiEntryScans(
                scanResultUpdater.getScanResults(scanResultUpdater.mMaxScanAgeMillis));
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null) {
            networkRequestEntry.updateConfig(
                    (List) this.mNetworkRequestConfigCache.get(networkRequestEntry.mKey));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:105:0x0166, code lost:

       continue;
    */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x044d, code lost:

       if (r4.isPrimaryNetwork() != false) goto L158;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateWifiEntries(int r17, java.lang.String r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 1199
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.wifitrackerlib.WifiPickerTracker.updateWifiEntries(int,"
                    + " java.lang.String, boolean):void");
    }

    public WifiPickerTracker(
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
            WifiPickerTrackerCallback wifiPickerTrackerCallback,
            SemWifiPickerTrackerCallback semWifiPickerTrackerCallback,
            boolean z) {
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
                wifiPickerTrackerCallback,
                "WifiPickerTracker");
        this.mConnected = new AtomicBoolean(false);
        this.mIsSettingSupportEasySetup = true;
        this.mEasySetupCandidateEntries = new ArrayList();
        this.mEasySetupEntries = new ArrayList();
        this.mSemEasySetupScanSettings = new ArrayList();
        this.mLockEasySetup = new Object();
        this.mLockAutoHotspot = new Object();
        this.mActiveWifiEntries = new ArrayList();
        this.mWifiEntries = new ArrayList();
        this.mAutoHotspotEntries = new ArrayList();
        this.mStandardWifiConfigCache = new ArrayMap();
        this.mSuggestedConfigCache = new ArrayMap();
        this.mNetworkRequestConfigCache = new ArrayMap();
        this.mStandardWifiEntryCache = new ArrayList();
        this.mSuggestedWifiEntryCache = new ArrayList();
        this.mPasspointConfigCache = new ArrayMap();
        this.mPasspointWifiConfigCache = new SparseArray();
        this.mPasspointWifiEntryCache = new ArrayMap();
        this.mOsuWifiEntryCache = new ArrayMap();
        this.mKnownNetworkDataCache = new ArrayList();
        this.mKnownNetworkEntryCache = new ArrayList();
        this.mHotspotNetworkDataCache = new ArrayList();
        this.mHotspotNetworkEntryCache = new ArrayList();
        this.mListener = wifiPickerTrackerCallback;
        this.mSemListener = semWifiPickerTrackerCallback;
        this.mIsSettingsTracker = z;
        this.mSemFilter = new SemWifiEntryFilter(context);
        SemWifiEntryFlags.isWpa3SaeSupported = -1;
        SemWifiEntryFlags.isWpa3SaeH2eSupported = -1;
        SemWifiEntryFlags.isWpa3OweSupported = -1;
        SemWifiEntryFlags.isWpa3SuiteBSupported = -1;
        SemWifiEntryFlags.isWifiDeveloperOptionOn = -1;
        SemWifiEntryFlags.isBlockedUnSecureWifiAutoJoin = -1;
        SemWifiEntryFlags.isShowBandSummaryOn = -1;
        SemWifiEntryFlags.isWepAllowed = -1;
        this.mEasySetupUtils = new EasySetupUtils(context);
    }
}
