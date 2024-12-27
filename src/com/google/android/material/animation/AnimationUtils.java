package com.google.android.material.animation;

import android.animation.TimeInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.compose.animation.AndroidFlingSpline$$ExternalSyntheticOutline0;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AnimationUtils {
    public static final TimeInterpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static final FastOutSlowInInterpolator FAST_OUT_SLOW_IN_INTERPOLATOR =
            new FastOutSlowInInterpolator();
    public static final FastOutLinearInInterpolator FAST_OUT_LINEAR_IN_INTERPOLATOR =
            new FastOutLinearInInterpolator(FastOutLinearInInterpolator.VALUES);
    public static final LinearOutSlowInInterpolator LINEAR_OUT_SLOW_IN_INTERPOLATOR =
            new LinearOutSlowInInterpolator(LinearOutSlowInInterpolator.VALUES);
    public static final TimeInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

    public static float lerp(float f, float f2, float f3) {
        return AndroidFlingSpline$$ExternalSyntheticOutline0.m(f2, f, f3, f);
    }

    public static int lerp(int i, int i2, float f) {
        return Math.round(f * (i2 - i)) + i;
    }

    public static float lerp(float f, float f2, float f3, float f4, float f5) {
        return f5 <= f3 ? f : f5 >= f4 ? f2 : lerp(f, f2, (f5 - f3) / (f4 - f3));
    }
}
