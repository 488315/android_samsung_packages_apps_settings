package com.samsung.android.settings.wifi.mobileap.hotspotlabs;

import android.os.Bundle;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.dashboard.DashboardFragment;

import com.github.mikephil.charting.data.Entry;
import com.samsung.android.settings.wifi.mobileap.datamodels.chart.WifiApChartDataModel;
import com.samsung.android.settings.wifi.mobileap.datamodels.hotspotlab.WifiApHotspotLabModel;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApHotspotLabUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApLineChartPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApHotspotSpeedHistory extends DashboardFragment
        implements OnActivityResultListener {
    public WifiApLineChartPreference wifiApLineChartPreference;
    public List mWifiApHotspotLabModelList = new ArrayList();
    public final List mWifiApHotspotSpeedHistoryList = new ArrayList();
    public final List mWifiApTrimmedHotspotSpeedHistoryList = new ArrayList();
    public final List mDateTimeList = new ArrayList();
    public final List mTrimmedDateTimeList = new ArrayList();

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiApHotspotSpeedHistory";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_ap_hotspot_lab_speed_history;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("WifiApHotspotSpeedHistory", "onCreate: ");
        getContext();
        this.wifiApLineChartPreference =
                (WifiApLineChartPreference)
                        getPreferenceScreen().findPreference("speed_line_chart_preference");
        this.mWifiApHotspotLabModelList =
                WifiApHotspotLabUtils.getHotspotLabModelList(
                        "#tag_wifi_ap_lab_hotspot_speed_event#");
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("WifiApHotspotSpeedHistory", "onResume called");
        this.mWifiApHotspotLabModelList =
                WifiApHotspotLabUtils.getHotspotLabModelList(
                        "#tag_wifi_ap_lab_hotspot_speed_event#");
        int i = 0;
        for (int i2 = 0; i2 < this.mWifiApHotspotLabModelList.size(); i2++) {
            ((ArrayList) this.mWifiApHotspotSpeedHistoryList)
                    .add(
                            Float.valueOf(
                                    Float.parseFloat(
                                                    ((WifiApHotspotLabModel)
                                                                    this.mWifiApHotspotLabModelList
                                                                            .get(i2))
                                                            .mEventLog)
                                            * 0.001f));
            ((ArrayList) this.mDateTimeList)
                    .add(
                            Long.valueOf(
                                    ((WifiApHotspotLabModel)
                                                    this.mWifiApHotspotLabModelList.get(i2))
                                            .mEventDateTimeInMillis));
        }
        for (int i3 = 0;
                i3 < Math.min(200, ((ArrayList) this.mWifiApHotspotSpeedHistoryList).size());
                i3++) {
            ((ArrayList) this.mWifiApTrimmedHotspotSpeedHistoryList)
                    .add((Float) ((ArrayList) this.mWifiApHotspotSpeedHistoryList).get(i3));
            ((ArrayList) this.mTrimmedDateTimeList)
                    .add((Long) ((ArrayList) this.mDateTimeList).get(i3));
        }
        Collections.reverse(this.mWifiApTrimmedHotspotSpeedHistoryList);
        Collections.reverse(this.mWifiApHotspotSpeedHistoryList);
        Collections.reverse(this.mDateTimeList);
        Collections.reverse(this.mTrimmedDateTimeList);
        Log.d("WifiApHotspotSpeedHistory", "FULL LIST" + this.mWifiApHotspotSpeedHistoryList);
        Log.d(
                "WifiApHotspotSpeedHistory",
                "TRIMMED LIST" + this.mWifiApTrimmedHotspotSpeedHistoryList);
        new ArrayList();
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) this.mTrimmedDateTimeList).iterator();
        while (it.hasNext()) {
            long longValue = ((Long) it.next()).longValue();
            Log.i("WifiApHotspotSpeedHistory", "adding time : " + longValue);
            arrayList.add(String.valueOf(longValue));
        }
        WifiApChartDataModel wifiApChartDataModel =
                new WifiApChartDataModel(arrayList, this.mWifiApTrimmedHotspotSpeedHistoryList);
        WifiApLineChartPreference wifiApLineChartPreference = this.wifiApLineChartPreference;
        wifiApLineChartPreference.mChartDataModel = wifiApChartDataModel;
        Iterator it2 = wifiApChartDataModel.mYaxisValueList.iterator();
        while (it2.hasNext()) {
            ((ArrayList) wifiApLineChartPreference.mEntryList)
                    .add(new Entry(i, ((Float) it2.next()).floatValue()));
            i++;
        }
    }
}
