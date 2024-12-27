package com.samsung.android.settings.wifi.develop.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ChartView extends LineChart {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int[] mGraphColors;
    public String[] mGraphLabels;
    public int mMaxY;
    public int mMinY;

    public ChartView(Context context) {
        super(context);
        this.mMinY = 0;
        this.mMaxY = 100;
        this.mGraphColors = new int[] {Color.parseColor("#a2e6ae")};
        this.mGraphLabels = new String[0];
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase,
              // com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
    public final BarLineScatterCandleBubbleData getData() {
        return this.mData;
    }

    public final void update(Integer[] numArr) {
        LineData lineData = (LineData) this.mData;
        if (lineData == null) {
            SemLog.d("ChartView", "data is null. item=" + numArr.length);
            return;
        }
        for (int i = 0; i < numArr.length && i < this.mGraphLabels.length; i++) {
            LineDataSet lineDataSet = (LineDataSet) lineData.getDataSetByIndex(i);
            if (lineDataSet != null) {
                Integer num = numArr[i];
                lineData.addEntry(
                        new Entry(
                                lineDataSet.mEntries.size(),
                                (num == null ? -127 : num.intValue()) == -127
                                        ? this.mMinY - 5
                                        : Math.max(Math.min(r4, this.mMaxY), this.mMinY)),
                        i);
                lineData.calcMinMax();
                if (lineDataSet.mEntries.size() > 30) {
                    this.mXAxis.mCustomAxisMax = false;
                }
            }
        }
        notifyDataSetChanged();
        invalidate();
        setVisibleXRangeMinimum(30.0f);
        setVisibleXRangeMaximum(30.0f);
        moveViewToX(lineData.getEntryCount());
    }

    public ChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMinY = 0;
        this.mMaxY = 100;
        this.mGraphColors = new int[] {Color.parseColor("#a2e6ae")};
        this.mGraphLabels = new String[0];
    }

    public ChartView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMinY = 0;
        this.mMaxY = 100;
        this.mGraphColors = new int[] {Color.parseColor("#a2e6ae")};
        this.mGraphLabels = new String[0];
    }
}
