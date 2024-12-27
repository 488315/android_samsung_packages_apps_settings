package com.android.launcher3.icons;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.InsetDrawable;
import android.util.SparseArray;

import com.android.launcher3.util.UserIconInfo;

import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract class BaseIconFactory implements AutoCloseable {
    public static final float LEGACY_ICON_SCALE =
            (1.0f / ((AdaptiveIconDrawable.getExtraInsetFraction() * 2.0f) + 1.0f)) * 0.7f;
    public final Canvas mCanvas;
    public final ColorExtractor mColorExtractor;
    public final Context mContext;
    public final int mFillResIconDpi;
    public final int mIconBitmapSize;
    public IconNormalizer mNormalizer;
    public final Rect mOldBounds = new Rect();
    public ShadowGenerator mShadowGenerator;
    public final boolean mShapeDetection;
    public int mWrapperBackgroundColor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EmptyWrapper extends DrawableWrapper {
        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public final Drawable.ConstantState getConstantState() {
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return null;
            }
            return drawable.getConstantState();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IconOptions {
        public UserIconInfo mUserIconInfo;
    }

    static {
        Color.rgb(
                IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode,
                IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode,
                IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode);
    }

    public BaseIconFactory(Context context, int i, int i2) {
        new SparseArray();
        this.mWrapperBackgroundColor = -1;
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mShapeDetection = false;
        this.mFillResIconDpi = i;
        this.mIconBitmapSize = i2;
        applicationContext.getPackageManager();
        this.mColorExtractor = new ColorExtractor();
        Canvas canvas = new Canvas();
        this.mCanvas = canvas;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        this.mWrapperBackgroundColor = -1;
    }

    public static Drawable createScaledDrawable(Drawable drawable, float f) {
        float f2;
        float intrinsicHeight = drawable.getIntrinsicHeight();
        float intrinsicWidth = drawable.getIntrinsicWidth();
        if (intrinsicHeight <= intrinsicWidth || intrinsicWidth <= 0.0f) {
            f2 =
                    (intrinsicWidth <= intrinsicHeight || intrinsicHeight <= 0.0f)
                            ? f
                            : (intrinsicHeight / intrinsicWidth) * f;
        } else {
            float f3 = (intrinsicWidth / intrinsicHeight) * f;
            f2 = f;
            f = f3;
        }
        float f4 = (1.0f - f) / 2.0f;
        float f5 = (1.0f - f2) / 2.0f;
        return new InsetDrawable(drawable, f4, f5, f4, f5);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mWrapperBackgroundColor = -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x017c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.launcher3.icons.BitmapInfo createBadgedIconBitmap(
            android.graphics.drawable.Drawable r22,
            com.android.launcher3.icons.BaseIconFactory.IconOptions r23) {
        /*
            Method dump skipped, instructions count: 791
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.launcher3.icons.BaseIconFactory.createBadgedIconBitmap(android.graphics.drawable.Drawable,"
                    + " com.android.launcher3.icons.BaseIconFactory$IconOptions):com.android.launcher3.icons.BitmapInfo");
    }
}
