package com.samsung.android.settings.usefulfeature.functionkey.item;

import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FunctionKeyApplication extends FunctionKeyItem {
    public final ArrayList actions;
    public final ComponentInfo componentInfo;
    public final Intent launchIntent;

    public FunctionKeyApplication(
            String str,
            int i,
            Drawable drawable,
            String str2,
            int i2,
            ComponentInfo componentInfo,
            Intent intent,
            ArrayList arrayList) {
        super(str, i, 0, drawable, str2, null, i2);
        this.componentInfo = componentInfo;
        this.launchIntent = intent;
        this.actions = arrayList;
    }
}
