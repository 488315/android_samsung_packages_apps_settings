package com.android.settings.network;

import android.R;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.NetworkTemplate;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.AirplaneModeEnabler;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.RestrictedSettingsFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.datausage.DataUsagePreference;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.location.WifiScanningFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.wifi.AddNetworkFragment;
import com.android.settings.wifi.AddWifiNetworkPreference;
import com.android.settings.wifi.ConfigureWifiEntryFragment;
import com.android.settings.wifi.ConnectedWifiEntryPreference;
import com.android.settings.wifi.LongPressWifiEntryPreference;
import com.android.settings.wifi.WifiDialog2;
import com.android.settings.wifi.WifiPickerTrackerHelper;
import com.android.settings.wifi.details.WifiNetworkDetailsFragment;
import com.android.settings.wifi.dpp.WifiDppConfiguratorActivity;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.settingslib.HelpUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.WirelessUtils;
import com.android.settingslib.connectivity.ConnectivitySubsystemsRecoveryManager;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.utils.StringUtil;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.FooterPreference;
import com.android.settingslib.widget.LayoutPreference;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;
import com.android.settingslib.wifi.WifiSavedConfigUtils;
import com.android.settingslib.wifi.WifiUtils;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;

import com.sec.ims.volte2.data.VolteConstants;

import kotlin.jvm.functions.Function0;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NetworkProviderSettings extends RestrictedSettingsFragment
        implements WifiPickerTracker.WifiPickerTrackerCallback,
                WifiDialog2.WifiDialog2Listener,
                DialogInterface.OnDismissListener,
                AirplaneModeEnabler.OnAirplaneModeChangedListener,
                InternetUpdater.InternetChangeListener {
    static final int ADD_NETWORK_REQUEST = 2;
    static final int MENU_ID_DISCONNECT = 3;
    static final int MENU_ID_FORGET = 4;
    static final String PREF_KEY_ADD_WIFI_NETWORK = "add_wifi_network";
    static final String PREF_KEY_CONNECTED_ACCESS_POINTS = "connected_access_point";
    static final String PREF_KEY_DATA_USAGE = "non_carrier_data_usage";
    static final String PREF_KEY_FIRST_ACCESS_POINTS = "first_access_points";
    static final String PREF_KEY_WIFI_TOGGLE = "main_toggle_wifi";
    public static final SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new SearchIndexProvider();
    AddWifiNetworkPreference mAddWifiNetworkPreference;
    AirplaneModeEnabler mAirplaneModeEnabler;
    Preference mAirplaneModeMsgPreference;
    public boolean mClickedConnect;
    Preference mConfigureWifiSettingsPreference;
    ConnectedEthernetNetworkController mConnectedEthernetNetworkController;
    PreferenceCategory mConnectedWifiEntryPreferenceCategory;
    DataUsagePreference mDataUsagePreference;
    public WifiDialog2 mDialog;
    public int mDialogMode;
    public WifiEntry mDialogWifiEntry;
    public String mDialogWifiEntryKey;
    public boolean mEnableNextOnConnection;
    PreferenceCategory mFirstWifiEntryPreferenceCategory;
    final Runnable mHideProgressBarRunnable;
    public InternetResetHelper mInternetResetHelper;
    InternetUpdater mInternetUpdater;
    boolean mIsAdmin;
    boolean mIsGuest;
    public boolean mIsRestricted;
    public boolean mIsViewLoading;
    MenuProvider mMenuProvider;
    public NetworkMobileProviderController mNetworkMobileProviderController;
    public String mOpenSsid;
    final Runnable mRemoveLoadingRunnable;
    LayoutPreference mResetInternetPreference;
    public AnonymousClass2 mSaveListener;
    Preference mSavedNetworksPreference;
    public WifiEntry mSelectedWifiEntry;
    final Runnable mUpdateWifiEntryPreferencesRunnable;
    PreferenceCategory mWifiEntryPreferenceCategory;
    public WifiManager mWifiManager;
    WifiPickerTracker mWifiPickerTracker;
    FooterPreference mWifiStatusMessagePreference;
    public WifiSwitchPreferenceController mWifiSwitchPreferenceController;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FirstWifiEntryPreference extends ConnectedWifiEntryPreference {
        @Override // com.android.settings.wifi.WifiEntryPreference
        public final int getIconColorAttr() {
            return R.attr.colorControlNormal;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiEntryConnectCallback implements WifiEntry.ConnectCallback {
        public final WifiEntry mConnectWifiEntry;
        public final boolean mEditIfNoConfig;
        public final boolean mFullScreenEdit;

        public WifiEntryConnectCallback(WifiEntry wifiEntry, boolean z, boolean z2) {
            this.mConnectWifiEntry = wifiEntry;
            this.mEditIfNoConfig = z;
            this.mFullScreenEdit = z2;
        }

        @Override // com.android.wifitrackerlib.WifiEntry.ConnectCallback
        public final void onConnectResult(int i) {
            SearchIndexProvider searchIndexProvider =
                    NetworkProviderSettings.SEARCH_INDEX_DATA_PROVIDER;
            NetworkProviderSettings networkProviderSettings = NetworkProviderSettings.this;
            if (networkProviderSettings.isFinishingOrDestroyed()) {
                return;
            }
            if (i == 0) {
                networkProviderSettings.mClickedConnect = true;
                return;
            }
            if (i != 1) {
                if (i == 2) {
                    Toast.makeText(
                                    networkProviderSettings.getContext(),
                                    com.android.settings.R.string.wifi_failed_connect_message,
                                    0)
                            .show();
                }
            } else if (this.mEditIfNoConfig) {
                boolean z = this.mFullScreenEdit;
                WifiEntry wifiEntry = this.mConnectWifiEntry;
                if (z) {
                    networkProviderSettings.launchConfigNewNetworkFragment(wifiEntry);
                } else {
                    networkProviderSettings.showDialog(wifiEntry, 1);
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class WifiRestriction {}

    public static void $r8$lambda$7OlqpiUmxFkDn9fJ9dGTdauOVvw(
            NetworkProviderSettings networkProviderSettings) {
        WifiEntry wifiEntry = networkProviderSettings.mSelectedWifiEntry;
        Context context = networkProviderSettings.getContext();
        WifiManager wifiManager = networkProviderSettings.mWifiManager;
        Duration duration = WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION;
        Intent intent = new Intent(context, (Class<?>) WifiDppConfiguratorActivity.class);
        if (wifiEntry.canShare()) {
            intent.setAction("android.settings.WIFI_DPP_CONFIGURATOR_QR_CODE_GENERATOR");
            WifiDppUtils.setConfiguratorIntentExtra(
                    intent, wifiManager, wifiEntry.getWifiConfiguration());
        } else {
            intent = null;
        }
        if (intent == null) {
            Log.e(
                    "NetworkProviderSettings",
                    "Launch Wi-Fi DPP QR code generator with a wrong Wi-Fi network!");
        } else {
            networkProviderSettings.mMetricsFeatureProvider.action(
                    0, 1710, 1595, Integer.MIN_VALUE, null);
            networkProviderSettings.startActivity(intent);
        }
    }

    /* renamed from: -$$Nest$mfixConnectivity, reason: not valid java name */
    public static void m956$$Nest$mfixConnectivity(
            NetworkProviderSettings networkProviderSettings) {
        WifiManager wifiManager;
        WifiManager wifiManager2;
        if (networkProviderSettings.mIsGuest) {
            Log.e("NetworkProviderSettings", "Can't reset network because the user is a guest.");
            EventLog.writeEvent(
                    1397638484,
                    "252995826",
                    Integer.valueOf(UserHandle.myUserId()),
                    "User is a guest");
            return;
        }
        InternetResetHelper internetResetHelper = networkProviderSettings.mInternetResetHelper;
        internetResetHelper.mRecoveryWorker.getClass();
        ConnectivitySubsystemsRecoveryManager connectivitySubsystemsRecoveryManager =
                InternetResetHelper.RecoveryWorker.sRecoveryManager;
        boolean z = false;
        if (!(Settings.Global.getInt(
                                connectivitySubsystemsRecoveryManager.mContext.getContentResolver(),
                                "airplane_mode_on",
                                0)
                        != 1
                || ((wifiManager2 = connectivitySubsystemsRecoveryManager.mWifiManager) != null
                        && (wifiManager2.isWifiEnabled()
                                || connectivitySubsystemsRecoveryManager.mWifiManager
                                        .isWifiApEnabled())))) {
            Log.e("InternetResetHelper", "The connectivity subsystem is not available to restart.");
            return;
        }
        internetResetHelper.showResettingAndSendTimeoutChecks();
        internetResetHelper.mIsWifiReady = !internetResetHelper.mWifiManager.isWifiEnabled();
        ConnectivitySubsystemsRecoveryManager connectivitySubsystemsRecoveryManager2 =
                InternetResetHelper.RecoveryWorker.sRecoveryManager;
        if (Settings.Global.getInt(
                                connectivitySubsystemsRecoveryManager2.mContext
                                        .getContentResolver(),
                                "airplane_mode_on",
                                0)
                        != 1
                || ((wifiManager = connectivitySubsystemsRecoveryManager2.mWifiManager) != null
                        && (wifiManager.isWifiEnabled()
                                || connectivitySubsystemsRecoveryManager2.mWifiManager
                                        .isWifiApEnabled()))) {
            z = true;
        }
        if (!z) {
            Log.e("RecoveryWorker", "The connectivity subsystem is not available to restart.");
            return;
        }
        InternetResetHelper.RecoveryWorker.sIsRecovering = true;
        final ConnectivitySubsystemsRecoveryManager connectivitySubsystemsRecoveryManager3 =
                InternetResetHelper.RecoveryWorker.sRecoveryManager;
        final InternetResetHelper.RecoveryWorker recoveryWorker =
                InternetResetHelper.RecoveryWorker.sInstance;
        connectivitySubsystemsRecoveryManager3.mHandler.post(
                new Runnable() { // from class:
                    // com.android.settingslib.connectivity.ConnectivitySubsystemsRecoveryManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        boolean z2;
                        final ConnectivitySubsystemsRecoveryManager
                                connectivitySubsystemsRecoveryManager4 =
                                        ConnectivitySubsystemsRecoveryManager.this;
                        InternetResetHelper.RecoveryWorker recoveryWorker2 = recoveryWorker;
                        if (connectivitySubsystemsRecoveryManager4.mWifiRestartInProgress) {
                            Log.e(
                                    "ConnectivitySubsystemsRecoveryManager",
                                    "Wifi restart still in progress");
                            return;
                        }
                        if (connectivitySubsystemsRecoveryManager4.mTelephonyRestartInProgress) {
                            Log.e(
                                    "ConnectivitySubsystemsRecoveryManager",
                                    "Telephony restart still in progress");
                            return;
                        }
                        WifiManager wifiManager3 =
                                connectivitySubsystemsRecoveryManager4.mWifiManager;
                        if (wifiManager3 != null
                                && (wifiManager3.isWifiEnabled()
                                        || connectivitySubsystemsRecoveryManager4.mWifiManager
                                                .isWifiApEnabled())) {
                            connectivitySubsystemsRecoveryManager4.mWifiManager
                                    .restartWifiSubsystem();
                            connectivitySubsystemsRecoveryManager4.mWifiRestartInProgress = true;
                            connectivitySubsystemsRecoveryManager4.startTrackingWifiRestart();
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (connectivitySubsystemsRecoveryManager4.mTelephonyManager != null
                                && Settings.Global.getInt(
                                                connectivitySubsystemsRecoveryManager4.mContext
                                                        .getContentResolver(),
                                                "airplane_mode_on",
                                                0)
                                        != 1
                                && connectivitySubsystemsRecoveryManager4.mTelephonyManager
                                        .rebootRadio()) {
                            connectivitySubsystemsRecoveryManager4.mTelephonyRestartInProgress =
                                    true;
                            connectivitySubsystemsRecoveryManager4.startTrackingTelephonyRestart();
                            z2 = true;
                        }
                        if (z2) {
                            connectivitySubsystemsRecoveryManager4.mCurrentRecoveryCallback =
                                    recoveryWorker2;
                            recoveryWorker2.getClass();
                            Log.d(
                                    "RecoveryWorker",
                                    "The connectivity subsystem is starting for recovery.");
                            InternetResetHelper.RecoveryWorker.sIsRecovering = true;
                            connectivitySubsystemsRecoveryManager4.mHandler.postDelayed(
                                    new Runnable() { // from class:
                                        // com.android.settingslib.connectivity.ConnectivitySubsystemsRecoveryManager$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            ConnectivitySubsystemsRecoveryManager
                                                    connectivitySubsystemsRecoveryManager5 =
                                                            ConnectivitySubsystemsRecoveryManager
                                                                    .this;
                                            connectivitySubsystemsRecoveryManager5
                                                    .stopTrackingWifiRestart();
                                            connectivitySubsystemsRecoveryManager5
                                                    .stopTrackingTelephonyRestart();
                                            connectivitySubsystemsRecoveryManager5
                                                            .mWifiRestartInProgress =
                                                    false;
                                            connectivitySubsystemsRecoveryManager5
                                                            .mTelephonyRestartInProgress =
                                                    false;
                                            if (connectivitySubsystemsRecoveryManager5
                                                            .mCurrentRecoveryCallback
                                                    != null) {
                                                Log.d(
                                                        "RecoveryWorker",
                                                        "The connectivity subsystem is done for"
                                                            + " recovery.");
                                                InternetResetHelper.RecoveryWorker.sIsRecovering =
                                                        false;
                                                InternetResetHelper internetResetHelper2 =
                                                        (InternetResetHelper)
                                                                InternetResetHelper.RecoveryWorker
                                                                        .sCallback
                                                                        .get();
                                                if (internetResetHelper2 != null) {
                                                    internetResetHelper2.resumePreferences();
                                                }
                                                connectivitySubsystemsRecoveryManager5
                                                                .mCurrentRecoveryCallback =
                                                        null;
                                            }
                                        }
                                    },
                                    15000L);
                        }
                    }
                });
        Log.d("RecoveryWorker", "The connectivity subsystem is restarting for recovery.");
    }

    public NetworkProviderSettings() {
        super("no_config_wifi");
        this.mRemoveLoadingRunnable =
                new NetworkProviderSettings$$ExternalSyntheticLambda1(this, 3);
        this.mUpdateWifiEntryPreferencesRunnable =
                new NetworkProviderSettings$$ExternalSyntheticLambda1(this, 1);
        this.mHideProgressBarRunnable =
                new NetworkProviderSettings$$ExternalSyntheticLambda1(this, 2);
        this.mIsAdmin = true;
        this.mIsGuest = false;
    }

    public void addForgetMenuIfSuitable(ContextMenu contextMenu) {
        if (this.mIsAdmin) {
            contextMenu.add(0, 4, 0, com.android.settings.R.string.forget);
        }
    }

    public void addModifyMenuIfSuitable(ContextMenu contextMenu, WifiEntry wifiEntry) {
        if (this.mIsAdmin && wifiEntry.isSaved() && wifiEntry.getConnectedState() != 2) {
            contextMenu.add(0, 5, 0, com.android.settings.R.string.wifi_modify);
        }
    }

    public final void addPreferences$1() {
        addPreferencesFromResource(com.android.settings.R.xml.network_provider_settings);
        this.mAirplaneModeMsgPreference = findPreference("airplane_mode_message");
        boolean isAirplaneModeOn =
                WirelessUtils.isAirplaneModeOn(this.mAirplaneModeEnabler.mContext);
        Preference preference = this.mAirplaneModeMsgPreference;
        if (preference != null) {
            preference.setVisible(isAirplaneModeOn);
        }
        this.mConnectedWifiEntryPreferenceCategory =
                (PreferenceCategory) findPreference(PREF_KEY_CONNECTED_ACCESS_POINTS);
        this.mFirstWifiEntryPreferenceCategory =
                (PreferenceCategory) findPreference(PREF_KEY_FIRST_ACCESS_POINTS);
        this.mWifiEntryPreferenceCategory = (PreferenceCategory) findPreference("access_points");
        this.mConfigureWifiSettingsPreference = findPreference("configure_network_settings");
        this.mSavedNetworksPreference = findPreference("saved_networks");
        AddWifiNetworkPreference addWifiNetworkPreference =
                (AddWifiNetworkPreference) findPreference(PREF_KEY_ADD_WIFI_NETWORK);
        this.mAddWifiNetworkPreference = addWifiNetworkPreference;
        this.mWifiEntryPreferenceCategory.removePreference(addWifiNetworkPreference);
        DataUsagePreference dataUsagePreference =
                (DataUsagePreference) findPreference(PREF_KEY_DATA_USAGE);
        this.mDataUsagePreference = dataUsagePreference;
        dataUsagePreference.setVisible(DataUsageUtils.hasWifiRadio(getContext()));
        this.mDataUsagePreference.setTemplate(new NetworkTemplate.Builder(4).build(), 0);
        LayoutPreference layoutPreference =
                (LayoutPreference) findPreference("resetting_your_internet");
        this.mResetInternetPreference = layoutPreference;
        if (layoutPreference != null) {
            layoutPreference.setVisible(false);
        }
        if (showAnySubscriptionInfo(getContext())) {
            if (this.mNetworkMobileProviderController == null) {
                this.mNetworkMobileProviderController =
                        new NetworkMobileProviderController(
                                getContext(),
                                NetworkMobileProviderController.PREF_KEY_PROVIDER_MOBILE_NETWORK);
            }
            this.mNetworkMobileProviderController.init(getSettingsLifecycle());
            this.mNetworkMobileProviderController.displayPreference(getPreferenceScreen());
        }
        if (this.mConnectedEthernetNetworkController == null) {
            Context context = getContext();
            Lifecycle settingsLifecycle = getSettingsLifecycle();
            ConnectedEthernetNetworkController connectedEthernetNetworkController =
                    new ConnectedEthernetNetworkController(context);
            connectedEthernetNetworkController.mInternetType =
                    new InternetUpdater(
                                    context, settingsLifecycle, connectedEthernetNetworkController)
                            .mInternetType;
            this.mConnectedEthernetNetworkController = connectedEthernetNetworkController;
        }
        this.mConnectedEthernetNetworkController.displayPreference(getPreferenceScreen());
        if (hasWifiManager$1()) {
            if (this.mWifiSwitchPreferenceController == null) {
                Context context2 = getContext();
                Lifecycle settingsLifecycle2 = getSettingsLifecycle();
                WifiSwitchPreferenceController wifiSwitchPreferenceController =
                        new WifiSwitchPreferenceController(context2);
                if (settingsLifecycle2 == null) {
                    throw new IllegalArgumentException("Lifecycle must be set");
                }
                settingsLifecycle2.addObserver(wifiSwitchPreferenceController);
                wifiSwitchPreferenceController.mIsChangeWifiStateAllowed =
                        WifiEnterpriseRestrictionUtils.isChangeWifiStateAllowed(context2);
                this.mWifiSwitchPreferenceController = wifiSwitchPreferenceController;
            }
            this.mWifiSwitchPreferenceController.displayPreference(getPreferenceScreen());
        }
        this.mWifiStatusMessagePreference =
                (FooterPreference) findPreference("wifi_status_message_footer");
        InternetResetHelper internetResetHelper =
                new InternetResetHelper(
                        getContext(),
                        getLifecycle(),
                        this.mNetworkMobileProviderController,
                        findPreference(PREF_KEY_WIFI_TOGGLE),
                        this.mConnectedWifiEntryPreferenceCategory,
                        this.mFirstWifiEntryPreferenceCategory,
                        this.mWifiEntryPreferenceCategory,
                        this.mResetInternetPreference);
        this.mInternetResetHelper = internetResetHelper;
        internetResetHelper.mRecoveryWorker.getClass();
        if (InternetResetHelper.RecoveryWorker.sIsRecovering) {
            internetResetHelper.mIsWifiReady = false;
            internetResetHelper.showResettingAndSendTimeoutChecks();
        }
    }

    public void addShareMenuIfSuitable(ContextMenu contextMenu) {
        if (this.mIsAdmin) {
            contextMenu.add(0, 7, 0, com.android.settings.R.string.share);
        } else {
            Log.w(
                    "NetworkProviderSettings",
                    "Don't add the Wi-Fi share menu because the user is not an admin.");
            EventLog.writeEvent(1397638484, "206986392", -1, "User is not an admin");
        }
    }

    public void changeNextButtonState(boolean z) {
        if (this.mEnableNextOnConnection && hasNextButton()) {
            getNextButton().setEnabled(z);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settings.network.NetworkProviderSettings$$ExternalSyntheticLambda5] */
    public void connect(final WifiEntry wifiEntry, boolean z, boolean z2) {
        this.mMetricsFeatureProvider.action(getActivity(), 135, wifiEntry.isSaved());
        final WifiEntryConnectCallback wifiEntryConnectCallback =
                new WifiEntryConnectCallback(wifiEntry, z, z2);
        if (wifiEntry.getSecurityTypes().contains(1)) {
            WifiUtils.checkWepAllowed(
                    getContext(),
                    getViewLifecycleOwner(),
                    wifiEntry.getSsid(),
                    new Function0() { // from class:
                                      // com.android.settings.network.NetworkProviderSettings$$ExternalSyntheticLambda5
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            NetworkProviderSettings.SearchIndexProvider searchIndexProvider =
                                    NetworkProviderSettings.SEARCH_INDEX_DATA_PROVIDER;
                            WifiEntry.this.connect(wifiEntryConnectCallback);
                            return null;
                        }
                    });
        } else {
            wifiEntry.connect(wifiEntryConnectCallback);
        }
    }

    public ConnectedWifiEntryPreference createConnectedWifiEntryPreference(WifiEntry wifiEntry) {
        return this.mInternetUpdater.mInternetType == 2
                ? new ConnectedWifiEntryPreference(getPrefContext(), this, wifiEntry)
                : new FirstWifiEntryPreference(getPrefContext(), this, wifiEntry);
    }

    public LongPressWifiEntryPreference createLongPressWifiEntryPreference(WifiEntry wifiEntry) {
        return new LongPressWifiEntryPreference(getPrefContext(), this, wifiEntry);
    }

    public PreferenceCategory getConnectedWifiPreferenceCategory() {
        if (this.mInternetUpdater.mInternetType == 2) {
            this.mFirstWifiEntryPreferenceCategory.setVisible(false);
            this.mFirstWifiEntryPreferenceCategory.removeAll();
            return this.mConnectedWifiEntryPreferenceCategory;
        }
        this.mConnectedWifiEntryPreferenceCategory.setVisible(false);
        this.mConnectedWifiEntryPreferenceCategory.removeAll();
        return this.mFirstWifiEntryPreferenceCategory;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        if (i != 1) {
            return 0;
        }
        return VolteConstants.ErrorCode.DECLINE;
    }

    public Intent getHelpIntent(Context context, String str) {
        return HelpUtils.getHelpIntent(context, str, context.getClass().getName());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 103;
    }

    public void handleAddNetworkRequest(int i, Intent intent) {
        WifiConfiguration wifiConfiguration;
        if (i == -1
                && (wifiConfiguration =
                                (WifiConfiguration) intent.getParcelableExtra("wifi_config_key"))
                        != null
                && hasWifiManager$1()) {
            this.mWifiManager.save(wifiConfiguration, this.mSaveListener);
        }
    }

    public final boolean hasWifiManager$1() {
        if (this.mWifiManager != null) {
            return true;
        }
        Context context = getContext();
        if (context == null) {
            return false;
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mWifiManager = wifiManager;
        return wifiManager != null;
    }

    public boolean isPhoneOnCall() {
        return ((TelephonyManager) getActivity().getSystemService(TelephonyManager.class))
                        .getCallState()
                != 0;
    }

    public void launchConfigNewNetworkFragment(WifiEntry wifiEntry) {
        if (this.mIsRestricted) {
            Log.e(
                    "NetworkProviderSettings",
                    "Can't configure Wi-Fi because NetworkProviderSettings is restricted.");
            EventLog.writeEvent(1397638484, "246301667", -1, "Fragment is restricted.");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("key_chosen_wifientry_key", wifiEntry.getKey());
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String title = wifiEntry.getTitle();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mTitle = title;
        launchRequest.mDestinationName = ConfigureWifiEntryFragment.class.getName();
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 103;
        subSettingLauncher.setResultListener(this, 3);
        subSettingLauncher.launch();
    }

    public void launchNetworkDetailsFragment(
            LongPressWifiEntryPreference longPressWifiEntryPreference) {
        WifiEntry wifiEntry = longPressWifiEntryPreference.mWifiEntry;
        Context requireContext = requireContext();
        Bundle bundle = new Bundle();
        bundle.putString("key_chosen_wifientry_key", wifiEntry.getKey());
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(requireContext);
        CharSequence text =
                requireContext.getText(com.android.settings.R.string.pref_title_network_details);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mTitle = text;
        launchRequest.mDestinationName = WifiNetworkDetailsFragment.class.getName();
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 103;
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (hasWifiManager$1()) {
            this.mWifiPickerTracker =
                    new WifiPickerTrackerHelper(getSettingsLifecycle(), getContext(), this)
                            .mWifiPickerTracker;
        }
        this.mInternetUpdater = new InternetUpdater(getContext(), getSettingsLifecycle(), this);
        this.mSaveListener = new AnonymousClass2(this, 0);
        if (bundle != null) {
            this.mDialogMode = bundle.getInt("dialog_mode");
            this.mDialogWifiEntryKey = bundle.getString("wifi_ap_key");
        }
        Intent intent = getActivity().getIntent();
        this.mEnableNextOnConnection = intent.getBooleanExtra("wifi_enable_next_on_connect", false);
        if (intent.hasExtra("wifi_start_connect_ssid")) {
            this.mOpenSsid = intent.getStringExtra("wifi_start_connect_ssid");
        }
        requireActivity().addMenuProvider(this.mMenuProvider);
    }

    @Override // com.android.settings.RestrictedSettingsFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        WifiConfiguration wifiConfiguration;
        WifiDialog2 wifiDialog2;
        super.onActivityResult(i, i2, intent);
        if (hasWifiManager$1()) {
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
            if (i == 3) {
                if (i2 != -1
                        || (wifiConfiguration =
                                        (WifiConfiguration)
                                                intent.getParcelableExtra("network_config_key"))
                                == null) {
                    return;
                }
                this.mWifiManager.connect(wifiConfiguration, new AnonymousClass2(this, 1));
                return;
            }
            if (i == 4) {
                return;
            }
        }
        boolean z = this.mIsRestricted;
        boolean isUiRestricted = isUiRestricted();
        this.mIsRestricted = isUiRestricted;
        if (z && !isUiRestricted && getPreferenceScreen().getPreferenceCount() == 0) {
            addPreferences$1();
        }
    }

    @Override // com.android.settings.AirplaneModeEnabler.OnAirplaneModeChangedListener
    public final void onAirplaneModeChanged(boolean z) {
        Preference preference = this.mAirplaneModeMsgPreference;
        if (preference != null) {
            preference.setVisible(z);
        }
        if (isAdded()) {
            requireActivity().invalidateOptionsMenu();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final boolean onContextItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 2) {
            connect(this.mSelectedWifiEntry, true, false);
            return true;
        }
        if (itemId == 3) {
            this.mSelectedWifiEntry.disconnect(null);
            return true;
        }
        if (itemId == 4) {
            WifiEntry wifiEntry = this.mSelectedWifiEntry;
            this.mMetricsFeatureProvider.action(getActivity(), 137, new Pair[0]);
            wifiEntry.forget(null);
            return true;
        }
        if (itemId != 5) {
            if (itemId != 7) {
                return super.onContextItemSelected(menuItem);
            }
            WifiDppUtils.showLockScreen(
                    getContext(), new NetworkProviderSettings$$ExternalSyntheticLambda1(this, 4));
            return true;
        }
        if (this.mIsAdmin) {
            showDialog(this.mSelectedWifiEntry, 2);
            return true;
        }
        Log.e("NetworkProviderSettings", "Can't modify Wi-Fi because the user isn't admin.");
        EventLog.writeEvent(
                1397638484,
                "237672190",
                Integer.valueOf(UserHandle.myUserId()),
                "User isn't admin");
        return true;
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        if (context != null
                && !context.getResources()
                        .getBoolean(com.android.settings.R.bool.config_show_internet_settings)) {
            finish();
            return;
        }
        this.mAirplaneModeEnabler = new AirplaneModeEnabler(getContext(), this);
        setAnimationAllowed(false);
        addPreferences$1();
        this.mIsRestricted = isUiRestricted();
        UserManager userManager = (UserManager) getSystemService(UserManager.class);
        if (userManager != null) {
            this.mIsAdmin = userManager.isAdminUser();
            this.mIsGuest = userManager.isGuestUser();
        }
        this.mMenuProvider =
                new MenuProvider() { // from class:
                                     // com.android.settings.network.NetworkProviderSettings.1
                    @Override // androidx.core.view.MenuProvider
                    public final void onCreateMenu(Menu menu, MenuInflater menuInflater) {
                        MenuItem add =
                                menu.add(0, 6, 0, com.android.settings.R.string.fix_connectivity);
                        add.setIcon(com.android.settings.R.drawable.ic_repair_24dp);
                        add.setShowAsAction(2);
                    }

                    @Override // androidx.core.view.MenuProvider
                    public final boolean onMenuItemSelected(MenuItem menuItem) {
                        if (menuItem.getItemId() != 6) {
                            return false;
                        }
                        NetworkProviderSettings networkProviderSettings =
                                NetworkProviderSettings.this;
                        if (networkProviderSettings.isPhoneOnCall()) {
                            networkProviderSettings.showResetInternetDialog();
                            return true;
                        }
                        NetworkProviderSettings.m956$$Nest$mfixConnectivity(
                                networkProviderSettings);
                        return true;
                    }

                    @Override // androidx.core.view.MenuProvider
                    public final void onPrepareMenu(Menu menu) {
                        NetworkProviderSettings networkProviderSettings =
                                NetworkProviderSettings.this;
                        WifiPickerTracker wifiPickerTracker =
                                networkProviderSettings.mWifiPickerTracker;
                        boolean z = false;
                        boolean z2 =
                                wifiPickerTracker != null && wifiPickerTracker.getWifiState() == 3;
                        AirplaneModeEnabler airplaneModeEnabler =
                                networkProviderSettings.mAirplaneModeEnabler;
                        boolean z3 =
                                airplaneModeEnabler != null
                                        && WirelessUtils.isAirplaneModeOn(
                                                airplaneModeEnabler.mContext);
                        MenuItem findItem = menu.findItem(6);
                        if (findItem == null) {
                            return;
                        }
                        if (!networkProviderSettings.mIsGuest && (!z3 || z2)) {
                            z = true;
                        }
                        findItem.setVisible(z);
                    }
                };
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView.Adapter onCreateAdapter(PreferenceScreen preferenceScreen) {
        RecyclerView.Adapter onCreateAdapter = super.onCreateAdapter(preferenceScreen);
        onCreateAdapter.setHasStableIds(true);
        return onCreateAdapter;
    }

    @Override // androidx.fragment.app.Fragment, android.view.View.OnCreateContextMenuListener
    public final void onCreateContextMenu(
            ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        Preference preference = (Preference) view.getTag();
        if (preference instanceof LongPressWifiEntryPreference) {
            WifiEntry wifiEntry = ((LongPressWifiEntryPreference) preference).mWifiEntry;
            this.mSelectedWifiEntry = wifiEntry;
            contextMenu.setHeaderTitle(wifiEntry.getTitle());
            if (this.mSelectedWifiEntry.canConnect()) {
                contextMenu.add(0, 2, 0, com.android.settings.R.string.wifi_connect);
            }
            if (this.mSelectedWifiEntry.canDisconnect()) {
                if (this.mSelectedWifiEntry.canShare()) {
                    addShareMenuIfSuitable(contextMenu);
                }
                contextMenu.add(0, 3, 1, com.android.settings.R.string.wifi_disconnect_button_text);
            }
            if (this.mSelectedWifiEntry.canForget()
                    && !com.android.settings.wifi.WifiUtils.isNetworkLockedDown(
                            getActivity(), this.mSelectedWifiEntry.getWifiConfiguration())) {
                addForgetMenuIfSuitable(contextMenu);
            }
            if (com.android.settings.wifi.WifiUtils.isNetworkLockedDown(
                    getActivity(), this.mSelectedWifiEntry.getWifiConfiguration())) {
                return;
            }
            addModifyMenuIfSuitable(contextMenu, this.mSelectedWifiEntry);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (i != 1) {
            return super.onCreateDialog(i);
        }
        WifiDialog2 wifiDialog2 =
                new WifiDialog2(requireContext(), this, this.mDialogWifiEntry, this.mDialogMode);
        this.mDialog = wifiDialog2;
        return wifiDialog2;
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        AirplaneModeEnabler airplaneModeEnabler = this.mAirplaneModeEnabler;
        if (airplaneModeEnabler != null) {
            airplaneModeEnabler.close();
        }
        super.onDestroy();
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final void onDialogShowing() {
        super.onDialogShowing();
        setOnDismissListener(this);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        this.mDialog = null;
        this.mDialogWifiEntry = null;
        this.mDialogWifiEntryKey = null;
    }

    @Override // com.android.settings.wifi.WifiDialog2.WifiDialog2Listener
    public final void onForget(WifiDialog2 wifiDialog2) {
        WifiEntry wifiEntry = wifiDialog2.wifiEntry;
        this.mMetricsFeatureProvider.action(getActivity(), 137, new Pair[0]);
        wifiEntry.forget(null);
    }

    @Override // com.android.settings.network.InternetUpdater.InternetChangeListener
    public final void onInternetTypeChanged(int i) {
        ThreadUtils.postOnMainThread(
                new NetworkProviderSettings$$ExternalSyntheticLambda1(this, 0));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onNumSavedNetworksChanged() {
        if (isFinishingOrDestroyed()) {
            return;
        }
        setAdditionalSettingsSummaries();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onNumSavedSubscriptionsChanged() {
        if (isFinishingOrDestroyed()) {
            return;
        }
        setAdditionalSettingsSummaries();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getFragment() != null) {
            preference.setOnPreferenceClickListener(null);
            return super.onPreferenceTreeClick(preference);
        }
        if (preference instanceof LongPressWifiEntryPreference) {
            onSelectedWifiPreferenceClick((LongPressWifiEntryPreference) preference);
            return true;
        }
        if (preference != this.mAddWifiNetworkPreference) {
            return super.onPreferenceTreeClick(preference);
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        subSettingLauncher.setTitleRes(com.android.settings.R.string.wifi_add_network, null);
        String name = AddNetworkFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 103;
        subSettingLauncher.setResultListener(this, 2);
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        RecyclerView listView = getListView();
        if (listView != null) {
            listView.setItemAnimator(null);
        }
        boolean z = this.mIsRestricted;
        boolean isUiRestricted = isUiRestricted();
        this.mIsRestricted = isUiRestricted;
        if (!z && isUiRestricted) {
            if (!isUiRestrictedByOnlyAdmin()) {
                this.mEmptyTextView.setText(
                        com.android.settings.R.string.wifi_empty_list_user_restricted);
            }
            getPreferenceScreen().removeAll();
        }
        WifiPickerTracker wifiPickerTracker = this.mWifiPickerTracker;
        changeNextButtonState(
                (wifiPickerTracker == null || wifiPickerTracker.mConnectedWifiEntry == null)
                        ? false
                        : true);
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

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onScanRequested() {
        showPinnedHeader(true);
    }

    public void onSelectedWifiPreferenceClick(
            LongPressWifiEntryPreference longPressWifiEntryPreference) {
        WifiEntry wifiEntry = longPressWifiEntryPreference.mWifiEntry;
        if (wifiEntry.shouldEditBeforeConnect()) {
            launchConfigNewNetworkFragment(wifiEntry);
        } else if (wifiEntry.canConnect()) {
            connect(wifiEntry, true, true);
        } else if (wifiEntry.isSaved()) {
            launchNetworkDetailsFragment(longPressWifiEntryPreference);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (this.mIsViewLoading) {
            getView()
                    .postDelayed(
                            this.mRemoveLoadingRunnable,
                            (hasWifiManager$1() && this.mWifiManager.isWifiEnabled())
                                    ? 1000L
                                    : 100L);
        }
        if (!this.mIsRestricted) {
            this.mAirplaneModeEnabler.start();
            return;
        }
        if (!isUiRestrictedByOnlyAdmin()) {
            this.mEmptyTextView.setText(
                    com.android.settings.R.string.wifi_empty_list_user_restricted);
        }
        getPreferenceScreen().removeAll();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        getView().removeCallbacks(this.mRemoveLoadingRunnable);
        getView().removeCallbacks(this.mUpdateWifiEntryPreferencesRunnable);
        getView().removeCallbacks(this.mHideProgressBarRunnable);
        this.mAirplaneModeEnabler.stop();
        super.onStop();
    }

    @Override // com.android.settings.wifi.WifiDialog2.WifiDialog2Listener
    public final void onSubmit(WifiDialog2 wifiDialog2) {
        if (hasWifiManager$1()) {
            int i = wifiDialog2.mode;
            WifiConfiguration config = wifiDialog2.getController().getConfig();
            WifiEntry wifiEntry = wifiDialog2.wifiEntry;
            if (i == 2) {
                if (config == null) {
                    Toast.makeText(
                                    getContext(),
                                    com.android.settings.R.string.wifi_failed_save_message,
                                    0)
                            .show();
                    return;
                } else {
                    this.mWifiManager.save(config, this.mSaveListener);
                    return;
                }
            }
            if (i == 1 || (i == 0 && wifiEntry.canConnect())) {
                if (config == null) {
                    connect(wifiEntry, false, false);
                } else {
                    this.mWifiManager.connect(config, new AnonymousClass2(this, 1));
                }
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (getActivity() == null) {
            return;
        }
        setPinnedHeaderView(com.android.settings.R.layout.progress_header);
        showPinnedHeader(false);
        if (hasWifiManager$1()) {
            setLoading(true, false);
            this.mIsViewLoading = true;
        }
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onWifiEntriesChanged(int i) {
        WifiPickerTracker wifiPickerTracker;
        updateWifiEntryPreferences();
        boolean z = false;
        if (i == 1) {
            showPinnedHeader(false);
        }
        WifiPickerTracker wifiPickerTracker2 = this.mWifiPickerTracker;
        if (wifiPickerTracker2 != null && wifiPickerTracker2.mConnectedWifiEntry != null) {
            z = true;
        }
        changeNextButtonState(z);
        if (this.mOpenSsid == null || (wifiPickerTracker = this.mWifiPickerTracker) == null) {
            return;
        }
        final int i2 = 0;
        Stream filter =
                wifiPickerTracker.getWifiEntries().stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.android.settings.network.NetworkProviderSettings$$ExternalSyntheticLambda9
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return TextUtils.equals(
                                                NetworkProviderSettings.this.mOpenSsid,
                                                ((WifiEntry) obj).getSsid());
                                    }
                                })
                        .filter(
                                new Predicate() { // from class:
                                                  // com.android.settings.network.NetworkProviderSettings$$ExternalSyntheticLambda10
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        WifiConfiguration.NetworkSelectionStatus
                                                networkSelectionStatus;
                                        WifiEntry wifiEntry = (WifiEntry) obj;
                                        switch (i2) {
                                            case 0:
                                                NetworkProviderSettings.SearchIndexProvider
                                                        searchIndexProvider =
                                                                NetworkProviderSettings
                                                                        .SEARCH_INDEX_DATA_PROVIDER;
                                                if (wifiEntry.getSecurity$1() == 0
                                                        || wifiEntry.getSecurity$1() == 4) {
                                                    break;
                                                }
                                                break;
                                            default:
                                                NetworkProviderSettings.SearchIndexProvider
                                                        searchIndexProvider2 =
                                                                NetworkProviderSettings
                                                                        .SEARCH_INDEX_DATA_PROVIDER;
                                                if (wifiEntry.isSaved()) {
                                                    WifiConfiguration wifiConfiguration =
                                                            wifiEntry.getWifiConfiguration();
                                                    if (wifiConfiguration == null
                                                            || (networkSelectionStatus =
                                                                            wifiConfiguration
                                                                                    .getNetworkSelectionStatus())
                                                                    == null
                                                            || networkSelectionStatus
                                                                            .getNetworkSelectionStatus()
                                                                    == 0
                                                            || 8
                                                                    != networkSelectionStatus
                                                                            .getNetworkSelectionDisableReason()) {
                                                        break;
                                                    }
                                                }
                                                break;
                                        }
                                        return false;
                                    }
                                });
        final int i3 = 1;
        Optional findFirst =
                filter.filter(
                                new Predicate() { // from class:
                                                  // com.android.settings.network.NetworkProviderSettings$$ExternalSyntheticLambda10
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        WifiConfiguration.NetworkSelectionStatus
                                                networkSelectionStatus;
                                        WifiEntry wifiEntry = (WifiEntry) obj;
                                        switch (i3) {
                                            case 0:
                                                NetworkProviderSettings.SearchIndexProvider
                                                        searchIndexProvider =
                                                                NetworkProviderSettings
                                                                        .SEARCH_INDEX_DATA_PROVIDER;
                                                if (wifiEntry.getSecurity$1() == 0
                                                        || wifiEntry.getSecurity$1() == 4) {
                                                    break;
                                                }
                                                break;
                                            default:
                                                NetworkProviderSettings.SearchIndexProvider
                                                        searchIndexProvider2 =
                                                                NetworkProviderSettings
                                                                        .SEARCH_INDEX_DATA_PROVIDER;
                                                if (wifiEntry.isSaved()) {
                                                    WifiConfiguration wifiConfiguration =
                                                            wifiEntry.getWifiConfiguration();
                                                    if (wifiConfiguration == null
                                                            || (networkSelectionStatus =
                                                                            wifiConfiguration
                                                                                    .getNetworkSelectionStatus())
                                                                    == null
                                                            || networkSelectionStatus
                                                                            .getNetworkSelectionStatus()
                                                                    == 0
                                                            || 8
                                                                    != networkSelectionStatus
                                                                            .getNetworkSelectionDisableReason()) {
                                                        break;
                                                    }
                                                }
                                                break;
                                        }
                                        return false;
                                    }
                                })
                        .findFirst();
        if (findFirst.isPresent()) {
            this.mOpenSsid = null;
            launchConfigNewNetworkFragment((WifiEntry) findFirst.get());
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {
        if (this.mIsRestricted || !hasWifiManager$1()) {
            return;
        }
        int wifiState = this.mWifiPickerTracker.getWifiState();
        if (BaseWifiTracker.sVerboseLogging) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    wifiState,
                    "onWifiStateChanged called with wifi state: ",
                    "NetworkProviderSettings");
        }
        if (isFinishingOrDestroyed()) {
            Log.w(
                    "NetworkProviderSettings",
                    "onWifiStateChanged shouldn't run when fragment is finishing or destroyed");
            return;
        }
        if (isAdded()) {
            requireActivity().invalidateOptionsMenu();
        }
        if (wifiState == 0) {
            removeConnectedWifiEntryPreference();
            this.mWifiEntryPreferenceCategory.removeAll();
            this.mWifiEntryPreferenceCategory.setVisible(false);
            return;
        }
        if (wifiState == 1) {
            setWifiScanMessage(false);
            removeConnectedWifiEntryPreference();
            this.mWifiEntryPreferenceCategory.removeAll();
            this.mWifiEntryPreferenceCategory.setVisible(false);
            setAdditionalSettingsSummaries();
            showPinnedHeader(false);
            this.mClickedConnect = false;
            return;
        }
        if (wifiState != 2) {
            if (wifiState != 3) {
                return;
            }
            setWifiScanMessage(true);
            updateWifiEntryPreferences();
            return;
        }
        removeConnectedWifiEntryPreference();
        this.mWifiEntryPreferenceCategory.removeAll();
        this.mWifiEntryPreferenceCategory.setVisible(false);
        showPinnedHeader(true);
    }

    public void openSubscriptionHelpPage(WifiEntry wifiEntry) {
        Context context = getContext();
        wifiEntry.getClass();
        Intent helpIntent = getHelpIntent(context, null);
        if (helpIntent != null) {
            try {
                startActivityForResult(helpIntent, 4);
            } catch (ActivityNotFoundException unused) {
                Log.e(
                        "NetworkProviderSettings",
                        "Activity was not found for intent, " + helpIntent.toString());
            }
        }
    }

    public final void removeConnectedWifiEntryPreference() {
        this.mConnectedWifiEntryPreferenceCategory.removeAll();
        this.mConnectedWifiEntryPreferenceCategory.setVisible(false);
        this.mFirstWifiEntryPreferenceCategory.setVisible(false);
        this.mFirstWifiEntryPreferenceCategory.removeAll();
    }

    public void setAdditionalSettingsSummaries() {
        String icuPluralsString;
        Preference preference = this.mConfigureWifiSettingsPreference;
        Context context = getContext();
        preference.setSummary(
                getString(
                        (hasWifiManager$1()
                                        && this.mWifiManager.isAutoWakeupEnabled()
                                        && this.mWifiManager.isScanAlwaysAvailable()
                                        && Settings.Global.getInt(
                                                        context.getContentResolver(),
                                                        "airplane_mode_on",
                                                        0)
                                                == 0
                                        && !((PowerManager)
                                                        context.getSystemService(
                                                                PowerManager.class))
                                                .isPowerSaveMode())
                                ? com.android.settings.R.string
                                        .wifi_configure_settings_preference_summary_wakeup_on
                                : com.android.settings.R.string
                                        .wifi_configure_settings_preference_summary_wakeup_off));
        WifiPickerTracker wifiPickerTracker = this.mWifiPickerTracker;
        int i = wifiPickerTracker == null ? 0 : wifiPickerTracker.mNumSavedNetworks;
        int size =
                wifiPickerTracker == null
                        ? 0
                        : ((ArrayMap) wifiPickerTracker.mPasspointConfigCache).size();
        int i2 = i + size;
        if (i2 <= 0) {
            this.mSavedNetworksPreference.setVisible(false);
            return;
        }
        this.mSavedNetworksPreference.setVisible(true);
        Preference preference2 = this.mSavedNetworksPreference;
        if (getContext() == null) {
            Log.w(
                    "NetworkProviderSettings",
                    "getSavedNetworkSettingsSummaryText shouldn't run if resource is not ready");
            icuPluralsString = null;
        } else {
            icuPluralsString =
                    size == 0
                            ? StringUtil.getIcuPluralsString(
                                    getContext(),
                                    i,
                                    com.android.settings.R.string.wifi_saved_access_points_summary)
                            : i == 0
                                    ? StringUtil.getIcuPluralsString(
                                            getContext(),
                                            size,
                                            com.android.settings.R.string
                                                    .wifi_saved_passpoint_access_points_summary)
                                    : StringUtil.getIcuPluralsString(
                                            getContext(),
                                            i2,
                                            com.android.settings.R.string
                                                    .wifi_saved_all_access_points_summary);
        }
        preference2.setSummary(icuPluralsString);
    }

    public void setWifiScanMessage(boolean z) {
        Context context = getContext();
        if (context == null) {
            return;
        }
        LocationManager locationManager =
                (LocationManager) context.getSystemService(LocationManager.class);
        if (!hasWifiManager$1()
                || z
                || !locationManager.isLocationEnabled()
                || !this.mWifiManager.isScanAlwaysAvailable()) {
            this.mWifiStatusMessagePreference.setVisible(false);
            return;
        }
        if (TextUtils.isEmpty(this.mWifiStatusMessagePreference.getTitle())) {
            this.mWifiStatusMessagePreference.setTitle(
                    com.android.settings.R.string.wifi_scan_notify_message);
            this.mWifiStatusMessagePreference.setLearnMoreText(
                    context.getString(com.android.settings.R.string.wifi_scan_change));
            this.mWifiStatusMessagePreference.setLearnMoreAction(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.network.NetworkProviderSettings$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            NetworkProviderSettings networkProviderSettings =
                                    NetworkProviderSettings.this;
                            NetworkProviderSettings.SearchIndexProvider searchIndexProvider =
                                    NetworkProviderSettings.SEARCH_INDEX_DATA_PROVIDER;
                            SubSettingLauncher subSettingLauncher =
                                    new SubSettingLauncher(networkProviderSettings.getContext());
                            String name = WifiScanningFragment.class.getName();
                            SubSettingLauncher.LaunchRequest launchRequest =
                                    subSettingLauncher.mLaunchRequest;
                            launchRequest.mDestinationName = name;
                            launchRequest.mSourceMetricsCategory = 746;
                            subSettingLauncher.launch();
                        }
                    });
        }
        this.mWifiStatusMessagePreference.setVisible(true);
    }

    public boolean showAnySubscriptionInfo(Context context) {
        if (context != null) {
            Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
            if (context.getResources()
                    .getBoolean(com.android.settings.R.bool.config_show_sim_info)) {
                return true;
            }
        }
        return false;
    }

    public final void showDialog(WifiEntry wifiEntry, int i) {
        if (com.android.settings.wifi.WifiUtils.isNetworkLockedDown(
                        getActivity(), wifiEntry.getWifiConfiguration())
                && wifiEntry.getConnectedState() == 2) {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                    getActivity(), RestrictedLockUtilsInternal.getDeviceOwner(getActivity()));
            return;
        }
        if (this.mDialog != null) {
            removeDialog(1);
            this.mDialog = null;
        }
        this.mDialogWifiEntry = wifiEntry;
        this.mDialogWifiEntryKey = wifiEntry.getKey();
        this.mDialogMode = i;
        showDialog(1);
    }

    public void showResetInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        DialogInterface.OnClickListener onClickListener =
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.network.NetworkProviderSettings.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        NetworkProviderSettings.m956$$Nest$mfixConnectivity(
                                NetworkProviderSettings.this);
                    }
                };
        builder.setTitle(com.android.settings.R.string.reset_your_internet_title);
        builder.setMessage(com.android.settings.R.string.reset_internet_text);
        builder.setPositiveButton(com.android.settings.R.string.tts_reset, onClickListener);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.create().show();
    }

    public final void updateWifiEntryPreferences() {
        WifiPickerTracker wifiPickerTracker;
        int i;
        if (getActivity() == null
                || getView() == null
                || this.mIsRestricted
                || (wifiPickerTracker = this.mWifiPickerTracker) == null
                || wifiPickerTracker.getWifiState() != 3) {
            return;
        }
        this.mWifiEntryPreferenceCategory.setVisible(true);
        final WifiEntry wifiEntry = this.mWifiPickerTracker.mConnectedWifiEntry;
        PreferenceCategory connectedWifiPreferenceCategory = getConnectedWifiPreferenceCategory();
        connectedWifiPreferenceCategory.setVisible(wifiEntry != null);
        if (wifiEntry != null) {
            LongPressWifiEntryPreference longPressWifiEntryPreference =
                    (LongPressWifiEntryPreference)
                            connectedWifiPreferenceCategory.findPreference(wifiEntry.getKey());
            if (longPressWifiEntryPreference == null
                    || longPressWifiEntryPreference.mWifiEntry != wifiEntry) {
                connectedWifiPreferenceCategory.removeAll();
                final ConnectedWifiEntryPreference createConnectedWifiEntryPreference =
                        createConnectedWifiEntryPreference(wifiEntry);
                createConnectedWifiEntryPreference.setKey(wifiEntry.getKey());
                createConnectedWifiEntryPreference.refresh();
                connectedWifiPreferenceCategory.addPreference(createConnectedWifiEntryPreference);
                createConnectedWifiEntryPreference.setOnPreferenceClickListener(
                        new Preference
                                .OnPreferenceClickListener() { // from class:
                                                               // com.android.settings.network.NetworkProviderSettings$$ExternalSyntheticLambda7
                            @Override // androidx.preference.Preference.OnPreferenceClickListener
                            public final boolean onPreferenceClick(Preference preference) {
                                NetworkProviderSettings.SearchIndexProvider searchIndexProvider =
                                        NetworkProviderSettings.SEARCH_INDEX_DATA_PROVIDER;
                                NetworkProviderSettings networkProviderSettings =
                                        NetworkProviderSettings.this;
                                networkProviderSettings.getClass();
                                WifiEntry wifiEntry2 = wifiEntry;
                                if (wifiEntry2.canSignIn()) {
                                    wifiEntry2.signIn();
                                    return true;
                                }
                                networkProviderSettings.launchNetworkDetailsFragment(
                                        createConnectedWifiEntryPreference);
                                return true;
                            }
                        });
                createConnectedWifiEntryPreference.mOnGearClickListener =
                        new NetworkProviderSettings$$ExternalSyntheticLambda8(
                                this, createConnectedWifiEntryPreference);
                createConnectedWifiEntryPreference.notifyChanged();
                if (this.mClickedConnect) {
                    this.mClickedConnect = false;
                    scrollToPreference(connectedWifiPreferenceCategory);
                }
            }
        } else {
            connectedWifiPreferenceCategory.removeAll();
        }
        cacheRemoveAllPrefs(this.mWifiEntryPreferenceCategory);
        boolean z = false;
        int i2 = 0;
        for (WifiEntry wifiEntry2 : this.mWifiPickerTracker.getWifiEntries()) {
            String key = wifiEntry2.getKey();
            LongPressWifiEntryPreference longPressWifiEntryPreference2 =
                    (LongPressWifiEntryPreference) getCachedPreference(key);
            if (longPressWifiEntryPreference2 != null) {
                if (longPressWifiEntryPreference2.mWifiEntry == wifiEntry2) {
                    i = i2 + 1;
                    longPressWifiEntryPreference2.setOrder(i2);
                    i2 = i;
                    z = true;
                } else {
                    removePreference(key);
                }
            }
            LongPressWifiEntryPreference createLongPressWifiEntryPreference =
                    createLongPressWifiEntryPreference(wifiEntry2);
            createLongPressWifiEntryPreference.setKey(wifiEntry2.getKey());
            i = i2 + 1;
            createLongPressWifiEntryPreference.setOrder(i2);
            createLongPressWifiEntryPreference.refresh();
            this.mWifiEntryPreferenceCategory.addPreference(createLongPressWifiEntryPreference);
            i2 = i;
            z = true;
        }
        removeCachedPrefs(this.mWifiEntryPreferenceCategory);
        if (!z) {
            Preference preference = new Preference(getPrefContext());
            preference.setSelectable(false);
            preference.setSummary(com.android.settings.R.string.wifi_empty_list_wifi_on);
            preference.setOrder(i2);
            preference.setKey("wifi_empty_list");
            this.mWifiEntryPreferenceCategory.addPreference(preference);
            i2++;
        }
        this.mAddWifiNetworkPreference.setOrder(i2);
        this.mWifiEntryPreferenceCategory.addPreference(this.mAddWifiNetworkPreference);
        setAdditionalSettingsSummaries();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class SearchIndexProvider extends BaseSearchIndexProvider {
        public final WifiRestriction mWifiRestriction;

        public SearchIndexProvider() {
            super(com.android.settings.R.xml.network_provider_settings);
            this.mWifiRestriction = new WifiRestriction();
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            this.mWifiRestriction.getClass();
            if (!(context == null
                    ? true
                    : WifiEnterpriseRestrictionUtils.isChangeWifiStateAllowed(context))) {
                ((ArrayList) nonIndexableKeys).add(NetworkProviderSettings.PREF_KEY_WIFI_TOGGLE);
            }
            WifiManager wifiManager = (WifiManager) context.getSystemService(WifiManager.class);
            if (wifiManager == null) {
                return nonIndexableKeys;
            }
            if (WifiSavedConfigUtils.getAllConfigsCount(context, wifiManager) == 0) {
                ((ArrayList) nonIndexableKeys).add("saved_networks");
            }
            if (wifiManager.getWifiState() != 3) {
                ((ArrayList) nonIndexableKeys)
                        .add(NetworkProviderSettings.PREF_KEY_ADD_WIFI_NETWORK);
            }
            if (!DataUsageUtils.hasWifiRadio(context)) {
                ((ArrayList) nonIndexableKeys).add(NetworkProviderSettings.PREF_KEY_DATA_USAGE);
            }
            return nonIndexableKeys;
        }

        public SearchIndexProvider(int i, WifiRestriction wifiRestriction) {
            super(i);
            this.mWifiRestriction = wifiRestriction;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.network.NetworkProviderSettings$2, reason: invalid class name */
    public final class AnonymousClass2 implements WifiManager.ActionListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ NetworkProviderSettings this$0;

        public /* synthetic */ AnonymousClass2(
                NetworkProviderSettings networkProviderSettings, int i) {
            this.$r8$classId = i;
            this.this$0 = networkProviderSettings;
        }

        public final void onFailure(int i) {
            switch (this.$r8$classId) {
                case 0:
                    FragmentActivity activity = this.this$0.getActivity();
                    if (activity != null) {
                        Toast.makeText(
                                        activity,
                                        com.android.settings.R.string.wifi_failed_save_message,
                                        0)
                                .show();
                        break;
                    }
                    break;
                default:
                    NetworkProviderSettings networkProviderSettings = this.this$0;
                    SearchIndexProvider searchIndexProvider =
                            NetworkProviderSettings.SEARCH_INDEX_DATA_PROVIDER;
                    if (!networkProviderSettings.isFinishingOrDestroyed()) {
                        Toast.makeText(
                                        this.this$0.getContext(),
                                        com.android.settings.R.string.wifi_failed_connect_message,
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
                    this.this$0.mClickedConnect = true;
                    break;
            }
        }

        private final void onSuccess$com$android$settings$network$NetworkProviderSettings$2() {}
    }
}
