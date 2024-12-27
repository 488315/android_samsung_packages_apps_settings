package com.google.zxing;

import androidx.compose.animation.AndroidFlingSpline$$ExternalSyntheticOutline0;

import com.google.zxing.common.detector.MathUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ResultPoint {
    public final float x;
    public final float y;

    public ResultPoint(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public static float distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.distance(resultPoint.x, resultPoint.y, resultPoint2.x, resultPoint2.y);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ResultPoint)) {
            return false;
        }
        ResultPoint resultPoint = (ResultPoint) obj;
        return this.x == resultPoint.x && this.y == resultPoint.y;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.y) + (Float.floatToIntBits(this.x) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(this.x);
        sb.append(',');
        return AndroidFlingSpline$$ExternalSyntheticOutline0.m(sb, this.y, ')');
    }
}
