package com.android.settingslib.mobile.dataservice;

import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.sqlite.SQLiteStatement;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SubscriptionInfoDao_Impl {
    public final RoomDatabase __db;
    public final AnonymousClass1 __insertAdapterOfSubscriptionInfoEntity = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.mobile.dataservice.SubscriptionInfoDao_Impl$1, reason: invalid class name */
    public final class AnonymousClass1 extends EntityInsertAdapter {
        @Override // androidx.room.EntityInsertAdapter
        public final void bind(SQLiteStatement sQLiteStatement, Object obj) {
            SubscriptionInfoEntity subscriptionInfoEntity = (SubscriptionInfoEntity) obj;
            String str = subscriptionInfoEntity.subId;
            if (str == null) {
                sQLiteStatement.bindNull(1);
            } else {
                sQLiteStatement.bindText(1, str);
            }
            sQLiteStatement.bindLong(2, subscriptionInfoEntity.simSlotIndex);
            sQLiteStatement.bindLong(3, subscriptionInfoEntity.carrierId);
            String str2 = subscriptionInfoEntity.displayName;
            if (str2 == null) {
                sQLiteStatement.bindNull(4);
            } else {
                sQLiteStatement.bindText(4, str2);
            }
            String str3 = subscriptionInfoEntity.carrierName;
            if (str3 == null) {
                sQLiteStatement.bindNull(5);
            } else {
                sQLiteStatement.bindText(5, str3);
            }
            sQLiteStatement.bindLong(6, subscriptionInfoEntity.dataRoaming);
            String str4 = subscriptionInfoEntity.mcc;
            if (str4 == null) {
                sQLiteStatement.bindNull(7);
            } else {
                sQLiteStatement.bindText(7, str4);
            }
            String str5 = subscriptionInfoEntity.mnc;
            if (str5 == null) {
                sQLiteStatement.bindNull(8);
            } else {
                sQLiteStatement.bindText(8, str5);
            }
            String str6 = subscriptionInfoEntity.countryIso;
            if (str6 == null) {
                sQLiteStatement.bindNull(9);
            } else {
                sQLiteStatement.bindText(9, str6);
            }
            sQLiteStatement.bindLong(10, subscriptionInfoEntity.isEmbedded ? 1L : 0L);
            sQLiteStatement.bindLong(11, subscriptionInfoEntity.cardId);
            sQLiteStatement.bindLong(12, subscriptionInfoEntity.portIndex);
            sQLiteStatement.bindLong(13, subscriptionInfoEntity.isOpportunistic ? 1L : 0L);
            String str7 = subscriptionInfoEntity.groupUUID;
            if (str7 == null) {
                sQLiteStatement.bindNull(14);
            } else {
                sQLiteStatement.bindText(14, str7);
            }
            sQLiteStatement.bindLong(15, subscriptionInfoEntity.subscriptionType);
            String str8 = subscriptionInfoEntity.uniqueName;
            if (str8 == null) {
                sQLiteStatement.bindNull(16);
            } else {
                sQLiteStatement.bindText(16, str8);
            }
            sQLiteStatement.bindLong(17, subscriptionInfoEntity.isSubscriptionVisible ? 1L : 0L);
            String str9 = subscriptionInfoEntity.formattedPhoneNumber;
            if (str9 == null) {
                sQLiteStatement.bindNull(18);
            } else {
                sQLiteStatement.bindText(18, str9);
            }
            sQLiteStatement.bindLong(
                    19, subscriptionInfoEntity.isFirstRemovableSubscription ? 1L : 0L);
            sQLiteStatement.bindLong(
                    20, subscriptionInfoEntity.isDefaultSubscriptionSelection ? 1L : 0L);
            sQLiteStatement.bindLong(21, subscriptionInfoEntity.isValidSubscription ? 1L : 0L);
            sQLiteStatement.bindLong(22, subscriptionInfoEntity.isUsableSubscription ? 1L : 0L);
            sQLiteStatement.bindLong(23, subscriptionInfoEntity.isActiveSubscriptionId ? 1L : 0L);
            sQLiteStatement.bindLong(24, subscriptionInfoEntity.isAvailableSubscription ? 1L : 0L);
            sQLiteStatement.bindLong(
                    25, subscriptionInfoEntity.isActiveDataSubscriptionId ? 1L : 0L);
        }

        @Override // androidx.room.EntityInsertAdapter
        public final String createQuery() {
            return "INSERT OR REPLACE INTO `subscriptionInfo`"
                       + " (`sudId`,`simSlotIndex`,`carrierId`,`displayName`,`carrierName`,`dataRoaming`,`mcc`,`mnc`,`countryIso`,`isEmbedded`,`cardId`,`portIndex`,`isOpportunistic`,`groupUUID`,`subscriptionType`,`uniqueName`,`isSubscriptionVisible`,`getFormattedPhoneNumber`,`isFirstRemovableSubscription`,`isDefaultSubscriptionSelection`,`isValidSubscription`,`isUsableSubscription`,`isActiveSubscription`,`isAvailableSubscription`,`isActiveDataSubscriptionId`)"
                       + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        }
    }

    public SubscriptionInfoDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
    }
}
