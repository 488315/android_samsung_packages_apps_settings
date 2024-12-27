package com.google.android.material.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.ViewUtilsLollipop;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SearchBar$ScrollingViewBehavior extends AppBarLayout.ScrollingViewBehavior {
    public boolean initialized;

    public SearchBar$ScrollingViewBehavior() {
        this.initialized = false;
    }

    @Override // com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior,
              // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onDependentViewChanged(
            CoordinatorLayout coordinatorLayout, View view, View view2) {
        super.onDependentViewChanged(coordinatorLayout, view, view2);
        if (!this.initialized && (view2 instanceof AppBarLayout)) {
            this.initialized = true;
            AppBarLayout appBarLayout = (AppBarLayout) view2;
            appBarLayout.setBackgroundColor(0);
            ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(appBarLayout, 0.0f);
        }
        return false;
    }

    @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
    public final boolean shouldHeaderOverlapScrollingChild() {
        return true;
    }

    public SearchBar$ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initialized = false;
    }
}
