package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WarningItemInfo extends GeneratedMessageLite {
    public static final int ANOMALY_HINT_PREF_KEY_FIELD_NUMBER = 10;
    public static final int CANCEL_BUTTON_STRING_FIELD_NUMBER = 7;
    private static final WarningItemInfo DEFAULT_INSTANCE;
    public static final int DESCRIPTION_STRING_FIELD_NUMBER = 5;
    public static final int END_TIMESTAMP_FIELD_NUMBER = 2;
    public static final int ITEM_KEY_FIELD_NUMBER = 8;
    public static final int MAIN_BUTTON_STRING_FIELD_NUMBER = 6;
    private static volatile Parser PARSER = null;
    public static final int START_TIMESTAMP_FIELD_NUMBER = 1;
    public static final int TITLE_STRING_FIELD_NUMBER = 4;
    public static final int TOP_CARD_STRING_FIELD_NUMBER = 3;
    public static final int WARNING_INFO_STRING_FIELD_NUMBER = 9;
    private int bitField0_;
    private long endTimestamp_;
    private long startTimestamp_;
    private String topCardString_ = ApnSettings.MVNO_NONE;
    private String titleString_ = ApnSettings.MVNO_NONE;
    private String descriptionString_ = ApnSettings.MVNO_NONE;
    private String mainButtonString_ = ApnSettings.MVNO_NONE;
    private String cancelButtonString_ = ApnSettings.MVNO_NONE;
    private String itemKey_ = ApnSettings.MVNO_NONE;
    private String warningInfoString_ = ApnSettings.MVNO_NONE;
    private String anomalyHintPrefKey_ = ApnSettings.MVNO_NONE;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    static {
        WarningItemInfo warningItemInfo = new WarningItemInfo();
        DEFAULT_INSTANCE = warningItemInfo;
        GeneratedMessageLite.registerDefaultInstance(WarningItemInfo.class, warningItemInfo);
    }

    public static WarningItemInfo getDefaultInstance() {
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
                        "\u0001\n"
                            + "\u0000\u0001\u0001\n\n"
                            + "\u0000\u0000\u0000\u0001ဂ\u0000\u0002ဂ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\bဈ\u0007"
                            + "\tဈ\b\n"
                            + "ဈ\t",
                        new Object[] {
                            "bitField0_",
                            "startTimestamp_",
                            "endTimestamp_",
                            "topCardString_",
                            "titleString_",
                            "descriptionString_",
                            "mainButtonString_",
                            "cancelButtonString_",
                            "itemKey_",
                            "warningInfoString_",
                            "anomalyHintPrefKey_"
                        });
            case 3:
                return new WarningItemInfo();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (WarningItemInfo.class) {
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

    public final String getAnomalyHintPrefKey() {
        return this.anomalyHintPrefKey_;
    }

    public final String getCancelButtonString() {
        return this.cancelButtonString_;
    }

    public final long getEndTimestamp() {
        return this.endTimestamp_;
    }

    public final String getItemKey() {
        return this.itemKey_;
    }

    public final String getMainButtonString() {
        return this.mainButtonString_;
    }

    public final long getStartTimestamp() {
        return this.startTimestamp_;
    }

    public final String getTitleString() {
        return this.titleString_;
    }

    public final String getWarningInfoString() {
        return this.warningInfoString_;
    }

    public final boolean hasEndTimestamp() {
        return (this.bitField0_ & 2) != 0;
    }

    public final boolean hasItemKey() {
        return (this.bitField0_ & 128) != 0;
    }

    public final boolean hasStartTimestamp() {
        return (this.bitField0_ & 1) != 0;
    }
}
