package com.android.wifitrackerlib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.telephony.SubscriptionManager;
import android.util.ArraySet;
import android.util.Log;

import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;

import com.samsung.android.wifi.SemWifiConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Clock;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StandardNetworkDetailsTracker extends NetworkDetailsTracker {
    public final StandardWifiEntry mChosenEntry;
    public final StandardWifiEntry.StandardWifiEntryKey mKey;

    public StandardNetworkDetailsTracker(
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
                "StandardNetworkDetailsTracker");
        StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey =
                new StandardWifiEntry.StandardWifiEntryKey();
        standardWifiEntryKey.mIsTargetingNewNetworks = false;
        StandardWifiEntry.ScanResultKey scanResultKey = new StandardWifiEntry.ScanResultKey();
        scanResultKey.mSecurityTypes = new ArraySet();
        standardWifiEntryKey.mScanResultKey = scanResultKey;
        if (str.startsWith("StandardWifiEntry:")) {
            try {
                JSONObject jSONObject = new JSONObject(str.substring(18));
                if (jSONObject.has("SCAN_RESULT_KEY")) {
                    standardWifiEntryKey.mScanResultKey =
                            new StandardWifiEntry.ScanResultKey(
                                    jSONObject.getString("SCAN_RESULT_KEY"));
                }
                if (jSONObject.has("SUGGESTION_PROFILE_KEY")) {
                    standardWifiEntryKey.mSuggestionProfileKey =
                            jSONObject.getString("SUGGESTION_PROFILE_KEY");
                }
                if (jSONObject.has("IS_NETWORK_REQUEST")) {
                    standardWifiEntryKey.mIsNetworkRequest =
                            jSONObject.getBoolean("IS_NETWORK_REQUEST");
                }
                if (jSONObject.has("IS_TARGETING_NEW_NETWORKS")) {
                    standardWifiEntryKey.mIsTargetingNewNetworks =
                            jSONObject.getBoolean("IS_TARGETING_NEW_NETWORKS");
                }
            } catch (JSONException e) {
                Log.e(
                        "StandardWifiEntry",
                        "JSONException while converting StandardWifiEntryKey to string: " + e);
            }
        } else {
            Log.e("StandardWifiEntry", "String key does not start with key prefix!");
        }
        this.mKey = standardWifiEntryKey;
        if (standardWifiEntryKey.mIsNetworkRequest) {
            this.mChosenEntry =
                    new NetworkRequestEntry(
                            this.mInjector,
                            this.mMainHandler,
                            standardWifiEntryKey,
                            this.mWifiManager,
                            false);
        } else {
            this.mChosenEntry =
                    new StandardWifiEntry(
                            this.mInjector,
                            this.mMainHandler,
                            standardWifiEntryKey,
                            this.mWifiManager,
                            false);
        }
        updateStartInfo$2();
    }

    public final void conditionallyUpdateConfig$1() {
        List list =
                (List)
                        this.mWifiManager.getPrivilegedConfiguredNetworks().stream()
                                .filter(
                                        new StandardNetworkDetailsTracker$$ExternalSyntheticLambda0(
                                                this, 0))
                                .collect(Collectors.toList());
        StandardWifiEntry standardWifiEntry = this.mChosenEntry;
        standardWifiEntry.updateConfig(list);
        if (standardWifiEntry.getWifiConfiguration() != null) {
            standardWifiEntry.semUpdateFlags(
                    (SemWifiConfiguration)
                            this.mSemWifiManager.getConfiguredNetworks().stream()
                                    .filter(
                                            new StandardNetworkDetailsTracker$$ExternalSyntheticLambda0(
                                                    this, 1))
                                    .findFirst()
                                    .orElse(null));
        }
    }

    public final void conditionallyUpdateScanResults$1(boolean z) {
        int wifiState = this.mWifiManager.getWifiState();
        StandardWifiEntry standardWifiEntry = this.mChosenEntry;
        if (wifiState == 1) {
            standardWifiEntry.updateScanResultInfo(Collections.emptyList());
            return;
        }
        ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
        long j = this.mMaxScanAgeMillis;
        if (z) {
            scanResultUpdater.update(this.mWifiManager.getScanResults());
        } else {
            j += this.mScanIntervalMillis;
        }
        standardWifiEntry.updateScanResultInfo(
                (List)
                        scanResultUpdater.getScanResults(j).stream()
                                .filter(
                                        new StandardNetworkDetailsTracker$$ExternalSyntheticLambda0(
                                                this, 2))
                                .collect(Collectors.toList()));
    }

    @Override // com.android.wifitrackerlib.NetworkDetailsTracker
    public final WifiEntry getWifiEntry() {
        return this.mChosenEntry;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConfiguredNetworksChangedAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        conditionallyUpdateConfig$1();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleOnStart() {
        updateStartInfo$2();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleScanResultsAvailableAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        conditionallyUpdateScanResults$1(intent.getBooleanExtra("resultsUpdated", true));
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleWifiStateChangedAction() {
        conditionallyUpdateScanResults$1(true);
    }

    public final void updateStartInfo$2() {
        this.mChosenEntry.clearConnectionInfo();
        conditionallyUpdateScanResults$1(true);
        conditionallyUpdateConfig$1();
        SubscriptionManager.getDefaultDataSubscriptionId();
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
