package com.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AspectRatioFrameLayout extends FrameLayout {
    float mAspectRatio;

    public AspectRatioFrameLayout(Context context) {
        this(context, null);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth == 0 || measuredHeight == 0) {
            return;
        }
        float f = measuredHeight;
        if (Math.abs(this.mAspectRatio - (measuredWidth / f)) <= 0.01f) {
            return;
        }
        super.onMeasure(
                View.MeasureSpec.makeMeasureSpec((int) (f * this.mAspectRatio), 1073741824),
                View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
    }

    public void setAspectRatio(float f) {
        this.mAspectRatio = f;
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAspectRatio = 1.0f;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(
                            attributeSet, R$styleable.AspectRatioFrameLayout);
            this.mAspectRatio = obtainStyledAttributes.getFloat(0, 1.0f);
            obtainStyledAttributes.recycle();
        }
    }
}
