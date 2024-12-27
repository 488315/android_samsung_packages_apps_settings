package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppUsageEndPoint extends GeneratedMessageLite {
    private static final AppUsageEndPoint DEFAULT_INSTANCE;
    private static volatile Parser PARSER = null;
    public static final int TIMESTAMP_FIELD_NUMBER = 2;
    public static final int TYPE_FIELD_NUMBER = 1;
    private int bitField0_;
    private long timestamp_;
    private int type_ = 1;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    /* renamed from: -$$Nest$msetTimestamp, reason: not valid java name */
    public static void m877$$Nest$msetTimestamp(AppUsageEndPoint appUsageEndPoint, long j) {
        appUsageEndPoint.bitField0_ |= 2;
        appUsageEndPoint.timestamp_ = j;
    }

    /* renamed from: -$$Nest$msetType, reason: not valid java name */
    public static void m878$$Nest$msetType(
            AppUsageEndPoint appUsageEndPoint, AppUsageEndPointType appUsageEndPointType) {
        appUsageEndPoint.getClass();
        appUsageEndPoint.type_ = appUsageEndPointType.getNumber();
        appUsageEndPoint.bitField0_ |= 1;
    }

    static {
        AppUsageEndPoint appUsageEndPoint = new AppUsageEndPoint();
        DEFAULT_INSTANCE = appUsageEndPoint;
        GeneratedMessageLite.registerDefaultInstance(AppUsageEndPoint.class, appUsageEndPoint);
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
                        "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဂ\u0001",
                        new Object[] {
                            "bitField0_",
                            "type_",
                            AppUsageEndPointType.AppUsageEndPointTypeVerifier.INSTANCE,
                            "timestamp_"
                        });
            case 3:
                return new AppUsageEndPoint();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (AppUsageEndPoint.class) {
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

    public final long getTimestamp() {
        return this.timestamp_;
    }

    public final AppUsageEndPointType getType() {
        int i = this.type_;
        AppUsageEndPointType appUsageEndPointType = AppUsageEndPointType.START;
        AppUsageEndPointType appUsageEndPointType2 =
                i != 1 ? i != 2 ? null : AppUsageEndPointType.END : appUsageEndPointType;
        return appUsageEndPointType2 == null ? appUsageEndPointType : appUsageEndPointType2;
    }
}
