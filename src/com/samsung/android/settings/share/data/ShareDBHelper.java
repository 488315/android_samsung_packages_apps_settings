package com.samsung.android.settings.share.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ShareDBHelper extends SQLiteOpenHelper {
    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS ranked_apps (id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " package_name TEXT, activity_name TEXT, userId INTEGER, label TEXT)");
        sQLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS all_apps (id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " last_locale TEXT, package_name TEXT, activity_name TEXT, userId INTEGER,"
                    + " main_label TEXT, sub_label TEXT)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ranked_apps");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS all_apps");
        onCreate(sQLiteDatabase);
    }
}
