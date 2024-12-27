package com.google.zxing.oned.rss.expanded.decoders;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AI01weightDecoder extends AI01decoder {
    public abstract void addWeightCode(int i, StringBuilder sb);

    public abstract int checkWeight(int i);

    public final void encodeCompressedWeight(StringBuilder sb, int i, int i2) {
        int extractNumericValueFromBitArray =
                GeneralAppIdDecoder.extractNumericValueFromBitArray(
                        i, i2, this.generalDecoder.information);
        addWeightCode(extractNumericValueFromBitArray, sb);
        int checkWeight = checkWeight(extractNumericValueFromBitArray);
        int i3 = 100000;
        for (int i4 = 0; i4 < 5; i4++) {
            if (checkWeight / i3 == 0) {
                sb.append('0');
            }
            i3 /= 10;
        }
        sb.append(checkWeight);
    }
}
