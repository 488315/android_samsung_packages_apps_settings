package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.transition.Transition;
import androidx.transition.TransitionValues;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class TextScale extends Transition {
    @Override // androidx.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view instanceof TextView) {
            ((HashMap) transitionValues.values)
                    .put("android:textscale:scale", Float.valueOf(((TextView) view).getScaleX()));
        }
    }

    @Override // androidx.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view instanceof TextView) {
            ((HashMap) transitionValues.values)
                    .put("android:textscale:scale", Float.valueOf(((TextView) view).getScaleX()));
        }
    }

    @Override // androidx.transition.Transition
    public final Animator createAnimator(
            ViewGroup viewGroup,
            TransitionValues transitionValues,
            TransitionValues transitionValues2) {
        if (transitionValues == null
                || transitionValues2 == null
                || !(transitionValues.view instanceof TextView)) {
            return null;
        }
        View view = transitionValues2.view;
        if (!(view instanceof TextView)) {
            return null;
        }
        final TextView textView = (TextView) view;
        Map map = transitionValues.values;
        Map map2 = transitionValues2.values;
        HashMap hashMap = (HashMap) map;
        float floatValue =
                hashMap.get("android:textscale:scale") != null
                        ? ((Float) hashMap.get("android:textscale:scale")).floatValue()
                        : 1.0f;
        HashMap hashMap2 = (HashMap) map2;
        float floatValue2 =
                hashMap2.get("android:textscale:scale") != null
                        ? ((Float) hashMap2.get("android:textscale:scale")).floatValue()
                        : 1.0f;
        if (floatValue == floatValue2) {
            return null;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(floatValue, floatValue2);
        ofFloat.addUpdateListener(
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.google.android.material.internal.TextScale.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue3 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        textView.setScaleX(floatValue3);
                        textView.setScaleY(floatValue3);
                    }
                });
        return ofFloat;
    }
}
