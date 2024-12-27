package com.google.zxing.oned.rss;

import com.google.zxing.ResultPoint;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FinderPattern {
    public final ResultPoint[] resultPoints;
    public final int[] startEnd;
    public final int value;

    public FinderPattern(int i, int i2, int i3, int i4, int[] iArr) {
        this.value = i;
        this.startEnd = iArr;
        float f = i4;
        this.resultPoints = new ResultPoint[] {new ResultPoint(i2, f), new ResultPoint(i3, f)};
    }

    public final boolean equals(Object obj) {
        return (obj instanceof FinderPattern) && this.value == ((FinderPattern) obj).value;
    }

    public final int hashCode() {
        return this.value;
    }
}
