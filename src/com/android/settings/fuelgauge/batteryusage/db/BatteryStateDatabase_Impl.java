package com.android.settings.fuelgauge.batteryusage.db;

import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteKt;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.sec.ims.settings.ImsSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryStateDatabase_Impl extends BatteryStateDatabase {
    public volatile AppUsageEventDao_Impl _appUsageEventDao;
    public volatile BatteryEventDao_Impl _batteryEventDao;
    public volatile BatteryReattributeDao_Impl _batteryReattributeDao;
    public volatile BatteryStateDao_Impl _batteryStateDao;
    public volatile BatteryUsageSlotDao_Impl _batteryUsageSlotDao;

    @Override // com.android.settings.fuelgauge.batteryusage.db.BatteryStateDatabase
    public final AppUsageEventDao_Impl appUsageEventDao() {
        AppUsageEventDao_Impl appUsageEventDao_Impl;
        if (this._appUsageEventDao != null) {
            return this._appUsageEventDao;
        }
        synchronized (this) {
            try {
                if (this._appUsageEventDao == null) {
                    this._appUsageEventDao = new AppUsageEventDao_Impl(this);
                }
                appUsageEventDao_Impl = this._appUsageEventDao;
            } catch (Throwable th) {
                throw th;
            }
        }
        return appUsageEventDao_Impl;
    }

    @Override // com.android.settings.fuelgauge.batteryusage.db.BatteryStateDatabase
    public final BatteryEventDao_Impl batteryEventDao() {
        BatteryEventDao_Impl batteryEventDao_Impl;
        if (this._batteryEventDao != null) {
            return this._batteryEventDao;
        }
        synchronized (this) {
            try {
                if (this._batteryEventDao == null) {
                    this._batteryEventDao = new BatteryEventDao_Impl(this);
                }
                batteryEventDao_Impl = this._batteryEventDao;
            } catch (Throwable th) {
                throw th;
            }
        }
        return batteryEventDao_Impl;
    }

    @Override // com.android.settings.fuelgauge.batteryusage.db.BatteryStateDatabase
    public final BatteryReattributeDao batteryReattributeDao() {
        BatteryReattributeDao_Impl batteryReattributeDao_Impl;
        if (this._batteryReattributeDao != null) {
            return this._batteryReattributeDao;
        }
        synchronized (this) {
            try {
                if (this._batteryReattributeDao == null) {
                    this._batteryReattributeDao = new BatteryReattributeDao_Impl();
                }
                batteryReattributeDao_Impl = this._batteryReattributeDao;
            } catch (Throwable th) {
                throw th;
            }
        }
        return batteryReattributeDao_Impl;
    }

    @Override // com.android.settings.fuelgauge.batteryusage.db.BatteryStateDatabase
    public final BatteryStateDao_Impl batteryStateDao() {
        BatteryStateDao_Impl batteryStateDao_Impl;
        if (this._batteryStateDao != null) {
            return this._batteryStateDao;
        }
        synchronized (this) {
            try {
                if (this._batteryStateDao == null) {
                    this._batteryStateDao = new BatteryStateDao_Impl(this);
                }
                batteryStateDao_Impl = this._batteryStateDao;
            } catch (Throwable th) {
                throw th;
            }
        }
        return batteryStateDao_Impl;
    }

    @Override // com.android.settings.fuelgauge.batteryusage.db.BatteryStateDatabase
    public final BatteryUsageSlotDao_Impl batteryUsageSlotDao() {
        BatteryUsageSlotDao_Impl batteryUsageSlotDao_Impl;
        if (this._batteryUsageSlotDao != null) {
            return this._batteryUsageSlotDao;
        }
        synchronized (this) {
            try {
                if (this._batteryUsageSlotDao == null) {
                    this._batteryUsageSlotDao = new BatteryUsageSlotDao_Impl(this);
                }
                batteryUsageSlotDao_Impl = this._batteryUsageSlotDao;
            } catch (Throwable th) {
                throw th;
            }
        }
        return batteryUsageSlotDao_Impl;
    }

    @Override // androidx.room.RoomDatabase
    public final InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(
                this,
                new HashMap(0),
                new HashMap(0),
                "AppUsageEventEntity",
                "BatteryEventEntity",
                "BatteryState",
                "BatteryUsageSlotEntity",
                "BatteryReattributeEntity");
    }

    @Override // androidx.room.RoomDatabase
    public final RoomOpenDelegate createOpenDelegate() {
        return new RoomOpenDelegate() { // from class:
            // com.android.settings.fuelgauge.batteryusage.db.BatteryStateDatabase_Impl.1
            @Override // androidx.room.RoomOpenDelegate
            public final void createAllTables(SQLiteConnection sQLiteConnection) {
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE TABLE IF NOT EXISTS `AppUsageEventEntity` (`mId` INTEGER PRIMARY"
                            + " KEY AUTOINCREMENT NOT NULL, `uid` INTEGER NOT NULL, `userId`"
                            + " INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `appUsageEventType`"
                            + " INTEGER NOT NULL, `packageName` TEXT, `instanceId` INTEGER NOT"
                            + " NULL, `taskRootPackageName` TEXT)");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE TABLE IF NOT EXISTS `BatteryEventEntity` (`mId` INTEGER PRIMARY KEY"
                            + " AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL,"
                            + " `batteryEventType` INTEGER NOT NULL, `batteryLevel` INTEGER NOT"
                            + " NULL)");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE TABLE IF NOT EXISTS `BatteryState` (`mId` INTEGER PRIMARY KEY"
                            + " AUTOINCREMENT NOT NULL, `uid` INTEGER NOT NULL, `userId` INTEGER"
                            + " NOT NULL, `packageName` TEXT, `timestamp` INTEGER NOT NULL,"
                            + " `consumerType` INTEGER NOT NULL, `isFullChargeCycleStart` INTEGER"
                            + " NOT NULL, `batteryInformation` TEXT, `batteryInformationDebug`"
                            + " TEXT)");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE TABLE IF NOT EXISTS `BatteryUsageSlotEntity` (`mId` INTEGER PRIMARY"
                            + " KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL,"
                            + " `batteryUsageSlot` TEXT)");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE TABLE IF NOT EXISTS `BatteryReattributeEntity` (`timestampStart`"
                            + " INTEGER NOT NULL, `timestampEnd` INTEGER NOT NULL,"
                            + " `reattributeData` TEXT, PRIMARY KEY(`timestampStart`))");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY"
                            + " KEY,identity_hash TEXT)");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42,"
                            + " '451abfef5ba692400e08ae29f1eef551')");
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void dropAllTables(SQLiteConnection sQLiteConnection) {
                SQLiteKt.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS `AppUsageEventEntity`");
                SQLiteKt.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS `BatteryEventEntity`");
                SQLiteKt.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS `BatteryState`");
                SQLiteKt.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS `BatteryUsageSlotEntity`");
                SQLiteKt.execSQL(
                        sQLiteConnection, "DROP TABLE IF EXISTS `BatteryReattributeEntity`");
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void onOpen(SQLiteConnection sQLiteConnection) {
                BatteryStateDatabase_Impl.this.internalInitInvalidationTracker(sQLiteConnection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void onPreMigrate(SQLiteConnection sQLiteConnection) {
                DBUtil.dropFtsSyncTriggers(sQLiteConnection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public final RoomOpenDelegate.ValidationResult onValidateSchema(
                    SQLiteConnection sQLiteConnection) {
                HashMap hashMap = new HashMap(8);
                hashMap.put("mId", new TableInfo.Column("mId", "INTEGER", true, 1, null, 1));
                hashMap.put(
                        NetworkAnalyticsConstants.DataPoints.UID,
                        new TableInfo.Column(
                                NetworkAnalyticsConstants.DataPoints.UID,
                                "INTEGER",
                                true,
                                0,
                                null,
                                1));
                hashMap.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        PhoneRestrictionPolicy.TIMESTAMP,
                        new TableInfo.Column(
                                PhoneRestrictionPolicy.TIMESTAMP, "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "appUsageEventType",
                        new TableInfo.Column("appUsageEventType", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "packageName",
                        new TableInfo.Column(
                                "packageName", ImsSettings.TYPE_TEXT, false, 0, null, 1));
                hashMap.put(
                        "instanceId",
                        new TableInfo.Column("instanceId", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "taskRootPackageName",
                        new TableInfo.Column(
                                "taskRootPackageName", ImsSettings.TYPE_TEXT, false, 0, null, 1));
                TableInfo tableInfo =
                        new TableInfo(
                                "AppUsageEventEntity", hashMap, new HashSet(0), new HashSet(0));
                TableInfo read = TableInfo.read(sQLiteConnection, "AppUsageEventEntity");
                if (!tableInfo.equals(read)) {
                    return new RoomOpenDelegate.ValidationResult(
                            "AppUsageEventEntity(com.android.settings.fuelgauge.batteryusage.db.AppUsageEventEntity).\n"
                                + " Expected:\n"
                                    + tableInfo
                                    + "\n Found:\n"
                                    + read,
                            false);
                }
                HashMap hashMap2 = new HashMap(4);
                hashMap2.put("mId", new TableInfo.Column("mId", "INTEGER", true, 1, null, 1));
                hashMap2.put(
                        PhoneRestrictionPolicy.TIMESTAMP,
                        new TableInfo.Column(
                                PhoneRestrictionPolicy.TIMESTAMP, "INTEGER", true, 0, null, 1));
                hashMap2.put(
                        "batteryEventType",
                        new TableInfo.Column("batteryEventType", "INTEGER", true, 0, null, 1));
                hashMap2.put(
                        "batteryLevel",
                        new TableInfo.Column("batteryLevel", "INTEGER", true, 0, null, 1));
                TableInfo tableInfo2 =
                        new TableInfo(
                                "BatteryEventEntity", hashMap2, new HashSet(0), new HashSet(0));
                TableInfo read2 = TableInfo.read(sQLiteConnection, "BatteryEventEntity");
                if (!tableInfo2.equals(read2)) {
                    return new RoomOpenDelegate.ValidationResult(
                            "BatteryEventEntity(com.android.settings.fuelgauge.batteryusage.db.BatteryEventEntity).\n"
                                + " Expected:\n"
                                    + tableInfo2
                                    + "\n Found:\n"
                                    + read2,
                            false);
                }
                HashMap hashMap3 = new HashMap(9);
                hashMap3.put("mId", new TableInfo.Column("mId", "INTEGER", true, 1, null, 1));
                hashMap3.put(
                        NetworkAnalyticsConstants.DataPoints.UID,
                        new TableInfo.Column(
                                NetworkAnalyticsConstants.DataPoints.UID,
                                "INTEGER",
                                true,
                                0,
                                null,
                                1));
                hashMap3.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "packageName",
                        new TableInfo.Column(
                                "packageName", ImsSettings.TYPE_TEXT, false, 0, null, 1));
                hashMap3.put(
                        PhoneRestrictionPolicy.TIMESTAMP,
                        new TableInfo.Column(
                                PhoneRestrictionPolicy.TIMESTAMP, "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "consumerType",
                        new TableInfo.Column("consumerType", "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "isFullChargeCycleStart",
                        new TableInfo.Column(
                                "isFullChargeCycleStart", "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "batteryInformation",
                        new TableInfo.Column(
                                "batteryInformation", ImsSettings.TYPE_TEXT, false, 0, null, 1));
                hashMap3.put(
                        "batteryInformationDebug",
                        new TableInfo.Column(
                                "batteryInformationDebug",
                                ImsSettings.TYPE_TEXT,
                                false,
                                0,
                                null,
                                1));
                TableInfo tableInfo3 =
                        new TableInfo("BatteryState", hashMap3, new HashSet(0), new HashSet(0));
                TableInfo read3 = TableInfo.read(sQLiteConnection, "BatteryState");
                if (!tableInfo3.equals(read3)) {
                    return new RoomOpenDelegate.ValidationResult(
                            "BatteryState(com.android.settings.fuelgauge.batteryusage.db.BatteryState).\n"
                                + " Expected:\n"
                                    + tableInfo3
                                    + "\n Found:\n"
                                    + read3,
                            false);
                }
                HashMap hashMap4 = new HashMap(3);
                hashMap4.put("mId", new TableInfo.Column("mId", "INTEGER", true, 1, null, 1));
                hashMap4.put(
                        PhoneRestrictionPolicy.TIMESTAMP,
                        new TableInfo.Column(
                                PhoneRestrictionPolicy.TIMESTAMP, "INTEGER", true, 0, null, 1));
                hashMap4.put(
                        "batteryUsageSlot",
                        new TableInfo.Column(
                                "batteryUsageSlot", ImsSettings.TYPE_TEXT, false, 0, null, 1));
                TableInfo tableInfo4 =
                        new TableInfo(
                                "BatteryUsageSlotEntity", hashMap4, new HashSet(0), new HashSet(0));
                TableInfo read4 = TableInfo.read(sQLiteConnection, "BatteryUsageSlotEntity");
                if (!tableInfo4.equals(read4)) {
                    return new RoomOpenDelegate.ValidationResult(
                            "BatteryUsageSlotEntity(com.android.settings.fuelgauge.batteryusage.db.BatteryUsageSlotEntity).\n"
                                + " Expected:\n"
                                    + tableInfo4
                                    + "\n Found:\n"
                                    + read4,
                            false);
                }
                HashMap hashMap5 = new HashMap(3);
                hashMap5.put(
                        "timestampStart",
                        new TableInfo.Column("timestampStart", "INTEGER", true, 1, null, 1));
                hashMap5.put(
                        "timestampEnd",
                        new TableInfo.Column("timestampEnd", "INTEGER", true, 0, null, 1));
                hashMap5.put(
                        "reattributeData",
                        new TableInfo.Column(
                                "reattributeData", ImsSettings.TYPE_TEXT, false, 0, null, 1));
                TableInfo tableInfo5 =
                        new TableInfo(
                                "BatteryReattributeEntity",
                                hashMap5,
                                new HashSet(0),
                                new HashSet(0));
                TableInfo read5 = TableInfo.read(sQLiteConnection, "BatteryReattributeEntity");
                if (tableInfo5.equals(read5)) {
                    return new RoomOpenDelegate.ValidationResult(null, true);
                }
                return new RoomOpenDelegate.ValidationResult(
                        "BatteryReattributeEntity(com.android.settings.fuelgauge.batteryusage.db.BatteryReattributeEntity).\n"
                            + " Expected:\n"
                                + tableInfo5
                                + "\n Found:\n"
                                + read5,
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
        hashMap.put(AppUsageEventDao_Impl.class, Collections.emptyList());
        hashMap.put(BatteryEventDao_Impl.class, Collections.emptyList());
        hashMap.put(BatteryStateDao_Impl.class, Collections.emptyList());
        hashMap.put(BatteryUsageSlotDao_Impl.class, Collections.emptyList());
        hashMap.put(BatteryReattributeDao.class, Collections.emptyList());
        return hashMap;
    }
}
