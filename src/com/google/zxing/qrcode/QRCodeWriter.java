package com.google.zxing.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;

import java.util.EnumMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class QRCodeWriter implements Writer {
    @Override // com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (barcodeFormat != BarcodeFormat.QR_CODE) {
            throw new IllegalArgumentException("Can only encode QR_CODE, but got " + barcodeFormat);
        }
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException(
                    "Requested dimensions are too small: " + i + 'x' + i2);
        }
        ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
        int i3 = 4;
        if (map != null) {
            EncodeHintType encodeHintType = EncodeHintType.ERROR_CORRECTION;
            EnumMap enumMap = (EnumMap) map;
            if (enumMap.containsKey(encodeHintType)) {
                errorCorrectionLevel =
                        ErrorCorrectionLevel.valueOf(enumMap.get(encodeHintType).toString());
            }
            EncodeHintType encodeHintType2 = EncodeHintType.MARGIN;
            if (enumMap.containsKey(encodeHintType2)) {
                i3 = Integer.parseInt(enumMap.get(encodeHintType2).toString());
            }
        }
        ByteMatrix byteMatrix = Encoder.encode(str, errorCorrectionLevel, map).matrix;
        if (byteMatrix == null) {
            throw new IllegalStateException();
        }
        int i4 = i3 * 2;
        int i5 = byteMatrix.width;
        int i6 = i5 + i4;
        int i7 = byteMatrix.height;
        int i8 = i4 + i7;
        int max = Math.max(i, i6);
        int max2 = Math.max(i2, i8);
        int min = Math.min(max / i6, max2 / i8);
        int i9 = (max - (i5 * min)) / 2;
        int i10 = (max2 - (i7 * min)) / 2;
        BitMatrix bitMatrix = new BitMatrix(max, max2);
        int i11 = 0;
        while (i11 < i7) {
            int i12 = 0;
            int i13 = i9;
            while (i12 < i5) {
                if (byteMatrix.get(i12, i11) == 1) {
                    bitMatrix.setRegion(i13, i10, min, min);
                }
                i12++;
                i13 += min;
            }
            i11++;
            i10 += min;
        }
        return bitMatrix;
    }
}
