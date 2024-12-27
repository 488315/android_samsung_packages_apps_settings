package com.android.settings.dream;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    public final boolean mRtl;
    public final int mSpacing;

    public GridSpacingItemDecoration(Context context) {
        this.mSpacing =
                context.getResources().getDimensionPixelSize(R.dimen.dream_preference_card_padding);
        this.mRtl = context.getResources().getConfiguration().getLayoutDirection() == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void getItemOffsets(
            Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int i = ((GridLayoutManager) layoutManager).mSpanCount;
            int childAdapterPosition = RecyclerView.getChildAdapterPosition(view);
            int i2 = childAdapterPosition % i;
            int i3 = this.mSpacing;
            int i4 = (i2 * i3) / i;
            int i5 = i3 - (((i2 + 1) * i3) / i);
            boolean z = this.mRtl;
            rect.left = z ? i5 : i4;
            if (!z) {
                i4 = i5;
            }
            rect.right = i4;
            if (childAdapterPosition >= i) {
                rect.top = i3;
            }
        }
    }
}
