package com.google.zxing.pdf417.decoder.ec;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ModulusGF {
    public static final ModulusGF PDF417_GF = new ModulusGF();
    public final int[] expTable = new int[929];
    public final int[] logTable = new int[929];
    public final ModulusPoly one;
    public final ModulusPoly zero;

    public ModulusGF() {
        int i = 1;
        for (int i2 = 0; i2 < 929; i2++) {
            this.expTable[i2] = i;
            i = (i * 3) % 929;
        }
        for (int i3 = 0; i3 < 928; i3++) {
            this.logTable[this.expTable[i3]] = i3;
        }
        this.zero = new ModulusPoly(this, new int[] {0});
        this.one = new ModulusPoly(this, new int[] {1});
    }

    public final int add(int i, int i2) {
        return (i + i2) % 929;
    }

    public final int inverse(int i) {
        if (i == 0) {
            throw new ArithmeticException();
        }
        return this.expTable[928 - this.logTable[i]];
    }

    public final int multiply(int i, int i2) {
        if (i == 0 || i2 == 0) {
            return 0;
        }
        int[] iArr = this.logTable;
        return this.expTable[(iArr[i] + iArr[i2]) % 928];
    }
}
