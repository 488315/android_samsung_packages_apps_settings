package com.android.settings.sim;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.TaskStackBuilder;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.network.SubscriptionUtil;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SimNotificationService extends JobService {
    public static void scheduleSimNotification(Context context, int i) {
        JobScheduler jobScheduler =
                (JobScheduler) context.getApplicationContext().getSystemService(JobScheduler.class);
        ComponentName componentName =
                new ComponentName(
                        context.getApplicationContext(), (Class<?>) SimNotificationService.class);
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt("notification_type", i);
        jobScheduler.schedule(
                new JobInfo.Builder(R.integer.sim_notification_send, componentName)
                        .setExtras(persistableBundle)
                        .build());
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        PersistableBundle extras = jobParameters.getExtras();
        if (extras == null) {
            Log.e("SimNotificationService", "Failed to get notification type.");
            return false;
        }
        int i = extras.getInt("notification_type");
        if (i == 1) {
            Log.i("SimNotificationService", "Sending SIM config notification.");
            SimActivationNotifier.setShowSimSettingsNotification(this, false);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(
                    new NotificationChannel(
                            "sim_setup", getString(R.string.sim_setup_channel_id), 4));
            notificationManager.createNotificationChannel(
                    new NotificationChannel(
                            "carrier_switching", getString(R.string.sim_switch_channel_id), 4));
            SubscriptionInfo subscriptionInfo =
                    (SubscriptionInfo)
                            SubscriptionUtil.getActiveSubscriptions(
                                            (SubscriptionManager)
                                                    getSystemService(SubscriptionManager.class))
                                    .stream()
                                    .filter(new SimActivationNotifier$$ExternalSyntheticLambda0())
                                    .findFirst()
                                    .orElse(null);
            if (subscriptionInfo == null) {
                Log.e(
                        "SimActivationNotifier",
                        "No removable subscriptions found. Do not show notification.");
            } else {
                CharSequence uniqueSubscriptionDisplayName =
                        SubscriptionUtil.getUniqueSubscriptionDisplayName(this, subscriptionInfo);
                String string =
                        getString(
                                R.string.post_dsds_reboot_notification_title_with_carrier,
                                TextUtils.isEmpty(uniqueSubscriptionDisplayName)
                                        ? getString(R.string.sim_card_label)
                                        : uniqueSubscriptionDisplayName.toString());
                String string2 = getString(R.string.post_dsds_reboot_notification_text);
                Intent intent =
                        new Intent(this, (Class<?>) Settings.MobileNetworkListActivity.class);
                ArrayList arrayList = new ArrayList();
                arrayList.add(intent);
                if (arrayList.isEmpty()) {
                    throw new IllegalStateException(
                            "No intents added to TaskStackBuilder; cannot getPendingIntent");
                }
                Intent[] intentArr = (Intent[]) arrayList.toArray(new Intent[0]);
                intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
                notificationManager.notify(
                        1,
                        new Notification.Builder(this, "sim_setup")
                                .setContentTitle(string)
                                .setContentText(string2)
                                .setContentIntent(
                                        PendingIntent.getActivities(
                                                this, 0, intentArr, 201326592, null))
                                .setSmallIcon(R.drawable.ic_sim_alert)
                                .setAutoCancel(true)
                                .build());
            }
        } else if (i == 2) {
            NotificationManager notificationManager2 =
                    (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager2.createNotificationChannel(
                    new NotificationChannel(
                            "sim_setup", getString(R.string.sim_setup_channel_id), 4));
            notificationManager2.createNotificationChannel(
                    new NotificationChannel(
                            "carrier_switching", getString(R.string.sim_switch_channel_id), 4));
            CarrierConfigManager carrierConfigManager =
                    (CarrierConfigManager) getSystemService(CarrierConfigManager.class);
            String simOperatorName =
                    ((TelephonyManager) getSystemService(TelephonyManager.class))
                            .getSimOperatorName();
            if (carrierConfigManager != null && carrierConfigManager.getConfig() != null) {
                boolean z =
                        carrierConfigManager.getConfig().getBoolean("carrier_name_override_bool");
                String string3 = carrierConfigManager.getConfig().getString("carrier_name_string");
                if (z || TextUtils.isEmpty(simOperatorName)) {
                    simOperatorName = string3;
                }
            }
            Intent intent2 = new Intent(this, (Class<?>) Settings.MobileNetworkListActivity.class);
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(intent2);
            if (arrayList2.isEmpty()) {
                throw new IllegalStateException(
                        "No intents added to TaskStackBuilder; cannot getPendingIntent");
            }
            Intent[] intentArr2 = (Intent[]) arrayList2.toArray(new Intent[0]);
            intentArr2[0] = new Intent(intentArr2[0]).addFlags(268484608);
            notificationManager2.notify(
                    2,
                    new Notification.Builder(this, "carrier_switching")
                            .setContentTitle(
                                    TextUtils.isEmpty(simOperatorName)
                                            ? getString(
                                                    R.string
                                                            .switch_to_removable_notification_no_carrier_name)
                                            : getString(
                                                    R.string.switch_to_removable_notification,
                                                    simOperatorName))
                            .setContentText(getString(R.string.network_changed_notification_text))
                            .setContentIntent(
                                    PendingIntent.getActivities(
                                            this, 0, intentArr2, 201326592, null))
                            .setSmallIcon(R.drawable.ic_sim_alert)
                            .setColor(
                                    getResources()
                                            .getColor(
                                                    R.color.homepage_generic_icon_background, null))
                            .setAutoCancel(true)
                            .build());
        } else if (i != 3) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Invalid notification type: ", "SimNotificationService");
        } else {
            NotificationManager notificationManager3 =
                    (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager3.createNotificationChannel(
                    new NotificationChannel(
                            "sim_setup", getString(R.string.sim_setup_channel_id), 4));
            notificationManager3.createNotificationChannel(
                    new NotificationChannel(
                            "carrier_switching", getString(R.string.sim_switch_channel_id), 4));
            Intent intent3 = new Intent(this, (Class<?>) Settings.MobileNetworkListActivity.class);
            Intent intent4 = new Intent(this, (Class<?>) DsdsDialogActivity.class);
            TaskStackBuilder taskStackBuilder = new TaskStackBuilder(this);
            taskStackBuilder.addNextIntentWithParentStack(intent3);
            taskStackBuilder.mIntents.add(intent4);
            if (taskStackBuilder.mIntents.isEmpty()) {
                throw new IllegalStateException(
                        "No intents added to TaskStackBuilder; cannot getPendingIntent");
            }
            Intent[] intentArr3 = (Intent[]) taskStackBuilder.mIntents.toArray(new Intent[0]);
            intentArr3[0] = new Intent(intentArr3[0]).addFlags(268484608);
            notificationManager3.notify(
                    1,
                    new Notification.Builder(this, "sim_setup")
                            .setContentTitle(getString(R.string.dsds_notification_after_suw_title))
                            .setContentText(getString(R.string.dsds_notification_after_suw_text))
                            .setContentIntent(
                                    PendingIntent.getActivities(
                                            taskStackBuilder.mSourceContext,
                                            0,
                                            intentArr3,
                                            201326592,
                                            null))
                            .setSmallIcon(R.drawable.ic_sim_alert)
                            .setAutoCancel(true)
                            .build());
        }
        return false;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
