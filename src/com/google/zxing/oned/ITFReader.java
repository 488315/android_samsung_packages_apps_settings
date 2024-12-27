package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ITFReader extends OneDReader {
    public int narrowLineWidth = -1;
    public static final int[] DEFAULT_ALLOWED_LENGTHS = {6, 8, 10, 12, 14};
    public static final int[] START_PATTERN = {1, 1, 1, 1};
    public static final int[][] END_PATTERN_REVERSED = {new int[] {1, 1, 2}, new int[] {1, 1, 3}};
    public static final int[][] PATTERNS = {
        new int[] {1, 1, 2, 2, 1},
        new int[] {2, 1, 1, 1, 2},
        new int[] {1, 2, 1, 1, 2},
        new int[] {2, 2, 1, 1, 1},
        new int[] {1, 1, 2, 1, 2},
        new int[] {2, 1, 2, 1, 1},
        new int[] {1, 2, 2, 1, 1},
        new int[] {1, 1, 1, 2, 2},
        new int[] {2, 1, 1, 2, 1},
        new int[] {1, 2, 1, 2, 1},
        new int[] {1, 1, 3, 3, 1},
        new int[] {3, 1, 1, 1, 3},
        new int[] {1, 3, 1, 1, 3},
        new int[] {3, 3, 1, 1, 1},
        new int[] {1, 1, 3, 1, 3},
        new int[] {3, 1, 3, 1, 1},
        new int[] {1, 3, 3, 1, 1},
        new int[] {1, 1, 1, 3, 3},
        new int[] {3, 1, 1, 3, 1},
        new int[] {1, 3, 1, 3, 1}
    };

    public static int decodeDigit(int[] iArr) {
        float f = 0.38f;
        int i = -1;
        for (int i2 = 0; i2 < 20; i2++) {
            float patternMatchVariance = OneDReader.patternMatchVariance(iArr, PATTERNS[i2], 0.5f);
            if (patternMatchVariance < f) {
                i = i2;
                f = patternMatchVariance;
            } else if (patternMatchVariance == f) {
                i = -1;
            }
        }
        if (i >= 0) {
            return i % 10;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public static int[] findGuardPattern(int i, BitArray bitArray, int[] iArr) {
        int length = iArr.length;
        int[] iArr2 = new int[length];
        int i2 = bitArray.size;
        int i3 = i;
        boolean z = false;
        int i4 = 0;
        while (i < i2) {
            if (bitArray.get(i) != z) {
                iArr2[i4] = iArr2[i4] + 1;
            } else {
                if (i4 != length - 1) {
                    i4++;
                } else {
                    if (OneDReader.patternMatchVariance(iArr2, iArr, 0.5f) < 0.38f) {
                        return new int[] {i3, i};
                    }
                    i3 += iArr2[0] + iArr2[1];
                    int i5 = i4 - 1;
                    System.arraycopy(iArr2, 2, iArr2, 0, i5);
                    iArr2[i5] = 0;
                    iArr2[i4] = 0;
                    i4--;
                }
                iArr2[i4] = 1;
                z = !z;
            }
            i++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.google.zxing.oned.OneDReader
    public final Result decodeRow(int i, BitArray bitArray, Map map) {
        int[] findGuardPattern;
        boolean z;
        int i2 = bitArray.size;
        int nextSet = bitArray.getNextSet(0);
        if (nextSet == i2) {
            throw NotFoundException.getNotFoundInstance();
        }
        int[] findGuardPattern2 = findGuardPattern(nextSet, bitArray, START_PATTERN);
        int i3 = findGuardPattern2[1];
        int i4 = findGuardPattern2[0];
        this.narrowLineWidth = (i3 - i4) / 4;
        validateQuietZone(i4, bitArray);
        int[][] iArr = END_PATTERN_REVERSED;
        bitArray.reverse();
        try {
            int i5 = bitArray.size;
            int nextSet2 = bitArray.getNextSet(0);
            if (nextSet2 == i5) {
                throw NotFoundException.getNotFoundInstance();
            }
            try {
                findGuardPattern = findGuardPattern(nextSet2, bitArray, iArr[0]);
            } catch (NotFoundException unused) {
                findGuardPattern = findGuardPattern(nextSet2, bitArray, iArr[1]);
            }
            validateQuietZone(findGuardPattern[0], bitArray);
            int i6 = findGuardPattern[0];
            int i7 = bitArray.size;
            findGuardPattern[0] = i7 - findGuardPattern[1];
            findGuardPattern[1] = i7 - i6;
            bitArray.reverse();
            StringBuilder sb = new StringBuilder(20);
            int i8 = findGuardPattern2[1];
            int i9 = findGuardPattern[0];
            int[] iArr2 = new int[10];
            int[] iArr3 = new int[5];
            int[] iArr4 = new int[5];
            while (i8 < i9) {
                OneDReader.recordPattern(i8, bitArray, iArr2);
                for (int i10 = 0; i10 < 5; i10++) {
                    int i11 = i10 * 2;
                    iArr3[i10] = iArr2[i11];
                    iArr4[i10] = iArr2[i11 + 1];
                }
                sb.append((char) (decodeDigit(iArr3) + 48));
                sb.append((char) (decodeDigit(iArr4) + 48));
                for (int i12 = 0; i12 < 10; i12++) {
                    i8 += iArr2[i12];
                }
            }
            String sb2 = sb.toString();
            int[] iArr5 = map != null ? (int[]) map.get(DecodeHintType.ALLOWED_LENGTHS) : null;
            if (iArr5 == null) {
                iArr5 = DEFAULT_ALLOWED_LENGTHS;
            }
            int length = sb2.length();
            int length2 = iArr5.length;
            int i13 = 0;
            int i14 = 0;
            while (true) {
                if (i13 >= length2) {
                    z = false;
                    break;
                }
                int i15 = iArr5[i13];
                if (length == i15) {
                    z = true;
                    break;
                }
                if (i15 > i14) {
                    i14 = i15;
                }
                i13++;
            }
            if (!z && length > i14) {
                z = true;
            }
            if (!z) {
                throw FormatException.getFormatInstance();
            }
            float f = i;
            Result result =
                    new Result(
                            sb2,
                            null,
                            new ResultPoint[] {
                                new ResultPoint(findGuardPattern2[1], f),
                                new ResultPoint(findGuardPattern[0], f)
                            },
                            BarcodeFormat.ITF);
            result.putMetadata(ResultMetadataType.SYMBOLOGY_IDENTIFIER, "]I0");
            return result;
        } catch (Throwable th) {
            bitArray.reverse();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001c, code lost:

       return;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void validateQuietZone(int r2, com.google.zxing.common.BitArray r3) {
        /*
            r1 = this;
            int r1 = r1.narrowLineWidth
            int r1 = r1 * 10
            int r1 = java.lang.Math.min(r1, r2)
            int r2 = r2 + (-1)
        La:
            if (r1 <= 0) goto L1a
            if (r2 < 0) goto L1a
            boolean r0 = r3.get(r2)
            if (r0 == 0) goto L15
            goto L1a
        L15:
            int r1 = r1 + (-1)
            int r2 = r2 + (-1)
            goto La
        L1a:
            if (r1 != 0) goto L1d
            return
        L1d:
            com.google.zxing.NotFoundException r1 = com.google.zxing.NotFoundException.getNotFoundInstance()
            throw r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.google.zxing.oned.ITFReader.validateQuietZone(int,"
                    + " com.google.zxing.common.BitArray):void");
    }
}
