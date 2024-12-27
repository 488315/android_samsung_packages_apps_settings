package com.google.android.material.ripple;

import android.R;
import android.content.res.ColorStateList;
import android.graphics.Color;

import androidx.core.graphics.ColorUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class RippleUtils {
    static final String TRANSPARENT_DEFAULT_COLOR_WARNING =
            "Use a non-transparent color for the default color as it will be used to finish ripple"
                + " animations.";
    public static final int[] PRESSED_STATE_SET = {R.attr.state_pressed};
    public static final int[] FOCUSED_STATE_SET = {R.attr.state_focused};
    public static final int[] SELECTED_PRESSED_STATE_SET = {
        R.attr.state_selected, R.attr.state_pressed
    };
    public static final int[] SELECTED_STATE_SET = {R.attr.state_selected};
    static final String LOG_TAG = "RippleUtils";

    public static int getColorForState(ColorStateList colorStateList, int[] iArr) {
        int colorForState =
                colorStateList != null
                        ? colorStateList.getColorForState(iArr, colorStateList.getDefaultColor())
                        : 0;
        return ColorUtils.setAlphaComponent(
                colorForState, Math.min(Color.alpha(colorForState) * 2, 255));
    }
}
