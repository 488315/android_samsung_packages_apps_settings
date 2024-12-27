package com.samsung.android.settings.wifi.advanced;

import android.content.Context;
import android.provider.Settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class OpenRoamingSettings {
    public final Context mContext;

    public OpenRoamingSettings(Context context) {
        this.mContext = context;
    }

    public final boolean isUserAllowedOAuthAgreement() {
        String string =
                Settings.Global.getString(
                        this.mContext.getContentResolver(), "sem_wifi_allowed_oauth_provider");
        return string != null && string.contains("[cisco]");
    }
}
