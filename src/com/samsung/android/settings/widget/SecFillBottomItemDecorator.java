package com.samsung.android.settings.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SecFillBottomItemDecorator extends RecyclerView.ItemDecoration {
    public final Paint mRectPaint;

    public SecFillBottomItemDecorator(Context context) {
        Paint paint = new Paint();
        this.mRectPaint = paint;
        paint.setColor(context.getColor(R.color.sec_round_and_bgcolor));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void seslOnDispatchDraw(
            Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        View childAt;
        if (recyclerView.canScrollVertically(1)
                || recyclerView.getAdapter() == null
                || !(recyclerView.getLayoutManager() instanceof LinearLayoutManager)) {
            return;
        }
        int itemCount = recyclerView.getAdapter().getItemCount() - 1;
        int bottom =
                (itemCount < 0
                                || itemCount
                                        > ((LinearLayoutManager) recyclerView.getLayoutManager())
                                                .findLastVisibleItemPosition()
                                || (childAt = recyclerView.getLayoutManager().getChildAt(itemCount))
                                        == null)
                        ? -1
                        : childAt.getBottom();
        if (bottom != -1) {
            canvas.drawRect(
                    0.0f,
                    bottom,
                    recyclerView.getWidth(),
                    recyclerView.getBottom(),
                    this.mRectPaint);
        }
    }
}
