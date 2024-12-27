package com.google.zxing.datamatrix.encoder;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ASCIIEncoder implements Encoder {
    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public final void encode(EncoderContext encoderContext) {
        int i = encoderContext.pos;
        String str = encoderContext.msg;
        int length = str.length();
        int i2 = i;
        while (i2 < length && HighLevelEncoder.isDigit(str.charAt(i2))) {
            i2++;
        }
        if (i2 - i >= 2) {
            char charAt = str.charAt(encoderContext.pos);
            char charAt2 = str.charAt(encoderContext.pos + 1);
            if (HighLevelEncoder.isDigit(charAt) && HighLevelEncoder.isDigit(charAt2)) {
                encoderContext.writeCodeword(
                        (char) ((charAt2 - '0') + ((charAt - '0') * 10) + 130));
                encoderContext.pos += 2;
                return;
            } else {
                throw new IllegalArgumentException("not digits: " + charAt + charAt2);
            }
        }
        char currentChar = encoderContext.getCurrentChar();
        int lookAheadTest = HighLevelEncoder.lookAheadTest(encoderContext.pos, 0, str);
        if (lookAheadTest == 0) {
            if (!HighLevelEncoder.isExtendedASCII(currentChar)) {
                encoderContext.writeCodeword((char) (currentChar + 1));
                encoderContext.pos++;
                return;
            } else {
                encoderContext.writeCodeword((char) 235);
                encoderContext.writeCodeword((char) (currentChar - 127));
                encoderContext.pos++;
                return;
            }
        }
        if (lookAheadTest == 1) {
            encoderContext.writeCodeword((char) 230);
            encoderContext.newEncoding = 1;
            return;
        }
        if (lookAheadTest == 2) {
            encoderContext.writeCodeword((char) 239);
            encoderContext.newEncoding = 2;
            return;
        }
        if (lookAheadTest == 3) {
            encoderContext.writeCodeword((char) 238);
            encoderContext.newEncoding = 3;
        } else if (lookAheadTest == 4) {
            encoderContext.writeCodeword((char) 240);
            encoderContext.newEncoding = 4;
        } else {
            if (lookAheadTest != 5) {
                throw new IllegalStateException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                lookAheadTest, "Illegal mode: "));
            }
            encoderContext.writeCodeword((char) 231);
            encoderContext.newEncoding = 5;
        }
    }
}
