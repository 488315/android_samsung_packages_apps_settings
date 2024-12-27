package com.samsung.android.settingslib.bluetooth.scsp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.SystemProperties;
import android.util.Log;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ScspUtils {
    public static final String FILE_EXTENSION_SVG;
    public static final String FILE_NAME;
    public static final String FILE_NAME_AURA_CAST;
    public static final String FILE_NAME_CASE;
    public static final String FILE_NAME_LEFT;
    public static final String FILE_NAME_RIGHT;
    public static final String FILE_NAME_TABLE;
    public static final String FILE_PATH_DEVICE_ID;
    public static final String FILE_PATH_ICON_INDEX;
    public static final String FILE_PATH_ROOT;

    static {
        StringBuilder sb = new StringBuilder("bluetooth");
        String str = File.separator;
        FILE_PATH_ROOT = BackStackRecord$$ExternalSyntheticOutline0.m(sb, str, "scsp", str);
        FILE_PATH_DEVICE_ID =
                AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("deviceid", str);
        FILE_PATH_ICON_INDEX =
                AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("iconindex", str);
        FILE_NAME = "icon";
        FILE_NAME_AURA_CAST = "icon_auracast";
        FILE_NAME_LEFT = "icon_l";
        FILE_NAME_RIGHT = "icon_r";
        FILE_NAME_CASE = "icon_case";
        FILE_EXTENSION_SVG = ".svg";
        FILE_NAME_TABLE = "table.txt";
    }

    public static BitmapDrawable bitmapFromSVG(Context context, FileInputStream fileInputStream) {
        try {
            SVG fromInputStream = SVG.getFromInputStream(fileInputStream);
            float dimension = context.getResources().getDimension(R.dimen.bt_nearby_icon_size);
            int i =
                    (int)
                            ((context.getResources().getDisplayMetrics().densityDpi / 160.0f)
                                    * dimension);
            int dimension2 =
                    (int)
                            ((context.getResources().getDisplayMetrics().densityDpi / 160.0f)
                                    * context.getResources()
                                            .getDimension(R.dimen.bt_nearby_icon_size));
            fromInputStream.setDocumentWidth(i);
            fromInputStream.setDocumentHeight(dimension2);
            Bitmap createBitmap = Bitmap.createBitmap(i, dimension2, Bitmap.Config.ARGB_8888);
            fromInputStream.renderToCanvas(new Canvas(createBitmap));
            return new BitmapDrawable(context.getResources(), createBitmap);
        } catch (SVGParseException e) {
            Log.e("ScspUtils", "bitmapFromSVG: SVGParseException.", e);
            return null;
        } catch (NullPointerException e2) {
            Log.e("ScspUtils", "bitmapFromSVG: NullPointerException.", e2);
            return null;
        }
    }

    public static String byteToString(byte b) {
        StringBuilder sb = new StringBuilder(2);
        sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        return sb.toString();
    }

    public static void deleteDirectory(File file) {
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                Log.d("ScspUtils", "deleteDirectory: listFiles is null");
                return;
            }
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    deleteDirectory(file2);
                } else {
                    try {
                        file2.delete();
                    } catch (Exception e) {
                        Log.d("ScspUtils", "deleteDirectory: file delete fail");
                        e.printStackTrace();
                    }
                }
            }
            try {
                file.delete();
            } catch (Exception e2) {
                Log.d("ScspUtils", "deleteDirectory: directory delete fail");
                e2.printStackTrace();
            }
        }
    }

    public static String getFileDirPath(Context context) {
        return getFileRootPath(context)
                + Integer.toString(SystemProperties.getInt("ro.build.version.oneui", 0))
                + File.separator;
    }

    public static String getFileRootPath(Context context) {
        return context.getApplicationContext().getFilesDir() + File.separator + FILE_PATH_ROOT;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.drawable.BitmapDrawable getIcon(
            android.content.Context r6, java.lang.String r7) {
        /*
            java.lang.String r0 = "getIcon: fail close"
            java.lang.String r1 = "getIcon: fail, resourcePath = "
            java.lang.String r2 = "getIcon: resourcePath = "
            java.lang.String r3 = "ScspUtils"
            androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0.m(r2, r7, r3)
            r2 = 0
            java.io.File r4 = new java.io.File     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L33
            r4.<init>(r7)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L33
            boolean r4 = r4.exists()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L33
            if (r4 == 0) goto L54
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L33
            r4.<init>(r7)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L33
            android.graphics.drawable.BitmapDrawable r6 = bitmapFromSVG(r6, r4)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2f
            r4.close()     // Catch: java.lang.Exception -> L24
            goto L2b
        L24:
            r7 = move-exception
            android.util.Log.d(r3, r0)
            r7.printStackTrace()
        L2b:
            return r6
        L2c:
            r6 = move-exception
            r2 = r4
            goto L55
        L2f:
            r6 = move-exception
            goto L35
        L31:
            r6 = move-exception
            goto L55
        L33:
            r6 = move-exception
            r4 = r2
        L35:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2c
            r5.<init>(r1)     // Catch: java.lang.Throwable -> L2c
            r5.append(r7)     // Catch: java.lang.Throwable -> L2c
            java.lang.String r7 = r5.toString()     // Catch: java.lang.Throwable -> L2c
            android.util.Log.d(r3, r7)     // Catch: java.lang.Throwable -> L2c
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L2c
            if (r4 == 0) goto L54
            r4.close()     // Catch: java.lang.Exception -> L4d
            goto L54
        L4d:
            r6 = move-exception
            android.util.Log.d(r3, r0)
            r6.printStackTrace()
        L54:
            return r2
        L55:
            if (r2 == 0) goto L62
            r2.close()     // Catch: java.lang.Exception -> L5b
            goto L62
        L5b:
            r7 = move-exception
            android.util.Log.d(r3, r0)
            r7.printStackTrace()
        L62:
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settingslib.bluetooth.scsp.ScspUtils.getIcon(android.content.Context,"
                    + " java.lang.String):android.graphics.drawable.BitmapDrawable");
    }

    public static boolean makeAllResourceData(Context context, Uri uri) {
        Log.d(
                "ScspUtils",
                "makeAllResourceData: uri.getLastPathSegment() = " + uri.getLastPathSegment());
        if (!saveFileFromUri(context, uri, getFileDirPath(context))) {
            Log.d("ScspUtils", "makeAllResourceData: save fail. uri = " + uri.toString());
            return false;
        }
        String str = getFileDirPath(context) + uri.getLastPathSegment();
        String fileDirPath = getFileDirPath(context);
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                ZipInputStream zipInputStream =
                        new ZipInputStream(new BufferedInputStream(fileInputStream));
                while (true) {
                    try {
                        ZipEntry nextEntry = zipInputStream.getNextEntry();
                        if (nextEntry == null) {
                            zipInputStream.close();
                            fileInputStream.close();
                            return true;
                        }
                        String str2 = fileDirPath + File.separator + nextEntry.getName();
                        if (nextEntry.isDirectory()) {
                            new File(str2).mkdirs();
                        } else {
                            File file = new File(str2);
                            File parentFile = file.getParentFile();
                            if (!parentFile.exists()) {
                                parentFile.mkdirs();
                            }
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            try {
                                byte[] bArr = new byte[1024];
                                while (true) {
                                    int read = zipInputStream.read(bArr);
                                    if (read == -1) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, read);
                                }
                                fileOutputStream.close();
                            } finally {
                            }
                        }
                    } finally {
                    }
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException e) {
            Log.d("ScspUtils", "unzip: unzip fail, FileNotFoundException");
            e.printStackTrace();
            Log.d("ScspUtils", "makeAllResourceData: unzip fail. uri = " + uri.toString());
            return false;
        } catch (Exception e2) {
            Log.d("ScspUtils", "unzip: unzip fail, Exception");
            e2.printStackTrace();
            Log.d("ScspUtils", "makeAllResourceData: unzip fail. uri = " + uri.toString());
            return false;
        }
    }

    public static String makeByteArrayDir(byte[] bArr) {
        if (bArr == null) {
            Log.e("ScspUtils", "makeByteArrayDir: byte array is null");
            return null;
        }
        if (bArr.length < 2) {
            Log.e("ScspUtils", "makeByteArrayDir: byte length is short. length: " + bArr.length);
        }
        try {
            return byteToString(bArr[0]) + "_" + byteToString(bArr[1]);
        } catch (NumberFormatException e) {
            Log.e("ScspUtils", "makeByteArrayDir: NumberFormatException.", e);
            return null;
        }
    }

    public static boolean saveFileFromUri(Context context, Uri uri, String str) {
        Log.d(
                "ScspUtils",
                "saveFileFromUri: uri.getLastPathSegment() = " + uri.getLastPathSegment());
        File file = new File(str, uri.getLastPathSegment());
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = openInputStream.read(bArr);
                        if (read == -1) {
                            fileOutputStream.close();
                            openInputStream.close();
                            Log.d("ScspUtils", "saveFileFromUri: true");
                            return true;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    }
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Log.e("TAG", "saveFileFromUri: Error saving file", e);
            return false;
        }
    }
}
