package com.android.settingslib.mobile.dataservice;

import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteKt;

import com.samsung.android.knox.accounts.Account;
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
/* loaded from: classes2.dex */
public final class MobileNetworkDatabase_Impl extends MobileNetworkDatabase {
    public volatile MobileNetworkInfoDao_Impl _mobileNetworkInfoDao;
    public volatile SubscriptionInfoDao_Impl _subscriptionInfoDao;
    public volatile UiccInfoDao_Impl _uiccInfoDao;

    @Override // androidx.room.RoomDatabase
    public final InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(
                this,
                new HashMap(0),
                new HashMap(0),
                "subscriptionInfo",
                "uiccInfo",
                "MobileNetworkInfo");
    }

    @Override // androidx.room.RoomDatabase
    public final RoomOpenDelegate createOpenDelegate() {
        return new RoomOpenDelegate() { // from class:
            // com.android.settingslib.mobile.dataservice.MobileNetworkDatabase_Impl.1
            @Override // androidx.room.RoomOpenDelegate
            public final void createAllTables(SQLiteConnection sQLiteConnection) {
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE TABLE IF NOT EXISTS `subscriptionInfo` (`sudId` TEXT NOT NULL,"
                            + " `simSlotIndex` INTEGER NOT NULL, `carrierId` INTEGER NOT NULL,"
                            + " `displayName` TEXT, `carrierName` TEXT, `dataRoaming` INTEGER NOT"
                            + " NULL, `mcc` TEXT, `mnc` TEXT, `countryIso` TEXT, `isEmbedded`"
                            + " INTEGER NOT NULL, `cardId` INTEGER NOT NULL, `portIndex` INTEGER"
                            + " NOT NULL, `isOpportunistic` INTEGER NOT NULL, `groupUUID` TEXT,"
                            + " `subscriptionType` INTEGER NOT NULL, `uniqueName` TEXT,"
                            + " `isSubscriptionVisible` INTEGER NOT NULL, `getFormattedPhoneNumber`"
                            + " TEXT, `isFirstRemovableSubscription` INTEGER NOT NULL,"
                            + " `isDefaultSubscriptionSelection` INTEGER NOT NULL,"
                            + " `isValidSubscription` INTEGER NOT NULL, `isUsableSubscription`"
                            + " INTEGER NOT NULL, `isActiveSubscription` INTEGER NOT NULL,"
                            + " `isAvailableSubscription` INTEGER NOT NULL,"
                            + " `isActiveDataSubscriptionId` INTEGER NOT NULL, PRIMARY"
                            + " KEY(`sudId`))");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE INDEX IF NOT EXISTS `index_subscriptionInfo_sudId` ON"
                            + " `subscriptionInfo` (`sudId`)");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE TABLE IF NOT EXISTS `uiccInfo` (`sudId` TEXT NOT NULL,"
                            + " `physicalSlotIndex` TEXT NOT NULL, `logicalSlotIndex` INTEGER NOT"
                            + " NULL, `cardId` INTEGER NOT NULL, `isEuicc` INTEGER NOT NULL,"
                            + " `isMultipleEnabledProfilesSupported` INTEGER NOT NULL, `cardState`"
                            + " INTEGER NOT NULL, `isRemovable` INTEGER NOT NULL, `isActive`"
                            + " INTEGER NOT NULL, `portIndex` INTEGER NOT NULL, PRIMARY"
                            + " KEY(`sudId`))");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE INDEX IF NOT EXISTS `index_uiccInfo_sudId` ON `uiccInfo`"
                            + " (`sudId`)");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE TABLE IF NOT EXISTS `MobileNetworkInfo` (`subId` TEXT NOT NULL,"
                            + " `isContactDiscoveryEnabled` INTEGER NOT NULL,"
                            + " `isContactDiscoveryVisible` INTEGER NOT NULL, `isMobileDataEnabled`"
                            + " INTEGER NOT NULL, `isCdmaOptions` INTEGER NOT NULL, `isGsmOptions`"
                            + " INTEGER NOT NULL, `isWorldMode` INTEGER NOT NULL,"
                            + " `shouldDisplayNetworkSelectOptions` INTEGER NOT NULL,"
                            + " `isTdscdmaSupported` INTEGER NOT NULL, `activeNetworkIsCellular`"
                            + " INTEGER NOT NULL, `showToggleForPhysicalSim` INTEGER NOT NULL,"
                            + " `isDataRoamingEnabled` INTEGER NOT NULL, PRIMARY KEY(`subId`))");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE INDEX IF NOT EXISTS `index_MobileNetworkInfo_subId` ON"
                            + " `MobileNetworkInfo` (`subId`)");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY"
                            + " KEY,identity_hash TEXT)");
                SQLiteKt.execSQL(
                        sQLiteConnection,
                        "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42,"
                            + " 'c1bc052ff01a823b0f4818883195eee2')");
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void dropAllTables(SQLiteConnection sQLiteConnection) {
                SQLiteKt.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS `subscriptionInfo`");
                SQLiteKt.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS `uiccInfo`");
                SQLiteKt.execSQL(sQLiteConnection, "DROP TABLE IF EXISTS `MobileNetworkInfo`");
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void onOpen(SQLiteConnection sQLiteConnection) {
                MobileNetworkDatabase_Impl.this.internalInitInvalidationTracker(sQLiteConnection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public final void onPreMigrate(SQLiteConnection sQLiteConnection) {
                DBUtil.dropFtsSyncTriggers(sQLiteConnection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public final RoomOpenDelegate.ValidationResult onValidateSchema(
                    SQLiteConnection sQLiteConnection) {
                HashMap hashMap = new HashMap(25);
                hashMap.put(
                        "sudId",
                        new TableInfo.Column("sudId", ImsSettings.TYPE_TEXT, true, 1, null, 1));
                hashMap.put(
                        "simSlotIndex",
                        new TableInfo.Column("simSlotIndex", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "carrierId",
                        new TableInfo.Column("carrierId", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        Account.DISPLAY_NAME,
                        new TableInfo.Column(
                                Account.DISPLAY_NAME, ImsSettings.TYPE_TEXT, false, 0, null, 1));
                hashMap.put(
                        "carrierName",
                        new TableInfo.Column(
                                "carrierName", ImsSettings.TYPE_TEXT, false, 0, null, 1));
                hashMap.put(
                        "dataRoaming",
                        new TableInfo.Column("dataRoaming", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "mcc",
                        new TableInfo.Column("mcc", ImsSettings.TYPE_TEXT, false, 0, null, 1));
                hashMap.put(
                        "mnc",
                        new TableInfo.Column("mnc", ImsSettings.TYPE_TEXT, false, 0, null, 1));
                hashMap.put(
                        "countryIso",
                        new TableInfo.Column(
                                "countryIso", ImsSettings.TYPE_TEXT, false, 0, null, 1));
                hashMap.put(
                        "isEmbedded",
                        new TableInfo.Column("isEmbedded", "INTEGER", true, 0, null, 1));
                hashMap.put("cardId", new TableInfo.Column("cardId", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "portIndex",
                        new TableInfo.Column("portIndex", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "isOpportunistic",
                        new TableInfo.Column("isOpportunistic", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "groupUUID",
                        new TableInfo.Column(
                                "groupUUID", ImsSettings.TYPE_TEXT, false, 0, null, 1));
                hashMap.put(
                        "subscriptionType",
                        new TableInfo.Column("subscriptionType", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "uniqueName",
                        new TableInfo.Column(
                                "uniqueName", ImsSettings.TYPE_TEXT, false, 0, null, 1));
                hashMap.put(
                        "isSubscriptionVisible",
                        new TableInfo.Column("isSubscriptionVisible", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "getFormattedPhoneNumber",
                        new TableInfo.Column(
                                "getFormattedPhoneNumber",
                                ImsSettings.TYPE_TEXT,
                                false,
                                0,
                                null,
                                1));
                hashMap.put(
                        "isFirstRemovableSubscription",
                        new TableInfo.Column(
                                "isFirstRemovableSubscription", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "isDefaultSubscriptionSelection",
                        new TableInfo.Column(
                                "isDefaultSubscriptionSelection", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "isValidSubscription",
                        new TableInfo.Column("isValidSubscription", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "isUsableSubscription",
                        new TableInfo.Column("isUsableSubscription", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "isActiveSubscription",
                        new TableInfo.Column("isActiveSubscription", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "isAvailableSubscription",
                        new TableInfo.Column(
                                "isAvailableSubscription", "INTEGER", true, 0, null, 1));
                hashMap.put(
                        "isActiveDataSubscriptionId",
                        new TableInfo.Column(
                                "isActiveDataSubscriptionId", "INTEGER", true, 0, null, 1));
                HashSet hashSet = new HashSet(0);
                HashSet hashSet2 = new HashSet(1);
                hashSet2.add(
                        new TableInfo.Index(
                                "index_subscriptionInfo_sudId",
                                false,
                                Arrays.asList("sudId"),
                                Arrays.asList("ASC")));
                TableInfo tableInfo = new TableInfo("subscriptionInfo", hashMap, hashSet, hashSet2);
                TableInfo read = TableInfo.read(sQLiteConnection, "subscriptionInfo");
                if (!tableInfo.equals(read)) {
                    return new RoomOpenDelegate.ValidationResult(
                            "subscriptionInfo(com.android.settingslib.mobile.dataservice.SubscriptionInfoEntity).\n"
                                + " Expected:\n"
                                    + tableInfo
                                    + "\n Found:\n"
                                    + read,
                            false);
                }
                HashMap hashMap2 = new HashMap(10);
                hashMap2.put(
                        "sudId",
                        new TableInfo.Column("sudId", ImsSettings.TYPE_TEXT, true, 1, null, 1));
                hashMap2.put(
                        "physicalSlotIndex",
                        new TableInfo.Column(
                                "physicalSlotIndex", ImsSettings.TYPE_TEXT, true, 0, null, 1));
                hashMap2.put(
                        "logicalSlotIndex",
                        new TableInfo.Column("logicalSlotIndex", "INTEGER", true, 0, null, 1));
                hashMap2.put("cardId", new TableInfo.Column("cardId", "INTEGER", true, 0, null, 1));
                hashMap2.put(
                        "isEuicc", new TableInfo.Column("isEuicc", "INTEGER", true, 0, null, 1));
                hashMap2.put(
                        "isMultipleEnabledProfilesSupported",
                        new TableInfo.Column(
                                "isMultipleEnabledProfilesSupported", "INTEGER", true, 0, null, 1));
                hashMap2.put(
                        "cardState",
                        new TableInfo.Column("cardState", "INTEGER", true, 0, null, 1));
                hashMap2.put(
                        "isRemovable",
                        new TableInfo.Column("isRemovable", "INTEGER", true, 0, null, 1));
                hashMap2.put(
                        "isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, 1));
                hashMap2.put(
                        "portIndex",
                        new TableInfo.Column("portIndex", "INTEGER", true, 0, null, 1));
                HashSet hashSet3 = new HashSet(0);
                HashSet hashSet4 = new HashSet(1);
                hashSet4.add(
                        new TableInfo.Index(
                                "index_uiccInfo_sudId",
                                false,
                                Arrays.asList("sudId"),
                                Arrays.asList("ASC")));
                TableInfo tableInfo2 = new TableInfo("uiccInfo", hashMap2, hashSet3, hashSet4);
                TableInfo read2 = TableInfo.read(sQLiteConnection, "uiccInfo");
                if (!tableInfo2.equals(read2)) {
                    return new RoomOpenDelegate.ValidationResult(
                            "uiccInfo(com.android.settingslib.mobile.dataservice.UiccInfoEntity).\n"
                                + " Expected:\n"
                                    + tableInfo2
                                    + "\n Found:\n"
                                    + read2,
                            false);
                }
                HashMap hashMap3 = new HashMap(12);
                hashMap3.put(
                        "subId",
                        new TableInfo.Column("subId", ImsSettings.TYPE_TEXT, true, 1, null, 1));
                hashMap3.put(
                        "isContactDiscoveryEnabled",
                        new TableInfo.Column(
                                "isContactDiscoveryEnabled", "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "isContactDiscoveryVisible",
                        new TableInfo.Column(
                                "isContactDiscoveryVisible", "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "isMobileDataEnabled",
                        new TableInfo.Column("isMobileDataEnabled", "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "isCdmaOptions",
                        new TableInfo.Column("isCdmaOptions", "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "isGsmOptions",
                        new TableInfo.Column("isGsmOptions", "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "isWorldMode",
                        new TableInfo.Column("isWorldMode", "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "shouldDisplayNetworkSelectOptions",
                        new TableInfo.Column(
                                "shouldDisplayNetworkSelectOptions", "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "isTdscdmaSupported",
                        new TableInfo.Column("isTdscdmaSupported", "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "activeNetworkIsCellular",
                        new TableInfo.Column(
                                "activeNetworkIsCellular", "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "showToggleForPhysicalSim",
                        new TableInfo.Column(
                                "showToggleForPhysicalSim", "INTEGER", true, 0, null, 1));
                hashMap3.put(
                        "isDataRoamingEnabled",
                        new TableInfo.Column("isDataRoamingEnabled", "INTEGER", true, 0, null, 1));
                HashSet hashSet5 = new HashSet(0);
                HashSet hashSet6 = new HashSet(1);
                hashSet6.add(
                        new TableInfo.Index(
                                "index_MobileNetworkInfo_subId",
                                false,
                                Arrays.asList("subId"),
                                Arrays.asList("ASC")));
                TableInfo tableInfo3 =
                        new TableInfo("MobileNetworkInfo", hashMap3, hashSet5, hashSet6);
                TableInfo read3 = TableInfo.read(sQLiteConnection, "MobileNetworkInfo");
                if (tableInfo3.equals(read3)) {
                    return new RoomOpenDelegate.ValidationResult(null, true);
                }
                return new RoomOpenDelegate.ValidationResult(
                        "MobileNetworkInfo(com.android.settingslib.mobile.dataservice.MobileNetworkInfoEntity).\n"
                            + " Expected:\n"
                                + tableInfo3
                                + "\n Found:\n"
                                + read3,
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
        hashMap.put(SubscriptionInfoDao_Impl.class, Collections.emptyList());
        hashMap.put(UiccInfoDao_Impl.class, Collections.emptyList());
        hashMap.put(MobileNetworkInfoDao_Impl.class, Collections.emptyList());
        return hashMap;
    }

    @Override // com.android.settingslib.mobile.dataservice.MobileNetworkDatabase
    public final MobileNetworkInfoDao_Impl mMobileNetworkInfoDao() {
        MobileNetworkInfoDao_Impl mobileNetworkInfoDao_Impl;
        if (this._mobileNetworkInfoDao != null) {
            return this._mobileNetworkInfoDao;
        }
        synchronized (this) {
            try {
                if (this._mobileNetworkInfoDao == null) {
                    this._mobileNetworkInfoDao = new MobileNetworkInfoDao_Impl(this);
                }
                mobileNetworkInfoDao_Impl = this._mobileNetworkInfoDao;
            } catch (Throwable th) {
                throw th;
            }
        }
        return mobileNetworkInfoDao_Impl;
    }

    @Override // com.android.settingslib.mobile.dataservice.MobileNetworkDatabase
    public final SubscriptionInfoDao_Impl mSubscriptionInfoDao() {
        SubscriptionInfoDao_Impl subscriptionInfoDao_Impl;
        if (this._subscriptionInfoDao != null) {
            return this._subscriptionInfoDao;
        }
        synchronized (this) {
            try {
                if (this._subscriptionInfoDao == null) {
                    this._subscriptionInfoDao = new SubscriptionInfoDao_Impl(this);
                }
                subscriptionInfoDao_Impl = this._subscriptionInfoDao;
            } catch (Throwable th) {
                throw th;
            }
        }
        return subscriptionInfoDao_Impl;
    }

    @Override // com.android.settingslib.mobile.dataservice.MobileNetworkDatabase
    public final UiccInfoDao_Impl mUiccInfoDao() {
        UiccInfoDao_Impl uiccInfoDao_Impl;
        if (this._uiccInfoDao != null) {
            return this._uiccInfoDao;
        }
        synchronized (this) {
            try {
                if (this._uiccInfoDao == null) {
                    this._uiccInfoDao = new UiccInfoDao_Impl(this);
                }
                uiccInfoDao_Impl = this._uiccInfoDao;
            } catch (Throwable th) {
                throw th;
            }
        }
        return uiccInfoDao_Impl;
    }
}
