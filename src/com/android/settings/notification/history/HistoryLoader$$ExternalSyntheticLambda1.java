package com.android.settings.notification.history;

import android.app.NotificationHistory;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class HistoryLoader$$ExternalSyntheticLambda1 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        NotificationHistoryPackage notificationHistoryPackage = (NotificationHistoryPackage) obj;
        NotificationHistoryPackage notificationHistoryPackage2 = (NotificationHistoryPackage) obj2;
        return Long.compare(
                        notificationHistoryPackage.notifications.isEmpty()
                                ? 0L
                                : ((NotificationHistory.HistoricalNotification)
                                                notificationHistoryPackage.notifications.first())
                                        .getPostedTimeMs(),
                        notificationHistoryPackage2.notifications.isEmpty()
                                ? 0L
                                : ((NotificationHistory.HistoricalNotification)
                                                notificationHistoryPackage2.notifications.first())
                                        .getPostedTimeMs())
                * (-1);
    }
}
