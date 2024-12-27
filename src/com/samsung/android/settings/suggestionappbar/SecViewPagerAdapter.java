package com.samsung.android.settings.suggestionappbar;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.model.SuggestAppBarModel;
import com.google.android.material.appbar.model.view.SuggestAppBarItemView;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecViewPagerAdapter extends RecyclerView.Adapter {
    public final Context context;
    public final List data = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final SuggestAppBarItemView appBarModuleView;

        public ViewHolder(SuggestAppBarItemView suggestAppBarItemView) {
            super(suggestAppBarItemView);
            this.appBarModuleView = suggestAppBarItemView;
        }
    }

    public SecViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return ((ArrayList) this.data).size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((SuggestAppBarModel) ((ArrayList) this.data).get(i))
                .init((SuggestAppBarModel) ((ViewHolder) viewHolder).appBarModuleView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        SuggestAppBarItemView suggestAppBarItemView =
                new SuggestAppBarItemView(this.context, null, 2, null);
        suggestAppBarItemView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        return new ViewHolder(suggestAppBarItemView);
    }
}
