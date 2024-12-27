package com.samsung.android.settings.uwb;

import android.content.Context;
import android.os.UserManager;
import android.provider.Settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class UwbSettingPolicy {
    public boolean isRegulationMode;
    public Context mContext;

    public final boolean isRestrictionMode() {
        String string;
        return Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0)
                        == 1
                || ((UserManager) this.mContext.getSystemService(UserManager.class))
                        .getUserRestrictions()
                        .getBoolean("no_ultra_wideband_radio")
                || (((string =
                                                Settings.Global.getString(
                                                        this.mContext.getContentResolver(),
                                                        "satellite_mode_radios"))
                                        == null
                                || string.contains("uwb"))
                        && Settings.Global.getInt(
                                        this.mContext.getContentResolver(),
                                        "satellite_mode_enabled",
                                        0)
                                == 1);
    }
}
