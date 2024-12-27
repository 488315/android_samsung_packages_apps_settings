package com.android.settings.sim;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.HelpTrampoline;
import com.android.settings.R;
import com.android.settings.network.SatelliteRepository;
import com.android.settings.network.SubscriptionUtil;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import kotlin.jvm.internal.Intrinsics;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SimSelectNotification extends BroadcastReceiver {

    @VisibleForTesting
    public static final String ENABLE_MMS_NOTIFICATION_CHANNEL = "enable_mms_notification_channel";

    @VisibleForTesting public static final int ENABLE_MMS_NOTIFICATION_ID = 2;

    @VisibleForTesting
    public static final String SIM_SELECT_NOTIFICATION_CHANNEL = "sim_select_notification_channel";

    @VisibleForTesting public static final int SIM_SELECT_NOTIFICATION_ID = 1;

    @VisibleForTesting
    public static final String SIM_WARNING_NOTIFICATION_CHANNEL =
            "sim_warning_notification_channel";

    @VisibleForTesting public static final int SIM_WARNING_NOTIFICATION_ID = 3;

    public static void onPrimarySubscriptionListChanged(Context context, Intent intent) {
        Log.d("SimSelectNotification", "Checking satellite enabled status");
        try {
            try {
                if (((Boolean)
                                new SatelliteRepository(context)
                                        .requestIsSessionStarted(
                                                Executors.newSingleThreadExecutor())
                                        .get(1000L, TimeUnit.MILLISECONDS))
                        .booleanValue()) {
                    Log.i(
                            "SimSelectNotification",
                            "Device is in a satellite session.g Unable to handle primary"
                                + " subscription list changes");
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                Log.w("SimSelectNotification", "Can't get satellite session status", e);
            }
        } finally {
            Log.i(
                    "SimSelectNotification",
                    "Device is not in a satellite session. Handle primary subscription list"
                        + " changes");
            startSimSelectDialogIfNeeded(context, intent);
            sendSimCombinationWarningIfNeeded(context, intent);
        }
    }

    public static void sendSimCombinationWarningIfNeeded(Context context, Intent intent) {
        int intExtra =
                intent.getIntExtra("android.telephony.extra.SIM_COMBINATION_WARNING_TYPE", 0);
        ((NotificationManager) context.getSystemService(NotificationManager.class)).cancel(3);
        if (intExtra == 1) {
            Resources resources = context.getResources();
            String stringExtra =
                    intent.getStringExtra("android.telephony.extra.SIM_COMBINATION_NAMES");
            if (stringExtra == null) {
                return;
            }
            String string =
                    resources.getString(
                            R.string.dual_cdma_sim_warning_notification_summary, stringExtra);
            NotificationChannel notificationChannel =
                    new NotificationChannel(
                            SIM_WARNING_NOTIFICATION_CHANNEL,
                            resources.getText(
                                    R.string.dual_cdma_sim_warning_notification_channel_title),
                            4);
            Notification.Builder autoCancel =
                    new Notification.Builder(context, SIM_WARNING_NOTIFICATION_CHANNEL)
                            .setSmallIcon(R.drawable.ic_sim_alert)
                            .setColor(context.getColor(R.color.sim_noitification))
                            .setContentTitle(
                                    resources.getText(
                                            R.string.sim_combination_warning_notification_title))
                            .setContentText(string)
                            .setStyle(new Notification.BigTextStyle().bigText(string))
                            .setAutoCancel(true);
            Intent intent2 = new Intent(context, (Class<?>) HelpTrampoline.class);
            intent2.putExtra("android.intent.extra.TEXT", "help_uri_sim_combination_warning");
            autoCancel.setContentIntent(PendingIntent.getActivity(context, 0, intent2, 335544320));
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(3, autoCancel.build());
        }
    }

    public static void startSimSelectDialogIfNeeded(Context context, Intent intent) {
        int intExtra =
                intent.getIntExtra("android.telephony.extra.DEFAULT_SUBSCRIPTION_SELECT_TYPE", 0);
        if (intExtra == 0) {
            return;
        }
        ((NotificationManager) context.getSystemService("notification")).cancel(1);
        if (intExtra == 5) {
            WeakReference weakReference = SimDialogProhibitService.sSimDialogActivity;
            final SimDialogActivity simDialogActivity =
                    weakReference == null ? null : (SimDialogActivity) weakReference.get();
            if (simDialogActivity == null) {
                Log.i("SimDialogProhibitService", "No SimDialogActivity for dismiss.");
                return;
            }
            try {
                simDialogActivity
                        .getMainExecutor()
                        .execute(
                                new Runnable() { // from class:
                                                 // com.android.settings.sim.SimDialogProhibitService$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SimDialogActivity.this.forceClose();
                                    }
                                });
                return;
            } catch (RejectedExecutionException e) {
                Log.w(
                        "SimDialogProhibitService",
                        "Fail to close SimDialogActivity through executor",
                        e);
                Intent intent2 = new Intent(context, (Class<?>) SimDialogActivity.class);
                intent2.addFlags(268435456);
                int i = SimDialogActivity.$r8$clinit;
                intent2.putExtra("dialog_type", 5);
                context.startActivity(intent2);
                return;
            }
        }
        Resources resources = context.getResources();
        NotificationChannel notificationChannel =
                new NotificationChannel(
                        SIM_SELECT_NOTIFICATION_CHANNEL,
                        resources.getText(R.string.sim_selection_channel_title),
                        2);
        Notification.Builder autoCancel =
                new Notification.Builder(context, SIM_SELECT_NOTIFICATION_CHANNEL)
                        .setSmallIcon(R.drawable.ic_sim_alert)
                        .setColor(context.getColor(R.color.sim_noitification))
                        .setContentTitle(resources.getText(R.string.sim_notification_title))
                        .setContentText(resources.getText(R.string.sim_notification_summary))
                        .setAutoCancel(true);
        Intent intent3 = new Intent("android.settings.WIRELESS_SETTINGS");
        intent3.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        intent3.addFlags(268435456);
        autoCancel.setContentIntent(PendingIntent.getActivity(context, 0, intent3, 335544320));
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService("notification");
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1, autoCancel.build());
        if (intExtra == 4) {
            int slotIndex =
                    SubscriptionManager.getSlotIndex(
                            intent.getIntExtra(
                                    "android.telephony.extra.SUBSCRIPTION_ID",
                                    Preference.DEFAULT_ORDER));
            Intent intent4 = new Intent(context, (Class<?>) SimDialogActivity.class);
            intent4.addFlags(268435456);
            int i2 = SimDialogActivity.$r8$clinit;
            intent4.putExtra("dialog_type", 3);
            intent4.putExtra("preferred_sim", slotIndex);
            context.startActivity(intent4);
            return;
        }
        if (intExtra == 1) {
            Intent intent5 = new Intent(context, (Class<?>) SimDialogActivity.class);
            intent5.addFlags(268435456);
            int i3 = SimDialogActivity.$r8$clinit;
            intent5.putExtra("dialog_type", 0);
            context.startActivity(intent5);
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        CharSequence text;
        if (!SubscriptionUtil.isSimHardwareVisible(context)) {
            Log.w("SimSelectNotification", "Received unexpected intent with null action.");
            return;
        }
        String action = intent.getAction();
        if (action == null) {
            Log.w("SimSelectNotification", "Received unexpected intent with null action.");
            return;
        }
        if (action.equals("android.telephony.action.PRIMARY_SUBSCRIPTION_LIST_CHANGED")) {
            int i = PrimarySubscriptionListChangedService.$r8$clinit;
            ComponentName componentName =
                    new ComponentName(
                            context, (Class<?>) PrimarySubscriptionListChangedService.class);
            Object systemService = context.getSystemService((Class<Object>) JobScheduler.class);
            Intrinsics.checkNotNull(systemService);
            JobScheduler jobScheduler = (JobScheduler) systemService;
            JobInfo.Builder builder =
                    new JobInfo.Builder(R.integer.primary_subscription_list_changed, componentName);
            Bundle extras = intent.getExtras();
            if (extras != null) {
                builder.setTransientExtras(extras);
            }
            jobScheduler.schedule(builder.build());
            return;
        }
        if (!action.equals("android.settings.ENABLE_MMS_DATA_REQUEST")) {
            Log.w("SimSelectNotification", "Received unexpected intent " + intent.getAction());
            return;
        }
        int intExtra = intent.getIntExtra("android.provider.extra.SUB_ID", -1);
        if (intExtra == Integer.MAX_VALUE) {
            intExtra = SubscriptionManager.getDefaultSmsSubscriptionId();
        }
        SubscriptionManager subscriptionManager =
                (SubscriptionManager) context.getSystemService("telephony_subscription_service");
        if (!subscriptionManager.isActiveSubscriptionId(intExtra)) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                    intExtra, "onEnableMmsDataRequest invalid sub ID ", "SimSelectNotification");
            return;
        }
        SubscriptionInfo activeSubscriptionInfo =
                subscriptionManager.getActiveSubscriptionInfo(intExtra);
        if (activeSubscriptionInfo == null) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                    intExtra,
                    "onEnableMmsDataRequest null SubscriptionInfo for sub ID ",
                    "SimSelectNotification");
            return;
        }
        int intExtra2 =
                intent.getIntExtra("android.settings.extra.ENABLE_MMS_DATA_REQUEST_REASON", -1);
        if (intExtra2 == 0) {
            text = context.getResources().getText(R.string.enable_receiving_mms_notification_title);
        } else {
            if (intExtra2 != 1) {
                RecordingInputConnection$$ExternalSyntheticOutline0.m(
                        intExtra2,
                        "onEnableMmsDataRequest invalid request reason ",
                        "SimSelectNotification");
                return;
            }
            text = context.getResources().getText(R.string.enable_sending_mms_notification_title);
        }
        if (((TelephonyManager) context.getSystemService("phone"))
                .createForSubscriptionId(intExtra)
                .isDataEnabledForApn(2)) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                    intExtra,
                    "onEnableMmsDataRequest MMS data already enabled on sub ID ",
                    "SimSelectNotification");
            return;
        }
        String string =
                context.getResources()
                        .getString(
                                R.string.enable_mms_notification_summary,
                                SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                        context, activeSubscriptionInfo));
        ((NotificationManager) context.getSystemService("notification")).cancel(2);
        NotificationChannel notificationChannel =
                new NotificationChannel(
                        ENABLE_MMS_NOTIFICATION_CHANNEL,
                        context.getResources()
                                .getText(R.string.enable_mms_notification_channel_title),
                        4);
        Notification.Builder autoCancel =
                new Notification.Builder(context, ENABLE_MMS_NOTIFICATION_CHANNEL)
                        .setSmallIcon(R.drawable.ic_settings_24dp)
                        .setColor(context.getColor(R.color.sim_noitification))
                        .setContentTitle(text)
                        .setContentText(string)
                        .setStyle(new Notification.BigTextStyle().bigText(string))
                        .setAutoCancel(true);
        Intent intent2 = new Intent("android.settings.MMS_MESSAGE_SETTING");
        intent2.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        intent2.putExtra("android.provider.extra.SUB_ID", intExtra);
        autoCancel.setContentIntent(PendingIntent.getActivity(context, 0, intent2, 335544320));
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService("notification");
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(2, autoCancel.build());
    }
}
