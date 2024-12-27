package com.github.mikephil.charting.data;

import android.graphics.Color;

import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BarDataSet extends BarLineScatterCandleBubbleDataSet {
    public final int mBarBorderColor;
    public final int mBarShadowColor;
    public final int mHighLightAlpha;
    public final String[] mStackLabels;
    public final int mStackSize;

    public BarDataSet(List list) {
        super(ApnSettings.MVNO_NONE, list);
        ArrayList arrayList;
        this.mStackSize = 1;
        this.mBarShadowColor = Color.rgb(215, 215, 215);
        this.mBarBorderColor = EmergencyPhoneWidget.BG_COLOR;
        this.mHighLightAlpha = 120;
        this.mStackLabels = new String[0];
        this.mHighLightColor = Color.rgb(0, 0, 0);
        int i = 0;
        while (true) {
            arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                break;
            }
            float[] fArr = ((BarEntry) arrayList.get(i)).mYVals;
            if (fArr != null && fArr.length > this.mStackSize) {
                this.mStackSize = fArr.length;
            }
            i++;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            float[] fArr2 = ((BarEntry) arrayList.get(i2)).mYVals;
        }
    }

    @Override // com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet
    public final void calcMinMax(Entry entry) {
        BarEntry barEntry = (BarEntry) entry;
        if (barEntry == null || Float.isNaN(barEntry.y)) {
            return;
        }
        if (barEntry.mYVals == null) {
            float f = barEntry.y;
            if (f < this.mYMin) {
                this.mYMin = f;
            }
            if (f > this.mYMax) {
                this.mYMax = f;
            }
        } else {
            float f2 = -barEntry.mNegativeSum;
            if (f2 < this.mYMin) {
                this.mYMin = f2;
            }
            float f3 = barEntry.mPositiveSum;
            if (f3 > this.mYMax) {
                this.mYMax = f3;
            }
        }
        float f4 = barEntry.x;
        if (f4 < this.mXMin) {
            this.mXMin = f4;
        }
        if (f4 > this.mXMax) {
            this.mXMax = f4;
        }
    }
}
