package com.google.android.setupdesign;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.R;

import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupcompat.template.StatusBarMixin;
import com.google.android.setupcompat.util.ForceTwoPaneHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupdesign.template.DescriptionMixin;
import com.google.android.setupdesign.template.HeaderMixin;
import com.google.android.setupdesign.template.IconMixin;
import com.google.android.setupdesign.template.IllustrationProgressMixin;
import com.google.android.setupdesign.template.ProfileMixin;
import com.google.android.setupdesign.template.ProgressBarMixin;
import com.google.android.setupdesign.template.RequireScrollMixin;
import com.google.android.setupdesign.template.ScrollViewScrollHandlingDelegate;
import com.google.android.setupdesign.util.LayoutStyler;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import com.google.android.setupdesign.util.TextViewPartnerStyler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class GlifLayout extends PartnerCustomizationLayout {
    public boolean applyPartnerHeavyThemeResource;
    public ColorStateList backgroundBaseColor;
    public boolean backgroundPatterned;
    public ColorStateList primaryColor;

    public GlifLayout(Context context) {
        this(context, 0, 0);
    }

    public static boolean isEmbeddedActivityOnePaneEnabled(Context context) {
        return PartnerConfigHelper.isEmbeddedActivityOnePaneEnabled(context)
                && ActivityEmbeddingController.getInstance(context)
                        .isActivityEmbedded(PartnerConfigHelper.lookupActivityFromContext(context));
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R.id.sud_layout_content;
        }
        if (i == 0) {
            i = R.id.suc_layout_content;
        }
        return (ViewGroup) findViewById(i);
    }

    public ColorStateList getBackgroundBaseColor() {
        return this.backgroundBaseColor;
    }

    public CharSequence getDescriptionText() {
        TextView textView = ((DescriptionMixin) getMixin(DescriptionMixin.class)).getTextView();
        if (textView != null) {
            return textView.getText();
        }
        return null;
    }

    public TextView getDescriptionTextView() {
        return ((DescriptionMixin) getMixin(DescriptionMixin.class)).getTextView();
    }

    public ColorStateList getHeaderColor() {
        TextView textView = ((HeaderMixin) getMixin(HeaderMixin.class)).getTextView();
        if (textView != null) {
            return textView.getTextColors();
        }
        return null;
    }

    public CharSequence getHeaderText() {
        TextView textView = ((HeaderMixin) getMixin(HeaderMixin.class)).getTextView();
        if (textView != null) {
            return textView.getText();
        }
        return null;
    }

    public TextView getHeaderTextView() {
        return ((HeaderMixin) getMixin(HeaderMixin.class)).getTextView();
    }

    public Drawable getIcon() {
        ImageView view = ((IconMixin) getMixin(IconMixin.class)).getView();
        if (view != null) {
            return view.getDrawable();
        }
        return null;
    }

    public ColorStateList getPrimaryColor() {
        return this.primaryColor;
    }

    public ScrollView getScrollView() {
        View findManagedViewById = findManagedViewById(R.id.sud_scroll_view);
        if (findManagedViewById instanceof ScrollView) {
            return (ScrollView) findManagedViewById;
        }
        return null;
    }

    public final void init$2(AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        TypedArray obtainStyledAttributes =
                getContext().obtainStyledAttributes(attributeSet, R$styleable.SudGlifLayout, i, 0);
        this.applyPartnerHeavyThemeResource =
                shouldApplyPartnerResource() && obtainStyledAttributes.getBoolean(4, false);
        registerMixin(HeaderMixin.class, new HeaderMixin(this, attributeSet, i));
        registerMixin(DescriptionMixin.class, new DescriptionMixin(this, attributeSet, i));
        registerMixin(IconMixin.class, new IconMixin(this, attributeSet, i));
        registerMixin(ProfileMixin.class, new ProfileMixin(this));
        registerMixin(ProgressBarMixin.class, new ProgressBarMixin(this, attributeSet, i));
        Mixin illustrationProgressMixin = new IllustrationProgressMixin();
        IllustrationProgressMixin.ProgressConfig[] progressConfigArr =
                IllustrationProgressMixin.ProgressConfig.$VALUES;
        getContext();
        registerMixin(IllustrationProgressMixin.class, illustrationProgressMixin);
        RequireScrollMixin requireScrollMixin = new RequireScrollMixin();
        registerMixin(RequireScrollMixin.class, requireScrollMixin);
        ScrollView scrollView = getScrollView();
        if (scrollView != null) {
            requireScrollMixin.delegate =
                    new ScrollViewScrollHandlingDelegate(requireScrollMixin, scrollView);
        }
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(2);
        if (colorStateList != null) {
            setPrimaryColor(colorStateList);
        }
        if (shouldApplyPartnerHeavyThemeResource() && !useFullDynamicColor()) {
            getRootView()
                    .setBackgroundColor(
                            PartnerConfigHelper.get(getContext())
                                    .getColor(
                                            getContext(),
                                            PartnerConfig.CONFIG_LAYOUT_BACKGROUND_COLOR));
        }
        View findManagedViewById = findManagedViewById(R.id.sud_layout_content);
        if (findManagedViewById != null) {
            if (shouldApplyPartnerResource()) {
                LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(findManagedViewById);
            }
            if (!(this instanceof GlifPreferenceLayout)) {
                tryApplyPartnerCustomizationContentPaddingTopStyle(findManagedViewById);
            }
        }
        updateLandscapeMiddleHorizontalSpacing();
        setBackgroundBaseColor(obtainStyledAttributes.getColorStateList(0));
        setBackgroundPatterned(obtainStyledAttributes.getBoolean(1, true));
        int resourceId = obtainStyledAttributes.getResourceId(3, 0);
        if (resourceId != 0) {
            ViewStub viewStub = (ViewStub) findManagedViewById(R.id.sud_layout_sticky_header);
            viewStub.setLayoutResource(resourceId);
            viewStub.inflate();
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        ((IconMixin) getMixin(IconMixin.class)).tryApplyPartnerCustomizationStyle();
        ((HeaderMixin) getMixin(HeaderMixin.class)).tryApplyPartnerCustomizationStyle();
        TemplateLayout templateLayout =
                ((DescriptionMixin) getMixin(DescriptionMixin.class)).templateLayout;
        TextView textView = (TextView) templateLayout.findManagedViewById(R.id.sud_layout_subtitle);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_DESCRIPTION_LINK_FONT_FAMILY;
        PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_DESCRIPTION_FONT_WEIGHT;
        PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_DESCRIPTION_FONT_FAMILY;
        PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_DESCRIPTION_TEXT_SIZE;
        PartnerConfig partnerConfig5 = PartnerConfig.CONFIG_DESCRIPTION_LINK_TEXT_COLOR;
        PartnerConfig partnerConfig6 = PartnerConfig.CONFIG_DESCRIPTION_TEXT_COLOR;
        if (textView != null && PartnerStyleHelper.shouldApplyPartnerResource(templateLayout)) {
            TextViewPartnerStyler.applyPartnerCustomizationStyle(
                    textView,
                    new TextViewPartnerStyler.TextPartnerConfigs(
                            partnerConfig6,
                            partnerConfig5,
                            partnerConfig4,
                            partnerConfig3,
                            partnerConfig2,
                            partnerConfig,
                            PartnerConfig.CONFIG_DESCRIPTION_TEXT_MARGIN_TOP,
                            PartnerConfig.CONFIG_DESCRIPTION_TEXT_MARGIN_BOTTOM,
                            PartnerStyleHelper.getLayoutGravity(textView.getContext())));
        }
        ProgressBarMixin progressBarMixin = (ProgressBarMixin) getMixin(ProgressBarMixin.class);
        ProgressBar peekProgressBar = progressBarMixin.peekProgressBar();
        if (progressBarMixin.useBottomProgressBar && peekProgressBar != null) {
            TemplateLayout templateLayout2 = progressBarMixin.templateLayout;
            if (!(templateLayout2 instanceof GlifLayout)
                    ? false
                    : ((GlifLayout) templateLayout2).shouldApplyPartnerHeavyThemeResource()) {
                Context context = peekProgressBar.getContext();
                ViewGroup.LayoutParams layoutParams = peekProgressBar.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams =
                            (ViewGroup.MarginLayoutParams) layoutParams;
                    int i = marginLayoutParams.topMargin;
                    PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
                    PartnerConfig partnerConfig7 = PartnerConfig.CONFIG_PROGRESS_BAR_MARGIN_TOP;
                    if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig7)) {
                        i =
                                (int)
                                        PartnerConfigHelper.get(context)
                                                .getDimension(
                                                        context,
                                                        partnerConfig7,
                                                        context.getResources()
                                                                .getDimension(
                                                                        R.dimen
                                                                                .sud_progress_bar_margin_top));
                    }
                    int i2 = marginLayoutParams.bottomMargin;
                    PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
                    PartnerConfig partnerConfig8 = PartnerConfig.CONFIG_PROGRESS_BAR_MARGIN_BOTTOM;
                    if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig8)) {
                        i2 =
                                (int)
                                        PartnerConfigHelper.get(context)
                                                .getDimension(
                                                        context,
                                                        partnerConfig8,
                                                        context.getResources()
                                                                .getDimension(
                                                                        R.dimen
                                                                                .sud_progress_bar_margin_bottom));
                    }
                    if (i != marginLayoutParams.topMargin
                            || i2 != marginLayoutParams.bottomMargin) {
                        marginLayoutParams.setMargins(
                                marginLayoutParams.leftMargin,
                                i,
                                marginLayoutParams.rightMargin,
                                i2);
                    }
                }
            } else {
                Context context2 = peekProgressBar.getContext();
                ViewGroup.LayoutParams layoutParams2 = peekProgressBar.getLayoutParams();
                if (layoutParams2 instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams2 =
                            (ViewGroup.MarginLayoutParams) layoutParams2;
                    marginLayoutParams2.setMargins(
                            marginLayoutParams2.leftMargin,
                            (int)
                                    context2.getResources()
                                            .getDimension(R.dimen.sud_progress_bar_margin_top),
                            marginLayoutParams2.rightMargin,
                            (int)
                                    context2.getResources()
                                            .getDimension(R.dimen.sud_progress_bar_margin_bottom));
                }
            }
        }
        TemplateLayout templateLayout3 =
                ((ProfileMixin) getMixin(ProfileMixin.class)).templateLayout;
        if (PartnerStyleHelper.shouldApplyPartnerResource(templateLayout3)) {
            ImageView imageView =
                    (ImageView) templateLayout3.findManagedViewById(R.id.sud_account_avatar);
            TextView textView2 =
                    (TextView) templateLayout3.findManagedViewById(R.id.sud_account_name);
            LinearLayout linearLayout =
                    (LinearLayout) templateLayout3.findManagedViewById(R.id.sud_layout_profile);
            LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(
                    templateLayout3.findManagedViewById(R.id.sud_layout_header));
            if (imageView != null && textView2 != null) {
                Context context3 = imageView.getContext();
                ViewGroup.LayoutParams layoutParams3 = imageView.getLayoutParams();
                if (layoutParams3 instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams3 =
                            (ViewGroup.MarginLayoutParams) layoutParams3;
                    marginLayoutParams3.setMargins(
                            marginLayoutParams3.leftMargin,
                            marginLayoutParams3.topMargin,
                            (int)
                                    PartnerConfigHelper.get(context3)
                                            .getDimension(
                                                    context3,
                                                    PartnerConfig.CONFIG_ACCOUNT_AVATAR_MARGIN_END,
                                                    0.0f),
                            marginLayoutParams3.bottomMargin);
                }
                imageView.setMaxHeight(
                        (int)
                                PartnerConfigHelper.get(context3)
                                        .getDimension(
                                                context3,
                                                PartnerConfig.CONFIG_ACCOUNT_AVATAR_SIZE,
                                                context3.getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sud_account_avatar_max_height)));
                textView2.setTextSize(
                        0,
                        (int)
                                PartnerConfigHelper.get(context3)
                                        .getDimension(
                                                context3,
                                                PartnerConfig.CONFIG_ACCOUNT_NAME_TEXT_SIZE,
                                                context3.getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sud_account_name_text_size)));
                Typeface create =
                        Typeface.create(
                                PartnerConfigHelper.get(context3)
                                        .getString(
                                                context3,
                                                PartnerConfig.CONFIG_ACCOUNT_NAME_FONT_FAMILY),
                                0);
                if (create != null) {
                    textView2.setTypeface(create);
                }
                linearLayout.setGravity(
                        PartnerStyleHelper.getLayoutGravity(linearLayout.getContext()));
            }
        }
        TextView textView3 = (TextView) findManagedViewById(R.id.sud_layout_description);
        if (textView3 != null) {
            if (this.applyPartnerHeavyThemeResource) {
                TextViewPartnerStyler.applyPartnerCustomizationStyle(
                        textView3,
                        new TextViewPartnerStyler.TextPartnerConfigs(
                                partnerConfig6,
                                partnerConfig5,
                                partnerConfig4,
                                partnerConfig3,
                                partnerConfig2,
                                partnerConfig,
                                null,
                                null,
                                PartnerStyleHelper.getLayoutGravity(textView3.getContext())));
            } else if (shouldApplyPartnerResource()) {
                int layoutGravity = PartnerStyleHelper.getLayoutGravity(textView3.getContext());
                TextViewPartnerStyler.applyPartnerCustomizationVerticalMargins(
                        textView3,
                        new TextViewPartnerStyler.TextPartnerConfigs(
                                null, null, null, null, null, null, null, null, layoutGravity));
                textView3.setGravity(layoutGravity);
            }
        }
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            if (isEmbeddedActivityOnePaneEnabled(getContext())) {
                i = R.layout.sud_glif_embedded_template;
            } else {
                Context context = getContext();
                Logger logger = ForceTwoPaneHelper.LOG;
                i =
                        PartnerConfigHelper.isForceTwoPaneEnabled(context)
                                ? ForceTwoPaneHelper.getForceTwoPaneStyleLayout(
                                        getContext(), R.layout.sud_glif_template)
                                : R.layout.sud_glif_template;
            }
        }
        return inflateTemplate(layoutInflater, 2132083860, i);
    }

    public void setBackgroundBaseColor(ColorStateList colorStateList) {
        this.backgroundBaseColor = colorStateList;
        updateBackground();
    }

    public void setBackgroundPatterned(boolean z) {
        this.backgroundPatterned = z;
        updateBackground();
    }

    public void setDescriptionText(int i) {
        DescriptionMixin descriptionMixin = (DescriptionMixin) getMixin(DescriptionMixin.class);
        TextView textView = descriptionMixin.getTextView();
        if (textView == null || i == 0) {
            Log.w(
                    "DescriptionMixin",
                    "Fail to set text due to either invalid resource id or text view not found.");
            return;
        }
        textView.setText(i);
        TextView textView2 = descriptionMixin.getTextView();
        if (textView2 != null) {
            textView2.setVisibility(0);
        }
    }

    public void setHeaderColor(ColorStateList colorStateList) {
        TextView textView = ((HeaderMixin) getMixin(HeaderMixin.class)).getTextView();
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }

    public void setHeaderText(int i) {
        ((HeaderMixin) getMixin(HeaderMixin.class)).setText(i);
    }

    public void setIcon(Drawable drawable) {
        IconMixin iconMixin = (IconMixin) getMixin(IconMixin.class);
        ImageView view = iconMixin.getView();
        if (view != null) {
            if (drawable != null) {
                drawable.applyTheme(iconMixin.context.getTheme());
            }
            view.setImageDrawable(drawable);
            view.setVisibility(drawable != null ? 0 : 8);
            int visibility = view.getVisibility();
            TemplateLayout templateLayout = iconMixin.templateLayout;
            if (((FrameLayout) templateLayout.findManagedViewById(R.id.sud_layout_icon_container))
                    != null) {
                ((FrameLayout) templateLayout.findManagedViewById(R.id.sud_layout_icon_container))
                        .setVisibility(visibility);
            }
            iconMixin.tryApplyPartnerCustomizationStyle();
        }
    }

    @TargetApi(31)
    public void setLandscapeHeaderAreaVisible(boolean z) {
        View findManagedViewById = findManagedViewById(R.id.sud_landscape_header_area);
        if (findManagedViewById == null) {
            return;
        }
        if (z) {
            findManagedViewById.setVisibility(0);
        } else {
            findManagedViewById.setVisibility(8);
        }
        updateLandscapeMiddleHorizontalSpacing();
    }

    public void setPrimaryColor(ColorStateList colorStateList) {
        this.primaryColor = colorStateList;
        updateBackground();
        ProgressBarMixin progressBarMixin = (ProgressBarMixin) getMixin(ProgressBarMixin.class);
        progressBarMixin.color = colorStateList;
        ProgressBar peekProgressBar = progressBarMixin.peekProgressBar();
        if (peekProgressBar != null) {
            peekProgressBar.setIndeterminateTintList(colorStateList);
            peekProgressBar.setProgressBackgroundTintList(colorStateList);
        }
    }

    public void setProgressBarShown(boolean z) {
        ((ProgressBarMixin) getMixin(ProgressBarMixin.class)).setShown(z);
    }

    public final boolean shouldApplyPartnerHeavyThemeResource() {
        return this.applyPartnerHeavyThemeResource
                || (shouldApplyPartnerResource()
                        && PartnerConfigHelper.shouldApplyExtendedPartnerConfig(getContext()));
    }

    public final void tryApplyPartnerCustomizationContentPaddingTopStyle(View view) {
        int dimension;
        Context context = view.getContext();
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_CONTENT_PADDING_TOP;
        boolean isPartnerConfigAvailable =
                partnerConfigHelper.isPartnerConfigAvailable(partnerConfig);
        if (shouldApplyPartnerResource()
                && isPartnerConfigAvailable
                && (dimension =
                                (int)
                                        PartnerConfigHelper.get(context)
                                                .getDimension(context, partnerConfig, 0.0f))
                        != view.getPaddingTop()) {
            view.setPadding(
                    view.getPaddingStart(),
                    dimension,
                    view.getPaddingEnd(),
                    view.getPaddingBottom());
        }
    }

    public final void updateBackground() {
        int defaultColor;
        if (findManagedViewById(R.id.suc_layout_status) != null) {
            ColorStateList colorStateList = this.backgroundBaseColor;
            if (colorStateList != null) {
                defaultColor = colorStateList.getDefaultColor();
            } else {
                ColorStateList colorStateList2 = this.primaryColor;
                defaultColor = colorStateList2 != null ? colorStateList2.getDefaultColor() : 0;
            }
            ((StatusBarMixin) getMixin(StatusBarMixin.class))
                    .setStatusBarBackground(
                            this.backgroundPatterned
                                    ? new GlifPatternDrawable(defaultColor)
                                    : new ColorDrawable(defaultColor));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateLandscapeMiddleHorizontalSpacing() {
        /*
            r8 = this;
            android.content.res.Resources r0 = r8.getResources()
            r1 = 2131169971(0x7f0712b3, float:1.7954287E38)
            int r0 = r0.getDimensionPixelSize(r1)
            boolean r1 = r8.shouldApplyPartnerResource()
            r2 = 0
            if (r1 == 0) goto L33
            android.content.Context r1 = r8.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r1 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r1)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r3 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_LAND_MIDDLE_HORIZONTAL_SPACING
            boolean r1 = r1.isPartnerConfigAvailable(r3)
            if (r1 == 0) goto L33
            android.content.Context r0 = r8.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r0 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r0)
            android.content.Context r1 = r8.getContext()
            float r0 = r0.getDimension(r1, r3, r2)
            int r0 = (int) r0
        L33:
            r1 = 2131365687(0x7f0a0f37, float:1.8351246E38)
            android.view.View r1 = r8.findManagedViewById(r1)
            r3 = 0
            if (r1 == 0) goto L8e
            boolean r4 = r8.shouldApplyPartnerResource()
            if (r4 == 0) goto L65
            android.content.Context r4 = r8.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r4 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r4)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r5 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_LAYOUT_MARGIN_END
            boolean r4 = r4.isPartnerConfigAvailable(r5)
            if (r4 == 0) goto L65
            android.content.Context r4 = r8.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r4 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r4)
            android.content.Context r6 = r8.getContext()
            float r4 = r4.getDimension(r6, r5, r2)
            int r4 = (int) r4
            goto L7c
        L65:
            android.content.Context r4 = r8.getContext()
            r5 = 2130970221(0x7f04066d, float:1.7549146E38)
            int[] r5 = new int[]{r5}
            android.content.res.TypedArray r4 = r4.obtainStyledAttributes(r5)
            int r5 = r4.getDimensionPixelSize(r3, r3)
            r4.recycle()
            r4 = r5
        L7c:
            int r5 = r0 / 2
            int r5 = r5 - r4
            int r4 = r1.getPaddingStart()
            int r6 = r1.getPaddingTop()
            int r7 = r1.getPaddingBottom()
            r1.setPadding(r4, r6, r5, r7)
        L8e:
            r4 = 2131365686(0x7f0a0f36, float:1.8351244E38)
            android.view.View r4 = r8.findManagedViewById(r4)
            if (r4 == 0) goto Leb
            boolean r5 = r8.shouldApplyPartnerResource()
            if (r5 == 0) goto Lbf
            android.content.Context r5 = r8.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r5 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r5)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r6 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_LAYOUT_MARGIN_START
            boolean r5 = r5.isPartnerConfigAvailable(r6)
            if (r5 == 0) goto Lbf
            android.content.Context r5 = r8.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r5 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r5)
            android.content.Context r8 = r8.getContext()
            float r8 = r5.getDimension(r8, r6, r2)
            int r8 = (int) r8
            goto Ld6
        Lbf:
            android.content.Context r8 = r8.getContext()
            r2 = 2130970222(0x7f04066e, float:1.7549148E38)
            int[] r2 = new int[]{r2}
            android.content.res.TypedArray r8 = r8.obtainStyledAttributes(r2)
            int r2 = r8.getDimensionPixelSize(r3, r3)
            r8.recycle()
            r8 = r2
        Ld6:
            if (r1 == 0) goto Ldc
            int r0 = r0 / 2
            int r3 = r0 - r8
        Ldc:
            int r8 = r4.getPaddingTop()
            int r0 = r4.getPaddingEnd()
            int r1 = r4.getPaddingBottom()
            r4.setPadding(r3, r8, r0, r1)
        Leb:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.setupdesign.GlifLayout.updateLandscapeMiddleHorizontalSpacing():void");
    }

    public GlifLayout(Context context, int i) {
        this(context, i, 0);
    }

    public void setHeaderText(CharSequence charSequence) {
        ((HeaderMixin) getMixin(HeaderMixin.class)).setText(charSequence);
    }

    public GlifLayout(Context context, int i, int i2) {
        super(context, i, i2);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        init$2(null, R.attr.sudLayoutTheme);
    }

    public GlifLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        init$2(attributeSet, R.attr.sudLayoutTheme);
    }

    public void setDescriptionText(CharSequence charSequence) {
        DescriptionMixin descriptionMixin = (DescriptionMixin) getMixin(DescriptionMixin.class);
        TextView textView = descriptionMixin.getTextView();
        if (textView != null) {
            textView.setText(charSequence);
            TextView textView2 = descriptionMixin.getTextView();
            if (textView2 != null) {
                textView2.setVisibility(0);
            }
        }
    }

    @TargetApi(11)
    public GlifLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        init$2(attributeSet, i);
    }
}
