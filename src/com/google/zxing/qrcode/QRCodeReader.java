package com.google.zxing.qrcode;

import com.google.zxing.Reader;
import com.google.zxing.ResultPoint;
import com.google.zxing.qrcode.decoder.Decoder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class QRCodeReader implements Reader {
    public static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    public final Decoder decoder = new Decoder();

    /* JADX WARN: Removed duplicated region for block: B:210:0x0326  */
    @Override // com.google.zxing.Reader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.Result decode(
            com.google.zxing.BinaryBitmap r41, java.util.Map r42) {
        /*
            Method dump skipped, instructions count: 1339
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.qrcode.QRCodeReader.decode(com.google.zxing.BinaryBitmap,"
                    + " java.util.Map):com.google.zxing.Result");
    }

    @Override // com.google.zxing.Reader
    public final void reset() {}
}
