package com.android.settings.applications.credentials;

import android.graphics.Canvas;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.util.DisplayMetrics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ImageUtils$IconResizer {
    public final Canvas mCanvas;
    public final int mIconHeight;
    public final int mIconWidth;
    public final DisplayMetrics mMetrics;
    public final Rect mOldBounds = new Rect();

    public ImageUtils$IconResizer(int i, int i2, DisplayMetrics displayMetrics) {
        Canvas canvas = new Canvas();
        this.mCanvas = canvas;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        this.mMetrics = displayMetrics;
        this.mIconWidth = i;
        this.mIconHeight = i2;
    }
}
