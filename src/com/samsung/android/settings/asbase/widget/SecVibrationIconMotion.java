package com.samsung.android.settings.asbase.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;

import androidx.dynamicanimation.animation.DynamicAnimation$OnAnimationUpdateListener;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecVibrationIconMotion {
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public Runnable mIconRunnable;
    public final Resources mResources;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.widget.SecVibrationIconMotion$1, reason: invalid class name */
    public final class AnonymousClass1 extends AnimatorListenerAdapter {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ SecVibrationIconMotion this$0;

        public /* synthetic */ AnonymousClass1(
                SecVibrationIconMotion secVibrationIconMotion, int i) {
            this.$r8$classId = i;
            this.this$0 = secVibrationIconMotion;
        }

        @Override // android.animation.AnimatorListenerAdapter,
                  // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            switch (this.$r8$classId) {
                case 0:
                    SecVibrationIconMotion secVibrationIconMotion = this.this$0;
                    secVibrationIconMotion.mHandler.postDelayed(
                            secVibrationIconMotion.mIconRunnable, 200L);
                    break;
                default:
                    SecVibrationIconMotion secVibrationIconMotion2 = this.this$0;
                    secVibrationIconMotion2.mHandler.postDelayed(
                            secVibrationIconMotion2.mIconRunnable, 200L);
                    break;
            }
        }
    }

    public SecVibrationIconMotion(Context context) {
        this.mResources = context.getResources();
    }

    public static void startSplashAnimation(final View view) {
        view.setScaleX(0.0f);
        SpringAnimation springAnimation = new SpringAnimation(view);
        springAnimation.cancel();
        springAnimation.mVelocity = 0.0f;
        springAnimation.addUpdateListener(
                new DynamicAnimation$OnAnimationUpdateListener() { // from class:
                                                                   // com.samsung.android.settings.asbase.widget.SecVibrationIconMotion$$ExternalSyntheticLambda0
                    @Override // androidx.dynamicanimation.animation.DynamicAnimation$OnAnimationUpdateListener
                    public final void onAnimationUpdate(
                            SpringAnimation springAnimation2, float f, float f2) {
                        View view2 = view;
                        if (f2 == 0.0f) {
                            view2.setPivotX(0.0f);
                            view2.setPivotY(0.0f);
                        }
                    }
                });
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(300.0f);
        springForce.setDampingRatio(0.58f);
        springAnimation.mSpring = springForce;
        springAnimation.mValue = 0.0f;
        springAnimation.mStartValueIsSet = true;
        springAnimation.animateToFinalPosition(1.0f);
    }

    public final void startMaxAnimation(
            int i, View view, View view2, View view3, View view4, View view5, boolean z) {
        view4.setVisibility(8);
        view.setVisibility(0);
        view5.setVisibility(8);
        int dimensionPixelSize =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_note_max_x);
        int dimensionPixelSize2 =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_wave_s_max_x);
        int dimensionPixelSize3 =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_wave_l_max_x);
        if (i == 2) {
            view2.setVisibility(0);
            view3.setVisibility(0);
            dimensionPixelSize2 =
                    this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_wave_s_max_x);
            dimensionPixelSize3 =
                    this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_wave_l_max_x);
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.5f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(z ? 0L : 150L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 =
                ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
        ObjectAnimator ofFloat4 =
                ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimensionPixelSize2);
        ObjectAnimator ofFloat5 =
                ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimensionPixelSize3);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofFloat3);
        animatorSet2.playTogether(ofFloat4);
        animatorSet2.playTogether(ofFloat5);
        animatorSet2.setDuration(z ? 0L : 200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet);
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.start();
    }

    public final void startMidAnimation(
            int i, int i2, View view, View view2, View view3, View view4, View view5, boolean z) {
        view4.setVisibility(8);
        int i3 = 0;
        view.setVisibility(0);
        view5.setVisibility(8);
        int dimensionPixelSize =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_note_mid_x);
        int dimensionPixelSize2 =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_wave_s_mid_x);
        int dimensionPixelSize3 =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_wave_l_mid_x);
        if (i == 2) {
            view2.setVisibility(0);
            view3.setVisibility(0);
            dimensionPixelSize2 =
                    this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_wave_s_mid_x);
            dimensionPixelSize3 =
                    this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_wave_l_mid_x);
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(z ? 0L : 100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 =
                ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
        ObjectAnimator ofFloat4 =
                ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimensionPixelSize2);
        ObjectAnimator ofFloat5 =
                ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimensionPixelSize3);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofFloat3);
        animatorSet2.playTogether(ofFloat4);
        animatorSet2.playTogether(ofFloat5);
        animatorSet2.setDuration(z ? 0L : 200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.playTogether(animatorSet);
        this.mHandler.removeCallbacks(this.mIconRunnable);
        this.mIconRunnable =
                new SecVibrationIconMotion$$ExternalSyntheticLambda1(
                        this, i2, i, view, view2, view3, view4, view5, 1);
        animatorSet3.addListener(new AnonymousClass1(this, i3));
        animatorSet3.start();
    }

    public final void startMinAnimation(
            int i, int i2, View view, View view2, View view3, View view4, View view5, boolean z) {
        float f;
        AnimatorSet animatorSet;
        AnimatorSet animatorSet2;
        long j;
        view4.setVisibility(8);
        view.setVisibility(0);
        view5.setVisibility(8);
        int dimensionPixelSize =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_note_min_x);
        if (i == 2) {
            view2.setVisibility(0);
            view3.setVisibility(0);
            dimensionPixelSize =
                    this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_note_min_x);
            f = 0.3f;
        } else {
            f = 0.0f;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(ofFloat);
        animatorSet3.playTogether(ofFloat2);
        animatorSet3.setDuration(z ? 0L : 100L);
        animatorSet3.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 =
                ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
        AnimatorSet animatorSet4 = new AnimatorSet();
        animatorSet4.playTogether(ofFloat3);
        if (i == 2) {
            animatorSet = animatorSet4;
            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(
                            view2,
                            "x",
                            view2.getX(),
                            this.mResources.getDimensionPixelSize(
                                    R.dimen.volume_sound_icon_wave_s_min_x)));
        } else {
            animatorSet = animatorSet4;
        }
        if (z) {
            animatorSet2 = animatorSet3;
            j = 0;
        } else {
            animatorSet2 = animatorSet3;
            j = 200;
        }
        animatorSet.setDuration(j);
        animatorSet.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        this.mHandler.removeCallbacks(this.mIconRunnable);
        this.mIconRunnable =
                new SecVibrationIconMotion$$ExternalSyntheticLambda1(
                        this, i2, i, view, view2, view3, view4, view5, 0);
        AnimatorSet animatorSet5 = new AnimatorSet();
        animatorSet5.playTogether(animatorSet2);
        animatorSet5.playTogether(animatorSet);
        animatorSet5.addListener(new AnonymousClass1(this, 1));
        animatorSet5.start();
    }

    public final void startMuteAnimation(
            int i, View view, View view2, View view3, View view4, View view5, boolean z) {
        view4.setVisibility(0);
        view.setVisibility(4);
        view5.setVisibility(0);
        int dimensionPixelSize =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_note_min_x);
        if (i == 2) {
            dimensionPixelSize =
                    this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_note_min_x);
            view2.setVisibility(4);
            view3.setVisibility(4);
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(z ? 0L : 100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 =
                ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
        ofFloat3.setDuration(z ? 0L : 200L);
        ofFloat3.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        this.mHandler.removeCallbacks(this.mIconRunnable);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorSet);
        animatorSet2.playTogether(ofFloat3);
        animatorSet2.start();
        if (z) {
            return;
        }
        startSplashAnimation(view5);
    }
}
