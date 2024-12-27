package com.google.zxing.common.reedsolomon;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class GenericGF {
    public static final GenericGF AZTEC_DATA_6;
    public static final GenericGF AZTEC_DATA_8;
    public static final GenericGF AZTEC_PARAM;
    public static final GenericGF DATA_MATRIX_FIELD_256;
    public static final GenericGF MAXICODE_FIELD_64;
    public static final GenericGF QR_CODE_FIELD_256;
    public final int[] expTable;
    public final int generatorBase;
    public final int[] logTable;
    public final GenericGFPoly one;
    public final int primitive;
    public final int size;
    public final GenericGFPoly zero;
    public static final GenericGF AZTEC_DATA_12 = new GenericGF(4201, 4096, 1);
    public static final GenericGF AZTEC_DATA_10 = new GenericGF(1033, 1024, 1);

    static {
        GenericGF genericGF = new GenericGF(67, 64, 1);
        AZTEC_DATA_6 = genericGF;
        AZTEC_PARAM = new GenericGF(19, 16, 1);
        QR_CODE_FIELD_256 =
                new GenericGF(IKnoxCustomManager.Stub.TRANSACTION_startProKioskMode, 256, 0);
        GenericGF genericGF2 = new GenericGF(301, 256, 1);
        DATA_MATRIX_FIELD_256 = genericGF2;
        AZTEC_DATA_8 = genericGF2;
        MAXICODE_FIELD_64 = genericGF;
    }

    public GenericGF(int i, int i2, int i3) {
        this.primitive = i;
        this.size = i2;
        this.generatorBase = i3;
        this.expTable = new int[i2];
        this.logTable = new int[i2];
        int i4 = 1;
        for (int i5 = 0; i5 < i2; i5++) {
            this.expTable[i5] = i4;
            i4 *= 2;
            if (i4 >= i2) {
                i4 = (i4 ^ i) & (i2 - 1);
            }
        }
        for (int i6 = 0; i6 < i2 - 1; i6++) {
            this.logTable[this.expTable[i6]] = i6;
        }
        this.zero = new GenericGFPoly(this, new int[] {0});
        this.one = new GenericGFPoly(this, new int[] {1});
    }

    public final GenericGFPoly buildMonomial(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        if (i2 == 0) {
            return this.zero;
        }
        int[] iArr = new int[i + 1];
        iArr[0] = i2;
        return new GenericGFPoly(this, iArr);
    }

    public final int inverse(int i) {
        if (i == 0) {
            throw new ArithmeticException();
        }
        return this.expTable[(this.size - this.logTable[i]) - 1];
    }

    public final int multiply(int i, int i2) {
        if (i == 0 || i2 == 0) {
            return 0;
        }
        int[] iArr = this.logTable;
        return this.expTable[(iArr[i] + iArr[i2]) % (this.size - 1)];
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("GF(0x");
        sb.append(Integer.toHexString(this.primitive));
        sb.append(',');
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.size, ')');
    }
}
