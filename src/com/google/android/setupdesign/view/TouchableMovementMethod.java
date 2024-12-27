package com.google.android.setupdesign.view;

import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface TouchableMovementMethod {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TouchableLinkMovementMethod extends LinkMovementMethod
            implements TouchableMovementMethod {
        public MotionEvent lastEvent;
        public boolean lastEventResult;

        @Override // android.text.method.LinkMovementMethod,
                  // android.text.method.ScrollingMovementMethod,
                  // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
        public final boolean onTouchEvent(
                TextView textView, Spannable spannable, MotionEvent motionEvent) {
            this.lastEvent = motionEvent;
            boolean onTouchEvent = super.onTouchEvent(textView, spannable, motionEvent);
            if (motionEvent.getAction() == 0) {
                this.lastEventResult = Selection.getSelectionStart(spannable) != -1;
            } else {
                this.lastEventResult = onTouchEvent;
            }
            return onTouchEvent;
        }
    }
}
