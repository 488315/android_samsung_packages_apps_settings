package com.samsung.android.settings.wifi.develop.history.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.settings.R;
import com.samsung.android.settings.wifi.develop.history.model.UsabilityStats;
import com.samsung.android.util.SemLog;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class TimeHistoryChartPreference extends Preference {
    public HistoryView mChart;
    public Spinner mSpinner;
    public final List mUsabilityStats;

    public TimeHistoryChartPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mUsabilityStats = new ArrayList();
        setLayoutResource(R.layout.sec_wifi_development_history_chart_fragment);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        this.mChart = (HistoryView) view.findViewById(R.id.chart_history_view);
        Context context = getContext();
        if (context == null) {
            SemLog.e("TimeHistoryChartPreference", "Fail to get Context");
        } else {
            SemWifiManager semWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            if (semWifiManager == null) {
                SemLog.e("TimeHistoryChartPreference", "Fail to get SemWifiManager");
            } else {
                String wifiUsabilityStatsEntry = semWifiManager.getWifiUsabilityStatsEntry(2400);
                if (wifiUsabilityStatsEntry != null) {
                    List parseEntry = UsabilityStats.parseEntry(wifiUsabilityStatsEntry);
                    if (!((ArrayList) parseEntry).isEmpty()) {
                        ((ArrayList) this.mUsabilityStats).clear();
                        ((ArrayList) this.mUsabilityStats).addAll(parseEntry);
                    }
                } else {
                    SemLog.e("TimeHistoryChartPreference", "WifiUsabilityStatsEntry is null");
                }
            }
        }
        if (((ArrayList) this.mUsabilityStats).isEmpty()) {
            view.findViewById(R.id.chart_notification_no_available_log).setVisibility(0);
            view.findViewById(R.id.chart_spinner).setVisibility(8);
            view.findViewById(R.id.chart_history_view).setVisibility(8);
            return;
        }
        view.findViewById(R.id.chart_notification_no_available_log).setVisibility(8);
        view.findViewById(R.id.chart_spinner).setVisibility(0);
        view.findViewById(R.id.chart_history_view).setVisibility(0);
        Context context2 = getContext();
        this.mSpinner = (Spinner) view.findViewById(R.id.chart_spinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(context2, android.R.layout.simple_spinner_item, getContext().getResources().getStringArray(R.array.time_history_entries));
        arrayAdapter.setDropDownViewResource(R.layout.sec_simple_spinner_dropdown_item);
        this.mSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.samsung.android.settings.wifi.develop.history.view.TimeHistoryChartPreference.1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r7v14, types: [com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData, com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$TxBadChartData] */
            /* JADX WARN: Type inference failed for: r7v15, types: [com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData, com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$TxBadChartData] */
            /* JADX WARN: Type inference failed for: r7v16, types: [com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData, com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$TxBadChartData] */
            /* JADX WARN: Type inference failed for: r7v17, types: [com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData, com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$TxBadChartData] */
            /* JADX WARN: Type inference failed for: r7v18, types: [com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData] */
            /* JADX WARN: Type inference failed for: r7v20, types: [com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData, com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$RssiChartData] */
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onItemSelected(AdapterView adapterView, View view2, int i, long j) {
                ?? chartDataFactory$TxBadChartData;
                String obj = TimeHistoryChartPreference.this.mSpinner.getSelectedItem().toString();
                TimeHistoryChartPreference timeHistoryChartPreference = TimeHistoryChartPreference.this;
                if (timeHistoryChartPreference.mChart == null || ((ArrayList) timeHistoryChartPreference.mUsabilityStats).isEmpty()) {
                    SemLog.e("TimeHistoryChartPreference", "changeItem, but chart is null or stats is empty");
                    return;
                }
                if ("RSSI".equals(obj)) {
                    List list = timeHistoryChartPreference.mUsabilityStats;
                    chartDataFactory$TxBadChartData = new ChartDataFactory$RssiChartData(list);
                    chartDataFactory$TxBadChartData.mMaxRssi = -128;
                    ArrayList arrayList = (ArrayList) list;
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        UsabilityStats usabilityStats = (UsabilityStats) it.next();
                        chartDataFactory$TxBadChartData.mMinRssi = Math.min(usabilityStats.mRssi, chartDataFactory$TxBadChartData.mMinRssi);
                        chartDataFactory$TxBadChartData.mMaxRssi = Math.max(usabilityStats.mRssi, chartDataFactory$TxBadChartData.mMaxRssi);
                    }
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        UsabilityStats usabilityStats2 = (UsabilityStats) it2.next();
                        int abs = Math.abs(chartDataFactory$TxBadChartData.mMinRssi) + usabilityStats2.mRssi;
                        long j2 = abs;
                        ((ArrayList) chartDataFactory$TxBadChartData.mPoints).add(Pair.create(Long.valueOf(usabilityStats2.mTimeStampMillis - chartDataFactory$TxBadChartData.mStartTime), Long.valueOf(j2)));
                        chartDataFactory$TxBadChartData.mMaxRangeValue = Math.max(j2, chartDataFactory$TxBadChartData.mMaxRangeValue);
                    }
                } else if ("BcnCnt".equals(obj)) {
                    List list2 = timeHistoryChartPreference.mUsabilityStats;
                    chartDataFactory$TxBadChartData = new ChartDataFactory$TxBadChartData(1, list2);
                    chartDataFactory$TxBadChartData.mMinTxBad = Long.MAX_VALUE;
                    ArrayList arrayList2 = (ArrayList) list2;
                    Iterator it3 = arrayList2.iterator();
                    while (it3.hasNext()) {
                        UsabilityStats usabilityStats3 = (UsabilityStats) it3.next();
                        chartDataFactory$TxBadChartData.mMinTxBad = Math.min(usabilityStats3.mTotalBeaconRx, chartDataFactory$TxBadChartData.mMinTxBad);
                        chartDataFactory$TxBadChartData.mMaxTxBad = Math.max(usabilityStats3.mTotalBeaconRx, chartDataFactory$TxBadChartData.mMaxTxBad);
                    }
                    Iterator it4 = arrayList2.iterator();
                    while (it4.hasNext()) {
                        UsabilityStats usabilityStats4 = (UsabilityStats) it4.next();
                        long j3 = usabilityStats4.mTotalBeaconRx - chartDataFactory$TxBadChartData.mMinTxBad;
                        ((ArrayList) chartDataFactory$TxBadChartData.mPoints).add(Pair.create(Long.valueOf(usabilityStats4.mTimeStampMillis - chartDataFactory$TxBadChartData.mStartTime), Long.valueOf(j3)));
                        chartDataFactory$TxBadChartData.mMaxRangeValue = Math.max(j3, chartDataFactory$TxBadChartData.mMaxRangeValue);
                    }
                } else if ("TxRetry".equals(obj)) {
                    List list3 = timeHistoryChartPreference.mUsabilityStats;
                    chartDataFactory$TxBadChartData = new ChartDataFactory$TxBadChartData(2, list3);
                    chartDataFactory$TxBadChartData.mMinTxBad = Long.MAX_VALUE;
                    ArrayList arrayList3 = (ArrayList) list3;
                    Iterator it5 = arrayList3.iterator();
                    while (it5.hasNext()) {
                        UsabilityStats usabilityStats5 = (UsabilityStats) it5.next();
                        chartDataFactory$TxBadChartData.mMinTxBad = Math.min(usabilityStats5.mTotalTxRetries, chartDataFactory$TxBadChartData.mMinTxBad);
                        chartDataFactory$TxBadChartData.mMaxTxBad = Math.max(usabilityStats5.mTotalTxRetries, chartDataFactory$TxBadChartData.mMaxTxBad);
                    }
                    Iterator it6 = arrayList3.iterator();
                    while (it6.hasNext()) {
                        UsabilityStats usabilityStats6 = (UsabilityStats) it6.next();
                        long j4 = usabilityStats6.mTotalTxRetries - chartDataFactory$TxBadChartData.mMinTxBad;
                        ((ArrayList) chartDataFactory$TxBadChartData.mPoints).add(Pair.create(Long.valueOf(usabilityStats6.mTimeStampMillis - chartDataFactory$TxBadChartData.mStartTime), Long.valueOf(j4)));
                        chartDataFactory$TxBadChartData.mMaxRangeValue = Math.max(j4, chartDataFactory$TxBadChartData.mMaxRangeValue);
                    }
                } else if ("TxGood".equals(obj)) {
                    List list4 = timeHistoryChartPreference.mUsabilityStats;
                    chartDataFactory$TxBadChartData = new ChartDataFactory$TxBadChartData(3, list4);
                    chartDataFactory$TxBadChartData.mMinTxBad = Long.MAX_VALUE;
                    ArrayList arrayList4 = (ArrayList) list4;
                    Iterator it7 = arrayList4.iterator();
                    while (it7.hasNext()) {
                        UsabilityStats usabilityStats7 = (UsabilityStats) it7.next();
                        chartDataFactory$TxBadChartData.mMinTxBad = Math.min(usabilityStats7.mTotalTxSuccess, chartDataFactory$TxBadChartData.mMinTxBad);
                        chartDataFactory$TxBadChartData.mMaxTxBad = Math.max(usabilityStats7.mTotalTxSuccess, chartDataFactory$TxBadChartData.mMaxTxBad);
                    }
                    Iterator it8 = arrayList4.iterator();
                    while (it8.hasNext()) {
                        UsabilityStats usabilityStats8 = (UsabilityStats) it8.next();
                        long j5 = usabilityStats8.mTotalTxSuccess - chartDataFactory$TxBadChartData.mMinTxBad;
                        ((ArrayList) chartDataFactory$TxBadChartData.mPoints).add(Pair.create(Long.valueOf(usabilityStats8.mTimeStampMillis - chartDataFactory$TxBadChartData.mStartTime), Long.valueOf(j5)));
                        chartDataFactory$TxBadChartData.mMaxRangeValue = Math.max(j5, chartDataFactory$TxBadChartData.mMaxRangeValue);
                    }
                } else {
                    if (!"TxBad".equals(obj)) {
                        SemLog.e("TimeHistoryChartPreference", "Unsupported chart");
                        return;
                    }
                    List list5 = timeHistoryChartPreference.mUsabilityStats;
                    chartDataFactory$TxBadChartData = new ChartDataFactory$TxBadChartData(0, list5);
                    chartDataFactory$TxBadChartData.mMinTxBad = Long.MAX_VALUE;
                    ArrayList arrayList5 = (ArrayList) list5;
                    Iterator it9 = arrayList5.iterator();
                    while (it9.hasNext()) {
                        UsabilityStats usabilityStats9 = (UsabilityStats) it9.next();
                        chartDataFactory$TxBadChartData.mMinTxBad = Math.min(usabilityStats9.mTotalTxBad, chartDataFactory$TxBadChartData.mMinTxBad);
                        chartDataFactory$TxBadChartData.mMaxTxBad = Math.max(usabilityStats9.mTotalTxBad, chartDataFactory$TxBadChartData.mMaxTxBad);
                    }
                    Iterator it10 = arrayList5.iterator();
                    while (it10.hasNext()) {
                        UsabilityStats usabilityStats10 = (UsabilityStats) it10.next();
                        long j6 = usabilityStats10.mTotalTxBad - chartDataFactory$TxBadChartData.mMinTxBad;
                        ((ArrayList) chartDataFactory$TxBadChartData.mPoints).add(Pair.create(Long.valueOf(usabilityStats10.mTimeStampMillis - chartDataFactory$TxBadChartData.mStartTime), Long.valueOf(j6)));
                        chartDataFactory$TxBadChartData.mMaxRangeValue = Math.max(j6, chartDataFactory$TxBadChartData.mMaxRangeValue);
                    }
                }
                timeHistoryChartPreference.mChart.clearPaths();
                HistoryView historyView = timeHistoryChartPreference.mChart;
                long j7 = chartDataFactory$TxBadChartData.mMaxDomainValue;
                if (j7 <= 0) {
                    j7 = 1;
                }
                long j8 = chartDataFactory$TxBadChartData.mMaxRangeValue;
                historyView.mMaxDomainValue = j7;
                HistoryGraph historyGraph = historyView.mHistoryGraph;
                historyGraph.mMaxX = j7;
                historyGraph.mMaxY = j8;
                historyGraph.calculateLocalPaths(historyGraph.mPaths, historyGraph.mLocalPaths);
                historyGraph.calculateLocalPaths(historyGraph.mSecondPaths, historyGraph.mLocalSecondPaths);
                historyGraph.postInvalidate();
                HistoryView historyView2 = timeHistoryChartPreference.mChart;
                HistoryGraph historyGraph2 = historyView2.mHistoryGraph;
                historyGraph2.mScatterRadius = 2 * historyGraph2.mDisplayMetrics.scaledDensity;
                historyGraph2.mScatterDensity = 0.3f;
                historyView2.setValueAxisLabels(chartDataFactory$TxBadChartData.getValueAxisLabels());
                timeHistoryChartPreference.mChart.mHistoryGraph.mValueAxisTick = chartDataFactory$TxBadChartData.getDomainAxisTick();
                HistoryView historyView3 = timeHistoryChartPreference.mChart;
                historyView3.mDomainAxisFormat = ChartDataFactory$BaseChartData.DEFAULT_DATE_FORMAT;
                Date[] domainAxisLabels = chartDataFactory$TxBadChartData.getDomainAxisLabels();
                if (domainAxisLabels.length != historyView3.mDomainAxisLabels.length) {
                    throw new IllegalArgumentException("Invalid number of labels");
                }
                historyView3.mDomainAxisValues = domainAxisLabels;
                timeHistoryChartPreference.mChart.mHistoryGraph.mValueAxisOffset = chartDataFactory$TxBadChartData.getValueAxisOffset();
                timeHistoryChartPreference.mChart.mHistoryGraph.mShowValueLabel = chartDataFactory$TxBadChartData.showValueLabel();
                HistoryView historyView4 = timeHistoryChartPreference.mChart;
                List list6 = chartDataFactory$TxBadChartData.mPoints;
                HistoryGraph historyGraph3 = historyView4.mHistoryGraph;
                List list7 = historyGraph3.mPaths;
                List list8 = historyGraph3.mLocalPaths;
                ArrayList arrayList6 = (ArrayList) list7;
                arrayList6.addAll(list6);
                historyGraph3.calculateLocalPaths(arrayList6, list8);
                timeHistoryChartPreference.mChart.mHistoryGraph.postInvalidate();
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onNothingSelected(AdapterView adapterView) {
            }
        });
    }
}
