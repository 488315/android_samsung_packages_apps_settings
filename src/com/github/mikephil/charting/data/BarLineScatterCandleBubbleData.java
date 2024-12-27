package com.github.mikephil.charting.data;

import android.util.Log;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class BarLineScatterCandleBubbleData {
    public final List mDataSets;
    public float mYMax = -3.4028235E38f;
    public float mYMin = Float.MAX_VALUE;
    public float mXMax = -3.4028235E38f;
    public float mXMin = Float.MAX_VALUE;
    public float mLeftAxisMax = -3.4028235E38f;
    public float mLeftAxisMin = Float.MAX_VALUE;
    public float mRightAxisMax = -3.4028235E38f;
    public float mRightAxisMin = Float.MAX_VALUE;

    public BarLineScatterCandleBubbleData(
            BarLineScatterCandleBubbleDataSet... barLineScatterCandleBubbleDataSetArr) {
        ArrayList arrayList = new ArrayList();
        for (BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet :
                barLineScatterCandleBubbleDataSetArr) {
            arrayList.add(barLineScatterCandleBubbleDataSet);
        }
        this.mDataSets = arrayList;
        calcMinMax();
    }

    public final void addEntry(Entry entry, int i) {
        if (this.mDataSets.size() <= i || i < 0) {
            Log.e("addEntry", "Cannot add Entry because dataSetIndex too high or too low.");
            return;
        }
        BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet =
                (BarLineScatterCandleBubbleDataSet) this.mDataSets.get(i);
        List list = barLineScatterCandleBubbleDataSet.mEntries;
        if (list == null) {
            list = new ArrayList();
        }
        barLineScatterCandleBubbleDataSet.calcMinMax(entry);
        if (list.add(entry)) {
            YAxis.AxisDependency axisDependency = barLineScatterCandleBubbleDataSet.mAxisDependency;
            float f = this.mYMax;
            float f2 = entry.y;
            if (f < f2) {
                this.mYMax = f2;
            }
            if (this.mYMin > f2) {
                this.mYMin = f2;
            }
            float f3 = this.mXMax;
            float f4 = entry.x;
            if (f3 < f4) {
                this.mXMax = f4;
            }
            if (this.mXMin > f4) {
                this.mXMin = f4;
            }
            if (axisDependency == YAxis.AxisDependency.LEFT) {
                if (this.mLeftAxisMax < f2) {
                    this.mLeftAxisMax = f2;
                }
                if (this.mLeftAxisMin > f2) {
                    this.mLeftAxisMin = f2;
                    return;
                }
                return;
            }
            if (this.mRightAxisMax < f2) {
                this.mRightAxisMax = f2;
            }
            if (this.mRightAxisMin > f2) {
                this.mRightAxisMin = f2;
            }
        }
    }

    public final void calcMinMax() {
        YAxis.AxisDependency axisDependency;
        BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet;
        BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet2;
        YAxis.AxisDependency axisDependency2;
        List list = this.mDataSets;
        if (list == null) {
            return;
        }
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        this.mXMax = -3.4028235E38f;
        this.mXMin = Float.MAX_VALUE;
        Iterator it = list.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            axisDependency = YAxis.AxisDependency.LEFT;
            if (!hasNext) {
                break;
            }
            BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet3 =
                    (BarLineScatterCandleBubbleDataSet) it.next();
            float f = this.mYMax;
            float f2 = barLineScatterCandleBubbleDataSet3.mYMax;
            if (f < f2) {
                this.mYMax = f2;
            }
            float f3 = this.mYMin;
            float f4 = barLineScatterCandleBubbleDataSet3.mYMin;
            if (f3 > f4) {
                this.mYMin = f4;
            }
            float f5 = this.mXMax;
            float f6 = barLineScatterCandleBubbleDataSet3.mXMax;
            if (f5 < f6) {
                this.mXMax = f6;
            }
            float f7 = this.mXMin;
            float f8 = barLineScatterCandleBubbleDataSet3.mXMin;
            if (f7 > f8) {
                this.mXMin = f8;
            }
            if (barLineScatterCandleBubbleDataSet3.mAxisDependency == axisDependency) {
                if (this.mLeftAxisMax < f2) {
                    this.mLeftAxisMax = f2;
                }
                if (this.mLeftAxisMin > f4) {
                    this.mLeftAxisMin = f4;
                }
            } else {
                if (this.mRightAxisMax < f2) {
                    this.mRightAxisMax = f2;
                }
                if (this.mRightAxisMin > f4) {
                    this.mRightAxisMin = f4;
                }
            }
        }
        this.mLeftAxisMax = -3.4028235E38f;
        this.mLeftAxisMin = Float.MAX_VALUE;
        this.mRightAxisMax = -3.4028235E38f;
        this.mRightAxisMin = Float.MAX_VALUE;
        Iterator it2 = this.mDataSets.iterator();
        while (true) {
            barLineScatterCandleBubbleDataSet = null;
            if (it2.hasNext()) {
                barLineScatterCandleBubbleDataSet2 = (BarLineScatterCandleBubbleDataSet) it2.next();
                if (barLineScatterCandleBubbleDataSet2.mAxisDependency == axisDependency) {
                    break;
                }
            } else {
                barLineScatterCandleBubbleDataSet2 = null;
                break;
            }
        }
        if (barLineScatterCandleBubbleDataSet2 != null) {
            this.mLeftAxisMax = barLineScatterCandleBubbleDataSet2.mYMax;
            this.mLeftAxisMin = barLineScatterCandleBubbleDataSet2.mYMin;
            for (BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet4 :
                    this.mDataSets) {
                if (barLineScatterCandleBubbleDataSet4.mAxisDependency == axisDependency) {
                    float f9 = barLineScatterCandleBubbleDataSet4.mYMin;
                    if (f9 < this.mLeftAxisMin) {
                        this.mLeftAxisMin = f9;
                    }
                    float f10 = barLineScatterCandleBubbleDataSet4.mYMax;
                    if (f10 > this.mLeftAxisMax) {
                        this.mLeftAxisMax = f10;
                    }
                }
            }
        }
        Iterator it3 = this.mDataSets.iterator();
        while (true) {
            boolean hasNext2 = it3.hasNext();
            axisDependency2 = YAxis.AxisDependency.RIGHT;
            if (!hasNext2) {
                break;
            }
            BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet5 =
                    (BarLineScatterCandleBubbleDataSet) it3.next();
            if (barLineScatterCandleBubbleDataSet5.mAxisDependency == axisDependency2) {
                barLineScatterCandleBubbleDataSet = barLineScatterCandleBubbleDataSet5;
                break;
            }
        }
        if (barLineScatterCandleBubbleDataSet != null) {
            this.mRightAxisMax = barLineScatterCandleBubbleDataSet.mYMax;
            this.mRightAxisMin = barLineScatterCandleBubbleDataSet.mYMin;
            for (BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet6 :
                    this.mDataSets) {
                if (barLineScatterCandleBubbleDataSet6.mAxisDependency == axisDependency2) {
                    float f11 = barLineScatterCandleBubbleDataSet6.mYMin;
                    if (f11 < this.mRightAxisMin) {
                        this.mRightAxisMin = f11;
                    }
                    float f12 = barLineScatterCandleBubbleDataSet6.mYMax;
                    if (f12 > this.mRightAxisMax) {
                        this.mRightAxisMax = f12;
                    }
                }
            }
        }
    }

    public final BarLineScatterCandleBubbleDataSet getDataSetByIndex(int i) {
        List list = this.mDataSets;
        if (list == null || i < 0 || i >= list.size()) {
            return null;
        }
        return (BarLineScatterCandleBubbleDataSet) this.mDataSets.get(i);
    }

    public final int getDataSetCount() {
        List list = this.mDataSets;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public final int getEntryCount() {
        Iterator it = this.mDataSets.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += ((BarLineScatterCandleBubbleDataSet) it.next()).mEntries.size();
        }
        return i;
    }

    public final Entry getEntryForHighlight(Highlight highlight) {
        if (highlight.mDataSetIndex >= this.mDataSets.size()) {
            return null;
        }
        return ((BarLineScatterCandleBubbleDataSet) this.mDataSets.get(highlight.mDataSetIndex))
                .getEntryForXValue(highlight.mX, highlight.mY, DataSet$Rounding.CLOSEST);
    }

    public final float getYMax(YAxis.AxisDependency axisDependency) {
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            float f = this.mLeftAxisMax;
            return f == -3.4028235E38f ? this.mRightAxisMax : f;
        }
        float f2 = this.mRightAxisMax;
        return f2 == -3.4028235E38f ? this.mLeftAxisMax : f2;
    }

    public final float getYMin(YAxis.AxisDependency axisDependency) {
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            float f = this.mLeftAxisMin;
            return f == Float.MAX_VALUE ? this.mRightAxisMin : f;
        }
        float f2 = this.mRightAxisMin;
        return f2 == Float.MAX_VALUE ? this.mLeftAxisMin : f2;
    }

    public BarLineScatterCandleBubbleData(List list) {
        this.mDataSets = list;
        calcMinMax();
    }
}
