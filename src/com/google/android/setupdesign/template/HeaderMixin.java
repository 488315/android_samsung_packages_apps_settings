package com.google.android.setupdesign.template;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.android.settings.R;

import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.util.LayoutStyler;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import com.google.android.setupdesign.util.TextViewPartnerStyler;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class HeaderMixin implements Mixin {
    public int defaultLineHeight;
    public float defaultTextSize;
    public float headerAutoSizeLineExtraSpacingInPx;
    public int headerAutoSizeMaxLineOfMaxSize;
    public float headerAutoSizeMaxTextSizeInPx;
    public float headerAutoSizeMinTextSizeInPx;
    public final TemplateLayout templateLayout;
    boolean autoTextSizeEnabled = false;
    public final ArrayList titlePreDrawListeners = new ArrayList();

    public HeaderMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        TextView textView;
        this.defaultTextSize = 0.0f;
        this.defaultLineHeight = 0;
        this.templateLayout = templateLayout;
        TypedArray obtainStyledAttributes =
                templateLayout
                        .getContext()
                        .obtainStyledAttributes(attributeSet, R$styleable.SucHeaderMixin, i, 0);
        CharSequence text = obtainStyledAttributes.getText(4);
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(5);
        obtainStyledAttributes.recycle();
        if (getTextView() != null) {
            this.defaultTextSize = getTextView().getTextSize();
            this.defaultLineHeight = getTextView().getLineHeight();
        }
        tryUpdateAutoTextSizeFlagWithPartnerConfig();
        if (text != null) {
            setText(text);
        }
        if (colorStateList == null || (textView = getTextView()) == null) {
            return;
        }
        textView.setTextColor(colorStateList);
    }

    public final void autoAdjustTextSize(final TextView textView) {
        if (textView == null) {
            return;
        }
        textView.setTextSize(0, this.headerAutoSizeMaxTextSizeInPx);
        this.defaultTextSize = textView.getTextSize();
        textView.setLineHeight(
                Math.round(
                        this.headerAutoSizeLineExtraSpacingInPx
                                + this.headerAutoSizeMaxTextSizeInPx));
        this.defaultLineHeight = textView.getLineHeight();
        textView.setMaxLines(6);
        ViewTreeObserver.OnPreDrawListener onPreDrawListener =
                new ViewTreeObserver
                        .OnPreDrawListener() { // from class:
                                               // com.google.android.setupdesign.template.HeaderMixin.1
                    @Override // android.view.ViewTreeObserver.OnPreDrawListener
                    public final boolean onPreDraw() {
                        textView.getViewTreeObserver().removeOnPreDrawListener(this);
                        int lineCount = textView.getLineCount();
                        HeaderMixin headerMixin = HeaderMixin.this;
                        if (lineCount <= headerMixin.headerAutoSizeMaxLineOfMaxSize) {
                            return true;
                        }
                        textView.setTextSize(0, headerMixin.headerAutoSizeMinTextSizeInPx);
                        TextView textView2 = textView;
                        HeaderMixin headerMixin2 = HeaderMixin.this;
                        textView2.setLineHeight(
                                Math.round(
                                        headerMixin2.headerAutoSizeLineExtraSpacingInPx
                                                + headerMixin2.headerAutoSizeMinTextSizeInPx));
                        textView.invalidate();
                        return false;
                    }
                };
        textView.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
        this.titlePreDrawListeners.add(onPreDrawListener);
    }

    public final TextView getTextView() {
        return (TextView) this.templateLayout.findManagedViewById(R.id.suc_layout_title);
    }

    public final void setAutoTextSizeEnabled(boolean z) {
        this.autoTextSizeEnabled = z;
        if (z) {
            tryUpdateAutoTextConfigWithPartnerConfig();
            if (z) {
                autoAdjustTextSize(getTextView());
                return;
            }
            return;
        }
        TextView textView = getTextView();
        if (textView == null) {
            return;
        }
        textView.setTextSize(0, this.defaultTextSize);
        textView.setLineHeight(this.defaultLineHeight);
        Iterator it = this.titlePreDrawListeners.iterator();
        while (it.hasNext()) {
            textView.getViewTreeObserver()
                    .removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener) it.next());
        }
        this.titlePreDrawListeners.clear();
    }

    public final void setText(int i) {
        TextView textView = getTextView();
        if (textView != null) {
            if (this.autoTextSizeEnabled) {
                autoAdjustTextSize(textView);
            }
            textView.setText(i);
        }
    }

    public final void tryApplyPartnerCustomizationStyle() {
        TemplateLayout templateLayout = this.templateLayout;
        TextView textView = (TextView) templateLayout.findManagedViewById(R.id.suc_layout_title);
        if (PartnerStyleHelper.shouldApplyPartnerResource(templateLayout)) {
            View findManagedViewById = templateLayout.findManagedViewById(R.id.sud_layout_header);
            LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(findManagedViewById);
            if (textView != null) {
                TextViewPartnerStyler.applyPartnerCustomizationStyle(
                        textView,
                        new TextViewPartnerStyler.TextPartnerConfigs(
                                PartnerConfig.CONFIG_HEADER_TEXT_COLOR,
                                null,
                                PartnerConfig.CONFIG_HEADER_TEXT_SIZE,
                                PartnerConfig.CONFIG_HEADER_FONT_FAMILY,
                                PartnerConfig.CONFIG_HEADER_FONT_WEIGHT,
                                null,
                                PartnerConfig.CONFIG_HEADER_TEXT_MARGIN_TOP,
                                PartnerConfig.CONFIG_HEADER_TEXT_MARGIN_BOTTOM,
                                PartnerStyleHelper.getLayoutGravity(textView.getContext())));
            }
            ViewGroup viewGroup = (ViewGroup) findManagedViewById;
            if (viewGroup != null) {
                Context context = viewGroup.getContext();
                viewGroup.setBackgroundColor(
                        PartnerConfigHelper.get(context)
                                .getColor(
                                        context,
                                        PartnerConfig.CONFIG_HEADER_AREA_BACKGROUND_COLOR));
                PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_HEADER_CONTAINER_MARGIN_BOTTOM;
                if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                    ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                        ViewGroup.MarginLayoutParams marginLayoutParams =
                                (ViewGroup.MarginLayoutParams) layoutParams;
                        marginLayoutParams.setMargins(
                                marginLayoutParams.leftMargin,
                                marginLayoutParams.topMargin,
                                marginLayoutParams.rightMargin,
                                (int)
                                        PartnerConfigHelper.get(context)
                                                .getDimension(context, partnerConfig, 0.0f));
                        viewGroup.setLayoutParams(layoutParams);
                    }
                }
            }
        }
        tryUpdateAutoTextSizeFlagWithPartnerConfig();
        if (this.autoTextSizeEnabled) {
            autoAdjustTextSize(textView);
        }
    }

    public final void tryUpdateAutoTextConfigWithPartnerConfig() {
        Context context = this.templateLayout.getContext();
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_MAX_TEXT_SIZE;
        if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
            this.headerAutoSizeMaxTextSizeInPx =
                    PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f);
        }
        PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_MIN_TEXT_SIZE;
        if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
            this.headerAutoSizeMinTextSizeInPx =
                    PartnerConfigHelper.get(context).getDimension(context, partnerConfig2, 0.0f);
        }
        PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_LINE_SPACING_EXTRA;
        if (partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig3)) {
            this.headerAutoSizeLineExtraSpacingInPx =
                    PartnerConfigHelper.get(context).getDimension(context, partnerConfig3, 0.0f);
        }
        PartnerConfigHelper partnerConfigHelper4 = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_MAX_LINE_OF_MAX_SIZE;
        if (partnerConfigHelper4.isPartnerConfigAvailable(partnerConfig4)) {
            this.headerAutoSizeMaxLineOfMaxSize =
                    PartnerConfigHelper.get(context).getInteger(context, partnerConfig4, 0);
        }
        if (this.headerAutoSizeMaxLineOfMaxSize >= 1) {
            float f = this.headerAutoSizeMinTextSizeInPx;
            if (f > 0.0f && this.headerAutoSizeMaxTextSizeInPx >= f) {
                return;
            }
        }
        Log.w("HeaderMixin", "Invalid configs, disable auto text size.");
        this.autoTextSizeEnabled = false;
    }

    public final void tryUpdateAutoTextSizeFlagWithPartnerConfig() {
        TemplateLayout templateLayout = this.templateLayout;
        Context context = templateLayout.getContext();
        if (!PartnerStyleHelper.shouldApplyPartnerResource(templateLayout)) {
            this.autoTextSizeEnabled = false;
            return;
        }
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_ENABLED;
        if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
            this.autoTextSizeEnabled =
                    PartnerConfigHelper.get(context)
                            .getBoolean(context, partnerConfig, this.autoTextSizeEnabled);
        }
        if (this.autoTextSizeEnabled) {
            tryUpdateAutoTextConfigWithPartnerConfig();
        }
    }

    public final void setText(CharSequence charSequence) {
        TextView textView = getTextView();
        if (textView != null) {
            if (this.autoTextSizeEnabled) {
                autoAdjustTextSize(textView);
            }
            textView.setText(charSequence);
        }
    }
}
