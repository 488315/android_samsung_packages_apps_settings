package com.android.launcher3.icons;

import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final class ThemedIconDrawable extends FastBitmapDrawable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BitmapInfo bitmapInfo;
    public final int colorBg;
    public final int colorFg;
    public final Bitmap mBgBitmap;
    public final ColorFilter mBgFilter;
    public final Paint mBgPaint;
    public final ColorFilter mMonoFilter;
    public final Bitmap mMonoIcon;
    public final Paint mMonoPaint;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ThemedConstantState extends FastBitmapDrawable.FastBitmapConstantState {
        public final BitmapInfo bitmapInfo;
        public final int colorBg;
        public final int colorFg;

        public ThemedConstantState(BitmapInfo bitmapInfo, int i, int i2) {
            super(bitmapInfo.icon, bitmapInfo.color);
            this.bitmapInfo = bitmapInfo;
            this.colorBg = i;
            this.colorFg = i2;
        }

        @Override // com.android.launcher3.icons.FastBitmapDrawable.FastBitmapConstantState
        public final FastBitmapDrawable createDrawable() {
            return new ThemedIconDrawable(this);
        }
    }

    public ThemedIconDrawable(ThemedConstantState themedConstantState) {
        super(themedConstantState.mBitmap, themedConstantState.colorFg);
        Paint paint = new Paint(3);
        this.mMonoPaint = paint;
        Paint paint2 = new Paint(3);
        this.mBgPaint = paint2;
        BitmapInfo bitmapInfo = themedConstantState.bitmapInfo;
        this.bitmapInfo = bitmapInfo;
        int i = themedConstantState.colorBg;
        this.colorBg = i;
        int i2 = themedConstantState.colorFg;
        this.colorFg = i2;
        this.mMonoIcon = bitmapInfo.mMono;
        BlendMode blendMode = BlendMode.SRC_IN;
        BlendModeColorFilter blendModeColorFilter = new BlendModeColorFilter(i2, blendMode);
        this.mMonoFilter = blendModeColorFilter;
        paint.setColorFilter(blendModeColorFilter);
        this.mBgBitmap = bitmapInfo.mWhiteShadowLayer;
        BlendModeColorFilter blendModeColorFilter2 = new BlendModeColorFilter(i, blendMode);
        this.mBgFilter = blendModeColorFilter2;
        paint2.setColorFilter(blendModeColorFilter2);
    }

    @Override // com.android.launcher3.icons.FastBitmapDrawable
    public final void drawInternal(Canvas canvas, Rect rect) {
        canvas.drawBitmap(this.mBgBitmap, (Rect) null, rect, this.mBgPaint);
        canvas.drawBitmap(this.mMonoIcon, (Rect) null, rect, this.mMonoPaint);
    }

    @Override // com.android.launcher3.icons.FastBitmapDrawable
    public final FastBitmapDrawable.FastBitmapConstantState newConstantState() {
        return new ThemedConstantState(this.bitmapInfo, this.colorBg, this.colorFg);
    }

    @Override // com.android.launcher3.icons.FastBitmapDrawable
    public final void updateFilter() {
        super.updateFilter();
        int i = this.mIsDisabled ? (int) (this.mDisabledAlpha * 255.0f) : 255;
        this.mBgPaint.setAlpha(i);
        this.mBgPaint.setColorFilter(
                this.mIsDisabled
                        ? new BlendModeColorFilter(
                                FastBitmapDrawable.getDisabledColor(this.colorBg), BlendMode.SRC_IN)
                        : this.mBgFilter);
        this.mMonoPaint.setAlpha(i);
        this.mMonoPaint.setColorFilter(
                this.mIsDisabled
                        ? new BlendModeColorFilter(
                                FastBitmapDrawable.getDisabledColor(this.colorFg), BlendMode.SRC_IN)
                        : this.mMonoFilter);
    }
}
