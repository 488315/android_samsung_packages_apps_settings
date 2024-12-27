package com.samsung.android.settings.wifi.mobileap.clients.report.chart;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet$Rounding;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DataBarRendererMonthly extends AbsRoundBarRenderer {
    public final void drawBufferDataSet$1(
            Canvas canvas, float[] fArr, BarDataSet barDataSet, boolean z) {
        int i = 0;
        while (i < fArr.length) {
            int i2 = (this.mEntrySize - 4) + i;
            int i3 = i2 + 2;
            float f = fArr[i3];
            ViewPortHandler viewPortHandler = this.mViewPortHandler;
            if (viewPortHandler.isInBoundsLeft(f)) {
                if (!viewPortHandler.isInBoundsRight(fArr[i2])) {
                    return;
                }
                if (z) {
                    this.mRenderPaint.setColor(barDataSet.getColor(i2 / this.mEntrySize));
                }
                float f2 = fArr[i2];
                int i4 = i2 + 1;
                float f3 = fArr[i4];
                float f4 = fArr[i3];
                int i5 = i2 + 3;
                float f5 = fArr[i5];
                RectF rectF = new RectF(fArr[i2], fArr[i4], fArr[i3], fArr[i5]);
                float f6 = this.mRadius;
                float f7 = rectF.top;
                float f8 = rectF.left;
                float f9 = rectF.right;
                float f10 = rectF.bottom;
                Path path = new Path();
                float f11 = f6 < 0.0f ? 0.0f : f6;
                if (f6 < 0.0f) {
                    f6 = 0.0f;
                }
                float f12 = f9 - f8;
                float f13 = f10 - f7;
                float f14 = f12 / 2.0f;
                if (f11 > f14) {
                    f11 = f14;
                }
                float f15 = f13 / 2.0f;
                if (f6 > f15) {
                    f6 = f15;
                }
                float f16 = f12 - (f11 * 2.0f);
                float f17 = f13 - (2.0f * f6);
                path.moveTo(f9, f7 + f6);
                float f18 = -f6;
                float f19 = -f11;
                path.rQuadTo(0.0f, f18, f19, f18);
                path.rLineTo(-f16, 0.0f);
                path.rQuadTo(f19, 0.0f, f19, f6);
                path.rLineTo(0.0f, f17);
                path.rLineTo(0.0f, f6);
                path.rLineTo(f11, 0.0f);
                path.rLineTo(f16, 0.0f);
                path.rLineTo(f11, 0.0f);
                path.rLineTo(0.0f, f18);
                path.rLineTo(0.0f, -f17);
                path.close();
                canvas.drawPath(path, this.mRenderPaint);
            }
            i += this.mEntrySize;
        }
    }

    @Override // com.samsung.android.settings.wifi.mobileap.clients.report.chart.AbsRoundBarRenderer, com.github.mikephil.charting.renderer.BarChartRenderer
    public final void drawDataSet(Canvas canvas, BarDataSet barDataSet, int i) {
        super.drawDataSet(canvas, barDataSet, i);
        boolean z = barDataSet.mColors.size() > 1;
        int i2 = this.mEntrySize * 2;
        int i3 = this.mSectionEntrySize;
        if (i3 != 0) {
            float[] fArr = this.mBuffer.buffer;
            if (fArr.length % i3 == 0 && this.mMissingIndex != -1 && fArr.length >= i2) {
                initAdjustedBuffer(fArr.length);
                drawBufferDataSet$1(canvas, this.mAdjustedBuffer, barDataSet, z);
                return;
            }
        }
        drawBufferDataSet$1(canvas, this.mBuffer.buffer, barDataSet, z);
    }

    @Override // com.github.mikephil.charting.renderer.BarChartRenderer,
              // com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer
    public final void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        BarData barData = (BarData) ((BarChart) this.mChart).mData;
        for (Highlight highlight : highlightArr) {
            BarDataSet barDataSet = (BarDataSet) barData.getDataSetByIndex(highlight.mDataSetIndex);
            if (barDataSet != null && barDataSet.mHighlightEnabled) {
                BarEntry barEntry =
                        (BarEntry)
                                barDataSet.getEntryForXValue(
                                        highlight.mX, highlight.mY, DataSet$Rounding.CLOSEST);
                if (isInBoundsX(barEntry, barDataSet)) {
                    Transformer transformer =
                            ((BarLineChartBase) this.mChart)
                                    .getTransformer(barDataSet.mAxisDependency);
                    float f = barEntry.y;
                    prepareBarHighlight(barEntry.x, f, f, barData.mBarWidth / 2.0f, transformer);
                    RectF rectF = this.mBarRect;
                    float centerX = rectF.centerX();
                    float f2 = rectF.top;
                    highlight.mDrawX = centerX;
                    highlight.mDrawY = f2;
                }
            }
        }
    }
}
