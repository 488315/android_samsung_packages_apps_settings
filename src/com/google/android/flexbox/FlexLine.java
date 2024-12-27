package com.google.android.flexbox;

import android.view.View;

import androidx.preference.Preference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FlexLine {
    public boolean mAnyItemsHaveFlexGrow;
    public boolean mAnyItemsHaveFlexShrink;
    public int mCrossSize;
    public int mDividerLengthInMainSize;
    public int mFirstIndex;
    public int mGoneItemCount;
    public int mItemCount;
    public int mLastIndex;
    public int mMainSize;
    public int mMaxBaseline;
    public int mSumCrossSizeBefore;
    public float mTotalFlexGrow;
    public float mTotalFlexShrink;
    public int mLeft = Preference.DEFAULT_ORDER;
    public int mTop = Preference.DEFAULT_ORDER;
    public int mRight = Integer.MIN_VALUE;
    public int mBottom = Integer.MIN_VALUE;
    public final List mIndicesAlignSelfStretch = new ArrayList();

    public final int getItemCountNotGone() {
        return this.mItemCount - this.mGoneItemCount;
    }

    public final void updatePositionFromView(View view, int i, int i2, int i3, int i4) {
        FlexItem flexItem = (FlexItem) view.getLayoutParams();
        this.mLeft = Math.min(this.mLeft, (view.getLeft() - flexItem.getMarginLeft()) - i);
        this.mTop = Math.min(this.mTop, (view.getTop() - flexItem.getMarginTop()) - i2);
        this.mRight = Math.max(this.mRight, flexItem.getMarginRight() + view.getRight() + i3);
        this.mBottom = Math.max(this.mBottom, flexItem.getMarginBottom() + view.getBottom() + i4);
    }
}
