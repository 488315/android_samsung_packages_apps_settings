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
public final class AppUsageEventDao_Impl {
    public final RoomDatabase __db;
    public final AnonymousClass1 __insertAdapterOfAppUsageEventEntity = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batteryusage.db.AppUsageEventDao_Impl$1, reason: invalid class name */
    public final class AnonymousClass1 extends EntityInsertAdapter {
        @Override // androidx.room.EntityInsertAdapter
        public final void bind(SQLiteStatement sQLiteStatement, Object obj) {
            AppUsageEventEntity appUsageEventEntity = (AppUsageEventEntity) obj;
            sQLiteStatement.bindLong(1, appUsageEventEntity.mId);
            sQLiteStatement.bindLong(2, appUsageEventEntity.uid);
            sQLiteStatement.bindLong(3, appUsageEventEntity.userId);
            sQLiteStatement.bindLong(4, appUsageEventEntity.timestamp);
            sQLiteStatement.bindLong(5, appUsageEventEntity.appUsageEventType);
            String str = appUsageEventEntity.packageName;
            if (str == null) {
                sQLiteStatement.bindNull(6);
            } else {
                sQLiteStatement.bindText(6, str);
            }
            sQLiteStatement.bindLong(7, appUsageEventEntity.instanceId);
            String str2 = appUsageEventEntity.taskRootPackageName;
            if (str2 == null) {
                sQLiteStatement.bindNull(8);
            } else {
                sQLiteStatement.bindText(8, str2);
            }
        }

        @Override // androidx.room.EntityInsertAdapter
        public final String createQuery() {
            return "INSERT OR REPLACE INTO `AppUsageEventEntity`"
                       + " (`mId`,`uid`,`userId`,`timestamp`,`appUsageEventType`,`packageName`,`instanceId`,`taskRootPackageName`)"
                       + " VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
        }
    }

    public AppUsageEventDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
    }

    public final Cursor getAllForUsersAfter(List list, long j) {
        StringBuilder m =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                        "SELECT * FROM AppUsageEventEntity WHERE timestamp >= ? AND userId IN (");
        int size = list.size();
        StringUtil.appendPlaceholders(size, m);
        m.append(") ORDER BY timestamp ASC");
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire(size + 1, m.toString());
        acquire.bindLong(1, j);
        Iterator it = list.iterator();
        int i = 2;
        while (it.hasNext()) {
            Long l = (Long) it.next();
            if (l == null) {
                acquire.bindNull(i);
            } else {
                acquire.bindLong(i, l.longValue());
            }
            i++;
        }
        RoomDatabase roomDatabase = this.__db;
        roomDatabase.getClass();
        return roomDatabase.query(acquire, null);
    }
}
