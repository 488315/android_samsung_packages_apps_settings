package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.drawable.InsetDrawable;
import android.view.View;

import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DividerItemDecorator extends RecyclerView.ItemDecoration {
    public final InsetDrawable mDivider;
    public final int mLeftInset;
    public final int mRightInset;

    public DividerItemDecorator(Context context, int i, int i2) {
        this.mDivider = (InsetDrawable) context.getDrawable(R.drawable.list_divider);
        this.mLeftInset = i;
        this.mRightInset = i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
        if (this.mDivider == null) {
            return;
        }
        boolean z = recyclerView.getLayoutDirection() == 1;
        Insets opticalInsets = this.mDivider.getOpticalInsets();
        int i = this.mLeftInset;
        int i2 = this.mRightInset;
        InsetDrawable insetDrawable =
                new InsetDrawable(
                        this.mDivider.getDrawable(),
                        z ? i2 : i,
                        opticalInsets.top,
                        z ? i : i2,
                        opticalInsets.bottom);
        int childCount = recyclerView.getChildCount() - 1;
        for (int i3 = 0; i3 <= childCount; i3++) {
            View childAt = recyclerView.getChildAt(i3);
            RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(childAt);
            if ((childViewHolder instanceof PreferenceViewHolder)
                    && ((PreferenceViewHolder) childViewHolder).mDividerAllowedBelow) {
                int indexOfChild = recyclerView.indexOfChild(childAt);
                if (indexOfChild < recyclerView.getChildCount() - 1) {
                    RecyclerView.ViewHolder childViewHolder2 =
                            recyclerView.getChildViewHolder(
                                    recyclerView.getChildAt(indexOfChild + 1));
                    if (childViewHolder2 instanceof PreferenceViewHolder) {
                        if (!((PreferenceViewHolder) childViewHolder2).mDividerAllowedAbove) {}
                    }
                }
                int height = childAt.getHeight() + ((int) childAt.getY());
                insetDrawable.setBounds(
                        0,
                        height,
                        recyclerView.getWidth(),
                        insetDrawable.getIntrinsicHeight() + height);
                insetDrawable.draw(canvas);
            }
        }
    }

    public DividerItemDecorator(Context context, int i) {
        this(
                context,
                i,
                context.getResources().getDimensionPixelSize(R.dimen.sesl_list_divider_inset));
    }
}
