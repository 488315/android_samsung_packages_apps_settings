package com.samsung.android.settings.analyzestorage.data.database.dao;

import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.sqlite.SQLiteStatement;

import com.samsung.android.settings.analyzestorage.data.model.UnusedAppBnRInfo;
import com.samsung.android.settings.analyzestorage.external.database.ManageStorageDatabase;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class UnusedAppBnRInfoDao_Impl {
    public final RoomDatabase __db;
    public final AnonymousClass1 __insertAdapterOfUnusedAppBnRInfo = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.analyzestorage.data.database.dao.UnusedAppBnRInfoDao_Impl$1, reason: invalid class name */
    public final class AnonymousClass1 extends EntityInsertAdapter {
        @Override // androidx.room.EntityInsertAdapter
        public final void bind(SQLiteStatement sQLiteStatement, Object obj) {
            String str = ((UnusedAppBnRInfo) obj).mPackageName;
            if (str == null) {
                sQLiteStatement.bindNull(1);
            } else {
                sQLiteStatement.bindText(1, str);
            }
        }

        @Override // androidx.room.EntityInsertAdapter
        public final String createQuery() {
            return "INSERT OR REPLACE INTO `bnr_unused_apps` (`package_name`) VALUES (?)";
        }
    }

    public UnusedAppBnRInfoDao_Impl(ManageStorageDatabase manageStorageDatabase) {
        this.__db = manageStorageDatabase;
    }
}
