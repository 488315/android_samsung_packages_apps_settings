package com.google.zxing.pdf417.detector;

import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Detector {
    public static final int[] INDEXES_START_PATTERN = {0, 4, 1, 5};
    public static final int[] INDEXES_STOP_PATTERN = {6, 2, 7, 3};
    public static final int[] START_PATTERN = {8, 1, 1, 1, 1, 1, 1, 3};
    public static final int[] STOP_PATTERN = {7, 1, 1, 3, 1, 1, 1, 2, 1};
    public static final int[] ROTATIONS = {0, 180, 270, 90};

    public static int[] findGuardPattern(
            BitMatrix bitMatrix, int i, int i2, int i3, int[] iArr, int[] iArr2) {
        Arrays.fill(iArr2, 0, iArr2.length, 0);
        int i4 = 0;
        while (bitMatrix.get(i, i2) && i > 0) {
            int i5 = i4 + 1;
            if (i4 >= 3) {
                break;
            }
            i--;
            i4 = i5;
        }
        int length = iArr.length;
        int i6 = i;
        int i7 = 0;
        boolean z = false;
        while (i < i3) {
            if (bitMatrix.get(i, i2) != z) {
                iArr2[i7] = iArr2[i7] + 1;
            } else {
                if (i7 != length - 1) {
                    i7++;
                } else {
                    if (patternMatchVariance(iArr2, iArr) < 0.42f) {
                        return new int[] {i6, i};
                    }
                    i6 += iArr2[0] + iArr2[1];
                    int i8 = i7 - 1;
                    System.arraycopy(iArr2, 2, iArr2, 0, i8);
                    iArr2[i8] = 0;
                    iArr2[i7] = 0;
                    i7--;
                }
                iArr2[i7] = 1;
                z = !z;
            }
            i++;
        }
        if (i7 != length - 1 || patternMatchVariance(iArr2, iArr) >= 0.42f) {
            return null;
        }
        return new int[] {i6, i - 1};
    }

    public static ResultPoint[] findRowsWithPattern(
            BitMatrix bitMatrix, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        boolean z;
        ResultPoint[] resultPointArr = new ResultPoint[4];
        int[] iArr2 = new int[iArr.length];
        int i6 = i3;
        while (true) {
            if (i6 >= i) {
                z = false;
                break;
            }
            int[] findGuardPattern = findGuardPattern(bitMatrix, i4, i6, i2, iArr, iArr2);
            if (findGuardPattern != null) {
                int i7 = i6;
                int[] iArr3 = findGuardPattern;
                while (i7 > 0) {
                    int i8 = i7 - 1;
                    int[] findGuardPattern2 = findGuardPattern(bitMatrix, i4, i8, i2, iArr, iArr2);
                    if (findGuardPattern2 == null) {
                        break;
                    }
                    iArr3 = findGuardPattern2;
                    i7 = i8;
                }
                float f = i7;
                resultPointArr[0] = new ResultPoint(iArr3[0], f);
                resultPointArr[1] = new ResultPoint(iArr3[1], f);
                z = true;
                i6 = i7;
            } else {
                i6 += 5;
            }
        }
        int i9 = i6 + 1;
        if (z) {
            int[] iArr4 = {(int) resultPointArr[0].x, (int) resultPointArr[1].x};
            int i10 = i9;
            int i11 = 0;
            while (i10 < i) {
                int[] findGuardPattern3 =
                        findGuardPattern(bitMatrix, iArr4[0], i10, i2, iArr, iArr2);
                if (findGuardPattern3 != null
                        && Math.abs(iArr4[0] - findGuardPattern3[0]) < 5
                        && Math.abs(iArr4[1] - findGuardPattern3[1]) < 5) {
                    iArr4 = findGuardPattern3;
                    i11 = 0;
                } else {
                    if (i11 > 25) {
                        break;
                    }
                    i11++;
                }
                i10++;
            }
            i9 = i10 - (i11 + 1);
            float f2 = i9;
            resultPointArr[2] = new ResultPoint(iArr4[0], f2);
            resultPointArr[3] = new ResultPoint(iArr4[1], f2);
        }
        if (i9 - i6 < i5) {
            Arrays.fill(resultPointArr, (Object) null);
        }
        return resultPointArr;
    }

    public static float patternMatchVariance(int[] iArr, int[] iArr2) {
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            i += iArr[i3];
            i2 += iArr2[i3];
        }
        if (i < i2) {
            return Float.POSITIVE_INFINITY;
        }
        float f = i;
        float f2 = f / i2;
        float f3 = 0.8f * f2;
        float f4 = 0.0f;
        for (int i4 = 0; i4 < length; i4++) {
            float f5 = iArr2[i4] * f2;
            float f6 = iArr[i4];
            float f7 = f6 > f5 ? f6 - f5 : f5 - f6;
            if (f7 > f3) {
                return Float.POSITIVE_INFINITY;
            }
            f4 += f7;
        }
        return f4 / f;
    }
}
