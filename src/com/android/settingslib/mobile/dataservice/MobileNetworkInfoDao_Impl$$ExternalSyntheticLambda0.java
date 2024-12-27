package com.android.settingslib.mobile.dataservice;

import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class MobileNetworkInfoDao_Impl$$ExternalSyntheticLambda0
        implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ MobileNetworkInfoDao_Impl$$ExternalSyntheticLambda0(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        SQLiteStatement prepare;
        switch (this.$r8$classId) {
            case 0:
                prepare =
                        ((SQLiteConnection) obj)
                                .prepare("SELECT * FROM MobileNetworkInfo WHERE subId = ?");
                String str = this.f$0;
                try {
                    if (str == null) {
                        prepare.bindNull(1);
                    } else {
                        prepare.bindText(1, str);
                    }
                    int columnIndexOrThrow =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "subId");
                    int columnIndexOrThrow2 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "isContactDiscoveryEnabled");
                    int columnIndexOrThrow3 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "isContactDiscoveryVisible");
                    int columnIndexOrThrow4 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "isMobileDataEnabled");
                    int columnIndexOrThrow5 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "isCdmaOptions");
                    int columnIndexOrThrow6 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "isGsmOptions");
                    int columnIndexOrThrow7 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(prepare, "isWorldMode");
                    int columnIndexOrThrow8 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "shouldDisplayNetworkSelectOptions");
                    int columnIndexOrThrow9 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "isTdscdmaSupported");
                    int columnIndexOrThrow10 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "activeNetworkIsCellular");
                    int columnIndexOrThrow11 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "showToggleForPhysicalSim");
                    int columnIndexOrThrow12 =
                            SQLiteStatementUtil.getColumnIndexOrThrow(
                                    prepare, "isDataRoamingEnabled");
                    if (prepare.step()) {
                        r15 =
                                new MobileNetworkInfoEntity(
                                        prepare.isNull(columnIndexOrThrow)
                                                ? null
                                                : prepare.getText(columnIndexOrThrow),
                                        ((int) prepare.getLong(columnIndexOrThrow2)) != 0,
                                        ((int) prepare.getLong(columnIndexOrThrow3)) != 0,
                                        ((int) prepare.getLong(columnIndexOrThrow4)) != 0,
                                        ((int) prepare.getLong(columnIndexOrThrow5)) != 0,
                                        ((int) prepare.getLong(columnIndexOrThrow6)) != 0,
                                        ((int) prepare.getLong(columnIndexOrThrow7)) != 0,
                                        ((int) prepare.getLong(columnIndexOrThrow8)) != 0,
                                        ((int) prepare.getLong(columnIndexOrThrow9)) != 0,
                                        ((int) prepare.getLong(columnIndexOrThrow10)) != 0,
                                        ((int) prepare.getLong(columnIndexOrThrow11)) != 0,
                                        ((int) prepare.getLong(columnIndexOrThrow12)) != 0);
                    }
                    prepare.close();
                    return r15;
                } finally {
                }
            default:
                prepare =
                        ((SQLiteConnection) obj)
                                .prepare("DELETE FROM MobileNetworkInfo WHERE subId = ?");
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
