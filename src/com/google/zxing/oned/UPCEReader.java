package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class UPCEReader extends UPCEANReader {
    public static final int[] MIDDLE_END_PATTERN = {1, 1, 1, 1, 1, 1};
    public static final int[][] NUMSYS_AND_CHECK_DIGIT_PATTERNS = {
        new int[] {56, 52, 50, 49, 44, 38, 35, 42, 41, 37},
        new int[] {7, 11, 13, 14, 19, 25, 28, 21, 22, 26}
    };
    public final int[] decodeMiddleCounters = new int[4];

    public static String convertUPCEtoUPCA(String str) {
        char[] cArr = new char[6];
        str.getChars(1, 7, cArr, 0);
        StringBuilder sb = new StringBuilder(12);
        sb.append(str.charAt(0));
        char c = cArr[5];
        switch (c) {
            case '0':
            case '1':
            case '2':
                sb.append(cArr, 0, 2);
                sb.append(c);
                sb.append("0000");
                sb.append(cArr, 2, 3);
                break;
            case '3':
                sb.append(cArr, 0, 3);
                sb.append("00000");
                sb.append(cArr, 3, 2);
                break;
            case '4':
                sb.append(cArr, 0, 4);
                sb.append("00000");
                sb.append(cArr[4]);
                break;
            default:
                sb.append(cArr, 0, 5);
                sb.append("0000");
                sb.append(c);
                break;
        }
        if (str.length() >= 8) {
            sb.append(str.charAt(7));
        }
        return sb.toString();
    }

    @Override // com.google.zxing.oned.UPCEANReader
    public final boolean checkChecksum(String str) {
        return UPCEANReader.checkStandardUPCEANChecksum(convertUPCEtoUPCA(str));
    }

    @Override // com.google.zxing.oned.UPCEANReader
    public final int[] decodeEnd(int i, BitArray bitArray) {
        return UPCEANReader.findGuardPattern(bitArray, i, true, MIDDLE_END_PATTERN, new int[6]);
    }

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
        for (int i6 = 0; i6 <= 1; i6++) {
            for (int i7 = 0; i7 < 10; i7++) {
                if (i3 == NUMSYS_AND_CHECK_DIGIT_PATTERNS[i6][i7]) {
                    sb.insert(0, (char) (i6 + 48));
                    sb.append((char) (i7 + 48));
                    return i2;
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.google.zxing.oned.UPCEANReader
    public final BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.UPC_E;
    }
}
