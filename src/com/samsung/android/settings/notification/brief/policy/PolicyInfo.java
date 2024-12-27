package com.samsung.android.settings.notification.brief.policy;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PolicyInfo {
    public final int category;
    public final int color;
    public final boolean defaultOn;
    public final String item;
    public final int priority;
    public final int range;
    public final int versionCode;

    public PolicyInfo(String str) {
        this.item = str;
        this.category = 11;
        this.range = 0;
        this.color = 0;
        this.versionCode = 0;
        this.priority = 0;
        this.defaultOn = false;
    }

    public final String toString() {
        return "item = "
                + this.item
                + ", category = "
                + this.category
                + ", range = "
                + this.range
                + ", versionCode = "
                + this.versionCode
                + ", color = "
                + this.color
                + ", priority = "
                + this.priority
                + ", defaultOn = "
                + this.defaultOn;
    }

    public PolicyInfo(int i, int i2, int i3, int i4, String str) {
        this.item = str;
        this.category = i;
        this.range = i2;
        this.color = i3;
        this.versionCode = i4;
        this.priority = 0;
        this.defaultOn = false;
    }

    public PolicyInfo(int i, String str, boolean z) {
        this.item = str;
        this.category = 10;
        this.priority = 1;
        this.defaultOn = z;
        this.color = i;
        this.range = 0;
        this.versionCode = 0;
    }
}
