package com.android.wifitrackerlib;

import android.content.Context;
import android.icu.text.MessageFormat;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.os.Handler;
import android.text.BidiFormatter;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HotspotNetworkEntry extends WifiEntry {
    public boolean mConnectionError;
    public final Context mContext;
    public HotspotNetwork mHotspotNetworkData;
    public HotspotNetworkEntryKey mKey;
    public int mLastStatus;
    public final SharedConnectivityManager mSharedConnectivityManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HotspotNetworkEntryKey {
        public long mDeviceId;
        public boolean mIsVirtualEntry;
        public StandardWifiEntry.ScanResultKey mScanResultKey;

        public HotspotNetworkEntryKey(HotspotNetwork hotspotNetwork) {
            this.mDeviceId = hotspotNetwork.getDeviceId();
            if (hotspotNetwork.getHotspotSsid() == null
                    || hotspotNetwork.getHotspotSecurityTypes() == null) {
                this.mIsVirtualEntry = true;
                this.mScanResultKey = null;
            } else {
                this.mIsVirtualEntry = false;
                this.mScanResultKey =
                        new StandardWifiEntry.ScanResultKey(
                                hotspotNetwork.getHotspotSsid(),
                                new ArrayList(hotspotNetwork.getHotspotSecurityTypes()));
            }
        }

        public final String toString() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("IS_VIRTUAL_ENTRY_KEY", this.mIsVirtualEntry);
                jSONObject.put("DEVICE_ID_KEY", this.mDeviceId);
                StandardWifiEntry.ScanResultKey scanResultKey = this.mScanResultKey;
                if (scanResultKey != null) {
                    jSONObject.put("SCAN_RESULT_KEY", scanResultKey.toString());
                }
            } catch (JSONException e) {
                Log.wtf(
                        "HotspotNetworkEntry",
                        "JSONException while converting HotspotNetworkEntryKey to string: " + e);
            }
            return "HotspotNetworkEntry:" + jSONObject.toString();
        }
    }

    public HotspotNetworkEntry(
            WifiTrackerInjector wifiTrackerInjector,
            Context context,
            Handler handler,
            WifiManager wifiManager,
            SharedConnectivityManager sharedConnectivityManager,
            HotspotNetwork hotspotNetwork) {
        super(wifiTrackerInjector, handler, wifiManager);
        this.mLastStatus = 0;
        this.mConnectionError = false;
        this.mContext = context;
        this.mSharedConnectivityManager = sharedConnectivityManager;
        this.mHotspotNetworkData = hotspotNetwork;
        this.mKey = new HotspotNetworkEntryKey(hotspotNetwork);
    }

    public static String getDeviceTypeId(int i) {
        return i != 1
                ? i != 2
                        ? i != 3 ? i != 4 ? i != 5 ? "UNKNOWN" : "VEHICLE" : "WATCH" : "COMPUTER"
                        : "TABLET"
                : "PHONE";
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canConnect() {
        return getConnectedState() == 0;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canDisconnect() {
        return getConnectedState() != 0;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void connect(WifiEntry.ConnectCallback connectCallback) {
        this.mConnectCallback = connectCallback;
        SharedConnectivityManager sharedConnectivityManager = this.mSharedConnectivityManager;
        if (sharedConnectivityManager != null) {
            sharedConnectivityManager.connectHotspotNetwork(this.mHotspotNetworkData);
        } else {
            if (connectCallback != null) {
                this.mCallbackHandler.post(
                        new HotspotNetworkEntry$$ExternalSyntheticLambda0(1, connectCallback));
            }
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean connectionInfoMatches(WifiInfo wifiInfo) {
        HotspotNetworkEntryKey hotspotNetworkEntryKey = this.mKey;
        if (hotspotNetworkEntryKey.mIsVirtualEntry) {
            return false;
        }
        return Objects.equals(
                hotspotNetworkEntryKey.mScanResultKey,
                new StandardWifiEntry.ScanResultKey(
                        WifiInfo.sanitizeSsid(wifiInfo.getSSID()),
                        Collections.singletonList(
                                Integer.valueOf(wifiInfo.getCurrentSecurityType()))));
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void disconnect(WifiEntry.DisconnectCallback disconnectCallback) {
        this.mCalledDisconnect = true;
        this.mDisconnectCallback = disconnectCallback;
        SharedConnectivityManager sharedConnectivityManager = this.mSharedConnectivityManager;
        if (sharedConnectivityManager != null) {
            sharedConnectivityManager.disconnectHotspotNetwork(this.mHotspotNetworkData);
        } else {
            if (disconnectCallback != null) {
                this.mCallbackHandler.post(
                        new HotspotNetworkEntry$$ExternalSyntheticLambda0(0, disconnectCallback));
            }
        }
    }

    public final synchronized String getAlternateSummary() {
        if (this.mHotspotNetworkData == null) {
            return ApnSettings.MVNO_NONE;
        }
        return this.mContext.getString(
                R.string.wifitrackerlib_hotspot_network_alternate,
                BidiFormatter.getInstance().unicodeWrap(this.mHotspotNetworkData.getNetworkName()),
                BidiFormatter.getInstance()
                        .unicodeWrap(
                                this.mHotspotNetworkData.getNetworkProviderInfo().getDeviceName()));
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getBandString() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo == null) {
            return ApnSettings.MVNO_NONE;
        }
        return Utils.wifiInfoToBandString(this.mContext, wifiInfo);
    }

    public final synchronized int getDeviceType() {
        HotspotNetwork hotspotNetwork = this.mHotspotNetworkData;
        if (hotspotNetwork == null) {
            return 0;
        }
        return hotspotNetwork.getNetworkProviderInfo().getDeviceType();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getKey() {
        return this.mKey.toString();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final int getLevel() {
        if (getConnectedState() == 0) {
            return 4;
        }
        return this.mLevel;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getMacAddress() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo == null) {
            return null;
        }
        String macAddress = wifiInfo.getMacAddress();
        if (!TextUtils.isEmpty(macAddress)) {
            if (!TextUtils.equals(macAddress, "02:00:00:00:00:00")) {
                return macAddress;
            }
        }
        return null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final int getPrivacy() {
        return 1;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSecurityString(boolean z) {
        if (this.mHotspotNetworkData == null) {
            return ApnSettings.MVNO_NONE;
        }
        return Utils.getSecurityString(
                this.mContext,
                new ArrayList(this.mHotspotNetworkData.getHotspotSecurityTypes()),
                z);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSsid() {
        StandardWifiEntry.ScanResultKey scanResultKey = this.mKey.mScanResultKey;
        if (scanResultKey == null) {
            return null;
        }
        return scanResultKey.mSsid;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getStandardString() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo == null) {
            return ApnSettings.MVNO_NONE;
        }
        return Utils.getStandardString(this.mContext, wifiInfo.getWifiStandard());
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSummary(boolean z) {
        if (this.mHotspotNetworkData == null) {
            return ApnSettings.MVNO_NONE;
        }
        if (this.mCalledConnect) {
            return this.mContext.getString(R.string.wifitrackerlib_hotspot_network_connecting);
        }
        if (!this.mConnectionError) {
            MessageFormat messageFormat =
                    new MessageFormat(
                            this.mContext.getString(
                                    R.string.wifitrackerlib_hotspot_network_summary_new));
            HashMap hashMap = new HashMap();
            hashMap.put(
                    "DEVICE_TYPE",
                    getDeviceTypeId(
                            this.mHotspotNetworkData.getNetworkProviderInfo().getDeviceType()));
            hashMap.put("NETWORK_NAME", this.mHotspotNetworkData.getNetworkName());
            return messageFormat.format(hashMap);
        }
        switch (this.mLastStatus) {
            case 3:
            case 4:
                return this.mContext.getString(
                        R.string.wifitrackerlib_hotspot_network_summary_error_carrier_incomplete,
                        BidiFormatter.getInstance()
                                .unicodeWrap(this.mHotspotNetworkData.getNetworkName()));
            case 5:
                return this.mContext.getString(
                        R.string.wifitrackerlib_hotspot_network_summary_error_carrier_block,
                        BidiFormatter.getInstance()
                                .unicodeWrap(this.mHotspotNetworkData.getNetworkName()));
            case 6:
            case 7:
            case 8:
            case 9:
                MessageFormat messageFormat2 =
                        new MessageFormat(
                                this.mContext.getString(
                                        R.string
                                                .wifitrackerlib_hotspot_network_summary_error_settings));
                HashMap hashMap2 = new HashMap();
                hashMap2.put(
                        "DEVICE_TYPE",
                        getDeviceTypeId(
                                this.mHotspotNetworkData.getNetworkProviderInfo().getDeviceType()));
                return messageFormat2.format(hashMap2);
            default:
                return this.mContext.getString(
                        R.string.wifitrackerlib_hotspot_network_summary_error_generic);
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getTitle() {
        HotspotNetwork hotspotNetwork = this.mHotspotNetworkData;
        if (hotspotNetwork == null) {
            return ApnSettings.MVNO_NONE;
        }
        return hotspotNetwork.getNetworkProviderInfo().getDeviceName();
    }

    public final synchronized int getUpstreamConnectionStrength() {
        HotspotNetwork hotspotNetwork = this.mHotspotNetworkData;
        if (hotspotNetwork == null) {
            return 0;
        }
        return hotspotNetwork.getNetworkProviderInfo().getConnectionStrength();
    }

    public final void onConnectionStatusChanged(int i) {
        this.mLastStatus = i;
        Handler handler = this.mCallbackHandler;
        switch (i) {
            case 1:
                this.mCalledConnect = true;
                this.mConnectionError = false;
                notifyOnUpdated();
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                final int i2 = 0;
                handler.post(
                        new Runnable(
                                this) { // from class:
                                        // com.android.wifitrackerlib.HotspotNetworkEntry$$ExternalSyntheticLambda2
                            public final /* synthetic */ HotspotNetworkEntry f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                int i3 = i2;
                                HotspotNetworkEntry hotspotNetworkEntry = this.f$0;
                                switch (i3) {
                                    case 0:
                                        WifiEntry.ConnectCallback connectCallback =
                                                hotspotNetworkEntry.mConnectCallback;
                                        if (connectCallback != null) {
                                            connectCallback.onConnectResult(2);
                                            break;
                                        }
                                        break;
                                    default:
                                        WifiEntry.ConnectCallback connectCallback2 =
                                                hotspotNetworkEntry.mConnectCallback;
                                        if (connectCallback2 != null) {
                                            connectCallback2.onConnectResult(0);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
                this.mCalledConnect = false;
                this.mConnectionError = true;
                notifyOnUpdated();
                break;
            case 10:
                final int i3 = 1;
                handler.post(
                        new Runnable(
                                this) { // from class:
                                        // com.android.wifitrackerlib.HotspotNetworkEntry$$ExternalSyntheticLambda2
                            public final /* synthetic */ HotspotNetworkEntry f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                int i32 = i3;
                                HotspotNetworkEntry hotspotNetworkEntry = this.f$0;
                                switch (i32) {
                                    case 0:
                                        WifiEntry.ConnectCallback connectCallback =
                                                hotspotNetworkEntry.mConnectCallback;
                                        if (connectCallback != null) {
                                            connectCallback.onConnectResult(2);
                                            break;
                                        }
                                        break;
                                    default:
                                        WifiEntry.ConnectCallback connectCallback2 =
                                                hotspotNetworkEntry.mConnectCallback;
                                        if (connectCallback2 != null) {
                                            connectCallback2.onConnectResult(0);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
                this.mCalledConnect = false;
                this.mConnectionError = false;
                notifyOnUpdated();
                break;
        }
    }

    public final synchronized void updateHotspotNetworkData(HotspotNetwork hotspotNetwork) {
        this.mHotspotNetworkData = hotspotNetwork;
        this.mKey = new HotspotNetworkEntryKey(hotspotNetwork);
        notifyOnUpdated();
    }

    public HotspotNetworkEntry(
            WifiTrackerInjector wifiTrackerInjector,
            Context context,
            Handler handler,
            WifiManager wifiManager,
            SharedConnectivityManager sharedConnectivityManager,
            HotspotNetworkEntryKey hotspotNetworkEntryKey) {
        super(wifiTrackerInjector, handler, wifiManager);
        this.mLastStatus = 0;
        this.mConnectionError = false;
        this.mContext = context;
        this.mSharedConnectivityManager = sharedConnectivityManager;
        this.mHotspotNetworkData = null;
        this.mKey = hotspotNetworkEntryKey;
    }
}
