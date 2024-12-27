package com.android.settings.fuelgauge.batteryusage.db;

import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatteryEventDao_Impl$$ExternalSyntheticLambda1
        implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        SQLiteStatement prepare =
                ((SQLiteConnection) obj)
                        .prepare(
                                "SELECT MAX(timestamp) FROM BatteryEventEntity WHERE"
                                    + " batteryEventType = 3");
        try {
            Long l = null;
            if (prepare.step() && !prepare.isNull(0)) {
                l = Long.valueOf(prepare.getLong(0));
            }
            return l;
        } finally {
            prepare.close();
        }
    }
}
