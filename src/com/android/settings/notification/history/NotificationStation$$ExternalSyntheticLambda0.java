package com.android.settings.notification.history;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationStation$$ExternalSyntheticLambda0
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int i = NotificationStation.$r8$clinit;
        return Long.compare(
                ((NotificationStation.HistoricalNotificationInfo) obj2).timestamp,
                ((NotificationStation.HistoricalNotificationInfo) obj).timestamp);
    }
}
