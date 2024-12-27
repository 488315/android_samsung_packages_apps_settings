package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtobufArrayList;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PowerAnomalyEventList extends GeneratedMessageLite {
    private static final PowerAnomalyEventList DEFAULT_INSTANCE;
    private static volatile Parser PARSER = null;
    public static final int POWER_ANOMALY_EVENTS_FIELD_NUMBER = 1;
    private Internal.ProtobufList powerAnomalyEvents_ = ProtobufArrayList.EMPTY_LIST;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    static {
        PowerAnomalyEventList powerAnomalyEventList = new PowerAnomalyEventList();
        DEFAULT_INSTANCE = powerAnomalyEventList;
        GeneratedMessageLite.registerDefaultInstance(
                PowerAnomalyEventList.class, powerAnomalyEventList);
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
                        new Object[] {"powerAnomalyEvents_", PowerAnomalyEvent.class});
            case 3:
                return new PowerAnomalyEventList();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (PowerAnomalyEventList.class) {
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

    public final int getPowerAnomalyEventsCount() {
        return this.powerAnomalyEvents_.size();
    }

    public final Internal.ProtobufList getPowerAnomalyEventsList() {
        return this.powerAnomalyEvents_;
    }
}
