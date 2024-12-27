package com.google.zxing;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class InvertedLuminanceSource extends LuminanceSource {
    public final LuminanceSource delegate;

    public InvertedLuminanceSource(LuminanceSource luminanceSource) {
        super(luminanceSource.width, luminanceSource.height);
        this.delegate = luminanceSource;
    }

    @Override // com.google.zxing.LuminanceSource
    public final byte[] getMatrix() {
        byte[] matrix = this.delegate.getMatrix();
        int i = this.width * this.height;
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) (255 - (matrix[i2] & 255));
        }
        return bArr;
    }

    @Override // com.google.zxing.LuminanceSource
    public final byte[] getRow(int i, byte[] bArr) {
        byte[] row = this.delegate.getRow(i, bArr);
        for (int i2 = 0; i2 < this.width; i2++) {
            row[i2] = (byte) (255 - (row[i2] & 255));
        }
        return row;
    }

    @Override // com.google.zxing.LuminanceSource
    public final boolean isRotateSupported() {
        return this.delegate.isRotateSupported();
    }

    @Override // com.google.zxing.LuminanceSource
    public final LuminanceSource rotateCounterClockwise() {
        return new InvertedLuminanceSource(this.delegate.rotateCounterClockwise());
    }
}
