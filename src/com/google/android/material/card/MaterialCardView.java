package com.google.android.material.card;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.FrameLayout;

import androidx.cardview.widget.CardView;
import androidx.cardview.widget.RoundRectDrawable;
import androidx.cardview.widget.RoundRectDrawableWithShadow;
import androidx.core.view.ViewCompat;

import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class MaterialCardView extends CardView implements Checkable, Shapeable {
    public static final int[] CHECKABLE_STATE_SET = {R.attr.state_checkable};
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public final MaterialCardViewHelper cardViewHelper;
    public boolean checked;
    public final boolean isParentCardViewDoneInitializing;

    public MaterialCardView(Context context) {
        this(context, null);
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.checked;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.cardViewHelper.updateClickable();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.cardViewHelper.bgDrawable);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 3);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        if (materialCardViewHelper != null && materialCardViewHelper.checkable) {
            FrameLayout.mergeDrawableStates(onCreateDrawableState, CHECKABLE_STATE_SET);
        }
        if (this.checked) {
            FrameLayout.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("androidx.cardview.widget.CardView");
        accessibilityEvent.setChecked(this.checked);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(
            AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.cardview.widget.CardView");
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        accessibilityNodeInfo.setCheckable(
                materialCardViewHelper != null && materialCardViewHelper.checkable);
        accessibilityNodeInfo.setClickable(isClickable());
        accessibilityNodeInfo.setChecked(this.checked);
    }

    @Override // androidx.cardview.widget.CardView, android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        super.onMeasure(i, i2);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (materialCardViewHelper.clickableForegroundDrawable != null) {
            MaterialCardView materialCardView = materialCardViewHelper.materialCardView;
            if (materialCardView.mCompatPadding) {
                i3 =
                        (int)
                                Math.ceil(
                                        ((((RoundRectDrawable)
                                                                                materialCardView
                                                                                        .mCardViewDelegate
                                                                                        .mCardBackground)
                                                                        .mPadding
                                                                * 1.5f)
                                                        + (materialCardViewHelper
                                                                        .shouldAddCornerPaddingOutsideCardBackground()
                                                                ? materialCardViewHelper
                                                                        .calculateActualCornerPadding()
                                                                : 0.0f))
                                                * 2.0f);
                i4 =
                        (int)
                                Math.ceil(
                                        (((RoundRectDrawable)
                                                                        materialCardView
                                                                                .mCardViewDelegate
                                                                                .mCardBackground)
                                                                .mPadding
                                                        + (materialCardViewHelper
                                                                        .shouldAddCornerPaddingOutsideCardBackground()
                                                                ? materialCardViewHelper
                                                                        .calculateActualCornerPadding()
                                                                : 0.0f))
                                                * 2.0f);
            } else {
                i3 = 0;
                i4 = 0;
            }
            int i7 = materialCardViewHelper.checkedIconGravity;
            int i8 =
                    (i7 & 8388613) == 8388613
                            ? ((measuredWidth - materialCardViewHelper.checkedIconMargin)
                                            - materialCardViewHelper.checkedIconSize)
                                    - i4
                            : materialCardViewHelper.checkedIconMargin;
            int i9 =
                    (i7 & 80) == 80
                            ? materialCardViewHelper.checkedIconMargin
                            : ((measuredHeight - materialCardViewHelper.checkedIconMargin)
                                            - materialCardViewHelper.checkedIconSize)
                                    - i3;
            int i10 =
                    (i7 & 8388613) == 8388613
                            ? materialCardViewHelper.checkedIconMargin
                            : ((measuredWidth - materialCardViewHelper.checkedIconMargin)
                                            - materialCardViewHelper.checkedIconSize)
                                    - i4;
            int i11 =
                    (i7 & 80) == 80
                            ? ((measuredHeight - materialCardViewHelper.checkedIconMargin)
                                            - materialCardViewHelper.checkedIconSize)
                                    - i3
                            : materialCardViewHelper.checkedIconMargin;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (materialCardView.getLayoutDirection() == 1) {
                i6 = i10;
                i5 = i8;
            } else {
                i5 = i10;
                i6 = i8;
            }
            materialCardViewHelper.clickableForegroundDrawable.setLayerInset(2, i6, i11, i5, i9);
        }
    }

    @Override // android.view.View
    public final void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        if (this.isParentCardViewDoneInitializing) {
            if (!this.cardViewHelper.isBackgroundOverwritten) {
                Log.i("MaterialCardView", "Setting a custom background is not supported.");
                this.cardViewHelper.isBackgroundOverwritten = true;
            }
            super.setBackgroundDrawable(drawable);
        }
    }

    @Override // android.widget.Checkable
    public final void setChecked(boolean z) {
        if (this.checked != z) {
            toggle();
        }
    }

    @Override // android.view.View
    public final void setClickable(boolean z) {
        super.setClickable(z);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        if (materialCardViewHelper != null) {
            materialCardViewHelper.updateClickable();
        }
    }

    @Override // com.google.android.material.shape.Shapeable
    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        RectF rectF = new RectF();
        rectF.set(this.cardViewHelper.bgDrawable.getBounds());
        setClipToOutline(shapeAppearanceModel.isRoundRect(rectF));
        this.cardViewHelper.setShapeAppearanceModel(shapeAppearanceModel);
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        if (materialCardViewHelper != null && materialCardViewHelper.checkable && isEnabled()) {
            this.checked = !this.checked;
            refreshDrawableState();
            MaterialCardViewHelper materialCardViewHelper2 = this.cardViewHelper;
            Drawable drawable = materialCardViewHelper2.rippleDrawable;
            if (drawable != null) {
                Rect bounds = drawable.getBounds();
                int i = bounds.bottom;
                materialCardViewHelper2.rippleDrawable.setBounds(
                        bounds.left, bounds.top, bounds.right, i - 1);
                materialCardViewHelper2.rippleDrawable.setBounds(
                        bounds.left, bounds.top, bounds.right, i);
            }
            this.cardViewHelper.setChecked(this.checked, true);
        }
    }

    public MaterialCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.settings.R.attr.materialCardViewStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v6, types: [android.graphics.drawable.Drawable] */
    public MaterialCardView(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132084883), attributeSet, i);
        this.checked = false;
        this.isParentCardViewDoneInitializing = true;
        TypedArray obtainStyledAttributes =
                ThemeEnforcement.obtainStyledAttributes(
                        getContext(),
                        attributeSet,
                        R$styleable.MaterialCardView,
                        i,
                        2132084883,
                        new int[0]);
        MaterialCardViewHelper materialCardViewHelper =
                new MaterialCardViewHelper(this, attributeSet, i);
        this.cardViewHelper = materialCardViewHelper;
        ColorStateList colorStateList =
                ((RoundRectDrawable) this.mCardViewDelegate.mCardBackground).mBackground;
        MaterialShapeDrawable materialShapeDrawable = materialCardViewHelper.bgDrawable;
        materialShapeDrawable.setFillColor(colorStateList);
        Rect rect = this.mContentPadding;
        materialCardViewHelper.userContentPadding.set(rect.left, rect.top, rect.right, rect.bottom);
        MaterialCardView materialCardView = materialCardViewHelper.materialCardView;
        float f = 0.0f;
        float calculateActualCornerPadding =
                ((!materialCardView.mPreventCornerOverlap || materialShapeDrawable.isRoundRect())
                                && !materialCardViewHelper
                                        .shouldAddCornerPaddingOutsideCardBackground())
                        ? 0.0f
                        : materialCardViewHelper.calculateActualCornerPadding();
        if (materialCardView.mPreventCornerOverlap && materialCardView.mCompatPadding) {
            f =
                    (float)
                            ((1.0d - MaterialCardViewHelper.COS_45)
                                    * ((RoundRectDrawable)
                                                    materialCardView
                                                            .mCardViewDelegate
                                                            .mCardBackground)
                                            .mRadius);
        }
        int i2 = (int) (calculateActualCornerPadding - f);
        Rect rect2 = materialCardViewHelper.userContentPadding;
        materialCardView.mContentPadding.set(
                rect2.left + i2, rect2.top + i2, rect2.right + i2, rect2.bottom + i2);
        CardView.AnonymousClass1 anonymousClass1 = materialCardView.mCardViewDelegate;
        if (!CardView.this.mCompatPadding) {
            anonymousClass1.setShadowPadding(0, 0, 0, 0);
        } else {
            RoundRectDrawable roundRectDrawable =
                    (RoundRectDrawable) anonymousClass1.mCardBackground;
            float f2 = roundRectDrawable.mPadding;
            float f3 = roundRectDrawable.mRadius;
            int ceil =
                    (int)
                            Math.ceil(
                                    RoundRectDrawableWithShadow.calculateHorizontalPadding(
                                            f2, f3, r3.mPreventCornerOverlap));
            int ceil2 =
                    (int)
                            Math.ceil(
                                    RoundRectDrawableWithShadow.calculateVerticalPadding(
                                            f2, f3, CardView.this.mPreventCornerOverlap));
            anonymousClass1.setShadowPadding(ceil, ceil2, ceil, ceil2);
        }
        ColorStateList colorStateList2 =
                MaterialResources.getColorStateList(
                        materialCardView.getContext(), obtainStyledAttributes, 11);
        materialCardViewHelper.strokeColor = colorStateList2;
        if (colorStateList2 == null) {
            materialCardViewHelper.strokeColor = ColorStateList.valueOf(-1);
        }
        materialCardViewHelper.strokeWidth = obtainStyledAttributes.getDimensionPixelSize(12, 0);
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        materialCardViewHelper.checkable = z;
        materialCardView.setLongClickable(z);
        materialCardViewHelper.checkedIconTint =
                MaterialResources.getColorStateList(
                        materialCardView.getContext(), obtainStyledAttributes, 6);
        Drawable drawable =
                MaterialResources.getDrawable(
                        materialCardView.getContext(), obtainStyledAttributes, 2);
        if (drawable != null) {
            Drawable mutate = drawable.mutate();
            materialCardViewHelper.checkedIcon = mutate;
            mutate.setTintList(materialCardViewHelper.checkedIconTint);
            materialCardViewHelper.setChecked(materialCardView.checked, false);
        } else {
            materialCardViewHelper.checkedIcon = null;
        }
        LayerDrawable layerDrawable = materialCardViewHelper.clickableForegroundDrawable;
        if (layerDrawable != null) {
            layerDrawable.setDrawableByLayerId(
                    com.android.settings.R.id.mtrl_card_checked_layer_id,
                    materialCardViewHelper.checkedIcon);
        }
        materialCardViewHelper.checkedIconSize = obtainStyledAttributes.getDimensionPixelSize(5, 0);
        materialCardViewHelper.checkedIconMargin =
                obtainStyledAttributes.getDimensionPixelSize(4, 0);
        materialCardViewHelper.checkedIconGravity = obtainStyledAttributes.getInteger(3, 8388661);
        ColorStateList colorStateList3 =
                MaterialResources.getColorStateList(
                        materialCardView.getContext(), obtainStyledAttributes, 7);
        materialCardViewHelper.rippleColor = colorStateList3;
        if (colorStateList3 == null) {
            materialCardViewHelper.rippleColor =
                    ColorStateList.valueOf(
                            MaterialColors.getColor(
                                    materialCardView,
                                    com.android.settings.R.attr.colorControlHighlight));
        }
        ColorStateList colorStateList4 =
                MaterialResources.getColorStateList(
                        materialCardView.getContext(), obtainStyledAttributes, 1);
        colorStateList4 = colorStateList4 == null ? ColorStateList.valueOf(0) : colorStateList4;
        MaterialShapeDrawable materialShapeDrawable2 =
                materialCardViewHelper.foregroundContentDrawable;
        materialShapeDrawable2.setFillColor(colorStateList4);
        Drawable drawable2 = materialCardViewHelper.rippleDrawable;
        if (drawable2 != null) {
            ((RippleDrawable) drawable2).setColor(materialCardViewHelper.rippleColor);
        }
        materialShapeDrawable.setElevation(CardView.this.getElevation());
        float f4 = materialCardViewHelper.strokeWidth;
        ColorStateList colorStateList5 = materialCardViewHelper.strokeColor;
        materialShapeDrawable2.drawableState.strokeWidth = f4;
        materialShapeDrawable2.invalidateSelf();
        MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState =
                materialShapeDrawable2.drawableState;
        if (materialShapeDrawableState.strokeColor != colorStateList5) {
            materialShapeDrawableState.strokeColor = colorStateList5;
            materialShapeDrawable2.onStateChange(materialShapeDrawable2.getState());
        }
        super.setBackgroundDrawable(materialCardViewHelper.insetDrawable(materialShapeDrawable));
        MaterialShapeDrawable clickableForeground =
                materialCardViewHelper.shouldUseClickableForeground()
                        ? materialCardViewHelper.getClickableForeground()
                        : materialShapeDrawable2;
        materialCardViewHelper.fgDrawable = clickableForeground;
        materialCardView.setForeground(materialCardViewHelper.insetDrawable(clickableForeground));
        obtainStyledAttributes.recycle();
    }
}
