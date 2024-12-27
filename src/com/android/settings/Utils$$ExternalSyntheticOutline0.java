package com.android.settings;

import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract /* synthetic */ class Utils$$ExternalSyntheticOutline0 {
    public static StringBuilder m(String str, boolean z, String str2, boolean z2, String str3) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(z);
        sb.append(str2);
        sb.append(z2);
        sb.append(str3);
        return sb;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static void m653m(String str, boolean z, String str2, boolean z2, String str3) {
        Log.d(str3, str + z + str2 + z2);
    }

    public static void m(StringBuilder sb, String str, String str2) {
        sb.append(str);
        Log.i(str2, sb.toString());
    }
}
