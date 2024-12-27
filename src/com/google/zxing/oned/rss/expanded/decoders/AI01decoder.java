package com.google.zxing.oned.rss.expanded.decoders;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AI01decoder extends AbstractExpandedDecoder {
    public final void encodeCompressedGtin(int i, StringBuilder sb) {
        sb.append("(01)");
        int length = sb.length();
        sb.append('9');
        encodeCompressedGtinWithoutAI(sb, i, length);
    }

    public final void encodeCompressedGtinWithoutAI(StringBuilder sb, int i, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            int extractNumericValueFromBitArray =
                    GeneralAppIdDecoder.extractNumericValueFromBitArray(
                            (i3 * 10) + i, 10, this.generalDecoder.information);
            if (extractNumericValueFromBitArray / 100 == 0) {
                sb.append('0');
            }
            if (extractNumericValueFromBitArray / 10 == 0) {
                sb.append('0');
            }
            sb.append(extractNumericValueFromBitArray);
        }
        int i4 = 0;
        for (int i5 = 0; i5 < 13; i5++) {
            int charAt = sb.charAt(i5 + i2) - '0';
            if ((i5 & 1) == 0) {
                charAt *= 3;
            }
            i4 += charAt;
        }
        int i6 = 10 - (i4 % 10);
        sb.append(i6 != 10 ? i6 : 0);
    }
}
