package com.android.settings.localepicker;

import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NotificationInfo {
    public final int mDismissCount;
    public final long mLastNotificationTimeMs;
    public final int mNotificationCount;
    public final int mNotificationId;
    public final Set mUidCollection;

    public NotificationInfo(Set set, int i, int i2, long j, int i3) {
        this.mUidCollection = set;
        this.mNotificationCount = i;
        this.mDismissCount = i2;
        this.mLastNotificationTimeMs = j;
        this.mNotificationId = i3;
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationInfo)) {
            return false;
        }
        NotificationInfo notificationInfo = (NotificationInfo) obj;
        return this.mUidCollection.equals(notificationInfo.mUidCollection)
                && this.mDismissCount == notificationInfo.mDismissCount
                && this.mNotificationCount == notificationInfo.mNotificationCount
                && this.mLastNotificationTimeMs == notificationInfo.mLastNotificationTimeMs
                && this.mNotificationId == notificationInfo.mNotificationId;
    }

    public final int hashCode() {
        return Objects.hash(
                this.mUidCollection,
                Integer.valueOf(this.mDismissCount),
                Integer.valueOf(this.mNotificationCount),
                Long.valueOf(this.mLastNotificationTimeMs),
                Integer.valueOf(this.mNotificationId));
    }
}
