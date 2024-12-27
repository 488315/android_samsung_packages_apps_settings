package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;

import com.android.settings.R;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface LimitedScale {
    static float calculateSize(float f, int i, Context context) {
        ArrayList arrayList = new ArrayList();
        float f2 = context.getResources().getConfiguration().fontScale;
        float f3 = (f / f2) / context.getResources().getDisplayMetrics().density;
        for (String str :
                context.getResources().getStringArray(R.array.sec_entryvalues_8_step_font_size)) {
            arrayList.add(Float.valueOf(str));
        }
        return Math.min(f2, ((Float) arrayList.get(i - 1)).floatValue()) * f3;
    }
}
