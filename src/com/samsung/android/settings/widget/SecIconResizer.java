package com.samsung.android.settings.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SecIconResizer {
    public final Canvas mCanvas;
    public final Context mContext;
    public int mIconHeight;
    public int mIconWidth;
    public final Rect mOldBounds = new Rect();

    public SecIconResizer(Context context) {
        this.mIconWidth = -1;
        this.mIconHeight = -1;
        Canvas canvas = new Canvas();
        this.mCanvas = canvas;
        this.mContext = context;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        Resources resources = context.getResources();
        int dimension = (int) resources.getDimension(R.dimen.sec_widget_app_icon_size);
        this.mIconHeight = dimension;
        this.mIconWidth = dimension;
        int i = resources.getConfiguration().screenLayout & 15;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "mScreenIsLarge : ", "IconResizer", i == 3 || i == 4);
    }

    public final Drawable createIconThumbnail(Drawable drawable) {
        int i = this.mIconWidth;
        int i2 = this.mIconHeight;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (drawable instanceof PaintDrawable) {
            PaintDrawable paintDrawable = (PaintDrawable) drawable;
            paintDrawable.setIntrinsicWidth(i);
            paintDrawable.setIntrinsicHeight(i2);
        }
        if (i <= 0 || i2 <= 0) {
            return drawable;
        }
        if (i >= intrinsicWidth && i2 >= intrinsicHeight) {
            if (intrinsicWidth >= i || intrinsicHeight >= i2) {
                return drawable;
            }
            Bitmap createBitmap =
                    Bitmap.createBitmap(this.mIconWidth, this.mIconHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = this.mCanvas;
            canvas.setBitmap(createBitmap);
            this.mOldBounds.set(drawable.getBounds());
            int i3 = 0 / 2;
            int i4 = 0 / 2;
            drawable.setBounds(i3, i4, i + i3, i2 + i4);
            drawable.draw(canvas);
            drawable.setBounds(this.mOldBounds);
            BitmapDrawable bitmapDrawable =
                    new BitmapDrawable(this.mContext.getResources(), createBitmap);
            canvas.setBitmap(null);
            return bitmapDrawable;
        }
        float f = intrinsicWidth / intrinsicHeight;
        if (intrinsicWidth > intrinsicHeight) {
            i2 = (int) (i / f);
        } else if (intrinsicHeight > intrinsicWidth) {
            i = (int) (i2 * f);
        }
        Bitmap createBitmap2 =
                Bitmap.createBitmap(this.mIconWidth, this.mIconHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = this.mCanvas;
        canvas2.setBitmap(createBitmap2);
        this.mOldBounds.set(drawable.getBounds());
        int i5 = (this.mIconWidth - i) / 2;
        int i6 = (this.mIconHeight - i2) / 2;
        drawable.setBounds(i5, i6, i + i5, i2 + i6);
        drawable.draw(canvas2);
        drawable.setBounds(this.mOldBounds);
        BitmapDrawable bitmapDrawable2 =
                new BitmapDrawable(this.mContext.getResources(), createBitmap2);
        canvas2.setBitmap(null);
        return bitmapDrawable2;
    }
}
