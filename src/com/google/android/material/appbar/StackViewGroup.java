package com.google.android.material.appbar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import com.android.settings.R;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.Stack;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class StackViewGroup {
    public final FrameLayout rootView;
    public final SceneStack sceneStack = new SceneStack();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\u0010\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003B\u0007¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"
            },
            d2 = {
                "com/google/android/material/appbar/StackViewGroup$SceneStack",
                "Landroid/view/View;",
                "T",
                "Ljava/util/Stack;",
                "<init>",
                "()V",
                "material_release"
            },
            k = 1,
            mv = {1, 8, 0})
    public final class SceneStack<T extends View> extends Stack<T> {
        @Override // java.util.Vector, java.util.AbstractCollection, java.util.Collection,
                  // java.util.List
        public final /* bridge */ boolean contains(Object obj) {
            if (obj == null ? true : obj instanceof View) {
                return super.contains((View) obj);
            }
            return false;
        }

        @Override // java.util.Vector, java.util.AbstractList, java.util.List
        public final /* bridge */ int indexOf(Object obj) {
            if (obj == null ? true : obj instanceof View) {
                return super.indexOf((View) obj);
            }
            return -1;
        }

        @Override // java.util.Vector, java.util.AbstractList, java.util.List
        public final /* bridge */ int lastIndexOf(Object obj) {
            if (obj == null ? true : obj instanceof View) {
                return super.lastIndexOf((View) obj);
            }
            return -1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Stack
        public final Object pop() {
            View result;
            synchronized (this) {
                try {
                    result = (View) super.pop();
                    if (super.size() > 0) {
                        ((View) peek()).setVisibility(0);
                    }
                    Intrinsics.checkNotNullExpressionValue(result, "result");
                } catch (Throwable th) {
                    throw th;
                }
            }
            return result;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Stack
        public final View push(View item) {
            Intrinsics.checkNotNullParameter(item, "item");
            if (super.size() > 0) {
                T peek = peek();
                Intrinsics.checkNotNull(peek);
                ((View) peek).setVisibility(8);
            }
            Object push = super.push((SceneStack<T>) item);
            Intrinsics.checkNotNullExpressionValue(push, "super.push(item)");
            return (View) push;
        }

        @Override // java.util.Vector, java.util.AbstractCollection, java.util.Collection,
                  // java.util.List
        public final /* bridge */ boolean remove(Object obj) {
            if (obj == null ? true : obj instanceof View) {
                return super.remove((View) obj);
            }
            return false;
        }
    }

    public StackViewGroup(FrameLayout frameLayout) {
        this.rootView = frameLayout;
        Interpolator loadInterpolator =
                AnimationUtils.loadInterpolator(
                        frameLayout.getContext(), R.interpolator.sesl_interpolator_0_0_1_1);
        Property property = View.ALPHA;
        final ObjectAnimator ofFloat =
                ObjectAnimator.ofFloat(
                        (Object) null, (Property<Object, Float>) property, 0.0f, 1.0f);
        ofFloat.setInterpolator(loadInterpolator);
        ofFloat.setDuration(200L);
        ofFloat.setStartDelay(100L);
        ofFloat.addListener(
                new Animator
                        .AnimatorListener() { // from class:
                                              // com.google.android.material.appbar.StackViewGroup$showAnimator$1$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        Object target = ofFloat.getTarget();
                        View view = target instanceof View ? (View) target : null;
                        if (view == null) {
                            return;
                        }
                        view.setVisibility(0);
                    }
                });
        final ObjectAnimator ofFloat2 =
                ObjectAnimator.ofFloat(
                        (Object) null, (Property<Object, Float>) property, 1.0f, 0.0f);
        ofFloat2.setInterpolator(loadInterpolator);
        ofFloat2.setDuration(100L);
        ofFloat2.addListener(
                new Animator
                        .AnimatorListener() { // from class:
                                              // com.google.android.material.appbar.StackViewGroup$hideAnimator$1$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        Object target = ofFloat2.getTarget();
                        View view = target instanceof View ? (View) target : null;
                        if (view != null) {
                            this.rootView.removeView(view);
                        }
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                    }
                });
        new AnimatorSet().playTogether(ofFloat2, ofFloat);
    }
}
