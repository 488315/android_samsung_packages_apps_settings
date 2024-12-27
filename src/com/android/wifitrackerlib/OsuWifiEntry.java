package com.android.wifitrackerlib;

import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.hotspot2.ProvisioningCallback;
import android.os.Handler;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Pair;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.core.os.BuildCompat;
import androidx.core.util.Preconditions;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class OsuWifiEntry extends WifiEntry {
    public final List mCurrentScanResults;
    public final boolean mHasAddConfigUserRestriction;
    public boolean mIsAlreadyProvisioned;
    public final String mKey;
    public final OsuProvider mOsuProvider;
    public String mOsuStatusString;
    public SemWifiManager mSemWifiManager;
    public String mSsid;
    public final List mTargetScanResults;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OsuWifiEntryProvisioningCallback extends ProvisioningCallback {
        public OsuWifiEntryProvisioningCallback() {}

        public final void onProvisioningComplete() {
            ScanResult bestScanResultByLevel;
            synchronized (OsuWifiEntry.this) {
                OsuWifiEntry osuWifiEntry = OsuWifiEntry.this;
                osuWifiEntry.mOsuStatusString =
                        osuWifiEntry.mContext.getString(
                                R.string.wifitrackerlib_osu_sign_up_complete);
            }
            OsuWifiEntry.this.notifyOnUpdated();
            OsuWifiEntry osuWifiEntry2 = OsuWifiEntry.this;
            PasspointConfiguration passpointConfiguration =
                    (PasspointConfiguration)
                            osuWifiEntry2
                                    .mWifiManager
                                    .getMatchingPasspointConfigsForOsuProviders(
                                            Collections.singleton(osuWifiEntry2.mOsuProvider))
                                    .get(OsuWifiEntry.this.mOsuProvider);
            WifiEntry.ConnectCallback connectCallback = OsuWifiEntry.this.mConnectCallback;
            if (passpointConfiguration == null) {
                if (connectCallback != null) {
                    connectCallback.onConnectResult(2);
                    return;
                }
                return;
            }
            String uniqueId = passpointConfiguration.getUniqueId();
            WifiManager wifiManager = OsuWifiEntry.this.mWifiManager;
            Iterator it =
                    wifiManager.getAllMatchingWifiConfigs(wifiManager.getScanResults()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Pair pair = (Pair) it.next();
                WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
                if (TextUtils.equals(wifiConfiguration.getKey(), uniqueId)) {
                    List list = (List) ((Map) pair.second).get(0);
                    List list2 = (List) ((Map) pair.second).get(1);
                    ((ArrayList) OsuWifiEntry.this.mTargetScanResults).clear();
                    ((ArrayList) OsuWifiEntry.this.mTargetScanResults).addAll(list);
                    ((ArrayList) OsuWifiEntry.this.mTargetScanResults).addAll(list2);
                    if (list != null && !list.isEmpty()) {
                        bestScanResultByLevel = Utils.getBestScanResultByLevel(list);
                    } else if (list2 != null && !list2.isEmpty()) {
                        bestScanResultByLevel = Utils.getBestScanResultByLevel(list2);
                    }
                    wifiConfiguration.SSID =
                            ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("\""), bestScanResultByLevel.SSID, "\"");
                    new WifiIssueDetectorUtil(OsuWifiEntry.this.mContext)
                            .reportConnectNetwork(wifiConfiguration);
                    OsuWifiEntry.this.mWifiManager.connect(wifiConfiguration, null);
                    OsuWifiEntry osuWifiEntry3 = OsuWifiEntry.this;
                    if (osuWifiEntry3.mSemWifiManager == null) {
                        osuWifiEntry3.mSemWifiManager =
                                (SemWifiManager)
                                        osuWifiEntry3.mContext.getSystemService(
                                                WiFiManagerExt.SEM_WIFI_SERVICE);
                    }
                    osuWifiEntry3.mSemWifiManager.notifyConnect(
                            wifiConfiguration.networkId, wifiConfiguration.getKey());
                    return;
                }
            }
            if (connectCallback != null) {
                connectCallback.onConnectResult(2);
            }
        }

        public final void onProvisioningFailure(int i) {
            synchronized (OsuWifiEntry.this) {
                try {
                    OsuWifiEntry osuWifiEntry = OsuWifiEntry.this;
                    if (TextUtils.equals(
                            osuWifiEntry.mOsuStatusString,
                            osuWifiEntry.mContext.getString(
                                    R.string.wifitrackerlib_osu_completing_sign_up))) {
                        OsuWifiEntry osuWifiEntry2 = OsuWifiEntry.this;
                        osuWifiEntry2.mOsuStatusString =
                                osuWifiEntry2.mContext.getString(
                                        R.string.wifitrackerlib_osu_sign_up_failed);
                    } else {
                        OsuWifiEntry osuWifiEntry3 = OsuWifiEntry.this;
                        osuWifiEntry3.mOsuStatusString =
                                osuWifiEntry3.mContext.getString(
                                        R.string.wifitrackerlib_osu_connect_failed);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            WifiEntry.ConnectCallback connectCallback = OsuWifiEntry.this.mConnectCallback;
            if (connectCallback != null) {
                connectCallback.onConnectResult(2);
            }
            OsuWifiEntry.this.notifyOnUpdated();
        }

        public final void onProvisioningStatus(int i) {
            String format;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    format =
                            String.format(
                                    OsuWifiEntry.this.mContext.getString(
                                            R.string.wifitrackerlib_osu_opening_provider),
                                    OsuWifiEntry.this.getTitle());
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                    format =
                            OsuWifiEntry.this.mContext.getString(
                                    R.string.wifitrackerlib_osu_completing_sign_up);
                    break;
                default:
                    format = null;
                    break;
            }
            synchronized (OsuWifiEntry.this) {
                try {
                    boolean z = !TextUtils.equals(OsuWifiEntry.this.mOsuStatusString, format);
                    OsuWifiEntry osuWifiEntry = OsuWifiEntry.this;
                    osuWifiEntry.mOsuStatusString = format;
                    if (z) {
                        osuWifiEntry.notifyOnUpdated();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public OsuWifiEntry(
            WifiTrackerInjector wifiTrackerInjector,
            Handler handler,
            OsuProvider osuProvider,
            WifiManager wifiManager) {
        super(wifiTrackerInjector, handler, wifiManager);
        this.mCurrentScanResults = new ArrayList();
        this.mIsAlreadyProvisioned = false;
        this.mHasAddConfigUserRestriction = false;
        this.mTargetScanResults = new ArrayList();
        Preconditions.checkNotNull(osuProvider, "Cannot construct with null osuProvider!");
        this.mOsuProvider = osuProvider;
        this.mKey = osuProviderToOsuWifiEntryKey(osuProvider);
        UserManager userManager = wifiTrackerInjector.mUserManager;
        int i = BuildCompat.$r8$clinit;
        if (userManager != null) {
            this.mHasAddConfigUserRestriction =
                    userManager.hasUserRestriction("no_add_wifi_config");
        }
    }

    public static String osuProviderToOsuWifiEntryKey(OsuProvider osuProvider) {
        Preconditions.checkNotNull(osuProvider, "Cannot create key with null OsuProvider!");
        return "OsuWifiEntry:"
                + osuProvider.getFriendlyName()
                + ","
                + osuProvider.getServerUri().toString();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canConnect() {
        boolean z = false;
        if (hasAdminRestrictions()) {
            return false;
        }
        if (this.mLevel != -1) {
            if (getConnectedState() == 0) {
                z = true;
            }
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void connect(WifiEntry.ConnectCallback connectCallback) {
        this.mConnectCallback = connectCallback;
        this.mWifiManager.stopRestrictingAutoJoinToSubscriptionId();
        this.mWifiManager.startSubscriptionProvisioning(
                this.mOsuProvider,
                this.mContext.getMainExecutor(),
                new OsuWifiEntryProvisioningCallback());
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean connectionInfoMatches(WifiInfo wifiInfo) {
        return wifiInfo.isOsuAp()
                && TextUtils.equals(
                        wifiInfo.getPasspointProviderFriendlyName(),
                        this.mOsuProvider.getFriendlyName());
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getKey() {
        return this.mKey;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getMacAddress() {
        return null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getScanResultDescription() {
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSsid() {
        return this.mSsid;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSummary(boolean z) {
        if (hasAdminRestrictions()) {
            return this.mContext.getString(R.string.wifitrackerlib_admin_restricted_network);
        }
        String str = this.mOsuStatusString;
        if (str != null) {
            return str;
        }
        synchronized (this) {
            if (this.mIsAlreadyProvisioned) {
                return z
                        ? this.mContext.getString(R.string.wifitrackerlib_wifi_passpoint_expired)
                        : this.mContext.getString(
                                R.string.wifitrackerlib_tap_to_renew_subscription_and_connect);
            }
            return this.mContext.getString(R.string.wifitrackerlib_tap_to_sign_up);
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getTitle() {
        String friendlyName = this.mOsuProvider.getFriendlyName();
        if (!TextUtils.isEmpty(friendlyName)) {
            return friendlyName;
        }
        if (!TextUtils.isEmpty(this.mSsid)) {
            return this.mSsid;
        }
        Uri serverUri = this.mOsuProvider.getServerUri();
        if (serverUri == null) {
            return ApnSettings.MVNO_NONE;
        }
        return serverUri.toString();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean hasAdminRestrictions() {
        if (this.mHasAddConfigUserRestriction) {
            if (!this.mIsAlreadyProvisioned) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final List semGetAllScanResult() {
        return this.mTargetScanResults;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean semIsAdminRestricted() {
        return hasAdminRestrictions();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner("][", "[", "]");
        stringJoiner.add("FriendlyName:" + this.mOsuProvider.getFriendlyName());
        stringJoiner.add("ServerUri:" + this.mOsuProvider.getServerUri());
        stringJoiner.add("SSID:" + this.mSsid);
        return super.toString() + stringJoiner;
    }

    public final synchronized void updateScanResultInfo(List list) {
        if (list == null) {
            try {
                list = new ArrayList();
            } catch (Throwable th) {
                throw th;
            }
        }
        ((ArrayList) this.mCurrentScanResults).clear();
        ((ArrayList) this.mCurrentScanResults).addAll(list);
        ScanResult bestScanResultByLevel = Utils.getBestScanResultByLevel(list);
        if (bestScanResultByLevel != null) {
            this.mSsid = bestScanResultByLevel.SSID;
            if (getConnectedState() == 0) {
                this.mLevel = SemWifiUtils.calculateSignalLevel(this.mRssi);
            }
            updateBestRssi(bestScanResultByLevel);
            setBand(bestScanResultByLevel.frequency);
            this.mFrequency = bestScanResultByLevel.frequency;
            this.mBssid = bestScanResultByLevel.BSSID;
        } else {
            this.mLevel = -1;
            this.mRssi = -127;
        }
        notifyOnUpdated();
    }
}
