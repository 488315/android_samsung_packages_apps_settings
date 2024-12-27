package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AI013x0xDecoder extends AI01weightDecoder {
    @Override // com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder
    public final String parseInformation() {
        if (this.information.size != 60) {
            throw NotFoundException.getNotFoundInstance();
        }
        StringBuilder sb = new StringBuilder();
        encodeCompressedGtin(5, sb);
        encodeCompressedWeight(sb, 45, 15);
        return sb.toString();
    }
}
