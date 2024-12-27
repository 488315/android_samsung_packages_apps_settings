package com.android.settings.notification;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BubbleHelper {
    public static boolean isSupportedByDevice(Context context) {
        return !((ActivityManager) context.getSystemService(ActivityManager.class)).isLowRamDevice()
                && Resources.getSystem()
                        .getBoolean(R.bool.config_switch_phone_on_voice_reg_state_change);
    }
}
