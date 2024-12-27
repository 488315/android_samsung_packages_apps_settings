package com.facebook.shimmer;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import androidx.compose.animation.AndroidFlingSpline$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ShimmerDrawable extends Drawable {
    public final Rect mDrawRect;
    public final Matrix mShaderMatrix;
    public Shimmer mShimmer;
    public final Paint mShimmerPaint;
    public final AnonymousClass1 mUpdateListener =
            new ValueAnimator
                    .AnimatorUpdateListener() { // from class:
                                                // com.facebook.shimmer.ShimmerDrawable.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ShimmerDrawable.this.invalidateSelf();
                }
            };
    public ValueAnimator mValueAnimator;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.facebook.shimmer.ShimmerDrawable$1] */
    public ShimmerDrawable() {
        Paint paint = new Paint();
        this.mShimmerPaint = paint;
        this.mDrawRect = new Rect();
        this.mShaderMatrix = new Matrix();
        paint.setAntiAlias(true);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        float m;
        float m2;
        if (this.mShimmer == null || this.mShimmerPaint.getShader() == null) {
            return;
        }
        float tan = (float) Math.tan(Math.toRadians(this.mShimmer.tilt));
        float width = (this.mDrawRect.width() * tan) + this.mDrawRect.height();
        float height = (tan * this.mDrawRect.height()) + this.mDrawRect.width();
        ValueAnimator valueAnimator = this.mValueAnimator;
        float f = 0.0f;
        float animatedFraction = valueAnimator != null ? valueAnimator.getAnimatedFraction() : 0.0f;
        int i = this.mShimmer.direction;
        if (i != 1) {
            if (i == 2) {
                m2 =
                        AndroidFlingSpline$$ExternalSyntheticOutline0.m(
                                -height, height, animatedFraction, height);
            } else if (i != 3) {
                float f2 = -height;
                m2 =
                        AndroidFlingSpline$$ExternalSyntheticOutline0.m(
                                height, f2, animatedFraction, f2);
            } else {
                m =
                        AndroidFlingSpline$$ExternalSyntheticOutline0.m(
                                -width, width, animatedFraction, width);
            }
            f = m2;
            m = 0.0f;
        } else {
            float f3 = -width;
            m = AndroidFlingSpline$$ExternalSyntheticOutline0.m(width, f3, animatedFraction, f3);
        }
        this.mShaderMatrix.reset();
        this.mShaderMatrix.setRotate(
                this.mShimmer.tilt, this.mDrawRect.width() / 2.0f, this.mDrawRect.height() / 2.0f);
        this.mShaderMatrix.postTranslate(f, m);
        this.mShimmerPaint.getShader().setLocalMatrix(this.mShaderMatrix);
        canvas.drawRect(this.mDrawRect, this.mShimmerPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        Shimmer shimmer = this.mShimmer;
        return (shimmer == null || !(shimmer.clipToChildren || shimmer.alphaShimmer)) ? -1 : -3;
    }

    public final void maybeStartShimmer() {
        Shimmer shimmer;
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator == null
                || valueAnimator.isStarted()
                || (shimmer = this.mShimmer) == null
                || !shimmer.autoStart
                || getCallback() == null) {
            return;
        }
        this.mValueAnimator.start();
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mDrawRect.set(0, 0, rect.width(), rect.height());
        updateShader();
        maybeStartShimmer();
    }

    public final void updateShader() {
        Shimmer shimmer;
        Shader radialGradient;
        Rect bounds = getBounds();
        int width = bounds.width();
        int height = bounds.height();
        if (width == 0 || height == 0 || (shimmer = this.mShimmer) == null) {
            return;
        }
        int i = shimmer.fixedWidth;
        if (i <= 0) {
            i = Math.round(shimmer.widthRatio * width);
        }
        Shimmer shimmer2 = this.mShimmer;
        int i2 = shimmer2.fixedHeight;
        if (i2 <= 0) {
            i2 = Math.round(shimmer2.heightRatio * height);
        }
        Shimmer shimmer3 = this.mShimmer;
        boolean z = true;
        if (shimmer3.shape != 1) {
            int i3 = shimmer3.direction;
            if (i3 != 1 && i3 != 3) {
                z = false;
            }
            if (z) {
                i = 0;
            }
            if (!z) {
                i2 = 0;
            }
            Shimmer shimmer4 = this.mShimmer;
            radialGradient =
                    new LinearGradient(
                            0.0f,
                            0.0f,
                            i,
                            i2,
                            shimmer4.colors,
                            shimmer4.positions,
                            Shader.TileMode.CLAMP);
        } else {
            float f = i2 / 2.0f;
            float max = (float) (Math.max(i, i2) / Math.sqrt(2.0d));
            Shimmer shimmer5 = this.mShimmer;
            radialGradient =
                    new RadialGradient(
                            i / 2.0f,
                            f,
                            max,
                            shimmer5.colors,
                            shimmer5.positions,
                            Shader.TileMode.CLAMP);
        }
        this.mShimmerPaint.setShader(radialGradient);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {}

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {}
}
