package com.github.mikephil.charting.utils;

import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;

import com.github.mikephil.charting.formatter.DefaultValueFormatter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Utils {
    public static final Rect mCalcTextHeightRect;
    public static final Rect mCalcTextSizeRect;
    public static final DefaultValueFormatter mDefaultValueFormatter;
    public static final Rect mDrawTextRectBuffer;
    public static final Rect mDrawableBoundsCache;
    public static final Paint.FontMetrics mFontMetrics;
    public static final Paint.FontMetrics mFontMetricsBuffer;
    public static int mMaximumFlingVelocity = 8000;
    public static DisplayMetrics mMetrics = null;
    public static int mMinimumFlingVelocity = 50;

    static {
        Double.longBitsToDouble(1L);
        Float.intBitsToFloat(1);
        mCalcTextHeightRect = new Rect();
        mFontMetrics = new Paint.FontMetrics();
        mCalcTextSizeRect = new Rect();
        mDefaultValueFormatter = new DefaultValueFormatter(1);
        mDrawableBoundsCache = new Rect();
        mDrawTextRectBuffer = new Rect();
        mFontMetricsBuffer = new Paint.FontMetrics();
    }

    public static int calcTextHeight(Paint paint, String str) {
        Rect rect = mCalcTextHeightRect;
        rect.set(0, 0, 0, 0);
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();
    }

    public static FSize calcTextSize(Paint paint, String str) {
        FSize fSize = (FSize) FSize.pool.get();
        fSize.width = 0.0f;
        fSize.height = 0.0f;
        Rect rect = mCalcTextSizeRect;
        rect.set(0, 0, 0, 0);
        paint.getTextBounds(str, 0, str.length(), rect);
        fSize.width = rect.width();
        fSize.height = rect.height();
        return fSize;
    }

    public static float convertDpToPixel(float f) {
        DisplayMetrics displayMetrics = mMetrics;
        if (displayMetrics != null) {
            return f * displayMetrics.density;
        }
        Log.e(
                "MPChartLib-Utils",
                "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before"
                    + " calling Utils.convertDpToPixel(...). Otherwise conversion does not take"
                    + " place.");
        return f;
    }

    public static float roundToNextSignificant(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d) || d == 0.0d) {
            return 0.0f;
        }
        return Math.round(d * r0)
                / ((float)
                        Math.pow(
                                10.0d,
                                1 - ((int) Math.ceil((float) Math.log10(d < 0.0d ? -d : d)))));
    }
}
