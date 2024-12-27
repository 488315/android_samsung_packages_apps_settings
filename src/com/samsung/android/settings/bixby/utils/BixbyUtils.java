package com.samsung.android.settings.bixby.utils;

import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BixbyUtils {
    public static Bundle buildActionResult(String str) {
        Log.d("BixbyUtils", "buildActionResult(), result : " + str);
        Bundle bundle = new Bundle();
        bundle.putString("result", str);
        return bundle;
    }

    public static Bundle getDeXDisplay(Context context) {
        return ActivityOptions.makeBasic().toBundle();
    }

    public static boolean isLargeSubDisplayScreen() {
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY");
        return string != null && string.contains("LARGESCREEN");
    }

    public static void setPendingIntentAfterUnlock(Context context, Intent intent) {
        Intent intent2 = new Intent();
        intent2.putExtra("showCoverToast", true);
        intent2.putExtra("ignoreKeyguardState", true);
        ((KeyguardManager) context.getSystemService("keyguard"))
                .semSetPendingIntentAfterUnlock(
                        PendingIntent.getActivity(context, 0, intent, 201326592), intent2);
    }

    public static void startSettingsMain(Context context) {
        Intent intent = new Intent();
        intent.addFlags(268468224);
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings");
        context.startActivity(intent);
    }
}
