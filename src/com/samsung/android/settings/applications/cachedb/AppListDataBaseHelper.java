package com.samsung.android.settings.applications.cachedb;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppListDataBaseHelper extends SQLiteOpenHelper {
    public static AppListDataBaseHelper sSingleton;

    public static synchronized AppListDataBaseHelper getInstance(Context context) {
        AppListDataBaseHelper appListDataBaseHelper;
        synchronized (AppListDataBaseHelper.class) {
            try {
                if (sSingleton == null) {
                    sSingleton =
                            new AppListDataBaseHelper(
                                    context.getApplicationContext(), "applist.db", null, 3);
                }
                appListDataBaseHelper = sSingleton;
            } catch (Throwable th) {
                throw th;
            }
        }
        return appListDataBaseHelper;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onConfigure(SQLiteDatabase sQLiteDatabase) {
        super.onConfigure(sQLiteDatabase);
        sQLiteDatabase.enableWriteAheadLogging();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE appinfo(package_name, app_title, last_updated)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            if (sQLiteDatabase == null) {
                Log.e("AppListDataBaseHelper", "reConstruct() failed - sqLiteDatabase is null");
            } else {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS appinfo");
                sQLiteDatabase.execSQL(
                        "CREATE TABLE appinfo(package_name, app_title, last_updated)");
                Log.i(
                        "AppListDataBaseHelper",
                        "reConstruct() took " + (System.currentTimeMillis() - currentTimeMillis));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
