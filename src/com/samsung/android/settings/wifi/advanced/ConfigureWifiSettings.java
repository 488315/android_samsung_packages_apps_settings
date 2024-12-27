package com.samsung.android.settings.wifi.advanced;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.scloud.lib.setting.SamsungCloudRPCSettingV2;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ConfigureWifiSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.sec_wifi_configure_settings);
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass3();
    public Context mContext;
    public IntentFilter mFilter;
    public WifiCloudSettingsStore mStore;
    public WifiCloudSyncPreferenceController mWifiCloudSyncPreferenceController;
    public WifiCloudSyncSwitchPreferenceController mWifiCloudSyncSwitchPreferenceController;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.advanced.ConfigureWifiSettings.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    Log.d("ConfigureWifiSettings", "Broadcast " + action);
                    action.getClass();
                    if (action.equals("com.android.sync.SYNC_CONN_STATUS_CHANGED")) {
                        ConfigureWifiSettings configureWifiSettings = ConfigureWifiSettings.this;
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                ConfigureWifiSettings.SEARCH_INDEX_DATA_PROVIDER;
                        configureWifiSettings.getClass();
                        configureWifiSettings.mStore.mIsMainSyncOn =
                                ContentResolver.getMasterSyncAutomatically();
                        configureWifiSettings.updateListAdapter();
                    }
                }
            };
    public final AnonymousClass4 mEnableStatusObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.wifi.advanced.ConfigureWifiSettings.4
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                            "onChanged value=",
                            "ConfigureWifiSettings",
                            Settings.Global.getInt(
                                            ConfigureWifiSettings.this.mContext
                                                    .getContentResolver(),
                                            "scloud_wifi_sync_enabled",
                                            0)
                                    != 0);
                    ConfigureWifiSettings.this.updateListAdapter();
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.advanced.ConfigureWifiSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            WifiFeatureUtils wifiFeatureUtils = new WifiFeatureUtils(context);
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add(wifiFeatureUtils.getKeyIfUnsupported("wifi_cloud_sa"));
            arrayList.add(wifiFeatureUtils.getKeyIfUnsupported("wifi_cloud_sync"));
            arrayList.add(wifiFeatureUtils.getKeyIfUnsupported("notify_open_networks"));
            arrayList.add(wifiFeatureUtils.getKeyIfUnsupported("MobileWIPS"));
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            Resources resources = context.getResources();
            ArrayList arrayList = new ArrayList();
            new SearchIndexableRaw(context);
            if ("VZW".equals(SemWifiUtils.readSalesCode())) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw).key = "wifi_cloud_sa";
                searchIndexableRaw.title =
                        String.valueOf(R.string.sec_bluetooth_advanced_sync_with_account_vzw);
                searchIndexableRaw.screenTitle =
                        resources.getString(R.string.wifi_menu_advanced_button);
                searchIndexableRaw.summaryOn =
                        resources.getString(R.string.sec_bluetooth_advanced_sign_in_sacount);
                arrayList.add(searchIndexableRaw);
                SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw2).key = "wifi_cloud_sync";
                searchIndexableRaw2.title =
                        String.valueOf(R.string.sec_bluetooth_advanced_sync_with_account_vzw);
                searchIndexableRaw2.screenTitle =
                        resources.getString(R.string.wifi_menu_advanced_button);
                searchIndexableRaw2.summaryOn =
                        resources.getString(R.string.sec_bluetooth_advanced_auto_sync_disabled);
                arrayList.add(searchIndexableRaw2);
            }
            if (Utils.isTablet()) {
                SearchIndexableRaw searchIndexableRaw3 = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw3).key = "install_credentials";
                searchIndexableRaw3.title =
                        String.valueOf(R.string.wifi_install_network_certificates);
                searchIndexableRaw3.screenTitle =
                        resources.getString(R.string.wifi_menu_advanced_button);
                searchIndexableRaw3.summaryOn =
                        resources.getString(
                                R.string.wifi_install_network_credentials_summary_tablet);
                arrayList.add(searchIndexableRaw3);
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if (context.getResources().getBoolean(R.bool.config_show_wifi_settings)) {
                SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
                if (!((UserManager) context.getSystemService("user")).isGuestUser()) {
                    return true;
                }
            }
            return false;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.advanced.ConfigureWifiSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            boolean syncAutomatically =
                    ContentResolver.getSyncAutomatically(
                            SemWifiUtils.getSamsungAccount(context),
                            "com.android.settings.wifiprofilesync");
            String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            String str2 = syncAutomatically ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str2;
            statusData.mStatusKey = "1245";
            arrayList.add(statusData);
            if (((WifiManager) context.getSystemService(WifiManager.class))
                    .isWifiPasspointEnabled()) {
                str = "1";
            }
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = str;
            statusData2.mStatusKey = "1240";
            arrayList.add(statusData2);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ConfigureWifiSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.XLSB;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_configure_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext().getApplicationContext();
        this.mStore = new WifiCloudSettingsStore();
        WifiCloudSyncSwitchPreferenceController wifiCloudSyncSwitchPreferenceController =
                (WifiCloudSyncSwitchPreferenceController)
                        use(WifiCloudSyncSwitchPreferenceController.class);
        this.mWifiCloudSyncSwitchPreferenceController = wifiCloudSyncSwitchPreferenceController;
        wifiCloudSyncSwitchPreferenceController.setFragment(this);
        WifiCloudSyncPreferenceController wifiCloudSyncPreferenceController =
                (WifiCloudSyncPreferenceController) use(WifiCloudSyncPreferenceController.class);
        this.mWifiCloudSyncPreferenceController = wifiCloudSyncPreferenceController;
        wifiCloudSyncPreferenceController.setFragment(this);
        this.mFilter = new IntentFilter("com.android.sync.SYNC_CONN_STATUS_CHANGED");
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SALogging.insertSALog("WIFI_200");
        this.mStore.mIsMainSyncOn = ContentResolver.getMasterSyncAutomatically();
        updateListAdapter();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("scloud_wifi_sync_enabled"),
                        false,
                        this.mEnableStatusObserver);
        this.mContext.registerReceiver(this.mReceiver, this.mFilter, 2);
        this.mStore.setRpcSettings(
                new SamsungCloudRPCSettingV2(
                        getContext().getApplicationContext(),
                        "com.android.settings.wifiprofilesync",
                        "com.android.settings.wifiprofilesetting"));
        this.mWifiCloudSyncSwitchPreferenceController.setSettingsStore(this.mStore);
        this.mWifiCloudSyncPreferenceController.setSettingsStore(this.mStore);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mContext.getContentResolver().unregisterContentObserver(this.mEnableStatusObserver);
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    public final void updateListAdapter() {
        if (this.mWifiCloudSyncSwitchPreferenceController.isAvailable()) {
            boolean autoSync = this.mStore.mSetting.getAutoSync();
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "getAutoSync=", "ConfigureWifiSettings", autoSync);
            this.mWifiCloudSyncSwitchPreferenceController.onChangeWifiSyncEnabled(
                    getPreferenceScreen(), autoSync);
        } else if (this.mWifiCloudSyncPreferenceController.isAvailable()) {
            this.mWifiCloudSyncPreferenceController.displayPreference(getPreferenceScreen());
        }
        RecyclerView.Adapter adapter = getListView().getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
