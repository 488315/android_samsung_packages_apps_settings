package com.samsung.android.settings.usefulfeature.functionkey.item;

import android.graphics.drawable.Drawable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class FunctionKeyItem {
    public final int availabilityStatus;
    public final Drawable icon;
    public final String key;
    public final int order;
    public final int pressType;
    public final String summary;
    public final String title;

    public FunctionKeyItem(
            String str, int i, int i2, Drawable drawable, String str2, String str3, int i3) {
        this.key = str;
        this.pressType = i;
        this.availabilityStatus = i2;
        this.icon = drawable;
        this.title = str2;
        this.summary = str3;
        this.order = i3;
    }
}
