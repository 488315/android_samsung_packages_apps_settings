package com.google.android.setupdesign.util;

import android.content.Context;
import android.widget.Button;

import com.google.android.setupcompat.internal.FooterButtonPartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.template.FooterButtonStyleUtils;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ButtonStyler {
    public static void applyPartnerCustomizationPrimaryButtonStyle(Context context, Button button) {
        if (button == null || context == null) {
            return;
        }
        boolean shouldApplyDynamicColor = ThemeHelper.shouldApplyDynamicColor(context);
        HashMap hashMap = FooterButtonStyleUtils.defaultTextColor;
        FooterButtonStyleUtils.applyButtonPartnerResources(
                context,
                button,
                shouldApplyDynamicColor,
                true,
                new FooterButtonPartnerConfig(
                        2132083740,
                        PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_BG_COLOR,
                        PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_DISABLED_TEXT_COLOR,
                        null,
                        PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_TEXT_COLOR,
                        PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_MARGIN_START));
    }
}
