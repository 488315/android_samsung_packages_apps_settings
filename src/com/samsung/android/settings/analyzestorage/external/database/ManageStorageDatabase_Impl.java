package com.samsung.android.settings.analyzestorage.external.database;

import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteKt;

import com.samsung.android.settings.analyzestorage.data.database.dao.UnusedAppBnRInfoDao_Impl;
import com.sec.ims.settings.ImsSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ManageStorageDatabase_Impl extends ManageStorageDatabase {
    public volatile UnusedAppBnRInfoDao_Impl _unusedAppBnRInfoDao;

    @Override // androidx.room.RoomDatabase
    public final InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "bnr_unused_apps");
    }

    @Override // androidx.room.RoomDatabase
    public final RoomOpenDelegate createOpenDelegate() {
        return new RoomOpenDelegate() { // from class:
            // com.samsung.android.settings.analyzestorage.external.database.ManageStorageDatabase_Impl.1
            @Override // androidx.room.RoomOpenDelegate
            public final void createAllTables(SQLiteConnection sQLiteConnection) {
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE TABLE IF NOT EXISTS `bnr_unused_apps` (`package_name` TEXT NOT"
                            + " NULL, PRIMARY KEY(`package_name`))");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE UNIQUE INDEX IF NOT EXISTS `index_bnr_unused_apps_package_name` ON"
                            + " `bnr_unused_apps` (`package_name`)");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY"
                            + " KEY,identity_hash TEXT)");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42,"
                            + " '87b5b6b885298e979cc2e7e21befa106')");
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void dropAllTables(SQLiteConnection sQLiteConnection) {
                SQLiteKt.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS `bnr_unused_apps`");
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void onOpen(SQLiteConnection sQLiteConnection) {
                ManageStorageDatabase_Impl.this.internalInitInvalidationTracker(sQLiteConnection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void onPreMigrate(SQLiteConnection sQLiteConnection) {
                DBUtil.dropFtsSyncTriggers(sQLiteConnection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public final RoomOpenDelegate.ValidationResult onValidateSchema(
                    SQLiteConnection sQLiteConnection) {
                HashMap hashMap = new HashMap(1);
                hashMap.put(
                        "package_name",
                        new TableInfo.Column(
                                "package_name", ImsSettings.TYPE_TEXT, true, 1, null, 1));
                HashSet hashSet = new HashSet(0);
                HashSet hashSet2 = new HashSet(1);
                hashSet2.add(
                        new TableInfo.Index(
                                "index_bnr_unused_apps_package_name",
                                true,
                                Arrays.asList("package_name"),
                                Arrays.asList("ASC")));
                TableInfo tableInfo = new TableInfo("bnr_unused_apps", hashMap, hashSet, hashSet2);
                TableInfo read = TableInfo.read(sQLiteConnection, "bnr_unused_apps");
                if (tableInfo.equals(read)) {
                    return new RoomOpenDelegate.ValidationResult(null, true);
                }
                return new RoomOpenDelegate.ValidationResult(
                        "bnr_unused_apps(com.samsung.android.settings.analyzestorage.data.model.UnusedAppBnRInfo).\n"
                            + " Expected:\n"
                                + tableInfo
                                + "\n Found:\n"
                                + read,
                        false);
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void onCreate(SQLiteConnection sQLiteConnection) {}

            @Override // androidx.room.RoomOpenDelegate
            public final void onPostMigrate(SQLiteConnection sQLiteConnection) {}
        };
    }

    @Override // androidx.room.RoomDatabase
    public final List getAutoMigrations(Map map) {
        return new ArrayList();
    }

    @Override // androidx.room.RoomDatabase
    public final Set getRequiredAutoMigrationSpecs() {
        return new HashSet();
    }

    @Override // androidx.room.RoomDatabase
    public final Map getRequiredTypeConverters() {
        HashMap hashMap = new HashMap();
        hashMap.put(UnusedAppBnRInfoDao_Impl.class, Collections.emptyList());
        return hashMap;
    }

    @Override // com.samsung.android.settings.analyzestorage.external.database.ManageStorageDatabase
    public final UnusedAppBnRInfoDao_Impl unusedAppBnRInfoDao() {
        UnusedAppBnRInfoDao_Impl unusedAppBnRInfoDao_Impl;
        if (this._unusedAppBnRInfoDao != null) {
            return this._unusedAppBnRInfoDao;
        }
        synchronized (this) {
            try {
                if (this._unusedAppBnRInfoDao == null) {
                    this._unusedAppBnRInfoDao = new UnusedAppBnRInfoDao_Impl(this);
                }
                unusedAppBnRInfoDao_Impl = this._unusedAppBnRInfoDao;
            } catch (Throwable th) {
                throw th;
            }
        }
        return unusedAppBnRInfoDao_Impl;
    }
}
