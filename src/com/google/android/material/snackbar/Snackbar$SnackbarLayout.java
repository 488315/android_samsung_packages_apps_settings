package com.google.android.material.snackbar;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.core.view.ViewCompat;

import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Snackbar$SnackbarLayout extends FrameLayout {
    public static final BaseTransientBottomBar$SnackbarBaseLayout$1 consumeAllTouchListener =
            new BaseTransientBottomBar$SnackbarBaseLayout$1();
    public ColorStateList backgroundTint;
    public PorterDuff.Mode backgroundTintMode;
    public final ShapeAppearanceModel shapeAppearanceModel;

    public Snackbar$SnackbarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        setBackgroundColor(R.color.transparent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api20Impl.requestApplyInsets(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        onMeasure$com$google$android$material$snackbar$BaseTransientBottomBar$SnackbarBaseLayout(
                i, i2);
        int childCount = getChildCount();
        int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getLayoutParams().width == -1) {
                childAt.measure(
                        View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824),
                        View.MeasureSpec.makeMeasureSpec(childAt.getMeasuredHeight(), 1073741824));
            }
            if (childAt.getLayoutParams().height == -2) {
                ViewGroup.MarginLayoutParams marginLayoutParams =
                        (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                if (childAt.getHeight()
                                + marginLayoutParams.topMargin
                                + marginLayoutParams.bottomMargin
                        > (getMeasuredHeight() - getPaddingBottom()) - getPaddingTop()) {
                    onMeasure$com$google$android$material$snackbar$BaseTransientBottomBar$SnackbarBaseLayout(
                            i, i2);
                }
            }
        }
    }

    public final void
            onMeasure$com$google$android$material$snackbar$BaseTransientBottomBar$SnackbarBaseLayout(
                    int i, int i2) {
        super.onMeasure(i, i2);
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public final void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        if (drawable != null && this.backgroundTint != null) {
            drawable = drawable.mutate();
            drawable.setTintList(this.backgroundTint);
            drawable.setTintMode(this.backgroundTintMode);
        }
        super.setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public final void setBackgroundTintList(ColorStateList colorStateList) {
        this.backgroundTint = colorStateList;
        if (getBackground() != null) {
            Drawable mutate = getBackground().mutate();
            mutate.setTintList(colorStateList);
            mutate.setTintMode(this.backgroundTintMode);
            if (mutate != getBackground()) {
                super.setBackgroundDrawable(mutate);
            }
        }
    }

    @Override // android.view.View
    public final void setBackgroundTintMode(PorterDuff.Mode mode) {
        this.backgroundTintMode = mode;
        if (getBackground() != null) {
            Drawable mutate = getBackground().mutate();
            mutate.setTintMode(mode);
            if (mutate != getBackground()) {
                super.setBackgroundDrawable(mutate);
            }
        }
    }

    @Override // android.view.View
    public final void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) layoutParams;
            new Rect(
                    marginLayoutParams.leftMargin,
                    marginLayoutParams.topMargin,
                    marginLayoutParams.rightMargin,
                    marginLayoutParams.bottomMargin);
        }
    }

    @Override // android.view.View
    public final void setOnClickListener(View.OnClickListener onClickListener) {
        setOnTouchListener(onClickListener != null ? null : consumeAllTouchListener);
        super.setOnClickListener(onClickListener);
    }

    public Snackbar$SnackbarLayout(Context context) {
        this(context, null, 0);
        setBackgroundColor(R.color.transparent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Snackbar$SnackbarLayout(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, 0, 0), attributeSet);
        GradientDrawable gradientDrawable;
        Context context2 = getContext();
        TypedArray obtainStyledAttributes =
                context2.obtainStyledAttributes(attributeSet, R$styleable.SnackbarLayout);
        if (obtainStyledAttributes.hasValue(6)) {
            float dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(6, 0);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.setElevation(this, dimensionPixelSize);
        }
        obtainStyledAttributes.getInt(2, 1);
        if (obtainStyledAttributes.hasValue(8) || obtainStyledAttributes.hasValue(9)) {
            this.shapeAppearanceModel =
                    ShapeAppearanceModel.builder(context2, attributeSet, 0, 0).build();
        }
        float f = obtainStyledAttributes.getFloat(3, 1.0f);
        setBackgroundTintList(
                MaterialResources.getColorStateList(context2, obtainStyledAttributes, 4));
        setBackgroundTintMode(
                ViewUtils.parseTintMode(
                        obtainStyledAttributes.getInt(5, -1), PorterDuff.Mode.SRC_IN));
        obtainStyledAttributes.getFloat(1, 1.0f);
        obtainStyledAttributes.getDimensionPixelSize(0, -1);
        obtainStyledAttributes.getDimensionPixelSize(7, -1);
        obtainStyledAttributes
                .getResources()
                .getDimensionPixelSize(
                        com.android.settings.R.dimen
                                .sesl_design_snackbar_suggest_transition_height);
        int i2 = BaseTransientBottomBar.$r8$clinit;
        obtainStyledAttributes.recycle();
        setOnTouchListener(consumeAllTouchListener);
        setFocusable(true);
        if (getBackground() == null) {
            int layer =
                    MaterialColors.layer(
                            MaterialColors.getColor(this, com.android.settings.R.attr.colorSurface),
                            MaterialColors.getColor(
                                    this, com.android.settings.R.attr.colorOnSurface),
                            f);
            ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearanceModel;
            if (shapeAppearanceModel != null) {
                MaterialShapeDrawable materialShapeDrawable =
                        new MaterialShapeDrawable(shapeAppearanceModel);
                materialShapeDrawable.setFillColor(ColorStateList.valueOf(layer));
                gradientDrawable = materialShapeDrawable;
            } else {
                float dimension =
                        getResources()
                                .getDimension(
                                        com.android.settings.R.dimen
                                                .mtrl_snackbar_background_corner_radius);
                GradientDrawable gradientDrawable2 = new GradientDrawable();
                gradientDrawable2.setShape(0);
                gradientDrawable2.setCornerRadius(dimension);
                gradientDrawable2.setColor(layer);
                gradientDrawable = gradientDrawable2;
            }
            ColorStateList colorStateList = this.backgroundTint;
            if (colorStateList != null) {
                gradientDrawable.setTintList(colorStateList);
            }
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            setBackgroundDrawable(gradientDrawable);
        }
    }
}
