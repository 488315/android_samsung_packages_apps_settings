package com.android.settings.spa.notification;

import android.content.pm.ApplicationInfo;

import androidx.lifecycle.MutableLiveData;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppNotificationController$_isEnabled$1 extends MutableLiveData {
    public final /* synthetic */ AppNotificationController this$0;

    public AppNotificationController$_isEnabled$1(
            AppNotificationController appNotificationController) {
        this.this$0 = appNotificationController;
    }

    @Override // androidx.lifecycle.LiveData
    public final void onActive() {
        AppNotificationController appNotificationController = this.this$0;
        AppNotificationRepository appNotificationRepository = appNotificationController.repository;
        ApplicationInfo app = appNotificationController.app;
        appNotificationRepository.getClass();
        Intrinsics.checkNotNullParameter(app, "app");
        postValue(
                Boolean.valueOf(
                        appNotificationRepository.notificationManager
                                .areNotificationsEnabledForPackage(app.packageName, app.uid)));
    }

    @Override // androidx.lifecycle.LiveData
    public final void onInactive() {}
}
