package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PowerAnomalyEvent extends GeneratedMessageLite {
    private static final PowerAnomalyEvent DEFAULT_INSTANCE;
    public static final int DISMISS_RECORD_KEY_FIELD_NUMBER = 8;
    public static final int EVENT_ID_FIELD_NUMBER = 1;
    public static final int KEY_FIELD_NUMBER = 4;
    private static volatile Parser PARSER = null;
    public static final int SCORE_FIELD_NUMBER = 5;
    public static final int TIMESTAMP_FIELD_NUMBER = 2;
    public static final int TYPE_FIELD_NUMBER = 3;
    public static final int WARNING_BANNER_INFO_FIELD_NUMBER = 6;
    public static final int WARNING_ITEM_INFO_FIELD_NUMBER = 7;
    private int bitField0_;
    private Object info_;
    private int key_;
    private float score_;
    private long timestamp_;
    private int type_;
    private int infoCase_ = 0;
    private String eventId_ = ApnSettings.MVNO_NONE;
    private String dismissRecordKey_ = ApnSettings.MVNO_NONE;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    static {
        PowerAnomalyEvent powerAnomalyEvent = new PowerAnomalyEvent();
        DEFAULT_INSTANCE = powerAnomalyEvent;
        GeneratedMessageLite.registerDefaultInstance(PowerAnomalyEvent.class, powerAnomalyEvent);
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
                        "\u0001\b\u0001\u0001\u0001\b\b\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဂ\u0001\u0003ဌ\u0002\u0004ဌ\u0003\u0005ခ\u0004\u0006ြ\u0000\u0007ြ\u0000\bဈ\u0007",
                        new Object[] {
                            "info_",
                            "infoCase_",
                            "bitField0_",
                            "eventId_",
                            "timestamp_",
                            "type_",
                            PowerAnomalyType.PowerAnomalyTypeVerifier.INSTANCE,
                            "key_",
                            PowerAnomalyKey.PowerAnomalyKeyVerifier.INSTANCE,
                            "score_",
                            WarningBannerInfo.class,
                            WarningItemInfo.class,
                            "dismissRecordKey_"
                        });
            case 3:
                return new PowerAnomalyEvent();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (PowerAnomalyEvent.class) {
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

    public final String getDismissRecordKey() {
        return this.dismissRecordKey_;
    }

    public final String getEventId() {
        return this.eventId_;
    }

    public final PowerAnomalyKey getKey() {
        PowerAnomalyKey forNumber = PowerAnomalyKey.forNumber(this.key_);
        return forNumber == null ? PowerAnomalyKey.KEY_BRIGHTNESS : forNumber;
    }

    public final float getScore() {
        return this.score_;
    }

    public final PowerAnomalyType getType() {
        int i = this.type_;
        PowerAnomalyType powerAnomalyType = PowerAnomalyType.TYPE_SETTINGS_BANNER;
        PowerAnomalyType powerAnomalyType2 =
                i != 0 ? i != 1 ? null : PowerAnomalyType.TYPE_APPS_ITEM : powerAnomalyType;
        return powerAnomalyType2 == null ? powerAnomalyType : powerAnomalyType2;
    }

    public final WarningBannerInfo getWarningBannerInfo() {
        return this.infoCase_ == 6
                ? (WarningBannerInfo) this.info_
                : WarningBannerInfo.getDefaultInstance();
    }

    public final WarningItemInfo getWarningItemInfo() {
        return this.infoCase_ == 7
                ? (WarningItemInfo) this.info_
                : WarningItemInfo.getDefaultInstance();
    }

    public final boolean hasWarningBannerInfo() {
        return this.infoCase_ == 6;
    }

    public final boolean hasWarningItemInfo() {
        return this.infoCase_ == 7;
    }
}
