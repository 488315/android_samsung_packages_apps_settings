package com.android.settings.fuelgauge.batteryusage.db;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.room.RoomDatabase;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BatteryStateDatabase extends RoomDatabase {
    public static BatteryStateDatabase sBatteryStateDatabase;

    public static BatteryStateDatabase getInstance(Context context) {
        if (sBatteryStateDatabase == null) {
            RoomDatabase.Builder databaseBuilder =
                    Room.databaseBuilder(
                            context, BatteryStateDatabase.class, "battery-usage-db-v10");
            databaseBuilder.allowMainThreadQueries = true;
            databaseBuilder.requireMigration = false;
            databaseBuilder.allowDestructiveMigrationOnDowngrade = true;
            sBatteryStateDatabase = (BatteryStateDatabase) databaseBuilder.build();
            Log.d("BatteryStateDatabase", "initialize battery states database");
        }
        return sBatteryStateDatabase;
    }

    public abstract AppUsageEventDao_Impl appUsageEventDao();

    public abstract BatteryEventDao_Impl batteryEventDao();

    public abstract BatteryReattributeDao batteryReattributeDao();

    public abstract BatteryStateDao_Impl batteryStateDao();

    public abstract BatteryUsageSlotDao_Impl batteryUsageSlotDao();
}
