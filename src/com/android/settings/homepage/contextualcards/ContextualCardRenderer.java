package com.android.settings.homepage.contextualcards;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface ContextualCardRenderer {
    void bindView(RecyclerView.ViewHolder viewHolder, ContextualCard contextualCard);

    RecyclerView.ViewHolder createViewHolder(View view, int i);
}
