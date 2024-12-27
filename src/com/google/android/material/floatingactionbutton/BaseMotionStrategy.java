package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Property;
import android.view.View;

import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.MotionSpec;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class BaseMotionStrategy {
    public final Context context;
    public MotionSpec defaultMotionSpec;
    public final ExtendedFloatingActionButton fab;
    public final ArrayList listeners = new ArrayList();
    public MotionSpec motionSpec;
    public final AnimatorTracker tracker;

    public BaseMotionStrategy(
            ExtendedFloatingActionButton extendedFloatingActionButton,
            AnimatorTracker animatorTracker) {
        this.fab = extendedFloatingActionButton;
        this.context = extendedFloatingActionButton.getContext();
        this.tracker = animatorTracker;
    }

    public AnimatorSet createAnimator() {
        MotionSpec motionSpec = this.motionSpec;
        if (motionSpec == null) {
            if (this.defaultMotionSpec == null) {
                this.defaultMotionSpec =
                        MotionSpec.createFromResource(this.context, getDefaultMotionSpecResource());
            }
            motionSpec = this.defaultMotionSpec;
            motionSpec.getClass();
        }
        return createAnimator(motionSpec);
    }

    public abstract int getDefaultMotionSpecResource();

    public void onAnimationCancel() {
        this.tracker.currentAnimator = null;
    }

    public abstract void onAnimationEnd();

    public abstract void onAnimationStart(Animator animator);

    public abstract void performNow();

    public abstract boolean shouldCancel();

    public final AnimatorSet createAnimator(MotionSpec motionSpec) {
        ArrayList arrayList = new ArrayList();
        boolean hasPropertyValues = motionSpec.hasPropertyValues("opacity");
        ExtendedFloatingActionButton extendedFloatingActionButton = this.fab;
        if (hasPropertyValues) {
            arrayList.add(
                    motionSpec.getAnimator("opacity", extendedFloatingActionButton, View.ALPHA));
        }
        if (motionSpec.hasPropertyValues("scale")) {
            arrayList.add(
                    motionSpec.getAnimator("scale", extendedFloatingActionButton, View.SCALE_Y));
            arrayList.add(
                    motionSpec.getAnimator("scale", extendedFloatingActionButton, View.SCALE_X));
        }
        if (motionSpec.hasPropertyValues("width")) {
            arrayList.add(
                    motionSpec.getAnimator(
                            "width",
                            extendedFloatingActionButton,
                            ExtendedFloatingActionButton.WIDTH));
        }
        if (motionSpec.hasPropertyValues("height")) {
            arrayList.add(
                    motionSpec.getAnimator(
                            "height",
                            extendedFloatingActionButton,
                            ExtendedFloatingActionButton.HEIGHT));
        }
        if (motionSpec.hasPropertyValues("paddingStart")) {
            arrayList.add(
                    motionSpec.getAnimator(
                            "paddingStart",
                            extendedFloatingActionButton,
                            ExtendedFloatingActionButton.PADDING_START));
        }
        if (motionSpec.hasPropertyValues("paddingEnd")) {
            arrayList.add(
                    motionSpec.getAnimator(
                            "paddingEnd",
                            extendedFloatingActionButton,
                            ExtendedFloatingActionButton.PADDING_END));
        }
        if (motionSpec.hasPropertyValues("labelOpacity")) {
            arrayList.add(
                    motionSpec.getAnimator(
                            "labelOpacity",
                            extendedFloatingActionButton,
                            new Property() { // from class:
                                             // com.google.android.material.floatingactionbutton.BaseMotionStrategy.1
                                @Override // android.util.Property
                                public final Object get(Object obj) {
                                    ExtendedFloatingActionButton extendedFloatingActionButton2 =
                                            (ExtendedFloatingActionButton) obj;
                                    return Float.valueOf(
                                            AnimationUtils.lerp(
                                                    0.0f,
                                                    1.0f,
                                                    (Color.alpha(
                                                                            extendedFloatingActionButton2
                                                                                    .getCurrentTextColor())
                                                                    / 255.0f)
                                                            / Color.alpha(
                                                                    extendedFloatingActionButton2
                                                                            .originalTextCsl
                                                                            .getColorForState(
                                                                                    extendedFloatingActionButton2
                                                                                            .getDrawableState(),
                                                                                    BaseMotionStrategy
                                                                                            .this
                                                                                            .fab
                                                                                            .originalTextCsl
                                                                                            .getDefaultColor()))));
                                }

                                @Override // android.util.Property
                                public final void set(Object obj, Object obj2) {
                                    ExtendedFloatingActionButton extendedFloatingActionButton2 =
                                            (ExtendedFloatingActionButton) obj;
                                    Float f = (Float) obj2;
                                    int colorForState =
                                            extendedFloatingActionButton2.originalTextCsl
                                                    .getColorForState(
                                                            extendedFloatingActionButton2
                                                                    .getDrawableState(),
                                                            BaseMotionStrategy.this.fab
                                                                    .originalTextCsl
                                                                    .getDefaultColor());
                                    ColorStateList valueOf =
                                            ColorStateList.valueOf(
                                                    Color.argb(
                                                            (int)
                                                                    (AnimationUtils.lerp(
                                                                                    0.0f,
                                                                                    Color.alpha(
                                                                                                    colorForState)
                                                                                            / 255.0f,
                                                                                    f.floatValue())
                                                                            * 255.0f),
                                                            Color.red(colorForState),
                                                            Color.green(colorForState),
                                                            Color.blue(colorForState)));
                                    if (f.floatValue() == 1.0f) {
                                        extendedFloatingActionButton2.silentlyUpdateTextColor(
                                                extendedFloatingActionButton2.originalTextCsl);
                                    } else {
                                        extendedFloatingActionButton2.silentlyUpdateTextColor(
                                                valueOf);
                                    }
                                }
                            }));
        }
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSetCompat.playTogether(animatorSet, arrayList);
        return animatorSet;
    }
}
