package com.samsung.android.settings.asbase.vibration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.util.SeslRoundedCorner;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RecyclerViewDecoration extends RecyclerView.ItemDecoration {
    public Drawable mDivider;
    public int mDividerHeight;
    public int mInsetLeft;
    public int mInsetRight;
    public SeslRoundedCorner mItemRoundedCorner;
    public SeslRoundedCorner mListRoundedCorner;

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void getItemOffsets(
            Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        int itemViewType;
        super.getItemOffsets(rect, view, recyclerView, state);
        recyclerView.getClass();
        int childAdapterPosition = RecyclerView.getChildAdapterPosition(view);
        VibPickerListAdapter vibPickerListAdapter =
                (VibPickerListAdapter) recyclerView.getAdapter();
        if (vibPickerListAdapter == null
                || (itemViewType = vibPickerListAdapter.getItemViewType(childAdapterPosition))
                        == VibPickerListItemViewType.MIDDLE_VIEW.getValue()
                || itemViewType == VibPickerListItemViewType.TOP_VIEW.getValue()) {
            rect.bottom = this.mDividerHeight;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
        LinearLayout linearLayout;
        View childAt;
        RecyclerView.ViewHolder childViewHolder;
        LinearLayout linearLayout2;
        int paddingLeft = recyclerView.getPaddingLeft() + this.mInsetLeft;
        int width = (recyclerView.getWidth() - recyclerView.getPaddingRight()) - this.mInsetRight;
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View childAt2 = recyclerView.getChildAt(i);
            if (childAt2 != null) {
                int bottom =
                        childAt2.getBottom()
                                + ((ViewGroup.MarginLayoutParams)
                                                ((RecyclerView.LayoutParams)
                                                        childAt2.getLayoutParams()))
                                        .bottomMargin;
                int i2 = this.mDividerHeight + bottom;
                RecyclerView.ViewHolder childViewHolder2 =
                        recyclerView.getChildViewHolder(childAt2);
                if (!(childViewHolder2 instanceof VibPickerListAdapter.ViewHolder)
                        || (((linearLayout =
                                                        (LinearLayout)
                                                                childViewHolder2.itemView
                                                                        .findViewById(
                                                                                R.id
                                                                                        .subtitle_layout))
                                                == null
                                        || linearLayout.getVisibility() != 0)
                                && ((childAt = recyclerView.getChildAt(i + 1)) == null
                                        || (childViewHolder =
                                                        recyclerView.getChildViewHolder(childAt))
                                                == null
                                        || (linearLayout2 =
                                                        (LinearLayout)
                                                                childViewHolder.itemView
                                                                        .findViewById(
                                                                                R.id
                                                                                        .subtitle_layout))
                                                == null
                                        || linearLayout2.getVisibility() != 0))) {
                    this.mDivider.setBounds(paddingLeft, bottom, width, i2);
                    this.mDivider.draw(canvas);
                }
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void seslOnDispatchDraw(
            Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(childAt);
            boolean z = childViewHolder instanceof VibPickerListAdapter.ViewHolder;
            SeslRoundedCorner seslRoundedCorner = this.mItemRoundedCorner;
            if (z) {
                seslRoundedCorner.setRoundedCorners(
                        ((VibPickerListAdapter.ViewHolder) childViewHolder).roundMode);
            } else {
                seslRoundedCorner.setRoundedCorners(0);
            }
            seslRoundedCorner.drawRoundedCorner(childAt, canvas);
        }
        SeslRoundedCorner seslRoundedCorner2 = this.mListRoundedCorner;
        canvas.getClipBounds(seslRoundedCorner2.mRoundedCornerBounds);
        seslRoundedCorner2.drawRoundedCornerInternal$1(canvas);
    }
}
