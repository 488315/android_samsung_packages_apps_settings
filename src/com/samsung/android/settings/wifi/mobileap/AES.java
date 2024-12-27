package com.samsung.android.settings.wifi.mobileap;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AES {
    public static byte[] key;
    public static SecretKeySpec secretKey;

    public static String decrypt(String str, String str2) {
        try {
            setKey(str2);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(2, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(str)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setKey(String str) {
        try {
            key = str.getBytes("UTF-8");
            byte[] digest = MessageDigest.getInstance("SHA-1").digest(key);
            key = digest;
            key = Arrays.copyOf(digest, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        }
    }
}
