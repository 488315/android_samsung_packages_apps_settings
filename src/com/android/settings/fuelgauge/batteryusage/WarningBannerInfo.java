package com.android.settings.fuelgauge.batteryusage;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WarningBannerInfo extends GeneratedMessageLite {
    public static final int CANCEL_BUTTON_STRING_FIELD_NUMBER = 7;
    private static final WarningBannerInfo DEFAULT_INSTANCE;
    public static final int DESCRIPTION_STRING_FIELD_NUMBER = 2;
    public static final int MAIN_BUTTON_CONFIG_SETTINGS_NAME_FIELD_NUMBER = 8;
    public static final int MAIN_BUTTON_CONFIG_SETTINGS_VALUE_FIELD_NUMBER = 9;
    public static final int MAIN_BUTTON_DESTINATION_FIELD_NUMBER = 4;
    public static final int MAIN_BUTTON_SOURCE_HIGHLIGHT_KEY_FIELD_NUMBER = 6;
    public static final int MAIN_BUTTON_SOURCE_METRICS_CATEGORY_FIELD_NUMBER = 5;
    public static final int MAIN_BUTTON_STRING_FIELD_NUMBER = 3;
    private static volatile Parser PARSER = null;
    public static final int TITLE_STRING_FIELD_NUMBER = 1;
    private int bitField0_;
    private int mainButtonConfigSettingsValue_;
    private int mainButtonSourceMetricsCategory_;
    private String titleString_ = ApnSettings.MVNO_NONE;
    private String descriptionString_ = ApnSettings.MVNO_NONE;
    private String mainButtonString_ = ApnSettings.MVNO_NONE;
    private String mainButtonDestination_ = ApnSettings.MVNO_NONE;
    private String mainButtonSourceHighlightKey_ = ApnSettings.MVNO_NONE;
    private String cancelButtonString_ = ApnSettings.MVNO_NONE;
    private String mainButtonConfigSettingsName_ = ApnSettings.MVNO_NONE;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {}

    static {
        WarningBannerInfo warningBannerInfo = new WarningBannerInfo();
        DEFAULT_INSTANCE = warningBannerInfo;
        GeneratedMessageLite.registerDefaultInstance(WarningBannerInfo.class, warningBannerInfo);
    }

    public static WarningBannerInfo getDefaultInstance() {
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
                        "\u0001\t\u0000\u0001\u0001\t"
                            + "\t\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005င\u0004\u0006ဈ\u0005\u0007ဈ\u0006\bဈ\u0007"
                            + "\tင\b",
                        new Object[] {
                            "bitField0_",
                            "titleString_",
                            "descriptionString_",
                            "mainButtonString_",
                            "mainButtonDestination_",
                            "mainButtonSourceMetricsCategory_",
                            "mainButtonSourceHighlightKey_",
                            "cancelButtonString_",
                            "mainButtonConfigSettingsName_",
                            "mainButtonConfigSettingsValue_"
                        });
            case 3:
                return new WarningBannerInfo();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (WarningBannerInfo.class) {
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

    public final String getCancelButtonString() {
        return this.cancelButtonString_;
    }

    public final String getMainButtonConfigSettingsName() {
        return this.mainButtonConfigSettingsName_;
    }

    public final int getMainButtonConfigSettingsValue() {
        return this.mainButtonConfigSettingsValue_;
    }

    public final String getMainButtonDestination() {
        return this.mainButtonDestination_;
    }

    public final String getMainButtonSourceHighlightKey() {
        return this.mainButtonSourceHighlightKey_;
    }

    public final int getMainButtonSourceMetricsCategory() {
        return this.mainButtonSourceMetricsCategory_;
    }

    public final String getMainButtonString() {
        return this.mainButtonString_;
    }

    public final String getTitleString() {
        return this.titleString_;
    }
}
