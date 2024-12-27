package com.google.zxing.aztec;

import com.google.zxing.Reader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AztecReader implements Reader {
    /* JADX WARN: Removed duplicated region for block: B:21:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0038  */
    @Override // com.google.zxing.Reader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.Result decode(
            com.google.zxing.BinaryBitmap r10, java.util.Map r11) {
        /*
            r9 = this;
            com.google.zxing.aztec.detector.Detector r9 = new com.google.zxing.aztec.detector.Detector
            com.google.zxing.common.BitMatrix r10 = r10.getBlackMatrix()
            r9.<init>(r10)
            r10 = 0
            r0 = 0
            com.google.zxing.aztec.AztecDetectorResult r1 = r9.detect(r0)     // Catch: com.google.zxing.FormatException -> L2a com.google.zxing.NotFoundException -> L31
            com.google.zxing.ResultPoint[] r2 = r1.points     // Catch: com.google.zxing.FormatException -> L2a com.google.zxing.NotFoundException -> L31
            int r0 = r1.errorsCorrected     // Catch: com.google.zxing.FormatException -> L22 com.google.zxing.NotFoundException -> L24
            com.google.zxing.aztec.decoder.Decoder r3 = new com.google.zxing.aztec.decoder.Decoder     // Catch: com.google.zxing.FormatException -> L22 com.google.zxing.NotFoundException -> L24
            r3.<init>()     // Catch: com.google.zxing.FormatException -> L22 com.google.zxing.NotFoundException -> L24
            com.google.zxing.common.DecoderResult r1 = r3.decode(r1)     // Catch: com.google.zxing.FormatException -> L22 com.google.zxing.NotFoundException -> L24
            r3 = r2
            r2 = r0
            r0 = r10
            r10 = r1
            r1 = r0
            goto L36
        L22:
            r1 = move-exception
            goto L2c
        L24:
            r1 = move-exception
            goto L33
        L26:
            r2 = r10
            goto L2c
        L28:
            r2 = r10
            goto L33
        L2a:
            r1 = move-exception
            goto L26
        L2c:
            r3 = r2
            r2 = r0
            r0 = r1
            r1 = r10
            goto L36
        L31:
            r1 = move-exception
            goto L28
        L33:
            r3 = r2
            r2 = r0
            r0 = r10
        L36:
            if (r10 != 0) goto L4a
            r10 = 1
            com.google.zxing.aztec.AztecDetectorResult r9 = r9.detect(r10)     // Catch: java.lang.Throwable -> L4c
            com.google.zxing.ResultPoint[] r3 = r9.points     // Catch: java.lang.Throwable -> L4c
            int r2 = r9.errorsCorrected     // Catch: java.lang.Throwable -> L4c
            com.google.zxing.aztec.decoder.Decoder r10 = new com.google.zxing.aztec.decoder.Decoder     // Catch: java.lang.Throwable -> L4c
            r10.<init>()     // Catch: java.lang.Throwable -> L4c
            com.google.zxing.common.DecoderResult r10 = r10.decode(r9)     // Catch: java.lang.Throwable -> L4c
        L4a:
            r6 = r3
            goto L54
        L4c:
            r9 = move-exception
            if (r1 != 0) goto L53
            if (r0 == 0) goto L52
            throw r0
        L52:
            throw r9
        L53:
            throw r1
        L54:
            if (r11 == 0) goto L61
            com.google.zxing.DecodeHintType r9 = com.google.zxing.DecodeHintType.NEED_RESULT_POINT_CALLBACK
            android.util.ArrayMap r11 = (android.util.ArrayMap) r11
            java.lang.Object r9 = r11.get(r9)
            androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(r9)
        L61:
            com.google.zxing.Result r9 = new com.google.zxing.Result
            com.google.zxing.BarcodeFormat r7 = com.google.zxing.BarcodeFormat.AZTEC
            java.lang.System.currentTimeMillis()
            byte[] r5 = r10.rawBytes
            r8 = 0
            java.lang.String r4 = r10.text
            r3 = r9
            r3.<init>(r4, r5, r6, r7, r8)
            java.util.List r11 = r10.byteSegments
            if (r11 == 0) goto L7a
            com.google.zxing.ResultMetadataType r0 = com.google.zxing.ResultMetadataType.BYTE_SEGMENTS
            r9.putMetadata(r0, r11)
        L7a:
            java.lang.String r11 = r10.ecLevel
            if (r11 == 0) goto L83
            com.google.zxing.ResultMetadataType r0 = com.google.zxing.ResultMetadataType.ERROR_CORRECTION_LEVEL
            r9.putMetadata(r0, r11)
        L83:
            java.lang.Integer r11 = r10.errorsCorrected
            int r11 = r11.intValue()
            int r11 = r11 + r2
            com.google.zxing.ResultMetadataType r0 = com.google.zxing.ResultMetadataType.ERRORS_CORRECTED
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
            r9.putMetadata(r0, r11)
            com.google.zxing.ResultMetadataType r11 = com.google.zxing.ResultMetadataType.SYMBOLOGY_IDENTIFIER
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "]z"
            r0.<init>(r1)
            int r10 = r10.symbologyModifier
            r0.append(r10)
            java.lang.String r10 = r0.toString()
            r9.putMetadata(r11, r10)
            return r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.aztec.AztecReader.decode(com.google.zxing.BinaryBitmap,"
                    + " java.util.Map):com.google.zxing.Result");
    }

    @Override // com.google.zxing.Reader
    public final void reset() {}
}
