package com.samsung.android.settings.lockscreen;

import android.graphics.drawable.Drawable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ShowContentAppInfo {
    public final Drawable appIcon;
    public final String appName;
    public boolean isSelected;
    public final String packageName;
    public final int priority;

    public ShowContentAppInfo(String str, String str2, Drawable drawable, int i, boolean z) {
        this.appName = str;
        this.packageName = str2;
        this.appIcon = drawable;
        this.priority = i;
        this.isSelected = z;
    }
}
