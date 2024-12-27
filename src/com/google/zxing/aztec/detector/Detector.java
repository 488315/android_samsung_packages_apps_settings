package com.google.zxing.aztec.detector;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;

import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.detector.MathUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Detector {
    public static final int[] EXPECTED_CORNER_BITS = {3808, 476, 2107, 1799};
    public boolean compact;
    public final BitMatrix image;
    public int nbCenterLayers;
    public int nbDataBlocks;
    public int nbLayers;
    public int shift;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Point {
        public final int x;
        public final int y;

        public Point(int i, int i2) {
            this.x = i;
            this.y = i2;
        }

        public final ResultPoint toResultPoint() {
            return new ResultPoint(this.x, this.y);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("<");
            sb.append(this.x);
            sb.append(' ');
            return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.y, '>');
        }
    }

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    public static ResultPoint[] expandSquare(ResultPoint[] resultPointArr, int i, int i2) {
        float f = i2 / (i * 2.0f);
        ResultPoint resultPoint = resultPointArr[0];
        float f2 = resultPoint.x;
        ResultPoint resultPoint2 = resultPointArr[2];
        float f3 = resultPoint2.x;
        float f4 = f2 - f3;
        float f5 = resultPoint.y;
        float f6 = resultPoint2.y;
        float f7 = f5 - f6;
        float f8 = (f2 + f3) / 2.0f;
        float f9 = (f5 + f6) / 2.0f;
        float f10 = f4 * f;
        float f11 = f7 * f;
        ResultPoint resultPoint3 = new ResultPoint(f8 + f10, f9 + f11);
        ResultPoint resultPoint4 = new ResultPoint(f8 - f10, f9 - f11);
        ResultPoint resultPoint5 = resultPointArr[1];
        float f12 = resultPoint5.x;
        ResultPoint resultPoint6 = resultPointArr[3];
        float f13 = resultPoint6.x;
        float f14 = f12 - f13;
        float f15 = resultPoint5.y;
        float f16 = resultPoint6.y;
        float f17 = f15 - f16;
        float f18 = (f12 + f13) / 2.0f;
        float f19 = (f15 + f16) / 2.0f;
        float f20 = f14 * f;
        float f21 = f * f17;
        return new ResultPoint[] {
            resultPoint3,
            new ResultPoint(f18 + f20, f19 + f21),
            resultPoint4,
            new ResultPoint(f18 - f20, f19 - f21)
        };
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x01dc, code lost:

       r24 = r11;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.aztec.AztecDetectorResult detect(boolean r38) {
        /*
            Method dump skipped, instructions count: 991
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.aztec.detector.Detector.detect(boolean):com.google.zxing.aztec.AztecDetectorResult");
    }

    public final int getColor(Point point, Point point2) {
        int i = point.x;
        int i2 = point.y;
        float distance = MathUtils.distance(i, i2, point2.x, point2.y);
        if (distance == 0.0f) {
            return 0;
        }
        float f = (r1 - i) / distance;
        float f2 = (r12 - i2) / distance;
        float f3 = i;
        float f4 = i2;
        BitMatrix bitMatrix = this.image;
        boolean z = bitMatrix.get(i, i2);
        int floor = (int) Math.floor(distance);
        int i3 = 0;
        for (int i4 = 0; i4 < floor; i4++) {
            if (bitMatrix.get(MathUtils.round(f3), MathUtils.round(f4)) != z) {
                i3++;
            }
            f3 += f;
            f4 += f2;
        }
        float f5 = i3 / distance;
        if (f5 <= 0.1f || f5 >= 0.9f) {
            return (f5 <= 0.1f) == z ? 1 : -1;
        }
        return 0;
    }

    public final int getDimension() {
        if (this.compact) {
            return (this.nbLayers * 4) + 11;
        }
        int i = this.nbLayers;
        return ((((i * 2) + 6) / 15) * 2) + (i * 4) + 15;
    }

    public final Point getFirstDifferent(Point point, boolean z, int i, int i2) {
        BitMatrix bitMatrix;
        int i3 = point.x + i;
        int i4 = point.y;
        while (true) {
            i4 += i2;
            boolean isValid = isValid(i3, i4);
            bitMatrix = this.image;
            if (!isValid || bitMatrix.get(i3, i4) != z) {
                break;
            }
            i3 += i;
        }
        int i5 = i3 - i;
        int i6 = i4 - i2;
        while (isValid(i5, i6) && bitMatrix.get(i5, i6) == z) {
            i5 += i;
        }
        int i7 = i5 - i;
        while (isValid(i7, i6) && bitMatrix.get(i7, i6) == z) {
            i6 += i2;
        }
        return new Point(i7, i6 - i2);
    }

    public final boolean isValid(ResultPoint resultPoint) {
        return isValid(MathUtils.round(resultPoint.x), MathUtils.round(resultPoint.y));
    }

    public final int sampleLine(ResultPoint resultPoint, ResultPoint resultPoint2, int i) {
        float f = resultPoint.x;
        float f2 = resultPoint2.x;
        float f3 = resultPoint.y;
        float f4 = resultPoint2.y;
        float distance = MathUtils.distance(f, f3, f2, f4);
        float f5 = distance / i;
        float f6 = resultPoint2.x;
        float f7 = resultPoint.x;
        float f8 = ((f6 - f7) * f5) / distance;
        float f9 = ((f4 - f3) * f5) / distance;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            float f10 = i3;
            if (this.image.get(
                    MathUtils.round((f10 * f8) + f7), MathUtils.round((f10 * f9) + f3))) {
                i2 |= 1 << ((i - i3) - 1);
            }
        }
        return i2;
    }

    public final boolean isValid(int i, int i2) {
        if (i >= 0) {
            BitMatrix bitMatrix = this.image;
            if (i < bitMatrix.width && i2 >= 0 && i2 < bitMatrix.height) {
                return true;
            }
        }
        return false;
    }
}
