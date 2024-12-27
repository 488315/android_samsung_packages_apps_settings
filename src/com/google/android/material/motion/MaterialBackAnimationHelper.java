package com.google.android.material.motion;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.view.View;
import android.view.animation.PathInterpolator;

import androidx.activity.BackEventCompat;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class MaterialBackAnimationHelper {
    public BackEventCompat backEvent;
    public final int cancelDuration;
    public final int hideDurationMax;
    public final int hideDurationMin;
    public final TimeInterpolator progressInterpolator;
    public final View view;

    public MaterialBackAnimationHelper(View view) {
        this.view = view;
        Context context = view.getContext();
        this.progressInterpolator =
                MotionUtils.resolveThemeInterpolator(
                        context,
                        R.attr.motionEasingStandardDecelerateInterpolator,
                        new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f));
        this.hideDurationMax =
                MotionUtils.resolveThemeDuration(context, R.attr.motionDurationMedium2, 300);
        this.hideDurationMin =
                MotionUtils.resolveThemeDuration(context, R.attr.motionDurationShort3, 150);
        this.cancelDuration =
                MotionUtils.resolveThemeDuration(context, R.attr.motionDurationShort2, 100);
    }
}
