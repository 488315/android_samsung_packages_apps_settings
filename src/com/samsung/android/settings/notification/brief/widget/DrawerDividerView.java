package com.samsung.android.settings.notification.brief.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DrawerDividerView extends ImageView {
    public final BitmapDrawable mBitmapDrawable;

    public DrawerDividerView(Context context) {
        super(context);
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BitmapDrawable bitmapDrawable = this.mBitmapDrawable;
        if (bitmapDrawable != null) {
            Shader.TileMode tileMode = Shader.TileMode.REPEAT;
            bitmapDrawable.setTileModeXY(tileMode, tileMode);
            Rect clipBounds = canvas.getClipBounds();
            clipBounds.set(
                    clipBounds.left - 1, clipBounds.top, clipBounds.right - 2, clipBounds.bottom);
            this.mBitmapDrawable.setBounds(clipBounds);
            this.mBitmapDrawable.draw(canvas);
        }
    }

    public DrawerDividerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Bitmap createBitmap;
        TypedArray obtainStyledAttributes =
                getContext()
                        .obtainStyledAttributes(attributeSet, R$styleable.DrawDividerView, 0, 0);
        if (obtainStyledAttributes != null) {
            int dimensionPixelSize =
                    getResources()
                            .getDimensionPixelSize(R.dimen.brief_popup_list_divider_point_size);
            int dimensionPixelSize2 =
                    obtainStyledAttributes.getDimensionPixelSize(
                            0,
                            getResources()
                                    .getDimensionPixelOffset(
                                            R.dimen.brief_popup_list_divider_point_margin));
            Drawable drawable = getResources().getDrawable(R.drawable.list_divider_shape, null);
            if (drawable != null) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.getDrawable(0);
                layerDrawable.setLayerInsetRight(0, dimensionPixelSize2);
                gradientDrawable.setSize(dimensionPixelSize, dimensionPixelSize);
                if (drawable instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    if (bitmapDrawable.getBitmap() != null) {
                        createBitmap = bitmapDrawable.getBitmap();
                        this.mBitmapDrawable = new BitmapDrawable(getResources(), createBitmap);
                    }
                }
                createBitmap =
                        (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0)
                                ? Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
                                : Bitmap.createBitmap(
                                        drawable.getIntrinsicWidth(),
                                        drawable.getIntrinsicHeight(),
                                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                this.mBitmapDrawable = new BitmapDrawable(getResources(), createBitmap);
            }
            obtainStyledAttributes.recycle();
        }
    }

    public DrawerDividerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public DrawerDividerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
