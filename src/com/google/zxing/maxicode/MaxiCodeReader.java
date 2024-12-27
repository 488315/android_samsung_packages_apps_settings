package com.google.zxing.maxicode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.maxicode.decoder.BitMatrixParser;
import com.google.zxing.maxicode.decoder.DecodedBitStreamParser;
import com.google.zxing.maxicode.decoder.Decoder;

import java.text.DecimalFormat;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MaxiCodeReader implements Reader {
    public static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    public final Decoder decoder = new Decoder();

    @Override // com.google.zxing.Reader
    public final Result decode(BinaryBitmap binaryBitmap, Map map) {
        int correctErrors;
        byte[] bArr;
        String sb;
        BitMatrix blackMatrix = binaryBitmap.getBlackMatrix();
        int i = blackMatrix.width;
        int i2 = blackMatrix.height;
        int i3 = -1;
        int i4 = -1;
        for (int i5 = 0; i5 < blackMatrix.height; i5++) {
            int i6 = 0;
            while (true) {
                int i7 = blackMatrix.rowSize;
                if (i6 < i7) {
                    int i8 = blackMatrix.bits[(i7 * i5) + i6];
                    if (i8 != 0) {
                        if (i5 < i2) {
                            i2 = i5;
                        }
                        if (i5 > i4) {
                            i4 = i5;
                        }
                        int i9 = i6 * 32;
                        if (i9 < i) {
                            int i10 = 0;
                            while ((i8 << (31 - i10)) == 0) {
                                i10++;
                            }
                            int i11 = i10 + i9;
                            if (i11 < i) {
                                i = i11;
                            }
                        }
                        if (i9 + 31 > i3) {
                            int i12 = 31;
                            while ((i8 >>> i12) == 0) {
                                i12--;
                            }
                            int i13 = i9 + i12;
                            if (i13 > i3) {
                                i3 = i13;
                            }
                        }
                    }
                    i6++;
                }
            }
        }
        int[] iArr = (i3 < i || i4 < i2) ? null : new int[] {i, i2, (i3 - i) + 1, (i4 - i2) + 1};
        if (iArr == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i14 = iArr[0];
        int i15 = iArr[1];
        int i16 = iArr[2];
        int i17 = iArr[3];
        int[] iArr2 = new int[33];
        for (int i18 = 0; i18 < 33; i18++) {
            int min = Math.min((((i17 / 2) + (i18 * i17)) / 33) + i15, i17 - 1);
            for (int i19 = 0; i19 < 30; i19++) {
                if (blackMatrix.get(
                        Math.min(
                                        ((((i18 & 1) * i16) / 2) + ((i16 / 2) + (i19 * i16))) / 30,
                                        i16 - 1)
                                + i14,
                        min)) {
                    int i20 = (i19 / 32) + i18;
                    iArr2[i20] = iArr2[i20] | (1 << (i19 & 31));
                }
            }
        }
        Decoder decoder = this.decoder;
        decoder.getClass();
        byte[] bArr2 = new byte[144];
        for (int i21 = 0; i21 < 33; i21++) {
            int[] iArr3 = BitMatrixParser.BITNR[i21];
            for (int i22 = 0; i22 < 30; i22++) {
                int i23 = iArr3[i22];
                if (i23 >= 0 && ((iArr2[(i22 / 32) + i21] >>> (i22 & 31)) & 1) != 0) {
                    int i24 = i23 / 6;
                    bArr2[i24] = (byte) (((byte) (1 << (5 - (i23 % 6)))) | bArr2[i24]);
                }
            }
        }
        int correctErrors2 = decoder.correctErrors(bArr2, 0, 10, 10, 0);
        int i25 = bArr2[0] & 15;
        if (i25 == 2 || i25 == 3 || i25 == 4) {
            correctErrors =
                    correctErrors2
                            + decoder.correctErrors(bArr2, 20, 84, 40, 1)
                            + decoder.correctErrors(bArr2, 20, 84, 40, 2);
            bArr = new byte[94];
        } else {
            if (i25 != 5) {
                throw FormatException.getFormatInstance();
            }
            correctErrors =
                    correctErrors2
                            + decoder.correctErrors(bArr2, 20, 68, 56, 1)
                            + decoder.correctErrors(bArr2, 20, 68, 56, 2);
            bArr = new byte[78];
        }
        System.arraycopy(bArr2, 0, bArr, 0, 10);
        System.arraycopy(bArr2, 20, bArr, 10, bArr.length - 10);
        StringBuilder sb2 = new StringBuilder(144);
        if (i25 == 2 || i25 == 3) {
            if (i25 == 2) {
                int i26 =
                        DecodedBitStreamParser.getInt(
                                bArr, DecodedBitStreamParser.POSTCODE_2_BYTES);
                int i27 =
                        DecodedBitStreamParser.getInt(
                                bArr, DecodedBitStreamParser.POSTCODE_2_LENGTH_BYTES);
                if (i27 > 10) {
                    throw FormatException.getFormatInstance();
                }
                sb = new DecimalFormat("0000000000".substring(0, i27)).format(i26);
            } else {
                StringBuilder sb3 = new StringBuilder(6);
                byte[][] bArr3 = DecodedBitStreamParser.POSTCODE_3_BYTES;
                for (int i28 = 0; i28 < 6; i28++) {
                    sb3.append(
                            DecodedBitStreamParser.SETS[0].charAt(
                                    DecodedBitStreamParser.getInt(bArr, bArr3[i28])));
                }
                sb = sb3.toString();
            }
            DecimalFormat decimalFormat = new DecimalFormat("000");
            String format =
                    decimalFormat.format(
                            DecodedBitStreamParser.getInt(
                                    bArr, DecodedBitStreamParser.COUNTRY_BYTES));
            String format2 =
                    decimalFormat.format(
                            DecodedBitStreamParser.getInt(
                                    bArr, DecodedBitStreamParser.SERVICE_CLASS_BYTES));
            sb2.append(DecodedBitStreamParser.getMessage(bArr, 10, 84));
            if (sb2.toString().startsWith("[)>\u001e01\u001d")) {
                sb2.insert(9, sb + (char) 29 + format + (char) 29 + format2 + (char) 29);
            } else {
                sb2.insert(0, sb + (char) 29 + format + (char) 29 + format2 + (char) 29);
            }
        } else if (i25 == 4) {
            sb2.append(DecodedBitStreamParser.getMessage(bArr, 1, 93));
        } else if (i25 == 5) {
            sb2.append(DecodedBitStreamParser.getMessage(bArr, 1, 77));
        }
        String sb4 = sb2.toString();
        String valueOf = String.valueOf(i25);
        Integer valueOf2 = Integer.valueOf(correctErrors);
        Result result = new Result(sb4, bArr, NO_POINTS, BarcodeFormat.MAXICODE);
        result.putMetadata(ResultMetadataType.ERRORS_CORRECTED, valueOf2);
        if (valueOf != null) {
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, valueOf);
        }
        return result;
    }

    @Override // com.google.zxing.Reader
    public final void reset() {}
}
