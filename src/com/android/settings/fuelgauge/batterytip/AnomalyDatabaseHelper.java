package com.android.settings.fuelgauge.batterytip;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AnomalyDatabaseHelper extends SQLiteOpenHelper {
    public static AnomalyDatabaseHelper sSingleton;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(
                "CREATE TABLE anomaly(uid INTEGER NOT NULL, package_name TEXT, anomaly_type INTEGER"
                    + " NOT NULL, anomaly_state INTEGER NOT NULL, time_stamp_ms INTEGER NOT NULL, "
                    + " PRIMARY KEY (uid,anomaly_type,anomaly_state,time_stamp_ms))");
        sQLiteDatabase.execSQL(
                "CREATE TABLE action(uid INTEGER NOT NULL, package_name TEXT, action_type INTEGER"
                    + " NOT NULL, time_stamp_ms INTEGER NOT NULL,  PRIMARY KEY"
                    + " (action_type,uid,package_name))");
        Log.i("BatteryDatabaseHelper", "Bootstrapped database");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Log.w(
                "BatteryDatabaseHelper",
                CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0.m(
                        "Detected schema version '",
                        "'. Index needs to be rebuilt for schema version '",
                        i,
                        i2,
                        "'."));
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS anomaly");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS action");
        sQLiteDatabase.execSQL(
                "CREATE TABLE anomaly(uid INTEGER NOT NULL, package_name TEXT, anomaly_type INTEGER"
                    + " NOT NULL, anomaly_state INTEGER NOT NULL, time_stamp_ms INTEGER NOT NULL, "
                    + " PRIMARY KEY (uid,anomaly_type,anomaly_state,time_stamp_ms))");
        sQLiteDatabase.execSQL(
                "CREATE TABLE action(uid INTEGER NOT NULL, package_name TEXT, action_type INTEGER"
                    + " NOT NULL, time_stamp_ms INTEGER NOT NULL,  PRIMARY KEY"
                    + " (action_type,uid,package_name))");
        Log.i("BatteryDatabaseHelper", "Bootstrapped database");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < 5) {
            Log.w(
                    "BatteryDatabaseHelper",
                    CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0
                            .m(
                                    "Detected schema version '",
                                    "'. Index needs to be rebuilt for schema version '",
                                    i,
                                    i2,
                                    "'."));
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS anomaly");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS action");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE anomaly(uid INTEGER NOT NULL, package_name TEXT, anomaly_type"
                        + " INTEGER NOT NULL, anomaly_state INTEGER NOT NULL, time_stamp_ms INTEGER"
                        + " NOT NULL,  PRIMARY KEY"
                        + " (uid,anomaly_type,anomaly_state,time_stamp_ms))");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE action(uid INTEGER NOT NULL, package_name TEXT, action_type"
                        + " INTEGER NOT NULL, time_stamp_ms INTEGER NOT NULL,  PRIMARY KEY"
                        + " (action_type,uid,package_name))");
            Log.i("BatteryDatabaseHelper", "Bootstrapped database");
        }
    }
}
