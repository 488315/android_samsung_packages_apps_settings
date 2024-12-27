package com.google.android.setupcompat.template;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Window;

import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SystemNavBarMixin implements Mixin {
    final boolean applyPartnerResources;
    public final TemplateLayout templateLayout;
    final boolean useFullDynamicColor;
    public final Window windowOfActivity;

    public SystemNavBarMixin(TemplateLayout templateLayout, Window window) {
        this.templateLayout = templateLayout;
        this.windowOfActivity = window;
        boolean z = templateLayout instanceof PartnerCustomizationLayout;
        boolean z2 = false;
        this.applyPartnerResources =
                z && ((PartnerCustomizationLayout) templateLayout).shouldApplyPartnerResource();
        if (z && ((PartnerCustomizationLayout) templateLayout).useFullDynamicColor()) {
            z2 = true;
        }
        this.useFullDynamicColor = z2;
    }

    public final void applyPartnerCustomizations(AttributeSet attributeSet, int i) {
        TemplateLayout templateLayout = this.templateLayout;
        TypedArray obtainStyledAttributes =
                templateLayout
                        .getContext()
                        .obtainStyledAttributes(
                                attributeSet, R$styleable.SucSystemNavBarMixin, i, 0);
        boolean z = true;
        int color = obtainStyledAttributes.getColor(1, 0);
        if (this.windowOfActivity != null) {
            if (this.applyPartnerResources && !this.useFullDynamicColor) {
                Context context = templateLayout.getContext();
                color =
                        PartnerConfigHelper.get(context)
                                .getColor(context, PartnerConfig.CONFIG_NAVIGATION_BAR_BG_COLOR);
            }
            this.windowOfActivity.setNavigationBarColor(color);
        }
        Window window = this.windowOfActivity;
        if (window != null && (window.getDecorView().getSystemUiVisibility() & 16) != 16) {
            z = false;
        }
        boolean z2 = obtainStyledAttributes.getBoolean(0, z);
        if (this.windowOfActivity != null) {
            if (this.applyPartnerResources) {
                Context context2 = templateLayout.getContext();
                z2 =
                        PartnerConfigHelper.get(context2)
                                .getBoolean(
                                        context2, PartnerConfig.CONFIG_LIGHT_NAVIGATION_BAR, false);
            }
            if (z2) {
                this.windowOfActivity
                        .getDecorView()
                        .setSystemUiVisibility(
                                this.windowOfActivity.getDecorView().getSystemUiVisibility() | 16);
            } else {
                this.windowOfActivity
                        .getDecorView()
                        .setSystemUiVisibility(
                                this.windowOfActivity.getDecorView().getSystemUiVisibility()
                                        & (-17));
            }
        }
        TypedArray obtainStyledAttributes2 =
                templateLayout
                        .getContext()
                        .obtainStyledAttributes(new int[] {R.attr.navigationBarDividerColor});
        int color2 = obtainStyledAttributes.getColor(2, obtainStyledAttributes2.getColor(0, 0));
        if (this.windowOfActivity != null) {
            if (this.applyPartnerResources) {
                Context context3 = templateLayout.getContext();
                PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context3);
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_NAVIGATION_BAR_DIVIDER_COLOR;
                if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                    color2 = PartnerConfigHelper.get(context3).getColor(context3, partnerConfig);
                }
            }
            this.windowOfActivity.setNavigationBarDividerColor(color2);
        }
        obtainStyledAttributes2.recycle();
        obtainStyledAttributes.recycle();
    }
}
