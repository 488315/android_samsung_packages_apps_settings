package com.samsung.android.settings.actions;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ActionsDatabaseHelper extends SQLiteOpenHelper {
    public static ActionsDatabaseHelper sSingleton;
    public final Context mContext;

    public ActionsDatabaseHelper(Context context) {
        super(context, "actions_index.db", (SQLiteDatabase.CursorFactory) null, 1);
        this.mContext = context;
    }

    public static synchronized ActionsDatabaseHelper getInstance(Context context) {
        ActionsDatabaseHelper actionsDatabaseHelper;
        synchronized (ActionsDatabaseHelper.class) {
            try {
                if (sSingleton == null) {
                    sSingleton = new ActionsDatabaseHelper(context.getApplicationContext());
                }
                actionsDatabaseHelper = sSingleton;
            } catch (Throwable th) {
                throw th;
            }
        }
        return actionsDatabaseHelper;
    }

    public final boolean isActionDataIndexed() {
        return this.mContext
                        .getSharedPreferences("actions_shared_prefs", 0)
                        .getBoolean(Build.FINGERPRINT, false)
                && this.mContext
                        .getSharedPreferences("actions_shared_prefs", 0)
                        .getBoolean(Locale.getDefault().toString(), false);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(
                "CREATE VIRTUAL TABLE actions_index USING fts4(key, title, summary, screentitle,"
                    + " keywords, icon, fragment, controller, action_type, recoverable,"
                    + " restore_key, target_fragment, payload);");
        Log.d("Command/ActionsDatabaseHelper", "Created databases");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i > 1) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "Reconstructing DB from ", "to ", i, i2, "Command/ActionsDatabaseHelper");
            reconstruct(sQLiteDatabase);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < 1) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "Reconstructing DB from ", " to ", i, i2, "Command/ActionsDatabaseHelper");
            reconstruct(sQLiteDatabase);
        }
    }

    public final void reconstruct(SQLiteDatabase sQLiteDatabase) {
        Log.i("Command/ActionsDatabaseHelper", "reconstruct()");
        this.mContext.getSharedPreferences("actions_shared_prefs", 0).edit().clear().apply();
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS actions_index");
        sQLiteDatabase.execSQL(
                "CREATE VIRTUAL TABLE actions_index USING fts4(key, title, summary, screentitle,"
                    + " keywords, icon, fragment, controller, action_type, recoverable,"
                    + " restore_key, target_fragment, payload);");
        Log.d("Command/ActionsDatabaseHelper", "Created databases");
    }

    public final void setIndexedState() {
        this.mContext.getSharedPreferences("actions_shared_prefs", 0).edit().clear().commit();
        this.mContext
                .getSharedPreferences("actions_shared_prefs", 0)
                .edit()
                .putBoolean(Build.FINGERPRINT, true)
                .apply();
        this.mContext
                .getSharedPreferences("actions_shared_prefs", 0)
                .edit()
                .putBoolean(Locale.getDefault().toString(), true)
                .apply();
    }
}
