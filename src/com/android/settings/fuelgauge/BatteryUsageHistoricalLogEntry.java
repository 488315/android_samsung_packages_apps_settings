package com.android.settings.fuelgauge;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryUsageHistoricalLogEntry extends GeneratedMessageLite {
    public static final int ACTION_DESCRIPTION_FIELD_NUMBER = 3;
    public static final int ACTION_FIELD_NUMBER = 2;
    private static final BatteryUsageHistoricalLogEntry DEFAULT_INSTANCE;
    private static volatile Parser PARSER = null;
    public static final int TIMESTAMP_FIELD_NUMBER = 1;
    private String actionDescription_ = ApnSettings.MVNO_NONE;
    private int action_;
    private int bitField0_;
    private long timestamp_;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum Action implements Internal.EnumLite {
        UNKNOWN(0),
        SCHEDULE_JOB(1),
        EXECUTE_JOB(2),
        RECHECK_JOB(3),
        FETCH_USAGE_DATA(4),
        INSERT_USAGE_DATA(5),
        TIME_UPDATED(6),
        TIMEZONE_UPDATED(7);

        private final int value;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class ActionVerifier implements Internal.EnumVerifier {
            public static final ActionVerifier INSTANCE = new ActionVerifier();

            @Override // com.google.protobuf.Internal.EnumVerifier
            public final boolean isInRange(int i) {
                return Action.forNumber(i) != null;
            }
        }

        Action(int i) {
            this.value = i;
        }

        public static Action forNumber(int i) {
            switch (i) {
                case 0:
                    return UNKNOWN;
                case 1:
                    return SCHEDULE_JOB;
                case 2:
                    return EXECUTE_JOB;
                case 3:
                    return RECHECK_JOB;
                case 4:
                    return FETCH_USAGE_DATA;
                case 5:
                    return INSERT_USAGE_DATA;
                case 6:
                    return TIME_UPDATED;
                case 7:
                    return TIMEZONE_UPDATED;
                default:
                    return null;
            }
        }

        @Override // com.google.protobuf.Internal.EnumLite
        public final int getNumber() {
            return this.value;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    static {
        BatteryUsageHistoricalLogEntry batteryUsageHistoricalLogEntry =
                new BatteryUsageHistoricalLogEntry();
        DEFAULT_INSTANCE = batteryUsageHistoricalLogEntry;
        GeneratedMessageLite.registerDefaultInstance(
                BatteryUsageHistoricalLogEntry.class, batteryUsageHistoricalLogEntry);
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
                        "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဂ\u0000\u0002ဌ\u0001\u0003ဈ\u0002",
                        new Object[] {
                            "bitField0_",
                            "timestamp_",
                            "action_",
                            Action.ActionVerifier.INSTANCE,
                            "actionDescription_"
                        });
            case 3:
                return new BatteryUsageHistoricalLogEntry();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (BatteryUsageHistoricalLogEntry.class) {
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

    public final Action getAction() {
        Action forNumber = Action.forNumber(this.action_);
        return forNumber == null ? Action.UNKNOWN : forNumber;
    }

    public final String getActionDescription() {
        return this.actionDescription_;
    }

    public final long getTimestamp() {
        return this.timestamp_;
    }
}
