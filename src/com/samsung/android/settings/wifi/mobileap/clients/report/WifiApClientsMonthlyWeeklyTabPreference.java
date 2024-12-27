package com.samsung.android.settings.wifi.mobileap.clients.report;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.google.android.material.tabs.TabLayout;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApClientsMonthlyWeeklyTabPreference extends Preference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mTabIndexSelected;
    public WifiApHotspotUsageReport mWifiApHotspotUsageReport;

    public WifiApClientsMonthlyWeeklyTabPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTabIndexSelected = 0;
        setLayoutResource(R.layout.sec_wifi_ap_clients_monthly_weekly_tab_layout_preference);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TabLayout tabLayout =
                (TabLayout) preferenceViewHolder.findViewById(R.id.data_monitor_tab_layout);
        tabLayout.seslSetSubTabStyle();
        tabLayout.addOnTabSelectedListener$1(
                new TabLayout
                        .OnTabSelectedListener() { // from class:
                                                   // com.samsung.android.settings.wifi.mobileap.clients.report.WifiApClientsMonthlyWeeklyTabPreference.1
                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabSelected(TabLayout.Tab tab) {
                        int i = tab.position;
                        int i2 = WifiApClientsMonthlyWeeklyTabPreference.$r8$clinit;
                        Log.i(
                                "WifiApClientsMonthlyWeeklyTabPreference",
                                "onTabSelected() : " + i + " is selected");
                        WifiApClientsMonthlyWeeklyTabPreference
                                wifiApClientsMonthlyWeeklyTabPreference =
                                        WifiApClientsMonthlyWeeklyTabPreference.this;
                        wifiApClientsMonthlyWeeklyTabPreference.mTabIndexSelected = i;
                        SALogging.insertSALog(i == 1 ? 1L : 0L, "TETH_016", "8091", (String) null);
                        WifiApHotspotUsageReport wifiApHotspotUsageReport =
                                wifiApClientsMonthlyWeeklyTabPreference.mWifiApHotspotUsageReport;
                        wifiApHotspotUsageReport.mReportCalendarRangeSelectorPreference
                                .updatePreference();
                        wifiApHotspotUsageReport.mReportBarChartPreference.notifyChanged();
                    }

                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabUnselected(TabLayout.Tab tab) {}
                });
    }

    public WifiApClientsMonthlyWeeklyTabPreference(
            Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiApClientsMonthlyWeeklyTabPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiApClientsMonthlyWeeklyTabPreference(Context context) {
        this(context, null);
    }
}
