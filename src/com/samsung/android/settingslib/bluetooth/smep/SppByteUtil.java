package com.samsung.android.settingslib.bluetooth.smep;

import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SppByteUtil {
    public static String byteArrayToHex(byte[] bArr) {
        if (bArr == null) {
            return ApnSettings.MVNO_NONE;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x ", Integer.valueOf(b & 255)));
        }
        return sb.toString();
    }

    public static String byteBufferToString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 2; i++) {
            stringBuffer.append(String.format("%02x", Byte.valueOf(bArr[i])));
        }
        return stringBuffer.toString();
    }

    public static byte[] intToBytes(int i) {
        return new byte[] {(byte) i, (byte) (i >> 8)};
    }

    public static byte[] makeTlv(int i, byte[] bArr) {
        if (!SmepTag.isValidConstantKey(i) || bArr == null || bArr.length == 0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(intToBytes(i));
            byteArrayOutputStream.write((byte) bArr.length);
            byteArrayOutputStream.write(bArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
