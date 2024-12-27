package com.android.launcher3.icons;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.FloatProperty;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class FastBitmapDrawable extends Drawable implements Drawable.Callback {
    protected static final float HOVERED_SCALE = 1.1f;
    protected static final float PRESSED_SCALE = 1.1f;
    public Drawable mBadge;
    public final Bitmap mBitmap;
    public ColorFilter mColorFilter;
    public final int mIconColor;
    public boolean mIsDisabled;
    protected boolean mIsHovered;
    protected boolean mIsPressed;
    protected ObjectAnimator mScaleAnimation;
    public static final Interpolator ACCEL = new AccelerateInterpolator();
    public static final Interpolator DEACCEL = new DecelerateInterpolator();
    public static final Interpolator HOVER_EMPHASIZED_DECELERATE_INTERPOLATOR =
            new PathInterpolator(0.05f, 0.7f, 0.1f, 1.0f);
    protected static final FloatProperty<FastBitmapDrawable> SCALE = new AnonymousClass1("scale");
    public final Paint mPaint = new Paint(3);
    public float mDisabledAlpha = 1.0f;
    public int mCreationFlags = 0;
    public float mScale = 1.0f;
    public int mAlpha = 255;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.launcher3.icons.FastBitmapDrawable$1, reason: invalid class name */
    public final class AnonymousClass1 extends FloatProperty {
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((FastBitmapDrawable) obj).mScale);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            FastBitmapDrawable fastBitmapDrawable = (FastBitmapDrawable) obj;
            fastBitmapDrawable.mScale = f;
            fastBitmapDrawable.invalidateSelf();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class FastBitmapConstantState extends Drawable.ConstantState {
        public Drawable.ConstantState mBadgeConstantState;
        public final Bitmap mBitmap;
        public int mCreationFlags = 0;
        public final int mIconColor;
        public boolean mIsDisabled;

        public FastBitmapConstantState(Bitmap bitmap, int i) {
            this.mBitmap = bitmap;
            this.mIconColor = i;
        }

        public FastBitmapDrawable createDrawable() {
            return new FastBitmapDrawable(this.mBitmap, this.mIconColor);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final FastBitmapDrawable newDrawable() {
            FastBitmapDrawable createDrawable = createDrawable();
            boolean z = this.mIsDisabled;
            if (createDrawable.mIsDisabled != z) {
                createDrawable.mIsDisabled = z;
                createDrawable.updateFilter();
            }
            Drawable.ConstantState constantState = this.mBadgeConstantState;
            if (constantState != null) {
                Drawable newDrawable = constantState.newDrawable();
                Drawable drawable = createDrawable.mBadge;
                if (drawable != null) {
                    drawable.setCallback(null);
                }
                createDrawable.mBadge = newDrawable;
                if (newDrawable != null) {
                    newDrawable.setCallback(createDrawable);
                }
                Rect bounds = createDrawable.getBounds();
                Drawable drawable2 = createDrawable.mBadge;
                if (drawable2 != null) {
                    int width = bounds.width();
                    float f = BaseIconFactory.LEGACY_ICON_SCALE;
                    int i = (int) (width * 0.444f);
                    int i2 = bounds.right;
                    int i3 = bounds.bottom;
                    drawable2.setBounds(i2 - i, i3 - i, i2, i3);
                }
                createDrawable.updateFilter();
            }
            createDrawable.mCreationFlags = this.mCreationFlags;
            return createDrawable;
        }
    }

    public FastBitmapDrawable(Bitmap bitmap, int i) {
        this.mBitmap = bitmap;
        this.mIconColor = i;
        setFilterBitmap(true);
    }

    public static final int getDisabledColor(int i) {
        int min =
                Math.min(
                        Math.round(
                                (((Color.blue(i) + (Color.green(i) + Color.red(i))) / 3) * 0.5f)
                                        + 127),
                        255);
        return Color.rgb(min, min, min);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (this.mScale == 1.0f) {
            drawInternal(canvas, getBounds());
            Drawable drawable = this.mBadge;
            if (drawable != null) {
                drawable.draw(canvas);
                return;
            }
            return;
        }
        int save = canvas.save();
        Rect bounds = getBounds();
        float f = this.mScale;
        canvas.scale(f, f, bounds.exactCenterX(), bounds.exactCenterY());
        drawInternal(canvas, bounds);
        Drawable drawable2 = this.mBadge;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        canvas.restoreToCount(save);
    }

    public void drawInternal(Canvas canvas, Rect rect) {
        canvas.drawBitmap(this.mBitmap, (Rect) null, rect, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.mAlpha;
    }

    public Drawable getBadge() {
        return this.mBadge;
    }

    @Override // android.graphics.drawable.Drawable
    public final ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        FastBitmapConstantState newConstantState = newConstantState();
        newConstantState.mIsDisabled = this.mIsDisabled;
        Drawable drawable = this.mBadge;
        if (drawable != null) {
            newConstantState.mBadgeConstantState = drawable.getConstantState();
        }
        newConstantState.mCreationFlags = this.mCreationFlags;
        return newConstantState;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.mBitmap.getHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.mBitmap.getWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getMinimumHeight() {
        return getBounds().height();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getMinimumWidth() {
        return getBounds().width();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        if (drawable == this.mBadge) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        return true;
    }

    public FastBitmapConstantState newConstantState() {
        return new FastBitmapConstantState(this.mBitmap, this.mIconColor);
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Drawable drawable = this.mBadge;
        if (drawable != null) {
            int width = rect.width();
            float f = BaseIconFactory.LEGACY_ICON_SCALE;
            int i = (int) (width * 0.444f);
            int i2 = rect.right;
            int i3 = rect.bottom;
            drawable.setBounds(i2 - i, i3 - i, i2, i3);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        boolean z;
        int length = iArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            if (iArr[i] == 16842919) {
                z = true;
                break;
            }
            i++;
        }
        if (this.mIsPressed == z && !this.mIsHovered) {
            return false;
        }
        ObjectAnimator objectAnimator = this.mScaleAnimation;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        float f = z ? 1.1f : 1.0f;
        if (this.mScale != f) {
            if (isVisible()) {
                boolean z2 = this.mIsPressed;
                Interpolator interpolator =
                        z != z2 ? z ? ACCEL : DEACCEL : HOVER_EMPHASIZED_DECELERATE_INTERPOLATOR;
                int i2 = z != z2 ? 200 : 300;
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, SCALE, f);
                this.mScaleAnimation = ofFloat;
                ofFloat.setDuration(i2);
                this.mScaleAnimation.setInterpolator(interpolator);
                this.mScaleAnimation.start();
            } else {
                this.mScale = f;
                invalidateSelf();
            }
        }
        this.mIsPressed = z;
        this.mIsHovered = false;
        return true;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        if (drawable == this.mBadge) {
            scheduleSelf(runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        if (this.mAlpha != i) {
            this.mAlpha = i;
            this.mPaint.setAlpha(i);
            invalidateSelf();
            Drawable drawable = this.mBadge;
            if (drawable != null) {
                drawable.setAlpha(i);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        updateFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        this.mPaint.setAntiAlias(z);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }

    public void updateFilter() {
        ColorFilter colorFilter;
        Paint paint = this.mPaint;
        if (this.mIsDisabled) {
            float f = this.mDisabledAlpha;
            ColorMatrix colorMatrix = new ColorMatrix();
            ColorMatrix colorMatrix2 = new ColorMatrix();
            colorMatrix2.setSaturation(0.0f);
            float[] array = colorMatrix.getArray();
            array[0] = 0.5f;
            array[6] = 0.5f;
            array[12] = 0.5f;
            float f2 = 127;
            array[4] = f2;
            array[9] = f2;
            array[14] = f2;
            array[18] = f;
            colorMatrix2.preConcat(colorMatrix);
            colorFilter = new ColorMatrixColorFilter(colorMatrix2);
        } else {
            colorFilter = this.mColorFilter;
        }
        paint.setColorFilter(colorFilter);
        Drawable drawable = this.mBadge;
        if (drawable != null) {
            drawable.setColorFilter(this.mPaint.getColorFilter());
        }
        invalidateSelf();
    }
}
