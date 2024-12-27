package com.github.mikephil.charting.data;

import com.github.mikephil.charting.highlight.Range;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BarEntry extends Entry {
    public final float mNegativeSum;
    public final float mPositiveSum;
    public final Range[] mRanges;
    public final float[] mYVals;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public BarEntry(float r8, float[] r9) {
        /*
            r7 = this;
            int r0 = r9.length
            r1 = 0
            r2 = 0
            r3 = r1
            r4 = r2
        L5:
            if (r3 >= r0) goto Ld
            r5 = r9[r3]
            float r4 = r4 + r5
            int r3 = r3 + 1
            goto L5
        Ld:
            r7.<init>(r8, r4)
            r7.mYVals = r9
            int r8 = r9.length
            r0 = r1
            r3 = r2
            r4 = r3
        L16:
            if (r0 >= r8) goto L28
            r5 = r9[r0]
            int r6 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r6 > 0) goto L24
            float r5 = java.lang.Math.abs(r5)
            float r3 = r3 + r5
            goto L25
        L24:
            float r4 = r4 + r5
        L25:
            int r0 = r0 + 1
            goto L16
        L28:
            r7.mNegativeSum = r3
            r7.mPositiveSum = r4
            float[] r8 = r7.mYVals
            if (r8 == 0) goto L5d
            int r9 = r8.length
            if (r9 != 0) goto L34
            goto L5d
        L34:
            int r9 = r8.length
            com.github.mikephil.charting.highlight.Range[] r9 = new com.github.mikephil.charting.highlight.Range[r9]
            r7.mRanges = r9
            float r9 = -r3
            r0 = r2
        L3b:
            com.github.mikephil.charting.highlight.Range[] r3 = r7.mRanges
            int r4 = r3.length
            if (r1 >= r4) goto L5d
            r4 = r8[r1]
            int r5 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r5 >= 0) goto L51
            com.github.mikephil.charting.highlight.Range r5 = new com.github.mikephil.charting.highlight.Range
            float r4 = r9 - r4
            r5.<init>(r9, r4)
            r3[r1] = r5
            r9 = r4
            goto L5a
        L51:
            com.github.mikephil.charting.highlight.Range r5 = new com.github.mikephil.charting.highlight.Range
            float r4 = r4 + r0
            r5.<init>(r0, r4)
            r3[r1] = r5
            r0 = r4
        L5a:
            int r1 = r1 + 1
            goto L3b
        L5d:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.github.mikephil.charting.data.BarEntry.<init>(float,"
                    + " float[]):void");
    }

    @Override // com.github.mikephil.charting.data.BaseEntry
    public final float getY() {
        return this.y;
    }
}
