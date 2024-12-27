package com.github.mikephil.charting.utils;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ViewPortHandler {
    public final Matrix mMatrixTouch = new Matrix();
    public final RectF mContentRect = new RectF();
    public float mChartWidth = 0.0f;
    public float mChartHeight = 0.0f;
    public float mMinScaleY = 1.0f;
    public float mMaxScaleY = Float.MAX_VALUE;
    public float mMinScaleX = 1.0f;
    public float mMaxScaleX = Float.MAX_VALUE;
    public float mScaleX = 1.0f;
    public float mScaleY = 1.0f;
    public float mTransX = 0.0f;
    public final float[] valsBufferForFitScreen = new float[9];
    public final Matrix mCenterViewPortMatrixBuffer = new Matrix();
    public final float[] matrixBuffer = new float[9];

    public final boolean isInBoundsLeft(float f) {
        return this.mContentRect.left <= f + 1.0f;
    }

    public final boolean isInBoundsRight(float f) {
        return this.mContentRect.right >= (((float) ((int) (f * 100.0f))) / 100.0f) - 1.0f;
    }

    public final boolean isInBoundsX(float f) {
        return isInBoundsLeft(f) && isInBoundsRight(f);
    }

    public final boolean isInBoundsY(float f) {
        RectF rectF = this.mContentRect;
        if (rectF.top <= f) {
            if (rectF.bottom >= ((int) (f * 100.0f)) / 100.0f) {
                return true;
            }
        }
        return false;
    }

    public final void limitTransAndScale(Matrix matrix, RectF rectF) {
        float f;
        float f2;
        float[] fArr = this.matrixBuffer;
        matrix.getValues(fArr);
        float f3 = fArr[2];
        float f4 = fArr[0];
        float f5 = fArr[5];
        float f6 = fArr[4];
        this.mScaleX = Math.min(Math.max(this.mMinScaleX, f4), this.mMaxScaleX);
        this.mScaleY = Math.min(Math.max(this.mMinScaleY, f6), this.mMaxScaleY);
        if (rectF != null) {
            f2 = rectF.width();
            f = rectF.height();
        } else {
            f = 0.0f;
            f2 = 0.0f;
        }
        this.mTransX = Math.min(Math.max(f3, ((this.mScaleX - 1.0f) * (-f2)) - 0.0f), 0.0f);
        float max = Math.max(Math.min(f5, ((this.mScaleY - 1.0f) * f) + 0.0f), -0.0f);
        fArr[2] = this.mTransX;
        fArr[0] = this.mScaleX;
        fArr[5] = max;
        fArr[4] = this.mScaleY;
        matrix.setValues(fArr);
    }

    public final void refresh(Matrix matrix, View view, boolean z) {
        this.mMatrixTouch.set(matrix);
        limitTransAndScale(this.mMatrixTouch, this.mContentRect);
        if (z) {
            view.invalidate();
        }
        matrix.set(this.mMatrixTouch);
    }
}
