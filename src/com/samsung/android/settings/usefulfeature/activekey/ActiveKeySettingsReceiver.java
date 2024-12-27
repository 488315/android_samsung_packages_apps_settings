package com.samsung.android.settings.usefulfeature.activekey;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.android.settings.R;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ActiveKeySettingsReceiver extends BroadcastReceiver {
    public final int activeKeyID = R.drawable.stat_notify_activity_zone;

    public final void makeActiveKeyNotification(Context context) {
        int i;
        SharedPreferences defaultSharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        try {
            i =
                    context.getPackageManager()
                            .getPackageUid(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, 128);
        } catch (PackageManager.NameNotFoundException unused) {
            i = -1;
        }
        int userId = i > 0 ? UserHandle.getUserId(i) : UserHandle.myUserId();
        Log.d(
                "ActiveKeySettingsReceiver",
                "packageUid : " + i + " userId : " + userId + UserHandle.myUserId());
        if (!UsefulfeatureUtils.hasActiveKey()
                || defaultSharedPreferences.getBoolean("pref_active_key_noti", false)) {
            return;
        }
        if ((((SemFloatingFeature.getInstance()
                                                .getBoolean(
                                                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE")
                                        || SemFloatingFeature.getInstance()
                                                .getBoolean(
                                                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_ULTRA_POWER_SAVING")
                                        || SemFloatingFeature.getInstance()
                                                .getBoolean(
                                                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_BATTERY_CONVERSING"))
                                && SemEmergencyManager.getInstance(context) != null)
                        ? SemEmergencyManager.isEmergencyMode(context)
                        : false)
                || SemDualAppManager.isDualAppId(userId)
                || SemPersonaManager.isSecureFolderId(userId)) {
            return;
        }
        Intent intent = new Intent();
        UsefulfeatureUtils.hasActiveKey();
        intent.setAction("com.samsung.ACTIVE_KEY_SETTINGS");
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 201326592);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService("notification");
        NotificationChannel notificationChannel =
                notificationManager.getNotificationChannel("activekey_notification_channel");
        UsefulfeatureUtils.hasActiveKey();
        UsefulfeatureUtils.hasActiveKey();
        if (notificationChannel == null) {
            NotificationChannel notificationChannel2 =
                    new NotificationChannel(
                            "activekey_notification_channel",
                            context.getResources().getString(R.string.active_key_title),
                            2);
            notificationChannel2.setShowBadge(false);
            notificationManager.createNotificationChannel(notificationChannel2);
        } else if (notificationChannel.canShowBadge()) {
            notificationChannel.setShowBadge(false);
            notificationManager.updateNotificationChannel(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, i, notificationChannel);
        }
        Notification.Builder builder =
                new Notification.Builder(context, "activekey_notification_channel");
        builder.setSmallIcon(this.activeKeyID)
                .setStyle(
                        new Notification.BigTextStyle()
                                .bigText(
                                        context.getResources()
                                                .getString(R.string.active_key_notification)))
                .setContentTitle(context.getResources().getString(R.string.active_key_title))
                .setContentText(context.getResources().getString(R.string.active_key_notification))
                .setWhen(0L)
                .setContentIntent(activity)
                .setChannelId("activekey_notification_channel")
                .setAutoCancel(true);
        notificationManager.notify(this.activeKeyID, builder.build());
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x005f, code lost:

       if (android.provider.Settings.System.getInt(r10.getContentResolver(), "dedicated_app_xcover_switch", 0) != 1) goto L11;
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0106, code lost:

       if (android.provider.Settings.System.getInt(r10.getContentResolver(), "dedicated_app_xcover_switch", 0) != 1) goto L11;
    */
    /* JADX WARN: Removed duplicated region for block: B:12:0x010f  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onReceive(android.content.Context r10, android.content.Intent r11) {
        /*
            Method dump skipped, instructions count: 419
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.usefulfeature.activekey.ActiveKeySettingsReceiver.onReceive(android.content.Context,"
                    + " android.content.Intent):void");
    }
}
