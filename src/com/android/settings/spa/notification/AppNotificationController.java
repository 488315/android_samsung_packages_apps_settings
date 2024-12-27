package com.android.settings.spa.notification;

import android.content.pm.ApplicationInfo;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppNotificationController {
    public final AppNotificationController$_isEnabled$1 _isEnabled;
    public final ApplicationInfo app;
    public final AppNotificationRepository repository;

    public AppNotificationController(
            AppNotificationRepository repository, ApplicationInfo applicationInfo) {
        Intrinsics.checkNotNullParameter(repository, "repository");
        this.repository = repository;
        this.app = applicationInfo;
        this._isEnabled = new AppNotificationController$_isEnabled$1(this);
    }

    public final boolean getEnabled() {
        AppNotificationController$_isEnabled$1 appNotificationController$_isEnabled$1 =
                this._isEnabled;
        Boolean bool = (Boolean) appNotificationController$_isEnabled$1.getValue();
        if (bool != null) {
            return bool.booleanValue();
        }
        AppNotificationController appNotificationController =
                appNotificationController$_isEnabled$1.this$0;
        AppNotificationRepository appNotificationRepository = appNotificationController.repository;
        ApplicationInfo app = appNotificationController.app;
        appNotificationRepository.getClass();
        Intrinsics.checkNotNullParameter(app, "app");
        boolean areNotificationsEnabledForPackage =
                appNotificationRepository.notificationManager.areNotificationsEnabledForPackage(
                        app.packageName, app.uid);
        appNotificationController$_isEnabled$1.postValue(
                Boolean.valueOf(areNotificationsEnabledForPackage));
        return areNotificationsEnabledForPackage;
    }
}
