package com.android.settings.fuelgauge;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryOptimizeHistoricalLogEntry extends GeneratedMessageLite {
    public static final int ACTION_DESCRIPTION_FIELD_NUMBER = 3;
    public static final int ACTION_FIELD_NUMBER = 2;
    private static final BatteryOptimizeHistoricalLogEntry DEFAULT_INSTANCE;
    public static final int PACKAGE_NAME_FIELD_NUMBER = 1;
    private static volatile Parser PARSER = null;
    public static final int TIMESTAMP_FIELD_NUMBER = 4;
    private int action_;
    private int bitField0_;
    private long timestamp_;
    private String packageName_ = ApnSettings.MVNO_NONE;
    private String actionDescription_ = ApnSettings.MVNO_NONE;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum Action implements Internal.EnumLite {
        UNKNOWN(0),
        LEAVE(1),
        APPLY(2),
        RESET(3),
        RESTORE(4),
        BACKUP(5),
        FORCE_RESET(6),
        EXTERNAL_UPDATE(7),
        EXPIRATION_RESET(8);

        private final int value;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class ActionVerifier implements Internal.EnumVerifier {
            public static final ActionVerifier INSTANCE = new ActionVerifier();

            @Override // com.google.protobuf.Internal.EnumVerifier
            public final boolean isInRange(int i) {
                Action action;
                switch (i) {
                    case 0:
                        action = Action.UNKNOWN;
                        break;
                    case 1:
                        action = Action.LEAVE;
                        break;
                    case 2:
                        action = Action.APPLY;
                        break;
                    case 3:
                        action = Action.RESET;
                        break;
                    case 4:
                        action = Action.RESTORE;
                        break;
                    case 5:
                        action = Action.BACKUP;
                        break;
                    case 6:
                        action = Action.FORCE_RESET;
                        break;
                    case 7:
                        action = Action.EXTERNAL_UPDATE;
                        break;
                    case 8:
                        action = Action.EXPIRATION_RESET;
                        break;
                    default:
                        action = null;
                        break;
                }
                return action != null;
            }
        }

        Action(int i) {
            this.value = i;
        }

        @Override // com.google.protobuf.Internal.EnumLite
        public final int getNumber() {
            return this.value;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    /* renamed from: -$$Nest$msetAction, reason: not valid java name */
    public static void m865$$Nest$msetAction(
            BatteryOptimizeHistoricalLogEntry batteryOptimizeHistoricalLogEntry, Action action) {
        batteryOptimizeHistoricalLogEntry.getClass();
        batteryOptimizeHistoricalLogEntry.action_ = action.getNumber();
        batteryOptimizeHistoricalLogEntry.bitField0_ |= 2;
    }

    /* renamed from: -$$Nest$msetActionDescription, reason: not valid java name */
    public static void m866$$Nest$msetActionDescription(
            BatteryOptimizeHistoricalLogEntry batteryOptimizeHistoricalLogEntry, String str) {
        batteryOptimizeHistoricalLogEntry.getClass();
        str.getClass();
        batteryOptimizeHistoricalLogEntry.bitField0_ |= 4;
        batteryOptimizeHistoricalLogEntry.actionDescription_ = str;
    }

    /* renamed from: -$$Nest$msetPackageName, reason: not valid java name */
    public static void m867$$Nest$msetPackageName(
            BatteryOptimizeHistoricalLogEntry batteryOptimizeHistoricalLogEntry, String str) {
        batteryOptimizeHistoricalLogEntry.getClass();
        str.getClass();
        batteryOptimizeHistoricalLogEntry.bitField0_ |= 1;
        batteryOptimizeHistoricalLogEntry.packageName_ = str;
    }

    /* renamed from: -$$Nest$msetTimestamp, reason: not valid java name */
    public static void m868$$Nest$msetTimestamp(
            BatteryOptimizeHistoricalLogEntry batteryOptimizeHistoricalLogEntry, long j) {
        batteryOptimizeHistoricalLogEntry.bitField0_ |= 8;
        batteryOptimizeHistoricalLogEntry.timestamp_ = j;
    }

    static {
        BatteryOptimizeHistoricalLogEntry batteryOptimizeHistoricalLogEntry =
                new BatteryOptimizeHistoricalLogEntry();
        DEFAULT_INSTANCE = batteryOptimizeHistoricalLogEntry;
        GeneratedMessageLite.registerDefaultInstance(
                BatteryOptimizeHistoricalLogEntry.class, batteryOptimizeHistoricalLogEntry);
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
                        "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဌ\u0001\u0003ဈ\u0002\u0004ဂ\u0003",
                        new Object[] {
                            "bitField0_",
                            "packageName_",
                            "action_",
                            Action.ActionVerifier.INSTANCE,
                            "actionDescription_",
                            "timestamp_"
                        });
            case 3:
                return new BatteryOptimizeHistoricalLogEntry();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (BatteryOptimizeHistoricalLogEntry.class) {
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
}
