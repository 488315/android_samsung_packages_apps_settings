package com.samsung.android.settings.notification;

import android.content.Context;
import android.provider.Settings;
import android.util.secutil.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class NotiUtils {
    public static final String[] mSIM2Channels = {
        "com.samsung.android.messaging:CHANNEL_ID_SMS_MMS1"
    };

    public static int getBadgeAppIconType(Context context) {
        int i = Settings.Secure.getInt(context.getContentResolver(), "badge_app_icon_type", 1);
        Log.secD("NotiUtils", "getBadgeAppIconType : " + i);
        if (i == 0) {
            return 0;
        }
        return i == 1 ? 1 : 2;
    }

    public static void setBadgeAppIconType(Context context, int i) {
        Log.secD("NotiUtils", "setBadgeAppIconType : " + i);
        if (i == 1) {
            Settings.Secure.putInt(context.getContentResolver(), "badge_app_icon_type", 1);
        } else if (i == 0) {
            Settings.Secure.putInt(context.getContentResolver(), "badge_app_icon_type", 0);
        } else {
            Settings.Secure.putInt(context.getContentResolver(), "badge_app_icon_type", 2);
        }
    }
}
