package com.sec.ims.settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class DebugConfiguration {
    public static final String FAKE_PANI = "fake_pani";
    public static final String FAKE_REG_RESPONSE = "fake_reg_response";
    public static final String REAL_PANI = "real_pani";
    private static final String URIString = "content://com.sec.ims.settings/debugconfig/";

    private DebugConfiguration() {}

    public static String getDebugConfig(Context context, int i, String str, String str2) {
        Cursor query =
                context.getContentResolver()
                        .query(Uri.parse(URIString + i), new String[] {str}, null, null, null);
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    str2 = query.getString(0);
                }
            } catch (Throwable th) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (query != null) {
            query.close();
        }
        return str2;
    }

    public static void setDebugConfig(Context context, int i, String str, String str2) {
        Uri parse = Uri.parse(URIString + i);
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, str2);
        context.getContentResolver().insert(parse, contentValues);
    }

    public static void setDebugConfig(Context context, int i, String str, int i2) {
        Uri parse = Uri.parse(URIString + i);
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, Integer.valueOf(i2));
        context.getContentResolver().insert(parse, contentValues);
    }

    public static int getDebugConfig(Context context, int i, String str, int i2) {
        try {
            return Integer.parseInt(getDebugConfig(context, i, str, ApnSettings.MVNO_NONE));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return i2;
        }
    }

    public static boolean getDebugConfig(Context context, int i, String str, boolean z) {
        Cursor query =
                context.getContentResolver()
                        .query(Uri.parse(URIString + i), new String[] {str}, null, null, null);
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    z = "true".equals(query.getString(0));
                }
            } catch (Throwable th) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (query != null) {
            query.close();
        }
        return z;
    }

    public static void setDebugConfig(Context context, int i, String str, boolean z) {
        Uri parse = Uri.parse(URIString + i);
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, z ? "true" : "false");
        context.getContentResolver().insert(parse, contentValues);
    }
}