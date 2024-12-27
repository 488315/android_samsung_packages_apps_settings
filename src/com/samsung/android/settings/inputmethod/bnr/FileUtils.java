package com.samsung.android.settings.inputmethod.bnr;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class FileUtils {
    public static boolean copyFileToStream(File file, OutputStream outputStream) {
        boolean z = false;
        try {
            BufferedInputStream bufferedInputStream =
                    new BufferedInputStream(new FileInputStream(file));
            try {
                try {
                    z = copyStream(bufferedInputStream, outputStream);
                    outputStream.close();
                    bufferedInputStream.close();
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Log.e("FileUtils", "copyFileToStream IOException, srcFile=" + file.getName(), e);
        }
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m("cpFileBufferedIO result=", ", srcFile=", z);
        m.append(file.getAbsolutePath());
        m.append(" length=");
        m.append(file.length());
        Log.v("FileUtils", m.toString());
        return z;
    }

    public static boolean copyStream(InputStream inputStream, OutputStream outputStream) {
        try {
            try {
                byte[] bArr = new byte[NetworkAnalyticsConstants.DataPoints.FLAG_UID];
                long j = 0;
                loop0:
                while (true) {
                    long j2 = j;
                    do {
                        int read = inputStream.read(bArr);
                        if (read != -1) {
                            outputStream.write(bArr, 0, read);
                            j += read;
                        } else {
                            try {
                                break loop0;
                            } catch (IOException e) {
                                Log.e("FileUtils", "cpStream out close IOException", e);
                            }
                        }
                    } while (j - j2 < 1048576);
                }
                outputStream.close();
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    Log.e("FileUtils", "cpStream in close IOException", e2);
                }
                return true;
            } catch (Exception e3) {
                Log.e("FileUtils", "cpStream Exception", e3);
                try {
                    outputStream.close();
                } catch (IOException e4) {
                    Log.e("FileUtils", "cpStream out close IOException", e4);
                }
                try {
                    inputStream.close();
                    return false;
                } catch (IOException e5) {
                    Log.e("FileUtils", "cpStream in close IOException", e5);
                    return false;
                }
            }
        } finally {
        }
    }

    public static String getDataFromUri(Context context, Uri uri) {
        InputStream openInputStream;
        String str = ApnSettings.MVNO_NONE;
        try {
            openInputStream = context.getContentResolver().openInputStream(uri);
        } catch (Exception e) {
            Log.e("FileUtils", "getDataFromUri Exception srcUri=" + uri, e);
        }
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(openInputStream);
            try {
                str = getStreamData(bufferedInputStream);
                bufferedInputStream.close();
                if (openInputStream != null) {
                    openInputStream.close();
                }
                Log.v("FileUtils", "getDataFromUri result=" + str + ", srcUri=" + uri);
                return str;
            } finally {
            }
        } finally {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getStreamData(java.io.InputStream r7) {
        /*
            r0 = 0
            java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch: java.io.IOException -> L49
            java.nio.charset.Charset r2 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.io.IOException -> L49
            r1.<init>(r7, r2)     // Catch: java.io.IOException -> L49
            java.io.BufferedReader r7 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L3e
            r7.<init>(r1)     // Catch: java.lang.Throwable -> L3e
            r2 = 2048(0x800, float:2.87E-42)
            char[] r3 = new char[r2]     // Catch: java.lang.Throwable -> L33
            r4 = r0
        L12:
            int r5 = r7.read(r3)     // Catch: java.lang.Throwable -> L21
            if (r5 <= 0) goto L28
            if (r4 != 0) goto L23
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L21
            r6.<init>(r2)     // Catch: java.lang.Throwable -> L21
            r4 = r6
            goto L23
        L21:
            r2 = move-exception
            goto L35
        L23:
            r6 = 0
            r4.append(r3, r6, r5)     // Catch: java.lang.Throwable -> L21
            goto L12
        L28:
            r7.close()     // Catch: java.lang.Throwable -> L31
            r1.close()     // Catch: java.io.IOException -> L2f
            goto L52
        L2f:
            r7 = move-exception
            goto L4b
        L31:
            r7 = move-exception
            goto L40
        L33:
            r2 = move-exception
            r4 = r0
        L35:
            r7.close()     // Catch: java.lang.Throwable -> L39
            goto L3d
        L39:
            r7 = move-exception
            r2.addSuppressed(r7)     // Catch: java.lang.Throwable -> L31
        L3d:
            throw r2     // Catch: java.lang.Throwable -> L31
        L3e:
            r7 = move-exception
            r4 = r0
        L40:
            r1.close()     // Catch: java.lang.Throwable -> L44
            goto L48
        L44:
            r1 = move-exception
            r7.addSuppressed(r1)     // Catch: java.io.IOException -> L2f
        L48:
            throw r7     // Catch: java.io.IOException -> L2f
        L49:
            r7 = move-exception
            r4 = r0
        L4b:
            java.lang.String r1 = "FileUtils"
            java.lang.String r2 = "getStreamData IOException"
            android.util.Log.e(r1, r2, r7)
        L52:
            if (r4 != 0) goto L55
            goto L59
        L55:
            java.lang.String r0 = r4.toString()
        L59:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.inputmethod.bnr.FileUtils.getStreamData(java.io.InputStream):java.lang.String");
    }
}
