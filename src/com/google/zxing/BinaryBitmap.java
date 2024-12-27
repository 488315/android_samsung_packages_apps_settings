package com.google.zxing;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.lang.reflect.Array;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BinaryBitmap {
    public final HybridBinarizer binarizer;
    public BitMatrix matrix;

    public BinaryBitmap(HybridBinarizer hybridBinarizer) {
        this.binarizer = hybridBinarizer;
    }

    public final BitMatrix getBlackMatrix() {
        int[] iArr;
        BinaryBitmap binaryBitmap = this;
        if (binaryBitmap.matrix == null) {
            HybridBinarizer hybridBinarizer = binaryBitmap.binarizer;
            BitMatrix bitMatrix = hybridBinarizer.matrix;
            if (bitMatrix == null) {
                LuminanceSource luminanceSource = hybridBinarizer.source;
                int i = luminanceSource.width;
                int i2 = luminanceSource.height;
                if (i < 40 || i2 < 40) {
                    BitMatrix bitMatrix2 = new BitMatrix(i, i2);
                    if (hybridBinarizer.luminances.length < i) {
                        hybridBinarizer.luminances = new byte[i];
                    }
                    int i3 = 0;
                    while (true) {
                        iArr = hybridBinarizer.buckets;
                        if (i3 >= 32) {
                            break;
                        }
                        iArr[i3] = 0;
                        i3++;
                    }
                    for (int i4 = 1; i4 < 5; i4++) {
                        byte[] row =
                                luminanceSource.getRow((i2 * i4) / 5, hybridBinarizer.luminances);
                        int i5 = (i * 4) / 5;
                        for (int i6 = i / 5; i6 < i5; i6++) {
                            int i7 = (row[i6] & 255) >> 3;
                            iArr[i7] = iArr[i7] + 1;
                        }
                    }
                    int estimateBlackPoint = HybridBinarizer.estimateBlackPoint(iArr);
                    byte[] matrix = luminanceSource.getMatrix();
                    for (int i8 = 0; i8 < i2; i8++) {
                        int i9 = i8 * i;
                        for (int i10 = 0; i10 < i; i10++) {
                            if ((matrix[i9 + i10] & 255) < estimateBlackPoint) {
                                bitMatrix2.set(i10, i8);
                            }
                        }
                    }
                    hybridBinarizer.matrix = bitMatrix2;
                } else {
                    byte[] matrix2 = luminanceSource.getMatrix();
                    int i11 = i >> 3;
                    if ((i & 7) != 0) {
                        i11++;
                    }
                    int i12 = i2 >> 3;
                    if ((i2 & 7) != 0) {
                        i12++;
                    }
                    int i13 = i2 - 8;
                    int i14 = i - 8;
                    int[][] iArr2 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i12, i11);
                    int i15 = 0;
                    while (true) {
                        int i16 = 8;
                        if (i15 >= i12) {
                            break;
                        }
                        int i17 = i15 << 3;
                        if (i17 > i13) {
                            i17 = i13;
                        }
                        int i18 = 0;
                        while (i18 < i11) {
                            int i19 = i18 << 3;
                            if (i19 > i14) {
                                i19 = i14;
                            }
                            int i20 = (i17 * i) + i19;
                            int i21 = 0;
                            int i22 = 0;
                            int i23 = 0;
                            int i24 = 255;
                            while (i21 < i16) {
                                int i25 = i17;
                                int i26 = i23;
                                int i27 = i24;
                                int i28 = 0;
                                while (i28 < i16) {
                                    HybridBinarizer hybridBinarizer2 = hybridBinarizer;
                                    int i29 = matrix2[i20 + i28] & 255;
                                    i22 += i29;
                                    if (i29 < i27) {
                                        i27 = i29;
                                    }
                                    if (i29 > i26) {
                                        i26 = i29;
                                    }
                                    i28++;
                                    hybridBinarizer = hybridBinarizer2;
                                    i16 = 8;
                                }
                                HybridBinarizer hybridBinarizer3 = hybridBinarizer;
                                if (i26 - i27 > 24) {
                                    while (true) {
                                        i21++;
                                        i20 += i;
                                        if (i21 < 8) {
                                            int i30 = 0;
                                            for (int i31 = 8; i30 < i31; i31 = 8) {
                                                i22 += matrix2[i20 + i30] & 255;
                                                i30++;
                                            }
                                        }
                                    }
                                }
                                i21++;
                                i20 += i;
                                i16 = 8;
                                i24 = i27;
                                i23 = i26;
                                i17 = i25;
                                hybridBinarizer = hybridBinarizer3;
                            }
                            HybridBinarizer hybridBinarizer4 = hybridBinarizer;
                            int i32 = i17;
                            int i33 = i22 >> 6;
                            int i34 = i24;
                            if (i23 - i34 <= 24) {
                                i33 = i34 / 2;
                                if (i15 > 0 && i18 > 0) {
                                    int[] iArr3 = iArr2[i15 - 1];
                                    int i35 = i18 - 1;
                                    int i36 =
                                            (((iArr2[i15][i35] * 2) + iArr3[i18]) + iArr3[i35]) / 4;
                                    if (i34 < i36) {
                                        i33 = i36;
                                    }
                                }
                            }
                            iArr2[i15][i18] = i33;
                            i18++;
                            i16 = 8;
                            i17 = i32;
                            hybridBinarizer = hybridBinarizer4;
                        }
                        i15++;
                    }
                    HybridBinarizer hybridBinarizer5 = hybridBinarizer;
                    BitMatrix bitMatrix3 = new BitMatrix(i, i2);
                    int i37 = 0;
                    while (i37 < i12) {
                        int i38 = i37 << 3;
                        if (i38 > i13) {
                            i38 = i13;
                        }
                        int min = i37 < 2 ? 2 : Math.min(i37, i12 - 3);
                        int i39 = 0;
                        while (i39 < i11) {
                            int i40 = i39 << 3;
                            if (i40 > i14) {
                                i40 = i14;
                            }
                            int min2 = i39 < 2 ? 2 : Math.min(i39, i11 - 3);
                            int i41 = -2;
                            int i42 = 0;
                            for (int i43 = 2; i41 <= i43; i43 = 2) {
                                int[] iArr4 = iArr2[min + i41];
                                i42 =
                                        iArr4[min2 - 2]
                                                + iArr4[min2 - 1]
                                                + iArr4[min2]
                                                + iArr4[min2 + 1]
                                                + iArr4[min2 + 2]
                                                + i42;
                                i41++;
                            }
                            int i44 = i42 / 25;
                            int i45 = (i38 * i) + i40;
                            int i46 = min;
                            int i47 = 0;
                            while (true) {
                                if (i47 < 8) {
                                    int i48 = i11;
                                    int i49 = 0;
                                    for (int i50 = 8; i49 < i50; i50 = 8) {
                                        byte[] bArr = matrix2;
                                        if ((matrix2[i45 + i49] & 255) <= i44) {
                                            bitMatrix3.set(i40 + i49, i38 + i47);
                                        }
                                        i49++;
                                        matrix2 = bArr;
                                    }
                                    i47++;
                                    i45 += i;
                                    i11 = i48;
                                }
                            }
                            i39++;
                            min = i46;
                        }
                        i37++;
                    }
                    hybridBinarizer = hybridBinarizer5;
                    hybridBinarizer.matrix = bitMatrix3;
                }
                bitMatrix = hybridBinarizer.matrix;
                binaryBitmap = this;
            }
            binaryBitmap.matrix = bitMatrix;
        }
        return binaryBitmap.matrix;
    }

    public final BitArray getBlackRow(int i, BitArray bitArray) {
        int[] iArr;
        HybridBinarizer hybridBinarizer = this.binarizer;
        LuminanceSource luminanceSource = hybridBinarizer.source;
        int i2 = luminanceSource.width;
        if (bitArray.size < i2) {
            bitArray = new BitArray(i2);
        } else {
            int length = bitArray.bits.length;
            for (int i3 = 0; i3 < length; i3++) {
                bitArray.bits[i3] = 0;
            }
        }
        if (hybridBinarizer.luminances.length < i2) {
            hybridBinarizer.luminances = new byte[i2];
        }
        int i4 = 0;
        while (true) {
            iArr = hybridBinarizer.buckets;
            if (i4 >= 32) {
                break;
            }
            iArr[i4] = 0;
            i4++;
        }
        byte[] row = luminanceSource.getRow(i, hybridBinarizer.luminances);
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = (row[i5] & 255) >> 3;
            iArr[i6] = iArr[i6] + 1;
        }
        int estimateBlackPoint = HybridBinarizer.estimateBlackPoint(iArr);
        if (i2 < 3) {
            for (int i7 = 0; i7 < i2; i7++) {
                if ((row[i7] & 255) < estimateBlackPoint) {
                    bitArray.set(i7);
                }
            }
        } else {
            int i8 = 1;
            int i9 = row[0] & 255;
            int i10 = row[1] & 255;
            while (i8 < i2 - 1) {
                int i11 = i8 + 1;
                int i12 = row[i11] & 255;
                if ((((i10 * 4) - i9) - i12) / 2 < estimateBlackPoint) {
                    bitArray.set(i8);
                }
                i9 = i10;
                i8 = i11;
                i10 = i12;
            }
        }
        return bitArray;
    }

    public final String toString() {
        try {
            return getBlackMatrix().toString();
        } catch (NotFoundException unused) {
            return ApnSettings.MVNO_NONE;
        }
    }
}
