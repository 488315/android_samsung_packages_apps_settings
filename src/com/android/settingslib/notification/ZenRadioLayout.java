package com.android.settingslib.notification;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenRadioLayout extends LinearLayout {
    public ZenRadioLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static View findFirstClickable(View view) {
        if (view.isClickable()) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View findFirstClickable = findFirstClickable(viewGroup.getChildAt(i));
            if (findFirstClickable != null) {
                return findFirstClickable;
            }
        }
        return null;
    }

    public static View findLastClickable(View view) {
        if (view.isClickable()) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View findLastClickable = findLastClickable(viewGroup.getChildAt(childCount));
            if (findLastClickable != null) {
                return findLastClickable;
            }
        }
        return null;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);
        ViewGroup viewGroup2 = (ViewGroup) getChildAt(1);
        int childCount = viewGroup.getChildCount();
        if (childCount != viewGroup2.getChildCount()) {
            throw new IllegalStateException("Expected matching children");
        }
        View view = null;
        boolean z = false;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = viewGroup.getChildAt(i3);
            View childAt2 = viewGroup2.getChildAt(i3);
            if (view != null) {
                childAt.setAccessibilityTraversalAfter(view.getId());
            }
            View findFirstClickable = findFirstClickable(childAt2);
            if (findFirstClickable != null) {
                findFirstClickable.setAccessibilityTraversalAfter(childAt.getId());
            }
            view = findLastClickable(childAt2);
            if (childAt.getLayoutParams().height != childAt2.getMeasuredHeight()) {
                childAt.getLayoutParams().height = childAt2.getMeasuredHeight();
                z = true;
            }
        }
        if (z) {
            super.onMeasure(i, i2);
        }
    }
}
