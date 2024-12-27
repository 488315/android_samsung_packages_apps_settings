package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppOptimizationModeEvent extends GeneratedMessageLite {
    private static final AppOptimizationModeEvent DEFAULT_INSTANCE;
    public static final int EXPIRATION_TIME_FIELD_NUMBER = 4;
    public static final int PACKAGE_NAME_FIELD_NUMBER = 2;
    private static volatile Parser PARSER = null;
    public static final int RESET_OPTIMIZATION_MODE_FIELD_NUMBER = 3;
    public static final int UID_FIELD_NUMBER = 1;
    private int bitField0_;
    private long expirationTime_;
    private String packageName_ = ApnSettings.MVNO_NONE;
    private int resetOptimizationMode_;
    private int uid_;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    /* renamed from: -$$Nest$msetExpirationTime, reason: not valid java name */
    public static void m873$$Nest$msetExpirationTime(
            AppOptimizationModeEvent appOptimizationModeEvent, long j) {
        appOptimizationModeEvent.bitField0_ |= 8;
        appOptimizationModeEvent.expirationTime_ = j;
    }

    /* renamed from: -$$Nest$msetPackageName, reason: not valid java name */
    public static void m874$$Nest$msetPackageName(
            AppOptimizationModeEvent appOptimizationModeEvent, String str) {
        appOptimizationModeEvent.getClass();
        str.getClass();
        appOptimizationModeEvent.bitField0_ |= 2;
        appOptimizationModeEvent.packageName_ = str;
    }

    /* renamed from: -$$Nest$msetResetOptimizationMode, reason: not valid java name */
    public static void m875$$Nest$msetResetOptimizationMode(
            AppOptimizationModeEvent appOptimizationModeEvent, int i) {
        appOptimizationModeEvent.bitField0_ |= 4;
        appOptimizationModeEvent.resetOptimizationMode_ = i;
    }

    /* renamed from: -$$Nest$msetUid, reason: not valid java name */
    public static void m876$$Nest$msetUid(
            AppOptimizationModeEvent appOptimizationModeEvent, int i) {
        appOptimizationModeEvent.bitField0_ |= 1;
        appOptimizationModeEvent.uid_ = i;
    }

    static {
        AppOptimizationModeEvent appOptimizationModeEvent = new AppOptimizationModeEvent();
        DEFAULT_INSTANCE = appOptimizationModeEvent;
        GeneratedMessageLite.registerDefaultInstance(
                AppOptimizationModeEvent.class, appOptimizationModeEvent);
    }

    public static AppOptimizationModeEvent getDefaultInstance() {
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
                        "\u0000\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001င\u0000\u0002ለ\u0001\u0003င\u0002\u0004ဂ\u0003",
                        new Object[] {
                            "bitField0_",
                            "uid_",
                            "packageName_",
                            "resetOptimizationMode_",
                            "expirationTime_"
                        });
            case 3:
                return new AppOptimizationModeEvent();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (AppOptimizationModeEvent.class) {
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

    public final int getResetOptimizationMode() {
        return this.resetOptimizationMode_;
    }

    public final int getUid() {
        return this.uid_;
    }
}
