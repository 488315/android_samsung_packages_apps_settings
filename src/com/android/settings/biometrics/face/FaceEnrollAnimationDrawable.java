package com.android.settings.biometrics.face;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.android.settings.biometrics.BiometricEnrollSidecar;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FaceEnrollAnimationDrawable extends Drawable
        implements BiometricEnrollSidecar.Listener {
    public final AnonymousClass1 mAnimationListener =
            new ParticleCollection
                    .Listener() { // from class:
                                  // com.android.settings.biometrics.face.FaceEnrollAnimationDrawable.1
                @Override // com.android.settings.biometrics.face.ParticleCollection.Listener
                public final void onEnrolled() {
                    FaceEnrollAnimationDrawable faceEnrollAnimationDrawable =
                            FaceEnrollAnimationDrawable.this;
                    TimeAnimator timeAnimator = faceEnrollAnimationDrawable.mTimeAnimator;
                    if (timeAnimator == null || !timeAnimator.isStarted()) {
                        return;
                    }
                    faceEnrollAnimationDrawable.mTimeAnimator.end();
                    faceEnrollAnimationDrawable.mListener.onEnrolled();
                }
            };
    public Rect mBounds;
    public final Paint mCircleCutoutPaint;
    public final Context mContext;
    public final ParticleCollection.Listener mListener;
    public ParticleCollection mParticleCollection;
    public final Paint mSquarePaint;
    public TimeAnimator mTimeAnimator;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.biometrics.face.FaceEnrollAnimationDrawable$1] */
    public FaceEnrollAnimationDrawable(
            Context context, FaceEnrollPreviewFragment.AnonymousClass1 anonymousClass1) {
        this.mContext = context;
        this.mListener = anonymousClass1;
        Paint paint = new Paint();
        this.mSquarePaint = paint;
        paint.setColor(-1);
        paint.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.mCircleCutoutPaint = paint2;
        paint2.setColor(0);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint2.setAntiAlias(true);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (this.mBounds == null) {
            return;
        }
        canvas.save();
        canvas.drawRect(0.0f, 0.0f, this.mBounds.width(), this.mBounds.height(), this.mSquarePaint);
        canvas.drawCircle(
                this.mBounds.exactCenterX(),
                this.mBounds.exactCenterY(),
                (this.mBounds.height() / 2) - 20,
                this.mCircleCutoutPaint);
        ParticleCollection particleCollection = this.mParticleCollection;
        for (int i = 0; i < ((ArrayList) particleCollection.mParticleList).size(); i++) {
            AnimationParticle animationParticle =
                    (AnimationParticle) ((ArrayList) particleCollection.mParticleList).get(i);
            int i2 = animationParticle.mAnimationState;
            int i3 = animationParticle.mBorderWidth;
            if (i2 != 4) {
                float exactCenterX = r2.right - animationParticle.mBounds.exactCenterX();
                float f = i3;
                canvas.drawCircle(
                        ((exactCenterX - f) * ((float) Math.cos(animationParticle.mCurrentAngle)))
                                + animationParticle.mBounds.exactCenterX(),
                        (((r4.bottom - animationParticle.mBounds.exactCenterY()) - f)
                                        * ((float) Math.sin(animationParticle.mCurrentAngle)))
                                + animationParticle.mBounds.exactCenterY(),
                        animationParticle.mCurrentSize,
                        animationParticle.mPaint);
            } else {
                float f2 = i3;
                RectF rectF =
                        new RectF(
                                f2,
                                f2,
                                animationParticle.mBounds.width() - i3,
                                animationParticle.mBounds.height() - i3);
                Path path = new Path();
                path.arcTo(
                        rectF,
                        (float) Math.toDegrees(animationParticle.mCurrentAngle),
                        animationParticle.mSweepAngle);
                canvas.drawPath(path, animationParticle.mPaint);
            }
        }
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        this.mBounds = rect;
        this.mParticleCollection =
                new ParticleCollection(this.mContext, this.mAnimationListener, rect);
        if (this.mTimeAnimator == null) {
            TimeAnimator timeAnimator = new TimeAnimator();
            this.mTimeAnimator = timeAnimator;
            timeAnimator.setTimeListener(
                    new TimeAnimator
                            .TimeListener() { // from class:
                                              // com.android.settings.biometrics.face.FaceEnrollAnimationDrawable$$ExternalSyntheticLambda0
                        @Override // android.animation.TimeAnimator.TimeListener
                        public final void onTimeUpdate(
                                TimeAnimator timeAnimator2, long j, long j2) {
                            FaceEnrollAnimationDrawable faceEnrollAnimationDrawable =
                                    FaceEnrollAnimationDrawable.this;
                            ParticleCollection particleCollection =
                                    faceEnrollAnimationDrawable.mParticleCollection;
                            for (int i = 0;
                                    i < ((ArrayList) particleCollection.mParticleList).size();
                                    i++) {
                                AnimationParticle animationParticle =
                                        (AnimationParticle)
                                                ((ArrayList) particleCollection.mParticleList)
                                                        .get(i);
                                int i2 = animationParticle.mAnimationState;
                                if (i2 != 4) {
                                    float f = j2 * 0.001f;
                                    float f2 = j * 0.001f;
                                    float f3 = animationParticle.mRotationSpeed;
                                    float f4 = f3 / 0.8f;
                                    if ((i2 == 2 || i2 == 3) && f3 > 0.0f) {
                                        animationParticle.mRotationSpeed =
                                                Math.max(f3 - (2.0f * f), 0.0f);
                                    } else if (i2 == 1 && f3 < 0.8f) {
                                        animationParticle.mRotationSpeed = (2.0f * f) + f3;
                                    }
                                    animationParticle.mCurrentAngle =
                                            (f * animationParticle.mRotationSpeed)
                                                    + animationParticle.mCurrentAngle;
                                    animationParticle.mCurrentSize =
                                            ((((((float)
                                                                                            Math
                                                                                                    .sin(
                                                                                                            (f2
                                                                                                                            * 6.2831855f)
                                                                                                                    + animationParticle
                                                                                                                            .mOffsetTimeSec))
                                                                                    * 5.0f)
                                                                            + 15.0f)
                                                                    - 10.0f)
                                                            * f4)
                                                    + 10.0f;
                                    int i3 = animationParticle.mAnimationState;
                                    int i4 = animationParticle.mErrorColor;
                                    int i5 = animationParticle.mAssignedColor;
                                    if (i3 == 3) {
                                        i5 =
                                                ((Integer)
                                                                animationParticle.mEvaluator
                                                                        .evaluate(
                                                                                1.0f - f4,
                                                                                Integer.valueOf(i5),
                                                                                Integer.valueOf(
                                                                                        i4)))
                                                        .intValue();
                                    } else if (animationParticle.mLastAnimationState == 3) {
                                        i5 =
                                                ((Integer)
                                                                animationParticle.mEvaluator
                                                                        .evaluate(
                                                                                1.0f - f4,
                                                                                Integer.valueOf(i5),
                                                                                Integer.valueOf(
                                                                                        i4)))
                                                        .intValue();
                                    }
                                    animationParticle.mPaint.setColor(i5);
                                    animationParticle.mPaint.setStrokeWidth(
                                            animationParticle.mCurrentSize);
                                } else {
                                    float f5 = j2 * 0.001f;
                                    float f6 = j * 0.001f;
                                    if (animationParticle.mRingAdjustRate == 0.0f) {
                                        animationParticle.mRingAdjustRate =
                                                (15.0f - animationParticle.mCurrentSize) / 0.1f;
                                        if (animationParticle.mRingCompletionTime == 0.0f) {
                                            animationParticle.mRingCompletionTime = f6 + 0.1f;
                                        }
                                    }
                                    float f7 = animationParticle.mRotationSpeed;
                                    if (f7 < 0.8f) {
                                        animationParticle.mRotationSpeed = (2.0f * f5) + f7;
                                    }
                                    animationParticle.mCurrentAngle =
                                            (animationParticle.mRotationSpeed * f5)
                                                    + animationParticle.mCurrentAngle;
                                    float f8 = animationParticle.mSweepAngle;
                                    if (f8 < 360.0f) {
                                        float f9 = animationParticle.mSweepRate;
                                        float f10 = f9 * f5;
                                        animationParticle.mSweepAngle = f8 + f10;
                                        animationParticle.mSweepRate = f9 + f10;
                                    }
                                    if (animationParticle.mSweepAngle > 360.0f) {
                                        animationParticle.mSweepAngle = 360.0f;
                                        ParticleCollection particleCollection2 =
                                                ParticleCollection.this;
                                        boolean isEmpty =
                                                ((ArrayList)
                                                                particleCollection2
                                                                        .mPrimariesInProgress)
                                                        .isEmpty();
                                        int i6 = 0;
                                        while (true) {
                                            if (i6
                                                    >= ((ArrayList)
                                                                    particleCollection2
                                                                            .mPrimariesInProgress)
                                                            .size()) {
                                                break;
                                            }
                                            if (((Integer)
                                                                    ((ArrayList)
                                                                                    particleCollection2
                                                                                            .mPrimariesInProgress)
                                                                            .get(i6))
                                                            .intValue()
                                                    == animationParticle.mIndex) {
                                                ((ArrayList)
                                                                particleCollection2
                                                                        .mPrimariesInProgress)
                                                        .remove(i6);
                                                break;
                                            }
                                            i6++;
                                        }
                                        if (((ArrayList) particleCollection2.mPrimariesInProgress)
                                                        .isEmpty()
                                                && !isEmpty) {
                                            particleCollection2.mListener.onEnrolled();
                                        }
                                    }
                                    if (f6 < 0.1f) {
                                        float f11 =
                                                (animationParticle.mRingAdjustRate * f5)
                                                        + animationParticle.mCurrentSize;
                                        animationParticle.mCurrentSize = f11;
                                        animationParticle.mPaint.setStrokeWidth(f11);
                                    } else {
                                        animationParticle.mCurrentSize = 15.0f;
                                        animationParticle.mPaint.setStrokeWidth(15.0f);
                                    }
                                }
                            }
                            faceEnrollAnimationDrawable.invalidateSelf();
                        }
                    });
            this.mTimeAnimator.start();
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentError(int i, CharSequence charSequence) {
        this.mParticleCollection.getClass();
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentHelp(int i, CharSequence charSequence) {
        this.mParticleCollection.getClass();
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentProgressChange(int i, int i2) {
        this.mParticleCollection.onEnrollmentProgressChange(i, i2);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {}

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {}
}
