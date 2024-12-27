package com.android.settings.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.SeslSeekBar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SecNewModeSeekBar extends SeslSeekBar {
    public SecNewModeSeekBar(Context context) {
        super(context);
    }

    @Override // androidx.appcompat.widget.SeslAbsSeekBar
    public final void drawTickMarks(Canvas canvas) {
        Drawable tickMark = getTickMark();
        if (tickMark != null) {
            int max = getMax();
            int intrinsicWidth = tickMark.getIntrinsicWidth();
            int intrinsicHeight = tickMark.getIntrinsicHeight();
            int i = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
            int i2 = intrinsicHeight >= 0 ? intrinsicHeight / 2 : 1;
            tickMark.setBounds(-i, -i2, i, i2);
            float width =
                    (((getWidth() - getPaddingLeft()) - getPaddingRight()) - (intrinsicWidth * 2))
                            / max;
            int save = canvas.save();
            canvas.translate(getPaddingLeft() + intrinsicWidth, getHeight() / 2.0f);
            for (int i3 = 0; i3 <= max; i3++) {
                if (i3 < getProgress()) {
                    canvas.translate(width, 0.0f);
                } else {
                    tickMark.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
            }
            canvas.restoreToCount(save);
        }
    }

    public SecNewModeSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SecNewModeSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
