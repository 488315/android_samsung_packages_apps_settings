package com.android.settings.notification.history;

import android.graphics.drawable.Drawable;

import java.util.Objects;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NotificationHistoryPackage {
    public Drawable icon;
    public CharSequence label;
    public TreeSet notifications;
    public String pkgName;
    public int uid;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || NotificationHistoryPackage.class != obj.getClass()) {
            return false;
        }
        NotificationHistoryPackage notificationHistoryPackage = (NotificationHistoryPackage) obj;
        return this.uid == notificationHistoryPackage.uid
                && Objects.equals(this.pkgName, notificationHistoryPackage.pkgName)
                && Objects.equals(this.notifications, notificationHistoryPackage.notifications)
                && Objects.equals(this.label, notificationHistoryPackage.label)
                && Objects.equals(this.icon, notificationHistoryPackage.icon);
    }

    public final int hashCode() {
        return Objects.hash(
                this.pkgName, Integer.valueOf(this.uid), this.notifications, this.label, this.icon);
    }
}
