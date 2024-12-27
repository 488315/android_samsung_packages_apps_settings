package com.google.android.material.dialog;

import android.R;
import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class InsetDialogOnTouchListener implements View.OnTouchListener {
    public final Dialog dialog;
    public final int leftInset;
    public final int topInset;

    public InsetDialogOnTouchListener(Dialog dialog, Rect rect) {
        this.dialog = dialog;
        this.leftInset = rect.left;
        this.topInset = rect.top;
        ViewConfiguration.get(dialog.getContext()).getScaledWindowTouchSlop();
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        View findViewById = view.findViewById(R.id.content);
        int left = findViewById.getLeft() + this.leftInset;
        int width = findViewById.getWidth() + left;
        if (new RectF(
                        left,
                        findViewById.getTop() + this.topInset,
                        width,
                        findViewById.getHeight() + r4)
                .contains(motionEvent.getX(), motionEvent.getY())) {
            return false;
        }
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        if (motionEvent.getAction() == 1) {
            obtain.setAction(4);
        }
        view.performClick();
        return this.dialog.onTouchEvent(obtain);
    }
}
