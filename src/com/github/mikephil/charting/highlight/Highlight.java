package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.components.YAxis;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Highlight {
    public final YAxis.AxisDependency axis;
    public final int mDataSetIndex;
    public float mDrawX;
    public float mDrawY;
    public final int mStackIndex;
    public final float mX;
    public final float mXPx;
    public final float mY;
    public final float mYPx;

    public Highlight(float f, float f2) {
        this.mStackIndex = -1;
        this.mX = f;
        this.mY = f2;
        this.mDataSetIndex = 0;
    }

    public final boolean equalTo(Highlight highlight) {
        return highlight != null
                && this.mDataSetIndex == highlight.mDataSetIndex
                && this.mX == highlight.mX
                && this.mStackIndex == highlight.mStackIndex;
    }

    public final String toString() {
        return "Highlight, x: "
                + this.mX
                + ", y: "
                + this.mY
                + ", dataSetIndex: "
                + this.mDataSetIndex
                + ", stackIndex (only stacked barentry): "
                + this.mStackIndex;
    }

    public Highlight(
            float f, float f2, float f3, float f4, int i, YAxis.AxisDependency axisDependency) {
        this.mStackIndex = -1;
        this.mX = f;
        this.mY = f2;
        this.mXPx = f3;
        this.mYPx = f4;
        this.mDataSetIndex = i;
        this.axis = axisDependency;
    }

    public Highlight(
            float f,
            float f2,
            float f3,
            float f4,
            int i,
            int i2,
            YAxis.AxisDependency axisDependency) {
        this(f, f2, f3, f4, i, axisDependency);
        this.mStackIndex = i2;
    }
}
