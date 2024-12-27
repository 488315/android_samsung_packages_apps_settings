package com.samsung.android.settings.accessibility;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.provider.Settings;

import androidx.core.app.NotificationCompat$Builder;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import com.sec.ims.IMSParameter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccessibilityNotificationUtil {
    public static void updateTapDurationNotification(Context context) {
        String string =
                context.getString(
                        R.string.tap_duration_notification_title,
                        context.getString(R.string.tap_duration_title));
        ((NotificationManager) context.getSystemService(NotificationManager.class))
                .createNotificationChannelGroup(
                        new NotificationChannelGroup(
                                "com.samsung.settings.accessibility.NOTIFICATION_GROUP",
                                context.getString(R.string.accessibility_settings)));
        NotificationChannel notificationChannel =
                new NotificationChannel("tap_duration", string, 2);
        notificationChannel.setGroup("com.samsung.settings.accessibility.NOTIFICATION_GROUP");
        ((NotificationManager) context.getSystemService(NotificationManager.class))
                .createNotificationChannel(notificationChannel);
        if (Settings.Secure.getInt(context.getContentResolver(), "tap_duration_enabled", 0) != 1) {
            ((NotificationManager) context.getSystemService(NotificationManager.class)).cancel(1);
            return;
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName =
                "com.samsung.android.settings.accessibility.dexterity.TapDurationPreferenceFragment";
        subSettingLauncher.setTitleRes(R.string.tap_duration_title, null);
        launchRequest.mSourceMetricsCategory = 0;
        subSettingLauncher.addFlags(335544320);
        PendingIntent activity =
                PendingIntent.getActivity(context, 0, subSettingLauncher.toIntent(), 335544320);
        NotificationCompat$Builder notificationCompat$Builder =
                new NotificationCompat$Builder(context, "tap_duration");
        notificationCompat$Builder.mNotification.icon = R.drawable.stat_notify_access_control;
        notificationCompat$Builder.mColor =
                context.getColor(R.color.tap_duration_notification_icon_color);
        notificationCompat$Builder.mContentTitle =
                NotificationCompat$Builder.limitCharSequenceLength(string);
        notificationCompat$Builder.mContentText =
                NotificationCompat$Builder.limitCharSequenceLength(
                        context.getText(R.string.tap_duration_notification_description));
        notificationCompat$Builder.mContentIntent = activity;
        notificationCompat$Builder.mNotification.when = System.currentTimeMillis();
        notificationCompat$Builder.mCategory = IMSParameter.CALL.STATUS;
        notificationCompat$Builder.setFlag(2);
        ((NotificationManager) context.getSystemService(NotificationManager.class))
                .notify(1, notificationCompat$Builder.build());
    }
}
