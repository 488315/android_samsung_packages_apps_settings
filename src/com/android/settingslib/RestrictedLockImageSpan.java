package com.android.settingslib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RestrictedLockImageSpan extends ImageSpan {
    public final float mExtraPadding;
    public final Drawable mRestrictedPadlock;

    public RestrictedLockImageSpan(Context context) {
        super((Drawable) null);
        this.mExtraPadding =
                context.getResources().getDimensionPixelSize(R.dimen.restricted_icon_padding);
        this.mRestrictedPadlock = RestrictedLockUtilsInternal.getRestrictedPadlock(context);
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public final void draw(
            Canvas canvas,
            CharSequence charSequence,
            int i,
            int i2,
            float f,
            int i3,
            int i4,
            int i5,
            Paint paint) {
        Drawable drawable = this.mRestrictedPadlock;
        canvas.save();
        canvas.translate(f + this.mExtraPadding, (i5 - drawable.getBounds().bottom) / 2.0f);
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.text.style.ImageSpan, android.text.style.DynamicDrawableSpan
    public final Drawable getDrawable() {
        return this.mRestrictedPadlock;
    }

    @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
    public final int getSize(
            Paint paint,
            CharSequence charSequence,
            int i,
            int i2,
            Paint.FontMetricsInt fontMetricsInt) {
        return (int)
                ((this.mExtraPadding * 2.0f)
                        + super.getSize(paint, charSequence, i, i2, fontMetricsInt));
    }
}
