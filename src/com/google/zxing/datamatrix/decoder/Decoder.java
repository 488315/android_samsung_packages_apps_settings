package com.google.zxing.datamatrix.decoder;

import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Decoder {
    public final ReedSolomonDecoder rsDecoder =
            new ReedSolomonDecoder(GenericGF.DATA_MATRIX_FIELD_256);

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0740, code lost:

       if (r3.length() <= 0) goto L462;
    */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x0742, code lost:

       r2.encodeCurrentBytesIfAny();
       r2.result.append((java.lang.CharSequence) r3);
    */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x074b, code lost:

       if (r15 == false) goto L477;
    */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x0751, code lost:

       if (r10.contains(0) != false) goto L476;
    */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x075c, code lost:

       if (r10.contains(4) == false) goto L469;
    */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0763, code lost:

       if (r10.contains(1) != false) goto L475;
    */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x076d, code lost:

       if (r10.contains(5) == false) goto L474;
    */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x0770, code lost:

       r15 = 4;
    */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x079e, code lost:

       r10 = r2.toString();
    */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x07a8, code lost:

       if (r4.isEmpty() == false) goto L493;
    */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x07aa, code lost:

       r11 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x07ae, code lost:

       r1 = new com.google.zxing.common.DecoderResult(r9, r10, r11, null, -1, -1, r15);
       r1.errorsCorrected = java.lang.Integer.valueOf(r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x07bb, code lost:

       return r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x07ad, code lost:

       r11 = r4;
    */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0772, code lost:

       r15 = 6;
    */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x0774, code lost:

       r15 = 5;
    */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x077a, code lost:

       if (r10.contains(0) != false) goto L489;
    */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0785, code lost:

       if (r10.contains(4) == false) goto L482;
    */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x078c, code lost:

       if (r10.contains(1) != false) goto L488;
    */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0796, code lost:

       if (r10.contains(5) == false) goto L487;
    */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0799, code lost:

       r15 = 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x079b, code lost:

       r15 = 3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x079d, code lost:

       r15 = 2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x03a8, code lost:

       throw com.google.zxing.FormatException.getFormatInstance();
    */
    /* JADX WARN: Removed duplicated region for block: B:130:0x03ee A[LOOP:15: B:115:0x035e->B:130:0x03ee, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x03ec A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.common.DecoderResult decode(
            com.google.zxing.common.BitMatrix r27) {
        /*
            Method dump skipped, instructions count: 2046
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.datamatrix.decoder.Decoder.decode(com.google.zxing.common.BitMatrix):com.google.zxing.common.DecoderResult");
    }
}
