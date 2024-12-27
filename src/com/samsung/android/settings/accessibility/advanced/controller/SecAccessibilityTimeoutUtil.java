package com.samsung.android.settings.accessibility.advanced.controller;

import android.content.Context;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecAccessibilityTimeoutUtil {
    public static CharSequence getTimeoutTitle(Context context, int i) {
        if (i <= 0) {
            return context.getResources().getString(R.string.accessibility_timeout_default);
        }
        int i2 = i / 60000;
        int i3 = (i / 1000) - (i2 * 60);
        StringBuilder sb = new StringBuilder();
        if (i2 > 0) {
            sb.append(
                    context.getResources()
                            .getQuantityString(
                                    R.plurals.accessibility_timeout_summary_minutes,
                                    i2,
                                    Integer.valueOf(i2)));
            if (i3 > 0) {
                sb.append(" ");
                sb.append(
                        context.getResources()
                                .getQuantityString(
                                        R.plurals.accessibility_timeout_summary_seconds,
                                        i3,
                                        Integer.valueOf(i3)));
            }
        } else {
            sb.append(
                    context.getResources()
                            .getQuantityString(
                                    R.plurals.accessibility_timeout_summary_seconds,
                                    i3,
                                    Integer.valueOf(i3)));
        }
        return sb.toString();
    }
}
