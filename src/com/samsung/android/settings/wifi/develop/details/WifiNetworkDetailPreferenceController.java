package com.samsung.android.settings.wifi.develop.details;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.wifi.MloLink;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.samsung.android.settings.wifi.develop.WifiDevelopUtils;
import com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionInfoTabFragment;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.configuration.DATA;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiNetworkDetailPreferenceController extends AbstractPreferenceController
        implements WifiEntry.WifiEntryCallback {
    public final ConnectedViewer mConnectedViewer;
    public LayoutPreference mConnectionInfoButton;
    public final ConnectivityManager mConnectivityManager;
    public LayoutPreference mConntionView;
    public final Context mContext;
    public LayoutPreference mCreationView;
    public final ConnectedViewer mDisconnectedViewer;
    public EntityHeaderController mEntityHeaderController;
    public final PreferenceFragmentCompat mFragment;
    public SecInsetCategoryPreference mHeaderInsetPref;
    public SecInsetCategoryPreference mHeaderInsetPref2;
    public SecInsetCategoryPreference mHeaderInsetPref3;
    public final IconInjector mIconInjector;
    public LinkProperties mLinkProperties;
    public LayoutPreference mMloInfoView;
    public int mRssiSignalLevel;
    public final SemWifiManager mSemWifiManager;
    public ConnectedViewer mViewer;
    public final WifiConfiguration mWifiConfigFromEntry;
    public final WifiEntry mWifiEntry;
    public final WifiManager mWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConnectedViewer {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiNetworkDetailPreferenceController this$0;
        public final /* synthetic */ WifiNetworkDetailPreferenceController this$0$1;

        public ConnectedViewer(
                WifiNetworkDetailPreferenceController wifiNetworkDetailPreferenceController,
                int i) {
            this.$r8$classId = i;
            this.this$0 = wifiNetworkDetailPreferenceController;
            this.this$0$1 = wifiNetworkDetailPreferenceController;
        }

        public final void
                refreshRssiViews$com$samsung$android$settings$wifi$develop$details$WifiNetworkDetailPreferenceController$Viewer() {
            WifiNetworkDetailPreferenceController wifiNetworkDetailPreferenceController =
                    this.this$0$1;
            EntityHeaderController entityHeaderController =
                    wifiNetworkDetailPreferenceController.mEntityHeaderController;
            int i = wifiNetworkDetailPreferenceController.mRssiSignalLevel;
            IconInjector iconInjector = wifiNetworkDetailPreferenceController.mIconInjector;
            iconInjector.getClass();
            if (i == -1) {
                i = 0;
            }
            Drawable mutate =
                    iconInjector.mContext.getDrawable(Utils.getWifiIconResource(i)).mutate();
            int dimensionPixelSize =
                    wifiNetworkDetailPreferenceController
                            .mContext
                            .getResources()
                            .getDimensionPixelSize(R.dimen.wifi_detail_page_header_image_size);
            int minimumWidth = mutate.getMinimumWidth();
            int minimumHeight = mutate.getMinimumHeight();
            if ((minimumWidth != dimensionPixelSize || minimumHeight != dimensionPixelSize)
                    && (mutate instanceof VectorDrawable)) {
                mutate.setTintList(null);
                mutate =
                        new BitmapDrawable(
                                (Resources) null,
                                com.android.settings.Utils.createBitmap(
                                        mutate, dimensionPixelSize, dimensionPixelSize));
            }
            entityHeaderController.setIcon(mutate);
            entityHeaderController.done(
                    wifiNetworkDetailPreferenceController.mFragment.getActivity());
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IconInjector {
        public final Context mContext;

        public IconInjector(Context context) {
            this.mContext = context;
        }
    }

    public WifiNetworkDetailPreferenceController(
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            WifiEntry wifiEntry) {
        super(context);
        this.mRssiSignalLevel = -1;
        this.mContext = context;
        this.mFragment = preferenceFragmentCompat;
        ConnectedViewer connectedViewer = new ConnectedViewer(this, 1);
        this.mDisconnectedViewer = connectedViewer;
        this.mConnectedViewer = new ConnectedViewer(this, 0);
        this.mViewer = connectedViewer;
        this.mWifiEntry = wifiEntry;
        wifiEntry.setListener(this);
        this.mWifiConfigFromEntry = wifiEntry.getWifiConfiguration();
        this.mIconInjector = new IconInjector(context);
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
    }

    public final void displayConnectionView() {
        int i = 32;
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry.getConnectedState() == 0) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        String currentStateAndEnterTime = this.mSemWifiManager.getCurrentStateAndEnterTime();
        if (!TextUtils.isEmpty(currentStateAndEnterTime)) {
            String[] split = currentStateAndEnterTime.split(" ");
            if (split.length >= 2 && TextUtils.equals(split[0], "Connected")) {
                currentTimeMillis = Long.valueOf(split[1]).longValue();
            }
        }
        String convertTimeToSimpleDateFormat =
                WifiDevelopUtils.convertTimeToSimpleDateFormat(currentTimeMillis);
        Date date = new Date(currentTimeMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i2 = calendar.get(11);
        int i3 = calendar.get(12);
        TextView textView =
                (TextView) this.mConntionView.mRootView.findViewById(R.id.conneted_time_value);
        StringBuilder m =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                        convertTimeToSimpleDateFormat);
        m.append(i2 < 10 ? " 0" : " ");
        m.append(i2);
        m.append(":");
        String str = ApnSettings.MVNO_NONE;
        m.append(i3 < 10 ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN : ApnSettings.MVNO_NONE);
        m.append(i3);
        textView.setText(m.toString());
        ((TextView) this.mConntionView.mRootView.findViewById(R.id.type_value))
                .setText(wifiEntry.getStandardString());
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        if (connectionInfo != null) {
            TextView textView2 =
                    (TextView) this.mConntionView.mRootView.findViewById(R.id.band_freq_value);
            int frequency = connectionInfo.getFrequency();
            textView2.setText(
                    String.valueOf(connectionInfo.getFrequency())
                            + " MHz ("
                            + ((frequency < 2400 || frequency >= 2500)
                                    ? (frequency < 4900 || frequency >= 5900)
                                            ? (frequency < 5925 || frequency >= 7125)
                                                    ? this.mContext
                                                            .getResources()
                                                            .getString(
                                                                    R.string
                                                                            .wifitrackerlib_wifi_band_unknown)
                                                    : this.mContext
                                                            .getResources()
                                                            .getString(
                                                                    R.string
                                                                            .wifitrackerlib_wifi_band_6_ghz)
                                            : this.mContext
                                                    .getResources()
                                                    .getString(
                                                            R.string.wifitrackerlib_wifi_band_5_ghz)
                                    : this.mContext
                                            .getResources()
                                            .getString(R.string.wifitrackerlib_wifi_band_24_ghz))
                            + " band)");
            ((TextView) this.mConntionView.mRootView.findViewById(R.id.channel_value))
                    .setText(
                            String.valueOf(
                                    ScanResult.convertFrequencyMhzToChannelIfSupported(
                                            connectionInfo.getFrequency())));
            ((TextView) this.mConntionView.mRootView.findViewById(R.id.rssi_value))
                    .setText(String.valueOf(connectionInfo.getRssi()) + " dBm");
            ((TextView) this.mConntionView.mRootView.findViewById(R.id.ap_bssid_value))
                    .setText(connectionInfo.getBSSID());
        }
        TextView textView3 =
                (TextView) this.mConntionView.mRootView.findViewById(R.id.ip_addr_value);
        TextView textView4 =
                (TextView) this.mConntionView.mRootView.findViewById(R.id.subnet_addr_value);
        StringBuilder sb = new StringBuilder("\n");
        LinkProperties linkProperties = this.mLinkProperties;
        if (linkProperties != null) {
            String str2 = null;
            for (LinkAddress linkAddress : linkProperties.getLinkAddresses()) {
                if (linkAddress.getAddress() instanceof Inet4Address) {
                    str = linkAddress.getAddress().getHostAddress();
                    int prefixLength = linkAddress.getPrefixLength();
                    try {
                        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
                        if (prefixLength < 0 || prefixLength > i) {
                            throw new IllegalArgumentException(
                                    "Invalid prefix length (0 <= prefix <= 32)");
                        }
                        int i4 = prefixLength == 0 ? 0 : (-1) << (32 - prefixLength);
                        try {
                            str2 =
                                    ((Inet4Address)
                                                    InetAddress.getByAddress(
                                                            new byte[] {
                                                                (byte) ((i4 >> 24) & 255),
                                                                (byte) ((i4 >> 16) & 255),
                                                                (byte) ((i4 >> 8) & 255),
                                                                (byte) (i4 & 255)
                                                            }))
                                            .getHostAddress();
                        } catch (UnknownHostException unused) {
                            throw new AssertionError();
                        }
                    } catch (IllegalArgumentException unused2) {
                        str2 = null;
                    }
                } else if (linkAddress.getAddress() instanceof Inet6Address) {
                    sb.append(linkAddress.getAddress().getHostAddress());
                    sb.append("\n");
                }
                i = 32;
            }
            textView3.setText((str + ((Object) sb)).trim());
            textView4.setText(str2);
        }
        DhcpInfo dhcpInfo = this.mWifiManager.getDhcpInfo();
        ((TextView) this.mConntionView.mRootView.findViewById(R.id.gateway_value))
                .setText(Formatter.formatIpAddress(dhcpInfo.gateway));
        WifiConfiguration wifiConfiguration = this.mWifiConfigFromEntry;
        Bundle wifiRouterInfo =
                wifiConfiguration != null
                        ? this.mSemWifiManager.getWifiRouterInfo(wifiConfiguration.getKey())
                        : null;
        if (wifiRouterInfo != null) {
            ((TextView) this.mConntionView.mRootView.findViewById(R.id.router_mac_value))
                    .setText(wifiRouterInfo.getString("gatewayMac"));
        }
        ((TextView) this.mConntionView.mRootView.findViewById(R.id.dns_info_value))
                .setText(Formatter.formatIpAddress(dhcpInfo.dns1));
        TextView textView5 =
                (TextView) this.mConntionView.mRootView.findViewById(R.id.router_info_value);
        WifiConfiguration wifiConfiguration2 = this.mWifiConfigFromEntry;
        String wifiRouterInfoPresentable =
                wifiConfiguration2 != null
                        ? this.mSemWifiManager.getWifiRouterInfoPresentable(
                                wifiConfiguration2.getKey())
                        : null;
        if (TextUtils.isEmpty(wifiRouterInfoPresentable)) {
            this.mConntionView.mRootView.findViewById(R.id.router_info_name).setVisibility(8);
            textView5.setVisibility(8);
        } else {
            textView5.setText(wifiRouterInfoPresentable);
        }
        TextView textView6 =
                (TextView) this.mConntionView.mRootView.findViewById(R.id.internet_available_value);
        WifiConfiguration wifiConfiguration3 = this.mWifiConfigFromEntry;
        if (wifiConfiguration3 != null) {
            textView6.setText(
                    wifiConfiguration3.hasNoInternetAccess() ? "Unavailable" : "Available");
        }
        this.mConntionView.setVisible(true);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference("wifi_detail_header_for_labs");
        PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
        this.mEntityHeaderController =
                new EntityHeaderController(
                        preferenceFragmentCompat.getActivity(),
                        preferenceFragmentCompat,
                        layoutPreference.mRootView.findViewById(R.id.entity_header));
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry.getConnectedState() == 2) {
            this.mLinkProperties =
                    this.mConnectivityManager.getLinkProperties(
                            this.mWifiManager.getCurrentNetwork());
        }
        this.mHeaderInsetPref =
                (SecInsetCategoryPreference)
                        preferenceScreen.findPreference("connection_info_inset");
        this.mHeaderInsetPref2 =
                (SecInsetCategoryPreference) preferenceScreen.findPreference("mlo_info_inset");
        this.mHeaderInsetPref3 =
                (SecInsetCategoryPreference)
                        preferenceScreen.findPreference("router_history_inset");
        this.mCreationView =
                (LayoutPreference) preferenceScreen.findPreference("wifi_detail_creation_info");
        this.mConntionView =
                (LayoutPreference) preferenceScreen.findPreference("wifi_detail_connection_info");
        this.mMloInfoView =
                (LayoutPreference) preferenceScreen.findPreference("wifi_detail_mlo_info");
        TextView textView =
                (TextView) this.mCreationView.mRootView.findViewById(R.id.creation_time_value);
        List configuredNetworks =
                ((SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                        .getConfiguredNetworks();
        SemWifiConfiguration semWifiConfiguration = null;
        if (configuredNetworks != null && wifiEntry.getWifiConfiguration() != null) {
            String key = wifiEntry.getWifiConfiguration().getKey();
            Iterator it = configuredNetworks.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                SemWifiConfiguration semWifiConfiguration2 = (SemWifiConfiguration) it.next();
                if (semWifiConfiguration2.configKey.equals(key)) {
                    semWifiConfiguration = semWifiConfiguration2;
                    break;
                }
            }
        }
        textView.setText(
                WifiDevelopUtils.convertTimeToSimpleDateFormat(semWifiConfiguration.creationTime));
        ((TextView) this.mCreationView.mRootView.findViewById(R.id.security_value))
                .setText(wifiEntry.getSecurityString(false));
        WifiConfiguration wifiConfiguration = this.mWifiConfigFromEntry;
        if (wifiConfiguration != null) {
            long[] networkUsageInfo =
                    this.mSemWifiManager.getNetworkUsageInfo(wifiConfiguration.getKey());
            ((TextView) this.mCreationView.mRootView.findViewById(R.id.tx_byte_value))
                    .setText(Formatter.formatFileSize(this.mContext, networkUsageInfo[2]));
            ((TextView) this.mCreationView.mRootView.findViewById(R.id.rx_byte_value))
                    .setText(Formatter.formatFileSize(this.mContext, networkUsageInfo[3]));
        }
        ((TextView) this.mCreationView.mRootView.findViewById(R.id.phone_mac_value))
                .setText(wifiEntry.getMacAddress());
        TextView textView2 =
                (TextView) this.mCreationView.mRootView.findViewById(R.id.properties_value);
        StringBuilder sb = new StringBuilder();
        WifiConfiguration wifiConfiguration2 = this.mWifiConfigFromEntry;
        if (wifiConfiguration2 != null && wifiConfiguration2.hiddenSSID) {
            sb.append("Hidden/");
        }
        if (wifiEntry.mSemFlags.isCaptivePortal) {
            sb.append("CaptivePortal/");
        }
        if (wifiEntry.isMetered()) {
            sb.append("Metered");
        }
        if (TextUtils.isEmpty(sb)) {
            this.mCreationView.mRootView.findViewById(R.id.properties_name).setVisibility(8);
            textView2.setVisibility(8);
        } else {
            textView2.setText(sb);
        }
        this.mCreationView.setVisible(true);
        displayConnectionView();
        setMloView();
        LayoutPreference layoutPreference2 =
                (LayoutPreference) preferenceScreen.findPreference("goto_connection_info");
        this.mConnectionInfoButton = layoutPreference2;
        TextView textView3 = (TextView) layoutPreference2.mRootView.findViewById(R.id.button);
        textView3.setText("More");
        textView3.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.develop.details.WifiNetworkDetailPreferenceController.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(
                                        WifiNetworkDetailPreferenceController.this.mContext);
                        String name = ConnectionInfoTabFragment.class.getName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = name;
                        launchRequest.mSourceMetricsCategory = 0;
                        subSettingLauncher.launch();
                    }
                });
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.wifitrackerlib.WifiEntry.WifiEntryCallback
    public final void onUpdated() {
        if (this.mWifiEntry.getConnectedState() == 2) {
            this.mLinkProperties =
                    this.mConnectivityManager.getLinkProperties(
                            this.mWifiManager.getCurrentNetwork());
            this.mViewer = this.mConnectedViewer;
            displayConnectionView();
            this.mConntionView.setVisible(true);
            this.mConnectionInfoButton.setVisible(true);
            this.mHeaderInsetPref.setVisible(true);
            this.mHeaderInsetPref2.setVisible(true);
            this.mHeaderInsetPref3.setVisible(true);
            setMloView();
        } else {
            this.mLinkProperties = null;
            this.mViewer = this.mDisconnectedViewer;
            this.mConntionView.setVisible(false);
            this.mConnectionInfoButton.setVisible(false);
            this.mMloInfoView.setVisible(false);
        }
        ConnectedViewer connectedViewer = this.mViewer;
        WifiNetworkDetailPreferenceController wifiNetworkDetailPreferenceController =
                connectedViewer.this$0$1;
        EntityHeaderController entityHeaderController =
                wifiNetworkDetailPreferenceController.mEntityHeaderController;
        WifiEntry wifiEntry = wifiNetworkDetailPreferenceController.mWifiEntry;
        entityHeaderController.mLabel = wifiEntry.getTitle();
        entityHeaderController.mSummary =
                wifiEntry.getConnectedState() == 2 ? "Connected" : "Disconnected";
        entityHeaderController.done(wifiNetworkDetailPreferenceController.mFragment.getActivity());
        switch (connectedViewer.$r8$classId) {
            case 0:
                WifiNetworkDetailPreferenceController wifiNetworkDetailPreferenceController2 =
                        connectedViewer.this$0;
                int level = wifiNetworkDetailPreferenceController2.mWifiEntry.getLevel();
                if (level != wifiNetworkDetailPreferenceController2.mRssiSignalLevel) {
                    wifiNetworkDetailPreferenceController2.mRssiSignalLevel = level;
                    connectedViewer
                            .refreshRssiViews$com$samsung$android$settings$wifi$develop$details$WifiNetworkDetailPreferenceController$Viewer();
                    break;
                }
                break;
            default:
                connectedViewer.this$0.mRssiSignalLevel = -1;
                connectedViewer
                        .refreshRssiViews$com$samsung$android$settings$wifi$develop$details$WifiNetworkDetailPreferenceController$Viewer();
                break;
        }
        ((WifiNetworkDetailsFragmentForLabs) this.mFragment).updatePreferenceStates();
    }

    public final void setMloView() {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        if (connectionInfo == null) {
            return;
        }
        List<MloLink> associatedMloLinks = connectionInfo.getAssociatedMloLinks();
        this.mMloInfoView.mRootView.findViewById(R.id.link0_view).setVisibility(8);
        this.mMloInfoView.mRootView.findViewById(R.id.link1_view).setVisibility(8);
        this.mMloInfoView.mRootView.findViewById(R.id.link2_view).setVisibility(8);
        this.mMloInfoView.setVisible(false);
        if (associatedMloLinks.isEmpty()) {
            return;
        }
        this.mConntionView.mRootView.findViewById(R.id.rssi_info).setVisibility(8);
        this.mConntionView.mRootView.findViewById(R.id.band_freq_info).setVisibility(8);
        this.mConntionView.mRootView.findViewById(R.id.band_channel_info).setVisibility(8);
        this.mConntionView.mRootView.findViewById(R.id.ap_bssid_info).setVisibility(8);
        ((TextView) this.mMloInfoView.mRootView.findViewById(R.id.mld_mac_value))
                .setText(connectionInfo.getApMldMacAddress().toString());
        for (MloLink mloLink : associatedMloLinks) {
            int linkId = mloLink.getLinkId();
            View findViewById =
                    linkId != 0
                            ? linkId != 1
                                    ? linkId != 2
                                            ? null
                                            : this.mMloInfoView.mRootView.findViewById(
                                                    R.id.link2_view)
                                    : this.mMloInfoView.mRootView.findViewById(R.id.link1_view)
                            : this.mMloInfoView.mRootView.findViewById(R.id.link0_view);
            if (findViewById != null) {
                int rssi = mloLink.getRssi();
                if (rssi == -127) {
                    findViewById.setVisibility(8);
                } else {
                    ((TextView) findViewById.findViewById(R.id.link_id))
                            .setText("MLO link " + linkId);
                    int convertChannelToFrequencyMhzIfSupported =
                            ScanResult.convertChannelToFrequencyMhzIfSupported(
                                    mloLink.getChannel(), mloLink.getBand());
                    TextView textView = (TextView) findViewById.findViewById(R.id.band_freq_value);
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.valueOf(convertChannelToFrequencyMhzIfSupported));
                    sb.append(" MHz (");
                    sb.append(
                            (convertChannelToFrequencyMhzIfSupported < 2400
                                            || convertChannelToFrequencyMhzIfSupported >= 2500)
                                    ? (convertChannelToFrequencyMhzIfSupported < 4900
                                                    || convertChannelToFrequencyMhzIfSupported
                                                            >= 5900)
                                            ? (convertChannelToFrequencyMhzIfSupported < 5925
                                                            || convertChannelToFrequencyMhzIfSupported
                                                                    >= 7125)
                                                    ? this.mContext
                                                            .getResources()
                                                            .getString(
                                                                    R.string
                                                                            .wifitrackerlib_wifi_band_unknown)
                                                    : this.mContext
                                                            .getResources()
                                                            .getString(
                                                                    R.string
                                                                            .wifitrackerlib_wifi_band_6_ghz)
                                            : this.mContext
                                                    .getResources()
                                                    .getString(
                                                            R.string.wifitrackerlib_wifi_band_5_ghz)
                                    : this.mContext
                                            .getResources()
                                            .getString(R.string.wifitrackerlib_wifi_band_24_ghz));
                    sb.append(" band)");
                    textView.setText(sb.toString());
                    ((TextView) findViewById.findViewById(R.id.channel_value))
                            .setText(String.valueOf(mloLink.getChannel()));
                    ((TextView) findViewById.findViewById(R.id.rssi_value))
                            .setText(String.valueOf(rssi) + " dBm");
                    ((TextView) findViewById.findViewById(R.id.ap_bssid_value))
                            .setText(mloLink.getApMacAddress().toString());
                    ((TextView) findViewById.findViewById(R.id.sta_mac_value))
                            .setText(mloLink.getStaMacAddress().toString());
                    findViewById.setVisibility(0);
                }
            }
        }
        this.mMloInfoView.setVisible(true);
    }
}
