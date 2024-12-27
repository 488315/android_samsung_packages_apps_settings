package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;

import java.util.EnumMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class UPCEANExtensionSupport {
    public static final int[] EXTENSION_START_PATTERN = {1, 1, 2};
    public final UPCEANExtension2Support twoSupport = new UPCEANExtension2Support();
    public final UPCEANExtension5Support fiveSupport = new UPCEANExtension5Support();

    public final Result decodeRow(int i, int i2, BitArray bitArray) {
        EnumMap enumMap;
        int[] findGuardPattern =
                UPCEANReader.findGuardPattern(
                        bitArray, i2, false, EXTENSION_START_PATTERN, new int[3]);
        try {
            return this.fiveSupport.decodeRow(i, bitArray, findGuardPattern);
        } catch (ReaderException unused) {
            UPCEANExtension2Support uPCEANExtension2Support = this.twoSupport;
            StringBuilder sb = uPCEANExtension2Support.decodeRowStringBuffer;
            sb.setLength(0);
            int[] iArr = uPCEANExtension2Support.decodeMiddleCounters;
            iArr[0] = 0;
            iArr[1] = 0;
            iArr[2] = 0;
            iArr[3] = 0;
            int i3 = bitArray.size;
            int i4 = findGuardPattern[1];
            int i5 = 0;
            for (int i6 = 0; i6 < 2 && i4 < i3; i6++) {
                int decodeDigit =
                        UPCEANReader.decodeDigit(bitArray, iArr, i4, UPCEANReader.L_AND_G_PATTERNS);
                sb.append((char) ((decodeDigit % 10) + 48));
                for (int i7 : iArr) {
                    i4 += i7;
                }
                if (decodeDigit >= 10) {
                    i5 |= 1 << (1 - i6);
                }
                if (i6 != 1) {
                    i4 = bitArray.getNextUnset(bitArray.getNextSet(i4));
                }
            }
            if (sb.length() != 2) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (Integer.parseInt(sb.toString()) % 4 != i5) {
                throw NotFoundException.getNotFoundInstance();
            }
            String sb2 = sb.toString();
            if (sb2.length() != 2) {
                enumMap = null;
            } else {
                enumMap = new EnumMap(ResultMetadataType.class);
                enumMap.put(
                        (EnumMap) ResultMetadataType.ISSUE_NUMBER,
                        (ResultMetadataType) Integer.valueOf(sb2));
            }
            float f = i;
            Result result =
                    new Result(
                            sb2,
                            null,
                            new ResultPoint[] {
                                new ResultPoint(
                                        (findGuardPattern[0] + findGuardPattern[1]) / 2.0f, f),
                                new ResultPoint(i4, f)
                            },
                            BarcodeFormat.UPC_EAN_EXTENSION);
            if (enumMap != null) {
                result.putAllMetadata(enumMap);
            }
            return result;
        }
    }
}
