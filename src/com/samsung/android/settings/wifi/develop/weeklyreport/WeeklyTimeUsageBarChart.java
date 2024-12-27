package com.samsung.android.settings.wifi.develop.weeklyreport;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.format.Formatter;
import android.util.AttributeSet;

import com.android.settings.R;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.develop.WifiLabsWeeklyReportPreference;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WeeklyTimeUsageBarChart extends BarChart {
    public static final int[] TIME_BAR_COLORS = {
        convertToColorRGB("#FFe4e4e4"), convertToColorRGB("#FF2ab5e2")
    };
    public static final int[] USAGE_BAR_COLORS = {
        convertToColorRGB("#FFe8ccf4"), convertToColorRGB("#FFca84e8")
    };
    public WifiLabsWeeklyReportPreference.AnonymousClass2 callbackListener;
    public final AnonymousClass1 chartValueSelected;
    public final Context mContext;
    public float mMaxYValue;
    public int mShiftIndex;
    public BarDataSet mWeeklyBarDataSet;
    public ArrayList mWeeklyBarEntryArrayList;
    public BarData mWeeklyBardata;
    public final ArrayList mWeeklyIBarDataSet;
    public WeeklyXAxisRenderer mXAxisRendererMonthly;
    public WeeklyChartXLabelFormatter mXLabelFormatter;

    public WeeklyTimeUsageBarChart(Context context) {
        this(context, null);
    }

    public static int convertToColorRGB(String str) {
        int parseLong = (int) Long.parseLong(str.replace("#", ApnSettings.MVNO_NONE), 16);
        return Color.rgb((parseLong >> 16) & 255, (parseLong >> 8) & 255, parseLong & 255);
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase,
              // com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
    public final BarLineScatterCandleBubbleData getData() {
        return this.mData;
    }

    public final void initialize() {
        this.mLegend.mEnabled = false;
        this.mHighlightFullBarEnabled = true;
        setHovered(true);
        this.mDescription.mEnabled = false;
        this.mScaleXEnabled = false;
        this.mScaleYEnabled = false;
        this.mDoubleTapToZoomEnabled = false;
        this.mDragXEnabled = true;
        this.mDragYEnabled = true;
        setVisibleXRangeMaximum(7.0f);
        this.mAutoScaleMinMaxEnabled = true;
        this.mSelectionListener = this.chartValueSelected;
        Context context = this.mContext;
        WeeklyXAxisRenderer weeklyXAxisRenderer =
                new WeeklyXAxisRenderer(
                        this.mViewPortHandler,
                        this.mXAxis,
                        getTransformer(YAxis.AxisDependency.RIGHT));
        weeklyXAxisRenderer.mSelectedInfoLoaded = false;
        Paint paint = new Paint();
        weeklyXAxisRenderer.mSelectedLabelPaint = paint;
        paint.setColor(
                context.getResources()
                        .getColor(
                                R.color.wifi_barchart_index_selected_text_color,
                                context.getTheme()));
        paint.setTextSize(
                context.getResources()
                        .getDimension(R.dimen.wifi_weekly_report_data_index_selected_text_size));
        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);
        Paint paint2 = new Paint();
        weeklyXAxisRenderer.mLabelPaint = paint2;
        paint2.setColor(context.getColor(R.color.wifi_barchart_secondary_text_color));
        paint2.setTextSize(
                context.getResources()
                        .getDimension(R.dimen.wifi_weekly_report_data_index_text_size));
        paint2.setFakeBoldText(false);
        paint2.setAntiAlias(true);
        weeklyXAxisRenderer.mLabelMargin =
                context.getResources()
                        .getDimensionPixelSize(
                                R.dimen.wifi_weekly_report_dashboard_chart_x_label_margin_top);
        Paint paint3 = new Paint();
        weeklyXAxisRenderer.mSelectedLabelBgPaint = paint3;
        paint3.setColor(
                context.getResources()
                        .getColor(R.color.wifi_barchart_marker_bg_line_color, context.getTheme()));
        paint3.setStyle(Paint.Style.FILL);
        paint3.setAntiAlias(true);
        weeklyXAxisRenderer.mLabelBgRadius =
                context.getResources()
                        .getDimensionPixelSize(
                                R.dimen.wifi_weekly_report_dashboard_chart_selected_label_radius);
        weeklyXAxisRenderer.mLabelBgYOffset =
                context.getResources()
                        .getDimensionPixelSize(
                                R.dimen.wifi_weekly_report_dashboard_chart_selected_label_y_offset);
        this.mXAxisRendererMonthly = weeklyXAxisRenderer;
        this.mXAxisRenderer = weeklyXAxisRenderer;
        setExtraOffsets(26.0f, 16.0f);
    }

    /* JADX WARN: Type inference failed for: r12v3, types: [java.time.LocalDateTime] */
    public final void updateBarGraph(int i, long[] jArr, long[] jArr2) {
        float f;
        float f2;
        this.mWeeklyBarEntryArrayList = new ArrayList();
        int i2 = -1;
        int i3 = -1;
        for (int i4 = 0; i4 < 7; i4++) {
            if (i == 0) {
                int i5 = 6 - i4;
                f = jArr[i5] / 3600000.0f;
                f2 = (jArr2[i5] / 3600000.0f) - f;
                float f3 = f + f2;
                if (i2 < f3) {
                    i2 = ((int) f3) + 1;
                }
                if (f3 > 24.0f) {
                    if (f > 24.0f) {
                        f = 24.0f;
                    }
                    f2 = 24.0f - f;
                }
            } else {
                int i6 = 6 - i4;
                f = jArr[i6] / 1000000.0f;
                f2 = jArr2[i6] / 1000000.0f;
                float f4 = f + f2;
                if (i3 < f4) {
                    i3 = (int) f4;
                }
            }
            this.mWeeklyBarEntryArrayList.add(new BarEntry(i4, new float[] {f, f2}));
        }
        if (i == 0) {
            int i7 = 24;
            if (i2 <= 24) {
                int i8 = 0;
                while (true) {
                    if (i8 >= 3) {
                        i7 = 0;
                        break;
                    }
                    int i9 = i2 + i8;
                    if (i9 % 3 == 0) {
                        i7 = i9;
                        break;
                    }
                    i8++;
                }
            }
            this.mMaxYValue = i7;
        }
        if (i == 1) {
            if (i3 < 3) {
                this.mMaxYValue = 3.0f;
            } else {
                this.mMaxYValue = 0.0f;
            }
        }
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            for (int i10 = 0; i10 < 2; i10++) {
                arrayList.add(Integer.valueOf(TIME_BAR_COLORS[i10]));
            }
        } else {
            for (int i11 = 0; i11 < 2; i11++) {
                arrayList.add(Integer.valueOf(USAGE_BAR_COLORS[i11]));
            }
        }
        BarDataSet barDataSet = new BarDataSet(this.mWeeklyBarEntryArrayList);
        this.mWeeklyBarDataSet = barDataSet;
        barDataSet.mColors = arrayList;
        this.mWeeklyIBarDataSet.clear();
        this.mWeeklyIBarDataSet.add(this.mWeeklyBarDataSet);
        BarData barData = new BarData(this.mWeeklyIBarDataSet);
        this.mWeeklyBardata = barData;
        setData(barData);
        BarData barData2 = this.mWeeklyBardata;
        barData2.mBarWidth = 0.5f;
        for (BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet :
                barData2.mDataSets) {
            barLineScatterCandleBubbleDataSet.getClass();
            barLineScatterCandleBubbleDataSet.mValueTextSize = Utils.convertDpToPixel(11.0f);
        }
        BarDataSet barDataSet2 = this.mWeeklyBarDataSet;
        barDataSet2.mDrawIcons = true;
        barDataSet2.mDrawValues = false;
        barDataSet2.mAxisDependency = YAxis.AxisDependency.RIGHT;
        boolean z = (getContext().getResources().getConfiguration().uiMode & 48) == 32;
        XAxis xAxis = this.mXAxis;
        xAxis.setGranularity();
        xAxis.mDrawGridLines = false;
        xAxis.mPosition = XAxis.XAxisPosition.BOTTOM;
        xAxis.setTextSize(11.0f);
        WeeklyChartXLabelFormatter weeklyChartXLabelFormatter = new WeeklyChartXLabelFormatter();
        weeklyChartXLabelFormatter.mLabels = new ArrayList();
        TextStyle textStyle = TextStyle.NARROW_STANDALONE;
        ArrayList arrayList2 = new ArrayList();
        int value =
                Instant.ofEpochMilli(System.currentTimeMillis())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                                .getDayOfWeek()
                                .getValue()
                        % 7;
        for (int i12 = 0; i12 < 7; i12++) {
            arrayList2.add(
                    DayOfWeek.of(((value + i12) % 7) + 1).getDisplayName(textStyle, Locale.US));
        }
        weeklyChartXLabelFormatter.mLabels.clear();
        weeklyChartXLabelFormatter.mLabels.addAll(arrayList2);
        xAxis.mAxisValueFormatter = weeklyChartXLabelFormatter;
        YAxis yAxis = this.mAxisLeft;
        yAxis.mDrawGridLines = false;
        yAxis.mEnabled = false;
        yAxis.setAxisMinimum(0.0f);
        YAxis yAxis2 = this.mAxisRight;
        yAxis2.mGranularityEnabled = true;
        yAxis2.setGranularity();
        yAxis2.setLabelCount(4);
        yAxis2.mDrawAxisLine = false;
        yAxis2.setAxisMinimum(0.0f);
        yAxis2.setTextSize(11.0f);
        yAxis2.mGridColor = getContext().getColor(R.color.wifi_barchart_yaxis_grid_color);
        yAxis2.mGridLineWidth = Utils.convertDpToPixel(1.0f);
        yAxis2.mMaxWidth = 44.0f;
        yAxis2.mMinWidth = 44.0f;
        if (z) {
            xAxis.mTextColor = getContext().getColor(R.color.wifi_barchart_bottom_text_dark_color);
            yAxis2.mTextColor = getContext().getColor(R.color.wifi_barchart_bottom_text_dark_color);
        } else {
            xAxis.mTextColor = getContext().getColor(R.color.wifi_barchart_bottom_text_color);
            yAxis2.mTextColor = getContext().getColor(R.color.wifi_barchart_bottom_text_color);
        }
        float f5 = yAxis2.mAxisMaximum;
        setVisibleYRange(f5, f5, yAxis2.mAxisDependency);
        Context context = this.mContext;
        WeeklyDataUsageYAxisValueFormatter weeklyDataUsageYAxisValueFormatter =
                new WeeklyDataUsageYAxisValueFormatter();
        weeklyDataUsageYAxisValueFormatter.mDataUsageUnit = "MB";
        weeklyDataUsageYAxisValueFormatter.mContext = context;
        if (i == 0) {
            weeklyDataUsageYAxisValueFormatter.setDataUsageUnit("h");
            yAxis2.setAxisMaximum(this.mMaxYValue);
        } else {
            float f6 = this.mMaxYValue;
            if (f6 != 0.0f) {
                yAxis2.setAxisMaximum(f6);
            }
            weeklyDataUsageYAxisValueFormatter.setDataUsageUnit("MB");
        }
        yAxis2.mAxisValueFormatter = weeklyDataUsageYAxisValueFormatter;
        WeeklyXAxisRenderer weeklyXAxisRenderer = this.mXAxisRendererMonthly;
        weeklyXAxisRenderer.mSelectedInfoLoaded = true;
        weeklyXAxisRenderer.mSelectedLabel = 6 - this.mShiftIndex;
        animateY();
        invalidate();
    }

    public WeeklyTimeUsageBarChart(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.samsung.android.settings.wifi.develop.weeklyreport.WeeklyTimeUsageBarChart$1] */
    public WeeklyTimeUsageBarChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mWeeklyIBarDataSet = new ArrayList();
        this.chartValueSelected =
                new OnChartValueSelectedListener() { // from class:
                                                     // com.samsung.android.settings.wifi.develop.weeklyreport.WeeklyTimeUsageBarChart.1
                    @Override // com.github.mikephil.charting.listener.OnChartValueSelectedListener
                    public final void onValueSelected(Entry entry, Highlight highlight) {
                        if (entry == null) {
                            return;
                        }
                        WeeklyTimeUsageBarChart weeklyTimeUsageBarChart =
                                WeeklyTimeUsageBarChart.this;
                        weeklyTimeUsageBarChart.mChartTouchListener.mLastHighlighted = null;
                        weeklyTimeUsageBarChart.highlightValue();
                        int i2 = 6 - ((int) entry.x);
                        weeklyTimeUsageBarChart.mShiftIndex = i2;
                        WifiLabsWeeklyReportPreference wifiLabsWeeklyReportPreference =
                                WifiLabsWeeklyReportPreference.this;
                        wifiLabsWeeklyReportPreference.mUsageBarChart.mShiftIndex = i2;
                        wifiLabsWeeklyReportPreference.mTimeBarChart.mShiftIndex = i2;
                        if (wifiLabsWeeklyReportPreference.mTabIndexSelected == 0) {
                            wifiLabsWeeklyReportPreference.mWifiOnRxData.setText(
                                    WifiLabsWeeklyReportPreference.getTimeString(
                                            wifiLabsWeeklyReportPreference
                                                    .mWr
                                                    .mDailyEnabledTime[i2]));
                            wifiLabsWeeklyReportPreference.mConnectedTxData.setText(
                                    WifiLabsWeeklyReportPreference.getTimeString(
                                            wifiLabsWeeklyReportPreference
                                                    .mWr
                                                    .mDailyConnectionTime[i2]));
                            wifiLabsWeeklyReportPreference.mDate.setText(
                                    (CharSequence)
                                            ((ArrayList) wifiLabsWeeklyReportPreference.mWeeklyDate)
                                                    .get(
                                                            wifiLabsWeeklyReportPreference
                                                                    .mTimeBarChart
                                                                    .mShiftIndex));
                        } else {
                            wifiLabsWeeklyReportPreference.mWifiOnRxData.setText(
                                    Formatter.formatShortFileSize(
                                            wifiLabsWeeklyReportPreference.getContext(),
                                            wifiLabsWeeklyReportPreference.mWr.mDailyRxBytes[i2]));
                            wifiLabsWeeklyReportPreference.mConnectedTxData.setText(
                                    Formatter.formatShortFileSize(
                                            wifiLabsWeeklyReportPreference.getContext(),
                                            wifiLabsWeeklyReportPreference.mWr.mDailyTxBytes[i2]));
                            wifiLabsWeeklyReportPreference.mDate.setText(
                                    (CharSequence)
                                            ((ArrayList) wifiLabsWeeklyReportPreference.mWeeklyDate)
                                                    .get(
                                                            wifiLabsWeeklyReportPreference
                                                                    .mUsageBarChart
                                                                    .mShiftIndex));
                        }
                        WeeklyXAxisRenderer weeklyXAxisRenderer =
                                weeklyTimeUsageBarChart.mXAxisRendererMonthly;
                        weeklyXAxisRenderer.mSelectedInfoLoaded = true;
                        weeklyXAxisRenderer.mSelectedLabel =
                                6 - weeklyTimeUsageBarChart.mShiftIndex;
                    }

                    @Override // com.github.mikephil.charting.listener.OnChartValueSelectedListener
                    public final void onNothingSelected() {}
                };
        this.mContext = context;
        WeeklyRoundedBarChartRenderer weeklyRoundedBarChartRenderer =
                new WeeklyRoundedBarChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        weeklyRoundedBarChartRenderer.mRadius = -1.0f;
        this.mRenderer = weeklyRoundedBarChartRenderer;
    }
}
