package com.samsung.android.scloud.oem.lib.utils;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class HashUtil {
    public static final char[] DIGITS_LOWER = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    public static String getFileSHA256(File file) {
        int i;
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bArr = new byte[1024];
            while (true) {
                try {
                    int read = fileInputStream.read(bArr, 0, 1024);
                    if (read <= 0) {
                        break;
                    }
                    messageDigest.update(bArr, 0, read);
                } catch (Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    throw th;
                }
            }
            byte[] digest = messageDigest.digest();
            char[] cArr = DIGITS_LOWER;
            char[] cArr2 = new char[digest.length << 1];
            int i2 = 0;
            for (byte b : digest) {
                int i3 = i2 + 1;
                cArr2[i2] = cArr[(b & 240) >>> 4];
                i2 += 2;
                cArr2[i3] = cArr[b & 15];
            }
            String str = new String(cArr2);
            try {
                fileInputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return str;
        } catch (NoSuchAlgorithmException e3) {
            throw new IllegalArgumentException(e3);
        }
    }

    public static String getMD5HashString(InputStream inputStream) {
        int i;
        try {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = inputStream.read(bArr, 0, 1024);
                    if (read <= 0) {
                        break;
                    }
                    messageDigest.update(bArr, 0, read);
                }
                byte[] digest = messageDigest.digest();
                StringBuffer stringBuffer = new StringBuffer();
                for (byte b : digest) {
                    stringBuffer.append(Integer.toString((b & 255) + 256, 16).substring(1));
                }
                String stringBuffer2 = stringBuffer.toString();
                try {
                    inputStream.close();
                    return stringBuffer2;
                } catch (IOException e) {
                    e.printStackTrace();
                    return stringBuffer2;
                }
            } catch (IOException | NoSuchAlgorithmException e2) {
                e2.printStackTrace();
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                return ApnSettings.MVNO_NONE;
            }
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            throw th;
        }
    }
}
