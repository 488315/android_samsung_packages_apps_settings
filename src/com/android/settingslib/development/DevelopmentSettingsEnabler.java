package com.android.settingslib.development;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.settingslib.MinorModeUtils;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.SettingsManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DevelopmentSettingsEnabler {
    /* JADX WARN: Multi-variable type inference failed */
    public static boolean isDevelopmentSettingsEnabled(Context context) {
        byte b =
                Settings.Global.getInt(
                                context.getContentResolver(),
                                "development_settings_enabled",
                                Build.TYPE.equals("eng") ? 1 : 0)
                        != 0;
        if (b != true) {
            return false;
        }
        SettingsManager settingsManager = SettingsManager.getInstance();
        if (((settingsManager != null ? settingsManager.getSettingsHiddenState() : 0) & 256) != 0) {
            return false;
        }
        EnterpriseDeviceManager enterpriseDeviceManager =
                EnterpriseDeviceManager.getInstance(context.getApplicationContext());
        if (enterpriseDeviceManager != null
                && !enterpriseDeviceManager.getRestrictionPolicy().isDeveloperModeAllowed()) {
            return false;
        }
        UserManager userManager = (UserManager) context.getSystemService("user");
        return userManager.isAdminUser()
                && !userManager.hasUserRestriction("no_debugging_features")
                && b == true;
    }

    public static void setDevelopmentSettingsEnabled(Context context, boolean z) {
        if (z) {
            Log.d("SettingsLib/MinorModeUtils", "develop options restriction:");
            if (MinorModeUtils.isCHNMinorModeRestrictionEnabled(
                    context,
                    "get_enable_developer_mode_restrict",
                    "enable_developer_mode_restrict")) {
                Log.d("SettingsLib/MinorModeUtils", "post request start");
                try {
                    if (Settings.Global.getInt(
                                    context.getContentResolver(), "development_settings_enabled")
                            == 0) {
                        Intent intent =
                                new Intent("com.samsung.android.minormode.action.REQUEST_PARENT");
                        intent.setPackage("com.samsung.android.minormode");
                        intent.putExtra("request_type", 11);
                        intent.putExtra(
                                KnoxContainerManager.CONTAINER_CREATION_REQUEST_ID, "setting");
                        context.startActivity(intent);
                    }
                    Log.d("SettingsLib/MinorModeUtils", "post request success");
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("SettingsLib/MinorModeUtils", "post request fail");
                    return;
                }
            }
        }
        Settings.Global.putInt(
                context.getContentResolver(), "development_settings_enabled", z ? 1 : 0);
        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(
                        new Intent(
                                "com.android.settingslib.development.DevelopmentSettingsEnabler.SETTINGS_CHANGED"));
    }
}
