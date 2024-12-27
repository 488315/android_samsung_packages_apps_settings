package com.samsung.android.settings;

import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class Log {
    public static void d(String str, String str2) {
        SemLog.d(str, str2);
    }

    public static void e(String str, String str2) {
        SemLog.e(str, str2);
    }

    public static void i(String str) {
        SemLog.i("Eternal/DisplayItem", str);
    }
}
