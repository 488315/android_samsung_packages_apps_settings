package com.google.android.gms.common;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationCompat$BigTextStyle;
import androidx.core.app.NotificationCompat$Builder;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;

import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zad;
import com.google.android.gms.common.internal.zaf;
import com.google.android.gms.common.internal.zag;
import com.google.android.gms.common.util.DeviceProperties;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class GoogleApiAvailability extends GoogleApiAvailabilityLight {
    public static final Object zaa = new Object();
    public static final GoogleApiAvailability zab = new GoogleApiAvailability();

    public static Dialog zaa(
            Context context, int i, zag zagVar, DialogInterface.OnCancelListener onCancelListener) {
        if (i == 0) {
            return null;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.alertDialogTheme, typedValue, true);
        AlertDialog.Builder builder =
                "Theme.Dialog.Alert"
                                .equals(
                                        context.getResources()
                                                .getResourceEntryName(typedValue.resourceId))
                        ? new AlertDialog.Builder(context, 5)
                        : null;
        if (builder == null) {
            builder = new AlertDialog.Builder(context);
        }
        builder.setMessage(com.google.android.gms.common.internal.zac.zad(context, i));
        if (onCancelListener != null) {
            builder.setOnCancelListener(onCancelListener);
        }
        Resources resources = context.getResources();
        String string =
                i != 1
                        ? i != 2
                                ? i != 3
                                        ? resources.getString(R.string.ok)
                                        : resources.getString(
                                                com.android.settings.R.string
                                                        .common_google_play_services_enable_button)
                                : resources.getString(
                                        com.android.settings.R.string
                                                .common_google_play_services_update_button)
                        : resources.getString(
                                com.android.settings.R.string
                                        .common_google_play_services_install_button);
        if (string != null) {
            builder.setPositiveButton(string, zagVar);
        }
        String zag = com.google.android.gms.common.internal.zac.zag(context, i);
        if (zag != null) {
            builder.setTitle(zag);
        }
        Log.w(
                "GoogleApiAvailability",
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        i,
                        "Creating dialog for Google Play services availability issue."
                            + " ConnectionResult="),
                new IllegalArgumentException());
        return builder.create();
    }

    public static void zad(
            Activity activity,
            Dialog dialog,
            String str,
            DialogInterface.OnCancelListener onCancelListener) {
        try {
            if (activity instanceof FragmentActivity) {
                FragmentManagerImpl supportFragmentManager =
                        ((FragmentActivity) activity).getSupportFragmentManager();
                SupportErrorDialogFragment supportErrorDialogFragment =
                        new SupportErrorDialogFragment();
                Preconditions.checkNotNull(dialog, "Cannot display null dialog");
                dialog.setOnCancelListener(null);
                dialog.setOnDismissListener(null);
                supportErrorDialogFragment.zaa = dialog;
                if (onCancelListener != null) {
                    supportErrorDialogFragment.zab = onCancelListener;
                }
                supportErrorDialogFragment.show(supportFragmentManager, str);
                return;
            }
        } catch (NoClassDefFoundError unused) {
        }
        FragmentManager fragmentManager = activity.getFragmentManager();
        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
        Preconditions.checkNotNull(dialog, "Cannot display null dialog");
        dialog.setOnCancelListener(null);
        dialog.setOnDismissListener(null);
        errorDialogFragment.zaa = dialog;
        if (onCancelListener != null) {
            errorDialogFragment.zab = onCancelListener;
        }
        errorDialogFragment.show(fragmentManager, str);
    }

    public final void showErrorDialogFragment(
            Activity activity, int i, DialogInterface.OnCancelListener onCancelListener) {
        Dialog zaa2 =
                zaa(
                        activity,
                        i,
                        new zad(activity, super.getErrorResolutionIntent(activity, i, "d")),
                        onCancelListener);
        if (zaa2 == null) {
            return;
        }
        zad(activity, zaa2, "GooglePlayServicesErrorDialog", onCancelListener);
    }

    public final void zae(Context context, int i, PendingIntent pendingIntent) {
        int i2;
        Log.w(
                "GoogleApiAvailability",
                LazyListMeasuredItem$$ExternalSyntheticOutline0.m(
                        i, "GMS core API Availability. ConnectionResult=", ", tag=null"),
                new IllegalArgumentException());
        if (i == 18) {
            new zac(this, context).sendEmptyMessageDelayed(1, 120000L);
            return;
        }
        if (pendingIntent == null) {
            if (i == 6) {
                Log.w(
                        "GoogleApiAvailability",
                        "Missing resolution for ConnectionResult.RESOLUTION_REQUIRED. Call"
                            + " GoogleApiAvailability#showErrorNotification(Context,"
                            + " ConnectionResult) instead.");
                return;
            }
            return;
        }
        String zai =
                i == 6
                        ? com.google.android.gms.common.internal.zac.zai(
                                context, "common_google_play_services_resolution_required_title")
                        : com.google.android.gms.common.internal.zac.zag(context, i);
        if (zai == null) {
            zai =
                    context.getResources()
                            .getString(
                                    com.android.settings.R.string
                                            .common_google_play_services_notification_ticker);
        }
        String zah =
                (i == 6 || i == 19)
                        ? com.google.android.gms.common.internal.zac.zah(
                                context,
                                "common_google_play_services_resolution_required_text",
                                com.google.android.gms.common.internal.zac.zaa(context))
                        : com.google.android.gms.common.internal.zac.zad(context, i);
        Resources resources = context.getResources();
        Object systemService = context.getSystemService("notification");
        Preconditions.checkNotNull(systemService);
        NotificationManager notificationManager = (NotificationManager) systemService;
        NotificationCompat$Builder notificationCompat$Builder =
                new NotificationCompat$Builder(context, null);
        notificationCompat$Builder.mLocalOnly = true;
        notificationCompat$Builder.setFlag(16);
        notificationCompat$Builder.mContentTitle =
                NotificationCompat$Builder.limitCharSequenceLength(zai);
        NotificationCompat$BigTextStyle notificationCompat$BigTextStyle =
                new NotificationCompat$BigTextStyle();
        notificationCompat$BigTextStyle.mBigText =
                NotificationCompat$Builder.limitCharSequenceLength(zah);
        notificationCompat$Builder.setStyle(notificationCompat$BigTextStyle);
        PackageManager packageManager = context.getPackageManager();
        if (DeviceProperties.zzd == null) {
            DeviceProperties.zzd =
                    Boolean.valueOf(packageManager.hasSystemFeature("android.hardware.type.watch"));
        }
        if (DeviceProperties.zzd.booleanValue()) {
            notificationCompat$Builder.mNotification.icon = context.getApplicationInfo().icon;
            notificationCompat$Builder.mPriority = 2;
            PackageManager packageManager2 = context.getPackageManager();
            if (DeviceProperties.zzd == null) {
                DeviceProperties.zzd =
                        Boolean.valueOf(
                                packageManager2.hasSystemFeature("android.hardware.type.watch"));
            }
            if (DeviceProperties.zzd.booleanValue()) {
                if (DeviceProperties.zze == null) {
                    DeviceProperties.zze =
                            Boolean.valueOf(
                                    context.getPackageManager().hasSystemFeature("cn.google"));
                }
                DeviceProperties.zze.getClass();
            }
            notificationCompat$Builder.mContentIntent = pendingIntent;
        } else {
            notificationCompat$Builder.mNotification.icon = R.drawable.stat_sys_warning;
            notificationCompat$Builder.mNotification.tickerText =
                    NotificationCompat$Builder.limitCharSequenceLength(
                            resources.getString(
                                    com.android.settings.R.string
                                            .common_google_play_services_notification_ticker));
            notificationCompat$Builder.mNotification.when = System.currentTimeMillis();
            notificationCompat$Builder.mContentIntent = pendingIntent;
            notificationCompat$Builder.mContentText =
                    NotificationCompat$Builder.limitCharSequenceLength(zah);
        }
        synchronized (zaa) {
        }
        NotificationChannel notificationChannel =
                notificationManager.getNotificationChannel("com.google.android.gms.availability");
        String string =
                context.getResources()
                        .getString(
                                com.android.settings.R.string
                                        .common_google_play_services_notification_channel_name);
        if (notificationChannel == null) {
            notificationManager.createNotificationChannel(
                    new NotificationChannel("com.google.android.gms.availability", string, 4));
        } else if (!string.contentEquals(notificationChannel.getName())) {
            notificationChannel.setName(string);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationCompat$Builder.mChannelId = "com.google.android.gms.availability";
        Notification build = notificationCompat$Builder.build();
        if (i == 1 || i == 2 || i == 3) {
            GooglePlayServicesUtilLight.sCanceledAvailabilityNotification.set(false);
            i2 = 10436;
        } else {
            i2 = 39789;
        }
        notificationManager.notify(i2, build);
    }

    public final void zag(
            Activity activity,
            LifecycleFragment lifecycleFragment,
            int i,
            DialogInterface.OnCancelListener onCancelListener) {
        Dialog zaa2 =
                zaa(
                        activity,
                        i,
                        new zaf(
                                super.getErrorResolutionIntent(activity, i, "d"),
                                lifecycleFragment),
                        onCancelListener);
        if (zaa2 == null) {
            return;
        }
        zad(activity, zaa2, "GooglePlayServicesErrorDialog", onCancelListener);
    }
}
