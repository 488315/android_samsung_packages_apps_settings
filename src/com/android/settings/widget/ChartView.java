package com.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChartView extends FrameLayout {
    public final Rect mContent;
    public final int mOptimalWidth;
    public final float mOptimalWidthWeight;

    public ChartView(Context context) {
        this(context, null, 0);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mContent.set(
                getPaddingLeft(),
                getPaddingTop(),
                (i3 - i) - getPaddingRight(),
                (i4 - i2) - getPaddingBottom());
        this.mContent.width();
        this.mContent.height();
        throw null;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int i3 = this.mOptimalWidth;
        int i4 = measuredWidth - i3;
        if (i3 <= 0 || i4 <= 0) {
            return;
        }
        super.onMeasure(
                View.MeasureSpec.makeMeasureSpec(
                        (int) ((i4 * this.mOptimalWidthWeight) + i3), 1073741824),
                i2);
    }

    public ChartView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChartView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOptimalWidth = -1;
        this.mOptimalWidthWeight = 0.0f;
        this.mContent = new Rect();
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.ChartView, i, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        float f = obtainStyledAttributes.getFloat(1, 0.0f);
        this.mOptimalWidth = dimensionPixelSize;
        this.mOptimalWidthWeight = f;
        requestLayout();
        obtainStyledAttributes.recycle();
        setClipToPadding(false);
        setClipChildren(false);
    }
}
