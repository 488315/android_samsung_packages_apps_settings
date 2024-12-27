package com.samsung.android.settings.wifi.mobileap.clients.report.chart;

import android.graphics.Canvas;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.utils.MPPointF;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WeeklyExtendedXAxisRenderer extends AbsXAxisRenderer {
    public String mExtraLabel;
    public float mLabelMarginTop;
    public float mOffsetLabelPosition;

    @Override // com.samsung.android.settings.wifi.mobileap.clients.report.chart.AbsXAxisRenderer,
              // com.github.mikephil.charting.renderer.XAxisRenderer
    public final void drawLabels(Canvas canvas, float f, MPPointF mPPointF) {
        super.drawLabels(canvas, f, mPPointF);
        float[] fArr = this.mPositions;
        int length = fArr.length;
        if (length > 3) {
            this.mOffsetLabelPosition = (fArr[2] - fArr[0]) / 2.0f;
        }
        XAxis xAxis = this.mXAxis;
        if (xAxis.mEntryCount == 7) {
            this.mExtraLabel =
                    ViewUtils.isRTL()
                            ? xAxis.getValueFormatter()
                                    .getFormattedValue(xAxis.mEntries[xAxis.mEntryCount - 1])
                            : xAxis.getValueFormatter().getFormattedValue(xAxis.mEntries[0]);
        }
        for (int i = 0; i < length; i += 2) {
            float f2 = this.mPositions[i];
            if (this.mViewPortHandler.isInBoundsX(f2)) {
                String formattedValue =
                        xAxis.getValueFormatter().getFormattedValue(xAxis.mEntries[i / 2]);
                boolean isRTL = ViewUtils.isRTL();
                float f3 = this.mLabelMarginTop;
                if (isRTL) {
                    if (i == 0) {
                        drawLabel(
                                canvas,
                                this.mExtraLabel,
                                f2 - this.mOffsetLabelPosition,
                                f + f3,
                                mPPointF);
                    }
                    drawLabel(
                            canvas,
                            formattedValue,
                            f2 + this.mOffsetLabelPosition,
                            f + f3,
                            mPPointF);
                } else {
                    float f4 = f3 + f;
                    drawLabel(canvas, formattedValue, f2 - this.mOffsetLabelPosition, f4, mPPointF);
                    if (i == length - 2) {
                        drawLabel(
                                canvas,
                                this.mExtraLabel,
                                f2 + this.mOffsetLabelPosition,
                                f4,
                                mPPointF);
                    }
                }
            }
        }
    }
}
