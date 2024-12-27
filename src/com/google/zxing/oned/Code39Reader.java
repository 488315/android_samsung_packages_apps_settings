package com.google.zxing.oned;

import androidx.preference.Preference;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.sec.ims.volte2.data.QuantumSecurityInfo;

import java.util.Arrays;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Code39Reader extends OneDReader {
    public static final int[] CHARACTER_ENCODINGS = {
        52,
        IKnoxCustomManager.Stub.TRANSACTION_setAsoc,
        97,
        QuantumSecurityInfo.QUANTUM_KEY_STATUS.KEY_STATUS_EXCEPTION,
        49,
        304,
        112,
        37,
        IKnoxCustomManager.Stub.TRANSACTION_startTcpDump,
        100,
        265,
        73,
        FileType.POTX,
        25,
        IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView,
        88,
        13,
        268,
        76,
        28,
        259,
        67,
        FileType.XLTX,
        19,
        IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastInternal,
        82,
        7,
        262,
        70,
        22,
        385,
        193,
        448,
        145,
        400,
        208,
        133,
        388,
        196,
        168,
        162,
        138,
        42
    };
    public final boolean usingCheckDigit;
    public final StringBuilder decodeRowResult = new StringBuilder(20);
    public final int[] counters = new int[9];

    public Code39Reader(boolean z) {
        this.usingCheckDigit = z;
    }

    public static int toNarrowWidePattern(int[] iArr) {
        int length = iArr.length;
        int i = 0;
        while (true) {
            int i2 = Preference.DEFAULT_ORDER;
            for (int i3 : iArr) {
                if (i3 < i2 && i3 > i) {
                    i2 = i3;
                }
            }
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            for (int i7 = 0; i7 < length; i7++) {
                int i8 = iArr[i7];
                if (i8 > i2) {
                    i5 |= 1 << ((length - 1) - i7);
                    i4++;
                    i6 += i8;
                }
            }
            if (i4 == 3) {
                for (int i9 = 0; i9 < length && i4 > 0; i9++) {
                    int i10 = iArr[i9];
                    if (i10 > i2) {
                        i4--;
                        if (i10 * 2 >= i6) {
                            return -1;
                        }
                    }
                }
                return i5;
            }
            if (i4 <= 3) {
                return -1;
            }
            i = i2;
        }
    }

    @Override // com.google.zxing.oned.OneDReader
    public final Result decodeRow(int i, BitArray bitArray, Map map) {
        char c;
        int[] iArr = this.counters;
        int i2 = 0;
        Arrays.fill(iArr, 0);
        StringBuilder sb = this.decodeRowResult;
        sb.setLength(0);
        int i3 = bitArray.size;
        int nextSet = bitArray.getNextSet(0);
        int length = iArr.length;
        boolean z = false;
        int i4 = 0;
        int i5 = nextSet;
        while (nextSet < i3) {
            if (bitArray.get(nextSet) != z) {
                iArr[i4] = iArr[i4] + 1;
            } else {
                if (i4 == length - 1) {
                    int i6 = 148;
                    if (toNarrowWidePattern(iArr) == 148
                            && bitArray.isRange(Math.max(0, i5 - ((nextSet - i5) / 2)), i5)) {
                        int nextSet2 = bitArray.getNextSet(new int[] {i5, nextSet}[1]);
                        int i7 = bitArray.size;
                        while (true) {
                            OneDReader.recordPattern(nextSet2, bitArray, iArr);
                            int narrowWidePattern = toNarrowWidePattern(iArr);
                            if (narrowWidePattern < 0) {
                                throw NotFoundException.getNotFoundInstance();
                            }
                            int i8 = i2;
                            while (true) {
                                if (i8 < 43) {
                                    if (CHARACTER_ENCODINGS[i8] == narrowWidePattern) {
                                        c =
                                                "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%"
                                                        .charAt(i8);
                                        break;
                                    }
                                    i8++;
                                } else {
                                    if (narrowWidePattern != i6) {
                                        throw NotFoundException.getNotFoundInstance();
                                    }
                                    c = '*';
                                }
                            }
                            sb.append(c);
                            int i9 = nextSet2;
                            for (int i10 : iArr) {
                                i9 += i10;
                            }
                            int nextSet3 = bitArray.getNextSet(i9);
                            if (c == '*') {
                                sb.setLength(sb.length() - 1);
                                int i11 = 0;
                                for (int i12 : iArr) {
                                    i11 += i12;
                                }
                                int i13 = (nextSet3 - nextSet2) - i11;
                                if (nextSet3 != i7 && i13 * 2 < i11) {
                                    throw NotFoundException.getNotFoundInstance();
                                }
                                if (this.usingCheckDigit) {
                                    int length2 = sb.length() - 1;
                                    int i14 = 0;
                                    for (int i15 = 0; i15 < length2; i15++) {
                                        i14 +=
                                                "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%"
                                                        .indexOf(sb.charAt(i15));
                                    }
                                    if (sb.charAt(length2)
                                            != "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%"
                                                    .charAt(i14 % 43)) {
                                        throw ChecksumException.getChecksumInstance();
                                    }
                                    sb.setLength(length2);
                                }
                                if (sb.length() == 0) {
                                    throw NotFoundException.getNotFoundInstance();
                                }
                                float f = i;
                                Result result =
                                        new Result(
                                                sb.toString(),
                                                null,
                                                new ResultPoint[] {
                                                    new ResultPoint((r5[1] + r5[0]) / 2.0f, f),
                                                    new ResultPoint((i11 / 2.0f) + nextSet2, f)
                                                },
                                                BarcodeFormat.CODE_39);
                                result.putMetadata(ResultMetadataType.SYMBOLOGY_IDENTIFIER, "]A0");
                                return result;
                            }
                            nextSet2 = nextSet3;
                            i2 = 0;
                            i6 = 148;
                        }
                    } else {
                        i5 += iArr[0] + iArr[1];
                        int i16 = i4 - 1;
                        System.arraycopy(iArr, 2, iArr, 0, i16);
                        iArr[i16] = 0;
                        iArr[i4] = 0;
                        i4--;
                    }
                } else {
                    i4++;
                }
                iArr[i4] = 1;
                z = !z;
            }
            nextSet++;
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
