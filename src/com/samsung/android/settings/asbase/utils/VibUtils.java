package com.samsung.android.settings.asbase.utils;

import android.content.Context;
import android.os.Vibrator;
import android.os.VibratorManager;

import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.asbase.rune.VibRune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class VibUtils {
    public static boolean hasSystemVibrationMenu(Context context) {
        if (isSupportDcHaptic(context)) {
            return true;
        }
        return hasVibrator(context) && isEnableIntensity(context);
    }

    public static boolean hasVibrator(Context context) {
        Vibrator defaultVibrator =
                ((VibratorManager) context.getSystemService("vibrator_manager"))
                        .getDefaultVibrator();
        return defaultVibrator != null && defaultVibrator.hasVibrator();
    }

    public static boolean isEnableIntensity(Context context) {
        Vibrator defaultVibrator =
                ((VibratorManager) context.getSystemService("vibrator_manager"))
                        .getDefaultVibrator();
        return defaultVibrator != null && defaultVibrator.semGetSupportedVibrationType() > 1;
    }

    public static boolean isSupportDcHaptic(Context context) {
        return VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR
                && hasVibrator(context)
                && !isEnableIntensity(context);
    }

    public static boolean isSupportVibrateWhenRing(Context context) {
        return hasVibrator(context)
                && (Utils.isVoiceCapable(context)
                        || Rune.supportSoftphone()
                        || Utils.isCMCAvailable(context));
    }
}
