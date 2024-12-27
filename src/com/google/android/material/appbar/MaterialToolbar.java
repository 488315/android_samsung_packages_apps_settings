package com.google.android.material.appbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class MaterialToolbar extends Toolbar {
    public static final ImageView.ScaleType[] LOGO_SCALE_TYPE_ARRAY = {
        ImageView.ScaleType.MATRIX,
        ImageView.ScaleType.FIT_XY,
        ImageView.ScaleType.FIT_START,
        ImageView.ScaleType.FIT_CENTER,
        ImageView.ScaleType.FIT_END,
        ImageView.ScaleType.CENTER,
        ImageView.ScaleType.CENTER_CROP,
        ImageView.ScaleType.CENTER_INSIDE
    };
    public final Boolean logoAdjustViewBounds;
    public final ImageView.ScaleType logoScaleType;
    public final Integer navigationIconTint;
    public final boolean subtitleCentered;
    public final boolean titleCentered;

    public MaterialToolbar(Context context) {
        this(context, null);
    }

    @Override // androidx.appcompat.widget.Toolbar
    public final void inflateMenu(int i) {
        MenuBuilder menu = getMenu();
        boolean z = menu instanceof MenuBuilder;
        if (z) {
            menu.stopDispatchingItemsChanged();
        }
        super.inflateMenu(i);
        if (z) {
            menu.startDispatchingItemsChanged();
        }
    }

    public final void layoutTitleCenteredHorizontally(View view, Pair pair) {
        int measuredWidth = getMeasuredWidth();
        int measuredWidth2 = view.getMeasuredWidth();
        int i = (measuredWidth / 2) - (measuredWidth2 / 2);
        int i2 = measuredWidth2 + i;
        int max =
                Math.max(
                        Math.max(((Integer) pair.first).intValue() - i, 0),
                        Math.max(i2 - ((Integer) pair.second).intValue(), 0));
        if (max > 0) {
            i += max;
            i2 -= max;
            view.measure(
                    View.MeasureSpec.makeMeasureSpec(i2 - i, 1073741824),
                    view.getMeasuredHeightAndState());
        }
        view.layout(i, view.getTop(), i2, view.getBottom());
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        ImageView imageView;
        Drawable drawable;
        super.onLayout(z, i, i2, i3, i4);
        int i5 = 0;
        ImageView imageView2 = null;
        if (this.titleCentered || this.subtitleCentered) {
            ToolbarUtils.AnonymousClass1 anonymousClass1 = ToolbarUtils.VIEW_TOP_COMPARATOR;
            List textViewsWithText = ToolbarUtils.getTextViewsWithText(this, this.mTitleText);
            TextView textView =
                    ((ArrayList) textViewsWithText).isEmpty()
                            ? null
                            : (TextView)
                                    Collections.min(
                                            textViewsWithText, ToolbarUtils.VIEW_TOP_COMPARATOR);
            List textViewsWithText2 = ToolbarUtils.getTextViewsWithText(this, this.mSubtitleText);
            TextView textView2 =
                    ((ArrayList) textViewsWithText2).isEmpty()
                            ? null
                            : (TextView)
                                    Collections.max(
                                            textViewsWithText2, ToolbarUtils.VIEW_TOP_COMPARATOR);
            if (textView != null || textView2 != null) {
                int measuredWidth = getMeasuredWidth();
                int i6 = measuredWidth / 2;
                int paddingLeft = getPaddingLeft();
                int paddingRight = measuredWidth - getPaddingRight();
                for (int i7 = 0; i7 < getChildCount(); i7++) {
                    View childAt = getChildAt(i7);
                    if (childAt.getVisibility() != 8
                            && childAt != textView
                            && childAt != textView2) {
                        if (childAt.getRight() < i6 && childAt.getRight() > paddingLeft) {
                            paddingLeft = childAt.getRight();
                        }
                        if (childAt.getLeft() > i6 && childAt.getLeft() < paddingRight) {
                            paddingRight = childAt.getLeft();
                        }
                    }
                }
                Pair pair = new Pair(Integer.valueOf(paddingLeft), Integer.valueOf(paddingRight));
                if (this.titleCentered && textView != null) {
                    layoutTitleCenteredHorizontally(textView, pair);
                }
                if (this.subtitleCentered && textView2 != null) {
                    layoutTitleCenteredHorizontally(textView2, pair);
                }
            }
        }
        ToolbarUtils.AnonymousClass1 anonymousClass12 = ToolbarUtils.VIEW_TOP_COMPARATOR;
        AppCompatImageView appCompatImageView = this.mLogoView;
        Drawable drawable2 = appCompatImageView != null ? appCompatImageView.getDrawable() : null;
        if (drawable2 != null) {
            while (true) {
                if (i5 >= getChildCount()) {
                    break;
                }
                View childAt2 = getChildAt(i5);
                if ((childAt2 instanceof ImageView)
                        && (drawable = (imageView = (ImageView) childAt2).getDrawable()) != null
                        && drawable.getConstantState() != null
                        && drawable.getConstantState().equals(drawable2.getConstantState())) {
                    imageView2 = imageView;
                    break;
                }
                i5++;
            }
        }
        if (imageView2 != null) {
            Boolean bool = this.logoAdjustViewBounds;
            if (bool != null) {
                imageView2.setAdjustViewBounds(bool.booleanValue());
            }
            ImageView.ScaleType scaleType = this.logoScaleType;
            if (scaleType != null) {
                imageView2.setScaleType(scaleType);
            }
        }
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        MaterialShapeUtils.setElevation(this, f);
    }

    @Override // androidx.appcompat.widget.Toolbar
    public final void setNavigationIcon(Drawable drawable) {
        if (drawable != null && this.navigationIconTint != null) {
            drawable = drawable.mutate();
            drawable.setTint(this.navigationIconTint.intValue());
        }
        super.setNavigationIcon(drawable);
    }

    public MaterialToolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.toolbarStyle);
    }

    public MaterialToolbar(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132084972), attributeSet, i);
        ColorStateList colorStateListOrNull;
        Context context2 = getContext();
        TypedArray obtainStyledAttributes =
                ThemeEnforcement.obtainStyledAttributes(
                        context2,
                        attributeSet,
                        R$styleable.MaterialToolbar,
                        i,
                        2132084972,
                        new int[0]);
        if (obtainStyledAttributes.hasValue(2)) {
            this.navigationIconTint = Integer.valueOf(obtainStyledAttributes.getColor(2, -1));
            AppCompatImageButton appCompatImageButton = this.mNavButtonView;
            Drawable drawable =
                    appCompatImageButton != null ? appCompatImageButton.getDrawable() : null;
            if (drawable != null) {
                setNavigationIcon(drawable);
            }
        }
        this.titleCentered = obtainStyledAttributes.getBoolean(4, false);
        this.subtitleCentered = obtainStyledAttributes.getBoolean(3, false);
        int i2 = obtainStyledAttributes.getInt(1, -1);
        if (i2 >= 0) {
            ImageView.ScaleType[] scaleTypeArr = LOGO_SCALE_TYPE_ARRAY;
            if (i2 < scaleTypeArr.length) {
                this.logoScaleType = scaleTypeArr[i2];
            }
        }
        if (obtainStyledAttributes.hasValue(0)) {
            this.logoAdjustViewBounds =
                    Boolean.valueOf(obtainStyledAttributes.getBoolean(0, false));
        }
        obtainStyledAttributes.recycle();
        Drawable background = getBackground();
        if (background == null) {
            colorStateListOrNull = ColorStateList.valueOf(0);
        } else {
            colorStateListOrNull = DrawableUtils.getColorStateListOrNull(background);
        }
        if (colorStateListOrNull != null) {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
            materialShapeDrawable.setFillColor(colorStateListOrNull);
            materialShapeDrawable.initializeElevationOverlay(context2);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            materialShapeDrawable.setElevation(ViewCompat.Api21Impl.getElevation(this));
            setBackground(materialShapeDrawable);
        }
    }
}
