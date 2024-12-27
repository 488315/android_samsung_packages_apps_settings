package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class EAN13Reader extends UPCEANReader {
    public static final int[] FIRST_DIGIT_ENCODINGS = {0, 11, 13, 14, 19, 25, 28, 21, 22, 26};
    public final int[] decodeMiddleCounters = new int[4];

    @Override // com.google.zxing.oned.UPCEANReader
    public final int decodeMiddle(BitArray bitArray, int[] iArr, StringBuilder sb) {
        int[] iArr2 = this.decodeMiddleCounters;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int i = bitArray.size;
        int i2 = iArr[1];
        int i3 = 0;
        for (int i4 = 0; i4 < 6 && i2 < i; i4++) {
            int decodeDigit =
                    UPCEANReader.decodeDigit(bitArray, iArr2, i2, UPCEANReader.L_AND_G_PATTERNS);
            sb.append((char) ((decodeDigit % 10) + 48));
            for (int i5 : iArr2) {
                i2 += i5;
            }
            if (decodeDigit >= 10) {
                i3 |= 1 << (5 - i4);
            }
        }
        for (int i6 = 0; i6 < 10; i6++) {
            if (i3 == FIRST_DIGIT_ENCODINGS[i6]) {
                sb.insert(0, (char) (i6 + 48));
                int i7 =
                        UPCEANReader.findGuardPattern(
                                bitArray, i2, true, UPCEANReader.MIDDLE_PATTERN, new int[5])[1];
                for (int i8 = 0; i8 < 6 && i7 < i; i8++) {
                    sb.append(
                            (char)
                                    (UPCEANReader.decodeDigit(
                                                    bitArray, iArr2, i7, UPCEANReader.L_PATTERNS)
                                            + 48));
                    for (int i9 : iArr2) {
                        i7 += i9;
                    }
                }
                return i7;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.google.zxing.oned.UPCEANReader
    public final BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.EAN_13;
    }
}
