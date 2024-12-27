package com.google.zxing.qrcode.decoder;

import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Decoder {
    public final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

    /* JADX WARN: Removed duplicated region for block: B:13:0x0028 A[Catch: ChecksumException | FormatException -> 0x0050, TryCatch #2 {ChecksumException | FormatException -> 0x0050, blocks: (B:10:0x0011, B:11:0x0022, B:13:0x0028, B:14:0x002b, B:16:0x002f, B:18:0x0039, B:20:0x003f, B:25:0x0044), top: B:9:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0044 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.common.DecoderResult decode(
            com.google.zxing.common.BitMatrix r9, java.util.Map r10) {
        /*
            r8 = this;
            com.google.zxing.qrcode.decoder.BitMatrixParser r0 = new com.google.zxing.qrcode.decoder.BitMatrixParser
            r0.<init>(r9)
            r9 = 0
            com.google.zxing.common.DecoderResult r8 = r8.decode(r0, r10)     // Catch: com.google.zxing.ChecksumException -> Lb com.google.zxing.FormatException -> Lf
            return r8
        Lb:
            r1 = move-exception
            r2 = r1
            r1 = r9
            goto L11
        Lf:
            r1 = move-exception
            r2 = r9
        L11:
            r0.remask()     // Catch: java.lang.Throwable -> L50
            r0.parsedVersion = r9     // Catch: java.lang.Throwable -> L50
            r0.parsedFormatInfo = r9     // Catch: java.lang.Throwable -> L50
            r9 = 1
            r0.mirror = r9     // Catch: java.lang.Throwable -> L50
            r0.readVersion()     // Catch: java.lang.Throwable -> L50
            r0.readFormatInformation()     // Catch: java.lang.Throwable -> L50
            r9 = 0
        L22:
            com.google.zxing.common.BitMatrix r3 = r0.bitMatrix     // Catch: java.lang.Throwable -> L50
            int r4 = r3.width     // Catch: java.lang.Throwable -> L50
            if (r9 >= r4) goto L44
            int r4 = r9 + 1
            r5 = r4
        L2b:
            int r6 = r3.height     // Catch: java.lang.Throwable -> L50
            if (r5 >= r6) goto L42
            boolean r6 = r3.get(r9, r5)     // Catch: java.lang.Throwable -> L50
            boolean r7 = r3.get(r5, r9)     // Catch: java.lang.Throwable -> L50
            if (r6 == r7) goto L3f
            r3.flip(r5, r9)     // Catch: java.lang.Throwable -> L50
            r3.flip(r9, r5)     // Catch: java.lang.Throwable -> L50
        L3f:
            int r5 = r5 + 1
            goto L2b
        L42:
            r9 = r4
            goto L22
        L44:
            com.google.zxing.common.DecoderResult r8 = r8.decode(r0, r10)     // Catch: java.lang.Throwable -> L50
            com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData r9 = new com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData     // Catch: java.lang.Throwable -> L50
            r9.<init>()     // Catch: java.lang.Throwable -> L50
            r8.other = r9     // Catch: java.lang.Throwable -> L50
            return r8
        L50:
            if (r1 == 0) goto L53
            throw r1
        L53:
            throw r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.qrcode.decoder.Decoder.decode(com.google.zxing.common.BitMatrix,"
                    + " java.util.Map):com.google.zxing.common.DecoderResult");
    }

    /* JADX WARN: Removed duplicated region for block: B:182:0x044a A[LOOP:21: B:142:0x0209->B:182:0x044a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0408 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:223:0x03be  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x03c5 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.common.DecoderResult decode(
            com.google.zxing.qrcode.decoder.BitMatrixParser r34, java.util.Map r35) {
        /*
            Method dump skipped, instructions count: 1121
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.qrcode.decoder.Decoder.decode(com.google.zxing.qrcode.decoder.BitMatrixParser,"
                    + " java.util.Map):com.google.zxing.common.DecoderResult");
    }
}
