package com.samsung.android.settings.wifi.mobileap.clients.report;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApHotspotUsageReport extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass2();
    public FragmentActivity mActivity;
    public Context mContext;
    public PreferenceScreen mPreferenceScreen;
    public WifiApClientsReportBarChartPreference mReportBarChartPreference;
    public WifiApClientsReportCalendarRangeSelectorPreference
            mReportCalendarRangeSelectorPreference;
    public final AnonymousClass1 mSemWifiApDataUsageUpdateCallback =
            new SemWifiManager
                    .SemWifiApClientListUpdateCallback() { // from class:
                                                           // com.samsung.android.settings.wifi.mobileap.clients.report.WifiApHotspotUsageReport.1
                public final void onClientListUpdated(List list, long j) {
                    BaseSearchIndexProvider baseSearchIndexProvider =
                            WifiApHotspotUsageReport.SEARCH_INDEX_DATA_PROVIDER;
                    Log.i(
                            "WifiApHotspotUsageReport",
                            "onClientListUpdated() : " + list.toString() + " : " + j);
                    WifiApHotspotUsageReport wifiApHotspotUsageReport =
                            WifiApHotspotUsageReport.this;
                    wifiApHotspotUsageReport.mReportCalendarRangeSelectorPreference
                            .updatePreference();
                    wifiApHotspotUsageReport.mReportBarChartPreference.notifyChanged();
                }

                public final void onOverallDataLimitChanged(long j) {
                    BaseSearchIndexProvider baseSearchIndexProvider =
                            WifiApHotspotUsageReport.SEARCH_INDEX_DATA_PROVIDER;
                    Log.i("WifiApHotspotUsageReport", "onOverallDataLimitChanged() : " + j);
                }
            };
    public SemWifiManager mSemWifiManager;
    public WifiApClientsMonthlyWeeklyTabPreference mWeeklyAndMonthlyTabPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.clients.report.WifiApHotspotUsageReport$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            if (UserHandle.myUserId() == 0
                    && WifiApFeatureUtils.isMobileDataUsageSupported(context)) {
                searchIndexableRaw.title =
                        String.valueOf(
                                WifiApUtils.getStringID(R.string.wifi_ap_hotspot_usage_report));
                searchIndexableRaw.screenTitle =
                        resources.getString(
                                WifiApUtils.getStringID(R.string.wifi_ap_hotspot_usage_report));
                arrayList.add(searchIndexableRaw);
            }
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiApHotspotUsageReport";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_ap_clients_report_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mWeeklyAndMonthlyTabPreference =
                (WifiApClientsMonthlyWeeklyTabPreference)
                        this.mPreferenceScreen.findPreference("weekly_monthly_tab_preference");
        this.mReportCalendarRangeSelectorPreference =
                (WifiApClientsReportCalendarRangeSelectorPreference)
                        this.mPreferenceScreen.findPreference(
                                "report_calendar_range_selector_preference");
        this.mReportBarChartPreference =
                (WifiApClientsReportBarChartPreference)
                        this.mPreferenceScreen.findPreference("report_bar_chart_preference");
        this.mWeeklyAndMonthlyTabPreference.mWifiApHotspotUsageReport = this;
        Log.i("WifiApClientsMonthlyWeeklyTabPreference", "Stack Chart setParent XYZ");
        this.mReportCalendarRangeSelectorPreference.mWifiApHotspotUsageReport = this;
        this.mReportBarChartPreference.mWifiApHotspotUsageReport = this;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.i("WifiApHotspotUsageReport", "onCreate");
        super.onCreate(bundle);
        this.mContext = getContext();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle(WifiApUtils.getStringID(R.string.wifi_ap_hotspot_usage_report));
        }
        this.mPreferenceScreen = getPreferenceScreen();
        this.mSemWifiManager =
                (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            Log.i("WifiApHotspotUsageReport", "Navigate Up clicked");
            SALogging.insertSALog("TETH_016", "8101");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        WifiApFrameworkUtils.setIsHotspotScreenOpened(this.mContext, false);
        this.mSemWifiManager.unregisterClientListDataUsageCallback(
                this.mSemWifiApDataUsageUpdateCallback);
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        WifiApFrameworkUtils.setIsHotspotScreenOpened(this.mContext, true);
        this.mSemWifiManager.registerClientListDataUsageCallback(
                this.mSemWifiApDataUsageUpdateCallback, this.mContext.getMainExecutor(), 3, 3);
        this.mReportCalendarRangeSelectorPreference.updatePreference();
        this.mReportBarChartPreference.notifyChanged();
    }
}
