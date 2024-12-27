package com.android.settingslib.utils;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ColorUtil {
    public static float getDisabledAlpha(Context context) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(new int[] {R.attr.disabledAlpha});
        float f = obtainStyledAttributes.getFloat(0, 0.0f);
        obtainStyledAttributes.recycle();
        return f;
    }
}
