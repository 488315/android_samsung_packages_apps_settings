package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryEvent extends GeneratedMessageLite {
    public static final int BATTERY_LEVEL_FIELD_NUMBER = 3;
    private static final BatteryEvent DEFAULT_INSTANCE;
    private static volatile Parser PARSER = null;
    public static final int TIMESTAMP_FIELD_NUMBER = 1;
    public static final int TYPE_FIELD_NUMBER = 2;
    private int batteryLevel_;
    private int bitField0_;
    private long timestamp_;
    private int type_;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    /* renamed from: -$$Nest$msetBatteryLevel, reason: not valid java name */
    public static void m889$$Nest$msetBatteryLevel(BatteryEvent batteryEvent, int i) {
        batteryEvent.bitField0_ |= 4;
        batteryEvent.batteryLevel_ = i;
    }

    /* renamed from: -$$Nest$msetTimestamp, reason: not valid java name */
    public static void m890$$Nest$msetTimestamp(BatteryEvent batteryEvent, long j) {
        batteryEvent.bitField0_ |= 1;
        batteryEvent.timestamp_ = j;
    }

    /* renamed from: -$$Nest$msetType, reason: not valid java name */
    public static void m891$$Nest$msetType(
            BatteryEvent batteryEvent, BatteryEventType batteryEventType) {
        batteryEvent.getClass();
        batteryEvent.type_ = batteryEventType.getNumber();
        batteryEvent.bitField0_ |= 2;
    }

    static {
        BatteryEvent batteryEvent = new BatteryEvent();
        DEFAULT_INSTANCE = batteryEvent;
        GeneratedMessageLite.registerDefaultInstance(BatteryEvent.class, batteryEvent);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
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
                        "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဂ\u0000\u0002ဌ\u0001\u0003င\u0002",
                        new Object[] {
                            "bitField0_",
                            "timestamp_",
                            "type_",
                            BatteryEventType.BatteryEventTypeVerifier.INSTANCE,
                            "batteryLevel_"
                        });
            case 3:
                return new BatteryEvent();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (BatteryEvent.class) {
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

    public final int getBatteryLevel() {
        return this.batteryLevel_;
    }

    public final long getTimestamp() {
        return this.timestamp_;
    }

    public final BatteryEventType getType() {
        BatteryEventType forNumber = BatteryEventType.forNumber(this.type_);
        return forNumber == null ? BatteryEventType.UNKNOWN_EVENT : forNumber;
    }
}
