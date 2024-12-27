package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BinaryShiftToken extends Token {
    public final int binaryShiftByteCount;
    public final int binaryShiftStart;

    public BinaryShiftToken(Token token, int i, int i2) {
        super(token);
        this.binaryShiftStart = i;
        this.binaryShiftByteCount = i2;
    }

    @Override // com.google.zxing.aztec.encoder.Token
    public final void appendTo(BitArray bitArray, byte[] bArr) {
        int i = 0;
        while (true) {
            int i2 = this.binaryShiftByteCount;
            if (i >= i2) {
                return;
            }
            if (i == 0 || (i == 31 && i2 <= 62)) {
                bitArray.appendBits(31, 5);
                if (i2 > 62) {
                    bitArray.appendBits(i2 - 31, 16);
                } else if (i == 0) {
                    bitArray.appendBits(Math.min(i2, 31), 5);
                } else {
                    bitArray.appendBits(i2 - 31, 5);
                }
            }
            bitArray.appendBits(bArr[this.binaryShiftStart + i], 8);
            i++;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("<");
        sb.append(this.binaryShiftStart);
        sb.append("::");
        sb.append((r1 + this.binaryShiftByteCount) - 1);
        sb.append('>');
        return sb.toString();
    }
}
