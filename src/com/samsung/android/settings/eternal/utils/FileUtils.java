package com.samsung.android.settings.eternal.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import com.samsung.android.settings.eternal.log.EternalFileLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.zip.GZIPInputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FileUtils {
    public Cipher mCipher;
    public byte[] mSalt;
    public int mSecurityLevel;
    public String mSessionKey;

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0091, code lost:

       return new java.io.File(r10.getPath());
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0085, code lost:

       if (r3.delete() == false) goto L47;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.io.File compressToGzip(android.content.Context r9, java.io.File r10) {
        /*
            java.lang.String r0 = "Compress to Gzip Succeeded"
            java.lang.String r1 = "Eternal/FileUtils"
            java.lang.String r2 = "Compress to Gzip started"
            android.util.Log.d(r1, r2)
            java.lang.String r2 = r10.getPath()
            java.lang.String r3 = "DTD_temp.xml"
            java.io.File r3 = rename(r9, r10, r3)
            if (r3 != 0) goto L1b
            java.lang.String r9 = "Can't change file name"
            android.util.Log.d(r1, r9)
            return r10
        L1b:
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L56 java.lang.Throwable -> L77
            java.lang.String r5 = r3.getPath()     // Catch: java.lang.Throwable -> L56 java.lang.Throwable -> L77
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L56 java.lang.Throwable -> L77
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L58
            r5.<init>(r2)     // Catch: java.lang.Throwable -> L58
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch: java.lang.Throwable -> L5a
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L5a
            r6 = 4096(0x1000, float:5.74E-42)
            byte[] r6 = new byte[r6]     // Catch: java.lang.Throwable -> L41
            int r7 = r4.read(r6)     // Catch: java.lang.Throwable -> L41
        L36:
            if (r7 <= 0) goto L43
            r8 = 0
            r2.write(r6, r8, r7)     // Catch: java.lang.Throwable -> L41
            int r7 = r4.read(r6)     // Catch: java.lang.Throwable -> L41
            goto L36
        L41:
            r6 = move-exception
            goto L5c
        L43:
            r2.close()     // Catch: java.lang.Throwable -> L5a
            r5.close()     // Catch: java.lang.Throwable -> L58
            r4.close()     // Catch: java.lang.Throwable -> L56 java.lang.Throwable -> L77 java.lang.Throwable -> L77
            boolean r9 = r3.delete()
            if (r9 == 0) goto L88
        L52:
            android.util.Log.d(r1, r0)
            goto L88
        L56:
            r9 = move-exception
            goto L92
        L58:
            r2 = move-exception
            goto L6e
        L5a:
            r2 = move-exception
            goto L65
        L5c:
            r2.close()     // Catch: java.lang.Throwable -> L60
            goto L64
        L60:
            r2 = move-exception
            r6.addSuppressed(r2)     // Catch: java.lang.Throwable -> L5a
        L64:
            throw r6     // Catch: java.lang.Throwable -> L5a
        L65:
            r5.close()     // Catch: java.lang.Throwable -> L69
            goto L6d
        L69:
            r5 = move-exception
            r2.addSuppressed(r5)     // Catch: java.lang.Throwable -> L58
        L6d:
            throw r2     // Catch: java.lang.Throwable -> L58
        L6e:
            r4.close()     // Catch: java.lang.Throwable -> L72
            goto L76
        L72:
            r4 = move-exception
            r2.addSuppressed(r4)     // Catch: java.lang.Throwable -> L56 java.lang.Throwable -> L77 java.lang.Throwable -> L77
        L76:
            throw r2     // Catch: java.lang.Throwable -> L56 java.lang.Throwable -> L77 java.lang.Throwable -> L77
        L77:
            java.lang.String r2 = "SettingsBackup.xml"
            rename(r9, r3, r2)     // Catch: java.lang.Throwable -> L56
            java.lang.String r9 = "Compress to Gzip failed"
            android.util.Log.d(r1, r9)     // Catch: java.lang.Throwable -> L56
            boolean r9 = r3.delete()
            if (r9 == 0) goto L88
            goto L52
        L88:
            java.io.File r9 = new java.io.File
            java.lang.String r10 = r10.getPath()
            r9.<init>(r10)
            return r9
        L92:
            boolean r10 = r3.delete()
            if (r10 == 0) goto L9b
            android.util.Log.d(r1, r0)
        L9b:
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.utils.FileUtils.compressToGzip(android.content.Context,"
                    + " java.io.File):java.io.File");
    }

    public static int copyFileInternal(InputStream inputStream, OutputStream outputStream) {
        try {
            byte[] bArr = new byte[1024];
            int i = 0;
            while (true) {
                int read = inputStream.read(bArr, 0, 1024);
                if (read == -1) {
                    return i;
                }
                i += read;
                outputStream.write(bArr, 0, read);
            }
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("copyFileInternal : "), "Eternal/FileUtils");
            return -1;
        }
    }

    public static void decompressFromGzip(Context context, File file) {
        FileInputStream fileInputStream;
        Log.d("Eternal/FileUtils", "Decompress to xml started");
        String path = file.getPath();
        File rename = rename(context, file, "DTD_temp.xml");
        try {
            if (rename == null) {
                Log.d("Eternal/FileUtils", "Can't change file name");
                return;
            }
            try {
                fileInputStream = new FileInputStream(rename.getPath());
            } catch (IOException | IllegalArgumentException unused) {
                rename(context, rename, "SettingsBackup.xml");
                Log.d("Eternal/FileUtils", "Decompress to xml failed");
                if (!rename.delete()) {
                    return;
                }
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(path);
                try {
                    GZIPInputStream gZIPInputStream = new GZIPInputStream(fileInputStream);
                    try {
                        byte[] bArr = new byte[4096];
                        for (int read = gZIPInputStream.read(bArr);
                                read > 0;
                                read = gZIPInputStream.read(bArr)) {
                            fileOutputStream.write(bArr, 0, read);
                        }
                        gZIPInputStream.close();
                        fileOutputStream.close();
                        fileInputStream.close();
                        if (!rename.delete()) {
                            return;
                        }
                        Log.d("Eternal/FileUtils", "Decompress to xml Succeeded");
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Throwable th3) {
            if (rename.delete()) {
                Log.d("Eternal/FileUtils", "Decompress to xml Succeeded");
            }
            throw th3;
        }
    }

    public static void deleteAllFiles(String str) {
        File[] listFiles;
        File file = new File(str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (file.isDirectory() && (listFiles = file.listFiles()) != null) {
            for (File file2 : listFiles) {
                if (file2 != null) {
                    file2.delete();
                }
            }
        }
        file.delete();
    }

    public static void deleteFile(File file, String str, String str2) {
        if (!file.exists()) {
            StringBuilder m =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            str2, " file is not exist. : ");
            m.append(file.getName());
            EternalFileLog.w(str, m.toString());
        } else if (file.delete()) {
            StringBuilder m2 =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            str2, " file is deleted. : ");
            m2.append(file.getName());
            EternalFileLog.d(str, m2.toString());
        } else {
            StringBuilder m3 =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            str2, " file delete failed. : ");
            m3.append(file.getName());
            EternalFileLog.e(str, m3.toString());
        }
    }

    public static File getFile(Context context) {
        return new File(context.getFilesDir().getPath(), "SettingsBackup.xml");
    }

    public static String getFileFullPath(Context context, String str) {
        return context.getFilesDir().getPath() + "/" + str;
    }

    public static File rename(Context context, File file, String str) {
        if (!file.exists()) {
            Log.d("Eternal/FileUtils", "sourceFile does not exist");
            return null;
        }
        File file2 = new File(context.getFilesDir().getPath() + "/" + str);
        if (file2.exists() && file2.delete()) {
            Log.d("Eternal/FileUtils", "destFile deleted");
        }
        if (file.renameTo(file2)) {
            return file2;
        }
        Log.d("Eternal/FileUtils", "Restore file name failed");
        return null;
    }

    public final int decrypt(Context context, String str) {
        if (rename(context, getFile(context), "SettingsBackup.xml.tmp") == null) {
            EternalFileLog.i("Eternal/FileUtils", "decrypt() failed - rename()");
            return 0;
        }
        String fileFullPath = getFileFullPath(context, "SettingsBackup.xml.tmp");
        try {
            FileInputStream fileInputStream = new FileInputStream(fileFullPath);
            try {
                InputStream decryptStream = decryptStream(fileInputStream);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(str);
                    try {
                        int copyFileInternal = copyFileInternal(decryptStream, fileOutputStream);
                        EternalFileLog.d(
                                "Eternal/FileUtils", "decrypt : size = " + copyFileInternal);
                        fileOutputStream.close();
                        decryptStream.close();
                        fileInputStream.close();
                        return copyFileInternal;
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e(
                    "Eternal/FileUtils",
                    "decrypt() Failed / inputFilePath : "
                            + fileFullPath
                            + " / outputFilePath : "
                            + str
                            + "\n"
                            + e.toString());
            return 0;
        } finally {
            deleteAllFiles(fileFullPath);
        }
    }

    public final InputStream decryptStream(InputStream inputStream) {
        SecretKeySpec generateSHA256SecretKey;
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        this.mCipher = cipher;
        byte[] bArr = new byte[cipher.getBlockSize()];
        if (inputStream.read(bArr) <= 0) {
            Log.d("Eternal/FileUtils", "Error reading bytes into iv");
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        if (this.mSecurityLevel == 1) {
            byte[] bArr2 = new byte[16];
            this.mSalt = bArr2;
            if (inputStream.read(bArr2) <= 0) {
                Log.d("Eternal/FileUtils", "Error reading bytes into mSalt");
            }
            generateSHA256SecretKey = generatePBKDF2SecretKey();
        } else {
            generateSHA256SecretKey = generateSHA256SecretKey();
        }
        this.mCipher.init(2, generateSHA256SecretKey, ivParameterSpec);
        return new CipherInputStream(inputStream, this.mCipher);
    }

    public final int encrypt(Context context, String str) {
        if (rename(context, getFile(context), "SettingsBackup.xml.tmp") == null) {
            EternalFileLog.i("Eternal/FileUtils", "encrypt() failed - rename()");
            return 0;
        }
        String fileFullPath = getFileFullPath(context, "SettingsBackup.xml.tmp");
        try {
            FileInputStream fileInputStream = new FileInputStream(fileFullPath);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(str);
                try {
                    OutputStream encryptStream = encryptStream(fileOutputStream);
                    try {
                        int copyFileInternal = copyFileInternal(fileInputStream, encryptStream);
                        EternalFileLog.d(
                                "Eternal/FileUtils", "encrypt : size = " + copyFileInternal);
                        encryptStream.close();
                        fileOutputStream.close();
                        fileInputStream.close();
                        return copyFileInternal;
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e(
                    "Eternal/FileUtils",
                    "encrypt() Failed / inputFilePath : "
                            + fileFullPath
                            + " / outputFilePath : "
                            + str
                            + "\n"
                            + e.toString());
            return 0;
        } finally {
            deleteAllFiles(fileFullPath);
        }
    }

    public final OutputStream encryptStream(OutputStream outputStream) {
        SecretKeySpec generateSHA256SecretKey;
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        this.mCipher = cipher;
        byte[] bArr = new byte[cipher.getBlockSize()];
        new SecureRandom().nextBytes(bArr);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        outputStream.write(bArr);
        if (this.mSecurityLevel == 1) {
            SecureRandom secureRandom = new SecureRandom();
            byte[] bArr2 = new byte[16];
            this.mSalt = bArr2;
            secureRandom.nextBytes(bArr2);
            byte[] bArr3 = this.mSalt;
            this.mSalt = bArr3;
            outputStream.write(bArr3);
            generateSHA256SecretKey = generatePBKDF2SecretKey();
        } else {
            generateSHA256SecretKey = generateSHA256SecretKey();
        }
        this.mCipher.init(1, generateSHA256SecretKey, ivParameterSpec);
        return new CipherOutputStream(outputStream, this.mCipher);
    }

    public final SecretKeySpec generatePBKDF2SecretKey() {
        return new SecretKeySpec(
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                        .generateSecret(
                                new PBEKeySpec(
                                        this.mSessionKey.toCharArray(), this.mSalt, 1000, 256))
                        .getEncoded(),
                "AES");
    }

    public final SecretKeySpec generateSHA256SecretKey() {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(this.mSessionKey.getBytes("UTF-8"));
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
        return new SecretKeySpec(bArr, "AES");
    }
}
