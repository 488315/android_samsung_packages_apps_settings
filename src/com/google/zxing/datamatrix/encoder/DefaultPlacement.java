package com.google.zxing.datamatrix.encoder;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DefaultPlacement {
    public final byte[] bits;
    public final CharSequence codewords;
    public final int numcols;
    public final int numrows;

    public DefaultPlacement(int i, int i2, CharSequence charSequence) {
        this.codewords = charSequence;
        this.numcols = i;
        this.numrows = i2;
        byte[] bArr = new byte[i * i2];
        this.bits = bArr;
        Arrays.fill(bArr, (byte) -1);
    }

    public final void module(int i, int i2, int i3, int i4) {
        if (i < 0) {
            int i5 = this.numrows;
            i += i5;
            i2 += 4 - ((i5 + 4) % 8);
        }
        int i6 = this.numcols;
        if (i2 < 0) {
            i2 += i6;
            i += 4 - ((i6 + 4) % 8);
        }
        this.bits[(i * i6) + i2] =
                (byte) ((this.codewords.charAt(i3) & (1 << (8 - i4))) == 0 ? 0 : 1);
    }

    public final boolean noBit(int i, int i2) {
        return this.bits[(i2 * this.numcols) + i] < 0;
    }

    public final void utah(int i, int i2, int i3) {
        int i4 = i - 2;
        int i5 = i2 - 2;
        module(i4, i5, i3, 1);
        int i6 = i2 - 1;
        module(i4, i6, i3, 2);
        int i7 = i - 1;
        module(i7, i5, i3, 3);
        module(i7, i6, i3, 4);
        module(i7, i2, i3, 5);
        module(i, i5, i3, 6);
        module(i, i6, i3, 7);
        module(i, i2, i3, 8);
    }
}
