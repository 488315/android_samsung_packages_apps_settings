package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import androidx.core.view.ViewCompat;

import com.android.settings.R;

import com.google.android.material.navigation.NavigationBarMenuView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BottomNavigationMenuView extends NavigationBarMenuView {
    public int activeItemMaxWidth;
    public final int activeItemMinWidth;
    public final int inactiveItemMaxWidth;
    public final int inactiveItemMinWidth;
    public boolean itemHorizontalTranslationEnabled;
    public boolean mHasIcon;
    public float mWidthPercent;
    public final List tempChildWidths;

    public BottomNavigationMenuView(Context context) {
        super(context);
        this.tempChildWidths = new ArrayList();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        setLayoutParams(layoutParams);
        Resources resources = getResources();
        TypedValue typedValue = new TypedValue();
        resources.getValue(R.dimen.sesl_bottom_navigation_width_proportion, typedValue, true);
        this.mWidthPercent = typedValue.getFloat();
        this.inactiveItemMaxWidth =
                resources.getDimensionPixelSize(R.dimen.sesl_bottom_navigation_item_max_width);
        this.inactiveItemMinWidth =
                resources.getDimensionPixelSize(R.dimen.sesl_bottom_navigation_item_min_width);
        this.activeItemMaxWidth =
                (int) (getResources().getDisplayMetrics().widthPixels * this.mWidthPercent);
        this.activeItemMinWidth =
                resources.getDimensionPixelSize(
                        R.dimen.sesl_bottom_navigation_active_item_min_width);
        resources.getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_mode_height);
        this.mUseItemPool = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int dimensionPixelSize =
                this.mHasIcon
                        ? this.mViewVisibleItemCount == 5
                                ? getResources()
                                        .getDimensionPixelSize(
                                                R.dimen
                                                        .sesl_bottom_navigation_icon_mode_min_padding_horizontal)
                                : getResources()
                                        .getDimensionPixelSize(
                                                R.dimen
                                                        .sesl_bottom_navigation_icon_mode_padding_horizontal)
                        : 0;
        int i7 = 0;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (getLayoutDirection() == 1) {
                    int i9 = i5 - i7;
                    childAt.layout(
                            (i9 - childAt.getMeasuredWidth()) + dimensionPixelSize,
                            0,
                            i9 - dimensionPixelSize,
                            i6);
                } else {
                    childAt.layout(
                            i7 + dimensionPixelSize,
                            0,
                            (childAt.getMeasuredWidth() + i7) - dimensionPixelSize,
                            i6);
                }
                i7 += childAt.getMeasuredWidth();
            }
        }
        updateBadgeIfNeeded();
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        int i4;
        if (View.MeasureSpec.getSize(i) / getResources().getDisplayMetrics().density < 590.0f) {
            this.mWidthPercent = 1.0f;
        } else {
            this.mWidthPercent = 0.75f;
        }
        this.activeItemMaxWidth =
                (int) (getResources().getDisplayMetrics().widthPixels * this.mWidthPercent);
        int size = (int) (View.MeasureSpec.getSize(i) * this.mWidthPercent);
        int i5 = 0;
        for (int i6 = 0; i6 < getChildCount(); i6++) {
            if (getChildAt(i6).getVisibility() == 0) {
                i5++;
            }
        }
        int childCount = getChildCount();
        ((ArrayList) this.tempChildWidths).clear();
        this.mHasIcon = this.mViewType != 3;
        getResources()
                .getDimensionPixelSize(
                        this.mHasIcon
                                ? R.dimen.sesl_bottom_navigation_icon_mode_height
                                : R.dimen.sesl_bottom_navigation_text_mode_height);
        int size2 = View.MeasureSpec.getSize(i2);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size2, 1073741824);
        if ((this.labelVisibilityMode == 0) && this.itemHorizontalTranslationEnabled) {
            View childAt = getChildAt(this.selectedItemPosition);
            int i7 = this.activeItemMinWidth;
            if (childAt.getVisibility() != 8) {
                childAt.measure(
                        View.MeasureSpec.makeMeasureSpec(
                                this.activeItemMaxWidth, Integer.MIN_VALUE),
                        makeMeasureSpec);
                i7 = Math.max(i7, childAt.getMeasuredWidth());
            }
            int i8 = childCount - (childAt.getVisibility() != 8 ? 1 : 0);
            int min =
                    Math.min(
                            size - (this.inactiveItemMinWidth * i8),
                            Math.min(i7, this.activeItemMaxWidth));
            int i9 = size - min;
            int min2 = Math.min(i9 / (i8 != 0 ? i8 : 1), this.inactiveItemMaxWidth);
            int i10 = i9 - (i8 * min2);
            int i11 = 0;
            while (i11 < childCount) {
                if (getChildAt(i11).getVisibility() != 8) {
                    i4 = i11 == this.selectedItemPosition ? min : min2;
                    if (i10 > 0) {
                        i4++;
                        i10--;
                    }
                } else {
                    i4 = 0;
                }
                ((ArrayList) this.tempChildWidths).add(Integer.valueOf(i4));
                i11++;
            }
        } else {
            int i12 = size / (i5 != 0 ? i5 : 1);
            if (i5 != 2) {
                i12 = Math.min(i12, this.activeItemMaxWidth);
            }
            int i13 = size - (i5 * i12);
            for (int i14 = 0; i14 < childCount; i14++) {
                if (getChildAt(i14).getVisibility() == 8) {
                    i3 = 0;
                } else if (i13 > 0) {
                    i3 = i12 + 1;
                    i13--;
                } else {
                    i3 = i12;
                }
                ((ArrayList) this.tempChildWidths).add(Integer.valueOf(i3));
            }
        }
        int i15 = 0;
        for (int i16 = 0; i16 < childCount; i16++) {
            View childAt2 = getChildAt(i16);
            if (childAt2 != null && childAt2.getVisibility() != 8) {
                childAt2.measure(
                        View.MeasureSpec.makeMeasureSpec(
                                ((Integer) ((ArrayList) this.tempChildWidths).get(i16)).intValue(),
                                1073741824),
                        makeMeasureSpec);
                childAt2.getLayoutParams().width = childAt2.getMeasuredWidth();
                i15 = childAt2.getMeasuredWidth() + i15;
            }
        }
        setMeasuredDimension(i15, size2);
    }
}
