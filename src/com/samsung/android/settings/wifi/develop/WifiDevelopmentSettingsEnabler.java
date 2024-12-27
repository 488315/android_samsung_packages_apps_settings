package com.samsung.android.settings.wifi.develop;

import android.content.Context;
import android.provider.Settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiDevelopmentSettingsEnabler {
    public static boolean isWifiDevelopmentSettingsEnabled(Context context) {
        return context != null
                && Settings.Global.getInt(
                                context.getContentResolver(),
                                "sem_wifi_developer_option_visible",
                                0)
                        == 1;
    }
}
