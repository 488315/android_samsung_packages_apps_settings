package com.samsung.android.smartswitchfileshare;

import android.content.ContentResolver;
import android.text.TextUtils;
import android.util.Log;

import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FileUtil {
    public final String TAG;
    public final ContentResolver contentResolver;

    public FileUtil(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        this.TAG =
                (TextUtils.isEmpty("WifiSmartSwitchBackupRestore")
                                ? "MSDG[SmartSwitchFileShare]"
                                : "WifiSmartSwitchBackupRestore[SmartSwitchFileShare]")
                        .concat("FileUtil");
    }

    public final boolean copyFileToStream(File file, OutputStream outputStream) {
        String str = this.TAG;
        boolean z = false;
        try {
            BufferedInputStream bufferedInputStream =
                    new BufferedInputStream(new FileInputStream(file));
            try {
                cpStream(bufferedInputStream, outputStream);
                z = true;
                bufferedInputStream.close();
            } finally {
            }
        } catch (Exception e) {
            Log.e(
                    str,
                    "copyFileToStream "
                            + file.getName()
                            + " Exception "
                            + Log.getStackTraceString(e));
        }
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m(
                        "copyFileToStream result :", ", srcFile : ", z);
        m.append(file.getAbsolutePath());
        m.append("(");
        m.append(file.length());
        m.append(")");
        Log.v(str, m.toString());
        return z;
    }

    public final boolean cpStream(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[NetworkAnalyticsConstants.DataPoints.FLAG_UID];
        long j = 0;
        while (true) {
            long j2 = j;
            do {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    return true;
                }
                outputStream.write(bArr, 0, read);
                j += read;
            } while (j - j2 < 1048576);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0088 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getDataFromUri(android.net.Uri r7) {
        /*
            r6 = this;
            java.lang.String r0 = "bInputStream close exception"
            java.lang.String r1 = "inputStream close exception"
            java.lang.String r2 = r6.TAG
            r3 = 0
            android.content.ContentResolver r4 = r6.contentResolver     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            java.io.InputStream r4 = r4.openInputStream(r7)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            r5.<init>(r4)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            java.lang.String r6 = r6.getStreamData(r5)     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L2a
            if (r4 == 0) goto L1f
            r4.close()     // Catch: java.io.IOException -> L1c
            goto L1f
        L1c:
            android.util.Log.e(r2, r1)
        L1f:
            r5.close()     // Catch: java.io.IOException -> L23
            goto L5d
        L23:
            android.util.Log.e(r2, r0)
            goto L5d
        L27:
            r6 = move-exception
        L28:
            r3 = r4
            goto L7d
        L2a:
            r6 = move-exception
        L2b:
            r3 = r4
            goto L38
        L2d:
            r6 = move-exception
            r5 = r3
            goto L28
        L30:
            r6 = move-exception
            r5 = r3
            goto L2b
        L33:
            r6 = move-exception
            r5 = r3
            goto L7d
        L36:
            r6 = move-exception
            r5 = r3
        L38:
            java.lang.String r4 = "getDataFromUri %s Exception %s"
            java.lang.String r6 = android.util.Log.getStackTraceString(r6)     // Catch: java.lang.Throwable -> L7c
            java.lang.Object[] r6 = new java.lang.Object[]{r7, r6}     // Catch: java.lang.Throwable -> L7c
            java.lang.String r6 = java.lang.String.format(r4, r6)     // Catch: java.lang.Throwable -> L7c
            android.util.Log.e(r2, r6)     // Catch: java.lang.Throwable -> L7c
            if (r3 == 0) goto L52
            r3.close()     // Catch: java.io.IOException -> L4f
            goto L52
        L4f:
            android.util.Log.e(r2, r1)
        L52:
            if (r5 == 0) goto L5b
            r5.close()     // Catch: java.io.IOException -> L58
            goto L5b
        L58:
            android.util.Log.e(r2, r0)
        L5b:
            java.lang.String r6 = ""
        L5d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "getDataFromUri result :"
            r0.<init>(r1)
            r0.append(r6)
            java.lang.String r1 = ", srcUri : "
            r0.append(r1)
            r0.append(r7)
            java.lang.String r7 = ")"
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            android.util.Log.v(r2, r7)
            return r6
        L7c:
            r6 = move-exception
        L7d:
            if (r3 == 0) goto L86
            r3.close()     // Catch: java.io.IOException -> L83
            goto L86
        L83:
            android.util.Log.e(r2, r1)
        L86:
            if (r5 == 0) goto L8f
            r5.close()     // Catch: java.io.IOException -> L8c
            goto L8f
        L8c:
            android.util.Log.e(r2, r0)
        L8f:
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.smartswitchfileshare.FileUtil.getDataFromUri(android.net.Uri):java.lang.String");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(2:(3:5|6|(2:(2:9|10)(1:12)|11)(0))|13) */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0030, code lost:

       android.util.Log.e(r7, "getStreamData close ex");
    */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getStreamData(java.io.InputStream r8) {
        /*
            r7 = this;
            java.lang.String r0 = "getStreamData close ex"
            java.lang.String r7 = r7.TAG
            java.io.InputStreamReader r1 = new java.io.InputStreamReader
            r1.<init>(r8)
            java.io.BufferedReader r8 = new java.io.BufferedReader
            r8.<init>(r1)
            r1 = 0
            r2 = 2048(0x800, float:2.87E-42)
            char[] r3 = new char[r2]     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L34
            r4 = r1
        L14:
            int r5 = r8.read(r3)     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L25
            if (r5 <= 0) goto L2c
            if (r4 != 0) goto L27
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L25
            r6.<init>(r2)     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L25
            r4 = r6
            goto L27
        L23:
            r1 = move-exception
            goto L57
        L25:
            r2 = move-exception
            goto L36
        L27:
            r6 = 0
            r4.append(r3, r6, r5)     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L25
            goto L14
        L2c:
            r8.close()     // Catch: java.io.IOException -> L30
            goto L4f
        L30:
            android.util.Log.e(r7, r0)
            goto L4f
        L34:
            r2 = move-exception
            r4 = r1
        L36:
            java.lang.String r2 = android.util.Log.getStackTraceString(r2)     // Catch: java.lang.Throwable -> L23
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L23
            r3.<init>()     // Catch: java.lang.Throwable -> L23
            java.lang.String r5 = "getStreamData ex : "
            r3.append(r5)     // Catch: java.lang.Throwable -> L23
            r3.append(r2)     // Catch: java.lang.Throwable -> L23
            java.lang.String r2 = r3.toString()     // Catch: java.lang.Throwable -> L23
            android.util.Log.e(r7, r2)     // Catch: java.lang.Throwable -> L23
            goto L2c
        L4f:
            if (r4 != 0) goto L52
            goto L56
        L52:
            java.lang.String r1 = r4.toString()
        L56:
            return r1
        L57:
            r8.close()     // Catch: java.io.IOException -> L5b
            goto L5e
        L5b:
            android.util.Log.e(r7, r0)
        L5e:
            throw r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.smartswitchfileshare.FileUtil.getStreamData(java.io.InputStream):java.lang.String");
    }
}
