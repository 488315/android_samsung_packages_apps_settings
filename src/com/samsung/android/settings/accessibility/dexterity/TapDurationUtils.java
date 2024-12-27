package com.samsung.android.settings.accessibility.dexterity;

import android.content.Context;
import android.provider.Settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class TapDurationUtils {
    public static float getTapDurationValue(Context context) {
        return Settings.Secure.getFloat(
                context.getContentResolver(), "tap_duration_threshold", 0.1f);
    }

    public static boolean isTapDurationEnabled(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "tap_duration_enabled", 0) == 1;
    }
}
