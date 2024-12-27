package com.android.settingslib.mobile.dataservice;

import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.sqlite.SQLiteStatement;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UiccInfoDao_Impl {
    public final RoomDatabase __db;
    public final AnonymousClass1 __insertAdapterOfUiccInfoEntity = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.mobile.dataservice.UiccInfoDao_Impl$1, reason: invalid class name */
    public final class AnonymousClass1 extends EntityInsertAdapter {
        @Override // androidx.room.EntityInsertAdapter
        public final void bind(SQLiteStatement sQLiteStatement, Object obj) {
            UiccInfoEntity uiccInfoEntity = (UiccInfoEntity) obj;
            String str = uiccInfoEntity.subId;
            if (str == null) {
                sQLiteStatement.bindNull(1);
            } else {
                sQLiteStatement.bindText(1, str);
            }
            String str2 = uiccInfoEntity.physicalSlotIndex;
            if (str2 == null) {
                sQLiteStatement.bindNull(2);
            } else {
                sQLiteStatement.bindText(2, str2);
            }
            sQLiteStatement.bindLong(3, uiccInfoEntity.logicalSlotIndex);
            sQLiteStatement.bindLong(4, uiccInfoEntity.cardId);
            sQLiteStatement.bindLong(5, uiccInfoEntity.isEuicc ? 1L : 0L);
            sQLiteStatement.bindLong(
                    6, uiccInfoEntity.isMultipleEnabledProfilesSupported ? 1L : 0L);
            sQLiteStatement.bindLong(7, uiccInfoEntity.cardState);
            sQLiteStatement.bindLong(8, uiccInfoEntity.isRemovable ? 1L : 0L);
            sQLiteStatement.bindLong(9, uiccInfoEntity.isActive ? 1L : 0L);
            sQLiteStatement.bindLong(10, uiccInfoEntity.portIndex);
        }

        @Override // androidx.room.EntityInsertAdapter
        public final String createQuery() {
            return "INSERT OR REPLACE INTO `uiccInfo`"
                       + " (`sudId`,`physicalSlotIndex`,`logicalSlotIndex`,`cardId`,`isEuicc`,`isMultipleEnabledProfilesSupported`,`cardState`,`isRemovable`,`isActive`,`portIndex`)"
                       + " VALUES (?,?,?,?,?,?,?,?,?,?)";
        }
    }

    public UiccInfoDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
    }
}
