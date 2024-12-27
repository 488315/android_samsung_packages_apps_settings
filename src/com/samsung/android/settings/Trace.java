package com.samsung.android.settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class Trace {
    public static void beginSection(String str) {
        if (str != null && str.length() > 96) {
            str = str.substring(0, 95);
        }
        android.os.Trace.traceBegin(1L, str);
    }

    public static void endSection() {
        android.os.Trace.traceEnd(1L);
    }
}
