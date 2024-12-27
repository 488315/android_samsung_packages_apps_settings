package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.SeslSeekBar;

import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class A11ySeekBar extends SeslSeekBar {
    public boolean centerBasedBar;

    public A11ySeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.A11ySeekBar);
        for (int i = 0; i < obtainStyledAttributes.getIndexCount(); i++) {
            try {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 1) {
                    setSeamless(obtainStyledAttributes.getBoolean(index, true));
                } else if (index == 0) {
                    this.centerBasedBar = obtainStyledAttributes.getBoolean(index, false);
                } else if (index == 2) {
                    setMode(obtainStyledAttributes.getInt(2, 0));
                }
            } catch (Throwable th) {
                if (obtainStyledAttributes != null) {
                    try {
                        obtainStyledAttributes.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        obtainStyledAttributes.close();
        setProgressTintMode(PorterDuff.Mode.SRC);
    }

    @Override // androidx.appcompat.widget.SeslAbsSeekBar
    public final void drawTickMarks(Canvas canvas) {
        if (!this.centerBasedBar) {
            super.drawTickMarks(canvas);
            return;
        }
        Drawable tickMark = getTickMark();
        if (tickMark != null) {
            int intrinsicWidth = tickMark.getIntrinsicWidth();
            int intrinsicHeight = tickMark.getIntrinsicHeight();
            int i = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
            int i2 = intrinsicHeight >= 0 ? intrinsicHeight / 2 : 1;
            tickMark.setBounds(-i, -i2, i, i2);
            float width =
                    (((getWidth() - getPaddingLeft()) - getPaddingRight()) - (intrinsicWidth * 2))
                            / 2.0f;
            int save = canvas.save();
            canvas.translate(getPaddingLeft() + intrinsicWidth, getHeight() / 2.0f);
            for (int i3 = 0; i3 <= 2; i3++) {
                tickMark.draw(canvas);
                canvas.translate(width, 0.0f);
            }
            canvas.restoreToCount(save);
        }
    }

    @Override // android.view.View
    public final boolean performHapticFeedback(int i) {
        int progress = getProgress();
        if (this.centerBasedBar) {
            int min = getMin();
            int max = getMax();
            if (progress != (min + max) / 2 && progress != min && progress != max) {
                return false;
            }
        }
        return super.performHapticFeedback(i);
    }
}
