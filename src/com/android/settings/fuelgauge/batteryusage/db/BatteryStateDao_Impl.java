package com.android.settings.fuelgauge.batteryusage.db;

import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.sqlite.SQLiteStatement;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryStateDao_Impl {
    public final RoomDatabase __db;
    public final AnonymousClass1 __insertAdapterOfBatteryState = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batteryusage.db.BatteryStateDao_Impl$1, reason: invalid class name */
    public final class AnonymousClass1 extends EntityInsertAdapter {
        @Override // androidx.room.EntityInsertAdapter
        public final void bind(SQLiteStatement sQLiteStatement, Object obj) {
            BatteryState batteryState = (BatteryState) obj;
            sQLiteStatement.bindLong(1, batteryState.mId);
            sQLiteStatement.bindLong(2, batteryState.uid);
            sQLiteStatement.bindLong(3, batteryState.userId);
            String str = batteryState.packageName;
            if (str == null) {
                sQLiteStatement.bindNull(4);
            } else {
                sQLiteStatement.bindText(4, str);
            }
            sQLiteStatement.bindLong(5, batteryState.timestamp);
            sQLiteStatement.bindLong(6, batteryState.consumerType);
            sQLiteStatement.bindLong(7, batteryState.isFullChargeCycleStart ? 1L : 0L);
            String str2 = batteryState.batteryInformation;
            if (str2 == null) {
                sQLiteStatement.bindNull(8);
            } else {
                sQLiteStatement.bindText(8, str2);
            }
            String str3 = batteryState.batteryInformationDebug;
            if (str3 == null) {
                sQLiteStatement.bindNull(9);
            } else {
                sQLiteStatement.bindText(9, str3);
            }
        }

        @Override // androidx.room.EntityInsertAdapter
        public final String createQuery() {
            return "INSERT OR REPLACE INTO `BatteryState`"
                       + " (`mId`,`uid`,`userId`,`packageName`,`timestamp`,`consumerType`,`isFullChargeCycleStart`,`batteryInformation`,`batteryInformationDebug`)"
                       + " VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
        }
    }

    public BatteryStateDao_Impl(BatteryStateDatabase batteryStateDatabase) {
        this.__db = batteryStateDatabase;
    }
}
