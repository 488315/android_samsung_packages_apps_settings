package com.samsung.android.settings.usefulfeature.functionkey.item;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FunctionKeyAction extends FunctionKeyItem {
    public final Intent actionIntent;
    public final int actionIntentType;
    public final int actionType;
    public final String actionValue;

    public FunctionKeyAction(
            String str,
            int i,
            int i2,
            Drawable drawable,
            String str2,
            String str3,
            int i3,
            int i4,
            String str4,
            int i5,
            Intent intent) {
        super(str, i, i2, drawable, str2, str3, i3);
        this.actionType = i4;
        this.actionValue = str4;
        this.actionIntentType = i5;
        this.actionIntent = intent;
    }
}
