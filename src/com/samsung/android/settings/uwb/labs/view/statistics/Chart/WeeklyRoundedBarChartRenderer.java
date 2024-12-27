package com.samsung.android.settings.uwb.labs.view.statistics.Chart;

import android.graphics.Canvas;
import android.graphics.Path;

import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WeeklyRoundedBarChartRenderer extends BarChartRenderer {
    public float mRadius;

    public static Path RoundedRect(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f3 - f;
        if (f5 < 0.0f || f6 < 0.0f) {
            f5 = f7 / 2.0f;
            f6 = f5;
        }
        float f8 = (f4 + f5) - f2;
        float f9 = f7 / 2.0f;
        if (f5 > f9) {
            f5 = f9;
        }
        float f10 = f8 / 2.0f;
        if (f6 > f10) {
            f6 = f10;
        }
        float f11 = f7 - (f5 * 2.0f);
        float f12 = f8 - (2.0f * f6);
        Path path = new Path();
        path.moveTo(f3, f2 + f6);
        float f13 = -f6;
        path.rQuadTo(0.0f, f13, -f5, f13);
        path.rLineTo(-f11, 0.0f);
        float f14 = -f5;
        path.rQuadTo(f14, 0.0f, f14, f6);
        path.rLineTo(0.0f, f12);
        path.rLineTo(0.0f, f6);
        path.rLineTo(f5, 0.0f);
        path.rLineTo(f11, 0.0f);
        path.rLineTo(f5, 0.0f);
        path.rLineTo(0.0f, -f6);
        path.rLineTo(0.0f, -f12);
        path.close();
        return path;
    }

    @Override // com.github.mikephil.charting.renderer.BarChartRenderer
    public final void drawDataSet(Canvas canvas, BarDataSet barDataSet, int i) {
        int length;
        Transformer transformer =
                ((BarLineChartBase) this.mChart).getTransformer(barDataSet.mAxisDependency);
        this.mShadowPaint.setColor(barDataSet.mBarShadowColor);
        this.mAnimator.getClass();
        BarBuffer[] barBufferArr = this.mBarBuffers;
        if (barBufferArr != null) {
            BarBuffer barBuffer = barBufferArr[i];
            barBuffer.phaseX = 1.0f;
            barBuffer.phaseY = 1.0f;
            BarLineScatterCandleBubbleDataProvider barLineScatterCandleBubbleDataProvider =
                    this.mChart;
            barBuffer.mBarWidth =
                    ((BarData) ((BarChart) barLineScatterCandleBubbleDataProvider).mData).mBarWidth;
            ((BarLineChartBase) barLineScatterCandleBubbleDataProvider)
                    .isInverted(barDataSet.mAxisDependency);
            int i2 = 0;
            barBuffer.mInverted = false;
            barBuffer.feed(barDataSet);
            float[] fArr = barBuffer.buffer;
            transformer.pointValuesToPixel(fArr);
            int size = barDataSet.mColors.size();
            ViewPortHandler viewPortHandler = this.mViewPortHandler;
            if (size <= 1) {
                this.mRenderPaint.setColor(barDataSet.getColor());
                while (i2 < fArr.length) {
                    int i3 = i2 + 2;
                    if (viewPortHandler.isInBoundsLeft(fArr[i3])) {
                        if (!viewPortHandler.isInBoundsRight(fArr[i2])) {
                            return;
                        }
                        ((BarChart) this.mChart).getClass();
                        float f = fArr[i2];
                        float f2 = fArr[i2 + 1];
                        float f3 = fArr[i3];
                        float f4 = fArr[i2 + 3];
                        float f5 = this.mRadius;
                        canvas.drawPath(RoundedRect(f, f2, f3, f4, f5, f5), this.mRenderPaint);
                    }
                    i2 += 4;
                }
                return;
            }
            int i4 = 0;
            for (int i5 = 0; i5 < barDataSet.mEntries.size(); i5++) {
                BarEntry barEntry = (BarEntry) barDataSet.getEntryForIndex(i5);
                if (barEntry != null) {
                    i4 += barEntry.mYVals.length;
                }
            }
            if (i4 * 4 == fArr.length) {
                float[] fArr2 = (float[]) fArr.clone();
                int i6 = 0;
                for (int i7 = 0; i7 < barDataSet.mEntries.size(); i7++) {
                    BarEntry barEntry2 = (BarEntry) barDataSet.getEntryForIndex(i7);
                    if (barEntry2 != null && (length = barEntry2.mYVals.length) != 0) {
                        int i8 = ((length - 1) * 4) + i6;
                        float f6 = fArr2[i6 + 3];
                        for (int i9 = 0; i9 < length; i9++) {
                            fArr[i6] = fArr2[i8];
                            fArr[i6 + 1] = fArr2[i8 + 1];
                            fArr[i6 + 2] = fArr2[i8 + 2];
                            fArr[i6 + 3] = f6;
                            i6 += 4;
                            i8 -= 4;
                        }
                    }
                }
            }
            while (i2 < fArr.length) {
                int i10 = i2 + 2;
                if (viewPortHandler.isInBoundsLeft(fArr[i10])) {
                    if (!viewPortHandler.isInBoundsRight(fArr[i2])) {
                        return;
                    }
                    ((BarChart) this.mChart).getClass();
                    this.mRenderPaint.setColor(barDataSet.getColor(i2 / 4));
                    float f7 = fArr[i2];
                    float f8 = fArr[i2 + 1];
                    float f9 = fArr[i10];
                    float f10 = fArr[i2 + 3];
                    float f11 = this.mRadius;
                    canvas.drawPath(RoundedRect(f7, f8, f9, f10, f11, f11), this.mRenderPaint);
                }
                i2 += 4;
            }
        }
    }
}
