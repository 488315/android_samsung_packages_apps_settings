package com.samsung.android.settings.uwb.labs.view.statistics.Chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.uwb.labs.view.statistics.WeeklyReportData;

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
        convertToColorRGB("#FFecd4f7"),
        convertToColorRGB("#FFd194eb"),
        convertToColorRGB("#FFab3eda")
    };
    public static final int[] USAGE_BAR_COLORS_DARK = {
        convertToColorRGB("#FFe6f0ff"),
        convertToColorRGB("#FF99c2ff"),
        convertToColorRGB("#FF4d94ff")
    };
    public ChartValueSelectedListener callbackListener;
    public final AnonymousClass1 chartValueSelected;
    public int mBarGraphTap;
    public final Context mContext;
    public int mMaxYValue;
    public BarData mWeeklyBarData;
    public BarDataSet mWeeklyBarDataSet;
    public final ArrayList mWeeklyIBarDataSet;
    public WeeklyXAxisRenderer mXAxisRendererMonthly;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ChartValueSelectedListener {
        void onValueSelected(int i);
    }

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
        this.mDrawValueAboveBar = false;
        this.mDescription.mEnabled = false;
        this.mScaleXEnabled = false;
        this.mScaleYEnabled = false;
        this.mDoubleTapToZoomEnabled = true;
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

    /* JADX WARN: Type inference failed for: r7v3, types: [java.time.LocalDateTime] */
    public final void showWeeklyBarChart() {
        setData(this.mWeeklyBarData);
        BarData barData = this.mWeeklyBarData;
        barData.mBarWidth = 0.5f;
        for (BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet :
                barData.mDataSets) {
            barLineScatterCandleBubbleDataSet.getClass();
            barLineScatterCandleBubbleDataSet.mValueTextSize = Utils.convertDpToPixel(11.0f);
        }
        BarDataSet barDataSet = this.mWeeklyBarDataSet;
        barDataSet.mDrawIcons = true;
        barDataSet.mDrawValues = false;
        ((ArrayList) barDataSet.mValueColors).clear();
        ((ArrayList) barDataSet.mValueColors)
                .add(Integer.valueOf(R.color.sec_uwb_labs_weekly_report_bar_color_3));
        this.mWeeklyBarDataSet.mAxisDependency = YAxis.AxisDependency.RIGHT;
        XAxis xAxis = this.mXAxis;
        xAxis.setGranularity();
        xAxis.mDrawGridLines = false;
        xAxis.mPosition = XAxis.XAxisPosition.BOTTOM;
        xAxis.setTextSize(11.0f);
        WeeklyChartXLabelFormatter weeklyChartXLabelFormatter = new WeeklyChartXLabelFormatter();
        weeklyChartXLabelFormatter.mLabels = new ArrayList();
        TextStyle textStyle = TextStyle.NARROW_STANDALONE;
        ArrayList arrayList = new ArrayList();
        int value =
                Instant.ofEpochMilli(System.currentTimeMillis())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                                .getDayOfWeek()
                                .getValue()
                        % 7;
        for (int i = 0; i < 7; i++) {
            arrayList.add(DayOfWeek.of(((value + i) % 7) + 1).getDisplayName(textStyle, Locale.US));
        }
        weeklyChartXLabelFormatter.mLabels.clear();
        weeklyChartXLabelFormatter.mLabels.addAll(arrayList);
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
        xAxis.mTextColor = getContext().getColor(R.color.sec_uwb_labs_bar_graph_bottom_text_color);
        yAxis2.mTextColor = getContext().getColor(R.color.sec_uwb_labs_bar_graph_bottom_text_color);
        float f = yAxis2.mAxisMaximum;
        setVisibleYRange(f, f, yAxis2.mAxisDependency);
        Context context = this.mContext;
        WeeklyDataUsageYAxisValueFormatter weeklyDataUsageYAxisValueFormatter =
                new WeeklyDataUsageYAxisValueFormatter();
        weeklyDataUsageYAxisValueFormatter.mContext = context;
        if (this.mBarGraphTap == 0) {
            String m =
                    SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                            .m(this.mContext, R.string.uwb_distance_unit_h, new StringBuilder(" "));
            if (m.contentEquals("h") || m.contentEquals("m")) {
                weeklyDataUsageYAxisValueFormatter.mDataUsageUnit = m;
            } else {
                weeklyDataUsageYAxisValueFormatter.mDataUsageUnit = ApnSettings.MVNO_NONE;
            }
        } else {
            String m2 =
                    SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                            .m(this.mContext, R.string.uwb_distance_unit, new StringBuilder(" "));
            if (m2.contentEquals("h") || m2.contentEquals("m")) {
                weeklyDataUsageYAxisValueFormatter.mDataUsageUnit = m2;
            } else {
                weeklyDataUsageYAxisValueFormatter.mDataUsageUnit = ApnSettings.MVNO_NONE;
            }
        }
        yAxis2.setAxisMaximum(this.mMaxYValue);
        yAxis2.mAxisValueFormatter = weeklyDataUsageYAxisValueFormatter;
        WeeklyXAxisRenderer weeklyXAxisRenderer = this.mXAxisRendererMonthly;
        weeklyXAxisRenderer.mSelectedInfoLoaded = true;
        weeklyXAxisRenderer.mSelectedLabel = WeeklyReportData.mSelectedDay;
        animateY();
        invalidate();
    }

    public WeeklyTimeUsageBarChart(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.samsung.android.settings.uwb.labs.view.statistics.Chart.WeeklyTimeUsageBarChart$1] */
    public WeeklyTimeUsageBarChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mWeeklyIBarDataSet = new ArrayList();
        this.mBarGraphTap = 0;
        this.chartValueSelected =
                new OnChartValueSelectedListener() { // from class:
                                                     // com.samsung.android.settings.uwb.labs.view.statistics.Chart.WeeklyTimeUsageBarChart.1
                    @Override // com.github.mikephil.charting.listener.OnChartValueSelectedListener
                    public final void onValueSelected(Entry entry, Highlight highlight) {
                        if (entry != null) {
                            WeeklyTimeUsageBarChart weeklyTimeUsageBarChart =
                                    WeeklyTimeUsageBarChart.this;
                            weeklyTimeUsageBarChart.mChartTouchListener.mLastHighlighted = null;
                            weeklyTimeUsageBarChart.highlightValue();
                            weeklyTimeUsageBarChart.callbackListener.onValueSelected((int) entry.x);
                            WeeklyXAxisRenderer weeklyXAxisRenderer =
                                    weeklyTimeUsageBarChart.mXAxisRendererMonthly;
                            weeklyXAxisRenderer.mSelectedInfoLoaded = true;
                            weeklyXAxisRenderer.mSelectedLabel = WeeklyReportData.mSelectedDay;
                        }
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
