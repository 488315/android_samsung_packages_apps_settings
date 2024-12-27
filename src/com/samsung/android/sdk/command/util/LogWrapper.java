package com.samsung.android.sdk.command.util;

import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class LogWrapper {
    public static void e(String str, String str2) {
        Log.e("[CmdL-2.0.8]".concat(str), str2);
    }

    public static void i(String str) {
        Log.i("[CmdL-2.0.8]CommandProvider", str);
    }
}
