package com.samsung.android.scloud.oem.lib.utils;

import com.samsung.android.scloud.oem.lib.LOG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class FileTool {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface PDMProgressListener {
        void transferred(long j, long j2);
    }

    public static boolean fileCopy(String str, String str2) {
        FileOutputStream fileOutputStream;
        LOG.i("FileTool", "fileCopy(), from : " + str + " , to : " + str2);
        File file = new File(str);
        if (!file.isFile()) {
            LOG.i("FileTool", "oldFile is null or not file~!");
            return false;
        }
        File file2 = new File(str2);
        if (file2.exists()) {
            file2.delete();
        }
        if (file.renameTo(file2)) {
            if (file.exists()) {
                file.delete();
            }
            return true;
        }
        byte[] bArr = new byte[1024];
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(file);
                try {
                    fileOutputStream = new FileOutputStream(file2);
                    while (true) {
                        try {
                            int read = fileInputStream2.read(bArr, 0, 1024);
                            if (read != -1) {
                                fileOutputStream.write(bArr, 0, read);
                            } else {
                                try {
                                    break;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (FileNotFoundException e2) {
                            e = e2;
                            fileInputStream = fileInputStream2;
                            LOG.e("FileTool", "fileCopy() failed", e);
                            try {
                                if (file.exists()) {
                                    file.delete();
                                }
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                            return false;
                        } catch (IOException e4) {
                            e = e4;
                            fileInputStream = fileInputStream2;
                            LOG.e("FileTool", "fileCopy() failed", e);
                            try {
                                if (file.exists()) {
                                    file.delete();
                                }
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            fileInputStream = fileInputStream2;
                            try {
                                if (file.exists()) {
                                    file.delete();
                                }
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                            throw th;
                        }
                    }
                    if (file.exists()) {
                        file.delete();
                    }
                    fileInputStream2.close();
                    fileOutputStream.close();
                    return true;
                } catch (FileNotFoundException e7) {
                    e = e7;
                    fileOutputStream = null;
                } catch (IOException e8) {
                    e = e8;
                    fileOutputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream = null;
                }
            } catch (FileNotFoundException e9) {
                e = e9;
                fileOutputStream = null;
            } catch (IOException e10) {
                e = e10;
                fileOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public static void writeToFile(
            InputStream inputStream,
            long j,
            FileOutputStream fileOutputStream,
            PDMProgressListener pDMProgressListener) {
        try {
            LOG.d("FileTool", "writeToFile - start Write with stream : " + fileOutputStream);
            byte[] bArr = new byte[131072];
            long j2 = 0;
            while (true) {
                int read = inputStream.read(bArr);
                if (read <= 0) {
                    try {
                        inputStream.close();
                        fileOutputStream.close();
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                j2 += read;
                pDMProgressListener.transferred(j2, j);
                fileOutputStream.write(bArr, 0, read);
            }
        } catch (Throwable th) {
            try {
                inputStream.close();
                fileOutputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            throw th;
        }
    }

    public static void writeToFile(
            InputStream inputStream, long j, String str, PDMProgressListener pDMProgressListener) {
        LOG.d("FileTool", "writeToFile - start Write with stream : " + str);
        String[] split = str.split("/");
        String substring = str.substring(0, str.length() - split[split.length + (-1)].length());
        File file = new File(substring);
        if (!file.exists()) {
            LOG.i("FileTool", "Creating folder : " + substring);
            if (!file.mkdirs()) {
                LOG.i("FileTool", "ORSMetaResponse.fromBinaryFile(): Can not create directory. ");
                throw new IOException();
            }
        }
        writeToFile(inputStream, j, new FileOutputStream(str, false), pDMProgressListener);
    }

    public static void writeToFile(
            String str,
            long j,
            FileOutputStream fileOutputStream,
            PDMProgressListener pDMProgressListener) {
        File file = new File(str);
        if (file.exists()) {
            writeToFile(new FileInputStream(file), j, fileOutputStream, pDMProgressListener);
            return;
        }
        throw new IOException();
    }
}
