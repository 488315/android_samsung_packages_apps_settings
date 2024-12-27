package com.android.settingslib.wifi;

import android.content.Context;
import android.os.UserManager;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class WifiEnterpriseRestrictionUtils {
    @VisibleForTesting
    public static boolean hasUserRestrictionFromT(Context context, String str) {
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        if (userManager == null) {
            return false;
        }
        return userManager.hasUserRestriction(str);
    }

    public static boolean isAddWifiConfigAllowed(Context context) {
        if (!hasUserRestrictionFromT(context, "no_add_wifi_config")) {
            return true;
        }
        Log.w("WifiEntResUtils", "Wi-Fi Add network isn't available due to user restriction.");
        return false;
    }

    public static boolean isChangeWifiStateAllowed(Context context) {
        if (!hasUserRestrictionFromT(context, "no_change_wifi_state")) {
            return true;
        }
        Log.w("WifiEntResUtils", "WI-FI state isn't allowed to change due to user restriction.");
        return false;
    }
}
