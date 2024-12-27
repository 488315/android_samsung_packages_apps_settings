package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WhiteRectangleDetector {
    public final int downInit;
    public final int height;
    public final BitMatrix image;
    public final int leftInit;
    public final int rightInit;
    public final int upInit;
    public final int width;

    public WhiteRectangleDetector(BitMatrix bitMatrix, int i, int i2, int i3) {
        this.image = bitMatrix;
        int i4 = bitMatrix.height;
        this.height = i4;
        int i5 = bitMatrix.width;
        this.width = i5;
        int i6 = i / 2;
        int i7 = i2 - i6;
        this.leftInit = i7;
        int i8 = i2 + i6;
        this.rightInit = i8;
        int i9 = i3 - i6;
        this.upInit = i9;
        int i10 = i3 + i6;
        this.downInit = i10;
        if (i9 < 0 || i7 < 0 || i10 >= i4 || i8 >= i5) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    public final boolean containsBlackPoint(int i, int i2, boolean z, int i3) {
        BitMatrix bitMatrix = this.image;
        if (z) {
            while (i <= i2) {
                if (bitMatrix.get(i, i3)) {
                    return true;
                }
                i++;
            }
            return false;
        }
        while (i <= i2) {
            if (bitMatrix.get(i3, i)) {
                return true;
            }
            i++;
        }
        return false;
    }

    public final ResultPoint[] detect() {
        int i;
        int i2;
        int i3 = this.leftInit;
        int i4 = this.rightInit;
        int i5 = this.upInit;
        int i6 = this.downInit;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = true;
        do {
            i = this.width;
            if (!z6) {
                break;
            }
            boolean z7 = false;
            boolean z8 = true;
            while (true) {
                if ((z8 || !z2) && i4 < i) {
                    z8 = containsBlackPoint(i5, i6, false, i4);
                    if (z8) {
                        i4++;
                        z2 = true;
                        z7 = true;
                    } else if (!z2) {
                        i4++;
                    }
                }
            }
            if (i4 >= i) {
                break;
            }
            boolean z9 = true;
            while (true) {
                i2 = this.height;
                if ((z9 || !z3) && i6 < i2) {
                    z9 = containsBlackPoint(i3, i4, true, i6);
                    if (z9) {
                        i6++;
                        z3 = true;
                        z7 = true;
                    } else if (!z3) {
                        i6++;
                    }
                }
            }
            if (i6 >= i2) {
                break;
            }
            boolean z10 = true;
            while (true) {
                if ((z10 || !z4) && i3 >= 0) {
                    z10 = containsBlackPoint(i5, i6, false, i3);
                    if (z10) {
                        i3--;
                        z4 = true;
                        z7 = true;
                    } else if (!z4) {
                        i3--;
                    }
                }
            }
            if (i3 < 0) {
                break;
            }
            z6 = z7;
            boolean z11 = true;
            while (true) {
                if ((z11 || !z5) && i5 >= 0) {
                    z11 = containsBlackPoint(i3, i4, true, i5);
                    if (z11) {
                        i5--;
                        z6 = true;
                        z5 = true;
                    } else if (!z5) {
                        i5--;
                    }
                }
            }
        } while (i5 >= 0);
        z = true;
        if (z) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i7 = i4 - i3;
        ResultPoint resultPoint = null;
        ResultPoint resultPoint2 = null;
        for (int i8 = 1; resultPoint2 == null && i8 < i7; i8++) {
            resultPoint2 = getBlackPointOnSegment(i3, i6 - i8, i3 + i8, i6);
        }
        if (resultPoint2 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        ResultPoint resultPoint3 = null;
        for (int i9 = 1; resultPoint3 == null && i9 < i7; i9++) {
            resultPoint3 = getBlackPointOnSegment(i3, i5 + i9, i3 + i9, i5);
        }
        if (resultPoint3 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        ResultPoint resultPoint4 = null;
        for (int i10 = 1; resultPoint4 == null && i10 < i7; i10++) {
            resultPoint4 = getBlackPointOnSegment(i4, i5 + i10, i4 - i10, i5);
        }
        if (resultPoint4 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        for (int i11 = 1; resultPoint == null && i11 < i7; i11++) {
            resultPoint = getBlackPointOnSegment(i4, i6 - i11, i4 - i11, i6);
        }
        if (resultPoint == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        float f = i / 2.0f;
        float f2 = resultPoint.x;
        float f3 = resultPoint2.x;
        float f4 = resultPoint4.x;
        float f5 = resultPoint3.x;
        float f6 = resultPoint.y;
        float f7 = resultPoint2.y;
        float f8 = resultPoint4.y;
        float f9 = resultPoint3.y;
        return f2 < f
                ? new ResultPoint[] {
                    new ResultPoint(f5 - 1.0f, f9 + 1.0f),
                    new ResultPoint(f3 + 1.0f, f7 + 1.0f),
                    new ResultPoint(f4 - 1.0f, f8 - 1.0f),
                    new ResultPoint(f2 + 1.0f, f6 - 1.0f)
                }
                : new ResultPoint[] {
                    new ResultPoint(f5 + 1.0f, f9 + 1.0f),
                    new ResultPoint(f3 + 1.0f, f7 - 1.0f),
                    new ResultPoint(f4 - 1.0f, f8 + 1.0f),
                    new ResultPoint(f2 - 1.0f, f6 - 1.0f)
                };
    }

    public final ResultPoint getBlackPointOnSegment(float f, float f2, float f3, float f4) {
        int round = MathUtils.round(MathUtils.distance(f, f2, f3, f4));
        float f5 = round;
        float f6 = (f3 - f) / f5;
        float f7 = (f4 - f2) / f5;
        for (int i = 0; i < round; i++) {
            float f8 = i;
            int round2 = MathUtils.round((f8 * f6) + f);
            int round3 = MathUtils.round((f8 * f7) + f2);
            if (this.image.get(round2, round3)) {
                return new ResultPoint(round2, round3);
            }
        }
        return null;
    }

    public WhiteRectangleDetector(BitMatrix bitMatrix) {
        this(bitMatrix, 10, bitMatrix.width / 2, bitMatrix.height / 2);
    }
}
