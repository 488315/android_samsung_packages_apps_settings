package com.google.android.material.navigation;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.R$styleable;
import androidx.appcompat.graphics.drawable.SeslRecoilDrawable;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeState;
import com.google.android.material.ripple.RippleUtils;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class NavigationBarItemView extends FrameLayout implements MenuView.ItemView {
    public static final ActiveIndicatorUnlabeledTransform ACTIVE_INDICATOR_UNLABELED_TRANSFORM =
            null;
    public final String TAG;
    public ValueAnimator activeIndicatorAnimator;
    public int activeIndicatorDesiredHeight;
    public int activeIndicatorDesiredWidth;
    public boolean activeIndicatorEnabled;
    public int activeIndicatorLabelPadding;
    public int activeIndicatorMarginHorizontal;
    public float activeIndicatorProgress;
    public ActiveIndicatorTransform activeIndicatorTransform;
    public final View activeIndicatorView;
    public int activeTextAppearance;
    public BadgeDrawable badgeDrawable;
    public int defaultMargin;
    public final ImageView icon;
    public final FrameLayout iconContainer;
    public ColorStateList iconTint;
    public boolean initialized;
    public boolean isNeedToSkipRefreshDrawable;
    public boolean isShifting;
    public Drawable itemBackground;
    public MenuItemImpl itemData;
    public int itemPaddingBottom;
    public int itemPaddingTop;
    public ColorStateList itemRippleColor;
    public final ViewGroup labelGroup;
    public int labelVisibilityMode;
    public final TextView largeLabel;
    public int mBadgeType;
    public boolean mIsBadgeNumberless;
    public SpannableStringBuilder mLabelImgSpan;
    public int mLargeLabelAppearance;
    public int mSmallLabelAppearance;
    public final int mViewType;
    public Drawable originalIconDrawable;
    public float scaleDownFactor;
    public float scaleUpFactor;
    public float shiftAmount;
    public final TextView smallLabel;
    public Drawable wrappedIconDrawable;
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public static final ActiveIndicatorTransform ACTIVE_INDICATOR_LABELED_TRANSFORM =
            new ActiveIndicatorTransform();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ActiveIndicatorTransform {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ActiveIndicatorUnlabeledTransform extends ActiveIndicatorTransform {}

    public NavigationBarItemView(Context context, int i) {
        super(context, null, 0);
        this.TAG = "NavigationBarItemView";
        this.initialized = false;
        this.activeTextAppearance = 0;
        this.activeIndicatorTransform = ACTIVE_INDICATOR_LABELED_TRANSFORM;
        this.activeIndicatorProgress = 0.0f;
        this.activeIndicatorEnabled = false;
        this.activeIndicatorDesiredWidth = 0;
        this.activeIndicatorDesiredHeight = 0;
        this.activeIndicatorMarginHorizontal = 0;
        this.mBadgeType = 1;
        this.mViewType = i;
        final NavigationBarMenuView.AnonymousClass2 anonymousClass2 =
                (NavigationBarMenuView.AnonymousClass2) this;
        LayoutInflater.from(context)
                .inflate(
                        anonymousClass2.val$menuItem.mSeslNaviMenuItemType == 1
                                ? com.android.settings.R.layout.sesl_bottom_navigation_item_checkbox
                                : anonymousClass2.val$layoutType != 3
                                        ? com.android.settings.R.layout.sesl_bottom_navigation_item
                                        : com.android.settings.R.layout
                                                .sesl_bottom_navigation_item_text,
                        (ViewGroup) this,
                        true);
        this.iconContainer =
                (FrameLayout)
                        findViewById(com.android.settings.R.id.navigation_bar_item_icon_container);
        this.activeIndicatorView =
                findViewById(com.android.settings.R.id.navigation_bar_item_active_indicator_view);
        ImageView imageView =
                (ImageView) findViewById(com.android.settings.R.id.navigation_bar_item_icon_view);
        this.icon = imageView;
        ViewGroup viewGroup =
                (ViewGroup)
                        findViewById(com.android.settings.R.id.navigation_bar_item_labels_group);
        this.labelGroup = viewGroup;
        TextView textView =
                (TextView)
                        findViewById(
                                com.android.settings.R.id.navigation_bar_item_small_label_view);
        this.smallLabel = textView;
        TextView textView2 =
                (TextView)
                        findViewById(
                                com.android.settings.R.id.navigation_bar_item_large_label_view);
        this.largeLabel = textView2;
        setBackgroundResource(com.android.settings.R.drawable.mtrl_navigation_bar_item_background);
        this.itemPaddingTop =
                getResources()
                        .getDimensionPixelSize(
                                com.android.settings.R.dimen
                                        .mtrl_navigation_bar_item_default_margin);
        this.itemPaddingBottom = viewGroup.getPaddingBottom();
        this.activeIndicatorLabelPadding =
                getResources()
                        .getDimensionPixelSize(
                                com.android.settings.R.dimen
                                        .m3_navigation_item_active_indicator_label_padding);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        textView.setImportantForAccessibility(2);
        textView2.setImportantForAccessibility(2);
        setFocusable(true);
        calculateTextScaleFactors(textView.getTextSize(), textView2.getTextSize());
        if (imageView != null) {
            imageView.addOnLayoutChangeListener(
                    new View
                            .OnLayoutChangeListener() { // from class:
                                                        // com.google.android.material.navigation.NavigationBarItemView.1
                        @Override // android.view.View.OnLayoutChangeListener
                        public final void onLayoutChange(
                                View view,
                                int i2,
                                int i3,
                                int i4,
                                int i5,
                                int i6,
                                int i7,
                                int i8,
                                int i9) {
                            if (anonymousClass2.icon.getVisibility() == 0) {
                                NavigationBarItemView navigationBarItemView = anonymousClass2;
                                ImageView imageView2 = navigationBarItemView.icon;
                                BadgeDrawable badgeDrawable = navigationBarItemView.badgeDrawable;
                                if (badgeDrawable != null) {
                                    Rect rect = new Rect();
                                    imageView2.getDrawingRect(rect);
                                    badgeDrawable.setBounds(rect);
                                    badgeDrawable.updateBadgeCoordinates(imageView2, null);
                                }
                            }
                        }
                    });
        }
        ViewCompat.setAccessibilityDelegate(this, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setTextAppearanceWithoutFontScaling(int r4, android.widget.TextView r5) {
        /*
            r5.setTextAppearance(r4)
            android.content.Context r0 = r5.getContext()
            r1 = 0
            if (r4 != 0) goto Lc
        La:
            r4 = r1
            goto L4c
        Lc:
            int[] r2 = androidx.appcompat.R$styleable.TextAppearance
            android.content.res.TypedArray r4 = r0.obtainStyledAttributes(r4, r2)
            android.util.TypedValue r2 = new android.util.TypedValue
            r2.<init>()
            boolean r3 = r4.getValue(r1, r2)
            r4.recycle()
            if (r3 != 0) goto L21
            goto La
        L21:
            int r4 = r2.getComplexUnit()
            r3 = 2
            if (r4 != r3) goto L3e
            int r4 = r2.data
            float r4 = android.util.TypedValue.complexToFloat(r4)
            android.content.res.Resources r0 = r0.getResources()
            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()
            float r0 = r0.density
            float r4 = r4 * r0
            int r4 = java.lang.Math.round(r4)
            goto L4c
        L3e:
            int r4 = r2.data
            android.content.res.Resources r0 = r0.getResources()
            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()
            int r4 = android.util.TypedValue.complexToDimensionPixelSize(r4, r0)
        L4c:
            if (r4 == 0) goto L52
            float r4 = (float) r4
            r5.setTextSize(r1, r4)
        L52:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.material.navigation.NavigationBarItemView.setTextAppearanceWithoutFontScaling(int,"
                    + " android.widget.TextView):void");
    }

    public static void setViewScaleValues(View view, float f, float f2, int i) {
        view.setScaleX(f);
        view.setScaleY(f2);
        view.setVisibility(i);
    }

    public static void setViewTopMarginAndGravity(View view, int i, int i2) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = i;
        layoutParams.bottomMargin = i;
        layoutParams.gravity = i2;
        view.setLayoutParams(layoutParams);
    }

    public static void updateViewPaddingBottom(View view, int i) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), i);
    }

    public final void calculateTextScaleFactors(float f, float f2) {
        if (f2 == 0.0f || f == 0.0f) {
            Log.e(this.TAG, "LabelSize is invalid");
            this.scaleUpFactor = 1.0f;
            this.scaleDownFactor = 1.0f;
            this.shiftAmount = 0.0f;
            return;
        }
        this.shiftAmount = f - f2;
        float f3 = (f2 * 1.0f) / f;
        this.scaleUpFactor = f3;
        this.scaleDownFactor = (f * 1.0f) / f2;
        if (f3 >= Float.MAX_VALUE || f3 <= -3.4028235E38f) {
            Log.e(this.TAG, "scaleUpFactor is invalid");
            this.scaleUpFactor = 1.0f;
            this.shiftAmount = 0.0f;
        }
        float f4 = this.scaleDownFactor;
        if (f4 >= Float.MAX_VALUE || f4 <= -3.4028235E38f) {
            Log.e(this.TAG, "scaleDownFactor is invalid");
            this.scaleDownFactor = 1.0f;
            this.shiftAmount = 0.0f;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        FrameLayout frameLayout = this.iconContainer;
        if (frameLayout != null && this.activeIndicatorEnabled) {
            frameLayout.dispatchTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final View getIconOrContainer() {
        FrameLayout frameLayout = this.iconContainer;
        return frameLayout != null ? frameLayout : this.icon;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public final MenuItemImpl getItemData() {
        return this.itemData;
    }

    @Override // android.view.View
    public final int getSuggestedMinimumHeight() {
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) this.labelGroup.getLayoutParams();
        return this.labelGroup.getMeasuredHeight()
                + getIconOrContainer().getMeasuredHeight()
                + ((FrameLayout.LayoutParams) getIconOrContainer().getLayoutParams()).topMargin
                + (this.labelGroup.getVisibility() == 0 ? this.activeIndicatorLabelPadding : 0)
                + layoutParams.topMargin
                + layoutParams.bottomMargin;
    }

    @Override // android.view.View
    public final int getSuggestedMinimumWidth() {
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) this.labelGroup.getLayoutParams();
        int measuredWidth =
                this.labelGroup.getMeasuredWidth()
                        + layoutParams.leftMargin
                        + layoutParams.rightMargin;
        BadgeDrawable badgeDrawable = this.badgeDrawable;
        int minimumWidth =
                badgeDrawable == null
                        ? 0
                        : badgeDrawable.getMinimumWidth()
                                - this.badgeDrawable.state.currentState.horizontalOffsetWithoutText
                                        .intValue();
        FrameLayout.LayoutParams layoutParams2 =
                (FrameLayout.LayoutParams) getIconOrContainer().getLayoutParams();
        return Math.max(
                Math.max(minimumWidth, layoutParams2.rightMargin)
                        + this.icon.getMeasuredWidth()
                        + Math.max(minimumWidth, layoutParams2.leftMargin),
                measuredWidth);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getBackground() instanceof SeslRecoilDrawable) {
            this.isNeedToSkipRefreshDrawable = true;
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setLargeTextSize(this.mLargeLabelAppearance, this.largeLabel);
        setLargeTextSize(this.mSmallLabelAppearance, this.smallLabel);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl != null && menuItemImpl.isCheckable() && this.itemData.isChecked()) {
            FrameLayout.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(
            AccessibilityNodeInfo accessibilityNodeInfo) {
        BadgeDrawable badgeDrawable;
        Context context;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.itemData != null
                && (badgeDrawable = this.badgeDrawable) != null
                && badgeDrawable.isVisible()) {
            MenuItemImpl menuItemImpl = this.itemData;
            CharSequence charSequence = menuItemImpl.mTitle;
            if (!TextUtils.isEmpty(menuItemImpl.mContentDescription)) {
                charSequence = this.itemData.mContentDescription;
            }
            StringBuilder sb = new StringBuilder();
            sb.append((Object) charSequence);
            sb.append(", ");
            BadgeDrawable badgeDrawable2 = this.badgeDrawable;
            CharSequence charSequence2 = null;
            if (badgeDrawable2.isVisible()) {
                BadgeState.State state = badgeDrawable2.state.currentState;
                String str = state.text;
                if (str != null) {
                    CharSequence charSequence3 = state.contentDescriptionForText;
                    charSequence2 = charSequence3 != null ? charSequence3 : str;
                } else if (!badgeDrawable2.hasNumber()) {
                    charSequence2 = badgeDrawable2.state.currentState.contentDescriptionNumberless;
                } else if (badgeDrawable2.state.currentState.contentDescriptionQuantityStrings != 0
                        && (context = (Context) badgeDrawable2.contextRef.get()) != null) {
                    if (badgeDrawable2.maxBadgeNumber != -2) {
                        int number = badgeDrawable2.getNumber();
                        int i = badgeDrawable2.maxBadgeNumber;
                        if (number > i) {
                            charSequence2 =
                                    context.getString(
                                            badgeDrawable2
                                                    .state
                                                    .currentState
                                                    .contentDescriptionExceedsMaxBadgeNumberRes,
                                            Integer.valueOf(i));
                        }
                    }
                    charSequence2 =
                            context.getResources()
                                    .getQuantityString(
                                            badgeDrawable2
                                                    .state
                                                    .currentState
                                                    .contentDescriptionQuantityStrings,
                                            badgeDrawable2.getNumber(),
                                            Integer.valueOf(badgeDrawable2.getNumber()));
                }
            }
            sb.append((Object) charSequence2);
            accessibilityNodeInfo.setContentDescription(sb.toString());
        }
        TextView textView = (TextView) findViewById(com.android.settings.R.id.notifications_badge);
        if (this.itemData != null
                && textView != null
                && textView.getVisibility() == 0
                && textView.getWidth() > 0) {
            CharSequence charSequence4 = this.itemData.mTitle;
            String charSequence5 = charSequence4.toString();
            if (TextUtils.isEmpty(this.itemData.mContentDescription)) {
                int i2 = this.mBadgeType;
                if (i2 == 0) {
                    charSequence5 =
                            ((Object) charSequence4)
                                    + " , "
                                    + getResources()
                                            .getString(
                                                    com.android.settings.R.string
                                                            .sesl_material_badge_description);
                } else if (i2 == 1) {
                    charSequence5 =
                            ((Object) charSequence4)
                                    + " , "
                                    + getResources()
                                            .getString(
                                                    com.android.settings.R.string
                                                            .mtrl_badge_numberless_content_description);
                } else if (i2 == 2) {
                    String charSequence6 = textView.getText().toString();
                    if (charSequence6 != null) {
                        try {
                            Integer.parseInt(charSequence6);
                            int parseInt = Integer.parseInt(charSequence6);
                            charSequence5 =
                                    ((Object) charSequence4)
                                            + " , "
                                            + getResources()
                                                    .getQuantityString(
                                                            com.android.settings.R.plurals
                                                                    .mtrl_badge_content_description,
                                                            parseInt,
                                                            Integer.valueOf(parseInt));
                        } catch (NumberFormatException unused) {
                        }
                    }
                    charSequence5 =
                            this.mIsBadgeNumberless
                                    ? ((Object) charSequence4)
                                            + " , "
                                            + getResources()
                                                    .getString(
                                                            com.android.settings.R.string
                                                                    .mtrl_exceed_max_badge_number_content_description,
                                                            999)
                                    : ((Object) charSequence4)
                                            + " , "
                                            + getResources()
                                                    .getString(
                                                            com.android.settings.R.string
                                                                    .sesl_material_badge_description);
                }
            } else {
                charSequence5 = this.itemData.mContentDescription.toString();
            }
            accessibilityNodeInfo.setContentDescription(charSequence5);
        }
        ViewGroup viewGroup = (ViewGroup) getParent();
        int indexOfChild = viewGroup.indexOfChild(this);
        int i3 = 0;
        for (int i4 = 0; i4 < indexOfChild; i4++) {
            View childAt = viewGroup.getChildAt(i4);
            if ((childAt instanceof NavigationBarItemView) && childAt.getVisibility() == 0) {
                i3++;
            }
        }
        accessibilityNodeInfo.setCollectionItemInfo(
                (AccessibilityNodeInfo.CollectionItemInfo)
                        AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(
                                        isSelected(), 0, 1, i3, 1)
                                .mInfo);
        if (isSelected()) {
            accessibilityNodeInfo.setClickable(false);
            accessibilityNodeInfo.removeAction(
                    (AccessibilityNodeInfo.AccessibilityAction)
                            AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK
                                    .mAction);
        }
        accessibilityNodeInfo.setClassName(Button.class.getName());
    }

    @Override // android.view.View
    public final void onSizeChanged(final int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        post(
                new Runnable() { // from class:
                                 // com.google.android.material.navigation.NavigationBarItemView.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavigationBarItemView.this.updateActiveIndicatorLayoutParams(i);
                    }
                });
    }

    public final void refreshChecked() {
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl != null) {
            setChecked(menuItemImpl.isChecked());
        }
    }

    @Override // android.view.View
    public final void refreshDrawableState() {
        super.refreshDrawableState();
        if (!this.isNeedToSkipRefreshDrawable || getStateListAnimator() == null) {
            return;
        }
        getStateListAnimator().jumpToCurrentState();
        this.isNeedToSkipRefreshDrawable = false;
    }

    public final void refreshItemBackground() {
        Drawable drawable = this.itemBackground;
        RippleDrawable rippleDrawable = null;
        boolean z = true;
        if (this.itemRippleColor != null) {
            View view = this.activeIndicatorView;
            Drawable background = view == null ? null : view.getBackground();
            if (this.activeIndicatorEnabled) {
                View view2 = this.activeIndicatorView;
                if ((view2 == null ? null : view2.getBackground()) != null
                        && this.iconContainer != null
                        && background != null) {
                    ColorStateList colorStateList = this.itemRippleColor;
                    if (colorStateList == null) {
                        colorStateList = ColorStateList.valueOf(0);
                    }
                    rippleDrawable = new RippleDrawable(colorStateList, null, background);
                    z = false;
                }
            }
            if (drawable == null) {
                ColorStateList colorStateList2 = this.itemRippleColor;
                int[] iArr = RippleUtils.SELECTED_STATE_SET;
                int colorForState =
                        RippleUtils.getColorForState(
                                colorStateList2, RippleUtils.SELECTED_PRESSED_STATE_SET);
                int[] iArr2 = RippleUtils.FOCUSED_STATE_SET;
                drawable =
                        new RippleDrawable(
                                new ColorStateList(
                                        new int[][] {iArr, iArr2, StateSet.NOTHING},
                                        new int[] {
                                            colorForState,
                                            RippleUtils.getColorForState(colorStateList2, iArr2),
                                            RippleUtils.getColorForState(
                                                    colorStateList2, RippleUtils.PRESSED_STATE_SET)
                                        }),
                                null,
                                null);
            }
        }
        FrameLayout frameLayout = this.iconContainer;
        if (frameLayout != null) {
            frameLayout.setPadding(0, 0, 0, 0);
            this.iconContainer.setForeground(rippleDrawable);
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setBackground(drawable);
        setDefaultFocusHighlightEnabled(z);
    }

    public final void setActiveIndicatorProgress(float f, float f2) {
        View view = this.activeIndicatorView;
        if (view != null) {
            this.activeIndicatorTransform.getClass();
            view.setScaleX(AnimationUtils.lerp(0.4f, 1.0f, f));
            view.setScaleY(1.0f);
            view.setAlpha(
                    AnimationUtils.lerp(
                            0.0f, 1.0f, f2 == 0.0f ? 0.8f : 0.0f, f2 == 0.0f ? 1.0f : 0.2f, f));
        }
        this.activeIndicatorProgress = f;
    }

    public final void setBadge(BadgeDrawable badgeDrawable) {
        BadgeDrawable badgeDrawable2 = this.badgeDrawable;
        if (badgeDrawable2 == badgeDrawable) {
            return;
        }
        if (badgeDrawable2 != null && this.icon != null) {
            Log.w("NavigationBar", "Multiple badges shouldn't be attached to one item.");
            ImageView imageView = this.icon;
            if (this.badgeDrawable != null) {
                if (imageView != null) {
                    setClipChildren(true);
                    setClipToPadding(true);
                    BadgeDrawable badgeDrawable3 = this.badgeDrawable;
                    if (badgeDrawable3 != null) {
                        if (badgeDrawable3.getCustomBadgeParent() != null) {
                            badgeDrawable3.getCustomBadgeParent().setForeground(null);
                        } else {
                            imageView.getOverlay().remove(badgeDrawable3);
                        }
                    }
                }
                this.badgeDrawable = null;
            }
        }
        this.badgeDrawable = badgeDrawable;
        ImageView imageView2 = this.icon;
        if (imageView2 != null) {
            setClipChildren(false);
            setClipToPadding(false);
            BadgeDrawable badgeDrawable4 = this.badgeDrawable;
            Rect rect = new Rect();
            imageView2.getDrawingRect(rect);
            badgeDrawable4.setBounds(rect);
            badgeDrawable4.updateBadgeCoordinates(imageView2, null);
            if (badgeDrawable4.getCustomBadgeParent() != null) {
                badgeDrawable4.getCustomBadgeParent().setForeground(badgeDrawable4);
            } else {
                imageView2.getOverlay().add(badgeDrawable4);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0155  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setChecked(boolean r11) {
        /*
            Method dump skipped, instructions count: 465
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.material.navigation.NavigationBarItemView.setChecked(boolean):void");
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.smallLabel.setEnabled(z);
        this.largeLabel.setEnabled(z);
        this.icon.setEnabled(z);
        if (z) {
            ViewCompat.Api24Impl.setPointerIcon(
                    this, PointerIcon.getSystemIcon(getContext(), 1002));
        } else {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api24Impl.setPointerIcon(this, null);
        }
    }

    public final void setIconSize(int i) {
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) this.icon.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        this.icon.setLayoutParams(layoutParams);
    }

    public final void setIconTintList(ColorStateList colorStateList) {
        Drawable drawable;
        this.iconTint = colorStateList;
        MenuItemImpl menuItemImpl = this.itemData;
        if ((menuItemImpl == null && this.wrappedIconDrawable == null)
                || menuItemImpl == null
                || (drawable = this.wrappedIconDrawable) == null) {
            return;
        }
        drawable.setTintList(colorStateList);
        this.wrappedIconDrawable.invalidateSelf();
    }

    public final void setLargeTextSize(int i, TextView textView) {
        if (textView != null) {
            TypedArray obtainStyledAttributes =
                    getContext().obtainStyledAttributes(i, R$styleable.TextAppearance);
            TypedValue peekValue = obtainStyledAttributes.peekValue(0);
            obtainStyledAttributes.recycle();
            textView.setTextSize(
                    1,
                    Math.min(getResources().getConfiguration().fontScale, 1.3f)
                            * TypedValue.complexToFloat(peekValue.data));
        }
    }

    public final void setTextAppearanceActive(int i) {
        this.activeTextAppearance = i;
        setTextAppearanceWithoutFontScaling(i, this.largeLabel);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    public final void setTextAppearanceInactive(int i) {
        setTextAppearanceWithoutFontScaling(i, this.smallLabel);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    public final void setTextColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.smallLabel.setTextColor(colorStateList);
            this.largeLabel.setTextColor(colorStateList);
        }
    }

    public final void updateActiveIndicatorLayoutParams(int i) {
        if (this.activeIndicatorView == null || i <= 0) {
            return;
        }
        int min =
                Math.min(
                        this.activeIndicatorDesiredWidth,
                        i - (this.activeIndicatorMarginHorizontal * 2));
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) this.activeIndicatorView.getLayoutParams();
        layoutParams.height = this.activeIndicatorDesiredHeight;
        layoutParams.width = min;
        this.activeIndicatorView.setLayoutParams(layoutParams);
    }
}
