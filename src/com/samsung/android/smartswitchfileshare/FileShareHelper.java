package com.samsung.android.smartswitchfileshare;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FileShareHelper {
    public final String TAG;
    public final ContentResolver contentResolver;
    public final Context context;
    public final FileUtil fileUtil;

    public FileShareHelper(Context context) {
        this.context = context;
        ContentResolver contentResolver = context.getContentResolver();
        this.contentResolver = contentResolver;
        String concat =
                (TextUtils.isEmpty("WifiSmartSwitchBackupRestore")
                                ? "MSDG[SmartSwitchFileShare]"
                                : "WifiSmartSwitchBackupRestore[SmartSwitchFileShare]")
                        .concat("FileShareHelper");
        this.TAG = concat;
        this.fileUtil = new FileUtil(contentResolver);
        Locale locale = Locale.ENGLISH;
        Log.i(concat, "FileShareHelper versionName[20220525_01], versionCode[4]");
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x0145  */
    /* JADX WARN: Type inference failed for: r2v13, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int copy(android.content.Intent r19, java.io.File r20) {
        /*
            Method dump skipped, instructions count: 522
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.smartswitchfileshare.FileShareHelper.copy(android.content.Intent,"
                    + " java.io.File):int");
    }

    public final int copyFileToDirUri(File file, Uri uri) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("copy invalid srcFile : " + file);
        }
        if (uri == null) {
            throw new IllegalArgumentException("copy invalid dstDirUri : " + uri);
        }
        boolean z = false;
        z = false;
        if (file.isDirectory()) {
            Uri createFile = createFile(uri, file.getName(), "vnd.android.document/directory");
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                return 0;
            }
            int i = 0;
            for (File file2 : listFiles) {
                i += copyFileToDirUri(file2, createFile);
            }
            return i;
        }
        Uri createFile2 = createFile(uri, file.getName(), null);
        String str = this.TAG;
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException(
                    "copyFileToFileUri not exist or not a File srcFile : " + file);
        }
        if (createFile2 == null) {
            throw new IllegalArgumentException(
                    "copyFileToFileUri invalid dstFileUri : " + createFile2);
        }
        try {
            BufferedOutputStream bufferedOutputStream =
                    new BufferedOutputStream(this.contentResolver.openOutputStream(createFile2));
            try {
                boolean copyFileToStream =
                        this.fileUtil.copyFileToStream(file, bufferedOutputStream);
                if (copyFileToStream) {
                    try {
                        z = file.delete();
                    } catch (SecurityException e) {
                        Log.w(
                                str,
                                String.format(
                                        Locale.ENGLISH,
                                        "copyFileToFileUri delete SecurityException [%s]",
                                        file),
                                e);
                    }
                }
                bufferedOutputStream.close();
                Log.i(
                        str,
                        String.format(
                                Locale.ENGLISH,
                                "copyFileToFileUri src[%s], dst[%s], needDelSrc[%b], delRes[%b]",
                                file,
                                createFile2,
                                Boolean.TRUE,
                                Boolean.valueOf(z)));
                return copyFileToStream ? 1 : 0;
            } finally {
            }
        } catch (IOException e2) {
            String format =
                    String.format(
                            Locale.ENGLISH,
                            "copyFileToFileUri src[%s], dst[%s]",
                            file,
                            createFile2);
            Log.w(str, format, e2);
            throw new IOException(format, e2);
        }
    }

    public final Uri createFile(Uri uri, String str, String str2) {
        Uri uri2;
        String str3 = this.TAG;
        try {
            uri2 = DocumentsContract.createDocument(this.contentResolver, uri, str2, str);
        } catch (Exception e) {
            Log.e(str3, "createFile : " + str, e);
            uri2 = null;
        }
        Log.i(
                str3,
                String.format(
                        "createFile : %s, Document Uri : %s, Created directory Uri : %s",
                        str, uri, uri2));
        return uri2;
    }

    public final List getPathUris(Intent intent) {
        String stringExtra;
        ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("SAVE_PATH_URIS");
        String str = this.TAG;
        if ((stringArrayListExtra == null || stringArrayListExtra.isEmpty())
                && (stringExtra = intent.getStringExtra("SAVE_URIS_FILE")) != null) {
            stringArrayListExtra = new ArrayList<>();
            try {
                JSONArray jSONArray =
                        new JSONObject(this.fileUtil.getDataFromUri(Uri.parse(stringExtra)))
                                .getJSONArray("dataList");
                for (int i = 0; i < jSONArray.length(); i++) {
                    try {
                        stringArrayListExtra.add(jSONArray.getJSONObject(i).getString("docUri"));
                    } catch (Exception e) {
                        Log.e(str, "getPathUris", e);
                    }
                }
            } catch (Exception e2) {
                Log.e(str, "getPathUris", e2);
            }
        }
        if (stringArrayListExtra == null || stringArrayListExtra.isEmpty()) {
            throw new IllegalArgumentException("getPathUris can't find uri info");
        }
        ArrayList arrayList = new ArrayList(stringArrayListExtra.size());
        for (String str2 : stringArrayListExtra) {
            arrayList.add(Uri.parse(str2));
            Log.v(str, "getPathUris [" + str2 + "]");
        }
        Log.i(str, String.format("getPathUris [%d]", Integer.valueOf(arrayList.size())));
        return arrayList;
    }

    public final int move(Intent intent, File file) {
        ArrayList arrayList = (ArrayList) getPathUris(intent);
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException("move can not find dstUri");
        }
        return copyFileToDirUri(file, (Uri) arrayList.get(0));
    }
}
