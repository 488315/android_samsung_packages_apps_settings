package com.android.wifitrackerlib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Pair;

import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;

import java.time.Clock;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PasspointNetworkDetailsTracker extends NetworkDetailsTracker {
    public final PasspointWifiEntry mChosenEntry;
    public WifiConfiguration mCurrentWifiConfig;
    public OsuWifiEntry mOsuWifiEntry;

    public PasspointNetworkDetailsTracker(
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
                "PasspointNetworkDetailsTracker");
        Optional<PasspointConfiguration> findAny =
                this.mWifiManager.getPasspointConfigurations().stream()
                        .filter(
                                new PasspointNetworkDetailsTracker$$ExternalSyntheticLambda0(
                                        0, str))
                        .findAny();
        if (findAny.isPresent()) {
            this.mChosenEntry =
                    new PasspointWifiEntry(
                            this.mInjector,
                            this.mMainHandler,
                            findAny.get(),
                            this.mWifiManager,
                            false);
        } else {
            Optional findAny2 =
                    this.mWifiManager.getPrivilegedConfiguredNetworks().stream()
                            .filter(
                                    new PasspointNetworkDetailsTracker$$ExternalSyntheticLambda0(
                                            1, str))
                            .findAny();
            if (!findAny2.isPresent()) {
                throw new IllegalArgumentException(
                        "Cannot find config for given PasspointWifiEntry key!");
            }
            this.mChosenEntry =
                    new PasspointWifiEntry(
                            this.mInjector,
                            this.mMainHandler,
                            (WifiConfiguration) findAny2.get(),
                            this.mWifiManager);
        }
        updateStartInfo$1();
    }

    public final void conditionallyUpdateScanResults(boolean z) {
        int wifiState = this.mWifiManager.getWifiState();
        PasspointWifiEntry passpointWifiEntry = this.mChosenEntry;
        if (wifiState == 1) {
            passpointWifiEntry.updateScanResultInfo(this.mCurrentWifiConfig, null, null);
            return;
        }
        ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
        long j = this.mMaxScanAgeMillis;
        if (z) {
            scanResultUpdater.update(this.mWifiManager.getScanResults());
        } else {
            j += this.mScanIntervalMillis;
        }
        List scanResults = scanResultUpdater.getScanResults(j);
        Iterator it = this.mWifiManager.getAllMatchingWifiConfigs(scanResults).iterator();
        while (true) {
            if (!it.hasNext()) {
                passpointWifiEntry.updateScanResultInfo(this.mCurrentWifiConfig, null, null);
                break;
            }
            Pair pair = (Pair) it.next();
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            if (TextUtils.equals(
                    PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey()),
                    passpointWifiEntry.mKey)) {
                this.mCurrentWifiConfig = wifiConfiguration;
                passpointWifiEntry.updateScanResultInfo(
                        wifiConfiguration,
                        (List) ((Map) pair.second).get(0),
                        (List) ((Map) pair.second).get(1));
                break;
            }
        }
        Map matchingOsuProviders = this.mWifiManager.getMatchingOsuProviders(scanResults);
        Map matchingPasspointConfigsForOsuProviders =
                this.mWifiManager.getMatchingPasspointConfigsForOsuProviders(
                        matchingOsuProviders.keySet());
        OsuWifiEntry osuWifiEntry = this.mOsuWifiEntry;
        if (osuWifiEntry != null) {
            osuWifiEntry.updateScanResultInfo(
                    (List) matchingOsuProviders.get(osuWifiEntry.mOsuProvider));
        } else {
            for (OsuProvider osuProvider : matchingOsuProviders.keySet()) {
                PasspointConfiguration passpointConfiguration =
                        (PasspointConfiguration)
                                matchingPasspointConfigsForOsuProviders.get(osuProvider);
                if (passpointConfiguration != null
                        && TextUtils.equals(
                                passpointWifiEntry.mKey,
                                PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(
                                        passpointConfiguration.getUniqueId()))) {
                    OsuWifiEntry osuWifiEntry2 =
                            new OsuWifiEntry(
                                    this.mInjector,
                                    this.mMainHandler,
                                    osuProvider,
                                    this.mWifiManager);
                    this.mOsuWifiEntry = osuWifiEntry2;
                    osuWifiEntry2.updateScanResultInfo(
                            (List) matchingOsuProviders.get(osuProvider));
                    OsuWifiEntry osuWifiEntry3 = this.mOsuWifiEntry;
                    synchronized (osuWifiEntry3) {
                        osuWifiEntry3.mIsAlreadyProvisioned = true;
                    }
                    OsuWifiEntry osuWifiEntry4 = this.mOsuWifiEntry;
                    synchronized (passpointWifiEntry) {
                        passpointWifiEntry.mOsuWifiEntry = osuWifiEntry4;
                        if (osuWifiEntry4 != null) {
                            osuWifiEntry4.setListener(passpointWifiEntry);
                        }
                    }
                    return;
                }
            }
        }
        OsuWifiEntry osuWifiEntry5 = this.mOsuWifiEntry;
        if (osuWifiEntry5 == null || osuWifiEntry5.mLevel != -1) {
            return;
        }
        synchronized (passpointWifiEntry) {
            passpointWifiEntry.mOsuWifiEntry = null;
        }
        this.mOsuWifiEntry = null;
    }

    @Override // com.android.wifitrackerlib.NetworkDetailsTracker
    public final WifiEntry getWifiEntry() {
        return this.mChosenEntry;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConfiguredNetworksChangedAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        this.mWifiManager.getPasspointConfigurations().stream()
                .filter(new PasspointNetworkDetailsTracker$$ExternalSyntheticLambda0(2, this))
                .findAny()
                .ifPresent(new PasspointNetworkDetailsTracker$$ExternalSyntheticLambda3(this));
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleOnStart() {
        updateStartInfo$1();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleScanResultsAvailableAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        conditionallyUpdateScanResults(intent.getBooleanExtra("resultsUpdated", true));
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleWifiStateChangedAction() {
        conditionallyUpdateScanResults(true);
    }

    public final void updateStartInfo$1() {
        this.mChosenEntry.clearConnectionInfo();
        conditionallyUpdateScanResults(true);
        this.mWifiManager.getPasspointConfigurations().stream()
                .filter(new PasspointNetworkDetailsTracker$$ExternalSyntheticLambda0(2, this))
                .findAny()
                .ifPresent(new PasspointNetworkDetailsTracker$$ExternalSyntheticLambda3(this));
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
    }
}
