package com.google.zxing.oned;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Code128Reader extends OneDReader {
    public static final int[][] CODE_PATTERNS;

    static {
        int[] iArr = new int[6];
        // fill-array-data instruction
        iArr[0] = 1;
        iArr[1] = 2;
        iArr[2] = 2;
        iArr[3] = 2;
        iArr[4] = 3;
        iArr[5] = 1;
        CODE_PATTERNS =
                new int[][] {
                    new int[] {2, 1, 2, 2, 2, 2},
                    new int[] {2, 2, 2, 1, 2, 2},
                    new int[] {2, 2, 2, 2, 2, 1},
                    new int[] {1, 2, 1, 2, 2, 3},
                    new int[] {1, 2, 1, 3, 2, 2},
                    new int[] {1, 3, 1, 2, 2, 2},
                    new int[] {1, 2, 2, 2, 1, 3},
                    new int[] {1, 2, 2, 3, 1, 2},
                    new int[] {1, 3, 2, 2, 1, 2},
                    new int[] {2, 2, 1, 2, 1, 3},
                    new int[] {2, 2, 1, 3, 1, 2},
                    new int[] {2, 3, 1, 2, 1, 2},
                    new int[] {1, 1, 2, 2, 3, 2},
                    new int[] {1, 2, 2, 1, 3, 2},
                    iArr,
                    new int[] {1, 1, 3, 2, 2, 2},
                    new int[] {1, 2, 3, 1, 2, 2},
                    new int[] {1, 2, 3, 2, 2, 1},
                    new int[] {2, 2, 3, 2, 1, 1},
                    new int[] {2, 2, 1, 1, 3, 2},
                    new int[] {2, 2, 1, 2, 3, 1},
                    new int[] {2, 1, 3, 2, 1, 2},
                    new int[] {2, 2, 3, 1, 1, 2},
                    new int[] {3, 1, 2, 1, 3, 1},
                    new int[] {3, 1, 1, 2, 2, 2},
                    new int[] {3, 2, 1, 1, 2, 2},
                    new int[] {3, 2, 1, 2, 2, 1},
                    new int[] {3, 1, 2, 2, 1, 2},
                    new int[] {3, 2, 2, 1, 1, 2},
                    new int[] {3, 2, 2, 2, 1, 1},
                    new int[] {2, 1, 2, 1, 2, 3},
                    new int[] {2, 1, 2, 3, 2, 1},
                    new int[] {2, 3, 2, 1, 2, 1},
                    new int[] {1, 1, 1, 3, 2, 3},
                    new int[] {1, 3, 1, 1, 2, 3},
                    new int[] {1, 3, 1, 3, 2, 1},
                    new int[] {1, 1, 2, 3, 1, 3},
                    new int[] {1, 3, 2, 1, 1, 3},
                    new int[] {1, 3, 2, 3, 1, 1},
                    new int[] {2, 1, 1, 3, 1, 3},
                    new int[] {2, 3, 1, 1, 1, 3},
                    new int[] {2, 3, 1, 3, 1, 1},
                    new int[] {1, 1, 2, 1, 3, 3},
                    new int[] {1, 1, 2, 3, 3, 1},
                    new int[] {1, 3, 2, 1, 3, 1},
                    new int[] {1, 1, 3, 1, 2, 3},
                    new int[] {1, 1, 3, 3, 2, 1},
                    new int[] {1, 3, 3, 1, 2, 1},
                    new int[] {3, 1, 3, 1, 2, 1},
                    new int[] {2, 1, 1, 3, 3, 1},
                    new int[] {2, 3, 1, 1, 3, 1},
                    new int[] {2, 1, 3, 1, 1, 3},
                    new int[] {2, 1, 3, 3, 1, 1},
                    new int[] {2, 1, 3, 1, 3, 1},
                    new int[] {3, 1, 1, 1, 2, 3},
                    new int[] {3, 1, 1, 3, 2, 1},
                    new int[] {3, 3, 1, 1, 2, 1},
                    new int[] {3, 1, 2, 1, 1, 3},
                    new int[] {3, 1, 2, 3, 1, 1},
                    new int[] {3, 3, 2, 1, 1, 1},
                    new int[] {3, 1, 4, 1, 1, 1},
                    new int[] {2, 2, 1, 4, 1, 1},
                    new int[] {4, 3, 1, 1, 1, 1},
                    new int[] {1, 1, 1, 2, 2, 4},
                    new int[] {1, 1, 1, 4, 2, 2},
                    new int[] {1, 2, 1, 1, 2, 4},
                    new int[] {1, 2, 1, 4, 2, 1},
                    new int[] {1, 4, 1, 1, 2, 2},
                    new int[] {1, 4, 1, 2, 2, 1},
                    new int[] {1, 1, 2, 2, 1, 4},
                    new int[] {1, 1, 2, 4, 1, 2},
                    new int[] {1, 2, 2, 1, 1, 4},
                    new int[] {1, 2, 2, 4, 1, 1},
                    new int[] {1, 4, 2, 1, 1, 2},
                    new int[] {1, 4, 2, 2, 1, 1},
                    new int[] {2, 4, 1, 2, 1, 1},
                    new int[] {2, 2, 1, 1, 1, 4},
                    new int[] {4, 1, 3, 1, 1, 1},
                    new int[] {2, 4, 1, 1, 1, 2},
                    new int[] {1, 3, 4, 1, 1, 1},
                    new int[] {1, 1, 1, 2, 4, 2},
                    new int[] {1, 2, 1, 1, 4, 2},
                    new int[] {1, 2, 1, 2, 4, 1},
                    new int[] {1, 1, 4, 2, 1, 2},
                    new int[] {1, 2, 4, 1, 1, 2},
                    new int[] {1, 2, 4, 2, 1, 1},
                    new int[] {4, 1, 1, 2, 1, 2},
                    new int[] {4, 2, 1, 1, 1, 2},
                    new int[] {4, 2, 1, 2, 1, 1},
                    new int[] {2, 1, 2, 1, 4, 1},
                    new int[] {2, 1, 4, 1, 2, 1},
                    new int[] {4, 1, 2, 1, 2, 1},
                    new int[] {1, 1, 1, 1, 4, 3},
                    new int[] {1, 1, 1, 3, 4, 1},
                    new int[] {1, 3, 1, 1, 4, 1},
                    new int[] {1, 1, 4, 1, 1, 3},
                    new int[] {1, 1, 4, 3, 1, 1},
                    new int[] {4, 1, 1, 1, 1, 3},
                    new int[] {4, 1, 1, 3, 1, 1},
                    new int[] {1, 1, 3, 1, 4, 1},
                    new int[] {1, 1, 4, 1, 3, 1},
                    new int[] {3, 1, 1, 1, 4, 1},
                    new int[] {4, 1, 1, 1, 3, 1},
                    new int[] {2, 1, 1, 4, 1, 2},
                    new int[] {2, 1, 1, 2, 1, 4},
                    new int[] {2, 1, 1, 2, 3, 2},
                    new int[] {2, 3, 3, 1, 1, 1, 2}
                };
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0166 A[PHI: r25
    0x0166: PHI (r25v9 int) = (r25v5 int), (r25v14 int) binds: [B:120:0x018f, B:85:0x0127] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x016c A[PHI: r25
    0x016c: PHI (r25v8 int) = (r25v5 int), (r25v14 int) binds: [B:120:0x018f, B:85:0x0127] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x012a A[FALL_THROUGH, PHI: r24 r25
    0x012a: PHI (r24v10 int) = (r24v1 int), (r24v6 int), (r24v6 int), (r24v6 int), (r24v1 int), (r24v12 int), (r24v12 int), (r24v12 int) binds: [B:120:0x018f, B:124:0x01a5, B:128:0x01b1, B:127:0x01ad, B:85:0x0127, B:91:0x013e, B:95:0x014a, B:94:0x0146] A[DONT_GENERATE, DONT_INLINE]
    0x012a: PHI (r25v13 int) = (r25v5 int), (r25v5 int), (r25v5 int), (r25v5 int), (r25v14 int), (r25v14 int), (r25v14 int), (r25v14 int) binds: [B:120:0x018f, B:124:0x01a5, B:128:0x01b1, B:127:0x01ad, B:85:0x0127, B:91:0x013e, B:95:0x014a, B:94:0x0146] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.zxing.oned.OneDReader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.Result decodeRow(
            int r28, com.google.zxing.common.BitArray r29, java.util.Map r30) {
        /*
            Method dump skipped, instructions count: 846
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.google.zxing.oned.Code128Reader.decodeRow(int,"
                    + " com.google.zxing.common.BitArray, java.util.Map):com.google.zxing.Result");
    }
}
