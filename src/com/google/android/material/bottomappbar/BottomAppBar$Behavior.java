package com.google.android.material.bottomappbar;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.compose.ui.node.LayoutModifierNodeCoordinator$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class BottomAppBar$Behavior extends HideBottomViewOnScrollBehavior<Object> {
    public final AnonymousClass1 fabLayoutListener;
    public final WeakReference viewRef;

    public BottomAppBar$Behavior() {
        new View
                .OnLayoutChangeListener() { // from class:
                                            // com.google.android.material.bottomappbar.BottomAppBar$Behavior.1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(
                    View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                        BottomAppBar$Behavior.this.viewRef.get());
                view.removeOnLayoutChangeListener(this);
            }
        };
        new Rect();
    }

    @Override // com.google.android.material.behavior.HideBottomViewOnScrollBehavior,
              // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        LayoutModifierNodeCoordinator$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
        throw null;
    }

    @Override // com.google.android.material.behavior.HideBottomViewOnScrollBehavior,
              // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onStartNestedScroll(
            CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
        LayoutModifierNodeCoordinator$$ExternalSyntheticThrowCCEIfNotNull0.m(view);
        throw null;
    }

    public BottomAppBar$Behavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        new View
                .OnLayoutChangeListener() { // from class:
                                            // com.google.android.material.bottomappbar.BottomAppBar$Behavior.1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(
                    View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                        BottomAppBar$Behavior.this.viewRef.get());
                view.removeOnLayoutChangeListener(this);
            }
        };
        new Rect();
    }
}
