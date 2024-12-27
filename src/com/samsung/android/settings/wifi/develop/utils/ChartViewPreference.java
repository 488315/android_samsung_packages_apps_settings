package com.samsung.android.settings.wifi.develop.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ChartViewPreference extends Preference {
    public ChartView mChartView;
    public int[] mGraphColors;
    public String[] mGraphLabels;
    public final ArrayList mHistory;
    public int mMaxY;
    public int mMinY;

    public ChartViewPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMinY = 0;
        this.mMaxY = 100;
        this.mGraphColors = new int[] {-16776961};
        this.mGraphLabels = new String[0];
        this.mHistory = new ArrayList();
        setLayoutResource(R.layout.sec_wifi_chart_view_preference);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        String str;
        super.onBindViewHolder(preferenceViewHolder);
        int defaultColor =
                ((TextView) preferenceViewHolder.findViewById(R.id.companion_text))
                        .getTextColors()
                        .getDefaultColor();
        final ChartView chartView = (ChartView) preferenceViewHolder.findViewById(R.id.chart_view);
        this.mChartView = chartView;
        int[] iArr = this.mGraphColors;
        String[] strArr = this.mGraphLabels;
        int i = this.mMinY;
        int i2 = this.mMaxY;
        chartView.getClass();
        SemLog.d("ChartView", "initChart");
        chartView.mMinY = i;
        chartView.mMaxY = i2;
        chartView.mGraphColors = iArr;
        chartView.mGraphLabels = strArr;
        chartView.mDescription.mEnabled = false;
        chartView.mTouchEnabled = true;
        chartView.mDragXEnabled = true;
        chartView.mDragYEnabled = true;
        chartView.mScaleXEnabled = false;
        chartView.mScaleYEnabled = false;
        chartView.mDrawGridBackground = false;
        XAxis xAxis = chartView.mXAxis;
        xAxis.mEnabled = true;
        xAxis.mTextColor = defaultColor;
        xAxis.mDrawGridLines = true;
        xAxis.mAvoidFirstLastClipping = true;
        xAxis.setAxisMinimum(0.0f);
        xAxis.setAxisMaximum(30.0f);
        xAxis.mPosition = XAxis.XAxisPosition.BOTTOM;
        xAxis.mDrawLabels = false;
        xAxis.mDrawAxisLine = false;
        YAxis yAxis = chartView.mAxisRight;
        yAxis.mEnabled = true;
        yAxis.mTextColor = defaultColor;
        yAxis.mDrawGridLines = false;
        yAxis.setAxisMinimum(chartView.mMinY);
        yAxis.setAxisMaximum(chartView.mMaxY);
        yAxis.setLabelCount(3);
        chartView.mAxisLeft.mEnabled = false;
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        while (true) {
            String[] strArr2 = chartView.mGraphLabels;
            if (i3 >= strArr2.length || (str = strArr2[i3]) == null) {
                break;
            }
            LineDataSet lineDataSet = new LineDataSet(str, null);
            lineDataSet.mAxisDependency = YAxis.AxisDependency.RIGHT;
            lineDataSet.setColor(chartView.mGraphColors[i3]);
            lineDataSet.setLineWidth(1.0f);
            lineDataSet.mDrawCircles = false;
            lineDataSet.mDrawValues = false;
            lineDataSet.mDrawHorizontalHighlightIndicator = false;
            lineDataSet.mDrawVerticalHighlightIndicator = false;
            lineDataSet.mDrawFilled = true;
            lineDataSet.mFillFormatter =
                    new IFillFormatter() { // from class:
                                           // com.samsung.android.settings.wifi.develop.utils.ChartView$$ExternalSyntheticLambda0
                        @Override // com.github.mikephil.charting.formatter.IFillFormatter
                        public final float getFillLinePosition(
                                LineDataSet lineDataSet2, LineDataProvider lineDataProvider) {
                            int i4 = ChartView.$r8$clinit;
                            return ChartView.this.mAxisRight.mAxisMinimum;
                        }
                    };
            lineDataSet.mFillDrawable =
                    new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[] {
                                (chartView.mGraphColors[i3] & 16777215) | (-805306368), 16777215
                            });
            arrayList.add(lineDataSet);
            i3++;
        }
        chartView.setData(new LineData(arrayList));
        Legend legend = chartView.mLegend;
        legend.mShape = Legend.LegendForm.LINE;
        legend.mTextColor = defaultColor;
        legend.mEnabled = chartView.mGraphLabels.length > 1;
        this.mHistory.forEach(
                new Consumer() { // from class:
                                 // com.samsung.android.settings.wifi.develop.utils.ChartViewPreference$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ChartViewPreference.this.mChartView.update((Integer[]) obj);
                    }
                });
    }
}
