package com.samsung.android.settings.analyzestorage.external.bnr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class RestoreDatabase {
    public static String[] getColumnListByTableSchema(SQLiteDatabase sQLiteDatabase) {
        String[] strArr = null;
        Cursor rawQuery =
                sQLiteDatabase.rawQuery("SELECT * FROM bnr_unused_apps WHERE 0 LIMIT 0", null);
        if (rawQuery != null) {
            try {
                strArr = rawQuery.getColumnNames();
            } catch (Throwable th) {
                try {
                    rawQuery.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (rawQuery != null) {
            rawQuery.close();
        }
        Log.d(
                "RestoreDatabase",
                "getColumnListByTableSchema() ] tableName : bnr_unused_apps , Columns[] : "
                        + Arrays.toString(strArr));
        return strArr;
    }
}
