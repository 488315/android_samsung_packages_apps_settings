package com.google.zxing.qrcode.detector;

import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AlignmentPatternFinder {
    public final int height;
    public final BitMatrix image;
    public final float moduleSize;
    public final int startX;
    public final int startY;
    public final int width;
    public final List possibleCenters = new ArrayList(5);
    public final int[] crossCheckStateCount = new int[3];

    public AlignmentPatternFinder(BitMatrix bitMatrix, int i, int i2, int i3, int i4, float f) {
        this.image = bitMatrix;
        this.startX = i;
        this.startY = i2;
        this.width = i3;
        this.height = i4;
        this.moduleSize = f;
    }

    public final boolean foundPatternCross(int[] iArr) {
        float f = this.moduleSize;
        float f2 = f / 2.0f;
        for (int i = 0; i < 3; i++) {
            if (Math.abs(f - iArr[i]) >= f2) {
                return false;
            }
        }
        return true;
    }

    public final AlignmentPattern handlePossibleCenter(int i, int i2, int[] iArr) {
        int i3 = iArr[0];
        int i4 = iArr[1];
        int i5 = i3 + i4 + iArr[2];
        float f = (i2 - r6) - (i4 / 2.0f);
        int i6 = (int) f;
        int i7 = i4 * 2;
        BitMatrix bitMatrix = this.image;
        int i8 = bitMatrix.height;
        int[] iArr2 = this.crossCheckStateCount;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        int i9 = i;
        while (i9 >= 0 && bitMatrix.get(i6, i9)) {
            int i10 = iArr2[1];
            if (i10 > i7) {
                break;
            }
            iArr2[1] = i10 + 1;
            i9--;
        }
        float f2 = Float.NaN;
        if (i9 >= 0 && iArr2[1] <= i7) {
            while (i9 >= 0 && !bitMatrix.get(i6, i9)) {
                int i11 = iArr2[0];
                if (i11 > i7) {
                    break;
                }
                iArr2[0] = i11 + 1;
                i9--;
            }
            if (iArr2[0] <= i7) {
                int i12 = i + 1;
                while (i12 < i8 && bitMatrix.get(i6, i12)) {
                    int i13 = iArr2[1];
                    if (i13 > i7) {
                        break;
                    }
                    iArr2[1] = i13 + 1;
                    i12++;
                }
                if (i12 != i8 && iArr2[1] <= i7) {
                    while (i12 < i8 && !bitMatrix.get(i6, i12)) {
                        int i14 = iArr2[2];
                        if (i14 > i7) {
                            break;
                        }
                        iArr2[2] = i14 + 1;
                        i12++;
                    }
                    int i15 = iArr2[2];
                    if (i15 <= i7
                            && Math.abs(((iArr2[0] + iArr2[1]) + i15) - i5) * 5 < i5 * 2
                            && foundPatternCross(iArr2)) {
                        f2 = (i12 - iArr2[2]) - (iArr2[1] / 2.0f);
                    }
                }
            }
        }
        if (Float.isNaN(f2)) {
            return null;
        }
        float f3 = ((iArr[0] + iArr[1]) + iArr[2]) / 3.0f;
        Iterator it = ((ArrayList) this.possibleCenters).iterator();
        while (it.hasNext()) {
            AlignmentPattern alignmentPattern = (AlignmentPattern) it.next();
            if (Math.abs(f2 - alignmentPattern.y) <= f3) {
                float f4 = alignmentPattern.x;
                if (Math.abs(f - f4) <= f3) {
                    float f5 = alignmentPattern.estimatedModuleSize;
                    float abs = Math.abs(f3 - f5);
                    if (abs <= 1.0f || abs <= f5) {
                        return new AlignmentPattern(
                                (f4 + f) / 2.0f,
                                (alignmentPattern.y + f2) / 2.0f,
                                (f5 + f3) / 2.0f);
                    }
                } else {
                    continue;
                }
            }
        }
        ((ArrayList) this.possibleCenters).add(new AlignmentPattern(f, f2, f3));
        return null;
    }
}
