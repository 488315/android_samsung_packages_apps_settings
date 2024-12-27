package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet$Rounding;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.sec.ims.configuration.DATA;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class BarChartRenderer extends BarLineScatterCandleBubbleRenderer {
    public final Paint mBarBorderPaint;
    public BarBuffer[] mBarBuffers;
    public final RectF mBarRect;
    public final RectF mBarShadowRectBuffer;
    public BarDataProvider mChart;
    public final Paint mShadowPaint;

    public BarChartRenderer(
            BarDataProvider barDataProvider,
            ChartAnimator chartAnimator,
            ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mBarRect = new RectF();
        new RectF();
        this.mChart = barDataProvider;
        Paint paint = new Paint(1);
        this.mHighlightPaint = paint;
        Paint.Style style = Paint.Style.FILL;
        paint.setStyle(style);
        this.mHighlightPaint.setColor(Color.rgb(0, 0, 0));
        this.mHighlightPaint.setAlpha(120);
        Paint paint2 = new Paint(1);
        this.mShadowPaint = paint2;
        paint2.setStyle(style);
        Paint paint3 = new Paint(1);
        this.mBarBorderPaint = paint3;
        paint3.setStyle(Paint.Style.STROKE);
    }

    @Override // com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer
    public final void drawData(Canvas canvas) {
        BarData barData = (BarData) ((BarChart) this.mChart).mData;
        for (int i = 0; i < barData.getDataSetCount(); i++) {
            BarDataSet barDataSet = (BarDataSet) barData.getDataSetByIndex(i);
            if (barDataSet.mVisible) {
                drawDataSet(canvas, barDataSet, i);
            }
        }
    }

    public void drawDataSet(Canvas canvas, BarDataSet barDataSet, int i) {
        Transformer transformer =
                ((BarLineChartBase) this.mChart).getTransformer(barDataSet.mAxisDependency);
        this.mBarBorderPaint.setColor(barDataSet.mBarBorderColor);
        this.mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(0.0f));
        this.mAnimator.getClass();
        ((BarChart) this.mChart).getClass();
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        BarBuffer barBuffer = this.mBarBuffers[i];
        barBuffer.phaseX = 1.0f;
        barBuffer.phaseY = 1.0f;
        ((BarLineChartBase) this.mChart).isInverted(barDataSet.mAxisDependency);
        int i2 = 0;
        barBuffer.mInverted = false;
        barBuffer.mBarWidth = ((BarData) ((BarChart) this.mChart).mData).mBarWidth;
        barBuffer.feed(barDataSet);
        float[] fArr = barBuffer.buffer;
        transformer.pointValuesToPixel(fArr);
        boolean z = barDataSet.mColors.size() == 1;
        ((BarLineChartBase) this.mChart).isInverted(barDataSet.mAxisDependency);
        if (z) {
            this.mRenderPaint.setColor(barDataSet.getColor());
        }
        int i3 = 0;
        while (i2 < fArr.length) {
            int i4 = i2 + 2;
            if (viewPortHandler.isInBoundsLeft(fArr[i4])) {
                if (!viewPortHandler.isInBoundsRight(fArr[i2])) {
                    return;
                }
                if (!z) {
                    this.mRenderPaint.setColor(barDataSet.getColor(i3));
                }
                canvas.drawRect(fArr[i2], fArr[i2 + 1], fArr[i4], fArr[i2 + 3], this.mRenderPaint);
            }
            i2 += 4;
            i3++;
        }
    }

    @Override // com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer
    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        float f;
        float f2;
        float f3;
        float f4;
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
                    this.mHighlightPaint.setColor(barDataSet.mHighLightColor);
                    this.mHighlightPaint.setAlpha(barDataSet.mHighLightAlpha);
                    int i = highlight.mStackIndex;
                    if (i < 0 || barEntry.mYVals == null) {
                        f = barEntry.y;
                        f2 = 0.0f;
                    } else if (((BarChart) this.mChart).mHighlightFullBarEnabled) {
                        f = barEntry.mPositiveSum;
                        f2 = -barEntry.mNegativeSum;
                    } else {
                        Range range = barEntry.mRanges[i];
                        f4 = range.from;
                        f3 = range.to;
                        prepareBarHighlight(
                                barEntry.x, f4, f3, barData.mBarWidth / 2.0f, transformer);
                        RectF rectF = this.mBarRect;
                        float centerX = rectF.centerX();
                        float f5 = rectF.top;
                        highlight.mDrawX = centerX;
                        highlight.mDrawY = f5;
                        canvas.drawRect(this.mBarRect, this.mHighlightPaint);
                    }
                    f3 = f2;
                    f4 = f;
                    prepareBarHighlight(barEntry.x, f4, f3, barData.mBarWidth / 2.0f, transformer);
                    RectF rectF2 = this.mBarRect;
                    float centerX2 = rectF2.centerX();
                    float f52 = rectF2.top;
                    highlight.mDrawX = centerX2;
                    highlight.mDrawY = f52;
                    canvas.drawRect(this.mBarRect, this.mHighlightPaint);
                }
            }
        }
    }

    @Override // com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer
    public void drawValues(Canvas canvas) {
        List list;
        float f;
        boolean z;
        MPPointF mPPointF;
        boolean z2;
        float[] fArr;
        int i;
        int i2;
        float f2;
        float[] fArr2;
        int i3;
        float f3;
        int i4;
        List list2;
        float f4;
        boolean z3;
        ViewPortHandler viewPortHandler;
        MPPointF mPPointF2;
        BarBuffer barBuffer;
        BarChartRenderer barChartRenderer = this;
        BarLineChartBase barLineChartBase = (BarLineChartBase) barChartRenderer.mChart;
        if (barLineChartBase.mData.getEntryCount()
                < barLineChartBase.mMaxVisibleCount * barChartRenderer.mViewPortHandler.mScaleX) {
            List list3 = ((BarData) ((BarChart) barChartRenderer.mChart).mData).mDataSets;
            float convertDpToPixel = Utils.convertDpToPixel(4.5f);
            boolean z4 = ((BarChart) barChartRenderer.mChart).mDrawValueAboveBar;
            int i5 = 0;
            while (i5 < ((BarData) ((BarChart) barChartRenderer.mChart).mData).getDataSetCount()) {
                BarDataSet barDataSet = (BarDataSet) list3.get(i5);
                if (barDataSet.mVisible && (barDataSet.mDrawValues || barDataSet.mDrawIcons)) {
                    barChartRenderer.mValuePaint.setTypeface(null);
                    barChartRenderer.mValuePaint.setTextSize(barDataSet.mValueTextSize);
                    ((BarLineChartBase) barChartRenderer.mChart)
                            .isInverted(barDataSet.mAxisDependency);
                    float calcTextHeight =
                            Utils.calcTextHeight(
                                    barChartRenderer.mValuePaint,
                                    DATA.DM_FIELD_INDEX.SIP_SESSION_TIMER);
                    float f5 = z4 ? -convertDpToPixel : calcTextHeight + convertDpToPixel;
                    float f6 = z4 ? calcTextHeight + convertDpToPixel : -convertDpToPixel;
                    BarBuffer barBuffer2 = barChartRenderer.mBarBuffers[i5];
                    barChartRenderer.mAnimator.getClass();
                    MPPointF mPPointF3 = barDataSet.mIconsOffset;
                    MPPointF mPPointF4 = (MPPointF) MPPointF.pool.get();
                    float f7 = mPPointF3.x;
                    mPPointF4.x = f7;
                    mPPointF4.y = mPPointF3.y;
                    mPPointF4.x = Utils.convertDpToPixel(f7);
                    mPPointF4.y = Utils.convertDpToPixel(mPPointF4.y);
                    boolean z5 = true;
                    boolean z6 = barDataSet.mStackSize > 1;
                    ViewPortHandler viewPortHandler2 = barChartRenderer.mViewPortHandler;
                    if (z6) {
                        list = list3;
                        f = convertDpToPixel;
                        z = z4;
                        mPPointF = mPPointF4;
                        Transformer transformer =
                                ((BarLineChartBase) barChartRenderer.mChart)
                                        .getTransformer(barDataSet.mAxisDependency);
                        int i6 = 0;
                        int i7 = 0;
                        while (i6 < barDataSet.mEntries.size() * 1.0f) {
                            BarEntry barEntry = (BarEntry) barDataSet.getEntryForIndex(i6);
                            float[] fArr3 = barEntry.mYVals;
                            float[] fArr4 = barBuffer2.buffer;
                            float f8 = (fArr4[i7] + fArr4[i7 + 2]) / 2.0f;
                            int valueTextColor = barDataSet.getValueTextColor(i6);
                            if (fArr3 != null) {
                                z2 = z5;
                                fArr = fArr3;
                                i = i6;
                                float f9 = f8;
                                int length = fArr.length * 2;
                                float[] fArr5 = new float[length];
                                float f10 = -barEntry.mNegativeSum;
                                float f11 = 0.0f;
                                int i8 = 0;
                                int i9 = 0;
                                while (i8 < length) {
                                    float f12 = fArr[i9];
                                    if (f12 == 0.0f && (f11 == 0.0f || f10 == 0.0f)) {
                                        float f13 = f10;
                                        f10 = f12;
                                        f3 = f13;
                                    } else if (f12 >= 0.0f) {
                                        f11 += f12;
                                        f3 = f10;
                                        f10 = f11;
                                    } else {
                                        f3 = f10 - f12;
                                    }
                                    fArr5[i8 + 1] = f10 * 1.0f;
                                    i8 += 2;
                                    i9++;
                                    f10 = f3;
                                }
                                transformer.pointValuesToPixel(fArr5);
                                int i10 = 0;
                                while (i10 < length) {
                                    int i11 = i10 / 2;
                                    float f14 = fArr[i11];
                                    float f15 =
                                            fArr5[i10 + 1]
                                                    + ((((f14 > 0.0f ? 1 : (f14 == 0.0f ? 0 : -1))
                                                                                    != 0
                                                                            || (f10 > 0.0f
                                                                                            ? 1
                                                                                            : (f10
                                                                                                            == 0.0f
                                                                                                    ? 0
                                                                                                    : -1))
                                                                                    != 0
                                                                            || (f11 > 0.0f
                                                                                            ? 1
                                                                                            : (f11
                                                                                                            == 0.0f
                                                                                                    ? 0
                                                                                                    : -1))
                                                                                    <= 0)
                                                                    && (f14 > 0.0f
                                                                                    ? 1
                                                                                    : (f14 == 0.0f
                                                                                            ? 0
                                                                                            : -1))
                                                                            >= 0)
                                                            ? false
                                                            : z2 ? f6 : f5);
                                    if (!viewPortHandler2.isInBoundsRight(f9)) {
                                        break;
                                    }
                                    if (viewPortHandler2.isInBoundsY(f15)
                                            && viewPortHandler2.isInBoundsLeft(f9)
                                            && barDataSet.mDrawValues) {
                                        i2 = i10;
                                        f2 = f9;
                                        fArr2 = fArr5;
                                        i3 = length;
                                        drawValue(
                                                canvas,
                                                barDataSet.getValueFormatter(),
                                                fArr[i11],
                                                f9,
                                                f15,
                                                valueTextColor);
                                    } else {
                                        i2 = i10;
                                        f2 = f9;
                                        fArr2 = fArr5;
                                        i3 = length;
                                    }
                                    i10 = i2 + 2;
                                    f9 = f2;
                                    length = i3;
                                    fArr5 = fArr2;
                                }
                            } else {
                                if (!viewPortHandler2.isInBoundsRight(f8)) {
                                    break;
                                }
                                int i12 = i7 + 1;
                                float[] fArr6 = barBuffer2.buffer;
                                if (!viewPortHandler2.isInBoundsY(fArr6[i12])
                                        || !viewPortHandler2.isInBoundsLeft(f8)) {
                                    z5 = true;
                                    i6 = i6;
                                } else if (barDataSet.mDrawValues) {
                                    DefaultValueFormatter valueFormatter =
                                            barDataSet.getValueFormatter();
                                    float f16 = barEntry.y;
                                    z2 = true;
                                    fArr = fArr3;
                                    i = i6;
                                    drawValue(
                                            canvas,
                                            valueFormatter,
                                            f16,
                                            f8,
                                            fArr6[i12] + (f16 >= 0.0f ? f5 : f6),
                                            valueTextColor);
                                } else {
                                    fArr = fArr3;
                                    i = i6;
                                    z2 = true;
                                }
                            }
                            i7 = fArr == null ? i7 + 4 : (fArr.length * 4) + i7;
                            i6 = i + 1;
                            z5 = z2;
                        }
                    } else {
                        int i13 = 0;
                        while (true) {
                            float f17 = i13;
                            float[] fArr7 = barBuffer2.buffer;
                            if (f17 >= fArr7.length * 1.0f) {
                                break;
                            }
                            float f18 = (fArr7[i13] + fArr7[i13 + 2]) / 2.0f;
                            if (!viewPortHandler2.isInBoundsRight(f18)) {
                                break;
                            }
                            int i14 = i13 + 1;
                            if (viewPortHandler2.isInBoundsY(fArr7[i14])
                                    && viewPortHandler2.isInBoundsLeft(f18)) {
                                int i15 = i13 / 4;
                                float f19 = ((BarEntry) barDataSet.getEntryForIndex(i15)).y;
                                ViewPortHandler viewPortHandler3 = viewPortHandler2;
                                if (barDataSet.mDrawValues) {
                                    list2 = list3;
                                    viewPortHandler = viewPortHandler3;
                                    i4 = i13;
                                    f4 = convertDpToPixel;
                                    mPPointF2 = mPPointF4;
                                    z3 = z4;
                                    barBuffer = barBuffer2;
                                    drawValue(
                                            canvas,
                                            barDataSet.getValueFormatter(),
                                            f19,
                                            f18,
                                            f19 >= 0.0f ? fArr7[i14] + f5 : fArr7[i13 + 3] + f6,
                                            barDataSet.getValueTextColor(i15));
                                    i13 = i4 + 4;
                                    viewPortHandler2 = viewPortHandler;
                                    mPPointF4 = mPPointF2;
                                    barBuffer2 = barBuffer;
                                    convertDpToPixel = f4;
                                    z4 = z3;
                                    list3 = list2;
                                } else {
                                    list2 = list3;
                                    f4 = convertDpToPixel;
                                    z3 = z4;
                                    viewPortHandler = viewPortHandler3;
                                    i4 = i13;
                                }
                            } else {
                                i4 = i13;
                                list2 = list3;
                                f4 = convertDpToPixel;
                                z3 = z4;
                                viewPortHandler = viewPortHandler2;
                            }
                            mPPointF2 = mPPointF4;
                            barBuffer = barBuffer2;
                            i13 = i4 + 4;
                            viewPortHandler2 = viewPortHandler;
                            mPPointF4 = mPPointF2;
                            barBuffer2 = barBuffer;
                            convertDpToPixel = f4;
                            z4 = z3;
                            list3 = list2;
                        }
                        list = list3;
                        f = convertDpToPixel;
                        z = z4;
                        mPPointF = mPPointF4;
                    }
                    MPPointF.pool.recycle(mPPointF);
                } else {
                    list = list3;
                    f = convertDpToPixel;
                    z = z4;
                }
                i5++;
                barChartRenderer = this;
                convertDpToPixel = f;
                z4 = z;
                list3 = list;
            }
        }
    }

    @Override // com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer
    public final void initBuffers() {
        BarData barData = (BarData) ((BarChart) this.mChart).mData;
        this.mBarBuffers = new BarBuffer[barData.getDataSetCount()];
        for (int i = 0; i < this.mBarBuffers.length; i++) {
            BarDataSet barDataSet = (BarDataSet) barData.getDataSetByIndex(i);
            BarBuffer[] barBufferArr = this.mBarBuffers;
            int size = barDataSet.mEntries.size() * 4;
            int i2 = barDataSet.mStackSize;
            boolean z = true;
            if (i2 <= 1) {
                i2 = 1;
            }
            int i3 = size * i2;
            barData.getDataSetCount();
            if (barDataSet.mStackSize <= 1) {
                z = false;
            }
            barBufferArr[i] = new BarBuffer(i3, z);
        }
    }

    public final void prepareBarHighlight(
            float f, float f2, float f3, float f4, Transformer transformer) {
        this.mBarRect.set(f - f4, f2, f + f4, f3);
        RectF rectF = this.mBarRect;
        this.mAnimator.getClass();
        transformer.getClass();
        rectF.top *= 1.0f;
        rectF.bottom *= 1.0f;
        transformer.mMatrixValueToPx.mapRect(rectF);
        transformer.mViewPortHandler.mMatrixTouch.mapRect(rectF);
        transformer.mMatrixOffset.mapRect(rectF);
    }

    @Override // com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer
    public final void drawExtras(Canvas canvas) {}
}
