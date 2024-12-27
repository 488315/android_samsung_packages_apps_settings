package com.google.android.setupdesign.template;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class RecyclerViewScrollHandlingDelegate
        implements RequireScrollMixin.ScrollHandlingDelegate {
    public final RecyclerView recyclerView;
    public final RequireScrollMixin requireScrollMixin;

    public RecyclerViewScrollHandlingDelegate(
            RequireScrollMixin requireScrollMixin, RecyclerView recyclerView) {
        this.requireScrollMixin = requireScrollMixin;
        this.recyclerView = recyclerView;
    }

    @Override // com.google.android.setupdesign.template.RequireScrollMixin.ScrollHandlingDelegate
    public final void pageScrollDown() {
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.smoothScrollBy(0, recyclerView.getHeight(), false);
        }
    }

    @Override // com.google.android.setupdesign.template.RequireScrollMixin.ScrollHandlingDelegate
    public final void startListening() {
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView == null) {
            Log.w("RVRequireScrollMixin", "Cannot require scroll. Recycler view is null.");
            return;
        }
        recyclerView.addOnScrollListener(
                new RecyclerView
                        .OnScrollListener() { // from class:
                                              // com.google.android.setupdesign.template.RecyclerViewScrollHandlingDelegate.1
                    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                    public final void onScrolled(RecyclerView recyclerView2, int i, int i2) {
                        RecyclerViewScrollHandlingDelegate recyclerViewScrollHandlingDelegate =
                                RecyclerViewScrollHandlingDelegate.this;
                        RequireScrollMixin requireScrollMixin =
                                recyclerViewScrollHandlingDelegate.requireScrollMixin;
                        boolean z = false;
                        RecyclerView recyclerView3 =
                                recyclerViewScrollHandlingDelegate.recyclerView;
                        if (recyclerView3 != null) {
                            int computeVerticalScrollOffset =
                                    recyclerView3.computeVerticalScrollOffset();
                            int computeVerticalScrollRange =
                                    recyclerView3.computeVerticalScrollRange()
                                            - recyclerView3.computeVerticalScrollExtent();
                            if (computeVerticalScrollRange != 0
                                    && computeVerticalScrollOffset
                                            < computeVerticalScrollRange - 1) {
                                z = true;
                            }
                        }
                        requireScrollMixin.notifyScrollabilityChange(z);
                    }
                });
        if (recyclerView != null) {
            int computeVerticalScrollOffset = recyclerView.computeVerticalScrollOffset();
            int computeVerticalScrollRange =
                    recyclerView.computeVerticalScrollRange()
                            - recyclerView.computeVerticalScrollExtent();
            if (computeVerticalScrollRange == 0
                    || computeVerticalScrollOffset >= computeVerticalScrollRange - 1) {
                return;
            }
            this.requireScrollMixin.notifyScrollabilityChange(true);
        }
    }
}
