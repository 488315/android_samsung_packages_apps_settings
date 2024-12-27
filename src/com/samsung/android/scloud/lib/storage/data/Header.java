package com.samsung.android.scloud.lib.storage.data;

import com.samsung.android.scloud.oem.lib.LOG;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class Header {
    public final String localId;
    public String recordId;
    public final String statue;
    public long timeStamp;

    public Header(long j, String str, String str2, String str3) {
        this.recordId = str;
        this.localId = str2;
        this.timeStamp = j;
        this.statue = str3;
    }

    public static String makeRecordId(String str) {
        int i;
        try {
            ByteArrayInputStream byteArrayInputStream =
                    new ByteArrayInputStream(str.getBytes("UTF-8"));
            try {
                byte[] bArr = new byte[8192];
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                while (true) {
                    int read = byteArrayInputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    messageDigest.update(bArr, 0, read);
                }
                byte[] digest = messageDigest.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b : digest) {
                    int i2 = b & 255;
                    if (i2 <= 15) {
                        sb.append('0');
                    }
                    sb.append(Integer.toHexString(i2));
                }
                String upperCase = sb.toString().toUpperCase(Locale.ENGLISH);
                byteArrayInputStream.close();
                return upperCase;
            } catch (Throwable th) {
                try {
                    byteArrayInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            LOG.e("HashUtil", "cannot make hash : " + e.getMessage(), null);
            return null;
        }
    }
}
