package com.android.settings.spa.notification;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NotificationSentState {
    public long lastSent;
    public int sentCount;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationSentState)) {
            return false;
        }
        NotificationSentState notificationSentState = (NotificationSentState) obj;
        return this.lastSent == notificationSentState.lastSent
                && this.sentCount == notificationSentState.sentCount;
    }

    public final int hashCode() {
        return Integer.hashCode(this.sentCount) + (Long.hashCode(this.lastSent) * 31);
    }

    public final String toString() {
        return "NotificationSentState(lastSent="
                + this.lastSent
                + ", sentCount="
                + this.sentCount
                + ")";
    }
}
