package com.android.settings.homepage.contextualcards;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.LifecycleOwner;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ContextualCardsAdapter extends RecyclerView.Adapter
        implements ContextualCardUpdateListener {
    public final Context mContext;
    final List<ContextualCard> mContextualCards = new ArrayList();
    public final ControllerRendererPool mControllerRendererPool;
    public final LifecycleOwner mLifecycleOwner;
    public RecyclerView mRecyclerView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SecRecyclerViewDecoration extends RecyclerView.ItemDecoration {
        public final Context mContext;

        public SecRecyclerViewDecoration(Context context) {
            this.mContext = context;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(
                Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            super.getItemOffsets(rect, view, recyclerView, state);
            if (R.layout.sec_legacy_suggestion_tile
                    == recyclerView.getChildViewHolder(view).mItemViewType) {
                rect.bottom =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.sec_suggestion_container_margin_bottom);
            }
        }
    }

    public ContextualCardsAdapter(
            Context context,
            LifecycleOwner lifecycleOwner,
            ContextualCardManager contextualCardManager) {
        this.mContext = context;
        this.mControllerRendererPool = contextualCardManager.mControllerRendererPool;
        this.mLifecycleOwner = lifecycleOwner;
        setHasStableIds(true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mContextualCards.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        return this.mContextualCards.get(i).hashCode();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return this.mContextualCards.get(i).mViewType;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        recyclerView.addItemDecoration(new SecRecyclerViewDecoration(this.mContext));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ContextualCard contextualCard = this.mContextualCards.get(i);
        this.mControllerRendererPool
                .getRendererByViewType(
                        this.mContext, this.mLifecycleOwner, contextualCard.mViewType)
                .bindView(viewHolder, contextualCard);
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardUpdateListener
    public final void onContextualCardUpdated(Map map) {
        List<ContextualCard> list = (List) map.get(0);
        boolean isEmpty = this.mContextualCards.isEmpty();
        boolean z = list == null || list.isEmpty();
        if (list == null) {
            this.mContextualCards.clear();
            notifyDataSetChanged();
        } else {
            DiffUtil.DiffResult calculateDiff =
                    DiffUtil.calculateDiff(
                            new ContextualCardsDiffCallback(this.mContextualCards, list));
            for (ContextualCard contextualCard : this.mContextualCards) {
                StringBuilder sb = new StringBuilder("card getCategory : ");
                sb.append(contextualCard.mCategory);
                sb.append(" , type : ");
                sb.append(contextualCard.getCardType());
                sb.append(" name : ");
                Utils$$ExternalSyntheticOutline0.m(
                        sb, contextualCard.mName, "ContextualCardsAdapter");
            }
            for (ContextualCard contextualCard2 : list) {
                StringBuilder sb2 = new StringBuilder("new card getCategory : ");
                sb2.append(contextualCard2.mCategory);
                sb2.append(" , type : ");
                sb2.append(contextualCard2.getCardType());
                sb2.append(" name : ");
                Utils$$ExternalSyntheticOutline0.m(
                        sb2, contextualCard2.mName, "ContextualCardsAdapter");
            }
            this.mContextualCards.clear();
            this.mContextualCards.addAll(list);
            calculateDiff.dispatchUpdatesTo(new AdapterListUpdateCallback(this));
        }
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView == null || !isEmpty || z) {
            return;
        }
        recyclerView.scheduleLayoutAnimation();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return this.mControllerRendererPool
                .getRendererByViewType(this.mContext, this.mLifecycleOwner, i)
                .createViewHolder(
                        MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                                viewGroup, i, viewGroup, false),
                        i);
    }

    public final void onSwiped(int i) {
        ContextualCard.Builder builder = this.mContextualCards.get(i).mBuilder;
        builder.mIsPendingDismiss = true;
        this.mContextualCards.set(i, builder.build());
        notifyItemChanged(i);
    }
}
