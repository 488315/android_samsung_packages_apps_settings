package com.google.zxing.aztec;

import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AztecDetectorResult {
    public final BitMatrix bits;
    public final boolean compact;
    public final int errorsCorrected;
    public final int nbDatablocks;
    public final int nbLayers;
    public final ResultPoint[] points;

    public AztecDetectorResult(
            BitMatrix bitMatrix, ResultPoint[] resultPointArr, boolean z, int i, int i2, int i3) {
        this.bits = bitMatrix;
        this.points = resultPointArr;
        this.compact = z;
        this.nbDatablocks = i;
        this.nbLayers = i2;
        this.errorsCorrected = i3;
    }
}
