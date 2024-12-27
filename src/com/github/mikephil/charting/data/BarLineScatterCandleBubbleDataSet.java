package com.github.mikephil.charting.data;

import android.graphics.Color;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class BarLineScatterCandleBubbleDataSet {
    public List mColors;
    public final List mEntries;
    public int mHighLightColor;
    public String mLabel;
    public List mValueColors;
    public transient DefaultValueFormatter mValueFormatter;
    public float mXMax;
    public float mXMin;
    public float mYMax;
    public float mYMin;
    public YAxis.AxisDependency mAxisDependency = YAxis.AxisDependency.LEFT;
    public boolean mHighlightEnabled = true;
    public Legend.LegendForm mForm = Legend.LegendForm.DEFAULT;
    public float mFormSize = Float.NaN;
    public float mFormLineWidth = Float.NaN;
    public boolean mDrawValues = true;
    public boolean mDrawIcons = true;
    public MPPointF mIconsOffset = new MPPointF();
    public float mValueTextSize = 17.0f;
    public boolean mVisible = true;

    public BarLineScatterCandleBubbleDataSet(String str, List list) {
        this.mColors = null;
        this.mValueColors = null;
        this.mLabel = "DataSet";
        this.mColors = new ArrayList();
        ArrayList arrayList = new ArrayList();
        this.mValueColors = arrayList;
        this.mColors.add(
                Integer.valueOf(
                        Color.rgb(
                                140,
                                IKnoxCustomManager.Stub.TRANSACTION_deleteHomeScreenPage,
                                255)));
        arrayList.add(Integer.valueOf(EmergencyPhoneWidget.BG_COLOR));
        this.mLabel = str;
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        this.mXMax = -3.4028235E38f;
        this.mXMin = Float.MAX_VALUE;
        this.mEntries = list;
        if (list == null) {
            this.mEntries = new ArrayList();
        }
        calcMinMax$1();
        this.mHighLightColor = Color.rgb(255, 187, 115);
    }

    public void calcMinMax(Entry entry) {
        if (entry == null) {
            return;
        }
        float f = entry.x;
        if (f < this.mXMin) {
            this.mXMin = f;
        }
        if (f > this.mXMax) {
            this.mXMax = f;
        }
        calcMinMaxY(entry);
    }

    public final void calcMinMax$1() {
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        this.mXMax = -3.4028235E38f;
        this.mXMin = Float.MAX_VALUE;
        List list = this.mEntries;
        if (list == null || list.isEmpty()) {
            return;
        }
        Iterator it = this.mEntries.iterator();
        while (it.hasNext()) {
            calcMinMax((Entry) it.next());
        }
    }

    public final void calcMinMaxY(Entry entry) {
        if (entry.getY() < this.mYMin) {
            this.mYMin = entry.getY();
        }
        if (entry.getY() > this.mYMax) {
            this.mYMax = entry.getY();
        }
    }

    public final int getColor() {
        return ((Integer) this.mColors.get(0)).intValue();
    }

    public final List getEntriesForXValue(float f) {
        ArrayList arrayList = new ArrayList();
        int size = this.mEntries.size() - 1;
        int i = 0;
        while (true) {
            if (i > size) {
                break;
            }
            int i2 = (size + i) / 2;
            float f2 = ((Entry) this.mEntries.get(i2)).x;
            if (f == f2) {
                while (i2 > 0 && ((Entry) this.mEntries.get(i2 - 1)).x == f) {
                    i2--;
                }
                int size2 = this.mEntries.size();
                while (i2 < size2) {
                    Entry entry = (Entry) this.mEntries.get(i2);
                    if (entry.x != f) {
                        break;
                    }
                    arrayList.add(entry);
                    i2++;
                }
            } else if (f > f2) {
                i = i2 + 1;
            } else {
                size = i2 - 1;
            }
        }
        return arrayList;
    }

    public final Entry getEntryForIndex(int i) {
        return (Entry) this.mEntries.get(i);
    }

    public final Entry getEntryForXValue(float f, float f2, DataSet$Rounding dataSet$Rounding) {
        int entryIndex = getEntryIndex(f, f2, dataSet$Rounding);
        if (entryIndex > -1) {
            return (Entry) this.mEntries.get(entryIndex);
        }
        return null;
    }

    public final int getEntryIndex(float f, float f2, DataSet$Rounding dataSet$Rounding) {
        int i;
        Entry entry;
        List list = this.mEntries;
        if (list == null || list.isEmpty()) {
            return -1;
        }
        int size = this.mEntries.size() - 1;
        int i2 = 0;
        while (i2 < size) {
            int i3 = (i2 + size) / 2;
            float f3 = ((Entry) this.mEntries.get(i3)).x - f;
            int i4 = i3 + 1;
            float f4 = ((Entry) this.mEntries.get(i4)).x - f;
            float abs = Math.abs(f3);
            float abs2 = Math.abs(f4);
            if (abs2 >= abs) {
                if (abs >= abs2) {
                    double d = f3;
                    if (d < 0.0d) {
                        if (d < 0.0d) {}
                    }
                }
                size = i3;
            }
            i2 = i4;
        }
        if (size == -1) {
            return size;
        }
        float f5 = ((Entry) this.mEntries.get(size)).x;
        if (dataSet$Rounding == DataSet$Rounding.UP) {
            if (f5 < f && size < this.mEntries.size() - 1) {
                size++;
            }
        } else if (dataSet$Rounding == DataSet$Rounding.DOWN && f5 > f && size > 0) {
            size--;
        }
        if (Float.isNaN(f2)) {
            return size;
        }
        while (size > 0 && ((Entry) this.mEntries.get(size - 1)).x == f5) {
            size--;
        }
        float y = ((Entry) this.mEntries.get(size)).getY();
        loop2:
        while (true) {
            i = size;
            do {
                size++;
                if (size >= this.mEntries.size()) {
                    break loop2;
                }
                entry = (Entry) this.mEntries.get(size);
                if (entry.x != f5) {
                    break loop2;
                }
            } while (Math.abs(entry.getY() - f2) > Math.abs(y - f2));
            y = f2;
        }
        return i;
    }

    public final DefaultValueFormatter getValueFormatter() {
        DefaultValueFormatter defaultValueFormatter = this.mValueFormatter;
        return defaultValueFormatter == null ? Utils.mDefaultValueFormatter : defaultValueFormatter;
    }

    public final int getValueTextColor(int i) {
        List list = this.mValueColors;
        return ((Integer) ((ArrayList) list).get(i % ((ArrayList) list).size())).intValue();
    }

    public final void setColor(int i) {
        if (this.mColors == null) {
            this.mColors = new ArrayList();
        }
        this.mColors.clear();
        this.mColors.add(Integer.valueOf(i));
    }

    public final void setColors(int... iArr) {
        int i = ColorTemplate.$r8$clinit;
        ArrayList arrayList = new ArrayList();
        for (int i2 : iArr) {
            arrayList.add(Integer.valueOf(i2));
        }
        this.mColors = arrayList;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuilder sb = new StringBuilder("DataSet, label: ");
        String str = this.mLabel;
        if (str == null) {
            str = ApnSettings.MVNO_NONE;
        }
        sb.append(str);
        sb.append(", entries: ");
        sb.append(this.mEntries.size());
        sb.append("\n");
        stringBuffer2.append(sb.toString());
        stringBuffer.append(stringBuffer2.toString());
        for (int i = 0; i < this.mEntries.size(); i++) {
            stringBuffer.append(((Entry) this.mEntries.get(i)).toString() + " ");
        }
        return stringBuffer.toString();
    }

    public final int getColor(int i) {
        List list = this.mColors;
        return ((Integer) list.get(i % list.size())).intValue();
    }
}
