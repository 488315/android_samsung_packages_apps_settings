package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.settings.R;

import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.internal.FooterButtonPartnerConfig;
import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.logging.internal.FooterBarMixinMetrics;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FooterBarMixin implements Mixin {
    final boolean applyDynamicColor;
    final boolean applyPartnerResources;
    public LinearLayout buttonContainer;
    public final Context context;
    int defaultPadding;
    public int footerBarPaddingBottom;
    int footerBarPaddingEnd;
    int footerBarPaddingStart;
    public int footerBarPaddingTop;
    public final int footerBarPrimaryBackgroundColor;
    public final int footerBarSecondaryBackgroundColor;
    final boolean footerButtonAlignEnd;
    public final ViewStub footerStub;
    public boolean isSecondaryButtonInPrimaryStyle = false;
    public final FooterBarMixinMetrics metrics;
    public FooterButton primaryButton;
    public int primaryButtonId;
    public FooterButtonPartnerConfig primaryButtonPartnerConfigForTesting;
    public FooterButton secondaryButton;
    public int secondaryButtonId;
    public FooterButtonPartnerConfig secondaryButtonPartnerConfigForTesting;
    final boolean useFullDynamicColor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.setupcompat.template.FooterBarMixin$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ int val$id;

        public AnonymousClass1(int i) {
            this.val$id = i;
        }
    }

    public FooterBarMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        XmlResourceParser xml;
        FooterBarMixinMetrics footerBarMixinMetrics = new FooterBarMixinMetrics();
        footerBarMixinMetrics.primaryButtonVisibility = "Unknown";
        footerBarMixinMetrics.secondaryButtonVisibility = "Unknown";
        this.metrics = footerBarMixinMetrics;
        Context context = templateLayout.getContext();
        this.context = context;
        this.footerStub = (ViewStub) templateLayout.findManagedViewById(R.id.suc_layout_footer);
        FooterButtonStyleUtils.defaultTextColor.clear();
        boolean z = templateLayout instanceof PartnerCustomizationLayout;
        this.applyPartnerResources =
                z && ((PartnerCustomizationLayout) templateLayout).shouldApplyPartnerResource();
        this.applyDynamicColor =
                z && ((PartnerCustomizationLayout) templateLayout).shouldApplyDynamicColor();
        this.useFullDynamicColor =
                z && ((PartnerCustomizationLayout) templateLayout).useFullDynamicColor();
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.SucFooterBarMixin, i, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(12, 0);
        this.defaultPadding = dimensionPixelSize;
        this.footerBarPaddingTop =
                obtainStyledAttributes.getDimensionPixelSize(11, dimensionPixelSize);
        this.footerBarPaddingBottom =
                obtainStyledAttributes.getDimensionPixelSize(8, this.defaultPadding);
        this.footerBarPaddingStart = obtainStyledAttributes.getDimensionPixelSize(10, 0);
        this.footerBarPaddingEnd = obtainStyledAttributes.getDimensionPixelSize(9, 0);
        this.footerBarPrimaryBackgroundColor = obtainStyledAttributes.getColor(13, 0);
        this.footerBarSecondaryBackgroundColor = obtainStyledAttributes.getColor(15, 0);
        this.footerButtonAlignEnd = obtainStyledAttributes.getBoolean(0, false);
        int resourceId = obtainStyledAttributes.getResourceId(14, 0);
        int resourceId2 = obtainStyledAttributes.getResourceId(16, 0);
        obtainStyledAttributes.recycle();
        FooterButtonInflater footerButtonInflater = new FooterButtonInflater(context);
        if (resourceId2 != 0) {
            xml = context.getResources().getXml(resourceId2);
            try {
                FooterButton inflate = footerButtonInflater.inflate(xml);
                xml.close();
                setSecondaryButton(inflate, false);
                footerBarMixinMetrics.primaryButtonVisibility =
                        footerBarMixinMetrics.primaryButtonVisibility.equals("Unknown")
                                ? "VisibleUsingXml"
                                : footerBarMixinMetrics.primaryButtonVisibility;
            } finally {
            }
        }
        if (resourceId != 0) {
            xml = context.getResources().getXml(resourceId);
            try {
                FooterButton inflate2 = footerButtonInflater.inflate(xml);
                xml.close();
                setPrimaryButton(inflate2);
                footerBarMixinMetrics.secondaryButtonVisibility =
                        footerBarMixinMetrics.secondaryButtonVisibility.equals("Unknown")
                                ? "VisibleUsingXml"
                                : footerBarMixinMetrics.secondaryButtonVisibility;
            } finally {
            }
        }
    }

    public final void addSpace() {
        LinearLayout ensureFooterInflated = ensureFooterInflated();
        View view = new View(this.context);
        view.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1.0f));
        view.setVisibility(4);
        ensureFooterInflated.addView(view);
    }

    public final void autoSetButtonBarVisibility() {
        Button primaryButtonView = getPrimaryButtonView();
        Button secondaryButtonView = getSecondaryButtonView();
        int i = 0;
        boolean z = primaryButtonView != null && primaryButtonView.getVisibility() == 0;
        boolean z2 = secondaryButtonView != null && secondaryButtonView.getVisibility() == 0;
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout != null) {
            if (!z && !z2) {
                i = 8;
            }
            linearLayout.setVisibility(i);
        }
    }

    public final LinearLayout ensureFooterInflated() {
        int dimension;
        if (this.buttonContainer == null) {
            if (this.footerStub == null) {
                throw new IllegalStateException("Footer stub is not found in this template");
            }
            this.footerStub.setLayoutInflater(
                    LayoutInflater.from(new ContextThemeWrapper(this.context, 2132083743)));
            this.footerStub.setLayoutResource(R.layout.suc_footer_button_bar);
            LinearLayout linearLayout = (LinearLayout) this.footerStub.inflate();
            this.buttonContainer = linearLayout;
            if (linearLayout != null) {
                linearLayout.setId(View.generateViewId());
                linearLayout.setPadding(
                        this.footerBarPaddingStart,
                        this.footerBarPaddingTop,
                        this.footerBarPaddingEnd,
                        this.footerBarPaddingBottom);
                if (isFooterButtonAlignedEnd()) {
                    linearLayout.setGravity(8388629);
                }
            }
            LinearLayout linearLayout2 = this.buttonContainer;
            if (linearLayout2 != null && this.applyPartnerResources) {
                if (!this.useFullDynamicColor) {
                    linearLayout2.setBackgroundColor(
                            PartnerConfigHelper.get(this.context)
                                    .getColor(
                                            this.context,
                                            PartnerConfig.CONFIG_FOOTER_BAR_BG_COLOR));
                }
                PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(this.context);
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_PADDING_TOP;
                if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                    this.footerBarPaddingTop =
                            (int)
                                    PartnerConfigHelper.get(this.context)
                                            .getDimension(this.context, partnerConfig, 0.0f);
                }
                PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(this.context);
                PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_FOOTER_BUTTON_PADDING_BOTTOM;
                if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                    this.footerBarPaddingBottom =
                            (int)
                                    PartnerConfigHelper.get(this.context)
                                            .getDimension(this.context, partnerConfig2, 0.0f);
                }
                PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(this.context);
                PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_FOOTER_BAR_PADDING_START;
                if (partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig3)) {
                    this.footerBarPaddingStart =
                            (int)
                                    PartnerConfigHelper.get(this.context)
                                            .getDimension(this.context, partnerConfig3, 0.0f);
                }
                PartnerConfigHelper partnerConfigHelper4 = PartnerConfigHelper.get(this.context);
                PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_FOOTER_BAR_PADDING_END;
                if (partnerConfigHelper4.isPartnerConfigAvailable(partnerConfig4)) {
                    this.footerBarPaddingEnd =
                            (int)
                                    PartnerConfigHelper.get(this.context)
                                            .getDimension(this.context, partnerConfig4, 0.0f);
                }
                linearLayout2.setPadding(
                        this.footerBarPaddingStart,
                        this.footerBarPaddingTop,
                        this.footerBarPaddingEnd,
                        this.footerBarPaddingBottom);
                PartnerConfigHelper partnerConfigHelper5 = PartnerConfigHelper.get(this.context);
                PartnerConfig partnerConfig5 = PartnerConfig.CONFIG_FOOTER_BAR_MIN_HEIGHT;
                if (partnerConfigHelper5.isPartnerConfigAvailable(partnerConfig5)
                        && (dimension =
                                        (int)
                                                PartnerConfigHelper.get(this.context)
                                                        .getDimension(
                                                                this.context, partnerConfig5, 0.0f))
                                > 0) {
                    linearLayout2.setMinimumHeight(dimension);
                }
            }
        }
        return this.buttonContainer;
    }

    public LinearLayout getButtonContainer() {
        return this.buttonContainer;
    }

    public int getPaddingBottom() {
        LinearLayout linearLayout = this.buttonContainer;
        return linearLayout != null
                ? linearLayout.getPaddingBottom()
                : this.footerStub.getPaddingBottom();
    }

    public int getPaddingTop() {
        LinearLayout linearLayout = this.buttonContainer;
        return linearLayout != null
                ? linearLayout.getPaddingTop()
                : this.footerStub.getPaddingTop();
    }

    public final int getPartnerTheme(
            FooterButton footerButton, int i, PartnerConfig partnerConfig) {
        int i2 = footerButton.theme;
        if (i2 != 0 && !this.applyPartnerResources) {
            i = i2;
        }
        return this.applyPartnerResources
                ? PartnerConfigHelper.get(this.context).getColor(this.context, partnerConfig) == 0
                        ? 2132083741
                        : 2132083740
                : i;
    }

    public final Button getPrimaryButtonView() {
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout == null) {
            return null;
        }
        return (Button) linearLayout.findViewById(this.primaryButtonId);
    }

    public final Button getSecondaryButtonView() {
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout == null) {
            return null;
        }
        return (Button) linearLayout.findViewById(this.secondaryButtonId);
    }

    public int getVisibility() {
        return this.buttonContainer.getVisibility();
    }

    public final FooterActionButton inflateButton(
            FooterButton footerButton, FooterButtonPartnerConfig footerButtonPartnerConfig) {
        FooterActionButton footerActionButton =
                (FooterActionButton)
                        LayoutInflater.from(
                                        new ContextThemeWrapper(
                                                this.context,
                                                footerButtonPartnerConfig.partnerTheme))
                                .inflate(R.layout.suc_button, (ViewGroup) null, false);
        footerActionButton.setId(View.generateViewId());
        footerActionButton.setText(footerButton.text);
        footerActionButton.setOnClickListener(footerButton);
        footerActionButton.setVisibility(footerButton.visibility);
        footerActionButton.setEnabled(footerButton.enabled);
        footerActionButton.footerButton = footerButton;
        footerButton.buttonListener = new AnonymousClass1(footerActionButton.getId());
        return footerActionButton;
    }

    public final boolean isFooterButtonAlignedEnd() {
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(this.context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ALIGNED_END;
        return partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)
                ? PartnerConfigHelper.get(this.context)
                        .getBoolean(this.context, partnerConfig, false)
                : this.footerButtonAlignEnd;
    }

    public boolean isPrimaryButtonVisible() {
        return getPrimaryButtonView() != null && getPrimaryButtonView().getVisibility() == 0;
    }

    public boolean isSecondaryButtonVisible() {
        return getSecondaryButtonView() != null && getSecondaryButtonView().getVisibility() == 0;
    }

    public final void onFooterButtonApplyPartnerResource(
            FooterActionButton footerActionButton,
            FooterButtonPartnerConfig footerButtonPartnerConfig) {
        if (this.applyPartnerResources) {
            FooterButtonStyleUtils.applyButtonPartnerResources(
                    this.context,
                    footerActionButton,
                    this.applyDynamicColor,
                    footerActionButton.getId() == this.primaryButtonId,
                    footerButtonPartnerConfig);
            if (this.applyDynamicColor) {
                return;
            }
            if (!footerActionButton.isEnabled()) {
                FooterButtonStyleUtils.updateButtonTextDisabledColorWithPartnerConfig(
                        this.context,
                        footerActionButton,
                        footerButtonPartnerConfig.buttonDisableTextColorConfig);
                return;
            }
            Context context = this.context;
            int color =
                    PartnerConfigHelper.get(context)
                            .getColor(context, footerButtonPartnerConfig.buttonTextColorConfig);
            if (color != 0) {
                footerActionButton.setTextColor(ColorStateList.valueOf(color));
            }
        }
    }

    public final void onFooterButtonInflated(FooterActionButton footerActionButton, int i) {
        if (!this.applyDynamicColor && i != 0) {
            HashMap hashMap = FooterButtonStyleUtils.defaultTextColor;
            footerActionButton.getBackground().mutate().setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        }
        this.buttonContainer.addView(footerActionButton);
        autoSetButtonBarVisibility();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void repopulateButtons() {
        /*
            r9 = this;
            android.widget.LinearLayout r0 = r9.ensureFooterInflated()
            android.widget.Button r1 = r9.getPrimaryButtonView()
            android.widget.Button r2 = r9.getSecondaryButtonView()
            r0.removeAllViews()
            boolean r3 = r9.isSecondaryButtonInPrimaryStyle
            r4 = 0
            if (r3 != 0) goto L16
        L14:
            r3 = r4
            goto L48
        L16:
            android.content.Context r3 = r9.context
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r3)
            android.content.Context r3 = r9.context
            android.os.Bundle r5 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.applyNeutralButtonStyleBundle
            java.lang.String r6 = "isNeutralButtonStyleEnabled"
            if (r5 != 0) goto L3d
            r5 = 0
            android.content.ContentResolver r7 = r3.getContentResolver()     // Catch: java.lang.Throwable -> L33
            android.net.Uri r3 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.getContentUri(r3)     // Catch: java.lang.Throwable -> L33
            android.os.Bundle r3 = r7.call(r3, r6, r5, r5)     // Catch: java.lang.Throwable -> L33
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.applyNeutralButtonStyleBundle = r3     // Catch: java.lang.Throwable -> L33
            goto L3d
        L33:
            java.lang.String r3 = "PartnerConfigHelper"
            java.lang.String r6 = "Neutral button style supporting status unknown; return as false."
            android.util.Log.w(r3, r6)
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.applyNeutralButtonStyleBundle = r5
            goto L14
        L3d:
            android.os.Bundle r3 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.applyNeutralButtonStyleBundle
            if (r3 == 0) goto L14
            boolean r3 = r3.getBoolean(r6, r4)
            if (r3 == 0) goto L14
            r3 = 1
        L48:
            android.content.Context r5 = r9.context
            android.content.res.Resources r5 = r5.getResources()
            android.content.res.Configuration r5 = r5.getConfiguration()
            int r5 = r5.orientation
            r6 = 2
            if (r5 != r6) goto L62
            if (r3 == 0) goto L62
            boolean r5 = r9.isFooterButtonAlignedEnd()
            if (r5 == 0) goto L62
            r9.addSpace()
        L62:
            if (r2 == 0) goto L7e
            boolean r5 = r9.isSecondaryButtonInPrimaryStyle
            if (r5 == 0) goto L7b
            int r5 = r0.getPaddingRight()
            int r6 = r0.getPaddingTop()
            int r7 = r0.getPaddingRight()
            int r8 = r0.getPaddingBottom()
            r0.setPadding(r5, r6, r7, r8)
        L7b:
            r0.addView(r2)
        L7e:
            boolean r5 = r9.isFooterButtonAlignedEnd()
            if (r5 != 0) goto L87
            r9.addSpace()
        L87:
            if (r1 == 0) goto L8c
            r0.addView(r1)
        L8c:
            if (r1 == 0) goto Lb1
            if (r2 == 0) goto Lb1
            if (r3 == 0) goto Lb1
            r1.measure(r4, r4)
            int r9 = r1.getMeasuredWidth()
            r2.measure(r4, r4)
            int r0 = r2.getMeasuredWidth()
            int r9 = java.lang.Math.max(r9, r0)
            android.view.ViewGroup$LayoutParams r0 = r1.getLayoutParams()
            r0.width = r9
            android.view.ViewGroup$LayoutParams r0 = r2.getLayoutParams()
            r0.width = r9
            goto Ld5
        Lb1:
            r9 = 0
            r0 = -2
            if (r1 == 0) goto Lc4
            android.view.ViewGroup$LayoutParams r3 = r1.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r3 = (android.widget.LinearLayout.LayoutParams) r3
            if (r3 == 0) goto Lc4
            r3.width = r0
            r3.weight = r9
            r1.setLayoutParams(r3)
        Lc4:
            if (r2 == 0) goto Ld5
            android.view.ViewGroup$LayoutParams r1 = r2.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r1 = (android.widget.LinearLayout.LayoutParams) r1
            if (r1 == 0) goto Ld5
            r1.width = r0
            r1.weight = r9
            r2.setLayoutParams(r1)
        Ld5:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.setupcompat.template.FooterBarMixin.repopulateButtons():void");
    }

    public final void setPrimaryButton(FooterButton footerButton) {
        PartnerConfig partnerConfig;
        Preconditions.ensureOnMainThread("setPrimaryButton");
        ensureFooterInflated();
        PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_BG_COLOR;
        int partnerTheme = getPartnerTheme(footerButton, 2132083740, partnerConfig2);
        PartnerConfig partnerConfig3 =
                PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_DISABLED_TEXT_COLOR;
        switch (footerButton.buttonType) {
            case 1:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_ADD_ANOTHER;
                break;
            case 2:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CANCEL;
                break;
            case 3:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CLEAR;
                break;
            case 4:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_DONE;
                break;
            case 5:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_NEXT;
                break;
            case 6:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_OPT_IN;
                break;
            case 7:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_SKIP;
                break;
            case 8:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_STOP;
                break;
            default:
                partnerConfig = null;
                break;
        }
        FooterButtonPartnerConfig footerButtonPartnerConfig =
                new FooterButtonPartnerConfig(
                        partnerTheme,
                        partnerConfig2,
                        partnerConfig3,
                        partnerConfig,
                        PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_TEXT_COLOR,
                        PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_MARGIN_START);
        FooterActionButton inflateButton = inflateButton(footerButton, footerButtonPartnerConfig);
        this.primaryButtonId = inflateButton.getId();
        inflateButton.isPrimaryButtonStyle = true;
        this.primaryButton = footerButton;
        this.primaryButtonPartnerConfigForTesting = footerButtonPartnerConfig;
        onFooterButtonInflated(inflateButton, this.footerBarPrimaryBackgroundColor);
        onFooterButtonApplyPartnerResource(inflateButton, footerButtonPartnerConfig);
        repopulateButtons();
    }

    public final void setSecondaryButton(FooterButton footerButton, boolean z) {
        PartnerConfig partnerConfig;
        Preconditions.ensureOnMainThread("setSecondaryButton");
        this.isSecondaryButtonInPrimaryStyle = z;
        ensureFooterInflated();
        int i = z ? 2132083740 : 2132083741;
        PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_BG_COLOR;
        PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_BG_COLOR;
        int partnerTheme = getPartnerTheme(footerButton, i, z ? partnerConfig3 : partnerConfig2);
        PartnerConfig partnerConfig4 = z ? partnerConfig3 : partnerConfig2;
        PartnerConfig partnerConfig5 =
                z
                        ? PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_DISABLED_TEXT_COLOR
                        : PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_DISABLED_TEXT_COLOR;
        switch (footerButton.buttonType) {
            case 1:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_ADD_ANOTHER;
                break;
            case 2:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CANCEL;
                break;
            case 3:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CLEAR;
                break;
            case 4:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_DONE;
                break;
            case 5:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_NEXT;
                break;
            case 6:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_OPT_IN;
                break;
            case 7:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_SKIP;
                break;
            case 8:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_STOP;
                break;
            default:
                partnerConfig = null;
                break;
        }
        FooterButtonPartnerConfig footerButtonPartnerConfig =
                new FooterButtonPartnerConfig(
                        partnerTheme,
                        partnerConfig4,
                        partnerConfig5,
                        partnerConfig,
                        z
                                ? PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_TEXT_COLOR
                                : PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_TEXT_COLOR,
                        PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_MARGIN_START);
        FooterActionButton inflateButton = inflateButton(footerButton, footerButtonPartnerConfig);
        this.secondaryButtonId = inflateButton.getId();
        inflateButton.isPrimaryButtonStyle = z;
        this.secondaryButton = footerButton;
        this.secondaryButtonPartnerConfigForTesting = footerButtonPartnerConfig;
        onFooterButtonInflated(inflateButton, this.footerBarSecondaryBackgroundColor);
        onFooterButtonApplyPartnerResource(inflateButton, footerButtonPartnerConfig);
        repopulateButtons();
    }
}
