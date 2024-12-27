package com.google.android.flexbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FlexboxLayout extends ViewGroup implements FlexContainer {
    public final int mAlignContent;
    public final int mAlignItems;
    public final Drawable mDividerDrawableHorizontal;
    public final Drawable mDividerDrawableVertical;
    public final int mDividerHorizontalHeight;
    public final int mDividerVerticalWidth;
    public final int mFlexDirection;
    public List mFlexLines;
    public final FlexboxHelper.FlexLinesResult mFlexLinesResult;
    public final int mFlexWrap;
    public final FlexboxHelper mFlexboxHelper;
    public final int mJustifyContent;
    public final int mMaxLine;
    public SparseIntArray mOrderCache;
    public int[] mReorderedIndices;
    public final int mShowDividerHorizontal;
    public final int mShowDividerVertical;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LayoutParams extends ViewGroup.MarginLayoutParams implements FlexItem {
        public static final Parcelable.Creator<LayoutParams> CREATOR = new AnonymousClass1();
        public int mAlignSelf;
        public float mFlexBasisPercent;
        public float mFlexGrow;
        public float mFlexShrink;
        public int mMaxHeight;
        public int mMaxWidth;
        public int mMinHeight;
        public int mMinWidth;
        public int mOrder;
        public boolean mWrapBefore;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.google.android.flexbox.FlexboxLayout$LayoutParams$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                LayoutParams layoutParams = new LayoutParams(0, 0);
                layoutParams.mOrder = 1;
                layoutParams.mFlexGrow = 0.0f;
                layoutParams.mFlexShrink = 1.0f;
                layoutParams.mAlignSelf = -1;
                layoutParams.mFlexBasisPercent = -1.0f;
                layoutParams.mMinWidth = -1;
                layoutParams.mMinHeight = -1;
                layoutParams.mMaxWidth = 16777215;
                layoutParams.mMaxHeight = 16777215;
                layoutParams.mOrder = parcel.readInt();
                layoutParams.mFlexGrow = parcel.readFloat();
                layoutParams.mFlexShrink = parcel.readFloat();
                layoutParams.mAlignSelf = parcel.readInt();
                layoutParams.mFlexBasisPercent = parcel.readFloat();
                layoutParams.mMinWidth = parcel.readInt();
                layoutParams.mMinHeight = parcel.readInt();
                layoutParams.mMaxWidth = parcel.readInt();
                layoutParams.mMaxHeight = parcel.readInt();
                layoutParams.mWrapBefore = parcel.readByte() != 0;
                ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = parcel.readInt();
                ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = parcel.readInt();
                ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = parcel.readInt();
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = parcel.readInt();
                ((ViewGroup.MarginLayoutParams) layoutParams).height = parcel.readInt();
                ((ViewGroup.MarginLayoutParams) layoutParams).width = parcel.readInt();
                return layoutParams;
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new LayoutParams[i];
            }
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final int getAlignSelf() {
            return this.mAlignSelf;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final float getFlexBasisPercent() {
            return this.mFlexBasisPercent;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final float getFlexGrow() {
            return this.mFlexGrow;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final float getFlexShrink() {
            return this.mFlexShrink;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final int getHeight() {
            return ((ViewGroup.MarginLayoutParams) this).height;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final int getMarginBottom() {
            return ((ViewGroup.MarginLayoutParams) this).bottomMargin;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final int getMarginLeft() {
            return ((ViewGroup.MarginLayoutParams) this).leftMargin;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final int getMarginRight() {
            return ((ViewGroup.MarginLayoutParams) this).rightMargin;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final int getMarginTop() {
            return ((ViewGroup.MarginLayoutParams) this).topMargin;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final int getMaxHeight() {
            return this.mMaxHeight;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final int getMaxWidth() {
            return this.mMaxWidth;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final int getMinHeight() {
            return this.mMinHeight;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final int getMinWidth() {
            return this.mMinWidth;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final int getOrder() {
            return this.mOrder;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final int getWidth() {
            return ((ViewGroup.MarginLayoutParams) this).width;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final boolean isWrapBefore() {
            return this.mWrapBefore;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final void setMinHeight(int i) {
            this.mMinHeight = i;
        }

        @Override // com.google.android.flexbox.FlexItem
        public final void setMinWidth(int i) {
            this.mMinWidth = i;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mOrder);
            parcel.writeFloat(this.mFlexGrow);
            parcel.writeFloat(this.mFlexShrink);
            parcel.writeInt(this.mAlignSelf);
            parcel.writeFloat(this.mFlexBasisPercent);
            parcel.writeInt(this.mMinWidth);
            parcel.writeInt(this.mMinHeight);
            parcel.writeInt(this.mMaxWidth);
            parcel.writeInt(this.mMaxHeight);
            parcel.writeByte(this.mWrapBefore ? (byte) 1 : (byte) 0);
            parcel.writeInt(((ViewGroup.MarginLayoutParams) this).bottomMargin);
            parcel.writeInt(((ViewGroup.MarginLayoutParams) this).leftMargin);
            parcel.writeInt(((ViewGroup.MarginLayoutParams) this).rightMargin);
            parcel.writeInt(((ViewGroup.MarginLayoutParams) this).topMargin);
            parcel.writeInt(((ViewGroup.MarginLayoutParams) this).height);
            parcel.writeInt(((ViewGroup.MarginLayoutParams) this).width);
        }
    }

    public FlexboxLayout(Context context) {
        this(context, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (this.mOrderCache == null) {
            this.mOrderCache = new SparseIntArray(getChildCount());
        }
        FlexboxHelper flexboxHelper = this.mFlexboxHelper;
        SparseIntArray sparseIntArray = this.mOrderCache;
        FlexContainer flexContainer = flexboxHelper.mFlexContainer;
        int flexItemCount = flexContainer.getFlexItemCount();
        List createOrders = flexboxHelper.createOrders(flexItemCount);
        FlexboxHelper.Order order = new FlexboxHelper.Order();
        if (view == null || !(layoutParams instanceof FlexItem)) {
            order.order = 1;
        } else {
            order.order = ((FlexItem) layoutParams).getOrder();
        }
        if (i == -1 || i == flexItemCount) {
            order.index = flexItemCount;
        } else if (i < flexContainer.getFlexItemCount()) {
            order.index = i;
            for (int i2 = i; i2 < flexItemCount; i2++) {
                ((FlexboxHelper.Order) ((ArrayList) createOrders).get(i2)).index++;
            }
        } else {
            order.index = flexItemCount;
        }
        ((ArrayList) createOrders).add(order);
        this.mReorderedIndices =
                FlexboxHelper.sortOrdersIntoReorderedIndices(
                        flexItemCount + 1, createOrders, sparseIntArray);
        super.addView(view, i, layoutParams);
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public final void drawDividersHorizontal(Canvas canvas, boolean z, boolean z2) {
        int paddingLeft = getPaddingLeft();
        int max = Math.max(0, (getWidth() - getPaddingRight()) - paddingLeft);
        int size = this.mFlexLines.size();
        for (int i = 0; i < size; i++) {
            FlexLine flexLine = (FlexLine) this.mFlexLines.get(i);
            for (int i2 = 0; i2 < flexLine.mItemCount; i2++) {
                int i3 = flexLine.mFirstIndex + i2;
                View reorderedChildAt = getReorderedChildAt(i3);
                if (reorderedChildAt != null && reorderedChildAt.getVisibility() != 8) {
                    LayoutParams layoutParams = (LayoutParams) reorderedChildAt.getLayoutParams();
                    if (hasDividerBeforeChildAtAlongMainAxis(i3, i2)) {
                        drawVerticalDivider(
                                canvas,
                                z
                                        ? reorderedChildAt.getRight()
                                                + ((ViewGroup.MarginLayoutParams) layoutParams)
                                                        .rightMargin
                                        : (reorderedChildAt.getLeft()
                                                        - ((ViewGroup.MarginLayoutParams)
                                                                        layoutParams)
                                                                .leftMargin)
                                                - this.mDividerVerticalWidth,
                                flexLine.mTop,
                                flexLine.mCrossSize);
                    }
                    if (i2 == flexLine.mItemCount - 1 && (this.mShowDividerVertical & 4) > 0) {
                        drawVerticalDivider(
                                canvas,
                                z
                                        ? (reorderedChildAt.getLeft()
                                                        - ((ViewGroup.MarginLayoutParams)
                                                                        layoutParams)
                                                                .leftMargin)
                                                - this.mDividerVerticalWidth
                                        : reorderedChildAt.getRight()
                                                + ((ViewGroup.MarginLayoutParams) layoutParams)
                                                        .rightMargin,
                                flexLine.mTop,
                                flexLine.mCrossSize);
                    }
                }
            }
            if (hasDividerBeforeFlexLine(i)) {
                drawHorizontalDivider(
                        canvas,
                        paddingLeft,
                        z2 ? flexLine.mBottom : flexLine.mTop - this.mDividerHorizontalHeight,
                        max);
            }
            if (hasEndDividerAfterFlexLine(i) && (this.mShowDividerHorizontal & 4) > 0) {
                drawHorizontalDivider(
                        canvas,
                        paddingLeft,
                        z2 ? flexLine.mTop - this.mDividerHorizontalHeight : flexLine.mBottom,
                        max);
            }
        }
    }

    public final void drawDividersVertical(Canvas canvas, boolean z, boolean z2) {
        int paddingTop = getPaddingTop();
        int max = Math.max(0, (getHeight() - getPaddingBottom()) - paddingTop);
        int size = this.mFlexLines.size();
        for (int i = 0; i < size; i++) {
            FlexLine flexLine = (FlexLine) this.mFlexLines.get(i);
            for (int i2 = 0; i2 < flexLine.mItemCount; i2++) {
                int i3 = flexLine.mFirstIndex + i2;
                View reorderedChildAt = getReorderedChildAt(i3);
                if (reorderedChildAt != null && reorderedChildAt.getVisibility() != 8) {
                    LayoutParams layoutParams = (LayoutParams) reorderedChildAt.getLayoutParams();
                    if (hasDividerBeforeChildAtAlongMainAxis(i3, i2)) {
                        drawHorizontalDivider(
                                canvas,
                                flexLine.mLeft,
                                z2
                                        ? reorderedChildAt.getBottom()
                                                + ((ViewGroup.MarginLayoutParams) layoutParams)
                                                        .bottomMargin
                                        : (reorderedChildAt.getTop()
                                                        - ((ViewGroup.MarginLayoutParams)
                                                                        layoutParams)
                                                                .topMargin)
                                                - this.mDividerHorizontalHeight,
                                flexLine.mCrossSize);
                    }
                    if (i2 == flexLine.mItemCount - 1 && (this.mShowDividerHorizontal & 4) > 0) {
                        drawHorizontalDivider(
                                canvas,
                                flexLine.mLeft,
                                z2
                                        ? (reorderedChildAt.getTop()
                                                        - ((ViewGroup.MarginLayoutParams)
                                                                        layoutParams)
                                                                .topMargin)
                                                - this.mDividerHorizontalHeight
                                        : reorderedChildAt.getBottom()
                                                + ((ViewGroup.MarginLayoutParams) layoutParams)
                                                        .bottomMargin,
                                flexLine.mCrossSize);
                    }
                }
            }
            if (hasDividerBeforeFlexLine(i)) {
                drawVerticalDivider(
                        canvas,
                        z ? flexLine.mRight : flexLine.mLeft - this.mDividerVerticalWidth,
                        paddingTop,
                        max);
            }
            if (hasEndDividerAfterFlexLine(i) && (this.mShowDividerVertical & 4) > 0) {
                drawVerticalDivider(
                        canvas,
                        z ? flexLine.mLeft - this.mDividerVerticalWidth : flexLine.mRight,
                        paddingTop,
                        max);
            }
        }
    }

    public final void drawHorizontalDivider(Canvas canvas, int i, int i2, int i3) {
        Drawable drawable = this.mDividerDrawableHorizontal;
        if (drawable == null) {
            return;
        }
        drawable.setBounds(i, i2, i3 + i, this.mDividerHorizontalHeight + i2);
        this.mDividerDrawableHorizontal.draw(canvas);
    }

    public final void drawVerticalDivider(Canvas canvas, int i, int i2, int i3) {
        Drawable drawable = this.mDividerDrawableVertical;
        if (drawable == null) {
            return;
        }
        drawable.setBounds(i, i2, this.mDividerVerticalWidth + i, i3 + i2);
        this.mDividerDrawableVertical.draw(canvas);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        LayoutParams layoutParams = new LayoutParams(context, attributeSet);
        layoutParams.mOrder = 1;
        layoutParams.mFlexGrow = 0.0f;
        layoutParams.mFlexShrink = 1.0f;
        layoutParams.mAlignSelf = -1;
        layoutParams.mFlexBasisPercent = -1.0f;
        layoutParams.mMinWidth = -1;
        layoutParams.mMinHeight = -1;
        layoutParams.mMaxWidth = 16777215;
        layoutParams.mMaxHeight = 16777215;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.FlexboxLayout_Layout);
        layoutParams.mOrder = obtainStyledAttributes.getInt(8, 1);
        layoutParams.mFlexGrow = obtainStyledAttributes.getFloat(2, 0.0f);
        layoutParams.mFlexShrink = obtainStyledAttributes.getFloat(3, 1.0f);
        layoutParams.mAlignSelf = obtainStyledAttributes.getInt(0, -1);
        layoutParams.mFlexBasisPercent = obtainStyledAttributes.getFraction(1, 1, 1, -1.0f);
        layoutParams.mMinWidth = obtainStyledAttributes.getDimensionPixelSize(7, -1);
        layoutParams.mMinHeight = obtainStyledAttributes.getDimensionPixelSize(6, -1);
        layoutParams.mMaxWidth = obtainStyledAttributes.getDimensionPixelSize(5, 16777215);
        layoutParams.mMaxHeight = obtainStyledAttributes.getDimensionPixelSize(4, 16777215);
        layoutParams.mWrapBefore = obtainStyledAttributes.getBoolean(9, false);
        obtainStyledAttributes.recycle();
        return layoutParams;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getAlignContent() {
        return this.mAlignContent;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getAlignItems() {
        return this.mAlignItems;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getChildHeightMeasureSpec(int i, int i2, int i3) {
        return ViewGroup.getChildMeasureSpec(i, i2, i3);
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getChildWidthMeasureSpec(int i, int i2, int i3) {
        return ViewGroup.getChildMeasureSpec(i, i2, i3);
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getDecorationLengthCrossAxis(View view) {
        return 0;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getDecorationLengthMainAxis(View view, int i, int i2) {
        int i3;
        int i4;
        if (isMainAxisDirectionHorizontal()) {
            i3 = hasDividerBeforeChildAtAlongMainAxis(i, i2) ? this.mDividerVerticalWidth : 0;
            if ((this.mShowDividerVertical & 4) <= 0) {
                return i3;
            }
            i4 = this.mDividerVerticalWidth;
        } else {
            i3 = hasDividerBeforeChildAtAlongMainAxis(i, i2) ? this.mDividerHorizontalHeight : 0;
            if ((this.mShowDividerHorizontal & 4) <= 0) {
                return i3;
            }
            i4 = this.mDividerHorizontalHeight;
        }
        return i3 + i4;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getFlexDirection() {
        return this.mFlexDirection;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final View getFlexItemAt(int i) {
        return getChildAt(i);
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getFlexItemCount() {
        return getChildCount();
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final List getFlexLinesInternal() {
        return this.mFlexLines;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getFlexWrap() {
        return this.mFlexWrap;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getLargestMainSize() {
        Iterator it = this.mFlexLines.iterator();
        int i = Integer.MIN_VALUE;
        while (it.hasNext()) {
            i = Math.max(i, ((FlexLine) it.next()).mMainSize);
        }
        return i;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getMaxLine() {
        return this.mMaxLine;
    }

    public final View getReorderedChildAt(int i) {
        if (i < 0) {
            return null;
        }
        int[] iArr = this.mReorderedIndices;
        if (i >= iArr.length) {
            return null;
        }
        return getChildAt(iArr[i]);
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final View getReorderedFlexItemAt(int i) {
        return getReorderedChildAt(i);
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getSumOfCrossSize() {
        int size = this.mFlexLines.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            FlexLine flexLine = (FlexLine) this.mFlexLines.get(i2);
            if (hasDividerBeforeFlexLine(i2)) {
                i +=
                        isMainAxisDirectionHorizontal()
                                ? this.mDividerHorizontalHeight
                                : this.mDividerVerticalWidth;
            }
            if (hasEndDividerAfterFlexLine(i2)) {
                i +=
                        isMainAxisDirectionHorizontal()
                                ? this.mDividerHorizontalHeight
                                : this.mDividerVerticalWidth;
            }
            i += flexLine.mCrossSize;
        }
        return i;
    }

    public final boolean hasDividerBeforeChildAtAlongMainAxis(int i, int i2) {
        for (int i3 = 1; i3 <= i2; i3++) {
            View reorderedChildAt = getReorderedChildAt(i - i3);
            if (reorderedChildAt != null && reorderedChildAt.getVisibility() != 8) {
                return isMainAxisDirectionHorizontal()
                        ? (this.mShowDividerVertical & 2) != 0
                        : (this.mShowDividerHorizontal & 2) != 0;
            }
        }
        return isMainAxisDirectionHorizontal()
                ? (this.mShowDividerVertical & 1) != 0
                : (this.mShowDividerHorizontal & 1) != 0;
    }

    public final boolean hasDividerBeforeFlexLine(int i) {
        if (i < 0 || i >= this.mFlexLines.size()) {
            return false;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (((FlexLine) this.mFlexLines.get(i2)).getItemCountNotGone() > 0) {
                return isMainAxisDirectionHorizontal()
                        ? (this.mShowDividerHorizontal & 2) != 0
                        : (this.mShowDividerVertical & 2) != 0;
            }
        }
        return isMainAxisDirectionHorizontal()
                ? (this.mShowDividerHorizontal & 1) != 0
                : (this.mShowDividerVertical & 1) != 0;
    }

    public final boolean hasEndDividerAfterFlexLine(int i) {
        if (i < 0 || i >= this.mFlexLines.size()) {
            return false;
        }
        for (int i2 = i + 1; i2 < this.mFlexLines.size(); i2++) {
            if (((FlexLine) this.mFlexLines.get(i2)).getItemCountNotGone() > 0) {
                return false;
            }
        }
        return isMainAxisDirectionHorizontal()
                ? (this.mShowDividerHorizontal & 4) != 0
                : (this.mShowDividerVertical & 4) != 0;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final boolean isMainAxisDirectionHorizontal() {
        int i = this.mFlexDirection;
        return i == 0 || i == 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x018d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void layoutHorizontal(boolean r29, int r30, int r31, int r32, int r33) {
        /*
            Method dump skipped, instructions count: 557
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.flexbox.FlexboxLayout.layoutHorizontal(boolean, int,"
                    + " int, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x017d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void layoutVertical(int r30, int r31, int r32, int r33, boolean r34, boolean r35) {
        /*
            Method dump skipped, instructions count: 533
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.flexbox.FlexboxLayout.layoutVertical(int, int, int, int,"
                    + " boolean, boolean):void");
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.mDividerDrawableVertical == null && this.mDividerDrawableHorizontal == null) {
            return;
        }
        if (this.mShowDividerHorizontal == 0 && this.mShowDividerVertical == 0) {
            return;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int layoutDirection = getLayoutDirection();
        int i = this.mFlexDirection;
        if (i == 0) {
            drawDividersHorizontal(canvas, layoutDirection == 1, this.mFlexWrap == 2);
            return;
        }
        if (i == 1) {
            drawDividersHorizontal(canvas, layoutDirection != 1, this.mFlexWrap == 2);
            return;
        }
        if (i == 2) {
            boolean z = layoutDirection == 1;
            if (this.mFlexWrap == 2) {
                z = !z;
            }
            drawDividersVertical(canvas, z, false);
            return;
        }
        if (i != 3) {
            return;
        }
        boolean z2 = layoutDirection == 1;
        if (this.mFlexWrap == 2) {
            z2 = !z2;
        }
        drawDividersVertical(canvas, z2, true);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int layoutDirection = getLayoutDirection();
        int i5 = this.mFlexDirection;
        if (i5 == 0) {
            layoutHorizontal(layoutDirection == 1, i, i2, i3, i4);
            return;
        }
        if (i5 == 1) {
            layoutHorizontal(layoutDirection != 1, i, i2, i3, i4);
            return;
        }
        if (i5 == 2) {
            z2 = layoutDirection == 1;
            if (this.mFlexWrap == 2) {
                z2 = !z2;
            }
            layoutVertical(i, i2, i3, i4, z2, false);
            return;
        }
        if (i5 != 3) {
            throw new IllegalStateException(
                    "Invalid flex direction is set: " + this.mFlexDirection);
        }
        z2 = layoutDirection == 1;
        if (this.mFlexWrap == 2) {
            z2 = !z2;
        }
        layoutVertical(i, i2, i3, i4, z2, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00e0  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMeasure(int r14, int r15) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.google.android.flexbox.FlexboxLayout.onMeasure(int,"
                    + " int):void");
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final void onNewFlexItemAdded(View view, int i, int i2, FlexLine flexLine) {
        if (hasDividerBeforeChildAtAlongMainAxis(i, i2)) {
            if (isMainAxisDirectionHorizontal()) {
                int i3 = flexLine.mMainSize;
                int i4 = this.mDividerVerticalWidth;
                flexLine.mMainSize = i3 + i4;
                flexLine.mDividerLengthInMainSize += i4;
                return;
            }
            int i5 = flexLine.mMainSize;
            int i6 = this.mDividerHorizontalHeight;
            flexLine.mMainSize = i5 + i6;
            flexLine.mDividerLengthInMainSize += i6;
        }
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final void onNewFlexLineAdded(FlexLine flexLine) {
        if (isMainAxisDirectionHorizontal()) {
            if ((this.mShowDividerVertical & 4) > 0) {
                int i = flexLine.mMainSize;
                int i2 = this.mDividerVerticalWidth;
                flexLine.mMainSize = i + i2;
                flexLine.mDividerLengthInMainSize += i2;
                return;
            }
            return;
        }
        if ((this.mShowDividerHorizontal & 4) > 0) {
            int i3 = flexLine.mMainSize;
            int i4 = this.mDividerHorizontalHeight;
            flexLine.mMainSize = i3 + i4;
            flexLine.mDividerLengthInMainSize += i4;
        }
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final void setFlexLines(List list) {
        this.mFlexLines = list;
    }

    public final void setMeasuredDimensionForFlex(int i, int i2, int i3, int i4) {
        int paddingBottom;
        int largestMainSize;
        int resolveSizeAndState;
        int resolveSizeAndState2;
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size2 = View.MeasureSpec.getSize(i3);
        if (i == 0 || i == 1) {
            paddingBottom = getPaddingBottom() + getPaddingTop() + getSumOfCrossSize();
            largestMainSize = getLargestMainSize();
        } else {
            if (i != 2 && i != 3) {
                throw new IllegalArgumentException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                i, "Invalid flex direction: "));
            }
            paddingBottom = getLargestMainSize();
            largestMainSize = getPaddingRight() + getPaddingLeft() + getSumOfCrossSize();
        }
        if (mode == Integer.MIN_VALUE) {
            if (size < largestMainSize) {
                i4 = View.combineMeasuredStates(i4, 16777216);
            } else {
                size = largestMainSize;
            }
            resolveSizeAndState = View.resolveSizeAndState(size, i2, i4);
        } else if (mode == 0) {
            resolveSizeAndState = View.resolveSizeAndState(largestMainSize, i2, i4);
        } else {
            if (mode != 1073741824) {
                throw new IllegalStateException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                mode, "Unknown width mode is set: "));
            }
            if (size < largestMainSize) {
                i4 = View.combineMeasuredStates(i4, 16777216);
            }
            resolveSizeAndState = View.resolveSizeAndState(size, i2, i4);
        }
        if (mode2 == Integer.MIN_VALUE) {
            if (size2 < paddingBottom) {
                i4 = View.combineMeasuredStates(i4, 256);
            } else {
                size2 = paddingBottom;
            }
            resolveSizeAndState2 = View.resolveSizeAndState(size2, i3, i4);
        } else if (mode2 == 0) {
            resolveSizeAndState2 = View.resolveSizeAndState(paddingBottom, i3, i4);
        } else {
            if (mode2 != 1073741824) {
                throw new IllegalStateException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                mode2, "Unknown height mode is set: "));
            }
            if (size2 < paddingBottom) {
                i4 = View.combineMeasuredStates(i4, 256);
            }
            resolveSizeAndState2 = View.resolveSizeAndState(size2, i3, i4);
        }
        setMeasuredDimension(resolveSizeAndState, resolveSizeAndState2);
    }

    public FlexboxLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FlexboxLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMaxLine = -1;
        this.mFlexboxHelper = new FlexboxHelper(this);
        this.mFlexLines = new ArrayList();
        this.mFlexLinesResult = new FlexboxHelper.FlexLinesResult();
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.FlexboxLayout, i, 0);
        this.mFlexDirection = obtainStyledAttributes.getInt(5, 0);
        this.mFlexWrap = obtainStyledAttributes.getInt(6, 0);
        this.mJustifyContent = obtainStyledAttributes.getInt(7, 0);
        this.mAlignItems = obtainStyledAttributes.getInt(1, 0);
        this.mAlignContent = obtainStyledAttributes.getInt(0, 0);
        this.mMaxLine = obtainStyledAttributes.getInt(8, -1);
        Drawable drawable = obtainStyledAttributes.getDrawable(2);
        if (drawable != null) {
            if (drawable != this.mDividerDrawableHorizontal) {
                this.mDividerDrawableHorizontal = drawable;
                this.mDividerHorizontalHeight = drawable.getIntrinsicHeight();
                if (this.mDividerDrawableHorizontal == null
                        && this.mDividerDrawableVertical == null) {
                    setWillNotDraw(true);
                } else {
                    setWillNotDraw(false);
                }
                requestLayout();
            }
            if (drawable != this.mDividerDrawableVertical) {
                this.mDividerDrawableVertical = drawable;
                this.mDividerVerticalWidth = drawable.getIntrinsicWidth();
                if (this.mDividerDrawableHorizontal == null
                        && this.mDividerDrawableVertical == null) {
                    setWillNotDraw(true);
                } else {
                    setWillNotDraw(false);
                }
                requestLayout();
            }
        }
        Drawable drawable2 = obtainStyledAttributes.getDrawable(3);
        if (drawable2 != null && drawable2 != this.mDividerDrawableHorizontal) {
            this.mDividerDrawableHorizontal = drawable2;
            this.mDividerHorizontalHeight = drawable2.getIntrinsicHeight();
            if (this.mDividerDrawableHorizontal == null && this.mDividerDrawableVertical == null) {
                setWillNotDraw(true);
            } else {
                setWillNotDraw(false);
            }
            requestLayout();
        }
        Drawable drawable3 = obtainStyledAttributes.getDrawable(4);
        if (drawable3 != null && drawable3 != this.mDividerDrawableVertical) {
            this.mDividerDrawableVertical = drawable3;
            this.mDividerVerticalWidth = drawable3.getIntrinsicWidth();
            if (this.mDividerDrawableHorizontal == null && this.mDividerDrawableVertical == null) {
                setWillNotDraw(true);
            } else {
                setWillNotDraw(false);
            }
            requestLayout();
        }
        int i2 = obtainStyledAttributes.getInt(9, 0);
        if (i2 != 0) {
            this.mShowDividerVertical = i2;
            this.mShowDividerHorizontal = i2;
        }
        int i3 = obtainStyledAttributes.getInt(11, 0);
        if (i3 != 0) {
            this.mShowDividerVertical = i3;
        }
        int i4 = obtainStyledAttributes.getInt(10, 0);
        if (i4 != 0) {
            this.mShowDividerHorizontal = i4;
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            LayoutParams layoutParams3 = new LayoutParams(layoutParams2);
            layoutParams3.mOrder = 1;
            layoutParams3.mFlexGrow = 0.0f;
            layoutParams3.mFlexShrink = 1.0f;
            layoutParams3.mAlignSelf = -1;
            layoutParams3.mFlexBasisPercent = -1.0f;
            layoutParams3.mMinWidth = -1;
            layoutParams3.mMinHeight = -1;
            layoutParams3.mMaxWidth = 16777215;
            layoutParams3.mMaxHeight = 16777215;
            layoutParams3.mOrder = layoutParams2.mOrder;
            layoutParams3.mFlexGrow = layoutParams2.mFlexGrow;
            layoutParams3.mFlexShrink = layoutParams2.mFlexShrink;
            layoutParams3.mAlignSelf = layoutParams2.mAlignSelf;
            layoutParams3.mFlexBasisPercent = layoutParams2.mFlexBasisPercent;
            layoutParams3.mMinWidth = layoutParams2.mMinWidth;
            layoutParams3.mMinHeight = layoutParams2.mMinHeight;
            layoutParams3.mMaxWidth = layoutParams2.mMaxWidth;
            layoutParams3.mMaxHeight = layoutParams2.mMaxHeight;
            layoutParams3.mWrapBefore = layoutParams2.mWrapBefore;
            return layoutParams3;
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            LayoutParams layoutParams4 =
                    new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
            layoutParams4.mOrder = 1;
            layoutParams4.mFlexGrow = 0.0f;
            layoutParams4.mFlexShrink = 1.0f;
            layoutParams4.mAlignSelf = -1;
            layoutParams4.mFlexBasisPercent = -1.0f;
            layoutParams4.mMinWidth = -1;
            layoutParams4.mMinHeight = -1;
            layoutParams4.mMaxWidth = 16777215;
            layoutParams4.mMaxHeight = 16777215;
            return layoutParams4;
        }
        LayoutParams layoutParams5 = new LayoutParams(layoutParams);
        layoutParams5.mOrder = 1;
        layoutParams5.mFlexGrow = 0.0f;
        layoutParams5.mFlexShrink = 1.0f;
        layoutParams5.mAlignSelf = -1;
        layoutParams5.mFlexBasisPercent = -1.0f;
        layoutParams5.mMinWidth = -1;
        layoutParams5.mMinHeight = -1;
        layoutParams5.mMaxWidth = 16777215;
        layoutParams5.mMaxHeight = 16777215;
        return layoutParams5;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final void updateViewCache(View view, int i) {}
}
