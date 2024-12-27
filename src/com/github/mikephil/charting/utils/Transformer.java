package com.github.mikephil.charting.utils;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Transformer {
    public final ViewPortHandler mViewPortHandler;
    public final Matrix mMatrixValueToPx = new Matrix();
    public final Matrix mMatrixOffset = new Matrix();
    public float[] valuePointsForGenerateTransformedValuesLine = new float[1];
    public final Matrix mPixelToValueMatrixBuffer = new Matrix();
    public final float[] ptsBuffer = new float[2];
    public final Matrix mMBuffer1 = new Matrix();

    public Transformer(ViewPortHandler viewPortHandler) {
        new Matrix();
        this.mViewPortHandler = viewPortHandler;
    }

    public final MPPointD getPixelForValues(float f, float f2) {
        float[] fArr = this.ptsBuffer;
        fArr[0] = f;
        fArr[1] = f2;
        pointValuesToPixel(fArr);
        return MPPointD.getInstance(fArr[0], fArr[1]);
    }

    public final void getValuesByTouchPoint(float f, float f2, MPPointD mPPointD) {
        float[] fArr = this.ptsBuffer;
        fArr[0] = f;
        fArr[1] = f2;
        Matrix matrix = this.mPixelToValueMatrixBuffer;
        matrix.reset();
        this.mMatrixOffset.invert(matrix);
        matrix.mapPoints(fArr);
        this.mViewPortHandler.mMatrixTouch.invert(matrix);
        matrix.mapPoints(fArr);
        this.mMatrixValueToPx.invert(matrix);
        matrix.mapPoints(fArr);
        mPPointD.x = fArr[0];
        mPPointD.y = fArr[1];
    }

    public final void pathValueToPixel(Path path) {
        path.transform(this.mMatrixValueToPx);
        path.transform(this.mViewPortHandler.mMatrixTouch);
        path.transform(this.mMatrixOffset);
    }

    public final void pointValuesToPixel(float[] fArr) {
        this.mMatrixValueToPx.mapPoints(fArr);
        this.mViewPortHandler.mMatrixTouch.mapPoints(fArr);
        this.mMatrixOffset.mapPoints(fArr);
    }

    public final void prepareMatrixOffset() {
        this.mMatrixOffset.reset();
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        Matrix matrix = this.mMatrixOffset;
        RectF rectF = viewPortHandler.mContentRect;
        float f = rectF.left;
        float f2 = viewPortHandler.mChartHeight;
        matrix.postTranslate(f, f2 - (f2 - rectF.bottom));
    }

    public final void prepareMatrixValuePx(float f, float f2, float f3, float f4) {
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        float width = viewPortHandler.mContentRect.width() / f2;
        float height = viewPortHandler.mContentRect.height() / f3;
        if (Float.isInfinite(width)) {
            width = 0.0f;
        }
        if (Float.isInfinite(height)) {
            height = 0.0f;
        }
        this.mMatrixValueToPx.reset();
        this.mMatrixValueToPx.postTranslate(-f, -f4);
        this.mMatrixValueToPx.postScale(width, -height);
    }
}
