package com.google.android.material.appbar;

import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.settings.widget.FloatingAppBarScrollingViewBehavior;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class HeaderScrollingViewBehavior extends ViewOffsetBehavior {
    public int overlayTop;
    public final Rect tempRect1;
    public final Rect tempRect2;
    public int verticalLayoutGap;

    public HeaderScrollingViewBehavior() {
        this.tempRect1 = new Rect();
        this.tempRect2 = new Rect();
        this.verticalLayoutGap = 0;
    }

    public final int getOverlapPixelsForOffset(View view) {
        int i;
        if (this.overlayTop == 0) {
            return 0;
        }
        float f = 0.0f;
        if (view instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            int totalScrollRange = appBarLayout.getTotalScrollRange();
            int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange();
            CoordinatorLayout.Behavior behavior =
                    ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).mBehavior;
            int topBottomOffsetForScrollingSibling =
                    behavior instanceof AppBarLayout.BaseBehavior
                            ? ((AppBarLayout.BaseBehavior) behavior)
                                    .getTopBottomOffsetForScrollingSibling()
                            : 0;
            if ((downNestedPreScrollRange == 0
                            || totalScrollRange + topBottomOffsetForScrollingSibling
                                    > downNestedPreScrollRange)
                    && (i = totalScrollRange - downNestedPreScrollRange) != 0) {
                f = (topBottomOffsetForScrollingSibling / i) + 1.0f;
            }
        }
        int i2 = this.overlayTop;
        return MathUtils.clamp((int) (f * i2), 0, i2);
    }

    @Override // com.google.android.material.appbar.ViewOffsetBehavior
    public final void layoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        AppBarLayout findFirstDependency =
                AppBarLayout.ScrollingViewBehavior.findFirstDependency(
                        coordinatorLayout.getDependencies(view));
        if (findFirstDependency == null) {
            coordinatorLayout.onLayoutChild(view, i);
            this.verticalLayoutGap = 0;
            return;
        }
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        Rect rect = this.tempRect1;
        rect.set(
                coordinatorLayout.getPaddingLeft()
                        + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin,
                findFirstDependency.getBottom()
                        + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin,
                (coordinatorLayout.getWidth() - coordinatorLayout.getPaddingRight())
                        - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin,
                ((findFirstDependency.getBottom() + coordinatorLayout.getHeight())
                                - coordinatorLayout.getPaddingBottom())
                        - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
        WindowInsetsCompat lastWindowInsets = coordinatorLayout.getLastWindowInsets();
        if (lastWindowInsets != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (coordinatorLayout.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
                rect.left = lastWindowInsets.getSystemWindowInsetLeft() + rect.left;
                rect.right -= lastWindowInsets.getSystemWindowInsetRight();
            }
        }
        Rect rect2 = this.tempRect2;
        int i2 = layoutParams.gravity;
        if (i2 == 0) {
            i2 = 8388659;
        }
        Gravity.apply(i2, view.getMeasuredWidth(), view.getMeasuredHeight(), rect, rect2, i);
        int overlapPixelsForOffset = getOverlapPixelsForOffset(findFirstDependency);
        view.layout(
                rect2.left,
                rect2.top - overlapPixelsForOffset,
                rect2.right,
                rect2.bottom - overlapPixelsForOffset);
        this.verticalLayoutGap = rect2.top - findFirstDependency.getBottom();
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onMeasureChild(
            CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
        AppBarLayout findFirstDependency;
        WindowInsetsCompat lastWindowInsets;
        int i4 = view.getLayoutParams().height;
        if ((i4 != -1 && i4 != -2)
                || (findFirstDependency =
                                AppBarLayout.ScrollingViewBehavior.findFirstDependency(
                                        coordinatorLayout.getDependencies(view)))
                        == null) {
            return false;
        }
        int size = View.MeasureSpec.getSize(i3);
        if (size > 0) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (findFirstDependency.getFitsSystemWindows()
                    && (lastWindowInsets = coordinatorLayout.getLastWindowInsets()) != null) {
                size +=
                        lastWindowInsets.getSystemWindowInsetBottom()
                                + lastWindowInsets.getSystemWindowInsetTop();
            }
        } else {
            size = coordinatorLayout.getHeight();
        }
        int totalScrollRange = findFirstDependency.getTotalScrollRange() + size;
        int measuredHeight = findFirstDependency.getMeasuredHeight();
        if (shouldHeaderOverlapScrollingChild()) {
            view.setTranslationY(-measuredHeight);
        } else {
            view.setTranslationY(0.0f);
            totalScrollRange -= measuredHeight;
        }
        coordinatorLayout.onMeasureChild(
                view,
                i,
                i2,
                View.MeasureSpec.makeMeasureSpec(
                        totalScrollRange >= 0 ? totalScrollRange : 0,
                        i4 == -1 ? 1073741824 : Integer.MIN_VALUE));
        return true;
    }

    public boolean shouldHeaderOverlapScrollingChild() {
        return this instanceof FloatingAppBarScrollingViewBehavior;
    }

    public HeaderScrollingViewBehavior(int i) {
        super(0);
        this.tempRect1 = new Rect();
        this.tempRect2 = new Rect();
        this.verticalLayoutGap = 0;
    }
}
