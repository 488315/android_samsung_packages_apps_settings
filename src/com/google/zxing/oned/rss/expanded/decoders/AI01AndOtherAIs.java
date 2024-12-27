package com.google.zxing.oned.rss.expanded.decoders;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AI01AndOtherAIs extends AI01decoder {
    @Override // com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder
    public final String parseInformation() {
        StringBuilder m =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m("(01)");
        int length = m.length();
        GeneralAppIdDecoder generalAppIdDecoder = this.generalDecoder;
        m.append(
                GeneralAppIdDecoder.extractNumericValueFromBitArray(
                        4, 4, generalAppIdDecoder.information));
        encodeCompressedGtinWithoutAI(m, 8, length);
        return generalAppIdDecoder.decodeAllCodes(48, m);
    }
}
