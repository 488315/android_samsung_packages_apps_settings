package com.samsung.android.settings.languagepack.apkverifier;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class GetSha1FromApk {
    public final Context mContext;

    public GetSha1FromApk(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x007f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0075 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String loadCertificates(
            java.util.jar.JarFile r6, java.util.jar.JarEntry r7) {
        /*
            r0 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L41
            r1.<init>()     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L41
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r2]     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            java.io.InputStream r6 = r6.getInputStream(r7)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            r4.<init>(r6)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
        L13:
            r6 = 0
            int r7 = r4.read(r3, r6, r2)     // Catch: java.lang.Throwable -> L1f java.lang.Exception -> L22
            r5 = -1
            if (r7 == r5) goto L24
            r1.write(r3, r6, r7)     // Catch: java.lang.Throwable -> L1f java.lang.Exception -> L22
            goto L13
        L1f:
            r6 = move-exception
            r0 = r4
            goto L73
        L22:
            r6 = move-exception
            goto L44
        L24:
            java.lang.String r6 = r1.toString()     // Catch: java.lang.Throwable -> L1f java.lang.Exception -> L22
            r4.close()     // Catch: java.lang.Exception -> L2c
            goto L30
        L2c:
            r7 = move-exception
            r7.printStackTrace()
        L30:
            r1.close()     // Catch: java.lang.Exception -> L34
            goto L5c
        L34:
            r7 = move-exception
            r7.printStackTrace()
            goto L5c
        L39:
            r6 = move-exception
            goto L73
        L3b:
            r6 = move-exception
            r4 = r0
            goto L44
        L3e:
            r6 = move-exception
            r1 = r0
            goto L73
        L41:
            r6 = move-exception
            r1 = r0
            r4 = r1
        L44:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L1f
            if (r4 == 0) goto L51
            r4.close()     // Catch: java.lang.Exception -> L4d
            goto L51
        L4d:
            r6 = move-exception
            r6.printStackTrace()
        L51:
            if (r1 == 0) goto L5b
            r1.close()     // Catch: java.lang.Exception -> L57
            goto L5b
        L57:
            r6 = move-exception
            r6.printStackTrace()
        L5b:
            r6 = r0
        L5c:
            if (r6 == 0) goto L72
            java.lang.String r7 = "Digest-Manifest: "
            int r7 = r6.indexOf(r7)
            int r7 = r7 + 17
            r0 = 61
            int r0 = r6.indexOf(r0, r7)
            int r0 = r0 + 1
            java.lang.String r0 = r6.substring(r7, r0)
        L72:
            return r0
        L73:
            if (r0 == 0) goto L7d
            r0.close()     // Catch: java.lang.Exception -> L79
            goto L7d
        L79:
            r7 = move-exception
            r7.printStackTrace()
        L7d:
            if (r1 == 0) goto L87
            r1.close()     // Catch: java.lang.Exception -> L83
            goto L87
        L83:
            r7 = move-exception
            r7.printStackTrace()
        L87:
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.languagepack.apkverifier.GetSha1FromApk.loadCertificates(java.util.jar.JarFile,"
                    + " java.util.jar.JarEntry):java.lang.String");
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x00e5 -> B:35:0x00f5). Please report as a decompilation issue!!! */
    public final String getSha1(String str) {
        JarFile jarFile;
        Log.d("GetSha1FromApk", "getSha1 Start path= " + str);
        String str2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        JarFile jarFile2 = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
        } catch (Error e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (this.mContext
                                .getPackageManager()
                                .getPackageArchiveInfo(str, 0)
                                .applicationInfo
                                .targetSdkVersion
                        >= 28
                && ApkSignatureV2V3Verifier.hasSignature(-262969152, str)) {
            String readContentDigest = ApkSignatureV2V3Verifier.readContentDigest(-262969152, str);
            Log.d("GetSha1FromApk(V3)", "ContentDigest = " + readContentDigest);
            return readContentDigest;
        }
        if (ApkSignatureV2V3Verifier.hasSignature(1896449818, str)) {
            String readContentDigest2 = ApkSignatureV2V3Verifier.readContentDigest(1896449818, str);
            Log.d("GetSha1FromApk(V2)", "ContentDigest = " + readContentDigest2);
            return readContentDigest2;
        }
        try {
            try {
                jarFile = new JarFile(new File(str));
            } catch (Exception e3) {
                e = e3;
                jarFile = null;
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        try {
            try {
                ArrayList arrayList = new ArrayList(8);
                Enumeration<JarEntry> entries = jarFile.entries();
                while (true) {
                    if (!entries.hasMoreElements()) {
                        break;
                    }
                    JarEntry nextElement = entries.nextElement();
                    if (nextElement.getName().startsWith("META-INF/")
                            && nextElement.getName().length() > 9
                            && nextElement.getName().endsWith(".SF")) {
                        arrayList.add(nextElement);
                        break;
                    }
                }
                JarEntry jarEntry = jarFile.getJarEntry(((ZipEntry) arrayList.get(0)).getName());
                if (jarEntry != null) {
                    try {
                        str2 = loadCertificates(jarFile, jarEntry);
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                jarFile.close();
            } catch (Exception e6) {
                e = e6;
                e.printStackTrace();
                if (jarFile != null) {
                    jarFile.close();
                }
                DialogFragment$$ExternalSyntheticOutline0.m("HashKey= ", str2, "GetSha1FromApk");
                return str2;
            }
            DialogFragment$$ExternalSyntheticOutline0.m("HashKey= ", str2, "GetSha1FromApk");
            return str2;
        } catch (Throwable th2) {
            th = th2;
            jarFile2 = jarFile;
            if (jarFile2 != null) {
                try {
                    jarFile2.close();
                } catch (Exception e7) {
                    e7.printStackTrace();
                }
            }
            throw th;
        }
    }
}
