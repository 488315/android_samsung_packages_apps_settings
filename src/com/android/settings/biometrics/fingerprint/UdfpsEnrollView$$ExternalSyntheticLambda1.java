package com.android.settings.biometrics.fingerprint;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Build;
import android.provider.Settings;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class UdfpsEnrollView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UdfpsEnrollView f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ UdfpsEnrollView$$ExternalSyntheticLambda1(
            UdfpsEnrollView udfpsEnrollView, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = udfpsEnrollView;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        int i2;
        PointF pointF;
        int i3;
        int i4;
        int i5;
        boolean z = false;
        z = false;
        z = false;
        z = false;
        final int i6 = 2;
        final int i7 = 1;
        switch (this.$r8$classId) {
            case 0:
                UdfpsEnrollView udfpsEnrollView = this.f$0;
                int i8 = this.f$1;
                int i9 = this.f$2;
                UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable =
                        udfpsEnrollView.mFingerprintProgressDrawable;
                udfpsEnrollProgressBarDrawable.mAfterFirstTouch = true;
                udfpsEnrollProgressBarDrawable.updateState(i8, i9, false);
                final UdfpsEnrollDrawable udfpsEnrollDrawable =
                        udfpsEnrollView.mFingerprintDrawable;
                UdfpsEnrollHelper udfpsEnrollHelper = udfpsEnrollDrawable.mEnrollHelper;
                if (udfpsEnrollHelper != null) {
                    if (udfpsEnrollHelper.isCenterEnrollmentStage()) {
                        udfpsEnrollDrawable.updateTipHintVisibility();
                    } else {
                        AnimatorSet animatorSet = udfpsEnrollDrawable.mTargetAnimatorSet;
                        if (animatorSet != null && animatorSet.isRunning()) {
                            udfpsEnrollDrawable.mTargetAnimatorSet.end();
                        }
                        UdfpsEnrollHelper udfpsEnrollHelper2 = udfpsEnrollDrawable.mEnrollHelper;
                        boolean z2 = udfpsEnrollHelper2.mAccessibilityEnabled;
                        if (z2
                                || z2
                                || (i3 = udfpsEnrollHelper2.mTotalSteps) == -1
                                || (i4 = udfpsEnrollHelper2.mRemainingSteps) == -1
                                || (i5 = i3 - i4) < udfpsEnrollHelper2.getStageThresholdSteps(i3, 0)
                                || i5
                                        >= udfpsEnrollHelper2.getStageThresholdSteps(
                                                udfpsEnrollHelper2.mTotalSteps, 1)) {
                            pointF = new PointF(0.0f, 0.0f);
                        } else {
                            float floatForUser =
                                    (Build.IS_ENG || Build.IS_USERDEBUG)
                                            ? Settings.Secure.getFloatForUser(
                                                    udfpsEnrollHelper2.mContext
                                                            .getContentResolver(),
                                                    "com.android.systemui.biometrics.UdfpsEnrollHelper.scale",
                                                    0.5f,
                                                    -2)
                                            : 0.5f;
                            int i10 =
                                    udfpsEnrollHelper2.mLocationsEnrolled
                                            - udfpsEnrollHelper2.mCenterTouchCount;
                            ArrayList arrayList =
                                    (ArrayList) udfpsEnrollHelper2.mGuidedEnrollmentPoints;
                            PointF pointF2 = (PointF) arrayList.get(i10 % arrayList.size());
                            pointF = new PointF(pointF2.x * floatForUser, pointF2.y * floatForUser);
                        }
                        float f = udfpsEnrollDrawable.mCurrentX;
                        float f2 = pointF.x;
                        if (f == f2 && udfpsEnrollDrawable.mCurrentY == pointF.y) {
                            udfpsEnrollDrawable.updateTipHintVisibility();
                        } else {
                            ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
                            final int i11 = z ? 1 : 0;
                            ofFloat.addUpdateListener(
                                    new ValueAnimator
                                            .AnimatorUpdateListener() { // from class:
                                                                        // com.android.settings.biometrics.fingerprint.UdfpsEnrollDrawable$$ExternalSyntheticLambda0
                                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                        public final void onAnimationUpdate(
                                                ValueAnimator valueAnimator) {
                                            int i12 = i11;
                                            UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                                    udfpsEnrollDrawable;
                                            udfpsEnrollDrawable2.getClass();
                                            switch (i12) {
                                                case 0:
                                                    udfpsEnrollDrawable2.mCurrentX =
                                                            ((Float)
                                                                            valueAnimator
                                                                                    .getAnimatedValue())
                                                                    .floatValue();
                                                    udfpsEnrollDrawable2.invalidateSelf();
                                                    break;
                                                case 1:
                                                    udfpsEnrollDrawable2.mCurrentY =
                                                            ((Float)
                                                                            valueAnimator
                                                                                    .getAnimatedValue())
                                                                    .floatValue();
                                                    udfpsEnrollDrawable2.invalidateSelf();
                                                    break;
                                                default:
                                                    udfpsEnrollDrawable2.mCurrentScale =
                                                            (((float)
                                                                                    Math.sin(
                                                                                            ((Float)
                                                                                                            valueAnimator
                                                                                                                    .getAnimatedValue())
                                                                                                    .floatValue()))
                                                                            * 0.25f)
                                                                    + 1.0f;
                                                    udfpsEnrollDrawable2.invalidateSelf();
                                                    break;
                                            }
                                        }
                                    });
                            ValueAnimator ofFloat2 =
                                    ValueAnimator.ofFloat(udfpsEnrollDrawable.mCurrentY, pointF.y);
                            ofFloat2.addUpdateListener(
                                    new ValueAnimator
                                            .AnimatorUpdateListener() { // from class:
                                                                        // com.android.settings.biometrics.fingerprint.UdfpsEnrollDrawable$$ExternalSyntheticLambda0
                                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                        public final void onAnimationUpdate(
                                                ValueAnimator valueAnimator) {
                                            int i12 = i7;
                                            UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                                    udfpsEnrollDrawable;
                                            udfpsEnrollDrawable2.getClass();
                                            switch (i12) {
                                                case 0:
                                                    udfpsEnrollDrawable2.mCurrentX =
                                                            ((Float)
                                                                            valueAnimator
                                                                                    .getAnimatedValue())
                                                                    .floatValue();
                                                    udfpsEnrollDrawable2.invalidateSelf();
                                                    break;
                                                case 1:
                                                    udfpsEnrollDrawable2.mCurrentY =
                                                            ((Float)
                                                                            valueAnimator
                                                                                    .getAnimatedValue())
                                                                    .floatValue();
                                                    udfpsEnrollDrawable2.invalidateSelf();
                                                    break;
                                                default:
                                                    udfpsEnrollDrawable2.mCurrentScale =
                                                            (((float)
                                                                                    Math.sin(
                                                                                            ((Float)
                                                                                                            valueAnimator
                                                                                                                    .getAnimatedValue())
                                                                                                    .floatValue()))
                                                                            * 0.25f)
                                                                    + 1.0f;
                                                    udfpsEnrollDrawable2.invalidateSelf();
                                                    break;
                                            }
                                        }
                                    });
                            long j = (pointF.x == 0.0f && pointF.y == 0.0f) ? 600L : 800L;
                            ValueAnimator ofFloat3 = ValueAnimator.ofFloat(0.0f, 3.1415927f);
                            ofFloat3.setDuration(j);
                            ofFloat3.addUpdateListener(
                                    new ValueAnimator
                                            .AnimatorUpdateListener() { // from class:
                                                                        // com.android.settings.biometrics.fingerprint.UdfpsEnrollDrawable$$ExternalSyntheticLambda0
                                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                        public final void onAnimationUpdate(
                                                ValueAnimator valueAnimator) {
                                            int i12 = i6;
                                            UdfpsEnrollDrawable udfpsEnrollDrawable2 =
                                                    udfpsEnrollDrawable;
                                            udfpsEnrollDrawable2.getClass();
                                            switch (i12) {
                                                case 0:
                                                    udfpsEnrollDrawable2.mCurrentX =
                                                            ((Float)
                                                                            valueAnimator
                                                                                    .getAnimatedValue())
                                                                    .floatValue();
                                                    udfpsEnrollDrawable2.invalidateSelf();
                                                    break;
                                                case 1:
                                                    udfpsEnrollDrawable2.mCurrentY =
                                                            ((Float)
                                                                            valueAnimator
                                                                                    .getAnimatedValue())
                                                                    .floatValue();
                                                    udfpsEnrollDrawable2.invalidateSelf();
                                                    break;
                                                default:
                                                    udfpsEnrollDrawable2.mCurrentScale =
                                                            (((float)
                                                                                    Math.sin(
                                                                                            ((Float)
                                                                                                            valueAnimator
                                                                                                                    .getAnimatedValue())
                                                                                                    .floatValue()))
                                                                            * 0.25f)
                                                                    + 1.0f;
                                                    udfpsEnrollDrawable2.invalidateSelf();
                                                    break;
                                            }
                                        }
                                    });
                            AnimatorSet animatorSet2 = new AnimatorSet();
                            udfpsEnrollDrawable.mTargetAnimatorSet = animatorSet2;
                            animatorSet2.setInterpolator(new AccelerateDecelerateInterpolator());
                            udfpsEnrollDrawable.mTargetAnimatorSet.setDuration(j);
                            udfpsEnrollDrawable.mTargetAnimatorSet.addListener(
                                    udfpsEnrollDrawable.mTargetAnimListener);
                            udfpsEnrollDrawable.mTargetAnimatorSet.playTogether(
                                    ofFloat, ofFloat2, ofFloat3);
                            udfpsEnrollDrawable.mTargetAnimatorSet.start();
                        }
                    }
                    UdfpsEnrollHelper udfpsEnrollHelper3 = udfpsEnrollDrawable.mEnrollHelper;
                    if (udfpsEnrollHelper3 != null
                            && (i = udfpsEnrollHelper3.mTotalSteps) != -1
                            && (i2 = udfpsEnrollHelper3.mRemainingSteps) != -1
                            && i - i2 >= udfpsEnrollHelper3.getStageThresholdSteps(i, 2)) {
                        z = true;
                    }
                    if (udfpsEnrollDrawable.mShouldShowEdgeHint != z) {
                        udfpsEnrollDrawable.mShouldShowEdgeHint = z;
                        break;
                    }
                }
                break;
            default:
                this.f$0.mFingerprintProgressDrawable.updateState(this.f$1, this.f$2, true);
                break;
        }
    }
}
