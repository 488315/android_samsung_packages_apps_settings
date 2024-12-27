package com.facebook.shimmer;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ShimmerFrameLayout extends FrameLayout {
    public final Paint mContentPaint;
    public final ShimmerDrawable mShimmerDrawable;
    public final boolean mShowShimmer;

    public ShimmerFrameLayout(Context context) {
        super(context);
        this.mContentPaint = new Paint();
        this.mShimmerDrawable = new ShimmerDrawable();
        this.mShowShimmer = true;
        init(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mShowShimmer) {
            this.mShimmerDrawable.draw(canvas);
        }
    }

    public final void init(Context context, AttributeSet attributeSet) {
        Shimmer.AlphaHighlightBuilder alphaHighlightBuilder;
        setWillNotDraw(false);
        this.mShimmerDrawable.setCallback(this);
        if (attributeSet == null) {
            setShimmer(new Shimmer.AlphaHighlightBuilder(0).build());
            return;
        }
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.ShimmerFrameLayout, 0, 0);
        try {
            if (obtainStyledAttributes.hasValue(4) && obtainStyledAttributes.getBoolean(4, false)) {
                alphaHighlightBuilder = new Shimmer.AlphaHighlightBuilder(1);
                alphaHighlightBuilder.mShimmer.alphaShimmer = false;
            } else {
                alphaHighlightBuilder = new Shimmer.AlphaHighlightBuilder(0);
            }
            switch (alphaHighlightBuilder.$r8$classId) {
                case 1:
                    alphaHighlightBuilder.consumeAttributes$com$facebook$shimmer$Shimmer$Builder(
                            obtainStyledAttributes);
                    boolean hasValue = obtainStyledAttributes.hasValue(2);
                    Shimmer shimmer = alphaHighlightBuilder.mShimmer;
                    if (hasValue) {
                        shimmer.baseColor =
                                (obtainStyledAttributes.getColor(2, shimmer.baseColor) & 16777215)
                                        | (shimmer.baseColor & EmergencyPhoneWidget.BG_COLOR);
                    }
                    if (obtainStyledAttributes.hasValue(12)) {
                        shimmer.highlightColor =
                                obtainStyledAttributes.getColor(12, shimmer.highlightColor);
                        break;
                    }
                    break;
                default:
                    alphaHighlightBuilder =
                            alphaHighlightBuilder
                                    .consumeAttributes$com$facebook$shimmer$Shimmer$Builder(
                                            obtainStyledAttributes);
                    break;
            }
            setShimmer(alphaHighlightBuilder.build());
            obtainStyledAttributes.recycle();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mShimmerDrawable.maybeStartShimmer();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopShimmer();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mShimmerDrawable.setBounds(0, 0, getWidth(), getHeight());
    }

    public final void setShimmer(Shimmer shimmer) {
        boolean z;
        ShimmerDrawable shimmerDrawable = this.mShimmerDrawable;
        shimmerDrawable.mShimmer = shimmer;
        if (shimmer != null) {
            shimmerDrawable.mShimmerPaint.setXfermode(
                    new PorterDuffXfermode(
                            shimmerDrawable.mShimmer.alphaShimmer
                                    ? PorterDuff.Mode.DST_IN
                                    : PorterDuff.Mode.SRC_IN));
        }
        shimmerDrawable.updateShader();
        if (shimmerDrawable.mShimmer != null) {
            ValueAnimator valueAnimator = shimmerDrawable.mValueAnimator;
            if (valueAnimator != null) {
                z = valueAnimator.isStarted();
                shimmerDrawable.mValueAnimator.cancel();
                shimmerDrawable.mValueAnimator.removeAllUpdateListeners();
            } else {
                z = false;
            }
            Shimmer shimmer2 = shimmerDrawable.mShimmer;
            ValueAnimator ofFloat =
                    ValueAnimator.ofFloat(
                            0.0f, (shimmer2.repeatDelay / shimmer2.animationDuration) + 1.0f);
            shimmerDrawable.mValueAnimator = ofFloat;
            ofFloat.setRepeatMode(shimmerDrawable.mShimmer.repeatMode);
            shimmerDrawable.mValueAnimator.setRepeatCount(shimmerDrawable.mShimmer.repeatCount);
            ValueAnimator valueAnimator2 = shimmerDrawable.mValueAnimator;
            Shimmer shimmer3 = shimmerDrawable.mShimmer;
            valueAnimator2.setDuration(shimmer3.animationDuration + shimmer3.repeatDelay);
            shimmerDrawable.mValueAnimator.addUpdateListener(shimmerDrawable.mUpdateListener);
            if (z) {
                shimmerDrawable.mValueAnimator.start();
            }
        }
        shimmerDrawable.invalidateSelf();
        if (shimmer == null || !shimmer.clipToChildren) {
            setLayerType(0, null);
        } else {
            setLayerType(2, this.mContentPaint);
        }
    }

    public final void stopShimmer() {
        ShimmerDrawable shimmerDrawable = this.mShimmerDrawable;
        ValueAnimator valueAnimator = shimmerDrawable.mValueAnimator;
        if (valueAnimator == null || valueAnimator == null || !valueAnimator.isStarted()) {
            return;
        }
        shimmerDrawable.mValueAnimator.cancel();
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mShimmerDrawable;
    }

    public ShimmerFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContentPaint = new Paint();
        this.mShimmerDrawable = new ShimmerDrawable();
        this.mShowShimmer = true;
        init(context, attributeSet);
    }

    public ShimmerFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContentPaint = new Paint();
        this.mShimmerDrawable = new ShimmerDrawable();
        this.mShowShimmer = true;
        init(context, attributeSet);
    }

    @TargetApi(21)
    public ShimmerFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mContentPaint = new Paint();
        this.mShimmerDrawable = new ShimmerDrawable();
        this.mShowShimmer = true;
        init(context, attributeSet);
    }
}
