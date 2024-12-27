package com.google.zxing.qrcode.encoder;

import com.google.zxing.common.StringUtils;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Encoder {
    public static final int[] ALPHANUMERIC_TABLE = {
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41,
        42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15,
        16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1,
        -1, -1
    };
    public static final Charset DEFAULT_BYTE_MODE_ENCODING = StandardCharsets.ISO_8859_1;

    /* JADX WARN: Removed duplicated region for block: B:18:0x004b A[LOOP:0: B:11:0x0020->B:18:0x004b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void appendBytes(
            java.lang.String r8,
            com.google.zxing.qrcode.decoder.Mode r9,
            com.google.zxing.common.BitArray r10,
            java.nio.charset.Charset r11) {
        /*
            Method dump skipped, instructions count: 274
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.qrcode.encoder.Encoder.appendBytes(java.lang.String,"
                    + " com.google.zxing.qrcode.decoder.Mode, com.google.zxing.common.BitArray,"
                    + " java.nio.charset.Charset):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:186:0x0468, code lost:

       if ((r3 >= 0 && r3 < 8) != false) goto L216;
    */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x052e, code lost:

       if (r0 == false) goto L281;
    */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0513  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x0584  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x05a1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:352:0x0636  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:379:0x0233 A[LOOP:27: B:378:0x0231->B:379:0x0233, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:382:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:385:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x0654  */
    /* JADX WARN: Removed duplicated region for block: B:392:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:437:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x027b A[LOOP:2: B:60:0x0279->B:61:0x027b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.zxing.qrcode.encoder.QRCode encode(
            java.lang.String r24,
            com.google.zxing.qrcode.decoder.ErrorCorrectionLevel r25,
            java.util.Map r26) {
        /*
            Method dump skipped, instructions count: 1692
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.qrcode.encoder.Encoder.encode(java.lang.String,"
                    + " com.google.zxing.qrcode.decoder.ErrorCorrectionLevel,"
                    + " java.util.Map):com.google.zxing.qrcode.encoder.QRCode");
    }

    public static boolean isOnlyDoubleByteKanji(String str) {
        byte[] bytes = str.getBytes(StringUtils.SHIFT_JIS_CHARSET);
        int length = bytes.length;
        if (length % 2 != 0) {
            return false;
        }
        for (int i = 0; i < length; i += 2) {
            int i2 = bytes[i] & 255;
            if ((i2 < 129 || i2 > 159) && (i2 < 224 || i2 > 235)) {
                return false;
            }
        }
        return true;
    }

    public static boolean willFit(
            int i, Version version, ErrorCorrectionLevel errorCorrectionLevel) {
        int i2 = version.totalCodewords;
        Version.ECBlocks eCBlocks = version.ecBlocks[errorCorrectionLevel.ordinal()];
        int i3 = 0;
        for (Version.ECB ecb : eCBlocks.ecBlocks) {
            i3 += ecb.count;
        }
        return i2 - (i3 * eCBlocks.ecCodewordsPerBlock) >= (i + 7) / 8;
    }
}
