package com.google.zxing.qrcode.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.detector.MathUtils;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Detector {
    public final BitMatrix image;

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    public final float calculateModuleSizeOneWay(
            ResultPoint resultPoint, ResultPoint resultPoint2) {
        int i = (int) resultPoint.x;
        float f = resultPoint.y;
        int i2 = (int) resultPoint2.x;
        float f2 = resultPoint2.y;
        float sizeOfBlackWhiteBlackRunBothWays =
                sizeOfBlackWhiteBlackRunBothWays(i, (int) f, i2, (int) f2);
        float sizeOfBlackWhiteBlackRunBothWays2 =
                sizeOfBlackWhiteBlackRunBothWays(
                        (int) resultPoint2.x, (int) f2, (int) resultPoint.x, (int) f);
        return Float.isNaN(sizeOfBlackWhiteBlackRunBothWays)
                ? sizeOfBlackWhiteBlackRunBothWays2 / 7.0f
                : Float.isNaN(sizeOfBlackWhiteBlackRunBothWays2)
                        ? sizeOfBlackWhiteBlackRunBothWays / 7.0f
                        : (sizeOfBlackWhiteBlackRunBothWays + sizeOfBlackWhiteBlackRunBothWays2)
                                / 14.0f;
    }

    public final AlignmentPattern findAlignmentInRegion(int i, float f, float f2, int i2) {
        BitMatrix bitMatrix;
        AlignmentPattern handlePossibleCenter;
        AlignmentPattern handlePossibleCenter2;
        int i3 = (int) (f2 * f);
        int max = Math.max(0, i - i3);
        BitMatrix bitMatrix2 = this.image;
        int min = Math.min(bitMatrix2.width - 1, i + i3) - max;
        float f3 = 3.0f * f;
        if (min < f3) {
            throw NotFoundException.getNotFoundInstance();
        }
        int max2 = Math.max(0, i2 - i3);
        int min2 = Math.min(bitMatrix2.height - 1, i2 + i3) - max2;
        if (min2 < f3) {
            throw NotFoundException.getNotFoundInstance();
        }
        AlignmentPatternFinder alignmentPatternFinder =
                new AlignmentPatternFinder(this.image, max, max2, min, min2, f);
        int i4 = alignmentPatternFinder.width;
        int i5 = alignmentPatternFinder.startX;
        int i6 = i4 + i5;
        int i7 = alignmentPatternFinder.height;
        int i8 = (i7 / 2) + alignmentPatternFinder.startY;
        int[] iArr = new int[3];
        for (int i9 = 0; i9 < i7; i9++) {
            int i10 = ((i9 & 1) == 0 ? (i9 + 1) / 2 : -((i9 + 1) / 2)) + i8;
            iArr[0] = 0;
            iArr[1] = 0;
            iArr[2] = 0;
            int i11 = i5;
            while (true) {
                bitMatrix = alignmentPatternFinder.image;
                if (i11 >= i6 || bitMatrix.get(i11, i10)) {
                    break;
                }
                i11++;
            }
            int i12 = 0;
            while (i11 < i6) {
                if (!bitMatrix.get(i11, i10)) {
                    if (i12 == 1) {
                        i12++;
                    }
                    iArr[i12] = iArr[i12] + 1;
                } else if (i12 == 1) {
                    iArr[1] = iArr[1] + 1;
                } else if (i12 != 2) {
                    i12++;
                    iArr[i12] = iArr[i12] + 1;
                } else {
                    if (alignmentPatternFinder.foundPatternCross(iArr)
                            && (handlePossibleCenter2 =
                                            alignmentPatternFinder.handlePossibleCenter(
                                                    i10, i11, iArr))
                                    != null) {
                        return handlePossibleCenter2;
                    }
                    iArr[0] = iArr[2];
                    iArr[1] = 1;
                    iArr[2] = 0;
                    i12 = 1;
                }
                i11++;
            }
            if (alignmentPatternFinder.foundPatternCross(iArr)
                    && (handlePossibleCenter =
                                    alignmentPatternFinder.handlePossibleCenter(i10, i6, iArr))
                            != null) {
                return handlePossibleCenter;
            }
        }
        if (((ArrayList) alignmentPatternFinder.possibleCenters).isEmpty()) {
            throw NotFoundException.getNotFoundInstance();
        }
        return (AlignmentPattern) ((ArrayList) alignmentPatternFinder.possibleCenters).get(0);
    }

    public final float sizeOfBlackWhiteBlackRun(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        Detector detector;
        boolean z;
        int i11;
        int i12 = 1;
        boolean z2 = Math.abs(i4 - i2) > Math.abs(i3 - i);
        if (z2) {
            i6 = i;
            i5 = i2;
            i8 = i3;
            i7 = i4;
        } else {
            i5 = i;
            i6 = i2;
            i7 = i3;
            i8 = i4;
        }
        int abs = Math.abs(i7 - i5);
        int abs2 = Math.abs(i8 - i6);
        int i13 = 2;
        int i14 = (-abs) / 2;
        int i15 = i5 < i7 ? 1 : -1;
        int i16 = i6 < i8 ? 1 : -1;
        int i17 = i7 + i15;
        int i18 = i5;
        int i19 = i6;
        int i20 = 0;
        while (true) {
            if (i18 == i17) {
                i9 = i17;
                i10 = i13;
                break;
            }
            int i21 = z2 ? i19 : i18;
            int i22 = z2 ? i18 : i19;
            if (i20 == i12) {
                z = z2;
                i11 = i12;
                i9 = i17;
                detector = this;
            } else {
                detector = this;
                z = z2;
                i9 = i17;
                i11 = 0;
            }
            if (i11 == detector.image.get(i21, i22)) {
                if (i20 == 2) {
                    return MathUtils.distance(i18, i19, i5, i6);
                }
                i20++;
            }
            i14 += abs2;
            if (i14 > 0) {
                if (i19 == i8) {
                    i10 = 2;
                    break;
                }
                i19 += i16;
                i14 -= abs;
            }
            i18 += i15;
            i17 = i9;
            z2 = z;
            i12 = 1;
            i13 = 2;
        }
        if (i20 == i10) {
            return MathUtils.distance(i9, i8, i5, i6);
        }
        return Float.NaN;
    }

    public final float sizeOfBlackWhiteBlackRunBothWays(int i, int i2, int i3, int i4) {
        float f;
        float f2;
        float sizeOfBlackWhiteBlackRun = sizeOfBlackWhiteBlackRun(i, i2, i3, i4);
        int i5 = i - (i3 - i);
        BitMatrix bitMatrix = this.image;
        int i6 = 0;
        if (i5 < 0) {
            f = i / (i - i5);
            i5 = 0;
        } else {
            int i7 = bitMatrix.width;
            if (i5 >= i7) {
                float f3 = ((i7 - 1) - i) / (i5 - i);
                int i8 = i7 - 1;
                f = f3;
                i5 = i8;
            } else {
                f = 1.0f;
            }
        }
        float f4 = i2;
        int i9 = (int) (f4 - ((i4 - i2) * f));
        if (i9 < 0) {
            f2 = f4 / (i2 - i9);
        } else {
            int i10 = bitMatrix.height;
            if (i9 >= i10) {
                f2 = ((i10 - 1) - i2) / (i9 - i2);
                i6 = i10 - 1;
            } else {
                i6 = i9;
                f2 = 1.0f;
            }
        }
        return (sizeOfBlackWhiteBlackRun(i, i2, (int) (((i5 - i) * f2) + i), i6)
                        + sizeOfBlackWhiteBlackRun)
                - 1.0f;
    }
}
