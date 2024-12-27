package com.samsung.android.settings.accessibility.dexterity;

import android.content.Context;
import android.hardware.input.InputSettings;
import android.provider.Settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BounceKeysUtils {
    public static void setBounceKeysEnable(Context context, boolean z) {
        InputSettings.setAccessibilityBounceKeysThreshold(
                context,
                z
                        ? (int)
                                (Math.min(
                                                Settings.Secure.getFloat(
                                                        context.getContentResolver(),
                                                        "bounce_keys_period",
                                                        0.5f),
                                                5.0f)
                                        * 1000.0f)
                        : 0);
    }
}
