package com.android.settings.wifi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.settings.R;
import com.android.settingslib.wifi.AccessPoint;
import com.sec.ims.IMSParameter;
import com.sec.ims.settings.ImsProfile;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiStatusTest extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public TextView mBSSID;
    public TextView mHiddenSSID;
    public TextView mHttpClientTest;
    public String mHttpClientTestResult;
    public TextView mIPAddr;
    public TextView mMACAddr;
    public TextView mNetworkId;
    public TextView mNetworkState;
    public final AnonymousClass2 mPingButtonHandler;
    public TextView mPingHostname;
    public String mPingHostnameResult;
    public TextView mRSSI;
    public TextView mRxLinkSpeed;
    public TextView mSSID;
    public TextView mScanList;
    public TextView mSupplicantState;
    public TextView mTxLinkSpeed;
    public WifiManager mWifiManager;
    public TextView mWifiState;
    public IntentFilter mWifiStateFilter;
    public final AnonymousClass1 mWifiStateReceiver = new BroadcastReceiver() { // from class: com.android.settings.wifi.WifiStatusTest.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                WifiStatusTest wifiStatusTest = WifiStatusTest.this;
                int intExtra = intent.getIntExtra("wifi_state", 4);
                int i = WifiStatusTest.$r8$clinit;
                wifiStatusTest.setWifiStateText(intExtra);
                return;
            }
            if (intent.getAction().equals("android.net.wifi.STATE_CHANGE")) {
                WifiStatusTest wifiStatusTest2 = WifiStatusTest.this;
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
                if (wifiStatusTest2.mWifiManager.isWifiEnabled()) {
                    android.net.wifi.WifiInfo connectionInfo = wifiStatusTest2.mWifiManager.getConnectionInfo();
                    wifiStatusTest2.mNetworkState.setText(AccessPoint.getSummary(wifiStatusTest2, connectionInfo.getSSID(), networkInfo.getDetailedState(), connectionInfo.getNetworkId() == -1, null));
                    return;
                }
                return;
            }
            if (intent.getAction().equals("android.net.wifi.SCAN_RESULTS")) {
                WifiStatusTest wifiStatusTest3 = WifiStatusTest.this;
                List<ScanResult> scanResults = wifiStatusTest3.mWifiManager.getScanResults();
                StringBuffer stringBuffer = new StringBuffer();
                if (scanResults != null) {
                    for (int size = scanResults.size() - 1; size >= 0; size--) {
                        ScanResult scanResult = scanResults.get(size);
                        if (scanResult != null && !TextUtils.isEmpty(scanResult.SSID)) {
                            stringBuffer.append(scanResult.SSID + " ");
                        }
                    }
                }
                wifiStatusTest3.mScanList.setText(stringBuffer);
                return;
            }
            if (intent.getAction().equals("android.net.wifi.supplicant.CONNECTION_CHANGE")) {
                return;
            }
            if (!intent.getAction().equals("android.net.wifi.supplicant.STATE_CHANGE")) {
                if (intent.getAction().equals("android.net.wifi.RSSI_CHANGED")) {
                    WifiStatusTest.this.mRSSI.setText(String.valueOf(intent.getIntExtra("newRssi", 0)));
                    return;
                } else {
                    if (intent.getAction().equals("android.net.wifi.NETWORK_IDS_CHANGED")) {
                        return;
                    }
                    Log.e("WifiStatusTest", "Received an unknown Wifi Intent");
                    return;
                }
            }
            WifiStatusTest wifiStatusTest4 = WifiStatusTest.this;
            SupplicantState supplicantState = (SupplicantState) intent.getParcelableExtra("newState");
            boolean hasExtra = intent.hasExtra("supplicantError");
            intent.getIntExtra("supplicantError", 0);
            if (hasExtra) {
                wifiStatusTest4.mSupplicantState.setText("ERROR AUTHENTICATING");
            } else {
                int i2 = WifiStatusTest.$r8$clinit;
                wifiStatusTest4.setSupplicantStateText(supplicantState);
            }
        }
    };
    public final AnonymousClass2 updateButtonHandler;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.wifi.WifiStatusTest$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.wifi.WifiStatusTest$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.wifi.WifiStatusTest$2] */
    public WifiStatusTest() {
        final int i = 0;
        this.mPingButtonHandler = new View.OnClickListener(this) { // from class: com.android.settings.wifi.WifiStatusTest.2
            public final /* synthetic */ WifiStatusTest this$0;

            {
                this.this$0 = this;
            }

            /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settings.wifi.WifiStatusTest$4] */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        final WifiStatusTest wifiStatusTest = this.this$0;
                        int i2 = WifiStatusTest.$r8$clinit;
                        wifiStatusTest.getClass();
                        final Handler handler = new Handler();
                        wifiStatusTest.mPingHostnameResult = wifiStatusTest.getResources().getString(R.string.radioInfo_unknown);
                        wifiStatusTest.mHttpClientTestResult = wifiStatusTest.getResources().getString(R.string.radioInfo_unknown);
                        wifiStatusTest.mPingHostname.setText(wifiStatusTest.mPingHostnameResult);
                        wifiStatusTest.mHttpClientTest.setText(wifiStatusTest.mHttpClientTestResult);
                        final ?? r0 = new Runnable() { // from class: com.android.settings.wifi.WifiStatusTest.4
                            @Override // java.lang.Runnable
                            public final void run() {
                                WifiStatusTest wifiStatusTest2 = WifiStatusTest.this;
                                wifiStatusTest2.mPingHostname.setText(wifiStatusTest2.mPingHostnameResult);
                                WifiStatusTest wifiStatusTest3 = WifiStatusTest.this;
                                wifiStatusTest3.mHttpClientTest.setText(wifiStatusTest3.mHttpClientTestResult);
                            }
                        };
                        final int i3 = 0;
                        new Thread() { // from class: com.android.settings.wifi.WifiStatusTest.5
                            @Override // java.lang.Thread, java.lang.Runnable
                            public final void run() {
                                switch (i3) {
                                    case 0:
                                        WifiStatusTest wifiStatusTest2 = wifiStatusTest;
                                        int i4 = WifiStatusTest.$r8$clinit;
                                        wifiStatusTest2.getClass();
                                        try {
                                            if (Runtime.getRuntime().exec("ping -c 1 -w 100 www.google.com").waitFor() == 0) {
                                                wifiStatusTest2.mPingHostnameResult = "Pass";
                                            } else {
                                                wifiStatusTest2.mPingHostnameResult = "Fail: Host unreachable";
                                            }
                                        } catch (UnknownHostException unused) {
                                            wifiStatusTest2.mPingHostnameResult = "Fail: Unknown Host";
                                        } catch (IOException unused2) {
                                            wifiStatusTest2.mPingHostnameResult = "Fail: IOException";
                                        } catch (InterruptedException unused3) {
                                            wifiStatusTest2.mPingHostnameResult = "Fail: InterruptedException";
                                        }
                                        handler.post(r0);
                                        return;
                                    default:
                                        WifiStatusTest wifiStatusTest3 = wifiStatusTest;
                                        int i5 = WifiStatusTest.$r8$clinit;
                                        wifiStatusTest3.getClass();
                                        HttpURLConnection httpURLConnection = null;
                                        try {
                                            try {
                                                HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL("https://www.google.com").openConnection();
                                                try {
                                                    if (httpURLConnection2.getResponseCode() == 200) {
                                                        wifiStatusTest3.mHttpClientTestResult = "Pass";
                                                    } else {
                                                        wifiStatusTest3.mHttpClientTestResult = "Fail: Code: " + httpURLConnection2.getResponseMessage();
                                                    }
                                                    httpURLConnection2.disconnect();
                                                } catch (IOException unused4) {
                                                    httpURLConnection = httpURLConnection2;
                                                    wifiStatusTest3.mHttpClientTestResult = "Fail: IOException";
                                                    if (httpURLConnection != null) {
                                                        httpURLConnection.disconnect();
                                                    }
                                                    handler.post(r0);
                                                    return;
                                                } catch (Throwable th) {
                                                    th = th;
                                                    httpURLConnection = httpURLConnection2;
                                                    if (httpURLConnection != null) {
                                                        httpURLConnection.disconnect();
                                                    }
                                                    throw th;
                                                }
                                            } catch (Throwable th2) {
                                                th = th2;
                                            }
                                        } catch (IOException unused5) {
                                        }
                                        handler.post(r0);
                                        return;
                                }
                            }
                        }.start();
                        final int i4 = 1;
                        new Thread() { // from class: com.android.settings.wifi.WifiStatusTest.5
                            @Override // java.lang.Thread, java.lang.Runnable
                            public final void run() {
                                switch (i4) {
                                    case 0:
                                        WifiStatusTest wifiStatusTest2 = wifiStatusTest;
                                        int i42 = WifiStatusTest.$r8$clinit;
                                        wifiStatusTest2.getClass();
                                        try {
                                            if (Runtime.getRuntime().exec("ping -c 1 -w 100 www.google.com").waitFor() == 0) {
                                                wifiStatusTest2.mPingHostnameResult = "Pass";
                                            } else {
                                                wifiStatusTest2.mPingHostnameResult = "Fail: Host unreachable";
                                            }
                                        } catch (UnknownHostException unused) {
                                            wifiStatusTest2.mPingHostnameResult = "Fail: Unknown Host";
                                        } catch (IOException unused2) {
                                            wifiStatusTest2.mPingHostnameResult = "Fail: IOException";
                                        } catch (InterruptedException unused3) {
                                            wifiStatusTest2.mPingHostnameResult = "Fail: InterruptedException";
                                        }
                                        handler.post(r0);
                                        return;
                                    default:
                                        WifiStatusTest wifiStatusTest3 = wifiStatusTest;
                                        int i5 = WifiStatusTest.$r8$clinit;
                                        wifiStatusTest3.getClass();
                                        HttpURLConnection httpURLConnection = null;
                                        try {
                                            try {
                                                HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL("https://www.google.com").openConnection();
                                                try {
                                                    if (httpURLConnection2.getResponseCode() == 200) {
                                                        wifiStatusTest3.mHttpClientTestResult = "Pass";
                                                    } else {
                                                        wifiStatusTest3.mHttpClientTestResult = "Fail: Code: " + httpURLConnection2.getResponseMessage();
                                                    }
                                                    httpURLConnection2.disconnect();
                                                } catch (IOException unused4) {
                                                    httpURLConnection = httpURLConnection2;
                                                    wifiStatusTest3.mHttpClientTestResult = "Fail: IOException";
                                                    if (httpURLConnection != null) {
                                                        httpURLConnection.disconnect();
                                                    }
                                                    handler.post(r0);
                                                    return;
                                                } catch (Throwable th) {
                                                    th = th;
                                                    httpURLConnection = httpURLConnection2;
                                                    if (httpURLConnection != null) {
                                                        httpURLConnection.disconnect();
                                                    }
                                                    throw th;
                                                }
                                            } catch (Throwable th2) {
                                                th = th2;
                                            }
                                        } catch (IOException unused5) {
                                        }
                                        handler.post(r0);
                                        return;
                                }
                            }
                        }.start();
                        break;
                    default:
                        android.net.wifi.WifiInfo connectionInfo = this.this$0.mWifiManager.getConnectionInfo();
                        WifiStatusTest wifiStatusTest2 = this.this$0;
                        wifiStatusTest2.setWifiStateText(wifiStatusTest2.mWifiManager.getWifiState());
                        this.this$0.mBSSID.setText(connectionInfo.getBSSID());
                        this.this$0.mHiddenSSID.setText(String.valueOf(connectionInfo.getHiddenSSID()));
                        int ipAddress = connectionInfo.getIpAddress();
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(ipAddress & 255);
                        stringBuffer.append('.');
                        stringBuffer.append((ipAddress >>> 8) & 255);
                        stringBuffer.append('.');
                        stringBuffer.append((ipAddress >>> 16) & 255);
                        stringBuffer.append('.');
                        stringBuffer.append((ipAddress >>> 24) & 255);
                        this.this$0.mIPAddr.setText(stringBuffer);
                        this.this$0.mTxLinkSpeed.setText(String.valueOf(connectionInfo.getTxLinkSpeedMbps()) + " Mbps");
                        this.this$0.mRxLinkSpeed.setText(String.valueOf(connectionInfo.getRxLinkSpeedMbps()) + " Mbps");
                        this.this$0.mMACAddr.setText(connectionInfo.getMacAddress());
                        this.this$0.mNetworkId.setText(String.valueOf(connectionInfo.getNetworkId()));
                        this.this$0.mRSSI.setText(String.valueOf(connectionInfo.getRssi()));
                        this.this$0.mSSID.setText(connectionInfo.getSSID());
                        this.this$0.setSupplicantStateText(connectionInfo.getSupplicantState());
                        break;
                }
            }
        };
        final int i2 = 1;
        this.updateButtonHandler = new View.OnClickListener(this) { // from class: com.android.settings.wifi.WifiStatusTest.2
            public final /* synthetic */ WifiStatusTest this$0;

            {
                this.this$0 = this;
            }

            /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settings.wifi.WifiStatusTest$4] */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        final WifiStatusTest wifiStatusTest = this.this$0;
                        int i22 = WifiStatusTest.$r8$clinit;
                        wifiStatusTest.getClass();
                        final Handler handler = new Handler();
                        wifiStatusTest.mPingHostnameResult = wifiStatusTest.getResources().getString(R.string.radioInfo_unknown);
                        wifiStatusTest.mHttpClientTestResult = wifiStatusTest.getResources().getString(R.string.radioInfo_unknown);
                        wifiStatusTest.mPingHostname.setText(wifiStatusTest.mPingHostnameResult);
                        wifiStatusTest.mHttpClientTest.setText(wifiStatusTest.mHttpClientTestResult);
                        final AnonymousClass4 r0 = new Runnable() { // from class: com.android.settings.wifi.WifiStatusTest.4
                            @Override // java.lang.Runnable
                            public final void run() {
                                WifiStatusTest wifiStatusTest2 = WifiStatusTest.this;
                                wifiStatusTest2.mPingHostname.setText(wifiStatusTest2.mPingHostnameResult);
                                WifiStatusTest wifiStatusTest3 = WifiStatusTest.this;
                                wifiStatusTest3.mHttpClientTest.setText(wifiStatusTest3.mHttpClientTestResult);
                            }
                        };
                        final int i3 = 0;
                        new Thread() { // from class: com.android.settings.wifi.WifiStatusTest.5
                            @Override // java.lang.Thread, java.lang.Runnable
                            public final void run() {
                                switch (i3) {
                                    case 0:
                                        WifiStatusTest wifiStatusTest2 = wifiStatusTest;
                                        int i42 = WifiStatusTest.$r8$clinit;
                                        wifiStatusTest2.getClass();
                                        try {
                                            if (Runtime.getRuntime().exec("ping -c 1 -w 100 www.google.com").waitFor() == 0) {
                                                wifiStatusTest2.mPingHostnameResult = "Pass";
                                            } else {
                                                wifiStatusTest2.mPingHostnameResult = "Fail: Host unreachable";
                                            }
                                        } catch (UnknownHostException unused) {
                                            wifiStatusTest2.mPingHostnameResult = "Fail: Unknown Host";
                                        } catch (IOException unused2) {
                                            wifiStatusTest2.mPingHostnameResult = "Fail: IOException";
                                        } catch (InterruptedException unused3) {
                                            wifiStatusTest2.mPingHostnameResult = "Fail: InterruptedException";
                                        }
                                        handler.post(r0);
                                        return;
                                    default:
                                        WifiStatusTest wifiStatusTest3 = wifiStatusTest;
                                        int i5 = WifiStatusTest.$r8$clinit;
                                        wifiStatusTest3.getClass();
                                        HttpURLConnection httpURLConnection = null;
                                        try {
                                            try {
                                                HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL("https://www.google.com").openConnection();
                                                try {
                                                    if (httpURLConnection2.getResponseCode() == 200) {
                                                        wifiStatusTest3.mHttpClientTestResult = "Pass";
                                                    } else {
                                                        wifiStatusTest3.mHttpClientTestResult = "Fail: Code: " + httpURLConnection2.getResponseMessage();
                                                    }
                                                    httpURLConnection2.disconnect();
                                                } catch (IOException unused4) {
                                                    httpURLConnection = httpURLConnection2;
                                                    wifiStatusTest3.mHttpClientTestResult = "Fail: IOException";
                                                    if (httpURLConnection != null) {
                                                        httpURLConnection.disconnect();
                                                    }
                                                    handler.post(r0);
                                                    return;
                                                } catch (Throwable th) {
                                                    th = th;
                                                    httpURLConnection = httpURLConnection2;
                                                    if (httpURLConnection != null) {
                                                        httpURLConnection.disconnect();
                                                    }
                                                    throw th;
                                                }
                                            } catch (Throwable th2) {
                                                th = th2;
                                            }
                                        } catch (IOException unused5) {
                                        }
                                        handler.post(r0);
                                        return;
                                }
                            }
                        }.start();
                        final int i4 = 1;
                        new Thread() { // from class: com.android.settings.wifi.WifiStatusTest.5
                            @Override // java.lang.Thread, java.lang.Runnable
                            public final void run() {
                                switch (i4) {
                                    case 0:
                                        WifiStatusTest wifiStatusTest2 = wifiStatusTest;
                                        int i42 = WifiStatusTest.$r8$clinit;
                                        wifiStatusTest2.getClass();
                                        try {
                                            if (Runtime.getRuntime().exec("ping -c 1 -w 100 www.google.com").waitFor() == 0) {
                                                wifiStatusTest2.mPingHostnameResult = "Pass";
                                            } else {
                                                wifiStatusTest2.mPingHostnameResult = "Fail: Host unreachable";
                                            }
                                        } catch (UnknownHostException unused) {
                                            wifiStatusTest2.mPingHostnameResult = "Fail: Unknown Host";
                                        } catch (IOException unused2) {
                                            wifiStatusTest2.mPingHostnameResult = "Fail: IOException";
                                        } catch (InterruptedException unused3) {
                                            wifiStatusTest2.mPingHostnameResult = "Fail: InterruptedException";
                                        }
                                        handler.post(r0);
                                        return;
                                    default:
                                        WifiStatusTest wifiStatusTest3 = wifiStatusTest;
                                        int i5 = WifiStatusTest.$r8$clinit;
                                        wifiStatusTest3.getClass();
                                        HttpURLConnection httpURLConnection = null;
                                        try {
                                            try {
                                                HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL("https://www.google.com").openConnection();
                                                try {
                                                    if (httpURLConnection2.getResponseCode() == 200) {
                                                        wifiStatusTest3.mHttpClientTestResult = "Pass";
                                                    } else {
                                                        wifiStatusTest3.mHttpClientTestResult = "Fail: Code: " + httpURLConnection2.getResponseMessage();
                                                    }
                                                    httpURLConnection2.disconnect();
                                                } catch (IOException unused4) {
                                                    httpURLConnection = httpURLConnection2;
                                                    wifiStatusTest3.mHttpClientTestResult = "Fail: IOException";
                                                    if (httpURLConnection != null) {
                                                        httpURLConnection.disconnect();
                                                    }
                                                    handler.post(r0);
                                                    return;
                                                } catch (Throwable th) {
                                                    th = th;
                                                    httpURLConnection = httpURLConnection2;
                                                    if (httpURLConnection != null) {
                                                        httpURLConnection.disconnect();
                                                    }
                                                    throw th;
                                                }
                                            } catch (Throwable th2) {
                                                th = th2;
                                            }
                                        } catch (IOException unused5) {
                                        }
                                        handler.post(r0);
                                        return;
                                }
                            }
                        }.start();
                        break;
                    default:
                        android.net.wifi.WifiInfo connectionInfo = this.this$0.mWifiManager.getConnectionInfo();
                        WifiStatusTest wifiStatusTest2 = this.this$0;
                        wifiStatusTest2.setWifiStateText(wifiStatusTest2.mWifiManager.getWifiState());
                        this.this$0.mBSSID.setText(connectionInfo.getBSSID());
                        this.this$0.mHiddenSSID.setText(String.valueOf(connectionInfo.getHiddenSSID()));
                        int ipAddress = connectionInfo.getIpAddress();
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(ipAddress & 255);
                        stringBuffer.append('.');
                        stringBuffer.append((ipAddress >>> 8) & 255);
                        stringBuffer.append('.');
                        stringBuffer.append((ipAddress >>> 16) & 255);
                        stringBuffer.append('.');
                        stringBuffer.append((ipAddress >>> 24) & 255);
                        this.this$0.mIPAddr.setText(stringBuffer);
                        this.this$0.mTxLinkSpeed.setText(String.valueOf(connectionInfo.getTxLinkSpeedMbps()) + " Mbps");
                        this.this$0.mRxLinkSpeed.setText(String.valueOf(connectionInfo.getRxLinkSpeedMbps()) + " Mbps");
                        this.this$0.mMACAddr.setText(connectionInfo.getMacAddress());
                        this.this$0.mNetworkId.setText(String.valueOf(connectionInfo.getNetworkId()));
                        this.this$0.mRSSI.setText(String.valueOf(connectionInfo.getRssi()));
                        this.this$0.mSSID.setText(connectionInfo.getSSID());
                        this.this$0.setSupplicantStateText(connectionInfo.getSupplicantState());
                        break;
                }
            }
        };
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mWifiManager = (WifiManager) getSystemService(ImsProfile.PDN_WIFI);
        IntentFilter intentFilter = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
        this.mWifiStateFilter = intentFilter;
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        this.mWifiStateFilter.addAction("android.net.wifi.SCAN_RESULTS");
        this.mWifiStateFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
        this.mWifiStateFilter.addAction("android.net.wifi.RSSI_CHANGED");
        this.mWifiStateFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        registerReceiver(this.mWifiStateReceiver, this.mWifiStateFilter, 2);
        setContentView(R.layout.wifi_status_test);
        ((Button) findViewById(R.id.update)).setOnClickListener(this.updateButtonHandler);
        this.mWifiState = (TextView) findViewById(R.id.wifi_state);
        this.mNetworkState = (TextView) findViewById(R.id.network_state);
        this.mSupplicantState = (TextView) findViewById(R.id.supplicant_state);
        this.mRSSI = (TextView) findViewById(R.id.rssi);
        this.mBSSID = (TextView) findViewById(R.id.bssid);
        this.mSSID = (TextView) findViewById(R.id.ssid);
        this.mHiddenSSID = (TextView) findViewById(R.id.hidden_ssid);
        this.mIPAddr = (TextView) findViewById(R.id.ipaddr);
        this.mMACAddr = (TextView) findViewById(R.id.macaddr);
        this.mNetworkId = (TextView) findViewById(R.id.networkid);
        this.mTxLinkSpeed = (TextView) findViewById(R.id.tx_link_speed);
        this.mRxLinkSpeed = (TextView) findViewById(R.id.rx_link_speed);
        this.mScanList = (TextView) findViewById(R.id.scan_list);
        this.mPingHostname = (TextView) findViewById(R.id.pingHostname);
        this.mHttpClientTest = (TextView) findViewById(R.id.httpClientTest);
        ((Button) findViewById(R.id.ping_test)).setOnClickListener(this.mPingButtonHandler);
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        unregisterReceiver(this.mWifiStateReceiver);
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        registerReceiver(this.mWifiStateReceiver, this.mWifiStateFilter, 2);
    }

    public final void setSupplicantStateText(SupplicantState supplicantState) {
        if (SupplicantState.FOUR_WAY_HANDSHAKE.equals(supplicantState)) {
            this.mSupplicantState.setText("FOUR WAY HANDSHAKE");
            return;
        }
        if (SupplicantState.ASSOCIATED.equals(supplicantState)) {
            this.mSupplicantState.setText("ASSOCIATED");
            return;
        }
        if (SupplicantState.ASSOCIATING.equals(supplicantState)) {
            this.mSupplicantState.setText("ASSOCIATING");
            return;
        }
        if (SupplicantState.COMPLETED.equals(supplicantState)) {
            this.mSupplicantState.setText("COMPLETED");
            return;
        }
        if (SupplicantState.DISCONNECTED.equals(supplicantState)) {
            this.mSupplicantState.setText("DISCONNECTED");
            return;
        }
        if (SupplicantState.DORMANT.equals(supplicantState)) {
            this.mSupplicantState.setText("DORMANT");
            return;
        }
        if (SupplicantState.GROUP_HANDSHAKE.equals(supplicantState)) {
            this.mSupplicantState.setText("GROUP HANDSHAKE");
            return;
        }
        if (SupplicantState.INACTIVE.equals(supplicantState)) {
            this.mSupplicantState.setText("INACTIVE");
            return;
        }
        if (SupplicantState.INVALID.equals(supplicantState)) {
            this.mSupplicantState.setText("INVALID");
            return;
        }
        if (SupplicantState.SCANNING.equals(supplicantState)) {
            this.mSupplicantState.setText("SCANNING");
        } else if (SupplicantState.UNINITIALIZED.equals(supplicantState)) {
            this.mSupplicantState.setText("UNINITIALIZED");
        } else {
            this.mSupplicantState.setText("BAD");
            Log.e("WifiStatusTest", "supplicant state is bad");
        }
    }

    public final void setWifiStateText(int i) {
        String string;
        if (i == 0) {
            string = getString(R.string.wifi_state_disabling);
        } else if (i == 1) {
            string = getString(R.string.wifi_state_disabled);
        } else if (i == 2) {
            string = getString(R.string.wifi_state_enabling);
        } else if (i == 3) {
            string = getString(R.string.wifi_state_enabled);
        } else if (i != 4) {
            Log.e("WifiStatusTest", "wifi state is bad");
            string = "BAD";
        } else {
            string = getString(R.string.wifi_state_unknown);
        }
        this.mWifiState.setText(string);
    }
}
