package com.google.zxing.datamatrix;

import com.google.zxing.Reader;
import com.google.zxing.ResultPoint;
import com.google.zxing.datamatrix.decoder.Decoder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DataMatrixReader implements Reader {
    public static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    public final Decoder decoder = new Decoder();

    /* JADX WARN: Code restructure failed: missing block: B:95:0x018f, code lost:

       if ((r3.transitionsBetween(r11, r15) + r3.transitionsBetween(r12, r15)) > (r3.transitionsBetween(r11, r7) + r3.transitionsBetween(r12, r7))) goto L55;
    */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x02d8  */
    @Override // com.google.zxing.Reader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.Result decode(
            com.google.zxing.BinaryBitmap r29, java.util.Map r30) {
        /*
            Method dump skipped, instructions count: 733
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.datamatrix.DataMatrixReader.decode(com.google.zxing.BinaryBitmap,"
                    + " java.util.Map):com.google.zxing.Result");
    }

    @Override // com.google.zxing.Reader
    public final void reset() {}
}
