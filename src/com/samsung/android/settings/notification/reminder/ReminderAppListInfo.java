package com.samsung.android.settings.notification.reminder;

import android.graphics.drawable.Drawable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ReminderAppListInfo {
    public final Drawable appIcon;
    public final String appName;
    public boolean isSelected;
    public final String packageName;

    public ReminderAppListInfo(String str, String str2, Drawable drawable, boolean z) {
        this.appName = str;
        this.packageName = str2;
        this.appIcon = drawable;
        this.isSelected = z;
    }
}
