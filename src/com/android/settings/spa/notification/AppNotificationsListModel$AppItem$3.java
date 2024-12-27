package com.android.settings.spa.notification;

import android.app.NotificationChannel;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */ class AppNotificationsListModel$AppItem$3 extends FunctionReferenceImpl
        implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        NotificationChannel notificationChannelForPackage;
        Boolean bool = (Boolean) obj;
        boolean booleanValue = bool.booleanValue();
        AppNotificationController appNotificationController =
                (AppNotificationController) this.receiver;
        ApplicationInfo app = appNotificationController.app;
        AppNotificationRepository appNotificationRepository = appNotificationController.repository;
        appNotificationRepository.getClass();
        Intrinsics.checkNotNullParameter(app, "app");
        if (appNotificationRepository.notificationManager.onlyHasDefaultChannel(
                        app.packageName, app.uid)
                && (notificationChannelForPackage =
                                appNotificationRepository.notificationManager
                                        .getNotificationChannelForPackage(
                                                app.packageName,
                                                app.uid,
                                                "miscellaneous",
                                                (String) null,
                                                true))
                        != null) {
            notificationChannelForPackage.setImportance(booleanValue ? -1000 : 0);
            appNotificationRepository.notificationManager.updateNotificationChannelForPackage(
                    app.packageName, app.uid, notificationChannelForPackage);
        }
        try {
            appNotificationRepository.notificationManager.setNotificationsEnabledForPackage(
                    app.packageName, app.uid, booleanValue);
            appNotificationController._isEnabled.postValue(bool);
        } catch (Exception e) {
            Log.w("AppNotificationsRepo", "Error calling INotificationManager", e);
        }
        return Unit.INSTANCE;
    }
}
