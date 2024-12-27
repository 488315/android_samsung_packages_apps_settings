package com.samsung.android.settings.wifi.develop.connectioninfo;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.wifitrackerlib.SemWifiUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ConnectionL3PreferenceController extends AbstractPreferenceController
        implements LifecycleEventObserver {
    public ConnectionInfoRepo mConnectionInfoRepo;
    public ConnectionInfoRepoCallback mConnectionInfoRepoCallback;
    public Preference mDns1;
    public Preference mDns2;
    public Preference mDnsResults;
    public Preference mGateway;
    public Preference mIpAddress;
    public Preference mNetworkPrefixLength;
    public Preference mSsidBssidPref;
    public Preference mTcpAllSocketStatistic;
    public Preference mTcpForegroundSocketsStatistic;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mSsidBssidPref = preferenceScreen.findPreference("ssid_bssid");
        this.mIpAddress = preferenceScreen.findPreference("ip_address");
        this.mGateway = preferenceScreen.findPreference("gateway");
        this.mNetworkPrefixLength = preferenceScreen.findPreference("network_prefix_length");
        this.mDns1 = preferenceScreen.findPreference("dns_1");
        this.mDns2 = preferenceScreen.findPreference("dns_2");
        this.mTcpAllSocketStatistic = preferenceScreen.findPreference("all_sockets_statistic");
        this.mTcpForegroundSocketsStatistic =
                preferenceScreen.findPreference("foreground_sockets_statistic");
        this.mDnsResults = preferenceScreen.findPreference("dns_results");
        updateView$2();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        ConnectionInfoRepo.InfoUpdatedCallback infoUpdatedCallback;
        Lifecycle.Event event2 = Lifecycle.Event.ON_RESUME;
        ConnectionInfoRepoCallback connectionInfoRepoCallback = this.mConnectionInfoRepoCallback;
        ConnectionInfoRepo connectionInfoRepo = this.mConnectionInfoRepo;
        if (event == event2) {
            connectionInfoRepo.mConnectionInfoUpdatedCallback = connectionInfoRepoCallback;
            updateView$2();
        } else if (event == Lifecycle.Event.ON_PAUSE
                && (infoUpdatedCallback = connectionInfoRepo.mConnectionInfoUpdatedCallback) != null
                && infoUpdatedCallback.equals(connectionInfoRepoCallback)) {
            connectionInfoRepo.mConnectionInfoUpdatedCallback = null;
        }
    }

    public final void updateView$2() {
        StringBuilder sb = new StringBuilder("updateView ");
        ConnectionInfoRepo connectionInfoRepo = this.mConnectionInfoRepo;
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                sb, connectionInfoRepo.mConnectionInfo.mIsConnected, "ConnectionL3PrefCtrl");
        ConnectionInfo connectionInfo = connectionInfoRepo.mConnectionInfo;
        if (!connectionInfo.mIsConnected) {
            this.mSsidBssidPref.setTitle("No network connected.");
            this.mSsidBssidPref.setSummary((CharSequence) null);
            this.mIpAddress.setVisible(false);
            this.mGateway.setVisible(false);
            this.mNetworkPrefixLength.setVisible(false);
            this.mDns1.setVisible(false);
            this.mDns2.setVisible(false);
            this.mTcpAllSocketStatistic.setVisible(false);
            this.mTcpForegroundSocketsStatistic.setVisible(false);
            this.mDnsResults.setVisible(false);
            return;
        }
        this.mSsidBssidPref.setTitle(SemWifiUtils.removeDoubleQuotes(connectionInfo.mSsid));
        this.mSsidBssidPref.setSummary(connectionInfoRepo.getBssid());
        this.mSsidBssidPref.setVisible(true);
        this.mIpAddress.setSummary(connectionInfoRepo.mConnectionInfo.mIpAddress);
        this.mIpAddress.setVisible(true);
        this.mGateway.setSummary(connectionInfoRepo.mConnectionInfo.mGateway);
        this.mGateway.setVisible(true);
        this.mNetworkPrefixLength.setSummary(
                connectionInfoRepo.mConnectionInfo.mNetworkPrefixLength);
        this.mNetworkPrefixLength.setVisible(true);
        String str = connectionInfoRepo.mConnectionInfo.mDns1;
        if (str != null) {
            this.mDns1.setSummary(str);
            this.mDns1.setVisible(true);
        } else {
            this.mDns1.setVisible(false);
        }
        String str2 = connectionInfoRepo.mConnectionInfo.mDns2;
        if (str2 != null) {
            this.mDns2.setSummary(str2);
            this.mDns2.setVisible(true);
        } else {
            this.mDns2.setVisible(false);
        }
        this.mTcpAllSocketStatistic.setSummary(
                connectionInfoRepo.mConnectionInfo.mTcpAllSocketStatistic);
        this.mTcpAllSocketStatistic.setVisible(true);
        this.mTcpForegroundSocketsStatistic.setSummary(
                connectionInfoRepo.mConnectionInfo.mTcpForegroundSocketStatistic);
        this.mTcpForegroundSocketsStatistic.setVisible(true);
        this.mDnsResults.setSummary(connectionInfoRepo.mConnectionInfo.mDnsResults);
        this.mDnsResults.setVisible(true);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConnectionInfoRepoCallback
            implements ConnectionInfoRepo.InfoUpdatedCallback {
        public ConnectionInfoRepoCallback() {}

        @Override // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo.InfoUpdatedCallback
        public final void allConnectionInfoUpdated() {
            ConnectionL3PreferenceController.this.updateView$2();
        }

        @Override // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo.InfoUpdatedCallback
        public final void tcpMonitorInfoUpdated() {
            ConnectionL3PreferenceController.this.updateView$2();
        }

        @Override // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo.InfoUpdatedCallback
        public final void scanResultInfoUpdated() {}

        @Override // com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoRepo.InfoUpdatedCallback
        public final void wifiInfoUpdated() {}
    }
}
