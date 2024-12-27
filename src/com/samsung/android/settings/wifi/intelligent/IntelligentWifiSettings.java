package com.samsung.android.settings.wifi.intelligent;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.telephony.SubscriptionManager;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.wifi.advanced.WifiFeatureUtils;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class IntelligentWifiSettings extends DashboardFragment
        implements OnDeveloperOptionEnabledListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.wifi_intelligent_settings);
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.intelligent.IntelligentWifiSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            WifiFeatureUtils wifiFeatureUtils = new WifiFeatureUtils(context);
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add(wifiFeatureUtils.getKeyIfUnsupported("auto_wifi"));
            arrayList.add(wifiFeatureUtils.getKeyIfUnsupported("ape"));
            arrayList.add(wifiFeatureUtils.getKeyIfUnsupported("wifi_poor_network_detection"));
            arrayList.add(wifiFeatureUtils.getKeyIfUnsupported("auto_connect_hotspot"));
            arrayList.add(
                    wifiFeatureUtils.getKeyIfUnsupported(
                            "switch_to_better_wifi_with_screen_off_only"));
            arrayList.add(wifiFeatureUtils.getKeyIfUnsupported("switch_to_better_wifi"));
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            searchIndexableRaw.title =
                    String.valueOf(
                            WifiApUtils.getStringID(R.string.wifi_auto_connect_hotspot_title));
            searchIndexableRaw.screenTitle =
                    resources.getString(R.string.wifi_intelligent_wifi_header);
            ((SearchIndexableData) searchIndexableRaw).key = "auto_connect_hotspot";
            arrayList.add(searchIndexableRaw);
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
    /* renamed from: com.samsung.android.settings.wifi.intelligent.IntelligentWifiSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            byte b =
                    Settings.Global.getInt(
                                    context.getContentResolver(),
                                    "wifi_watchdog_poor_network_test_enabled",
                                    0)
                            == 1;
            String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            String str2 = b != false ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str2;
            statusData.mStatusKey = "1210";
            arrayList.add(statusData);
            ContentResolver contentResolver = context.getContentResolver();
            String str3 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            String str4 =
                    (Settings.Global.getInt(
                                                    contentResolver,
                                                    "sem_wifi_switch_to_better_wifi_enabled",
                                                    !"VZW".equals(Utils.getSalesCode()) ? 1 : 0)
                                            == 1)
                                    != false
                            ? "1"
                            : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = str4;
            statusData2.mStatusKey = "1221";
            arrayList.add(statusData2);
            String str5 =
                    SemWifiUtils.isAutoWifiEnabled(context)
                            ? "1"
                            : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData3 = new StatusData();
            statusData3.mStatusValue = str5;
            statusData3.mStatusKey = "1215";
            arrayList.add(statusData3);
            String str6 =
                    (Settings.Global.getInt(context.getContentResolver(), "sem_wifi_ape_enabled", 1)
                                            == 1)
                                    != false
                            ? "1"
                            : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData4 = new StatusData();
            statusData4.mStatusValue = str6;
            statusData4.mStatusKey = "1222";
            arrayList.add(statusData4);
            if (Settings.Secure.getInt(context.getContentResolver(), "wifi_mwips", 0) == 1) {
                str = "1";
            }
            StatusData statusData5 = new StatusData();
            statusData5.mStatusValue = str;
            statusData5.mStatusKey = "1343";
            arrayList.add(statusData5);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "IntelligentWifiSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.XLSB;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.wifi_intelligent_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        SALogging.insertSALog(
                ((WifiBadgeUtils.hasNewFavoriteNetwork(context)
                                        && SemWifiUtils.isAutoWifiEnabled(context))
                                || WifiBadgeUtils.isNewItemForWips(context))
                        ? 1L
                        : 0L,
                "WIFI_200",
                "1283",
                (String) null);
        ((IntelligentWifiVersionPreferenceController)
                        use(IntelligentWifiVersionPreferenceController.class))
                .setOnDeveloperOptionEnabledListener(this);
    }
}
