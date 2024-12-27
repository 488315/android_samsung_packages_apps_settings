package com.google.zxing.pdf417.detector;

import com.google.zxing.common.BitMatrix;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class PDF417DetectorResult {
    public final BitMatrix bits;
    public final List points;
    public final int rotation;

    public PDF417DetectorResult(BitMatrix bitMatrix, List list, int i) {
        this.bits = bitMatrix;
        this.points = list;
        this.rotation = i;
    }
}
