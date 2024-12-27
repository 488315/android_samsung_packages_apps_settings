package com.samsung.android.settings.eternal.smartswitch;

import android.util.Log;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SmartSwitchFileUtil {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:41:0x008b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v6, types: [long] */
    /* JADX WARN: Type inference failed for: r10v0, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r10v1, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v4, types: [java.lang.StringBuilder] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean copyFileToStream(java.io.File r9, java.io.OutputStream r10) {
        /*
            java.lang.String r0 = "bOutputStream close exception"
            java.lang.String r1 = "bInputStream close exception"
            java.lang.String r2 = "SSM[SelfBnRTest]SmartSwitchFileUtil"
            java.lang.String r3 = "cpFileBufferedIO "
            r4 = 0
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            r6.<init>(r9)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            boolean r3 = cpStream(r5, r10)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L29
            r5.close()     // Catch: java.io.IOException -> L1b
            goto L1e
        L1b:
            android.util.Log.e(r2, r1)
        L1e:
            r10.close()     // Catch: java.io.IOException -> L22
            goto L61
        L22:
            android.util.Log.e(r2, r0)
            goto L61
        L26:
            r9 = move-exception
            r4 = r5
            goto L89
        L29:
            r4 = move-exception
            goto L31
        L2b:
            r9 = move-exception
            goto L89
        L2d:
            r5 = move-exception
            r8 = r5
            r5 = r4
            r4 = r8
        L31:
            java.lang.String r6 = r9.getName()     // Catch: java.lang.Throwable -> L26
            java.lang.String r4 = android.util.Log.getStackTraceString(r4)     // Catch: java.lang.Throwable -> L26
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L26
            r7.<init>(r3)     // Catch: java.lang.Throwable -> L26
            r7.append(r6)     // Catch: java.lang.Throwable -> L26
            java.lang.String r3 = " Exception "
            r7.append(r3)     // Catch: java.lang.Throwable -> L26
            r7.append(r4)     // Catch: java.lang.Throwable -> L26
            java.lang.String r3 = r7.toString()     // Catch: java.lang.Throwable -> L26
            android.util.Log.e(r2, r3)     // Catch: java.lang.Throwable -> L26
            if (r5 == 0) goto L59
            r5.close()     // Catch: java.io.IOException -> L56
            goto L59
        L56:
            android.util.Log.e(r2, r1)
        L59:
            r10.close()     // Catch: java.io.IOException -> L5d
            goto L60
        L5d:
            android.util.Log.e(r2, r0)
        L60:
            r3 = 0
        L61:
            java.lang.String r10 = "cpFileBufferedIO result :"
            java.lang.String r0 = ", srcFile : "
            java.lang.StringBuilder r10 = androidx.slice.widget.RowView$$ExternalSyntheticOutline0.m(r10, r0, r3)
            java.lang.String r0 = r9.getAbsolutePath()
            r10.append(r0)
            java.lang.String r0 = "("
            r10.append(r0)
            long r0 = r9.length()
            r10.append(r0)
            java.lang.String r9 = ")"
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            android.util.Log.v(r2, r9)
            return r3
        L89:
            if (r4 == 0) goto L92
            r4.close()     // Catch: java.io.IOException -> L8f
            goto L92
        L8f:
            android.util.Log.e(r2, r1)
        L92:
            r10.close()     // Catch: java.io.IOException -> L96
            goto L99
        L96:
            android.util.Log.e(r2, r0)
        L99:
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.smartswitch.SmartSwitchFileUtil.copyFileToStream(java.io.File,"
                    + " java.io.OutputStream):boolean");
    }

    public static boolean cpStream(InputStream inputStream, OutputStream outputStream) {
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
                            } catch (IOException unused) {
                                Log.e(
                                        "SSM[SelfBnRTest]SmartSwitchFileUtil",
                                        "cpStream out close ex");
                            }
                        }
                    } while (j - j2 < 1048576);
                }
                outputStream.close();
                try {
                    inputStream.close();
                } catch (IOException unused2) {
                    Log.e("SSM[SelfBnRTest]SmartSwitchFileUtil", "cpStream in close ex");
                }
                return true;
            } catch (Exception e) {
                Log.e(
                        "SSM[SelfBnRTest]SmartSwitchFileUtil",
                        "cpStream ex:" + Log.getStackTraceString(e));
                try {
                    outputStream.close();
                } catch (IOException unused3) {
                    Log.e("SSM[SelfBnRTest]SmartSwitchFileUtil", "cpStream out close ex");
                }
                try {
                    inputStream.close();
                    return false;
                } catch (IOException unused4) {
                    Log.e("SSM[SelfBnRTest]SmartSwitchFileUtil", "cpStream in close ex");
                    return false;
                }
            }
        } finally {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x008c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0083 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getDataFromUri(android.content.Context r7, android.net.Uri r8) {
        /*
            java.lang.String r0 = "bInputStream close exception"
            java.lang.String r1 = "inputStream close exception"
            java.lang.String r2 = "SSM[SelfBnRTest]SmartSwitchFileUtil"
            r3 = 0
            android.content.ContentResolver r7 = r7.getContentResolver()     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L39
            java.io.InputStream r7 = r7.openInputStream(r8)     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L39
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L31
            r4.<init>(r7)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L31
            java.lang.String r3 = getStreamData(r4)     // Catch: java.lang.Throwable -> L29 java.lang.Exception -> L2c
            if (r7 == 0) goto L21
            r7.close()     // Catch: java.io.IOException -> L1e
            goto L21
        L1e:
            android.util.Log.e(r2, r1)
        L21:
            r4.close()     // Catch: java.io.IOException -> L25
            goto L62
        L25:
            android.util.Log.e(r2, r0)
            goto L62
        L29:
            r8 = move-exception
        L2a:
            r3 = r7
            goto L81
        L2c:
            r3 = move-exception
            goto L3d
        L2e:
            r8 = move-exception
            r4 = r3
            goto L2a
        L31:
            r4 = move-exception
            r6 = r4
            r4 = r3
            r3 = r6
            goto L3d
        L36:
            r8 = move-exception
            r4 = r3
            goto L81
        L39:
            r7 = move-exception
            r4 = r3
            r3 = r7
            r7 = r4
        L3d:
            java.lang.String r5 = "getDataFromUri %s Exception %s"
            java.lang.String r3 = android.util.Log.getStackTraceString(r3)     // Catch: java.lang.Throwable -> L29
            java.lang.Object[] r3 = new java.lang.Object[]{r8, r3}     // Catch: java.lang.Throwable -> L29
            java.lang.String r3 = java.lang.String.format(r5, r3)     // Catch: java.lang.Throwable -> L29
            android.util.Log.e(r2, r3)     // Catch: java.lang.Throwable -> L29
            if (r7 == 0) goto L57
            r7.close()     // Catch: java.io.IOException -> L54
            goto L57
        L54:
            android.util.Log.e(r2, r1)
        L57:
            if (r4 == 0) goto L60
            r4.close()     // Catch: java.io.IOException -> L5d
            goto L60
        L5d:
            android.util.Log.e(r2, r0)
        L60:
            java.lang.String r3 = ""
        L62:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r0 = "getDataFromUri result :"
            r7.<init>(r0)
            r7.append(r3)
            java.lang.String r0 = ", srcUri : "
            r7.append(r0)
            r7.append(r8)
            java.lang.String r8 = ")"
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.util.Log.v(r2, r7)
            return r3
        L81:
            if (r3 == 0) goto L8a
            r3.close()     // Catch: java.io.IOException -> L87
            goto L8a
        L87:
            android.util.Log.e(r2, r1)
        L8a:
            if (r4 == 0) goto L93
            r4.close()     // Catch: java.io.IOException -> L90
            goto L93
        L90:
            android.util.Log.e(r2, r0)
        L93:
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.smartswitch.SmartSwitchFileUtil.getDataFromUri(android.content.Context,"
                    + " android.net.Uri):java.lang.String");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(2:(3:5|6|(2:(2:9|10)(1:12)|11)(0))|13) */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0030, code lost:

       android.util.Log.e("SSM[SelfBnRTest]SmartSwitchFileUtil", "getStreamData close ex");
    */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getStreamData(java.io.InputStream r8) {
        /*
            java.lang.String r0 = "getStreamData close ex"
            java.lang.String r1 = "SSM[SelfBnRTest]SmartSwitchFileUtil"
            java.io.InputStreamReader r2 = new java.io.InputStreamReader
            r2.<init>(r8)
            java.io.BufferedReader r8 = new java.io.BufferedReader
            r8.<init>(r2)
            r2 = 0
            r3 = 2048(0x800, float:2.87E-42)
            char[] r4 = new char[r3]     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L34
            r5 = r2
        L14:
            int r6 = r8.read(r4)     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L25
            if (r6 <= 0) goto L2c
            if (r5 != 0) goto L27
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L25
            r7.<init>(r3)     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L25
            r5 = r7
            goto L27
        L23:
            r2 = move-exception
            goto L57
        L25:
            r3 = move-exception
            goto L36
        L27:
            r7 = 0
            r5.append(r4, r7, r6)     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L25
            goto L14
        L2c:
            r8.close()     // Catch: java.io.IOException -> L30
            goto L4f
        L30:
            android.util.Log.e(r1, r0)
            goto L4f
        L34:
            r3 = move-exception
            r5 = r2
        L36:
            java.lang.String r3 = android.util.Log.getStackTraceString(r3)     // Catch: java.lang.Throwable -> L23
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L23
            r4.<init>()     // Catch: java.lang.Throwable -> L23
            java.lang.String r6 = "getStreamData ex : "
            r4.append(r6)     // Catch: java.lang.Throwable -> L23
            r4.append(r3)     // Catch: java.lang.Throwable -> L23
            java.lang.String r3 = r4.toString()     // Catch: java.lang.Throwable -> L23
            android.util.Log.e(r1, r3)     // Catch: java.lang.Throwable -> L23
            goto L2c
        L4f:
            if (r5 != 0) goto L52
            goto L56
        L52:
            java.lang.String r2 = r5.toString()
        L56:
            return r2
        L57:
            r8.close()     // Catch: java.io.IOException -> L5b
            goto L5e
        L5b:
            android.util.Log.e(r1, r0)
        L5e:
            throw r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.smartswitch.SmartSwitchFileUtil.getStreamData(java.io.InputStream):java.lang.String");
    }
}
