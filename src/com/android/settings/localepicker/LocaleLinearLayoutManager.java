package com.android.settings.localepicker;

import android.content.Context;
import android.view.View;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocaleLinearLayoutManager extends LinearLayoutManager {
    public final AccessibilityNodeInfoCompat.AccessibilityActionCompat mActionMoveBottom;
    public final AccessibilityNodeInfoCompat.AccessibilityActionCompat mActionMoveDown;
    public final AccessibilityNodeInfoCompat.AccessibilityActionCompat mActionMoveTop;
    public final AccessibilityNodeInfoCompat.AccessibilityActionCompat mActionMoveUp;
    public final LocaleDragAndDropAdapter mAdapter;

    public LocaleLinearLayoutManager(
            Context context, LocaleDragAndDropAdapter localeDragAndDropAdapter) {
        super(1);
        this.mAdapter = localeDragAndDropAdapter;
        this.mActionMoveUp =
                new AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                        R.id.action_drag_move_up,
                        context.getString(R.string.action_drag_label_move_up));
        this.mActionMoveDown =
                new AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                        R.id.action_drag_move_down,
                        context.getString(R.string.action_drag_label_move_down));
        this.mActionMoveTop =
                new AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                        R.id.action_drag_move_top,
                        context.getString(R.string.action_drag_label_move_top));
        this.mActionMoveBottom =
                new AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                        R.id.action_drag_move_bottom,
                        context.getString(R.string.action_drag_label_move_bottom));
        new AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                R.id.action_drag_remove, context.getString(R.string.action_drag_label_remove));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onInitializeAccessibilityNodeInfoForItem(
            RecyclerView.Recycler recycler,
            RecyclerView.State state,
            View view,
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfoForItem(
                recycler, state, view, accessibilityNodeInfoCompat);
        int itemCount = getItemCount();
        int position = RecyclerView.LayoutManager.getPosition(view);
        StringBuilder sb = new StringBuilder();
        int i = position + 1;
        sb.append(i);
        sb.append(", ");
        sb.append((Object) ((LocaleDragCell) view).getContentDescription());
        accessibilityNodeInfoCompat.setContentDescription(sb.toString());
        if (this.mAdapter.mEditMode) {
            return;
        }
        if (position > 0) {
            accessibilityNodeInfoCompat.addAction(this.mActionMoveUp);
            accessibilityNodeInfoCompat.addAction(this.mActionMoveTop);
        }
        if (i < itemCount) {
            accessibilityNodeInfoCompat.addAction(this.mActionMoveDown);
            accessibilityNodeInfoCompat.addAction(this.mActionMoveBottom);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean performAccessibilityActionForItem(View view, int i) {
        int itemCount = getItemCount();
        int position = RecyclerView.LayoutManager.getPosition(view);
        LocaleDragAndDropAdapter localeDragAndDropAdapter = this.mAdapter;
        if (i == R.id.action_drag_move_up) {
            if (position > 0) {
                localeDragAndDropAdapter.onItemMove(position, position - 1);
                return true;
            }
        } else if (i == R.id.action_drag_move_down) {
            int i2 = position + 1;
            if (i2 < itemCount) {
                localeDragAndDropAdapter.onItemMove(position, i2);
                return true;
            }
        } else if (i == R.id.action_drag_move_top) {
            if (position != 0) {
                localeDragAndDropAdapter.onItemMove(position, 0);
                return true;
            }
        } else if (i == R.id.action_drag_move_bottom) {
            int i3 = itemCount - 1;
            if (position != i3) {
                localeDragAndDropAdapter.onItemMove(position, i3);
                return true;
            }
        } else {
            if (i != R.id.action_drag_remove) {
                return false;
            }
            if (itemCount > 1) {
                int size = localeDragAndDropAdapter.mFeedItemList.size();
                if (size <= 1 || position < 0 || position >= size) {
                    return true;
                }
                localeDragAndDropAdapter.mFeedItemList.remove(position);
                localeDragAndDropAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }
}
