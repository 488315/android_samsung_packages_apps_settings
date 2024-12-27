package com.google.zxing.oned.rss.expanded.decoders;

import com.samsung.android.knox.container.EnterpriseContainerConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AI01320xDecoder extends AI013x0xDecoder {
    @Override // com.google.zxing.oned.rss.expanded.decoders.AI01weightDecoder
    public final void addWeightCode(int i, StringBuilder sb) {
        if (i < 10000) {
            sb.append("(3202)");
        } else {
            sb.append("(3203)");
        }
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.AI01weightDecoder
    public final int checkWeight(int i) {
        return i < 10000 ? i : i - EnterpriseContainerConstants.SYSTEM_SIGNED_APP;
    }
}
