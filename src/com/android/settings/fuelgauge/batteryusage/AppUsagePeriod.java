package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppUsagePeriod extends GeneratedMessageLite {
    private static final AppUsagePeriod DEFAULT_INSTANCE;
    public static final int END_TIME_FIELD_NUMBER = 2;
    private static volatile Parser PARSER = null;
    public static final int START_TIME_FIELD_NUMBER = 1;
    private int bitField0_;
    private long endTime_;
    private long startTime_;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {
        public final void setEndTime(long j) {
            copyOnWrite();
            AppUsagePeriod.m886$$Nest$msetEndTime((AppUsagePeriod) this.instance, j);
        }

        public final void setStartTime(long j) {
            copyOnWrite();
            AppUsagePeriod.m887$$Nest$msetStartTime((AppUsagePeriod) this.instance, j);
        }
    }

    /* renamed from: -$$Nest$msetEndTime, reason: not valid java name */
    public static void m886$$Nest$msetEndTime(AppUsagePeriod appUsagePeriod, long j) {
        appUsagePeriod.bitField0_ |= 2;
        appUsagePeriod.endTime_ = j;
    }

    /* renamed from: -$$Nest$msetStartTime, reason: not valid java name */
    public static void m887$$Nest$msetStartTime(AppUsagePeriod appUsagePeriod, long j) {
        appUsagePeriod.bitField0_ |= 1;
        appUsagePeriod.startTime_ = j;
    }

    static {
        AppUsagePeriod appUsagePeriod = new AppUsagePeriod();
        DEFAULT_INSTANCE = appUsagePeriod;
        GeneratedMessageLite.registerDefaultInstance(AppUsagePeriod.class, appUsagePeriod);
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
                        "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဂ\u0000\u0002ဂ\u0001",
                        new Object[] {"bitField0_", "startTime_", "endTime_"});
            case 3:
                return new AppUsagePeriod();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (AppUsagePeriod.class) {
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

    public final long getEndTime() {
        return this.endTime_;
    }

    public final long getStartTime() {
        return this.startTime_;
    }

    public final boolean hasStartTime() {
        return (this.bitField0_ & 1) != 0;
    }
}
