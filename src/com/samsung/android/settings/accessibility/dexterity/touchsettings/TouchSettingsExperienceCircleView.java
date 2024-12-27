package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.provider.Settings;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class TouchSettingsExperienceCircleView extends FrameLayout {
    public final ImageView mCircle;
    public final Context mContext;
    public final ImageView mFail;
    public final ImageView mHold;
    public final ProgressBar mProgress;
    public final AnimatorSet mRotationSet;
    public final AnimatorSet mScaleSet;
    public final ImageView mStandBy;
    public int mState;
    public final ImageView mSuccess;
    public final View mView;

    public TouchSettingsExperienceCircleView(Context context) {
        super(context);
        this.mState = -1;
        this.mContext = context;
        View inflate =
                LayoutInflater.from(context)
                        .inflate(R.layout.layout_touch_settings_experience_circle_view, this);
        this.mView = inflate;
        ImageView imageView = (ImageView) inflate.findViewById(R.id.tap_circle);
        this.mCircle = imageView;
        this.mProgress = (ProgressBar) inflate.findViewById(R.id.tap_progress);
        this.mStandBy = (ImageView) inflate.findViewById(R.id.tap_stand_by);
        this.mHold = (ImageView) inflate.findViewById(R.id.tap_hold);
        this.mSuccess = (ImageView) inflate.findViewById(R.id.tap_success);
        this.mFail = (ImageView) inflate.findViewById(R.id.tap_fail);
        this.mScaleSet = new AnimatorSet();
        this.mRotationSet = new AnimatorSet();
        this.mScaleSet.playTogether(
                ObjectAnimator.ofFloat(inflate, (Property<View, Float>) View.SCALE_X, 1.0f, 1.0f),
                ObjectAnimator.ofFloat(inflate, (Property<View, Float>) View.SCALE_Y, 1.0f, 1.0f));
        this.mScaleSet.setDuration(350L);
        this.mScaleSet.addListener(
                new Animator
                        .AnimatorListener() { // from class:
                                              // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceCircleView.1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        boolean z =
                                Settings.Global.getInt(
                                                TouchSettingsExperienceCircleView.this.mContext
                                                        .getContentResolver(),
                                                "remove_animations",
                                                0)
                                        == 1;
                        TouchSettingsExperienceCircleView.this.mCircle.setVisibility(z ? 8 : 0);
                        TouchSettingsExperienceCircleView.this.mProgress.setVisibility(z ? 8 : 0);
                        TouchSettingsExperienceCircleView.this.mStandBy.setVisibility(z ? 0 : 8);
                        TouchSettingsExperienceCircleView.this.mHold.setVisibility(0);
                        TouchSettingsExperienceCircleView.this.mCircle.setRotation(0.0f);
                        TouchSettingsExperienceCircleView.this.mProgress.setProgress(0);
                        TouchSettingsExperienceCircleView.this.mView.setScaleX(1.0f);
                        TouchSettingsExperienceCircleView.this.mView.setScaleY(1.0f);
                        TouchSettingsExperienceCircleView.this.mView.setAlpha(1.0f);
                        TouchSettingsExperienceCircleView.this.mView.setVisibility(0);
                        TouchSettingsExperienceCircleView.this.mRotationSet.start();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {}

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {}

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {}
                });
        this.mScaleSet.setInterpolator(new PathInterpolator(0.22f, 0.17f, 0.0f, 1.0f));
        ObjectAnimator ofFloat =
                ObjectAnimator.ofFloat(
                        imageView, (Property<ImageView, Float>) View.ROTATION, 0.0f, 360.0f);
        ofFloat.addUpdateListener(
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceCircleView$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        TouchSettingsExperienceCircleView touchSettingsExperienceCircleView =
                                TouchSettingsExperienceCircleView.this;
                        touchSettingsExperienceCircleView.getClass();
                        touchSettingsExperienceCircleView.mProgress.setProgress(
                                (int) ((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
        this.mRotationSet.play(ofFloat);
    }

    public final void reset() {
        this.mProgress.setVisibility(4);
        this.mCircle.setVisibility(4);
        this.mHold.setVisibility(4);
        this.mSuccess.setVisibility(4);
        this.mFail.setVisibility(4);
        this.mStandBy.setVisibility(0);
    }
}
