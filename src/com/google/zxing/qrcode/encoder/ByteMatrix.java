package com.google.zxing.qrcode.encoder;

import java.lang.reflect.Array;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ByteMatrix {
    public final byte[][] bytes;
    public final int height;
    public final int width;

    public ByteMatrix(int i, int i2) {
        this.bytes = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, i2, i);
        this.width = i;
        this.height = i2;
    }

    public final byte get(int i, int i2) {
        return this.bytes[i2][i];
    }

    public final void set(int i, int i2, int i3) {
        this.bytes[i2][i] = (byte) i3;
    }

    public final String toString() {
        int i = this.width;
        int i2 = this.height;
        StringBuilder sb = new StringBuilder((i * 2 * i2) + 2);
        for (int i3 = 0; i3 < i2; i3++) {
            byte[] bArr = this.bytes[i3];
            for (int i4 = 0; i4 < i; i4++) {
                byte b = bArr[i4];
                if (b == 0) {
                    sb.append(" 0");
                } else if (b != 1) {
                    sb.append("  ");
                } else {
                    sb.append(" 1");
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public final void set(int i, int i2, boolean z) {
        this.bytes[i2][i] = z ? (byte) 1 : (byte) 0;
    }
}
