package com.sec.android.secsetupwizardlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class BottomScrollView extends ScrollView {
    public BottomScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    public final void onScrollChanged(int i, int i2, int i3, int i4) {
        if (isAttachedToWindow()) {
            getChildAt(getChildCount() - 1).getBottom();
            getHeight();
            getScrollY();
        }
        super.onScrollChanged(i, i2, i3, i4);
    }

    public BottomScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BottomScrollView(Context context) {
        super(context);
    }
}
