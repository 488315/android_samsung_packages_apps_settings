package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryInformation extends GeneratedMessageLite {
    public static final int APP_LABEL_FIELD_NUMBER = 7;
    public static final int BACKGROUND_USAGE_CONSUME_POWER_FIELD_NUMBER = 18;
    public static final int BACKGROUND_USAGE_TIME_IN_MS_FIELD_NUMBER = 15;
    public static final int BOOT_TIMESTAMP_FIELD_NUMBER = 3;
    public static final int CACHED_USAGE_CONSUME_POWER_FIELD_NUMBER = 19;
    public static final int CONSUME_POWER_FIELD_NUMBER = 11;
    private static final BatteryInformation DEFAULT_INSTANCE;
    public static final int DEVICE_BATTERY_STATE_FIELD_NUMBER = 1;
    public static final int DRAIN_TYPE_FIELD_NUMBER = 13;
    public static final int FOREGROUND_SERVICE_USAGE_CONSUME_POWER_FIELD_NUMBER = 17;
    public static final int FOREGROUND_SERVICE_USAGE_TIME_IN_MS_FIELD_NUMBER = 20;
    public static final int FOREGROUND_USAGE_CONSUME_POWER_FIELD_NUMBER = 16;
    public static final int FOREGROUND_USAGE_TIME_IN_MS_FIELD_NUMBER = 14;
    public static final int IS_HIDDEN_FIELD_NUMBER = 2;
    private static volatile Parser PARSER = null;
    public static final int PERCENT_OF_TOTAL_FIELD_NUMBER = 12;
    public static final int TOTAL_POWER_FIELD_NUMBER = 10;
    public static final int ZONE_ID_FIELD_NUMBER = 4;
    private double backgroundUsageConsumePower_;
    private long backgroundUsageTimeInMs_;
    private int bitField0_;
    private long bootTimestamp_;
    private double cachedUsageConsumePower_;
    private double consumePower_;
    private DeviceBatteryState deviceBatteryState_;
    private int drainType_;
    private double foregroundServiceUsageConsumePower_;
    private long foregroundServiceUsageTimeInMs_;
    private double foregroundUsageConsumePower_;
    private long foregroundUsageTimeInMs_;
    private boolean isHidden_;
    private double percentOfTotal_;
    private double totalPower_;
    private String zoneId_ = ApnSettings.MVNO_NONE;
    private String appLabel_ = ApnSettings.MVNO_NONE;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    /* renamed from: -$$Nest$msetAppLabel, reason: not valid java name */
    public static void m892$$Nest$msetAppLabel(BatteryInformation batteryInformation, String str) {
        batteryInformation.getClass();
        str.getClass();
        batteryInformation.bitField0_ |= 16;
        batteryInformation.appLabel_ = str;
    }

    /* renamed from: -$$Nest$msetBackgroundUsageConsumePower, reason: not valid java name */
    public static void m893$$Nest$msetBackgroundUsageConsumePower(
            BatteryInformation batteryInformation, double d) {
        batteryInformation.bitField0_ |= 8192;
        batteryInformation.backgroundUsageConsumePower_ = d;
    }

    /* renamed from: -$$Nest$msetBackgroundUsageTimeInMs, reason: not valid java name */
    public static void m894$$Nest$msetBackgroundUsageTimeInMs(
            BatteryInformation batteryInformation, long j) {
        batteryInformation.bitField0_ |= 1024;
        batteryInformation.backgroundUsageTimeInMs_ = j;
    }

    /* renamed from: -$$Nest$msetBootTimestamp, reason: not valid java name */
    public static void m895$$Nest$msetBootTimestamp(BatteryInformation batteryInformation) {
        batteryInformation.bitField0_ |= 4;
        batteryInformation.bootTimestamp_ = 0L;
    }

    /* renamed from: -$$Nest$msetCachedUsageConsumePower, reason: not valid java name */
    public static void m896$$Nest$msetCachedUsageConsumePower(
            BatteryInformation batteryInformation, double d) {
        batteryInformation.bitField0_ |= NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        batteryInformation.cachedUsageConsumePower_ = d;
    }

    /* renamed from: -$$Nest$msetConsumePower, reason: not valid java name */
    public static void m897$$Nest$msetConsumePower(
            BatteryInformation batteryInformation, double d) {
        batteryInformation.bitField0_ |= 64;
        batteryInformation.consumePower_ = d;
    }

    /* renamed from: -$$Nest$msetDeviceBatteryState, reason: not valid java name */
    public static void m898$$Nest$msetDeviceBatteryState(
            BatteryInformation batteryInformation, DeviceBatteryState deviceBatteryState) {
        batteryInformation.getClass();
        batteryInformation.deviceBatteryState_ = deviceBatteryState;
        batteryInformation.bitField0_ |= 1;
    }

    /* renamed from: -$$Nest$msetDrainType, reason: not valid java name */
    public static void m899$$Nest$msetDrainType(BatteryInformation batteryInformation, int i) {
        batteryInformation.bitField0_ |= 256;
        batteryInformation.drainType_ = i;
    }

    /* renamed from: -$$Nest$msetForegroundServiceUsageConsumePower, reason: not valid java name */
    public static void m900$$Nest$msetForegroundServiceUsageConsumePower(
            BatteryInformation batteryInformation, double d) {
        batteryInformation.bitField0_ |= 4096;
        batteryInformation.foregroundServiceUsageConsumePower_ = d;
    }

    /* renamed from: -$$Nest$msetForegroundServiceUsageTimeInMs, reason: not valid java name */
    public static void m901$$Nest$msetForegroundServiceUsageTimeInMs(
            BatteryInformation batteryInformation, long j) {
        batteryInformation.bitField0_ |= NetworkAnalyticsConstants.DataPoints.FLAG_UID;
        batteryInformation.foregroundServiceUsageTimeInMs_ = j;
    }

    /* renamed from: -$$Nest$msetForegroundUsageConsumePower, reason: not valid java name */
    public static void m902$$Nest$msetForegroundUsageConsumePower(
            BatteryInformation batteryInformation, double d) {
        batteryInformation.bitField0_ |= 2048;
        batteryInformation.foregroundUsageConsumePower_ = d;
    }

    /* renamed from: -$$Nest$msetForegroundUsageTimeInMs, reason: not valid java name */
    public static void m903$$Nest$msetForegroundUsageTimeInMs(
            BatteryInformation batteryInformation, long j) {
        batteryInformation.bitField0_ |= 512;
        batteryInformation.foregroundUsageTimeInMs_ = j;
    }

    /* renamed from: -$$Nest$msetIsHidden, reason: not valid java name */
    public static void m904$$Nest$msetIsHidden(BatteryInformation batteryInformation, boolean z) {
        batteryInformation.bitField0_ |= 2;
        batteryInformation.isHidden_ = z;
    }

    /* renamed from: -$$Nest$msetPercentOfTotal, reason: not valid java name */
    public static void m905$$Nest$msetPercentOfTotal(
            BatteryInformation batteryInformation, double d) {
        batteryInformation.bitField0_ |= 128;
        batteryInformation.percentOfTotal_ = d;
    }

    /* renamed from: -$$Nest$msetTotalPower, reason: not valid java name */
    public static void m906$$Nest$msetTotalPower(BatteryInformation batteryInformation, double d) {
        batteryInformation.bitField0_ |= 32;
        batteryInformation.totalPower_ = d;
    }

    /* renamed from: -$$Nest$msetZoneId, reason: not valid java name */
    public static void m907$$Nest$msetZoneId(BatteryInformation batteryInformation, String str) {
        batteryInformation.getClass();
        str.getClass();
        batteryInformation.bitField0_ |= 8;
        batteryInformation.zoneId_ = str;
    }

    static {
        BatteryInformation batteryInformation = new BatteryInformation();
        DEFAULT_INSTANCE = batteryInformation;
        GeneratedMessageLite.registerDefaultInstance(BatteryInformation.class, batteryInformation);
    }

    public static BatteryInformation getDefaultInstance() {
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
                        "\u0001\u0010\u0000\u0001\u0001\u0014\u0010\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဇ\u0001\u0003ဂ\u0002\u0004ဈ\u0003\u0007ဈ\u0004\n"
                            + "က\u0005\u000bက\u0006\fက\u0007\r"
                            + "င\b\u000eဂ\t\u000fဂ\n"
                            + "\u0010က\u000b\u0011က\f\u0012က\r"
                            + "\u0013က\u000e\u0014ဂ\u000f",
                        new Object[] {
                            "bitField0_",
                            "deviceBatteryState_",
                            "isHidden_",
                            "bootTimestamp_",
                            "zoneId_",
                            "appLabel_",
                            "totalPower_",
                            "consumePower_",
                            "percentOfTotal_",
                            "drainType_",
                            "foregroundUsageTimeInMs_",
                            "backgroundUsageTimeInMs_",
                            "foregroundUsageConsumePower_",
                            "foregroundServiceUsageConsumePower_",
                            "backgroundUsageConsumePower_",
                            "cachedUsageConsumePower_",
                            "foregroundServiceUsageTimeInMs_"
                        });
            case 3:
                return new BatteryInformation();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (BatteryInformation.class) {
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

    public final String getAppLabel() {
        return this.appLabel_;
    }

    public final double getBackgroundUsageConsumePower() {
        return this.backgroundUsageConsumePower_;
    }

    public final long getBackgroundUsageTimeInMs() {
        return this.backgroundUsageTimeInMs_;
    }

    public final long getBootTimestamp() {
        return this.bootTimestamp_;
    }

    public final double getCachedUsageConsumePower() {
        return this.cachedUsageConsumePower_;
    }

    public final double getConsumePower() {
        return this.consumePower_;
    }

    public final DeviceBatteryState getDeviceBatteryState() {
        DeviceBatteryState deviceBatteryState = this.deviceBatteryState_;
        return deviceBatteryState == null
                ? DeviceBatteryState.getDefaultInstance()
                : deviceBatteryState;
    }

    public final int getDrainType() {
        return this.drainType_;
    }

    public final double getForegroundServiceUsageConsumePower() {
        return this.foregroundServiceUsageConsumePower_;
    }

    public final long getForegroundServiceUsageTimeInMs() {
        return this.foregroundServiceUsageTimeInMs_;
    }

    public final double getForegroundUsageConsumePower() {
        return this.foregroundUsageConsumePower_;
    }

    public final long getForegroundUsageTimeInMs() {
        return this.foregroundUsageTimeInMs_;
    }

    public final boolean getIsHidden() {
        return this.isHidden_;
    }

    public final double getPercentOfTotal() {
        return this.percentOfTotal_;
    }

    public final double getTotalPower() {
        return this.totalPower_;
    }

    public final String getZoneId() {
        return this.zoneId_;
    }
}
