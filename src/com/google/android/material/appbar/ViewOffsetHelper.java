package com.google.android.material.appbar;

import android.view.View;

import androidx.core.view.ViewCompat;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ViewOffsetHelper {
    public int layoutLeft;
    public int layoutTop;
    public final int offsetLeft;
    public int offsetTop;
    public final View view;

    public ViewOffsetHelper(View view) {
        this.view = view;
    }

    public final void applyOffsets() {
        View view = this.view;
        int top = this.offsetTop - (view.getTop() - this.layoutTop);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        view.offsetTopAndBottom(top);
        View view2 = this.view;
        view2.offsetLeftAndRight(this.offsetLeft - (view2.getLeft() - this.layoutLeft));
    }

    public final boolean setTopAndBottomOffset(int i) {
        if (this.offsetTop == i) {
            return false;
        }
        this.offsetTop = i;
        applyOffsets();
        return true;
    }
}
