package com.google.zxing.qrcode.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BitMatrixParser {
    public final BitMatrix bitMatrix;
    public boolean mirror;
    public FormatInformation parsedFormatInfo;
    public Version parsedVersion;

    public BitMatrixParser(BitMatrix bitMatrix) {
        int i = bitMatrix.height;
        if (i < 21 || (i & 3) != 1) {
            throw FormatException.getFormatInstance();
        }
        this.bitMatrix = bitMatrix;
    }

    public final int copyBit(int i, int i2, int i3) {
        boolean z = this.mirror;
        BitMatrix bitMatrix = this.bitMatrix;
        return z ? bitMatrix.get(i2, i) : bitMatrix.get(i, i2) ? (i3 << 1) | 1 : i3 << 1;
    }

    public final FormatInformation readFormatInformation() {
        FormatInformation formatInformation = this.parsedFormatInfo;
        if (formatInformation != null) {
            return formatInformation;
        }
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < 6; i3++) {
            i2 = copyBit(i3, 8, i2);
        }
        int copyBit = copyBit(8, 7, copyBit(8, 8, copyBit(7, 8, i2)));
        for (int i4 = 5; i4 >= 0; i4--) {
            copyBit = copyBit(8, i4, copyBit);
        }
        int i5 = this.bitMatrix.height;
        int i6 = i5 - 7;
        for (int i7 = i5 - 1; i7 >= i6; i7--) {
            i = copyBit(8, i7, i);
        }
        for (int i8 = i5 - 8; i8 < i5; i8++) {
            i = copyBit(i8, 8, i);
        }
        FormatInformation doDecodeFormatInformation =
                FormatInformation.doDecodeFormatInformation(copyBit, i);
        if (doDecodeFormatInformation == null) {
            doDecodeFormatInformation =
                    FormatInformation.doDecodeFormatInformation(copyBit ^ 21522, i ^ 21522);
        }
        this.parsedFormatInfo = doDecodeFormatInformation;
        if (doDecodeFormatInformation != null) {
            return doDecodeFormatInformation;
        }
        throw FormatException.getFormatInstance();
    }

    public final Version readVersion() {
        Version version = this.parsedVersion;
        if (version != null) {
            return version;
        }
        int i = this.bitMatrix.height;
        int i2 = (i - 17) / 4;
        if (i2 <= 6) {
            return Version.getVersionForNumber(i2);
        }
        int i3 = i - 11;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 5; i6 >= 0; i6--) {
            for (int i7 = i - 9; i7 >= i3; i7--) {
                i5 = copyBit(i7, i6, i5);
            }
        }
        Version decodeVersionInformation = Version.decodeVersionInformation(i5);
        if (decodeVersionInformation != null
                && (decodeVersionInformation.versionNumber * 4) + 17 == i) {
            this.parsedVersion = decodeVersionInformation;
            return decodeVersionInformation;
        }
        for (int i8 = 5; i8 >= 0; i8--) {
            for (int i9 = i - 9; i9 >= i3; i9--) {
                i4 = copyBit(i8, i9, i4);
            }
        }
        Version decodeVersionInformation2 = Version.decodeVersionInformation(i4);
        if (decodeVersionInformation2 == null
                || (decodeVersionInformation2.versionNumber * 4) + 17 != i) {
            throw FormatException.getFormatInstance();
        }
        this.parsedVersion = decodeVersionInformation2;
        return decodeVersionInformation2;
    }

    public final void remask() {
        if (this.parsedFormatInfo == null) {
            return;
        }
        DataMask dataMask = DataMask.values()[this.parsedFormatInfo.dataMask];
        BitMatrix bitMatrix = this.bitMatrix;
        int i = bitMatrix.height;
        dataMask.getClass();
        for (int i2 = 0; i2 < i; i2++) {
            for (int i3 = 0; i3 < i; i3++) {
                if (dataMask.isMasked(i2, i3)) {
                    bitMatrix.flip(i3, i2);
                }
            }
        }
    }
}
