package com.google.zxing.common;

import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class HybridBinarizer {
    public static final byte[] EMPTY = new byte[0];
    public BitMatrix matrix;
    public final LuminanceSource source;
    public byte[] luminances = EMPTY;
    public final int[] buckets = new int[32];

    public HybridBinarizer(LuminanceSource luminanceSource) {
        this.source = luminanceSource;
    }

    public static int estimateBlackPoint(int[] iArr) {
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            int i5 = iArr[i4];
            if (i5 > i) {
                i3 = i4;
                i = i5;
            }
            if (i5 > i2) {
                i2 = i5;
            }
        }
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < length; i8++) {
            int i9 = i8 - i3;
            int i10 = iArr[i8] * i9 * i9;
            if (i10 > i7) {
                i6 = i8;
                i7 = i10;
            }
        }
        if (i3 <= i6) {
            int i11 = i3;
            i3 = i6;
            i6 = i11;
        }
        if (i3 - i6 <= length / 16) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i12 = i3 - 1;
        int i13 = -1;
        int i14 = i12;
        while (i12 > i6) {
            int i15 = i12 - i6;
            int i16 = (i2 - iArr[i12]) * (i3 - i12) * i15 * i15;
            if (i16 > i13) {
                i14 = i12;
                i13 = i16;
            }
            i12--;
        }
        return i14 << 3;
    }
}
