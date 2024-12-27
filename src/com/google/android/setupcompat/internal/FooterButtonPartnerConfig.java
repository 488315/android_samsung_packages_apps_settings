package com.google.android.setupcompat.internal;

import com.google.android.setupcompat.partnerconfig.PartnerConfig;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FooterButtonPartnerConfig {
    public final PartnerConfig buttonBackgroundConfig;
    public final PartnerConfig buttonDisableAlphaConfig;
    public final PartnerConfig buttonDisableBackgroundConfig;
    public final PartnerConfig buttonDisableTextColorConfig;
    public final PartnerConfig buttonIconConfig;
    public final PartnerConfig buttonMarginStartConfig;
    public final PartnerConfig buttonMinHeightConfig;
    public final PartnerConfig buttonRadiusConfig;
    public final PartnerConfig buttonRippleColorAlphaConfig;
    public final PartnerConfig buttonTextColorConfig;
    public final PartnerConfig buttonTextSizeConfig;
    public final PartnerConfig buttonTextStyleConfig;
    public final PartnerConfig buttonTextTypeFaceConfig;
    public final PartnerConfig buttonTextWeightConfig;
    public final int partnerTheme;

    public FooterButtonPartnerConfig(
            int i,
            PartnerConfig partnerConfig,
            PartnerConfig partnerConfig2,
            PartnerConfig partnerConfig3,
            PartnerConfig partnerConfig4,
            PartnerConfig partnerConfig5) {
        PartnerConfig partnerConfig6 = PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_ALPHA;
        PartnerConfig partnerConfig7 = PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_BG_COLOR;
        PartnerConfig partnerConfig8 = PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_SIZE;
        PartnerConfig partnerConfig9 = PartnerConfig.CONFIG_FOOTER_BUTTON_MIN_HEIGHT;
        PartnerConfig partnerConfig10 = PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_FAMILY;
        PartnerConfig partnerConfig11 = PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_WEIGHT;
        PartnerConfig partnerConfig12 = PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_STYLE;
        PartnerConfig partnerConfig13 = PartnerConfig.CONFIG_FOOTER_BUTTON_RADIUS;
        PartnerConfig partnerConfig14 = PartnerConfig.CONFIG_FOOTER_BUTTON_RIPPLE_COLOR_ALPHA;
        this.partnerTheme = i;
        this.buttonTextColorConfig = partnerConfig4;
        this.buttonMarginStartConfig = partnerConfig5;
        this.buttonTextSizeConfig = partnerConfig8;
        this.buttonMinHeightConfig = partnerConfig9;
        this.buttonTextTypeFaceConfig = partnerConfig10;
        this.buttonTextWeightConfig = partnerConfig11;
        this.buttonTextStyleConfig = partnerConfig12;
        this.buttonBackgroundConfig = partnerConfig;
        this.buttonDisableAlphaConfig = partnerConfig6;
        this.buttonDisableBackgroundConfig = partnerConfig7;
        this.buttonDisableTextColorConfig = partnerConfig2;
        this.buttonRadiusConfig = partnerConfig13;
        this.buttonIconConfig = partnerConfig3;
        this.buttonRippleColorAlphaConfig = partnerConfig14;
    }
}
