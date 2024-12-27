package com.samsung.android.settings.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SecDividerItemDecorator extends RecyclerView.ItemDecoration {
    public final Context mContext;
    public final Drawable mDivider;
    public final int mStartsPadding;

    public SecDividerItemDecorator(int i, Context context, Drawable drawable) {
        this.mDivider = drawable;
        this.mContext = context;
        this.mStartsPadding = i;
    }

    public static boolean shouldDrawDividerBelow$4(View view, RecyclerView recyclerView) {
        RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(view);
        boolean z = false;
        if (!(childViewHolder instanceof PreferenceViewHolder)
                || !((PreferenceViewHolder) childViewHolder).mDividerAllowedBelow) {
            return false;
        }
        int indexOfChild = recyclerView.indexOfChild(view);
        if (indexOfChild >= recyclerView.getChildCount() - 1) {
            return true;
        }
        RecyclerView.ViewHolder childViewHolder2 =
                recyclerView.getChildViewHolder(recyclerView.getChildAt(indexOfChild + 1));
        if ((childViewHolder2 instanceof PreferenceViewHolder)
                && ((PreferenceViewHolder) childViewHolder2).mDividerAllowedAbove) {
            z = true;
        }
        return z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void seslOnDispatchDraw(
            Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int left;
        int right;
        Context context = this.mContext;
        if (context != null) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(
                            new int[] {
                                R.attr.listPreferredItemPaddingStart,
                                R.attr.listPreferredItemPaddingEnd
                            });
            try {
                try {
                    try {
                        int layoutDirection = recyclerView.getLayoutDirection();
                        int i = this.mStartsPadding;
                        if (layoutDirection == 1) {
                            left =
                                    ((int) obtainStyledAttributes.getDimension(1, 0.0f))
                                            + recyclerView.getPaddingEnd();
                            right = (recyclerView.getRight() - recyclerView.getPaddingStart()) - i;
                        } else {
                            left = recyclerView.getLeft() + recyclerView.getPaddingStart() + i;
                            right =
                                    ((int)
                                                    (recyclerView.getRight()
                                                            - obtainStyledAttributes.getDimension(
                                                                    1, 0.0f)))
                                            - recyclerView.getPaddingEnd();
                        }
                        int childCount = recyclerView.getChildCount() - 1;
                        for (int i2 = 0; i2 <= childCount; i2++) {
                            View childAt = recyclerView.getChildAt(i2);
                            if (shouldDrawDividerBelow$4(childAt, recyclerView)) {
                                int bottom =
                                        childAt.getBottom()
                                                + ((ViewGroup.MarginLayoutParams)
                                                                ((RecyclerView.LayoutParams)
                                                                        childAt.getLayoutParams()))
                                                        .bottomMargin;
                                this.mDivider.setBounds(
                                        left,
                                        bottom,
                                        right,
                                        this.mDivider.getIntrinsicHeight() + bottom);
                                this.mDivider.draw(canvas);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (obtainStyledAttributes == null) {
                            return;
                        } else {
                            obtainStyledAttributes.recycle();
                        }
                    }
                    if (obtainStyledAttributes != null) {
                        obtainStyledAttributes.recycle();
                    }
                } catch (Throwable th) {
                    if (obtainStyledAttributes != null) {
                        try {
                            obtainStyledAttributes.recycle();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }
}
