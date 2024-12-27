package com.android.wifitrackerlib;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.text.TextUtils;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.android.settings.R;

import java.util.StringJoiner;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MergedCarrierEntry extends WifiEntry {
    public final String mKey;
    public final int mSubscriptionId;

    public MergedCarrierEntry(
            WifiTrackerInjector wifiTrackerInjector,
            Handler handler,
            WifiManager wifiManager,
            int i) {
        super(wifiTrackerInjector, handler, wifiManager);
        this.mSubscriptionId = i;
        this.mKey = SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "MergedCarrierEntry:");
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canConnect() {
        return getConnectedState() == 0;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean canDisconnect() {
        return getConnectedState() == 2;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void connect(WifiEntry.ConnectCallback connectCallback) {
        synchronized (this) {
            this.mConnectCallback = connectCallback;
            this.mWifiManager.startRestrictingAutoJoinToSubscriptionId(this.mSubscriptionId);
            if (this.mConnectCallback != null) {
                this.mCallbackHandler.post(
                        new MergedCarrierEntry$$ExternalSyntheticLambda0(this, 1));
            }
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean connectionInfoMatches(WifiInfo wifiInfo) {
        return wifiInfo.isCarrierMerged() && this.mSubscriptionId == wifiInfo.getSubscriptionId();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void disconnect(WifiEntry.DisconnectCallback disconnectCallback) {
        this.mDisconnectCallback = disconnectCallback;
        this.mWifiManager.stopRestrictingAutoJoinToSubscriptionId();
        this.mWifiManager.startScan();
        if (this.mDisconnectCallback != null) {
            this.mCallbackHandler.post(new MergedCarrierEntry$$ExternalSyntheticLambda0(this, 0));
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getKey() {
        return this.mKey;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getMacAddress() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo != null) {
            String macAddress = wifiInfo.getMacAddress();
            if (!TextUtils.isEmpty(macAddress)) {
                if (!TextUtils.equals(macAddress, "02:00:00:00:00:00")) {
                    return macAddress;
                }
            }
        }
        return null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSsid() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo == null) {
            return null;
        }
        return WifiInfo.sanitizeSsid(wifiInfo.getSSID());
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getSummary(boolean z) {
        StringJoiner stringJoiner =
                new StringJoiner(
                        this.mContext.getString(R.string.wifitrackerlib_summary_separator));
        String verboseLoggingDescription = Utils.getVerboseLoggingDescription(this, this.mSemFlags);
        if (!TextUtils.isEmpty(verboseLoggingDescription)) {
            stringJoiner.add(verboseLoggingDescription);
        }
        return stringJoiner.toString();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner("][", "[", "]");
        stringJoiner.add("SubId:" + this.mSubscriptionId);
        return super.toString() + stringJoiner;
    }
}
