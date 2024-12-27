package com.samsung.android.settings.wifi.develop.connectioninfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.MacAddress;
import android.net.NetworkInfo;
import android.net.RouteInfo;
import android.net.wifi.MloLink;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiScanner;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.IMSParameter;
import com.sec.ims.configuration.DATA;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ConnectionInfoRepo implements LifecycleObserver, OnStart, OnStop {
    public final ConnectionInfo mConnectionInfo;
    public final ConnectionInfoAnalyzer mConnectionInfoAnalyzer;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final ConnectionInfoRepoHandler mHandler;
    public boolean mIsValidCu;
    public final SemWifiManager mSemWifiManager;
    public final SemWifiPartialScanner mSemWifiPartialScanner;
    public WifiInfo mWifiInfo;
    public final WifiManager mWifiManager;
    public InfoUpdatedCallback mConnectionInfoUpdatedCallback = null;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals("android.net.wifi.STATE_CHANGE")) {
                        NetworkInfo networkInfo =
                                (NetworkInfo)
                                        intent.getParcelableExtra(
                                                IMSParameter.GENERAL.NETWORK_INFO);
                        ConnectionInfoRepoHandler connectionInfoRepoHandler =
                                ConnectionInfoRepo.this.mHandler;
                        connectionInfoRepoHandler.sendMessage(
                                connectionInfoRepoHandler.obtainMessage(1, networkInfo));
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConnectionInfoRepoHandler extends Handler {
        public ConnectionInfoRepoHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            int i2 = 0;
            ConnectionInfoRepo connectionInfoRepo = ConnectionInfoRepo.this;
            if (i != 1) {
                if (i == 2) {
                    connectionInfoRepo.updateWifiInfo();
                    InfoUpdatedCallback infoUpdatedCallback =
                            connectionInfoRepo.mConnectionInfoUpdatedCallback;
                    if (infoUpdatedCallback != null) {
                        infoUpdatedCallback.wifiInfoUpdated();
                    }
                    sendEmptyMessageDelayed(2, 3000L);
                    return;
                }
                if (i != 3) {
                    if (i != 4) {
                        Preference$$ExternalSyntheticOutline0.m(
                                new StringBuilder("handleMessage undefined msg: msg.what = "),
                                message.what,
                                "ConnectionInfoRepo");
                        return;
                    }
                    connectionInfoRepo.updateTcpMonitorInfo();
                    InfoUpdatedCallback infoUpdatedCallback2 =
                            connectionInfoRepo.mConnectionInfoUpdatedCallback;
                    if (infoUpdatedCallback2 != null) {
                        infoUpdatedCallback2.tcpMonitorInfoUpdated();
                    }
                    sendEmptyMessageDelayed(4, 1000L);
                    return;
                }
                WifiScanner.ScanData[] scanDataArr = (WifiScanner.ScanData[]) message.obj;
                ConnectionInfo connectionInfo = connectionInfoRepo.mConnectionInfo;
                String str = connectionInfo.mBssid;
                if (str == null) {
                    Log.e(
                            "ConnectionInfoRepo",
                            "Failed to search mBssid on scan result - mBssid is null");
                    return;
                }
                ScanResult[] results = scanDataArr[0].getResults();
                int length = results.length;
                while (i2 < length) {
                    ScanResult scanResult = results[i2];
                    if (str.equals(scanResult.BSSID)) {
                        connectionInfoRepo.updateConnectionInfo(scanResult);
                        return;
                    }
                    i2++;
                }
                for (ScanResult scanResult2 : connectionInfoRepo.mWifiManager.getScanResults()) {
                    if (str.equals(scanResult2.BSSID)) {
                        connectionInfoRepo.updateConnectionInfo(scanResult2);
                        return;
                    }
                }
                connectionInfo.mKVR = -1;
                Log.d("ConnectionInfoRepo", "ConnectionInfoRepo: No matched BSSID");
                return;
            }
            NetworkInfo networkInfo = (NetworkInfo) message.obj;
            if (networkInfo != null) {
                connectionInfoRepo.mConnectionInfo.mIsConnected = networkInfo.isConnected();
            }
            connectionInfoRepo.mIsValidCu = false;
            connectionInfoRepo.updateWifiInfo();
            ArrayList arrayList = new ArrayList();
            ConnectionInfo connectionInfo2 = connectionInfoRepo.mConnectionInfo;
            if (connectionInfo2.mIsMloConnected) {
                for (MloLink mloLink : connectionInfoRepo.getAssociatedMloLinks()) {
                    arrayList.add(
                            Integer.valueOf(
                                    ScanResult.convertChannelToFrequencyMhzIfSupported(
                                            mloLink.getChannel(), mloLink.getBand())));
                }
            }
            if (arrayList.size() == 0) {
                arrayList.add(Integer.valueOf(connectionInfo2.mFrequency));
            }
            SemWifiPartialScanner semWifiPartialScanner = connectionInfoRepo.mSemWifiPartialScanner;
            semWifiPartialScanner.getClass();
            Log.d("ConnectionInfo.WifiPartialScanner", "trigger partial scan: " + arrayList);
            WifiScanner.ScanSettings scanSettings = new WifiScanner.ScanSettings();
            scanSettings.type = 2;
            scanSettings.band = 0;
            scanSettings.channels = new WifiScanner.ChannelSpec[arrayList.size()];
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                scanSettings.channels[i2] =
                        new WifiScanner.ChannelSpec(((Integer) it.next()).intValue());
                i2++;
            }
            scanSettings.reportEvents = 3;
            semWifiPartialScanner.mWifiScanner.startScan(scanSettings, semWifiPartialScanner);
            connectionInfo2.mIpAddress = null;
            connectionInfo2.mNetworkPrefixLength = null;
            connectionInfo2.mGateway = null;
            connectionInfo2.mDns1 = null;
            connectionInfo2.mDns2 = null;
            LinkProperties linkProperties =
                    connectionInfoRepo.mConnectivityManager.getLinkProperties(
                            connectionInfoRepo.mWifiManager.getCurrentNetwork());
            if (linkProperties != null) {
                Iterator<LinkAddress> it2 = linkProperties.getLinkAddresses().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    LinkAddress next = it2.next();
                    InetAddress address = next.getAddress();
                    if (!address.isAnyLocalAddress()
                            && !address.isLinkLocalAddress()
                            && !address.isLoopbackAddress()
                            && (address instanceof Inet4Address)) {
                        connectionInfo2.mIpAddress = address.getHostAddress();
                        connectionInfo2.mNetworkPrefixLength =
                                Integer.toString(next.getPrefixLength());
                        break;
                    }
                }
                Iterator<RouteInfo> it3 = linkProperties.getRoutes().iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        break;
                    }
                    InetAddress gateway = it3.next().getGateway();
                    if (!gateway.isAnyLocalAddress()
                            && !gateway.isLinkLocalAddress()
                            && !gateway.isLoopbackAddress()
                            && (gateway instanceof Inet4Address)) {
                        connectionInfo2.mGateway = gateway.getHostAddress();
                        break;
                    }
                }
                Iterator<InetAddress> it4 = linkProperties.getDnsServers().iterator();
                while (true) {
                    if (!it4.hasNext()) {
                        break;
                    }
                    InetAddress next2 = it4.next();
                    if (connectionInfo2.mDns1 != null) {
                        connectionInfo2.mDns2 = next2.getHostAddress();
                        break;
                    }
                    connectionInfo2.mDns1 = next2.getHostAddress();
                }
            }
            connectionInfoRepo.updateTcpMonitorInfo();
            InfoUpdatedCallback infoUpdatedCallback3 =
                    connectionInfoRepo.mConnectionInfoUpdatedCallback;
            if (infoUpdatedCallback3 != null) {
                infoUpdatedCallback3.allConnectionInfoUpdated();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface InfoUpdatedCallback {
        void allConnectionInfoUpdated();

        void scanResultInfoUpdated();

        void tcpMonitorInfoUpdated();

        void wifiInfoUpdated();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo$1] */
    public ConnectionInfoRepo(Context context, Lifecycle lifecycle) {
        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.mChannelUtilization = -1;
        connectionInfo.mChannelUtilizationMap = new HashMap();
        this.mConnectionInfo = connectionInfo;
        this.mContext = context;
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.mHandler = new ConnectionInfoRepoHandler();
        lifecycle.addObserver(this);
        SemWifiPartialScanner semWifiPartialScanner = new SemWifiPartialScanner();
        semWifiPartialScanner.mWifiScanner = (WifiScanner) context.getSystemService("wifiscanner");
        semWifiPartialScanner.mListener = this;
        this.mSemWifiPartialScanner = semWifiPartialScanner;
        ConnectionInfoAnalyzer connectionInfoAnalyzer = new ConnectionInfoAnalyzer();
        connectionInfoAnalyzer.mModulation = PeripheralConstants.Result.NOT_AVAILABLE;
        connectionInfoAnalyzer.mbgn = PeripheralConstants.Result.NOT_AVAILABLE;
        connectionInfoAnalyzer.channelWidth = 0;
        connectionInfoAnalyzer.mGeneration = 0;
        this.mConnectionInfoAnalyzer = connectionInfoAnalyzer;
    }

    public static String getBandString(int i) {
        return i != 1
                ? i != 2
                        ? i != 8
                                ? i != 16 ? PeripheralConstants.Result.NOT_AVAILABLE : "60GHz"
                                : "6GHz"
                        : "5GHz"
                : "2.4GHz";
    }

    public final List getAffiliatedMloLinks() {
        List<MloLink> affiliatedMloLinks =
                (this.mWifiInfo == null || !this.mWifiManager.isWifiStandardSupported(8))
                        ? null
                        : this.mWifiInfo.getAffiliatedMloLinks();
        return affiliatedMloLinks == null ? new ArrayList() : affiliatedMloLinks;
    }

    public final List getAssociatedMloLinks() {
        List<MloLink> associatedMloLinks =
                (this.mWifiInfo == null || !this.mWifiManager.isWifiStandardSupported(8))
                        ? null
                        : this.mWifiInfo.getAssociatedMloLinks();
        return associatedMloLinks == null ? new ArrayList() : associatedMloLinks;
    }

    public final String getBssid() {
        WifiInfo wifiInfo;
        MacAddress apMldMacAddress;
        ConnectionInfo connectionInfo = this.mConnectionInfo;
        String str = null;
        if (connectionInfo.mIsMloConnected
                && (wifiInfo = this.mWifiInfo) != null
                && (apMldMacAddress = wifiInfo.getApMldMacAddress()) != null) {
            str = apMldMacAddress.toString();
        }
        return str != null ? str.concat(" [MLD]") : connectionInfo.mBssid;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        NetworkInfo networkInfo = this.mConnectivityManager.getNetworkInfo(1);
        this.mConnectionInfo.mIsConnected = networkInfo != null && networkInfo.isConnected();
        Context context = this.mContext;
        if (context != null) {
            context.registerReceiver(
                    this.mReceiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));
        }
        ConnectionInfoRepoHandler connectionInfoRepoHandler = this.mHandler;
        connectionInfoRepoHandler.sendEmptyMessageDelayed(2, 3000L);
        connectionInfoRepoHandler.sendEmptyMessageDelayed(4, 1000L);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        Context context = this.mContext;
        if (context != null) {
            context.unregisterReceiver(this.mReceiver);
        }
        ConnectionInfoRepoHandler connectionInfoRepoHandler = this.mHandler;
        connectionInfoRepoHandler.removeMessages(2);
        connectionInfoRepoHandler.removeMessages(4);
    }

    public final void setKVRBitmask(int i, boolean z) {
        Log.d("ConnectionInfoRepo", "setKVRBitmask " + i + ", " + z);
        ConnectionInfo connectionInfo = this.mConnectionInfo;
        if (z) {
            connectionInfo.mKVR = i | connectionInfo.mKVR;
        } else {
            connectionInfo.mKVR = (~i) & connectionInfo.mKVR;
        }
    }

    public final void updateConnectionInfo(ScanResult scanResult) {
        ConnectionInfo connectionInfo = this.mConnectionInfo;
        connectionInfo.mKVR = 0;
        setKVRBitmask(4, scanResult.capabilities.contains("FT"));
        for (ScanResult.InformationElement informationElement :
                scanResult.getInformationElements()) {
            int id = informationElement.getId();
            if (id == 7) {
                try {
                    ByteBuffer order = informationElement.getBytes().order(ByteOrder.LITTLE_ENDIAN);
                    connectionInfo.mApCountryCode = String.valueOf((char) order.get(0));
                    connectionInfo.mApCountryCode += ((char) order.get(1));
                } catch (BufferUnderflowException unused) {
                    Log.e("ConnectionInfoRepo", "BufferUnderflowException WLAN_AP_COUNTRY_CODE");
                }
            } else if (id == 70) {
                BitSet valueOf = BitSet.valueOf(informationElement.getBytes());
                Log.d(
                        "ConnectionInfoRepo",
                        "Neighbor Report Bit is 1. updateNeighborReportInfoFromRmIe : " + valueOf);
                setKVRBitmask(1, valueOf.get(1));
            } else if (id == 127) {
                BitSet valueOf2 = BitSet.valueOf(informationElement.getBytes());
                Log.d(
                        "ConnectionInfoRepo",
                        "BTM Transition bit is 19. updateBtmInfoFromExtendedCapsIe : " + valueOf2);
                setKVRBitmask(2, valueOf2.get(19));
            }
        }
        int i = scanResult.channelWidth;
        ConnectionInfoAnalyzer connectionInfoAnalyzer = this.mConnectionInfoAnalyzer;
        connectionInfoAnalyzer.channelWidth = i;
        WifiInfo connectionInfo2 = this.mWifiManager.getConnectionInfo();
        this.mWifiInfo = connectionInfo2;
        if (connectionInfo2 != null) {
            connectionInfoAnalyzer.getNetworkInfofromLinkSpeed(
                    connectionInfo2.getLinkSpeed(), this.mWifiInfo.getWifiStandard());
        }
        InfoUpdatedCallback infoUpdatedCallback = this.mConnectionInfoUpdatedCallback;
        if (infoUpdatedCallback != null) {
            infoUpdatedCallback.scanResultInfoUpdated();
        }
    }

    public final void updateTcpMonitorInfo() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        String str = simpleDateFormat.format(date).toString() + ", No Data";
        ConnectionInfo connectionInfo = this.mConnectionInfo;
        connectionInfo.mDnsResults = str;
        connectionInfo.mTcpAllSocketStatistic =
                simpleDateFormat.format(date).toString() + ", No Data";
        connectionInfo.mTcpForegroundSocketStatistic =
                simpleDateFormat.format(date).toString() + ", No Data";
        String tcpMonitorDnsHistory = this.mSemWifiManager.getTcpMonitorDnsHistory(2);
        char c = 5;
        int i = 1;
        if (tcpMonitorDnsHistory != null) {
            String[] split = tcpMonitorDnsHistory.split("\n");
            if (!split[0].equals("EMPTY")) {
                int i2 = 1;
                while (i2 < split.length) {
                    String[] split2 = split[i2].split(",");
                    if (i2 == i) {
                        connectionInfo.mDnsResults = split2[0];
                    } else {
                        connectionInfo.mDnsResults += "\n" + split2[0];
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(connectionInfo.mDnsResults);
                    sb.append("\nPackage Name: ");
                    sb.append(split2[2]);
                    sb.append("\nHost Name: ");
                    sb.append(split2[3]);
                    sb.append("\nResult: ");
                    sb.append(
                            split2[c].equals("true")
                                    ? "Blocked"
                                    : split2[4].equals(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)
                                            ? "Succeed"
                                            : "Failed");
                    connectionInfo.mDnsResults = sb.toString();
                    if (split2.length == 9) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(connectionInfo.mDnsResults);
                        sb2.append(" (");
                        connectionInfo.mDnsResults =
                                ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                        sb2, split2[8], ")");
                    }
                    i2++;
                    c = 5;
                    i = 1;
                }
            }
        }
        String tcpMonitorAllSocketHistory = this.mSemWifiManager.getTcpMonitorAllSocketHistory(1);
        if (tcpMonitorAllSocketHistory != null) {
            String[] split3 = tcpMonitorAllSocketHistory.split("\n");
            if (!split3[0].equals("EMPTY")) {
                String[] split4 = split3[1].split(",");
                connectionInfo.mTcpAllSocketStatistic =
                        split4[0]
                                + "\nSocket Healthy: "
                                + split4[4]
                                + "\nEstablished: "
                                + split4[1]
                                + ", Waiting: "
                                + split4[2]
                                + ", Retransmission: "
                                + split4[3];
            }
        }
        String tcpMonitorSocketForegroundHistory =
                this.mSemWifiManager.getTcpMonitorSocketForegroundHistory(1);
        if (tcpMonitorSocketForegroundHistory != null) {
            String[] split5 = tcpMonitorSocketForegroundHistory.split("\n");
            if (split5[0].equals("EMPTY")) {
                return;
            }
            String[] split6 = split5[1].split(",");
            connectionInfo.mTcpForegroundSocketStatistic =
                    split6[0]
                            + "\nPackage Name: "
                            + split6[2]
                            + "\nSocket Healthy: "
                            + split6[6]
                            + "\nEstablished: "
                            + split6[3]
                            + ", Waiting: "
                            + split6[4]
                            + ", Retransmission: "
                            + split6[5];
        }
    }

    public final void updateWifiInfo() {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        this.mWifiInfo = connectionInfo;
        ConnectionInfo connectionInfo2 = this.mConnectionInfo;
        if (connectionInfo != null) {
            connectionInfo2.mSsid = connectionInfo.getSSID();
            connectionInfo2.mBssid = this.mWifiInfo.getBSSID();
            connectionInfo2.mFrequency = this.mWifiInfo.getFrequency();
            connectionInfo2.mRssi = this.mWifiInfo.getRssi();
            this.mWifiInfo.getLinkSpeed();
            connectionInfo2.getClass();
            connectionInfo2.mTxLinkSpeed = this.mWifiInfo.getTxLinkSpeedMbps();
            connectionInfo2.mRxLinkSpeed = this.mWifiInfo.getRxLinkSpeedMbps();
            connectionInfo2.mTxSuccess = this.mWifiInfo.getSuccessfulTxPacketsPerSecond();
            connectionInfo2.mRxSuccess = this.mWifiInfo.getSuccessfulRxPacketsPerSecond();
            connectionInfo2.mTxRetries = this.mWifiInfo.getRetriedTxPacketsPerSecond();
            connectionInfo2.mTxLost = this.mWifiInfo.getLostTxPacketsPerSecond();
            connectionInfo2.mSecurityType = this.mWifiInfo.getCurrentSecurityType();
            connectionInfo2.mStaCountryCode = this.mWifiManager.getCountryCode();
            int linkSpeed = this.mWifiInfo.getLinkSpeed();
            int wifiStandard = this.mWifiInfo.getWifiStandard();
            ConnectionInfoAnalyzer connectionInfoAnalyzer = this.mConnectionInfoAnalyzer;
            connectionInfoAnalyzer.getNetworkInfofromLinkSpeed(linkSpeed, wifiStandard);
            int wifiStandard2 = this.mWifiInfo.getWifiStandard();
            boolean z = false;
            if (wifiStandard2 == 4) {
                connectionInfoAnalyzer.mGeneration = 4;
            } else if (wifiStandard2 == 5) {
                connectionInfoAnalyzer.mGeneration = 5;
            } else if (wifiStandard2 == 6) {
                connectionInfoAnalyzer.mGeneration = 6;
            } else if (wifiStandard2 != 8) {
                connectionInfoAnalyzer.mGeneration = 0;
            } else {
                connectionInfoAnalyzer.mGeneration = 7;
            }
            connectionInfo2.mWifiGeneration = connectionInfoAnalyzer.mGeneration;
            String str = connectionInfoAnalyzer.mbgn;
            connectionInfo2.getClass();
            String str2 = connectionInfoAnalyzer.mModulation;
            connectionInfo2.getClass();
            List affiliatedMloLinks = getAffiliatedMloLinks();
            List associatedMloLinks = getAssociatedMloLinks();
            Log.d(
                    "ConnectionInfoRepo",
                    "getAffiliatedMloLinks="
                            + affiliatedMloLinks.size()
                            + " getAssociatedMloLinks="
                            + associatedMloLinks);
            if (!affiliatedMloLinks.isEmpty() && !associatedMloLinks.isEmpty()) {
                z = true;
            }
            connectionInfo2.mIsMloConnected = z;
        }
        ((HashMap) connectionInfo2.mChannelUtilizationMap).clear();
        if (connectionInfo2.mIsMloConnected) {
            Map map = connectionInfo2.mChannelUtilizationMap;
            HashMap hashMap = new HashMap();
            for (MloLink mloLink : getAssociatedMloLinks()) {
                hashMap.put(
                        Integer.valueOf(mloLink.getLinkId()), Integer.valueOf(mloLink.getBand()));
            }
            Map channelUtilizationExtended = this.mSemWifiManager.getChannelUtilizationExtended();
            HashMap hashMap2 = new HashMap();
            for (Integer num : channelUtilizationExtended.keySet()) {
                if (hashMap.containsKey(num)) {
                    hashMap2.put(
                            (Integer) hashMap.get(num),
                            (Integer) channelUtilizationExtended.get(num));
                } else {
                    Log.e(
                            "ConnectionInfoRepo",
                            "Abnormal link ID=" + num + " from " + channelUtilizationExtended);
                }
            }
            ((HashMap) map).putAll(hashMap2);
            if (((HashMap) connectionInfo2.mChannelUtilizationMap).size() > 0) {
                this.mIsValidCu = true;
            }
        } else {
            connectionInfo2.mChannelUtilization = this.mSemWifiManager.getChannelUtilization();
            if (connectionInfo2.mChannelUtilization != -1) {
                this.mIsValidCu = true;
                ((HashMap) connectionInfo2.mChannelUtilizationMap)
                        .put(
                                Integer.valueOf(Repository.toBand(connectionInfo2.mFrequency)),
                                Integer.valueOf(connectionInfo2.mChannelUtilization));
            }
        }
        Log.d(
                "ConnectionInfoRepo",
                "rssi=" + connectionInfo2.mRssi + " cu=" + connectionInfo2.mChannelUtilizationMap);
    }
}
