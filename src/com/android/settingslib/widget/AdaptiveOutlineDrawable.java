package com.android.settingslib.widget;

import android.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.DrawableWrapper;
import android.util.PathParser;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdaptiveOutlineDrawable extends DrawableWrapper {
    public final Bitmap mBitmap;
    public final int mInsetPx;
    Paint mOutlinePaint;
    public final Path mPath;
    public final int mStrokeWidth;
    public final int mType;

    public AdaptiveOutlineDrawable(Resources resources, Bitmap bitmap) {
        super(new AdaptiveIconShapeDrawable(resources));
        this.mType = 0;
        getDrawable().setTint(-1);
        this.mPath =
                new Path(
                        PathParser.createPathFromPathData(
                                resources.getString(R.string.elapsed_time_short_format_h_mm_ss)));
        this.mStrokeWidth =
                resources.getDimensionPixelSize(
                        com.android.settings.R.dimen.adaptive_outline_stroke);
        Paint paint = new Paint();
        this.mOutlinePaint = paint;
        paint.setColor(resources.getColor(com.android.settings.R.color.bt_outline_color, null));
        this.mOutlinePaint.setStyle(Paint.Style.STROKE);
        this.mOutlinePaint.setStrokeWidth(this.mStrokeWidth);
        this.mOutlinePaint.setAntiAlias(true);
        this.mInsetPx =
                resources.getDimensionPixelSize(
                        com.android.settings.R.dimen.dashboard_tile_foreground_image_inset);
        this.mBitmap = bitmap;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        Rect bounds = getBounds();
        int save = canvas.save();
        canvas.scale((bounds.right - bounds.left) / 100.0f, (bounds.bottom - bounds.top) / 100.0f);
        if (this.mType == 0) {
            canvas.drawPath(this.mPath, this.mOutlinePaint);
        } else {
            canvas.drawCircle(50.0f, 50.0f, 48.0f, this.mOutlinePaint);
        }
        canvas.restoreToCount(save);
        Bitmap bitmap = this.mBitmap;
        int i = bounds.left;
        int i2 = this.mInsetPx;
        canvas.drawBitmap(bitmap, i + i2, bounds.top + i2, (Paint) null);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return (this.mInsetPx * 2) + this.mBitmap.getHeight();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return (this.mInsetPx * 2) + this.mBitmap.getWidth();
    }
}
