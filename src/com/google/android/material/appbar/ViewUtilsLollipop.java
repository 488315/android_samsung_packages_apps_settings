package com.google.android.material.appbar;

import android.R;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ViewUtilsLollipop {
    public static final int[] STATE_LIST_ANIM_ATTRS = {R.attr.stateListAnimator};

    public static void setDefaultAppBarLayoutStateListAnimator(View view, float f) {
        int integer =
                view.getResources()
                        .getInteger(com.android.settings.R.integer.app_bar_elevation_anim_duration);
        StateListAnimator stateListAnimator = new StateListAnimator();
        long j = integer;
        stateListAnimator.addState(
                new int[] {
                    R.attr.state_enabled, com.android.settings.R.attr.state_liftable, -2130970082
                },
                ObjectAnimator.ofFloat(view, "elevation", 0.0f).setDuration(j));
        stateListAnimator.addState(
                new int[] {R.attr.state_enabled},
                ObjectAnimator.ofFloat(view, "elevation", f).setDuration(j));
        stateListAnimator.addState(
                new int[0], ObjectAnimator.ofFloat(view, "elevation", 0.0f).setDuration(0L));
        view.setStateListAnimator(stateListAnimator);
    }
}
