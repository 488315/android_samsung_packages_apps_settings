package com.google.android.setupdesign.transition;

import android.app.Activity;
import android.content.res.TypedArray;

import com.android.settings.R;

import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.BuildCompatUtils;
import com.google.android.setupdesign.util.ThemeHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class TransitionHelper {
    static boolean isFinishCalled = false;
    static boolean isStartActivity = false;
    static boolean isStartActivityForResult = false;

    public static void applyForwardTransition(Activity activity, int i) {
        if (BuildCompatUtils.isAtLeastU()
                && PartnerConfigHelper.isGlifThemeControlledTransitionApplied(activity)
                && i != 6) {
            return;
        }
        if (BuildCompatUtils.isAtLeastU() && i == 6) {
            if (PartnerConfigHelper.isGlifThemeControlledTransitionApplied(activity)) {
                activity.overridePendingTransition(
                        ThemeHelper.shouldApplyDynamicColor(activity)
                                ? R.anim.shared_x_axis_activity_open_enter_dynamic_color
                                : R.anim.shared_x_axis_activity_open_enter,
                        R.anim.shared_x_axis_activity_open_exit);
                return;
            } else {
                activity.overridePendingTransition(
                        R.anim.sud_slide_next_in, R.anim.sud_slide_next_out);
                return;
            }
        }
        if (i == 2) {
            activity.overridePendingTransition(R.anim.sud_slide_next_in, R.anim.sud_slide_next_out);
            return;
        }
        if (i == 3) {
            activity.overridePendingTransition(android.R.anim.fade_in, R.anim.sud_stay);
            return;
        }
        if (i == 1) {
            TypedArray obtainStyledAttributes =
                    activity.obtainStyledAttributes(
                            android.R.style.Animation.Activity,
                            new int[] {
                                android.R.attr.activityOpenEnterAnimation,
                                android.R.attr.activityOpenExitAnimation
                            });
            activity.overridePendingTransition(
                    obtainStyledAttributes.getResourceId(0, 0),
                    obtainStyledAttributes.getResourceId(1, 0));
            obtainStyledAttributes.recycle();
        } else if (i == 4) {
            activity.overridePendingTransition(
                    R.anim.sud_pre_p_activity_open_enter, R.anim.sud_pre_p_activity_open_exit);
        } else if (i == -1) {
            activity.overridePendingTransition(0, 0);
        }
    }
}
