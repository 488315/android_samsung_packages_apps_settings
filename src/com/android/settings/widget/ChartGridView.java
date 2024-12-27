package com.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.android.internal.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChartGridView extends View {
    public final Drawable mBorder;
    public final Drawable mPrimary;
    public final Drawable mSecondary;

    public ChartGridView(Context context) {
        this(context, null, 0);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight() - getPaddingBottom();
        Drawable drawable = this.mSecondary;
        if (drawable != null) {
            drawable.getIntrinsicHeight();
            throw null;
        }
        Drawable drawable2 = this.mPrimary;
        if (drawable2 == null) {
            this.mBorder.setBounds(0, 0, width, height);
            this.mBorder.draw(canvas);
        } else {
            drawable2.getIntrinsicWidth();
            drawable2.getIntrinsicHeight();
            throw null;
        }
    }

    public ChartGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChartGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setWillNotDraw(false);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.ChartGridView, i, 0);
        this.mPrimary = obtainStyledAttributes.getDrawable(3);
        this.mSecondary = obtainStyledAttributes.getDrawable(4);
        this.mBorder = obtainStyledAttributes.getDrawable(2);
        TypedArray obtainStyledAttributes2 =
                context.obtainStyledAttributes(
                        obtainStyledAttributes.getResourceId(0, -1), R.styleable.TextAppearance);
        obtainStyledAttributes2.getDimensionPixelSize(0, 0);
        obtainStyledAttributes2.recycle();
        obtainStyledAttributes.getColorStateList(1).getDefaultColor();
        obtainStyledAttributes.recycle();
    }
}
