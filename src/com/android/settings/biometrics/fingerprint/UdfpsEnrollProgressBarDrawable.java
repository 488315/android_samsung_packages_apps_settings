package com.android.settings.biometrics.fingerprint;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UdfpsEnrollProgressBarDrawable extends Drawable {
    public boolean mAfterFirstTouch;
    public ValueAnimator mBackgroundColorAnimator;
    public final UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1
            mBackgroundColorUpdateListener;
    public final Paint mBackgroundPaint;
    public ValueAnimator mCheckmarkAnimator;
    public final Drawable mCheckmarkDrawable;
    public final Interpolator mCheckmarkInterpolator;
    public final UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1 mCheckmarkUpdateListener;
    public final Context mContext;
    public final int mEnrollProgress;
    public final int mEnrollProgressHelp;
    public final int mEnrollProgressHelpWithTalkback;
    public ValueAnimator mFillColorAnimator;
    public final UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1 mFillColorUpdateListener;

    @VisibleForTesting final Paint mFillPaint;
    public final int mHelpColor;
    public final boolean mIsAccessibilityEnabled;
    public final int mMovingTargetFill;
    public final int mMovingTargetFillError;
    public final int mOnFirstBucketFailedColor;
    public ValueAnimator mProgressAnimator;
    public final int mProgressColor;
    public final UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1 mProgressUpdateListener;
    public final float mStrokeWidthPx;
    public final Vibrator mVibrator;
    public static final Interpolator DEACCEL = new DecelerateInterpolator();
    public static final VibrationEffect VIBRATE_EFFECT_ERROR =
            VibrationEffect.createWaveform(new long[] {0, 5, 55, 60}, -1);
    public static final VibrationAttributes FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES =
            VibrationAttributes.createForUsage(66);
    public static final VibrationAttributes HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES =
            VibrationAttributes.createForUsage(50);
    public static final VibrationEffect SUCCESS_VIBRATION_EFFECT = VibrationEffect.get(0);
    public int mRemainingSteps = 0;
    public int mTotalSteps = 0;
    public float mProgress = 0.0f;
    public boolean mComplete = false;
    public float mCheckmarkScale = 0.0f;

    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.settings.biometrics.fingerprint.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.settings.biometrics.fingerprint.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r6v5, types: [com.android.settings.biometrics.fingerprint.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r6v6, types: [com.android.settings.biometrics.fingerprint.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1] */
    public UdfpsEnrollProgressBarDrawable(Context context, AttributeSet attributeSet) {
        this.mContext = context;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet,
                        R$styleable.BiometricsEnrollView,
                        R.attr.biometricsEnrollStyle,
                        R.style.BiometricsEnrollStyle);
        this.mMovingTargetFill = obtainStyledAttributes.getColor(5, 0);
        this.mMovingTargetFillError = obtainStyledAttributes.getColor(6, 0);
        this.mEnrollProgress = obtainStyledAttributes.getColor(1, 0);
        this.mEnrollProgressHelp = obtainStyledAttributes.getColor(2, 0);
        this.mEnrollProgressHelpWithTalkback = obtainStyledAttributes.getColor(3, 0);
        obtainStyledAttributes.recycle();
        float f = (context.getResources().getDisplayMetrics().densityDpi / 160.0f) * 12.0f;
        this.mStrokeWidthPx = f;
        int i = this.mEnrollProgress;
        this.mProgressColor = i;
        boolean isTouchExplorationEnabled =
                ((AccessibilityManager) context.getSystemService(AccessibilityManager.class))
                        .isTouchExplorationEnabled();
        this.mIsAccessibilityEnabled = isTouchExplorationEnabled;
        this.mOnFirstBucketFailedColor = this.mMovingTargetFillError;
        if (isTouchExplorationEnabled) {
            this.mHelpColor = this.mEnrollProgressHelpWithTalkback;
        } else {
            this.mHelpColor = this.mEnrollProgressHelp;
        }
        Drawable drawable = context.getDrawable(R.drawable.udfps_enroll_checkmark);
        this.mCheckmarkDrawable = drawable;
        drawable.mutate();
        this.mCheckmarkInterpolator = new OvershootInterpolator();
        Paint paint = new Paint();
        this.mBackgroundPaint = paint;
        paint.setStrokeWidth(f);
        paint.setColor(this.mMovingTargetFill);
        paint.setAntiAlias(true);
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        Paint.Cap cap = Paint.Cap.ROUND;
        paint.setStrokeCap(cap);
        Paint paint2 = new Paint();
        this.mFillPaint = paint2;
        paint2.setStrokeWidth(f);
        paint2.setColor(i);
        paint2.setAntiAlias(true);
        paint2.setStyle(style);
        paint2.setStrokeCap(cap);
        this.mVibrator = (Vibrator) context.getSystemService(Vibrator.class);
        final int i2 = 1;
        this.mProgressUpdateListener =
                new ValueAnimator.AnimatorUpdateListener(
                        this) { // from class:
                                // com.android.settings.biometrics.fingerprint.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1
                    public final /* synthetic */ UdfpsEnrollProgressBarDrawable f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int i3 = i2;
                        UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable = this.f$0;
                        switch (i3) {
                            case 0:
                                udfpsEnrollProgressBarDrawable.mFillPaint.setColor(
                                        ((Integer) valueAnimator.getAnimatedValue()).intValue());
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                            case 1:
                                Interpolator interpolator = UdfpsEnrollProgressBarDrawable.DEACCEL;
                                udfpsEnrollProgressBarDrawable.getClass();
                                udfpsEnrollProgressBarDrawable.mProgress =
                                        ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                            case 2:
                                Interpolator interpolator2 = UdfpsEnrollProgressBarDrawable.DEACCEL;
                                udfpsEnrollProgressBarDrawable.getClass();
                                udfpsEnrollProgressBarDrawable.mCheckmarkScale =
                                        ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                            default:
                                udfpsEnrollProgressBarDrawable.mBackgroundPaint.setColor(
                                        ((Integer) valueAnimator.getAnimatedValue()).intValue());
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                        }
                    }
                };
        final int i3 = 0;
        this.mFillColorUpdateListener =
                new ValueAnimator.AnimatorUpdateListener(
                        this) { // from class:
                                // com.android.settings.biometrics.fingerprint.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1
                    public final /* synthetic */ UdfpsEnrollProgressBarDrawable f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int i32 = i3;
                        UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable = this.f$0;
                        switch (i32) {
                            case 0:
                                udfpsEnrollProgressBarDrawable.mFillPaint.setColor(
                                        ((Integer) valueAnimator.getAnimatedValue()).intValue());
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                            case 1:
                                Interpolator interpolator = UdfpsEnrollProgressBarDrawable.DEACCEL;
                                udfpsEnrollProgressBarDrawable.getClass();
                                udfpsEnrollProgressBarDrawable.mProgress =
                                        ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                            case 2:
                                Interpolator interpolator2 = UdfpsEnrollProgressBarDrawable.DEACCEL;
                                udfpsEnrollProgressBarDrawable.getClass();
                                udfpsEnrollProgressBarDrawable.mCheckmarkScale =
                                        ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                            default:
                                udfpsEnrollProgressBarDrawable.mBackgroundPaint.setColor(
                                        ((Integer) valueAnimator.getAnimatedValue()).intValue());
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                        }
                    }
                };
        final int i4 = 2;
        this.mCheckmarkUpdateListener =
                new ValueAnimator.AnimatorUpdateListener(
                        this) { // from class:
                                // com.android.settings.biometrics.fingerprint.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1
                    public final /* synthetic */ UdfpsEnrollProgressBarDrawable f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int i32 = i4;
                        UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable = this.f$0;
                        switch (i32) {
                            case 0:
                                udfpsEnrollProgressBarDrawable.mFillPaint.setColor(
                                        ((Integer) valueAnimator.getAnimatedValue()).intValue());
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                            case 1:
                                Interpolator interpolator = UdfpsEnrollProgressBarDrawable.DEACCEL;
                                udfpsEnrollProgressBarDrawable.getClass();
                                udfpsEnrollProgressBarDrawable.mProgress =
                                        ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                            case 2:
                                Interpolator interpolator2 = UdfpsEnrollProgressBarDrawable.DEACCEL;
                                udfpsEnrollProgressBarDrawable.getClass();
                                udfpsEnrollProgressBarDrawable.mCheckmarkScale =
                                        ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                            default:
                                udfpsEnrollProgressBarDrawable.mBackgroundPaint.setColor(
                                        ((Integer) valueAnimator.getAnimatedValue()).intValue());
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                        }
                    }
                };
        final int i5 = 3;
        this.mBackgroundColorUpdateListener =
                new ValueAnimator.AnimatorUpdateListener(
                        this) { // from class:
                                // com.android.settings.biometrics.fingerprint.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1
                    public final /* synthetic */ UdfpsEnrollProgressBarDrawable f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int i32 = i5;
                        UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable = this.f$0;
                        switch (i32) {
                            case 0:
                                udfpsEnrollProgressBarDrawable.mFillPaint.setColor(
                                        ((Integer) valueAnimator.getAnimatedValue()).intValue());
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                            case 1:
                                Interpolator interpolator = UdfpsEnrollProgressBarDrawable.DEACCEL;
                                udfpsEnrollProgressBarDrawable.getClass();
                                udfpsEnrollProgressBarDrawable.mProgress =
                                        ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                            case 2:
                                Interpolator interpolator2 = UdfpsEnrollProgressBarDrawable.DEACCEL;
                                udfpsEnrollProgressBarDrawable.getClass();
                                udfpsEnrollProgressBarDrawable.mCheckmarkScale =
                                        ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                            default:
                                udfpsEnrollProgressBarDrawable.mBackgroundPaint.setColor(
                                        ((Integer) valueAnimator.getAnimatedValue()).intValue());
                                udfpsEnrollProgressBarDrawable.invalidateSelf();
                                break;
                        }
                    }
                };
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(-90.0f, getBounds().centerX(), getBounds().centerY());
        float f = this.mStrokeWidthPx / 2.0f;
        if (this.mProgress < 1.0f) {
            canvas.drawArc(
                    f,
                    f,
                    getBounds().right - f,
                    getBounds().bottom - f,
                    0.0f,
                    360.0f,
                    false,
                    this.mBackgroundPaint);
        }
        if (this.mProgress > 0.0f) {
            canvas.drawArc(
                    f,
                    f,
                    getBounds().right - f,
                    getBounds().bottom - f,
                    0.0f,
                    this.mProgress * 360.0f,
                    false,
                    this.mFillPaint);
        }
        canvas.restore();
        if (this.mCheckmarkScale > 0.0f) {
            float sqrt = ((float) Math.sqrt(2.0d)) / 2.0f;
            float width = ((getBounds().width() - this.mStrokeWidthPx) / 2.0f) * sqrt;
            float height = ((getBounds().height() - this.mStrokeWidthPx) / 2.0f) * sqrt;
            float centerX = getBounds().centerX() + width;
            float centerY = getBounds().centerY() + height;
            float intrinsicWidth =
                    (this.mCheckmarkDrawable.getIntrinsicWidth() / 2.0f) * this.mCheckmarkScale;
            float intrinsicHeight =
                    (this.mCheckmarkDrawable.getIntrinsicHeight() / 2.0f) * this.mCheckmarkScale;
            this.mCheckmarkDrawable.setBounds(
                    Math.round(centerX - intrinsicWidth),
                    Math.round(centerY - intrinsicHeight),
                    Math.round(centerX + intrinsicWidth),
                    Math.round(centerY + intrinsicHeight));
            this.mCheckmarkDrawable.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 0;
    }

    public final void updateState(int i, int i2, boolean z) {
        if (this.mRemainingSteps != i || this.mTotalSteps != i2) {
            if (z) {
                Vibrator vibrator = this.mVibrator;
                if (vibrator != null && this.mIsAccessibilityEnabled) {
                    vibrator.vibrate(
                            Process.myUid(),
                            this.mContext.getOpPackageName(),
                            VIBRATE_EFFECT_ERROR,
                            getClass().getSimpleName().concat("::onEnrollmentHelp"),
                            FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES);
                }
            } else {
                Vibrator vibrator2 = this.mVibrator;
                if (vibrator2 != null) {
                    if (i == -1 && this.mIsAccessibilityEnabled) {
                        vibrator2.vibrate(
                                Process.myUid(),
                                this.mContext.getOpPackageName(),
                                VIBRATE_EFFECT_ERROR,
                                getClass().getSimpleName().concat("::onFirstTouchError"),
                                FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES);
                    } else if (i != -1 && !this.mIsAccessibilityEnabled) {
                        vibrator2.vibrate(
                                Process.myUid(),
                                this.mContext.getOpPackageName(),
                                SUCCESS_VIBRATION_EFFECT,
                                getClass().getSimpleName().concat("::OnEnrollmentProgress"),
                                HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES);
                    }
                }
            }
            this.mRemainingSteps = i;
            this.mTotalSteps = i2;
            int max = Math.max(0, i2 - i);
            boolean z2 = this.mAfterFirstTouch;
            if (z2) {
                max++;
            }
            float min = Math.min(1.0f, max / (z2 ? this.mTotalSteps + 1 : this.mTotalSteps));
            ValueAnimator valueAnimator = this.mProgressAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mProgressAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mProgress, min);
            this.mProgressAnimator = ofFloat;
            ofFloat.setDuration(400L);
            this.mProgressAnimator.addUpdateListener(this.mProgressUpdateListener);
            this.mProgressAnimator.start();
            if (i == 0) {
                if (!this.mComplete) {
                    this.mComplete = true;
                    ValueAnimator valueAnimator2 = this.mCheckmarkAnimator;
                    if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                        this.mCheckmarkAnimator.cancel();
                    }
                    ValueAnimator ofFloat2 = ValueAnimator.ofFloat(this.mCheckmarkScale, 1.0f);
                    this.mCheckmarkAnimator = ofFloat2;
                    ofFloat2.setStartDelay(200L);
                    this.mCheckmarkAnimator.setDuration(300L);
                    this.mCheckmarkAnimator.setInterpolator(this.mCheckmarkInterpolator);
                    this.mCheckmarkAnimator.addUpdateListener(this.mCheckmarkUpdateListener);
                    this.mCheckmarkAnimator.start();
                }
            } else if (i > 0 && this.mComplete) {
                this.mComplete = false;
                ValueAnimator valueAnimator3 = this.mCheckmarkAnimator;
                long round =
                        Math.round(
                                (valueAnimator3 != null
                                                ? valueAnimator3.getAnimatedFraction()
                                                : 0.0f)
                                        * 200.0f);
                ValueAnimator valueAnimator4 = this.mCheckmarkAnimator;
                if (valueAnimator4 != null && valueAnimator4.isRunning()) {
                    this.mCheckmarkAnimator.cancel();
                }
                ValueAnimator ofFloat3 = ValueAnimator.ofFloat(this.mCheckmarkScale, 0.0f);
                this.mCheckmarkAnimator = ofFloat3;
                ofFloat3.setDuration(round);
                this.mCheckmarkAnimator.addUpdateListener(this.mCheckmarkUpdateListener);
                this.mCheckmarkAnimator.start();
            }
        }
        if (this.mAfterFirstTouch || !z) {
            ValueAnimator valueAnimator5 = this.mFillColorAnimator;
            if (valueAnimator5 != null && valueAnimator5.isRunning()) {
                this.mFillColorAnimator.end();
            }
            ValueAnimator ofArgb =
                    ValueAnimator.ofArgb(
                            this.mFillPaint.getColor(), z ? this.mHelpColor : this.mProgressColor);
            this.mFillColorAnimator = ofArgb;
            ofArgb.setDuration(350L);
            this.mFillColorAnimator.setRepeatCount(1);
            this.mFillColorAnimator.setRepeatMode(2);
            this.mFillColorAnimator.setInterpolator(DEACCEL);
            this.mFillColorAnimator.addUpdateListener(this.mFillColorUpdateListener);
            this.mFillColorAnimator.start();
            return;
        }
        ValueAnimator valueAnimator6 = this.mBackgroundColorAnimator;
        if (valueAnimator6 != null && valueAnimator6.isRunning()) {
            this.mBackgroundColorAnimator.end();
        }
        ValueAnimator ofArgb2 =
                ValueAnimator.ofArgb(
                        this.mBackgroundPaint.getColor(), this.mOnFirstBucketFailedColor);
        this.mBackgroundColorAnimator = ofArgb2;
        ofArgb2.setDuration(350L);
        this.mBackgroundColorAnimator.setRepeatCount(1);
        this.mBackgroundColorAnimator.setRepeatMode(2);
        this.mBackgroundColorAnimator.setInterpolator(DEACCEL);
        this.mBackgroundColorAnimator.addUpdateListener(this.mBackgroundColorUpdateListener);
        this.mBackgroundColorAnimator.start();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {}

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {}
}
