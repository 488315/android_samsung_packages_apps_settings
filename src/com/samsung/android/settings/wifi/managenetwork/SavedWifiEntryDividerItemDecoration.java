package com.samsung.android.settings.wifi.managenetwork;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SavedWifiEntryDividerItemDecoration extends DividerItemDecoration {
    public final Drawable mDivider;

    public SavedWifiEntryDividerItemDecoration(Context context, int i) {
        super(context, i);
        this.mDivider =
                context.getResources()
                        .getDrawable(R.drawable.sec_wifi_saved_access_points_list_divider, null);
    }

    @Override // androidx.recyclerview.widget.DividerItemDecoration,
              // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void getItemOffsets(
            Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        rect.set(0, 0, 0, this.mDivider.getIntrinsicHeight());
    }

    @Override // androidx.recyclerview.widget.DividerItemDecoration,
              // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
        int width;
        int i;
        Rect rect = new Rect();
        if (recyclerView.getClipToPadding()) {
            i = recyclerView.getPaddingLeft();
            width = recyclerView.getWidth() - recyclerView.getPaddingRight();
        } else {
            width = recyclerView.getWidth();
            i = 0;
        }
        for (int i2 = 0;
                i2 < recyclerView.getChildCount() && i2 != recyclerView.getChildCount() - 1;
                i2++) {
            View childAt = recyclerView.getChildAt(i2);
            RecyclerView.getDecoratedBoundsWithMarginsInt(rect, childAt);
            int round = Math.round(childAt.getTranslationY()) + rect.bottom;
            this.mDivider.setBounds(i, round - this.mDivider.getIntrinsicHeight(), width, round);
            this.mDivider.draw(canvas);
        }
    }
}
