package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SpanningLinearLayoutManager extends LinearLayoutManager {
    public SpanningLinearLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager,
              // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollHorizontally() {
        return false;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager,
              // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollVertically() {
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams != null;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager,
              // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateDefaultLayoutParams() {
        RecyclerView.LayoutParams generateDefaultLayoutParams = super.generateDefaultLayoutParams();
        spanLayoutSize(generateDefaultLayoutParams);
        return generateDefaultLayoutParams;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateLayoutParams(
            ViewGroup.LayoutParams layoutParams) {
        RecyclerView.LayoutParams generateLayoutParams = super.generateLayoutParams(layoutParams);
        spanLayoutSize(generateLayoutParams);
        return generateLayoutParams;
    }

    public final void spanLayoutSize(RecyclerView.LayoutParams layoutParams) {
        int i = this.mOrientation;
        if (i == 0) {
            ((ViewGroup.MarginLayoutParams) layoutParams).width =
                    (int)
                            Math.round(
                                    ((this.mWidth - getPaddingRight()) - getPaddingLeft())
                                            / getItemCount());
        } else if (i == 1) {
            ((ViewGroup.MarginLayoutParams) layoutParams).height =
                    (int)
                            Math.round(
                                    ((this.mHeight - getPaddingBottom()) - getPaddingTop())
                                            / getItemCount());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateLayoutParams(
            Context context, AttributeSet attributeSet) {
        RecyclerView.LayoutParams layoutParams =
                new RecyclerView.LayoutParams(context, attributeSet);
        spanLayoutSize(layoutParams);
        return layoutParams;
    }
}
