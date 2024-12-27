package com.samsung.android.settings.wifi.develop.weeklyreport;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WeeklyXAxisRenderer extends XAxisRenderer {
    public float mLabelBgRadius;
    public int mLabelBgYOffset;
    public float mLabelMargin;
    public Paint mLabelPaint;
    public float[] mPositions;
    public boolean mSelectedInfoLoaded;
    public int mSelectedLabel;
    public Paint mSelectedLabelBgPaint;
    public Paint mSelectedLabelPaint;

    @Override // com.github.mikephil.charting.renderer.XAxisRenderer
    public final void drawLabels(Canvas canvas, float f, MPPointF mPPointF) {
        float[] fArr;
        XAxis xAxis = this.mXAxis;
        xAxis.getClass();
        this.mPositions = new float[xAxis.mEntryCount * 2];
        int i = 0;
        while (true) {
            fArr = this.mPositions;
            if (i >= fArr.length) {
                break;
            }
            fArr[i] = xAxis.mEntries[i / 2];
            i += 2;
        }
        this.mTrans.pointValuesToPixel(fArr);
        float f2 = f + this.mLabelMargin;
        int length = this.mPositions.length;
        for (int i2 = 0; i2 < length; i2 += 2) {
            float f3 = this.mPositions[i2];
            if (this.mViewPortHandler.isInBoundsX(f3)) {
                String formattedValue =
                        xAxis.getValueFormatter().getFormattedValue(xAxis.mEntries[i2 / 2]);
                if (this.mSelectedInfoLoaded && i2 == this.mSelectedLabel * 2) {
                    canvas.drawCircle(
                            f3,
                            ((int) f2) + this.mLabelBgYOffset,
                            this.mLabelBgRadius,
                            this.mSelectedLabelBgPaint);
                    this.mAxisLabelPaint.set(this.mSelectedLabelPaint);
                } else {
                    this.mAxisLabelPaint.set(this.mLabelPaint);
                }
                drawLabel(canvas, formattedValue, f3, f2, mPPointF);
            }
        }
    }
}
