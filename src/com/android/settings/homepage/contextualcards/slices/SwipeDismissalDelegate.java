package com.android.settings.homepage.contextualcards.slices;

import android.graphics.Canvas;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.homepage.contextualcards.ContextualCardsAdapter;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SwipeDismissalDelegate extends ItemTouchHelper.Callback {
    public final ContextualCardsAdapter mListener;

    public SwipeDismissalDelegate(ContextualCardsAdapter contextualCardsAdapter) {
        this.mListener = contextualCardsAdapter;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public final void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        View view =
                viewHolder.mItemViewType == R.layout.contextual_slice_half_tile
                        ? ((SliceHalfCardRendererHelper.HalfCardViewHolder) viewHolder).content
                        : ((SliceFullCardRendererHelper.SliceViewHolder) viewHolder).sliceView;
        Object tag = view.getTag(R.id.item_touch_helper_previous_elevation);
        if (tag instanceof Float) {
            float floatValue = ((Float) tag).floatValue();
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.setElevation(view, floatValue);
        }
        view.setTag(R.id.item_touch_helper_previous_elevation, null);
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public final int getMovementFlags(RecyclerView.ViewHolder viewHolder) {
        int i = viewHolder.mItemViewType;
        if ((i == R.layout.contextual_slice_full_tile || i == R.layout.contextual_slice_half_tile)
                && viewHolder.itemView.findViewById(R.id.dismissal_view).getVisibility() != 0) {
            return ItemTouchHelper.Callback.makeMovementFlags(0, 12);
        }
        return 0;
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
        View view =
                viewHolder.mItemViewType == R.layout.contextual_slice_half_tile
                        ? ((SliceHalfCardRendererHelper.HalfCardViewHolder) viewHolder).content
                        : ((SliceFullCardRendererHelper.SliceViewHolder) viewHolder).sliceView;
        View findViewById = viewHolder.itemView.findViewById(R.id.dismissal_icon_start);
        View findViewById2 = viewHolder.itemView.findViewById(R.id.dismissal_icon_end);
        float f3 = 0.0f;
        if (f > 0.0f) {
            findViewById.setVisibility(0);
            findViewById2.setVisibility(8);
        } else if (f < 0.0f) {
            findViewById.setVisibility(8);
            findViewById2.setVisibility(0);
        }
        if (z && view.getTag(R.id.item_touch_helper_previous_elevation) == null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            Float valueOf = Float.valueOf(ViewCompat.Api21Impl.getElevation(view));
            int childCount = recyclerView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = recyclerView.getChildAt(i2);
                if (childAt != view) {
                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    float elevation = ViewCompat.Api21Impl.getElevation(childAt);
                    if (elevation > f3) {
                        f3 = elevation;
                    }
                }
            }
            ViewCompat.Api21Impl.setElevation(view, f3 + 1.0f);
            view.setTag(R.id.item_touch_helper_previous_elevation, valueOf);
        }
        view.setTranslationX(f);
        view.setTranslationY(f2);
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
        this.mListener.onSwiped(viewHolder.getBindingAdapterPosition());
    }
}
