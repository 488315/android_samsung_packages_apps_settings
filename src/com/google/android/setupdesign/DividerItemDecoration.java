package com.google.android.setupdesign;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    public Drawable divider;
    public int dividerCondition;
    public final int dividerHeight;
    public int dividerIntrinsicHeight;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DividedViewHolder {
        boolean isDividerAllowedAbove();

        boolean isDividerAllowedBelow();
    }

    public DividerItemDecoration(Context context) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(R$styleable.SudDividerItemDecoration);
        Drawable drawable = obtainStyledAttributes.getDrawable(1);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        int i = obtainStyledAttributes.getInt(2, 0);
        obtainStyledAttributes.recycle();
        if (drawable != null) {
            this.dividerIntrinsicHeight = drawable.getIntrinsicHeight();
        } else {
            this.dividerIntrinsicHeight = 0;
        }
        this.divider = drawable;
        this.dividerHeight = dimensionPixelSize;
        this.dividerCondition = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void getItemOffsets(
            Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (shouldDrawDividerBelow$1(view, recyclerView)) {
            int i = this.dividerHeight;
            if (i == 0) {
                i = this.dividerIntrinsicHeight;
            }
            rect.bottom = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isDividerAllowedAbove(RecyclerView.ViewHolder viewHolder) {
        return !(viewHolder instanceof DividedViewHolder)
                || ((DividedViewHolder) viewHolder).isDividerAllowedAbove();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isDividerAllowedBelow(RecyclerView.ViewHolder viewHolder) {
        return !(viewHolder instanceof DividedViewHolder)
                || ((DividedViewHolder) viewHolder).isDividerAllowedBelow();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
        if (this.divider == null) {
            return;
        }
        int childCount = recyclerView.getChildCount();
        int width = recyclerView.getWidth();
        int i = this.dividerHeight;
        if (i == 0) {
            i = this.dividerIntrinsicHeight;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = recyclerView.getChildAt(i2);
            if (shouldDrawDividerBelow$1(childAt, recyclerView)) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                int height = childAt.getHeight() + ((int) childAt.getY());
                this.divider.setBounds(0, height, width, height + i);
                this.divider.draw(canvas);
            }
        }
    }

    public final boolean shouldDrawDividerBelow$1(View view, RecyclerView recyclerView) {
        RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(view);
        int layoutPosition = childViewHolder.getLayoutPosition();
        int itemCount = recyclerView.getAdapter().getItemCount() - 1;
        if (isDividerAllowedBelow(childViewHolder)) {
            if (this.dividerCondition == 0) {
                return true;
            }
        } else if (this.dividerCondition == 1 || layoutPosition == itemCount) {
            return false;
        }
        return layoutPosition >= itemCount
                || isDividerAllowedAbove(
                        recyclerView.findViewHolderForPosition(layoutPosition + 1, false));
    }
}
