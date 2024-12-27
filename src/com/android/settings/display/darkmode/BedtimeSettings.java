package com.android.settings.display.darkmode;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.FeatureFlagUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BedtimeSettings {
    public final Context mContext;
    public final PackageManager mPackageManager;
    public final String mWellbeingPackage;

    public BedtimeSettings(Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mWellbeingPackage = context.getResources().getString(R.string.config_systemWellbeing);
    }

    public final Intent getBedtimeSettingsIntent() {
        Intent intent;
        ResolveInfo resolveActivity;
        if (FeatureFlagUtils.isEnabled(
                        this.mContext, "settings_app_allow_dark_theme_activation_at_bedtime")
                && (resolveActivity =
                                this.mPackageManager.resolveActivity(
                                        (intent =
                                                new Intent("android.settings.BEDTIME_SETTINGS")
                                                        .setPackage(this.mWellbeingPackage)),
                                        65536))
                        != null
                && resolveActivity.activityInfo.isEnabled()) {
            return intent;
        }
        return null;
    }
}
