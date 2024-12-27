package com.samsung.android.settings.asbase.rune;

import android.os.Build;

import com.samsung.android.audio.Rune;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class VibRune {
    public static final boolean SUPPORT_CHARGING_SYSTEM_VIBRATION;
    public static final boolean SUPPORT_CLASSIFIED_SYSTEM_VIBRATION;
    public static final boolean SUPPORT_DEFAULT_CATEGORIZE;
    public static final boolean SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR =
            SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_DC_MOTOR_HAPTIC_FEEDBACK");
    public static final boolean SUPPORT_SEC_VIBRATION_PICKER;
    public static final boolean SUPPORT_SYNC_WITH_HAPTIC;

    static {
        int i = Build.VERSION.SEM_PLATFORM_INT;
        SUPPORT_CLASSIFIED_SYSTEM_VIBRATION = i >= 110500;
        SUPPORT_SEC_VIBRATION_PICKER = i >= 120100;
        SUPPORT_CHARGING_SYSTEM_VIBRATION = i >= 120100;
        SUPPORT_DEFAULT_CATEGORIZE = i >= 130000;
        SUPPORT_SYNC_WITH_HAPTIC = Rune.SEC_AUDIO_SUPPORT_ACH_RINGTONE;
    }
}
