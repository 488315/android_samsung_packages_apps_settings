package com.samsung.android.sdk.scs.base.utils;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class Log {
    public static String concatPrefixTag(String str) {
        String str2 = ApnSettings.MVNO_NONE;
        if (str != null) {
            str2 = str.replace("ScsApi@", ApnSettings.MVNO_NONE);
        }
        return "ScsApi@".concat(str2);
    }

    public static void e(String str, String str2) {
        android.util.Log.e(concatPrefixTag(str), str2);
    }

    public static void w(String str, String str2) {
        android.util.Log.w(concatPrefixTag(str), str2);
    }
}
