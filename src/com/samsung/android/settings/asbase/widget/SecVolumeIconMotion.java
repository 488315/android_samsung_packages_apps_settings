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

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecVolumeIconMotion {
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public Runnable mIconRunnable;
    public final Resources mResources;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.widget.SecVolumeIconMotion$1, reason: invalid class name */
    public final class AnonymousClass1 extends AnimatorListenerAdapter {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ SecVolumeIconMotion this$0;

        public /* synthetic */ AnonymousClass1(SecVolumeIconMotion secVolumeIconMotion, int i) {
            this.$r8$classId = i;
            this.this$0 = secVolumeIconMotion;
        }

        @Override // android.animation.AnimatorListenerAdapter,
                  // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            switch (this.$r8$classId) {
                case 0:
                    SecVolumeIconMotion secVolumeIconMotion = this.this$0;
                    secVolumeIconMotion.mHandler.postDelayed(
                            secVolumeIconMotion.mIconRunnable, 200L);
                    break;
                default:
                    SecVolumeIconMotion secVolumeIconMotion2 = this.this$0;
                    secVolumeIconMotion2.mHandler.postDelayed(
                            secVolumeIconMotion2.mIconRunnable, 200L);
                    break;
            }
        }
    }

    public SecVolumeIconMotion(Context context) {
        this.mResources = context.getResources();
    }

    public static Animator getVibrationAnimator(View view, float f, float f2, int i) {
        ObjectAnimator ofFloat =
                ObjectAnimator.ofFloat(view, "translationX", f, f2 != 0.0f ? (-f) + f2 : 0.0f);
        ofFloat.setDuration(i);
        ofFloat.setInterpolator(new LinearInterpolator());
        return ofFloat;
    }

    public static void startSplashAnimation(final View view) {
        view.setScaleX(0.0f);
        SpringAnimation springAnimation = new SpringAnimation(view);
        springAnimation.cancel();
        springAnimation.mVelocity = 0.0f;
        springAnimation.addUpdateListener(
                new DynamicAnimation$OnAnimationUpdateListener() { // from class:
                                                                   // com.samsung.android.settings.asbase.widget.SecVolumeIconMotion$$ExternalSyntheticLambda0
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
            int i,
            View view,
            View view2,
            View view3,
            View view4,
            View view5,
            View view6,
            boolean z) {
        view5.setVisibility(8);
        view.setVisibility(0);
        view6.setVisibility(8);
        int dimensionPixelSize =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_note_max_x);
        int dimensionPixelSize2 =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_wave_s_max_x);
        int dimensionPixelSize3 =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_wave_l_max_x);
        if (SecVolumeValues.isSpeakerType(i)) {
            view4.setVisibility(8);
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
            int i,
            int i2,
            View view,
            View view2,
            View view3,
            View view4,
            View view5,
            View view6,
            boolean z) {
        view5.setVisibility(8);
        int i3 = 0;
        view.setVisibility(0);
        view6.setVisibility(8);
        int dimensionPixelSize =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_note_mid_x);
        int dimensionPixelSize2 =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_wave_s_mid_x);
        int dimensionPixelSize3 =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_wave_l_mid_x);
        if (SecVolumeValues.isSpeakerType(i)) {
            view4.setVisibility(8);
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
                new SecVolumeIconMotion$$ExternalSyntheticLambda1(
                        this, i2, i, view, view2, view3, view4, view5, view6, 1);
        animatorSet3.addListener(new AnonymousClass1(this, i3));
        animatorSet3.start();
    }

    public final void startMinAnimation(
            int i,
            int i2,
            View view,
            View view2,
            View view3,
            View view4,
            View view5,
            View view6,
            boolean z) {
        float f;
        int i3 = 1;
        view5.setVisibility(8);
        view.setVisibility(0);
        view6.setVisibility(8);
        int dimensionPixelSize =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_note_min_x);
        if (SecVolumeValues.isSpeakerType(i)) {
            view4.setVisibility(8);
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
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(z ? 0L : 100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 =
                ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofFloat3);
        if (SecVolumeValues.isSpeakerType(i)) {
            animatorSet2.playTogether(
                    ObjectAnimator.ofFloat(
                            view2,
                            "x",
                            view2.getX(),
                            this.mResources.getDimensionPixelSize(
                                    R.dimen.volume_sound_icon_wave_s_min_x)));
        }
        animatorSet2.setDuration(z ? 0L : 200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        this.mHandler.removeCallbacks(this.mIconRunnable);
        this.mIconRunnable =
                new SecVolumeIconMotion$$ExternalSyntheticLambda1(
                        this, i2, i, view, view2, view3, view4, view5, view6, 0);
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet);
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.addListener(new AnonymousClass1(this, i3));
        animatorSet3.start();
    }

    public final void startModeMuteChangeAnimation(View view, final View view2) {
        view.setVisibility(0);
        view2.setVisibility(4);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "x", view.getX(), view.getX());
        ofFloat.setDuration(100L);
        ofFloat.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.start();
        animatorSet.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.samsung.android.settings.asbase.widget.SecVolumeIconMotion.3
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        view2.setVisibility(0);
                        SecVolumeIconMotion secVolumeIconMotion = SecVolumeIconMotion.this;
                        View view3 = view2;
                        secVolumeIconMotion.getClass();
                        SecVolumeIconMotion.startSplashAnimation(view3);
                    }
                });
    }

    public final void startMuteAnimation(
            int i,
            View view,
            View view2,
            View view3,
            View view4,
            View view5,
            View view6,
            boolean z) {
        view5.setVisibility(0);
        view.setVisibility(4);
        view6.setVisibility(0);
        int dimensionPixelSize =
                this.mResources.getDimensionPixelSize(R.dimen.volume_media_icon_note_min_x);
        if (SecVolumeValues.isSpeakerType(i)) {
            dimensionPixelSize =
                    this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_note_min_x);
            view4.setVisibility(8);
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
        startSplashAnimation(view6);
    }

    public final void startSoundAnimation(
            final int i, final View view, final View view2, final View view3, final boolean z) {
        int i2;
        float f;
        int dimensionPixelSize =
                this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_note_min_x);
        int dimensionPixelSize2 =
                this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_wave_s_min_x);
        int dimensionPixelSize3 =
                this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_wave_l_mid_x);
        float f2 = 0.5f;
        if (i == 2) {
            dimensionPixelSize =
                    this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_note_mid_x);
            dimensionPixelSize2 =
                    this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_wave_s_mid_x);
            dimensionPixelSize3 =
                    this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_wave_l_mid_x);
            f = 0.1f;
            i2 = 150;
        } else if (z || i == 3) {
            dimensionPixelSize =
                    this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_note_max_x);
            dimensionPixelSize2 =
                    this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_wave_s_max_x);
            dimensionPixelSize3 =
                    this.mResources.getDimensionPixelSize(R.dimen.volume_sound_icon_wave_l_max_x);
            i2 = z ? 0 : 250;
            f = 0.5f;
        } else {
            f2 = 0.3f;
            i2 = 100;
            f = 0.0f;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), f2);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        long j = i2;
        animatorSet.setDuration(j);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 =
                ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
        ObjectAnimator ofFloat4 =
                ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimensionPixelSize2);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofFloat3);
        animatorSet2.playTogether(ofFloat4);
        if (z || i != 1) {
            animatorSet2.playTogether(
                    ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimensionPixelSize3));
        }
        animatorSet2.setDuration(j);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.playTogether(animatorSet);
        animatorSet3.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.samsung.android.settings.asbase.widget.SecVolumeIconMotion.4
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        if (z) {
                            return;
                        }
                        int i3 = i;
                        if (i3 == 1) {
                            SecVolumeIconMotion.this.startSoundAnimation(
                                    2, view, view2, view3, false);
                        } else if (i3 == 2) {
                            SecVolumeIconMotion.this.startSoundAnimation(
                                    3, view, view2, view3, false);
                        }
                    }
                });
        animatorSet3.start();
    }

    public final void startVibrationAnimation(View view) {
        float dimensionPixelSize =
                this.mResources.getDimensionPixelSize(R.dimen.volume_vibrate_init);
        float dimensionPixelSize2 =
                this.mResources.getDimensionPixelSize(R.dimen.volume_vibrate_offset);
        float f = -dimensionPixelSize;
        Animator vibrationAnimator = getVibrationAnimator(view, 0.0f, f, 60);
        float f2 = dimensionPixelSize - dimensionPixelSize2;
        Animator vibrationAnimator2 = getVibrationAnimator(view, f, f2, 80);
        float f3 = -(dimensionPixelSize - (dimensionPixelSize2 * 2.0f));
        Animator vibrationAnimator3 = getVibrationAnimator(view, f2, f3, 100);
        Animator vibrationAnimator4 = getVibrationAnimator(view, f3, 0.0f, 120);
        ArrayList arrayList = new ArrayList();
        arrayList.add(vibrationAnimator);
        arrayList.add(vibrationAnimator2);
        arrayList.add(vibrationAnimator3);
        arrayList.add(vibrationAnimator4);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(arrayList);
        animatorSet.start();
    }
}
