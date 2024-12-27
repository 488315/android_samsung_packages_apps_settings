package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryUsageDiff extends GeneratedMessageLite {
    public static final int APP_OPTIMIZATION_MODE_FIELD_NUMBER = 18;
    public static final int BACKGROUND_USAGE_CONSUME_POWER_FIELD_NUMBER = 11;
    public static final int BACKGROUND_USAGE_TIME_FIELD_NUMBER = 15;
    public static final int CACHED_USAGE_CONSUME_POWER_FIELD_NUMBER = 13;
    public static final int COMPONENT_ID_FIELD_NUMBER = 7;
    public static final int CONSUMER_TYPE_FIELD_NUMBER = 8;
    public static final int CONSUME_POWER_FIELD_NUMBER = 9;
    private static final BatteryUsageDiff DEFAULT_INSTANCE;
    public static final int FOREGROUND_SERVICE_USAGE_CONSUME_POWER_FIELD_NUMBER = 12;
    public static final int FOREGROUND_SERVICE_USAGE_TIME_FIELD_NUMBER = 17;
    public static final int FOREGROUND_USAGE_CONSUME_POWER_FIELD_NUMBER = 10;
    public static final int FOREGROUND_USAGE_TIME_FIELD_NUMBER = 14;
    public static final int IS_HIDDEN_FIELD_NUMBER = 6;
    public static final int KEY_FIELD_NUMBER = 5;
    public static final int LABEL_FIELD_NUMBER = 4;
    public static final int PACKAGE_NAME_FIELD_NUMBER = 3;
    private static volatile Parser PARSER = null;
    public static final int SCREEN_ON_TIME_FIELD_NUMBER = 16;
    public static final int UID_FIELD_NUMBER = 1;
    public static final int USER_ID_FIELD_NUMBER = 2;
    private int appOptimizationMode_;
    private double backgroundUsageConsumePower_;
    private long backgroundUsageTime_;
    private int bitField0_;
    private double cachedUsageConsumePower_;
    private int componentId_;
    private double consumePower_;
    private int consumerType_;
    private double foregroundServiceUsageConsumePower_;
    private long foregroundServiceUsageTime_;
    private double foregroundUsageConsumePower_;
    private long foregroundUsageTime_;
    private boolean isHidden_;
    private long screenOnTime_;
    private long uid_;
    private long userId_;
    private String packageName_ = ApnSettings.MVNO_NONE;
    private String label_ = ApnSettings.MVNO_NONE;
    private String key_ = ApnSettings.MVNO_NONE;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    /* renamed from: -$$Nest$msetBackgroundUsageConsumePower, reason: not valid java name */
    public static void m914$$Nest$msetBackgroundUsageConsumePower(
            BatteryUsageDiff batteryUsageDiff, double d) {
        batteryUsageDiff.bitField0_ |= 1024;
        batteryUsageDiff.backgroundUsageConsumePower_ = d;
    }

    /* renamed from: -$$Nest$msetBackgroundUsageTime, reason: not valid java name */
    public static void m915$$Nest$msetBackgroundUsageTime(
            BatteryUsageDiff batteryUsageDiff, long j) {
        batteryUsageDiff.bitField0_ |= NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        batteryUsageDiff.backgroundUsageTime_ = j;
    }

    /* renamed from: -$$Nest$msetCachedUsageConsumePower, reason: not valid java name */
    public static void m916$$Nest$msetCachedUsageConsumePower(
            BatteryUsageDiff batteryUsageDiff, double d) {
        batteryUsageDiff.bitField0_ |= 4096;
        batteryUsageDiff.cachedUsageConsumePower_ = d;
    }

    /* renamed from: -$$Nest$msetComponentId, reason: not valid java name */
    public static void m917$$Nest$msetComponentId(BatteryUsageDiff batteryUsageDiff, int i) {
        batteryUsageDiff.bitField0_ |= 64;
        batteryUsageDiff.componentId_ = i;
    }

    /* renamed from: -$$Nest$msetConsumePower, reason: not valid java name */
    public static void m918$$Nest$msetConsumePower(BatteryUsageDiff batteryUsageDiff, double d) {
        batteryUsageDiff.bitField0_ |= 256;
        batteryUsageDiff.consumePower_ = d;
    }

    /* renamed from: -$$Nest$msetConsumerType, reason: not valid java name */
    public static void m919$$Nest$msetConsumerType(BatteryUsageDiff batteryUsageDiff, int i) {
        batteryUsageDiff.bitField0_ |= 128;
        batteryUsageDiff.consumerType_ = i;
    }

    /* renamed from: -$$Nest$msetForegroundServiceUsageConsumePower, reason: not valid java name */
    public static void m920$$Nest$msetForegroundServiceUsageConsumePower(
            BatteryUsageDiff batteryUsageDiff, double d) {
        batteryUsageDiff.bitField0_ |= 2048;
        batteryUsageDiff.foregroundServiceUsageConsumePower_ = d;
    }

    /* renamed from: -$$Nest$msetForegroundServiceUsageTime, reason: not valid java name */
    public static void m921$$Nest$msetForegroundServiceUsageTime(
            BatteryUsageDiff batteryUsageDiff, long j) {
        batteryUsageDiff.bitField0_ |= 65536;
        batteryUsageDiff.foregroundServiceUsageTime_ = j;
    }

    /* renamed from: -$$Nest$msetForegroundUsageConsumePower, reason: not valid java name */
    public static void m922$$Nest$msetForegroundUsageConsumePower(
            BatteryUsageDiff batteryUsageDiff, double d) {
        batteryUsageDiff.bitField0_ |= 512;
        batteryUsageDiff.foregroundUsageConsumePower_ = d;
    }

    /* renamed from: -$$Nest$msetForegroundUsageTime, reason: not valid java name */
    public static void m923$$Nest$msetForegroundUsageTime(
            BatteryUsageDiff batteryUsageDiff, long j) {
        batteryUsageDiff.bitField0_ |= 8192;
        batteryUsageDiff.foregroundUsageTime_ = j;
    }

    /* renamed from: -$$Nest$msetIsHidden, reason: not valid java name */
    public static void m924$$Nest$msetIsHidden(BatteryUsageDiff batteryUsageDiff, boolean z) {
        batteryUsageDiff.bitField0_ |= 32;
        batteryUsageDiff.isHidden_ = z;
    }

    /* renamed from: -$$Nest$msetKey, reason: not valid java name */
    public static void m925$$Nest$msetKey(BatteryUsageDiff batteryUsageDiff, String str) {
        batteryUsageDiff.getClass();
        str.getClass();
        batteryUsageDiff.bitField0_ |= 16;
        batteryUsageDiff.key_ = str;
    }

    /* renamed from: -$$Nest$msetLabel, reason: not valid java name */
    public static void m926$$Nest$msetLabel(BatteryUsageDiff batteryUsageDiff, String str) {
        batteryUsageDiff.getClass();
        str.getClass();
        batteryUsageDiff.bitField0_ |= 8;
        batteryUsageDiff.label_ = str;
    }

    /* renamed from: -$$Nest$msetPackageName, reason: not valid java name */
    public static void m927$$Nest$msetPackageName(BatteryUsageDiff batteryUsageDiff, String str) {
        batteryUsageDiff.getClass();
        str.getClass();
        batteryUsageDiff.bitField0_ |= 4;
        batteryUsageDiff.packageName_ = str;
    }

    /* renamed from: -$$Nest$msetScreenOnTime, reason: not valid java name */
    public static void m928$$Nest$msetScreenOnTime(BatteryUsageDiff batteryUsageDiff, long j) {
        batteryUsageDiff.bitField0_ |= NetworkAnalyticsConstants.DataPoints.FLAG_UID;
        batteryUsageDiff.screenOnTime_ = j;
    }

    /* renamed from: -$$Nest$msetUid, reason: not valid java name */
    public static void m929$$Nest$msetUid(BatteryUsageDiff batteryUsageDiff, long j) {
        batteryUsageDiff.bitField0_ |= 1;
        batteryUsageDiff.uid_ = j;
    }

    /* renamed from: -$$Nest$msetUserId, reason: not valid java name */
    public static void m930$$Nest$msetUserId(BatteryUsageDiff batteryUsageDiff, long j) {
        batteryUsageDiff.bitField0_ |= 2;
        batteryUsageDiff.userId_ = j;
    }

    static {
        BatteryUsageDiff batteryUsageDiff = new BatteryUsageDiff();
        DEFAULT_INSTANCE = batteryUsageDiff;
        GeneratedMessageLite.registerDefaultInstance(BatteryUsageDiff.class, batteryUsageDiff);
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
                        "\u0001\u0012\u0000\u0001\u0001\u0012\u0012\u0000\u0000\u0000\u0001ဂ\u0000\u0002ဂ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဇ\u0005\u0007င\u0006\bင\u0007"
                            + "\tက\b\n"
                            + "က\t\u000bက\n"
                            + "\fက\u000b\r"
                            + "က\f\u000eဂ\r"
                            + "\u000fဂ\u000e\u0010ဂ\u000f\u0011ဂ\u0010\u0012ဌ\u0011",
                        new Object[] {
                            "bitField0_",
                            "uid_",
                            "userId_",
                            "packageName_",
                            "label_",
                            "key_",
                            "isHidden_",
                            "componentId_",
                            "consumerType_",
                            "consumePower_",
                            "foregroundUsageConsumePower_",
                            "backgroundUsageConsumePower_",
                            "foregroundServiceUsageConsumePower_",
                            "cachedUsageConsumePower_",
                            "foregroundUsageTime_",
                            "backgroundUsageTime_",
                            "screenOnTime_",
                            "foregroundServiceUsageTime_",
                            "appOptimizationMode_",
                            BatteryOptimizationMode.BatteryOptimizationModeVerifier.INSTANCE
                        });
            case 3:
                return new BatteryUsageDiff();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (BatteryUsageDiff.class) {
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

    public final double getBackgroundUsageConsumePower() {
        return this.backgroundUsageConsumePower_;
    }

    public final long getBackgroundUsageTime() {
        return this.backgroundUsageTime_;
    }

    public final double getCachedUsageConsumePower() {
        return this.cachedUsageConsumePower_;
    }

    public final int getComponentId() {
        return this.componentId_;
    }

    public final double getConsumePower() {
        return this.consumePower_;
    }

    public final int getConsumerType() {
        return this.consumerType_;
    }

    public final double getForegroundServiceUsageConsumePower() {
        return this.foregroundServiceUsageConsumePower_;
    }

    public final long getForegroundServiceUsageTime() {
        return this.foregroundServiceUsageTime_;
    }

    public final double getForegroundUsageConsumePower() {
        return this.foregroundUsageConsumePower_;
    }

    public final long getForegroundUsageTime() {
        return this.foregroundUsageTime_;
    }

    public final boolean getIsHidden() {
        return this.isHidden_;
    }

    public final String getKey() {
        return this.key_;
    }

    public final String getLabel() {
        return this.label_;
    }

    public final String getPackageName() {
        return this.packageName_;
    }

    public final long getScreenOnTime() {
        return this.screenOnTime_;
    }

    public final long getUid() {
        return this.uid_;
    }

    public final long getUserId() {
        return this.userId_;
    }
}
