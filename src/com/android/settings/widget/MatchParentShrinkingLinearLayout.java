package com.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;

import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import com.android.internal.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MatchParentShrinkingLinearLayout extends ViewGroup {
    public boolean mBaselineAligned;
    public int mBaselineAlignedChildIndex;
    public int mBaselineChildTop;
    public Drawable mDivider;
    public int mDividerHeight;
    public int mDividerPadding;
    public int mDividerWidth;
    public int mGravity;
    public int mLayoutDirection;
    public int mOrientation;
    public int mShowDividers;
    public int mTotalLength;
    public boolean mUseLargestChild;
    public float mWeightSum;

    public MatchParentShrinkingLinearLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public final void drawHorizontalDivider(int i, Canvas canvas) {
        this.mDivider.setBounds(
                getPaddingLeft() + this.mDividerPadding,
                i,
                (getWidth() - getPaddingRight()) - this.mDividerPadding,
                this.mDividerHeight + i);
        this.mDivider.draw(canvas);
    }

    public final void drawVerticalDivider(int i, Canvas canvas) {
        this.mDivider.setBounds(
                i,
                getPaddingTop() + this.mDividerPadding,
                this.mDividerWidth + i,
                (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    public final void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("layout:baselineAligned", this.mBaselineAligned);
        viewHierarchyEncoder.addProperty(
                "layout:baselineAlignedChildIndex", this.mBaselineAlignedChildIndex);
        viewHierarchyEncoder.addProperty("measurement:baselineChildTop", this.mBaselineChildTop);
        viewHierarchyEncoder.addProperty("measurement:orientation", this.mOrientation);
        viewHierarchyEncoder.addProperty("measurement:gravity", this.mGravity);
        viewHierarchyEncoder.addProperty("measurement:totalLength", this.mTotalLength);
        viewHierarchyEncoder.addProperty("layout:totalLength", this.mTotalLength);
        viewHierarchyEncoder.addProperty("layout:useLargestChild", this.mUseLargestChild);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        int i = this.mOrientation;
        if (i == 0) {
            return new LayoutParams(-2);
        }
        if (i == 1) {
            return new LayoutParams(-1);
        }
        return null;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return MatchParentShrinkingLinearLayout.class.getName();
    }

    @Override // android.view.View
    public int getBaseline() {
        int i;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i2 = this.mBaselineAlignedChildIndex;
        if (childCount <= i2) {
            throw new RuntimeException(
                    "mBaselineAlignedChildIndex of LinearLayout set to an index that is out of"
                        + " bounds.");
        }
        View childAt = getChildAt(i2);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            }
            throw new RuntimeException(
                    "mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know"
                        + " how to get its baseline.");
        }
        int i3 = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (i = this.mGravity & 112) != 48) {
            if (i == 16) {
                i3 =
                        AbsActionBarView$$ExternalSyntheticOutline0.m(
                                ((((ViewGroup) this).mBottom - ((ViewGroup) this).mTop)
                                                - ((ViewGroup) this).mPaddingTop)
                                        - ((ViewGroup) this).mPaddingBottom,
                                this.mTotalLength,
                                2,
                                i3);
            } else if (i == 80) {
                i3 =
                        ((((ViewGroup) this).mBottom - ((ViewGroup) this).mTop)
                                        - ((ViewGroup) this).mPaddingBottom)
                                - this.mTotalLength;
            }
        }
        return i3
                + ((ViewGroup.MarginLayoutParams) ((LayoutParams) childAt.getLayoutParams()))
                        .topMargin
                + baseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    public final boolean hasDividerBeforeChildAt(int i) {
        if (i == 0) {
            return (this.mShowDividers & 1) != 0;
        }
        if (i == getChildCount()) {
            return (this.mShowDividers & 4) != 0;
        }
        if ((this.mShowDividers & 2) == 0) {
            return false;
        }
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (getChildAt(i2).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int right;
        int left;
        int i;
        if (this.mDivider == null) {
            return;
        }
        int i2 = 0;
        if (this.mOrientation == 1) {
            int virtualChildCount = getVirtualChildCount();
            while (i2 < virtualChildCount) {
                View childAt = getChildAt(i2);
                if (childAt != null
                        && childAt.getVisibility() != 8
                        && hasDividerBeforeChildAt(i2)) {
                    drawHorizontalDivider(
                            (childAt.getTop()
                                            - ((ViewGroup.MarginLayoutParams)
                                                            ((LayoutParams)
                                                                    childAt.getLayoutParams()))
                                                    .topMargin)
                                    - this.mDividerHeight,
                            canvas);
                }
                i2++;
            }
            if (hasDividerBeforeChildAt(virtualChildCount)) {
                View childAt2 = getChildAt(virtualChildCount - 1);
                drawHorizontalDivider(
                        childAt2 == null
                                ? (getHeight() - getPaddingBottom()) - this.mDividerHeight
                                : childAt2.getBottom()
                                        + ((ViewGroup.MarginLayoutParams)
                                                        ((LayoutParams) childAt2.getLayoutParams()))
                                                .bottomMargin,
                        canvas);
                return;
            }
            return;
        }
        int virtualChildCount2 = getVirtualChildCount();
        boolean isLayoutRtl = isLayoutRtl();
        while (i2 < virtualChildCount2) {
            View childAt3 = getChildAt(i2);
            if (childAt3 != null && childAt3.getVisibility() != 8 && hasDividerBeforeChildAt(i2)) {
                LayoutParams layoutParams = (LayoutParams) childAt3.getLayoutParams();
                drawVerticalDivider(
                        isLayoutRtl
                                ? childAt3.getRight()
                                        + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin
                                : (childAt3.getLeft()
                                                - ((ViewGroup.MarginLayoutParams) layoutParams)
                                                        .leftMargin)
                                        - this.mDividerWidth,
                        canvas);
            }
            i2++;
        }
        if (hasDividerBeforeChildAt(virtualChildCount2)) {
            View childAt4 = getChildAt(virtualChildCount2 - 1);
            if (childAt4 != null) {
                LayoutParams layoutParams2 = (LayoutParams) childAt4.getLayoutParams();
                if (isLayoutRtl) {
                    left =
                            childAt4.getLeft()
                                    - ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin;
                    i = this.mDividerWidth;
                    right = left - i;
                } else {
                    right =
                            childAt4.getRight()
                                    + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin;
                }
            } else if (isLayoutRtl) {
                right = getPaddingLeft();
            } else {
                left = getWidth() - getPaddingRight();
                i = this.mDividerWidth;
                right = left - i;
            }
            drawVerticalDivider(right, canvas);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0156  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onLayout(boolean r18, int r19, int r20, int r21, int r22) {
        /*
            Method dump skipped, instructions count: 393
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.widget.MatchParentShrinkingLinearLayout.onLayout(boolean,"
                    + " int, int, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00a6  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMeasure(int r30, int r31) {
        /*
            Method dump skipped, instructions count: 939
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.widget.MatchParentShrinkingLinearLayout.onMeasure(int,"
                    + " int):void");
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (i != this.mLayoutDirection) {
            this.mLayoutDirection = i;
            if (this.mOrientation == 0) {
                requestLayout();
            }
        }
    }

    @RemotableViewMethod
    public void setBaselineAligned(boolean z) {
        this.mBaselineAligned = z;
    }

    @RemotableViewMethod
    public void setBaselineAlignedChildIndex(int i) {
        if (i >= 0 && i < getChildCount()) {
            this.mBaselineAlignedChildIndex = i;
            return;
        }
        throw new IllegalArgumentException(
                "base aligned child index out of range (0, " + getChildCount() + ")");
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.mDivider) {
            return;
        }
        this.mDivider = drawable;
        if (drawable != null) {
            this.mDividerWidth = drawable.getIntrinsicWidth();
            this.mDividerHeight = drawable.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        setWillNotDraw(drawable == null);
        requestLayout();
    }

    public void setDividerPadding(int i) {
        this.mDividerPadding = i;
    }

    @RemotableViewMethod
    public void setGravity(int i) {
        if (this.mGravity != i) {
            if ((8388615 & i) == 0) {
                i |= 8388611;
            }
            if ((i & 112) == 0) {
                i |= 48;
            }
            this.mGravity = i;
            requestLayout();
        }
    }

    @RemotableViewMethod
    public void setHorizontalGravity(int i) {
        int i2 = i & 8388615;
        int i3 = this.mGravity;
        if ((8388615 & i3) != i2) {
            this.mGravity = i2 | ((-8388616) & i3);
            requestLayout();
        }
    }

    @RemotableViewMethod
    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.mUseLargestChild = z;
    }

    public void setOrientation(int i) {
        if (this.mOrientation != i) {
            this.mOrientation = i;
            requestLayout();
        }
    }

    public void setShowDividers(int i) {
        if (i != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = i;
    }

    @RemotableViewMethod
    public void setVerticalGravity(int i) {
        int i2 = i & 112;
        int i3 = this.mGravity;
        if ((i3 & 112) != i2) {
            this.mGravity = i2 | (i3 & (-113));
            requestLayout();
        }
    }

    @RemotableViewMethod
    public void setWeightSum(float f) {
        this.mWeightSum = Math.max(0.0f, f);
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    public MatchParentShrinkingLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        LayoutParams layoutParams2 = new LayoutParams(layoutParams);
        layoutParams2.gravity = -1;
        return layoutParams2;
    }

    public MatchParentShrinkingLinearLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MatchParentShrinkingLinearLayout(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        this.mLayoutDirection = -1;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayout, i, i2);
        int i3 = obtainStyledAttributes.getInt(1, -1);
        if (i3 >= 0) {
            setOrientation(i3);
        }
        int i4 = obtainStyledAttributes.getInt(0, -1);
        if (i4 >= 0) {
            setGravity(i4);
        }
        boolean z = obtainStyledAttributes.getBoolean(2, true);
        if (!z) {
            setBaselineAligned(z);
        }
        this.mWeightSum = obtainStyledAttributes.getFloat(4, -1.0f);
        this.mBaselineAlignedChildIndex = obtainStyledAttributes.getInt(3, -1);
        this.mUseLargestChild = obtainStyledAttributes.getBoolean(6, false);
        setDividerDrawable(obtainStyledAttributes.getDrawable(5));
        this.mShowDividers = obtainStyledAttributes.getInt(7, 0);
        this.mDividerPadding = obtainStyledAttributes.getDimensionPixelSize(8, 0);
        obtainStyledAttributes.recycle();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int gravity;
        public final float weight;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = -1;
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayout_Layout);
            this.weight = obtainStyledAttributes.getFloat(3, 0.0f);
            this.gravity = obtainStyledAttributes.getInt(0, -1);
            obtainStyledAttributes.recycle();
        }

        public final String debug(String str) {
            StringBuilder m =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            str, "MatchParentShrinkingLinearLayout.LayoutParams={width=");
            m.append(
                    ViewGroup.MarginLayoutParams.sizeToString(
                            ((ViewGroup.MarginLayoutParams) this).width));
            m.append(", height=");
            m.append(
                    ViewGroup.MarginLayoutParams.sizeToString(
                            ((ViewGroup.MarginLayoutParams) this).height));
            m.append(" weight=");
            m.append(this.weight);
            m.append("}");
            return m.toString();
        }

        public final void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:weight", this.weight);
            viewHierarchyEncoder.addProperty("layout:gravity", this.gravity);
        }

        public LayoutParams(int i) {
            super(i, -2);
            this.gravity = -1;
            this.weight = 0.0f;
        }
    }
}
