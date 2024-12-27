package com.github.mikephil.charting.renderer;

import android.graphics.Paint;
import android.graphics.RectF;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AxisRenderer extends Renderer {
    public final AxisBase mAxis;
    public final Paint mAxisLabelPaint;
    public final Paint mAxisLinePaint;
    public final Paint mGridPaint;
    public final Paint mLimitLinePaint;
    public final Transformer mTrans;

    public AxisRenderer(
            ViewPortHandler viewPortHandler, Transformer transformer, AxisBase axisBase) {
        super(viewPortHandler);
        this.mTrans = transformer;
        this.mAxis = axisBase;
        if (viewPortHandler != null) {
            this.mAxisLabelPaint = new Paint(1);
            Paint paint = new Paint();
            this.mGridPaint = paint;
            paint.setColor(-7829368);
            paint.setStrokeWidth(1.0f);
            Paint.Style style = Paint.Style.STROKE;
            paint.setStyle(style);
            paint.setAlpha(90);
            Paint paint2 = new Paint();
            this.mAxisLinePaint = paint2;
            paint2.setColor(EmergencyPhoneWidget.BG_COLOR);
            paint2.setStrokeWidth(1.0f);
            paint2.setStyle(style);
            Paint paint3 = new Paint(1);
            this.mLimitLinePaint = paint3;
            paint3.setStyle(style);
        }
    }

    public void computeAxis(float f, float f2) {
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        if (viewPortHandler != null && viewPortHandler.mContentRect.width() > 10.0f) {
            float f3 = viewPortHandler.mScaleY;
            float f4 = viewPortHandler.mMinScaleY;
            if (f3 > f4 || f4 > 1.0f) {
                RectF rectF = viewPortHandler.mContentRect;
                float f5 = rectF.left;
                float f6 = rectF.top;
                Transformer transformer = this.mTrans;
                transformer.getClass();
                MPPointD mPPointD = MPPointD.getInstance(0.0d, 0.0d);
                transformer.getValuesByTouchPoint(f5, f6, mPPointD);
                RectF rectF2 = viewPortHandler.mContentRect;
                float f7 = rectF2.left;
                float f8 = rectF2.bottom;
                MPPointD mPPointD2 = MPPointD.getInstance(0.0d, 0.0d);
                transformer.getValuesByTouchPoint(f7, f8, mPPointD2);
                f = (float) mPPointD2.y;
                f2 = (float) mPPointD.y;
                MPPointD.recycleInstance(mPPointD);
                MPPointD.recycleInstance(mPPointD2);
            }
        }
        computeAxisValues(f, f2);
    }

    public void computeAxisValues(float f, float f2) {
        double floor;
        int i;
        float f3 = f;
        AxisBase axisBase = this.mAxis;
        int i2 = axisBase.mLabelCount;
        double abs = Math.abs(f2 - f3);
        if (i2 == 0 || abs <= 0.0d || Double.isInfinite(abs)) {
            axisBase.mEntries = new float[0];
            axisBase.mEntryCount = 0;
            return;
        }
        double roundToNextSignificant = Utils.roundToNextSignificant(abs / i2);
        if (axisBase.mGranularityEnabled) {
            double d = axisBase.mGranularity;
            if (roundToNextSignificant < d) {
                roundToNextSignificant = d;
            }
        }
        double roundToNextSignificant2 =
                Utils.roundToNextSignificant(
                        Math.pow(10.0d, (int) Math.log10(roundToNextSignificant)));
        if (((int) (roundToNextSignificant / roundToNextSignificant2)) > 5) {
            double d2 = roundToNextSignificant2 * 10.0d;
            if (Math.floor(d2) != 0.0d) {
                roundToNextSignificant = Math.floor(d2);
            }
        }
        if (axisBase.mForceLabels) {
            roundToNextSignificant = ((float) abs) / (i2 - 1);
            axisBase.mEntryCount = i2;
            if (axisBase.mEntries.length < i2) {
                axisBase.mEntries = new float[i2];
            }
            for (int i3 = 0; i3 < i2; i3++) {
                axisBase.mEntries[i3] = f3;
                f3 = (float) (f3 + roundToNextSignificant);
            }
        } else {
            double ceil =
                    roundToNextSignificant == 0.0d
                            ? 0.0d
                            : Math.ceil(f3 / roundToNextSignificant) * roundToNextSignificant;
            if (roundToNextSignificant == 0.0d) {
                floor = 0.0d;
            } else {
                floor = Math.floor(f2 / roundToNextSignificant) * roundToNextSignificant;
                if (floor != Double.POSITIVE_INFINITY) {
                    double d3 = floor + 0.0d;
                    floor =
                            Double.longBitsToDouble(
                                    Double.doubleToRawLongBits(d3) + (d3 >= 0.0d ? 1L : -1L));
                }
            }
            if (roundToNextSignificant == 0.0d || floor == ceil) {
                i = floor == ceil ? 1 : 0;
            } else {
                i = 0;
                for (double d4 = ceil; d4 <= floor; d4 += roundToNextSignificant) {
                    i++;
                }
            }
            axisBase.mEntryCount = i;
            if (axisBase.mEntries.length < i) {
                axisBase.mEntries = new float[i];
            }
            for (int i4 = 0; i4 < i; i4++) {
                if (ceil == 0.0d) {
                    ceil = 0.0d;
                }
                axisBase.mEntries[i4] = (float) ceil;
                ceil += roundToNextSignificant;
            }
        }
        if (roundToNextSignificant < 1.0d) {
            axisBase.mDecimals = (int) Math.ceil(-Math.log10(roundToNextSignificant));
        } else {
            axisBase.mDecimals = 0;
        }
    }
}
