package com.samsung.android.settings.analyzestorage.ui.pages.filelist;

import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SynchronizedViewPool extends RecyclerView.RecycledViewPool {
    @Override // androidx.recyclerview.widget.RecyclerView.RecycledViewPool
    public final synchronized RecyclerView.ViewHolder getRecycledView(int i) {
        return super.getRecycledView(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.RecycledViewPool
    public final synchronized void putRecycledView(RecyclerView.ViewHolder viewHolder) {
        super.putRecycledView(viewHolder);
    }
}
