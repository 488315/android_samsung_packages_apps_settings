package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.context.sdk.samsunganalytics.DBOpenHelper;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DbManager {
    public final DBOpenHelper dbOpenHelper;
    public final Queue list;

    public DbManager(Context context) {
        DefaultDBOpenHelper defaultDBOpenHelper =
                new DefaultDBOpenHelper(context, "SamsungAnalytics.db", null, 1);
        this.list = new LinkedBlockingQueue();
        this.dbOpenHelper = defaultDBOpenHelper;
        defaultDBOpenHelper
                .getWritableDatabase()
                .execSQL(
                        "CREATE TABLE IF NOT EXISTS logs_v2 (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + " timestamp INTEGER, logtype TEXT, data TEXT)");
        ((DefaultDBOpenHelper) this.dbOpenHelper)
                .getWritableDatabase()
                .delete("logs_v2", "timestamp <= 5", null);
    }

    public final void insert(SimpleLog simpleLog) {
        SQLiteDatabase writableDatabase =
                ((DefaultDBOpenHelper) this.dbOpenHelper).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PhoneRestrictionPolicy.TIMESTAMP, Long.valueOf(simpleLog.timestamp));
        contentValues.put("data", simpleLog.data);
        contentValues.put("logtype", simpleLog.type.getAbbrev());
        writableDatabase.insert("logs_v2", null, contentValues);
    }

    public final Queue select(String str) {
        ((LinkedBlockingQueue) this.list).clear();
        Cursor rawQuery =
                ((DefaultDBOpenHelper) this.dbOpenHelper).getReadableDatabase().rawQuery(str, null);
        while (rawQuery.moveToNext()) {
            SimpleLog simpleLog = new SimpleLog();
            simpleLog._id = rawQuery.getString(rawQuery.getColumnIndex("_id"));
            simpleLog.data = rawQuery.getString(rawQuery.getColumnIndex("data"));
            simpleLog.timestamp =
                    rawQuery.getLong(rawQuery.getColumnIndex(PhoneRestrictionPolicy.TIMESTAMP));
            String string = rawQuery.getString(rawQuery.getColumnIndex("logtype"));
            LogType logType = LogType.DEVICE;
            if (!string.equals(logType.getAbbrev())) {
                logType = LogType.UIX;
            }
            simpleLog.type = logType;
            this.list.add(simpleLog);
        }
        rawQuery.close();
        return this.list;
    }
}
