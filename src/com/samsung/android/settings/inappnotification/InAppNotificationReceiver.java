package com.samsung.android.settings.inappnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.R;

import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class InAppNotificationReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Log.i("InAppReceiver", "onReceive intent=" + intent);
        if (!Rune.SUPPORT_FEATURE_INAPP_NOTI_WITH_BUNDLE) {
            Log.i("InAppReceiver", "In app notification doesn't supported.");
            return;
        }
        if (intent == null || intent.getAction() == null) {
            Log.w("InAppReceiver", "intent or action is null");
            return;
        }
        Bundle bundleExtra = intent.getBundleExtra("inapp_noti_bundle");
        if (bundleExtra == null) {
            Log.w("InAppReceiver", "notification bundle is null");
            return;
        }
        if (!"com.samsung.settings.SETTINGS_INAPP_NOTI".equals(intent.getAction())) {
            if ("com.samsung.settings.SETTINGS_INAPP_NOTI_CANCEL".equals(intent.getAction())) {
                Log.d("InAppReceiver", "cancelNotification");
                if (!bundleExtra.containsKey("inapp_noti_id")) {
                    Log.w(
                            "InAppReceiver",
                            "To cancel inapp notification, notification ID must be included.");
                    return;
                } else {
                    ((NotificationManager) context.getSystemService("notification"))
                            .cancel(bundleExtra.getInt("inapp_noti_id"));
                    return;
                }
            }
            return;
        }
        Log.d("InAppReceiver", "createNotification");
        if (!bundleExtra.containsKey("inapp_noti_channel_id")
                || !bundleExtra.containsKey("inapp_noti_id")
                || !bundleExtra.containsKey("inapp_noti_data")) {
            Log.w(
                    "InAppReceiver",
                    "To create inapp notification, notification channel ID, ID, data must be"
                        + " included.");
            return;
        }
        String string = bundleExtra.getString("inapp_noti_channel_id");
        String string2 =
                bundleExtra.getString(
                        "inapp_noti_channel_name", context.getString(R.string.settings_label));
        int i = bundleExtra.getInt("inapp_noti_channel_important", 3);
        boolean z = bundleExtra.getBoolean("inapp_noti_channel_show_badge", true);
        int i2 = bundleExtra.getInt("inapp_noti_id", 0);
        Notification notification = (Notification) bundleExtra.getParcelable("inapp_noti_data");
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService("notification");
        NotificationChannel notificationChannel = new NotificationChannel(string, string2, i);
        notificationChannel.setShowBadge(z);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(i2, notification);
    }
}
