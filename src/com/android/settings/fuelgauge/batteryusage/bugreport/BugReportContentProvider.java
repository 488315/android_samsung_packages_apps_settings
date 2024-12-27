package com.android.settings.fuelgauge.batteryusage.bugreport;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.ArraySet;
import android.util.Log;

import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;

import com.android.settings.fuelgauge.BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticLambda1;
import com.android.settings.fuelgauge.BatteryUsageHistoricalLog;
import com.android.settings.fuelgauge.BatteryUsageHistoricalLogEntry;
import com.android.settings.fuelgauge.batteryusage.AppOptModeSharedPreferencesUtils;
import com.android.settings.fuelgauge.batteryusage.BatteryUsageSlot;
import com.android.settings.fuelgauge.batteryusage.ConvertUtils;
import com.android.settings.fuelgauge.batteryusage.DatabaseUtils;
import com.android.settings.fuelgauge.batteryusage.db.AppUsageEventDao_Impl;
import com.android.settings.fuelgauge.batteryusage.db.AppUsageEventEntity;
import com.android.settings.fuelgauge.batteryusage.db.BatteryEventDao_Impl;
import com.android.settings.fuelgauge.batteryusage.db.BatteryEventEntity;
import com.android.settings.fuelgauge.batteryusage.db.BatteryState;
import com.android.settings.fuelgauge.batteryusage.db.BatteryStateDao_Impl;
import com.android.settings.fuelgauge.batteryusage.db.BatteryStateDatabase;
import com.android.settings.fuelgauge.batteryusage.db.BatteryUsageSlotDao_Impl;
import com.android.settings.fuelgauge.batteryusage.db.BatteryUsageSlotEntity;
import com.android.settingslib.fuelgauge.BatteryUtils;

import com.google.protobuf.Internal;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;

import kotlin.jvm.functions.Function1;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BugReportContentProvider extends ContentProvider {
    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("unsupported!");
    }

    @Override // android.content.ContentProvider
    public final void dump(
            FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr) {
        Set<String> stringSet;
        final int i = 0;
        final int i2 = 1;
        Context context = getContext();
        if (context == null) {
            Log.w("BugReportContentProvider", "failed to dump BatteryUsage state: null context");
            return;
        }
        Context applicationContext = context.getApplicationContext();
        if (applicationContext == null) {
            Log.w(
                    "BugReportContentProvider",
                    "failed to dump BatteryUsage state: null application context");
            return;
        }
        if (BatteryUtils.isWorkProfile(applicationContext)) {
            Log.w(
                    "BugReportContentProvider",
                    "ignore battery usage states dump in the work profile");
            return;
        }
        printWriter.println("dump BatteryUsage and AppUsage states:");
        Duration duration = LogUtils.DUMP_TIME_OFFSET;
        printWriter.println("\n\tApp Optimization Mode Event History:");
        LogUtils.dumpListItems(
                printWriter,
                AppOptModeSharedPreferencesUtils.getAllEvents(applicationContext),
                new BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticLambda1(1));
        try {
            LogUtils.dumpBatteryReattributeDatabaseHist(
                    BatteryStateDatabase.getInstance(applicationContext).batteryReattributeDao(),
                    printWriter);
        } catch (Exception e) {
            Log.e("LogUtils", "failed to run dumpBatteryReattributeDatabaseHist()", e);
        }
        printWriter.println("\nBattery PeriodicJob History:");
        Internal.ProtobufList logEntryList =
                ((BatteryUsageHistoricalLog)
                                com.android.settings.fuelgauge.BatteryUtils.parseProtoFromString(
                                        BatteryUsageLogUtils.getSharedPreferences(
                                                        applicationContext)
                                                .getString(
                                                        "battery_usage_logs_key",
                                                        ApnSettings.MVNO_NONE),
                                        BatteryUsageHistoricalLog.getDefaultInstance()))
                        .getLogEntryList();
        if (logEntryList.isEmpty()) {
            printWriter.println("\tnothing to dump");
        } else {
            logEntryList.forEach(
                    new Consumer() { // from class:
                                     // com.android.settings.fuelgauge.batteryusage.bugreport.BatteryUsageLogUtils$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            PrintWriter printWriter2 = printWriter;
                            BatteryUsageHistoricalLogEntry batteryUsageHistoricalLogEntry =
                                    (BatteryUsageHistoricalLogEntry) obj;
                            StringBuilder sb = new StringBuilder("\t");
                            sb.append(
                                    ConvertUtils.utcToLocalTimeForLogging(
                                            batteryUsageHistoricalLogEntry.getTimestamp()));
                            sb.append(" " + batteryUsageHistoricalLogEntry.getAction());
                            String actionDescription =
                                    batteryUsageHistoricalLogEntry.getActionDescription();
                            if (!actionDescription.isEmpty()) {
                                sb.append(" ".concat(actionDescription));
                            }
                            printWriter2.println(sb.toString());
                        }
                    });
        }
        printWriter.flush();
        DatabaseUtils.writeString(
                applicationContext,
                printWriter,
                "BatteryLevelChanged",
                "android.intent.action.BATTERY_LEVEL_CHANGED");
        DatabaseUtils.writeString(
                applicationContext,
                printWriter,
                "BatteryPlugging",
                "com.android.settings.battery.action.ACTION_BATTERY_PLUGGING");
        DatabaseUtils.writeString(
                applicationContext,
                printWriter,
                "BatteryUnplugging",
                "com.android.settings.battery.action.ACTION_BATTERY_UNPLUGGING");
        DatabaseUtils.writeString(
                applicationContext,
                printWriter,
                "ClearBatteryCacheData",
                "com.android.settings.battery.action.CLEAR_BATTERY_CACHE_DATA");
        DatabaseUtils.writeString(
                applicationContext,
                printWriter,
                "LastLoadFullChargeTime",
                "last_load_full_charge_time");
        DatabaseUtils.writeString(
                applicationContext,
                printWriter,
                "LastUploadFullChargeTime",
                "last_upload_full_charge_time");
        SharedPreferences sharedPreferences =
                applicationContext
                        .getApplicationContext()
                        .getSharedPreferences("battery_usage_shared_prefs", 0);
        if (sharedPreferences != null
                && (stringSet =
                                sharedPreferences.getStringSet(
                                        "dismissed_power_anomaly_keys", new ArraySet()))
                        != null) {
            printWriter.println("\t\tDismissedPowerAnomalyKeys: " + stringSet.toString());
        }
        printWriter.flush();
        BatteryStateDao_Impl batteryStateDao =
                BatteryStateDatabase.getInstance(applicationContext).batteryStateDao();
        final long millis = Clock.systemUTC().millis() - LogUtils.DUMP_TIME_OFFSET.toMillis();
        batteryStateDao.getClass();
        List list =
                (List)
                        DBUtil.performBlocking(
                                batteryStateDao.__db,
                                true,
                                false,
                                new Function1() { // from class:
                                    // com.android.settings.fuelgauge.batteryusage.db.BatteryStateDao_Impl$$ExternalSyntheticLambda1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        SQLiteStatement prepare;
                                        switch (i) {
                                            case 0:
                                                long j = millis;
                                                prepare =
                                                        ((SQLiteConnection) obj)
                                                                .prepare(
                                                                        "SELECT DISTINCT timestamp"
                                                                            + " FROM BatteryState"
                                                                            + " WHERE timestamp >"
                                                                            + " ?");
                                                try {
                                                    prepare.bindLong(1, j);
                                                    ArrayList arrayList = new ArrayList();
                                                    while (prepare.step()) {
                                                        arrayList.add(
                                                                prepare.isNull(0)
                                                                        ? null
                                                                        : Long.valueOf(
                                                                                prepare.getLong(
                                                                                        0)));
                                                    }
                                                    return arrayList;
                                                } finally {
                                                }
                                            default:
                                                long j2 = millis;
                                                prepare =
                                                        ((SQLiteConnection) obj)
                                                                .prepare(
                                                                        "SELECT * FROM BatteryState"
                                                                            + " WHERE timestamp > ?"
                                                                            + " ORDER BY timestamp"
                                                                            + " DESC");
                                                try {
                                                    prepare.bindLong(1, j2);
                                                    int columnIndexOrThrow =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare, "mId");
                                                    int columnIndexOrThrow2 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare,
                                                                            NetworkAnalyticsConstants
                                                                                    .DataPoints
                                                                                    .UID);
                                                    int columnIndexOrThrow3 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare, "userId");
                                                    int columnIndexOrThrow4 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare, "packageName");
                                                    int columnIndexOrThrow5 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare,
                                                                            PhoneRestrictionPolicy
                                                                                    .TIMESTAMP);
                                                    int columnIndexOrThrow6 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare,
                                                                            "consumerType");
                                                    int columnIndexOrThrow7 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare,
                                                                            "isFullChargeCycleStart");
                                                    int columnIndexOrThrow8 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare,
                                                                            "batteryInformation");
                                                    int columnIndexOrThrow9 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare,
                                                                            "batteryInformationDebug");
                                                    ArrayList arrayList2 = new ArrayList();
                                                    while (prepare.step()) {
                                                        int i3 = columnIndexOrThrow3;
                                                        BatteryState batteryState =
                                                                new BatteryState(
                                                                        prepare.getLong(
                                                                                columnIndexOrThrow2),
                                                                        prepare.getLong(
                                                                                columnIndexOrThrow3),
                                                                        prepare.isNull(
                                                                                        columnIndexOrThrow4)
                                                                                ? null
                                                                                : prepare.getText(
                                                                                        columnIndexOrThrow4),
                                                                        prepare.getLong(
                                                                                columnIndexOrThrow5),
                                                                        (int)
                                                                                prepare.getLong(
                                                                                        columnIndexOrThrow6),
                                                                        ((int)
                                                                                        prepare
                                                                                                .getLong(
                                                                                                        columnIndexOrThrow7))
                                                                                != 0,
                                                                        prepare.isNull(
                                                                                        columnIndexOrThrow8)
                                                                                ? null
                                                                                : prepare.getText(
                                                                                        columnIndexOrThrow8),
                                                                        prepare.isNull(
                                                                                        columnIndexOrThrow9)
                                                                                ? null
                                                                                : prepare.getText(
                                                                                        columnIndexOrThrow9));
                                                        batteryState.mId =
                                                                prepare.getLong(columnIndexOrThrow);
                                                        arrayList2.add(batteryState);
                                                        columnIndexOrThrow3 = i3;
                                                    }
                                                    return arrayList2;
                                                } finally {
                                                }
                                        }
                                    }
                                });
        int size = list.size();
        printWriter.println("\n\tBattery DatabaseHistory:");
        printWriter.println("distinct timestamp count:" + size);
        Log.w("LogUtils", "distinct timestamp count:" + size);
        if (size != 0) {
            list.forEach(
                    new Consumer() { // from class:
                                     // com.android.settings.fuelgauge.batteryusage.bugreport.LogUtils$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            PrintWriter printWriter2 = printWriter;
                            String utcToLocalTimeForLogging =
                                    ConvertUtils.utcToLocalTimeForLogging(((Long) obj).longValue());
                            printWriter2.println("\t" + utcToLocalTimeForLogging);
                            Log.w("LogUtils", "\t" + utcToLocalTimeForLogging);
                        }
                    });
            printWriter.flush();
        }
        AppUsageEventDao_Impl appUsageEventDao =
                BatteryStateDatabase.getInstance(applicationContext).appUsageEventDao();
        printWriter.println("\n\tApp DatabaseHistory:");
        long millis2 = Clock.systemUTC().millis();
        Duration duration2 = LogUtils.DUMP_TIME_OFFSET_FOR_ENTRY;
        final long millis3 = millis2 - duration2.toMillis();
        appUsageEventDao.getClass();
        LogUtils.dumpListItems(
                printWriter,
                (List)
                        DBUtil.performBlocking(
                                appUsageEventDao.__db,
                                true,
                                false,
                                new Function1() { // from class:
                                    // com.android.settings.fuelgauge.batteryusage.db.AppUsageEventDao_Impl$$ExternalSyntheticLambda1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        int i3;
                                        int i4;
                                        String text;
                                        long j = millis3;
                                        SQLiteStatement prepare =
                                                ((SQLiteConnection) obj)
                                                        .prepare(
                                                                "SELECT * FROM AppUsageEventEntity"
                                                                    + " WHERE timestamp > ? ORDER"
                                                                    + " BY timestamp DESC");
                                        try {
                                            prepare.bindLong(1, j);
                                            int columnIndexOrThrow =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare, "mId");
                                            int columnIndexOrThrow2 =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare,
                                                            NetworkAnalyticsConstants.DataPoints
                                                                    .UID);
                                            int columnIndexOrThrow3 =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare, "userId");
                                            int columnIndexOrThrow4 =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare,
                                                            PhoneRestrictionPolicy.TIMESTAMP);
                                            int columnIndexOrThrow5 =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare, "appUsageEventType");
                                            int columnIndexOrThrow6 =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare, "packageName");
                                            int columnIndexOrThrow7 =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare, "instanceId");
                                            int columnIndexOrThrow8 =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare, "taskRootPackageName");
                                            ArrayList arrayList = new ArrayList();
                                            while (prepare.step()) {
                                                long j2 = prepare.getLong(columnIndexOrThrow2);
                                                long j3 = prepare.getLong(columnIndexOrThrow3);
                                                long j4 = prepare.getLong(columnIndexOrThrow4);
                                                int i5 = (int) prepare.getLong(columnIndexOrThrow5);
                                                if (prepare.isNull(columnIndexOrThrow6)) {
                                                    i3 = columnIndexOrThrow3;
                                                    i4 = columnIndexOrThrow4;
                                                    text = null;
                                                } else {
                                                    i3 = columnIndexOrThrow3;
                                                    i4 = columnIndexOrThrow4;
                                                    text = prepare.getText(columnIndexOrThrow6);
                                                }
                                                AppUsageEventEntity appUsageEventEntity =
                                                        new AppUsageEventEntity(
                                                                j2,
                                                                j3,
                                                                j4,
                                                                i5,
                                                                text,
                                                                (int)
                                                                        prepare.getLong(
                                                                                columnIndexOrThrow7),
                                                                prepare.isNull(columnIndexOrThrow8)
                                                                        ? null
                                                                        : prepare.getText(
                                                                                columnIndexOrThrow8));
                                                appUsageEventEntity.mId =
                                                        prepare.getLong(columnIndexOrThrow);
                                                arrayList.add(appUsageEventEntity);
                                                columnIndexOrThrow3 = i3;
                                                columnIndexOrThrow4 = i4;
                                            }
                                            return arrayList;
                                        } finally {
                                            prepare.close();
                                        }
                                    }
                                }),
                new Function() { // from class:
                    // com.android.settings.fuelgauge.batteryusage.bugreport.LogUtils$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        switch (i) {
                            case 0:
                                return (AppUsageEventEntity) obj;
                            case 1:
                                return (BatteryState) obj;
                            case 2:
                                return (BatteryEventEntity) obj;
                            default:
                                return (BatteryUsageSlot)
                                        com.android.settings.fuelgauge.BatteryUtils
                                                .parseProtoFromString(
                                                        ((BatteryUsageSlotEntity) obj)
                                                                .batteryUsageSlot,
                                                        BatteryUsageSlot.getDefaultInstance());
                        }
                    }
                });
        BatteryUsageSlotDao_Impl batteryUsageSlotDao =
                BatteryStateDatabase.getInstance(applicationContext).batteryUsageSlotDao();
        printWriter.println("\n\tBattery Usage Slot TimeZone ID: " + TimeZone.getDefault().getID());
        printWriter.println("\n\tBattery Usage Slot DatabaseHistory:");
        final long lastFullChargeTimestamp =
                LogUtils.getLastFullChargeTimestamp(applicationContext);
        batteryUsageSlotDao.getClass();
        final int i3 = 3;
        LogUtils.dumpListItems(
                printWriter,
                (List)
                        DBUtil.performBlocking(
                                batteryUsageSlotDao.__db,
                                true,
                                false,
                                new Function1() { // from class:
                                    // com.android.settings.fuelgauge.batteryusage.db.BatteryUsageSlotDao_Impl$$ExternalSyntheticLambda1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        long j = lastFullChargeTimestamp;
                                        SQLiteStatement prepare =
                                                ((SQLiteConnection) obj)
                                                        .prepare(
                                                                "SELECT * FROM"
                                                                    + " BatteryUsageSlotEntity"
                                                                    + " WHERE timestamp >= ? ORDER"
                                                                    + " BY timestamp DESC");
                                        try {
                                            prepare.bindLong(1, j);
                                            int columnIndexOrThrow =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare, "mId");
                                            int columnIndexOrThrow2 =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare,
                                                            PhoneRestrictionPolicy.TIMESTAMP);
                                            int columnIndexOrThrow3 =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare, "batteryUsageSlot");
                                            ArrayList arrayList = new ArrayList();
                                            while (prepare.step()) {
                                                BatteryUsageSlotEntity batteryUsageSlotEntity =
                                                        new BatteryUsageSlotEntity(
                                                                prepare.getLong(
                                                                        columnIndexOrThrow2),
                                                                prepare.isNull(columnIndexOrThrow3)
                                                                        ? null
                                                                        : prepare.getText(
                                                                                columnIndexOrThrow3));
                                                batteryUsageSlotEntity.mId =
                                                        prepare.getLong(columnIndexOrThrow);
                                                arrayList.add(batteryUsageSlotEntity);
                                            }
                                            return arrayList;
                                        } finally {
                                            prepare.close();
                                        }
                                    }
                                }),
                new Function() { // from class:
                    // com.android.settings.fuelgauge.batteryusage.bugreport.LogUtils$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        switch (i3) {
                            case 0:
                                return (AppUsageEventEntity) obj;
                            case 1:
                                return (BatteryState) obj;
                            case 2:
                                return (BatteryEventEntity) obj;
                            default:
                                return (BatteryUsageSlot)
                                        com.android.settings.fuelgauge.BatteryUtils
                                                .parseProtoFromString(
                                                        ((BatteryUsageSlotEntity) obj)
                                                                .batteryUsageSlot,
                                                        BatteryUsageSlot.getDefaultInstance());
                        }
                    }
                });
        BatteryEventDao_Impl batteryEventDao =
                BatteryStateDatabase.getInstance(applicationContext).batteryEventDao();
        printWriter.println("\n\tBattery Event DatabaseHistory:");
        final long lastFullChargeTimestamp2 =
                LogUtils.getLastFullChargeTimestamp(applicationContext);
        batteryEventDao.getClass();
        final int i4 = 2;
        LogUtils.dumpListItems(
                printWriter,
                (List)
                        DBUtil.performBlocking(
                                batteryEventDao.__db,
                                true,
                                false,
                                new Function1() { // from class:
                                    // com.android.settings.fuelgauge.batteryusage.db.BatteryEventDao_Impl$$ExternalSyntheticLambda2
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        long j = lastFullChargeTimestamp2;
                                        SQLiteStatement prepare =
                                                ((SQLiteConnection) obj)
                                                        .prepare(
                                                                "SELECT * FROM BatteryEventEntity"
                                                                    + " WHERE timestamp >= ? ORDER"
                                                                    + " BY timestamp DESC");
                                        try {
                                            prepare.bindLong(1, j);
                                            int columnIndexOrThrow =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare, "mId");
                                            int columnIndexOrThrow2 =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare,
                                                            PhoneRestrictionPolicy.TIMESTAMP);
                                            int columnIndexOrThrow3 =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare, "batteryEventType");
                                            int columnIndexOrThrow4 =
                                                    SQLiteStatementUtil.getColumnIndexOrThrow(
                                                            prepare, "batteryLevel");
                                            ArrayList arrayList = new ArrayList();
                                            while (prepare.step()) {
                                                BatteryEventEntity batteryEventEntity =
                                                        new BatteryEventEntity(
                                                                (int)
                                                                        prepare.getLong(
                                                                                columnIndexOrThrow3),
                                                                (int)
                                                                        prepare.getLong(
                                                                                columnIndexOrThrow4),
                                                                prepare.getLong(
                                                                        columnIndexOrThrow2));
                                                batteryEventEntity.mId =
                                                        prepare.getLong(columnIndexOrThrow);
                                                arrayList.add(batteryEventEntity);
                                            }
                                            return arrayList;
                                        } finally {
                                            prepare.close();
                                        }
                                    }
                                }),
                new Function() { // from class:
                    // com.android.settings.fuelgauge.batteryusage.bugreport.LogUtils$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        switch (i4) {
                            case 0:
                                return (AppUsageEventEntity) obj;
                            case 1:
                                return (BatteryState) obj;
                            case 2:
                                return (BatteryEventEntity) obj;
                            default:
                                return (BatteryUsageSlot)
                                        com.android.settings.fuelgauge.BatteryUtils
                                                .parseProtoFromString(
                                                        ((BatteryUsageSlotEntity) obj)
                                                                .batteryUsageSlot,
                                                        BatteryUsageSlot.getDefaultInstance());
                        }
                    }
                });
        BatteryStateDao_Impl batteryStateDao2 =
                BatteryStateDatabase.getInstance(applicationContext).batteryStateDao();
        printWriter.println("\n\tBatteryState DatabaseHistory:");
        final long millis4 = Clock.systemUTC().millis() - duration2.toMillis();
        batteryStateDao2.getClass();
        LogUtils.dumpListItems(
                printWriter,
                (List)
                        DBUtil.performBlocking(
                                batteryStateDao2.__db,
                                true,
                                false,
                                new Function1() { // from class:
                                    // com.android.settings.fuelgauge.batteryusage.db.BatteryStateDao_Impl$$ExternalSyntheticLambda1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        SQLiteStatement prepare;
                                        switch (i2) {
                                            case 0:
                                                long j = millis4;
                                                prepare =
                                                        ((SQLiteConnection) obj)
                                                                .prepare(
                                                                        "SELECT DISTINCT timestamp"
                                                                            + " FROM BatteryState"
                                                                            + " WHERE timestamp >"
                                                                            + " ?");
                                                try {
                                                    prepare.bindLong(1, j);
                                                    ArrayList arrayList = new ArrayList();
                                                    while (prepare.step()) {
                                                        arrayList.add(
                                                                prepare.isNull(0)
                                                                        ? null
                                                                        : Long.valueOf(
                                                                                prepare.getLong(
                                                                                        0)));
                                                    }
                                                    return arrayList;
                                                } finally {
                                                }
                                            default:
                                                long j2 = millis4;
                                                prepare =
                                                        ((SQLiteConnection) obj)
                                                                .prepare(
                                                                        "SELECT * FROM BatteryState"
                                                                            + " WHERE timestamp > ?"
                                                                            + " ORDER BY timestamp"
                                                                            + " DESC");
                                                try {
                                                    prepare.bindLong(1, j2);
                                                    int columnIndexOrThrow =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare, "mId");
                                                    int columnIndexOrThrow2 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare,
                                                                            NetworkAnalyticsConstants
                                                                                    .DataPoints
                                                                                    .UID);
                                                    int columnIndexOrThrow3 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare, "userId");
                                                    int columnIndexOrThrow4 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare, "packageName");
                                                    int columnIndexOrThrow5 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare,
                                                                            PhoneRestrictionPolicy
                                                                                    .TIMESTAMP);
                                                    int columnIndexOrThrow6 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare,
                                                                            "consumerType");
                                                    int columnIndexOrThrow7 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare,
                                                                            "isFullChargeCycleStart");
                                                    int columnIndexOrThrow8 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare,
                                                                            "batteryInformation");
                                                    int columnIndexOrThrow9 =
                                                            SQLiteStatementUtil
                                                                    .getColumnIndexOrThrow(
                                                                            prepare,
                                                                            "batteryInformationDebug");
                                                    ArrayList arrayList2 = new ArrayList();
                                                    while (prepare.step()) {
                                                        int i32 = columnIndexOrThrow3;
                                                        BatteryState batteryState =
                                                                new BatteryState(
                                                                        prepare.getLong(
                                                                                columnIndexOrThrow2),
                                                                        prepare.getLong(
                                                                                columnIndexOrThrow3),
                                                                        prepare.isNull(
                                                                                        columnIndexOrThrow4)
                                                                                ? null
                                                                                : prepare.getText(
                                                                                        columnIndexOrThrow4),
                                                                        prepare.getLong(
                                                                                columnIndexOrThrow5),
                                                                        (int)
                                                                                prepare.getLong(
                                                                                        columnIndexOrThrow6),
                                                                        ((int)
                                                                                        prepare
                                                                                                .getLong(
                                                                                                        columnIndexOrThrow7))
                                                                                != 0,
                                                                        prepare.isNull(
                                                                                        columnIndexOrThrow8)
                                                                                ? null
                                                                                : prepare.getText(
                                                                                        columnIndexOrThrow8),
                                                                        prepare.isNull(
                                                                                        columnIndexOrThrow9)
                                                                                ? null
                                                                                : prepare.getText(
                                                                                        columnIndexOrThrow9));
                                                        batteryState.mId =
                                                                prepare.getLong(columnIndexOrThrow);
                                                        arrayList2.add(batteryState);
                                                        columnIndexOrThrow3 = i32;
                                                    }
                                                    return arrayList2;
                                                } finally {
                                                }
                                        }
                                    }
                                }),
                new Function() { // from class:
                    // com.android.settings.fuelgauge.batteryusage.bugreport.LogUtils$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        switch (i2) {
                            case 0:
                                return (AppUsageEventEntity) obj;
                            case 1:
                                return (BatteryState) obj;
                            case 2:
                                return (BatteryEventEntity) obj;
                            default:
                                return (BatteryUsageSlot)
                                        com.android.settings.fuelgauge.BatteryUtils
                                                .parseProtoFromString(
                                                        ((BatteryUsageSlotEntity) obj)
                                                                .batteryUsageSlot,
                                                        BatteryUsageSlot.getDefaultInstance());
                        }
                    }
                });
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        throw new UnsupportedOperationException("unsupported!");
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("unsupported!");
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        throw new UnsupportedOperationException("unsupported!");
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("unsupported!");
    }
}
