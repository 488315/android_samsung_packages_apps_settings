package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.DisplayMetrics;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class XAxisRenderer extends AxisRenderer {
    public final RectF mGridClippingRect;
    public final RectF mLimitLineClippingRect;
    public final Path mLimitLinePath;
    public final float[] mLimitLineSegmentsBuffer;
    public float[] mRenderGridLinesBuffer;
    public final Path mRenderGridLinesPath;
    public final float[] mRenderLimitLinesBuffer;
    public final XAxis mXAxis;

    public XAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer transformer) {
        super(viewPortHandler, transformer, xAxis);
        this.mRenderGridLinesPath = new Path();
        this.mRenderGridLinesBuffer = new float[2];
        this.mGridClippingRect = new RectF();
        this.mRenderLimitLinesBuffer = new float[2];
        this.mLimitLineClippingRect = new RectF();
        this.mLimitLineSegmentsBuffer = new float[4];
        this.mLimitLinePath = new Path();
        this.mXAxis = xAxis;
        this.mAxisLabelPaint.setColor(EmergencyPhoneWidget.BG_COLOR);
        this.mAxisLabelPaint.setTextAlign(Paint.Align.CENTER);
        this.mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(10.0f));
    }

    @Override // com.github.mikephil.charting.renderer.AxisRenderer
    public final void computeAxis(float f, float f2) {
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        if (viewPortHandler.mContentRect.width() > 10.0f) {
            float f3 = viewPortHandler.mScaleX;
            float f4 = viewPortHandler.mMinScaleX;
            if (!(f3 <= f4 && f4 <= 1.0f)) {
                RectF rectF = viewPortHandler.mContentRect;
                float f5 = rectF.left;
                float f6 = rectF.top;
                Transformer transformer = this.mTrans;
                transformer.getClass();
                MPPointD mPPointD = MPPointD.getInstance(0.0d, 0.0d);
                transformer.getValuesByTouchPoint(f5, f6, mPPointD);
                RectF rectF2 = viewPortHandler.mContentRect;
                float f7 = rectF2.right;
                float f8 = rectF2.top;
                MPPointD mPPointD2 = MPPointD.getInstance(0.0d, 0.0d);
                transformer.getValuesByTouchPoint(f7, f8, mPPointD2);
                f = (float) mPPointD.x;
                f2 = (float) mPPointD2.x;
                MPPointD.recycleInstance(mPPointD);
                MPPointD.recycleInstance(mPPointD2);
            }
        }
        computeAxisValues(f, f2);
    }

    @Override // com.github.mikephil.charting.renderer.AxisRenderer
    public final void computeAxisValues(float f, float f2) {
        super.computeAxisValues(f, f2);
        XAxis xAxis = this.mXAxis;
        String longestLabel = xAxis.getLongestLabel();
        this.mAxisLabelPaint.setTypeface(xAxis.mTypeface);
        this.mAxisLabelPaint.setTextSize(xAxis.mTextSize);
        FSize calcTextSize = Utils.calcTextSize(this.mAxisLabelPaint, longestLabel);
        float f3 = calcTextSize.width;
        float calcTextHeight = Utils.calcTextHeight(this.mAxisLabelPaint, "Q");
        double d = 0.0f;
        float abs =
                Math.abs(((float) Math.sin(d)) * calcTextHeight)
                        + Math.abs(((float) Math.cos(d)) * f3);
        float abs2 =
                Math.abs(((float) Math.cos(d)) * calcTextHeight)
                        + Math.abs(((float) Math.sin(d)) * f3);
        FSize fSize = (FSize) FSize.pool.get();
        fSize.width = abs;
        fSize.height = abs2;
        Math.round(f3);
        Math.round(calcTextHeight);
        Math.round(fSize.width);
        xAxis.mLabelRotatedHeight = Math.round(fSize.height);
        FSize.pool.recycle(fSize);
        FSize.pool.recycle(calcTextSize);
    }

    public final void drawLabel(Canvas canvas, String str, float f, float f2, MPPointF mPPointF) {
        Paint paint = this.mAxisLabelPaint;
        Paint.FontMetrics fontMetrics = Utils.mFontMetricsBuffer;
        float fontMetrics2 = paint.getFontMetrics(fontMetrics);
        paint.getTextBounds(str, 0, str.length(), Utils.mDrawTextRectBuffer);
        float f3 = 0.0f - r3.left;
        float f4 = (-fontMetrics.ascent) + 0.0f;
        Paint.Align textAlign = paint.getTextAlign();
        paint.setTextAlign(Paint.Align.LEFT);
        if (mPPointF.x != 0.0f || mPPointF.y != 0.0f) {
            f3 -= r3.width() * mPPointF.x;
            f4 -= fontMetrics2 * mPPointF.y;
        }
        canvas.drawText(str, f3 + f, f4 + f2, paint);
        paint.setTextAlign(textAlign);
    }

    public void drawLabels(Canvas canvas, float f, MPPointF mPPointF) {
        float f2;
        XAxis xAxis = this.mXAxis;
        xAxis.getClass();
        int i = xAxis.mEntryCount * 2;
        float[] fArr = new float[i];
        for (int i2 = 0; i2 < i; i2 += 2) {
            fArr[i2] = xAxis.mEntries[i2 / 2];
        }
        this.mTrans.pointValuesToPixel(fArr);
        for (int i3 = 0; i3 < i; i3 += 2) {
            float f3 = fArr[i3];
            ViewPortHandler viewPortHandler = this.mViewPortHandler;
            if (viewPortHandler.isInBoundsX(f3)) {
                int i4 = i3 / 2;
                String formattedValue =
                        xAxis.getValueFormatter().getFormattedValue(xAxis.mEntries[i4]);
                if (xAxis.mAvoidFirstLastClipping) {
                    int i5 = xAxis.mEntryCount;
                    if (i4 == i5 - 1 && i5 > 1) {
                        Paint paint = this.mAxisLabelPaint;
                        DisplayMetrics displayMetrics = Utils.mMetrics;
                        float measureText = (int) paint.measureText(formattedValue);
                        float f4 = viewPortHandler.mChartWidth;
                        if (measureText > (f4 - viewPortHandler.mContentRect.right) * 2.0f
                                && f3 + measureText > f4) {
                            f3 -= measureText / 2.0f;
                        }
                    } else if (i3 == 0) {
                        Paint paint2 = this.mAxisLabelPaint;
                        DisplayMetrics displayMetrics2 = Utils.mMetrics;
                        f2 = (((int) paint2.measureText(formattedValue)) / 2.0f) + f3;
                        drawLabel(canvas, formattedValue, f2, f, mPPointF);
                    }
                }
                f2 = f3;
                drawLabel(canvas, formattedValue, f2, f, mPPointF);
            }
        }
    }

    public void renderGridLines(Canvas canvas) {
        XAxis xAxis = this.mXAxis;
        if (xAxis.mDrawGridLines && xAxis.mEnabled) {
            int save = canvas.save();
            RectF rectF = this.mGridClippingRect;
            ViewPortHandler viewPortHandler = this.mViewPortHandler;
            rectF.set(viewPortHandler.mContentRect);
            RectF rectF2 = this.mGridClippingRect;
            AxisBase axisBase = this.mAxis;
            rectF2.inset(-axisBase.mGridLineWidth, 0.0f);
            canvas.clipRect(this.mGridClippingRect);
            if (this.mRenderGridLinesBuffer.length != axisBase.mEntryCount * 2) {
                this.mRenderGridLinesBuffer = new float[xAxis.mEntryCount * 2];
            }
            float[] fArr = this.mRenderGridLinesBuffer;
            for (int i = 0; i < fArr.length; i += 2) {
                float[] fArr2 = xAxis.mEntries;
                int i2 = i / 2;
                fArr[i] = fArr2[i2];
                fArr[i + 1] = fArr2[i2];
            }
            this.mTrans.pointValuesToPixel(fArr);
            this.mGridPaint.setColor(xAxis.mGridColor);
            this.mGridPaint.setStrokeWidth(xAxis.mGridLineWidth);
            this.mGridPaint.setPathEffect(null);
            Path path = this.mRenderGridLinesPath;
            path.reset();
            for (int i3 = 0; i3 < fArr.length; i3 += 2) {
                float f = fArr[i3];
                float f2 = fArr[i3 + 1];
                path.moveTo(f, viewPortHandler.mContentRect.bottom);
                path.lineTo(f, viewPortHandler.mContentRect.top);
                canvas.drawPath(path, this.mGridPaint);
                path.reset();
            }
            canvas.restoreToCount(save);
        }
    }
}
