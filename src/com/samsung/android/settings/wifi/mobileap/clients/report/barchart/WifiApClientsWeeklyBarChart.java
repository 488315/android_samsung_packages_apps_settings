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
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.AbsRoundBarRenderer;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.WeeklyChartXLabelFormatter;
import com.samsung.android.settings.wifi.mobileap.clients.report.chart.WeeklyXAxisRenderer;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.chart.WifiApDailyStackedProgressBarEntryConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.chart.WifiApWeeklyStackedProgressBarEntryConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApDateUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApClientsWeeklyBarChart extends BarChart {
    public static final int[] STACKED_BAR_COLORS;
    public final AnonymousClass1 chartGestureListener;
    public final AnonymousClass1 chartValueSelected;
    public final Context mContext;
    public boolean mCurrentWeekClick;
    public final List mDailyStackedProgressBarEntryConfigList;
    public float mIndexOfBarChartToBeHighlight;
    public WifiApClientsReportBarChartPreference.AnonymousClass1 mOnBarSelectedCLickListener;
    public final List mSelectedBarEntriesDataInMB;
    public int mShiftIndexFromRightToLeft;
    public boolean mSingleTapEnable;
    public WifiApDataUsageConfig mTopWifiApDataUsageConfig;
    public BarData mWeeklyBarData;
    public BarDataSet mWeeklyBarDataSet;
    public final ArrayList mWeeklyBarEntryArrayList;
    public AbsRoundBarRenderer mWeeklyBarRenderer;
    public final ArrayList mWeeklyIBarDataSets;
    public WifiApWeeklyStackedProgressBarEntryConfig mWeeklyStackedProgressBarEntryConfig;
    public WeeklyChartXLabelFormatter mXLabelFormatter;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.clients.report.barchart.WifiApClientsWeeklyBarChart$1, reason: invalid class name */
    public final class AnonymousClass1 implements OnChartValueSelectedListener {
        public /* synthetic */ AnonymousClass1() {}

        @Override // com.github.mikephil.charting.listener.OnChartValueSelectedListener
        public void onNothingSelected() {
            int[] iArr = WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS;
            Log.d("WifiApClientsWeeklyBarChart", "nothing selected X is ");
        }

        @Override // com.github.mikephil.charting.listener.OnChartValueSelectedListener
        public void onValueSelected(Entry entry, Highlight highlight) {
            if (entry == null) {
                return;
            }
            WifiApClientsWeeklyBarChart wifiApClientsWeeklyBarChart =
                    WifiApClientsWeeklyBarChart.this;
            if (wifiApClientsWeeklyBarChart.mSingleTapEnable) {
                wifiApClientsWeeklyBarChart.mSingleTapEnable = false;
                int i = (int) entry.x;
                wifiApClientsWeeklyBarChart.mShiftIndexFromRightToLeft = 6 - i;
                int[] iArr = WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS;
                StringBuilder sb = new StringBuilder();
                sb.append(entry);
                sb.append(" : Value: ");
                sb.append(entry.getY());
                sb.append(", xIndex: ");
                sb.append(entry.x);
                sb.append(", DataSet index: ");
                Preference$$ExternalSyntheticOutline0.m(
                        sb, highlight.mDataSetIndex, "WifiApClientsWeeklyBarChart");
                WeeklyXAxisRenderer weeklyXAxisRenderer =
                        (WeeklyXAxisRenderer) wifiApClientsWeeklyBarChart.mXAxisRenderer;
                weeklyXAxisRenderer.mSelectedInfoLoaded = true;
                weeklyXAxisRenderer.mSelectedLabel =
                        6 - wifiApClientsWeeklyBarChart.mShiftIndexFromRightToLeft;
                wifiApClientsWeeklyBarChart.updateBarSelectedListener$1(
                        ((WifiApDailyStackedProgressBarEntryConfig)
                                        ((ArrayList)
                                                        wifiApClientsWeeklyBarChart
                                                                .mDailyStackedProgressBarEntryConfigList)
                                                .get(i))
                                .mDateInMillis);
                wifiApClientsWeeklyBarChart.mCurrentWeekClick = false;
            }
        }
    }

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

    public WifiApClientsWeeklyBarChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDailyStackedProgressBarEntryConfigList = new ArrayList();
        this.mTopWifiApDataUsageConfig = new WifiApDataUsageConfig(0L);
        this.mCurrentWeekClick = true;
        new ArrayList();
        this.mSelectedBarEntriesDataInMB = new ArrayList();
        this.mWeeklyBarData = null;
        this.mWeeklyBarDataSet = null;
        this.mWeeklyIBarDataSets = new ArrayList();
        this.mWeeklyBarRenderer = null;
        this.mSingleTapEnable = false;
        this.mWeeklyBarEntryArrayList = new ArrayList();
        this.chartValueSelected = new AnonymousClass1();
        this.chartGestureListener = new AnonymousClass1();
        this.mContext = context;
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase,
              // com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
    public final BarLineScatterCandleBubbleData getData() {
        return this.mData;
    }

    public final void updateBarSelectedListener$1(long j) {
        Log.i("WifiApClientsWeeklyBarChart", "updateBarSelectedListener: " + new Date(j));
        WifiApWeeklyStackedProgressBarEntryConfig wifiApWeeklyStackedProgressBarEntryConfig =
                this.mWeeklyStackedProgressBarEntryConfig;
        if (wifiApWeeklyStackedProgressBarEntryConfig == null) {
            Log.d(
                    "WifiApClientsWeeklyBarChart",
                    "mWeeklyStackedProgressBarEntryConfig/mOnBarSelectedCLickListener is null");
            return;
        }
        wifiApWeeklyStackedProgressBarEntryConfig.getClass();
        WifiApDailyStackedProgressBarEntryConfig wifiApDailyStackedProgressBarEntryConfig =
                new WifiApDailyStackedProgressBarEntryConfig();
        Iterator it =
                wifiApWeeklyStackedProgressBarEntryConfig.mDailyStackedProgressBarEntryConfigList
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            WifiApDailyStackedProgressBarEntryConfig wifiApDailyStackedProgressBarEntryConfig2 =
                    (WifiApDailyStackedProgressBarEntryConfig) it.next();
            if (WifiApDateUtils.isEqualsDate(
                    wifiApDailyStackedProgressBarEntryConfig2.mDateInMillis, j)) {
                wifiApDailyStackedProgressBarEntryConfig =
                        wifiApDailyStackedProgressBarEntryConfig2;
                break;
            }
        }
        WifiApClientsReportBarChartPreference.AnonymousClass1 anonymousClass1 =
                this.mOnBarSelectedCLickListener;
        if (anonymousClass1 != null) {
            anonymousClass1.getClass();
            int i = WifiApClientsReportBarChartPreference.$r8$clinit;
            Log.i(
                    "WifiApClientsReportBarChartPreference",
                    "Weekly OnBarSelected: " + wifiApDailyStackedProgressBarEntryConfig);
            WifiApClientsReportBarChartPreference.this.updateDeviceList(
                    wifiApDailyStackedProgressBarEntryConfig);
        }
    }

    public WifiApClientsWeeklyBarChart(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiApClientsWeeklyBarChart(Context context) {
        this(context, null);
    }
}
