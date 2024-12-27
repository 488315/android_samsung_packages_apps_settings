package com.samsung.android.settings.uwb.labs.view.uwbtest;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class UwbUtil {
    public static final char[] HEXCHARS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    public static byte[] toByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] =
                    (byte)
                            (Character.digit(str.charAt(i + 1), 16)
                                    + (Character.digit(str.charAt(i), 16) << 4));
        }
        return bArr;
    }

    public static String toHexString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        if (bArr == null) {
            return null;
        }
        for (int i = 0; i != bArr.length; i++) {
            byte b = bArr[i];
            char[] cArr = HEXCHARS;
            stringBuffer.append(cArr[((b & 255) >> 4) & 15]);
            stringBuffer.append(cArr[b & 15]);
        }
        return stringBuffer.toString();
    }
}
