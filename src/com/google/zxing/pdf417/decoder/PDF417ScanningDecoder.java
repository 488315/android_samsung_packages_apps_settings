package com.google.zxing.pdf417.decoder;

import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class PDF417ScanningDecoder {
    public static final ErrorCorrection errorCorrection = new ErrorCorrection();

    public static BoundingBox adjustBoundingBox(
            DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn) {
        int i;
        int[] iArr;
        int i2;
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        ResultPoint resultPoint3;
        ResultPoint resultPoint4;
        if (detectionResultRowIndicatorColumn == null) {
            return null;
        }
        BarcodeMetadata barcodeMetadata = detectionResultRowIndicatorColumn.getBarcodeMetadata();
        Codeword[] codewordArr = detectionResultRowIndicatorColumn.codewords;
        boolean z = detectionResultRowIndicatorColumn.isLeft;
        BoundingBox boundingBox = detectionResultRowIndicatorColumn.boundingBox;
        if (barcodeMetadata == null) {
            iArr = null;
        } else {
            ResultPoint resultPoint5 = z ? boundingBox.topLeft : boundingBox.topRight;
            ResultPoint resultPoint6 = z ? boundingBox.bottomLeft : boundingBox.bottomRight;
            int imageRowToCodewordIndex =
                    detectionResultRowIndicatorColumn.imageRowToCodewordIndex((int) resultPoint5.y);
            int imageRowToCodewordIndex2 =
                    detectionResultRowIndicatorColumn.imageRowToCodewordIndex((int) resultPoint6.y);
            int i3 = 0;
            int i4 = 1;
            int i5 = -1;
            while (true) {
                i = barcodeMetadata.rowCount;
                if (imageRowToCodewordIndex >= imageRowToCodewordIndex2) {
                    break;
                }
                Codeword codeword = codewordArr[imageRowToCodewordIndex];
                if (codeword != null) {
                    codeword.setRowNumberAsRowIndicatorColumn();
                    int i6 = codeword.rowNumber;
                    int i7 = i6 - i5;
                    if (i7 == 0) {
                        i3++;
                    } else if (i7 == 1) {
                        i4 = Math.max(i4, i3);
                        i5 = codeword.rowNumber;
                        i3 = 1;
                    } else if (i6 >= i) {
                        codewordArr[imageRowToCodewordIndex] = null;
                    } else {
                        i3 = 1;
                        i5 = i6;
                    }
                }
                imageRowToCodewordIndex++;
            }
            iArr = new int[i];
            for (Codeword codeword2 : codewordArr) {
                if (codeword2 != null && (i2 = codeword2.rowNumber) < i) {
                    iArr[i2] = iArr[i2] + 1;
                }
            }
        }
        if (iArr == null) {
            return null;
        }
        int i8 = -1;
        for (int i9 : iArr) {
            i8 = Math.max(i8, i9);
        }
        int i10 = 0;
        for (int i11 : iArr) {
            i10 += i8 - i11;
            if (i11 > 0) {
                break;
            }
        }
        for (int i12 = 0; i10 > 0 && codewordArr[i12] == null; i12++) {
            i10--;
        }
        int i13 = 0;
        for (int length = iArr.length - 1; length >= 0; length--) {
            int i14 = iArr[length];
            i13 += i8 - i14;
            if (i14 > 0) {
                break;
            }
        }
        for (int length2 = codewordArr.length - 1;
                i13 > 0 && codewordArr[length2] == null;
                length2--) {
            i13--;
        }
        ResultPoint resultPoint7 = boundingBox.topLeft;
        ResultPoint resultPoint8 = boundingBox.topRight;
        if (i10 > 0) {
            ResultPoint resultPoint9 = z ? resultPoint7 : resultPoint8;
            ResultPoint resultPoint10 =
                    new ResultPoint(resultPoint9.x, ((int) resultPoint9.y) - i10 >= 0 ? r6 : 0);
            if (z) {
                resultPoint2 = resultPoint8;
                resultPoint = resultPoint10;
            } else {
                resultPoint = resultPoint7;
                resultPoint2 = resultPoint10;
            }
        } else {
            resultPoint = resultPoint7;
            resultPoint2 = resultPoint8;
        }
        ResultPoint resultPoint11 = boundingBox.bottomLeft;
        ResultPoint resultPoint12 = boundingBox.bottomRight;
        if (i13 > 0) {
            ResultPoint resultPoint13 = z ? resultPoint11 : resultPoint12;
            int i15 = ((int) resultPoint13.y) + i13;
            int i16 = boundingBox.image.height;
            if (i15 >= i16) {
                i15 = i16 - 1;
            }
            ResultPoint resultPoint14 = new ResultPoint(resultPoint13.x, i15);
            if (z) {
                resultPoint4 = resultPoint12;
                resultPoint3 = resultPoint14;
            } else {
                resultPoint3 = resultPoint11;
                resultPoint4 = resultPoint14;
            }
        } else {
            resultPoint3 = resultPoint11;
            resultPoint4 = resultPoint12;
        }
        return new BoundingBox(
                boundingBox.image, resultPoint, resultPoint3, resultPoint2, resultPoint4);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x04ac, code lost:

       if (r4 == 924) goto L284;
    */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x04b1, code lost:

       if (r11 >= r24[0]) goto L282;
    */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x04b5, code lost:

       if (r24[r11] >= 900) goto L282;
    */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x04bc, code lost:

       r7 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x04bf, code lost:

       if (r7 >= 6) goto L400;
    */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x04c1, code lost:

       r3.currentBytes.append((char) (((byte) (r13 >> ((5 - r7) * 8))) & 255));
       r7 = r7 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x04d6, code lost:

       r8 = r11;
       r7 = 0;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0449  */
    /* JADX WARN: Type inference failed for: r4v47 */
    /* JADX WARN: Type inference failed for: r4v48 */
    /* JADX WARN: Type inference failed for: r4v49, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v54 */
    /* JADX WARN: Type inference failed for: r4v67 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.zxing.common.DecoderResult decodeCodewords(
            int[] r24, int r25, int[] r26) {
        /*
            Method dump skipped, instructions count: 1486
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.pdf417.decoder.PDF417ScanningDecoder.decodeCodewords(int[],"
                    + " int, int[]):com.google.zxing.common.DecoderResult");
    }

    /* JADX WARN: Code restructure failed: missing block: B:134:0x0031, code lost:

       continue;
    */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0031, code lost:

       continue;
    */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0031, code lost:

       continue;
    */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x005b A[EDGE_INSN: B:31:0x005b->B:32:0x005b BREAK  A[LOOP:2: B:19:0x0042->B:27:0x0042], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.zxing.pdf417.decoder.Codeword detectCodeword(
            com.google.zxing.common.BitMatrix r20,
            int r21,
            int r22,
            boolean r23,
            int r24,
            int r25,
            int r26,
            int r27) {
        /*
            Method dump skipped, instructions count: 381
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.pdf417.decoder.PDF417ScanningDecoder.detectCodeword(com.google.zxing.common.BitMatrix,"
                    + " int, int, boolean, int, int, int,"
                    + " int):com.google.zxing.pdf417.decoder.Codeword");
    }

    public static DetectionResultRowIndicatorColumn getRowIndicatorColumn(
            BitMatrix bitMatrix,
            BoundingBox boundingBox,
            ResultPoint resultPoint,
            boolean z,
            int i,
            int i2) {
        DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn =
                new DetectionResultRowIndicatorColumn(boundingBox, z);
        int i3 = 0;
        while (i3 < 2) {
            int i4 = i3 == 0 ? 1 : -1;
            int i5 = (int) resultPoint.x;
            for (int i6 = (int) resultPoint.y;
                    i6 <= boundingBox.maxY && i6 >= boundingBox.minY;
                    i6 += i4) {
                Codeword detectCodeword =
                        detectCodeword(bitMatrix, 0, bitMatrix.width, z, i5, i6, i, i2);
                if (detectCodeword != null) {
                    detectionResultRowIndicatorColumn
                                    .codewords[
                                    detectionResultRowIndicatorColumn.imageRowToCodewordIndex(i6)] =
                            detectCodeword;
                    i5 = z ? detectCodeword.startX : detectCodeword.endX;
                }
            }
            i3++;
        }
        return detectionResultRowIndicatorColumn;
    }
}
