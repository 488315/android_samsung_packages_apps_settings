package com.samsung.android.settings.eternal.smartswitch;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.Log;

import com.samsung.android.settings.eternal.utils.EternalUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BnRDocumentStorageAccessHelper {
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
    jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:29:0x004d
    	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
    	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v9 */
    public static int copyFileToDirUri(
            android.content.Context r6, java.io.File r7, android.net.Uri r8) {
        /*
            boolean r0 = r7.isDirectory()
            r1 = 0
            if (r0 == 0) goto L29
            java.lang.String r0 = r7.getName()
            java.lang.String r2 = "vnd.android.document/directory"
            android.net.Uri r8 = createFile(r6, r8, r0, r2)
            java.io.File[] r7 = r7.listFiles()
            if (r7 != 0) goto L19
            return r1
        L19:
            int r0 = r7.length
            r2 = r1
        L1b:
            if (r1 >= r0) goto L27
            r3 = r7[r1]
            int r3 = copyFileToDirUri(r6, r3, r8)
            int r2 = r2 + r3
            int r1 = r1 + 1
            goto L1b
        L27:
            r1 = r2
            goto L7d
        L29:
            java.lang.String r0 = r7.getName()
            r2 = 0
            android.net.Uri r8 = createFile(r6, r8, r0, r2)
            if (r8 == 0) goto L7d
            java.lang.String r0 = "copyFileToFileUri bufferedOutputStream close exception"
            java.lang.String r3 = "SSM[SelfBnRTest]BnRDocumentStorageAccessHelper"
            java.io.BufferedOutputStream r4 = new java.io.BufferedOutputStream     // Catch: java.lang.Throwable -> L59 java.io.FileNotFoundException -> L5b
            android.content.ContentResolver r6 = r6.getContentResolver()     // Catch: java.lang.Throwable -> L59 java.io.FileNotFoundException -> L5b
            java.io.OutputStream r6 = r6.openOutputStream(r8)     // Catch: java.lang.Throwable -> L59 java.io.FileNotFoundException -> L5b
            r4.<init>(r6)     // Catch: java.lang.Throwable -> L59 java.io.FileNotFoundException -> L5b
            boolean r1 = com.samsung.android.settings.eternal.smartswitch.SmartSwitchFileUtil.copyFileToStream(r7, r4)     // Catch: java.lang.Throwable -> L53 java.io.FileNotFoundException -> L56
            r4.close()     // Catch: java.io.IOException -> L4d
            goto L7d
        L4d:
            java.util.Locale r6 = java.util.Locale.ENGLISH
            android.util.Log.w(r3, r0)
            goto L7d
        L53:
            r6 = move-exception
            r2 = r4
            goto L71
        L56:
            r6 = move-exception
            r2 = r4
            goto L5c
        L59:
            r6 = move-exception
            goto L71
        L5b:
            r6 = move-exception
        L5c:
            java.util.Locale r4 = java.util.Locale.ENGLISH     // Catch: java.lang.Throwable -> L59
            java.lang.String r5 = "copyFileToFileUri src[%s], dst[%s]"
            java.lang.Object[] r7 = new java.lang.Object[]{r7, r8}     // Catch: java.lang.Throwable -> L59
            java.lang.String r7 = java.lang.String.format(r4, r5, r7)     // Catch: java.lang.Throwable -> L59
            android.util.Log.w(r3, r7, r6)     // Catch: java.lang.Throwable -> L59
            if (r2 == 0) goto L7d
            r2.close()     // Catch: java.io.IOException -> L4d
            goto L7d
        L71:
            if (r2 == 0) goto L7c
            r2.close()     // Catch: java.io.IOException -> L77
            goto L7c
        L77:
            java.util.Locale r7 = java.util.Locale.ENGLISH
            android.util.Log.w(r3, r0)
        L7c:
            throw r6
        L7d:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.smartswitch.BnRDocumentStorageAccessHelper.copyFileToDirUri(android.content.Context,"
                    + " java.io.File, android.net.Uri):int");
    }

    public static Uri createFile(Context context, Uri uri, String str, String str2) {
        Uri uri2;
        ContentResolver contentResolver = context.getContentResolver();
        Log.d("SSM[SelfBnRTest]BnRDocumentStorageAccessHelper", "createFile parentUri : " + uri);
        try {
            uri2 = DocumentsContract.createDocument(contentResolver, uri, str2, str);
        } catch (FileNotFoundException e) {
            Log.e("SSM[SelfBnRTest]BnRDocumentStorageAccessHelper", "createFile", e);
            uri2 = null;
        }
        Log.i(
                "SSM[SelfBnRTest]BnRDocumentStorageAccessHelper",
                String.format(
                        "createFile : %s, Document Uri : %s, Created directory Uri : %s",
                        str, uri, uri2));
        return uri2;
    }

    public static List getPathUris(Context context, Intent intent) {
        String stringExtra;
        ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("SAVE_PATH_URIS");
        if ((stringArrayListExtra == null || stringArrayListExtra.isEmpty())
                && (stringExtra = intent.getStringExtra("SAVE_URIS_FILE")) != null) {
            stringArrayListExtra = new ArrayList<>();
            try {
                JSONArray jSONArray =
                        new JSONObject(
                                        SmartSwitchFileUtil.getDataFromUri(
                                                context, Uri.parse(stringExtra)))
                                .getJSONArray("dataList");
                StringBuilder sb = new StringBuilder();
                sb.append("getPathUris\n");
                for (int i = 0; i < jSONArray.length(); i++) {
                    try {
                        stringArrayListExtra.add(jSONArray.getJSONObject(i).getString("docUri"));
                    } catch (Exception e) {
                        sb.append(EternalUtils.buildLogMessage("getPathUris() " + e.getMessage()));
                    }
                }
                Log.e("SSM[SelfBnRTest]BnRDocumentStorageAccessHelper", sb.toString());
            } catch (Exception e2) {
                Log.e("SSM[SelfBnRTest]BnRDocumentStorageAccessHelper", "getPathUris", e2);
            }
        }
        ArrayList arrayList = new ArrayList();
        if (stringArrayListExtra != null && !stringArrayListExtra.isEmpty()) {
            StringBuilder sb2 = new StringBuilder("getPathUris\n");
            for (String str : stringArrayListExtra) {
                arrayList.add(Uri.parse(str));
                sb2.append(EternalUtils.buildLogMessage("getPathUris [" + str + "]"));
            }
            Log.i("SSM[SelfBnRTest]BnRDocumentStorageAccessHelper", sb2.toString());
        }
        Log.i(
                "SSM[SelfBnRTest]BnRDocumentStorageAccessHelper",
                String.format("getPathUris [%d]", Integer.valueOf(arrayList.size())));
        return arrayList;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:(3:16|17|18)|(20:19|20|22|23|24|25|26|27|28|29|31|32|(2:59|60)|34|35|36|37|38|39|40)|41|42|43|(1:46)|47|48) */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0173, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0174, code lost:

       r2 = new java.lang.StringBuilder();
       r3 = java.util.Locale.ENGLISH;
       r2.append("moveUrisToDir delete FileNotFoundException [" + r13 + "] ");
       r2.append(r0.getMessage());
       r7.append(com.samsung.android.settings.eternal.utils.EternalUtils.buildLogMessage(r2.toString()));
       r0 = false;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:103:0x01e9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01e0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01d7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01a7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x012c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0123 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x011a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0111 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01f2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int moveUrisToDir(
            android.content.Context r22,
            android.net.Uri r23,
            java.util.Collection r24,
            java.io.File r25) {
        /*
            Method dump skipped, instructions count: 575
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.smartswitch.BnRDocumentStorageAccessHelper.moveUrisToDir(android.content.Context,"
                    + " android.net.Uri, java.util.Collection, java.io.File):int");
    }
}
