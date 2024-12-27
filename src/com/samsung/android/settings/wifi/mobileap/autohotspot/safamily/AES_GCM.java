package com.samsung.android.settings.wifi.mobileap.autohotspot.safamily;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AES_GCM {
    public static byte[] generateHashKey(String str) {
        long j = 0;
        for (int i = 0; i < str.length(); i++) {
            j = ((31 * j) + str.charAt(i)) % 9223372036854775806L;
        }
        if (j < 0) {
            j *= -1;
        }
        return Long.valueOf(j).toString().getBytes(StandardCharsets.UTF_8);
    }

    public static SecretKeySpec setKey(String str) {
        try {
            return new SecretKeySpec(
                    Arrays.copyOf(
                            MessageDigest.getInstance("SHA-256").digest(str.getBytes("UTF-8")), 16),
                    "AES");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
