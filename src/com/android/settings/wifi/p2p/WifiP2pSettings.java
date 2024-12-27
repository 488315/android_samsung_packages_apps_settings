package com.android.settings.wifi.p2p;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.MacAddress;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.wifi.p2p.WifiP2pNoItemPreference;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifi.p2p.SemWifiP2pManager;
import com.sec.ims.IMSParameter;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiP2pSettings extends DashboardFragment
        implements WifiP2pManager.PeerListListener,
                WifiP2pManager.DeviceInfoListener,
                WifiP2pManager.GroupInfoListener {
    static final String SAVE_DEVICE_NAME = "DEV_NAME";
    static final String SAVE_DIALOG_PEER = "PEER_STATE";
    static final String SAVE_SELECTED_GROUP = "GROUP_NAME";
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass10();
    public static float mCurFontScale = 1.1f;
    static WifiP2pManager.Channel sChannel;
    Activity mActivity;
    int mConnectedDevices;
    public WifiP2pGroup mCreatedGroupInfo;
    public String mDeviceDescriptionViewText;
    public WifiP2pDevice mGoDevice;
    Handler mHandler;
    public boolean mInitialized;
    public boolean mIsInitDiscovery;
    public boolean mIsTablet;
    boolean mLastGroupFormed;
    public AnonymousClass3 mListenTimer;
    public WifiP2pNoItemPreference mNoDevicesPreference;
    PreferenceGroup mPeersPreference;
    public PowerManager mPowerManager;
    String mSavedDeviceName;
    String mSelectedGroupName;
    WifiP2pPeer mSelectedWifiPeer;
    public SemWifiManager mSemWifiManager;
    public SemWifiP2pManager mSemWifiP2pManager;
    public WifiP2pDevice mThisDevice;
    public TextView mThisDeviceDescriptionView;
    public boolean mWifiP2pEnabled;
    WifiP2pManager mWifiP2pManager;
    boolean mWifiP2pSearching;
    boolean needToEnableP2p;
    public MenuItem progressBarItem;
    public final IntentFilter mIntentFilter = new IntentFilter();
    public WifiP2pDeviceList mPeers = new WifiP2pDeviceList();
    public NetworkInfo.DetailedState mNetworkState = NetworkInfo.DetailedState.IDLE;
    final BroadcastReceiver mReceiver = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.p2p.WifiP2pSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            WifiP2pManager.Channel channel;
            WifiP2pManager.Channel channel2;
            WifiP2pManager.Channel channel3;
            String action = intent.getAction();
            Log.d("WifiP2pSettings", "onReceive - " + action);
            if ("android.net.wifi.p2p.STATE_CHANGED".equals(action)) {
                WifiP2pSettings.this.mWifiP2pEnabled = intent.getIntExtra("wifi_p2p_state", 1) == 2;
                WifiP2pSettings wifiP2pSettings = WifiP2pSettings.this;
                WifiP2pManager wifiP2pManager = wifiP2pSettings.mWifiP2pManager;
                if (wifiP2pManager == null || (channel3 = WifiP2pSettings.sChannel) == null) {
                    return;
                }
                if (wifiP2pSettings.mWifiP2pEnabled) {
                    wifiP2pManager.requestDeviceInfo(channel3, wifiP2pSettings);
                    return;
                } else {
                    wifiP2pSettings.updateSearchOff();
                    return;
                }
            }
            if ("android.net.wifi.p2p.PEERS_CHANGED".equals(action)) {
                WifiP2pSettings.this.mPeers =
                        (WifiP2pDeviceList) intent.getParcelableExtra("wifiP2pDeviceList");
                WifiP2pSettings.this.handlePeersChanged();
                return;
            }
            if ("android.net.wifi.p2p.CONNECTION_STATE_CHANGE".equals(action)) {
                if (WifiP2pSettings.this.mWifiP2pManager == null) {
                    return;
                }
                NetworkInfo networkInfo =
                        (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
                WifiP2pInfo wifiP2pInfo = (WifiP2pInfo) intent.getParcelableExtra("wifiP2pInfo");
                WifiP2pSettings.this.mNetworkState = networkInfo.getDetailedState();
                NetworkInfo.DetailedState detailedState = WifiP2pSettings.this.mNetworkState;
                if (detailedState == NetworkInfo.DetailedState.CONNECTED) {
                    Log.d("WifiP2pSettings", "Connected!!!");
                    WifiP2pSettings wifiP2pSettings2 = WifiP2pSettings.this;
                    wifiP2pSettings2.mWifiP2pManager.requestPeers(
                            WifiP2pSettings.sChannel, wifiP2pSettings2);
                    WifiP2pSettings.this.mCreatedGroupInfo =
                            (WifiP2pGroup) intent.getParcelableExtra("p2pGroupInfo");
                } else if (detailedState == NetworkInfo.DetailedState.CONNECTING) {
                    Log.d("WifiP2pSettings", "Connecting!!!");
                    WifiP2pSettings.this.updateSearchOff();
                } else if (detailedState == NetworkInfo.DetailedState.DISCONNECTED
                        || detailedState == NetworkInfo.DetailedState.FAILED) {
                    Log.d("WifiP2pSettings", "Disconnected");
                    WifiP2pSettings wifiP2pSettings3 = WifiP2pSettings.this;
                    wifiP2pSettings3.mCreatedGroupInfo = null;
                    if (!wifiP2pSettings3.mLastGroupFormed) {
                        wifiP2pSettings3.mHandler.postDelayed(
                                new WifiP2pSettings$$ExternalSyntheticLambda1(1, this), 500L);
                    }
                }
                WifiP2pSettings.this.mLastGroupFormed = wifiP2pInfo.groupFormed;
                return;
            }
            if ("android.net.wifi.p2p.THIS_DEVICE_CHANGED".equals(action)
                    && !isInitialStickyBroadcast()) {
                WifiP2pDevice wifiP2pDevice = (WifiP2pDevice) intent.getExtra("wifiP2pDevice");
                if (wifiP2pDevice != null) {
                    WifiP2pSettings wifiP2pSettings4 = WifiP2pSettings.this;
                    wifiP2pSettings4.mThisDevice = wifiP2pDevice;
                    if (wifiP2pDevice.status == 4) {
                        wifiP2pSettings4.finish();
                        return;
                    }
                }
                Log.d("WifiP2pSettings", "This device changed. Requesting device info.");
                WifiP2pSettings wifiP2pSettings5 = WifiP2pSettings.this;
                WifiP2pManager wifiP2pManager2 = wifiP2pSettings5.mWifiP2pManager;
                if (wifiP2pManager2 == null || (channel2 = WifiP2pSettings.sChannel) == null) {
                    return;
                }
                wifiP2pManager2.requestDeviceInfo(channel2, wifiP2pSettings5);
                return;
            }
            if ("android.net.wifi.p2p.action.WIFI_P2P_REQUEST_RESPONSE_CHANGED".equals(action)) {
                boolean booleanExtra =
                        intent.getBooleanExtra(
                                "android.net.wifi.p2p.extra.REQUEST_RESPONSE", false);
                if (isInitialStickyBroadcast() || booleanExtra) {
                    return;
                }
                WifiP2pSettings.this.mWifiP2pManager.stopPeerDiscovery(
                        WifiP2pSettings.sChannel, null);
                WifiP2pSettings.this.startSearch();
                return;
            }
            if ("android.net.wifi.p2p.DISCOVERY_STATE_CHANGE".equals(action)) {
                int intExtra = intent.getIntExtra("discoveryState", 1);
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        intExtra, "Discovery state changed: ", "WifiP2pSettings");
                if (intExtra != 2) {
                    WifiP2pSettings wifiP2pSettings6 = WifiP2pSettings.this;
                    wifiP2pSettings6.mWifiP2pSearching = false;
                    WifiP2pManager wifiP2pManager3 = wifiP2pSettings6.mWifiP2pManager;
                    if (wifiP2pManager3 == null || (channel = WifiP2pSettings.sChannel) == null) {
                        return;
                    }
                    wifiP2pManager3.requestDeviceInfo(
                            channel,
                            new WifiP2pManager
                                    .DeviceInfoListener() { // from class:
                                                            // com.android.settings.wifi.p2p.WifiP2pSettings.1.2
                                @Override // android.net.wifi.p2p.WifiP2pManager.DeviceInfoListener
                                public final void onDeviceInfoAvailable(
                                        WifiP2pDevice wifiP2pDevice2) {
                                    WifiP2pSettings.this.mThisDevice = wifiP2pDevice2;
                                    Preference$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("Device status : "),
                                            WifiP2pSettings.this.mThisDevice.status,
                                            "WifiP2pSettings");
                                    WifiP2pSettings wifiP2pSettings7 = WifiP2pSettings.this;
                                    if (wifiP2pSettings7.mThisDevice.status != 3
                                            || wifiP2pSettings7.mNetworkState
                                                    == NetworkInfo.DetailedState.CONNECTING) {
                                        wifiP2pSettings7.updateSearchOff();
                                    } else {
                                        wifiP2pSettings7.startSearch();
                                    }
                                }
                            });
                    return;
                }
                WifiP2pSettings wifiP2pSettings7 = WifiP2pSettings.this;
                wifiP2pSettings7.mWifiP2pSearching = true;
                MenuItem menuItem = wifiP2pSettings7.progressBarItem;
                if (menuItem == null) {
                    return;
                }
                menuItem.setEnabled(true);
                wifiP2pSettings7.progressBarItem.setActionView(R.layout.sec_wifi_p2p_progressbar);
                wifiP2pSettings7.progressBarItem.setVisible(true);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.p2p.WifiP2pSettings$10, reason: invalid class name */
    public final class AnonymousClass10 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            searchIndexableRaw.title = String.valueOf(R.string.wifi_menu_p2p);
            searchIndexableRaw.screenTitle = resources.getString(R.string.wifi_menu_p2p);
            ((SearchIndexableData) searchIndexableRaw).key = "wifi_p2p";
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.p2p.WifiP2pSettings$5, reason: invalid class name */
    public final class AnonymousClass5 implements WifiP2pManager.ActionListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass5(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
        public final void onFailure(int i) {
            switch (this.$r8$classId) {
                case 0:
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            i, " discover fail ", "WifiP2pSettings");
                    break;
                case 1:
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            i, " remove group fail ", "WifiP2pSettings");
                    break;
                case 2:
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            i, " cancelConnect fail ", "WifiP2pSettings");
                    WifiP2pSettings wifiP2pSettings = (WifiP2pSettings) this.this$0;
                    float f = WifiP2pSettings.mCurFontScale;
                    wifiP2pSettings.startSearch();
                    break;
                default:
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            i, " stopPeerDiscovery fail ", "WifiP2pSettings");
                    break;
            }
        }

        @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
        public final void onSuccess() {
            switch (this.$r8$classId) {
                case 0:
                    WifiP2pSettings wifiP2pSettings = (WifiP2pSettings) this.this$0;
                    wifiP2pSettings.mWifiP2pSearching = true;
                    MenuItem menuItem = wifiP2pSettings.progressBarItem;
                    if (menuItem != null) {
                        menuItem.setEnabled(true);
                        wifiP2pSettings.progressBarItem.setActionView(
                                R.layout.sec_wifi_p2p_progressbar);
                        wifiP2pSettings.progressBarItem.setVisible(true);
                        break;
                    }
                    break;
                case 1:
                    Log.d("WifiP2pSettings", " remove group success");
                    ((WifiP2pSettings) this.this$0).mCreatedGroupInfo = null;
                    break;
                case 2:
                    Log.d("WifiP2pSettings", " cancelConnect success");
                    WifiP2pSettings wifiP2pSettings2 = (WifiP2pSettings) this.this$0;
                    float f = WifiP2pSettings.mCurFontScale;
                    wifiP2pSettings2.startSearch();
                    break;
                default:
                    WifiP2pSettings wifiP2pSettings3 = WifiP2pSettings.this;
                    wifiP2pSettings3.mWifiP2pSearching = false;
                    wifiP2pSettings3.startSearch();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.p2p.WifiP2pSettings$7, reason: invalid class name */
    public final class AnonymousClass7 implements SemWifiP2pManager.ActionListener {
        public final void onFailure(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "sem remove client fail ", "WifiP2pSettings");
        }

        public final void onSuccess() {
            Log.d("WifiP2pSettings", "sem remove client success");
        }
    }

    public static WifiP2pConfig getPreferredConfig(WifiP2pDevice wifiP2pDevice) {
        WifiP2pConfig build =
                new WifiP2pConfig.Builder()
                        .setDeviceAddress(MacAddress.fromString(wifiP2pDevice.deviceAddress))
                        .enablePersistentMode(true)
                        .build();
        if (wifiP2pDevice.wpsPbcSupported()) {
            build.wps.setup = 0;
        } else if (wifiP2pDevice.wpsKeypadSupported()) {
            build.wps.setup = 2;
        } else if (wifiP2pDevice.wpsDisplaySupported()) {
            build.wps.setup = 1;
        } else {
            build.wps.setup = 0;
        }
        return build;
    }

    public static String getSecuredMacAddress(String str) {
        if (str.length() != 17) {
            return str;
        }
        return str.substring(0, 2) + ":" + str.substring(12, 14) + ":" + str.substring(15, 17);
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final void finish() {
        Log.d("WifiP2pSettings", "Finish()");
        super.finish();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiP2pSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 109;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_p2p_settings;
    }

    public final WifiP2pPeer getWifiP2pPeer(WifiP2pDevice wifiP2pDevice) {
        try {
            return new WifiP2pPeer(
                    getPrefContext(),
                    wifiP2pDevice,
                    this.mSemWifiP2pManager.getSemWifiP2pDevice(wifiP2pDevice));
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new WifiP2pPeer(getPrefContext(), wifiP2pDevice, null);
        }
    }

    public final void handlePeersChanged() {
        boolean z;
        this.mPeersPreference.removeAll();
        this.mConnectedDevices = 0;
        if (this.mCreatedGroupInfo == null || !this.mLastGroupFormed) {
            Log.d("WifiP2pSettings", "List of available peers");
            loop1:
            while (true) {
                z = false;
                for (WifiP2pDevice wifiP2pDevice : this.mPeers.getDeviceList()) {
                    Log.d("WifiP2pSettings", "-> " + wifiP2pDevice);
                    if (wifiP2pDevice != null) {
                        this.mPeersPreference.addPreference(getWifiP2pPeer(wifiP2pDevice));
                        if (wifiP2pDevice.status == 1) {
                            z = true;
                        }
                    }
                }
            }
            if (z && this.mWifiP2pSearching) {
                this.mWifiP2pManager.stopPeerDiscovery(sChannel, null);
            }
        } else {
            Log.d("WifiP2pSettings", "show connected devices");
            if (this.mCreatedGroupInfo.isGroupOwner()) {
                Log.d("WifiP2pSettings", "I am GO");
                for (WifiP2pDevice wifiP2pDevice2 : this.mCreatedGroupInfo.getClientList()) {
                    Log.d("WifiP2pSettings", "-> " + wifiP2pDevice2);
                    if (wifiP2pDevice2 != null) {
                        wifiP2pDevice2.status = 0;
                        this.mPeersPreference.addPreference(getWifiP2pPeer(wifiP2pDevice2));
                        this.mConnectedDevices++;
                    }
                }
            } else {
                WifiP2pDevice owner = this.mCreatedGroupInfo.getOwner();
                this.mGoDevice = owner;
                if (owner == null) {
                    this.mCreatedGroupInfo = null;
                    return;
                }
                Log.d(
                        "WifiP2pSettings",
                        "I am GC, my GO addr : "
                                + getSecuredMacAddress(this.mGoDevice.deviceAddress));
                WifiP2pDevice wifiP2pDevice3 = this.mGoDevice;
                wifiP2pDevice3.status = 0;
                this.mPeersPreference.addPreference(getWifiP2pPeer(wifiP2pDevice3));
                this.mConnectedDevices++;
            }
        }
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder(" mConnectedDevices "),
                this.mConnectedDevices,
                "WifiP2pSettings");
    }

    public final boolean initChannel() {
        if (sChannel != null) {
            return true;
        }
        WifiP2pManager wifiP2pManager = this.mWifiP2pManager;
        if (wifiP2pManager != null) {
            sChannel =
                    wifiP2pManager.initialize(
                            getActivity().getApplicationContext(),
                            getActivity().getMainLooper(),
                            null);
        }
        if (sChannel != null) {
            return true;
        }
        Log.e("WifiP2pSettings", "Failed to set up connection with wifi p2p service");
        return false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        String str;
        super.onActivityCreated(bundle);
        mCurFontScale = getContext().getResources().getConfiguration().fontScale;
        View inflate =
                this.mActivity
                        .getLayoutInflater()
                        .inflate(
                                R.layout.sec_wifi_p2p_custom_preference_my_device,
                                (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.p2ptitle);
        this.mThisDeviceDescriptionView = textView;
        if (textView != null) {
            textView.setMovementMethod(new ScrollingMovementMethod());
        }
        String string = Settings.System.getString(getContentResolver(), "device_name");
        if (string == null) {
            string = Settings.Global.getString(getContentResolver(), "device_name");
        }
        if (string == null && (string = Build.MODEL) == null) {
            str =
                    "Android_"
                            + Settings.Secure.getString(getContentResolver(), "android_id")
                                    .substring(0, 4);
        } else {
            str = "\u200e" + Html.escapeHtml(string) + "\u200e";
        }
        this.mSavedDeviceName = str;
        this.mDeviceDescriptionViewText =
                "<br/><br/>"
                        .concat(
                                getResources()
                                        .getString(R.string.sec_wifi_p2p_enabled_on_description))
                        .concat(
                                " "
                                        + getResources()
                                                .getString(
                                                        R.string
                                                                .sec_wifi_p2p_max_number_connectable_devices,
                                                        4));
        String concat =
                (this.mIsTablet
                                ? getResources()
                                        .getString(
                                                R.string.sec_wifi_p2p_my_device_description_tablet,
                                                this.mSavedDeviceName)
                                : getResources()
                                        .getString(
                                                R.string.sec_wifi_p2p_my_device_description,
                                                this.mSavedDeviceName))
                        .concat(this.mDeviceDescriptionViewText);
        TextView textView2 = this.mThisDeviceDescriptionView;
        if (textView2 != null) {
            textView2.setText(Html.fromHtml(concat));
            this.mThisDeviceDescriptionView.setTextAppearance(
                    R.style.sec_WifiDirectDescriptionText);
        }
        if (this.mThisDeviceDescriptionView != null && mCurFontScale > 1.2f) {
            if (getResources().getConfiguration().orientation == 2) {
                this.mThisDeviceDescriptionView.setMaxLines(2);
            } else {
                this.mThisDeviceDescriptionView.setMaxLines(5);
            }
        }
        ((LinearLayout) getView()).addView(inflate, 0);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        PreferenceGroup preferenceGroup;
        super.onConfigurationChanged(configuration);
        TextView textView = this.mThisDeviceDescriptionView;
        if (textView != null && mCurFontScale > 1.2f) {
            textView.scrollTo(0, 0);
            if (getResources().getConfiguration().orientation == 2) {
                this.mThisDeviceDescriptionView.setMaxLines(2);
            } else {
                this.mThisDeviceDescriptionView.setMaxLines(5);
            }
        }
        if (this.mNoDevicesPreference == null
                || (preferenceGroup = this.mPeersPreference) == null
                || preferenceGroup.getPreferenceCount() <= 0
                || this.mPeersPreference.getPreference(0).getLayoutResource()
                        != this.mNoDevicesPreference.getLayoutResource()) {
            return;
        }
        this.mPeersPreference.removeAll();
        getPreferenceScreen().addPreference(this.mPeersPreference);
        if (this.mNoDevicesPreference == null) {
            Activity activity = this.mActivity;
            RecyclerView listView = getListView();
            WifiP2pNoItemPreference wifiP2pNoItemPreference = new WifiP2pNoItemPreference(activity);
            wifiP2pNoItemPreference.mBaseView = listView;
            this.mNoDevicesPreference = wifiP2pNoItemPreference;
        }
        this.mNoDevicesPreference.setLayoutResource(R.layout.sec_wifi_p2p_preference_noitem);
        try {
            this.mNoDevicesPreference.setTitle(
                    getResources().getString(R.string.sec_wifi_p2p_no_device_found));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        this.mNoDevicesPreference.setEnabled(true);
        this.mNoDevicesPreference.setSelectable(false);
        this.mPeersPreference.addPreference(this.mNoDevicesPreference);
        Log.d("WifiP2pSettings", "onConfigurationChanged : nodevice found");
    }

    /* JADX WARN: Type inference failed for: r4v10, types: [com.android.settings.wifi.p2p.WifiP2pSettings$3] */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.d("WifiP2pSettings", "onCreate()");
        super.onCreate(bundle);
        this.mIsTablet = Utils.isTablet();
        this.mHandler = new Handler();
        this.needToEnableP2p = true;
        this.mIsInitDiscovery = true;
        this.mActivity = getActivity();
        this.mPowerManager = (PowerManager) getSystemService("power");
        Intent intent = this.mActivity.getIntent();
        if (intent != null
                && intent.hasExtra(":settings:fragment_args_key")
                && intent.getStringExtra(":settings:fragment_args_key").equals("wifi_p2p")) {
            Log.d("WifiP2pSettings", "intent have a wifi_p2p");
        }
        if (intent != null
                && intent.hasExtra("EXTRA_TRIGGER_APP")
                && intent.getStringExtra("EXTRA_TRIGGER_APP").equals("bixby")) {
            Log.d("WifiP2pSettings", "intent have a bixby");
        }
        this.mListenTimer =
                new CountDownTimer() { // from class:
                                       // com.android.settings.wifi.p2p.WifiP2pSettings.3
                    @Override // android.os.CountDownTimer
                    public final void onFinish() {
                        Log.d("WifiP2pSettings", "mListen Default finished");
                        WifiP2pManager wifiP2pManager = WifiP2pSettings.this.mWifiP2pManager;
                        if (wifiP2pManager == null) {
                            return;
                        }
                        wifiP2pManager.stopPeerDiscovery(WifiP2pSettings.sChannel, null);
                    }

                    @Override // android.os.CountDownTimer
                    public final void onTick(long j) {
                        WifiP2pSettings wifiP2pSettings = WifiP2pSettings.this;
                        if (wifiP2pSettings.mWifiP2pManager == null) {
                            return;
                        }
                        if (wifiP2pSettings.mPowerManager.isScreenOn()) {
                            Log.d("WifiP2pSettings", "mListen Default canceled in timer");
                            cancel();
                            WifiP2pSettings.this.mWifiP2pManager.stopPeerDiscovery(
                                    WifiP2pSettings.sChannel, null);
                            return;
                        }
                        double d = j / 1000.0d;
                        if (Math.ceil(d) % 124.0d == 0.0d) {
                            Log.d(
                                    "WifiP2pSettings",
                                    "mListen Default set time: " + Math.ceil(d) + "s");
                            WifiP2pSettings.this.mWifiP2pManager.startListening(
                                    WifiP2pSettings.sChannel, null);
                        }
                    }
                };
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        MenuItem add = menu.add(0, 1, 0, (CharSequence) null);
        this.progressBarItem = add;
        add.setShowAsAction(2);
        this.progressBarItem.setActionView(R.layout.sec_wifi_p2p_progressbar);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d("WifiP2pSettings", "onCreateView()");
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        if (this.mWifiP2pManager == null) {
            this.mWifiP2pManager = (WifiP2pManager) getSystemService("wifip2p");
        }
        if (this.mSemWifiP2pManager == null) {
            this.mSemWifiP2pManager = (SemWifiP2pManager) getSystemService("sem_wifi_p2p");
        }
        if (this.mSemWifiManager == null) {
            this.mSemWifiManager =
                    (SemWifiManager) getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        }
        if (this.mWifiP2pManager == null) {
            Log.e("WifiP2pSettings", "mWifiP2pManager is null !");
        } else if (!initChannel()) {
            Log.e("WifiP2pSettings", "Failed to set up connection with wifi p2p service");
        }
        if (this.mSemWifiP2pManager == null) {
            Log.e("WifiP2pSettings", "mSemWifiP2pManager is null !");
        }
        if (bundle == null) {
            return onCreateView;
        }
        if (bundle.containsKey(SAVE_DIALOG_PEER) && this.mSemWifiP2pManager != null) {
            WifiP2pDevice wifiP2pDevice = (WifiP2pDevice) bundle.getParcelable(SAVE_DIALOG_PEER);
            this.mSelectedWifiPeer =
                    new WifiP2pPeer(
                            getPrefContext(),
                            wifiP2pDevice,
                            this.mSemWifiP2pManager.getSemWifiP2pDevice(wifiP2pDevice));
        }
        if (bundle.containsKey(SAVE_DEVICE_NAME)) {
            this.mSavedDeviceName = bundle.getString(SAVE_DEVICE_NAME);
        }
        if (bundle.containsKey(SAVE_SELECTED_GROUP)) {
            this.mSelectedGroupName = bundle.getString(SAVE_SELECTED_GROUP);
        }
        return onCreateView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        WifiP2pManager.Channel channel;
        WifiP2pManager.Channel channel2;
        super.onDestroy();
        Log.d("WifiP2pSettings", "onDestroy()");
        cancel();
        WifiP2pManager wifiP2pManager = this.mWifiP2pManager;
        if (wifiP2pManager != null && (channel2 = sChannel) != null) {
            wifiP2pManager.stopListening(channel2, null);
        }
        WifiP2pManager wifiP2pManager2 = this.mWifiP2pManager;
        if (wifiP2pManager2 != null && (channel = sChannel) != null) {
            wifiP2pManager2.stopPeerDiscovery(channel, null);
            if (!this.mLastGroupFormed) {
                sChannel.close();
                sChannel = null;
            }
        }
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.DeviceInfoListener
    public final void onDeviceInfoAvailable(WifiP2pDevice wifiP2pDevice) {
        WifiP2pManager.Channel channel;
        Log.d("WifiP2pSettings", "Update device info: " + wifiP2pDevice);
        Log.secD(
                "WifiP2pSettings",
                " onDeviceInfoAvailable: Mac: "
                        + getSecuredMacAddress(wifiP2pDevice.deviceAddress));
        int i = wifiP2pDevice.status;
        if (i != 4) {
            if (i == 2 || (channel = sChannel) == null) {
                return;
            }
            this.mWifiP2pManager.requestNetworkInfo(
                    channel,
                    new WifiP2pManager
                            .NetworkInfoListener() { // from class:
                                                     // com.android.settings.wifi.p2p.WifiP2pSettings$$ExternalSyntheticLambda2
                        @Override // android.net.wifi.p2p.WifiP2pManager.NetworkInfoListener
                        public final void onNetworkInfoAvailable(final NetworkInfo networkInfo) {
                            final WifiP2pSettings wifiP2pSettings = WifiP2pSettings.this;
                            WifiP2pManager.Channel channel2 = WifiP2pSettings.sChannel;
                            if (channel2 == null) {
                                wifiP2pSettings.getClass();
                            } else {
                                wifiP2pSettings.mWifiP2pManager.requestConnectionInfo(
                                        channel2,
                                        new WifiP2pManager
                                                .ConnectionInfoListener() { // from class:
                                                                            // com.android.settings.wifi.p2p.WifiP2pSettings$$ExternalSyntheticLambda3
                                            @Override // android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener
                                            public final void onConnectionInfoAvailable(
                                                    WifiP2pInfo wifiP2pInfo) {
                                                WifiP2pSettings wifiP2pSettings2 =
                                                        WifiP2pSettings.this;
                                                NetworkInfo networkInfo2 = networkInfo;
                                                float f = WifiP2pSettings.mCurFontScale;
                                                wifiP2pSettings2.getClass();
                                                if (networkInfo2.isConnected()) {
                                                    Log.d("WifiP2pSettings", "Connected");
                                                } else if (!wifiP2pSettings2.mLastGroupFormed) {
                                                    wifiP2pSettings2.startSearch();
                                                }
                                                wifiP2pSettings2.mLastGroupFormed =
                                                        wifiP2pInfo.groupFormed;
                                            }
                                        });
                                wifiP2pSettings.mInitialized = true;
                            }
                        }
                    });
            this.needToEnableP2p = false;
            return;
        }
        if (!this.needToEnableP2p && this.mInitialized) {
            finish();
        }
        startSearch();
        if (this.mIsInitDiscovery) {
            this.mHandler.postDelayed(
                    new WifiP2pSettings$$ExternalSyntheticLambda1(0, this), 2000L);
            this.mIsInitDiscovery = false;
        }
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.GroupInfoListener
    public final void onGroupInfoAvailable(WifiP2pGroup wifiP2pGroup) {
        this.mCreatedGroupInfo = wifiP2pGroup;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.needToEnableP2p = false;
        Log.d("WifiP2pSettings", "onPause()");
        Intent intent = new Intent("com.android.settings.wifi.p2p.SETTINGS_STRATED");
        intent.putExtra("started", false);
        this.mActivity.getApplicationContext().sendBroadcastAsUser(intent, UserHandle.ALL);
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.PeerListListener
    public final void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
        Log.d("WifiP2pSettings", "Requested peers are available");
        this.mPeers = wifiP2pDeviceList;
        handlePeersChanged();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        WifiP2pPeer wifiP2pPeer;
        WifiP2pDevice wifiP2pDevice;
        if (preference instanceof WifiP2pPeer) {
            WifiP2pPeer wifiP2pPeer2 = (WifiP2pPeer) preference;
            this.mSelectedWifiPeer = wifiP2pPeer2;
            WifiP2pDevice wifiP2pDevice2 = wifiP2pPeer2.mDevice;
            int i = wifiP2pDevice2.status;
            if (i == 0) {
                WifiP2pGroup wifiP2pGroup = this.mCreatedGroupInfo;
                if (wifiP2pGroup == null || !wifiP2pGroup.isGroupOwner()) {
                    WifiP2pManager wifiP2pManager = this.mWifiP2pManager;
                    if (wifiP2pManager != null) {
                        wifiP2pManager.removeGroup(sChannel, new AnonymousClass5(1, this));
                    }
                } else if (this.mWifiP2pManager != null
                        && (wifiP2pPeer = this.mSelectedWifiPeer) != null
                        && (wifiP2pDevice = wifiP2pPeer.mDevice) != null) {
                    WifiP2pConfig preferredConfig = getPreferredConfig(wifiP2pDevice);
                    if (this.mWifiP2pManager.isGroupClientRemovalSupported()) {
                        try {
                            final int i2 = 1;
                            this.mWifiP2pManager.removeClient(
                                    sChannel,
                                    MacAddress.fromString(preferredConfig.deviceAddress),
                                    new WifiP2pManager
                                            .ActionListener() { // from class:
                                                                // com.android.settings.wifi.p2p.WifiP2pSettings.4
                                        @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                                        public final void onFailure(int i3) {
                                            switch (i2) {
                                                case 0:
                                                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                                            i3,
                                                            " connect fail ",
                                                            "WifiP2pSettings");
                                                    break;
                                                default:
                                                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                                            i3,
                                                            " remove client fail ",
                                                            "WifiP2pSettings");
                                                    break;
                                            }
                                        }

                                        @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                                        public final void onSuccess() {
                                            switch (i2) {
                                                case 0:
                                                    Log.d("WifiP2pSettings", " connect success");
                                                    break;
                                                default:
                                                    Log.d(
                                                            "WifiP2pSettings",
                                                            " remove client success");
                                                    break;
                                            }
                                        }
                                    });
                        } catch (IllegalArgumentException | NullPointerException unused) {
                            MainClearConfirm$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(" device address is wrong : "),
                                    preferredConfig.deviceAddress,
                                    "WifiP2pSettings");
                        }
                    } else {
                        SemWifiP2pManager semWifiP2pManager = this.mSemWifiP2pManager;
                        if (semWifiP2pManager != null) {
                            semWifiP2pManager.removeClient(
                                    preferredConfig.deviceAddress, new AnonymousClass7());
                        }
                    }
                }
            } else if (i != 1) {
                WifiP2pConfig preferredConfig2 = getPreferredConfig(wifiP2pDevice2);
                WifiP2pManager wifiP2pManager2 = this.mWifiP2pManager;
                if (wifiP2pManager2 != null) {
                    final int i3 = 0;
                    wifiP2pManager2.connect(
                            sChannel,
                            preferredConfig2,
                            new WifiP2pManager
                                    .ActionListener() { // from class:
                                                        // com.android.settings.wifi.p2p.WifiP2pSettings.4
                                @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                                public final void onFailure(int i32) {
                                    switch (i3) {
                                        case 0:
                                            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                                    i32, " connect fail ", "WifiP2pSettings");
                                            break;
                                        default:
                                            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                                    i32, " remove client fail ", "WifiP2pSettings");
                                            break;
                                    }
                                }

                                @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                                public final void onSuccess() {
                                    switch (i3) {
                                        case 0:
                                            Log.d("WifiP2pSettings", " connect success");
                                            break;
                                        default:
                                            Log.d("WifiP2pSettings", " remove client success");
                                            break;
                                    }
                                }
                            });
                }
            } else if (this.mWifiP2pManager != null) {
                Log.d("WifiP2pSettings", "cancelConnect ====> cancelConnect()");
                this.mWifiP2pManager.cancelConnect(sChannel, new AnonymousClass5(2, this));
            }
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("WifiP2pSettings", "onResume()");
        if (((UserManager) getContext().getSystemService(UserManager.class))
                .getUserRestrictions()
                .getBoolean("no_wifi_direct")) {
            Log.i(
                    "WifiDevicePolicyManager",
                    "isAllowedWifiDirectEnabled false - DISALLOW_WIFI_DIRECT");
            finish();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        setTargetFragment(null, -1);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00d2  */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onStart() {
        /*
            r6 = this;
            super.onStart()
            java.lang.String r0 = "WifiP2pSettings"
            java.lang.String r1 = "onStart()"
            android.util.Log.d(r0, r1)
            android.content.IntentFilter r1 = r6.mIntentFilter
            java.lang.String r2 = "android.net.wifi.p2p.STATE_CHANGED"
            r1.addAction(r2)
            android.content.IntentFilter r1 = r6.mIntentFilter
            java.lang.String r2 = "android.net.wifi.p2p.PEERS_CHANGED"
            r1.addAction(r2)
            android.content.IntentFilter r1 = r6.mIntentFilter
            java.lang.String r2 = "android.net.wifi.p2p.CONNECTION_STATE_CHANGE"
            r1.addAction(r2)
            android.content.IntentFilter r1 = r6.mIntentFilter
            java.lang.String r2 = "android.net.wifi.p2p.THIS_DEVICE_CHANGED"
            r1.addAction(r2)
            android.content.IntentFilter r1 = r6.mIntentFilter
            java.lang.String r2 = "android.net.wifi.p2p.DISCOVERY_STATE_CHANGE"
            r1.addAction(r2)
            android.content.IntentFilter r1 = r6.mIntentFilter
            java.lang.String r2 = "android.net.wifi.p2p.action.WIFI_P2P_REQUEST_RESPONSE_CHANGED"
            r1.addAction(r2)
            java.lang.String r1 = "p2p_persistent_group"
            androidx.preference.Preference r1 = r6.findPreference(r1)
            if (r1 == 0) goto L43
            androidx.preference.PreferenceScreen r2 = r6.getPreferenceScreen()
            r2.removePreference(r1)
        L43:
            java.lang.String r1 = "p2p_this_device"
            androidx.preference.Preference r1 = r6.findPreference(r1)
            if (r1 == 0) goto L52
            androidx.preference.PreferenceScreen r2 = r6.getPreferenceScreen()
            r2.removePreference(r1)
        L52:
            java.lang.String r1 = "p2p_peer_devices"
            androidx.preference.Preference r1 = r6.findPreference(r1)
            androidx.preference.PreferenceCategory r1 = (androidx.preference.PreferenceCategory) r1
            r6.mPeersPreference = r1
            android.net.wifi.p2p.WifiP2pManager r1 = r6.mWifiP2pManager
            r2 = 0
            if (r1 == 0) goto Lea
            boolean r1 = r6.initChannel()
            if (r1 == 0) goto Lea
            androidx.fragment.app.FragmentActivity r1 = r6.getActivity()
            android.content.BroadcastReceiver r3 = r6.mReceiver
            android.content.IntentFilter r4 = r6.mIntentFilter
            r5 = 2
            r1.registerReceiver(r3, r4, r5)
            android.net.wifi.p2p.WifiP2pManager r1 = r6.mWifiP2pManager
            android.net.wifi.p2p.WifiP2pManager$Channel r3 = com.android.settings.wifi.p2p.WifiP2pSettings.sChannel
            r1.requestGroupInfo(r3, r6)
            android.net.wifi.p2p.WifiP2pManager r1 = r6.mWifiP2pManager
            android.net.wifi.p2p.WifiP2pManager$Channel r3 = com.android.settings.wifi.p2p.WifiP2pSettings.sChannel
            com.android.settings.wifi.p2p.WifiP2pSettings$$ExternalSyntheticLambda0 r4 = new com.android.settings.wifi.p2p.WifiP2pSettings$$ExternalSyntheticLambda0
            r4.<init>()
            r1.requestConnectionInfo(r3, r4)
            java.lang.String r1 = "wifi"
            java.lang.Object r1 = r6.getSystemService(r1)
            android.net.wifi.WifiManager r1 = (android.net.wifi.WifiManager) r1
            if (r1 == 0) goto Ldc
            boolean r3 = r1.isWifiEnabled()
            if (r3 != 0) goto Ldc
            java.lang.String r3 = ""
            androidx.fragment.app.FragmentActivity r4 = r6.getActivity()     // Catch: android.os.RemoteException -> Lb5 android.content.pm.PackageManager.NameNotFoundException -> Lc4
            android.os.IBinder r4 = r4.getActivityToken()     // Catch: android.os.RemoteException -> Lb5 android.content.pm.PackageManager.NameNotFoundException -> Lc4
            android.app.IActivityManager r5 = android.app.ActivityManager.getService()     // Catch: android.os.RemoteException -> Lb5 android.content.pm.PackageManager.NameNotFoundException -> Lc4
            java.lang.String r3 = r5.getLaunchedFromPackage(r4)     // Catch: android.os.RemoteException -> Lb5 android.content.pm.PackageManager.NameNotFoundException -> Lc4
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch: android.os.RemoteException -> Lb5 android.content.pm.PackageManager.NameNotFoundException -> Lc4
            if (r4 == 0) goto Lb7
            java.lang.String r4 = "Calling package is empty"
            android.util.Log.e(r0, r4)     // Catch: android.os.RemoteException -> Lb5 android.content.pm.PackageManager.NameNotFoundException -> Lc4
        Lb3:
            r3 = r2
            goto Ld0
        Lb5:
            r3 = move-exception
            goto Lca
        Lb7:
            android.content.pm.PackageManager r4 = r6.getPackageManager()     // Catch: android.os.RemoteException -> Lb5 android.content.pm.PackageManager.NameNotFoundException -> Lc4
            android.content.pm.ApplicationInfo r4 = r4.getApplicationInfo(r3, r2)     // Catch: android.os.RemoteException -> Lb5 android.content.pm.PackageManager.NameNotFoundException -> Lc4
            boolean r3 = r4.isSignedWithPlatformKey()     // Catch: android.os.RemoteException -> Lb5 android.content.pm.PackageManager.NameNotFoundException -> Lc4
            goto Ld0
        Lc4:
            java.lang.String r4 = "Not found package "
            androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0.m$1(r4, r3, r0)
            goto Lb3
        Lca:
            java.lang.String r4 = "Could not talk to activity manager."
            android.util.Log.e(r0, r4, r3)
            goto Lb3
        Ld0:
            if (r3 == 0) goto Lea
            java.lang.String r3 = "Need to enable wifi"
            android.util.Log.d(r0, r3)
            r0 = 1
            r1.setWifiEnabled(r0)
            goto Lea
        Ldc:
            android.net.wifi.p2p.WifiP2pManager r0 = r6.mWifiP2pManager
            android.net.wifi.p2p.WifiP2pManager$Channel r1 = com.android.settings.wifi.p2p.WifiP2pSettings.sChannel
            r0.requestPeers(r1, r6)
            android.net.wifi.p2p.WifiP2pManager r0 = r6.mWifiP2pManager
            android.net.wifi.p2p.WifiP2pManager$Channel r1 = com.android.settings.wifi.p2p.WifiP2pSettings.sChannel
            r0.requestDeviceInfo(r1, r6)
        Lea:
            com.samsung.android.wifi.SemWifiManager r6 = r6.mSemWifiManager
            r6.setAllowWifiScan(r2)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.p2p.WifiP2pSettings.onStart():void");
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        WifiP2pManager.Channel channel;
        Log.d("WifiP2pSettings", "onStop");
        super.onStop();
        WifiP2pManager wifiP2pManager = this.mWifiP2pManager;
        if (wifiP2pManager != null && (channel = sChannel) != null) {
            wifiP2pManager.stopPeerDiscovery(channel, null);
        }
        this.mSemWifiManager.setAllowWifiScan(true);
        if (!this.mPowerManager.isScreenOn() && this.mCreatedGroupInfo == null) {
            Log.d("WifiP2pSettings", "mListen Offloading or Default start");
            cancel();
            start();
        }
        getActivity().unregisterReceiver(this.mReceiver);
    }

    public final void startSearch() {
        if (this.mWifiP2pManager == null || sChannel == null || isFinishingOrDestroyed()) {
            return;
        }
        Log.d("WifiP2pSettings", "startSearch");
        this.mWifiP2pManager.discoverPeers(sChannel, new AnonymousClass5(0, this));
    }

    public final void updateSearchOff() {
        this.mWifiP2pSearching = false;
        MenuItem menuItem = this.progressBarItem;
        if (menuItem == null) {
            return;
        }
        menuItem.setEnabled(false);
        this.progressBarItem.setActionView((View) null);
        this.progressBarItem.setVisible(false);
    }
}
