package com.android.settingslib.mobile.dataservice;

import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;

import com.samsung.android.knox.accounts.Account;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubscriptionInfoDao_Impl$$ExternalSyntheticLambda1
        implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ SubscriptionInfoDao_Impl$$ExternalSyntheticLambda1(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        SQLiteStatement prepare;
        String text;
        int i;
        String text2;
        int i2;
        String text3;
        int i3;
        boolean z;
        int i4;
        boolean z2;
        int i5;
        boolean z3;
        int i6;
        boolean z4;
        int i7;
        boolean z5;
        int i8;
        boolean z6;
        int i9;
        switch (this.$r8$classId) {
            case 0:
                prepare =
                        ((SQLiteConnection) obj)
                                .prepare("SELECT * FROM subscriptionInfo WHERE sudId = ?");
                String str = this.f$0;
                try {
                    if (str == null) {
                        prepare.bindNull(1);
                    } else {
                        prepare.bindText(1, str);
                    }
                    int columnIndexOrThrow =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "sudId");
                    int columnIndexOrThrow2 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "simSlotIndex");
                    int columnIndexOrThrow3 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "carrierId");
                    int columnIndexOrThrow4 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, Account.DISPLAY_NAME);
                    int columnIndexOrThrow5 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "carrierName");
                    int columnIndexOrThrow6 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "dataRoaming");
                    int columnIndexOrThrow7 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "mcc");
                    int columnIndexOrThrow8 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "mnc");
                    int columnIndexOrThrow9 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "countryIso");
                    int columnIndexOrThrow10 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "isEmbedded");
                    int columnIndexOrThrow11 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "cardId");
                    int columnIndexOrThrow12 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "portIndex");
                    int columnIndexOrThrow13 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "isOpportunistic");
                    int columnIndexOrThrow14 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "groupUUID");
                    int columnIndexOrThrow15 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "subscriptionType");
                    int columnIndexOrThrow16 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "uniqueName");
                    int columnIndexOrThrow17 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "isSubscriptionVisible");
                    int columnIndexOrThrow18 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "getFormattedPhoneNumber");
                    int columnIndexOrThrow19 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "isFirstRemovableSubscription");
                    int columnIndexOrThrow20 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "isDefaultSubscriptionSelection");
                    int columnIndexOrThrow21 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "isValidSubscription");
                    int columnIndexOrThrow22 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "isUsableSubscription");
                    int columnIndexOrThrow23 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "isActiveSubscription");
                    int columnIndexOrThrow24 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "isAvailableSubscription");
                    int columnIndexOrThrow25 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "isActiveDataSubscriptionId");
                    if (prepare.step()) {
                        if (prepare.isNull(columnIndexOrThrow)) {
                            i = columnIndexOrThrow25;
                            text = null;
                        } else {
                            text = prepare.getText(columnIndexOrThrow);
                            i = columnIndexOrThrow25;
                        }
                        int i10 = (int) prepare.getLong(columnIndexOrThrow2);
                        int i11 = (int) prepare.getLong(columnIndexOrThrow3);
                        String text4 =
                                prepare.isNull(columnIndexOrThrow4)
                                        ? null
                                        : prepare.getText(columnIndexOrThrow4);
                        String text5 =
                                prepare.isNull(columnIndexOrThrow5)
                                        ? null
                                        : prepare.getText(columnIndexOrThrow5);
                        int i12 = (int) prepare.getLong(columnIndexOrThrow6);
                        String text6 =
                                prepare.isNull(columnIndexOrThrow7)
                                        ? null
                                        : prepare.getText(columnIndexOrThrow7);
                        String text7 =
                                prepare.isNull(columnIndexOrThrow8)
                                        ? null
                                        : prepare.getText(columnIndexOrThrow8);
                        String text8 =
                                prepare.isNull(columnIndexOrThrow9)
                                        ? null
                                        : prepare.getText(columnIndexOrThrow9);
                        boolean z7 = ((int) prepare.getLong(columnIndexOrThrow10)) != 0;
                        int i13 = (int) prepare.getLong(columnIndexOrThrow11);
                        int i14 = (int) prepare.getLong(columnIndexOrThrow12);
                        boolean z8 = ((int) prepare.getLong(columnIndexOrThrow13)) != 0;
                        if (prepare.isNull(columnIndexOrThrow14)) {
                            i2 = columnIndexOrThrow15;
                            text2 = null;
                        } else {
                            text2 = prepare.getText(columnIndexOrThrow14);
                            i2 = columnIndexOrThrow15;
                        }
                        int i15 = (int) prepare.getLong(i2);
                        if (prepare.isNull(columnIndexOrThrow16)) {
                            i3 = columnIndexOrThrow17;
                            text3 = null;
                        } else {
                            text3 = prepare.getText(columnIndexOrThrow16);
                            i3 = columnIndexOrThrow17;
                        }
                        if (((int) prepare.getLong(i3)) != 0) {
                            i4 = columnIndexOrThrow18;
                            z = true;
                        } else {
                            z = false;
                            i4 = columnIndexOrThrow18;
                        }
                        String text9 = prepare.isNull(i4) ? null : prepare.getText(i4);
                        if (((int) prepare.getLong(columnIndexOrThrow19)) != 0) {
                            i5 = columnIndexOrThrow20;
                            z2 = true;
                        } else {
                            z2 = false;
                            i5 = columnIndexOrThrow20;
                        }
                        if (((int) prepare.getLong(i5)) != 0) {
                            i6 = columnIndexOrThrow21;
                            z3 = true;
                        } else {
                            z3 = false;
                            i6 = columnIndexOrThrow21;
                        }
                        if (((int) prepare.getLong(i6)) != 0) {
                            i7 = columnIndexOrThrow22;
                            z4 = true;
                        } else {
                            z4 = false;
                            i7 = columnIndexOrThrow22;
                        }
                        if (((int) prepare.getLong(i7)) != 0) {
                            i8 = columnIndexOrThrow23;
                            z5 = true;
                        } else {
                            z5 = false;
                            i8 = columnIndexOrThrow23;
                        }
                        if (((int) prepare.getLong(i8)) != 0) {
                            i9 = columnIndexOrThrow24;
                            z6 = true;
                        } else {
                            z6 = false;
                            i9 = columnIndexOrThrow24;
                        }
                        r26 =
                                new SubscriptionInfoEntity(
                                        text,
                                        i10,
                                        i11,
                                        text4,
                                        text5,
                                        i12,
                                        text6,
                                        text7,
                                        text8,
                                        z7,
                                        i13,
                                        i14,
                                        z8,
                                        text2,
                                        i15,
                                        text3,
                                        z,
                                        text9,
                                        z2,
                                        z3,
                                        z4,
                                        z5,
                                        z6,
                                        ((int) prepare.getLong(i9)) != 0,
                                        ((int) prepare.getLong(i)) != 0);
                    }
                    prepare.close();
                    return r26;
                } finally {
                }
            default:
                prepare =
                        ((SQLiteConnection) obj)
                                .prepare("DELETE FROM subscriptionInfo WHERE sudId = ?");
                String str2 = this.f$0;
                try {
                    if (str2 == null) {
                        prepare.bindNull(1);
                    } else {
                        prepare.bindText(1, str2);
                    }
                    prepare.step();
                    prepare.close();
                    return null;
                } finally {
                }
        }
    }
}
