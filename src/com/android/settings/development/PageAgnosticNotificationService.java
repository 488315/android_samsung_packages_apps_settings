package com.android.settings.development;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PageAgnosticNotificationService extends Service {
    public NotificationManager mNotificationManager;

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        NotificationChannel notificationChannel =
                new NotificationChannel(
                        "com.android.settings.development.PageAgnosticNotificationService",
                        getString(R.string.page_agnostic_notification_channel_name),
                        4);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NotificationManager.class);
        this.mNotificationManager = notificationManager;
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel);
        }
        if (Settings.Global.getInt(
                        getApplicationContext().getContentResolver(),
                        "ota_disable_automatic_update",
                        0)
                == 0) {
            Settings.Global.putInt(
                    getApplicationContext().getContentResolver(),
                    "ota_disable_automatic_update",
                    1);
        }
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        boolean isUsing16kbPages = Enable16kUtils.isUsing16kbPages();
        String string =
                isUsing16kbPages
                        ? getString(R.string.page_agnostic_16k_pages_title)
                        : getString(R.string.page_agnostic_4k_pages_title);
        String string2 =
                isUsing16kbPages
                        ? getString(R.string.page_agnostic_16k_pages_text_short)
                        : getString(R.string.page_agnostic_4k_pages_text_short);
        Intent intent2 = new Intent(this, (Class<?>) PageAgnosticWarningActivity.class);
        intent2.setFlags(268468224);
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent2, 201326592);
        Intent intent3 = new Intent(this, (Class<?>) Enable16KBootReceiver.class);
        intent3.setAction("com.android.settings.development.NOTIFICATION_DISMISSED");
        Notification build =
                new Notification.Builder(
                                this,
                                "com.android.settings.development.PageAgnosticNotificationService")
                        .setContentTitle(string)
                        .setContentText(string2)
                        .setOngoing(true)
                        .setSmallIcon(R.drawable.ic_settings_24dp)
                        .setStyle(new Notification.BigTextStyle().bigText(string2))
                        .setContentIntent(activity)
                        .setDeleteIntent(
                                PendingIntent.getBroadcast(
                                        getApplicationContext(), 0, intent3, 201326592))
                        .addAction(
                                new Notification.Action.Builder(
                                                R.drawable.empty_icon,
                                                getString(
                                                        R.string.page_agnostic_notification_action),
                                                activity)
                                        .build())
                        .build();
        NotificationManager notificationManager = this.mNotificationManager;
        if (notificationManager == null) {
            return 2;
        }
        notificationManager.notify(1, build);
        return 2;
    }
}
