package com.samsung.android.settings.languagepack.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LanguagePackSettingsDividerItemDecorator extends RecyclerView.ItemDecoration {
    public final Context mContext;
    public final Drawable mDivider;
    public final int mStartsPadding;

    public LanguagePackSettingsDividerItemDecorator(int i, Context context, Drawable drawable) {
        this.mContext = context;
        this.mDivider = drawable;
        this.mStartsPadding = i;
    }

    public static int getDividerType(View view, RecyclerView recyclerView) {
        int indexOfChild;
        RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(view);
        TextView textView = (TextView) childViewHolder.itemView.findViewById(R.id.title_text);
        if (textView != null
                && !textView.getText().isEmpty()
                && (indexOfChild = recyclerView.indexOfChild(view))
                        < recyclerView.getChildCount() - 1) {
            TextView textView2 =
                    (TextView)
                            recyclerView
                                    .getChildViewHolder(recyclerView.getChildAt(indexOfChild + 1))
                                    .itemView
                                    .findViewById(R.id.title_text);
            RadioButton radioButton =
                    (RadioButton) childViewHolder.itemView.findViewById(R.id.radio_btn);
            if (textView2 != null && !textView2.getText().isEmpty()) {
                return (radioButton == null || radioButton.getVisibility() != 0) ? 2 : 1;
            }
        }
        return 0;
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
                            android.R.attr.listPreferredItemPaddingStart,
                            android.R.attr.listPreferredItemPaddingEnd
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
                        int bottom =
                                childAt.getBottom()
                                        + ((ViewGroup.MarginLayoutParams)
                                                        ((RecyclerView.LayoutParams)
                                                                childAt.getLayoutParams()))
                                                .bottomMargin;
                        int intrinsicHeight = this.mDivider.getIntrinsicHeight() + bottom;
                        int dividerType = getDividerType(childAt, recyclerView);
                        if (dividerType == 1) {
                            if (recyclerView.getLayoutDirection() == 1) {
                                this.mDivider.setBounds(dimension, bottom, right2, intrinsicHeight);
                            } else {
                                this.mDivider.setBounds(left, bottom, right, intrinsicHeight);
                            }
                            this.mDivider.draw(canvas);
                        } else if (dividerType == 2) {
                            this.mDivider.setBounds(dimension, bottom, right, intrinsicHeight);
                            this.mDivider.draw(canvas);
                        }
                    }
                    obtainStyledAttributes.recycle();
                } finally {
                    try {
                        obtainStyledAttributes.recycle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            obtainStyledAttributes.recycle();
        }
    }
}
