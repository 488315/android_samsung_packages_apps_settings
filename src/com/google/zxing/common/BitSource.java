package com.google.zxing.common;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BitSource {
    public int bitOffset;
    public int byteOffset;
    public final byte[] bytes;

    public BitSource(byte[] bArr) {
        this.bytes = bArr;
    }

    public final int available() {
        return ((this.bytes.length - this.byteOffset) * 8) - this.bitOffset;
    }

    public final int readBits(int i) {
        if (i < 1 || i > 32 || i > available()) {
            throw new IllegalArgumentException(String.valueOf(i));
        }
        int i2 = this.bitOffset;
        int i3 = 0;
        byte[] bArr = this.bytes;
        if (i2 > 0) {
            int i4 = 8 - i2;
            int min = Math.min(i, i4);
            int i5 = i4 - min;
            int i6 = this.byteOffset;
            int i7 = (((255 >> (8 - min)) << i5) & bArr[i6]) >> i5;
            i -= min;
            int i8 = this.bitOffset + min;
            this.bitOffset = i8;
            if (i8 == 8) {
                this.bitOffset = 0;
                this.byteOffset = i6 + 1;
            }
            i3 = i7;
        }
        if (i <= 0) {
            return i3;
        }
        while (i >= 8) {
            int i9 = i3 << 8;
            int i10 = this.byteOffset;
            int i11 = i9 | (bArr[i10] & 255);
            this.byteOffset = i10 + 1;
            i -= 8;
            i3 = i11;
        }
        if (i <= 0) {
            return i3;
        }
        int i12 = 8 - i;
        int i13 = (i3 << i) | ((((255 >> i12) << i12) & bArr[this.byteOffset]) >> i12);
        this.bitOffset += i;
        return i13;
    }
}
