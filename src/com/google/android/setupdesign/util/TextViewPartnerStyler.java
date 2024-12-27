package com.google.android.setupdesign.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;

import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.view.RichTextView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class TextViewPartnerStyler {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TextPartnerConfigs {
        public final PartnerConfig textColorConfig;
        public final PartnerConfig textFontFamilyConfig;
        public final PartnerConfig textFontWeightConfig;
        public final int textGravity;
        public final PartnerConfig textLinkFontFamilyConfig;
        public final PartnerConfig textLinkedColorConfig;
        public final PartnerConfig textMarginBottomConfig;
        public final PartnerConfig textMarginTopConfig;
        public final PartnerConfig textSizeConfig;

        public TextPartnerConfigs(
                PartnerConfig partnerConfig,
                PartnerConfig partnerConfig2,
                PartnerConfig partnerConfig3,
                PartnerConfig partnerConfig4,
                PartnerConfig partnerConfig5,
                PartnerConfig partnerConfig6,
                PartnerConfig partnerConfig7,
                PartnerConfig partnerConfig8,
                int i) {
            this.textColorConfig = partnerConfig;
            this.textLinkedColorConfig = partnerConfig2;
            this.textSizeConfig = partnerConfig3;
            this.textFontFamilyConfig = partnerConfig4;
            this.textFontWeightConfig = partnerConfig5;
            this.textLinkFontFamilyConfig = partnerConfig6;
            this.textMarginTopConfig = partnerConfig7;
            this.textMarginBottomConfig = partnerConfig8;
            this.textGravity = i;
        }
    }

    public static void applyPartnerCustomizationStyle(
            TextView textView, TextPartnerConfigs textPartnerConfigs) {
        PartnerConfig partnerConfig;
        Typeface create;
        PartnerConfig partnerConfig2;
        boolean z;
        int color;
        TemplateLayout findLayoutFromActivity;
        int color2;
        Context context = textView.getContext();
        PartnerConfig partnerConfig3 = textPartnerConfigs.textColorConfig;
        if (partnerConfig3 != null
                && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig3)
                && (color2 = PartnerConfigHelper.get(context).getColor(context, partnerConfig3))
                        != 0) {
            textView.setTextColor(color2);
        }
        PartnerConfig partnerConfig4 = textPartnerConfigs.textLinkedColorConfig;
        if (partnerConfig4 != null
                && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig4)) {
            Context context2 = textView.getContext();
            try {
                Logger logger = PartnerCustomizationLayout.LOG;
                findLayoutFromActivity =
                        PartnerStyleHelper.findLayoutFromActivity(
                                PartnerConfigHelper.lookupActivityFromContext(context2));
            } catch (ClassCastException | IllegalArgumentException unused) {
            }
            if (findLayoutFromActivity instanceof GlifLayout) {
                z = ((GlifLayout) findLayoutFromActivity).shouldApplyDynamicColor();
                if (!z
                        && (color =
                                        PartnerConfigHelper.get(context)
                                                .getColor(context, partnerConfig4))
                                != 0) {
                    textView.setLinkTextColor(color);
                }
            }
            TypedArray obtainStyledAttributes =
                    context2.obtainStyledAttributes(new int[] {R.attr.sucFullDynamicColor});
            boolean hasValue = obtainStyledAttributes.hasValue(0);
            obtainStyledAttributes.recycle();
            z = hasValue;
            if (!z) {
                textView.setLinkTextColor(color);
            }
        }
        PartnerConfig partnerConfig5 = textPartnerConfigs.textSizeConfig;
        if (partnerConfig5 != null
                && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig5)) {
            float dimension =
                    PartnerConfigHelper.get(context).getDimension(context, partnerConfig5, 0.0f);
            if (dimension > 0.0f) {
                textView.setTextSize(0, dimension);
            }
        }
        PartnerConfig partnerConfig6 = textPartnerConfigs.textFontFamilyConfig;
        Typeface create2 =
                (partnerConfig6 == null
                                || !PartnerConfigHelper.get(context)
                                        .isPartnerConfigAvailable(partnerConfig6))
                        ? null
                        : Typeface.create(
                                PartnerConfigHelper.get(context).getString(context, partnerConfig6),
                                0);
        if (PartnerConfigHelper.isFontWeightEnabled(context)
                && (partnerConfig2 = textPartnerConfigs.textFontWeightConfig) != null
                && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig2)) {
            int integer = PartnerConfigHelper.get(context).getInteger(context, partnerConfig2, 400);
            if (create2 == null) {
                create2 = textView.getTypeface();
            }
            create2 = Typeface.create(create2, integer, false);
        }
        if (create2 != null) {
            textView.setTypeface(create2);
        }
        if ((textView instanceof RichTextView)
                && (partnerConfig = textPartnerConfigs.textLinkFontFamilyConfig) != null
                && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig)
                && (create =
                                Typeface.create(
                                        PartnerConfigHelper.get(context)
                                                .getString(context, partnerConfig),
                                        0))
                        != null) {
            RichTextView.setSpanTypeface(create);
        }
        applyPartnerCustomizationVerticalMargins(textView, textPartnerConfigs);
        textView.setGravity(textPartnerConfigs.textGravity);
    }

    public static void applyPartnerCustomizationVerticalMargins(
            TextView textView, TextPartnerConfigs textPartnerConfigs) {
        PartnerConfig partnerConfig = textPartnerConfigs.textMarginTopConfig;
        PartnerConfig partnerConfig2 = textPartnerConfigs.textMarginBottomConfig;
        if (partnerConfig == null && partnerConfig2 == null) {
            return;
        }
        Context context = textView.getContext();
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
            layoutParams2.setMargins(
                    layoutParams2.leftMargin,
                    (partnerConfig == null
                                    || !PartnerConfigHelper.get(context)
                                            .isPartnerConfigAvailable(partnerConfig))
                            ? layoutParams2.topMargin
                            : (int)
                                    PartnerConfigHelper.get(context)
                                            .getDimension(context, partnerConfig, 0.0f),
                    layoutParams2.rightMargin,
                    (partnerConfig2 == null
                                    || !PartnerConfigHelper.get(context)
                                            .isPartnerConfigAvailable(partnerConfig2))
                            ? layoutParams2.bottomMargin
                            : (int)
                                    PartnerConfigHelper.get(context)
                                            .getDimension(context, partnerConfig2, 0.0f));
            textView.setLayoutParams(layoutParams);
        }
    }
}
