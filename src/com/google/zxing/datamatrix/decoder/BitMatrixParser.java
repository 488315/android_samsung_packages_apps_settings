package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BitMatrixParser {
    public final BitMatrix mappingBitMatrix;
    public final BitMatrix readMappingMatrix;
    public final Version version;

    public BitMatrixParser(BitMatrix bitMatrix) {
        int i;
        int i2;
        int i3 = bitMatrix.height;
        if (i3 < 8 || i3 > 144 || (i = i3 & 1) != 0) {
            throw FormatException.getFormatInstance();
        }
        int i4 = bitMatrix.width;
        Version[] versionArr = Version.VERSIONS;
        if (i != 0 || (i4 & 1) != 0) {
            throw FormatException.getFormatInstance();
        }
        Version[] versionArr2 = Version.VERSIONS;
        for (int i5 = 0; i5 < 48; i5++) {
            Version version = versionArr2[i5];
            int i6 = version.symbolSizeRows;
            if (i6 == i3 && (i2 = version.symbolSizeColumns) == i4) {
                this.version = version;
                if (bitMatrix.height != i6) {
                    throw new IllegalArgumentException(
                            "Dimension of bitMatrix must match the version size");
                }
                int i7 = version.dataRegionSizeRows;
                int i8 = i6 / i7;
                int i9 = version.dataRegionSizeColumns;
                int i10 = i2 / i9;
                BitMatrix bitMatrix2 = new BitMatrix(i10 * i9, i8 * i7);
                for (int i11 = 0; i11 < i8; i11++) {
                    int i12 = i11 * i7;
                    for (int i13 = 0; i13 < i10; i13++) {
                        int i14 = i13 * i9;
                        for (int i15 = 0; i15 < i7; i15++) {
                            int i16 = ((i7 + 2) * i11) + 1 + i15;
                            int i17 = i12 + i15;
                            for (int i18 = 0; i18 < i9; i18++) {
                                if (bitMatrix.get(((i9 + 2) * i13) + 1 + i18, i16)) {
                                    bitMatrix2.set(i14 + i18, i17);
                                }
                            }
                        }
                    }
                }
                this.mappingBitMatrix = bitMatrix2;
                this.readMappingMatrix = new BitMatrix(bitMatrix2.width, bitMatrix2.height);
                return;
            }
        }
        throw FormatException.getFormatInstance();
    }

    public final boolean readModule(int i, int i2, int i3, int i4) {
        if (i < 0) {
            i += i3;
            i2 += 4 - ((i3 + 4) & 7);
        }
        if (i2 < 0) {
            i2 += i4;
            i += 4 - ((i4 + 4) & 7);
        }
        if (i >= i3) {
            i -= i3;
        }
        this.readMappingMatrix.set(i2, i);
        return this.mappingBitMatrix.get(i2, i);
    }

    public final int readUtah(int i, int i2, int i3, int i4) {
        int i5 = i - 2;
        int i6 = i2 - 2;
        int i7 = (readModule(i5, i6, i3, i4) ? 1 : 0) << 1;
        int i8 = i2 - 1;
        if (readModule(i5, i8, i3, i4)) {
            i7 |= 1;
        }
        int i9 = i7 << 1;
        int i10 = i - 1;
        if (readModule(i10, i6, i3, i4)) {
            i9 |= 1;
        }
        int i11 = i9 << 1;
        if (readModule(i10, i8, i3, i4)) {
            i11 |= 1;
        }
        int i12 = i11 << 1;
        if (readModule(i10, i2, i3, i4)) {
            i12 |= 1;
        }
        int i13 = i12 << 1;
        if (readModule(i, i6, i3, i4)) {
            i13 |= 1;
        }
        int i14 = i13 << 1;
        if (readModule(i, i8, i3, i4)) {
            i14 |= 1;
        }
        int i15 = i14 << 1;
        return readModule(i, i2, i3, i4) ? i15 | 1 : i15;
    }
}
