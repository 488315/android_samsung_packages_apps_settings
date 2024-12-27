package com.android.settings.biometrics.fingerprint;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.android.settings.R;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintLocationAnimationView extends View
        implements FingerprintFindSensorAnimation {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ValueAnimator mAlphaAnimator;
    public final Paint mDotPaint;
    public final int mDotRadius;
    public final Interpolator mFastOutSlowInInterpolator;
    public final float mFractionCenterX;
    public final float mFractionCenterY;
    public final Interpolator mLinearOutSlowInInterpolator;
    public final int mMaxPulseRadius;
    public final Paint mPulsePaint;
    public float mPulseRadius;
    public ValueAnimator mRadiusAnimator;
    public final AnonymousClass5 mStartPhaseRunnable;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.settings.biometrics.fingerprint.FingerprintLocationAnimationView$5] */
    public FingerprintLocationAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mDotPaint = paint;
        Paint paint2 = new Paint();
        this.mPulsePaint = paint2;
        this.mStartPhaseRunnable =
                new Runnable() { // from class:
                                 // com.android.settings.biometrics.fingerprint.FingerprintLocationAnimationView.5
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintLocationAnimationView fingerprintLocationAnimationView =
                                FingerprintLocationAnimationView.this;
                        int i = FingerprintLocationAnimationView.$r8$clinit;
                        fingerprintLocationAnimationView.startPhase();
                    }
                };
        this.mDotRadius = getResources().getDimensionPixelSize(R.dimen.fingerprint_dot_radius);
        this.mMaxPulseRadius =
                getResources().getDimensionPixelSize(R.dimen.fingerprint_pulse_radius);
        this.mFractionCenterX =
                getResources().getFraction(R.fraction.fingerprint_sensor_location_fraction_x, 1, 1);
        this.mFractionCenterY =
                getResources().getFraction(R.fraction.fingerprint_sensor_location_fraction_y, 1, 1);
        int colorAttrDefaultColor =
                Utils.getColorAttrDefaultColor(context, android.R.attr.colorAccent);
        paint.setAntiAlias(true);
        paint2.setAntiAlias(true);
        paint.setColor(colorAttrDefaultColor);
        paint2.setColor(colorAttrDefaultColor);
        this.mLinearOutSlowInInterpolator =
                AnimationUtils.loadInterpolator(context, android.R.interpolator.linear_out_slow_in);
        this.mFastOutSlowInInterpolator =
                AnimationUtils.loadInterpolator(context, android.R.interpolator.linear_out_slow_in);
    }

    private float getCenterX() {
        return getWidth() * this.mFractionCenterX;
    }

    private float getCenterY() {
        return getHeight() * this.mFractionCenterY;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        canvas.drawCircle(getCenterX(), getCenterY(), this.mPulseRadius, this.mPulsePaint);
        canvas.drawCircle(getCenterX(), getCenterY(), this.mDotRadius, this.mDotPaint);
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFindSensorAnimation
    public final void pauseAnimation() {
        stopAnimation();
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFindSensorAnimation
    public final void startAnimation() {
        startPhase();
    }

    public final void startPhase() {
        final int i = 1;
        final int i2 = 0;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, this.mMaxPulseRadius);
        ofFloat.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener(
                        this) { // from class:
                                // com.android.settings.biometrics.fingerprint.FingerprintLocationAnimationView.1
                    public final /* synthetic */ FingerprintLocationAnimationView this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        switch (i2) {
                            case 0:
                                this.this$0.mPulseRadius =
                                        ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                this.this$0.invalidate();
                                break;
                            default:
                                this.this$0.mPulsePaint.setAlpha(
                                        (int)
                                                (((Float) valueAnimator.getAnimatedValue())
                                                                .floatValue()
                                                        * 255.0f));
                                this.this$0.invalidate();
                                break;
                        }
                    }
                });
        ofFloat.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.android.settings.biometrics.fingerprint.FingerprintLocationAnimationView.2
                    public boolean mCancelled;

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        this.mCancelled = true;
                    }

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        FingerprintLocationAnimationView fingerprintLocationAnimationView =
                                FingerprintLocationAnimationView.this;
                        fingerprintLocationAnimationView.mRadiusAnimator = null;
                        if (this.mCancelled) {
                            return;
                        }
                        fingerprintLocationAnimationView.postDelayed(
                                fingerprintLocationAnimationView.mStartPhaseRunnable, 1000L);
                    }
                });
        ofFloat.setDuration(1000L);
        ofFloat.setInterpolator(this.mLinearOutSlowInInterpolator);
        ofFloat.start();
        this.mRadiusAnimator = ofFloat;
        this.mPulsePaint.setAlpha(38);
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.15f, 0.0f);
        ofFloat2.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener(
                        this) { // from class:
                                // com.android.settings.biometrics.fingerprint.FingerprintLocationAnimationView.1
                    public final /* synthetic */ FingerprintLocationAnimationView this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        switch (i) {
                            case 0:
                                this.this$0.mPulseRadius =
                                        ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                this.this$0.invalidate();
                                break;
                            default:
                                this.this$0.mPulsePaint.setAlpha(
                                        (int)
                                                (((Float) valueAnimator.getAnimatedValue())
                                                                .floatValue()
                                                        * 255.0f));
                                this.this$0.invalidate();
                                break;
                        }
                    }
                });
        ofFloat2.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.android.settings.biometrics.fingerprint.FingerprintLocationAnimationView.4
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        FingerprintLocationAnimationView.this.mAlphaAnimator = null;
                    }
                });
        ofFloat2.setDuration(750L);
        ofFloat2.setInterpolator(this.mFastOutSlowInInterpolator);
        ofFloat2.setStartDelay(250L);
        ofFloat2.start();
        this.mAlphaAnimator = ofFloat2;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFindSensorAnimation
    public final void stopAnimation() {
        removeCallbacks(this.mStartPhaseRunnable);
        ValueAnimator valueAnimator = this.mRadiusAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimator2 = this.mAlphaAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
    }
}
