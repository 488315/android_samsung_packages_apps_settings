package com.android.settings.sim;

import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SimActivationNotifier {
    public static void setShowSimSettingsNotification(Context context, boolean z) {
        context.getSharedPreferences("sim_prefs", 0)
                .edit()
                .putBoolean("show_sim_settings_notification", z)
                .apply();
    }
}
