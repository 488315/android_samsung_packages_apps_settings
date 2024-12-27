package com.samsung.android.server.uwb.bigdata.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class UwbDbHelper extends SQLiteOpenHelper {
    public static UwbDbHelper INSTANCE;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        Log.d("UwbDbHelper", "create Table");
        sQLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS uwb_state(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " update_time BIGINT, current_state INTEGER);");
        sQLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS uwb_usage(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " session_id BIGINT, package_name TEXT, service_type INTEGER, channel_no"
                    + " INTEGER, ranging_duration_time BIGINT, update_time BIGINT);");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {}
}
