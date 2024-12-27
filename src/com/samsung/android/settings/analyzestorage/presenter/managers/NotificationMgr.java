package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;

import com.android.settings.R;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.utils.preference.PreferenceUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NotificationMgr {
    public static volatile NotificationMgr sInstance;
    public Notification mNotification;
    public final NotificationManager mNotificationManager;

    public NotificationMgr(Context context) {
        this.mNotificationManager = (NotificationManager) context.getSystemService("notification");
    }

    public static NotificationMgr getInstance(Context context) {
        if (sInstance == null) {
            synchronized (NotificationMgr.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new NotificationMgr(context.getApplicationContext());
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public final void createStorageNotification(Context context, String str) {
        Notification.Builder builder = new Notification.Builder(context, "StorageManager");
        Icon.createWithResource(context, R.drawable.as_optimize_storage_notification_big_icon);
        builder.setSmallIcon(R.drawable.as_notification_ic_folder);
        builder.setVisibility(1);
        builder.setShowWhen(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setColor(context.getColor(R.color.as_app_notification_icon_color));
        builder.setOngoing(true);
        Intent intent = new Intent("com.sec.android.app.myfiles.RUN_STORAGE_ANALYSIS");
        intent.addFlags(268435456);
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 335544320);
        builder.addAction(
                new Notification.Action.Builder(
                                (Icon) null,
                                context.getString(R.string.as_device_storage_notification_button),
                                activity)
                        .build());
        builder.setContentTitle(
                context.getString(R.string.as_device_storage_low_notification_title));
        builder.setContentText(str);
        builder.setCategory("CATEGORY_STATUS");
        builder.setStyle(new Notification.BigTextStyle().bigText(str));
        builder.setContentIntent(activity);
        builder.setAutoCancel(true);
        this.mNotification = builder.build();
        NotificationChannel notificationChannel =
                new NotificationChannel(
                        "StorageManager", context.getString(R.string.as_app_title), 3);
        notificationChannel.setSound(null, Notification.AUDIO_ATTRIBUTES_DEFAULT);
        this.mNotificationManager.createNotificationChannel(notificationChannel);
        this.mNotificationManager.notify("StorageManager", 102, this.mNotification);
    }

    public final void needOnGoingNotification(Context context) {
        long storageTotalSize = StorageUsageManager.getStorageTotalSize(0);
        long storageFreeSpace = StorageUsageManager.getStorageFreeSpace(0);
        Log.d(
                "NotificationMgr",
                "needOnGoingNotification() ] totalSize (GiB) = "
                        + (storageTotalSize / 1073741824)
                        + " , freeSpace (GB) = "
                        + (storageFreeSpace / 1073741824));
        if (storageFreeSpace > 1073741824) {
            PreferenceUtils.setExtremelyFullCondition(context, false);
            updateChannelByID("StorageManager", context.getString(R.string.as_app_title));
            return;
        }
        Log.d(
                "NotificationMgr",
                "needOnGoingNotification() ] Free space is "
                        + (storageFreeSpace / 1048576)
                        + " MiB. Storage Low Notification can show up.");
        Intent intent = new Intent("com.sec.android.app.myfiles.INTERNAL_STORAGE_LOW");
        intent.setComponent(
                new ComponentName(
                        context.getPackageName(),
                        "com.samsung.android.settings.analyzestorage.external.receiver.StorageMonitorReceiver"));
        context.sendBroadcast(intent);
        Log.d("NotificationMgr", "triggerInternalStorageLowIntent() ] Request Done.");
        PreferenceUtils.setExtremelyFullCondition(context, true);
    }

    public final void updateChannelByID(String str, CharSequence charSequence) {
        if (this.mNotificationManager.getNotificationChannel(str) != null) {
            NotificationChannel notificationChannel = new NotificationChannel(str, charSequence, 3);
            notificationChannel.setShowBadge(false);
            notificationChannel.setSound(null, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            this.mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
