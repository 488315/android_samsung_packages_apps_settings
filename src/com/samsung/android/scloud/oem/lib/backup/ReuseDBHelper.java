package com.samsung.android.scloud.oem.lib.backup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.scloud.oem.lib.LOG;
import com.samsung.android.scloud.oem.lib.bnr.BNRFile;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ReuseDBHelper extends SQLiteOpenHelper {
    public static ReuseDBHelper INSTANCE;

    public static synchronized ReuseDBHelper getInstance(Context context) {
        ReuseDBHelper reuseDBHelper;
        synchronized (ReuseDBHelper.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new ReuseDBHelper(context, "backup.db", null, 1);
                }
                reuseDBHelper = INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        return reuseDBHelper;
    }

    public final long addReuseFile(String str, BNRFile bNRFile) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sourcekey", str);
        contentValues.put("path", bNRFile.path);
        contentValues.put("complete", Integer.valueOf(bNRFile.isComplete ? 1 : 0));
        String str2 = bNRFile.checksum;
        if (!ApnSettings.MVNO_NONE.equals(str2)) {
            contentValues.put("checksum", str2);
        }
        String str3 = bNRFile.startKey;
        if (!ApnSettings.MVNO_NONE.equals(str3)) {
            contentValues.put("start_key", str3);
        }
        String str4 = bNRFile.nextKey;
        if (!ApnSettings.MVNO_NONE.equals(str4)) {
            contentValues.put("next_key", str4);
        }
        long j = bNRFile.size;
        if (j > 0) {
            contentValues.put("size", Long.valueOf(j));
        }
        long j2 = bNRFile.size;
        if (j2 > 0) {
            contentValues.put("offset", Long.valueOf(j2));
        }
        LOG.i("ReuseDBHelper", "addReuseFile, CV : " + contentValues.toString());
        return getWritableDatabase().insertWithOnConflict("reuse_files", "path", contentValues, 5);
    }

    public final void clearReuseFile(String str) {
        LOG.i("ReuseDBHelper", "clearRestoreFileDB() is called~!, " + str);
        getReadableDatabase()
                .delete(
                        "reuse_files",
                        ComposerKt$$ExternalSyntheticOutline0.m("sourcekey = '", str, "'"),
                        null);
    }

    public final ArrayList getReusePathList(String str) {
        ArrayList arrayList = new ArrayList();
        Cursor query =
                getWritableDatabase()
                        .query(
                                "reuse_files",
                                new String[] {"path"},
                                ComposerKt$$ExternalSyntheticOutline0.m("sourcekey = '", str, "'"),
                                null,
                                null,
                                null,
                                "_id ASC");
        while (query.moveToNext()) {
            arrayList.add(query.getString(query.getColumnIndex("path")));
        }
        LOG.d("ReuseDBHelper", "getReusePathList, pathList : " + arrayList.toString());
        query.close();
        return arrayList;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        LOG.i("ReuseDBHelper", "create TABLE if not exists~! ");
        sQLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS reuse_files(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " sourcekey TEXT NOT NULL, path TEXT UNIQUE NOT NULL, checksum TEXT, offset"
                    + " INTEGER DEFAULT 0, start_key TEXT, next_key TEXT, size INTEGER DEFAULT 0,"
                    + " complete INTEGER DEFAULT 0 );");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < i2) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS reuse_files");
            onCreate(sQLiteDatabase);
        }
    }
}
