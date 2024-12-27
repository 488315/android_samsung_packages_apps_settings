package com.google.android.material.floatingactionbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.compose.ui.node.LayoutModifierNodeCoordinator$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FloatingActionButton$BaseBehavior<T> extends CoordinatorLayout.Behavior {
    public FloatingActionButton$BaseBehavior() {}

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean getInsetDodgeRect(View view) {
        LayoutModifierNodeCoordinator$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
        throw null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        if (layoutParams.dodgeInsetEdges == 0) {
            layoutParams.dodgeInsetEdges = 80;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onDependentViewChanged(
            CoordinatorLayout coordinatorLayout, View view, View view2) {
        LayoutModifierNodeCoordinator$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
        throw null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        LayoutModifierNodeCoordinator$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
        throw null;
    }

    public FloatingActionButton$BaseBehavior(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet, R$styleable.FloatingActionButton_Behavior_Layout);
        obtainStyledAttributes.getBoolean(0, true);
        obtainStyledAttributes.recycle();
    }

    public void setInternalAutoHideListener(
            FloatingActionButton$OnVisibilityChangedListener
                    floatingActionButton$OnVisibilityChangedListener) {}
}
