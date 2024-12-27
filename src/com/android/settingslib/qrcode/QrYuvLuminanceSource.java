package com.android.settingslib.qrcode;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.google.zxing.LuminanceSource;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class QrYuvLuminanceSource extends LuminanceSource {
    public final int mHeight;
    public final int mWidth;
    public final byte[] mYuvData;

    public QrYuvLuminanceSource(byte[] bArr, int i, int i2) {
        super(i, i2);
        this.mWidth = i;
        this.mHeight = i2;
        this.mYuvData = bArr;
    }

    @Override // com.google.zxing.LuminanceSource
    public final byte[] getMatrix() {
        return this.mYuvData;
    }

    @Override // com.google.zxing.LuminanceSource
    public final byte[] getRow(int i, byte[] bArr) {
        if (i < 0 || i >= this.mHeight) {
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            i, "Requested row is outside the image: "));
        }
        int i2 = this.mWidth;
        if (bArr == null || bArr.length < i2) {
            bArr = new byte[i2];
        }
        System.arraycopy(this.mYuvData, i * i2, bArr, 0, i2);
        return bArr;
    }
}
