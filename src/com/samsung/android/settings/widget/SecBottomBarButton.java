package com.samsung.android.settings.widget;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecBottomBarButton extends Button {
    public SecBottomBarButton(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        setAlpha(z ? 1.0f : 0.4f);
    }

    public SecBottomBarButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.buttonStyle);
    }

    public SecBottomBarButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecBottomBarButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setEnabled(isEnabled());
        semSetButtonShapeEnabled(true);
    }
}
