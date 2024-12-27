package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DefaultFillFormatter implements IFillFormatter {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.formatter.IFillFormatter
    public final float getFillLinePosition(
            LineDataSet lineDataSet, LineDataProvider lineDataProvider) {
        BarLineChartBase barLineChartBase = (BarLineChartBase) lineDataProvider;
        float max =
                Math.max(
                        barLineChartBase.mAxisLeft.mAxisMaximum,
                        barLineChartBase.mAxisRight.mAxisMaximum);
        float min =
                Math.min(
                        barLineChartBase.mAxisLeft.mAxisMinimum,
                        barLineChartBase.mAxisRight.mAxisMinimum);
        LineData lineData = (LineData) ((LineChart) lineDataProvider).mData;
        if (lineDataSet.mYMax > 0.0f && lineDataSet.mYMin < 0.0f) {
            return 0.0f;
        }
        if (lineData.mYMax > 0.0f) {
            max = 0.0f;
        }
        if (lineData.mYMin < 0.0f) {
            min = 0.0f;
        }
        return lineDataSet.mYMin >= 0.0f ? min : max;
    }
}
