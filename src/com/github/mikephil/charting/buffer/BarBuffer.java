package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BarBuffer {
    public final float[] buffer;
    public final boolean mContainsStacks;
    public float phaseX = 1.0f;
    public float phaseY = 1.0f;
    public int index = 0;
    public boolean mInverted = false;
    public float mBarWidth = 1.0f;

    public BarBuffer(int i, boolean z) {
        this.buffer = new float[i];
        this.mContainsStacks = z;
    }

    public final void feed(BarDataSet barDataSet) {
        float f;
        float abs;
        float abs2;
        float f2;
        float size = barDataSet.mEntries.size() * this.phaseX;
        float f3 = this.mBarWidth / 2.0f;
        for (int i = 0; i < size; i++) {
            BarEntry barEntry = (BarEntry) barDataSet.getEntryForIndex(i);
            if (barEntry != null) {
                float f4 = barEntry.x;
                float f5 = barEntry.y;
                float[] fArr = barEntry.mYVals;
                boolean z = this.mContainsStacks;
                float[] fArr2 = this.buffer;
                if (!z || fArr == null) {
                    float f6 = f4 - f3;
                    float f7 = f4 + f3;
                    if (this.mInverted) {
                        f = f5 >= 0.0f ? f5 : 0.0f;
                        if (f5 > 0.0f) {
                            f5 = 0.0f;
                        }
                    } else {
                        float f8 = f5 >= 0.0f ? f5 : 0.0f;
                        if (f5 > 0.0f) {
                            f5 = 0.0f;
                        }
                        float f9 = f5;
                        f5 = f8;
                        f = f9;
                    }
                    if (f5 > 0.0f) {
                        f5 *= this.phaseY;
                    } else {
                        f *= this.phaseY;
                    }
                    int i2 = this.index;
                    fArr2[i2] = f6;
                    fArr2[i2 + 1] = f5;
                    fArr2[i2 + 2] = f7;
                    this.index = i2 + 4;
                    fArr2[i2 + 3] = f;
                } else {
                    float f10 = -barEntry.mNegativeSum;
                    float f11 = 0.0f;
                    int i3 = 0;
                    while (i3 < fArr.length) {
                        float f12 = fArr[i3];
                        if (f12 == 0.0f && (f11 == 0.0f || f10 == 0.0f)) {
                            abs = f12;
                            abs2 = f10;
                            f10 = abs;
                        } else if (f12 >= 0.0f) {
                            abs = f12 + f11;
                            abs2 = f10;
                            f10 = f11;
                            f11 = abs;
                        } else {
                            abs = Math.abs(f12) + f10;
                            abs2 = Math.abs(f12) + f10;
                        }
                        float f13 = f4 - f3;
                        float f14 = f4 + f3;
                        if (this.mInverted) {
                            f2 = f10 >= abs ? f10 : abs;
                            if (f10 > abs) {
                                f10 = abs;
                            }
                        } else {
                            float f15 = f10 >= abs ? f10 : abs;
                            if (f10 > abs) {
                                f10 = abs;
                            }
                            float f16 = f10;
                            f10 = f15;
                            f2 = f16;
                        }
                        float f17 = this.phaseY;
                        float f18 = f10 * f17;
                        float f19 = f2 * f17;
                        int i4 = this.index;
                        fArr2[i4] = f13;
                        fArr2[i4 + 1] = f18;
                        fArr2[i4 + 2] = f14;
                        this.index = i4 + 4;
                        fArr2[i4 + 3] = f19;
                        i3++;
                        f10 = abs2;
                    }
                }
            }
        }
        this.index = 0;
    }
}
