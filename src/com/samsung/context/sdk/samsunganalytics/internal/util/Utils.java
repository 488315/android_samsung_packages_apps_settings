package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.os.Build;

import com.samsung.context.sdk.samsunganalytics.AnalyticsException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Utils {
    public static boolean compareDays(int i, Long l) {
        return System.currentTimeMillis() > (((long) i) * 86400000) + l.longValue();
    }

    public static void throwException(String str) {
        if (Build.TYPE.equals("eng")) {
            throw new AnalyticsException(str);
        }
        Debug.LogE(str);
    }
}
