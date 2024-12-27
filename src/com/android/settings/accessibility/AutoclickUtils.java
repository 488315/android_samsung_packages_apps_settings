package com.android.settings.accessibility;

import android.content.Context;

import com.android.settingslib.utils.StringUtil;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AutoclickUtils {
    public static CharSequence getAutoclickDelaySummary(Context context, int i, int i2) {
        int i3 = i2 == 1000 ? 1 : 3;
        float f = i2 / 1000.0f;
        String str = f == 1.0f ? "%.0f" : "%.1f";
        HashMap hashMap = new HashMap();
        hashMap.put("count", Integer.valueOf(i3));
        hashMap.put("time", String.format(str, Float.valueOf(f)));
        return StringUtil.getIcuPluralsString(context, hashMap, i);
    }
}
