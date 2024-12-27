package com.android.settings.notification.history;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationHistoryRecyclerView extends RecyclerView {
    public float dXLast;
    public OnItemSwipeDeleteListener listener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DismissTouchHelper extends ItemTouchHelper.SimpleCallback {
        public DismissTouchHelper() {
            super(0, 48);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public final void onChildDraw(
                Canvas canvas,
                RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder,
                float f,
                float f2,
                int i,
                boolean z) {
            super.onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, z);
            NotificationHistoryRecyclerView notificationHistoryRecyclerView =
                    NotificationHistoryRecyclerView.this;
            if (!z) {
                notificationHistoryRecyclerView.dXLast = 0.0f;
                return;
            }
            View view = viewHolder.itemView;
            float width = 0.5f * view.getWidth();
            float f3 = -width;
            boolean z2 = true;
            boolean z3 = f < f3 || f > width;
            float f4 = notificationHistoryRecyclerView.dXLast;
            if (f4 >= f3 && f4 <= width) {
                z2 = false;
            }
            if (z3 != z2) {
                view.performHapticFeedback(4);
            }
            notificationHistoryRecyclerView.dXLast = f;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public final boolean onMove(
                RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder,
                RecyclerView.ViewHolder viewHolder2) {
            return false;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public final void onSwiped(RecyclerView.ViewHolder viewHolder) {
            OnItemSwipeDeleteListener onItemSwipeDeleteListener =
                    NotificationHistoryRecyclerView.this.listener;
            if (onItemSwipeDeleteListener != null) {
                ((NotificationHistoryAdapter) onItemSwipeDeleteListener)
                        .onItemSwipeDeleted(viewHolder.getBindingAdapterPosition());
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnItemSwipeDeleteListener {}

    public NotificationHistoryRecyclerView(Context context) {
        this(context, null);
    }

    public void setOnItemSwipeDeleteListener(OnItemSwipeDeleteListener onItemSwipeDeleteListener) {
        this.listener = onItemSwipeDeleteListener;
    }

    public NotificationHistoryRecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationHistoryRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        getContext();
        setLayoutManager(new LinearLayoutManager(1));
        addItemDecoration(new DividerItemDecoration(getContext(), 1));
        new ItemTouchHelper(new DismissTouchHelper()).attachToRecyclerView(this);
    }
}
