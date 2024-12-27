package com.google.zxing.pdf417.decoder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Codeword {
    public final int bucket;
    public final int endX;
    public int rowNumber = -1;
    public final int startX;
    public final int value;

    public Codeword(int i, int i2, int i3, int i4) {
        this.startX = i;
        this.endX = i2;
        this.bucket = i3;
        this.value = i4;
    }

    public final boolean isValidRowNumber(int i) {
        if (i != -1) {
            if (this.bucket == (i % 3) * 3) {
                return true;
            }
        }
        return false;
    }

    public final void setRowNumberAsRowIndicatorColumn() {
        this.rowNumber = (this.bucket / 3) + ((this.value / 30) * 3);
    }

    public final String toString() {
        return this.rowNumber + "|" + this.value;
    }
}
