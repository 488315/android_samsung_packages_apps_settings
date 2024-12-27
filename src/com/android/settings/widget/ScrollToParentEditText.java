package com.android.settings.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImeAwareEditText;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ScrollToParentEditText extends ImeAwareEditText {
    public final Rect mRect;

    public ScrollToParentEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRect = new Rect();
    }

    public final boolean requestRectangleOnScreen(Rect rect, boolean z) {
        Object parent = getParent();
        if (!(parent instanceof View)) {
            return super.requestRectangleOnScreen(rect, z);
        }
        View view = (View) parent;
        view.getDrawingRect(this.mRect);
        return view.requestRectangleOnScreen(this.mRect, z);
    }
}
