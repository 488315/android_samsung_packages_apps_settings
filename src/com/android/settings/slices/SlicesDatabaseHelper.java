package com.android.settings.slices;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SlicesDatabaseHelper extends SQLiteOpenHelper {
    public static SlicesDatabaseHelper sSingleton;
    public final Context mContext;

    public SlicesDatabaseHelper(Context context) {
        super(context, "slices_index.db", (SQLiteDatabase.CursorFactory) null, 10);
        this.mContext = context;
    }

    public static synchronized SlicesDatabaseHelper getInstance(Context context) {
        SlicesDatabaseHelper slicesDatabaseHelper;
        synchronized (SlicesDatabaseHelper.class) {
            try {
                if (sSingleton == null) {
                    sSingleton = new SlicesDatabaseHelper(context.getApplicationContext());
                }
                slicesDatabaseHelper = sSingleton;
            } catch (Throwable th) {
                throw th;
            }
        }
        return slicesDatabaseHelper;
    }

    public String getBuildTag() {
        return Build.FINGERPRINT;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(
                "CREATE VIRTUAL TABLE slices_index USING fts4(key, slice_uri, title, summary,"
                    + " screentitle, keywords, icon, fragment, controller, slice_type,"
                    + " unavailable_slice_subtitle, public_slice, highlight_menu, user_restriction"
                    + " INTEGER DEFAULT 0 );");
        Log.d("SlicesDatabaseHelper", "Created databases");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < 10) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "Reconstructing DB from ", " to ", i, i2, "SlicesDatabaseHelper");
            this.mContext.getSharedPreferences("slices_shared_prefs", 0).edit().clear().apply();
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS slices_index");
            sQLiteDatabase.execSQL(
                    "CREATE VIRTUAL TABLE slices_index USING fts4(key, slice_uri, title, summary,"
                        + " screentitle, keywords, icon, fragment, controller, slice_type,"
                        + " unavailable_slice_subtitle, public_slice, highlight_menu,"
                        + " user_restriction INTEGER DEFAULT 0 );");
            Log.d("SlicesDatabaseHelper", "Created databases");
        }
    }
}
