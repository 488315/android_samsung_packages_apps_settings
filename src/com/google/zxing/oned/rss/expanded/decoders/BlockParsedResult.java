package com.google.zxing.oned.rss.expanded.decoders;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BlockParsedResult {
    public final DecodedInformation decodedInformation;
    public final boolean finished;

    public BlockParsedResult() {
        this(null, false);
    }

    public BlockParsedResult(DecodedInformation decodedInformation, boolean z) {
        this.finished = z;
        this.decodedInformation = decodedInformation;
    }
}
