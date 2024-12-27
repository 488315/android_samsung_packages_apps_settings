package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.DataSet$Rounding;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BarHighlighter extends ChartHighlighter {
    @Override // com.github.mikephil.charting.highlight.ChartHighlighter
    public final BarLineScatterCandleBubbleData getData() {
        return (BarData) ((BarChart) ((BarDataProvider) this.mChart)).mData;
    }

    @Override // com.github.mikephil.charting.highlight.ChartHighlighter
    public final float getDistance(float f, float f2, float f3, float f4) {
        return Math.abs(f - f3);
    }

    @Override // com.github.mikephil.charting.highlight.ChartHighlighter
    public final Highlight getHighlight(float f, float f2) {
        int i;
        Highlight highlight = super.getHighlight(f, f2);
        Highlight highlight2 = null;
        if (highlight == null) {
            return null;
        }
        Transformer transformer =
                ((BarLineChartBase) this.mChart).getTransformer(YAxis.AxisDependency.LEFT);
        transformer.getClass();
        MPPointD mPPointD = MPPointD.getInstance(0.0d, 0.0d);
        transformer.getValuesByTouchPoint(f, f2, mPPointD);
        BarLineScatterCandleBubbleDataProvider barLineScatterCandleBubbleDataProvider =
                (BarDataProvider) this.mChart;
        BarDataSet barDataSet =
                (BarDataSet)
                        ((BarData) ((BarChart) barLineScatterCandleBubbleDataProvider).mData)
                                .getDataSetByIndex(highlight.mDataSetIndex);
        if (barDataSet.mStackSize <= 1) {
            MPPointD.recycleInstance(mPPointD);
            return highlight;
        }
        float f3 = (float) mPPointD.x;
        float f4 = (float) mPPointD.y;
        BarEntry barEntry =
                (BarEntry) barDataSet.getEntryForXValue(f3, f4, DataSet$Rounding.CLOSEST);
        if (barEntry != null) {
            if (barEntry.mYVals == null) {
                return highlight;
            }
            Range[] rangeArr = barEntry.mRanges;
            if (rangeArr.length > 0) {
                int i2 = 0;
                if (rangeArr.length != 0) {
                    int i3 = 0;
                    for (Range range : rangeArr) {
                        if (f4 > range.from && f4 <= range.to) {
                            i = i3;
                            break;
                        }
                        i3++;
                    }
                    int max = Math.max(rangeArr.length - 1, 0);
                    if (f4 > rangeArr[max].to) {
                        i2 = max;
                    }
                }
                i = i2;
                MPPointD pixelForValues =
                        ((BarLineChartBase) barLineScatterCandleBubbleDataProvider)
                                .getTransformer(barDataSet.mAxisDependency)
                                .getPixelForValues(highlight.mX, rangeArr[i].to);
                highlight2 =
                        new Highlight(
                                barEntry.x,
                                barEntry.y,
                                (float) pixelForValues.x,
                                (float) pixelForValues.y,
                                highlight.mDataSetIndex,
                                i,
                                highlight.axis);
                MPPointD.recycleInstance(pixelForValues);
            }
        }
        return highlight2;
    }
}
