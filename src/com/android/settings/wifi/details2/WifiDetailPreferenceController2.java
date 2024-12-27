package com.android.settings.wifi.details2;

import android.app.AlertDialog;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.CaptivePortalData;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.RouteInfo;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.provider.Telephony;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.text.BidiFormatter;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.widget.EntityHeaderController;
import com.android.settings.wifi.WifiDialog2;
import com.android.settings.wifi.details.WifiNetworkDetailsFragment;
import com.android.settings.wifi.dpp.WifiDppConfiguratorActivity;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.utils.StringUtil;
import com.android.settingslib.widget.ActionButtonsPreference;
import com.android.settingslib.widget.LayoutPreference;
import com.android.settingslib.wifi.WifiUtils;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.PasspointWifiEntry;
import com.android.wifitrackerlib.WifiEntry;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiDetailPreferenceController2 extends AbstractPreferenceController
        implements PreferenceControllerMixin,
                WifiDialog2.WifiDialog2Listener,
                LifecycleObserver,
                OnPause,
                OnResume,
                WifiEntry.WifiEntryCallback,
                WifiEntry.ConnectCallback,
                WifiEntry.DisconnectCallback,
                WifiEntry.ForgetCallback {
    public static final boolean DEBUG = Log.isLoggable("WifiDetailsPrefCtrl2", 3);
    static final String KEY_BUTTONS_PREF = "buttons";
    static final String KEY_DATA_USAGE_HEADER = "status_header";
    static final String KEY_DNS_PREF = "dns";
    static final String KEY_EAP_SIM_SUBSCRIPTION_PREF = "eap_sim_subscription";
    static final String KEY_FREQUENCY_PREF = "frequency";
    static final String KEY_GATEWAY_PREF = "gateway";
    static final String KEY_HEADER = "connection_header";
    static final String KEY_IPV6_ADDRESSES_PREF = "ipv6_addresses";
    static final String KEY_IPV6_CATEGORY = "ipv6_category";
    static final String KEY_IP_ADDRESS_PREF = "ip_address";
    static final String KEY_MAC_ADDRESS_PREF = "mac_address";
    static final String KEY_RX_LINK_SPEED = "rx_link_speed";
    static final String KEY_SECURITY_PREF = "security";
    static final String KEY_SIGNAL_STRENGTH_PREF = "signal_strength";
    static final String KEY_SSID_PREF = "ssid";
    static final String KEY_SUBNET_MASK_PREF = "subnet_mask";
    static final String KEY_TX_LINK_SPEED = "tx_link_speed";
    static final String KEY_WIFI_TYPE_PREF = "type";
    public ActionButtonsPreference mButtonsPref;
    public CarrierIdAsyncQueryHandler mCarrierIdAsyncQueryHandler;
    public final Clock mClock;
    public final ConnectivityManager mConnectivityManager;
    public Preference mDnsPref;
    public Preference mEapSimSubscriptionPref;
    EntityHeaderController mEntityHeaderController;
    public final PreferenceFragmentCompat mFragment;
    public Preference mFrequencyPref;
    public Preference mGatewayPref;
    public final Handler mHandler;
    public final IconInjector mIconInjector;
    public Preference mIpAddressPref;
    public Preference mIpv6AddressPref;
    public LinkProperties mLinkProperties;
    public Preference mMacAddressPref;
    public final MetricsFeatureProvider mMetricsFeatureProvider;
    public Network mNetwork;
    public final AnonymousClass1 mNetworkCallback;
    public NetworkCapabilities mNetworkCapabilities;
    public final NetworkRequest mNetworkRequest;
    public int mRssiSignalLevel;
    public Preference mRxLinkSpeedPref;
    public Preference mSecurityPref;
    boolean mShowX;
    public final String[] mSignalStr;
    public Preference mSignalStrengthPref;
    public Preference mSsidPref;
    public Preference mSubnetPref;
    public Preference mTxLinkSpeedPref;
    public Preference mTypePref;
    public final WifiEntry mWifiEntry;
    public final WifiManager mWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CarrierIdAsyncQueryHandler extends AsyncQueryHandler {
        public CarrierIdAsyncQueryHandler(Context context) {
            super(context.getContentResolver());
        }

        @Override // android.content.AsyncQueryHandler
        public final void onQueryComplete(int i, Object obj, Cursor cursor) {
            if (i == 1) {
                if (((AbstractPreferenceController) WifiDetailPreferenceController2.this).mContext
                                == null
                        || cursor == null
                        || !cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    WifiDetailPreferenceController2.this.mEapSimSubscriptionPref.setSummary(
                            R.string.wifi_require_sim_card_to_connect);
                } else {
                    WifiDetailPreferenceController2 wifiDetailPreferenceController2 =
                            WifiDetailPreferenceController2.this;
                    wifiDetailPreferenceController2.mEapSimSubscriptionPref.setSummary(
                            ((AbstractPreferenceController) wifiDetailPreferenceController2)
                                    .mContext.getString(
                                            R.string.wifi_require_specific_sim_card_to_connect,
                                            cursor.getString(0)));
                    cursor.close();
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class Clock {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class IconInjector {
        public final Context mContext;

        public IconInjector(Context context) {
            this.mContext = context;
        }

        public final Drawable getIcon(int i, boolean z) {
            Context context = this.mContext;
            if (i < 0) {
                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                        .m(i, "Wi-Fi level is out of range! level:", "WifiUtils");
                i = 0;
            } else if (i >= 5) {
                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                        .m(i, "Wi-Fi level is out of range! level:", "WifiUtils");
                i = 4;
            }
            return context.getDrawable(
                            z ? WifiUtils.NO_INTERNET_WIFI_PIE[i] : WifiUtils.WIFI_PIE[i])
                    .mutate();
        }
    }

    public static void $r8$lambda$FbJzRgXOjWGUBr24yFcTz5OXoNQ(
            WifiDetailPreferenceController2 wifiDetailPreferenceController2) {
        Context context = wifiDetailPreferenceController2.mContext;
        WifiManager wifiManager = wifiDetailPreferenceController2.mWifiManager;
        Duration duration = WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION;
        Intent intent = new Intent(context, (Class<?>) WifiDppConfiguratorActivity.class);
        WifiEntry wifiEntry = wifiDetailPreferenceController2.mWifiEntry;
        if (wifiEntry.canShare()) {
            intent.setAction("android.settings.WIFI_DPP_CONFIGURATOR_QR_CODE_GENERATOR");
            WifiDppUtils.setConfiguratorIntentExtra(
                    intent, wifiManager, wifiEntry.getWifiConfiguration());
        } else {
            intent = null;
        }
        if (intent == null) {
            Log.e(
                    "WifiDetailsPrefCtrl2",
                    "Launch Wi-Fi DPP QR code generator with a wrong Wi-Fi network!");
        } else {
            wifiDetailPreferenceController2.mMetricsFeatureProvider.action(
                    0, 1710, 1595, Integer.MIN_VALUE, null);
            wifiDetailPreferenceController2.mContext.startActivity(intent);
        }
    }

    /* renamed from: $r8$lambda$vB_t2zP40JY6BG9hVjhe-AgGwrE, reason: not valid java name */
    public static /* synthetic */ void m1025$r8$lambda$vB_t2zP40JY6BG9hVjheAgGwrE(
            WifiDetailPreferenceController2 wifiDetailPreferenceController2, Uri uri) {
        wifiDetailPreferenceController2.getClass();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(268435456);
        intent.setData(uri);
        wifiDetailPreferenceController2.mContext.startActivity(intent);
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.wifi.details2.WifiDetailPreferenceController2$1] */
    public WifiDetailPreferenceController2(
            WifiEntry wifiEntry,
            ConnectivityManager connectivityManager,
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            Handler handler,
            Lifecycle lifecycle,
            WifiManager wifiManager,
            MetricsFeatureProvider metricsFeatureProvider,
            IconInjector iconInjector,
            Clock clock) {
        super(context);
        this.mRssiSignalLevel = -1;
        this.mNetworkRequest =
                new NetworkRequest.Builder().clearCapabilities().addTransportType(1).build();
        this.mNetworkCallback =
                new ConnectivityManager
                        .NetworkCallback() { // from class:
                                             // com.android.settings.wifi.details2.WifiDetailPreferenceController2.1
                    public final boolean hasCapabilityChanged(
                            NetworkCapabilities networkCapabilities, int i) {
                        NetworkCapabilities networkCapabilities2 =
                                WifiDetailPreferenceController2.this.mNetworkCapabilities;
                        return networkCapabilities2 == null
                                || networkCapabilities2.hasCapability(i)
                                        != networkCapabilities.hasCapability(i);
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onCapabilitiesChanged(
                            Network network, NetworkCapabilities networkCapabilities) {
                        if (!network.equals(WifiDetailPreferenceController2.this.mNetwork)
                                || networkCapabilities.equals(
                                        WifiDetailPreferenceController2.this
                                                .mNetworkCapabilities)) {
                            return;
                        }
                        NetworkCapabilities networkCapabilities2 =
                                WifiDetailPreferenceController2.this.mNetworkCapabilities;
                        if (networkCapabilities2 == null
                                || networkCapabilities2.isPrivateDnsBroken()
                                        != networkCapabilities.isPrivateDnsBroken()
                                || hasCapabilityChanged(networkCapabilities, 16)
                                || hasCapabilityChanged(networkCapabilities, 17)
                                || hasCapabilityChanged(networkCapabilities, 24)) {
                            WifiDetailPreferenceController2.this.refreshEntityHeader();
                        }
                        WifiDetailPreferenceController2 wifiDetailPreferenceController2 =
                                WifiDetailPreferenceController2.this;
                        wifiDetailPreferenceController2.mNetworkCapabilities = networkCapabilities;
                        wifiDetailPreferenceController2.refreshButtons();
                        WifiDetailPreferenceController2.this.refreshIpLayerInfo();
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLinkPropertiesChanged(
                            Network network, LinkProperties linkProperties) {
                        if (!network.equals(WifiDetailPreferenceController2.this.mNetwork)
                                || linkProperties.equals(
                                        WifiDetailPreferenceController2.this.mLinkProperties)) {
                            return;
                        }
                        WifiDetailPreferenceController2 wifiDetailPreferenceController2 =
                                WifiDetailPreferenceController2.this;
                        wifiDetailPreferenceController2.mLinkProperties = linkProperties;
                        wifiDetailPreferenceController2.refreshEntityHeader();
                        WifiDetailPreferenceController2.this.refreshButtons();
                        WifiDetailPreferenceController2.this.refreshIpLayerInfo();
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLost(Network network) {
                        if (WifiDetailPreferenceController2.this.mWifiEntry.isSaved()
                                || !network.equals(WifiDetailPreferenceController2.this.mNetwork)) {
                            return;
                        }
                        if (WifiDetailPreferenceController2.DEBUG) {
                            Log.d("WifiDetailsPrefCtrl2", "OnLost and exit WifiNetworkDetailsPage");
                        }
                        WifiDetailPreferenceController2.this.mFragment.getActivity().finish();
                    }
                };
        this.mWifiEntry = wifiEntry;
        wifiEntry.setListener(this);
        this.mConnectivityManager = connectivityManager;
        this.mFragment = preferenceFragmentCompat;
        this.mHandler = handler;
        this.mSignalStr = context.getResources().getStringArray(R.array.wifi_signal);
        this.mWifiManager = wifiManager;
        this.mMetricsFeatureProvider = metricsFeatureProvider;
        this.mIconInjector = iconInjector;
        this.mClock = clock;
        lifecycle.addObserver(this);
    }

    public static WifiDetailPreferenceController2 newInstance(
            WifiEntry wifiEntry,
            ConnectivityManager connectivityManager,
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            Handler handler,
            Lifecycle lifecycle,
            WifiManager wifiManager,
            MetricsFeatureProvider metricsFeatureProvider) {
        return new WifiDetailPreferenceController2(
                wifiEntry,
                connectivityManager,
                context,
                preferenceFragmentCompat,
                handler,
                lifecycle,
                wifiManager,
                metricsFeatureProvider,
                new IconInjector(context),
                new Clock());
    }

    public static void updatePreference(Preference preference, String str) {
        if (TextUtils.isEmpty(str)) {
            preference.setVisible(false);
        } else {
            preference.setSummary(str);
            preference.setVisible(true);
        }
    }

    public final boolean canModifyNetwork() {
        WifiEntry wifiEntry = this.mWifiEntry;
        return wifiEntry.isSaved()
                && !com.android.settings.wifi.WifiUtils.isNetworkLockedDown(
                        this.mContext, wifiEntry.getWifiConfiguration());
    }

    public void connectDisconnectNetwork() {
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry.getConnectedState() == 0) {
            wifiEntry.connect(this);
        } else {
            wifiEntry.disconnect(this);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        int i;
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(KEY_HEADER);
        PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
        this.mEntityHeaderController =
                new EntityHeaderController(
                        preferenceFragmentCompat.getActivity(),
                        preferenceFragmentCompat,
                        layoutPreference.mRootView.findViewById(R.id.entity_header));
        ((ImageView) layoutPreference.mRootView.findViewById(R.id.entity_header_icon))
                .setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ActionButtonsPreference actionButtonsPreference =
                (ActionButtonsPreference) preferenceScreen.findPreference(KEY_BUTTONS_PREF);
        actionButtonsPreference.setButton1Text(R.string.forget);
        actionButtonsPreference.setButton1Icon(R.drawable.ic_settings_delete);
        actionButtonsPreference.setButton1OnClickListener(
                new WifiDetailPreferenceController2$$ExternalSyntheticLambda0(this, 0));
        actionButtonsPreference.setButton2Text(R.string.wifi_sign_in_button_text);
        actionButtonsPreference.setButton2Icon(R.drawable.ic_settings_sign_in);
        actionButtonsPreference.setButton2OnClickListener(
                new WifiDetailPreferenceController2$$ExternalSyntheticLambda0(this, 1));
        actionButtonsPreference.setButton3Text(getConnectDisconnectButtonTextResource());
        int connectedState = this.mWifiEntry.getConnectedState();
        if (connectedState == 0 || connectedState == 1) {
            i = R.drawable.ic_settings_wireless;
        } else {
            if (connectedState != 2) {
                throw new IllegalStateException("Invalid WifiEntry connected state");
            }
            i = R.drawable.ic_settings_close;
        }
        actionButtonsPreference.setButton3Icon(i);
        WifiDetailPreferenceController2$$ExternalSyntheticLambda0
                wifiDetailPreferenceController2$$ExternalSyntheticLambda0 =
                        new WifiDetailPreferenceController2$$ExternalSyntheticLambda0(this, 2);
        ActionButtonsPreference.ButtonInfo buttonInfo = actionButtonsPreference.mButton3Info;
        if (wifiDetailPreferenceController2$$ExternalSyntheticLambda0 != buttonInfo.mListener) {
            buttonInfo.mListener = wifiDetailPreferenceController2$$ExternalSyntheticLambda0;
            actionButtonsPreference.notifyChanged();
        }
        String string = actionButtonsPreference.getContext().getString(R.string.share);
        if (!TextUtils.equals(string, actionButtonsPreference.mButton4Info.mText)) {
            actionButtonsPreference.mButton4Info.mText = string;
            actionButtonsPreference.notifyChanged();
        }
        try {
            actionButtonsPreference.mButton4Info.mIcon =
                    actionButtonsPreference.getContext().getDrawable(R.drawable.ic_qrcode_24dp);
            actionButtonsPreference.notifyChanged();
        } catch (Resources.NotFoundException unused) {
            Log.e("ActionButtonPreference", "Resource does not exist: 2131232115");
        }
        WifiDetailPreferenceController2$$ExternalSyntheticLambda0
                wifiDetailPreferenceController2$$ExternalSyntheticLambda02 =
                        new WifiDetailPreferenceController2$$ExternalSyntheticLambda0(this, 3);
        ActionButtonsPreference.ButtonInfo buttonInfo2 = actionButtonsPreference.mButton4Info;
        if (wifiDetailPreferenceController2$$ExternalSyntheticLambda02 != buttonInfo2.mListener) {
            buttonInfo2.mListener = wifiDetailPreferenceController2$$ExternalSyntheticLambda02;
            actionButtonsPreference.notifyChanged();
        }
        this.mButtonsPref = actionButtonsPreference;
        updateCaptivePortalButton();
        this.mSignalStrengthPref = preferenceScreen.findPreference(KEY_SIGNAL_STRENGTH_PREF);
        this.mTxLinkSpeedPref = preferenceScreen.findPreference(KEY_TX_LINK_SPEED);
        this.mRxLinkSpeedPref = preferenceScreen.findPreference(KEY_RX_LINK_SPEED);
        this.mFrequencyPref = preferenceScreen.findPreference(KEY_FREQUENCY_PREF);
        this.mSecurityPref = preferenceScreen.findPreference(KEY_SECURITY_PREF);
        this.mSsidPref = preferenceScreen.findPreference(KEY_SSID_PREF);
        this.mEapSimSubscriptionPref =
                preferenceScreen.findPreference(KEY_EAP_SIM_SUBSCRIPTION_PREF);
        this.mMacAddressPref = preferenceScreen.findPreference(KEY_MAC_ADDRESS_PREF);
        this.mIpAddressPref = preferenceScreen.findPreference(KEY_IP_ADDRESS_PREF);
        this.mGatewayPref = preferenceScreen.findPreference(KEY_GATEWAY_PREF);
        this.mSubnetPref = preferenceScreen.findPreference(KEY_SUBNET_MASK_PREF);
        this.mDnsPref = preferenceScreen.findPreference(KEY_DNS_PREF);
        this.mTypePref = preferenceScreen.findPreference("type");
        this.mIpv6AddressPref = preferenceScreen.findPreference(KEY_IPV6_ADDRESSES_PREF);
    }

    public SubscriptionInfo fineSubscriptionInfo(int i, List<SubscriptionInfo> list, int i2) {
        SubscriptionInfo subscriptionInfo = null;
        for (SubscriptionInfo subscriptionInfo2 : list) {
            if (i2 == subscriptionInfo2.getSubscriptionId()
                    && (i == subscriptionInfo2.getCarrierId() || i == -1)) {
                return subscriptionInfo2;
            }
            if (subscriptionInfo == null && i == subscriptionInfo2.getCarrierId()) {
                subscriptionInfo = subscriptionInfo2;
            }
        }
        return subscriptionInfo;
    }

    public final int getConnectDisconnectButtonTextResource() {
        int connectedState = this.mWifiEntry.getConnectedState();
        if (connectedState == 0) {
            return R.string.wifi_connect;
        }
        if (connectedState == 1) {
            return R.string.wifi_connecting;
        }
        if (connectedState == 2) {
            return R.string.wifi_disconnect_button_text;
        }
        throw new IllegalStateException("Invalid WifiEntry connected state");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    public Drawable getWifiDrawable(WifiEntry wifiEntry) {
        if (wifiEntry instanceof HotspotNetworkEntry) {
            return this.mContext.getDrawable(
                    WifiUtils.getHotspotIconResource(
                            ((HotspotNetworkEntry) wifiEntry).getDeviceType()));
        }
        if (this.mWifiEntry.getLevel() == -1) {
            return this.mContext.getDrawable(R.drawable.empty_icon);
        }
        boolean shouldShowXLevelIcon = wifiEntry.shouldShowXLevelIcon();
        return this.mIconInjector.getIcon(wifiEntry.getLevel(), shouldShowXLevelIcon);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.wifitrackerlib.WifiEntry.ConnectCallback
    public final void onConnectResult(int i) {
        WifiEntry wifiEntry = this.mWifiEntry;
        if (i == 0) {
            Context context = this.mContext;
            Preference$$ExternalSyntheticOutline0.m(
                    context,
                    R.string.wifi_connected_to_message,
                    new Object[] {wifiEntry.getTitle()},
                    context,
                    0);
        } else if (wifiEntry.getLevel() == -1) {
            Toast.makeText(this.mContext, R.string.wifi_not_in_range_message, 0).show();
        } else {
            Toast.makeText(this.mContext, R.string.wifi_failed_connect_message, 0).show();
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry.DisconnectCallback
    public final void onDisconnectResult(int i) {
        if (i != 0) {
            Log.e("WifiDetailsPrefCtrl2", "Disconnect Wi-Fi network failed");
            return;
        }
        FragmentActivity activity = this.mFragment.getActivity();
        if (activity != null) {
            Toast.makeText(
                            activity,
                            activity.getString(
                                    R.string.wifi_disconnected_from,
                                    new Object[] {this.mWifiEntry.getTitle()}),
                            0)
                    .show();
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry.ForgetCallback
    public final void onForgetResult(int i) {
        if (i != 0) {
            Log.e("WifiDetailsPrefCtrl2", "Forget Wi-Fi network failed");
        }
        FragmentActivity activity = this.mFragment.getActivity();
        if (activity != null) {
            this.mMetricsFeatureProvider.action(activity, 137, new Pair[0]);
            activity.finish();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        RecyclerView listView = this.mFragment.getListView();
        if (listView != null) {
            listView.setItemAnimator(null);
        }
        updateNetworkInfo();
        refreshPage();
        this.mConnectivityManager.registerNetworkCallback(
                this.mNetworkRequest, this.mNetworkCallback, this.mHandler);
    }

    @Override // com.android.settings.wifi.WifiDialog2.WifiDialog2Listener
    public final void onSubmit(WifiDialog2 wifiDialog2) {
        wifiDialog2.getController();
        this.mWifiManager.save(
                wifiDialog2.getController().getConfig(),
                new WifiManager
                        .ActionListener() { // from class:
                                            // com.android.settings.wifi.details2.WifiDetailPreferenceController2.2
                    public final void onFailure(int i) {
                        FragmentActivity activity =
                                WifiDetailPreferenceController2.this.mFragment.getActivity();
                        if (activity != null) {
                            Toast.makeText(activity, R.string.wifi_failed_save_message, 0).show();
                        }
                    }

                    public final void onSuccess() {}
                });
    }

    @Override // com.android.wifitrackerlib.WifiEntry.WifiEntryCallback
    public final void onUpdated() {
        updateNetworkInfo();
        refreshPage();
        ((WifiNetworkDetailsFragment) this.mFragment).refreshPreferences();
    }

    public final void refreshButtons() {
        int i;
        WifiEntry wifiEntry = this.mWifiEntry;
        boolean z = true;
        boolean z2 =
                wifiEntry.canForget()
                        && !com.android.settings.wifi.WifiUtils.isNetworkLockedDown(
                                this.mContext, wifiEntry.getWifiConfiguration());
        boolean updateCaptivePortalButton = updateCaptivePortalButton();
        boolean z3 = wifiEntry.canConnect() || wifiEntry.canDisconnect();
        boolean canShare = wifiEntry.canShare();
        ActionButtonsPreference actionButtonsPreference = this.mButtonsPref;
        ActionButtonsPreference.ButtonInfo buttonInfo = actionButtonsPreference.mButton1Info;
        if (z2 != buttonInfo.mIsVisible) {
            buttonInfo.mIsVisible = z2;
            actionButtonsPreference.notifyChanged();
        }
        ActionButtonsPreference actionButtonsPreference2 = this.mButtonsPref;
        ActionButtonsPreference.ButtonInfo buttonInfo2 = actionButtonsPreference2.mButton2Info;
        if (updateCaptivePortalButton != buttonInfo2.mIsVisible) {
            buttonInfo2.mIsVisible = updateCaptivePortalButton;
            actionButtonsPreference2.notifyChanged();
        }
        ActionButtonsPreference actionButtonsPreference3 = this.mButtonsPref;
        boolean z4 = z3 || wifiEntry.getConnectedState() == 1;
        ActionButtonsPreference.ButtonInfo buttonInfo3 = actionButtonsPreference3.mButton3Info;
        if (z4 != buttonInfo3.mIsVisible) {
            buttonInfo3.mIsVisible = z4;
            actionButtonsPreference3.notifyChanged();
        }
        ActionButtonsPreference actionButtonsPreference4 = this.mButtonsPref;
        ActionButtonsPreference.ButtonInfo buttonInfo4 = actionButtonsPreference4.mButton3Info;
        if (z3 != buttonInfo4.mIsEnabled) {
            buttonInfo4.mIsEnabled = z3;
            actionButtonsPreference4.notifyChanged();
        }
        this.mButtonsPref.setButton3Text(getConnectDisconnectButtonTextResource());
        ActionButtonsPreference actionButtonsPreference5 = this.mButtonsPref;
        int connectedState = wifiEntry.getConnectedState();
        if (connectedState == 0 || connectedState == 1) {
            i = R.drawable.ic_settings_wireless;
        } else {
            if (connectedState != 2) {
                throw new IllegalStateException("Invalid WifiEntry connected state");
            }
            i = R.drawable.ic_settings_close;
        }
        actionButtonsPreference5.setButton3Icon(i);
        ActionButtonsPreference actionButtonsPreference6 = this.mButtonsPref;
        ActionButtonsPreference.ButtonInfo buttonInfo5 = actionButtonsPreference6.mButton4Info;
        if (canShare != buttonInfo5.mIsVisible) {
            buttonInfo5.mIsVisible = canShare;
            actionButtonsPreference6.notifyChanged();
        }
        ActionButtonsPreference actionButtonsPreference7 = this.mButtonsPref;
        if (!z2 && !updateCaptivePortalButton && !z3 && !canShare) {
            z = false;
        }
        actionButtonsPreference7.setVisible(z);
    }

    public final void refreshEntityHeader() {
        EntityHeaderController entityHeaderController = this.mEntityHeaderController;
        WifiEntry wifiEntry = this.mWifiEntry;
        entityHeaderController.mLabel = wifiEntry.getTitle();
        entityHeaderController.mSummary = wifiEntry.getSummary(true);
        LinkProperties linkProperties = this.mLinkProperties;
        String str = null;
        if (linkProperties != null && linkProperties.getCaptivePortalData() != null) {
            long expiryTimeMillis =
                    this.mLinkProperties.getCaptivePortalData().getExpiryTimeMillis();
            if (expiryTimeMillis > 0) {
                this.mClock.getClass();
                ZonedDateTime now = ZonedDateTime.now();
                ZonedDateTime ofInstant =
                        ZonedDateTime.ofInstant(
                                Instant.ofEpochMilli(expiryTimeMillis), now.getZone());
                if (!now.isAfter(ofInstant)) {
                    if (now.plusDays(2L).isAfter(ofInstant)) {
                        Context context = this.mContext;
                        str =
                                context.getString(
                                        R.string.wifi_time_remaining,
                                        StringUtil.formatElapsedTime(
                                                context,
                                                Duration.between(now, ofInstant).getSeconds()
                                                        * 1000,
                                                false,
                                                false));
                    } else {
                        str =
                                this.mContext.getString(
                                        R.string.wifi_expiry_time,
                                        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                                                .format(ofInstant));
                    }
                }
            }
        }
        entityHeaderController.mSecondSummary = str;
        entityHeaderController.done(true);
    }

    public void refreshEntryHeaderIcon() {
        if (this.mEntityHeaderController == null) {
            return;
        }
        Drawable wifiDrawable = getWifiDrawable(this.mWifiEntry);
        EntityHeaderController entityHeaderController = this.mEntityHeaderController;
        int dimensionPixelSize =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.wifi_detail_page_header_image_size);
        int minimumWidth = wifiDrawable.getMinimumWidth();
        int minimumHeight = wifiDrawable.getMinimumHeight();
        if ((minimumWidth != dimensionPixelSize || minimumHeight != dimensionPixelSize)
                && VectorDrawable.class.isInstance(wifiDrawable)) {
            wifiDrawable.setTintList(null);
            BitmapDrawable bitmapDrawable =
                    new BitmapDrawable(
                            (Resources) null,
                            Utils.createBitmap(
                                    wifiDrawable, dimensionPixelSize, dimensionPixelSize));
            bitmapDrawable.setTintList(
                    com.android.settingslib.Utils.getColorAttr(
                            this.mContext, android.R.attr.textColorPrimary));
            wifiDrawable = bitmapDrawable;
        }
        entityHeaderController.setIcon(wifiDrawable);
        entityHeaderController.done(true);
    }

    public final void refreshIpLayerInfo() {
        if (this.mWifiEntry.getConnectedState() != 2
                || this.mNetwork == null
                || this.mLinkProperties == null) {
            this.mIpAddressPref.setVisible(false);
            this.mSubnetPref.setVisible(false);
            this.mGatewayPref.setVisible(false);
            this.mDnsPref.setVisible(false);
            this.mIpv6AddressPref.setVisible(false);
            return;
        }
        StringJoiner stringJoiner = new StringJoiner("\n");
        String str = null;
        String str2 = null;
        String str3 = null;
        for (LinkAddress linkAddress : this.mLinkProperties.getLinkAddresses()) {
            if (linkAddress.getAddress() instanceof Inet4Address) {
                str2 = linkAddress.getAddress().getHostAddress();
                int prefixLength = linkAddress.getPrefixLength();
                if (prefixLength < 0 || prefixLength > 32) {
                    throw new IllegalArgumentException("Invalid prefix length (0 <= prefix <= 32)");
                }
                int i = prefixLength == 0 ? 0 : (-1) << (32 - prefixLength);
                try {
                    try {
                        str3 =
                                ((Inet4Address)
                                                InetAddress.getByAddress(
                                                        new byte[] {
                                                            (byte) ((i >> 24) & 255),
                                                            (byte) ((i >> 16) & 255),
                                                            (byte) ((i >> 8) & 255),
                                                            (byte) (i & 255)
                                                        }))
                                        .getHostAddress();
                    } catch (UnknownHostException unused) {
                        throw new AssertionError();
                    }
                } catch (IllegalArgumentException unused2) {
                    str3 = null;
                }
            } else if (linkAddress.getAddress() instanceof Inet6Address) {
                stringJoiner.add(linkAddress.getAddress().getHostAddress());
            }
        }
        Iterator<RouteInfo> it = this.mLinkProperties.getRoutes().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            RouteInfo next = it.next();
            if (next.hasGateway()
                    && next.isDefaultRoute()
                    && (next.getDestination().getAddress() instanceof Inet4Address)) {
                str = next.getGateway().getHostAddress();
                break;
            }
        }
        String str4 =
                (String)
                        this.mLinkProperties.getDnsServers().stream()
                                .map(
                                        new WifiDetailPreferenceController2$$ExternalSyntheticLambda8())
                                .collect(Collectors.joining("\n"));
        updatePreference(this.mIpAddressPref, str2);
        updatePreference(this.mSubnetPref, str3);
        updatePreference(this.mGatewayPref, str);
        updatePreference(this.mDnsPref, str4);
        if (stringJoiner.length() <= 0) {
            this.mIpv6AddressPref.setVisible(false);
        } else {
            this.mIpv6AddressPref.setVisible(true);
            this.mIpv6AddressPref.setSummary(
                    BidiFormatter.getInstance().unicodeWrap(stringJoiner.toString()));
        }
    }

    public final void refreshPage() {
        WifiConfiguration wifiConfiguration;
        WifiEnterpriseConfig wifiEnterpriseConfig;
        SubscriptionInfo fineSubscriptionInfo;
        Log.d("WifiDetailsPrefCtrl2", "Update UI!");
        refreshEntryHeaderIcon();
        refreshEntityHeader();
        refreshButtons();
        WifiEntry wifiEntry = this.mWifiEntry;
        int level = wifiEntry.getLevel();
        if (level == -1) {
            this.mSignalStrengthPref.setVisible(false);
            this.mRssiSignalLevel = -1;
        } else {
            boolean shouldShowXLevelIcon = wifiEntry.shouldShowXLevelIcon();
            if (this.mRssiSignalLevel != level || this.mShowX != shouldShowXLevelIcon) {
                this.mRssiSignalLevel = level;
                this.mShowX = shouldShowXLevelIcon;
                Drawable mutate =
                        this.mIconInjector
                                .getIcon(level, shouldShowXLevelIcon)
                                .getConstantState()
                                .newDrawable()
                                .mutate();
                mutate.setTintList(
                        com.android.settingslib.Utils.getColorAttr(
                                this.mContext, android.R.attr.colorControlNormal));
                this.mSignalStrengthPref.setIcon(mutate);
                this.mSignalStrengthPref.setSummary(this.mSignalStr[this.mRssiSignalLevel]);
                this.mSignalStrengthPref.setVisible(true);
            }
        }
        String bandString = wifiEntry.getBandString();
        if (TextUtils.isEmpty(bandString)) {
            this.mFrequencyPref.setVisible(false);
        } else {
            this.mFrequencyPref.setSummary(bandString);
            this.mFrequencyPref.setVisible(true);
        }
        this.mSecurityPref.setSummary(wifiEntry.getSecurityString(false));
        String speedString =
                com.android.wifitrackerlib.Utils.getSpeedString(
                        wifiEntry.mContext, wifiEntry.mWifiInfo, true);
        if (TextUtils.isEmpty(speedString)) {
            this.mTxLinkSpeedPref.setVisible(false);
        } else {
            this.mTxLinkSpeedPref.setVisible(true);
            this.mTxLinkSpeedPref.setSummary(speedString);
        }
        String speedString2 =
                com.android.wifitrackerlib.Utils.getSpeedString(
                        wifiEntry.mContext, wifiEntry.mWifiInfo, false);
        if (TextUtils.isEmpty(speedString2)) {
            this.mRxLinkSpeedPref.setVisible(false);
        } else {
            this.mRxLinkSpeedPref.setVisible(true);
            this.mRxLinkSpeedPref.setSummary(speedString2);
        }
        refreshIpLayerInfo();
        if (!(wifiEntry instanceof PasspointWifiEntry) || wifiEntry.getSsid() == null) {
            this.mSsidPref.setVisible(false);
        } else {
            this.mSsidPref.setVisible(true);
            this.mSsidPref.setSummary(wifiEntry.getSsid());
        }
        this.mEapSimSubscriptionPref.setVisible(false);
        if (wifiEntry.getSecurity$1() == 3
                && (wifiConfiguration = wifiEntry.getWifiConfiguration()) != null
                && (wifiEnterpriseConfig = wifiConfiguration.enterpriseConfig) != null
                && wifiEnterpriseConfig.isAuthenticationSimBased()) {
            this.mEapSimSubscriptionPref.setVisible(true);
            List<SubscriptionInfo> activeSubscriptionInfoList =
                    ((SubscriptionManager)
                                    this.mContext.getSystemService(SubscriptionManager.class))
                            .getActiveSubscriptionInfoList();
            if (activeSubscriptionInfoList != null
                    && (fineSubscriptionInfo =
                                    fineSubscriptionInfo(
                                            wifiConfiguration.carrierId,
                                            activeSubscriptionInfoList,
                                            SubscriptionManager.getDefaultDataSubscriptionId()))
                            != null) {
                this.mEapSimSubscriptionPref.setSummary(
                        SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                this.mContext, fineSubscriptionInfo));
            } else if (wifiConfiguration.carrierId == -1) {
                this.mEapSimSubscriptionPref.setSummary(R.string.wifi_no_related_sim_card);
            } else {
                if (this.mCarrierIdAsyncQueryHandler == null) {
                    this.mCarrierIdAsyncQueryHandler =
                            new CarrierIdAsyncQueryHandler(this.mContext);
                }
                this.mCarrierIdAsyncQueryHandler.cancelOperation(1);
                this.mCarrierIdAsyncQueryHandler.startQuery(
                        1,
                        null,
                        Telephony.CarrierId.All.CONTENT_URI,
                        new String[] {"carrier_name"},
                        "carrier_id=?",
                        new String[] {Integer.toString(wifiConfiguration.carrierId)},
                        null);
            }
        }
        String macAddress = wifiEntry.getMacAddress();
        if (TextUtils.isEmpty(macAddress)) {
            this.mMacAddressPref.setVisible(false);
        } else {
            this.mMacAddressPref.setVisible(true);
            this.mMacAddressPref.setTitle(
                    wifiEntry.getPrivacy() == 1
                            ? wifiEntry.getConnectedState() == 2
                                    ? R.string.wifi_advanced_randomized_mac_address_title
                                    : R.string
                                            .wifi_advanced_randomized_mac_address_disconnected_title
                            : R.string.wifi_advanced_device_mac_address_title);
            if (macAddress.equals("02:00:00:00:00:00")) {
                this.mMacAddressPref.setSummary(R.string.device_info_not_available);
            } else {
                this.mMacAddressPref.setSummary(macAddress);
            }
        }
        String standardString = wifiEntry.getStandardString();
        if (TextUtils.isEmpty(standardString)) {
            this.mTypePref.setVisible(false);
        } else {
            this.mTypePref.setSummary(standardString);
            this.mTypePref.setVisible(true);
        }
    }

    public void showConfirmForgetDialog() {
        new AlertDialog.Builder(this.mContext)
                .setPositiveButton(
                        R.string.forget,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.wifi.details2.WifiDetailPreferenceController2$$ExternalSyntheticLambda4
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                WifiDetailPreferenceController2 wifiDetailPreferenceController2 =
                                        WifiDetailPreferenceController2.this;
                                wifiDetailPreferenceController2.getClass();
                                try {
                                    wifiDetailPreferenceController2.mWifiEntry.forget(
                                            wifiDetailPreferenceController2);
                                } catch (RuntimeException e) {
                                    Log.e(
                                            "WifiDetailsPrefCtrl2",
                                            "Failed to remove Passpoint configuration: " + e);
                                }
                                PreferenceFragmentCompat preferenceFragmentCompat =
                                        wifiDetailPreferenceController2.mFragment;
                                wifiDetailPreferenceController2.mMetricsFeatureProvider.action(
                                        preferenceFragmentCompat.getActivity(), 137, new Pair[0]);
                                preferenceFragmentCompat.getActivity().finish();
                            }
                        })
                .setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null)
                .setTitle(R.string.wifi_forget_dialog_title)
                .setMessage(R.string.forget_passpoint_dialog_message)
                .create()
                .show();
    }

    public final boolean updateCaptivePortalButton() {
        CaptivePortalData captivePortalData;
        LinkProperties linkProperties = this.mLinkProperties;
        final Uri uri = null;
        if (linkProperties != null
                && (captivePortalData = linkProperties.getCaptivePortalData()) != null) {
            uri = captivePortalData.getVenueInfoUrl();
        }
        WifiEntry wifiEntry = this.mWifiEntry;
        if (uri == null) {
            ActionButtonsPreference actionButtonsPreference = this.mButtonsPref;
            actionButtonsPreference.setButton2Text(R.string.wifi_sign_in_button_text);
            actionButtonsPreference.setButton2Icon(R.drawable.ic_settings_sign_in);
            actionButtonsPreference.setButton2OnClickListener(
                    new WifiDetailPreferenceController2$$ExternalSyntheticLambda0(this, 4));
            return wifiEntry.canSignIn();
        }
        ActionButtonsPreference actionButtonsPreference2 = this.mButtonsPref;
        actionButtonsPreference2.setButton2Text(R.string.wifi_venue_website_button_text);
        actionButtonsPreference2.setButton2Icon(R.drawable.ic_settings_sign_in);
        actionButtonsPreference2.setButton2OnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.wifi.details2.WifiDetailPreferenceController2$$ExternalSyntheticLambda7
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        WifiDetailPreferenceController2.m1025$r8$lambda$vB_t2zP40JY6BG9hVjheAgGwrE(
                                WifiDetailPreferenceController2.this, uri);
                    }
                });
        return wifiEntry.getConnectedState() == 2;
    }

    public void updateNetworkInfo() {
        if (this.mWifiEntry.getConnectedState() != 2) {
            this.mNetwork = null;
            this.mLinkProperties = null;
            this.mNetworkCapabilities = null;
        } else {
            Network currentNetwork = this.mWifiManager.getCurrentNetwork();
            this.mNetwork = currentNetwork;
            this.mLinkProperties = this.mConnectivityManager.getLinkProperties(currentNetwork);
            this.mNetworkCapabilities =
                    this.mConnectivityManager.getNetworkCapabilities(this.mNetwork);
        }
    }
}
