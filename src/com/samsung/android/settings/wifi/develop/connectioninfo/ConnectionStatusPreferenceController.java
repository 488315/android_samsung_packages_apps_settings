package com.samsung.android.settings.wifi.develop.connectioninfo;

import android.graphics.Color;
import android.net.wifi.MloLink;
import android.util.Log;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository;
import com.samsung.android.settings.wifi.develop.utils.ChartView;
import com.samsung.android.settings.wifi.develop.utils.ChartViewPreference;
import com.samsung.android.util.SemLog;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ConnectionStatusPreferenceController extends AbstractPreferenceController
        implements LifecycleObserver {
    public ConnectionInfoRepo mConnectionInfoRepo;
    public ConnectionInfoRepoCallback mConnectionInfoRepoCallback;
    public ChartViewPreference mCuGraphPref;
    public Preference mCuPref;
    public String[] mGraphLabels;
    public Preference mLinkSpeedPref;
    public ChartViewPreference mRssiGraphPref;
    public Preference mRssiPref;
    public Preference mSsidPref;
    public Preference mTxRxPref;
    public static final int[] RSSI_GRAPH_PARAMS = {-90, -10};
    public static final int[] RSSI_GRAPH_COLORS = {
        Color.parseColor("#6BDAC2"), Color.parseColor("#40A1F6"), Color.parseColor("#C987FF")
    };
    public static final int[] CU_GRAPH_PARAMS = {0, 100};
    public static final int[] CU_GRAPH_COLORS = {
        Color.parseColor("#6BDAC2"), Color.parseColor("#40A1F6"), Color.parseColor("#C987FF")
    };

    /* renamed from: -$$Nest$mupdateView, reason: not valid java name */
    public static void m1333$$Nest$mupdateView(
            ConnectionStatusPreferenceController connectionStatusPreferenceController) {
        HashMap hashMap;
        String sb;
        String m;
        String sb2;
        ConnectionInfoRepo connectionInfoRepo =
                connectionStatusPreferenceController.mConnectionInfoRepo;
        ConnectionInfo connectionInfo = connectionInfoRepo.mConnectionInfo;
        if (connectionInfo.mIsConnected) {
            connectionStatusPreferenceController.mSsidPref.setTitle(
                    SemWifiUtils.removeDoubleQuotes(connectionInfo.mSsid));
            connectionStatusPreferenceController.mSsidPref.setSummary(
                    connectionInfoRepo.getBssid());
            Preference preference = connectionStatusPreferenceController.mRssiPref;
            if (connectionInfo.mIsMloConnected) {
                StringBuilder sb3 = new StringBuilder();
                for (MloLink mloLink : connectionInfoRepo.getAssociatedMloLinks()) {
                    sb3.append(" [");
                    sb3.append(ConnectionInfoRepo.getBandString(mloLink.getBand()));
                    sb3.append("] ");
                    if (mloLink.getRssi() == -127) {
                        sb3.append("Not in use");
                    } else {
                        sb3.append(mloLink.getRssi());
                        sb3.append(" dBm");
                    }
                    sb3.append("\n");
                }
                if (sb3.length() > 0) {
                    sb3.deleteCharAt(sb3.length() - 1);
                }
                sb = sb3.toString();
            } else {
                sb =
                        Anchor$$ExternalSyntheticOutline0.m(
                                new StringBuilder(), connectionInfo.mRssi, " dBm");
            }
            preference.setSummary(sb);
            connectionStatusPreferenceController.mRssiPref.setVisible(true);
            connectionStatusPreferenceController.mRssiGraphPref.setVisible(true);
            Preference preference2 = connectionStatusPreferenceController.mLinkSpeedPref;
            if (connectionInfo.mIsMloConnected) {
                StringBuilder sb4 = new StringBuilder();
                for (MloLink mloLink2 : connectionInfoRepo.getAssociatedMloLinks()) {
                    sb4.append(" [");
                    sb4.append(ConnectionInfoRepo.getBandString(mloLink2.getBand()));
                    sb4.append("] Tx=");
                    if (mloLink2.getTxLinkSpeedMbps() == -1) {
                        sb4.append("Unknown");
                    } else {
                        sb4.append(mloLink2.getTxLinkSpeedMbps());
                        sb4.append(" Mbps");
                    }
                    sb4.append(" / Rx=");
                    if (mloLink2.getRxLinkSpeedMbps() == -1) {
                        sb4.append("Unknown");
                    } else {
                        sb4.append(mloLink2.getRxLinkSpeedMbps());
                        sb4.append(" Mbps");
                    }
                    sb4.append("\n");
                }
                if (sb4.length() > 0) {
                    sb4.deleteCharAt(sb4.length() - 1);
                }
                m = sb4.toString();
            } else {
                StringBuilder sb5 = new StringBuilder("Tx=");
                sb5.append(connectionInfo.mTxLinkSpeed);
                sb5.append(" Mbps / Rx=");
                m = Anchor$$ExternalSyntheticOutline0.m(sb5, connectionInfo.mRxLinkSpeed, " Mbps");
            }
            preference2.setSummary(m);
            connectionStatusPreferenceController.mLinkSpeedPref.setVisible(true);
            connectionStatusPreferenceController.mTxRxPref.setSummary(
                    new DecimalFormat("0.00#").format(connectionInfo.mTxSuccess)
                            + " / "
                            + new DecimalFormat("0.00#").format(connectionInfo.mRxSuccess)
                            + " / "
                            + new DecimalFormat("0.00#").format(connectionInfo.mTxLost)
                            + " / "
                            + new DecimalFormat("0.00#").format(connectionInfo.mTxRetries)
                            + "\n(Tx success/Rx Success/Tx lost/Tx retries per sec)");
            connectionStatusPreferenceController.mTxRxPref.setVisible(true);
            if (connectionInfoRepo.mIsValidCu) {
                Preference preference3 = connectionStatusPreferenceController.mCuPref;
                if (connectionInfo.mIsMloConnected) {
                    StringBuilder sb6 = new StringBuilder();
                    for (Map.Entry entry :
                            ((HashMap) connectionInfo.mChannelUtilizationMap).entrySet()) {
                        sb6.append(" [");
                        sb6.append(
                                ConnectionInfoRepo.getBandString(
                                        ((Integer) entry.getKey()).intValue()));
                        sb6.append("] ");
                        if (((Integer) entry.getValue()).intValue() < 0
                                || ((Integer) entry.getValue()).intValue() > 255) {
                            sb6.append("Not in use");
                        } else {
                            int intValue = ((Integer) entry.getValue()).intValue();
                            if (intValue >= 0 && intValue <= 255) {
                                intValue = (intValue * 100) / 255;
                            }
                            sb6.append(intValue);
                            sb6.append("% (");
                            sb6.append(entry.getValue());
                            sb6.append("/255)");
                        }
                        sb6.append("\n");
                    }
                    if (sb6.length() > 0) {
                        sb6.deleteCharAt(sb6.length() - 1);
                    }
                    sb2 = sb6.toString();
                } else {
                    StringBuilder sb7 = new StringBuilder();
                    int i = connectionInfo.mChannelUtilization;
                    if (i >= 0 && i <= 255) {
                        i = (i * 100) / 255;
                    }
                    sb7.append(i);
                    sb7.append("% (");
                    sb2 =
                            Anchor$$ExternalSyntheticOutline0.m(
                                    sb7, connectionInfo.mChannelUtilization, "/255)");
                }
                preference3.setSummary(sb2);
                connectionStatusPreferenceController.mCuPref.setVisible(true);
                connectionStatusPreferenceController.mCuGraphPref.setVisible(true);
            } else {
                connectionStatusPreferenceController.mCuPref.setVisible(false);
                connectionStatusPreferenceController.mCuGraphPref.setVisible(false);
            }
        } else {
            connectionStatusPreferenceController.mSsidPref.setTitle("No network connected.");
            connectionStatusPreferenceController.mSsidPref.setSummary((CharSequence) null);
            connectionStatusPreferenceController.mRssiPref.setVisible(false);
            connectionStatusPreferenceController.mRssiGraphPref.setVisible(false);
            connectionStatusPreferenceController.mCuPref.setVisible(false);
            connectionStatusPreferenceController.mCuGraphPref.setVisible(false);
            connectionStatusPreferenceController.mLinkSpeedPref.setVisible(false);
            connectionStatusPreferenceController.mTxRxPref.setVisible(false);
            connectionStatusPreferenceController.mCuPref.setVisible(false);
        }
        if (connectionInfo.mIsMloConnected) {
            hashMap = new HashMap();
            for (MloLink mloLink3 : connectionInfoRepo.getAssociatedMloLinks()) {
                hashMap.put(
                        Integer.valueOf(mloLink3.getBand()), Integer.valueOf(mloLink3.getRssi()));
            }
        } else {
            hashMap = new HashMap();
            hashMap.put(
                    Integer.valueOf(Repository.toBand(connectionInfo.mFrequency)),
                    Integer.valueOf(connectionInfo.mRssi));
        }
        Log.d("ConnectionStatusPrefCtrl", "rssiMap=" + hashMap.toString());
        Integer[] numArr = new Integer[3];
        Integer num = (Integer) hashMap.get(1);
        if (num != null) {
            numArr[0] = num;
        }
        Integer num2 = (Integer) hashMap.get(2);
        if (num2 != null) {
            numArr[1] = num2;
        }
        Integer num3 = (Integer) hashMap.get(8);
        if (num3 != null) {
            numArr[2] = num3;
        }
        ChartViewPreference chartViewPreference =
                connectionStatusPreferenceController.mRssiGraphPref;
        chartViewPreference.mHistory.add(numArr);
        ChartView chartView = chartViewPreference.mChartView;
        if (chartView != null) {
            chartView.update(numArr);
        } else {
            SemLog.d("ChartViewPreference", "Chart is not initialized");
        }
        HashMap hashMap2 = new HashMap();
        for (Map.Entry entry2 : ((HashMap) connectionInfo.mChannelUtilizationMap).entrySet()) {
            Integer num4 = (Integer) entry2.getKey();
            int intValue2 = ((Integer) entry2.getValue()).intValue();
            if (intValue2 >= 0 && intValue2 <= 255) {
                intValue2 = (intValue2 * 100) / 255;
            }
            hashMap2.put(num4, Integer.valueOf(intValue2));
        }
        Log.d("ConnectionStatusPrefCtrl", "cuMap=" + hashMap2.toString());
        Integer[] numArr2 = new Integer[3];
        Integer num5 = (Integer) hashMap2.get(1);
        if (num5 != null) {
            numArr2[0] = num5;
        }
        Integer num6 = (Integer) hashMap2.get(2);
        if (num6 != null) {
            numArr2[1] = num6;
        }
        Integer num7 = (Integer) hashMap2.get(8);
        if (num7 != null) {
            numArr2[2] = num7;
        }
        ChartViewPreference chartViewPreference2 =
                connectionStatusPreferenceController.mCuGraphPref;
        chartViewPreference2.mHistory.add(numArr2);
        ChartView chartView2 = chartViewPreference2.mChartView;
        if (chartView2 != null) {
            chartView2.update(numArr2);
        } else {
            SemLog.d("ChartViewPreference", "Chart is not initialized");
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mSsidPref = preferenceScreen.findPreference("ssid");
        this.mRssiGraphPref = (ChartViewPreference) preferenceScreen.findPreference("rssi_graph");
        this.mRssiPref = preferenceScreen.findPreference("rssi");
        this.mCuPref = preferenceScreen.findPreference("channel_utilization");
        this.mCuGraphPref = (ChartViewPreference) preferenceScreen.findPreference("cu_graph");
        this.mLinkSpeedPref = preferenceScreen.findPreference("link_speed");
        this.mTxRxPref = preferenceScreen.findPreference("tx_rx_data");
        String[] strArr = this.mGraphLabels;
        ConnectionInfoRepo connectionInfoRepo = this.mConnectionInfoRepo;
        if (connectionInfoRepo != null) {
            if (!connectionInfoRepo.mWifiManager.is6GHzBandSupported() && strArr.length > 2) {
                strArr[2] = null;
            }
            if (!connectionInfoRepo.mWifiManager.is5GHzBandSupported() && strArr.length > 1) {
                strArr[1] = null;
            }
            StringBuilder sb = new StringBuilder("Supported bands=");
            sb.append(strArr[0]);
            sb.append(" ");
            sb.append(strArr[1]);
            sb.append(" ");
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    sb, strArr[2], "ConnectionStatusPrefCtrl");
        }
        ChartViewPreference chartViewPreference = this.mRssiGraphPref;
        int[] iArr = RSSI_GRAPH_PARAMS;
        int i = iArr[0];
        int i2 = iArr[1];
        chartViewPreference.mMinY = i;
        chartViewPreference.mMaxY = i2;
        chartViewPreference.mGraphColors = RSSI_GRAPH_COLORS;
        if (strArr != null) {
            chartViewPreference.mGraphLabels = strArr;
        }
        ChartViewPreference chartViewPreference2 = this.mCuGraphPref;
        int[] iArr2 = CU_GRAPH_PARAMS;
        int i3 = iArr2[0];
        int i4 = iArr2[1];
        chartViewPreference2.mMinY = i3;
        chartViewPreference2.mMaxY = i4;
        chartViewPreference2.mGraphColors = CU_GRAPH_COLORS;
        if (strArr != null) {
            chartViewPreference2.mGraphLabels = strArr;
        }
        this.mCuPref.setVisible(false);
        this.mCuGraphPref.setVisible(false);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        ConnectionInfoRepo connectionInfoRepo = this.mConnectionInfoRepo;
        ConnectionInfoRepoCallback connectionInfoRepoCallback = this.mConnectionInfoRepoCallback;
        ConnectionInfoRepo.InfoUpdatedCallback infoUpdatedCallback =
                connectionInfoRepo.mConnectionInfoUpdatedCallback;
        if (infoUpdatedCallback == null
                || !infoUpdatedCallback.equals(connectionInfoRepoCallback)) {
            return;
        }
        connectionInfoRepo.mConnectionInfoUpdatedCallback = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        this.mConnectionInfoRepo.mConnectionInfoUpdatedCallback = this.mConnectionInfoRepoCallback;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConnectionInfoRepoCallback
            implements ConnectionInfoRepo.InfoUpdatedCallback {
        public ConnectionInfoRepoCallback() {}

        @Override // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo.InfoUpdatedCallback
        public final void allConnectionInfoUpdated() {
            ConnectionStatusPreferenceController.m1333$$Nest$mupdateView(
                    ConnectionStatusPreferenceController.this);
        }

        @Override // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo.InfoUpdatedCallback
        public final void scanResultInfoUpdated() {
            ConnectionStatusPreferenceController.m1333$$Nest$mupdateView(
                    ConnectionStatusPreferenceController.this);
        }

        @Override // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo.InfoUpdatedCallback
        public final void wifiInfoUpdated() {
            ConnectionStatusPreferenceController.m1333$$Nest$mupdateView(
                    ConnectionStatusPreferenceController.this);
        }

        @Override // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo.InfoUpdatedCallback
        public final void tcpMonitorInfoUpdated() {}
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {}
}
