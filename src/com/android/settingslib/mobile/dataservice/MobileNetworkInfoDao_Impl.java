package com.android.settingslib.mobile.dataservice;

import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.sqlite.SQLiteStatement;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MobileNetworkInfoDao_Impl {
    public final RoomDatabase __db;
    public final AnonymousClass1 __insertAdapterOfMobileNetworkInfoEntity = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.mobile.dataservice.MobileNetworkInfoDao_Impl$1, reason: invalid class name */
    public final class AnonymousClass1 extends EntityInsertAdapter {
        @Override // androidx.room.EntityInsertAdapter
        public final void bind(SQLiteStatement sQLiteStatement, Object obj) {
            MobileNetworkInfoEntity mobileNetworkInfoEntity = (MobileNetworkInfoEntity) obj;
            String str = mobileNetworkInfoEntity.subId;
            if (str == null) {
                sQLiteStatement.bindNull(1);
            } else {
                sQLiteStatement.bindText(1, str);
            }
            sQLiteStatement.bindLong(
                    2, mobileNetworkInfoEntity.isContactDiscoveryEnabled ? 1L : 0L);
            sQLiteStatement.bindLong(
                    3, mobileNetworkInfoEntity.isContactDiscoveryVisible ? 1L : 0L);
            sQLiteStatement.bindLong(4, mobileNetworkInfoEntity.isMobileDataEnabled ? 1L : 0L);
            sQLiteStatement.bindLong(5, mobileNetworkInfoEntity.isCdmaOptions ? 1L : 0L);
            sQLiteStatement.bindLong(6, mobileNetworkInfoEntity.isGsmOptions ? 1L : 0L);
            sQLiteStatement.bindLong(7, mobileNetworkInfoEntity.isWorldMode ? 1L : 0L);
            sQLiteStatement.bindLong(
                    8, mobileNetworkInfoEntity.shouldDisplayNetworkSelectOptions ? 1L : 0L);
            sQLiteStatement.bindLong(9, mobileNetworkInfoEntity.isTdscdmaSupported ? 1L : 0L);
            sQLiteStatement.bindLong(10, mobileNetworkInfoEntity.activeNetworkIsCellular ? 1L : 0L);
            sQLiteStatement.bindLong(
                    11, mobileNetworkInfoEntity.showToggleForPhysicalSim ? 1L : 0L);
            sQLiteStatement.bindLong(12, mobileNetworkInfoEntity.isDataRoamingEnabled ? 1L : 0L);
        }

        @Override // androidx.room.EntityInsertAdapter
        public final String createQuery() {
            return "INSERT OR REPLACE INTO `MobileNetworkInfo`"
                       + " (`subId`,`isContactDiscoveryEnabled`,`isContactDiscoveryVisible`,`isMobileDataEnabled`,`isCdmaOptions`,`isGsmOptions`,`isWorldMode`,`shouldDisplayNetworkSelectOptions`,`isTdscdmaSupported`,`activeNetworkIsCellular`,`showToggleForPhysicalSim`,`isDataRoamingEnabled`)"
                       + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        }
    }

    public MobileNetworkInfoDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
    }
}
