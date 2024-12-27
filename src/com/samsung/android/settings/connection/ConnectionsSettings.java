package com.samsung.android.settings.connection;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.preference.Preference;

import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.drawer.DashboardCategory;
import com.android.settingslib.drawer.Tile;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.connection.tether.SecTabTetherPreferenceController;
import com.samsung.android.settings.connection.tether.SecTetherPreferenceController;
import com.samsung.android.settings.datausage.DataUsageSummaryCHN;
import com.samsung.android.settings.datausage.DataUsageUtilsCHN;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.general.GeneralUtils;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.security.SecureWifiPreferenceController;
import com.samsung.android.settings.widget.SecRelativeLinkView;
import com.sec.ims.configuration.DATA;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ConnectionsSettings extends SecDynamicFragment {
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.connection.ConnectionsSettings.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    ConnectionsSettings connectionsSettings = ConnectionsSettings.this;
                    if (connectionsSettings.mRegisterIntent != null) {
                        connectionsSettings.mRegisterIntent = null;
                        if ("android.intent.action.SIM_STATE_CHANGED".equals(intent.getAction())) {
                            return;
                        }
                    }
                    ConnectionsSettings.this.updatePreferenceStates();
                }
            };
    public Intent mRegisterIntent;
    public SecRelativeLinkView mRelativeLinkView;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass3();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.connection.ConnectionsSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            boolean isWifiEnabled = ConnectionsUtils.isWifiEnabled(context);
            String valueOf = String.valueOf(3129);
            String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            String str2 = isWifiEnabled ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            boolean z = defaultAdapter != null && defaultAdapter.isEnabled();
            String valueOf2 = String.valueOf(3209);
            String str3 = z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = str3;
            statusData2.mStatusKey = valueOf2;
            arrayList.add(statusData2);
            boolean isAirplaneModeEnabled = ConnectionsUtils.isAirplaneModeEnabled(context);
            String valueOf3 = String.valueOf(3213);
            String str4 = isAirplaneModeEnabled ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData3 = new StatusData();
            statusData3.mStatusValue = str4;
            statusData3.mStatusKey = valueOf3;
            arrayList.add(statusData3);
            NfcAdapter defaultAdapter2 = NfcAdapter.getDefaultAdapter(context);
            boolean z2 =
                    defaultAdapter2 != null
                            && (defaultAdapter2.getAdapterState() == 2
                                    || defaultAdapter2.getAdapterState() == 3);
            String valueOf4 = String.valueOf(3214);
            String str5 = z2 ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData4 = new StatusData();
            statusData4.mStatusValue = str5;
            statusData4.mStatusKey = valueOf4;
            arrayList.add(statusData4);
            NfcAdapter defaultAdapter3 = NfcAdapter.getDefaultAdapter(context);
            if ((defaultAdapter3 == null ? false : defaultAdapter3.isReaderOptionSupported())
                    && z2) {
                boolean z3 =
                        NfcAdapter.getDefaultAdapter(context) != null
                                ? !r9.isReaderOptionEnabled()
                                : false;
                String valueOf5 = String.valueOf(7015);
                if (z3) {
                    str = "1";
                }
                StatusData statusData5 = new StatusData();
                statusData5.mStatusValue = str;
                statusData5.mStatusKey = valueOf5;
                arrayList.add(statusData5);
            }
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.connection.ConnectionsSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            new SearchIndexableRaw(context);
            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw).key = "data_usage_settings";
                searchIndexableRaw.title = resources.getString(R.string.data_usage_summary_title);
                searchIndexableRaw.screenTitle =
                        resources.getString(R.string.tab_category_connections);
                ((SearchIndexableData) searchIndexableRaw).intentTargetPackage =
                        context.getPackageName();
                ((SearchIndexableData) searchIndexableRaw).intentTargetClass =
                        DataUsageSummaryCHN.class.getName();
                arrayList.add(searchIndexableRaw);
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = ConnectionsSettings.class.getName();
            searchIndexableResource.xmlResId = R.xml.sec_connections_settings;
            return Arrays.asList(searchIndexableResource);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ConnectionsSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.SIMULTANEOUS_CALL_LIMIT_HAS_ALREADY_BEEN_REACHED;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_connections_settings;
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((SecTetherPreferenceController) use(SecTetherPreferenceController.class)).setHost(this);
        ((SecTabTetherPreferenceController) use(SecTabTetherPreferenceController.class))
                .setHost(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.i("ConnectionsSettings", "onCreate");
        Trace.beginSection("ConnectionsSettings#onCreate");
        Trace.beginSection("ConnectionsSettings#super.onCreate");
        super.onCreate(bundle);
        Trace.endSection();
        Log.d("ConnectionsSettings", "removeKnoxCustomSettingsHiddenItems");
        KnoxUtils.removeKnoxCustomSettingsHiddenItems(this);
        checkForKnoxCustomProKioskEnabledItems();
        Trace.endSection();
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.i("ConnectionsSettings", "onPause");
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        Log.d("ConnectionsSettings", "Inside onPreferenceTreeClick");
        String key = preference.getKey();
        Log.d("ConnectionsSettings", "onPreferenceTreeClick: key = " + key);
        if (!"data_usage_settings".equals(key) || !Rune.SUPPORT_SMARTMANAGER_CN) {
            return super.onPreferenceTreeClick(preference);
        }
        startActivity(DataUsageUtilsCHN.getSMDataUsageSummaryIntent(false));
        return true;
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        String str;
        Log.i("ConnectionsSettings", "onResume");
        Trace.beginSection("ConnectionsSettings#onResume");
        Trace.beginSection("ConnectionsSettings#super.onResume");
        super.onResume();
        Trace.endSection();
        Trace.beginSection("ConnectionsSettings#setLinkedDataView");
        Log.i("ConnectionsSettings", "setLinkedDataView");
        getActivity();
        if (Rune.supportRelativeLink()) {
            int myUserId = UserHandle.myUserId();
            boolean z = myUserId != 0;
            if (this.mRelativeLinkView == null) {
                this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
            }
            this.mRelativeLinkView.mLinkContainer.removeAllViews();
            Trace.beginSection("ConnectionsSettings#setLink_SamsungCloud");
            if (AccountUtils.checkSamsungBackup(getActivity())) {
                Log.i("ConnectionsSettings", "set Samsung Cloud");
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                        new SettingsPreferenceFragmentLinkData();
                Intent intent = new Intent("com.samsung.android.scloud.SCLOUD_MAIN");
                intent.putExtra("flowId", "9001");
                intent.putExtra(
                        "callerMetric",
                        VolteConstants.ErrorCode.SIMULTANEOUS_CALL_LIMIT_HAS_ALREADY_BEEN_REACHED);
                settingsPreferenceFragmentLinkData.intent = intent;
                settingsPreferenceFragmentLinkData.topLevelKey = "top_level_accounts_backup";
                settingsPreferenceFragmentLinkData.titleRes = R.string.cloud_title;
                if (!Rune.isChinaModel()) {
                    this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
                } else if (Settings.System.getInt(
                                getContext().getContentResolver(),
                                "samsung_cloud_switch_china_delta",
                                1)
                        != 0) {
                    this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
                }
            }
            Trace.endSection();
            Trace.beginSection("ConnectionsSettings#setLink_SecureWifi");
            if (Utils.isSupportSecureWiFi(getActivity()) && UserHandle.myUserId() == 0) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData2 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData2.flowId = "9300";
                settingsPreferenceFragmentLinkData2.callerMetric =
                        VolteConstants.ErrorCode.SIMULTANEOUS_CALL_LIMIT_HAS_ALREADY_BEEN_REACHED;
                Intent intent2 = new Intent("com.samsung.android.fast.ACTION_SECURE_WIFI");
                intent2.setPackage("com.samsung.android.fast");
                intent2.putExtra(SecureWifiPreferenceController.KEY_IS_FROM_RELATIVE, true);
                settingsPreferenceFragmentLinkData2.intent = intent2;
                settingsPreferenceFragmentLinkData2.topLevelKey = "top_level_security_and_privacy";
                settingsPreferenceFragmentLinkData2.titleRes = R.string.secure_wifi;
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData2);
            }
            Trace.endSection();
            Trace.beginSection("ConnectionsSettings#setLink_ResetNetwork");
            if (Rune.isDomesticModel()
                    && GeneralUtils.isResetEnabled(getActivity())
                    && myUserId == 0) {
                Log.i("ConnectionsSettings", "set Reset Network");
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData3 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData3.flowId = "4663";
                settingsPreferenceFragmentLinkData3.callerMetric =
                        VolteConstants.ErrorCode.SIMULTANEOUS_CALL_LIMIT_HAS_ALREADY_BEEN_REACHED;
                settingsPreferenceFragmentLinkData3.intent =
                        DisplaySettings$$ExternalSyntheticOutline0.m(
                                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                                "com.android.settings.Settings$ResetNetworkActivity");
                settingsPreferenceFragmentLinkData3.topLevelKey = "top_level_general";
                settingsPreferenceFragmentLinkData3.titleRes =
                        R.string.sec_reset_mobile_network_settings;
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData3);
            }
            Trace.endSection();
            Trace.beginSection("ConnectionsSettings#setLink_LinkToWindow");
            Log.i("ConnectionsSettings", "set Link to Window");
            Tile tile = null;
            try {
                str =
                        getContext()
                                .getPackageManager()
                                .getApplicationInfo("com.samsung.android.mdx", 128)
                                .metaData
                                .getString("LinkToWindowsRelativeLinkActivity");
                if (TextUtils.isEmpty(str)) {
                    Log.e(
                            "ConnectionsSettings",
                            "getActivityName: Landing activity is null or empty.");
                } else {
                    Log.d(
                            "ConnectionsSettings",
                            "getActivityName: retrieved landing page = " + str);
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("ConnectionsSettings", "getActivityName: Exception = " + e.getMessage());
                str = null;
            }
            if (!TextUtils.isEmpty(str)) {
                try {
                    getContext().getPackageManager().getPackageInfo("com.samsung.android.mdx", 0);
                    if (!z) {
                        SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData4 =
                                new SettingsPreferenceFragmentLinkData();
                        Intent intent3 = new Intent();
                        intent3.setClassName("com.samsung.android.mdx", str);
                        intent3.putExtra("flowId", "9030");
                        settingsPreferenceFragmentLinkData4.intent = intent3;
                        settingsPreferenceFragmentLinkData4.topLevelKey = "top_level_multi_devices";
                        settingsPreferenceFragmentLinkData4.titleRes =
                                R.string.link_to_windows_title;
                        this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData4);
                    }
                } catch (PackageManager.NameNotFoundException e2) {
                    Log.e(
                            "ConnectionsSettings",
                            "checkTargetAppInstalled: Exception = " + e2.getMessage());
                }
            }
            Trace.endSection();
            Trace.beginSection("ConnectionsSettings#setLink_AndroidAuto");
            Log.i("ConnectionsSettings", "set Android Auto");
            getContext();
            Log.d("ConnectionsSettings", "Call getAndroidAutoTile");
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            DashboardCategory tilesForCategory =
                    featureFactoryImpl
                            .getDashboardFeatureProvider()
                            .getTilesForCategory("com.android.settings.category.ia.device");
            if (tilesForCategory != null) {
                Iterator it = ((ArrayList) tilesForCategory.getTiles()).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Tile tile2 = (Tile) it.next();
                    if ("com.google.android.projection.gearhead"
                            .equalsIgnoreCase(tile2.mComponentPackage)) {
                        tile2.mMetaData.putString("update", "update tile");
                        tile = tile2;
                        break;
                    }
                }
            } else {
                Log.d("ConnectionsSettings", "dashboardCategory is Null...return NULL");
            }
            if (tile != null) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData5 =
                        new SettingsPreferenceFragmentLinkData();
                Intent intent4 = new Intent(tile.mIntent);
                intent4.putExtra("flowId", "95021");
                settingsPreferenceFragmentLinkData5.intent = intent4;
                settingsPreferenceFragmentLinkData5.topLevelKey = "top_level_multi_devices";
                settingsPreferenceFragmentLinkData5.titleRes = R.string.android_auto_title;
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData5);
            }
            Trace.endSection();
            Trace.beginSection("ConnectionsSettings#setLink_QuickShare");
            if (Utils.hasPackage(getContext(), "com.samsung.android.app.sharelive")) {
                Log.i("ConnectionsSettings", "set Quick Share");
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData6 =
                        new SettingsPreferenceFragmentLinkData();
                Intent intent5 = new Intent("android.intent.action.MAIN");
                intent5.setClassName(
                        "com.samsung.android.app.sharelive",
                        "com.samsung.android.app.sharelive.presentation.settings.DeviceVisibilitySettingsActivity");
                intent5.putExtra("flowId", "95022");
                intent5.putExtra(
                        "callerMetric",
                        VolteConstants.ErrorCode.SIMULTANEOUS_CALL_LIMIT_HAS_ALREADY_BEEN_REACHED);
                settingsPreferenceFragmentLinkData6.intent = intent5;
                settingsPreferenceFragmentLinkData6.topLevelKey = "top_level_multi_devices";
                try {
                    Bundle bundle =
                            getContext()
                                    .getPackageManager()
                                    .getApplicationInfo("com.samsung.android.app.sharelive", 128)
                                    .metaData;
                    if (bundle != null) {
                        if (bundle.getBoolean(
                                "com.samsung.android.app.sharelive.supportChinaP2p", false)) {
                            settingsPreferenceFragmentLinkData6.titleRes =
                                    R.string.quickshare_title_china;
                            if (Settings.System.getInt(
                                            getContext().getContentResolver(),
                                            "quickshare_activated",
                                            1)
                                    != 1) {
                                throw new PackageManager.NameNotFoundException(
                                        "QuickShare disabled");
                            }
                        } else {
                            settingsPreferenceFragmentLinkData6.titleRes =
                                    R.string.quickshare_title;
                        }
                        this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData6);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.d("ConnectionsSettings", "PackageManager could not find QuickShare");
                }
            }
            Trace.endSection();
            Trace.beginSection("ConnectionsSettings#setLink_create");
            LinearLayout linearLayout = this.mRelativeLinkView.mLinkContainer;
            if (linearLayout != null && linearLayout.getChildCount() <= 0) {
                Log.d("RelativeLinkView", "The current screen doesn't have link data.");
            } else if (getFooterView() == null) {
                this.mRelativeLinkView.create(this);
            }
            Trace.endSection();
        }
        Trace.endSection();
        Trace.endSection();
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        Log.i("ConnectionsSettings", "onStart");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        this.mRegisterIntent = getActivity().registerReceiver(this.mReceiver, intentFilter, 2);
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        Log.i("ConnectionsSettings", "onStop");
        getActivity().unregisterReceiver(this.mReceiver);
    }
}
