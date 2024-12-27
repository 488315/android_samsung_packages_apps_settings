package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtobufArrayList;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryUsageSlot extends GeneratedMessageLite {
    public static final int APP_USAGE_FIELD_NUMBER = 6;
    private static final BatteryUsageSlot DEFAULT_INSTANCE;
    public static final int END_BATTERY_LEVEL_FIELD_NUMBER = 4;
    public static final int END_TIMESTAMP_FIELD_NUMBER = 2;
    private static volatile Parser PARSER = null;
    public static final int SCREEN_ON_TIME_FIELD_NUMBER = 5;
    public static final int START_BATTERY_LEVEL_FIELD_NUMBER = 3;
    public static final int START_TIMESTAMP_FIELD_NUMBER = 1;
    public static final int SYSTEM_USAGE_FIELD_NUMBER = 7;
    private Internal.ProtobufList appUsage_;
    private int bitField0_;
    private int endBatteryLevel_;
    private long endTimestamp_;
    private long screenOnTime_;
    private int startBatteryLevel_;
    private long startTimestamp_;
    private Internal.ProtobufList systemUsage_;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    static {
        BatteryUsageSlot batteryUsageSlot = new BatteryUsageSlot();
        DEFAULT_INSTANCE = batteryUsageSlot;
        GeneratedMessageLite.registerDefaultInstance(BatteryUsageSlot.class, batteryUsageSlot);
    }

    public BatteryUsageSlot() {
        ProtobufArrayList protobufArrayList = ProtobufArrayList.EMPTY_LIST;
        this.appUsage_ = protobufArrayList;
        this.systemUsage_ = protobufArrayList;
    }

    public static BatteryUsageSlot getDefaultInstance() {
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
                        "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0002\u0000\u0001ဂ\u0000\u0002ဂ\u0001\u0003င\u0002\u0004င\u0003\u0005ဂ\u0004\u0006\u001b\u0007\u001b",
                        new Object[] {
                            "bitField0_",
                            "startTimestamp_",
                            "endTimestamp_",
                            "startBatteryLevel_",
                            "endBatteryLevel_",
                            "screenOnTime_",
                            "appUsage_",
                            BatteryUsageDiff.class,
                            "systemUsage_",
                            BatteryUsageDiff.class
                        });
            case 3:
                return new BatteryUsageSlot();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (BatteryUsageSlot.class) {
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

    public final Internal.ProtobufList getAppUsageList() {
        return this.appUsage_;
    }

    public final int getEndBatteryLevel() {
        return this.endBatteryLevel_;
    }

    public final long getEndTimestamp() {
        return this.endTimestamp_;
    }

    public final long getScreenOnTime() {
        return this.screenOnTime_;
    }

    public final int getStartBatteryLevel() {
        return this.startBatteryLevel_;
    }

    public final long getStartTimestamp() {
        return this.startTimestamp_;
    }

    public final Internal.ProtobufList getSystemUsageList() {
        return this.systemUsage_;
    }
}
