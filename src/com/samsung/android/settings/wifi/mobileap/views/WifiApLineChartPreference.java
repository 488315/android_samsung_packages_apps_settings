package com.samsung.android.settings.wifi.mobileap.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.mobileap.datamodels.chart.WifiApChartDataModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApLineChartPreference extends Preference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public WifiApChartDataModel mChartDataModel;
    public final Context mContext;
    public final List mEntryList;
    public final String mLineChartLabel;

    public WifiApLineChartPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mEntryList = new ArrayList();
        this.mLineChartLabel = ApnSettings.MVNO_NONE;
        this.mChartDataModel = new WifiApChartDataModel(null, null);
        setLayoutResource(R.layout.sec_wifi_ap_line_chart_view_layout);
        this.mContext = context;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        final LineChart lineChart = (LineChart) preferenceViewHolder.findViewById(R.id.lineChart);
        int color = this.mContext.getColor(R.color.sec_fingerprint_verfiedid);
        int color2 = this.mContext.getColor(R.color.sec_dashboard_account_default_id_color);
        int color3 = this.mContext.getColor(R.color.settingslib_color_grey600);
        LineDataSet lineDataSet = new LineDataSet(this.mLineChartLabel, this.mEntryList);
        lineDataSet.mAxisDependency = YAxis.AxisDependency.RIGHT;
        lineDataSet.setColor(color3);
        lineDataSet.setLineWidth(1.0f);
        lineDataSet.mDrawCircles = false;
        lineDataSet.mDrawValues = false;
        lineDataSet.mDrawHorizontalHighlightIndicator = false;
        lineDataSet.mDrawVerticalHighlightIndicator = false;
        lineDataSet.mFillFormatter =
                new IFillFormatter() { // from class:
                                       // com.samsung.android.settings.wifi.mobileap.views.WifiApLineChartPreference$$ExternalSyntheticLambda0
                    @Override // com.github.mikephil.charting.formatter.IFillFormatter
                    public final float getFillLinePosition(
                            LineDataSet lineDataSet2, LineDataProvider lineDataProvider) {
                        int i = WifiApLineChartPreference.$r8$clinit;
                        return LineChart.this.mAxisRight.mAxisMinimum;
                    }
                };
        lineChart.setData(new LineData(lineDataSet));
        lineChart.mDescription.mEnabled = false;
        lineChart.mTouchEnabled = true;
        lineChart.mDragXEnabled = true;
        lineChart.mDragYEnabled = true;
        lineChart.mScaleXEnabled = false;
        lineChart.mScaleYEnabled = false;
        lineChart.mDrawGridBackground = false;
        XAxis xAxis = lineChart.mXAxis;
        xAxis.mEnabled = true;
        xAxis.mTextColor = color;
        xAxis.mDrawGridLines = false;
        xAxis.mAvoidFirstLastClipping = true;
        xAxis.mPosition = XAxis.XAxisPosition.BOTTOM;
        xAxis.mDrawLabels = true;
        xAxis.mDrawAxisLine = true;
        xAxis.mAxisValueFormatter =
                new IAxisValueFormatter() { // from class:
                                            // com.samsung.android.settings.wifi.mobileap.views.WifiApLineChartPreference.1
                    @Override // com.github.mikephil.charting.formatter.IAxisValueFormatter
                    public final String getFormattedValue(float f) {
                        return new SimpleDateFormat("HH:mm", Locale.US)
                                .format(
                                        new Date(
                                                Long.valueOf(
                                                                (String)
                                                                        WifiApLineChartPreference
                                                                                .this
                                                                                .mChartDataModel
                                                                                .mXaxisValueList
                                                                                .get((int) f))
                                                        .longValue()));
                    }
                };
        YAxis yAxis = lineChart.mAxisRight;
        yAxis.mEnabled = true;
        yAxis.mTextColor = color2;
        yAxis.mDrawGridLines = false;
        yAxis.setAxisMinimum(0.0f);
        lineChart.mAxisLeft.mEnabled = false;
        Legend legend = lineChart.mLegend;
        legend.mVerticalAlignment = Legend.LegendVerticalAlignment.TOP;
        legend.mHorizontalAlignment = Legend.LegendHorizontalAlignment.LEFT;
        legend.mShape = Legend.LegendForm.LINE;
        legend.mTextColor = color3;
        legend.setTextSize(14.0f);
        lineChart.mLegend.mEnabled = true;
        LegendEntry legendEntry = new LegendEntry();
        legendEntry.formSize = Float.NaN;
        legendEntry.formLineWidth = Float.NaN;
        legendEntry.formLineDashEffect = null;
        legendEntry.label = "Recent hotspot speed in Mbps";
        legendEntry.form = Legend.LegendForm.SQUARE;
        legendEntry.formColor = color3;
        List singletonList = Collections.singletonList(legendEntry);
        legend.mEntries =
                (LegendEntry[]) singletonList.toArray(new LegendEntry[singletonList.size()]);
        legend.mIsLegendCustom = true;
        lineChart.invalidate();
    }

    public WifiApLineChartPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiApLineChartPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiApLineChartPreference(Context context) {
        this(context, null);
    }
}
