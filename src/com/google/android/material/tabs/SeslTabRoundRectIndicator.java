package com.google.android.material.tabs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.ScaleAnimation;

import androidx.appcompat.util.SeslMisc;
import androidx.core.view.ViewCompat;

import com.android.settings.R;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SeslTabRoundRectIndicator extends View {
    public final Interpolator LINEAR_INTERPOLATOR;
    public final PathInterpolator SCALE_INTERPOLATOR;
    public AnimationSet mPressAnimationSet;

    public SeslTabRoundRectIndicator(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.LINEAR_INTERPOLATOR = new LinearInterpolator();
        this.SCALE_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        Drawable drawable =
                context.getDrawable(
                        SeslMisc.isLightTheme(context)
                                ? R.drawable.sesl_tablayout_subtab_indicator_background_light
                                : R.drawable.sesl_tablayout_subtab_indicator_background_dark);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setBackground(drawable);
    }

    public final void onHide() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 0.0f);
        alphaAnimation.setDuration(0L);
        alphaAnimation.setFillAfter(true);
        startAnimation(alphaAnimation);
        setAlpha(0.0f);
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0 || isSelected()) {
            return;
        }
        onHide();
    }

    public final void setPressed() {
        setAlpha(1.0f);
        AnimationSet animationSet = new AnimationSet(false);
        this.mPressAnimationSet = animationSet;
        animationSet.setFillAfter(true);
        this.mPressAnimationSet.setAnimationListener(
                new Animation
                        .AnimationListener() { // from class:
                                               // com.google.android.material.tabs.SeslTabRoundRectIndicator.1
                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationEnd(Animation animation) {
                        SeslTabRoundRectIndicator.this.mPressAnimationSet = null;
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationRepeat(Animation animation) {}

                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationStart(Animation animation) {}
                });
        ScaleAnimation scaleAnimation =
                new ScaleAnimation(1.0f, 0.95f, 1.0f, 0.95f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(250L);
        scaleAnimation.setInterpolator(this.SCALE_INTERPOLATOR);
        scaleAnimation.setFillAfter(true);
        this.mPressAnimationSet.addAnimation(scaleAnimation);
        if (!isSelected()) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(100L);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setInterpolator(this.LINEAR_INTERPOLATOR);
            this.mPressAnimationSet.addAnimation(alphaAnimation);
        }
        startAnimation(this.mPressAnimationSet);
    }

    public final void setReleased() {
        setAlpha(1.0f);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);
        ScaleAnimation scaleAnimation =
                new ScaleAnimation(0.95f, 1.0f, 0.95f, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(350L);
        scaleAnimation.setInterpolator(this.SCALE_INTERPOLATOR);
        scaleAnimation.setFillAfter(true);
        animationSet.addAnimation(scaleAnimation);
        startAnimation(animationSet);
    }

    public final void setSelectedIndicatorColor(int i) {
        if (getBackground() instanceof NinePatchDrawable) {
            return;
        }
        getBackground().setTint(i);
        if (isSelected()) {
            return;
        }
        onHide();
    }

    public final void setShow() {
        setAlpha(1.0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
        alphaAnimation.setDuration(0L);
        alphaAnimation.setFillAfter(true);
        startAnimation(alphaAnimation);
    }

    public SeslTabRoundRectIndicator(Context context) {
        this(context, null);
    }

    public SeslTabRoundRectIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeslTabRoundRectIndicator(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }
}
