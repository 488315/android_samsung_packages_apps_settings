package com.samsung.android.settings.analyzestorage.external.bnr;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class EncryptionUtils {
    public static Cipher sCipher;
    public static byte[] sSalt;
    public static SecretKeySpec sSecretKey;
    public static String sSecurityPassword;

    public static File decrypt(int i, String str, String str2) {
        File file = new File(str);
        File file2 = new File(str2);
        try {
            if (!file2.exists()) {
                file2.createNewFile();
            }
            if (file.length() > 0) {
                InputStream decryptStream = decryptStream(new FileInputStream(file), i);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    try {
                        if (decryptStream != null) {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int read = decryptStream.read(bArr, 0, 1024);
                                if (read == -1) {
                                    break;
                                }
                                fileOutputStream.write(bArr, 0, read);
                            }
                        } else {
                            Log.e("EncryptionUtil", "decrypt() ] inputStream is null");
                        }
                        fileOutputStream.close();
                        if (decryptStream != null) {
                            decryptStream.close();
                        }
                    } finally {
                    }
                } finally {
                }
            } else {
                Log.d(
                        "EncryptionUtil",
                        "decrypt() ] Fail : Encrypted file is invalid. Path : "
                                + Log.getEncodedMsg(str));
            }
        } catch (Exception e) {
            Log.e("EncryptionUtil", "decrypt() ] Exception e : " + e);
        }
        return file2;
    }

    public static InputStream decryptStream(InputStream inputStream, int i) {
        byte[] bArr = new byte[sCipher.getBlockSize()];
        if (inputStream.read(bArr) == -1) {
            Log.e("EncryptionUtil", "decryptStream() ] Security Level read error");
            return null;
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        if (i == 1) {
            byte[] bArr2 = new byte[16];
            sSalt = bArr2;
            if (inputStream.read(bArr2) == -1) {
                Log.e("EncryptionUtil", "decryptStream() ] EncryptSalt read error");
                return null;
            }
            sSecretKey = generatePBKDF2SecretKey();
        } else {
            sSecretKey = generateSHA256SecretKey();
        }
        sCipher.init(2, sSecretKey, ivParameterSpec);
        return new CipherInputStream(inputStream, sCipher);
    }

    public static File encrypt(File file, String str, int i) {
        File file2 = new File(str);
        try {
            if (!file2.exists()) {
                file2.createNewFile();
            }
            if (file.length() > 0) {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    OutputStream encryptStream = encryptStream(new FileOutputStream(file2), i);
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = fileInputStream.read(bArr, 0, 1024);
                            if (read == -1) {
                                break;
                            }
                            encryptStream.write(bArr, 0, read);
                        }
                        encryptStream.close();
                        fileInputStream.close();
                    } finally {
                    }
                } finally {
                }
            } else {
                Log.d(
                        "EncryptionUtil",
                        "encrypt() ] Fail : Source file is invalid. Path : "
                                + Log.getEncodedMsg(file.getAbsolutePath()));
            }
        } catch (Exception e) {
            Log.e("EncryptionUtil", "Exception e : " + e);
        }
        return file2;
    }

    public static OutputStream encryptStream(OutputStream outputStream, int i) {
        byte[] bArr = new byte[sCipher.getBlockSize()];
        new SecureRandom().nextBytes(bArr);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        outputStream.write(bArr);
        if (i == 1) {
            byte[] bArr2 = new byte[16];
            new SecureRandom().nextBytes(bArr2);
            sSalt = bArr2;
            outputStream.write(bArr2);
            sSecretKey = generatePBKDF2SecretKey();
        } else {
            sSecretKey = generateSHA256SecretKey();
        }
        sCipher.init(1, sSecretKey, ivParameterSpec);
        return new CipherOutputStream(outputStream, sCipher);
    }

    public static SecretKeySpec generatePBKDF2SecretKey() {
        return new SecretKeySpec(
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                        .generateSecret(
                                new PBEKeySpec(sSecurityPassword.toCharArray(), sSalt, 1000, 256))
                        .getEncoded(),
                "AES");
    }

    public static SecretKeySpec generateSHA256SecretKey() {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(sSecurityPassword.getBytes(StandardCharsets.UTF_8));
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
        return new SecretKeySpec(bArr, "AES");
    }

    public static void streamCrypt(String str) {
        sSecurityPassword = str;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(sSecurityPassword.getBytes(StandardCharsets.UTF_8));
            byte[] bArr = new byte[16];
            System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
            sCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            sSecretKey = new SecretKeySpec(bArr, "AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            Log.e("EncryptionUtil", "streamCrypt() ] e : " + e);
        }
    }
}
