package com.samsung.android.settings.asbase.utils;

import android.view.HapticFeedbackConstants;
import android.widget.SeekBar;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SeekBarUtil {
    public static final void vibrateIfNeeded(SeekBar seekBar, int i, int i2) {
        Intrinsics.checkNotNullParameter(seekBar, "<this>");
        if (i == 2) {
            if (i2 != seekBar.getMax()) {
                return;
            }
        } else if (i2 != seekBar.getMin() && i2 != seekBar.getMax()) {
            return;
        }
        seekBar.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
    }
}
