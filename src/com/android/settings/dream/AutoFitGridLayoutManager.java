package com.android.settings.dream;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutoFitGridLayoutManager extends GridLayoutManager {
    public final float mColumnWidth;

    public AutoFitGridLayoutManager(Context context) {
        super(1);
        this.mColumnWidth =
                context.getResources().getDimensionPixelSize(R.dimen.dream_item_min_column_width);
    }

    @Override // androidx.recyclerview.widget.GridLayoutManager,
              // androidx.recyclerview.widget.LinearLayoutManager,
              // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        setSpanCount(
                Math.max(
                        1,
                        (int)
                                (((this.mWidth - getPaddingRight()) - getPaddingLeft())
                                        / this.mColumnWidth)));
        super.onLayoutChildren(recycler, state);
    }
}
