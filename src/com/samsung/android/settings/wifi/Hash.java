package com.samsung.android.settings.wifi;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import java.util.Locale;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Hash {
    public static final Checksum checksum;

    static {
        byte[] bytes = "2309851Cdgewlk3E".getBytes();
        bytesLEtoLong(0, bytes);
        bytesLEtoLong(8, bytes);
        bytesLEtoLong(0, bytes);
        bytesLEtoLong(8, bytes);
        checksum = new CRC32();
    }

    public static void bytesLEtoLong(int i, byte[] bArr) {
        if (bArr.length - i < 8) {
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            i, "Less then 8 bytes starting from offset:"));
        }
        for (int i2 = 0; i2 < 8; i2++) {
            byte b = bArr[i2 + i];
        }
    }

    public static String getDataCheckString(String str) {
        if ("00000000".equals(str)) {
            return "0000";
        }
        byte[] bytes = str.getBytes();
        Checksum checksum2 = checksum;
        ((CRC32) checksum2).update(bytes, 0, bytes.length);
        long value = ((CRC32) checksum2).getValue();
        ((CRC32) checksum2).reset();
        StringBuilder sb = new StringBuilder(18);
        for (int i = 7; i >= 0; i--) {
            sb.append(String.format("%02x", Byte.valueOf((byte) ((value >>> (i * 8)) & 255))));
        }
        String sb2 = sb.toString();
        Locale locale = Locale.ENGLISH;
        return sb2.toLowerCase(locale).substring(r7.length() - 4).toLowerCase(locale);
    }
}
