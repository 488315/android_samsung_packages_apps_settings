package com.google.zxing.datamatrix.encoder;

import androidx.preference.Preference;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class HighLevelEncoder {
    public static int findMinimums(float[] fArr, int[] iArr, byte[] bArr) {
        int i = Preference.DEFAULT_ORDER;
        for (int i2 = 0; i2 < 6; i2++) {
            int ceil = (int) Math.ceil(fArr[i2]);
            iArr[i2] = ceil;
            if (i > ceil) {
                Arrays.fill(bArr, (byte) 0);
                i = ceil;
            }
            if (i == ceil) {
                bArr[i2] = (byte) (bArr[i2] + 1);
            }
        }
        return i;
    }

    public static void illegalCharacter(char c) {
        String hexString = Integer.toHexString(c);
        throw new IllegalArgumentException(
                "Illegal character: "
                        + c
                        + " (0x"
                        + ("0000".substring(0, 4 - hexString.length()) + hexString)
                        + ')');
    }

    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isExtendedASCII(char c) {
        return c >= 128 && c <= 255;
    }

    public static boolean isNativeC40(char c) {
        return c == ' ' || (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z');
    }

    public static boolean isNativeEDIFACT(char c) {
        return c >= ' ' && c <= '^';
    }

    public static boolean isNativeText(char c) {
        return c == ' ' || (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z');
    }

    public static boolean isNativeX12(char c) {
        return c == '\r'
                || c == '*'
                || c == '>'
                || c == ' '
                || (c >= '0' && c <= '9')
                || (c >= 'A' && c <= 'Z');
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x026a, code lost:

       r3 = 5;
    */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0185, code lost:

       r4 = 3;
    */
    /* JADX WARN: Removed duplicated region for block: B:22:0x029c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int lookAheadTest(int r19, int r20, java.lang.CharSequence r21) {
        /*
            Method dump skipped, instructions count: 701
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.zxing.datamatrix.encoder.HighLevelEncoder.lookAheadTest(int,"
                    + " int, java.lang.CharSequence):int");
    }
}
