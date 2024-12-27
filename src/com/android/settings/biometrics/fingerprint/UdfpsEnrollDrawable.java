package com.android.settings.biometrics.fingerprint;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.util.PathParser;

import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UdfpsEnrollDrawable extends Drawable {
    public int mAlpha;
    public final Paint mBlueFill;
    public float mCurrentX;
    public float mCurrentY;
    public UdfpsEnrollHelper mEnrollHelper;
    public final int mEnrollIcon;
    public final ShapeDrawable mFingerprintDrawable;
    public final int mMovingTargetFill;
    public final Drawable mMovingTargetFpIcon;
    public final Paint mSensorOutlinePaint;
    public RectF mSensorRect;
    public final AnonymousClass1 mTargetAnimListener;
    public AnimatorSet mTargetAnimatorSet;
    public boolean mSkipDraw = false;
    public float mCurrentScale = 1.0f;
    public boolean mShouldShowTipHint = false;
    public boolean mShouldShowEdgeHint = false;

    /* JADX WARN: Type inference failed for: r6v5, types: [com.android.settings.biometrics.fingerprint.UdfpsEnrollDrawable$1] */
    public UdfpsEnrollDrawable(Context context, AttributeSet attributeSet) {
        ShapeDrawable shapeDrawable =
                new ShapeDrawable(
                        new PathShape(
                                PathParser.createPathFromPathData(
                                        context.getResources()
                                                .getString(R.string.config_udfpsIcon)),
                                72.0f,
                                72.0f));
        shapeDrawable.mutate();
        shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
        shapeDrawable.getPaint().setStrokeCap(Paint.Cap.ROUND);
        shapeDrawable.getPaint().setStrokeWidth(3.0f);
        this.mFingerprintDrawable = shapeDrawable;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet,
                        R$styleable.BiometricsEnrollView,
                        R.attr.biometricsEnrollStyle,
                        R.style.BiometricsEnrollStyle);
        this.mEnrollIcon = obtainStyledAttributes.getColor(0, 0);
        this.mMovingTargetFill = obtainStyledAttributes.getColor(5, 0);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint(0);
        this.mSensorOutlinePaint = paint;
        paint.setAntiAlias(true);
        paint.setColor(this.mMovingTargetFill);
        Paint.Style style = Paint.Style.FILL;
        paint.setStyle(style);
        Paint paint2 = new Paint(0);
        this.mBlueFill = paint2;
        paint2.setAntiAlias(true);
        paint2.setColor(this.mMovingTargetFill);
        paint2.setStyle(style);
        Drawable drawable =
                context.getResources().getDrawable(R.drawable.ic_enrollment_fingerprint, null);
        this.mMovingTargetFpIcon = drawable;
        drawable.setTint(this.mEnrollIcon);
        drawable.mutate();
        shapeDrawable.setTint(this.mEnrollIcon);
        setAlpha(255);
        this.mTargetAnimListener =
                new Animator
                        .AnimatorListener() { // from class:
                                              // com.android.settings.biometrics.fingerprint.UdfpsEnrollDrawable.1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        UdfpsEnrollDrawable.this.updateTipHintVisibility();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {}

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {}

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {}
                };
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (this.mSkipDraw) {
            return;
        }
        UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
        if (udfpsEnrollHelper == null || udfpsEnrollHelper.isCenterEnrollmentStage()) {
            RectF rectF = this.mSensorRect;
            if (rectF != null) {
                canvas.drawOval(rectF, this.mSensorOutlinePaint);
            }
            this.mFingerprintDrawable.draw(canvas);
            this.mFingerprintDrawable.setAlpha(this.mAlpha);
            this.mSensorOutlinePaint.setAlpha(this.mAlpha);
            return;
        }
        canvas.save();
        canvas.translate(this.mCurrentX, this.mCurrentY);
        RectF rectF2 = this.mSensorRect;
        if (rectF2 != null) {
            float f = this.mCurrentScale;
            canvas.scale(f, f, rectF2.centerX(), this.mSensorRect.centerY());
            canvas.drawOval(this.mSensorRect, this.mBlueFill);
        }
        this.mMovingTargetFpIcon.draw(canvas);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.mAlpha = i;
        this.mFingerprintDrawable.setAlpha(i);
        this.mSensorOutlinePaint.setAlpha(i);
        this.mBlueFill.setAlpha(i);
        this.mMovingTargetFpIcon.setAlpha(i);
        invalidateSelf();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001e, code lost:

       if (r2 < r0.getStageThresholdSteps(r0.mTotalSteps, 2)) goto L15;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateTipHintVisibility() {
        /*
            r5 = this;
            com.android.settings.biometrics.fingerprint.UdfpsEnrollHelper r0 = r5.mEnrollHelper
            if (r0 == 0) goto L21
            int r1 = r0.mTotalSteps
            r2 = -1
            if (r1 == r2) goto L21
            int r3 = r0.mRemainingSteps
            if (r3 != r2) goto Le
            goto L21
        Le:
            int r2 = r1 - r3
            r3 = 1
            int r1 = r0.getStageThresholdSteps(r1, r3)
            if (r2 < r1) goto L21
            int r1 = r0.mTotalSteps
            r4 = 2
            int r0 = r0.getStageThresholdSteps(r1, r4)
            if (r2 >= r0) goto L21
            goto L22
        L21:
            r3 = 0
        L22:
            boolean r0 = r5.mShouldShowTipHint
            if (r0 != r3) goto L27
            return
        L27:
            r5.mShouldShowTipHint = r3
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics.fingerprint.UdfpsEnrollDrawable.updateTipHintVisibility():void");
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {}
}
