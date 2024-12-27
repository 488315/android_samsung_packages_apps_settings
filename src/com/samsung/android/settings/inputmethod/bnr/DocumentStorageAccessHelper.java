package com.samsung.android.settings.inputmethod.bnr;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class DocumentStorageAccessHelper {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v4, types: [int] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v9 */
    public static int copyFileToDirUri(Context context, File file, Uri uri) {
        boolean z = false;
        z = false;
        z = false;
        ?? r2 = 0;
        z = false;
        if (file.isDirectory()) {
            Uri createFile = createFile(context, uri, file.getName(), "vnd.android.document/directory");
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                int i = 0;
                for (File file2 : listFiles) {
                    i += copyFileToDirUri(context, file2, createFile);
                }
                r2 = i;
            }
        } else {
            Uri createFile2 = createFile(context, uri, file.getName(), null);
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(context.getContentResolver().openOutputStream(createFile2));
                try {
                    z = FileUtils.copyFileToStream(file, bufferedOutputStream);
                    bufferedOutputStream.close();
                    r2 = z;
                } finally {
                }
            } catch (IOException e) {
                Log.w("DocumentStorageAccessHelper", "copyFileToFileUri src=" + file + " dst=" + createFile2 + ", ", e);
                r2 = z;
            }
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m((int) r2, "copyFileToDirUri copyCount=", "DocumentStorageAccessHelper");
        return r2;
    }

    public static Uri createFile(Context context, Uri uri, String str, String str2) {
        Uri uri2;
        ContentResolver contentResolver = context.getContentResolver();
        Log.d("DocumentStorageAccessHelper", "createFile parentUri=" + uri);
        try {
            uri2 = DocumentsContract.createDocument(contentResolver, uri, str2, str);
        } catch (FileNotFoundException e) {
            Log.e("DocumentStorageAccessHelper", "createFile", e);
            uri2 = null;
        }
        Log.i("DocumentStorageAccessHelper", "createFile=" + str + " Document Uri=" + uri + " Created directory Uri=" + uri2);
        return uri2;
    }

    public static List getPathUris(Context context, Intent intent) {
        ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("SAVE_PATH_URIS");
        if (stringArrayListExtra == null || stringArrayListExtra.isEmpty()) {
            String stringExtra = intent.getStringExtra("SAVE_URIS_FILE");
            if (TextUtils.isEmpty(stringExtra)) {
                return null;
            }
            stringArrayListExtra = new ArrayList<>();
            try {
                String dataFromUri = FileUtils.getDataFromUri(context, Uri.parse(stringExtra));
                if (dataFromUri == null) {
                    return null;
                }
                JSONArray jSONArray = new JSONObject(dataFromUri).getJSONArray("dataList");
                for (int i = 0; i < jSONArray.length(); i++) {
                    stringArrayListExtra.add(jSONArray.getJSONObject(i).getString("docUri"));
                }
            } catch (Exception e) {
                Log.e("DocumentStorageAccessHelper", "failed getPathUris. ", e);
            }
        }
        ArrayList arrayList = new ArrayList();
        if (!stringArrayListExtra.isEmpty()) {
            for (String str : stringArrayListExtra) {
                arrayList.add(Uri.parse(str));
                Log.v("DocumentStorageAccessHelper", "getPathUris=" + str);
            }
        }
        Log.i("DocumentStorageAccessHelper", "getPathUris size=" + arrayList.size());
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0188 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0161 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x010a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int moveUrisToDir(android.content.Context r17, android.net.Uri r18, java.util.Collection r19, java.io.File r20) {
        /*
            Method dump skipped, instructions count: 454
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.inputmethod.bnr.DocumentStorageAccessHelper.moveUrisToDir(android.content.Context, android.net.Uri, java.util.Collection, java.io.File):int");
    }
}
