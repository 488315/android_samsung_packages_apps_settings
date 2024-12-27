package com.google.zxing.oned.rss.expanded.decoders;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AI013103decoder extends AI013x0xDecoder {
    @Override // com.google.zxing.oned.rss.expanded.decoders.AI01weightDecoder
    public final void addWeightCode(int i, StringBuilder sb) {
        sb.append("(3103)");
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.AI01weightDecoder
    public final int checkWeight(int i) {
        return i;
    }
}
