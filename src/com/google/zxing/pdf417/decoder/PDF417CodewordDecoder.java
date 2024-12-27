package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.PDF417Common;

import java.lang.reflect.Array;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class PDF417CodewordDecoder {
    public static final float[][] RATIOS_TABLE =
            (float[][]) Array.newInstance((Class<?>) Float.TYPE, 2787, 8);

    static {
        int i;
        for (int i2 = 0; i2 < 2787; i2++) {
            int i3 = PDF417Common.SYMBOL_TABLE[i2];
            int i4 = i3 & 1;
            int i5 = 0;
            while (i5 < 8) {
                float f = 0.0f;
                while (true) {
                    i = i3 & 1;
                    if (i == i4) {
                        f += 1.0f;
                        i3 >>= 1;
                    }
                }
                RATIOS_TABLE[i2][7 - i5] = f / 17.0f;
                i5++;
                i4 = i;
            }
        }
    }
}
