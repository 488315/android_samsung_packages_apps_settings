package com.samsung.android.settings.eternal;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.eternal.utils.KnoxRestrictionsFileLog;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.LockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class KnoxRestrictionsReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String[] split;
        String str;
        StringBuilder sb = new StringBuilder("onReceive() action : ");
        sb.append(intent != null ? intent.getAction() : " null");
        KnoxRestrictionsFileLog.d("Eternal/KnoxRestrictionsReceiver", sb.toString());
        String action = intent.getAction();
        if (EnterpriseDeviceManager.ACTION_KNOX_RESTRICTIONS_CHANGED.equals(action)) {
            KnoxUtils.updateRestrictionState(context);
            KnoxRestrictionsFileLog.d(
                    "Eternal/KnoxRestrictionsReceiver",
                    "updateRestrictionState: " + KnoxUtils.appRestrictionState);
            intent.setComponent(
                    new ComponentName(context, KnoxRestrictionsService.class.getCanonicalName()));
            context.startService(intent);
            return;
        }
        if ("com.samsung.android.knox.intent.action.KNOX_FACE_WIDGET_OFF_INTERNAL".equals(action)) {
            String str2 = LockUtils.CONFIG_LOCK_SCREEN_CLOCK_DISPLAY_POLICY;
            if (context == null) {
                split = null;
            } else {
                String string =
                        Settings.System.getString(
                                context.getContentResolver(), "face_widget_order");
                if (string == null || ApnSettings.MVNO_NONE.equals(string)) {
                    Settings.System.putString(
                            context.getContentResolver(),
                            "face_widget_order",
                            "servicebox_music;servicebox_calendar;servicebox_alarm");
                }
                split =
                        Settings.System.getString(context.getContentResolver(), "face_widget_order")
                                .split(";");
            }
            if (split != null) {
                for (String str3 : split) {
                    ContentResolver contentResolver = context.getContentResolver();
                    str3.getClass();
                    switch (str3) {
                        case "servicebox_alarm":
                            str = "add_info_alarm";
                            break;
                        case "servicebox_music":
                            str = "add_info_music_control";
                            break;
                        case "servicebox_calendar":
                            str = "add_info_today_schedule";
                            break;
                        default:
                            if (str3.contains("#")) {
                                str = "add_info_" + str3.replace(".", "_");
                                break;
                            } else {
                                str = "add_info_" + str3.replace(".", "_");
                                break;
                            }
                    }
                    Settings.System.putInt(contentResolver, str, 0);
                }
            }
        }
    }
}
