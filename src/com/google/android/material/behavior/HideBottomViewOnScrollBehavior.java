package com.google.android.material.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.settings.R;

import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;

import java.util.Iterator;
import java.util.LinkedHashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class HideBottomViewOnScrollBehavior<V extends View> extends CoordinatorLayout.Behavior {
    public ViewPropertyAnimator currentAnimator;
    public int enterAnimDuration;
    public TimeInterpolator enterAnimInterpolator;
    public int exitAnimDuration;
    public TimeInterpolator exitAnimInterpolator;
    public final LinkedHashSet onScrollStateChangedListeners = new LinkedHashSet();
    public int height = 0;
    public int currentState = 2;

    public HideBottomViewOnScrollBehavior() {}

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        this.height =
                view.getMeasuredHeight()
                        + ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin;
        this.enterAnimDuration =
                MotionUtils.resolveThemeDuration(
                        view.getContext(), R.attr.motionDurationLong2, 225);
        this.exitAnimDuration =
                MotionUtils.resolveThemeDuration(
                        view.getContext(), R.attr.motionDurationMedium4, 175);
        this.enterAnimInterpolator =
                MotionUtils.resolveThemeInterpolator(
                        view.getContext(),
                        R.attr.motionEasingEmphasizedInterpolator,
                        AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        this.exitAnimInterpolator =
                MotionUtils.resolveThemeInterpolator(
                        view.getContext(),
                        R.attr.motionEasingEmphasizedInterpolator,
                        AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        return false;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onNestedScroll(
            CoordinatorLayout coordinatorLayout,
            View view,
            View view2,
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            int[] iArr) {
        if (i2 > 0) {
            if (this.currentState == 1) {
                return;
            }
            ViewPropertyAnimator viewPropertyAnimator = this.currentAnimator;
            if (viewPropertyAnimator != null) {
                viewPropertyAnimator.cancel();
                view.clearAnimation();
            }
            this.currentState = 1;
            Iterator it = this.onScrollStateChangedListeners.iterator();
            if (it.hasNext()) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                throw null;
            }
            this.currentAnimator =
                    view.animate()
                            .translationY(this.height)
                            .setInterpolator(this.exitAnimInterpolator)
                            .setDuration(this.exitAnimDuration)
                            .setListener(
                                    new AnimatorListenerAdapter() { // from class:
                                                                    // com.google.android.material.behavior.HideBottomViewOnScrollBehavior.1
                                        @Override // android.animation.AnimatorListenerAdapter,
                                                  // android.animation.Animator.AnimatorListener
                                        public final void onAnimationEnd(Animator animator) {
                                            HideBottomViewOnScrollBehavior.this.currentAnimator =
                                                    null;
                                        }
                                    });
            return;
        }
        if (i2 >= 0 || this.currentState == 2) {
            return;
        }
        ViewPropertyAnimator viewPropertyAnimator2 = this.currentAnimator;
        if (viewPropertyAnimator2 != null) {
            viewPropertyAnimator2.cancel();
            view.clearAnimation();
        }
        this.currentState = 2;
        Iterator it2 = this.onScrollStateChangedListeners.iterator();
        if (it2.hasNext()) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it2.next());
            throw null;
        }
        this.currentAnimator =
                view.animate()
                        .translationY(0)
                        .setInterpolator(this.enterAnimInterpolator)
                        .setDuration(this.enterAnimDuration)
                        .setListener(
                                new AnimatorListenerAdapter() { // from class:
                                                                // com.google.android.material.behavior.HideBottomViewOnScrollBehavior.1
                                    @Override // android.animation.AnimatorListenerAdapter,
                                              // android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator) {
                                        HideBottomViewOnScrollBehavior.this.currentAnimator = null;
                                    }
                                });
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(
            CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
        return i == 2;
    }

    public HideBottomViewOnScrollBehavior(Context context, AttributeSet attributeSet) {}
}
