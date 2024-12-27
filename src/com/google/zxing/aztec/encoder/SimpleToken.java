package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SimpleToken extends Token {
    public final short bitCount;
    public final short value;

    public SimpleToken(Token token, int i, int i2) {
        super(token);
        this.value = (short) i;
        this.bitCount = (short) i2;
    }

    @Override // com.google.zxing.aztec.encoder.Token
    public final void appendTo(BitArray bitArray, byte[] bArr) {
        bitArray.appendBits(this.value, this.bitCount);
    }

    public final String toString() {
        short s = this.bitCount;
        return "<"
                + Integer.toBinaryString((this.value & ((1 << s) - 1)) | (1 << s) | (1 << s))
                        .substring(1)
                + '>';
    }
}
