package com.android.settings.biometrics2.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.util.PathParser;
import android.util.TypedValue;
import android.view.accessibility.AccessibilityManager;

import com.android.settings.R;
import com.android.settings.R$styleable;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UdfpsEnrollDrawable extends Drawable {
    public final boolean mAccessibilityEnabled;
    public int mAlpha;
    public final Paint mBlueFill;
    public final Context mContext;
    public float mCurrentX;
    public float mCurrentY;
    public final int mEnrollIcon;
    public final ShapeDrawable mFingerprintDrawable;
    public final FingerprintManager mFingerprintManager;
    public final List mGuidedEnrollmentPoints;
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
    public int mTotalSteps = -1;
    public int mRemainingSteps = -1;
    public int mLocationsEnrolled = 0;
    public int mCenterTouchCount = 0;

    /* JADX WARN: Type inference failed for: r9v8, types: [com.android.settings.biometrics2.ui.widget.UdfpsEnrollDrawable$1] */
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
                                              // com.android.settings.biometrics2.ui.widget.UdfpsEnrollDrawable.1
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
        this.mContext = context;
        this.mFingerprintManager =
                (FingerprintManager) context.getSystemService(FingerprintManager.class);
        this.mAccessibilityEnabled =
                ((AccessibilityManager) context.getSystemService(AccessibilityManager.class))
                        .isEnabled();
        ArrayList arrayList = new ArrayList();
        this.mGuidedEnrollmentPoints = arrayList;
        float applyDimension =
                TypedValue.applyDimension(5, 1.0f, context.getResources().getDisplayMetrics());
        if (Settings.Secure.getIntForUser(
                                context.getContentResolver(),
                                "com.android.systemui.biometrics.UdfpsNewCoords",
                                0,
                                -2)
                        == 0
                || !(Build.IS_ENG || Build.IS_USERDEBUG)) {
            Log.v("UdfpsAnimationEnroll", "Using old coordinates");
            arrayList.add(new PointF(2.0f * applyDimension, 0.0f * applyDimension));
            arrayList.add(new PointF(0.87f * applyDimension, (-2.7f) * applyDimension));
            float f = (-1.8f) * applyDimension;
            arrayList.add(new PointF(f, (-1.31f) * applyDimension));
            arrayList.add(new PointF(f, 1.31f * applyDimension));
            arrayList.add(new PointF(0.88f * applyDimension, 2.7f * applyDimension));
            arrayList.add(new PointF(3.94f * applyDimension, (-1.06f) * applyDimension));
            arrayList.add(new PointF(2.9f * applyDimension, (-4.14f) * applyDimension));
            arrayList.add(new PointF((-0.52f) * applyDimension, (-5.95f) * applyDimension));
            float f2 = (-3.33f) * applyDimension;
            arrayList.add(new PointF(f2, f2));
            arrayList.add(new PointF((-3.99f) * applyDimension, (-0.35f) * applyDimension));
            arrayList.add(new PointF((-3.62f) * applyDimension, 2.54f * applyDimension));
            arrayList.add(new PointF((-1.49f) * applyDimension, 5.57f * applyDimension));
            arrayList.add(new PointF(2.29f * applyDimension, 4.92f * applyDimension));
            arrayList.add(new PointF(3.82f * applyDimension, applyDimension * 1.78f));
            return;
        }
        Log.v("UdfpsAnimationEnroll", "Using new coordinates");
        float f3 = (-0.15f) * applyDimension;
        arrayList.add(new PointF(f3, (-1.02f) * applyDimension));
        arrayList.add(new PointF(f3, 1.02f * applyDimension));
        float f4 = 0.0f * applyDimension;
        arrayList.add(new PointF(0.29f * applyDimension, f4));
        float f5 = 2.17f * applyDimension;
        arrayList.add(new PointF(f5, (-2.35f) * applyDimension));
        float f6 = 1.07f * applyDimension;
        arrayList.add(new PointF(f6, (-3.96f) * applyDimension));
        float f7 = (-0.37f) * applyDimension;
        arrayList.add(new PointF(f7, (-4.31f) * applyDimension));
        float f8 = (-1.69f) * applyDimension;
        arrayList.add(new PointF(f8, (-3.29f) * applyDimension));
        float f9 = (-2.48f) * applyDimension;
        arrayList.add(new PointF(f9, (-1.23f) * applyDimension));
        arrayList.add(new PointF(f9, 1.23f * applyDimension));
        arrayList.add(new PointF(f8, 3.29f * applyDimension));
        arrayList.add(new PointF(f7, 4.31f * applyDimension));
        arrayList.add(new PointF(f6, 3.96f * applyDimension));
        arrayList.add(new PointF(f5, 2.35f * applyDimension));
        arrayList.add(new PointF(applyDimension * 2.58f, f4));
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (this.mSkipDraw) {
            return;
        }
        if (isCenterEnrollmentStage()) {
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

    public final int getStageThresholdSteps(int i, int i2) {
        return Math.round(this.mFingerprintManager.getEnrollStageThreshold(i2) * i);
    }

    public final boolean isCenterEnrollmentStage() {
        int i;
        int i2 = this.mTotalSteps;
        return i2 == -1
                || (i = this.mRemainingSteps) == -1
                || i2 - i < getStageThresholdSteps(i2, 0);
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

    public final void updateTipHintVisibility() {
        int i;
        int i2;
        int i3 = this.mTotalSteps;
        boolean z = false;
        if (i3 != -1
                && (i = this.mRemainingSteps) != -1
                && (i2 = i3 - i) >= getStageThresholdSteps(i3, 1)
                && i2 < getStageThresholdSteps(this.mTotalSteps, 2)) {
            z = true;
        }
        if (this.mShouldShowTipHint == z) {
            return;
        }
        this.mShouldShowTipHint = z;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {}
}
