package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AlignmentPattern extends ResultPoint {
    public final float estimatedModuleSize;

    public AlignmentPattern(float f, float f2, float f3) {
        super(f, f2);
        this.estimatedModuleSize = f3;
    }
}
