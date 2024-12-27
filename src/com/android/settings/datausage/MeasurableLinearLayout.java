package com.android.settings.datausage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MeasurableLinearLayout extends RelativeLayout {
    public MeasurableLinearLayout(Context context) {
        super(context, null);
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    public MeasurableLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public MeasurableLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
    }

    public MeasurableLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
