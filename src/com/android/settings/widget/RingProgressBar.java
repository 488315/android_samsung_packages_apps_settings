package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RingProgressBar extends ProgressBar {
    public RingProgressBar(Context context) {
        this(context, null);
    }

    @Override // android.widget.ProgressBar, android.view.View
    public final synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int min = Math.min(getMeasuredHeight(), getMeasuredWidth());
        setMeasuredDimension(min, min);
    }

    public RingProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RingProgressBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.RingProgressBarStyle);
    }

    public RingProgressBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
