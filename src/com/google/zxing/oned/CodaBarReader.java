package com.google.zxing.oned;

import androidx.preference.Preference;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;

import java.util.Arrays;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CodaBarReader extends OneDReader {
    public static final char[] ALPHABET = "0123456789-$:/.+ABCD".toCharArray();
    public static final int[] CHARACTER_ENCODINGS = {
        3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14
    };
    public static final char[] STARTEND_ENCODING = {'A', 'B', 'C', 'D'};
    public final StringBuilder decodeRowResult = new StringBuilder(20);
    public int[] counters = new int[80];
    public int counterLength = 0;

    public static boolean arrayContains(char[] cArr, char c) {
        if (cArr != null) {
            for (char c2 : cArr) {
                if (c2 == c) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.google.zxing.oned.OneDReader
    public final Result decodeRow(int i, BitArray bitArray, Map map) {
        int i2;
        int[] iArr;
        int i3;
        int i4 = 0;
        Arrays.fill(this.counters, 0);
        this.counterLength = 0;
        int nextUnset = bitArray.getNextUnset(0);
        int i5 = bitArray.size;
        if (nextUnset >= i5) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i6 = 1;
        int i7 = 0;
        boolean z = true;
        while (nextUnset < i5) {
            if (bitArray.get(nextUnset) != z) {
                i7++;
            } else {
                int[] iArr2 = this.counters;
                int i8 = this.counterLength;
                iArr2[i8] = i7;
                int i9 = i8 + 1;
                this.counterLength = i9;
                if (i9 >= iArr2.length) {
                    int[] iArr3 = new int[i9 * 2];
                    System.arraycopy(iArr2, 0, iArr3, 0, i9);
                    this.counters = iArr3;
                }
                z = !z;
                i7 = 1;
            }
            nextUnset++;
        }
        int[] iArr4 = this.counters;
        int i10 = this.counterLength;
        iArr4[i10] = i7;
        int i11 = i10 + 1;
        this.counterLength = i11;
        if (i11 >= iArr4.length) {
            int[] iArr5 = new int[i11 * 2];
            System.arraycopy(iArr4, 0, iArr5, 0, i11);
            this.counters = iArr5;
        }
        int i12 = 1;
        while (i12 < this.counterLength) {
            int narrowWidePattern = toNarrowWidePattern(i12);
            if (narrowWidePattern != -1) {
                char[] cArr = ALPHABET;
                char c = cArr[narrowWidePattern];
                char[] cArr2 = STARTEND_ENCODING;
                if (arrayContains(cArr2, c)) {
                    int i13 = i4;
                    for (int i14 = i12; i14 < i12 + 7; i14++) {
                        i13 += this.counters[i14];
                    }
                    if (i12 == i6 || this.counters[i12 - 1] >= i13 / 2) {
                        StringBuilder sb = this.decodeRowResult;
                        sb.setLength(i4);
                        int i15 = i12;
                        while (true) {
                            int narrowWidePattern2 = toNarrowWidePattern(i15);
                            if (narrowWidePattern2 == -1) {
                                throw NotFoundException.getNotFoundInstance();
                            }
                            sb.append((char) narrowWidePattern2);
                            i2 = i15 + 8;
                            if ((sb.length() <= i6
                                            || !arrayContains(cArr2, cArr[narrowWidePattern2]))
                                    && i2 < this.counterLength) {
                                i15 = i2;
                                i6 = i6;
                                i4 = i4;
                            }
                        }
                        int i16 = i15 + 7;
                        int i17 = this.counters[i16];
                        int i18 = i4;
                        for (int i19 = -8; i19 < -1; i19++) {
                            i18 += this.counters[i2 + i19];
                        }
                        if (i2 < this.counterLength && i17 < i18 / 2) {
                            throw NotFoundException.getNotFoundInstance();
                        }
                        int[] iArr6 = new int[4];
                        iArr6[i4] = i4;
                        iArr6[i6] = i4;
                        iArr6[2] = i4;
                        iArr6[3] = i4;
                        int[] iArr7 = new int[4];
                        iArr7[i4] = i4;
                        iArr7[i6] = i4;
                        iArr7[2] = i4;
                        iArr7[3] = i4;
                        int length = sb.length() - i6;
                        int i20 = i12;
                        int i21 = i4;
                        while (true) {
                            iArr = CHARACTER_ENCODINGS;
                            if (i21 > length) {
                                break;
                            }
                            int i22 = iArr[sb.charAt(i21)];
                            for (int i23 = 6; i23 >= 0; i23--) {
                                int i24 = ((i22 & 1) * 2) + (i23 & 1);
                                iArr6[i24] = iArr6[i24] + this.counters[i20 + i23];
                                iArr7[i24] = iArr7[i24] + i6;
                                i22 >>= 1;
                            }
                            i20 += 8;
                            i21++;
                        }
                        float[] fArr = new float[4];
                        float[] fArr2 = new float[4];
                        int i25 = i4;
                        for (int i26 = 2; i25 < i26; i26 = 2) {
                            fArr2[i25] = 0.0f;
                            int i27 = i25 + 2;
                            float f = iArr6[i27];
                            float f2 = iArr7[i27];
                            float f3 = ((f / f2) + (iArr6[i25] / iArr7[i25])) / 2.0f;
                            fArr2[i27] = f3;
                            fArr[i25] = f3;
                            fArr[i27] = ((f * 2.0f) + 1.5f) / f2;
                            i25++;
                        }
                        int i28 = i12;
                        for (int i29 = 0; i29 <= length; i29++) {
                            int i30 = iArr[sb.charAt(i29)];
                            for (int i31 = 6; i31 >= 0; i31--) {
                                int i32 = ((i30 & 1) * 2) + (i31 & 1);
                                float f4 = this.counters[i28 + i31];
                                if (f4 < fArr2[i32] || f4 > fArr[i32]) {
                                    throw NotFoundException.getNotFoundInstance();
                                }
                                i30 >>= 1;
                            }
                            i28 += 8;
                        }
                        for (int i33 = 0; i33 < sb.length(); i33++) {
                            sb.setCharAt(i33, cArr[sb.charAt(i33)]);
                        }
                        if (!arrayContains(cArr2, sb.charAt(0))) {
                            throw NotFoundException.getNotFoundInstance();
                        }
                        if (!arrayContains(cArr2, sb.charAt(sb.length() - 1))) {
                            throw NotFoundException.getNotFoundInstance();
                        }
                        if (sb.length() <= 3) {
                            throw NotFoundException.getNotFoundInstance();
                        }
                        if (map == null
                                || !map.containsKey(DecodeHintType.RETURN_CODABAR_START_END)) {
                            sb.deleteCharAt(sb.length() - 1);
                            i3 = 0;
                            sb.deleteCharAt(0);
                        } else {
                            i3 = 0;
                        }
                        for (int i34 = i3; i34 < i12; i34++) {
                            i3 += this.counters[i34];
                        }
                        float f5 = i3;
                        while (i12 < i16) {
                            i3 += this.counters[i12];
                            i12++;
                        }
                        float f6 = i;
                        Result result =
                                new Result(
                                        sb.toString(),
                                        null,
                                        new ResultPoint[] {
                                            new ResultPoint(f5, f6), new ResultPoint(i3, f6)
                                        },
                                        BarcodeFormat.CODABAR);
                        result.putMetadata(ResultMetadataType.SYMBOLOGY_IDENTIFIER, "]F0");
                        return result;
                    }
                }
            }
            i12 += 2;
            i6 = i6;
            i4 = i4;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public final int toNarrowWidePattern(int i) {
        int i2 = i + 7;
        if (i2 >= this.counterLength) {
            return -1;
        }
        int[] iArr = this.counters;
        int i3 = Preference.DEFAULT_ORDER;
        int i4 = 0;
        int i5 = Integer.MAX_VALUE;
        int i6 = 0;
        for (int i7 = i; i7 < i2; i7 += 2) {
            int i8 = iArr[i7];
            if (i8 < i5) {
                i5 = i8;
            }
            if (i8 > i6) {
                i6 = i8;
            }
        }
        int i9 = (i5 + i6) / 2;
        int i10 = 0;
        for (int i11 = i + 1; i11 < i2; i11 += 2) {
            int i12 = iArr[i11];
            if (i12 < i3) {
                i3 = i12;
            }
            if (i12 > i10) {
                i10 = i12;
            }
        }
        int i13 = (i3 + i10) / 2;
        int i14 = 128;
        int i15 = 0;
        for (int i16 = 0; i16 < 7; i16++) {
            i14 >>= 1;
            if (iArr[i + i16] > ((i16 & 1) == 0 ? i9 : i13)) {
                i15 |= i14;
            }
        }
        while (true) {
            int[] iArr2 = CHARACTER_ENCODINGS;
            if (i4 >= iArr2.length) {
                return -1;
            }
            if (iArr2[i4] == i15) {
                return i4;
            }
            i4++;
        }
    }
}
