package com.samsung.android.settings.share.data;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.samsung.android.settings.share.structure.DBShareComponent;
import com.samsung.android.settings.share.structure.LabelShareComponent;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ShareDBAdapter {
    public final ShareDBHelper mDBHelper;
    public SQLiteDatabase mDb;

    public ShareDBAdapter(Context context) {
        this.mDBHelper = new ShareDBHelper(context, "sem_share.db", null, 4);
    }

    public final void close() {
        Log.i("ShareDBAdapter", "DB close");
        SQLiteDatabase sQLiteDatabase = this.mDb;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            return;
        }
        this.mDb.close();
    }

    public final void createShareAppTable() {
        SQLiteDatabase sQLiteDatabase = this.mDb;
        this.mDBHelper.getClass();
        sQLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS all_apps (id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " last_locale TEXT, package_name TEXT, activity_name TEXT, userId INTEGER,"
                    + " main_label TEXT, sub_label TEXT)");
    }

    public final List getAllRankedLabelComponents() {
        ArrayList arrayList = new ArrayList();
        Cursor query =
                this.mDb.query(
                        "ranked_apps",
                        new String[] {"id", "package_name", "activity_name", "userId"},
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        while (query.moveToNext()) {
            try {
                arrayList.add(
                        new DBShareComponent(
                                query.getInt(0),
                                query.getInt(3),
                                new ComponentName(query.getString(1), query.getString(2))));
            } catch (Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        query.close();
        return arrayList;
    }

    public final List getAllShareAppComponents() {
        ArrayList arrayList = new ArrayList();
        Cursor query =
                this.mDb.query(
                        "all_apps",
                        new String[] {
                            "id",
                            "last_locale",
                            "package_name",
                            "activity_name",
                            "userId",
                            "main_label",
                            "sub_label"
                        },
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        while (query.moveToNext()) {
            try {
                String string = query.getString(1);
                String string2 = query.getString(2);
                String string3 = query.getString(3);
                int i = query.getInt(4);
                String string4 = query.getString(5);
                String string5 = query.getString(6);
                LabelShareComponent labelShareComponent = new LabelShareComponent();
                labelShareComponent.locale = string;
                labelShareComponent.packageName = string2;
                labelShareComponent.className = string3;
                labelShareComponent.uid = i;
                labelShareComponent.label = string4;
                labelShareComponent.subLabel = string5;
                arrayList.add(labelShareComponent);
            } catch (Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        query.close();
        return arrayList;
    }

    public final void insertShareApps(
            String str, String str2, String str3, int i, String str4, String str5) {
        this.mDb.beginTransaction();
        Cursor query =
                this.mDb.query(
                        "all_apps",
                        new String[] {"package_name"},
                        "package_name",
                        null,
                        null,
                        null,
                        null,
                        null);
        try {
            if (query.getCount() >= 1024) {
                query.close();
                return;
            }
            query.close();
            query =
                    this.mDb.query(
                            "all_apps",
                            new String[] {"package_name"},
                            "last_locale = ? AND package_name = ? AND activity_name = ? AND userId"
                                + " = ? AND main_label = ?AND sub_label = ?",
                            new String[] {str, str2, str3, String.valueOf(i), str4, str5},
                            null,
                            null,
                            null,
                            null);
            try {
                if (query.getCount() >= 1) {
                    query.close();
                    return;
                }
                query.close();
                ContentValues contentValues = new ContentValues();
                contentValues.put("last_locale", str);
                contentValues.put("package_name", str2);
                contentValues.put("activity_name", str3);
                contentValues.put("userId", Integer.valueOf(i));
                contentValues.put("main_label", str4);
                contentValues.put("sub_label", str5);
                this.mDb.insert("all_apps", null, contentValues);
                try {
                    try {
                        this.mDb.setTransactionSuccessful();
                    } catch (IllegalStateException e) {
                        Log.e("ShareDBAdapter", "IllegalStateException:" + e);
                    }
                } finally {
                    this.mDb.endTransaction();
                }
            } finally {
            }
        } finally {
        }
    }

    public final void open() {
        ShareDBHelper shareDBHelper = this.mDBHelper;
        try {
            Log.i("ShareDBAdapter", "DB open");
            this.mDb = shareDBHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            Log.e("ShareDBAdapter", "SQLiteException : " + e);
            this.mDb = shareDBHelper.getReadableDatabase();
        }
    }

    public final void removeAllApps() {
        Cursor query = this.mDb.query("all_apps", null, null, null, null, null, null, null);
        try {
            this.mDb.delete("all_apps", null, null);
            if (query != null) {
                query.close();
            }
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
