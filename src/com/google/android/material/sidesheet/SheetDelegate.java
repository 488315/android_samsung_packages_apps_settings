package com.google.android.material.sidesheet;

import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SheetDelegate {
    public abstract int calculateInnerMargin(ViewGroup.MarginLayoutParams marginLayoutParams);

    public abstract float calculateSlideOffset(int i);

    public abstract int getCoplanarSiblingAdjacentMargin(
            ViewGroup.MarginLayoutParams marginLayoutParams);

    public abstract int getExpandedOffset();

    public abstract int getHiddenOffset();

    public abstract int getMaxViewPositionHorizontal();

    public abstract int getMinViewPositionHorizontal();

    public abstract int getOuterEdge(View view);

    public abstract int getParentInnerEdge(CoordinatorLayout coordinatorLayout);

    public abstract int getSheetEdge();

    public abstract boolean isExpandingOutwards(float f);

    public abstract boolean isReleasedCloseToInnerEdge(View view);

    public abstract boolean isSwipeSignificant(float f, float f2);

    public abstract boolean shouldHide(View view, float f);

    public abstract void updateCoplanarSiblingAdjacentMargin(
            ViewGroup.MarginLayoutParams marginLayoutParams, int i);

    public abstract void updateCoplanarSiblingLayoutParams(
            ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2);
}
