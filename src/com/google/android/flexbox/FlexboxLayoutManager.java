package com.google.android.flexbox;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.animation.ChangeSize$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper$1;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView$SmoothScroller$ScrollVectorProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FlexboxLayoutManager extends RecyclerView.LayoutManager
        implements FlexContainer, RecyclerView$SmoothScroller$ScrollVectorProvider {
    public static final Rect TEMP_RECT = new Rect();
    public final int mAlignItems;
    public final Context mContext;
    public int mFlexDirection;
    public final int mFlexWrap;
    public boolean mFromBottomToTop;
    public boolean mIsRtl;
    public LayoutState mLayoutState;
    public OrientationHelper$1 mOrientationHelper;
    public View mParent;
    public SavedState mPendingSavedState;
    public RecyclerView.Recycler mRecycler;
    public RecyclerView.State mState;
    public OrientationHelper$1 mSubOrientationHelper;
    public final int mMaxLine = -1;
    public List mFlexLines = new ArrayList();
    public final FlexboxHelper mFlexboxHelper = new FlexboxHelper(this);
    public final AnchorInfo mAnchorInfo = new AnchorInfo();
    public int mPendingScrollPosition = -1;
    public int mPendingScrollPositionOffset = Integer.MIN_VALUE;
    public int mLastWidth = Integer.MIN_VALUE;
    public int mLastHeight = Integer.MIN_VALUE;
    public final SparseArray mViewCache = new SparseArray();
    public int mDirtyPosition = -1;
    public final FlexboxHelper.FlexLinesResult mFlexLinesResult =
            new FlexboxHelper.FlexLinesResult();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AnchorInfo {
        public boolean mAssignedFromSavedState;
        public int mCoordinate;
        public int mFlexLinePosition;
        public boolean mLayoutFromEnd;
        public int mPerpendicularCoordinate = 0;
        public int mPosition;
        public boolean mValid;

        public AnchorInfo() {}

        public static void access$1600(AnchorInfo anchorInfo) {
            FlexboxLayoutManager flexboxLayoutManager = FlexboxLayoutManager.this;
            if (flexboxLayoutManager.isMainAxisDirectionHorizontal()
                    || !flexboxLayoutManager.mIsRtl) {
                anchorInfo.mCoordinate =
                        anchorInfo.mLayoutFromEnd
                                ? flexboxLayoutManager.mOrientationHelper.getEndAfterPadding()
                                : flexboxLayoutManager.mOrientationHelper.getStartAfterPadding();
            } else {
                anchorInfo.mCoordinate =
                        anchorInfo.mLayoutFromEnd
                                ? flexboxLayoutManager.mOrientationHelper.getEndAfterPadding()
                                : flexboxLayoutManager.mWidth
                                        - flexboxLayoutManager.mOrientationHelper
                                                .getStartAfterPadding();
            }
        }

        public static void access$800(AnchorInfo anchorInfo) {
            anchorInfo.mPosition = -1;
            anchorInfo.mFlexLinePosition = -1;
            anchorInfo.mCoordinate = Integer.MIN_VALUE;
            anchorInfo.mValid = false;
            anchorInfo.mAssignedFromSavedState = false;
            FlexboxLayoutManager flexboxLayoutManager = FlexboxLayoutManager.this;
            if (flexboxLayoutManager.isMainAxisDirectionHorizontal()) {
                int i = flexboxLayoutManager.mFlexWrap;
                if (i == 0) {
                    anchorInfo.mLayoutFromEnd = flexboxLayoutManager.mFlexDirection == 1;
                    return;
                } else {
                    anchorInfo.mLayoutFromEnd = i == 2;
                    return;
                }
            }
            int i2 = flexboxLayoutManager.mFlexWrap;
            if (i2 == 0) {
                anchorInfo.mLayoutFromEnd = flexboxLayoutManager.mFlexDirection == 3;
            } else {
                anchorInfo.mLayoutFromEnd = i2 == 2;
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("AnchorInfo{mPosition=");
            sb.append(this.mPosition);
            sb.append(", mFlexLinePosition=");
            sb.append(this.mFlexLinePosition);
            sb.append(", mCoordinate=");
            sb.append(this.mCoordinate);
            sb.append(", mPerpendicularCoordinate=");
            sb.append(this.mPerpendicularCoordinate);
            sb.append(", mLayoutFromEnd=");
            sb.append(this.mLayoutFromEnd);
            sb.append(", mValid=");
            sb.append(this.mValid);
            sb.append(", mAssignedFromSavedState=");
            return ChangeSize$$ExternalSyntheticOutline0.m(sb, this.mAssignedFromSavedState, '}');
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LayoutParams extends RecyclerView.LayoutParams implements FlexItem {
        public static final Parcelable.Creator<LayoutParams> CREATOR =
                new SavedState.AnonymousClass1(1);
        public int mAlignSelf;
        public float mFlexBasisPercent;
        public float mFlexGrow;
        public float mFlexShrink;
        public int mMaxHeight;
        public int mMaxWidth;
        public int mMinHeight;
        public int mMinWidth;
        public boolean mWrapBefore;

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
            return 1;
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

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LayoutState {
        public int mAvailable;
        public int mFlexLinePosition;
        public boolean mInfinite;
        public int mItemDirection;
        public int mLastScrollDelta;
        public int mLayoutDirection;
        public int mOffset;
        public int mPosition;
        public int mScrollingOffset;
        public boolean mShouldRecycle;

        public final String toString() {
            StringBuilder sb = new StringBuilder("LayoutState{mAvailable=");
            sb.append(this.mAvailable);
            sb.append(", mFlexLinePosition=");
            sb.append(this.mFlexLinePosition);
            sb.append(", mPosition=");
            sb.append(this.mPosition);
            sb.append(", mOffset=");
            sb.append(this.mOffset);
            sb.append(", mScrollingOffset=");
            sb.append(this.mScrollingOffset);
            sb.append(", mLastScrollDelta=");
            sb.append(this.mLastScrollDelta);
            sb.append(", mItemDirection=");
            sb.append(this.mItemDirection);
            sb.append(", mLayoutDirection=");
            return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.mLayoutDirection, '}');
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new AnonymousClass1(0);
        public int mAnchorOffset;
        public int mAnchorPosition;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.google.android.flexbox.FlexboxLayoutManager$SavedState$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.Creator {
            public final /* synthetic */ int $r8$classId;

            public /* synthetic */ AnonymousClass1(int i) {
                this.$r8$classId = i;
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                switch (this.$r8$classId) {
                    case 0:
                        SavedState savedState = new SavedState();
                        savedState.mAnchorPosition = parcel.readInt();
                        savedState.mAnchorOffset = parcel.readInt();
                        return savedState;
                    default:
                        LayoutParams layoutParams = new LayoutParams(-2, -2);
                        layoutParams.mFlexGrow = 0.0f;
                        layoutParams.mFlexShrink = 1.0f;
                        layoutParams.mAlignSelf = -1;
                        layoutParams.mFlexBasisPercent = -1.0f;
                        layoutParams.mMaxWidth = 16777215;
                        layoutParams.mMaxHeight = 16777215;
                        layoutParams.mFlexGrow = parcel.readFloat();
                        layoutParams.mFlexShrink = parcel.readFloat();
                        layoutParams.mAlignSelf = parcel.readInt();
                        layoutParams.mFlexBasisPercent = parcel.readFloat();
                        layoutParams.mMinWidth = parcel.readInt();
                        layoutParams.mMinHeight = parcel.readInt();
                        layoutParams.mMaxWidth = parcel.readInt();
                        layoutParams.mMaxHeight = parcel.readInt();
                        layoutParams.mWrapBefore = parcel.readByte() != 0;
                        ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin =
                                parcel.readInt();
                        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = parcel.readInt();
                        ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin =
                                parcel.readInt();
                        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = parcel.readInt();
                        ((ViewGroup.MarginLayoutParams) layoutParams).height = parcel.readInt();
                        ((ViewGroup.MarginLayoutParams) layoutParams).width = parcel.readInt();
                        return layoutParams;
                }
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                switch (this.$r8$classId) {
                    case 0:
                        return new SavedState[i];
                    default:
                        return new LayoutParams[i];
                }
            }
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SavedState{mAnchorPosition=");
            sb.append(this.mAnchorPosition);
            sb.append(", mAnchorOffset=");
            return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.mAnchorOffset, '}');
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mAnchorPosition);
            parcel.writeInt(this.mAnchorOffset);
        }
    }

    public FlexboxLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        RecyclerView.LayoutManager.Properties properties =
                RecyclerView.LayoutManager.getProperties(context, attributeSet, i, i2);
        int i3 = properties.orientation;
        if (i3 != 0) {
            if (i3 == 1) {
                if (properties.reverseLayout) {
                    setFlexDirection(3);
                } else {
                    setFlexDirection(2);
                }
            }
        } else if (properties.reverseLayout) {
            setFlexDirection(1);
        } else {
            setFlexDirection(0);
        }
        int i4 = this.mFlexWrap;
        if (i4 != 1) {
            if (i4 == 0) {
                removeAllViews();
                this.mFlexLines.clear();
                AnchorInfo anchorInfo = this.mAnchorInfo;
                AnchorInfo.access$800(anchorInfo);
                anchorInfo.mPerpendicularCoordinate = 0;
            }
            this.mFlexWrap = 1;
            this.mOrientationHelper = null;
            this.mSubOrientationHelper = null;
            requestLayout();
        }
        if (this.mAlignItems != 4) {
            removeAllViews();
            this.mFlexLines.clear();
            AnchorInfo anchorInfo2 = this.mAnchorInfo;
            AnchorInfo.access$800(anchorInfo2);
            anchorInfo2.mPerpendicularCoordinate = 0;
            this.mAlignItems = 4;
            requestLayout();
        }
        this.mContext = context;
    }

    public static boolean isMeasurementUpToDate(int i, int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (i3 > 0 && i != i3) {
            return false;
        }
        if (mode == Integer.MIN_VALUE) {
            return size >= i;
        }
        if (mode != 0) {
            return mode == 1073741824 && size == i;
        }
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollHorizontally() {
        if (this.mFlexWrap == 0) {
            return isMainAxisDirectionHorizontal();
        }
        if (isMainAxisDirectionHorizontal()) {
            int i = this.mWidth;
            View view = this.mParent;
            if (i <= (view != null ? view.getWidth() : 0)) {
                return false;
            }
        }
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollVertically() {
        if (this.mFlexWrap == 0) {
            return !isMainAxisDirectionHorizontal();
        }
        if (isMainAxisDirectionHorizontal()) {
            return true;
        }
        int i = this.mHeight;
        View view = this.mParent;
        return i > (view != null ? view.getHeight() : 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent$2(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset$2(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollRange(RecyclerView.State state) {
        return computeScrollRange$2(state);
    }

    public final int computeScrollExtent$2(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        int itemCount = state.getItemCount();
        ensureOrientationHelper();
        View findFirstReferenceChild = findFirstReferenceChild(itemCount);
        View findLastReferenceChild = findLastReferenceChild(itemCount);
        if (state.getItemCount() == 0
                || findFirstReferenceChild == null
                || findLastReferenceChild == null) {
            return 0;
        }
        return Math.min(
                this.mOrientationHelper.getTotalSpace(),
                this.mOrientationHelper.getDecoratedEnd(findLastReferenceChild)
                        - this.mOrientationHelper.getDecoratedStart(findFirstReferenceChild));
    }

    public final int computeScrollOffset$2(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        int itemCount = state.getItemCount();
        View findFirstReferenceChild = findFirstReferenceChild(itemCount);
        View findLastReferenceChild = findLastReferenceChild(itemCount);
        if (state.getItemCount() != 0
                && findFirstReferenceChild != null
                && findLastReferenceChild != null) {
            int position = RecyclerView.LayoutManager.getPosition(findFirstReferenceChild);
            int position2 = RecyclerView.LayoutManager.getPosition(findLastReferenceChild);
            int abs =
                    Math.abs(
                            this.mOrientationHelper.getDecoratedEnd(findLastReferenceChild)
                                    - this.mOrientationHelper.getDecoratedStart(
                                            findFirstReferenceChild));
            int i = this.mFlexboxHelper.mIndexToFlexLine[position];
            if (i != 0 && i != -1) {
                return Math.round(
                        (i * (abs / ((r4[position2] - i) + 1)))
                                + (this.mOrientationHelper.getStartAfterPadding()
                                        - this.mOrientationHelper.getDecoratedStart(
                                                findFirstReferenceChild)));
            }
        }
        return 0;
    }

    public final int computeScrollRange$2(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        int itemCount = state.getItemCount();
        View findFirstReferenceChild = findFirstReferenceChild(itemCount);
        View findLastReferenceChild = findLastReferenceChild(itemCount);
        if (state.getItemCount() == 0
                || findFirstReferenceChild == null
                || findLastReferenceChild == null) {
            return 0;
        }
        View findOneVisibleChild = findOneVisibleChild(0, getChildCount());
        int position =
                findOneVisibleChild == null
                        ? -1
                        : RecyclerView.LayoutManager.getPosition(findOneVisibleChild);
        return (int)
                ((Math.abs(
                                        this.mOrientationHelper.getDecoratedEnd(
                                                        findLastReferenceChild)
                                                - this.mOrientationHelper.getDecoratedStart(
                                                        findFirstReferenceChild))
                                / (((findOneVisibleChild(getChildCount() - 1, -1) != null
                                                        ? RecyclerView.LayoutManager.getPosition(r4)
                                                        : -1)
                                                - position)
                                        + 1))
                        * state.getItemCount());
    }

    @Override // androidx.recyclerview.widget.RecyclerView$SmoothScroller$ScrollVectorProvider
    public final PointF computeScrollVectorForPosition(int i) {
        View childAt;
        if (getChildCount() == 0 || (childAt = getChildAt(0)) == null) {
            return null;
        }
        int i2 = i < RecyclerView.LayoutManager.getPosition(childAt) ? -1 : 1;
        return isMainAxisDirectionHorizontal() ? new PointF(0.0f, i2) : new PointF(i2, 0.0f);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent$2(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset$2(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollRange(RecyclerView.State state) {
        return computeScrollRange$2(state);
    }

    public final void ensureOrientationHelper() {
        if (this.mOrientationHelper != null) {
            return;
        }
        if (isMainAxisDirectionHorizontal()) {
            if (this.mFlexWrap == 0) {
                this.mOrientationHelper = new OrientationHelper$1(this, 0);
                this.mSubOrientationHelper = new OrientationHelper$1(this, 1);
                return;
            } else {
                this.mOrientationHelper = new OrientationHelper$1(this, 1);
                this.mSubOrientationHelper = new OrientationHelper$1(this, 0);
                return;
            }
        }
        if (this.mFlexWrap == 0) {
            this.mOrientationHelper = new OrientationHelper$1(this, 1);
            this.mSubOrientationHelper = new OrientationHelper$1(this, 0);
        } else {
            this.mOrientationHelper = new OrientationHelper$1(this, 0);
            this.mSubOrientationHelper = new OrientationHelper$1(this, 1);
        }
    }

    public final int fill(
            RecyclerView.Recycler recycler, RecyclerView.State state, LayoutState layoutState) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        FlexboxHelper flexboxHelper;
        boolean z;
        int i6;
        View view;
        int i7;
        LayoutParams layoutParams;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        FlexboxHelper flexboxHelper2;
        View view2;
        LayoutParams layoutParams2;
        int i15 = layoutState.mScrollingOffset;
        if (i15 != Integer.MIN_VALUE) {
            int i16 = layoutState.mAvailable;
            if (i16 < 0) {
                layoutState.mScrollingOffset = i15 + i16;
            }
            recycleByLayoutState(recycler, layoutState);
        }
        int i17 = layoutState.mAvailable;
        boolean isMainAxisDirectionHorizontal = isMainAxisDirectionHorizontal();
        int i18 = i17;
        int i19 = 0;
        while (true) {
            if (i18 <= 0 && !this.mLayoutState.mInfinite) {
                break;
            }
            List list = this.mFlexLines;
            int i20 = layoutState.mPosition;
            if (i20 < 0
                    || i20 >= state.getItemCount()
                    || (i = layoutState.mFlexLinePosition) < 0
                    || i >= list.size()) {
                break;
            }
            FlexLine flexLine = (FlexLine) this.mFlexLines.get(layoutState.mFlexLinePosition);
            layoutState.mPosition = flexLine.mFirstIndex;
            boolean isMainAxisDirectionHorizontal2 = isMainAxisDirectionHorizontal();
            AnchorInfo anchorInfo = this.mAnchorInfo;
            FlexboxHelper flexboxHelper3 = this.mFlexboxHelper;
            if (isMainAxisDirectionHorizontal2) {
                int paddingLeft = getPaddingLeft();
                int paddingRight = getPaddingRight();
                int i21 = this.mWidth;
                int i22 = layoutState.mOffset;
                if (layoutState.mLayoutDirection == -1) {
                    i22 -= flexLine.mCrossSize;
                }
                int i23 = layoutState.mPosition;
                float f = anchorInfo.mPerpendicularCoordinate;
                float f2 = paddingLeft - f;
                float f3 = (i21 - paddingRight) - f;
                float max = Math.max(0.0f, 0.0f);
                int i24 = flexLine.mItemCount;
                int i25 = i23;
                int i26 = 0;
                while (i25 < i23 + i24) {
                    View flexItemAt = getFlexItemAt(i25);
                    if (flexItemAt == null) {
                        i12 = i17;
                        i13 = i22;
                        i14 = i25;
                        i10 = i24;
                        flexboxHelper2 = flexboxHelper3;
                        i11 = i23;
                    } else {
                        i10 = i24;
                        i11 = i23;
                        if (layoutState.mLayoutDirection == 1) {
                            calculateItemDecorationsForChild(TEMP_RECT, flexItemAt);
                            i12 = i17;
                            addViewInt(flexItemAt, -1, false);
                        } else {
                            i12 = i17;
                            calculateItemDecorationsForChild(TEMP_RECT, flexItemAt);
                            int i27 = i26;
                            addViewInt(flexItemAt, i27, false);
                            i26 = i27 + 1;
                        }
                        long j = flexboxHelper3.mMeasureSpecCache[i25];
                        int i28 = (int) j;
                        int i29 = (int) (j >> 32);
                        LayoutParams layoutParams3 = (LayoutParams) flexItemAt.getLayoutParams();
                        if (shouldMeasureChild(flexItemAt, i28, i29, layoutParams3)) {
                            flexItemAt.measure(i28, i29);
                        }
                        float f4 =
                                ((ViewGroup.MarginLayoutParams) layoutParams3).leftMargin
                                        + ((RecyclerView.LayoutParams) flexItemAt.getLayoutParams())
                                                .mDecorInsets
                                                .left
                                        + f2;
                        float f5 =
                                f3
                                        - (((ViewGroup.MarginLayoutParams) layoutParams3)
                                                        .rightMargin
                                                + ((RecyclerView.LayoutParams)
                                                                flexItemAt.getLayoutParams())
                                                        .mDecorInsets
                                                        .right);
                        int i30 =
                                i22
                                        + ((RecyclerView.LayoutParams) flexItemAt.getLayoutParams())
                                                .mDecorInsets
                                                .top;
                        if (this.mIsRtl) {
                            i14 = i25;
                            i13 = i22;
                            flexboxHelper2 = flexboxHelper3;
                            view2 = flexItemAt;
                            layoutParams2 = layoutParams3;
                            this.mFlexboxHelper.layoutSingleChildHorizontal(
                                    flexItemAt,
                                    flexLine,
                                    Math.round(f5) - flexItemAt.getMeasuredWidth(),
                                    i30,
                                    Math.round(f5),
                                    flexItemAt.getMeasuredHeight() + i30);
                        } else {
                            i13 = i22;
                            i14 = i25;
                            flexboxHelper2 = flexboxHelper3;
                            view2 = flexItemAt;
                            layoutParams2 = layoutParams3;
                            this.mFlexboxHelper.layoutSingleChildHorizontal(
                                    view2,
                                    flexLine,
                                    Math.round(f4),
                                    i30,
                                    view2.getMeasuredWidth() + Math.round(f4),
                                    view2.getMeasuredHeight() + i30);
                        }
                        f2 =
                                view2.getMeasuredWidth()
                                        + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin
                                        + ((RecyclerView.LayoutParams) view2.getLayoutParams())
                                                .mDecorInsets
                                                .right
                                        + max
                                        + f4;
                        f3 =
                                f5
                                        - (((view2.getMeasuredWidth()
                                                                + ((ViewGroup.MarginLayoutParams)
                                                                                layoutParams2)
                                                                        .leftMargin)
                                                        + ((RecyclerView.LayoutParams)
                                                                        view2.getLayoutParams())
                                                                .mDecorInsets
                                                                .left)
                                                + max);
                    }
                    i25 = i14 + 1;
                    flexboxHelper3 = flexboxHelper2;
                    i24 = i10;
                    i23 = i11;
                    i17 = i12;
                    i22 = i13;
                }
                i2 = i17;
                layoutState.mFlexLinePosition += this.mLayoutState.mLayoutDirection;
                i5 = flexLine.mCrossSize;
            } else {
                i2 = i17;
                FlexboxHelper flexboxHelper4 = flexboxHelper3;
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();
                int i31 = this.mHeight;
                int i32 = layoutState.mOffset;
                if (layoutState.mLayoutDirection == -1) {
                    int i33 = flexLine.mCrossSize;
                    i4 = i32 + i33;
                    i3 = i32 - i33;
                } else {
                    i3 = i32;
                    i4 = i3;
                }
                int i34 = layoutState.mPosition;
                float f6 = i31 - paddingBottom;
                float f7 = anchorInfo.mPerpendicularCoordinate;
                float f8 = paddingTop - f7;
                float f9 = f6 - f7;
                float max2 = Math.max(0.0f, 0.0f);
                int i35 = flexLine.mItemCount;
                int i36 = i34;
                int i37 = 0;
                while (i36 < i34 + i35) {
                    View flexItemAt2 = getFlexItemAt(i36);
                    if (flexItemAt2 == null) {
                        flexboxHelper = flexboxHelper4;
                        i7 = i36;
                        i9 = i35;
                        i8 = i34;
                    } else {
                        int i38 = i35;
                        int i39 = i34;
                        long j2 = flexboxHelper4.mMeasureSpecCache[i36];
                        int i40 = (int) j2;
                        int i41 = (int) (j2 >> 32);
                        LayoutParams layoutParams4 = (LayoutParams) flexItemAt2.getLayoutParams();
                        if (shouldMeasureChild(flexItemAt2, i40, i41, layoutParams4)) {
                            flexItemAt2.measure(i40, i41);
                        }
                        float f10 =
                                f8
                                        + ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin
                                        + ((RecyclerView.LayoutParams)
                                                        flexItemAt2.getLayoutParams())
                                                .mDecorInsets
                                                .top;
                        float f11 =
                                f9
                                        - (((ViewGroup.MarginLayoutParams) layoutParams4)
                                                        .rightMargin
                                                + ((RecyclerView.LayoutParams)
                                                                flexItemAt2.getLayoutParams())
                                                        .mDecorInsets
                                                        .bottom);
                        if (layoutState.mLayoutDirection == 1) {
                            calculateItemDecorationsForChild(TEMP_RECT, flexItemAt2);
                            flexboxHelper = flexboxHelper4;
                            z = false;
                            addViewInt(flexItemAt2, -1, false);
                        } else {
                            flexboxHelper = flexboxHelper4;
                            z = false;
                            calculateItemDecorationsForChild(TEMP_RECT, flexItemAt2);
                            addViewInt(flexItemAt2, i37, false);
                            i37++;
                        }
                        int i42 = i37;
                        int i43 =
                                i3
                                        + ((RecyclerView.LayoutParams)
                                                        flexItemAt2.getLayoutParams())
                                                .mDecorInsets
                                                .left;
                        int i44 =
                                i4
                                        - ((RecyclerView.LayoutParams)
                                                        flexItemAt2.getLayoutParams())
                                                .mDecorInsets
                                                .right;
                        boolean z2 = this.mIsRtl;
                        if (!z2) {
                            i6 = i42;
                            view = flexItemAt2;
                            i7 = i36;
                            layoutParams = layoutParams4;
                            i8 = i39;
                            i9 = i38;
                            if (this.mFromBottomToTop) {
                                this.mFlexboxHelper.layoutSingleChildVertical(
                                        view,
                                        flexLine,
                                        z2,
                                        i43,
                                        Math.round(f11) - view.getMeasuredHeight(),
                                        view.getMeasuredWidth() + i43,
                                        Math.round(f11));
                            } else {
                                this.mFlexboxHelper.layoutSingleChildVertical(
                                        view,
                                        flexLine,
                                        z2,
                                        i43,
                                        Math.round(f10),
                                        view.getMeasuredWidth() + i43,
                                        view.getMeasuredHeight() + Math.round(f10));
                            }
                        } else if (this.mFromBottomToTop) {
                            view = flexItemAt2;
                            i7 = i36;
                            i9 = i38;
                            i6 = i42;
                            layoutParams = layoutParams4;
                            i8 = i39;
                            this.mFlexboxHelper.layoutSingleChildVertical(
                                    flexItemAt2,
                                    flexLine,
                                    z2,
                                    i44 - flexItemAt2.getMeasuredWidth(),
                                    Math.round(f11) - flexItemAt2.getMeasuredHeight(),
                                    i44,
                                    Math.round(f11));
                        } else {
                            i6 = i42;
                            view = flexItemAt2;
                            i7 = i36;
                            layoutParams = layoutParams4;
                            i8 = i39;
                            i9 = i38;
                            this.mFlexboxHelper.layoutSingleChildVertical(
                                    view,
                                    flexLine,
                                    z2,
                                    i44 - view.getMeasuredWidth(),
                                    Math.round(f10),
                                    i44,
                                    view.getMeasuredHeight() + Math.round(f10));
                        }
                        f9 =
                                f11
                                        - (((view.getMeasuredHeight()
                                                                + ((ViewGroup.MarginLayoutParams)
                                                                                layoutParams)
                                                                        .bottomMargin)
                                                        + ((RecyclerView.LayoutParams)
                                                                        view.getLayoutParams())
                                                                .mDecorInsets
                                                                .top)
                                                + max2);
                        f8 =
                                view.getMeasuredHeight()
                                        + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin
                                        + ((RecyclerView.LayoutParams) view.getLayoutParams())
                                                .mDecorInsets
                                                .bottom
                                        + max2
                                        + f10;
                        i37 = i6;
                    }
                    i36 = i7 + 1;
                    flexboxHelper4 = flexboxHelper;
                    i35 = i9;
                    i34 = i8;
                }
                layoutState.mFlexLinePosition += this.mLayoutState.mLayoutDirection;
                i5 = flexLine.mCrossSize;
            }
            i19 += i5;
            if (isMainAxisDirectionHorizontal || !this.mIsRtl) {
                layoutState.mOffset += flexLine.mCrossSize * layoutState.mLayoutDirection;
            } else {
                layoutState.mOffset -= flexLine.mCrossSize * layoutState.mLayoutDirection;
            }
            i18 -= flexLine.mCrossSize;
            i17 = i2;
        }
        int i45 = i17;
        int i46 = layoutState.mAvailable - i19;
        layoutState.mAvailable = i46;
        int i47 = layoutState.mScrollingOffset;
        if (i47 != Integer.MIN_VALUE) {
            int i48 = i47 + i19;
            layoutState.mScrollingOffset = i48;
            if (i46 < 0) {
                layoutState.mScrollingOffset = i48 + i46;
            }
            recycleByLayoutState(recycler, layoutState);
        }
        return i45 - layoutState.mAvailable;
    }

    public final View findFirstReferenceChild(int i) {
        View findReferenceChild = findReferenceChild(0, getChildCount(), i);
        if (findReferenceChild == null) {
            return null;
        }
        int i2 =
                this.mFlexboxHelper
                        .mIndexToFlexLine[
                        RecyclerView.LayoutManager.getPosition(findReferenceChild)];
        if (i2 == -1) {
            return null;
        }
        return findFirstReferenceViewInLine(findReferenceChild, (FlexLine) this.mFlexLines.get(i2));
    }

    public final View findFirstReferenceViewInLine(View view, FlexLine flexLine) {
        boolean isMainAxisDirectionHorizontal = isMainAxisDirectionHorizontal();
        int i = flexLine.mItemCount;
        for (int i2 = 1; i2 < i; i2++) {
            View childAt = getChildAt(i2);
            if (childAt != null && childAt.getVisibility() != 8) {
                if (!this.mIsRtl || isMainAxisDirectionHorizontal) {
                    if (this.mOrientationHelper.getDecoratedStart(view)
                            <= this.mOrientationHelper.getDecoratedStart(childAt)) {}
                    view = childAt;
                } else {
                    if (this.mOrientationHelper.getDecoratedEnd(view)
                            >= this.mOrientationHelper.getDecoratedEnd(childAt)) {}
                    view = childAt;
                }
            }
        }
        return view;
    }

    public final View findLastReferenceChild(int i) {
        View findReferenceChild = findReferenceChild(getChildCount() - 1, -1, i);
        if (findReferenceChild == null) {
            return null;
        }
        return findLastReferenceViewInLine(
                findReferenceChild,
                (FlexLine)
                        this.mFlexLines.get(
                                this.mFlexboxHelper
                                        .mIndexToFlexLine[
                                        RecyclerView.LayoutManager.getPosition(
                                                findReferenceChild)]));
    }

    public final View findLastReferenceViewInLine(View view, FlexLine flexLine) {
        boolean isMainAxisDirectionHorizontal = isMainAxisDirectionHorizontal();
        int childCount = (getChildCount() - flexLine.mItemCount) - 1;
        for (int childCount2 = getChildCount() - 2; childCount2 > childCount; childCount2--) {
            View childAt = getChildAt(childCount2);
            if (childAt != null && childAt.getVisibility() != 8) {
                if (!this.mIsRtl || isMainAxisDirectionHorizontal) {
                    if (this.mOrientationHelper.getDecoratedEnd(view)
                            >= this.mOrientationHelper.getDecoratedEnd(childAt)) {}
                    view = childAt;
                } else {
                    if (this.mOrientationHelper.getDecoratedStart(view)
                            <= this.mOrientationHelper.getDecoratedStart(childAt)) {}
                    view = childAt;
                }
            }
        }
        return view;
    }

    public final View findOneVisibleChild(int i, int i2) {
        int i3 = i2 > i ? 1 : -1;
        while (i != i2) {
            View childAt = getChildAt(i);
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = this.mWidth - getPaddingRight();
            int paddingBottom = this.mHeight - getPaddingBottom();
            int decoratedLeft =
                    RecyclerView.LayoutManager.getDecoratedLeft(childAt)
                            - ((ViewGroup.MarginLayoutParams)
                                            ((RecyclerView.LayoutParams) childAt.getLayoutParams()))
                                    .leftMargin;
            int decoratedTop =
                    RecyclerView.LayoutManager.getDecoratedTop(childAt)
                            - ((ViewGroup.MarginLayoutParams)
                                            ((RecyclerView.LayoutParams) childAt.getLayoutParams()))
                                    .topMargin;
            int decoratedRight =
                    RecyclerView.LayoutManager.getDecoratedRight(childAt)
                            + ((ViewGroup.MarginLayoutParams)
                                            ((RecyclerView.LayoutParams) childAt.getLayoutParams()))
                                    .rightMargin;
            int decoratedBottom =
                    RecyclerView.LayoutManager.getDecoratedBottom(childAt)
                            + ((ViewGroup.MarginLayoutParams)
                                            ((RecyclerView.LayoutParams) childAt.getLayoutParams()))
                                    .bottomMargin;
            boolean z = decoratedLeft >= paddingRight || decoratedRight >= paddingLeft;
            boolean z2 = decoratedTop >= paddingBottom || decoratedBottom >= paddingTop;
            if (z && z2) {
                return childAt;
            }
            i += i3;
        }
        return null;
    }

    public final View findReferenceChild(int i, int i2, int i3) {
        int position;
        ensureOrientationHelper();
        if (this.mLayoutState == null) {
            LayoutState layoutState = new LayoutState();
            layoutState.mItemDirection = 1;
            layoutState.mLayoutDirection = 1;
            this.mLayoutState = layoutState;
        }
        int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
        int i4 = i2 <= i ? -1 : 1;
        View view = null;
        View view2 = null;
        while (i != i2) {
            View childAt = getChildAt(i);
            if (childAt != null
                    && (position = RecyclerView.LayoutManager.getPosition(childAt)) >= 0
                    && position < i3) {
                if (((RecyclerView.LayoutParams) childAt.getLayoutParams())
                        .mViewHolder.isRemoved()) {
                    if (view2 == null) {
                        view2 = childAt;
                    }
                } else {
                    if (this.mOrientationHelper.getDecoratedStart(childAt) >= startAfterPadding
                            && this.mOrientationHelper.getDecoratedEnd(childAt)
                                    <= endAfterPadding) {
                        return childAt;
                    }
                    if (view == null) {
                        view = childAt;
                    }
                }
            }
            i += i4;
        }
        return view != null ? view : view2;
    }

    public final int fixLayoutEndGap$1(
            int i, RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int i2;
        int endAfterPadding;
        if (isMainAxisDirectionHorizontal() || !this.mIsRtl) {
            int endAfterPadding2 = this.mOrientationHelper.getEndAfterPadding() - i;
            if (endAfterPadding2 <= 0) {
                return 0;
            }
            i2 = -handleScrollingMainOrientation(-endAfterPadding2, recycler, state);
        } else {
            int startAfterPadding = i - this.mOrientationHelper.getStartAfterPadding();
            if (startAfterPadding <= 0) {
                return 0;
            }
            i2 = handleScrollingMainOrientation(startAfterPadding, recycler, state);
        }
        int i3 = i + i2;
        if (!z || (endAfterPadding = this.mOrientationHelper.getEndAfterPadding() - i3) <= 0) {
            return i2;
        }
        this.mOrientationHelper.offsetChildren(endAfterPadding);
        return endAfterPadding + i2;
    }

    public final int fixLayoutStartGap$1(
            int i, RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int i2;
        int startAfterPadding;
        if (isMainAxisDirectionHorizontal() || !this.mIsRtl) {
            int startAfterPadding2 = i - this.mOrientationHelper.getStartAfterPadding();
            if (startAfterPadding2 <= 0) {
                return 0;
            }
            i2 = -handleScrollingMainOrientation(startAfterPadding2, recycler, state);
        } else {
            int endAfterPadding = this.mOrientationHelper.getEndAfterPadding() - i;
            if (endAfterPadding <= 0) {
                return 0;
            }
            i2 = handleScrollingMainOrientation(-endAfterPadding, recycler, state);
        }
        int i3 = i + i2;
        if (!z || (startAfterPadding = i3 - this.mOrientationHelper.getStartAfterPadding()) <= 0) {
            return i2;
        }
        this.mOrientationHelper.offsetChildren(-startAfterPadding);
        return i2 - startAfterPadding;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.mFlexGrow = 0.0f;
        layoutParams.mFlexShrink = 1.0f;
        layoutParams.mAlignSelf = -1;
        layoutParams.mFlexBasisPercent = -1.0f;
        layoutParams.mMaxWidth = 16777215;
        layoutParams.mMaxHeight = 16777215;
        return layoutParams;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateLayoutParams(
            Context context, AttributeSet attributeSet) {
        LayoutParams layoutParams = new LayoutParams(context, attributeSet);
        layoutParams.mFlexGrow = 0.0f;
        layoutParams.mFlexShrink = 1.0f;
        layoutParams.mAlignSelf = -1;
        layoutParams.mFlexBasisPercent = -1.0f;
        layoutParams.mMaxWidth = 16777215;
        layoutParams.mMaxHeight = 16777215;
        return layoutParams;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getAlignContent() {
        return 5;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getAlignItems() {
        return this.mAlignItems;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getChildHeightMeasureSpec(int i, int i2, int i3) {
        return RecyclerView.LayoutManager.getChildMeasureSpec(
                canScrollVertically(), this.mHeight, this.mHeightMode, i2, i3);
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getChildWidthMeasureSpec(int i, int i2, int i3) {
        return RecyclerView.LayoutManager.getChildMeasureSpec(
                canScrollHorizontally(), this.mWidth, this.mWidthMode, i2, i3);
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getDecorationLengthCrossAxis(View view) {
        return isMainAxisDirectionHorizontal()
                ? ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.top
                        + ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.bottom
                : ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.left
                        + ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.right;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getDecorationLengthMainAxis(View view, int i, int i2) {
        return isMainAxisDirectionHorizontal()
                ? ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.left
                        + ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.right
                : ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.top
                        + ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.bottom;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getFlexDirection() {
        return this.mFlexDirection;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final View getFlexItemAt(int i) {
        View view = (View) this.mViewCache.get(i);
        return view != null
                ? view
                : this.mRecycler.tryGetViewHolderForPositionByDeadline(i, Long.MAX_VALUE).itemView;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getFlexItemCount() {
        return this.mState.getItemCount();
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
        if (this.mFlexLines.size() == 0) {
            return 0;
        }
        int size = this.mFlexLines.size();
        int i = Integer.MIN_VALUE;
        for (int i2 = 0; i2 < size; i2++) {
            i = Math.max(i, ((FlexLine) this.mFlexLines.get(i2)).mMainSize);
        }
        return i;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getMaxLine() {
        return this.mMaxLine;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final View getReorderedFlexItemAt(int i) {
        return getFlexItemAt(i);
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final int getSumOfCrossSize() {
        int size = this.mFlexLines.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += ((FlexLine) this.mFlexLines.get(i2)).mCrossSize;
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x01de A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int handleScrollingMainOrientation(
            int r19,
            androidx.recyclerview.widget.RecyclerView.Recycler r20,
            androidx.recyclerview.widget.RecyclerView.State r21) {
        /*
            Method dump skipped, instructions count: 505
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.flexbox.FlexboxLayoutManager.handleScrollingMainOrientation(int,"
                    + " androidx.recyclerview.widget.RecyclerView$Recycler,"
                    + " androidx.recyclerview.widget.RecyclerView$State):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0041, code lost:

       if ((r4 + r5) > 0) goto L26;
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0056, code lost:

       r5 = -r4;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0053, code lost:

       if ((r4 + r5) >= 0) goto L27;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int handleScrollingSubOrientation(int r5) {
        /*
            r4 = this;
            int r0 = r4.getChildCount()
            if (r0 == 0) goto L59
            if (r5 != 0) goto L9
            goto L59
        L9:
            r4.ensureOrientationHelper()
            boolean r0 = r4.isMainAxisDirectionHorizontal()
            android.view.View r1 = r4.mParent
            if (r0 == 0) goto L19
            int r1 = r1.getWidth()
            goto L1d
        L19:
            int r1 = r1.getHeight()
        L1d:
            if (r0 == 0) goto L22
            int r0 = r4.mWidth
            goto L24
        L22:
            int r0 = r4.mHeight
        L24:
            int r2 = r4.getLayoutDirection()
            r3 = 1
            com.google.android.flexbox.FlexboxLayoutManager$AnchorInfo r4 = r4.mAnchorInfo
            if (r2 != r3) goto L44
            int r2 = java.lang.Math.abs(r5)
            if (r5 >= 0) goto L3d
            int r4 = r4.mPerpendicularCoordinate
            int r0 = r0 + r4
            int r0 = r0 - r1
            int r4 = java.lang.Math.min(r0, r2)
            int r4 = -r4
            goto L58
        L3d:
            int r4 = r4.mPerpendicularCoordinate
            int r0 = r4 + r5
            if (r0 <= 0) goto L57
            goto L56
        L44:
            if (r5 <= 0) goto L4f
            int r4 = r4.mPerpendicularCoordinate
            int r0 = r0 - r4
            int r0 = r0 - r1
            int r4 = java.lang.Math.min(r0, r5)
            goto L58
        L4f:
            int r4 = r4.mPerpendicularCoordinate
            int r0 = r4 + r5
            if (r0 < 0) goto L56
            goto L57
        L56:
            int r5 = -r4
        L57:
            r4 = r5
        L58:
            return r4
        L59:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.flexbox.FlexboxLayoutManager.handleScrollingSubOrientation(int):int");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean isAutoMeasureEnabled() {
        return true;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final boolean isMainAxisDirectionHorizontal() {
        int i = this.mFlexDirection;
        return i == 0 || i == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onAdapterChanged() {
        removeAllViews();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onAttachedToWindow(RecyclerView recyclerView) {
        this.mParent = (View) recyclerView.getParent();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsAdded(int i, int i2) {
        updateDirtyPosition(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsMoved(int i, int i2) {
        updateDirtyPosition(Math.min(i, i2));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsRemoved(int i, int i2) {
        updateDirtyPosition(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsUpdated(int i) {
        updateDirtyPosition(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i;
        View childAt;
        boolean z;
        int i2;
        int i3;
        int i4;
        FlexboxHelper.FlexLinesResult flexLinesResult;
        int i5;
        this.mRecycler = recycler;
        this.mState = state;
        int itemCount = state.getItemCount();
        if (itemCount == 0 && state.mInPreLayout) {
            return;
        }
        int layoutDirection = getLayoutDirection();
        int i6 = this.mFlexDirection;
        if (i6 == 0) {
            this.mIsRtl = layoutDirection == 1;
            this.mFromBottomToTop = this.mFlexWrap == 2;
        } else if (i6 == 1) {
            this.mIsRtl = layoutDirection != 1;
            this.mFromBottomToTop = this.mFlexWrap == 2;
        } else if (i6 == 2) {
            boolean z2 = layoutDirection == 1;
            this.mIsRtl = z2;
            if (this.mFlexWrap == 2) {
                this.mIsRtl = !z2;
            }
            this.mFromBottomToTop = false;
        } else if (i6 != 3) {
            this.mIsRtl = false;
            this.mFromBottomToTop = false;
        } else {
            boolean z3 = layoutDirection == 1;
            this.mIsRtl = z3;
            if (this.mFlexWrap == 2) {
                this.mIsRtl = !z3;
            }
            this.mFromBottomToTop = true;
        }
        ensureOrientationHelper();
        if (this.mLayoutState == null) {
            LayoutState layoutState = new LayoutState();
            layoutState.mItemDirection = 1;
            layoutState.mLayoutDirection = 1;
            this.mLayoutState = layoutState;
        }
        FlexboxHelper flexboxHelper = this.mFlexboxHelper;
        flexboxHelper.ensureMeasureSpecCache(itemCount);
        flexboxHelper.ensureMeasuredSizeCache(itemCount);
        flexboxHelper.ensureIndexToFlexLine(itemCount);
        this.mLayoutState.mShouldRecycle = false;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null && (i5 = savedState.mAnchorPosition) >= 0 && i5 < itemCount) {
            this.mPendingScrollPosition = i5;
        }
        AnchorInfo anchorInfo = this.mAnchorInfo;
        if (!anchorInfo.mValid || this.mPendingScrollPosition != -1 || savedState != null) {
            AnchorInfo.access$800(anchorInfo);
            SavedState savedState2 = this.mPendingSavedState;
            if (!state.mInPreLayout && (i = this.mPendingScrollPosition) != -1) {
                if (i < 0 || i >= state.getItemCount()) {
                    this.mPendingScrollPosition = -1;
                    this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
                } else {
                    int i7 = this.mPendingScrollPosition;
                    anchorInfo.mPosition = i7;
                    anchorInfo.mFlexLinePosition = flexboxHelper.mIndexToFlexLine[i7];
                    SavedState savedState3 = this.mPendingSavedState;
                    if (savedState3 != null) {
                        int itemCount2 = state.getItemCount();
                        int i8 = savedState3.mAnchorPosition;
                        if (i8 >= 0 && i8 < itemCount2) {
                            anchorInfo.mCoordinate =
                                    this.mOrientationHelper.getStartAfterPadding()
                                            + savedState2.mAnchorOffset;
                            anchorInfo.mAssignedFromSavedState = true;
                            anchorInfo.mFlexLinePosition = -1;
                            anchorInfo.mValid = true;
                        }
                    }
                    if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
                        View findViewByPosition = findViewByPosition(this.mPendingScrollPosition);
                        if (findViewByPosition == null) {
                            if (getChildCount() > 0 && (childAt = getChildAt(0)) != null) {
                                anchorInfo.mLayoutFromEnd =
                                        this.mPendingScrollPosition
                                                < RecyclerView.LayoutManager.getPosition(childAt);
                            }
                            AnchorInfo.access$1600(anchorInfo);
                        } else if (this.mOrientationHelper.getDecoratedMeasurement(
                                        findViewByPosition)
                                > this.mOrientationHelper.getTotalSpace()) {
                            AnchorInfo.access$1600(anchorInfo);
                        } else if (this.mOrientationHelper.getDecoratedStart(findViewByPosition)
                                        - this.mOrientationHelper.getStartAfterPadding()
                                < 0) {
                            anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding();
                            anchorInfo.mLayoutFromEnd = false;
                        } else if (this.mOrientationHelper.getEndAfterPadding()
                                        - this.mOrientationHelper.getDecoratedEnd(
                                                findViewByPosition)
                                < 0) {
                            anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding();
                            anchorInfo.mLayoutFromEnd = true;
                        } else {
                            anchorInfo.mCoordinate =
                                    anchorInfo.mLayoutFromEnd
                                            ? this.mOrientationHelper.getTotalSpaceChange()
                                                    + this.mOrientationHelper.getDecoratedEnd(
                                                            findViewByPosition)
                                            : this.mOrientationHelper.getDecoratedStart(
                                                    findViewByPosition);
                        }
                    } else if (isMainAxisDirectionHorizontal() || !this.mIsRtl) {
                        anchorInfo.mCoordinate =
                                this.mOrientationHelper.getStartAfterPadding()
                                        + this.mPendingScrollPositionOffset;
                    } else {
                        anchorInfo.mCoordinate =
                                this.mPendingScrollPositionOffset
                                        - this.mOrientationHelper.getEndPadding();
                    }
                    anchorInfo.mValid = true;
                }
            }
            if (getChildCount() != 0) {
                View findLastReferenceChild =
                        anchorInfo.mLayoutFromEnd
                                ? findLastReferenceChild(state.getItemCount())
                                : findFirstReferenceChild(state.getItemCount());
                if (findLastReferenceChild != null) {
                    FlexboxLayoutManager flexboxLayoutManager = FlexboxLayoutManager.this;
                    OrientationHelper$1 orientationHelper$1 =
                            flexboxLayoutManager.mFlexWrap == 0
                                    ? flexboxLayoutManager.mSubOrientationHelper
                                    : flexboxLayoutManager.mOrientationHelper;
                    if (flexboxLayoutManager.isMainAxisDirectionHorizontal()
                            || !flexboxLayoutManager.mIsRtl) {
                        if (anchorInfo.mLayoutFromEnd) {
                            anchorInfo.mCoordinate =
                                    orientationHelper$1.getTotalSpaceChange()
                                            + orientationHelper$1.getDecoratedEnd(
                                                    findLastReferenceChild);
                        } else {
                            anchorInfo.mCoordinate =
                                    orientationHelper$1.getDecoratedStart(findLastReferenceChild);
                        }
                    } else if (anchorInfo.mLayoutFromEnd) {
                        anchorInfo.mCoordinate =
                                orientationHelper$1.getTotalSpaceChange()
                                        + orientationHelper$1.getDecoratedStart(
                                                findLastReferenceChild);
                    } else {
                        anchorInfo.mCoordinate =
                                orientationHelper$1.getDecoratedEnd(findLastReferenceChild);
                    }
                    int position = RecyclerView.LayoutManager.getPosition(findLastReferenceChild);
                    anchorInfo.mPosition = position;
                    anchorInfo.mAssignedFromSavedState = false;
                    int[] iArr = flexboxLayoutManager.mFlexboxHelper.mIndexToFlexLine;
                    if (position == -1) {
                        position = 0;
                    }
                    int i9 = iArr[position];
                    if (i9 == -1) {
                        i9 = 0;
                    }
                    anchorInfo.mFlexLinePosition = i9;
                    int size = flexboxLayoutManager.mFlexLines.size();
                    int i10 = anchorInfo.mFlexLinePosition;
                    if (size > i10) {
                        anchorInfo.mPosition =
                                ((FlexLine) flexboxLayoutManager.mFlexLines.get(i10)).mFirstIndex;
                    }
                    anchorInfo.mValid = true;
                }
            }
            AnchorInfo.access$1600(anchorInfo);
            anchorInfo.mPosition = 0;
            anchorInfo.mFlexLinePosition = 0;
            anchorInfo.mValid = true;
        }
        detachAndScrapAttachedViews(recycler);
        if (anchorInfo.mLayoutFromEnd) {
            updateLayoutStateToFillStart(anchorInfo, false, true);
        } else {
            updateLayoutStateToFillEnd(anchorInfo, false, true);
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mWidth, this.mWidthMode);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mHeight, this.mHeightMode);
        int i11 = this.mWidth;
        int i12 = this.mHeight;
        if (isMainAxisDirectionHorizontal()) {
            int i13 = this.mLastWidth;
            z = (i13 == Integer.MIN_VALUE || i13 == i11) ? false : true;
            LayoutState layoutState2 = this.mLayoutState;
            i2 =
                    layoutState2.mInfinite
                            ? this.mContext.getResources().getDisplayMetrics().heightPixels
                            : layoutState2.mAvailable;
        } else {
            int i14 = this.mLastHeight;
            z = (i14 == Integer.MIN_VALUE || i14 == i12) ? false : true;
            LayoutState layoutState3 = this.mLayoutState;
            i2 =
                    layoutState3.mInfinite
                            ? this.mContext.getResources().getDisplayMetrics().widthPixels
                            : layoutState3.mAvailable;
        }
        int i15 = i2;
        this.mLastWidth = i11;
        this.mLastHeight = i12;
        int i16 = this.mDirtyPosition;
        FlexboxHelper.FlexLinesResult flexLinesResult2 = this.mFlexLinesResult;
        if (i16 != -1 || (this.mPendingScrollPosition == -1 && !z)) {
            int min = i16 != -1 ? Math.min(i16, anchorInfo.mPosition) : anchorInfo.mPosition;
            flexLinesResult2.mFlexLines = null;
            flexLinesResult2.mChildState = 0;
            if (isMainAxisDirectionHorizontal()) {
                if (this.mFlexLines.size() > 0) {
                    flexboxHelper.clearFlexLines(min, this.mFlexLines);
                    this.mFlexboxHelper.calculateFlexLines(
                            this.mFlexLinesResult,
                            makeMeasureSpec,
                            makeMeasureSpec2,
                            i15,
                            min,
                            anchorInfo.mPosition,
                            this.mFlexLines);
                } else {
                    flexboxHelper.ensureIndexToFlexLine(itemCount);
                    this.mFlexboxHelper.calculateFlexLines(
                            this.mFlexLinesResult,
                            makeMeasureSpec,
                            makeMeasureSpec2,
                            i15,
                            0,
                            -1,
                            this.mFlexLines);
                }
            } else if (this.mFlexLines.size() > 0) {
                flexboxHelper.clearFlexLines(min, this.mFlexLines);
                this.mFlexboxHelper.calculateFlexLines(
                        this.mFlexLinesResult,
                        makeMeasureSpec2,
                        makeMeasureSpec,
                        i15,
                        min,
                        anchorInfo.mPosition,
                        this.mFlexLines);
            } else {
                flexboxHelper.ensureIndexToFlexLine(itemCount);
                this.mFlexboxHelper.calculateFlexLines(
                        this.mFlexLinesResult,
                        makeMeasureSpec2,
                        makeMeasureSpec,
                        i15,
                        0,
                        -1,
                        this.mFlexLines);
            }
            this.mFlexLines = flexLinesResult2.mFlexLines;
            flexboxHelper.determineMainSize(makeMeasureSpec, makeMeasureSpec2, min);
            flexboxHelper.stretchViews(min);
        } else if (!anchorInfo.mLayoutFromEnd) {
            this.mFlexLines.clear();
            flexLinesResult2.mFlexLines = null;
            flexLinesResult2.mChildState = 0;
            if (isMainAxisDirectionHorizontal()) {
                flexLinesResult = flexLinesResult2;
                this.mFlexboxHelper.calculateFlexLines(
                        this.mFlexLinesResult,
                        makeMeasureSpec,
                        makeMeasureSpec2,
                        i15,
                        0,
                        anchorInfo.mPosition,
                        this.mFlexLines);
            } else {
                flexLinesResult = flexLinesResult2;
                this.mFlexboxHelper.calculateFlexLines(
                        this.mFlexLinesResult,
                        makeMeasureSpec2,
                        makeMeasureSpec,
                        i15,
                        0,
                        anchorInfo.mPosition,
                        this.mFlexLines);
            }
            this.mFlexLines = flexLinesResult.mFlexLines;
            flexboxHelper.determineMainSize(makeMeasureSpec, makeMeasureSpec2, 0);
            flexboxHelper.stretchViews(0);
            int i17 = flexboxHelper.mIndexToFlexLine[anchorInfo.mPosition];
            anchorInfo.mFlexLinePosition = i17;
            this.mLayoutState.mFlexLinePosition = i17;
        }
        fill(recycler, state, this.mLayoutState);
        if (anchorInfo.mLayoutFromEnd) {
            i4 = this.mLayoutState.mOffset;
            updateLayoutStateToFillEnd(anchorInfo, true, false);
            fill(recycler, state, this.mLayoutState);
            i3 = this.mLayoutState.mOffset;
        } else {
            i3 = this.mLayoutState.mOffset;
            updateLayoutStateToFillStart(anchorInfo, true, false);
            fill(recycler, state, this.mLayoutState);
            i4 = this.mLayoutState.mOffset;
        }
        if (getChildCount() > 0) {
            if (anchorInfo.mLayoutFromEnd) {
                fixLayoutStartGap$1(
                        fixLayoutEndGap$1(i3, recycler, state, true) + i4, recycler, state, false);
            } else {
                fixLayoutEndGap$1(
                        fixLayoutStartGap$1(i4, recycler, state, true) + i3,
                        recycler,
                        state,
                        false);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutCompleted(RecyclerView.State state) {
        this.mPendingSavedState = null;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mDirtyPosition = -1;
        AnchorInfo.access$800(this.mAnchorInfo);
        this.mViewCache.clear();
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final void onNewFlexItemAdded(View view, int i, int i2, FlexLine flexLine) {
        calculateItemDecorationsForChild(TEMP_RECT, view);
        if (isMainAxisDirectionHorizontal()) {
            int i3 =
                    ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.left
                            + ((RecyclerView.LayoutParams) view.getLayoutParams())
                                    .mDecorInsets
                                    .right;
            flexLine.mMainSize += i3;
            flexLine.mDividerLengthInMainSize += i3;
        } else {
            int i4 =
                    ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.top
                            + ((RecyclerView.LayoutParams) view.getLayoutParams())
                                    .mDecorInsets
                                    .bottom;
            flexLine.mMainSize += i4;
            flexLine.mDividerLengthInMainSize += i4;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.mPendingSavedState = (SavedState) parcelable;
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final Parcelable onSaveInstanceState() {
        if (this.mPendingSavedState != null) {
            SavedState savedState = this.mPendingSavedState;
            SavedState savedState2 = new SavedState();
            savedState2.mAnchorPosition = savedState.mAnchorPosition;
            savedState2.mAnchorOffset = savedState.mAnchorOffset;
            return savedState2;
        }
        SavedState savedState3 = new SavedState();
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            savedState3.mAnchorPosition = RecyclerView.LayoutManager.getPosition(childAt);
            savedState3.mAnchorOffset =
                    this.mOrientationHelper.getDecoratedStart(childAt)
                            - this.mOrientationHelper.getStartAfterPadding();
        } else {
            savedState3.mAnchorPosition = -1;
        }
        return savedState3;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x007f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0112 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void recycleByLayoutState(
            androidx.recyclerview.widget.RecyclerView.Recycler r10,
            com.google.android.flexbox.FlexboxLayoutManager.LayoutState r11) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.flexbox.FlexboxLayoutManager.recycleByLayoutState(androidx.recyclerview.widget.RecyclerView$Recycler,"
                    + " com.google.android.flexbox.FlexboxLayoutManager$LayoutState):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollHorizontallyBy(
            int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!isMainAxisDirectionHorizontal() || this.mFlexWrap == 0) {
            int handleScrollingMainOrientation = handleScrollingMainOrientation(i, recycler, state);
            this.mViewCache.clear();
            return handleScrollingMainOrientation;
        }
        int handleScrollingSubOrientation = handleScrollingSubOrientation(i);
        this.mAnchorInfo.mPerpendicularCoordinate += handleScrollingSubOrientation;
        this.mSubOrientationHelper.offsetChildren(-handleScrollingSubOrientation);
        return handleScrollingSubOrientation;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void scrollToPosition(int i) {
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            savedState.mAnchorPosition = -1;
        }
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollVerticallyBy(
            int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (isMainAxisDirectionHorizontal()
                || (this.mFlexWrap == 0 && !isMainAxisDirectionHorizontal())) {
            int handleScrollingMainOrientation = handleScrollingMainOrientation(i, recycler, state);
            this.mViewCache.clear();
            return handleScrollingMainOrientation;
        }
        int handleScrollingSubOrientation = handleScrollingSubOrientation(i);
        this.mAnchorInfo.mPerpendicularCoordinate += handleScrollingSubOrientation;
        this.mSubOrientationHelper.offsetChildren(-handleScrollingSubOrientation);
        return handleScrollingSubOrientation;
    }

    public final void setFlexDirection(int i) {
        if (this.mFlexDirection != i) {
            removeAllViews();
            this.mFlexDirection = i;
            this.mOrientationHelper = null;
            this.mSubOrientationHelper = null;
            this.mFlexLines.clear();
            AnchorInfo anchorInfo = this.mAnchorInfo;
            AnchorInfo.access$800(anchorInfo);
            anchorInfo.mPerpendicularCoordinate = 0;
            requestLayout();
        }
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final void setFlexLines(List list) {
        this.mFlexLines = list;
    }

    public final boolean shouldMeasureChild(View view, int i, int i2, LayoutParams layoutParams) {
        return (!view.isLayoutRequested()
                        && this.mMeasurementCacheEnabled
                        && isMeasurementUpToDate(
                                view.getWidth(),
                                i,
                                ((ViewGroup.MarginLayoutParams) layoutParams).width)
                        && isMeasurementUpToDate(
                                view.getHeight(),
                                i2,
                                ((ViewGroup.MarginLayoutParams) layoutParams).height))
                ? false
                : true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void smoothScrollToPosition(int i, RecyclerView recyclerView) {
        LinearSmoothScroller linearSmoothScroller =
                new LinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.mTargetPosition = i;
        startSmoothScroll(linearSmoothScroller);
    }

    public final void updateDirtyPosition(int i) {
        View findOneVisibleChild = findOneVisibleChild(getChildCount() - 1, -1);
        if (i
                >= (findOneVisibleChild != null
                        ? RecyclerView.LayoutManager.getPosition(findOneVisibleChild)
                        : -1)) {
            return;
        }
        int childCount = getChildCount();
        FlexboxHelper flexboxHelper = this.mFlexboxHelper;
        flexboxHelper.ensureMeasureSpecCache(childCount);
        flexboxHelper.ensureMeasuredSizeCache(childCount);
        flexboxHelper.ensureIndexToFlexLine(childCount);
        if (i >= flexboxHelper.mIndexToFlexLine.length) {
            return;
        }
        this.mDirtyPosition = i;
        View childAt = getChildAt(0);
        if (childAt == null) {
            return;
        }
        this.mPendingScrollPosition = RecyclerView.LayoutManager.getPosition(childAt);
        if (isMainAxisDirectionHorizontal() || !this.mIsRtl) {
            this.mPendingScrollPositionOffset =
                    this.mOrientationHelper.getDecoratedStart(childAt)
                            - this.mOrientationHelper.getStartAfterPadding();
        } else {
            this.mPendingScrollPositionOffset =
                    this.mOrientationHelper.getEndPadding()
                            + this.mOrientationHelper.getDecoratedEnd(childAt);
        }
    }

    public final void updateLayoutStateToFillEnd(AnchorInfo anchorInfo, boolean z, boolean z2) {
        int i;
        if (z2) {
            int i2 = isMainAxisDirectionHorizontal() ? this.mHeightMode : this.mWidthMode;
            this.mLayoutState.mInfinite = i2 == 0 || i2 == Integer.MIN_VALUE;
        } else {
            this.mLayoutState.mInfinite = false;
        }
        if (isMainAxisDirectionHorizontal() || !this.mIsRtl) {
            this.mLayoutState.mAvailable =
                    this.mOrientationHelper.getEndAfterPadding() - anchorInfo.mCoordinate;
        } else {
            this.mLayoutState.mAvailable = anchorInfo.mCoordinate - getPaddingRight();
        }
        LayoutState layoutState = this.mLayoutState;
        layoutState.mPosition = anchorInfo.mPosition;
        layoutState.mItemDirection = 1;
        layoutState.mLayoutDirection = 1;
        layoutState.mOffset = anchorInfo.mCoordinate;
        layoutState.mScrollingOffset = Integer.MIN_VALUE;
        layoutState.mFlexLinePosition = anchorInfo.mFlexLinePosition;
        if (!z
                || this.mFlexLines.size() <= 1
                || (i = anchorInfo.mFlexLinePosition) < 0
                || i >= this.mFlexLines.size() - 1) {
            return;
        }
        FlexLine flexLine = (FlexLine) this.mFlexLines.get(anchorInfo.mFlexLinePosition);
        LayoutState layoutState2 = this.mLayoutState;
        layoutState2.mFlexLinePosition++;
        layoutState2.mPosition += flexLine.mItemCount;
    }

    public final void updateLayoutStateToFillStart(AnchorInfo anchorInfo, boolean z, boolean z2) {
        if (z2) {
            int i = isMainAxisDirectionHorizontal() ? this.mHeightMode : this.mWidthMode;
            this.mLayoutState.mInfinite = i == 0 || i == Integer.MIN_VALUE;
        } else {
            this.mLayoutState.mInfinite = false;
        }
        if (isMainAxisDirectionHorizontal() || !this.mIsRtl) {
            this.mLayoutState.mAvailable =
                    anchorInfo.mCoordinate - this.mOrientationHelper.getStartAfterPadding();
        } else {
            this.mLayoutState.mAvailable =
                    (this.mParent.getWidth() - anchorInfo.mCoordinate)
                            - this.mOrientationHelper.getStartAfterPadding();
        }
        LayoutState layoutState = this.mLayoutState;
        layoutState.mPosition = anchorInfo.mPosition;
        layoutState.mItemDirection = 1;
        layoutState.mLayoutDirection = -1;
        layoutState.mOffset = anchorInfo.mCoordinate;
        layoutState.mScrollingOffset = Integer.MIN_VALUE;
        int i2 = anchorInfo.mFlexLinePosition;
        layoutState.mFlexLinePosition = i2;
        if (!z || i2 <= 0) {
            return;
        }
        int size = this.mFlexLines.size();
        int i3 = anchorInfo.mFlexLinePosition;
        if (size > i3) {
            FlexLine flexLine = (FlexLine) this.mFlexLines.get(i3);
            LayoutState layoutState2 = this.mLayoutState;
            layoutState2.mFlexLinePosition--;
            layoutState2.mPosition -= flexLine.mItemCount;
        }
    }

    @Override // com.google.android.flexbox.FlexContainer
    public final void updateViewCache(View view, int i) {
        this.mViewCache.put(i, view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
        updateDirtyPosition(i);
        updateDirtyPosition(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onDetachedFromWindow(RecyclerView recyclerView) {}

    @Override // com.google.android.flexbox.FlexContainer
    public final void onNewFlexLineAdded(FlexLine flexLine) {}
}
