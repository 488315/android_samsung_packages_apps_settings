package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.data.DataSet$Rounding;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ChartHighlighter {
    public final BarLineScatterCandleBubbleDataProvider mChart;
    public final List mHighlightBuffer = new ArrayList();

    public ChartHighlighter(
            BarLineScatterCandleBubbleDataProvider barLineScatterCandleBubbleDataProvider) {
        this.mChart = barLineScatterCandleBubbleDataProvider;
    }

    public static float getMinimumDistance(
            List list, float f, YAxis.AxisDependency axisDependency) {
        float f2 = Float.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            Highlight highlight = (Highlight) list.get(i);
            if (highlight.axis == axisDependency) {
                float abs = Math.abs(highlight.mYPx - f);
                if (abs < f2) {
                    f2 = abs;
                }
            }
        }
        return f2;
    }

    public BarLineScatterCandleBubbleData getData() {
        return this.mChart.getData();
    }

    public float getDistance(float f, float f2, float f3, float f4) {
        return (float) Math.hypot(f - f3, f2 - f4);
    }

    public Highlight getHighlight(float f, float f2) {
        List list;
        float f3;
        BarLineScatterCandleBubbleData barLineScatterCandleBubbleData;
        int i;
        int i2;
        Entry entryForXValue;
        YAxis.AxisDependency axisDependency = YAxis.AxisDependency.LEFT;
        Transformer transformer = ((BarLineChartBase) this.mChart).getTransformer(axisDependency);
        transformer.getClass();
        MPPointD mPPointD = MPPointD.getInstance(0.0d, 0.0d);
        transformer.getValuesByTouchPoint(f, f2, mPPointD);
        float f4 = (float) mPPointD.x;
        MPPointD.recycleInstance(mPPointD);
        ((ArrayList) this.mHighlightBuffer).clear();
        BarLineScatterCandleBubbleData data = getData();
        BarLineScatterCandleBubbleDataProvider barLineScatterCandleBubbleDataProvider = this.mChart;
        if (data == null) {
            list = this.mHighlightBuffer;
        } else {
            int dataSetCount = data.getDataSetCount();
            int i3 = 0;
            while (i3 < dataSetCount) {
                BarLineScatterCandleBubbleDataSet dataSetByIndex = data.getDataSetByIndex(i3);
                if (dataSetByIndex.mHighlightEnabled) {
                    List list2 = this.mHighlightBuffer;
                    DataSet$Rounding dataSet$Rounding = DataSet$Rounding.CLOSEST;
                    ArrayList arrayList = new ArrayList();
                    List<Entry> entriesForXValue = dataSetByIndex.getEntriesForXValue(f4);
                    if (((ArrayList) entriesForXValue).size() == 0
                            && (entryForXValue =
                                            dataSetByIndex.getEntryForXValue(
                                                    f4, Float.NaN, dataSet$Rounding))
                                    != null) {
                        entriesForXValue = dataSetByIndex.getEntriesForXValue(entryForXValue.x);
                    }
                    if (entriesForXValue.size() != 0) {
                        for (Entry entry : entriesForXValue) {
                            MPPointD pixelForValues =
                                    ((BarLineChartBase) barLineScatterCandleBubbleDataProvider)
                                            .getTransformer(dataSetByIndex.mAxisDependency)
                                            .getPixelForValues(entry.x, entry.getY());
                            BarLineScatterCandleBubbleData barLineScatterCandleBubbleData2 = data;
                            ArrayList arrayList2 = arrayList;
                            arrayList2.add(
                                    new Highlight(
                                            entry.x,
                                            entry.getY(),
                                            (float) pixelForValues.x,
                                            (float) pixelForValues.y,
                                            i3,
                                            dataSetByIndex.mAxisDependency));
                            arrayList = arrayList2;
                            list2 = list2;
                            dataSetByIndex = dataSetByIndex;
                            i3 = i3;
                            f4 = f4;
                            data = barLineScatterCandleBubbleData2;
                            dataSetCount = dataSetCount;
                        }
                    }
                    f3 = f4;
                    barLineScatterCandleBubbleData = data;
                    i = dataSetCount;
                    i2 = i3;
                    ((ArrayList) list2).addAll(arrayList);
                } else {
                    f3 = f4;
                    barLineScatterCandleBubbleData = data;
                    i = dataSetCount;
                    i2 = i3;
                }
                i3 = i2 + 1;
                f4 = f3;
                data = barLineScatterCandleBubbleData;
                dataSetCount = i;
            }
            list = this.mHighlightBuffer;
        }
        Highlight highlight = null;
        if (!list.isEmpty()) {
            float minimumDistance = getMinimumDistance(list, f2, axisDependency);
            YAxis.AxisDependency axisDependency2 = YAxis.AxisDependency.RIGHT;
            if (minimumDistance >= getMinimumDistance(list, f2, axisDependency2)) {
                axisDependency = axisDependency2;
            }
            float f5 =
                    ((BarLineChartBase) barLineScatterCandleBubbleDataProvider)
                            .mMaxHighlightDistance;
            for (int i4 = 0; i4 < list.size(); i4++) {
                Highlight highlight2 = (Highlight) list.get(i4);
                if (highlight2.axis == axisDependency) {
                    float distance = getDistance(f, f2, highlight2.mXPx, highlight2.mYPx);
                    if (distance < f5) {
                        highlight = highlight2;
                        f5 = distance;
                    }
                }
            }
        }
        return highlight;
    }
}
