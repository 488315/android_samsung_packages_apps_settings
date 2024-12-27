package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.ViewUtilsLollipop;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FloatingAppBarScrollingViewBehavior extends AppBarLayout.ScrollingViewBehavior {
    public boolean initialized;

    public FloatingAppBarScrollingViewBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior,
              // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onDependentViewChanged(
            CoordinatorLayout coordinatorLayout, View view, View view2) {
        super.onDependentViewChanged(coordinatorLayout, view, view2);
        if (this.initialized || !(view2 instanceof AppBarLayout)) {
            return false;
        }
        this.initialized = true;
        setAppBarLayoutTransparent((AppBarLayout) view2);
        return false;
    }

    public void setAppBarLayoutTransparent(AppBarLayout appBarLayout) {
        appBarLayout.setBackgroundColor(0);
        ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(appBarLayout, 0.0f);
    }
}
