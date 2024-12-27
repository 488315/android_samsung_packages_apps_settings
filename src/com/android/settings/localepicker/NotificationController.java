package com.android.settings.localepicker;

import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NotificationController {
    public static NotificationController sInstance;
    public final LocaleNotificationDataManager mDataManager;

    public NotificationController(Context context) {
        LocaleNotificationDataManager localeNotificationDataManager =
                new LocaleNotificationDataManager();
        localeNotificationDataManager.mContext = context;
        this.mDataManager = localeNotificationDataManager;
    }

    public static synchronized NotificationController getInstance(Context context) {
        NotificationController notificationController;
        synchronized (NotificationController.class) {
            try {
                if (sInstance == null) {
                    sInstance = new NotificationController(context.getApplicationContext());
                }
                notificationController = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return notificationController;
    }

    public LocaleNotificationDataManager getDataManager() {
        return this.mDataManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldTriggerNotification(int r19, java.lang.String r20) {
        /*
            r18 = this;
            r0 = r20
            boolean r1 = com.android.settings.localepicker.LocaleUtils.isInSystemLocale(r20)
            r2 = 0
            if (r1 == 0) goto La
            return r2
        La:
            r1 = r18
            com.android.settings.localepicker.LocaleNotificationDataManager r1 = r1.mDataManager
            com.android.settings.localepicker.NotificationInfo r3 = r1.getNotificationInfo(r0)
            if (r3 != 0) goto L2c
            com.android.settings.localepicker.NotificationInfo r3 = new com.android.settings.localepicker.NotificationInfo
            java.lang.Integer r4 = java.lang.Integer.valueOf(r19)
            java.util.Set r5 = java.util.Set.of(r4)
            r6 = 0
            r7 = 0
            r8 = 0
            r10 = 0
            r4 = r3
            r4.<init>(r5, r6, r7, r8, r10)
            r1.putNotificationInfo(r0, r3)
            goto L9f
        L2c:
            java.util.Set r12 = r3.mUidCollection
            java.lang.Integer r4 = java.lang.Integer.valueOf(r19)
            boolean r4 = r12.contains(r4)
            if (r4 == 0) goto L3a
            goto L9f
        L3a:
            r4 = 1
            int r5 = r3.mNotificationCount
            int r14 = r3.mDismissCount
            r6 = 2
            long r7 = r3.mLastNotificationTimeMs
            int r3 = r3.mNotificationId
            if (r14 >= r6) goto L8e
            if (r5 >= r6) goto L8e
            java.lang.Integer r9 = java.lang.Integer.valueOf(r19)
            r12.add(r9)
            int r9 = r12.size()
            int r9 = r9 % r6
            if (r9 != 0) goto L8e
            java.util.Calendar r6 = java.util.Calendar.getInstance()
            java.lang.String r9 = "android.localenotification.duration.threshold"
            r10 = 10080(0x2760, float:1.4125E-41)
            int r9 = android.os.SystemProperties.getInt(r9, r10)
            int r9 = r9 * (-1)
            r10 = 12
            r6.add(r10, r9)
            long r9 = r6.getTimeInMillis()
            int r6 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r6 >= 0) goto L72
            goto L8e
        L72:
            int r6 = r5 + 1
            java.util.Calendar r7 = java.util.Calendar.getInstance()
            long r7 = r7.getTimeInMillis()
            java.lang.String r9 = "notificationCount:"
            java.lang.String r10 = "NotificationController"
            com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0.m(r6, r9, r10)
            if (r6 != r4) goto L8a
            long r9 = android.os.SystemClock.uptimeMillis()
            int r3 = (int) r9
        L8a:
            r17 = r3
        L8c:
            r15 = r7
            goto L92
        L8e:
            r17 = r3
            r6 = r5
            goto L8c
        L92:
            com.android.settings.localepicker.NotificationInfo r3 = new com.android.settings.localepicker.NotificationInfo
            r11 = r3
            r13 = r6
            r11.<init>(r12, r13, r14, r15, r17)
            r1.putNotificationInfo(r0, r3)
            if (r6 <= r5) goto L9f
            r2 = r4
        L9f:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.localepicker.NotificationController.shouldTriggerNotification(int,"
                    + " java.lang.String):boolean");
    }
}
