package com.samsung.android.settings.easymode;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EasyModeUtils {
    public static String sSamsungKeypadHighContrastSettingsActivity = "";
    public static String sSamsungKeypadInputMethodId = "";
    public static String sSamsungKeypadPackageName = "";
    public static String sSamsungKeypadProvider = "";
    public AccessibilityManager mAccessibilityManager;
    public Context mContext;

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0055, code lost:

       if (r9 != null) goto L33;
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0057, code lost:

       r9.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0061, code lost:

       if (r9 != null) goto L33;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isHighContrastKeyboardOn() {
        /*
            r10 = this;
            java.lang.String r0 = "NAME"
            java.lang.String r1 = ""
            java.lang.String r2 = "high_contrast_keyboard"
            java.lang.String[] r7 = new java.lang.String[]{r2}
            r9 = 0
            android.content.Context r10 = r10.mContext     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            android.content.ContentResolver r3 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            java.lang.String r10 = com.samsung.android.settings.easymode.EasyModeUtils.sSamsungKeypadProvider     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            android.net.Uri r4 = android.net.Uri.parse(r10)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            r6 = 0
            r8 = 0
            r5 = 0
            android.database.Cursor r9 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            if (r9 == 0) goto L55
        L20:
            boolean r10 = r9.moveToNext()     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            if (r10 == 0) goto L55
            int r10 = r9.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            r3 = -1
            if (r10 == r3) goto L20
            java.lang.String r10 = r9.getString(r10)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            if (r10 == 0) goto L20
            boolean r10 = r10.isEmpty()     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            if (r10 != 0) goto L20
            int r10 = r9.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            java.lang.String r10 = r9.getString(r10)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            boolean r10 = r10.equals(r2)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            if (r10 == 0) goto L20
            java.lang.String r10 = "VALUE"
            int r10 = r9.getColumnIndex(r10)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            java.lang.String r10 = r9.getString(r10)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L61
            r1 = r10
            goto L20
        L53:
            r10 = move-exception
            goto L5b
        L55:
            if (r9 == 0) goto L64
        L57:
            r9.close()     // Catch: java.lang.Exception -> L64
            goto L64
        L5b:
            if (r9 == 0) goto L60
            r9.close()     // Catch: java.lang.Exception -> L60
        L60:
            throw r10
        L61:
            if (r9 == 0) goto L64
            goto L57
        L64:
            java.lang.String r10 = "1"
            boolean r10 = r1.equals(r10)
            return r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.easymode.EasyModeUtils.isHighContrastKeyboardOn():boolean");
    }

    public final void putValueToKeyboard(String str) {
        try {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put("high_contrast_keyboard", str);
            contentResolver.update(Uri.parse(sSamsungKeypadProvider), contentValues, null, null);
        } catch (Exception unused) {
            Log.i("EasyModeUtils", "putValueToKeyboard couldn't be executed.");
        }
    }
}
