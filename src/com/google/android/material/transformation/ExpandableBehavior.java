package com.google.android.material.transformation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Deprecated
/* loaded from: classes3.dex */
public abstract class ExpandableBehavior extends CoordinatorLayout.Behavior {
    public ExpandableBehavior() {}

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public abstract boolean layoutDependsOn(View view, View view2);

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onDependentViewChanged(
            CoordinatorLayout coordinatorLayout, View view, View view2) {
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(view2);
        throw null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (!view.isLaidOut()) {
            List dependencies = coordinatorLayout.getDependencies(view);
            int size = dependencies.size();
            for (int i2 = 0; i2 < size; i2++) {
                layoutDependsOn(view, (View) dependencies.get(i2));
            }
        }
        return false;
    }

    public ExpandableBehavior(Context context, AttributeSet attributeSet) {}
}
