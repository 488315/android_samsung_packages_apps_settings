package com.android.settings.wifi;

import android.accounts.AccountManager;
import android.app.ActivityClient;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.WindowConfiguration;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.AnonymousClass7;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.RestrictedSettingsFragment;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.LoadingViewController;
import com.android.settings.widget.MainSwitchBarController;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settings.widget.SwitchWidgetController;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.settings.wifi.p2p.WifiP2pSettings;
import com.android.settingslib.wifi.WifiSavedConfigUtils;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.bixby.AppContext;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.AutoHotspotListAdapter;
import com.samsung.android.settings.wifi.AutoHotspotListAdapter$$ExternalSyntheticLambda0;
import com.samsung.android.settings.wifi.AvailableListAdapter;
import com.samsung.android.settings.wifi.ConnectedListAdapter;
import com.samsung.android.settings.wifi.EasySetupListAdapter;
import com.samsung.android.settings.wifi.InstantHotspotListAdapter;
import com.samsung.android.settings.wifi.McfAutoHotspotListAdapter;
import com.samsung.android.settings.wifi.WifiEntriesFilter;
import com.samsung.android.settings.wifi.WifiLoadingControllerListener;
import com.samsung.android.settings.wifi.WifiOffloadDialog;
import com.samsung.android.settings.wifi.WifiPickerDialog;
import com.samsung.android.settings.wifi.WifiPickerHelper;
import com.samsung.android.settings.wifi.WifiRetryPopupController;
import com.samsung.android.settings.wifi.WifiSettingsListController;
import com.samsung.android.settings.wifi.WifiSettingsListController.AnonymousClass1;
import com.samsung.android.settings.wifi.WifiSetupWizardActivity;
import com.samsung.android.settings.wifi.WifiSetupWizardActivityVzw;
import com.samsung.android.settings.wifi.advanced.OpenRoamingSettings;
import com.samsung.android.settings.wifi.details.WifiNetworkConnectFragment;
import com.samsung.android.settings.wifi.intelligent.IntelligentWifiSettings;
import com.samsung.android.settings.wifi.intelligent.WifiBadgeUtils;
import com.samsung.android.settingslib.wifi.WifiWarningDialogController;
import com.samsung.android.widget.SemTipPopup;
import com.samsung.android.wifi.SemWifiApBleScanResult;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.LogUtils;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.volte2.data.VolteConstants;

import kotlin.jvm.internal.Intrinsics;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Deprecated
/* loaded from: classes2.dex */
public class WifiSettings extends RestrictedSettingsFragment
        implements WifiPickerTracker.WifiPickerTrackerCallback,
                WifiPickerTracker.SemWifiPickerTrackerCallback,
                WifiDialog2.WifiDialog2Listener,
                DialogInterface.OnDismissListener,
                WifiEntriesFilter.OnFilterChangedListener {
    static final int ADD_NETWORK_REQUEST = 2;
    static final int MENU_ID_DISCONNECT = 3;
    static final int MENU_ID_FORGET = 4;
    static final String PREF_KEY_DATA_USAGE = "wifi_data_usage";
    public static boolean mWifiSettingsForeground;
    public boolean isFragmentPaused;
    public boolean mAlreadyCalledAutoHotspotApi;
    public boolean mAlreadyCalledMcfAutoHotspotApi;
    public View mAppBarView;
    public android.net.wifi.WifiInfo mAutoHotspotWifiInfo;
    public int mAutoHotspotWifiState;
    public AnonymousClass2 mConnectListener;
    public final AnonymousClass6 mConnectedListAdapterListener;
    public int mConnectingNetworkId;
    public Context mContext;
    public WifiDialog2 mDialog;
    public int mDialogMode;
    public String mDialogWifiEntryKey;
    public boolean mEnableNextOnConnection;
    public ProgressBar mExteranlProgressBar;
    public boolean mFinishIfConnected;
    public boolean mFinishIfWifiDisabled;
    public boolean mHasShownBubbleTip;
    public boolean mHideActionBarMenus;
    public final WifiSettings$$ExternalSyntheticLambda3 mHideProgressBarRunnable;
    public boolean mIgnoreUpdateOnDisabling;
    public boolean mInOffloadDialog;
    public boolean mInPickerDialog;
    public boolean mInSetupWizardActivity;
    public boolean mInSetupWizardActivityVzw;
    public Intent mIntent;
    public boolean mIsDexMode;
    public boolean mIsEasySetupDisplayed;
    public boolean mIsFirstLoaded;
    public boolean mIsLaunching;
    public boolean mIsProgressCircleVisible;
    public boolean mIsRestricted;
    public boolean mIsScanning;
    public boolean mIsSupportedContactUs;
    public long mLastQRAssociate;
    public int mLastQRConnectId;
    public final AnonymousClass6 mListAdapterListener;
    public WifiSettingsListController mListController;
    public View mListView;
    public ConnectedListAdapter.OnEventListener mListener;
    public WifiLoadingControllerListener mLoadingListener;
    public final LogUtils mLog;
    public String mLoggingScreenId;
    public Handler mMainHandler;
    public WifiEntry mManualConnectingNetwork;
    public OpenRoamingSettings mOpenRoamingSettings;
    public String mOpenSsid;
    public int mPreviousWifiState;
    public SettingsMainSwitchBar mProgressHeader;
    public List mQrConfigs;
    public final AnonymousClass1 mReceiver;
    public WifiRetryPopupController mRetryPopupController;
    public AnonymousClass2 mSaveListener;
    public int mScrollCounter;
    public long mScrollTimer;
    public WifiEntry mSelectedWifiEntry;
    public final AnonymousClass9 mSemWifiApSmartCallback;
    public SemWifiManager mSemWifiManager;
    public long mTimerLoggingSelectNetwork;
    public SemTipPopup mTipsDescriptionPopup;
    public WifiWarningDialogController mWarningDialogController;
    public WifiEnabler mWifiEnabler;
    public final AnonymousClass6 mWifiEnablerListener;
    public WifiEntriesFilter mWifiEntriesFilter;
    public WifiManager mWifiManager;
    public WifiPickerHelper mWifiPickerHelper;
    WifiPickerTracker mWifiPickerTracker;
    public MenuItem mWifiQrScanMenu;
    public Handler mWorkerHandler;
    public HandlerThread mWorkerThread;
    static Boolean IS_ENABLED_PROVIDER_MODEL = Boolean.TRUE;
    public static long mScanIntervalMillis = 13000;
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass5();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.WifiSettings$5, reason: invalid class name */
    public final class AnonymousClass5 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (WifiSavedConfigUtils.getAllConfigsCount(
                            context, (WifiManager) context.getSystemService(WifiManager.class))
                    == 0) {
                ((ArrayList) nonIndexableKeys).add("saved_networks");
            }
            if (!DataUsageUtils.hasWifiRadio(context)) {
                ((ArrayList) nonIndexableKeys).add(WifiSettings.PREF_KEY_DATA_USAGE);
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = WifiSettings.class.getName();
            searchIndexableResource.xmlResId = R.xml.sec_wifi_more_options;
            arrayList.add(searchIndexableResource);
            return Arrays.asList(searchIndexableResource);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return !WifiSettings.IS_ENABLED_PROVIDER_MODEL.booleanValue();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.WifiSettings$6, reason: invalid class name */
    public final class AnonymousClass6 implements ConnectedListAdapter.OnEventListener {
        public /* synthetic */ AnonymousClass6() {}

        @Override // com.samsung.android.settings.wifi.ConnectedListAdapter.OnEventListener
        public void onItemClicked(WifiEntry wifiEntry) {
            if (WifiSettings.DBG()) {
                Log.d("WifiSettings", "onItemClicked - connected");
            }
            WifiSettings wifiSettings = WifiSettings.this;
            WifiPickerHelper wifiPickerHelper = wifiSettings.mWifiPickerHelper;
            if (wifiPickerHelper != null) {
                if (!wifiPickerHelper.mIsFromLocation
                        || (wifiEntry != null && wifiEntry.getConnectedState() == 2)) {
                    Intent apIntent = wifiSettings.mWifiPickerHelper.getApIntent(wifiEntry);
                    wifiSettings.getActivity().setResult(-1, apIntent);
                    if (wifiSettings.getActivity() instanceof SettingsActivity) {
                        ((SettingsActivity) wifiSettings.getActivity())
                                .finishPreferencePanel(apIntent);
                        return;
                    } else {
                        wifiSettings.finish();
                        return;
                    }
                }
                wifiPickerHelper.mLastUserPickedNetwork = wifiEntry;
            }
            if (wifiEntry == null || !wifiEntry.canSignIn()) {
                return;
            }
            wifiEntry.signIn();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiEntryConnectCallback implements WifiEntry.ConnectCallback {
        public final WifiEntry mConnectWifiEntry;

        public WifiEntryConnectCallback(WifiEntry wifiEntry) {
            this.mConnectWifiEntry = wifiEntry;
        }

        @Override // com.android.wifitrackerlib.WifiEntry.ConnectCallback
        public final void onConnectResult(int i) {
            boolean z = WifiSettings.mWifiSettingsForeground;
            WifiSettings wifiSettings = WifiSettings.this;
            if (wifiSettings.isFinishingOrDestroyed()) {
                return;
            }
            if (i == 0) {
                wifiSettings.getClass();
                return;
            }
            if (i == 1) {
                wifiSettings.launchConnectFragment(this.mConnectWifiEntry, false);
                return;
            }
            if (i == 3) {
                if (SemWifiUtils.isSimCheck(wifiSettings.mContext)) {
                    wifiSettings.showErrorDialog$1(
                            wifiSettings.mContext.getString(
                                    R.string.activation_insert_a_valid_sim_card));
                    return;
                } else {
                    wifiSettings.showErrorDialog$1(
                            wifiSettings.mContext.getString(R.string.wifi_no_usim_warning));
                    return;
                }
            }
            if (i == 2) {
                Log.d("WifiSettings", "failed to connect wifiEntry with unknown reason");
            } else if (i == 4) {
                Toast.makeText(wifiSettings.getContext(), R.string.wifi_failed_connect_message, 0)
                        .show();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.wifi.WifiSettings$1] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.settings.wifi.WifiSettings$9] */
    public WifiSettings() {
        super("no_config_wifi");
        this.mHideProgressBarRunnable = new WifiSettings$$ExternalSyntheticLambda3(this, 1);
        this.mQrConfigs = new ArrayList();
        this.mLastQRConnectId = -1;
        this.mLastQRAssociate = -1L;
        this.mScrollTimer = 0L;
        this.mScrollCounter = 0;
        this.mConnectingNetworkId = -1;
        this.mTimerLoggingSelectNetwork = 0L;
        this.mAutoHotspotWifiState = 1;
        this.mAlreadyCalledMcfAutoHotspotApi = false;
        this.mReceiver =
                new BroadcastReceiver() { // from class: com.android.settings.wifi.WifiSettings.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        int i;
                        String action = intent.getAction();
                        Log.d("WifiSettings", "onReceive action: " + action);
                        if (!"com.samsung.android.net.wifi.NETWORK_CONNECT_FAILED".equals(action)) {
                            if ("com.samsung.android.wifi.ACTION_AUTO_WIFI_BUBBLE_TIP"
                                    .equals(action)) {
                                Context context2 = WifiSettings.this.mContext;
                                SubscriptionManager subscriptionManager =
                                        SemWifiUtils.mSubscriptionManager;
                                if (((SemWifiManager)
                                                context2.getSystemService(
                                                        WiFiManagerExt.SEM_WIFI_SERVICE))
                                        .shouldShowAutoWifiBubbleTip()) {
                                    WifiSettings.this.showAutoWifiTipPopup();
                                    return;
                                }
                                return;
                            }
                            if ("com.samsung.android.server.wifi.softap.smarttethering.updateautohotspot"
                                    .equals(action)) {
                                WifiSettings wifiSettings = WifiSettings.this;
                                boolean z = WifiSettings.mWifiSettingsForeground;
                                wifiSettings.autoHotspotPreferenceCategory();
                                return;
                            } else if ("com.samsung.android.server.wifi.softap.smarttethering.updatemcfhotspot"
                                    .equals(action)) {
                                WifiSettings wifiSettings2 = WifiSettings.this;
                                boolean z2 = WifiSettings.mWifiSettingsForeground;
                                wifiSettings2.addMcfAutoHotspotPreference();
                                return;
                            } else {
                                if ("com.samsung.wifi.salogging.intent.action.WIFI_TIPS_SA_LOGGING"
                                        .equals(action)) {
                                    SALogging.insertSALog(
                                            WifiSettings.this.mLoggingScreenId, "1500");
                                    return;
                                }
                                return;
                            }
                        }
                        WifiSettings wifiSettings3 = WifiSettings.this;
                        boolean z3 = WifiSettings.mWifiSettingsForeground;
                        wifiSettings3.getClass();
                        int intExtra =
                                intent.getIntExtra(
                                        "reason_code",
                                        EnterpriseContainerConstants.SYSTEM_SIGNED_APP);
                        Log.d(
                                "WifiSettings",
                                "NETWORK_CONNECT_FAILED "
                                        + wifiSettings3.mConnectingNetworkId
                                        + "/"
                                        + intExtra);
                        if (wifiSettings3.mManualConnectingNetwork == null) {
                            Log.d("WifiSettings", "not manual connect or supported security ");
                            return;
                        }
                        WifiConfiguration wifiConfiguration =
                                wifiSettings3.getWifiConfiguration(
                                        wifiSettings3.mConnectingNetworkId);
                        if (wifiConfiguration != null) {
                            WifiRetryPopupController wifiRetryPopupController =
                                    wifiSettings3.mRetryPopupController;
                            if (intExtra >= 10000) {
                                wifiRetryPopupController.getClass();
                                return;
                            }
                            if (wifiRetryPopupController.isAllowToShowDialogForRetry(
                                    wifiConfiguration)) {
                                SubscriptionManager subscriptionManager2 =
                                        SemWifiUtils.mSubscriptionManager;
                                WifiEnterpriseConfig wifiEnterpriseConfig =
                                        wifiConfiguration.enterpriseConfig;
                                if (wifiEnterpriseConfig == null
                                        || wifiEnterpriseConfig.getEapMethod() == -1) {
                                    wifiRetryPopupController.mDisableReason = intExtra;
                                } else {
                                    List configuredNetworks =
                                            ((SemWifiManager)
                                                            wifiRetryPopupController.mContext
                                                                    .getSystemService(
                                                                            WiFiManagerExt
                                                                                    .SEM_WIFI_SERVICE))
                                                    .getConfiguredNetworks();
                                    int i2 = 0;
                                    if (!configuredNetworks.isEmpty()) {
                                        Iterator it = configuredNetworks.iterator();
                                        while (true) {
                                            if (!it.hasNext()) {
                                                break;
                                            }
                                            SemWifiConfiguration semWifiConfiguration =
                                                    (SemWifiConfiguration) it.next();
                                            if (semWifiConfiguration.configKey.contains(
                                                            wifiConfiguration.SSID)
                                                    && (i =
                                                                    semWifiConfiguration
                                                                            .networkDisableReason)
                                                            != 0) {
                                                i2 = i;
                                                break;
                                            }
                                        }
                                    }
                                    if (i2 != 0) {
                                        intExtra = i2;
                                    }
                                    wifiRetryPopupController.mDisableReason = intExtra;
                                }
                                wifiSettings3.updateWifiEntryWithCurrentConfiguration(
                                        wifiConfiguration);
                                WifiSettings wifiSettings4 = WifiSettings.this;
                                wifiSettings4.showConnectFragmentForRetry(
                                        wifiSettings4.mManualConnectingNetwork);
                            }
                        }
                    }
                };
        this.mConnectedListAdapterListener = new AnonymousClass6();
        this.mListAdapterListener = new AnonymousClass6();
        this.mWifiEnablerListener = new AnonymousClass6();
        this.mSemWifiApSmartCallback =
                new SemWifiManager
                        .SemWifiApSmartCallback() { // from class:
                                                    // com.android.settings.wifi.WifiSettings.9
                    public final void onStateChanged(int i, String str) {
                        Log.d(
                                "WifiSettings.AutoHotspot",
                                "SemWifiManager.SemWifiApSmartCallback()`s onStateChanged() - "
                                        + i
                                        + ", MhsMac: "
                                        + str);
                        WifiSettings wifiSettings = WifiSettings.this;
                        boolean z = WifiSettings.mWifiSettingsForeground;
                        wifiSettings.addAutoHotspotPreference();
                    }
                };
        this.mLog = new LogUtils();
    }

    public static final boolean DBG() {
        if (Debug.semIsProductDev() || BaseWifiTracker.sVerboseLogging) {
            return true;
        }
        return Log.isLoggable("WifiSettings", 3);
    }

    public final void addAutoHotspotPreference() {
        int i;
        String str;
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                new StringBuilder("addAutoHotspotPreference() - Triggered,mInSetupWizardActivity:"),
                this.mInSetupWizardActivity,
                "WifiSettings.AutoHotspot");
        int wifiState = this.mWifiManager.getWifiState();
        Preference$$ExternalSyntheticOutline0.m(
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        wifiState,
                        "addAutoHotspotPreference() - wifiState: ",
                        ", mAutoHotspotWifiState: "),
                this.mAutoHotspotWifiState,
                "WifiSettings.AutoHotspot");
        if (wifiState == 1
                || wifiState == 0
                || wifiState == 4
                || (i = this.mAutoHotspotWifiState) == 1
                || i == 0) {
            Log.d(
                    "WifiSettings.AutoHotspot",
                    "addAutoHotspotPreference() - wifiState is"
                        + " WIFI_STATE_DISABLED/WIFI_STATE_DISABLING");
            this.mListController.destroyAutoHotspot();
            return;
        }
        List<SemWifiApBleScanResult> wifiApBleScanDetail =
                this.mSemWifiManager.getWifiApBleScanDetail();
        if (wifiApBleScanDetail == null) {
            Log.d(
                    "WifiSettings.AutoHotspot",
                    "addAutoHotspotPreference() - BLE scan result is null");
            this.mListController.destroyAutoHotspot();
            return;
        }
        Log.i(
                "WifiSettings.AutoHotspot",
                "addAutoHotspotPreference() - BLE scan Started, bleScanResult.size() : "
                        + wifiApBleScanDetail.size());
        SemWifiApBleScanResult semWifiApBleScanResult = null;
        if (this.mAutoHotspotWifiInfo == null || !checkWifiConnectivity()) {
            str = null;
        } else {
            str = SemWifiUtils.removeDoubleQuotes(this.mAutoHotspotWifiInfo.getSSID());
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "addAutoHotspotPreference() - mConnectedSSID: ",
                    str,
                    "WifiSettings.AutoHotspot");
        }
        for (SemWifiApBleScanResult semWifiApBleScanResult2 : wifiApBleScanDetail) {
            int smartApConnectedStatusFromScanResult =
                    this.mSemWifiManager.getSmartApConnectedStatusFromScanResult(
                            semWifiApBleScanResult2.mWifiMac);
            Log.d(
                    "WifiSettings.AutoHotspot",
                    "addAutoHotspotPreference() - Received BleAp values, mWifiMac: "
                            + semWifiApBleScanResult2.mWifiMac
                            + ", mSSID: "
                            + semWifiApBleScanResult2.mSSID
                            + ", connectedState:"
                            + smartApConnectedStatusFromScanResult);
            if (smartApConnectedStatusFromScanResult == 3
                    || (str != null && str.equals(semWifiApBleScanResult2.mSSID))) {
                semWifiApBleScanResult = semWifiApBleScanResult2;
                break;
            }
        }
        if (semWifiApBleScanResult != null) {
            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                    new StringBuilder("addAutoHotspotPreference() - removing temp: "),
                    semWifiApBleScanResult.mSSID,
                    " from bleScanResult",
                    "WifiSettings.AutoHotspot");
            wifiApBleScanDetail.remove(semWifiApBleScanResult);
        }
        WifiSettingsListController wifiSettingsListController = this.mListController;
        wifiSettingsListController.getClass();
        Log.d("WifiSettings.VI.AutoHotspot", "updateAutoHotspotList() - Triggered");
        if (wifiApBleScanDetail.size() == 0) {
            Log.d(
                    "WifiSettings.VI.AutoHotspot",
                    "updateAutoHotspotList() - bleApList is null or size is 0");
            wifiSettingsListController.destroyAutoHotspot();
            return;
        }
        Log.d(
                "WifiSettings.VI.AutoHotspot",
                "updateAutoHotspotList() - bleApList count: " + wifiApBleScanDetail.size());
        if (!wifiSettingsListController.mInSetupWizardActivity
                && !wifiSettingsListController.mInPickerDialog) {
            wifiSettingsListController.mAutoHotspotCategory.setVisibility(0);
        }
        AutoHotspotListAdapter autoHotspotListAdapter =
                wifiSettingsListController.mAutoHotspotListAdapter;
        autoHotspotListAdapter.getClass();
        Log.i(
                "AutoHotspotListAdapter",
                "updateItems() - Start, bleAccessPoint count: " + wifiApBleScanDetail.size());
        autoHotspotListAdapter.mBleAccessPoints = wifiApBleScanDetail;
        Collections.sort(
                wifiApBleScanDetail, new AutoHotspotListAdapter$$ExternalSyntheticLambda0());
        autoHotspotListAdapter.notifyDataSetChanged();
    }

    public final void addInstantHotspotPreferenceCategory(ArrayList arrayList) {
        Log.i("WifiSettings.InstantHotspot", "instantList size:" + arrayList.size());
        boolean z = UserHandle.myUserId() == 0;
        if (arrayList.isEmpty()
                || !z
                || this.mInSetupWizardActivity
                || !Utils.SPF_SupportInstantHotspot) {
            this.mListController.destroyInstantHotspot();
            return;
        }
        int wifiState = this.mWifiManager.getWifiState();
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                wifiState,
                "addInstantHotspotPreference() - wifiState: ",
                "WifiSettings.InstantHotspot");
        if (wifiState == 1 || wifiState == 0 || wifiState == 4) {
            Log.d(
                    "WifiSettings.InstantHotspot",
                    "addInstantHotspotPreference() - wifiState is"
                        + " WIFI_STATE_DISABLED/WIFI_STATE_DISABLING");
            this.mListController.destroyInstantHotspot();
            return;
        }
        WifiSettingsListController wifiSettingsListController = this.mListController;
        wifiSettingsListController.getClass();
        Log.d("WifiSettings.VI.InstantHotspot", "updateInstantHotspotList() - Triggered");
        if (arrayList.size() == 0) {
            Log.d(
                    "WifiSettings.VI.InstantHotspot",
                    "updateInstantHotspotList() - bleApList is null or size is 0");
            wifiSettingsListController.destroyInstantHotspot();
            return;
        }
        Log.d(
                "WifiSettings.VI.InstantHotspot",
                "updateInstantHotspotList() - bleApList count: " + arrayList.size());
        if (!wifiSettingsListController.mInSetupWizardActivity
                && !wifiSettingsListController.mInPickerDialog) {
            wifiSettingsListController.mInstantHotspotCategory.setVisibility(0);
        }
        InstantHotspotListAdapter instantHotspotListAdapter =
                wifiSettingsListController.mInstantHotspotListAdapter;
        instantHotspotListAdapter.getClass();
        Log.i(
                "InstantHotspotListAdapter",
                "updateItems() - Start, bleAccessPoint count: " + arrayList.size());
        instantHotspotListAdapter.mBleAccessPoints = arrayList;
        instantHotspotListAdapter.notifyDataSetChanged();
    }

    public final void addMcfAutoHotspotPreference() {
        SemWifiApBleScanResult semWifiApBleScanResult;
        SemWifiManager semWifiManager = this.mSemWifiManager;
        if (semWifiManager == null || !semWifiManager.isMCFClientAutohotspotSupported()) {
            return;
        }
        Log.i("WifiSettings.MCFAutoHotspot", "addMcfAutoHotspotPreference() - Triggered");
        int wifiState = this.mWifiManager.getWifiState();
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                wifiState,
                "addMcfAutoHotspotPreference() - wifiState: ",
                "WifiSettings.MCFAutoHotspot");
        if (wifiState == 1 || wifiState == 0 || wifiState == 4) {
            Log.d(
                    "WifiSettings.AutoHotspot",
                    "addMcfAutoHotspotPreference() - wifiState is"
                        + " WIFI_STATE_DISABLED/WIFI_STATE_DISABLING");
            this.mListController.destroyMcfAutoHotspot();
            return;
        }
        List mcfScanDetail = this.mSemWifiManager.getMcfScanDetail();
        if (mcfScanDetail == null) {
            Log.d(
                    "WifiSettings.MCFAutoHotspot",
                    "addMcfAutoHotspotPreference() - Mcf scan result is null");
            this.mListController.destroyMcfAutoHotspot();
            return;
        }
        Log.i(
                "WifiSettings.MCFAutoHotspot",
                "addMcfAutoHotspotPreference() - Mcf scan Started, mcfScanResult.size() : "
                        + mcfScanDetail.size());
        Iterator it = mcfScanDetail.iterator();
        while (true) {
            if (!it.hasNext()) {
                semWifiApBleScanResult = null;
                break;
            }
            semWifiApBleScanResult = (SemWifiApBleScanResult) it.next();
            int mcfConnectedStatusFromScanResult =
                    this.mSemWifiManager.getMcfConnectedStatusFromScanResult(
                            semWifiApBleScanResult.mWifiMac);
            Log.d(
                    "WifiSettings.MCFAutoHotspot",
                    "addMcfAutoHotspotPreference() - Received BleAp values, mWifiMac: "
                            + semWifiApBleScanResult.mWifiMac
                            + ", mSSID: "
                            + semWifiApBleScanResult.mSSID
                            + ", connectedState:"
                            + mcfConnectedStatusFromScanResult);
            if (mcfConnectedStatusFromScanResult == 3) {
                break;
            }
        }
        if (semWifiApBleScanResult != null) {
            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                    new StringBuilder("addMcfAutoHotspotPreference() - removing temp: "),
                    semWifiApBleScanResult.mSSID,
                    " from mcfScanResult",
                    "WifiSettings.AutoHotspot");
            mcfScanDetail.remove(semWifiApBleScanResult);
        }
        WifiSettingsListController wifiSettingsListController = this.mListController;
        wifiSettingsListController.getClass();
        Log.d("WifiSettings.VI.MCFAutoHotspot", "updateMcfAutoHotspotList() - Triggered");
        if (mcfScanDetail.size() == 0) {
            Log.d(
                    "WifiSettings.VI.MCFAutoHotspot",
                    "updateMcfAutoHotspotList() - bleApList is null or size is 0");
            wifiSettingsListController.destroyMcfAutoHotspot();
            return;
        }
        Log.d(
                "WifiSettings.VI.MCFAutoHotspot",
                "updateMcfAutoHotspotList() - bleApList count: " + mcfScanDetail.size());
        if (!wifiSettingsListController.mInSetupWizardActivity
                && !wifiSettingsListController.mInPickerDialog) {
            wifiSettingsListController.mMcfAutoHotspotCategory.setVisibility(0);
        }
        McfAutoHotspotListAdapter mcfAutoHotspotListAdapter =
                wifiSettingsListController.mMcfAutoHotspotListAdapter;
        mcfAutoHotspotListAdapter.getClass();
        Log.i(
                "McfAutoHotspotListAdapter",
                "updateItems() - Start, bleAccessPoint count: " + mcfScanDetail.size());
        mcfAutoHotspotListAdapter.mMcfAccessPoints = mcfScanDetail;
        mcfAutoHotspotListAdapter.notifyDataSetChanged();
    }

    public final void autoHotspotPreferenceCategory() {
        boolean z = UserHandle.myUserId() == 0;
        StringBuilder sb =
                new StringBuilder(
                        "autoHotspotPreferenceCategory() - Triggered, getSamsungAccountCount() : ");
        sb.append(
                ((AccountManager) this.mContext.getSystemService("account"))
                        .getAccountsByType("com.osp.app.signin")
                        .length);
        sb.append(", isPrimaryUser : ");
        sb.append(z);
        sb.append(",mInSetupWizardActivity:");
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                sb, this.mInSetupWizardActivity, "WifiSettings.AutoHotspot");
        if (z && !this.mInSetupWizardActivity && Utils.SPF_SupportMobileApEnhanced) {
            addAutoHotspotPreference();
        } else {
            this.mListController.destroyAutoHotspot();
        }
    }

    public void changeNextButtonState(boolean z) {
        if (this.mEnableNextOnConnection && hasNextButton()) {
            getNextButton().setEnabled(z);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x006d, code lost:

       if (r2 == 6) goto L34;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void checkConnectViaQr() {
        /*
            Method dump skipped, instructions count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.WifiSettings.checkConnectViaQr():void");
    }

    public final boolean checkPermission(String str, String str2) {
        return getPackageManager().checkPermission(str2, str) == 0;
    }

    public final boolean checkWifiConnectivity() {
        NetworkInfo networkInfo;
        Context context;
        ConnectivityManager connectivityManager =
                getActivity() != null
                        ? (ConnectivityManager) getActivity().getSystemService("connectivity")
                        : null;
        if (connectivityManager == null && (context = this.mContext) != null) {
            connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        }
        if (connectivityManager == null
                || (networkInfo = connectivityManager.getNetworkInfo(1)) == null) {
            return false;
        }
        return networkInfo.isConnected();
    }

    public final boolean configureCurrentNetwork(WifiEntry wifiEntry) {
        if (wifiEntry == null) {
            WifiSettingsListController wifiSettingsListController = this.mListController;
            wifiSettingsListController.mConnectedCategory.setVisibility(8);
            wifiSettingsListController.mConnectedListAdapter.removeAll();
            return false;
        }
        WifiSettingsListController wifiSettingsListController2 = this.mListController;
        if (!wifiSettingsListController2.mInSetupWizardActivity
                && !wifiSettingsListController2.mInPickerDialog) {
            wifiSettingsListController2.mConnectedCategory.setVisibility(0);
        }
        ConnectedListAdapter connectedListAdapter =
                wifiSettingsListController2.mConnectedListAdapter;
        connectedListAdapter.mWifiEntries = new ArrayList(Collections.singletonList(wifiEntry));
        connectedListAdapter.notifyDataSetChanged();
        return true;
    }

    public final void configureEasySetupNetwork(List list) {
        if (list.size() == 0) {
            WifiSettingsListController wifiSettingsListController = this.mListController;
            wifiSettingsListController.mEasySetupCategory.setVisibility(8);
            wifiSettingsListController.mEasySetupListAdapter.removeAll();
            return;
        }
        this.mIsEasySetupDisplayed = true;
        WifiSettingsListController wifiSettingsListController2 = this.mListController;
        if (!wifiSettingsListController2.mInSetupWizardActivity
                && !wifiSettingsListController2.mInPickerDialog) {
            wifiSettingsListController2.setVisibleEasySetupList(true);
            EasySetupListAdapter easySetupListAdapter =
                    wifiSettingsListController2.mEasySetupListAdapter;
            easySetupListAdapter.mWifiEntries = list;
            easySetupListAdapter.notifyDataSetChanged();
        }
        Log.d("WifiSettings", "easy setup " + list.size());
    }

    public void connect(WifiEntry wifiEntry, boolean z, boolean z2) {
        connect(wifiEntry);
    }

    public final void forceScrollToTopOfList() {
        if (SystemClock.currentThreadTimeMillis() - this.mScrollTimer > 1500) {
            if (this.isFragmentPaused) {
                Log.d("WifiSettings", "Keep mScrollerTimer since fragment is paused");
                return;
            }
            this.mScrollTimer = 0L;
            this.mScrollCounter = 0;
            Log.d("WifiSettings", "Finish - force scroll up");
            return;
        }
        if (this.mListController != null) {
            if (DBG()) {
                Log.d("WifiSettings", "force scroll up");
            }
            this.mListController.mScrollView.scrollTo(0, 0);
            this.mScrollCounter++;
        }
    }

    public final void forget$1(WifiEntry wifiEntry) {
        this.mMetricsFeatureProvider.action(getActivity(), 137, new Pair[0]);
        wifiEntry.forget(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final AppContext getAppContext() {
        if (this.mProgressHeader == null) {
            return null;
        }
        AppContext.Builder builder = new AppContext.Builder();
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(
                "wifiEnabled",
                ((SeslSwitchBar) this.mProgressHeader).mSwitch.isChecked() ? "true" : "false");
        jSONArray.put(jSONObject2);
        JSONArray jSONArray2 = builder.mLLMContext;
        jSONObject.put(
                "type",
                jSONArray.length() > 1
                        ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                "WifiSettings", "[]")
                        : "WifiSettings");
        int length = jSONArray.length();
        JSONObject jSONObject3 = jSONArray;
        if (length <= 1) {
            jSONObject3 = jSONObject2;
        }
        jSONObject.put("value", jSONObject3);
        jSONArray2.put(jSONObject);
        return builder.build();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        if (i != 1) {
            return 0;
        }
        return VolteConstants.ErrorCode.DECLINE;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 103;
    }

    public final WifiConfiguration getWifiConfiguration(int i) {
        List<WifiConfiguration> configuredNetworks = this.mWifiManager.getConfiguredNetworks();
        if (configuredNetworks == null) {
            return null;
        }
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (i == wifiConfiguration.networkId) {
                return wifiConfiguration;
            }
        }
        return null;
    }

    public final ArrayList getWifiEntries() {
        ArrayList arrayList =
                new ArrayList(
                        (Collection)
                                this.mWifiPickerTracker.getWifiEntries().stream()
                                        .filter(new WifiSettings$$ExternalSyntheticLambda0(0))
                                        .collect(Collectors.toList()));
        Context context = this.mContext;
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        if (Settings.Global.getInt(context.getContentResolver(), "sec_wifi_developer_use_filter", 0)
                != 1) {
            return arrayList;
        }
        final WifiEntriesFilter wifiEntriesFilter = this.mWifiEntriesFilter;
        wifiEntriesFilter.getClass();
        Object collect =
                new ArrayList(arrayList)
                        .stream()
                                .filter(
                                        new Predicate() { // from class:
                                            // com.samsung.android.settings.wifi.WifiEntriesFilter$getFilteredWifiEntries$1
                                            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                                            /* JADX WARN: Code restructure failed: missing block: B:48:0x009d, code lost:

                                               if (com.samsung.android.wifitrackerlib.SemWifiUtils.isSecured(r6.getSecurity$1()) == false) goto L20;
                                            */
                                            /* JADX WARN: Code restructure failed: missing block: B:6:0x002b, code lost:

                                               if (kotlin.text.StringsKt___StringsKt.contains(r0, r3, true) != false) goto L9;
                                            */
                                            /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
                                            @Override // java.util.function.Predicate
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                                            */
                                            public final boolean test(java.lang.Object r6) {
                                                /*
                                                    r5 = this;
                                                    com.android.wifitrackerlib.WifiEntry r6 = (com.android.wifitrackerlib.WifiEntry) r6
                                                    com.samsung.android.settings.wifi.WifiEntriesFilter r5 = com.samsung.android.settings.wifi.WifiEntriesFilter.this
                                                    kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
                                                    r5.getClass()
                                                    java.lang.String r0 = r5.ssidFilter
                                                    boolean r0 = android.text.TextUtils.isEmpty(r0)
                                                    r1 = 1
                                                    r2 = 0
                                                    if (r0 == 0) goto L15
                                                    goto L2d
                                                L15:
                                                    java.lang.String r0 = r6.getSsid()
                                                    boolean r3 = android.text.TextUtils.isEmpty(r0)
                                                    if (r3 != 0) goto La8
                                                    kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
                                                    java.lang.String r3 = r5.ssidFilter
                                                    kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
                                                    boolean r0 = kotlin.text.StringsKt___StringsKt.contains(r0, r3, r1)
                                                    if (r0 == 0) goto La8
                                                L2d:
                                                    com.samsung.android.settings.wifi.WifiMultiChoiceFilter r5 = r5.multiChoiceFilter
                                                    r5.getClass()
                                                    java.util.ArrayList r0 = r5.filters
                                                    int r0 = r0.size()
                                                    if (r0 != 0) goto L3d
                                                L3a:
                                                    r5 = r1
                                                    goto La5
                                                L3d:
                                                    java.util.ArrayList r5 = r5.filters
                                                    java.util.Iterator r5 = r5.iterator()
                                                L43:
                                                    boolean r0 = r5.hasNext()
                                                    if (r0 == 0) goto La4
                                                    java.lang.Object r0 = r5.next()
                                                    com.samsung.android.settings.wifi.WifiMultiChoiceFilter$MultiChoiceFilter r0 = (com.samsung.android.settings.wifi.WifiMultiChoiceFilter.MultiChoiceFilter) r0
                                                    if (r0 != 0) goto L53
                                                    r3 = -1
                                                    goto L5b
                                                L53:
                                                    int[] r3 = com.samsung.android.settings.wifi.WifiMultiChoiceFilter.WhenMappings.$EnumSwitchMapping$0
                                                    int r4 = r0.ordinal()
                                                    r3 = r3[r4]
                                                L5b:
                                                    switch(r3) {
                                                        case 1: goto L95;
                                                        case 2: goto L8c;
                                                        case 3: goto L87;
                                                        case 4: goto L82;
                                                        case 5: goto L7d;
                                                        case 6: goto L6a;
                                                        case 7: goto L65;
                                                        case 8: goto L60;
                                                        default: goto L5e;
                                                    }
                                                L5e:
                                                    r0 = r1
                                                    goto La1
                                                L60:
                                                    boolean r0 = r6.semIsWifi7Network()
                                                    goto La1
                                                L65:
                                                    boolean r0 = r6.semIsWifi6Network()
                                                    goto La1
                                                L6a:
                                                    android.net.wifi.WifiInfo r0 = r6.mWifiInfo
                                                    r3 = 5
                                                    if (r0 == 0) goto L76
                                                    int r0 = r0.getWifiStandard()
                                                    if (r0 != r3) goto La0
                                                    goto L5e
                                                L76:
                                                    com.samsung.android.wifitrackerlib.SemWifiEntryFlags r0 = r6.mSemFlags
                                                    int r0 = r0.wifiStandard
                                                    if (r0 < r3) goto La0
                                                    goto L5e
                                                L7d:
                                                    boolean r0 = com.samsung.android.settings.wifi.WifiMultiChoiceFilter.isBandMatching(r0, r6)
                                                    goto La1
                                                L82:
                                                    boolean r0 = com.samsung.android.settings.wifi.WifiMultiChoiceFilter.isBandMatching(r0, r6)
                                                    goto La1
                                                L87:
                                                    boolean r0 = com.samsung.android.settings.wifi.WifiMultiChoiceFilter.isBandMatching(r0, r6)
                                                    goto La1
                                                L8c:
                                                    int r0 = r6.getSecurity$1()
                                                    boolean r0 = com.samsung.android.wifitrackerlib.SemWifiUtils.isSecured(r0)
                                                    goto La1
                                                L95:
                                                    int r0 = r6.getSecurity$1()
                                                    boolean r0 = com.samsung.android.wifitrackerlib.SemWifiUtils.isSecured(r0)
                                                    if (r0 != 0) goto La0
                                                    goto L5e
                                                La0:
                                                    r0 = r2
                                                La1:
                                                    if (r0 == 0) goto L43
                                                    goto L3a
                                                La4:
                                                    r5 = r2
                                                La5:
                                                    if (r5 == 0) goto La8
                                                    goto La9
                                                La8:
                                                    r1 = r2
                                                La9:
                                                    return r1
                                                */
                                                throw new UnsupportedOperationException(
                                                        "Method not decompiled:"
                                                            + " com.samsung.android.settings.wifi.WifiEntriesFilter$getFilteredWifiEntries$1.test(java.lang.Object):boolean");
                                            }
                                        })
                                .collect(Collectors.toList());
        Intrinsics.checkNotNull(
                collect,
                "null cannot be cast to non-null type"
                    + " java.util.ArrayList<com.android.wifitrackerlib.WifiEntry>{"
                    + " kotlin.collections.TypeAliasesKt.ArrayList<com.android.wifitrackerlib.WifiEntry>"
                    + " }");
        return (ArrayList) collect;
    }

    public void handleAddNetworkRequest(int i, Intent intent) {
        WifiConfiguration wifiConfiguration;
        if (i != -1
                || (wifiConfiguration =
                                (WifiConfiguration) intent.getParcelableExtra("wifi_config_key"))
                        == null) {
            return;
        }
        this.mWifiManager.save(wifiConfiguration, this.mSaveListener);
    }

    public final void launchConnectFragment(WifiEntry wifiEntry, boolean z) {
        if (this.mIsLaunching) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("key_connect_wifientry_key", wifiEntry.getKey());
        bundle.putBoolean("DialogForRetry", z);
        bundle.putInt("disableReason", this.mRetryPopupController.mDisableReason);
        bundle.putString("bssid", wifiEntry.semGetBssid());
        String name = WifiNetworkConnectFragment.class.getName();
        String title = wifiEntry.getTitle();
        if (DBG()) {
            Log.d("WifiSettings", "launch " + name + ". " + title);
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 103;
        launchRequest.mTitle = title;
        subSettingLauncher.launch();
    }

    public final void launchFragment(int i, String str) {
        if (DBG()) {
            DialogFragment$$ExternalSyntheticOutline0.m("launch ", str, "WifiSettings");
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = str;
        launchRequest.mArguments = null;
        launchRequest.mSourceMetricsCategory = 103;
        launchRequest.mTitle = getContext().getString(i);
        subSettingLauncher.launch();
    }

    public final void launchOpenRoamingFragment() {
        Log.d("WifiSettings", "launchOpenRoamingFragment");
        forceScrollToTopOfList();
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.net.wifi.wifiguider.OPENROAMING_EULA");
        intent.setFlags(67108864);
        intent.putExtra("oauth_provider", "cisco");
        getActivity().startActivityAsUser(intent, UserHandle.CURRENT);
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        getActivity().setTitle(R.string.wifi_settings_title);
        super.onActivityCreated(bundle);
        this.mConnectListener = new AnonymousClass2(this, 1);
        this.mSaveListener = new AnonymousClass2(this, 0);
        if (bundle != null) {
            this.mDialogMode = bundle.getInt("dialog_mode");
            this.mDialogWifiEntryKey = bundle.getString("wifi_ap_key");
        }
        Intent intent = getActivity().getIntent();
        this.mEnableNextOnConnection = intent.getBooleanExtra("wifi_enable_next_on_connect", false);
        if (this.mInPickerDialog) {
            if (mWifiSettingsForeground) {
                Log.d(
                        "WifiSettings",
                        "finished Wi-Fi picker dialog because another Wi-Fi settings activity is"
                            + " activated");
                popOrFinishThisActivity();
                return;
            } else if (!this.mWifiManager.isWifiEnabled()) {
                Log.d("WifiSettings", "finished Wi-Fi picker dialog because Wi-Fi is disabled");
                popOrFinishThisActivity();
                return;
            } else if (checkWifiConnectivity()) {
                Log.d(
                        "WifiSettings",
                        "finished Wi-Fi picker dialog because device was connected with AP");
                popOrFinishThisActivity();
                return;
            } else {
                Log.d("WifiSettings", "Wi-Fi picker dialog is showing");
                this.mFinishIfConnected = true;
                this.mFinishIfWifiDisabled = true;
            }
        }
        if ("com.samsung.android.net.wifi.PICK_WIFI_NETWORK_RESULT".equals(intent.getAction())) {
            this.mWifiPickerHelper = new WifiPickerHelper(this.mWifiManager, intent);
            this.mHideActionBarMenus = true;
            if (!r0.mIsFromLocation) {
                this.mListController.mListAdapter.mHideContextMenus = true;
            }
            this.mListController.mConnectedListAdapter.mPickerHelperMode = true;
        }
        if (SemWifiUtils.isEnabledUltraPowerSaving(this.mContext)) {
            this.mHideActionBarMenus = true;
        }
        if (intent.hasExtra("wifi_start_connect_ssid")) {
            this.mOpenSsid = intent.getStringExtra("wifi_start_connect_ssid");
        }
        this.mSemWifiManager.clearAutoHotspotLists();
        onWifiStateChanged();
    }

    @Override // com.android.settings.RestrictedSettingsFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        WifiConfiguration wifiConfiguration;
        WifiDialog2 wifiDialog2;
        super.onActivityResult(i, i2, intent);
        if (i == 2) {
            handleAddNetworkRequest(i2, intent);
            return;
        }
        if (i == 0) {
            if (i2 != -1 || (wifiDialog2 = this.mDialog) == null) {
                return;
            }
            wifiDialog2.dismiss();
            return;
        }
        if (i != 3) {
            if (i == 5) {
                onReceiveQrIntent(intent, false);
            } else if (i == 4) {
                return;
            }
            this.mIsRestricted = isUiRestricted();
            return;
        }
        if (i2 != -1
                || (wifiConfiguration =
                                (WifiConfiguration) intent.getParcelableExtra("network_config_key"))
                        == null) {
            return;
        }
        new WifiIssueDetectorUtil(this.mContext).reportConnectNetwork(wifiConfiguration);
        this.mWifiManager.connect(wifiConfiguration, new AnonymousClass2(this, 1));
        this.mSemWifiManager.notifyConnect(wifiConfiguration.networkId, wifiConfiguration.getKey());
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        SemTipPopup semTipPopup;
        super.onConfigurationChanged(configuration);
        updateQrScanMenuVisibility();
        if (this.mHasShownBubbleTip
                && (semTipPopup = this.mTipsDescriptionPopup) != null
                && semTipPopup.isShowing()) {
            this.mTipsDescriptionPopup.dismiss(false);
            this.mHasShownBubbleTip = false;
            showAutoWifiTipPopup();
        }
        WifiSettingsListController wifiSettingsListController = this.mListController;
        if (wifiSettingsListController != null) {
            wifiSettingsListController.setLayoutParams();
        }
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            arguments.getBoolean("manage_network", false);
        }
        this.mIsDexMode = Rune.isSamsungDexMode(getActivity());
        this.mInPickerDialog = getActivity() instanceof WifiPickerDialog;
        this.mInOffloadDialog = getActivity() instanceof WifiOffloadDialog;
        this.mContext = getContext();
        HandlerThread handlerThread =
                new HandlerThread(
                        "WifiSettings{" + Integer.toHexString(System.identityHashCode(this)) + "}",
                        10);
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        int i =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "sem_wifi_settings_framework_scan_interval",
                        0);
        boolean z = true;
        if (i == 0) {
            mScanIntervalMillis = 13000L;
        } else if (i == 1) {
            mScanIntervalMillis = 7000L;
        } else {
            mScanIntervalMillis = 20000L;
        }
        Log.d("WifiSettings", "mScanIntervalMillis : " + mScanIntervalMillis);
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mWorkerHandler = this.mWorkerThread.getThreadHandler();
        if (this.mWifiPickerTracker == null) {
            this.mWifiPickerTracker =
                    new WifiPickerTrackerHelper(
                                    getSettingsLifecycle(),
                                    getContext(),
                                    this,
                                    this,
                                    mScanIntervalMillis)
                            .mWifiPickerTracker;
        }
        FragmentActivity activity = getActivity();
        if (activity != null) {
            this.mWifiManager = (WifiManager) getActivity().getSystemService(WifiManager.class);
            this.mSemWifiManager =
                    (SemWifiManager)
                            getActivity().getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            this.mIntent = activity.getIntent();
        }
        Intent intent = this.mIntent;
        if (intent != null && !intent.getBooleanExtra("support_easysetup", true)) {
            this.mWifiPickerTracker.mIsSettingSupportEasySetup = false;
        }
        if (this.mInPickerDialog) {
            this.mLoggingScreenId = "WIFI_102";
        } else if (this.mInSetupWizardActivity) {
            this.mLoggingScreenId = "WIFI_230";
        } else if (getActivity() instanceof WifiPickerActivity) {
            this.mLoggingScreenId = "WIFI_103";
        } else {
            this.mLoggingScreenId = "WIFI_100";
        }
        WifiEntriesFilter wifiEntriesFilter = new WifiEntriesFilter();
        this.mWifiEntriesFilter = wifiEntriesFilter;
        wifiEntriesFilter.listener = this;
        Context context = this.mContext;
        WifiEntry wifiEntry = this.mWifiPickerTracker.mConnectedWifiEntry;
        WifiSettingsListController wifiSettingsListController =
                new WifiSettingsListController(
                        context, getWifiEntries(), this.mLoggingScreenId, this);
        this.mListController = wifiSettingsListController;
        wifiSettingsListController.mWifiTracker = this.mWifiPickerTracker;
        wifiSettingsListController.mFilter = this.mWifiEntriesFilter;
        if (!this.mInSetupWizardActivity) {
            this.mIsSupportedContactUs = Utils.isSupportContactUs(this.mContext, 231501000L);
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    new StringBuilder("support ContactUs : "),
                    this.mIsSupportedContactUs,
                    "WifiSettings");
        }
        this.mRetryPopupController = new WifiRetryPopupController(this.mContext);
        Context context2 = this.mContext;
        this.mOpenRoamingSettings = new OpenRoamingSettings(context2);
        this.mWarningDialogController = new WifiWarningDialogController(context2);
        onReceiveQrIntent(this.mIntent, true);
        Intent intent2 = this.mIntent;
        if (intent2 != null && intent2.hasExtra("wifi_start_connect_ssid")) {
            this.mOpenSsid = this.mIntent.getStringExtra("wifi_start_connect_ssid");
            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                    new StringBuilder("mOpenSsid is "),
                    this.mOpenSsid,
                    ", remove EXTRA_START_CONNECT_SSID intent extras",
                    "WifiSettings");
            this.mIntent.removeExtra("wifi_start_connect_ssid");
            getActivity().setIntent(this.mIntent);
        }
        this.mIsRestricted = isUiRestricted();
        if (!(getActivity() instanceof WifiSetupWizardActivity)
                && !(getActivity() instanceof WifiSetupWizardActivityVzw)) {
            z = false;
        }
        this.mInSetupWizardActivity = z;
        this.mInSetupWizardActivityVzw = getActivity() instanceof WifiSetupWizardActivityVzw;
        this.mHideActionBarMenus = false;
        this.mHasShownBubbleTip = false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView.Adapter onCreateAdapter(PreferenceScreen preferenceScreen) {
        RecyclerView.Adapter onCreateAdapter = super.onCreateAdapter(preferenceScreen);
        onCreateAdapter.setHasStableIds(true);
        return onCreateAdapter;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (i != 1) {
            return super.onCreateDialog(i);
        }
        WifiDialog2 wifiDialog2 = new WifiDialog2(requireContext(), this, null, this.mDialogMode);
        this.mDialog = wifiDialog2;
        return wifiDialog2;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (this.mIsRestricted || this.mHideActionBarMenus) {
            return;
        }
        FragmentActivity activity = getActivity();
        if ((activity instanceof AppCompatActivity
                        ? ((AppCompatActivity) activity).getSupportActionBar()
                        : null)
                != null) {
            Context context = this.mContext;
            if (SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_CAMERA_SUPPORT_QRCODE")) {
                try {
                    context.getPackageManager()
                            .getPackageInfoAsUser(
                                    "com.sec.android.app.camera", 0, UserHandle.myUserId());
                    MenuItem add = menu.add(0, 15, 0, R.string.sec_wifi_qr_scan_button_tts);
                    this.mWifiQrScanMenu = add;
                    add.setShowAsAction(2);
                    this.mWifiQrScanMenu.setIcon(R.drawable.sec_wifi_qr_code_scanner_24dp);
                    this.mWifiQrScanMenu.setTooltipText(
                            this.mContext.getString(R.string.sec_wifi_qr_scan_button_tts));
                    updateQrScanMenuVisibility();
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
        }
        Log.d("WifiSettings", "create options menu");
        menu.add(0, 14, 0, R.string.wifi_menu_p2p).setShowAsAction(0);
        menu.add(0, 6, 0, R.string.wifi_intelligent_wifi_header).setShowAsAction(0);
        menu.add(0, 5, 0, R.string.wifi_menu_advanced_button).setShowAsAction(0);
        if (this.mIsSupportedContactUs) {
            menu.add(0, 13, 0, R.string.contact_us_title).setVisible(true).setShowAsAction(0);
        }
        int wifiState = this.mWifiManager.getWifiState();
        if (((UserManager) getActivity().getSystemService(UserManager.class))
                .getUserRestrictions()
                .getBoolean("no_wifi_direct")) {
            Log.i(
                    "WifiDevicePolicyManager",
                    "isAllowedWifiDirectEnabled false - DISALLOW_WIFI_DIRECT");
        } else if (getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
            if (wifiState == 3 || wifiState != 1) {
                menu.findItem(14).setEnabled(true);
                super.onCreateOptionsMenu(menu, menuInflater);
            }
            menu.findItem(14).setEnabled(false);
            super.onCreateOptionsMenu(menu, menuInflater);
        }
        Log.i("WifiSettings", "not allowed Wi-Fi direct. disable Wi-Fi direct menu");
        menu.findItem(14).setEnabled(false);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ArrayList<String> arrayList;
        View findViewById;
        View view;
        super.onCreateView(layoutInflater, viewGroup, bundle);
        WifiSettingsListController wifiSettingsListController = this.mListController;
        boolean z = this.mInSetupWizardActivity;
        boolean z2 = this.mInSetupWizardActivityVzw;
        boolean z3 = this.mInPickerDialog || this.mInOffloadDialog;
        wifiSettingsListController.mContainer = viewGroup;
        wifiSettingsListController.mInPickerDialog = z3;
        wifiSettingsListController.mInSetupWizardActivity = z;
        wifiSettingsListController.mInSetupWizardActivityVzw = z2;
        View inflate =
                z
                        ? layoutInflater.inflate(R.layout.sec_wifi_settings_suw, viewGroup, false)
                        : wifiSettingsListController.isHideListSidePadding()
                                ? layoutInflater.inflate(
                                        R.layout.sec_wifi_settings_without_side_padding,
                                        viewGroup,
                                        false)
                                : layoutInflater.inflate(
                                        R.layout.sec_wifi_settings, viewGroup, false);
        wifiSettingsListController.mView = inflate;
        if (wifiSettingsListController.mInSetupWizardActivity) {
            View findViewById2 =
                    inflate.findViewById(R.id.rounded_background).findViewById(R.id.rounded_frame);
            findViewById2.semSetRoundedCorners(15);
            findViewById2.semSetRoundedCornerColor(
                    15,
                    wifiSettingsListController
                            .mContext
                            .getResources()
                            .getColor(R.color.sec_wifi_setupwizard_round_and_bg_color));
        } else if (!wifiSettingsListController.isHideListSidePadding()) {
            View findViewById3 =
                    inflate.findViewById(R.id.rounded_background).findViewById(R.id.rounded_frame);
            findViewById3.semSetRoundedCorners(15);
            findViewById3.semSetRoundedCornerColor(
                    15,
                    wifiSettingsListController
                            .mContext
                            .getResources()
                            .getColor(R.color.sec_widget_round_and_bgcolor));
        }
        wifiSettingsListController.mQrScannerLayout = inflate.findViewById(R.id.qr_scanner);
        if (wifiSettingsListController.mInSetupWizardActivity
                && (findViewById = inflate.findViewById(R.id.qr_scanner_button)) != null) {
            findViewById.setOnClickListener(
                    new WifiSettingsListController.AnonymousClass2(wifiSettingsListController, 0));
            boolean z4 = wifiSettingsListController.mWifiTracker.getWifiState() == 3;
            if (wifiSettingsListController.mInSetupWizardActivity
                    && (view = wifiSettingsListController.mQrScannerLayout) != null) {
                if (z4) {
                    view.setVisibility(0);
                } else {
                    view.setVisibility(8);
                }
            }
        }
        wifiSettingsListController.mRefreshListLayout = inflate.findViewById(R.id.refresh_list);
        if (wifiSettingsListController.mInSetupWizardActivityVzw) {
            inflate.findViewById(R.id.refresh_list_button)
                    .setOnClickListener(
                            new WifiSettingsListController.AnonymousClass2(
                                    wifiSettingsListController, 1));
            boolean z5 = wifiSettingsListController.mWifiTracker.getWifiState() == 3;
            if (wifiSettingsListController.mInSetupWizardActivityVzw) {
                if (z5) {
                    wifiSettingsListController.mRefreshListLayout.setVisibility(0);
                } else {
                    wifiSettingsListController.mRefreshListLayout.setVisibility(8);
                }
            }
        }
        NestedScrollView nestedScrollView =
                (NestedScrollView) inflate.findViewById(R.id.wifi_settings_nested_scroll_view);
        wifiSettingsListController.mScrollView = nestedScrollView;
        nestedScrollView.seslSetFillHorizontalPaddingEnabled(
                wifiSettingsListController
                        .mContext
                        .getResources()
                        .getColor(R.color.sec_widget_round_and_bgcolor));
        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) inflate.findViewById(R.id.refresh);
        wifiSettingsListController.mRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.mCircleView.setBackgroundColor(
                swipeRefreshLayout
                        .getContext()
                        .getColor(R.color.sec_wifi_SwipeRefreshLayout_progress_bg_color));
        SwipeRefreshLayout swipeRefreshLayout2 = wifiSettingsListController.mRefreshLayout;
        swipeRefreshLayout2.mProgress.mAnimationEndCallback =
                swipeRefreshLayout2.new AnonymousClass7();
        swipeRefreshLayout2.mListener = wifiSettingsListController.new AnonymousClass1();
        View findViewById4 = inflate.findViewById(R.id.content_layout);
        TextView textView = (TextView) findViewById4.findViewById(R.id.connected_network_category);
        wifiSettingsListController.mConnectedCategory = textView;
        textView.setAccessibilityHeading(true);
        RecyclerView recyclerView = (RecyclerView) findViewById4.findViewById(R.id.connected_list);
        wifiSettingsListController.mConnectedList = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        if (wifiSettingsListController.mConnectedListAdapter == null) {
            wifiSettingsListController.mConnectedListAdapter =
                    new ConnectedListAdapter(
                            wifiSettingsListController.mContext,
                            wifiSettingsListController.mConnectedList,
                            new ArrayList(),
                            wifiSettingsListController.isHideListSidePadding(),
                            wifiSettingsListController.mSAScreenId,
                            wifiSettingsListController.mInSetupWizardActivity);
        }
        wifiSettingsListController.mConnectedList.setAdapter(
                wifiSettingsListController.mConnectedListAdapter);
        wifiSettingsListController.setRoundedCorner(wifiSettingsListController.mConnectedList);
        TextView textView2 =
                (TextView) findViewById4.findViewById(R.id.instant_hotspot_network_category);
        wifiSettingsListController.mInstantHotspotCategory = textView2;
        textView2.setAccessibilityHeading(true);
        RecyclerView recyclerView2 =
                (RecyclerView) findViewById4.findViewById(R.id.instant_hotspot_list);
        wifiSettingsListController.mInstantHotspotList = recyclerView2;
        recyclerView2.setLayoutManager(new LinearLayoutManager(1));
        wifiSettingsListController.mInstantHotspotList.setItemAnimator(new DefaultItemAnimator());
        if (wifiSettingsListController.mInstantHotspotListAdapter == null) {
            Context context = wifiSettingsListController.mContext;
            RecyclerView recyclerView3 = wifiSettingsListController.mInstantHotspotList;
            InstantHotspotListAdapter instantHotspotListAdapter = new InstantHotspotListAdapter();
            Log.i("InstantHotspotListAdapter", "AutoHotspotListAdapter() - initiated");
            instantHotspotListAdapter.mContext = context;
            instantHotspotListAdapter.mParentView = recyclerView3;
            instantHotspotListAdapter.mBleAccessPoints = new ArrayList();
            instantHotspotListAdapter.notifyDataSetChanged();
            wifiSettingsListController.mInstantHotspotListAdapter = instantHotspotListAdapter;
        }
        wifiSettingsListController.mInstantHotspotList.setAdapter(
                wifiSettingsListController.mInstantHotspotListAdapter);
        wifiSettingsListController.setRoundedCorner(wifiSettingsListController.mInstantHotspotList);
        TextView textView3 =
                (TextView) findViewById4.findViewById(R.id.auto_hotspot_network_category);
        wifiSettingsListController.mAutoHotspotCategory = textView3;
        textView3.setAccessibilityHeading(true);
        RecyclerView recyclerView4 =
                (RecyclerView) findViewById4.findViewById(R.id.auto_hotspot_list);
        wifiSettingsListController.mAutoHotspotList = recyclerView4;
        recyclerView4.setLayoutManager(new LinearLayoutManager(1));
        wifiSettingsListController.mAutoHotspotList.setItemAnimator(new DefaultItemAnimator());
        if (wifiSettingsListController.mAutoHotspotListAdapter == null) {
            Context context2 = wifiSettingsListController.mContext;
            RecyclerView recyclerView5 = wifiSettingsListController.mAutoHotspotList;
            AutoHotspotListAdapter autoHotspotListAdapter = new AutoHotspotListAdapter();
            Log.i("AutoHotspotListAdapter", "AutoHotspotListAdapter() - initiated");
            autoHotspotListAdapter.mContext = context2;
            autoHotspotListAdapter.mParentView = recyclerView5;
            autoHotspotListAdapter.mBleAccessPoints = new ArrayList();
            autoHotspotListAdapter.mSemWifiManager =
                    (SemWifiManager) context2.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            autoHotspotListAdapter.notifyDataSetChanged();
            wifiSettingsListController.mAutoHotspotListAdapter = autoHotspotListAdapter;
        }
        wifiSettingsListController.mAutoHotspotList.setAdapter(
                wifiSettingsListController.mAutoHotspotListAdapter);
        wifiSettingsListController.setRoundedCorner(wifiSettingsListController.mAutoHotspotList);
        wifiSettingsListController.mAutoHotspotCategory.setText(
                Rune.isJapanModel()
                        ? R.string.wifi_ap_smart_tethering_title_jpn
                        : R.string.wifi_ap_smart_tethering_title);
        TextView textView4 =
                (TextView) findViewById4.findViewById(R.id.mcf_auto_hotspot_network_category);
        wifiSettingsListController.mMcfAutoHotspotCategory = textView4;
        textView4.setAccessibilityHeading(true);
        RecyclerView recyclerView6 =
                (RecyclerView) findViewById4.findViewById(R.id.mcf_auto_hotspot_list);
        wifiSettingsListController.mMcfAutoHotspotList = recyclerView6;
        recyclerView6.setLayoutManager(new LinearLayoutManager(1));
        wifiSettingsListController.mMcfAutoHotspotList.setItemAnimator(new DefaultItemAnimator());
        if (wifiSettingsListController.mMcfAutoHotspotListAdapter == null) {
            Context context3 = wifiSettingsListController.mContext;
            RecyclerView recyclerView7 = wifiSettingsListController.mMcfAutoHotspotList;
            McfAutoHotspotListAdapter mcfAutoHotspotListAdapter = new McfAutoHotspotListAdapter();
            Log.i("McfAutoHotspotListAdapter", "AutoHotspotListAdapter() - initiated");
            mcfAutoHotspotListAdapter.mContext = context3;
            mcfAutoHotspotListAdapter.mParentView = recyclerView7;
            mcfAutoHotspotListAdapter.mMcfAccessPoints = new ArrayList();
            mcfAutoHotspotListAdapter.mSemWifiManager =
                    (SemWifiManager) context3.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            mcfAutoHotspotListAdapter.notifyDataSetChanged();
            wifiSettingsListController.mMcfAutoHotspotListAdapter = mcfAutoHotspotListAdapter;
        }
        wifiSettingsListController.mMcfAutoHotspotList.setAdapter(
                wifiSettingsListController.mMcfAutoHotspotListAdapter);
        wifiSettingsListController.setRoundedCorner(wifiSettingsListController.mMcfAutoHotspotList);
        TextView textView5 = (TextView) findViewById4.findViewById(R.id.available_network_category);
        wifiSettingsListController.mAvailableCategory = textView5;
        textView5.setVisibility(0);
        RecyclerView recyclerView8 = (RecyclerView) findViewById4.findViewById(R.id.twlist);
        wifiSettingsListController.mAvailableList = recyclerView8;
        recyclerView8.setLayoutManager(new LinearLayoutManager(1));
        wifiSettingsListController.mAvailableList.setItemAnimator(new DefaultItemAnimator());
        if (wifiSettingsListController.mListAdapter == null) {
            AvailableListAdapter availableListAdapter =
                    new AvailableListAdapter(
                            wifiSettingsListController.mContext,
                            wifiSettingsListController.mAvailableList,
                            wifiSettingsListController.mWifiEntries,
                            wifiSettingsListController.isHideListSidePadding(),
                            wifiSettingsListController.mSAScreenId,
                            wifiSettingsListController.mInSetupWizardActivity);
            if (availableListAdapter.mIsSupportWifiTips) {
                Intent intent = new Intent();
                intent.setPackage("com.samsung.android.net.wifi.wifiguider");
                intent.setClassName(
                        "com.samsung.android.net.wifi.wifiguider",
                        "com.samsung.android.net.wifi.wifiguider.activity.GuideApActivity");
                availableListAdapter.mDisableIntent = intent;
            }
            availableListAdapter.notifyDataSetChanged();
            wifiSettingsListController.mListAdapter = availableListAdapter;
        }
        wifiSettingsListController.mAvailableList.setAdapter(
                wifiSettingsListController.mListAdapter);
        wifiSettingsListController.setRoundedCorner(wifiSettingsListController.mAvailableList);
        TextView textView6 = (TextView) findViewById4.findViewById(R.id.easy_setup_category);
        wifiSettingsListController.mEasySetupCategory = textView6;
        textView6.setAccessibilityHeading(true);
        RecyclerView recyclerView9 =
                (RecyclerView) findViewById4.findViewById(R.id.easy_setup_list);
        wifiSettingsListController.mEasySetupList = recyclerView9;
        recyclerView9.setLayoutManager(new LinearLayoutManager(1));
        if (wifiSettingsListController.mEasySetupListAdapter == null) {
            wifiSettingsListController.mEasySetupListAdapter =
                    new EasySetupListAdapter(
                            wifiSettingsListController.mContext,
                            wifiSettingsListController.mEasySetupList,
                            wifiSettingsListController.mWifiEntries,
                            wifiSettingsListController.isHideListSidePadding(),
                            wifiSettingsListController.mSAScreenId,
                            wifiSettingsListController.mWifiTracker);
        }
        wifiSettingsListController.mEasySetupList.setAdapter(
                wifiSettingsListController.mEasySetupListAdapter);
        wifiSettingsListController.setRoundedCorner(wifiSettingsListController.mEasySetupList);
        wifiSettingsListController.mEmptyView = (TextView) findViewById4.findViewById(R.id.empty);
        wifiSettingsListController.mBottomArea = findViewById4.findViewById(R.id.bottom_area);
        wifiSettingsListController.mFilterLayout =
                (LinearLayout) findViewById4.findViewById(R.id.filter_layout);
        wifiSettingsListController.setLayoutParams();
        if (wifiSettingsListController.mInSetupWizardActivity) {
            wifiSettingsListController.mEmptyView.setBackgroundColor(
                    wifiSettingsListController
                            .mContext
                            .getResources()
                            .getColor(R.color.sec_lock_suw_background_color));
            Log.d("WifiSettings.VI", "setSetupWizardMode() - Triggered");
            wifiSettingsListController.mConnectedCategory.setVisibility(8);
            wifiSettingsListController.mAvailableCategory.setVisibility(8);
            wifiSettingsListController.mAutoHotspotCategory.setVisibility(8);
            wifiSettingsListController.mMcfAutoHotspotCategory.setVisibility(8);
        }
        if (wifiSettingsListController.mInPickerDialog) {
            Log.d("WifiSettings.VI", "setPickerDialogMode() - Triggered");
            wifiSettingsListController.mConnectedCategory.setVisibility(8);
            wifiSettingsListController.mAvailableCategory.setVisibility(8);
            wifiSettingsListController.mScrollView.setScrollIndicators(3);
            wifiSettingsListController.mAutoHotspotCategory.setVisibility(8);
            wifiSettingsListController.mMcfAutoHotspotCategory.setVisibility(8);
        }
        if (!wifiSettingsListController.mInSetupWizardActivity
                && !wifiSettingsListController.mInPickerDialog) {
            String string =
                    Settings.Global.getString(
                            wifiSettingsListController.mContext.getContentResolver(),
                            "sem_what_hintcard_have_to_be_shown");
            Log.e("WifiSettings.VI", "dbString: " + string);
            String str = null;
            if (string == null || string.isEmpty()) {
                arrayList = null;
            } else {
                arrayList = new ArrayList(Arrays.asList(string.split(",")));
                if (arrayList.contains("1001") && arrayList.contains("1")) {
                    arrayList.remove("1");
                }
            }
            Context context4 = wifiSettingsListController.mContext;
            if (arrayList != null) {
                loop0:
                for (String str2 : WifiSettingsListController.PRIORITY_HINTCARD) {
                    for (String str3 : arrayList) {
                        if (str2.equals(str3.trim())
                                && (!"1".equals(str3.trim())
                                        || Utils.updateSmartNetworkSwitchAvailability(context4)
                                                == 1)) {
                            str = str2;
                            break loop0;
                        }
                    }
                }
            }
            Log.e("WifiSettings.VI", "hintType: " + str + " hintList: " + arrayList);
        }
        this.mListView = inflate;
        return inflate;
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mMainHandler.removeCallbacksAndMessages(null);
        this.mWorkerHandler.removeCallbacksAndMessages(null);
        HandlerThread handlerThread = this.mWorkerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        SALogging.insertSALog(
                this.mListController.mListAdapter == null ? 0 : r2.mWifiEntries.size(),
                this.mLoggingScreenId,
                "0154",
                (String) null);
        HashMap hashMap = new HashMap();
        hashMap.put(
                "easy_setup_result", this.mIsEasySetupDisplayed ? "Displayed" : "Not displayed");
        SALogging.insertSALog(this.mLoggingScreenId, "0105", hashMap, 0);
        super.onDestroy();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        SwitchWidgetController switchWidgetController;
        if (DBG()) {
            Log.d("WifiSettings", "onDestroyView");
        }
        View view = this.mListView;
        if (view != null) {
            ((ViewGroup) view).removeAllViews();
            this.mListView = null;
        }
        WifiEnabler wifiEnabler = this.mWifiEnabler;
        if (wifiEnabler != null && (switchWidgetController = wifiEnabler.mSwitchWidget) != null) {
            if (wifiEnabler.mListeningToOnSwitchChange) {
                switchWidgetController.stopListening();
                wifiEnabler.mListeningToOnSwitchChange = false;
            }
            wifiEnabler.mSwitchWidget.teardownView();
        }
        super.onDestroyView();
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final void onDialogShowing() {
        super.onDialogShowing();
        setOnDismissListener(this);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        this.mDialog = null;
        this.mDialogWifiEntryKey = null;
    }

    @Override // com.android.settings.wifi.WifiDialog2.WifiDialog2Listener
    public final void onForget(WifiDialog2 wifiDialog2) {
        forget$1(wifiDialog2.wifiEntry);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (this.mIsRestricted || this.mHideActionBarMenus) {
            return false;
        }
        int itemId = menuItem.getItemId();
        if (itemId == 5) {
            launchFragment(
                    R.string.wifi_menu_advanced_button,
                    com.samsung.android.settings.wifi.advanced.ConfigureWifiSettings.class
                            .getCanonicalName());
            return true;
        }
        if (itemId == 6) {
            launchFragment(
                    R.string.wifi_intelligent_wifi_header,
                    IntelligentWifiSettings.class.getCanonicalName());
            return true;
        }
        switch (itemId) {
            case 13:
                Log.d("WifiSettings", "start contact us activity");
                SALogging.insertSALog(this.mLoggingScreenId, "1400");
                if (this.mIsSupportedContactUs) {
                    Intent contactUsIntent =
                            Utils.getContactUsIntent(
                                    "com.android.settings.wifi",
                                    getContext()
                                            .getResources()
                                            .getString(R.string.wifi_settings_title),
                                    "6u17f9w7m9");
                    contactUsIntent.putExtra("faqUrl", "voc://view/categories");
                    startActivityForResult(contactUsIntent, 1001);
                }
                return true;
            case 14:
                launchFragment(R.string.wifi_menu_p2p, WifiP2pSettings.class.getName());
                return true;
            case 15:
                SALogging.insertSALog(this.mLoggingScreenId, "1300");
                try {
                    startActivityForResult(WifiUtils.getSecScannerIntent(), 5);
                } catch (ActivityNotFoundException e) {
                    Log.e("WifiSettings", "No activity found : " + e.toString());
                }
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        SemWifiManager semWifiManager;
        AnonymousClass9 anonymousClass9;
        super.onPause();
        WifiEnabler wifiEnabler = this.mWifiEnabler;
        if (wifiEnabler != null) {
            wifiEnabler.pause();
        }
        try {
            this.mContext.unregisterReceiver(this.mReceiver);
        } catch (IllegalArgumentException unused) {
            Log.d("WifiSettings", "Receiver not registered");
        }
        this.mOpenSsid = null;
        this.isFragmentPaused = true;
        Log.d("WifiSettings.AutoHotspot", "Unregistering WifiApSmartCallback");
        if (Utils.SPF_SupportMobileApEnhanced
                && (semWifiManager = this.mSemWifiManager) != null
                && (anonymousClass9 = this.mSemWifiApSmartCallback) != null) {
            semWifiManager.unregisterWifiApSmartCallback(anonymousClass9);
        }
        wifiApBleClientRole(false);
        startMcfClientMHSDiscovery(false);
        this.mSemWifiManager.setWifiSettingsForegroundState(0);
        this.mSemWifiManager.clearAutoHotspotLists();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItemImpl menuItemImpl = (MenuItemImpl) menu.findItem(6);
        if (menuItemImpl != null) {
            Context context = this.mContext;
            menuItemImpl.setBadgeText(
                    ((WifiBadgeUtils.hasNewFavoriteNetwork(context)
                                            && SemWifiUtils.isAutoWifiEnabled(context))
                                    || WifiBadgeUtils.isNewItemForWips(context)
                                    || (Settings.Global.getInt(
                                                            this.mContext.getContentResolver(),
                                                            "sem_auto_wifi_control_enabled",
                                                            0)
                                                    != 1
                                            && WifiBadgeUtils.isAbTestCurrentlyAvailable(
                                                    this.mContext)
                                            && WifiBadgeUtils.getABTestParam(this.mContext)
                                                    .equals("redDot")))
                            ? getContext().getString(R.string.sec_dashboard_badge_new)
                            : null);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x0174, code lost:

       if (r13 == r3) goto L75;
    */
    /* JADX WARN: Removed duplicated region for block: B:107:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00bd A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01fb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onReceiveQrIntent(android.content.Intent r17, boolean r18) {
        /*
            Method dump skipped, instructions count: 979
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.WifiSettings.onReceiveQrIntent(android.content.Intent,"
                    + " boolean):void");
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        SemWifiManager semWifiManager;
        AnonymousClass9 anonymousClass9;
        FragmentActivity activity = getActivity();
        super.onResume();
        updateQrScanMenuVisibility();
        boolean z = this.mIsRestricted;
        boolean isUiRestricted = isUiRestricted();
        this.mIsRestricted = isUiRestricted;
        if (!z && isUiRestricted) {
            restrictUi$2();
        }
        WifiEnabler wifiEnabler = this.mWifiEnabler;
        if (wifiEnabler != null) {
            wifiEnabler.resume();
        }
        this.mTimerLoggingSelectNetwork = SystemClock.elapsedRealtime();
        activity.setTitle(R.string.wifi_settings_title);
        onDataSetChanged();
        IntentFilter intentFilter =
                new IntentFilter("com.samsung.android.net.wifi.NETWORK_CONNECT_FAILED");
        intentFilter.addAction("com.samsung.android.wifi.ACTION_AUTO_WIFI_BUBBLE_TIP");
        intentFilter.addAction(
                "com.samsung.android.server.wifi.softap.smarttethering.updateautohotspot");
        intentFilter.addAction(
                "com.samsung.android.server.wifi.softap.smarttethering.updatemcfhotspot");
        intentFilter.addAction("com.samsung.wifi.salogging.intent.action.WIFI_TIPS_SA_LOGGING");
        this.mContext.registerReceiver(
                this.mReceiver, intentFilter, "android.permission.NETWORK_SETTINGS", null, 2);
        this.isFragmentPaused = false;
        if (this.mScrollTimer != 0) {
            this.mScrollTimer = SystemClock.currentThreadTimeMillis();
            forceScrollToTopOfList();
        }
        Log.d("WifiSettings.AutoHotspot", "Registering WifiApSmartCallback");
        if (Utils.SPF_SupportMobileApEnhanced
                && (semWifiManager = this.mSemWifiManager) != null
                && (anonymousClass9 = this.mSemWifiApSmartCallback) != null) {
            semWifiManager.registerWifiApSmartCallback(
                    anonymousClass9, this.mContext.getMainExecutor());
        }
        wifiApBleClientRole(true);
        startMcfClientMHSDiscovery(true);
        this.mSemWifiManager.setWifiSettingsForegroundState(1);
        changeNextButtonState(this.mWifiPickerTracker.mConnectedWifiEntry != null);
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.mDialog != null) {
            bundle.putInt("dialog_mode", this.mDialogMode);
            bundle.putString("wifi_ap_key", this.mDialogWifiEntryKey);
        }
    }

    @Override // com.android.settings.wifi.WifiDialog2.WifiDialog2Listener
    public final void onScan(WifiDialog2 wifiDialog2, String str) {
        startActivityForResult(
                WifiDppUtils.getEnrolleeQrCodeScannerIntent(wifiDialog2.getContext(), str), 0);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        android.net.wifi.WifiInfo connectionInfo;
        WifiConfiguration wifiConfiguration;
        super.onStart();
        if (!this.mInPickerDialog) {
            mWifiSettingsForeground = true;
        }
        FragmentActivity activity = getActivity();
        WifiEnabler wifiEnabler =
                activity instanceof SettingsActivity
                        ? new WifiEnabler(
                                getActivity(),
                                new MainSwitchBarController(
                                        ((SettingsActivity) activity).mMainSwitch),
                                this.mMetricsFeatureProvider)
                        : null;
        this.mWifiEnabler = wifiEnabler;
        if (wifiEnabler != null) {
            wifiEnabler.mListener = this.mWifiEnablerListener;
            FragmentActivity activity2 = getActivity();
            Log.d("WifiEnabler", NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
            wifiEnabler.mContext = activity2;
            activity2.registerReceiver(wifiEnabler.mReceiver, wifiEnabler.mIntentFilter);
            wifiEnabler.mRegisteredReceiver = true;
        }
        if (this.mIsRestricted) {
            restrictUi$2();
        }
        this.mPreviousWifiState = 4;
        onWifiStateChanged();
        SALogging.insertSALog(this.mLoggingScreenId, (String) null, (String) null);
        AvailableListAdapter availableListAdapter = this.mListController.mListAdapter;
        if (availableListAdapter != null) {
            availableListAdapter.mListener = this.mListAdapterListener;
            Context context = availableListAdapter.mContext;
            SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
            availableListAdapter.mIsMaxSummaryLineOn =
                    Settings.Global.getInt(
                                            context.getContentResolver(),
                                            "sem_wifi_developer_option_visible",
                                            0)
                                    == 1
                            && Settings.Global.getInt(
                                            availableListAdapter.mContext.getContentResolver(),
                                            "sec_wifi_developer_max_summary_line",
                                            0)
                                    == 1;
        }
        ConnectedListAdapter connectedListAdapter = this.mListController.mConnectedListAdapter;
        if (connectedListAdapter != null) {
            connectedListAdapter.mListener = this.mConnectedListAdapterListener;
            Context context2 = connectedListAdapter.mContext;
            SubscriptionManager subscriptionManager2 = SemWifiUtils.mSubscriptionManager;
            connectedListAdapter.mIsMaxSummaryLineOn =
                    Settings.Global.getInt(
                                            context2.getContentResolver(),
                                            "sem_wifi_developer_option_visible",
                                            0)
                                    == 1
                            && Settings.Global.getInt(
                                            connectedListAdapter.mContext.getContentResolver(),
                                            "sec_wifi_developer_max_summary_line",
                                            0)
                                    == 1;
        }
        if (Settings.Global.getInt(
                        this.mContext.getContentResolver(), "sem_auto_wifi_control_enabled", 0)
                != 1) {
            if (WifiBadgeUtils.getABTestParam(this.mContext).equals("bubbleTip")) {
                Context context3 = this.mContext;
                SubscriptionManager subscriptionManager3 = SemWifiUtils.mSubscriptionManager;
                if (((SemWifiManager) context3.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                        .shouldShowAutoWifiBubbleTip()) {
                    showAutoWifiTipPopup();
                    return;
                }
                return;
            }
            return;
        }
        if (checkWifiConnectivity()) {
            Context context4 = this.mContext;
            SubscriptionManager subscriptionManager4 = SemWifiUtils.mSubscriptionManager;
            if (((SemWifiManager) context4.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                            .shouldShowAutoWifiBubbleTip()
                    && (connectionInfo = this.mWifiManager.getConnectionInfo()) != null
                    && (wifiConfiguration = getWifiConfiguration(connectionInfo.getNetworkId()))
                            != null
                    && !wifiConfiguration.isPasspoint()
                    && wifiConfiguration.allowAutojoin
                    && wifiConfiguration.carrierId == -1
                    && this.mSemWifiManager.hasConfiguredNetworkLocations(
                            wifiConfiguration.getKey())) {
                showAutoWifiTipPopup();
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        getView().removeCallbacks(this.mHideProgressBarRunnable);
        if (!this.mInPickerDialog) {
            mWifiSettingsForeground = false;
        }
        AvailableListAdapter availableListAdapter = this.mListController.mListAdapter;
        if (availableListAdapter != null) {
            if (availableListAdapter.mIsTipsIconCreated) {
                SALogging.insertSALog(this.mLoggingScreenId, "1501");
            }
            if (this.mListController.mListAdapter.mIsTipsIconClicked) {
                SALogging.insertSALog(this.mLoggingScreenId, "1500");
            }
            this.mListController.mListAdapter.mListener = null;
        }
        ConnectedListAdapter connectedListAdapter = this.mListController.mConnectedListAdapter;
        if (connectedListAdapter != null) {
            connectedListAdapter.mListener = null;
        }
        WifiEnabler wifiEnabler = this.mWifiEnabler;
        if (wifiEnabler != null) {
            wifiEnabler.stop();
        }
        super.onStop();
    }

    @Override // com.android.settings.wifi.WifiDialog2.WifiDialog2Listener
    public final void onSubmit(WifiDialog2 wifiDialog2) {
        int i = wifiDialog2.mode;
        WifiConfiguration config = wifiDialog2.getController().getConfig();
        WifiEntry wifiEntry = wifiDialog2.wifiEntry;
        if (i == 2) {
            if (config == null) {
                Toast.makeText(getContext(), R.string.wifi_failed_save_message, 0).show();
                return;
            } else {
                this.mWifiManager.save(config, this.mSaveListener);
                return;
            }
        }
        if (i == 1 || (i == 0 && wifiEntry.canConnect())) {
            if (config == null) {
                connect(wifiEntry, false, false);
                return;
            }
            new WifiIssueDetectorUtil(this.mContext).reportConnectNetwork(config);
            this.mWifiManager.connect(config, new AnonymousClass2(this, 1));
            this.mSemWifiManager.notifyConnect(config.networkId, config.getKey());
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        FragmentActivity activity = getActivity();
        if (activity == null || !(activity instanceof SettingsActivity)) {
            return;
        }
        this.mProgressHeader = ((SettingsActivity) activity).mMainSwitch;
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onWifiEntriesChanged() {}

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00a2, code lost:

       if (com.samsung.android.wifitrackerlib.SemWifiEntryFlags.isWpa3SuiteBSupported == 1) goto L51;
    */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x00c6, code lost:

       if (com.samsung.android.wifitrackerlib.SemWifiEntryFlags.isWpa3SaeH2eSupported == 1) goto L51;
    */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x00cf, code lost:

       if (com.samsung.android.wifitrackerlib.SemWifiEntryFlags.isEnhancedOpenSupported(r11.mWifiManager) == false) goto L49;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onWifiEntryPreferenceClick(com.android.wifitrackerlib.WifiEntry r11) {
        /*
            Method dump skipped, instructions count: 473
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.WifiSettings.onWifiEntryPreferenceClick(com.android.wifitrackerlib.WifiEntry):void");
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {
        WifiSetupWizardActivity wifiSetupWizardActivity;
        boolean z;
        if (this.mIsRestricted || this.mContext == null || isFinishingOrDestroyed()) {
            return;
        }
        int wifiState = this.mWifiPickerTracker.getWifiState();
        if (BaseWifiTracker.sVerboseLogging) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    wifiState, "onWifiStateChanged called with wifi state: ", "WifiSettings");
        }
        if (wifiState != this.mPreviousWifiState) {
            if (getActivity() != null) {
                getActivity().invalidateOptionsMenu();
            }
            if (wifiState != 0) {
                if (wifiState != 1) {
                    if (wifiState == 2) {
                        setEmptyView$1(R.string.wifi_starting);
                        this.mAutoHotspotWifiState = 2;
                        WifiLoadingControllerListener wifiLoadingControllerListener =
                                this.mLoadingListener;
                        if (wifiLoadingControllerListener != null && !this.mIsFirstLoaded) {
                            ((WifiSetupWizardActivity) wifiLoadingControllerListener).runLoading();
                        }
                    } else if (wifiState == 3) {
                        this.mListController.updateEmptyView(true);
                        updateWifiEntryPreferences(false);
                        this.mIsScanning = true;
                        Log.d("WifiSettings", "onWifiStateChanged() Case: WIFI_STATE_ENABLED");
                        this.mAutoHotspotWifiState = 3;
                        ActionBarContextView$$ExternalSyntheticOutline0.m(
                                new StringBuilder(
                                        "wifiApBleClientRoleWithoutCheckingWifi() - isEnabled: true"
                                            + " mAlreadyCalledAutoHotspotApi : "),
                                this.mAlreadyCalledAutoHotspotApi,
                                "WifiSettings.AutoHotspot");
                        if (Utils.SPF_SupportMobileApEnhanced
                                && !this.mAlreadyCalledAutoHotspotApi) {
                            this.mAlreadyCalledAutoHotspotApi = true;
                            Log.d(
                                    "WifiSettings.AutoHotspot",
                                    "Setting wifiApBleClientRoleWithoutCheckingWifi(true)");
                            this.mSemWifiManager.wifiApBleClientRole(true);
                        }
                        SemWifiManager semWifiManager = this.mSemWifiManager;
                        if (semWifiManager != null
                                && semWifiManager.isMCFClientAutohotspotSupported()) {
                            ActionBarContextView$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(
                                            "startMcfClientAdvertiseWithoutCheckingWifi() -"
                                                + " isEnabled: true mAlreadyCalledMcfAutoHotspotApi"
                                                + " : "),
                                    this.mAlreadyCalledMcfAutoHotspotApi,
                                    "WifiSettings.MCFAutoHotspot");
                            if (!this.mAlreadyCalledMcfAutoHotspotApi) {
                                Log.d(
                                        "WifiSettings.MCFAutoHotspot",
                                        "Setting startMcfClientAdvertiseWithoutCheckingWifi(true)");
                                this.mAlreadyCalledMcfAutoHotspotApi = true;
                                this.mSemWifiManager.startMcfClientMHSDiscovery(true);
                            }
                        }
                    }
                } else {
                    if (this.mFinishIfWifiDisabled) {
                        popOrFinishThisActivity();
                        return;
                    }
                    setEmptyView$1(R.string.wifi_empty_list_wifi_off);
                    setProgressBarVisible(false);
                    WifiLoadingControllerListener wifiLoadingControllerListener2 =
                            this.mLoadingListener;
                    if (wifiLoadingControllerListener2 != null
                            && (z =
                                    (wifiSetupWizardActivity =
                                                    (WifiSetupWizardActivity)
                                                            wifiLoadingControllerListener2)
                                            .mIsLoading)) {
                        LoadingViewController loadingViewController =
                                wifiSetupWizardActivity.mLoadingViewController;
                        if (loadingViewController != null && z) {
                            loadingViewController.showContent(false);
                            wifiSetupWizardActivity.mIsLoading = false;
                        }
                        this.mIsFirstLoaded = true;
                    }
                    this.mAutoHotspotWifiState = 1;
                    if (Utils.SPF_SupportMobileApEnhanced) {
                        Log.d(
                                "WifiSettings.AutoHotspot",
                                "removeAutoHotspotList() , Setting wifiApBleClientRole(false)");
                        wifiApBleClientRole(false);
                        this.mListController.destroyAutoHotspot();
                    }
                    this.mListController.destroyMcfAutoHotspot();
                    this.mListController.destroyInstantHotspot();
                }
            } else {
                if (this.mFinishIfWifiDisabled) {
                    popOrFinishThisActivity();
                    return;
                }
                setEmptyView$1(R.string.wifi_stopping);
                this.mAutoHotspotWifiState = 0;
                if (Utils.SPF_SupportMobileApEnhanced) {
                    Log.d(
                            "WifiSettings.AutoHotspot",
                            "removeAutoHotspotList() , Setting wifiApBleClientRole(false)");
                    wifiApBleClientRole(false);
                    this.mListController.destroyAutoHotspot();
                }
                this.mListController.destroyMcfAutoHotspot();
                this.mListController.destroyInstantHotspot();
            }
        }
        this.mPreviousWifiState = wifiState;
    }

    public final void popOrFinishThisActivity() {
        if (getActivity() instanceof SettingsActivity) {
            ((SettingsActivity) getActivity()).finishPreferencePanel(null);
        } else {
            finish();
        }
    }

    public final void restrictUi$2() {
        if (!isUiRestrictedByOnlyAdmin()) {
            setEmptyView$1(R.string.wifi_empty_list_user_restricted);
        }
        WifiSettingsListController wifiSettingsListController = this.mListController;
        wifiSettingsListController.getClass();
        Log.d("WifiSettings.VI", "setRestrictionView");
        wifiSettingsListController.setEnableRefreshLayout(false);
        wifiSettingsListController.mEmptyView.setVisibility(0);
        wifiSettingsListController.setVisibleAvailableList(false);
    }

    public final void setEmptyView$1(int i) {
        this.mListController.updateEmptyView(false);
        TextView textView = this.mListController.mEmptyView;
        if (textView == null) {
            return;
        }
        textView.setText(i);
        textView.setLineSpacing(
                TypedValue.applyDimension(
                        0, 4.0f, this.mContext.getResources().getDisplayMetrics()),
                1.0f);
    }

    public final void setProgressBarVisible(boolean z) {
        SettingsMainSwitchBar settingsMainSwitchBar = this.mProgressHeader;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setProgressBarVisible(z);
        }
        ProgressBar progressBar = this.mExteranlProgressBar;
        if (progressBar != null) {
            progressBar.setVisibility(z ? 0 : 8);
        }
        this.mIsProgressCircleVisible = z;
    }

    public final void showAutoWifiTipPopup() {
        Log.d("WifiSettings", "showAutoWifiTipPopup");
        new Handler().post(new WifiSettings$$ExternalSyntheticLambda3(this, 2));
    }

    public final void showConnectFragmentForRetry(WifiEntry wifiEntry) {
        if (wifiEntry == null) {
            Log.e("WifiSettings", "AP is null, ignored retry popup");
            return;
        }
        if (wifiEntry.mSemFlags.isOpenRoamingNetwork) {
            Log.e("WifiSettings", "AP is OpenRoamingNetwork, ignored retry popup");
            return;
        }
        Log.d("WifiSettings", "showConnectFragmentForRetry");
        this.mSelectedWifiEntry = wifiEntry;
        launchConnectFragment(wifiEntry, true);
        this.mManualConnectingNetwork = null;
    }

    public final void showErrorDialog$1(String str) {
        new AlertDialog.Builder(this.mContext)
                .setMessage(str)
                .setCancelable(false)
                .setPositiveButton(
                        android.R.string.ok, new WifiSettings$$ExternalSyntheticLambda1())
                .create()
                .show();
    }

    public final void startMcfClientMHSDiscovery(boolean z) {
        SemWifiManager semWifiManager = this.mSemWifiManager;
        if (semWifiManager == null || !semWifiManager.isMCFClientAutohotspotSupported()) {
            return;
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                RowView$$ExternalSyntheticOutline0.m(
                        "startMcfClientAdvertise() - isEnabled: ",
                        " mAlreadyCalledMcfAutoHotspotApi : ",
                        z),
                this.mAlreadyCalledMcfAutoHotspotApi,
                "WifiSettings.MCFAutoHotspot");
        if (!z || this.mAlreadyCalledMcfAutoHotspotApi) {
            if (z || !this.mAlreadyCalledMcfAutoHotspotApi) {
                return;
            }
            this.mSemWifiManager.startMcfClientMHSDiscovery(false);
            this.mAlreadyCalledMcfAutoHotspotApi = false;
            return;
        }
        if (this.mWifiManager.getWifiState() == 3) {
            Log.d("WifiSettings.MCFAutoHotspot", "Setting startMcfClientAdvertise(true)");
            this.mAlreadyCalledMcfAutoHotspotApi = true;
            this.mSemWifiManager.startMcfClientMHSDiscovery(true);
        }
    }

    public final void updateQrScanMenuVisibility() {
        int wifiState = this.mWifiManager.getWifiState();
        if (this.mWifiQrScanMenu == null || getActivity() == null) {
            return;
        }
        Configuration taskConfiguration =
                ActivityClient.getInstance().getTaskConfiguration(getActivity().getActivityToken());
        if (taskConfiguration == null
                ? false
                : WindowConfiguration.inMultiWindowMode(
                        taskConfiguration.windowConfiguration.getWindowingMode())) {
            Log.d("WifiSettings", "multiwindow not support QR scanner");
        } else {
            if (!this.mIsDexMode) {
                if (wifiState == 3) {
                    this.mWifiQrScanMenu.setVisible(true);
                    return;
                } else {
                    if (wifiState == 1 || wifiState == 0) {
                        this.mWifiQrScanMenu.setVisible(false);
                        return;
                    }
                    return;
                }
            }
            Log.d("WifiSettings", "Dex mode not support QR");
        }
        this.mWifiQrScanMenu.setVisible(false);
    }

    public final void updateWifiEntryPreferences(boolean z) {
        View view;
        View view2;
        View view3;
        List list;
        ConnectedListAdapter.OnEventListener onEventListener;
        View view4;
        View view5;
        boolean z2;
        boolean z3;
        try {
            if (this.mIsRestricted) {
                Log.d("WifiSettings", "updateWifiEntryPreferences - UI restricted");
                if (z2) {
                    return;
                }
                if (!z3 || view == null) {
                    return;
                } else {
                    return;
                }
            }
            if (getActivity() == null) {
                if (this.mIsScanning
                        || !this.mIsProgressCircleVisible
                        || (view5 = getView()) == null) {
                    return;
                }
                view5.postDelayed(this.mHideProgressBarRunnable, 1700L);
                return;
            }
            if (this.mIgnoreUpdateOnDisabling) {
                Log.d("WifiSettings", "updateWifiEntryPreferences ignoring...");
                this.mIgnoreUpdateOnDisabling = false;
                if (this.mIsScanning
                        || !this.mIsProgressCircleVisible
                        || (view4 = getView()) == null) {
                    return;
                }
                view4.postDelayed(this.mHideProgressBarRunnable, 1700L);
                return;
            }
            int wifiState = this.mWifiManager.getWifiState();
            if (wifiState != 1 && wifiState != 0 && wifiState != 4) {
                ArrayList arrayList = new ArrayList();
                ArrayList wifiEntries = getWifiEntries();
                WifiEntry wifiEntry = this.mWifiPickerTracker.mConnectedWifiEntry;
                int size = wifiEntries.size();
                ArrayList arrayList2 = new ArrayList();
                if (size == 0) {
                    Log.d("WifiSettings", "No available networks");
                    this.mListController.mListAdapter.removeAll();
                }
                boolean configureCurrentNetwork = configureCurrentNetwork(wifiEntry);
                if (configureCurrentNetwork
                        && (getActivity() instanceof WifiSetupWizardActivityVzw)
                        && (onEventListener = this.mListener) != null) {
                    onEventListener.onItemClicked(wifiEntry);
                }
                Log.d(
                        "WifiSettings",
                        "updateWifiEntryPreferences size:"
                                + size
                                + " connected:"
                                + configureCurrentNetwork
                                + " scan:"
                                + z);
                if (Settings.Global.getInt(this.mContext.getContentResolver(), "safe_wifi", 0) == 0
                        && !this.mInSetupWizardActivity
                        && !this.mInPickerDialog) {
                    WifiPickerTracker wifiPickerTracker = this.mWifiPickerTracker;
                    synchronized (wifiPickerTracker.mLockEasySetup) {
                        list =
                                (List)
                                        wifiPickerTracker.mEasySetupEntries.stream()
                                                .limit(5L)
                                                .collect(Collectors.toList());
                    }
                    configureEasySetupNetwork(list);
                }
                for (int i = 0; i < size; i++) {
                    WifiEntry wifiEntry2 = (WifiEntry) wifiEntries.get(i);
                    if (wifiEntry2 instanceof HotspotNetworkEntry) {
                        arrayList.add((HotspotNetworkEntry) wifiEntry2);
                    } else if (wifiEntry2.getLevel() != -1) {
                        arrayList2.add(wifiEntry2);
                    }
                }
                this.mListController.updateWithAnimation(wifiEntry, arrayList2, z);
                WifiLoadingControllerListener wifiLoadingControllerListener = this.mLoadingListener;
                if (wifiLoadingControllerListener != null
                        && ((WifiSetupWizardActivity) wifiLoadingControllerListener).mIsLoading
                        && !this.mIsFirstLoaded
                        && (z || size > 0)) {
                    WifiSetupWizardActivity wifiSetupWizardActivity =
                            (WifiSetupWizardActivity) wifiLoadingControllerListener;
                    LoadingViewController loadingViewController =
                            wifiSetupWizardActivity.mLoadingViewController;
                    if (loadingViewController != null && wifiSetupWizardActivity.mIsLoading) {
                        loadingViewController.showContent(false);
                        wifiSetupWizardActivity.mIsLoading = false;
                    }
                    this.mIsFirstLoaded = true;
                }
                addInstantHotspotPreferenceCategory(arrayList);
                autoHotspotPreferenceCategory();
                addMcfAutoHotspotPreference();
                if (this.mScrollTimer == 0 || this.mScrollCounter >= 2) {
                    this.mScrollTimer = 0L;
                    this.mScrollCounter = 0;
                } else {
                    forceScrollToTopOfList();
                }
                if (z) {
                    setProgressBarVisible(false);
                }
                if (this.mIsScanning
                        || !this.mIsProgressCircleVisible
                        || (view3 = getView()) == null) {
                    return;
                }
                view3.postDelayed(this.mHideProgressBarRunnable, 1700L);
                return;
            }
            Log.d(
                    "WifiSettings",
                    "updateWifiEntryPreferences - Wi-Fi state is disabling/disabled " + wifiState);
            if (this.mIsScanning || !this.mIsProgressCircleVisible || (view2 = getView()) == null) {
                return;
            }
            view2.postDelayed(this.mHideProgressBarRunnable, 1700L);
        } finally {
            if (!this.mIsScanning && this.mIsProgressCircleVisible && (view = getView()) != null) {
                view.postDelayed(this.mHideProgressBarRunnable, 1700L);
            }
        }
    }

    public final void updateWifiEntryWithCurrentConfiguration(WifiConfiguration wifiConfiguration) {
        WifiConfiguration wifiConfiguration2 = this.mManualConnectingNetwork.getWifiConfiguration();
        int i = wifiConfiguration2 == null ? -1 : wifiConfiguration2.networkId;
        int i2 = this.mConnectingNetworkId;
        if (i == i2) {
            if (wifiConfiguration == null) {
                wifiConfiguration = getWifiConfiguration(i2);
            }
            if (wifiConfiguration != null) {
                this.mManualConnectingNetwork.semUpdateConfig(wifiConfiguration);
            }
        }
    }

    public final void wifiApBleClientRole(boolean z) {
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                RowView$$ExternalSyntheticOutline0.m(
                        "wifiApBleClientRole() - isEnabled: ",
                        " mAlreadyCalledAutoHotspotApi : ",
                        z),
                this.mAlreadyCalledAutoHotspotApi,
                "WifiSettings.AutoHotspot");
        if (Utils.SPF_SupportMobileApEnhanced) {
            if (!z || this.mAlreadyCalledAutoHotspotApi) {
                if (z || !this.mAlreadyCalledAutoHotspotApi) {
                    return;
                }
                this.mSemWifiManager.wifiApBleClientRole(false);
                this.mAlreadyCalledAutoHotspotApi = false;
                return;
            }
            if (this.mWifiManager.getWifiState() == 3) {
                Log.d("WifiSettings.AutoHotspot", "Setting wifiApBleClientRole(true)");
                this.mAlreadyCalledAutoHotspotApi = true;
                this.mSemWifiManager.wifiApBleClientRole(true);
            }
        }
    }

    public final void connect(WifiEntry wifiEntry) {
        WifiConfiguration wifiConfiguration = wifiEntry.getWifiConfiguration();
        if (wifiConfiguration != null) {
            SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
            WifiEnterpriseConfig wifiEnterpriseConfig = wifiConfiguration.enterpriseConfig;
            if (wifiEnterpriseConfig != null
                    && wifiEnterpriseConfig.isAuthenticationSimBased()
                    && !SemWifiUtils.isSimCheck(this.mContext)) {
                showErrorDialog$1(this.mContext.getString(R.string.wifi_no_usim_warning));
                return;
            }
        }
        this.mMetricsFeatureProvider.action(getActivity(), 135, wifiEntry.isSaved());
        if (!SemWifiEntryFlags.isWepAllowed(this.mContext)
                && wifiEntry.getSecurityTypes().contains(1)) {
            WifiWarningDialogController wifiWarningDialogController = this.mWarningDialogController;
            String ssid = wifiEntry.getSsid();
            wifiWarningDialogController.getClass();
            Intent wifiWarningIntent = WifiWarningDialogController.getWifiWarningIntent();
            wifiWarningIntent.putExtra("req_type", 0);
            wifiWarningIntent.putExtra("extra_type", 9);
            wifiWarningIntent.putExtra("ssid", ssid);
            try {
                wifiWarningDialogController.mContext.startActivity(wifiWarningIntent);
                return;
            } catch (ActivityNotFoundException unused) {
                return;
            }
        }
        wifiEntry.connect(new WifiEntryConnectCallback(wifiEntry));
        WifiConfiguration wifiConfiguration2 = wifiEntry.getWifiConfiguration();
        if (this.mManualConnectingNetwork != null && wifiConfiguration2 != null) {
            this.mConnectingNetworkId = wifiConfiguration2.networkId;
        }
        this.mScrollTimer = SystemClock.currentThreadTimeMillis();
        if (wifiConfiguration2 != null) {
            this.mSemWifiManager.setConnectionAttemptInfo(
                    wifiConfiguration2.networkId, true, wifiConfiguration2.getKey());
        } else {
            this.mSemWifiManager.setConnectionAttemptInfo(-1, true);
        }
    }

    public final void onWifiEntriesChanged(boolean z) {
        if (isFinishingOrDestroyed()) {
            return;
        }
        updateWifiEntryPreferences(z);
        changeNextButtonState(this.mWifiPickerTracker.mConnectedWifiEntry != null);
        String str = this.mOpenSsid;
        if (str != null) {
            WifiEntry wifiEntry = this.mWifiPickerTracker.mConnectedWifiEntry;
            if (wifiEntry != null
                    && TextUtils.equals(str, wifiEntry.getSsid())
                    && wifiEntry.canSignIn()) {
                wifiEntry.signIn();
                this.mOpenSsid = null;
            } else {
                if (this.mOpenSsid.contains("wifi_start_openroaming")) {
                    this.mOpenSsid = null;
                    launchOpenRoamingFragment();
                    return;
                }
                Optional findFirst =
                        this.mWifiPickerTracker.getWifiEntries().stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.settings.wifi.WifiSettings$$ExternalSyntheticLambda6
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return TextUtils.equals(
                                                        WifiSettings.this.mOpenSsid,
                                                        ((WifiEntry) obj).getSsid());
                                            }
                                        })
                                .filter(new WifiSettings$$ExternalSyntheticLambda0(1))
                                .filter(new WifiSettings$$ExternalSyntheticLambda0(2))
                                .findFirst();
                if (findFirst.isPresent()) {
                    this.mOpenSsid = null;
                    onWifiEntryPreferenceClick((WifiEntry) findFirst.get());
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.WifiSettings$2, reason: invalid class name */
    public final class AnonymousClass2 implements WifiManager.ActionListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ WifiSettings this$0;

        public /* synthetic */ AnonymousClass2(WifiSettings wifiSettings, int i) {
            this.$r8$classId = i;
            this.this$0 = wifiSettings;
        }

        public final void onFailure(int i) {
            switch (this.$r8$classId) {
                case 0:
                    FragmentActivity activity = this.this$0.getActivity();
                    if (activity != null) {
                        Toast.makeText(activity, R.string.wifi_failed_save_message, 0).show();
                        break;
                    }
                    break;
                default:
                    WifiSettings wifiSettings = this.this$0;
                    boolean z = WifiSettings.mWifiSettingsForeground;
                    if (!wifiSettings.isFinishingOrDestroyed()) {
                        Toast.makeText(
                                        this.this$0.getContext(),
                                        R.string.wifi_failed_connect_message,
                                        0)
                                .show();
                        break;
                    }
                    break;
            }
        }

        public final void onSuccess() {
            switch (this.$r8$classId) {
                case 0:
                    break;
                default:
                    WifiSettings wifiSettings = this.this$0;
                    boolean z = WifiSettings.mWifiSettingsForeground;
                    wifiSettings.getClass();
                    break;
            }
        }

        private final void onSuccess$com$android$settings$wifi$WifiSettings$2() {}
    }
}
