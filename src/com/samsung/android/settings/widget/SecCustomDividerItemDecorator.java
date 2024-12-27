package com.samsung.android.settings.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SecCustomDividerItemDecorator extends RecyclerView.ItemDecoration {
    public final Context mContext;
    public final int mCustomFrameViewId;
    public final int mCustomViewId;
    public final Drawable mDivider;
    public final int mStartsPadding;

    public SecCustomDividerItemDecorator(
            Drawable drawable, Context context, int i, int i2, int i3) {
        this.mContext = context;
        this.mDivider = drawable;
        this.mStartsPadding = i;
        this.mCustomFrameViewId = i2;
        this.mCustomViewId = i3;
    }

    public static boolean shouldDrawDividerBelow$3(View view, RecyclerView recyclerView) {
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
        int i = this.mStartsPadding;
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        new int[] {
                            R.attr.listPreferredItemPaddingStart, R.attr.listPreferredItemPaddingEnd
                        });
        try {
            try {
                try {
                    int dimension =
                            ((int) obtainStyledAttributes.getDimension(1, 0.0f))
                                    + recyclerView.getPaddingEnd();
                    int right =
                            ((int)
                                            (recyclerView.getRight()
                                                    - obtainStyledAttributes.getDimension(1, 0.0f)))
                                    - recyclerView.getPaddingEnd();
                    int left = recyclerView.getLeft() + recyclerView.getPaddingStart() + i;
                    int right2 = (recyclerView.getRight() - recyclerView.getPaddingStart()) - i;
                    int childCount = recyclerView.getChildCount() - 1;
                    for (int i2 = 0; i2 <= childCount; i2++) {
                        View childAt = recyclerView.getChildAt(i2);
                        if (shouldDrawDividerBelow$3(childAt, recyclerView)) {
                            int bottom =
                                    childAt.getBottom()
                                            + ((ViewGroup.MarginLayoutParams)
                                                            ((RecyclerView.LayoutParams)
                                                                    childAt.getLayoutParams()))
                                                    .bottomMargin;
                            int intrinsicHeight = this.mDivider.getIntrinsicHeight() + bottom;
                            View view = null;
                            try {
                                View findViewById = childAt.findViewById(this.mCustomFrameViewId);
                                if (findViewById != null && findViewById.getVisibility() != 8) {
                                    view = findViewById.findViewById(this.mCustomViewId);
                                }
                            } catch (Exception e) {
                                Log.i("SecCustomDividerItemDecorator", "get ImageView fail: ", e);
                            }
                            if (view == null) {
                                this.mDivider.setBounds(dimension, bottom, right, intrinsicHeight);
                            } else if (recyclerView.getLayoutDirection() == 1) {
                                this.mDivider.setBounds(dimension, bottom, right2, intrinsicHeight);
                            } else {
                                this.mDivider.setBounds(left, bottom, right, intrinsicHeight);
                            }
                            this.mDivider.draw(canvas);
                        }
                    }
                    obtainStyledAttributes.recycle();
                } finally {
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (obtainStyledAttributes != null) {
                    obtainStyledAttributes.recycle();
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }
}
