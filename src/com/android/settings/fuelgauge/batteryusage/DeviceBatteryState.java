package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DeviceBatteryState extends GeneratedMessageLite {
    public static final int BATTERY_HEALTH_FIELD_NUMBER = 3;
    public static final int BATTERY_LEVEL_FIELD_NUMBER = 1;
    public static final int BATTERY_STATUS_FIELD_NUMBER = 2;
    private static final DeviceBatteryState DEFAULT_INSTANCE;
    private static volatile Parser PARSER;
    private int batteryHealth_;
    private int batteryLevel_;
    private int batteryStatus_;
    private int bitField0_;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    /* renamed from: -$$Nest$msetBatteryHealth, reason: not valid java name */
    public static void m935$$Nest$msetBatteryHealth(DeviceBatteryState deviceBatteryState) {
        deviceBatteryState.bitField0_ |= 4;
        deviceBatteryState.batteryHealth_ = 0;
    }

    /* renamed from: -$$Nest$msetBatteryLevel, reason: not valid java name */
    public static void m936$$Nest$msetBatteryLevel(DeviceBatteryState deviceBatteryState) {
        deviceBatteryState.bitField0_ |= 1;
        deviceBatteryState.batteryLevel_ = 0;
    }

    /* renamed from: -$$Nest$msetBatteryStatus, reason: not valid java name */
    public static void m937$$Nest$msetBatteryStatus(DeviceBatteryState deviceBatteryState) {
        deviceBatteryState.bitField0_ |= 2;
        deviceBatteryState.batteryStatus_ = 0;
    }

    static {
        DeviceBatteryState deviceBatteryState = new DeviceBatteryState();
        DEFAULT_INSTANCE = deviceBatteryState;
        GeneratedMessageLite.registerDefaultInstance(DeviceBatteryState.class, deviceBatteryState);
    }

    public static DeviceBatteryState getDefaultInstance() {
        return DEFAULT_INSTANCE;
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
                        "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001င\u0000\u0002င\u0001\u0003င\u0002",
                        new Object[] {
                            "bitField0_", "batteryLevel_", "batteryStatus_", "batteryHealth_"
                        });
            case 3:
                return new DeviceBatteryState();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (DeviceBatteryState.class) {
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

    public final int getBatteryHealth() {
        return this.batteryHealth_;
    }

    public final int getBatteryLevel() {
        return this.batteryLevel_;
    }

    public final int getBatteryStatus() {
        return this.batteryStatus_;
    }
}
