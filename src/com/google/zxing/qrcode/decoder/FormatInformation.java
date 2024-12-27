package com.google.zxing.qrcode.decoder;

import androidx.preference.Preference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FormatInformation {
    public static final int[][] FORMAT_INFO_DECODE_LOOKUP = {
        new int[] {21522, 0},
        new int[] {20773, 1},
        new int[] {24188, 2},
        new int[] {23371, 3},
        new int[] {17913, 4},
        new int[] {16590, 5},
        new int[] {20375, 6},
        new int[] {19104, 7},
        new int[] {30660, 8},
        new int[] {29427, 9},
        new int[] {32170, 10},
        new int[] {30877, 11},
        new int[] {26159, 12},
        new int[] {25368, 13},
        new int[] {27713, 14},
        new int[] {26998, 15},
        new int[] {5769, 16},
        new int[] {5054, 17},
        new int[] {7399, 18},
        new int[] {6608, 19},
        new int[] {1890, 20},
        new int[] {597, 21},
        new int[] {3340, 22},
        new int[] {2107, 23},
        new int[] {13663, 24},
        new int[] {12392, 25},
        new int[] {16177, 26},
        new int[] {14854, 27},
        new int[] {9396, 28},
        new int[] {8579, 29},
        new int[] {11994, 30},
        new int[] {11245, 31}
    };
    public final byte dataMask;
    public final ErrorCorrectionLevel errorCorrectionLevel;

    public FormatInformation(int i) {
        int i2 = (i >> 3) & 3;
        if (i2 < 0 || i2 >= 4) {
            throw new IllegalArgumentException();
        }
        this.errorCorrectionLevel = ErrorCorrectionLevel.FOR_BITS[i2];
        this.dataMask = (byte) (i & 7);
    }

    public static FormatInformation doDecodeFormatInformation(int i, int i2) {
        int bitCount;
        int[][] iArr = FORMAT_INFO_DECODE_LOOKUP;
        int i3 = Preference.DEFAULT_ORDER;
        int i4 = 0;
        for (int i5 = 0; i5 < 32; i5++) {
            int[] iArr2 = iArr[i5];
            int i6 = iArr2[0];
            if (i6 == i || i6 == i2) {
                return new FormatInformation(iArr2[1]);
            }
            int bitCount2 = Integer.bitCount(i ^ i6);
            if (bitCount2 < i3) {
                i4 = iArr2[1];
                i3 = bitCount2;
            }
            if (i != i2 && (bitCount = Integer.bitCount(i6 ^ i2)) < i3) {
                i4 = iArr2[1];
                i3 = bitCount;
            }
        }
        if (i3 <= 3) {
            return new FormatInformation(i4);
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof FormatInformation)) {
            return false;
        }
        FormatInformation formatInformation = (FormatInformation) obj;
        return this.errorCorrectionLevel == formatInformation.errorCorrectionLevel
                && this.dataMask == formatInformation.dataMask;
    }

    public final int hashCode() {
        return this.dataMask | (this.errorCorrectionLevel.ordinal() << 3);
    }
}
