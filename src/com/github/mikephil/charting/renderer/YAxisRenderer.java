package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class YAxisRenderer extends AxisRenderer {
    public float[] mGetTransformedPositionsBuffer;
    public final RectF mGridClippingRect;
    public final RectF mLimitLineClippingRect;
    public final Path mRenderGridLinesPath;
    public final Path mRenderLimitLines;
    public final float[] mRenderLimitLinesBuffer;
    public final YAxis mYAxis;

    public YAxisRenderer(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer transformer) {
        super(viewPortHandler, transformer, yAxis);
        this.mRenderGridLinesPath = new Path();
        this.mGridClippingRect = new RectF();
        this.mGetTransformedPositionsBuffer = new float[2];
        new Path();
        new RectF();
        this.mRenderLimitLines = new Path();
        this.mRenderLimitLinesBuffer = new float[2];
        this.mLimitLineClippingRect = new RectF();
        this.mYAxis = yAxis;
        if (viewPortHandler != null) {
            this.mAxisLabelPaint.setColor(EmergencyPhoneWidget.BG_COLOR);
            this.mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(10.0f));
            Paint paint = new Paint(1);
            paint.setColor(-7829368);
            paint.setStrokeWidth(1.0f);
            paint.setStyle(Paint.Style.STROKE);
        }
    }

    public void drawYLabels(Canvas canvas, float f, float[] fArr, float f2) {
        YAxis yAxis = this.mYAxis;
        int i = yAxis.mDrawTopYLabelEntry ? yAxis.mEntryCount : yAxis.mEntryCount - 1;
        for (int i2 = !yAxis.mDrawBottomYLabelEntry ? 1 : 0; i2 < i; i2++) {
            canvas.drawText(
                    yAxis.getFormattedLabel(i2),
                    0.0f + f,
                    fArr[(i2 * 2) + 1] + f2,
                    this.mAxisLabelPaint);
        }
    }

    public final float[] getTransformedPositions() {
        int length = this.mGetTransformedPositionsBuffer.length;
        YAxis yAxis = this.mYAxis;
        int i = yAxis.mEntryCount;
        if (length != i * 2) {
            this.mGetTransformedPositionsBuffer = new float[i * 2];
        }
        float[] fArr = this.mGetTransformedPositionsBuffer;
        for (int i2 = 0; i2 < fArr.length; i2 += 2) {
            fArr[i2 + 1] = yAxis.mEntries[i2 / 2];
        }
        this.mTrans.pointValuesToPixel(fArr);
        return fArr;
    }

    public final void renderAxisLabels(Canvas canvas) {
        float f;
        float f2;
        float f3;
        YAxis yAxis = this.mYAxis;
        if (yAxis.mEnabled && yAxis.mDrawLabels) {
            float[] transformedPositions = getTransformedPositions();
            this.mAxisLabelPaint.setTypeface(yAxis.mTypeface);
            this.mAxisLabelPaint.setTextSize(yAxis.mTextSize);
            this.mAxisLabelPaint.setColor(yAxis.mTextColor);
            float f4 = yAxis.mXOffset;
            float calcTextHeight =
                    (Utils.calcTextHeight(this.mAxisLabelPaint, ImsProfile.TIMER_NAME_A) / 2.5f)
                            + yAxis.mYOffset;
            YAxis.AxisDependency axisDependency = yAxis.mAxisDependency;
            YAxis.YAxisLabelPosition yAxisLabelPosition = yAxis.mPosition;
            YAxis.AxisDependency axisDependency2 = YAxis.AxisDependency.LEFT;
            YAxis.YAxisLabelPosition yAxisLabelPosition2 = YAxis.YAxisLabelPosition.OUTSIDE_CHART;
            ViewPortHandler viewPortHandler = this.mViewPortHandler;
            if (axisDependency == axisDependency2) {
                if (yAxisLabelPosition == yAxisLabelPosition2) {
                    this.mAxisLabelPaint.setTextAlign(Paint.Align.RIGHT);
                    f = viewPortHandler.mContentRect.left;
                    f3 = f - f4;
                } else {
                    this.mAxisLabelPaint.setTextAlign(Paint.Align.LEFT);
                    f2 = viewPortHandler.mContentRect.left;
                    f3 = f2 + f4;
                }
            } else if (yAxisLabelPosition == yAxisLabelPosition2) {
                this.mAxisLabelPaint.setTextAlign(Paint.Align.LEFT);
                f2 = viewPortHandler.mContentRect.right;
                f3 = f2 + f4;
            } else {
                this.mAxisLabelPaint.setTextAlign(Paint.Align.RIGHT);
                f = viewPortHandler.mContentRect.right;
                f3 = f - f4;
            }
            drawYLabels(canvas, f3, transformedPositions, calcTextHeight);
        }
    }

    public final void renderAxisLine(Canvas canvas) {
        YAxis yAxis = this.mYAxis;
        if (yAxis.mEnabled && yAxis.mDrawAxisLine) {
            this.mAxisLinePaint.setColor(yAxis.mAxisLineColor);
            this.mAxisLinePaint.setStrokeWidth(yAxis.mAxisLineWidth);
            YAxis.AxisDependency axisDependency = yAxis.mAxisDependency;
            YAxis.AxisDependency axisDependency2 = YAxis.AxisDependency.LEFT;
            ViewPortHandler viewPortHandler = this.mViewPortHandler;
            if (axisDependency == axisDependency2) {
                RectF rectF = viewPortHandler.mContentRect;
                float f = rectF.left;
                canvas.drawLine(f, rectF.top, f, rectF.bottom, this.mAxisLinePaint);
            } else {
                RectF rectF2 = viewPortHandler.mContentRect;
                float f2 = rectF2.right;
                canvas.drawLine(f2, rectF2.top, f2, rectF2.bottom, this.mAxisLinePaint);
            }
        }
    }

    public final void renderGridLines(Canvas canvas) {
        YAxis yAxis = this.mYAxis;
        if (yAxis.mEnabled && yAxis.mDrawGridLines) {
            int save = canvas.save();
            RectF rectF = this.mGridClippingRect;
            ViewPortHandler viewPortHandler = this.mViewPortHandler;
            rectF.set(viewPortHandler.mContentRect);
            this.mGridClippingRect.inset(0.0f, -this.mAxis.mGridLineWidth);
            canvas.clipRect(this.mGridClippingRect);
            float[] transformedPositions = getTransformedPositions();
            this.mGridPaint.setColor(yAxis.mGridColor);
            this.mGridPaint.setStrokeWidth(yAxis.mGridLineWidth);
            this.mGridPaint.setPathEffect(null);
            Path path = this.mRenderGridLinesPath;
            path.reset();
            for (int i = 0; i < transformedPositions.length; i += 2) {
                int i2 = i + 1;
                path.moveTo(viewPortHandler.mContentRect.left, transformedPositions[i2]);
                path.lineTo(viewPortHandler.mContentRect.right, transformedPositions[i2]);
                canvas.drawPath(path, this.mGridPaint);
                path.reset();
            }
            canvas.restoreToCount(save);
        }
    }

    public void renderLimitLines(Canvas canvas) {
        List list = this.mYAxis.mLimitLines;
        if (list != null) {
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.size() <= 0) {
                return;
            }
            float[] fArr = this.mRenderLimitLinesBuffer;
            fArr[0] = 0.0f;
            fArr[1] = 0.0f;
            Path path = this.mRenderLimitLines;
            path.reset();
            for (int i = 0; i < arrayList.size(); i++) {
                LimitLine limitLine = (LimitLine) arrayList.get(i);
                if (limitLine.mEnabled) {
                    int save = canvas.save();
                    RectF rectF = this.mLimitLineClippingRect;
                    ViewPortHandler viewPortHandler = this.mViewPortHandler;
                    rectF.set(viewPortHandler.mContentRect);
                    this.mLimitLineClippingRect.inset(0.0f, -limitLine.mLineWidth);
                    canvas.clipRect(this.mLimitLineClippingRect);
                    this.mLimitLinePaint.setStyle(Paint.Style.STROKE);
                    this.mLimitLinePaint.setColor(limitLine.mLineColor);
                    this.mLimitLinePaint.setStrokeWidth(limitLine.mLineWidth);
                    this.mLimitLinePaint.setPathEffect(limitLine.mDashPathEffect);
                    fArr[1] = limitLine.mLimit;
                    this.mTrans.pointValuesToPixel(fArr);
                    path.moveTo(viewPortHandler.mContentRect.left, fArr[1]);
                    path.lineTo(viewPortHandler.mContentRect.right, fArr[1]);
                    canvas.drawPath(path, this.mLimitLinePaint);
                    path.reset();
                    String str = limitLine.mLabel;
                    if (str != null && !str.equals(ApnSettings.MVNO_NONE)) {
                        this.mLimitLinePaint.setStyle(limitLine.mTextStyle);
                        this.mLimitLinePaint.setPathEffect(null);
                        this.mLimitLinePaint.setColor(limitLine.mTextColor);
                        this.mLimitLinePaint.setTypeface(limitLine.mTypeface);
                        this.mLimitLinePaint.setStrokeWidth(0.5f);
                        this.mLimitLinePaint.setTextSize(limitLine.mTextSize);
                        float calcTextHeight = Utils.calcTextHeight(this.mLimitLinePaint, str);
                        float convertDpToPixel = Utils.convertDpToPixel(4.0f) + limitLine.mXOffset;
                        float f = limitLine.mLineWidth + calcTextHeight + limitLine.mYOffset;
                        LimitLine.LimitLabelPosition limitLabelPosition = limitLine.mLabelPosition;
                        if (limitLabelPosition == LimitLine.LimitLabelPosition.RIGHT_TOP) {
                            this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                            canvas.drawText(
                                    str,
                                    viewPortHandler.mContentRect.right - convertDpToPixel,
                                    (fArr[1] - f) + calcTextHeight,
                                    this.mLimitLinePaint);
                        } else if (limitLabelPosition
                                == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
                            this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                            canvas.drawText(
                                    str,
                                    viewPortHandler.mContentRect.right - convertDpToPixel,
                                    fArr[1] + f,
                                    this.mLimitLinePaint);
                        } else if (limitLabelPosition == LimitLine.LimitLabelPosition.LEFT_TOP) {
                            this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                            canvas.drawText(
                                    str,
                                    viewPortHandler.mContentRect.left + convertDpToPixel,
                                    (fArr[1] - f) + calcTextHeight,
                                    this.mLimitLinePaint);
                        } else {
                            this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                            canvas.drawText(
                                    str,
                                    viewPortHandler.mContentRect.left + convertDpToPixel,
                                    fArr[1] + f,
                                    this.mLimitLinePaint);
                        }
                    }
                    canvas.restoreToCount(save);
                }
            }
        }
    }
}
