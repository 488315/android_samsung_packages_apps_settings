package com.google.android.setupcompat.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.FrameLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class StatusBarBackgroundLayout extends FrameLayout {
    public Object lastInsets;
    public Drawable statusBarBackground;

    public StatusBarBackgroundLayout(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.lastInsets = windowInsets;
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.lastInsets == null) {
            requestApplyInsets();
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int systemWindowInsetTop;
        super.onDraw(canvas);
        Object obj = this.lastInsets;
        if (obj == null
                || (systemWindowInsetTop = ((WindowInsets) obj).getSystemWindowInsetTop()) <= 0) {
            return;
        }
        this.statusBarBackground.setBounds(0, 0, getWidth(), systemWindowInsetTop);
        this.statusBarBackground.draw(canvas);
    }

    public StatusBarBackgroundLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @TargetApi(11)
    public StatusBarBackgroundLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
