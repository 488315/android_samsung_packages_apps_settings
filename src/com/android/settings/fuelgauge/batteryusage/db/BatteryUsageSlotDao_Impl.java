package com.android.settings.fuelgauge.batteryusage.db;

import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.sqlite.SQLiteStatement;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryUsageSlotDao_Impl {
    public final RoomDatabase __db;
    public final AnonymousClass1 __insertAdapterOfBatteryUsageSlotEntity = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batteryusage.db.BatteryUsageSlotDao_Impl$1, reason: invalid class name */
    public final class AnonymousClass1 extends EntityInsertAdapter {
        @Override // androidx.room.EntityInsertAdapter
        public final void bind(SQLiteStatement sQLiteStatement, Object obj) {
            BatteryUsageSlotEntity batteryUsageSlotEntity = (BatteryUsageSlotEntity) obj;
            sQLiteStatement.bindLong(1, batteryUsageSlotEntity.mId);
            sQLiteStatement.bindLong(2, batteryUsageSlotEntity.timestamp);
            String str = batteryUsageSlotEntity.batteryUsageSlot;
            if (str == null) {
                sQLiteStatement.bindNull(3);
            } else {
                sQLiteStatement.bindText(3, str);
            }
        }

        @Override // androidx.room.EntityInsertAdapter
        public final String createQuery() {
            return "INSERT OR REPLACE INTO `BatteryUsageSlotEntity`"
                       + " (`mId`,`timestamp`,`batteryUsageSlot`) VALUES (nullif(?, 0),?,?)";
        }
    }

    public BatteryUsageSlotDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
    }
}
