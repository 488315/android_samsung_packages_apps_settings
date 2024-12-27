package com.android.settings.fuelgauge;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtobufArrayList;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryUsageHistoricalLog extends GeneratedMessageLite {
    private static final BatteryUsageHistoricalLog DEFAULT_INSTANCE;
    public static final int LOG_ENTRY_FIELD_NUMBER = 1;
    private static volatile Parser PARSER;
    private Internal.ProtobufList logEntry_ = ProtobufArrayList.EMPTY_LIST;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    static {
        BatteryUsageHistoricalLog batteryUsageHistoricalLog = new BatteryUsageHistoricalLog();
        DEFAULT_INSTANCE = batteryUsageHistoricalLog;
        GeneratedMessageLite.registerDefaultInstance(
                BatteryUsageHistoricalLog.class, batteryUsageHistoricalLog);
    }

    public static BatteryUsageHistoricalLog getDefaultInstance() {
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
                        new Object[] {"logEntry_", BatteryUsageHistoricalLogEntry.class});
            case 3:
                return new BatteryUsageHistoricalLog();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (BatteryUsageHistoricalLog.class) {
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

    public final Internal.ProtobufList getLogEntryList() {
        return this.logEntry_;
    }
}
