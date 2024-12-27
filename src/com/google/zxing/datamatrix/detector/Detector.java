package com.google.zxing.datamatrix.detector;

import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.detector.WhiteRectangleDetector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Detector {
    public final BitMatrix image;
    public final WhiteRectangleDetector rectangleDetector;

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
        this.rectangleDetector = new WhiteRectangleDetector(bitMatrix);
    }

    public static ResultPoint moveAway(ResultPoint resultPoint, float f, float f2) {
        float f3 = resultPoint.x;
        float f4 = f3 < f ? f3 - 1.0f : f3 + 1.0f;
        float f5 = resultPoint.y;
        return new ResultPoint(f4, f5 < f2 ? f5 - 1.0f : f5 + 1.0f);
    }

    public static ResultPoint shiftPoint(ResultPoint resultPoint, ResultPoint resultPoint2, int i) {
        float f = resultPoint2.x;
        float f2 = resultPoint.x;
        float f3 = i + 1;
        float f4 = resultPoint2.y;
        float f5 = resultPoint.y;
        return new ResultPoint(f2 + ((f - f2) / f3), f5 + ((f4 - f5) / f3));
    }

    public final boolean isValid(ResultPoint resultPoint) {
        float f = resultPoint.x;
        if (f >= 0.0f) {
            BitMatrix bitMatrix = this.image;
            if (f <= bitMatrix.width - 1) {
                float f2 = resultPoint.y;
                if (f2 > 0.0f && f2 <= bitMatrix.height - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int transitionsBetween(ResultPoint resultPoint, ResultPoint resultPoint2) {
        int i = (int) resultPoint.x;
        int i2 = (int) resultPoint.y;
        int i3 = (int) resultPoint2.x;
        BitMatrix bitMatrix = this.image;
        int min = Math.min(bitMatrix.height - 1, (int) resultPoint2.y);
        int i4 = 0;
        boolean z = Math.abs(min - i2) > Math.abs(i3 - i);
        if (z) {
            i = i2;
            i2 = i;
            i3 = min;
            min = i3;
        }
        int abs = Math.abs(i3 - i);
        int abs2 = Math.abs(min - i2);
        int i5 = (-abs) / 2;
        int i6 = i2 < min ? 1 : -1;
        int i7 = i >= i3 ? -1 : 1;
        boolean z2 = bitMatrix.get(z ? i2 : i, z ? i : i2);
        while (i != i3) {
            boolean z3 = bitMatrix.get(z ? i2 : i, z ? i : i2);
            if (z3 != z2) {
                i4++;
                z2 = z3;
            }
            i5 += abs2;
            if (i5 > 0) {
                if (i2 == min) {
                    break;
                }
                i2 += i6;
                i5 -= abs;
            }
            i += i7;
        }
        return i4;
    }
}
