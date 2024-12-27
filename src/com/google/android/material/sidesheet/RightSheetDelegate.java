package com.google.android.material.sidesheet;

import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class RightSheetDelegate extends SheetDelegate {
    public final SideSheetBehavior sheetBehavior;

    public RightSheetDelegate(SideSheetBehavior sideSheetBehavior) {
        this.sheetBehavior = sideSheetBehavior;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int calculateInnerMargin(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return marginLayoutParams.rightMargin;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final float calculateSlideOffset(int i) {
        float f = this.sheetBehavior.parentWidth;
        return (f - i) / (f - getExpandedOffset());
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getCoplanarSiblingAdjacentMargin(
            ViewGroup.MarginLayoutParams marginLayoutParams) {
        return marginLayoutParams.rightMargin;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getExpandedOffset() {
        SideSheetBehavior sideSheetBehavior = this.sheetBehavior;
        return Math.max(
                0,
                (sideSheetBehavior.parentWidth - sideSheetBehavior.childWidth)
                        - sideSheetBehavior.innerMargin);
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getHiddenOffset() {
        return this.sheetBehavior.parentWidth;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getMaxViewPositionHorizontal() {
        return this.sheetBehavior.parentWidth;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getMinViewPositionHorizontal() {
        return getExpandedOffset();
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getOuterEdge(View view) {
        return view.getLeft() - this.sheetBehavior.innerMargin;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getParentInnerEdge(CoordinatorLayout coordinatorLayout) {
        return coordinatorLayout.getRight();
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getSheetEdge() {
        return 0;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final boolean isExpandingOutwards(float f) {
        return f < 0.0f;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final boolean isReleasedCloseToInnerEdge(View view) {
        return view.getLeft() > (getExpandedOffset() + this.sheetBehavior.parentWidth) / 2;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final boolean isSwipeSignificant(float f, float f2) {
        if (Math.abs(f) > Math.abs(f2)) {
            float abs = Math.abs(f);
            this.sheetBehavior.getClass();
            if (abs > 500) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final boolean shouldHide(View view, float f) {
        return Math.abs((f * this.sheetBehavior.hideFriction) + ((float) view.getRight())) > 0.5f;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final void updateCoplanarSiblingAdjacentMargin(
            ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        marginLayoutParams.rightMargin = i;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final void updateCoplanarSiblingLayoutParams(
            ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2) {
        int i3 = this.sheetBehavior.parentWidth;
        if (i <= i3) {
            marginLayoutParams.rightMargin = i3 - i;
        }
    }
}
