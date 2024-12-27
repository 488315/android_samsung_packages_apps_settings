package com.google.zxing.qrcode.encoder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BlockPair {
    public final byte[] dataBytes;
    public final byte[] errorCorrectionBytes;

    public BlockPair(byte[] bArr, byte[] bArr2) {
        this.dataBytes = bArr;
        this.errorCorrectionBytes = bArr2;
    }
}
