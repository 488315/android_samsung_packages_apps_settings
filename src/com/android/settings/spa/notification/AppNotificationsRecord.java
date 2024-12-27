package com.android.settings.spa.notification;

import android.content.pm.ApplicationInfo;

import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppNotificationsRecord implements AppRecord {
    public final ApplicationInfo app;
    public final AppNotificationController controller;
    public final NotificationSentState sentState;

    public AppNotificationsRecord(
            ApplicationInfo applicationInfo,
            NotificationSentState notificationSentState,
            AppNotificationController appNotificationController) {
        this.app = applicationInfo;
        this.sentState = notificationSentState;
        this.controller = appNotificationController;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppNotificationsRecord)) {
            return false;
        }
        AppNotificationsRecord appNotificationsRecord = (AppNotificationsRecord) obj;
        return Intrinsics.areEqual(this.app, appNotificationsRecord.app)
                && Intrinsics.areEqual(this.sentState, appNotificationsRecord.sentState)
                && Intrinsics.areEqual(this.controller, appNotificationsRecord.controller);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppRecord
    public final ApplicationInfo getApp() {
        return this.app;
    }

    public final int hashCode() {
        int hashCode = this.app.hashCode() * 31;
        NotificationSentState notificationSentState = this.sentState;
        return this.controller.hashCode()
                + ((hashCode
                                + (notificationSentState == null
                                        ? 0
                                        : notificationSentState.hashCode()))
                        * 31);
    }

    public final String toString() {
        return "AppNotificationsRecord(app="
                + this.app
                + ", sentState="
                + this.sentState
                + ", controller="
                + this.controller
                + ")";
    }
}
