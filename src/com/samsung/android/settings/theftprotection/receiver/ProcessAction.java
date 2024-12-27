package com.samsung.android.settings.theftprotection.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.settings.theftprotection.TheftProtectionUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface ProcessAction {
    static void cancelNotifications(Context context) {
        ((NotificationManager) context.getSystemService(NotificationManager.class))
                .deleteNotificationChannel("mandatory_biometrics_channel");
    }

    static PendingIntent getIntentForSecurityDelayExpiration(Context context) {
        Intent intent = new Intent("com.samsung.android.settings.action.TIME_DELAY_EXPIRATION");
        intent.setPackage(context.getPackageName());
        return PendingIntent.getBroadcast(context, 0, intent, 201326592);
    }

    static PendingIntent getIntentForSecurityGraceExpiration(Context context) {
        Intent intent = new Intent("com.samsung.android.settings.action.GRACE_TIME_EXPIRATION");
        intent.setPackage(context.getPackageName());
        return PendingIntent.getBroadcast(context, 0, intent, 201326592);
    }

    static boolean isSecurityDelayActive(Context context) {
        return Settings.Secure.getLong(
                        context.getContentResolver(), "mandatory_biometrics_delay_time", 0L)
                > 0;
    }

    static boolean isSecurityGraceActive(Context context) {
        return Settings.Secure.getLong(
                        context.getContentResolver(), "mandatory_biometrics_grace_time", 0L)
                > 0;
    }

    static void notifyTimeDelayExpired(Context context, boolean z) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("security_delay_notifications_pref", 0);
        int i =
                z
                        ? sharedPreferences.getInt("pp_noti_delay_end_immediately_res", -1)
                        : sharedPreferences.getInt("pp_noti_delay_end_res", -1);
        String string = sharedPreferences.getString("pp_package_name", null);
        Notification.Builder autoCancel =
                new Notification.Builder(context, "mandatory_biometrics_channel")
                        .setContentTitle(
                                context.getText(
                                        R.string.protection_timer_expiration_notification_title))
                        .setContentText(
                                (string == null || i < 0)
                                        ? context.getString(
                                                R.string.protection_timer_notification_message)
                                        : TheftProtectionUtils.getStringFromPackage(
                                                context, i, string))
                        .setSmallIcon(R.drawable.ic_settings_security)
                        .setAutoCancel(true);
        String string2 = sharedPreferences.getString("pp_action_name", null);
        if (string2 != null) {
            Intent intent = new Intent(string2);
            intent.setFlags(268468224);
            autoCancel.setContentIntent(PendingIntent.getActivity(context, 0, intent, 201326592));
        }
        NotificationChannel notificationChannel =
                new NotificationChannel("mandatory_biometrics_channel", "mandatory_biometrics", 4);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(2498, autoCancel.build());
        sharedPreferences.edit().clear().apply();
    }

    static void notifyTimeDelayStart(Context context, long j) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("security_delay_notifications_pref", 0);
        int i = sharedPreferences.getInt("pp_noti_in_progress_res", -1);
        String string = sharedPreferences.getString("pp_package_name", null);
        String string2 =
                (string == null || i < 0)
                        ? context.getString(R.string.protection_timer_notification_message)
                        : TheftProtectionUtils.getStringFromPackage(context, i, string);
        NotificationChannel notificationChannel =
                new NotificationChannel("mandatory_biometrics_channel", "mandatory_biometrics", 4);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);
        long currentTimeMillis = System.currentTimeMillis() + j;
        Intent intent = new Intent("com.samsung.android.settings.action.TIME_DELAY_SERVICE");
        intent.putExtra("delay_running", true);
        intent.setFlags(268468224);
        notificationManager.notify(
                2497,
                new Notification.Builder(context, "mandatory_biometrics_channel")
                        .setContentTitle(context.getString(R.string.protection_timer_title))
                        .setContentText(string2)
                        .setSmallIcon(R.drawable.ic_settings_security)
                        .setContentIntent(PendingIntent.getActivity(context, 0, intent, 201326592))
                        .setUsesChronometer(true)
                        .setChronometerCountDown(true)
                        .setWhen(currentTimeMillis)
                        .setTimeoutAfter(j)
                        .setOngoing(true)
                        .setAutoCancel(false)
                        .build());
    }

    void handle(Context context, Intent intent);
}
