package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AI013x0x1xDecoder extends AI01weightDecoder {
    public final String dateCode;
    public final String firstAIdigits;

    public AI013x0x1xDecoder(BitArray bitArray, String str, String str2) {
        super(bitArray);
        this.dateCode = str2;
        this.firstAIdigits = str;
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.AI01weightDecoder
    public final void addWeightCode(int i, StringBuilder sb) {
        sb.append('(');
        sb.append(this.firstAIdigits);
        sb.append(i / 100000);
        sb.append(')');
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.AI01weightDecoder
    public final int checkWeight(int i) {
        return i % 100000;
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder
    public final String parseInformation() {
        if (this.information.size != 84) {
            throw NotFoundException.getNotFoundInstance();
        }
        StringBuilder sb = new StringBuilder();
        encodeCompressedGtin(8, sb);
        encodeCompressedWeight(sb, 48, 20);
        int extractNumericValueFromBitArray =
                GeneralAppIdDecoder.extractNumericValueFromBitArray(
                        68, 16, this.generalDecoder.information);
        if (extractNumericValueFromBitArray != 38400) {
            sb.append('(');
            sb.append(this.dateCode);
            sb.append(')');
            int i = extractNumericValueFromBitArray % 32;
            int i2 = extractNumericValueFromBitArray / 32;
            int i3 = (i2 % 12) + 1;
            int i4 = i2 / 12;
            if (i4 / 10 == 0) {
                sb.append('0');
            }
            sb.append(i4);
            if (i3 / 10 == 0) {
                sb.append('0');
            }
            sb.append(i3);
            if (i / 10 == 0) {
                sb.append('0');
            }
            sb.append(i);
        }
        return sb.toString();
    }
}
