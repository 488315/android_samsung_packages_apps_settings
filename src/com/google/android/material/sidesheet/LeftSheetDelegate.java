package com.google.android.material.sidesheet;

import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LeftSheetDelegate extends SheetDelegate {
    public final SideSheetBehavior sheetBehavior;

    public LeftSheetDelegate(SideSheetBehavior sideSheetBehavior) {
        this.sheetBehavior = sideSheetBehavior;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int calculateInnerMargin(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return marginLayoutParams.leftMargin;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final float calculateSlideOffset(int i) {
        float hiddenOffset = getHiddenOffset();
        return (i - hiddenOffset) / (getExpandedOffset() - hiddenOffset);
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getCoplanarSiblingAdjacentMargin(
            ViewGroup.MarginLayoutParams marginLayoutParams) {
        return marginLayoutParams.leftMargin;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getExpandedOffset() {
        SideSheetBehavior sideSheetBehavior = this.sheetBehavior;
        return Math.max(0, sideSheetBehavior.parentInnerEdge + sideSheetBehavior.innerMargin);
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getHiddenOffset() {
        SideSheetBehavior sideSheetBehavior = this.sheetBehavior;
        return (-sideSheetBehavior.childWidth) - sideSheetBehavior.innerMargin;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getMaxViewPositionHorizontal() {
        return this.sheetBehavior.innerMargin;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getMinViewPositionHorizontal() {
        return -this.sheetBehavior.childWidth;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getOuterEdge(View view) {
        return view.getRight() + this.sheetBehavior.innerMargin;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getParentInnerEdge(CoordinatorLayout coordinatorLayout) {
        return coordinatorLayout.getLeft();
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final int getSheetEdge() {
        return 1;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final boolean isExpandingOutwards(float f) {
        return f > 0.0f;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final boolean isReleasedCloseToInnerEdge(View view) {
        return view.getRight() < (getExpandedOffset() - getHiddenOffset()) / 2;
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
        return Math.abs((f * this.sheetBehavior.hideFriction) + ((float) view.getLeft())) > 0.5f;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final void updateCoplanarSiblingAdjacentMargin(
            ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        marginLayoutParams.leftMargin = i;
    }

    @Override // com.google.android.material.sidesheet.SheetDelegate
    public final void updateCoplanarSiblingLayoutParams(
            ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2) {
        if (i <= this.sheetBehavior.parentWidth) {
            marginLayoutParams.leftMargin = i2;
        }
    }
}
