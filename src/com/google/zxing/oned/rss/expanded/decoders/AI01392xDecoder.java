package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AI01392xDecoder extends AI01decoder {
    @Override // com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder
    public final String parseInformation() {
        if (this.information.size < 48) {
            throw NotFoundException.getNotFoundInstance();
        }
        StringBuilder sb = new StringBuilder();
        encodeCompressedGtin(8, sb);
        GeneralAppIdDecoder generalAppIdDecoder = this.generalDecoder;
        int extractNumericValueFromBitArray =
                GeneralAppIdDecoder.extractNumericValueFromBitArray(
                        48, 2, generalAppIdDecoder.information);
        sb.append("(392");
        sb.append(extractNumericValueFromBitArray);
        sb.append(')');
        sb.append(generalAppIdDecoder.decodeGeneralPurposeField(50, null).newString);
        return sb.toString();
    }
}
