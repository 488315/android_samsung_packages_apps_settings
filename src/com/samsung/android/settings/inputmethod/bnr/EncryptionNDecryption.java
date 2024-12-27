package com.samsung.android.settings.inputmethod.bnr;

import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class EncryptionNDecryption {
    public static OutputStream encryptStream(int i, OutputStream outputStream, String str) {
        SecretKeySpec generateSHA256SecretKey;
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] bArr = new byte[cipher.getBlockSize()];
        new SecureRandom().nextBytes(bArr);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        outputStream.write(bArr);
        if (i == 1) {
            byte[] bArr2 = new byte[16];
            new SecureRandom().nextBytes(bArr2);
            outputStream.write(bArr2);
            generateSHA256SecretKey = generatePBKDF2SecretKey(bArr2, str);
        } else {
            generateSHA256SecretKey = generateSHA256SecretKey(str);
        }
        cipher.init(1, generateSHA256SecretKey, ivParameterSpec);
        return new CipherOutputStream(outputStream, cipher);
    }

    public static SecretKeySpec generatePBKDF2SecretKey(byte[] bArr, String str) {
        return new SecretKeySpec(
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                        .generateSecret(new PBEKeySpec(str.toCharArray(), bArr, 1000, 256))
                        .getEncoded(),
                "AES");
    }

    public static SecretKeySpec generateSHA256SecretKey(String str) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(str.getBytes("UTF-8"));
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
        return new SecretKeySpec(bArr, "AES");
    }
}
