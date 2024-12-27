package com.samsung.android.settings.eternal.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import com.samsung.android.knox.accounts.Account;
import com.samsung.android.settings.eternal.log.EternalFileLog;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.NotImplementedError;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DatabaseHelper extends SQLiteOpenHelper {
    public static DatabaseHelper instance;
    public final String tag;

    public DatabaseHelper(Context context) {
        super(context, "deferred_restore_data.db", (SQLiteDatabase.CursorFactory) null, 1);
        this.tag = "Eternal/DatabaseHelper";
    }

    public static void bootstrapDB(SQLiteDatabase sQLiteDatabase) {
        createTable(
                sQLiteDatabase,
                "backup_scene",
                GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
                "data",
                Account.IS_DEFAULT);
        createTable(sQLiteDatabase, "skip_policy", "data");
        createTable(sQLiteDatabase, "general_info", "data");
        createTable(
                sQLiteDatabase,
                "source_info",
                "Uid",
                "DTDVersion",
                "DeviceType",
                "Manufacturer",
                "OSVersion",
                "OneUIVersion",
                "PackageList",
                "RequestFrom",
                "RestoreViaFastTrack",
                "Version");
    }

    public static void createTable(SQLiteDatabase sQLiteDatabase, String str, String... strArr) {
        String m = ComposerKt$$ExternalSyntheticOutline0.m("CREATE TABLE ", str, "(");
        for (String str2 : strArr) {
            m = ((Object) m) + str2;
            if (!Intrinsics.areEqual(ArraysKt___ArraysKt.last(strArr), str2)) {
                m = ((Object) m) + ",";
            }
        }
        sQLiteDatabase.execSQL(((Object) m) + ");");
    }

    public final void dropTable(String str) {
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS ".concat(str));
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase != null) {
            bootstrapDB(sQLiteDatabase);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    public final Cursor query(String str) {
        try {
            Cursor query = getReadableDatabase().query(str, null, null, null, null, null, null);
            Intrinsics.checkNotNullExpressionValue(query, "query(...)");
            return query;
        } catch (Exception e) {
            EternalFileLog.e(this.tag, "query(" + str + ") - " + e.getMessage());
            return null;
        }
    }
}
