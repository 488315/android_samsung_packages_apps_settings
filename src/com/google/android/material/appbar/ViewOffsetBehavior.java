package com.google.android.material.appbar;

import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ViewOffsetBehavior extends CoordinatorLayout.Behavior {
    public int tempTopBottomOffset = 0;
    public ViewOffsetHelper viewOffsetHelper;

    public ViewOffsetBehavior() {}

    public final int getTopAndBottomOffset() {
        ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
        if (viewOffsetHelper != null) {
            return viewOffsetHelper.offsetTop;
        }
        return 0;
    }

    public void layoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        coordinatorLayout.onLayoutChild(view, i);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        layoutChild(coordinatorLayout, view, i);
        if (this.viewOffsetHelper == null) {
            this.viewOffsetHelper = new ViewOffsetHelper(view);
        }
        ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
        viewOffsetHelper.layoutTop = viewOffsetHelper.view.getTop();
        viewOffsetHelper.layoutLeft = viewOffsetHelper.view.getLeft();
        this.viewOffsetHelper.applyOffsets();
        int i2 = this.tempTopBottomOffset;
        if (i2 == 0) {
            return true;
        }
        this.viewOffsetHelper.setTopAndBottomOffset(i2);
        this.tempTopBottomOffset = 0;
        return true;
    }

    public ViewOffsetBehavior(int i) {}
}
