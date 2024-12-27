package com.google.zxing.oned;

import android.util.ArrayMap;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.HybridBinarizer;

import java.util.Arrays;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class OneDReader implements Reader {
    public static float patternMatchVariance(int[] iArr, int[] iArr2, float f) {
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            i += iArr[i3];
            i2 += iArr2[i3];
        }
        if (i < i2) {
            return Float.POSITIVE_INFINITY;
        }
        float f2 = i;
        float f3 = f2 / i2;
        float f4 = f * f3;
        float f5 = 0.0f;
        for (int i4 = 0; i4 < length; i4++) {
            float f6 = iArr2[i4] * f3;
            float f7 = iArr[i4];
            float f8 = f7 > f6 ? f7 - f6 : f6 - f7;
            if (f8 > f4) {
                return Float.POSITIVE_INFINITY;
            }
            f5 += f8;
        }
        return f5 / f2;
    }

    public static void recordPattern(int i, BitArray bitArray, int[] iArr) {
        int length = iArr.length;
        int i2 = 0;
        Arrays.fill(iArr, 0, length, 0);
        int i3 = bitArray.size;
        if (i >= i3) {
            throw NotFoundException.getNotFoundInstance();
        }
        boolean z = !bitArray.get(i);
        while (i < i3) {
            if (bitArray.get(i) != z) {
                iArr[i2] = iArr[i2] + 1;
            } else {
                i2++;
                if (i2 == length) {
                    break;
                }
                iArr[i2] = 1;
                z = !z;
            }
            i++;
        }
        if (i2 != length) {
            if (i2 != length - 1 || i != i3) {
                throw NotFoundException.getNotFoundInstance();
            }
        }
    }

    public static void recordPatternInReverse(int i, BitArray bitArray, int[] iArr) {
        int length = iArr.length;
        boolean z = bitArray.get(i);
        while (i > 0 && length >= 0) {
            i--;
            if (bitArray.get(i) != z) {
                length--;
                z = !z;
            }
        }
        if (length >= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        recordPattern(i + 1, bitArray, iArr);
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap, Map map) {
        try {
            return doDecode(binaryBitmap, map);
        } catch (NotFoundException e) {
            if (map != null) {
                if (((ArrayMap) map).containsKey(DecodeHintType.TRY_HARDER)
                        && binaryBitmap.binarizer.source.isRotateSupported()) {
                    LuminanceSource rotateCounterClockwise =
                            binaryBitmap.binarizer.source.rotateCounterClockwise();
                    Result doDecode =
                            this.doDecode(
                                    new BinaryBitmap(new HybridBinarizer(rotateCounterClockwise)),
                                    map);
                    Map map2 = doDecode.resultMetadata;
                    ResultMetadataType resultMetadataType = ResultMetadataType.ORIENTATION;
                    int i = 270;
                    if (map2 != null && map2.containsKey(resultMetadataType)) {
                        i = (((Integer) map2.get(resultMetadataType)).intValue() + 270) % 360;
                    }
                    doDecode.putMetadata(resultMetadataType, Integer.valueOf(i));
                    ResultPoint[] resultPointArr = doDecode.resultPoints;
                    if (resultPointArr != null) {
                        int i2 = rotateCounterClockwise.height;
                        for (int i3 = 0; i3 < resultPointArr.length; i3++) {
                            ResultPoint resultPoint = resultPointArr[i3];
                            resultPointArr[i3] =
                                    new ResultPoint((i2 - resultPoint.y) - 1.0f, resultPoint.x);
                        }
                    }
                    return doDecode;
                }
            }
            throw e;
        }
    }

    public abstract Result decodeRow(int i, BitArray bitArray, Map map);

    /* JADX WARN: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007c A[Catch: ReaderException -> 0x00bc, TRY_LEAVE, TryCatch #5 {ReaderException -> 0x00bc, blocks: (B:31:0x0076, B:33:0x007c), top: B:30:0x0076 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00c0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.Result doDecode(
            com.google.zxing.BinaryBitmap r19, java.util.Map r20) {
        /*
            Method dump skipped, instructions count: 221
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.oned.OneDReader.doDecode(com.google.zxing.BinaryBitmap,"
                    + " java.util.Map):com.google.zxing.Result");
    }

    @Override // com.google.zxing.Reader
    public void reset() {}
}
