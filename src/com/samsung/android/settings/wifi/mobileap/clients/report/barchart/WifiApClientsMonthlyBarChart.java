package com.samsung.android.settings.wifi.mobileap.clients.report.barchart;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.samsung.android.settings.wifi.mobileap.clients.report.WifiApClientsReportBarChartPreference;
import com.samsung.android.settings.wifi.mobileap.clients.report.WifiApClientsReportCalendarRangeSelectorPreference;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.AbsRoundBarRenderer;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.AbsXAxisRenderer;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.DataChartMarkerMonthly;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.WeeklyXAxisRenderer;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.chart.WifiApDailyStackedProgressBarEntryConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.chart.WifiApMonthlyStackedProgressBarEntryConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApClientsMonthlyBarChart extends BarChart {
    public static final int[] STACKED_BAR_COLORS;
    public final AnonymousClass1 chartValueSelectedMonthly;
    public final Context mContext;
    public final List mDailyStackedProgressBarEntryConfigList;
    public AbsRoundBarRenderer mMonthlyBarChartRenderer;
    public BarData mMonthlyBarData;
    public BarDataSet mMonthlyBarDataSet;
    public final ArrayList mMonthlyBarEntryArrayList;
    public final ArrayList mMonthlyIBarDataSetArrayList;
    public DataChartMarkerMonthly mMonthlyMarkerView;
    public WifiApClientsReportBarChartPreference.AnonymousClass1 mOnBarSelectedCLickListener;
    public int mSelectedXAxisIndexInReverseOrder;
    public WifiApDataUsageConfig mTopWifiApDataUsageConfig;
    public WifiApMonthlyStackedProgressBarEntryConfig mWifiApMonthlyStackedProgressBarEntryConfig;
    public AbsXAxisRenderer mXAxisRendererMonthly;

    static {
        int convertToColorRGB = WifiApSettingsUtils.convertToColorRGB("#FFC0C9D8");
        STACKED_BAR_COLORS =
                new int[] {
                    WifiApSettingsUtils.convertToColorRGB("#FF387AFF"),
                    WifiApSettingsUtils.convertToColorRGB("#FF154050"),
                    WifiApSettingsUtils.convertToColorRGB("#FF0FBE7A"),
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB,
                    convertToColorRGB
                };
    }

    /* JADX WARN: Type inference failed for: r8v6, types: [com.samsung.android.settings.wifi.mobileap.clients.report.barchart.WifiApClientsMonthlyBarChart$1] */
    public WifiApClientsMonthlyBarChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        WifiApMonthlyStackedProgressBarEntryConfig wifiApMonthlyStackedProgressBarEntryConfig =
                new WifiApMonthlyStackedProgressBarEntryConfig();
        wifiApMonthlyStackedProgressBarEntryConfig.mDailyStackedProgressBarEntryConfigList =
                new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        new WifiApDataUsageConfig(0L);
        Date date = new Date(currentTimeMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Log.i("WifiApDailyStackedProgressBarEntryConfig", "Adding empty progress List");
        Log.i(
                "WifiApDailyStackedProgressBarEntryConfig",
                "Created Entry for Calendar: "
                        + calendar.getTime()
                        + ", list Count: "
                        + new ArrayList().size());
        this.mWifiApMonthlyStackedProgressBarEntryConfig =
                wifiApMonthlyStackedProgressBarEntryConfig;
        this.mTopWifiApDataUsageConfig = new WifiApDataUsageConfig(0L);
        this.mDailyStackedProgressBarEntryConfigList = new ArrayList();
        this.mMonthlyBarData = null;
        this.mMonthlyIBarDataSetArrayList = new ArrayList();
        this.mMonthlyBarDataSet = null;
        this.mMonthlyBarEntryArrayList = new ArrayList();
        this.chartValueSelectedMonthly =
                new OnChartValueSelectedListener() { // from class:
                                                     // com.samsung.android.settings.wifi.mobileap.clients.report.barchart.WifiApClientsMonthlyBarChart.1
                    @Override // com.github.mikephil.charting.listener.OnChartValueSelectedListener
                    public final void onNothingSelected() {
                        int[] iArr = WifiApClientsMonthlyBarChart.STACKED_BAR_COLORS;
                        Log.d("WifiApClientsMonthlyBarChart", "nothing selected X is ");
                    }

                    @Override // com.github.mikephil.charting.listener.OnChartValueSelectedListener
                    public final void onValueSelected(Entry entry, Highlight highlight) {
                        if (entry == null) {
                            return;
                        }
                        int i2 = (int) entry.x;
                        WifiApClientsMonthlyBarChart wifiApClientsMonthlyBarChart =
                                WifiApClientsMonthlyBarChart.this;
                        wifiApClientsMonthlyBarChart.mSelectedXAxisIndexInReverseOrder = 5 - i2;
                        int[] iArr = WifiApClientsMonthlyBarChart.STACKED_BAR_COLORS;
                        StringBuilder sb = new StringBuilder("Value: ");
                        sb.append(entry.getY());
                        sb.append(", xIndex: ");
                        sb.append(entry.x);
                        sb.append(", DataSet index: ");
                        Preference$$ExternalSyntheticOutline0.m(
                                sb, highlight.mDataSetIndex, "WifiApClientsMonthlyBarChart");
                        WeeklyXAxisRenderer weeklyXAxisRenderer =
                                (WeeklyXAxisRenderer)
                                        wifiApClientsMonthlyBarChart.mXAxisRendererMonthly;
                        weeklyXAxisRenderer.mSelectedInfoLoaded = true;
                        weeklyXAxisRenderer.mSelectedLabel =
                                5 - wifiApClientsMonthlyBarChart.mSelectedXAxisIndexInReverseOrder;
                        long j =
                                ((WifiApDailyStackedProgressBarEntryConfig)
                                                ((ArrayList)
                                                                wifiApClientsMonthlyBarChart
                                                                        .mDailyStackedProgressBarEntryConfigList)
                                                        .get(i2))
                                        .mDateInMillis;
                        Log.i(
                                "WifiApClientsMonthlyBarChart",
                                "updateBarSelectedListener: " + new Date(j));
                        WifiApClientsReportBarChartPreference.AnonymousClass1 anonymousClass1 =
                                wifiApClientsMonthlyBarChart.mOnBarSelectedCLickListener;
                        Long valueOf = Long.valueOf(j);
                        anonymousClass1.getClass();
                        int i3 = WifiApClientsReportBarChartPreference.$r8$clinit;
                        Log.i(
                                "WifiApClientsReportBarChartPreference",
                                "Monthly OnBarSelected: " + new Date(j));
                        WifiApClientsReportCalendarRangeSelectorPreference
                                wifiApClientsReportCalendarRangeSelectorPreference =
                                        WifiApClientsReportBarChartPreference.this
                                                .mWifiApHotspotUsageReport
                                                .mReportCalendarRangeSelectorPreference;
                        wifiApClientsReportCalendarRangeSelectorPreference.getClass();
                        Log.i(
                                "WifiApClientsReportCalendarRangeSelectorPreference",
                                "Settings Monthly Calendar for: " + new Date(j));
                        wifiApClientsReportCalendarRangeSelectorPreference
                                        .mSelectedMonthDateInMillis =
                                valueOf;
                        wifiApClientsReportCalendarRangeSelectorPreference.updatePreference();
                        wifiApClientsReportCalendarRangeSelectorPreference.mWifiApHotspotUsageReport
                                .mReportBarChartPreference.notifyChanged();
                    }
                };
        this.mContext = context;
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase,
              // com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
    public final BarLineScatterCandleBubbleData getData() {
        return this.mData;
    }

    public WifiApClientsMonthlyBarChart(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiApClientsMonthlyBarChart(Context context) {
        this(context, null);
    }
}
