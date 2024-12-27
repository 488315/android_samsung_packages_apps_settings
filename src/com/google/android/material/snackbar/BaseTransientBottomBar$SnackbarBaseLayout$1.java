package com.google.android.material.snackbar;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BaseTransientBottomBar$SnackbarBaseLayout$1 implements View.OnTouchListener {
    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        View view2;
        if (!(view instanceof ViewGroup)) {
            return true;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        ViewGroup viewGroup = (ViewGroup) view;
        BaseTransientBottomBar$SnackbarBaseLayout$1 baseTransientBottomBar$SnackbarBaseLayout$1 =
                Snackbar$SnackbarLayout.consumeAllTouchListener;
        int childCount = viewGroup.getChildCount() - 1;
        while (true) {
            if (childCount < 0) {
                view2 = null;
                break;
            }
            view2 = viewGroup.getChildAt(childCount);
            float x2 = view2.getX();
            float y2 = view2.getY();
            float width = view2.getWidth() + x2;
            float height = view2.getHeight() + y2;
            if (x >= x2 && y >= y2 && x < width && y < height) {
                break;
            }
            childCount--;
        }
        return view2 != null;
    }
}
