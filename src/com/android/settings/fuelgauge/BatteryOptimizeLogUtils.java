package com.android.settings.fuelgauge;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Protobuf;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BatteryOptimizeLogUtils {
    static final int MAX_ENTRIES = 40;

    public static String getPackageNameWithUserId(int i, String str) {
        return str + ":" + i;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getApplicationContext()
                .getSharedPreferences("battery_optimize_historical_logs", 0);
    }

    public static void writeLog(
            SharedPreferences sharedPreferences,
            BatteryOptimizeHistoricalLogEntry.Action action,
            String str,
            String str2) {
        BatteryOptimizeHistoricalLogEntry.Builder newBuilder =
                BatteryOptimizeHistoricalLogEntry.newBuilder();
        newBuilder.copyOnWrite();
        BatteryOptimizeHistoricalLogEntry.m867$$Nest$msetPackageName(
                (BatteryOptimizeHistoricalLogEntry) newBuilder.instance, str);
        newBuilder.copyOnWrite();
        BatteryOptimizeHistoricalLogEntry.m865$$Nest$msetAction(
                (BatteryOptimizeHistoricalLogEntry) newBuilder.instance, action);
        newBuilder.copyOnWrite();
        BatteryOptimizeHistoricalLogEntry.m866$$Nest$msetActionDescription(
                (BatteryOptimizeHistoricalLogEntry) newBuilder.instance, str2);
        long currentTimeMillis = System.currentTimeMillis();
        newBuilder.copyOnWrite();
        BatteryOptimizeHistoricalLogEntry.m868$$Nest$msetTimestamp(
                (BatteryOptimizeHistoricalLogEntry) newBuilder.instance, currentTimeMillis);
        BatteryOptimizeHistoricalLogEntry batteryOptimizeHistoricalLogEntry =
                (BatteryOptimizeHistoricalLogEntry) newBuilder.build();
        BatteryOptimizeHistoricalLog batteryOptimizeHistoricalLog =
                (BatteryOptimizeHistoricalLog)
                        BatteryUtils.parseProtoFromString(
                                sharedPreferences.getString(
                                        "battery_optimize_logs_key", ApnSettings.MVNO_NONE),
                                BatteryOptimizeHistoricalLog.getDefaultInstance());
        GeneratedMessageLite.Builder builder =
                (GeneratedMessageLite.Builder)
                        batteryOptimizeHistoricalLog.dynamicMethod(
                                GeneratedMessageLite.MethodToInvoke.NEW_BUILDER, null);
        if (!builder.defaultInstance.equals(batteryOptimizeHistoricalLog)) {
            builder.copyOnWrite();
            GeneratedMessageLite generatedMessageLite = builder.instance;
            Protobuf protobuf = Protobuf.INSTANCE;
            protobuf.getClass();
            protobuf.schemaFor(generatedMessageLite.getClass())
                    .mergeFrom(generatedMessageLite, batteryOptimizeHistoricalLog);
        }
        BatteryOptimizeHistoricalLog.Builder builder2 =
                (BatteryOptimizeHistoricalLog.Builder) builder;
        if (batteryOptimizeHistoricalLog.getLogEntryCount() >= 40) {
            builder2.copyOnWrite();
            BatteryOptimizeHistoricalLog.m864$$Nest$mremoveLogEntry(
                    (BatteryOptimizeHistoricalLog) builder2.instance);
        }
        builder2.copyOnWrite();
        BatteryOptimizeHistoricalLog.m863$$Nest$maddLogEntry(
                (BatteryOptimizeHistoricalLog) builder2.instance,
                batteryOptimizeHistoricalLogEntry);
        sharedPreferences
                .edit()
                .putString(
                        "battery_optimize_logs_key",
                        Base64.encodeToString(
                                ((BatteryOptimizeHistoricalLog) builder2.build()).toByteArray(), 0))
                .apply();
    }
}
