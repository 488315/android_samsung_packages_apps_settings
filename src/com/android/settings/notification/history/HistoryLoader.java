package com.android.settings.notification.history;

import android.content.Context;
import android.content.pm.PackageManager;

import com.android.settings.notification.NotificationBackend;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HistoryLoader {
    public final NotificationBackend mBackend;
    public final Context mContext;
    public final PackageManager mPm;

    public HistoryLoader(
            Context context,
            NotificationBackend notificationBackend,
            PackageManager packageManager) {
        this.mContext = context;
        this.mBackend = notificationBackend;
        this.mPm = packageManager;
    }
}
