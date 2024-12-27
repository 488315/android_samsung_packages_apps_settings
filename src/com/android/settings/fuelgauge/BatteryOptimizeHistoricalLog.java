package com.android.settings.fuelgauge;

import com.google.protobuf.AbstractProtobufList;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtobufArrayList;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryOptimizeHistoricalLog extends GeneratedMessageLite {
    private static final BatteryOptimizeHistoricalLog DEFAULT_INSTANCE;
    public static final int LOG_ENTRY_FIELD_NUMBER = 1;
    private static volatile Parser PARSER;
    private Internal.ProtobufList logEntry_ = ProtobufArrayList.EMPTY_LIST;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    /* renamed from: -$$Nest$maddLogEntry, reason: not valid java name */
    public static void m863$$Nest$maddLogEntry(
            BatteryOptimizeHistoricalLog batteryOptimizeHistoricalLog,
            BatteryOptimizeHistoricalLogEntry batteryOptimizeHistoricalLogEntry) {
        batteryOptimizeHistoricalLog.getClass();
        Internal.ProtobufList protobufList = batteryOptimizeHistoricalLog.logEntry_;
        if (!((AbstractProtobufList) protobufList).isMutable) {
            int size = protobufList.size();
            batteryOptimizeHistoricalLog.logEntry_ =
                    protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
        }
        batteryOptimizeHistoricalLog.logEntry_.add(batteryOptimizeHistoricalLogEntry);
    }

    /* renamed from: -$$Nest$mremoveLogEntry, reason: not valid java name */
    public static void m864$$Nest$mremoveLogEntry(
            BatteryOptimizeHistoricalLog batteryOptimizeHistoricalLog) {
        Internal.ProtobufList protobufList = batteryOptimizeHistoricalLog.logEntry_;
        if (!((AbstractProtobufList) protobufList).isMutable) {
            int size = protobufList.size();
            batteryOptimizeHistoricalLog.logEntry_ =
                    protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
        }
        batteryOptimizeHistoricalLog.logEntry_.remove(0);
    }

    static {
        BatteryOptimizeHistoricalLog batteryOptimizeHistoricalLog =
                new BatteryOptimizeHistoricalLog();
        DEFAULT_INSTANCE = batteryOptimizeHistoricalLog;
        GeneratedMessageLite.registerDefaultInstance(
                BatteryOptimizeHistoricalLog.class, batteryOptimizeHistoricalLog);
    }

    public static BatteryOptimizeHistoricalLog getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(
            GeneratedMessageLite.MethodToInvoke methodToInvoke,
            GeneratedMessageLite generatedMessageLite) {
        switch (methodToInvoke.ordinal()) {
            case 0:
                return (byte) 1;
            case 1:
                return null;
            case 2:
                return new RawMessageInfo(
                        DEFAULT_INSTANCE,
                        "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b",
                        new Object[] {"logEntry_", BatteryOptimizeHistoricalLogEntry.class});
            case 3:
                return new BatteryOptimizeHistoricalLog();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (BatteryOptimizeHistoricalLog.class) {
                        try {
                            parser = PARSER;
                            if (parser == null) {
                                parser =
                                        new GeneratedMessageLite.DefaultInstanceBasedParser(
                                                DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        } finally {
                        }
                    }
                }
                return parser;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public final int getLogEntryCount() {
        return this.logEntry_.size();
    }
}
