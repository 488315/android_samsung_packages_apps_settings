package com.google.zxing.pdf417;

import androidx.preference.Preference;

import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class PDF417Reader implements Reader {
    public static final Result[] EMPTY_RESULT_ARRAY = new Result[0];

    public static int getMaxWidth(ResultPoint resultPoint, ResultPoint resultPoint2) {
        if (resultPoint == null || resultPoint2 == null) {
            return 0;
        }
        return (int) Math.abs(resultPoint.x - resultPoint2.x);
    }

    public static int getMinWidth(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return (resultPoint == null || resultPoint2 == null)
                ? Preference.DEFAULT_ORDER
                : (int) Math.abs(resultPoint.x - resultPoint2.x);
    }

    /* JADX WARN: Code restructure failed: missing block: B:131:0x032c, code lost:

       if (r9 == 0) goto L171;
    */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x032e, code lost:

       r3 = r14.boundingBox.minX;
    */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x0333, code lost:

       r3 = r14.boundingBox.maxX;
    */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x06c4, code lost:

       r2 = new com.google.zxing.Result(r0.text, null, r1, com.google.zxing.BarcodeFormat.PDF_417);
       r2.putMetadata(com.google.zxing.ResultMetadataType.ERROR_CORRECTION_LEVEL, r0.ecLevel);
       r2.putMetadata(com.google.zxing.ResultMetadataType.ERRORS_CORRECTED, r0.errorsCorrected);
       r2.putMetadata(com.google.zxing.ResultMetadataType.ERASURES_CORRECTED, r0.erasures);
       r0 = (com.google.zxing.pdf417.PDF417ResultMetadata) r0.other;
    */
    /* JADX WARN: Code restructure failed: missing block: B:261:0x06e7, code lost:

       if (r0 == null) goto L427;
    */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x06e9, code lost:

       r2.putMetadata(com.google.zxing.ResultMetadataType.PDF417_EXTRA_METADATA, r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:264:0x06ee, code lost:

       r2.putMetadata(com.google.zxing.ResultMetadataType.ORIENTATION, java.lang.Integer.valueOf(r4.rotation));
       r2.putMetadata(com.google.zxing.ResultMetadataType.SYMBOLOGY_IDENTIFIER, "]L0");
       r12 = r37;
       r12.add(r2);
       r3 = r12;
       r8 = 4;
       r0 = r34;
       r2 = 1;
       r6 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:430:0x01f0, code lost:

       if (r12 != r11.rowCount) goto L76;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00d0 A[LOOP:2: B:19:0x00ce->B:20:0x00d0, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:433:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x02bb  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0339  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0345  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02c6  */
    @Override // com.google.zxing.Reader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.Result decode(
            com.google.zxing.BinaryBitmap r40, java.util.Map r41) {
        /*
            Method dump skipped, instructions count: 1890
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.pdf417.PDF417Reader.decode(com.google.zxing.BinaryBitmap,"
                    + " java.util.Map):com.google.zxing.Result");
    }

    @Override // com.google.zxing.Reader
    public final void reset() {}
}
