package com.android.settings;

import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract /* synthetic */ class MainClearConfirm$$ExternalSyntheticOutline0 {
    public static StringBuilder m(StringBuilder sb, boolean z, String str, String str2) {
        sb.append(z);
        Log.d(str, sb.toString());
        return new StringBuilder(str2);
    }

    public static void m(int i, String str, String str2) {
        Log.i(str2, str + i);
    }

    public static void m(String str, String str2, int i, int i2, String str3) {
        Log.d(str3, str + i + str2 + i2);
    }

    public static void m(StringBuilder sb, String str, String str2) {
        sb.append(str);
        Log.d(str2, sb.toString());
    }
}
