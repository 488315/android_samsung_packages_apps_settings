package com.google.zxing.datamatrix.encoder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class TextEncoder extends C40Encoder {
    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    public final int encodeChar(char c, StringBuilder sb) {
        if (c == ' ') {
            sb.append((char) 3);
            return 1;
        }
        if (c >= '0' && c <= '9') {
            sb.append((char) (c - ','));
            return 1;
        }
        if (c >= 'a' && c <= 'z') {
            sb.append((char) (c - 'S'));
            return 1;
        }
        if (c < ' ') {
            sb.append((char) 0);
            sb.append(c);
            return 2;
        }
        if (c <= '/') {
            sb.append((char) 1);
            sb.append((char) (c - '!'));
            return 2;
        }
        if (c <= '@') {
            sb.append((char) 1);
            sb.append((char) (c - '+'));
            return 2;
        }
        if (c >= '[' && c <= '_') {
            sb.append((char) 1);
            sb.append((char) (c - 'E'));
            return 2;
        }
        if (c == '`') {
            sb.append((char) 2);
            sb.append((char) 0);
            return 2;
        }
        if (c <= 'Z') {
            sb.append((char) 2);
            sb.append((char) (c - '@'));
            return 2;
        }
        if (c > 127) {
            sb.append("\u0001\u001e");
            return encodeChar((char) (c - 128), sb) + 2;
        }
        sb.append((char) 2);
        sb.append((char) (c - '`'));
        return 2;
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    public final int getEncodingMode() {
        return 2;
    }
}
