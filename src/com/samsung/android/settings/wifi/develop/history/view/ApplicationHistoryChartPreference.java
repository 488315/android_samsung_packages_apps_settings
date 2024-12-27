package com.samsung.android.settings.wifi.develop.history.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.compose.runtime.PrioritySet$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.wifi.develop.history.model.TcpSocketStats;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ApplicationHistoryChartPreference extends Preference {
    public HistoryView mChart;
    public Spinner mSpinner;
    public final Map mTcpSocketStats;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.develop.history.view.ApplicationHistoryChartPreference$1TcpChart, reason: invalid class name */
    public final class C1TcpChart extends ChartDataFactory$BaseChartData {
        public long mMaxSockets;
        public long mMinSockets;

        @Override // com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData
        public final long getDomainAxisTick() {
            return 1000L;
        }

        @Override // com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData
        public final CharSequence[] getValueAxisLabels() {
            long j = this.mMaxSockets;
            long j2 = this.mMinSockets;
            return new CharSequence[] {
                String.valueOf(j), String.valueOf((j + j2) / 2), String.valueOf(j2)
            };
        }

        @Override // com.samsung.android.settings.wifi.develop.history.view.ChartDataFactory$BaseChartData
        public final long getValueAxisOffset() {
            return this.mMinSockets;
        }
    }

    public ApplicationHistoryChartPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTcpSocketStats = new HashMap();
        setLayoutResource(R.layout.sec_wifi_development_history_chart_fragment);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        this.mChart = (HistoryView) view.findViewById(R.id.chart_history_view);
        Context context = getContext();
        if (context == null) {
            Log.e("ApplicationHistoryChartPreference", "Context is null");
        } else {
            SemWifiManager semWifiManager =
                    (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            if (semWifiManager == null) {
                Log.e("ApplicationHistoryChartPreference", "Fail to get SemWifiManager");
            } else {
                String tcpMonitorSocketForegroundHistory =
                        semWifiManager.getTcpMonitorSocketForegroundHistory(600);
                if (tcpMonitorSocketForegroundHistory != null) {
                    Map parseEntry = TcpSocketStats.parseEntry(tcpMonitorSocketForegroundHistory);
                    ((HashMap) this.mTcpSocketStats).clear();
                    ((HashMap) this.mTcpSocketStats).putAll(parseEntry);
                } else {
                    Log.e(
                            "ApplicationHistoryChartPreference",
                            "TcpMonitorSocketForegroundHistory is null");
                }
            }
        }
        if (((HashMap) this.mTcpSocketStats).isEmpty()) {
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
        ArrayAdapter arrayAdapter =
                new ArrayAdapter(
                        context2,
                        android.R.layout.simple_spinner_item,
                        (String[])
                                ((HashMap) this.mTcpSocketStats).keySet().toArray(new String[0]));
        arrayAdapter.setDropDownViewResource(R.layout.sec_simple_spinner_dropdown_item);
        this.mSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.mSpinner.setOnItemSelectedListener(
                new AdapterView
                        .OnItemSelectedListener() { // from class:
                                                    // com.samsung.android.settings.wifi.develop.history.view.ApplicationHistoryChartPreference.1
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onItemSelected(
                            AdapterView adapterView, View view2, int i, long j) {
                        String obj =
                                ApplicationHistoryChartPreference.this
                                        .mSpinner
                                        .getSelectedItem()
                                        .toString();
                        ApplicationHistoryChartPreference applicationHistoryChartPreference =
                                ApplicationHistoryChartPreference.this;
                        if (applicationHistoryChartPreference.mChart == null) {
                            Log.e(
                                    "ApplicationHistoryChartPreference",
                                    "changeItem, but chart is null");
                            return;
                        }
                        List<TcpSocketStats> list =
                                (List)
                                        ((HashMap)
                                                        applicationHistoryChartPreference
                                                                .mTcpSocketStats)
                                                .get(obj);
                        C1TcpChart c1TcpChart = new C1TcpChart();
                        c1TcpChart.mPoints = new ArrayList();
                        c1TcpChart.mSecondPoints = new ArrayList();
                        c1TcpChart.mMinSockets = Long.MAX_VALUE;
                        if (list != null && !list.isEmpty()) {
                            c1TcpChart.mStartTime = ((TcpSocketStats) list.get(0)).mDate.getTime();
                            c1TcpChart.mMaxDomainValue =
                                    ((TcpSocketStats)
                                                            PrioritySet$$ExternalSyntheticOutline0
                                                                    .m(1, list))
                                                    .mDate.getTime()
                                            - c1TcpChart.mStartTime;
                            for (TcpSocketStats tcpSocketStats : list) {
                                c1TcpChart.mMinSockets =
                                        Math.min(
                                                tcpSocketStats.mGoodSockets,
                                                c1TcpChart.mMinSockets);
                                c1TcpChart.mMaxSockets =
                                        Math.max(
                                                tcpSocketStats.mGoodSockets
                                                        + tcpSocketStats.mWaitSockets
                                                        + tcpSocketStats.mSlowSockets,
                                                c1TcpChart.mMaxSockets);
                            }
                            for (TcpSocketStats tcpSocketStats2 : list) {
                                long j2 =
                                        ((tcpSocketStats2.mGoodSockets
                                                                + tcpSocketStats2.mWaitSockets)
                                                        + tcpSocketStats2.mSlowSockets)
                                                - c1TcpChart.mMinSockets;
                                ((ArrayList) c1TcpChart.mPoints)
                                        .add(
                                                Pair.create(
                                                        Long.valueOf(
                                                                tcpSocketStats2.mDate.getTime()
                                                                        - c1TcpChart.mStartTime),
                                                        Long.valueOf(j2)));
                                c1TcpChart.mMaxRangeValue = Math.max(j2, c1TcpChart.mMaxRangeValue);
                            }
                            for (TcpSocketStats tcpSocketStats3 : list) {
                                long j3 = tcpSocketStats3.mGoodSockets - c1TcpChart.mMinSockets;
                                ((ArrayList) c1TcpChart.mSecondPoints)
                                        .add(
                                                Pair.create(
                                                        Long.valueOf(
                                                                tcpSocketStats3.mDate.getTime()
                                                                        - c1TcpChart.mStartTime),
                                                        Long.valueOf(j3)));
                                c1TcpChart.mMaxRangeValue = Math.max(j3, c1TcpChart.mMaxRangeValue);
                            }
                        }
                        applicationHistoryChartPreference.mChart.clearPaths();
                        HistoryView historyView = applicationHistoryChartPreference.mChart;
                        long j4 = c1TcpChart.mMaxDomainValue;
                        if (j4 <= 0) {
                            j4 = 1;
                        }
                        long j5 = c1TcpChart.mMaxRangeValue;
                        historyView.mMaxDomainValue = j4;
                        HistoryGraph historyGraph = historyView.mHistoryGraph;
                        historyGraph.mMaxX = j4;
                        historyGraph.mMaxY = j5;
                        historyGraph.calculateLocalPaths(
                                historyGraph.mPaths, historyGraph.mLocalPaths);
                        historyGraph.calculateLocalPaths(
                                historyGraph.mSecondPaths, historyGraph.mLocalSecondPaths);
                        historyGraph.postInvalidate();
                        HistoryView historyView2 = applicationHistoryChartPreference.mChart;
                        HistoryGraph historyGraph2 = historyView2.mHistoryGraph;
                        historyGraph2.mScatterRadius =
                                2 * historyGraph2.mDisplayMetrics.scaledDensity;
                        historyGraph2.mScatterDensity = 0.3f;
                        historyView2.setValueAxisLabels(c1TcpChart.getValueAxisLabels());
                        HistoryView historyView3 = applicationHistoryChartPreference.mChart;
                        historyView3.mHistoryGraph.mValueAxisTick = 1000L;
                        historyView3.mDomainAxisFormat =
                                ChartDataFactory$BaseChartData.DEFAULT_DATE_FORMAT;
                        Date[] domainAxisLabels = c1TcpChart.getDomainAxisLabels();
                        if (domainAxisLabels.length != historyView3.mDomainAxisLabels.length) {
                            throw new IllegalArgumentException("Invalid number of labels");
                        }
                        historyView3.mDomainAxisValues = domainAxisLabels;
                        HistoryView historyView4 = applicationHistoryChartPreference.mChart;
                        long j6 = c1TcpChart.mMinSockets;
                        HistoryGraph historyGraph3 = historyView4.mHistoryGraph;
                        historyGraph3.mValueAxisOffset = j6;
                        historyGraph3.mShowValueLabel = true;
                        historyView4.mHistoryGraph.mLinePaint.setColor(Color.rgb(180, 180, 180));
                        HistoryView historyView5 = applicationHistoryChartPreference.mChart;
                        int rgb = Color.rgb(180, 180, 180);
                        historyView5.mLegend1.setText("Total sockets(Good, Wait, Slow)");
                        historyView5.mLegend1.setTextColor(rgb);
                        historyView5.mLegend1.setVisibility(0);
                        applicationHistoryChartPreference.mChart.mHistoryGraph.mSecondLinePaint
                                .setColor(Color.rgb(63, 145, 255));
                        HistoryView historyView6 = applicationHistoryChartPreference.mChart;
                        int rgb2 = Color.rgb(63, 145, 255);
                        historyView6.mLegend2.setText("Good sockets");
                        historyView6.mLegend2.setTextColor(rgb2);
                        historyView6.mLegend2.setVisibility(0);
                        HistoryView historyView7 = applicationHistoryChartPreference.mChart;
                        List list2 = c1TcpChart.mPoints;
                        HistoryGraph historyGraph4 = historyView7.mHistoryGraph;
                        List list3 = historyGraph4.mPaths;
                        List list4 = historyGraph4.mLocalPaths;
                        ArrayList arrayList = (ArrayList) list3;
                        arrayList.addAll(list2);
                        historyGraph4.calculateLocalPaths(arrayList, list4);
                        HistoryView historyView8 = applicationHistoryChartPreference.mChart;
                        List list5 = c1TcpChart.mSecondPoints;
                        HistoryGraph historyGraph5 = historyView8.mHistoryGraph;
                        List list6 = historyGraph5.mSecondPaths;
                        List list7 = historyGraph5.mLocalSecondPaths;
                        ArrayList arrayList2 = (ArrayList) list6;
                        arrayList2.addAll(list5);
                        historyGraph5.calculateLocalPaths(arrayList2, list7);
                        applicationHistoryChartPreference.mChart.mHistoryGraph.postInvalidate();
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onNothingSelected(AdapterView adapterView) {}
                });
    }
}
