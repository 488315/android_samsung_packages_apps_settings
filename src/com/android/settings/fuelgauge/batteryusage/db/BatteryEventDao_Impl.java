package com.android.settings.fuelgauge.batteryusage.db;

import android.database.Cursor;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.StringUtil;
import androidx.sqlite.SQLiteStatement;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryEventDao_Impl {
    public final RoomDatabase __db;
    public final AnonymousClass1 __insertAdapterOfBatteryEventEntity = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batteryusage.db.BatteryEventDao_Impl$1, reason: invalid class name */
    public final class AnonymousClass1 extends EntityInsertAdapter {
        @Override // androidx.room.EntityInsertAdapter
        public final void bind(SQLiteStatement sQLiteStatement, Object obj) {
            BatteryEventEntity batteryEventEntity = (BatteryEventEntity) obj;
            sQLiteStatement.bindLong(1, batteryEventEntity.mId);
            sQLiteStatement.bindLong(2, batteryEventEntity.timestamp);
            sQLiteStatement.bindLong(3, batteryEventEntity.batteryEventType);
            sQLiteStatement.bindLong(4, batteryEventEntity.batteryLevel);
        }

        @Override // androidx.room.EntityInsertAdapter
        public final String createQuery() {
            return "INSERT OR REPLACE INTO `BatteryEventEntity`"
                       + " (`mId`,`timestamp`,`batteryEventType`,`batteryLevel`) VALUES (nullif(?,"
                       + " 0),?,?,?)";
        }
    }

    public BatteryEventDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
    }

    public final Cursor getAllAfter(List list, long j) {
        StringBuilder m =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                        "SELECT * FROM BatteryEventEntity WHERE timestamp >= ? AND batteryEventType"
                            + " IN (");
        int size = list == null ? 1 : list.size();
        StringUtil.appendPlaceholders(size, m);
        m.append(") ORDER BY timestamp DESC");
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire(size + 1, m.toString());
        acquire.bindLong(1, j);
        int i = 2;
        if (list == null) {
            acquire.bindNull(2);
        } else {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (((Integer) it.next()) == null) {
                    acquire.bindNull(i);
                } else {
                    acquire.bindLong(i, r7.intValue());
                }
                i++;
            }
        }
        RoomDatabase roomDatabase = this.__db;
        roomDatabase.getClass();
        return roomDatabase.query(acquire, null);
    }
}
