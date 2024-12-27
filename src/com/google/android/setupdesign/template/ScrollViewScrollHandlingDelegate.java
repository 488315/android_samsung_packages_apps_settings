package com.google.android.setupdesign.template;

import android.util.Log;
import android.widget.ScrollView;

import com.google.android.setupdesign.view.BottomScrollView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ScrollViewScrollHandlingDelegate
        implements RequireScrollMixin.ScrollHandlingDelegate,
                BottomScrollView.BottomScrollListener {
    public final RequireScrollMixin requireScrollMixin;
    public final BottomScrollView scrollView;

    public ScrollViewScrollHandlingDelegate(
            RequireScrollMixin requireScrollMixin, ScrollView scrollView) {
        this.requireScrollMixin = requireScrollMixin;
        if (scrollView instanceof BottomScrollView) {
            this.scrollView = (BottomScrollView) scrollView;
            return;
        }
        Log.w("ScrollViewDelegate", "Cannot set non-BottomScrollView. Found=" + scrollView);
        this.scrollView = null;
    }

    @Override // com.google.android.setupdesign.template.RequireScrollMixin.ScrollHandlingDelegate
    public final void pageScrollDown() {
        BottomScrollView bottomScrollView = this.scrollView;
        if (bottomScrollView != null) {
            bottomScrollView.pageScroll(130);
        }
    }

    @Override // com.google.android.setupdesign.template.RequireScrollMixin.ScrollHandlingDelegate
    public final void startListening() {
        BottomScrollView bottomScrollView = this.scrollView;
        if (bottomScrollView != null) {
            bottomScrollView.listener = this;
        } else {
            Log.w("ScrollViewDelegate", "Cannot require scroll. Scroll view is null.");
        }
    }
}
