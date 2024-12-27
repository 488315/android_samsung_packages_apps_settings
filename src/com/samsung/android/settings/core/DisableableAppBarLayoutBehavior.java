package com.samsung.android.settings.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DisableableAppBarLayoutBehavior extends AppBarLayout.Behavior {
    public boolean mEnabled;

    public DisableableAppBarLayoutBehavior() {
        this.mEnabled = true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final /* bridge */ /* synthetic */ void onNestedScroll(View view) {}

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior,
              // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onNestedPreScroll(
            CoordinatorLayout coordinatorLayout,
            AppBarLayout appBarLayout,
            View view,
            int i,
            int i2,
            int[] iArr,
            int i3) {
        if (this.mEnabled) {
            super.onNestedPreScroll(coordinatorLayout, appBarLayout, view, i, i2, iArr, i3);
        }
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior,
              // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onStartNestedScroll(
            CoordinatorLayout coordinatorLayout,
            AppBarLayout appBarLayout,
            View view,
            View view2,
            int i,
            int i2) {
        return this.mEnabled
                && super.onStartNestedScroll(coordinatorLayout, appBarLayout, view, view2, i, i2);
    }

    public DisableableAppBarLayoutBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEnabled = true;
    }
}
