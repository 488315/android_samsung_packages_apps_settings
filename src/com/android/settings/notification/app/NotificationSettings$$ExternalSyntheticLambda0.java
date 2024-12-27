package com.android.settings.notification.app;

import android.app.NotificationChannel;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationSettings$$ExternalSyntheticLambda0
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        NotificationChannel notificationChannel = (NotificationChannel) obj;
        NotificationChannel notificationChannel2 = (NotificationChannel) obj2;
        boolean z = NotificationSettings.DEBUG;
        if (notificationChannel.isDeleted() != notificationChannel2.isDeleted()) {
            return Boolean.compare(
                    notificationChannel.isDeleted(), notificationChannel2.isDeleted());
        }
        if (notificationChannel.getId().equals("miscellaneous")) {
            return 1;
        }
        if (notificationChannel2.getId().equals("miscellaneous")) {
            return -1;
        }
        return notificationChannel.getId().compareTo(notificationChannel2.getId());
    }
}
