package com.samsung.android.settings.wifi.details;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.widget.EntityHeaderController;
import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.widget.LayoutPreference;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.samsung.android.settings.wifi.WifiDevicePolicyManager;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiDetailPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin,
                LifecycleObserver,
                OnResume,
                OnPause,
                WifiEntry.WifiEntryCallback,
                WifiDppAuthenticationErrorListener {
    public final WifiDetailPreferenceController mAuthErrorListener;
    public SecInsetCategoryPreference mConnectInfoInsetPref;
    public final ConnectedViewer mConnectedViewer;
    public final ConnectivityManager mConnectivityManager;
    public final ConnectedViewer mDisconnectedViewer;
    public LayoutPreference mEapIdPref;
    public EditText mEapIdentityView;
    public EntityHeaderController mEntityHeaderController;
    public final PreferenceFragmentCompat mFragment;
    public final Handler mHandler;
    public SecInsetCategoryPreference mHeaderInsetPref;
    public final IconInjector mIconInjector;
    public final boolean mInManageNetwork;
    public Preference mIpAddressPref;
    public boolean mIsKeyguardLoading;
    public double mLatitude;
    public LinkProperties mLinkProperties;
    public double mLongitude;
    public Preference mMacAddressPref;
    public Network mNetwork;
    public final AnonymousClass1 mNetworkCallback;
    public NetworkCapabilities mNetworkCapabilities;
    public final NetworkRequest mNetworkRequest;
    public Preference mPasspointPref;
    public LayoutPreference mPasswordPref;
    public ImageView mPasswordShowButton;
    public EditText mPasswordView;
    public Preference mRouterLocation;
    public int mRssiSignalLevel;
    public Preference mSecurityPref;
    public final SemWifiManager mSemWifiManager;
    public ConnectedViewer mViewer;
    public WifiConfiguration mWifiConfig;
    public final WifiEntry mWifiEntry;
    public WifiInfo mWifiInfo;
    public final WifiManager mWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConnectedViewer {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiDetailPreferenceController this$0;
        public final /* synthetic */ WifiDetailPreferenceController this$0$1;

        public ConnectedViewer(
                WifiDetailPreferenceController wifiDetailPreferenceController, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiDetailPreferenceController;
            this.this$0$1 = wifiDetailPreferenceController;
        }

        public final void refreshEntityHeader() {
            WifiDetailPreferenceController wifiDetailPreferenceController = this.this$0$1;
            EntityHeaderController entityHeaderController =
                    wifiDetailPreferenceController.mEntityHeaderController;
            entityHeaderController.mLabel = wifiDetailPreferenceController.mWifiEntry.getTitle();
            entityHeaderController.mSummary =
                    (wifiDetailPreferenceController.mInManageNetwork
                                    || wifiDetailPreferenceController.mWifiEntry.semIsEphemeral())
                            ? ApnSettings.MVNO_NONE
                            : wifiDetailPreferenceController.mWifiEntry.getSummary(true);
            entityHeaderController.done(
                    ((AbstractPreferenceController) wifiDetailPreferenceController).mContext);
            wifiDetailPreferenceController.mHeaderInsetPref.setVisible(true);
        }

        public final void refreshIpAddress() {
            LinkProperties linkProperties;
            WifiDetailPreferenceController wifiDetailPreferenceController = this.this$0;
            switch (this.$r8$classId) {
                case 0:
                    Context context =
                            ((AbstractPreferenceController) wifiDetailPreferenceController)
                                    .mContext;
                    StringBuilder sb = Utils.sBuilder;
                    Network currentNetwork =
                            ((WifiManager) context.getSystemService(WifiManager.class))
                                    .getCurrentNetwork();
                    String str = ApnSettings.MVNO_NONE;
                    String str2 = null;
                    if (currentNetwork != null
                            && (linkProperties =
                                            ((ConnectivityManager)
                                                            context.getSystemService(
                                                                    "connectivity"))
                                                    .getLinkProperties(currentNetwork))
                                    != null) {
                        Iterator it = linkProperties.getAllLinkAddresses().iterator();
                        if (it.hasNext()) {
                            str2 = ApnSettings.MVNO_NONE;
                            while (it.hasNext()) {
                                StringBuilder m =
                                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                                .m(str2);
                                m.append(((LinkAddress) it.next()).getAddress().getHostAddress());
                                str2 = m.toString();
                                if (it.hasNext()) {
                                    str2 =
                                            AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                                    str2, "\n");
                                }
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(str2)
                            && wifiDetailPreferenceController.mLinkProperties != null) {
                        StringBuilder sb2 = new StringBuilder("\n");
                        for (LinkAddress linkAddress :
                                wifiDetailPreferenceController.mLinkProperties.getLinkAddresses()) {
                            if (linkAddress.getAddress() instanceof Inet4Address) {
                                str = linkAddress.getAddress().getHostAddress();
                            } else if (linkAddress.getAddress() instanceof Inet6Address) {
                                sb2.append(linkAddress.getAddress().getHostAddress());
                                sb2.append("\n");
                            }
                        }
                        wifiDetailPreferenceController.mIpAddressPref.setSummary(
                                (str + ((Object) sb2)).trim());
                        wifiDetailPreferenceController.mIpAddressPref.setVisible(true);
                        break;
                    } else {
                        wifiDetailPreferenceController.mIpAddressPref.setVisible(false);
                        break;
                    }
                    break;
                default:
                    wifiDetailPreferenceController.mIpAddressPref.setVisible(false);
                    break;
            }
        }

        public final void refreshPage() {
            int i;
            WifiConfiguration wifiConfiguration;
            boolean z;
            int i2 = this.$r8$classId;
            WifiDetailPreferenceController wifiDetailPreferenceController = this.this$0;
            Log.d("WifiDetailsPrefCtrl", "Update UI!");
            refreshEntityHeader();
            WifiDetailPreferenceController wifiDetailPreferenceController2 = this.this$0$1;
            Preference preference = wifiDetailPreferenceController2.mSecurityPref;
            WifiInfo wifiInfo = wifiDetailPreferenceController2.mWifiInfo;
            String bssid = wifiInfo != null ? wifiInfo.getBSSID() : null;
            WifiEntry wifiEntry = wifiDetailPreferenceController2.mWifiEntry;
            preference.setSummary(wifiEntry.semGetSecurityString(bssid));
            if (wifiDetailPreferenceController2.mWifiConfig != null) {
                WifiDetailPreferenceController.m1328$$Nest$msetIdPasswordPref(
                        wifiDetailPreferenceController2);
            }
            if (wifiDetailPreferenceController2.mWifiConfig != null) {
                wifiDetailPreferenceController2.mPasspointPref.setSummary(
                        String.format(
                                ((AbstractPreferenceController) wifiDetailPreferenceController2)
                                        .mContext.getString(R.string.passpoint_content),
                                wifiDetailPreferenceController2.mWifiConfig.providerFriendlyName));
                wifiDetailPreferenceController2.mPasspointPref.setVisible(
                        wifiDetailPreferenceController2.mWifiConfig.isPasspoint());
            } else {
                wifiDetailPreferenceController2.mPasspointPref.setVisible(false);
            }
            switch (i2) {
                case 0:
                    i = i2;
                    wifiDetailPreferenceController.mRouterLocation.setVisible(false);
                    break;
                default:
                    if (!wifiDetailPreferenceController.mInManageNetwork
                            || (wifiConfiguration = wifiDetailPreferenceController.mWifiConfig)
                                    == null) {
                        i = i2;
                        break;
                    } else {
                        SemWifiManager semWifiManager =
                                wifiDetailPreferenceController.mSemWifiManager;
                        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
                        Map configuredNetworkLocations =
                                semWifiManager.getConfiguredNetworkLocations();
                        String key = wifiConfiguration.getKey();
                        if (configuredNetworkLocations.get(key) == null
                                && (key.contains(WifiPolicy.SECURITY_TYPE_WPA_PSK)
                                        || key.contains(WifiPolicy.SECURITY_TYPE_SAE))) {
                            if (key.contains(WifiPolicy.SECURITY_TYPE_WPA_PSK)) {
                                key =
                                        ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                                new StringBuilder(),
                                                wifiConfiguration.SSID,
                                                WifiPolicy.SECURITY_TYPE_SAE);
                            } else if (key.contains(WifiPolicy.SECURITY_TYPE_SAE)) {
                                key =
                                        ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                                new StringBuilder(),
                                                wifiConfiguration.SSID,
                                                WifiPolicy.SECURITY_TYPE_WPA_PSK);
                            }
                        }
                        Pair pair =
                                configuredNetworkLocations.get(key) == null
                                        ? new Pair(Double.valueOf(1000.0d), Double.valueOf(1000.0d))
                                        : new Pair(
                                                (Double)
                                                        ((Map) configuredNetworkLocations.get(key))
                                                                .get("latitude"),
                                                (Double)
                                                        ((Map) configuredNetworkLocations.get(key))
                                                                .get("longitude"));
                        wifiDetailPreferenceController.mLatitude =
                                ((Double) pair.first).doubleValue();
                        wifiDetailPreferenceController.mLongitude =
                                ((Double) pair.second).doubleValue();
                        Preference preference2 = wifiDetailPreferenceController.mRouterLocation;
                        double d = wifiDetailPreferenceController.mLatitude;
                        i = i2;
                        double d2 = wifiDetailPreferenceController.mLongitude;
                        if (d != 1000.0d && d2 != 1000.0d) {
                            if (d >= -90.0d && d <= 90.0d && d2 >= -180.0d && d2 <= 180.0d) {
                                z = true;
                                preference2.setVisible(z);
                                break;
                            } else {
                                Log.i(
                                        "SemWifiUtils",
                                        "Invalid location information. Hide LocationPref");
                            }
                        } else {
                            Log.i(
                                    "SemWifiUtils",
                                    "There is no location information. Hide LocationPref");
                        }
                        z = false;
                        preference2.setVisible(z);
                    }
                    break;
            }
            String macAddress = wifiEntry.getMacAddress();
            if (TextUtils.isEmpty(macAddress) || wifiEntry.isSubscription()) {
                wifiDetailPreferenceController2.mMacAddressPref.setVisible(false);
            } else {
                if (macAddress.equals("02:00:00:00:00:00")) {
                    wifiDetailPreferenceController2.mMacAddressPref.setSummary(
                            R.string.device_info_not_available);
                } else {
                    wifiDetailPreferenceController2.mMacAddressPref.setSummary(macAddress);
                }
                wifiDetailPreferenceController2.mMacAddressPref.setVisible(true);
            }
            refreshIpAddress();
            switch (i) {
                case 0:
                    int level = wifiDetailPreferenceController.mWifiEntry.getLevel();
                    Preference$$ExternalSyntheticOutline0.m(
                            ListPopupWindow$$ExternalSyntheticOutline0.m(
                                    level,
                                    "refreshRssiViews ::  signalLevel : ",
                                    ", mRssiSignalLevel : "),
                            wifiDetailPreferenceController.mRssiSignalLevel,
                            "WifiDetailsPrefCtrl");
                    if (level != wifiDetailPreferenceController.mRssiSignalLevel) {
                        wifiDetailPreferenceController.mRssiSignalLevel = level;
                        refreshRssiViews$com$samsung$android$settings$wifi$details$WifiDetailPreferenceController$Viewer();
                        break;
                    }
                    break;
                default:
                    wifiDetailPreferenceController.mRssiSignalLevel = -1;
                    refreshRssiViews$com$samsung$android$settings$wifi$details$WifiDetailPreferenceController$Viewer();
                    break;
            }
        }

        public final void
                refreshRssiViews$com$samsung$android$settings$wifi$details$WifiDetailPreferenceController$Viewer() {
            WifiDetailPreferenceController wifiDetailPreferenceController = this.this$0$1;
            EntityHeaderController entityHeaderController =
                    wifiDetailPreferenceController.mEntityHeaderController;
            int i = wifiDetailPreferenceController.mRssiSignalLevel;
            IconInjector iconInjector = wifiDetailPreferenceController.mIconInjector;
            iconInjector.getClass();
            if (i == -1) {
                i = 0;
            }
            Drawable mutate =
                    iconInjector
                            .mContext
                            .getDrawable(com.android.settingslib.Utils.getWifiIconResource(i))
                            .mutate();
            int dimensionPixelSize =
                    ((AbstractPreferenceController) wifiDetailPreferenceController)
                            .mContext
                            .getResources()
                            .getDimensionPixelSize(R.dimen.sec_wifi_header_app_icon_size);
            int minimumWidth = mutate.getMinimumWidth();
            int minimumHeight = mutate.getMinimumHeight();
            if ((minimumWidth != dimensionPixelSize || minimumHeight != dimensionPixelSize)
                    && (mutate instanceof VectorDrawable)) {
                mutate.setTintList(null);
                mutate =
                        new BitmapDrawable(
                                (Resources) null,
                                Utils.createBitmap(mutate, dimensionPixelSize, dimensionPixelSize));
            }
            entityHeaderController.setIcon(mutate);
            entityHeaderController.done(
                    ((AbstractPreferenceController) wifiDetailPreferenceController).mContext);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IconInjector {
        public final Context mContext;

        public IconInjector(Context context) {
            this.mContext = context;
        }
    }

    /* renamed from: -$$Nest$msetIdPasswordPref, reason: not valid java name */
    public static void m1328$$Nest$msetIdPasswordPref(
            WifiDetailPreferenceController wifiDetailPreferenceController) {
        wifiDetailPreferenceController.mEapIdPref.setVisible(false);
        wifiDetailPreferenceController.mPasswordPref.setVisible(false);
        if (Rune.isMaintenanceMode()
                || Rune.isShopDemo(wifiDetailPreferenceController.mContext)
                || Rune.isLDUModel()
                || WifiUtils.isNetworkLockedDown(
                        wifiDetailPreferenceController.mContext,
                        wifiDetailPreferenceController.mWifiConfig)
                || !WifiDevicePolicyManager.isAllowedToShowPasswordHiddenView(
                        wifiDetailPreferenceController.mContext)) {
            return;
        }
        WifiEntry wifiEntry = wifiDetailPreferenceController.mWifiEntry;
        if (!wifiEntry.semCanShowPassword()
                || Rune.isSamsungDexMode(wifiDetailPreferenceController.mContext)) {
            return;
        }
        int security$1 = wifiEntry.getSecurity$1();
        if (security$1 == 2 || security$1 == 5) {
            wifiDetailPreferenceController.mPasswordPref.setVisible(true);
            wifiDetailPreferenceController.mPasswordView.setText(
                    SemWifiUtils.removeDoubleQuotes(
                            wifiDetailPreferenceController.mWifiConfig.preSharedKey));
        } else if (security$1 == 1) {
            wifiDetailPreferenceController.mPasswordPref.setVisible(true);
            wifiDetailPreferenceController.mPasswordView.setText(
                    SemWifiUtils.removeDoubleQuotes(
                            wifiDetailPreferenceController.mWifiConfig.wepKeys[0]));
        } else if (security$1 == 6) {
            wifiDetailPreferenceController.mEapIdPref.setVisible(true);
            wifiDetailPreferenceController.mEapIdentityView.setText(
                    wifiDetailPreferenceController.mWifiConfig.enterpriseConfig.getIdentity());
        } else {
            if (security$1 != 3 && security$1 != 7) {
                return;
            }
            int eapMethod =
                    wifiDetailPreferenceController.mWifiConfig.enterpriseConfig.getEapMethod();
            if (eapMethod == 0 || eapMethod == 2 || eapMethod == 3) {
                wifiDetailPreferenceController.mEapIdPref.setVisible(true);
                wifiDetailPreferenceController.mEapIdentityView.setText(
                        wifiDetailPreferenceController.mWifiConfig.enterpriseConfig.getIdentity());
                wifiDetailPreferenceController.mPasswordPref.setVisible(true);
                wifiDetailPreferenceController.mPasswordView.setText(
                        wifiDetailPreferenceController.mWifiConfig.enterpriseConfig.getPassword());
            } else if (eapMethod == 1) {
                wifiDetailPreferenceController.mEapIdPref.setVisible(true);
                wifiDetailPreferenceController.mEapIdentityView.setText(
                        wifiDetailPreferenceController.mWifiConfig.enterpriseConfig.getIdentity());
            }
        }
        wifiDetailPreferenceController.mConnectInfoInsetPref.setVisible(true);
    }

    static {
        Log.isLoggable("WifiDetailsPrefCtrl", 3);
    }

    /* JADX WARN: Type inference failed for: r5v6, types: [com.samsung.android.settings.wifi.details.WifiDetailPreferenceController$1] */
    public WifiDetailPreferenceController(
            WifiEntry wifiEntry,
            ConnectivityManager connectivityManager,
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            Handler handler,
            Lifecycle lifecycle,
            WifiManager wifiManager,
            SemWifiManager semWifiManager,
            IconInjector iconInjector,
            boolean z) {
        super(context);
        this.mRssiSignalLevel = -1;
        this.mLatitude = 1000.0d;
        this.mLongitude = 1000.0d;
        this.mNetworkRequest =
                new NetworkRequest.Builder().clearCapabilities().addTransportType(1).build();
        this.mNetworkCallback =
                new ConnectivityManager
                        .NetworkCallback() { // from class:
                                             // com.samsung.android.settings.wifi.details.WifiDetailPreferenceController.1
                    public final boolean hasCapabilityChanged(
                            NetworkCapabilities networkCapabilities, int i) {
                        NetworkCapabilities networkCapabilities2 =
                                WifiDetailPreferenceController.this.mNetworkCapabilities;
                        return networkCapabilities2 == null
                                || networkCapabilities2.hasCapability(i)
                                        != networkCapabilities.hasCapability(i);
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onCapabilitiesChanged(
                            Network network, NetworkCapabilities networkCapabilities) {
                        if (!network.equals(WifiDetailPreferenceController.this.mNetwork)
                                || networkCapabilities.equals(
                                        WifiDetailPreferenceController.this.mNetworkCapabilities)) {
                            return;
                        }
                        if (hasCapabilityChanged(networkCapabilities, 16)
                                || hasCapabilityChanged(networkCapabilities, 17)
                                || hasCapabilityChanged(networkCapabilities, 24)) {
                            WifiDetailPreferenceController.this.mViewer.refreshEntityHeader();
                        }
                        WifiDetailPreferenceController wifiDetailPreferenceController =
                                WifiDetailPreferenceController.this;
                        wifiDetailPreferenceController.mNetworkCapabilities = networkCapabilities;
                        wifiDetailPreferenceController.mViewer.refreshIpAddress();
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLinkPropertiesChanged(
                            Network network, LinkProperties linkProperties) {
                        if (!network.equals(WifiDetailPreferenceController.this.mNetwork)
                                || linkProperties.equals(
                                        WifiDetailPreferenceController.this.mLinkProperties)) {
                            return;
                        }
                        WifiDetailPreferenceController wifiDetailPreferenceController =
                                WifiDetailPreferenceController.this;
                        wifiDetailPreferenceController.mLinkProperties = linkProperties;
                        wifiDetailPreferenceController.mViewer.refreshEntityHeader();
                        WifiDetailPreferenceController.this.mViewer.refreshIpAddress();
                    }
                };
        this.mWifiEntry = wifiEntry;
        wifiEntry.setListener(this);
        this.mConnectivityManager = connectivityManager;
        this.mFragment = preferenceFragmentCompat;
        this.mHandler = handler;
        this.mWifiManager = wifiManager;
        this.mSemWifiManager = semWifiManager;
        this.mIconInjector = iconInjector;
        lifecycle.addObserver(this);
        this.mInManageNetwork = z;
        this.mAuthErrorListener = this;
        ConnectedViewer connectedViewer = new ConnectedViewer(this, 1);
        this.mDisconnectedViewer = connectedViewer;
        this.mConnectedViewer = new ConnectedViewer(this, 0);
        this.mViewer = connectedViewer;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference("wifi_detail_header");
        PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
        this.mEntityHeaderController =
                new EntityHeaderController(
                        preferenceFragmentCompat.getActivity(),
                        preferenceFragmentCompat,
                        layoutPreference.mRootView.findViewById(R.id.entity_header));
        this.mHeaderInsetPref =
                (SecInsetCategoryPreference) preferenceScreen.findPreference("header_inset");
        this.mConnectInfoInsetPref =
                (SecInsetCategoryPreference) preferenceScreen.findPreference("connect_info");
        this.mSecurityPref = preferenceScreen.findPreference("security");
        LayoutPreference layoutPreference2 =
                (LayoutPreference) preferenceScreen.findPreference("eap_identity");
        this.mEapIdPref = layoutPreference2;
        ((TextView) layoutPreference2.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_eap_identity);
        this.mEapIdentityView = (EditText) this.mEapIdPref.mRootView.findViewById(R.id.edittext);
        this.mEapIdPref.mRootView.findViewById(R.id.show_icon).setVisibility(8);
        this.mEapIdentityView.setInputType(144);
        this.mEapIdentityView.setOnLongClickListener(
                new WifiDetailPreferenceController$$ExternalSyntheticLambda0(this, 0));
        LayoutPreference layoutPreference3 =
                (LayoutPreference) preferenceScreen.findPreference("wifi_password");
        this.mPasswordPref = layoutPreference3;
        ((TextView) layoutPreference3.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_password);
        this.mPasswordView = (EditText) this.mPasswordPref.mRootView.findViewById(R.id.edittext);
        ImageView imageView = (ImageView) this.mPasswordPref.mRootView.findViewById(R.id.show_icon);
        this.mPasswordShowButton = imageView;
        imageView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.details.WifiDetailPreferenceController$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        final WifiDetailPreferenceController wifiDetailPreferenceController =
                                WifiDetailPreferenceController.this;
                        if (wifiDetailPreferenceController.mIsKeyguardLoading) {
                            return;
                        }
                        wifiDetailPreferenceController.mIsKeyguardLoading = true;
                        WifiUtils.authenticateUser(
                                155,
                                new Runnable() { // from class:
                                                 // com.samsung.android.settings.wifi.details.WifiDetailPreferenceController$$ExternalSyntheticLambda3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        WifiDetailPreferenceController.this.showPassword$1();
                                    }
                                },
                                wifiDetailPreferenceController.mAuthErrorListener,
                                wifiDetailPreferenceController.mFragment);
                    }
                });
        this.mPasspointPref = preferenceScreen.findPreference("passpoint");
        this.mMacAddressPref = preferenceScreen.findPreference("mac_address");
        this.mIpAddressPref = preferenceScreen.findPreference("ip_address");
        this.mRouterLocation = preferenceScreen.findPreference("router_location");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!"router_location".equals(preference.getKey())) {
            return false;
        }
        Intent intent =
                new Intent(
                        "android.intent.action.VIEW",
                        Uri.parse("geo:0,0?q=" + this.mLatitude + "," + this.mLongitude));
        if (intent.resolveActivity(this.mContext.getPackageManager()) == null) {
            Toast.makeText(this.mContext, R.string.wifi_application_not_found, 0).show();
            return true;
        }
        this.mContext.startActivity(intent);
        Log.d("WifiDetailsPrefCtrl", "Open map application");
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.samsung.android.settings.wifi.details.WifiDppAuthenticationErrorListener
    public final void onAuthenticationError() {
        this.mIsKeyguardLoading = false;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        this.mNetwork = null;
        this.mLinkProperties = null;
        this.mNetworkCapabilities = null;
        this.mWifiInfo = null;
        this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        updateNetworkInfo$2();
        this.mViewer.refreshPage();
        this.mIsKeyguardLoading = false;
        this.mConnectivityManager.registerNetworkCallback(
                this.mNetworkRequest, this.mNetworkCallback, this.mHandler);
    }

    @Override // com.android.wifitrackerlib.WifiEntry.WifiEntryCallback
    public final void onUpdated() {
        updateNetworkInfo$2();
        this.mViewer.refreshPage();
        WifiNetworkDetailsFragment wifiNetworkDetailsFragment =
                (WifiNetworkDetailsFragment) this.mFragment;
        wifiNetworkDetailsFragment.updatePreferenceStates();
        PreferenceScreen preferenceScreen = wifiNetworkDetailsFragment.getPreferenceScreen();
        Iterator it = ((ArrayList) wifiNetworkDetailsFragment.mControllers).iterator();
        while (it.hasNext()) {
            AbstractPreferenceController abstractPreferenceController =
                    (AbstractPreferenceController) it.next();
            if (!(abstractPreferenceController instanceof WifiDetailPreferenceController)) {
                abstractPreferenceController.displayPreference(preferenceScreen);
            }
        }
        WifiDetailNavigationController wifiDetailNavigationController =
                wifiNetworkDetailsFragment.mWifiDetailNavigationController;
        if (wifiDetailNavigationController.mBottomMode == 1) {
            wifiDetailNavigationController.displayBottomNavigation();
        }
    }

    public final void setLongClickListenerOnPasswordButton(View view) {
        ((ClipboardManager) this.mContext.getSystemService("clipboard"))
                .setText(((EditText) view).getText());
    }

    public final void showPassword$1() {
        this.mPasswordShowButton.setVisibility(8);
        this.mPasswordShowButton.performAccessibilityAction(128, null);
        this.mPasswordView.performAccessibilityAction(64, null);
        this.mPasswordView.setInputType(131217);
        this.mPasswordView.setOnLongClickListener(
                new WifiDetailPreferenceController$$ExternalSyntheticLambda0(this, 1));
    }

    public final void updateNetworkInfo$2() {
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry.getConnectedState() == 2) {
            Network currentNetwork = this.mWifiManager.getCurrentNetwork();
            this.mNetwork = currentNetwork;
            this.mLinkProperties = this.mConnectivityManager.getLinkProperties(currentNetwork);
            this.mNetworkCapabilities =
                    this.mConnectivityManager.getNetworkCapabilities(this.mNetwork);
            this.mConnectivityManager.getNetworkInfo(this.mNetwork);
            this.mWifiInfo = this.mWifiManager.getConnectionInfo();
            this.mViewer = this.mConnectedViewer;
        } else {
            this.mNetwork = null;
            this.mLinkProperties = null;
            this.mNetworkCapabilities = null;
            this.mWifiInfo = null;
            this.mViewer = this.mDisconnectedViewer;
        }
        this.mWifiConfig = wifiEntry.getWifiConfiguration();
    }
}
