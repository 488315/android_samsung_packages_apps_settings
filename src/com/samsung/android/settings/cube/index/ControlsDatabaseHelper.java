package com.samsung.android.settings.cube.index;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ControlsDatabaseHelper extends SQLiteOpenHelper {
    public static ControlsDatabaseHelper sSingleton;
    public final Context mContext;

    public ControlsDatabaseHelper(Context context) {
        super(context, "controls_index.db", (SQLiteDatabase.CursorFactory) null, 1);
        this.mContext = context;
    }

    public static synchronized ControlsDatabaseHelper getInstance(Context context) {
        ControlsDatabaseHelper controlsDatabaseHelper;
        synchronized (ControlsDatabaseHelper.class) {
            try {
                if (sSingleton == null) {
                    sSingleton = new ControlsDatabaseHelper(context.getApplicationContext());
                }
                controlsDatabaseHelper = sSingleton;
            } catch (Throwable th) {
                throw th;
            }
        }
        return controlsDatabaseHelper;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(
                "CREATE VIRTUAL TABLE controls_index USING fts4(key, title, summary, screen_title,"
                    + " keywords, icon_uri, fragment, controller, control_type, recoverable,"
                    + " restore_key);");
        Log.d("ControlsDatabaseHelper", "Created databases");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i > 1) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "Reconstructing DB from ", "to ", i, i2, "ControlsDatabaseHelper");
            reconstruct(sQLiteDatabase);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < 1) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "Reconstructing DB from ", " to ", i, i2, "ControlsDatabaseHelper");
            reconstruct(sQLiteDatabase);
        }
    }

    public final void reconstruct(SQLiteDatabase sQLiteDatabase) {
        this.mContext.getSharedPreferences("controls_prefs", 0).edit().clear().apply();
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS controls_index");
        sQLiteDatabase.execSQL(
                "CREATE VIRTUAL TABLE controls_index USING fts4(key, title, summary, screen_title,"
                    + " keywords, icon_uri, fragment, controller, control_type, recoverable,"
                    + " restore_key);");
        Log.d("ControlsDatabaseHelper", "Created databases");
    }

    public final void setIndexedState() {
        this.mContext.getSharedPreferences("controls_prefs", 0).edit().clear().commit();
        this.mContext
                .getSharedPreferences("controls_prefs", 0)
                .edit()
                .putBoolean(Build.FINGERPRINT, true)
                .apply();
        this.mContext
                .getSharedPreferences("controls_prefs", 0)
                .edit()
                .putBoolean(Locale.getDefault().toString(), true)
                .apply();
    }
}
